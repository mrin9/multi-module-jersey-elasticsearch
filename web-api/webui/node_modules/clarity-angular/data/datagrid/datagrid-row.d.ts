import { AfterContentInit, EventEmitter, QueryList } from "@angular/core";
import { Expand } from "../../utils/expand/providers/expand";
import { DatagridCell } from "./datagrid-cell";
import { DatagridHideableColumn } from "./datagrid-hideable-column";
import { ExpandableRowsCount } from "./providers/global-expandable-rows";
import { HideableColumnService } from "./providers/hideable-column.service";
import { RowActionService } from "./providers/row-action-service";
import { Selection, SelectionType } from "./providers/selection";
export declare class DatagridRow implements AfterContentInit {
    selection: Selection;
    rowActionService: RowActionService;
    globalExpandable: ExpandableRowsCount;
    expand: Expand;
    hideableColumnService: HideableColumnService;
    id: string;
    SELECTION_TYPE: typeof SelectionType;
    private readonly ENTER_KEY_CODE;
    private readonly SPACE_KEY_CODE;
    /**
     * Model of the row, to use for selection
     */
    item: any;
    role: string;
    constructor(selection: Selection, rowActionService: RowActionService, globalExpandable: ExpandableRowsCount, expand: Expand, hideableColumnService: HideableColumnService);
    private _selected;
    /**
     * Indicates if the row is selected
     */
    selected: boolean;
    selectedChanged: EventEmitter<boolean>;
    toggle(selected?: boolean): void;
    expanded: boolean;
    expandedChange: EventEmitter<boolean>;
    toggleExpand(): void;
    toggleSelection(): void;
    keypress(event: KeyboardEvent): void;
    private subscription;
    /*****
     * property dgCells
     *
     * @description
     * A Query List of the Datagrid cells in this row.
     *
     * @type QueryList<DatagridCell>
     */
    dgCells: QueryList<DatagridCell>;
    ngAfterContentInit(): void;
    /**********
     * @function updateCellsForColumns
     *
     * @description
     * 1. Maps the new columnListChange to the dgCells list by index
     * 2. Sets the hidden state on the cell
     * Take a Column list and use index to access the columns for hideable properties.
     *
     * @param columnList<DatagridColumn[]>
     */
    updateCellsForColumns(columnList: DatagridHideableColumn[]): void;
    ngOnDestroy(): void;
}
