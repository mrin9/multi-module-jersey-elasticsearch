import { AfterContentInit, OnDestroy, QueryList } from "@angular/core";
import { Expand } from "../../utils/expand/providers/expand";
import { DatagridCell } from "./datagrid-cell";
import { DatagridHideableColumn } from "./datagrid-hideable-column";
import { HideableColumnService } from "./providers/hideable-column.service";
import { RowActionService } from "./providers/row-action-service";
import { Selection, SelectionType } from "./providers/selection";
/**
 * Generic bland container serving various purposes for Datagrid.
 * For instance, it can help span a text over multiple rows in detail view.
 */
export declare class DatagridRowDetail implements AfterContentInit, OnDestroy {
    selection: Selection;
    rowActionService: RowActionService;
    expand: Expand;
    hideableColumnService: HideableColumnService;
    SELECTION_TYPE: typeof SelectionType;
    constructor(selection: Selection, rowActionService: RowActionService, expand: Expand, hideableColumnService: HideableColumnService);
    cells: QueryList<DatagridCell>;
    replace: boolean;
    private subscription;
    ngAfterContentInit(): void;
    updateCellsForColumns(columnList: DatagridHideableColumn[]): void;
    ngOnDestroy(): void;
}
