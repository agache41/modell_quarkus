package modell.quarkus.contract.classes;

import lombok.Data;
import lombok.NoArgsConstructor;
import modell.quarkus.contract.interfaces.HeaderInfo;

@Data
public class ColDef<T, R> {

    //private final FilterParams filterParams = new FilterParams();
    private String colId;
    private String headerName;
    private String field;
    private String aggFunc;
    private boolean sortable;
    private String filter;


    public ColDef() {
    }

    public ColDef(String fieldName, HeaderInfo headerInfo) {
        this.colId = fieldName;
        this.field = fieldName;
        this.headerName = headerInfo.name();
        this.sortable = headerInfo.sortable();
    }


//
//  public ColDef(
//      String headerName,
//      String colId,
//      String aggFunc,
//      boolean sortable,
//      String filter){
//    assert (Strings.isNotBlank(colId));
//    this.colId = colId;
//    assert (Strings.isNotBlank(headerName));
//    this.headerName = headerName;
//    this.field = colId;
//    assert (get != null);
//    this.get = get;
//    this.aggFunc = aggFunc;
//    this.sortable = sortable;
//    this.filter = filter != null ? filter : AgFilter.AG_DEFAULT_FILTER.getFilter();
//    this.toString = toString != null ? toString : new ToStringImpl<R>();
//  }
//
//  public ColDef(CSVFieldDescriptor fd) {
//    this(
//        fd.getDisplayLabel(),
//        fd.getColId(),
//        null,
//        (Get<T, R>) fd.getGet(),
//        fd.isSortable(),
//        AgFilter.getByClass(fd.getClazz()).getFilter(),
//        ToStringImpl.getByClass(fd.getClazz()));
//  }
//
//  public ColDef(String headerName, String field, Get<T, R> get) {
//    this(headerName, field, null, get, true, null, null);
//  }
//
//  public ColDef(String headerName, String field, Get<T, R> get, boolean sortable) {
//    this(headerName, field, null, get, sortable, null, null);
//  }
//
//  public ColDef(String headerName, String field, Get<T, R> get, boolean sortable, String filter) {
//    this(headerName, field, null, get, sortable, filter, null);
//  }
//
//  public ColDef(String headerName, String field, Get<T, R> get, boolean sortable, String filter, ToString<R> toString) {
//    this(headerName, field, null, get, sortable, filter, toString);
//  }
//
//  public String getColId() {
//    return colId;
//  }
//
//  public void setColId(String colId) {
//    this.colId = colId;
//  }
//
//  public String getHeaderName() {
//    return headerName;
//  }
//
//  public void setHeaderName(String headerName) {
//    this.headerName = headerName;
//  }
//
//  public String getField() {
//    return field;
//  }
//
//  public void setField(String field) {
//    this.field = field;
//  }
//
//  public String getAggFunc() {
//    return aggFunc;
//  }
//
//  public void setAggFunc(String aggFunc) {
//    this.aggFunc = aggFunc;
//  }
//
//  public Get<T, R> getGetter() {
//    return get;
//  }
//
//  public void setGetter(Get<T, R> get) {
//    this.get = get;
//  }
//
//  public boolean isSortable() {
//    return sortable;
//  }
//
//  public void setSortable(boolean sortable) {
//    this.sortable = sortable;
//  }
//
//  public String getFilter() {
//    return filter;
//  }
//
//  public void setFilter(String filter) {
//    this.filter = filter;
//  }
//
//  public FilterParams getFilterParams() {
//    return filterParams;
//  }
//
//  @Override
//  public boolean equals(Object o) {
//    if (this == o) {
//      return true;
//    }
//    if (o == null || getClass() != o.getClass()) {
//      return false;
//    }
//    ColDef colDef = (ColDef) o;
//    return Objects.equals(colId, colDef.colId) && Objects.equals(headerName, colDef.headerName)
//        && Objects.equals(field, colDef.field) && Objects.equals(aggFunc, colDef.aggFunc);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(colId, headerName, field, aggFunc);
//  }
//
//  /**
//   * Returns the string representation of the column.
//   *
//   * @param source
//   * @return
//   */
//  public String toString(@NotNull T source) {
//    R value;
//    return (value = this.get.get(source)) == null
//        ? null
//        : this.toString.toString(value);
//  }
//
//  public R getValue(T source) {
//    return this.get.get(source);
//  }
//
//  public boolean hasValue(T source) {
//    return this.getValue(source) != null;
//  }
//
//  public static class FilterParams {
//
//    private final boolean suppressAndOrCondition;
//
//    public FilterParams() {
//      this.suppressAndOrCondition = true;
//    }
//
//    public boolean isSuppressAndOrCondition() {
//      return suppressAndOrCondition;
//    }
//
//  }
}
