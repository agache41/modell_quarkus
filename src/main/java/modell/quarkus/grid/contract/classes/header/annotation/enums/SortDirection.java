package modell.quarkus.grid.contract.classes.header.annotation.enums;

public enum SortDirection {
    NONE(null),
    ASC("asc"),
    DESC("desc");

    private final String name;

    SortDirection(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
