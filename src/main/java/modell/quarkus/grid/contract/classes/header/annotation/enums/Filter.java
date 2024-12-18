package modell.quarkus.grid.contract.classes.header.annotation.enums;

public enum Filter {
    NONE(null),
    TEXT("agTextColumnFilter"),
    NUMBER("agNumberColumnFilter"),
    DATE("agDateColumnFilter"),
    SET("agSetColumnFilter");
    // not available
    // MULTI("agMultiColumnFilter")

    private final String name;

    Filter(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
