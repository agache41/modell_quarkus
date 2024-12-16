package modell.quarkus.contract.classes;

import lombok.Data;
import modell.quarkus.contract.interfaces.HeaderInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class ColDef<T, R> {
    private final String colId;
    private final String field;

    //Columns
    private List<String> type;
    private String cellDataType;
    //Columns:Display
    private Boolean hide;
    private Boolean initialHide;
    private Boolean lockVisible;
    private String lockPosition;
    private Boolean suppressMovable;
    private Boolean useValueFormatterForExport;
    //Columns:Editing
    private Boolean editable;
    private Boolean cellEditorPopup;
    private String cellEditorPopupPosition;
    private Boolean singleClickEdit;
    private Boolean useValueParserForImport;
    //Columns:Filter
    private String filter;
    private Boolean floatingFilter;

    private FilterParams filterParams = new FilterParams();


    //Columns: Header
    private String headerName;
    private String headerTooltip;
    private Boolean wrapHeaderText;
    private Boolean autoHeaderHeight;
    private Boolean suppressHeaderMenuButton;
    private Boolean suppressHeaderFilterButton;
    private Boolean suppressHeaderContextMenu;
    private Boolean suppressHeaderKeyboardEvent;
    private Boolean suppressFloatingFilterButton;


    //Columns: Integrated Charts
    private String chartDataType;

    //Columns: Pinned
    private String pinned;
    private String initialPinned;
    private Boolean lockPinned;

    //Columns: Pivoting
    private Boolean pivot;
    private Boolean initialPivot;
    private Integer pivotIndex;
    private Integer initialPivotIndex;
    private Boolean enablePivot;
    private String pivotComparator;

    //Columns: Rendering and Styling
    private String cellStyle;
    private String cellClass;
    private Boolean autoHeight;
    private Boolean wrapText;
    private Boolean enableCellChangeFlash;

    //Columns: Row Dragging
    private Boolean rowDrag;

    //Columns: Row Grouping
    private Boolean rowGroup;
    private Boolean initialRowGroup;
    private Integer rowGroupIndex;
    private Integer initialRowGroupIndex;
    private Boolean enableRowGroup;
    private Boolean showRowGroup;

    //Columns: Sort
    private Boolean sortable;
    private String sort;
    private Integer sortIndex;
    private Integer initialSortIndex;
    private List<String> sortingOrder;
    private String comparator;
    private Boolean unSortIcon;

    //Columns:width
    private Integer width;
    private Integer initialWidth;
    private Integer minWidth;
    private Integer maxWidth;
    private Integer flex;
    private Integer initialFlex;
    private Boolean resizable;
    private Boolean suppressSizeToFit;
    private Boolean suppressAutoSize;

    //

    public ColDef(String fieldName, HeaderInfo classHeaderInfo, HeaderInfo fieldHeaderInfo) {
        // take the field name
        this.colId = fieldName;
        this.field = fieldName;
        this.initFields(classHeaderInfo);
        this.initFields(fieldHeaderInfo);
    }

    private void initFields(HeaderInfo headerInfo) {
        if (headerInfo == null) return;
        // Columns
        this.type = noEmpty(headerInfo.type());
        this.cellDataType = headerInfo.cellDataType()
                                      .toString();
        //Columns:Display
        this.hide = setNoNull(this.hide, noFalse(headerInfo.hide()));
        this.initialHide = setNoNull(this.initialHide, noFalse(headerInfo.initialHide()));
        this.lockVisible = setNoNull(this.lockVisible, noFalse(headerInfo.lockVisible()));
        this.lockPosition = setNoNull(this.lockPosition, headerInfo.lockPosition()
                                                                   .toString());
        this.suppressMovable = setNoNull(this.suppressMovable, noFalse(headerInfo.suppressMovable()));
        this.useValueFormatterForExport = setNoNull(this.useValueFormatterForExport, noTrue(headerInfo.useValueFormatterForExport()));

        //Columns:Editing
        this.editable = setNoNull(this.editable, noFalse(headerInfo.editable()));
        this.cellEditorPopup = setNoNull(this.cellEditorPopup, noFalse(headerInfo.cellEditorPopup()));
        this.cellEditorPopupPosition = setNoNull(this.cellEditorPopupPosition, headerInfo.cellEditorPopupPosition()
                                                                                         .toString());
        this.singleClickEdit = setNoNull(this.singleClickEdit, noFalse(headerInfo.singleClickEdit()));
        this.useValueParserForImport = setNoNull(this.useValueParserForImport, noTrue(headerInfo.useValueParserForImport()));

        //Columns:Filter
        this.filter = setNoNull(this.filter, headerInfo.filter()
                                                       .toString());
        this.floatingFilter = setNoNull(this.floatingFilter, noFalse(headerInfo.floatingFilter()));

        //Columns: Header
        this.headerName = setNoNull(this.headerName, noBlank(headerInfo.headerName()));
        this.headerTooltip = setNoNull(this.headerTooltip, noBlank(headerInfo.headerTooltip()));
        this.wrapHeaderText = setNoNull(this.wrapHeaderText, noFalse(headerInfo.wrapHeaderText()));
        this.autoHeaderHeight = setNoNull(this.autoHeaderHeight, noFalse(headerInfo.autoHeaderHeight()));
        this.suppressHeaderMenuButton = setNoNull(this.suppressHeaderMenuButton, noFalse(headerInfo.suppressHeaderMenuButton()));
        this.suppressHeaderFilterButton = setNoNull(this.suppressHeaderFilterButton, noFalse(headerInfo.suppressHeaderFilterButton()));
        this.suppressHeaderContextMenu = setNoNull(this.suppressHeaderContextMenu, noFalse(headerInfo.suppressHeaderContextMenu()));
        this.suppressHeaderKeyboardEvent = setNoNull(this.suppressHeaderKeyboardEvent, noFalse(headerInfo.suppressHeaderKeyboardEvent()));
        this.suppressFloatingFilterButton = setNoNull(this.suppressFloatingFilterButton, noFalse(headerInfo.suppressFloatingFilterButton()));

        //Columns: Integrated Charts
        this.chartDataType = setNoNull(this.chartDataType, headerInfo.chartDataType()
                                                                     .toString());

        //Columns: Pinned
        this.pinned = setNoNull(this.pinned, headerInfo.pinned()
                                                       .toString());
        this.initialPinned = setNoNull(this.initialPinned, headerInfo.initialPinned()
                                                                     .toString());
        this.lockPinned = setNoNull(this.lockPinned, noFalse(headerInfo.lockPinned()));

        //Columns: Pivoting
        this.pivot = setNoNull(this.pivot, noFalse(headerInfo.pivot()));
        this.initialPivot = setNoNull(this.initialPivot, noFalse(headerInfo.initialPivot()));
        this.pivotIndex = setNoNull(this.pivotIndex, noMinus(headerInfo.pivotIndex()));
        this.initialPivotIndex = setNoNull(this.initialPivotIndex, noMinus(headerInfo.initialPivotIndex()));
        this.enablePivot = setNoNull(this.enablePivot, noFalse(headerInfo.enablePivot()));
        this.pivotComparator = setNoNull(this.pivotComparator, noBlank(headerInfo.pivotComparator()));

        //Columns: Rendering and Styling
        this.cellStyle = setNoNull(this.cellStyle, noBlank(headerInfo.cellStyle()));
        this.cellClass = setNoNull(this.cellClass, noBlank(headerInfo.cellClass()));
        this.autoHeight = setNoNull(this.autoHeight, noFalse(headerInfo.autoHeight()));
        this.wrapText = setNoNull(this.wrapText, noFalse(headerInfo.wrapText()));
        this.enableCellChangeFlash = setNoNull(this.enableCellChangeFlash, noFalse(headerInfo.enableCellChangeFlash()));

        //Columns: Row Dragging
        this.rowDrag = setNoNull(this.rowDrag, noFalse(headerInfo.rowDrag()));

        //Columns: Row Grouping
        this.rowGroup = setNoNull(this.rowGroup, noFalse(headerInfo.rowGroup()));
        this.initialRowGroup = setNoNull(this.initialRowGroup, noFalse(headerInfo.initialRowGroup()));
        this.rowGroupIndex = setNoNull(this.rowGroupIndex, noMinus(headerInfo.rowGroupIndex()));
        this.initialRowGroupIndex = setNoNull(this.initialRowGroupIndex, noMinus(headerInfo.initialRowGroupIndex()));
        this.enableRowGroup = setNoNull(this.enableRowGroup, noFalse(headerInfo.enableRowGroup()));
        this.showRowGroup = setNoNull(this.showRowGroup, noFalse(headerInfo.showRowGroup()));

        //Columns: Sort
        this.sortable = setNoNull(this.sortable, noTrue(headerInfo.sortable()));
        this.sort = setNoNull(this.sort, headerInfo.sort()
                                                   .toString());
        this.sortIndex = setNoNull(this.sortIndex, noMinus(headerInfo.sortIndex()));
        this.initialSortIndex = setNoNull(this.initialSortIndex, noMinus(headerInfo.initialSortIndex()));
        this.sortingOrder = setNoNull(this.sortingOrder, this.sortable == null ? null : noEmpty(headerInfo.sortingOrder()));
        this.comparator = setNoNull(this.comparator, noBlank(headerInfo.comparator()));
        this.unSortIcon = setNoNull(this.unSortIcon, noFalse(headerInfo.unSortIcon()));

        //Columns:width
        this.width = setNoNull(this.width, noMinus(headerInfo.width()));
        this.initialWidth = setNoNull(this.initialWidth, noMinus(headerInfo.initialWidth()));
        this.minWidth = setNoNull(this.minWidth, noMinus(headerInfo.minWidth()));
        this.maxWidth = setNoNull(this.maxWidth, noMinus(headerInfo.maxWidth()));
        this.flex = setNoNull(this.flex, noMinus(headerInfo.flex()));
        this.initialFlex = setNoNull(this.initialFlex, noMinus(headerInfo.initialFlex()));
        this.resizable = setNoNull(this.resizable, noTrue(headerInfo.resizable()));
        this.suppressSizeToFit = setNoNull(this.suppressSizeToFit, noFalse(headerInfo.suppresSizeToFit()));
        this.suppressAutoSize = setNoNull(this.suppressAutoSize, noFalse(headerInfo.suppressAutoSize()));
    }

    private <V> V setNoNull(V previous, V actual) {
        if (actual != null) return actual;
        return previous;
    }


    private Boolean noFalse(boolean input) {
        if (input) return Boolean.TRUE;
        return null;
    }

    private Boolean noTrue(boolean input) {
        if (!input) return Boolean.FALSE;
        return null;
    }

    private Integer noMinus(int input) {
        if (input < 0) return null;
        return input;
    }

    private <V> List<String> noEmpty(V[] input) {
        if (input == null || input.length == 0) return null;
        return Stream.of(input)
                     .map(Objects::toString)
                     .collect(Collectors.toCollection(LinkedList::new));
    }

    private String noBlank(String input) {
        if (input == null || input.isBlank()) return null;
        return input;
    }
}
