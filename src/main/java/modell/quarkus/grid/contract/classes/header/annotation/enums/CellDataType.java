package modell.quarkus.grid.contract.classes.header.annotation.enums;

public enum CellDataType {
    NONE(null),
    TEXT("text"),
    NUMBER("number"),
    BOOLEAN("boolean"),
    DATE("date"),
    DATESTRING("dateString"),
    OBJECT("object");

    private final String name;

    CellDataType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
