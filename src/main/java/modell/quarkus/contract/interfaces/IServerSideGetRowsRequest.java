package modell.quarkus.contract.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IServerSideGetRowsRequest extends Serializable {
    Long getStartRow();

    void setStartRow(Long startRow);

    Long getEndRow();

    void setEndRow(Long endRow);

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

    Map<String, IFilterModel> getFilterModel();

    void setFilterModel(Map<String, IFilterModel> filterModel);

    List<ISortModelItem> getSortModel();

    void setSortModel(List<ISortModelItem> sortModel);
}
