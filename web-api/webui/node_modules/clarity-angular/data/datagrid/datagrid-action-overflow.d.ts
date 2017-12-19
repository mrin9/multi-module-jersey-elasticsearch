import { EventEmitter, OnDestroy } from "@angular/core";
import { Point } from "../../popover/common/popover";
import { RowActionService } from "./providers/row-action-service";
export declare class DatagridActionOverflow implements OnDestroy {
    private rowActionService;
    anchorPoint: Point;
    popoverPoint: Point;
    constructor(rowActionService: RowActionService);
    ngOnDestroy(): void;
    /**
     * Tracks whether the action overflow menu is open or not
     */
    private _open;
    open: boolean;
    openChanged: EventEmitter<boolean>;
    private openingEvent;
    /**
     * Shows/hides the action overflow menu
     */
    toggle(event: any): void;
    close(event: MouseEvent): void;
}
