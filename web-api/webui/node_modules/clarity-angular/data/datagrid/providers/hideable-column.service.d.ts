import { Observable } from "rxjs/Observable";
import { DatagridHideableColumn } from "../datagrid-hideable-column";
/**
 * @class HideableColumnService
 *
 * @description
 * An @Injectable provider class that enables
 *
 * 1. Managing, track hideability of DatagridColumns
 *
 */
export declare class HideableColumnService {
    /**********
     * @property dgHiddenColumnMap
     *
     * @description
     * An array of DatagridHideableColumn.
     * NOTE: because we can have columns w/o the *clrDgHideableColumn directive
     * this array will have empty spaces a.k.a nulls. This is needed to be able to map
     * DatagridCells to DatagridColumns in the RowRenderer.
     *
     *
     * @type { DatagridHideableColumn[] }
     */
    private _columnList;
    /**********
     *
     * @property dgHiddenColumnMapChange
     *
     * @description
     * A behavior subject that can broadcast updates to the column list.
     * NOTE: I am using BehaviorSubject because <clr-dg-column-toggle> is not getting the latest _columnListChange
     * on page load.
     *
     * @type {BehaviorSubject<DatagridColumn[]>}
     */
    private _columnListChange;
    /**********
     *
     * @property canHideNextColumn
     *
     * @description
     * Service function that is called by clr-dg-column-toggle component. Use this if you need to ask if you can hide
     * a column. It acts as a guard against hiding all the columns making sure there is at least one column displayed.
     *
     * @returns {boolean}
     */
    readonly canHideNextColumn: boolean;
    /**********
     *
     * @property checkForAllColumnsVisible
     *
     * @description
     * For when you need to know if the datagrid's columns are all showing.
     *
     * @return {boolean}
     */
    readonly checkForAllColumnsVisible: boolean;
    /***********
     * @property columnListChange
     *
     * @description
     * A public property that enables subscribers to hear updates to the column map.
     * Use this if you need to do something whenever the Datagrid's column list is changed (i.e *ngIf on a column).
     *
     * @returns {Observable<DatagridHideableColumn[]>}
     */
    readonly columnListChange: Observable<DatagridHideableColumn[]>;
    /**********
     *
     * @function getColumns
     *
     * @description
     * Public function that returns the current list of columns. I needed an array of to iterate on in the RowRenderer
     * but subscribing to the _columnListChange changes did not seem like the correct way to get it.
     *
     * @returns {DatagridColumn[]}
     */
    getColumns(): DatagridHideableColumn[];
    /**********
     * @function showHiddenColumns
     *
     * @description
     * Iterate through the current _columnList:
     * - if it has a DatagridHideableColumn and is hidden then show it.
     * - if it's DatagridHideableColumn was previously the last column visible, turn that flag off.
     *
     */
    showHiddenColumns(): void;
    /**
     * @function updateColumnList
     *
     * @param columns: DatagridColumn[]
     *
     * @description
     * Creates an array of DatagridHideableColumn's || null based column array passed as param.
     * Is dependent on the order in @ContentChildren in Datagrid.
     *
     * @param columns
     *
     */
    updateColumnList(columns: DatagridHideableColumn[]): void;
    /**********
     *
     * @function updateForLastVisibleColumn
     *
     * @description
     * Gets the current visible count for all columns.
     * When it is greater than 1 it marks everything as false for the lastVisibleColumn.
     * When visible count is not > 1 (i.e) 1. , it finds the only column that is not hidden and marks it as the
     * lastVisibleColumn.
     *
     * @return void
     *
     */
    updateForLastVisibleColumn(): void;
    /**********
     *
     * @function getColumnById
     *
     * @description
     * Return a HideableColumn in this._columnList for the given id.
     *
     * @param id
     *
     * @type string
     *
     * @returns HideableColumn
     *
     */
    getColumnById(id: string): undefined | DatagridHideableColumn;
}
