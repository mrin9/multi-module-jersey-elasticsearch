import { ChangeDetectorRef, OnInit } from "@angular/core";
import { HideableColumnService } from "./providers/hideable-column.service";
import { Selection, SelectionType } from "./providers/selection";
export declare class DatagridFooter implements OnInit {
    selection: Selection;
    hideableColumnService: HideableColumnService;
    cdr: ChangeDetectorRef;
    constructor(selection: Selection, hideableColumnService: HideableColumnService, cdr: ChangeDetectorRef);
    activeToggler: boolean;
    private subscriptions;
    SELECTION_TYPE: typeof SelectionType;
    ngOnInit(): void;
    ngOnDestroy(): void;
}
