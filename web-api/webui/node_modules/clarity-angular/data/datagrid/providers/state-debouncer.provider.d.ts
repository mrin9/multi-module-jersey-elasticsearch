import { Observable } from "rxjs/Observable";
export declare class StateDebouncer {
    /**
     * The Observable that lets other classes subscribe to global state changes
     */
    private _change;
    readonly change: Observable<void>;
    private nbChanges;
    changeStart(): void;
    changeDone(): void;
}
