package repository;

import dao.JanggiGameDao;
import dao.PieceDao;
import domain.JanggiGame;
import domain.Position;
import dto.BoardDto;
import dto.GameRecordDto;
import dto.SavedGameDto;
import dto.SavedPieceDto;
import java.sql.Connection;
import java.util.List;

public class JanggiGameRepository implements GameRepository{
    private final JanggiGameDao janggiGameDao;
    private final PieceDao pieceDao;

    public JanggiGameRepository(JanggiGameDao janggiGameDao, PieceDao pieceDao) {
        this.janggiGameDao = janggiGameDao;
        this.pieceDao = pieceDao;
    }

    @Override
    public int createNewGame(String initialTurn) {
        return janggiGameDao.createNewGame(initialTurn);
    }

    @Override
    public void saveInitBoard(int gameId, BoardDto boardDto) {
        pieceDao.savePiecePosition(gameId, boardDto);
    }

    @Override
    public void saveMove(Connection connection, int gameId, Position start, Position end, boolean isJangRemoved,
                         JanggiGame janggiGame) {
        if (isJangRemoved) {
            pieceDao.deletePiece(gameId, end.getRow(), end.getCol());
        }
        pieceDao.movePiece(gameId, start.getRow(), start.getCol(), end.getRow(), end.getCol());

        if (janggiGame.isPlaying()) {
            janggiGameDao.updateTurn(gameId, janggiGame.getCountry().name());
        }
    }

    @Override
    public List<SavedGameDto> getSavedGames() {
        return janggiGameDao.getSavedGames();
    }

    @Override
    public List<SavedPieceDto> getSavedBoard(int gameId) {
        return pieceDao.getSavedBoard(gameId);
    }

    @Override
    public String getSavedTurn(int gameId) {
        return janggiGameDao.getSavedTurn(gameId);
    }

    @Override
    public void finishGame(int gameId, double choScore, double hanScore) {
        janggiGameDao.finishGame(gameId, choScore, hanScore);
    }

    @Override
    public List<GameRecordDto> getGameRecords() {
        return janggiGameDao.getGameRecords();
    }
}
