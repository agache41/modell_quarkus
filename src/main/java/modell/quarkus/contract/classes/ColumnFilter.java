package modell.quarkus.contract.classes;

import modell.quarkus.contract.interfaces.IFilterModel;

import java.util.List;

public class ColumnFilter implements IFilterModel {

  private String colId;
  private String filterType;
  private String type;
  private String filter;
  private String filterTo;
  private String dateFrom;
  private String dateTo;
  private List<String> values;

  public ColumnFilter() {
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
  public String getFilterType() {
    return filterType;
  }

  @Override
  public void setFilterType(String filterType) {
    this.filterType = filterType;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String getFilter() {
    return filter;
  }

  @Override
  public void setFilter(String filter) {
    this.filter = filter;
  }

  @Override
  public String getFilterTo() {
    return filterTo;
  }

  @Override
  public void setFilterTo(String filterTo) {
    this.filterTo = filterTo;
  }

  @Override
  public List<String> getValues() {
    return values;
  }

  @Override
  public void setValues(List<String> values) {
    this.values = values;
  }

  @Override
  public String getDateFrom() {
    return dateFrom;
  }

  @Override
  public void setDateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
  }

  @Override
  public String getDateTo() {
    return dateTo;
  }

  @Override
  public void setDateTo(String dateTo) {
    this.dateTo = dateTo;
  }

}
