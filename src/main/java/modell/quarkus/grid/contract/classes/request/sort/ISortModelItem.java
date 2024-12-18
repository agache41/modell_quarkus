package modell.quarkus.grid.contract.classes.request.sort;

public interface ISortModelItem {
    String getColId();
    void setColId(String colId);

    Sort getSort();

    void setSort(Sort sort);
}
