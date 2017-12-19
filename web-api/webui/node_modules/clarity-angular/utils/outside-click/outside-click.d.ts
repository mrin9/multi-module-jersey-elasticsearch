import { ElementRef, EventEmitter } from "@angular/core";
export declare class OutsideClick {
    private el;
    constructor(el: ElementRef);
    strict: boolean;
    outsideClick: EventEmitter<any>;
    documentClick(event: MouseEvent): void;
}
