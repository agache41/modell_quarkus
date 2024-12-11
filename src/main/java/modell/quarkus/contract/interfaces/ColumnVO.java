package modell.quarkus.contract.interfaces;

public interface ColumnVO {
    String getId();

    String getDisplayName();

    String getField();

    void setField(String field);

    String getAggFunc();

    void setAggFunc(String aggFunc);
}
