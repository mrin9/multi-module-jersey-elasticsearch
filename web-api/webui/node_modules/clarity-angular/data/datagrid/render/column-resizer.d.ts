import { AfterViewInit, ElementRef, EventEmitter, OnDestroy, Renderer2 } from "@angular/core";
import { DragDispatcher } from "../providers/drag-dispatcher";
import { DomAdapter } from "./dom-adapter";
import { DatagridRenderOrganizer } from "./render-organizer";
export declare class DatagridColumnResizer implements AfterViewInit, OnDestroy {
    private el;
    private renderer;
    private organizer;
    private domAdapter;
    private dragDispatcher;
    constructor(el: ElementRef, renderer: Renderer2, organizer: DatagridRenderOrganizer, domAdapter: DomAdapter, dragDispatcher: DragDispatcher);
    columnEl: any;
    columnRectWidth: number;
    columnResizeBy: number;
    handleTrackerEl: ElementRef;
    pageStartPositionX: number;
    dragDistancePositionX: number;
    dragWithinMinWidth: boolean;
    columnMinWidth: number;
    resizeEmitter: EventEmitter<number>;
    private subscriptions;
    ngOnDestroy(): void;
    ngAfterViewInit(): void;
    dragStartHandler(): void;
    dragMoveHandler(moveEvent: any): void;
    dragEndHandler(): void;
    getPositionWithinMax(draggedDistance: number): number;
}
