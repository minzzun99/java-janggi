package domain;

import domain.constant.Country;
import domain.constant.Palace;
import domain.constant.PieceType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(List<PieceType> choMaSang, List<PieceType> hanMaSang) {
        this.board = BoardFactory.createInitBoard(choMaSang, hanMaSang);
    }

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public boolean move(Position start, Position end) {
        Piece startPiece = board.getOrDefault(start, Piece.getEmptyPiece());
        Piece endPiece = board.getOrDefault(end, Piece.getEmptyPiece());

        validateMove(start, end, startPiece, endPiece);

        boolean isJangRemoved = checkJangRemoved(end);
        movePiece(start, end, startPiece);

        return isJangRemoved;
    }

    private void validateMove(Position start, Position end, Piece startPiece, Piece endPiece) {
        if (startPiece.isPalacePiece()) {
            validatePalaceMove(startPiece, end);
        }

        if (!canMove(startPiece, start, end, endPiece)) {
            throw new IllegalArgumentException("말을 이동할 수 없습니다.");
        }
    }

    private void validatePalaceMove(Piece piece, Position end) {
        Palace palace = Palace.from(piece.country());
        if (!palace.isPalace(end)) {
            throw new IllegalArgumentException("장과 사는 궁성 내부에서만 이동 가능합니다.");
        }
    }

    private boolean canMove(Piece startPiece, Position start, Position end, Piece endPiece) {
        return startPiece.canMovePosition(start, end)
                && startPiece.isDifferentCountry(endPiece.country())
                && startPiece.isAvailableRoute(getPieceRoute(startPiece, start, end), endPiece.pieceType());
    }

    private void movePiece(Position start, Position end, Piece piece) {
        removePiece(start);
        board.put(end, piece);
    }

    private List<Piece> getPieceRoute(Piece startPiece, Position start, Position end) {
        PieceType pieceType = startPiece.pieceType();
        List<Position> piecePositions = pieceType.getRoutePosition(startPiece, start, end);
        return piecePositions.stream()
                .filter(board::containsKey)
                .map(board::get)
                .toList();
    }

    private void removePiece(Position endPosition) {
        board.remove(endPosition);
    }

    public PieceType getPieceType(Position position) {
        if (!board.containsKey(position)) {
            return PieceType.NONE;
        }
        return board.get(position).pieceType();
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(board);
    }

    public List<Position> getPiecesNowPosition(Country country, PieceType pieceType) {
        List<Position> positions = board.entrySet().stream()
                .filter(entry ->
                        entry.getValue().pieceType() == pieceType &&
                                entry.getValue().country() == country)
                .map(Map.Entry::getKey)
                .toList();
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 기물입니다. 장기판 위의 기물을 입력해주세요.");
        }
        return positions;
    }

    public double calculateScore(Country country) {
        return board.values().stream()
                .filter(piece -> piece.country() == country)
                .mapToDouble(Piece::getScore)
                .sum();
    }

    private boolean checkJangRemoved(Position end) {
        Piece piece = board.getOrDefault(end, Piece.getEmptyPiece());
        return piece.pieceType() == PieceType.JANG;
    }

    public boolean checkEndPosition(Position end) {
        return board.getOrDefault(end, Piece.getEmptyPiece()).isEmpty();
    }
}
