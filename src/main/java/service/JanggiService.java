package service;

import domain.Board;
import domain.MaSangPosition;
import domain.PieceType;
import domain.Position;
import java.util.ArrayList;
import java.util.List;
import service.dto.BoardDto;

public class JanggiService {

    public Board createBoard(int choCommand, int hanCommand) {
        // TODO : 마상관련 처리 필요
        return new Board(createMaSangPosition(choCommand), createMaSangPosition(hanCommand));
    }

    private MaSangPosition createMaSangPosition(int command) {
        return MaSangPosition.from(command)
                .orElseThrow(() -> new IllegalArgumentException("다시 입력해주세요."));
    }

    public BoardDto getBoard(Board board) {
        List<BoardDto.Row> boardAll = new ArrayList<>();
        List<String> values;
        for (int x = 1; x <= Position.MAX_ROW; x++) {
            values = new ArrayList<>();
            for (int y = 1; y <= Position.MAX_COL; y++) {
                PieceType pieceType = board.getPiece(new Position(x, y));
                values.add(pieceType.getName());
            }
            boardAll.add(new BoardDto.Row(values));
        }

        return new BoardDto(boardAll);
    }
}
