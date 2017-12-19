import "rxjs/add/operator/map";
import { Observable } from "rxjs/Observable";
import { State } from "../interfaces/state";
import { FiltersProvider } from "./filters";
import { Page } from "./page";
import { Sort } from "./sort";
import { StateDebouncer } from "./state-debouncer.provider";
/**
 * This provider aggregates state changes from the various providers of the Datagrid
 */
export declare class StateProvider {
    private filters;
    private sort;
    private page;
    private debouncer;
    constructor(filters: FiltersProvider, sort: Sort, page: Page, debouncer: StateDebouncer);
    /**
     * The Observable that lets other classes subscribe to global state changes
     */
    change: Observable<State>;
    readonly state: State;
}
