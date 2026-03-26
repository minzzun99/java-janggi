package domain.strategy;

import domain.Board;
import domain.Country;
import domain.Position;

public class JolMoveStrategy implements MoveStrategy{

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

        int changeX = end.getX() - start.getX();
        int changeY = end.getY() - start.getY();
        if (Math.abs(changeX) + Math.abs(changeY) != 1) {
            return false;
        }

        if (startCountry.equals(Country.CHO)) {
            return changeX >= 0;
        }
        return changeX <= 0;
    }
}
