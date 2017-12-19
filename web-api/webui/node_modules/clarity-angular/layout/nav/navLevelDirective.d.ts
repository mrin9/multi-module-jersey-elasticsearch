import { ElementRef, OnInit } from "@angular/core";
import { ClrResponsiveNavCodes } from "./clrResponsiveNavCodes";
import { ClrResponsiveNavigationService } from "./clrResponsiveNavigationService";
export declare class NavLevelDirective implements OnInit {
    private responsiveNavService;
    private elementRef;
    _level: number;
    constructor(responsiveNavService: ClrResponsiveNavigationService, elementRef: ElementRef);
    ngOnInit(): void;
    addNavClass(level: number): void;
    readonly level: number;
    readonly responsiveNavCodes: ClrResponsiveNavCodes;
    open(): void;
    close(): void;
    onMouseClick(target: any): void;
    ngOnDestroy(): void;
}
