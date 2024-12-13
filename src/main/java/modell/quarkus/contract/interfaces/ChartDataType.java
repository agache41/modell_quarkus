package modell.quarkus.contract.interfaces;

public enum ChartDataType {
    NONE(""),
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
