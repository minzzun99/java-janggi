package domain.strategy;

import domain.Board;
import domain.Position;

public class SangMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position start, Position end, Board board) {
        return false;
    }
}
