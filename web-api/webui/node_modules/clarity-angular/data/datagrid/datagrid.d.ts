import { AfterContentInit, AfterViewInit, EventEmitter, OnDestroy, QueryList } from "@angular/core";
import { DatagridColumn } from "./datagrid-column";
import { DatagridItems } from "./datagrid-items";
import { DatagridPlaceholder } from "./datagrid-placeholder";
import { DatagridRow } from "./datagrid-row";
import { State } from "./interfaces/state";
import { ExpandableRowsCount } from "./providers/global-expandable-rows";
import { HideableColumnService } from "./providers/hideable-column.service";
import { Items } from "./providers/items";
import { RowActionService } from "./providers/row-action-service";
import { Selection, SelectionType } from "./providers/selection";
import { StateProvider } from "./providers/state.provider";
import { DatagridRenderOrganizer } from "./render/render-organizer";
export declare class Datagrid implements AfterContentInit, AfterViewInit, OnDestroy {
    private columnService;
    private organizer;
    items: Items;
    expandableRows: ExpandableRowsCount;
    selection: Selection;
    rowActionService: RowActionService;
    private stateProvider;
    constructor(columnService: HideableColumnService, organizer: DatagridRenderOrganizer, items: Items, expandableRows: ExpandableRowsCount, selection: Selection, rowActionService: RowActionService, stateProvider: StateProvider);
    SELECTION_TYPE: typeof SelectionType;
    /**
     * Freezes the datagrid while data is loading
     */
    loading: boolean;
    /**
     * Output emitted whenever the data needs to be refreshed, based on user action or external ones
     */
    refresh: EventEmitter<State>;
    /**
     * Public method to re-trigger the computation of displayed items manually
     */
    dataChanged(): void;
    /**
     * We grab the smart iterator from projected content
     */
    iterator: DatagridItems;
    /**
     * Array of all selected items
     */
    selected: any[];
    selectedChanged: EventEmitter<any[]>;
    /**
     * Selected item in single-select mode
     */
    singleSelected: any;
    singleSelectedChanged: EventEmitter<any>;
    /**
     * Selection/Deselection on row click mode
     */
    rowSelectionMode: boolean;
    /**
     * Indicates if all currently displayed items are selected
     */
    /**
     * Selects/deselects all currently displayed items
     * @param value
     */
    allSelected: boolean;
    /**
     * Custom placeholder detection
     */
    placeholder: DatagridPlaceholder;
    /**
     * Hideable Column data source / detection.
     */
    columns: QueryList<DatagridColumn>;
    /**
     * When the datagrid is user-managed without the smart iterator, we get the items displayed
     * by querying the projected content. This is needed to keep track of the models currently
     * displayed, typically for selection.
     */
    rows: QueryList<DatagridRow>;
    ngAfterContentInit(): void;
    /**
     * Our setup happens in the view of some of our components, so we wait for it to be done before starting
     */
    ngAfterViewInit(): void;
    /**
     * Subscriptions to all the services and queries changes
     */
    private _subscriptions;
    ngOnDestroy(): void;
    resize(): void;
}
