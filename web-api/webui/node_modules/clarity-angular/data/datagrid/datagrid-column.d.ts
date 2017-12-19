import { ElementRef, EventEmitter } from "@angular/core";
import { DatagridStringFilterImpl } from "./built-in/filters/datagrid-string-filter-impl";
import { DatagridHideableColumn } from "./datagrid-hideable-column";
import { Comparator } from "./interfaces/comparator";
import { SortOrder } from "./interfaces/sort-order";
import { DragDispatcher } from "./providers/drag-dispatcher";
import { FiltersProvider } from "./providers/filters";
import { Sort } from "./providers/sort";
import { DatagridFilterRegistrar } from "./utils/datagrid-filter-registrar";
export declare class DatagridColumn extends DatagridFilterRegistrar<DatagridStringFilterImpl> {
    private _sort;
    private _dragDispatcher;
    constructor(_sort: Sort, filters: FiltersProvider, _dragDispatcher: DragDispatcher);
    /**
     * @property columnId
     *
     * @description
     * A DatagridColumn class variable that holds the number of DatagridColumn instances for a Datagrid.
     * It is used to generate a unique id for the DatagridColumn instance.
     *
     * @type {string}
     */
    columnId: string;
    /**
     * @property hidden
     *
     * @description
     * A property that allows the column to be hidden / shown with css
     * Note the default allows the DatagridColumn to have an *ngIf on it. (EHCAIWC - will occur if its not initialized)
     *
     * @default false
     *
     * @type boolean
     */
    readonly hidden: boolean;
    handleElRef: ElementRef;
    handleTrackerElRef: ElementRef;
    /**
     * Subscription to the sort service changes
     */
    private _sortSubscription;
    ngOnDestroy(): void;
    private _field;
    field: string;
    /**
     * Comparator to use when sorting the column
     */
    private _sortBy;
    sortBy: Comparator<any> | string;
    /**
     * Indicates if the column is sortable
     */
    readonly sortable: boolean;
    /**
     * Indicates if the column is currently sorted
     *
     * @deprecated This will be removed soon, in favor of the sortOrder mechanism
     */
    private _sorted;
    /**
     * @deprecated This will be removed soon, in favor of the sortOrder mechanism
     */
    sorted: boolean;
    /**
     * @deprecated This will be removed soon, in favor of the sortOrder mechanism
     */
    sortedChange: EventEmitter<boolean>;
    /**
     * Indicates how the column is currently sorted
     */
    private _sortOrder;
    sortOrder: SortOrder;
    sortOrderChange: EventEmitter<SortOrder>;
    /**
     * Sorts the datagrid based on this column
     */
    sort(reverse?: boolean): void;
    /**
     * Indicates if the column is currently sorted in ascending order
     */
    readonly asc: boolean;
    /**
     * Indicates if the column is currently sorted in descending order
     */
    readonly desc: boolean;
    /**
     * A custom filter for this column that can be provided in the projected content
     */
    customFilter: boolean;
    projectedFilter: any;
    filterValue: string;
    filterValueChange: EventEmitter<{}>;
    /***********
     *
     * @property hideable
     *
     * @description
     * When a column is hideable this is defined with an instance of DatagridHideableColumn.
     * When its not hideable should be undefined.
     *
     * @type {DatagridHideableColumn}
     */
    hideable: DatagridHideableColumn;
}
