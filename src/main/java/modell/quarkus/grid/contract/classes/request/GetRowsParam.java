package modell.quarkus.grid.contract.classes.request;


import modell.quarkus.grid.contract.classes.request.sort.SortModel;

import java.util.List;
import java.util.Map;

public class GetRowsParam implements IGetRowsParam {

    private Integer startRow, endRow;

    // row group columns
    private List<ColumnVO> rowGroupCols;

    // value columns
    private List<ColumnVO> valueCols;

    // pivot columns
    private List<ColumnVO> pivotCols;

    // true if pivot mode is one, otherwise false
    private Boolean pivotMode;

    // what groups the user is viewing
    private List<String> groupKeys;

    // if filtering, what the filter model is
    private Map<String, CombinedSimpleModel> filterModel;

    // if sorting, what the sort model is
    private List<SortModel> sortModel;

    public GetRowsParam() {
    }

    @Override
    public Integer getStartRow() {
        return startRow;
    }

    @Override
    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    @Override
    public Integer getEndRow() {
        return endRow;
    }

    @Override
    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    @Override
    public List<ColumnVO> getRowGroupCols() {
        return rowGroupCols;
    }

    @Override
    public void setRowGroupCols(List<ColumnVO> rowGroupCols) {
        this.rowGroupCols = rowGroupCols;
    }

    @Override
    public List<ColumnVO> getValueCols() {
        return valueCols;
    }

    @Override
    public void setValueCols(List<ColumnVO> valueCols) {
        this.valueCols = valueCols;
    }

    @Override
    public List<ColumnVO> getPivotCols() {
        return pivotCols;
    }

    @Override
    public void setPivotCols(List<ColumnVO> pivotCols) {
        this.pivotCols = pivotCols;
    }

    @Override
    public Boolean isPivotMode() {
        return pivotMode;
    }

    @Override
    public void setPivotMode(boolean pivotMode) {
        this.pivotMode = pivotMode;
    }

    @Override
    public List<String> getGroupKeys() {
        return groupKeys;
    }

    @Override
    public void setGroupKeys(List<String> groupKeys) {
        this.groupKeys = groupKeys;
    }

    @Override
    public Map<String, CombinedSimpleModel> getFilterModel() {
        return filterModel;
    }

    @Override
    public void setFilterModel(Map<String, CombinedSimpleModel> filterModel) {
        this.filterModel = filterModel;
    }

    @Override
    public List<SortModel> getSortModel() {
        return sortModel;
    }

    @Override
    public void setSortModel(List<SortModel> sortModel) {
        this.sortModel = sortModel;
    }
}