package net.ramixin.soulstones.units;

public enum State {
    FAILURE,
    SUCCESS
    ;

    public static State fromOrdinal(int ordinal) {
        return switch (ordinal) {
            case 0 -> FAILURE;
            case 1 -> SUCCESS;
            default -> throw new IllegalStateException("Unexpected ordinal value: " + ordinal);
        };
    }
}
