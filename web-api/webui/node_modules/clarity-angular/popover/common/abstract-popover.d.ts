import { AfterViewChecked, ElementRef, Injector, OnDestroy, Renderer2 } from "@angular/core";
import { IfOpenService } from "../../utils/conditional/if-open.service";
import { Point, PopoverOptions } from "./popover";
export declare abstract class AbstractPopover implements AfterViewChecked, OnDestroy {
    protected parentHost: ElementRef;
    constructor(injector: Injector, parentHost: ElementRef);
    protected el: ElementRef;
    protected ifOpenService: IfOpenService;
    protected renderer: Renderer2;
    private popoverInstance;
    private subscription;
    private updateAnchor;
    protected anchorElem: any;
    protected anchorPoint: Point;
    protected popoverPoint: Point;
    protected popoverOptions: PopoverOptions;
    protected anchor(): void;
    protected release(): void;
    ngAfterViewChecked(): void;
    ngOnDestroy(): void;
    readonly isOffScreen: boolean;
    closeOnOutsideClick: boolean;
    private hostListener;
    private documentListener;
    private ignore;
    private attachOutsideClickListener();
    private detachOutsideClickListener();
}
