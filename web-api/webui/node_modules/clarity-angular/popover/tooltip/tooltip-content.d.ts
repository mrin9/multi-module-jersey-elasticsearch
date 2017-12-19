import { ElementRef, Injector } from "@angular/core";
import { AbstractPopover } from "../common/abstract-popover";
export declare class TooltipContent extends AbstractPopover {
    constructor(injector: Injector, parentHost: ElementRef);
    private _position;
    position: string;
    private _size;
    size: string;
}
