import { Observable } from "rxjs/Observable";
import { Subject } from "rxjs/Subject";
export declare class DatagridRenderOrganizer {
    private alreadySized;
    widths: {
        px: number;
        strict: boolean;
    }[];
    protected _noLayout: Subject<boolean>;
    readonly noLayout: Observable<boolean>;
    protected _clearWidths: Subject<any>;
    readonly clearWidths: Observable<any>;
    protected _detectStrictWidths: Subject<any>;
    readonly detectStrictWidths: Observable<any>;
    protected _tableMode: Subject<boolean>;
    readonly tableMode: Observable<boolean>;
    protected _computeWidths: Subject<any>;
    readonly computeWidths: Observable<any>;
    protected _alignColumns: Subject<any>;
    readonly alignColumns: Observable<any>;
    scrollbar: Subject<any>;
    scrollbarWidth: Subject<number>;
    protected _done: Subject<any>;
    readonly done: Observable<any>;
    resize(): void;
}
