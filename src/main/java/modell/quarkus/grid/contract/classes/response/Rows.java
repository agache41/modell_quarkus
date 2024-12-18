package modell.quarkus.grid.contract.classes.response;

import java.util.List;

public class Rows<ENTITY> {

  private List<ENTITY> data;
  private Integer lastRow;
  private List<String> secondaryColumnFields;

  public Rows() {
  }

  public Rows(List<ENTITY> data, Integer lastRow, List<String> secondaryColumnFields) {
    this.data = data;
    this.lastRow = lastRow;
    this.secondaryColumnFields = secondaryColumnFields;
  }

  public List<ENTITY> getData() {
    return data;
  }

  public void setData(List<ENTITY> data) {
    this.data = data;
  }

  public Integer getLastRow() {
    return lastRow;
  }

  public void setLastRow(Integer lastRow) {
    this.lastRow = lastRow;
  }

  public List<String> getSecondaryColumnFields() {
    return secondaryColumnFields;
  }

  public void setSecondaryColumns(List<String> secondaryColumnFields) {
    this.secondaryColumnFields = secondaryColumnFields;
  }

}
