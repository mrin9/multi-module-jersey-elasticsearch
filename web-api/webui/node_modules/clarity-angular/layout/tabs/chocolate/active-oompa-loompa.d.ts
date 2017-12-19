import { ChangeDetectorRef } from "@angular/core";
import { OompaLoompa } from "../../../utils/chocolate/oompa-loompa";
import { IfActiveService } from "../../../utils/conditional/if-active.service";
import { TabsWillyWonka } from "./tabs-willy-wonka";
export declare class ActiveOompaLoompa extends OompaLoompa {
    private ifActive;
    private id;
    constructor(cdr: ChangeDetectorRef, willyWonka: TabsWillyWonka, id: number, ifActive: IfActiveService);
    readonly flavor: boolean;
}
