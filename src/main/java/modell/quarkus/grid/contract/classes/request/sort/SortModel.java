package modell.quarkus.grid.contract.classes.request.sort;

public class SortModel implements ISortModelItem {
    private String colId;
    private Sort sort;

    public SortModel() {
    }

    @Override
    public String getColId() {
        return colId;
    }

    @Override
    public void setColId(String colId) {
        this.colId = colId;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public void setSort(Sort sort) {
        this.sort = sort;
    }

}
