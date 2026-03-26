package domain;

import domain.strategy.ChaMoveStrategy;
import domain.strategy.JangMoveStrategy;
import domain.strategy.JolMoveStrategy;
import domain.strategy.MaMoveStrategy;
import domain.strategy.MoveStrategy;
import domain.strategy.PoMoveStrategy;
import domain.strategy.SaMoveStrategy;
import domain.strategy.SangMoveStrategy;
import java.util.ArrayList;
import java.util.List;

public enum PieceType {
    CHA("차", new ChaMoveStrategy(),
            List.of(new Position(1, 1), new Position(1, 9)),
            List.of(new Position(10, 1), new Position(10, 9))),

    MA("마", new MaMoveStrategy(), List.of(), List.of()),

    SANG("상",new SangMoveStrategy(), List.of(), List.of()),

    SA("사", new SaMoveStrategy(),
            List.of(new Position(1, 4), new Position(1, 6)),
            List.of(new Position(10, 4), new Position(10, 6))),

    JANG("장", new JangMoveStrategy(),
            List.of(new Position(2, 5)),
            List.of(new Position(9, 5))),

    PO("포", new PoMoveStrategy(),
            List.of(new Position(3, 2), new Position(3, 8)),
            List.of(new Position(8, 2), new Position(8, 8))),

    JOL("졸", new JolMoveStrategy(),
            List.of(
                    new Position(4, 1), new Position(4, 3), new Position(4, 5),
                    new Position(4, 7), new Position(4, 9)
            ),
            List.of(
                    new Position(7, 1), new Position(7, 3), new Position(7, 5),
                    new Position(7, 7), new Position(7, 9)
            )),

    NONE("+", (start, end, board) -> false, List.of(), List.of()),;

    private String name;
    private MoveStrategy moveStrategy;
    private List<Position> choPosition;
    private List<Position> hanPosition;

    PieceType(String name, MoveStrategy moveStrategy, List<Position> choPosition, List<Position> hanPosition) {
        this.name = name;
        this.moveStrategy = moveStrategy;
        this.choPosition = choPosition;
        this.hanPosition = hanPosition;
    }

    public String getName() {
        return name;
    }

    public List<Position> getAllPosition() {
        List<Position> allPosition = new ArrayList<>(choPosition);
        allPosition.addAll(hanPosition);
        return allPosition;
    }

    public List<Position> getChoPosition() {
        return choPosition;
    }

    public List<Position> getHanPosition() {
        return hanPosition;
    }

    public boolean canMove(Position start, Position end, Board board) {
        return moveStrategy.canMove(start, end, board);
    }
}
