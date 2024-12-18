package modell.quarkus.grid.contract.classes.request;

public interface ColumnVO {
    String getId();

    String getDisplayName();

    String getField();

    void setField(String field);

    String getAggFunc();

    void setAggFunc(String aggFunc);
}
