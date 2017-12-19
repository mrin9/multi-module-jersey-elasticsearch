import { OnDestroy } from "@angular/core";
import { ClrResponsiveNavCodes } from "./clrResponsiveNavCodes";
import { ClrResponsiveNavigationService } from "./clrResponsiveNavigationService";
export declare class Header implements OnDestroy {
    private responsiveNavService;
    private _subscription;
    isNavLevel1OnPage: boolean;
    isNavLevel2OnPage: boolean;
    constructor(responsiveNavService: ClrResponsiveNavigationService);
    readonly responsiveNavCodes: ClrResponsiveNavCodes;
    resetNavTriggers(): void;
    initializeNavTriggers(navList: number[]): void;
    closeOpenNav(): void;
    toggleNav(navLevel: number): void;
    ngOnDestroy(): void;
}
