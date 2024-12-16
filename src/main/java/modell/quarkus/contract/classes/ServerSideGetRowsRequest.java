package modell.quarkus.contract.classes;



import modell.quarkus.contract.interfaces.ColumnVO;
import modell.quarkus.contract.interfaces.IServerSideGetRowsRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class ServerSideGetRowsRequest implements IServerSideGetRowsRequest {

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
    private Map<String, CombinedSimpleModel> filterModel = new HashMap<>();

    // if sorting, what the sort model is
    private List<SortModel> sortModel;

    public ServerSideGetRowsRequest() {
        this.rowGroupCols = emptyList();
        this.valueCols = emptyList();
        this.pivotCols = emptyList();
        this.groupKeys = emptyList();
        this.filterModel = emptyMap();
        this.sortModel = emptyList();
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