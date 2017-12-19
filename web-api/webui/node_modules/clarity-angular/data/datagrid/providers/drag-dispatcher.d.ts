import { ElementRef, NgZone, Renderer2 } from "@angular/core";
import { Observable } from "rxjs/Observable";
export declare class DragDispatcher {
    private _ngZone;
    private _renderer;
    private _listeners;
    handleRef: ElementRef;
    handleTrackerRef: ElementRef;
    private _onDragStart;
    private _onDragMove;
    private _onDragEnd;
    readonly onDragStart: Observable<any>;
    readonly onDragMove: Observable<any>;
    readonly onDragEnd: Observable<any>;
    constructor(_ngZone: NgZone, _renderer: Renderer2);
    addDragListener(): void;
    customDragEvent(element: HTMLElement, startOnEvent: string, moveOnEvent: string, endOnEvent: string): Function;
    notifyDragStart(event: any): void;
    notifyDragMove(event: any): void;
    notifyDragEnd(event: any): void;
    destroy(): void;
}
