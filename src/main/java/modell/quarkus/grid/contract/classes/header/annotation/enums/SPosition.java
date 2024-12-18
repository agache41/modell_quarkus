package modell.quarkus.grid.contract.classes.header.annotation.enums;

public enum SPosition {
    NONE(null),
    OVER("over"),
    UNDER("under");

    private final String name;

    SPosition(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
