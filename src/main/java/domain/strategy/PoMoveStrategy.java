package domain.strategy;

import domain.Board;
import domain.Country;
import domain.PieceType;
import domain.Position;

public class PoMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Position start, Position end, Board board) {
        if (start.equals(end)) {
            return false;
        }

        Country startCountry = board.getCountry(start);
        Country endCountry = board.getCountry(end);
        if (startCountry.equals(Country.NONE)) {
            throw new IllegalArgumentException("기물이 존재하지 않습니다.");
        }

        if (startCountry.equals(endCountry)) {
            return false;
        }

        // 가로나 세로가 같은지 (직선)
        if (start.getX() != end.getX() && start.getY() != end.getY()) {
            return false;
        }

        // 중간에 기물 하나인지
        if (board.countSameLine(start, end) != 1) {
            return false;
        }

        // 넘는게 포인지
        if (board.getSameLinePieceType(start, end).equals(PieceType.PO)) {
            return false;
        }

        // 도착 기물이 포인지
        if (board.getPiece(end).equals(PieceType.PO)) {
            return false;
        }

        return true;
    }
}
