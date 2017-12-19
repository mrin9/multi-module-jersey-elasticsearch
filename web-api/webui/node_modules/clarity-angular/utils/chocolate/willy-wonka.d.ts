import { AfterViewChecked } from "@angular/core";
import { Observable } from "rxjs/Observable";
export declare class WillyWonka implements AfterViewChecked {
    private _chocolate;
    readonly chocolate: Observable<void>;
    ngAfterViewChecked(): void;
}
