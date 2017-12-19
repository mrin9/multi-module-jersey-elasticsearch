import { ElementRef, OnDestroy, Renderer2, TemplateRef, ViewContainerRef } from "@angular/core";
import { DatagridRenderOrganizer } from "./render-organizer";
export declare class DatagridTableRenderer implements OnDestroy {
    private el;
    private renderer;
    constructor(el: ElementRef, renderer: Renderer2, organizer: DatagridRenderOrganizer);
    private subscriptions;
    ngOnDestroy(): void;
    projected: TemplateRef<any>;
    outsideContainer: ViewContainerRef;
    insideContainer: ViewContainerRef;
    ngAfterViewInit(): void;
    private tableMode(on);
    private noLayout(on);
}
