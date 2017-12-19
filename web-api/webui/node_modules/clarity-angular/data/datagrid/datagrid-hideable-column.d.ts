import { TemplateRef } from "@angular/core";
import { Observable } from "rxjs/Observable";
/**
 * @class DatagridHideableColumn
 *
 * @description
 * A utility class for that adds hide/show functionality to a column, its cells and enables a toggler in the
 * DatagridColumnToggle Component.
 *
 */
export declare class DatagridHideableColumn {
    private _template;
    private _id;
    private _hidden;
    /**
     * @property hiddenChanges
     *
     * @description
     * A stream of state changes an instance of DatagridHideableColumn will broadcast to subscribers.
     *
     * @type {Subject<boolean>}
     */
    private hiddenChangesState;
    /**
     * @constructor
     *
     * @description
     * The init function for DatagridHideableColumn instances that does the following:
     *
     * 1. Set values for the private variables that enable a hideable column
     * 2. Broadcast the next hidden change for anyone (already) subscribed to this DatagridHideableColumn
     * TODO: Debug and verify that #2 is really necessary.
     *
     * @param _template
     * @param _id
     * @param _hidden
     */
    constructor(_template: TemplateRef<any>, _id: string, _hidden?: boolean);
    /**
     * @function template
     *
     * @description
     * A getter function that returns an TemplateRef of the DatagridColumn that is hideable. This is currently used to
     * populate the DatagridColumnToggle UI with the correct Column name.
     *
     * @returns {TemplateRef<any>}
     */
    readonly template: TemplateRef<any>;
    /**
     * @function id
     *
     * @description
     * public function that returns the id of a HideableCOlumn instance. Used by the HideableCOlumnService for passing
     * state and actions between DateGridColumns, DataGridCells & the DatagridColumnToggle Components.
     *
     * @returns {string}
     */
    readonly id: string;
    /**
     * @function hidden
     *
     * @description
     * A getter that returns the hidden value of a DatagridHideableColumn instance.
     * TODO: debug and make sure you really need this since we have the hiddenCHanges observable.
     *
     * @returns {boolean}
     */
    /**
     * @function hidden
     *
     * @description
     * The setter for setting the hidden state of a DatagridHideableColumn instance.
     * It also broadcasts the change after its set.
     *
     * @param value
     */
    hidden: boolean;
    /**
     * @function hiddenChangeState
     *
     * @description
     * An Observable for the HideableColumns hidden changes.
     *
     * @returns {Observable<boolean>}
     */
    readonly hiddenChangeState: Observable<boolean>;
    lastVisibleColumn: boolean;
}
