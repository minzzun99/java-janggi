package view;

import java.util.Arrays;

public enum PieceName {
    CHA("차"),
    MA("마"),
    SANG("상"),
    SA("사"),
    JANG("장"),
    PO("포"),
    JOL("졸"),
    NONE("+");

    private final String name;

    PieceName(String name) {
        this.name = name;
    }

    public static String getPieceName(String pieceType) {
        return Arrays.stream(values())
                .filter(pieceName -> pieceName.name().equals(pieceType))
                .findAny()
                .map(pieceName -> pieceName.name)
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 값입니다."));
    }

    public static String getPieceType(String pieceName) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.name.equals(pieceName))
                .findAny()
                .map(Enum::name)
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 입력입니다."));
    }
}
