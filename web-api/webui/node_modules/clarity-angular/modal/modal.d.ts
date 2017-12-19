import { AnimationEvent } from "@angular/animations";
import { EventEmitter, OnChanges, OnDestroy, SimpleChange } from "@angular/core";
import { FocusTrapDirective } from "../utils/focus-trap/focus-trap.directive";
import { ScrollingService } from "../utils/scrolling/scrolling-service";
export declare class Modal implements OnChanges, OnDestroy {
    private _scrollingService;
    focusTrap: FocusTrapDirective;
    _open: boolean;
    _openChanged: EventEmitter<boolean>;
    closable: boolean;
    size: string;
    staticBackdrop: boolean;
    skipAnimation: string;
    ghostPageState: string;
    bypassScrollService: boolean;
    stopClose: boolean;
    altClose: EventEmitter<boolean>;
    constructor(_scrollingService: ScrollingService);
    readonly sizeClass: string;
    ngOnChanges(changes: {
        [propName: string]: SimpleChange;
    }): void;
    ngOnDestroy(): void;
    open(): void;
    close(): void;
    fadeDone(e: AnimationEvent): void;
}
