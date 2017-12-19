import { ElementRef, OnDestroy } from "@angular/core";
import { DomAdapter } from "./dom-adapter";
import { DatagridRenderOrganizer } from "./render-organizer";
export declare class DatagridBodyRenderer implements OnDestroy {
    private el;
    private organizer;
    private domAdapter;
    constructor(el: ElementRef, organizer: DatagridRenderOrganizer, domAdapter: DomAdapter);
    private subscription;
    ngOnDestroy(): void;
    private computeScrollbarWidth();
}
