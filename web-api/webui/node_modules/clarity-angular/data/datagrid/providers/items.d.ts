import { TrackByFunction } from "@angular/core";
import { Observable } from "rxjs/Observable";
import { FiltersProvider } from "./filters";
import { Page } from "./page";
import { Sort } from "./sort";
export declare class Items {
    private _filters;
    private _sort;
    private _page;
    constructor(_filters: FiltersProvider, _sort: Sort, _page: Page);
    /**
     * Indicates if the data is currently loading
     */
    loading: boolean;
    /**
     * Tracking function to identify objects. Default is reference equality.
     */
    trackBy: TrackByFunction<any>;
    /**
     * Subscriptions to the other providers changes.
     */
    private _filtersSub;
    private _sortSub;
    private _pageSub;
    /**
     * Cleans up our subscriptions to other providers
     */
    destroy(): void;
    /**
     * Whether we should use smart items for this datagrid or let the user handle
     * everything.
     */
    private _smart;
    readonly smart: boolean;
    smartenUp(): void;
    /**
     * List of all items in the datagrid
     */
    private _all;
    all: any[];
    /**
     * Manually recompute the list of displayed items
     */
    refresh(): void;
    /**
     * Internal temporary step, which we preserve to avoid re-filtering or re-sorting if not necessary
     */
    private _filtered;
    /**
     * List of items currently displayed
     */
    private _displayed;
    readonly displayed: any[];
    /**
     * The Observable that lets other classes subscribe to items changes
     */
    private _change;
    private emitChange();
    readonly change: Observable<any[]>;
    private _allChanges;
    private emitAllChanges();
    readonly allChanges: Observable<any[]>;
    /**
     * Checks if we don't have data to process yet, to abort early operations
     */
    private readonly uninitialized;
    /**
     * FiltersProvider items from the raw list
     */
    private _filterItems();
    /**
     * Sorts items in the filtered list
     */
    private _sortItems();
    /**
     * Extracts the current page from the sorted list
     */
    private _changePage();
}
