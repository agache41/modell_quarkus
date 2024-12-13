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
        this.hide = noFalse(headerInfo.hide());
        this.initialHide = noFalse(headerInfo.initialHide());
        this.lockVisible = noFalse(headerInfo.lockVisible());
        this.lockPosition = headerInfo.lockPosition()
                                      .toString();
        this.suppressMovable = noFalse(headerInfo.suppressMovable());
        this.useValueFormatterForExport = noTrue(headerInfo.useValueFormatterForExport());

        //Columns:Editing
        this.editable = noFalse(headerInfo.editable());
        this.cellEditorPopup = noFalse(headerInfo.cellEditorPopup());
        this.cellEditorPopupPosition = headerInfo.cellEditorPopupPosition()
                                                 .toString();
        this.singleClickEdit = noFalse(headerInfo.singleClickEdit());
        this.useValueParserForImport = noTrue(headerInfo.useValueParserForImport());

        //Columns:Filter
        this.filter = headerInfo.filter()
                                .toString();
        this.floatingFilter = noFalse(headerInfo.floatingFilter());

        //Columns: Header
        this.headerName = noBlank(headerInfo.headerName());
        this.headerTooltip = noBlank(headerInfo.headerTooltip());
        this.wrapHeaderText = noFalse(headerInfo.wrapHeaderText());
        this.autoHeaderHeight = noFalse(headerInfo.autoHeaderHeight());
        this.suppressHeaderMenuButton = noFalse(headerInfo.suppressHeaderMenuButton());
        this.suppressHeaderFilterButton = noFalse(headerInfo.suppressHeaderFilterButton());
        this.suppressHeaderContextMenu = noFalse(headerInfo.suppressHeaderContextMenu());
        this.suppressHeaderKeyboardEvent = noFalse(headerInfo.suppressHeaderKeyboardEvent());
        this.suppressFloatingFilterButton = noFalse(headerInfo.suppressFloatingFilterButton());

        //Columns: Integrated Charts
        this.chartDataType = headerInfo.chartDataType()
                                       .toString();

        //Columns: Pinned
        this.pinned = headerInfo.pinned()
                                .toString();
        this.initialPinned = headerInfo.initialPinned()
                                       .toString();
        this.lockPinned = noFalse(headerInfo.lockPinned());

        //Columns: Pivoting
        this.pivot = noFalse(headerInfo.pivot());
        this.initialPivot = noFalse(headerInfo.initialPivot());
        this.pivotIndex = noMinus(headerInfo.pivotIndex());
        this.initialPivotIndex = noMinus(headerInfo.initialPivotIndex());
        this.enablePivot = noFalse(headerInfo.enablePivot());
        this.pivotComparator = noBlank(headerInfo.pivotComparator());

        //Columns: Rendering and Styling
        this.cellStyle = noBlank(headerInfo.cellStyle());
        this.cellClass = noBlank(headerInfo.cellClass());
        this.autoHeight = noFalse(headerInfo.autoHeight());
        this.wrapText = noFalse(headerInfo.wrapText());
        this.enableCellChangeFlash = noFalse(headerInfo.enableCellChangeFlash());

        //Columns: Row Dragging
        this.rowDrag = noFalse(headerInfo.rowDrag());

        //Columns: Row Grouping
        this.rowGroup = noFalse(headerInfo.rowGroup());
        this.initialRowGroup = noFalse(headerInfo.initialRowGroup());
        this.rowGroupIndex = noMinus(headerInfo.rowGroupIndex());
        this.initialRowGroupIndex = noMinus(headerInfo.initialRowGroupIndex());
        this.enableRowGroup = noFalse(headerInfo.enableRowGroup());
        this.showRowGroup = noFalse(headerInfo.showRowGroup());

        //Columns: Sort
        this.sortable = noTrue(headerInfo.sortable());
        this.sort = headerInfo.sort()
                              .toString();
        this.sortIndex = noMinus(headerInfo.sortIndex());
        this.initialSortIndex = noMinus(headerInfo.initialSortIndex());
        this.sortingOrder = this.sortable == null ? null : noEmpty(headerInfo.sortingOrder());
        this.comparator = noBlank(headerInfo.comparator());
        this.unSortIcon = noFalse(headerInfo.unSortIcon());

        //Columns:width
        this.width = noMinus(headerInfo.width());
        this.initialWidth = noMinus(headerInfo.initialWidth());
        this.minWidth = noMinus(headerInfo.minWidth());
        this.maxWidth = noMinus(headerInfo.maxWidth());
        this.flex = noMinus(headerInfo.flex());
        this.initialFlex = noMinus(headerInfo.initialFlex());
        this.resizable = noTrue(headerInfo.resizable());
        this.suppressSizeToFit = noFalse(headerInfo.suppresSizeToFit());
        this.suppressAutoSize = noFalse((headerInfo.suppressAutoSize()));
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
