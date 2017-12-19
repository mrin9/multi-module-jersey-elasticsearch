import { Observable } from "rxjs/Observable";
export declare class VerticalNavService {
    private _animateOnCollapsed;
    readonly animateOnCollapsed: Observable<boolean>;
    private _collapsedChanged;
    readonly collapsedChanged: Observable<boolean>;
    private _collapsed;
    collapsed: boolean;
    private _collapsible;
    collapsible: boolean;
    private updateCollapseBehavior(value);
}
