import { Observable } from "rxjs/Observable";
import { Comparator } from "../interfaces/comparator";
import { StateDebouncer } from "./state-debouncer.provider";
export declare class Sort {
    private stateDebouncer;
    constructor(stateDebouncer: StateDebouncer);
    /**
     * Currently active comparator
     */
    private _comparator;
    comparator: Comparator<any>;
    /**
     * Ascending order if false, descending if true
     */
    private _reverse;
    reverse: boolean;
    /**
     * The Observable that lets other classes subscribe to sort changes
     */
    private _change;
    private emitChange();
    readonly change: Observable<Sort>;
    /**
     * Sets a comparator as the current one, or toggles reverse if the comparator is already used. The
     * optional forceReverse input parameter allows to override that toggling behavior by sorting in
     * reverse order if `true`.
     *
     * @param {Comparator<any>} sortBy the comparator to use for sorting
     * @param {boolean} [forceReverse] `true` to force sorting descendingly
     *
     * @memberof Sort
     */
    toggle(sortBy: Comparator<any>, forceReverse?: boolean): void;
    /**
     * Clears the current sorting order
     */
    clear(): void;
    /**
     * Compares two objects according to the current comparator
     */
    compare(a: any, b: any): number;
}
