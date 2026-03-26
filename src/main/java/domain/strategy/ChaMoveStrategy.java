package domain.strategy;

import domain.Board;
import domain.Country;
import domain.Position;

public class ChaMoveStrategy implements MoveStrategy {

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

        // 가로나 세로가 같으면 움직일 수 있음
        return start.getX() == end.getX() || start.getY() == end.getY();
    }
}
