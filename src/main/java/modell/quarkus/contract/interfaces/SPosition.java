package modell.quarkus.contract.interfaces;

public enum SPosition {
    NONE(""),
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
