import { ElementRef, Renderer2 } from "@angular/core";
import { Expand } from "../../../utils/expand/providers/expand";
import { DomAdapter } from "../render/dom-adapter";
import { DatagridRenderOrganizer } from "../render/render-organizer";
export declare class DatagridRowExpandAnimation {
    private el;
    private domAdapter;
    private renderer;
    private expand;
    private renderOrganizer;
    constructor(el: ElementRef, domAdapter: DomAdapter, renderer: Renderer2, expand: Expand, renderOrganizer: DatagridRenderOrganizer);
    private running;
    private oldHeight;
    private animate();
    private run();
}
