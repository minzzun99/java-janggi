package domain.strategy;

import domain.Position;
import domain.Piece;
import domain.constant.Palace;
import domain.constant.PieceType;
import java.util.ArrayList;
import java.util.List;

public class ChaMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        if (start.getRow() == end.getRow() || start.getCol() == end.getCol()) {
            return true;
        }
        Palace palace = Palace.from(piece.country());
        return palace.isDiagonalPath(start, end);
    }

    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return pieces.isEmpty();
    }

    @Override
    public List<Position> getRoutePosition(Piece startPiece, Position start, Position end) {
        if (start.getRow() == end.getRow() || start.getCol() == end.getCol()) {
            return getStraightRoute(start, end);
        }
        return getDiagonalRoute(startPiece, start, end);
    }

    private List<Position> getStraightRoute(Position start, Position end) {
        if (start.getRow() == end.getRow()) {
            return getHorizontalRoute(start, end);
        }
        return getVerticalRoute(start, end);
    }

    private List<Position> getHorizontalRoute(Position start, Position end) {
        List<Position> positions = new ArrayList<>();
        int row = start.getRow();
        int minCol = Math.min(start.getCol(), end.getCol());
        int maxCol = Math.max(start.getCol(), end.getCol());
        for (int i = minCol + 1; i < maxCol; i++) {
            positions.add(Position.create(row, i));
        }

        return positions;
    }

    private List<Position> getVerticalRoute(Position start, Position end) {
        List<Position> positions = new ArrayList<>();
        int col = start.getCol();
        int minRow = Math.min(start.getRow(), end.getRow());
        int maxRow = Math.max(start.getRow(), end.getRow());
        for (int i = minRow + 1; i < maxRow; i++) {
            positions.add(Position.create(i, col));
        }

        return positions;
    }

    private List<Position> getDiagonalRoute(Piece startPiece, Position start, Position end) {
        List<Position> positions = new ArrayList<>();
        Palace palace = Palace.from(startPiece.country());
        if (palace.isDiagonalPath(start, end) && Math.abs(end.getRow() - start.getRow()) == 2) {
            positions.add(palace.getCenter());
        }

        return positions;
    }
}
