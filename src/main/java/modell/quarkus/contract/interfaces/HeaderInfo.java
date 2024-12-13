package modell.quarkus.contract.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * The interface Header info.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface HeaderInfo {


    // Columns

    /**
     * A comma separated string or array of strings containing ColumnType keys which
     * can be used as a template for a column.
     * This helps to reduce duplication of properties when you have a lot
     * of common column properties.
     *
     * @return the types Array
     */
    String[] type() default {};

    /**
     * The data type of the cell values for this column.
     * Can either infer the data type from the row data (true - the default behaviour), define a specific data type (string), or have no data type (false).
     * If setting a specific data type (string value), this can either be one of the pre-defined data types
     * 'text', 'number', 'boolean', 'date', 'dateString' or 'object',
     * or a custom data type that has been defined in the dataTypeDefinitions grid option.
     * Data type inference only works for the Client-Side Row Model, and requires non-null data.
     * It will also not work if the valueGetter, valueParser or refData properties are defined, or if this column is a sparkline.
     *
     * @return cell data type
     */
    CellDataType cellDataType() default CellDataType.NONE;

    //Columns: Display

    /**
     * Set to true for this column to be hidden.
     *
     * @return boolean
     */
    boolean hide() default false;


    /**
     * Same as hide, except only applied when creating a new column.
     * Not applied when updating column definitions.
     *
     * @return boolean
     */
    boolean initialHide() default false;


    /**
     * Set to true to block making column visible / hidden via the UI (API will still work).
     *
     * @return boolean
     */
    boolean lockVisible() default false;


    /**
     * Lock a column to position to 'left' or'right' to always
     * have this column displayed in that position. true is treated as 'left'
     *
     * @return h position
     */
    HPosition lockPosition() default HPosition.NONE;

    /**
     * Set to true if you do not want this column to be movable via dragging.
     *
     * @return boolean
     */
    boolean suppressMovable() default false;


    /**
     * By default, values are formatted using the column's valueFormatter
     * when exporting data from the grid. This applies to CSV and Excel export,
     * as well as clipboard operations and the fill handle.
     * Set to false to prevent values from being formatted for these operations.
     * Regardless of this option, if custom handling is provided for the export operation,
     * the value formatter will not be used.
     *
     * @return boolean
     */
    boolean useValueFormatterForExport() default true;


    //Columns: Editing

    /**
     * Set to true if this column is editable, otherwise false.
     * Can also be a function to have different rows editable
     *
     * @return boolean
     */
    boolean editable() default false;


    /**
     * Set to true, to have the cell editor appear in a popup.
     *
     * @return boolean
     */
    boolean cellEditorPopup() default false;


    /**
     * Set the position for the popup cell editor. Possible values are
     * over Popup will be positioned over the cell
     * under Popup will be positioned below the cell leaving the cell value visible.
     *
     * @return s position
     */
    SPosition cellEditorPopupPosition() default SPosition.NONE;

    /**
     * Set to true to have cells under this column enter edit mode after single click.
     *
     * @return boolean
     */
    boolean singleClickEdit() default false;

    /**
     * By default, values are parsed using the column's valueParser when importing data to the grid. This applies to clipboard operations and the fill handle.
     * Set to false to prevent values from being parsed for these operations. Regardless of this option, if custom handling is provided for the import operation, the value parser will not be used.
     *
     * @return boolean
     */
    boolean useValueParserForImport() default true;

    //Columns:Filter

    /**
     * Filter filter.
     *
     * @return the filter
     */
    Filter filter() default Filter.NONE;

    //todo:
    //filterParams

    /**
     * Whether to display a floating filter for this column.
     *
     * @return the boolean
     */
    boolean floatingFilter() default false;

    //Columns: Header

    /**
     * The name to render in the column header.
     * If not specified and field is specified,
     * the field name will be used as the header name.
     *
     * @return the string
     */
    String headerName() default "";

    /**
     * Tooltip for the column header
     *
     * @return the string
     */
    String headerTooltip() default "";

    /**
     * If enabled then column header names that are too long
     * for the column width will wrap onto the next line. Default false
     *
     * @return the boolean
     */
    boolean wrapHeaderText() default false;

    /**
     * If enabled then the column header row will automatically
     * adjust height to accommodate the size of the header cell.
     * This can be useful when using your own headerComponent or
     * long header names in conjunction with wrapHeaderText.
     *
     * @return the boolean
     */
    boolean autoHeaderHeight() default false;

    /**
     * Set to true if no menu button should be shown for this column header.
     *
     * @return the boolean
     */
    boolean suppressHeaderMenuButton() default false;

    /**
     * Set to true to not display the filter button in the column header.
     * Doesn't apply when columnMenu = 'legacy'.
     *
     * @return the boolean
     */
    boolean suppressHeaderFilterButton() default false;

    /**
     * Set to true to not display the column menu when the column header is right-clicked.
     * Doesn't apply when columnMenu = 'legacy'.
     *
     * @return the boolean
     */
    boolean suppressHeaderContextMenu() default false;

    /**
     * Suppress the grid taking action for the relevant keyboard event when a header is focused.
     *
     * @return the boolean
     */
    boolean suppressHeaderKeyboardEvent() default false;

    /**
     * If true, the button in the floating filter that opens
     * the parent filter in a popup will not be displayed. Only applies if floatingFilter = true.
     *
     * @return the boolean
     */
    boolean suppressFloatingFilterButton() default false;


    //Columns: Integrated Charts

    /**
     * Sortable boolean.
     *
     * @return the boolean
     */
    ChartDataType chartDataType() default ChartDataType.NONE;

    //Columns: Pinned

    /**
     * Pin a column to one side: right or left.
     * A value of true is converted to 'left'.
     *
     * @return the boolean
     */
    HPosition pinned() default HPosition.NONE;


    /**
     * Same as pinned, except only applied when creating a new column.
     * Not applied when updating column definitions.
     *
     * @return the boolean
     */
    HPosition initialPinned() default HPosition.NONE;


    /**
     * Set to true to block the user pinning the column,
     * the column can only be pinned via definitions or API.
     *
     * @return the boolean
     */
    boolean lockPinned() default false;

    //Columns: Pivoting

    /**
     * Set to true to pivot by this column.
     *
     * @return the boolean
     */
    boolean pivot() default false;

    /**
     * Same as pivot, except only applied when creating a new column.
     * Not applied when updating column definitions.
     *
     * @return the boolean
     */
    boolean initialPivot() default false;

    /**
     * Set this in columns you want to pivot by.
     * If only pivoting by one column, set this to any number (e.g. 0).
     * If pivoting by multiple columns, set this to where you want this column
     * to be in the order of pivots (e.g. 0 for first, 1 for second, and so on).
     *
     * @return the int
     */
    int pivotIndex() default -1;

    /**
     * Same as pivotIndex, except only applied when creating a new column.
     * Not applied when updating column definitions.
     *
     * @return the int
     */
    int initialPivotIndex() default -1;

    /**
     * Set to true if you want to be able to pivot by this column via the GUI.
     * This will not block the API or properties being used to achieve pivot.
     *
     * @return the boolean
     */
    boolean enablePivot() default false;


    /**
     * Only for CSRM, see SSRM Pivoting.
     * Comparator to use when ordering the pivot columns, when this column is used to pivot on.
     * The values will always be strings, as the pivot service uses strings as keys for the pivot groups.
     * pivotComparator = (
     * valueA: string,
     * valueB: string
     * ) => number;
     *
     * @return the boolean
     */
    String pivotComparator() default "";

    //Columns: Rendering and Styling

    /**
     * An object of css values / or function returning an object of css values for a particular cell.
     *
     * @return the string
     */
    String cellStyle() default "";

    /**
     * Class to use for the cell. Can be string, array of strings,
     * or function that returns a string or array of strings.
     *
     * @return the string
     */
    String cellClass() default "";

    /**
     * Set to true to have the grid calculate the height of a row based on contents of this column.
     *
     * @return the boolean
     */
    boolean autoHeight() default false;

    /**
     * Set to true to have the text wrap inside the cell - typically used with autoHeight.
     *
     * @return the boolean
     */
    boolean wrapText() default false;

    /**
     * Set to true to flash a cell when it's refreshed.
     *
     * @return the boolean
     */
    boolean enableCellChangeFlash() default false;

    //Columns: Row Dragging

    /**
     * boolean or Function.
     * Set to true (or return true from function) to allow row dragging.
     *
     * @return the boolean
     */
    boolean rowDrag() default false;

    //Columns: Row Grouping

    /**
     * Set to true to row group by this column.
     *
     * @return the boolean
     */
    boolean rowGroup() default false;

    /**
     * Same as rowGroup, except only applied when creating a new column.
     * Not applied when updating column definitions.
     *
     * @return the boolean
     */
    boolean initialRowGroup() default false;

    /**
     * Set this in columns you want to group by.
     * If only grouping by one column, set this to any number (e.g. 0).
     * If grouping by multiple columns,set this to where you want this column
     * to be in the group (e.g. 0 for first, 1 for second, and so on).
     *
     * @return the boolean
     */
    int rowGroupIndex() default -1;

    /**
     * Same as rowGroupIndex, except only applied when creating a new column.
     * Not applied when updating column definitions.
     *
     * @return the boolean
     */
    int initialRowGroupIndex() default -1;

    /**
     * Set to true if you want to be able to row group by this column via the GUI.
     * This will not block the API or properties being used to achieve row grouping.
     *
     * @return the boolean
     */
    boolean enableRowGroup() default false;

    /**
     * Set to true to have the grid place the values for the group into the cell,
     * or put the name of a grouped column to just show that group.
     *
     * @return the boolean
     */
    boolean showRowGroup() default false;

    //Columns: Sort

    /**
     * Set to false to disable sorting which is enabled by default.
     *
     * @return the boolean
     */
    boolean sortable() default true;

    /**
     * If sorting by default, set it here. Set to asc or desc.
     *
     * @return the string
     */
    SortDirection sort() default SortDirection.NONE;

    /**
     * If sorting more than one column by default,
     * specifies order in which the sorting should be applied.
     *
     * @return the width
     */
    int sortIndex() default -1;

    /**
     * Same as sortIndex, except only applied when creating a new column.
     * Not applied when updating column definitions.
     *
     * @return the width
     */
    int initialSortIndex() default -1;

    /**
     * Array defining the order in which sorting occurs (if sorting is enabled).
     * An array with any of the following in any order ['asc','desc',null].
     * sortingOrder: SortDirection[];
     * <p>
     * type SortDirection =
     * 'asc'
     * | 'desc'
     * | null
     *
     * @return the string
     */
    SortDirection[] sortingOrder() default {SortDirection.ASC, SortDirection.DESC};

    /**
     * Override the default sorting order by providing a custom sort comparator.
     * valueA, valueB are the values to compare.
     * nodeA, nodeB are the corresponding RowNodes.
     * Useful if additional details are required by the sort.
     * isDescending - true if sort direction is desc.
     * Not to be used for inverting the return value as the grid already applies asc or desc ordering.
     * Return:
     * 0 valueA is the same as valueB
     * > 0 Sort valueA after valueB
     * < 0 Sort valueA before valueB
     *
     * @return the width
     */
    String comparator() default "";

    /**
     * Set to true if you want the unsorted icon to be shown when no sort is applied to this column.
     *
     * @return the width
     */
    boolean unSortIcon() default false;

    //Columns: Width

    /**
     * Initial width in pixels for the cell.
     *
     * @return the width
     */
    int width() default -1;

    /**
     * Same as width, except only applied when creating a new column. Not applied when updating column definitions.
     *
     * @return the initial width
     */
    int initialWidth() default -1;

    /**
     * Minimum width in pixels for the cell.
     *
     * @return the minimum width
     */
    int minWidth() default -1;

    /**
     * Maximum width in pixels for the cell.
     *
     * @return the max width
     */
    int maxWidth() default -1;

    /**
     * Equivalent to flex-grow in CSS. When flex is set on one or more columns, any width value is ignored and instead the remaining free space in the grid is divided among flex columns in proportion to their flex value, so a column with flex: 2 will be twice the size as one with flex: 1.
     *
     * @return the int
     */
    int flex() default -1;

    /**
     * Same as flex, except only applied when creating a new column. Not applied when updating column definitions.
     *
     * @return the int
     */
    int initialFlex() default -1;

    /**
     * Set to false to disable resizing which is enabled by default.
     *
     * @return boolean boolean
     */
    boolean resizable() default true;

    /**
     * Set to true if you want this column's width to be fixed during 'size to fit' operations.
     *
     * @return boolean
     */
    boolean suppresSizeToFit() default false;

    /**
     * Set to true if you do not want this column to be auto-resizable by double clicking it's edge.
     *
     * @return boolean
     */
    boolean suppressAutoSize() default false;

}
