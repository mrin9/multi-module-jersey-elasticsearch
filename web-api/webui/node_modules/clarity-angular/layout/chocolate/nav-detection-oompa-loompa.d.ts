import { ChangeDetectorRef } from "@angular/core";
import { OompaLoompa } from "../../utils/chocolate/oompa-loompa";
import { ClrResponsiveNavigationService } from "../nav/clrResponsiveNavigationService";
import { MainContainerWillyWonka } from "./main-container-willy-wonka";
export declare class NavDetectionOompaLoompa extends OompaLoompa {
    private responsiveNavService;
    constructor(cdr: ChangeDetectorRef, willyWonka: MainContainerWillyWonka, responsiveNavService: ClrResponsiveNavigationService);
    readonly flavor: number;
}
