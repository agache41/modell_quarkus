package modell.quarkus.grid.contract.classes.request;

import modell.quarkus.grid.contract.classes.request.sort.SortModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IGetRowsParam extends Serializable {
    Integer getStartRow();

    void setStartRow(Integer startRow);

    Integer getEndRow();

    void setEndRow(Integer endRow);

    List<ColumnVO> getRowGroupCols();

    void setRowGroupCols(List<ColumnVO> rowGroupCols);

    List<ColumnVO> getValueCols();

    void setValueCols(List<ColumnVO> valueCols);

    List<ColumnVO> getPivotCols();

    void setPivotCols(List<ColumnVO> pivotCols);

    Boolean isPivotMode();

    void setPivotMode(boolean pivotMode);

    List<String> getGroupKeys();

    void setGroupKeys(List<String> groupKeys);

    Map<String, CombinedSimpleModel> getFilterModel();

    void setFilterModel(Map<String, CombinedSimpleModel> filterModel);

    List<SortModel> getSortModel();

    void setSortModel(List<SortModel> sortModel);
}
