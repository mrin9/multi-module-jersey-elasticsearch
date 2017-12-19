import { ElementRef, OnDestroy, Renderer2 } from "@angular/core";
import { DatagridRenderOrganizer } from "./render-organizer";
export declare class DatagridCellRenderer implements OnDestroy {
    private el;
    private renderer;
    constructor(el: ElementRef, renderer: Renderer2, organizer: DatagridRenderOrganizer);
    private subscription;
    ngOnDestroy(): void;
    private clearWidth();
    setWidth(strict: boolean, value: number): void;
}
