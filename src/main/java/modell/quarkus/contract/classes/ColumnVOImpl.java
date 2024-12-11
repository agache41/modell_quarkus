package modell.quarkus.contract.classes;


import modell.quarkus.contract.interfaces.ColumnVO;

public class ColumnVOImpl implements ColumnVO {
    private final String id;
    private final String displayName;
    private String field;
    private String aggFunc;

    public ColumnVOImpl(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String getAggFunc() {
        return aggFunc;
    }

    @Override
    public void setAggFunc(String aggFunc) {
        this.aggFunc = aggFunc;
    }
}
