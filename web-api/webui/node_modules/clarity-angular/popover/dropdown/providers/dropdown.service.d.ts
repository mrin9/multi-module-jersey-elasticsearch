import { SkipSelf } from "@angular/core";
import { Observable } from "rxjs/Observable";
export declare class RootDropdownService {
    private _changes;
    readonly changes: Observable<boolean>;
    closeMenus(): void;
}
export declare function clrRootDropdownFactory(existing: RootDropdownService): RootDropdownService;
export declare const ROOT_DROPDOWN_PROVIDER: {
    provide: typeof RootDropdownService;
    useFactory: (existing: RootDropdownService) => RootDropdownService;
    deps: SkipSelf[][];
};
