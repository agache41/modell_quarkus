package modell.quarkus.contract.interfaces;

public enum Sort {
    ASC("asc"), //
    DESC("desc"); //

    private final String name;

    Sort(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
