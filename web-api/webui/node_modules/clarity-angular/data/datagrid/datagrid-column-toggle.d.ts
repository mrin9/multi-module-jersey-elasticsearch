import { OnDestroy, OnInit } from "@angular/core";
import { Point } from "../../popover/common/popover";
import { DatagridHideableColumn } from "./datagrid-hideable-column";
import { HideableColumnService } from "./providers/hideable-column.service";
export declare class DatagridColumnToggle implements OnInit, OnDestroy {
    hideableColumnService: HideableColumnService;
    private _hideableColumnChangeSubscription;
    private _allColumnsVisible;
    /***
     * Popover init
     * @type {Point}
     */
    anchorPoint: Point;
    popoverPoint: Point;
    open: boolean;
    /****
     * DatagridHideableColumn init
     * @type {Array}
     */
    columns: DatagridHideableColumn[];
    allColumnsVisible: boolean;
    constructor(hideableColumnService: HideableColumnService);
    ngOnInit(): void;
    ngOnDestroy(): void;
    selectAll(): void;
    toggleColumn(event: boolean, column: DatagridHideableColumn): void;
    toggleUI(): void;
}
