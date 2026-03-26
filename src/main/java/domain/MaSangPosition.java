package domain;

import java.util.List;
import java.util.Optional;

public enum MaSangPosition {
    MA_SANG_SANG_MA(1,
            List.of(new Position(1, 2), new Position(1, 8)),
            List.of(new Position(1, 3), new Position(1, 7)),
            List.of(new Position(10, 2), new Position(10, 8)),
            List.of(new Position(10, 3), new Position(10, 7))
    ),

    MA_SANG_MA_SANG(2,
            List.of(new Position(1, 2), new Position(1, 7)),
            List.of(new Position(1, 3), new Position(1, 8)),
            List.of(new Position(10, 2), new Position(10, 7)),
            List.of(new Position(10, 3), new Position(10, 8))
    ),

    SANG_MA_SANG_MA(3,
            List.of(new Position(1, 3), new Position(1, 8)),
            List.of(new Position(1, 2), new Position(1, 7)),
            List.of(new Position(10, 3), new Position(10, 8)),
            List.of(new Position(10, 2), new Position(10, 7))
    ),

    SANG_MA_MA_SANG(4,
            List.of(new Position(1, 3), new Position(1, 7)),
            List.of(new Position(1, 2), new Position(1, 8)),
            List.of(new Position(10, 3), new Position(10, 7)),
            List.of(new Position(10, 2), new Position(10, 8))
    );

    private final int command;
    private final List<Position> choMa;
    private final List<Position> choSang;
    private final List<Position> hanMa;
    private final List<Position> hanSang;

    MaSangPosition(int command, List<Position> choMa, List<Position> choSang, List<Position> hanMa, List<Position> hanSang) {
        this.command = command;
        this.choMa = choMa;
        this.choSang = choSang;
        this.hanMa = hanMa;
        this.hanSang = hanSang;
    }

    public static Optional<MaSangPosition> from(int command) {
        for (MaSangPosition maSangPosition : MaSangPosition.values()) {
            if (maSangPosition.command == command) {
                return Optional.of(maSangPosition);
            }
        }
        return Optional.empty();
    }

    public List<Position> getChoMa() {
        return choMa;
    }

    public List<Position> getChoSang() {
        return choSang;
    }

    public List<Position> getHanMa() {
        return hanMa;
    }

    public List<Position> getHanSang() {
        return hanSang;
    }
}