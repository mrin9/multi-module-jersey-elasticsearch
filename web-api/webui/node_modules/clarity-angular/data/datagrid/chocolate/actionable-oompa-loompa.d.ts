import { ChangeDetectorRef } from "@angular/core";
import { OompaLoompa } from "../../../utils/chocolate/oompa-loompa";
import { RowActionService } from "../providers/row-action-service";
import { DatagridWillyWonka } from "./datagrid-willy-wonka";
export declare class ActionableOompaLoompa extends OompaLoompa {
    private rowActions;
    constructor(cdr: ChangeDetectorRef, willyWonka: DatagridWillyWonka, rowActions: RowActionService);
    readonly flavor: boolean;
}
