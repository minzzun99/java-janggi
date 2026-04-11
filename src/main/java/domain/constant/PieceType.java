    package domain.constant;

    import domain.Piece;
    import domain.Position;
    import domain.strategy.ChaMoveRule;
    import domain.strategy.JangMoveRule;
    import domain.strategy.JolMoveRule;
    import domain.strategy.MaMoveRule;
    import domain.strategy.MoveRule;
    import domain.strategy.PoMoveRule;
    import domain.strategy.SaMoveRule;
    import domain.strategy.SangMoveRule;
    import java.util.List;

    public enum PieceType {
        CHA(new ChaMoveRule(), 13),
        MA(new MaMoveRule(), 5),
        SANG(new SangMoveRule(), 3),
        SA(new SaMoveRule(), 3),
        JANG(new JangMoveRule(), 0),
        PO(new PoMoveRule(), 7),
        JOL(new JolMoveRule(), 2),
        NONE((start, end, piece) -> false, 0);

        private final MoveRule moveRule;
        private final int score;

        PieceType(MoveRule moveRule, int score) {
            this.moveRule = moveRule;
            this.score = score;
        }

        public boolean canMovePosition(Position start, Position end, Piece piece) {
            return moveRule.canMovePosition(start, end, piece);
        }

        public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
            return moveRule.isAvailableRoute(pieces, endPieceType);
        }

        public int getScore() {
            return score;
        }
    }
