import { Observable } from "rxjs/Observable";
import { FiltersProvider } from "./filters";
import { Items } from "./items";
export declare enum SelectionType {
    None = 0,
    Single = 1,
    Multi = 2,
}
export declare class Selection {
    private _items;
    private _filters;
    id: string;
    constructor(_items: Items, _filters: FiltersProvider);
    clearSelection(): void;
    private _selectionType;
    selectionType: SelectionType;
    rowSelectionMode: boolean;
    private readonly _selectable;
    /**
     * Ignore items changes in the same change detection cycle.
     */
    private debounce;
    /**
     * Subscriptions to the other providers changes.
     */
    private _itemsSub;
    private _filtersSub;
    /**
     * Cleans up our subscriptions to other providers
     */
    destroy(): void;
    /**
     * The current selection in single selection type
     */
    private _currentSingle;
    currentSingle: any;
    /**
     * The current selection
     */
    private _current;
    current: any[];
    /**
     * The Observable that lets other classes subscribe to selection changes
     */
    private _change;
    private emitChange();
    readonly change: Observable<any[] | any>;
    /**
     * Checks if an item is currently selected
     */
    isSelected(item: any): boolean;
    /**
     * Selects or deselects an item
     */
    setSelected(item: any, selected: boolean): void;
    /**
     * Checks if all currently displayed items are selected
     */
    isAllSelected(): boolean;
    /**
     * Selects or deselects all currently displayed items
     */
    toggleAll(): void;
}
