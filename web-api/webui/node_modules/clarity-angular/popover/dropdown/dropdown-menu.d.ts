import { ElementRef, Injector } from "@angular/core";
import { AbstractPopover } from "../common/abstract-popover";
export declare class DropdownMenu extends AbstractPopover {
    constructor(injector: Injector, parentHost: ElementRef, nested: DropdownMenu);
    position: string;
}
