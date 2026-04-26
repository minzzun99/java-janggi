package service;

import config.ConnectionManager;
import domain.Board;
import domain.BoardFactory;
import domain.JanggiGame;
import domain.Position;
import domain.constant.Country;
import domain.constant.MaSang;
import domain.constant.PieceType;
import dto.BoardDto;
import dto.GameRecordDto;
import dto.GameResultDto;
import dto.PieceDto;
import dto.PositionDto;
import dto.SavedGameDto;
import dto.SavedPieceDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import repository.GameRepository;

public class JanggiService {
    private final GameRepository gameRepository;

    public JanggiService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public int createNewGame(String initialTurn) {
        return gameRepository.createNewGame(initialTurn);
    }

    public void saveInitBoard(int gameId, Board board) {
        BoardDto boardDto = createBoardDto(board);
        gameRepository.saveInitBoard(gameId, boardDto);
    }

    public Board createBoard(List<PieceType> choMaSangChoose, List<PieceType> hanMaSangChoose) {
        return new Board(choMaSangChoose, hanMaSangChoose);
    }

    public JanggiGame createJanggiGame(Board board) {
        return new JanggiGame(board);
    }

    public BoardDto createBoardDto(Board board) {
        Map<PositionDto, PieceDto> pieceDtos = board.getPieces().entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(
                        entry -> new PositionDto(entry.getKey().getRow(), entry.getKey().getCol()),
                        entry -> new PieceDto(entry.getValue().country().name(),
                                entry.getValue().pieceType().name())
                ));
        return new BoardDto(pieceDtos);
    }

    public List<PositionDto> getPiecePositions(JanggiGame janggiGame, PieceType pieceType) {
        List<PositionDto> positionDtos = new ArrayList<>();
        for (Position position : janggiGame.getPiecesNowPosition(pieceType)) {
            positionDtos.add(new PositionDto(position.getRow(), position.getCol()));
        }
        return positionDtos;
    }

    public void applyMove(int gameId, Position start, Position end, JanggiGame game) {
        boolean isRemoved = !game.isEmptyPosition(end);
        game.play(start, end);
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            gameRepository.saveMove(connection, gameId, start, end, isRemoved, game);
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException se) {
                    throw new RuntimeException("롤백 실패");
                }
            }
            throw new RuntimeException("기물 이동 트랜잭션 처리 중 에러 발생");
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("커넥션 종료 실패");
                }
            }
        }
    }

    public List<PieceType> createMaSang(int command) {
        return MaSang.getMaSangPosition(command);
    }

    public List<SavedGameDto> getSavedGames() {
        return gameRepository.getSavedGames();
    }

    public Board getSavedBoard(int gameId) {
        List<SavedPieceDto> savedPieceDtos = gameRepository.getSavedBoard(gameId);
        return new Board(BoardFactory.createLoadBoard(savedPieceDtos));
    }

    public JanggiGame loadGame(int gameId, Board board) {
        Country country = Country.getCountry(gameRepository.getSavedTurn(gameId));
        return new JanggiGame(board, country);
    }

    public void finishGame(int gameId, JanggiGame janggiGame) {
        gameRepository.finishGame(gameId, janggiGame.calculateChoScore(), janggiGame.calculateHanScore());
    }

    public List<GameRecordDto> getGameRecords() {
        return gameRepository.getGameRecords();
    }

    public GameResultDto getGameResult(JanggiGame janggiGame) {
        return new GameResultDto(janggiGame.getWinnerCountry(), janggiGame.calculateChoScore(),
                janggiGame.calculateHanScore());
    }
}
