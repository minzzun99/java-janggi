package repository;

import domain.JanggiGame;
import domain.Position;
import dto.BoardDto;
import dto.GameRecordDto;
import dto.SavedGameDto;
import dto.SavedPieceDto;
import java.sql.Connection;
import java.util.List;

public interface GameRepository {
    int createNewGame(String initialTurn);

    void saveInitBoard(int gameId, BoardDto boardDto);

    void saveMove(Connection connection, int gameId, Position start, Position end, boolean isJangRemoved,
                  JanggiGame janggiGame);

    List<SavedGameDto> getSavedGames();

    List<SavedPieceDto> getSavedBoard(int gameId);

    String getSavedTurn(int gameId);

    void finishGame(int gameId, double choScore, double hanScore);

    List<GameRecordDto> getGameRecords();
}
