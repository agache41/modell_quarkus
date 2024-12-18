package modell.quarkus.grid.contract.classes.header.annotation.enums;

public enum HPosition {
    NONE(null),
    TRUE("true"),
    LEFT("left"),
    RIGHT("right");

    private final String name;

    HPosition(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
