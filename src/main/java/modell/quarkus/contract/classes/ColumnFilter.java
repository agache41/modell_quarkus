package modell.quarkus.contract.classes;

import java.util.List;

public class ColumnFilter {

  private String filterType;
  private String type;
  private String filter;
  private String filterTo;
  private String dateFrom;
  private String dateTo;
  private List<String> values;

  public ColumnFilter() {
  }

  public String getFilterType() {
    return filterType;
  }

  public void setFilterType(String filterType) {
    this.filterType = filterType;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public String getFilterTo() {
    return filterTo;
  }

  public void setFilterTo(String filterTo) {
    this.filterTo = filterTo;
  }

  public List<String> getValues() {
    return values;
  }

  public void setValues(List<String> values) {
    this.values = values;
  }

  public String getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
  }

  public String getDateTo() {
    return dateTo;
  }

  public void setDateTo(String dateTo) {
    this.dateTo = dateTo;
  }

}
