import { ChangeDetectorRef } from "@angular/core";
import { OompaLoompa } from "../../../utils/chocolate/oompa-loompa";
import { ExpandableRowsCount } from "../providers/global-expandable-rows";
import { DatagridWillyWonka } from "./datagrid-willy-wonka";
export declare class ExpandableOompaLoompa extends OompaLoompa {
    private expandableCount;
    constructor(cdr: ChangeDetectorRef, willyWonka: DatagridWillyWonka, expandableCount: ExpandableRowsCount);
    readonly flavor: boolean;
}
