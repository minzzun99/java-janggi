package domain;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(MaSangPosition choSetUp, MaSangPosition hanSetUp) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceType type : PieceType.values()) {
            if (type.equals(PieceType.NONE) || type.equals(PieceType.MA) || type.equals(PieceType.SANG)) {
                continue;
            }

            for (Position position : type.getChoPosition()) {
                pieces.put(position, new Piece(Country.CHO, type));
            }

            for (Position position : type.getHanPosition()) {
                pieces.put(position, new Piece(Country.HAN, type));
            }
        }

        for (Position position : choSetUp.getChoMa()) {
            pieces.put(position, new Piece(Country.CHO, PieceType.MA));
        }

        for (Position position : choSetUp.getChoSang()) {
            pieces.put(position, new Piece(Country.CHO, PieceType.SANG));
        }

        for (Position position : hanSetUp.getHanMa()) {
            pieces.put(position, new Piece(Country.HAN, PieceType.MA));
        }

        for (Position position : hanSetUp.getHanSang()) {
            pieces.put(position, new Piece(Country.HAN, PieceType.SANG));
        }

        this.board = pieces;
    }

    public PieceType getPiece(Position position) {
        if(!board.containsKey(position)) {
            return PieceType.NONE;
        }
        return board.get(position).getPieceType();
    }

    public Country getCountry(Position position) {
        if (!board.containsKey(position)) {
            return Country.NONE;
        }
        Piece piece = board.get(position);
        return piece.getCountry();
    }

    public int countSameLine(Position start, Position end) {
        int count = 0;
        // 가로 이동 - x좌표 동일
        if (start.getX() == end.getX()) {
            int x = start.getX();
            int minY = Math.min(start.getY(), end.getY());
            int maxY = Math.max(start.getY(), end.getY());
            for (int y = minY + 1; y < maxY; y++) {
                if (getPiece(new Position(x, y)) != PieceType.NONE) {
                    count++;
                }
            }
        }

        // 세로 이동 - y좌표 동일
        if (start.getY() == end.getY()) {
            int y = start.getY();
            int minX = Math.min(start.getX(), end.getX());
            int maxX = Math.max(start.getX(), end.getX());
            for (int x = minX + 1; x < maxX; x++) {
                if (getPiece(new Position(x, y)) != PieceType.NONE) {
                    count++;
                }
            }
        }
        return count;
    }

    public PieceType getSameLinePieceType(Position start, Position end) {
        if (start.getX() == end.getX()) {
            int x = start.getX();
            int minY = Math.min(start.getY(), end.getY());
            int maxY = Math.max(start.getY(), end.getY());
            for (int y = minY + 1; y < maxY; y++) {
                if (getPiece(new Position(x, y)) != PieceType.NONE) {
                    return getPiece(new Position(x, y));
                }
            }
        }

        // 세로 이동 - y좌표 동일
        if (start.getY() == end.getY()) {
            int y = start.getY();
            int minX = Math.min(start.getX(), end.getX());
            int maxX = Math.max(start.getX(), end.getX());
            for (int x = minX + 1; x < maxX; x++) {
                if (getPiece(new Position(x, y)) != PieceType.NONE) {
                    return getPiece(new Position(x, y));
                }
            }
        }

        return PieceType.NONE;
    }
}
