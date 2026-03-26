package domain.strategy;

import domain.Board;
import domain.Position;

public interface MoveStrategy {
    boolean canMove(Position start, Position end, Board board);
}