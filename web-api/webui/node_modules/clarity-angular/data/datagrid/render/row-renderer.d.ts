import { AfterContentInit, OnDestroy, QueryList } from "@angular/core";
import { DatagridCellRenderer } from "./cell-renderer";
import { DatagridRenderOrganizer } from "./render-organizer";
export declare class DatagridRowRenderer implements AfterContentInit, OnDestroy {
    private organizer;
    constructor(organizer: DatagridRenderOrganizer);
    private subscription;
    ngOnDestroy(): void;
    cells: QueryList<DatagridCellRenderer>;
    private setWidths();
    ngAfterContentInit(): void;
    ngAfterViewInit(): void;
}
