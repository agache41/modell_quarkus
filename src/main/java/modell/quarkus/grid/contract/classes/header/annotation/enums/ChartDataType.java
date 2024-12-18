package modell.quarkus.grid.contract.classes.header.annotation.enums;

public enum ChartDataType {
    NONE(null),
    CATEGORY("category"),
    SERIES("series"),
    TIME("time"),
    EXCLUDED("excluded");

    private final String name;

    ChartDataType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
