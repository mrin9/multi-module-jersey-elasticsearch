import { ElementRef, OnDestroy, Renderer2 } from "@angular/core";
import { DatagridColumnResizer } from "./column-resizer";
import { DomAdapter } from "./dom-adapter";
import { DatagridRenderOrganizer } from "./render-organizer";
export declare class DatagridHeaderRenderer implements OnDestroy {
    private el;
    private renderer;
    private organizer;
    private domAdapter;
    private columnResizer;
    constructor(el: ElementRef, renderer: Renderer2, organizer: DatagridRenderOrganizer, domAdapter: DomAdapter, columnResizer: DatagridColumnResizer);
    private subscriptions;
    /**
     * Indicates if the column has a strict width, so it doesn't shrink or expand based on the content.
     */
    strictWidth: number;
    private widthSet;
    ngOnDestroy(): void;
    private clearWidth();
    private detectStrictWidth();
    computeWidth(): number;
    setWidth(width: number): void;
}
