package modell.quarkus.contract.classes;

import java.util.List;
import java.util.Map;

public class EnterpriseGetRowsResponse<ENTITY> {

  private List<ENTITY> data;
  private Long lastRow;
  private List<String> secondaryColumnFields;

  public EnterpriseGetRowsResponse() {
  }

  public EnterpriseGetRowsResponse(List<ENTITY> data, Long lastRow, List<String> secondaryColumnFields) {
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

  public Long getLastRow() {
    return lastRow;
  }

  public void setLastRow(Long lastRow) {
    this.lastRow = lastRow;
  }

  public List<String> getSecondaryColumnFields() {
    return secondaryColumnFields;
  }

  public void setSecondaryColumns(List<String> secondaryColumnFields) {
    this.secondaryColumnFields = secondaryColumnFields;
  }

}
