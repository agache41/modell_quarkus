package modell.quarkus.contract.interfaces;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Filter {
    NONE(""),
    TEXT("true"),
    NUMBER("agNumberColumnFilter"),
    DATE("agDateColumnFilter"),
    SET("agSetColumnFilter"),
    MULTI("agMultiColumnFilter");

    private final String name;

    Filter(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
