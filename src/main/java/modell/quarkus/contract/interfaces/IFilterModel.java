package modell.quarkus.contract.interfaces;


import java.util.List;

public interface IFilterModel {
    String getColId();
    void setColId(String colId);

    String getFilterType();

    void setFilterType(String filterType);

    String getType();

    void setType(String type);

    String getFilter();

    void setFilter(String filter);

    String getFilterTo();

    void setFilterTo(String filterTo);

    List<String> getValues();

    void setValues(List<String> values);

    String getDateFrom();

    void setDateFrom(String dateFrom);

    String getDateTo();

    void setDateTo(String dateTo);
}