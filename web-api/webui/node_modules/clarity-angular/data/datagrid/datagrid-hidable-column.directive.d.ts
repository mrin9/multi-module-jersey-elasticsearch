import { TemplateRef, ViewContainerRef } from "@angular/core";
import { DatagridColumn } from "./datagrid-column";
import { DatagridHideableColumn } from "./datagrid-hideable-column";
export declare class DatagridHideableColumnDirective {
    private templateRef;
    private viewContainerRef;
    private dgColumn;
    /**
     * @property _hidden
     *
     * @description
     * Used to initialize the column with either hidden or visible state.
     *
     * @type boolean
     */
    private _hidden;
    /**
     * @function clrDgHideableColumn
     *
     * @description
     * Setter fn for the @Input with the same name as this structural directive.
     * It allows the user to pre-configure the column's hide/show state. { hidden: true }
     * It's more verbose but has more Clarity.
     *
     * @default false
     *
     * @type object
     *
     * @example
     * *clrDgHideableColumn
     * *clrDgHideableColumn={hidden: false}
     * *clrDgHideableColumn={hidden: true}
     *
     * @param value
     *
     */
    clrDgHideableColumn: any;
    /**
     * @property columnId
     *
     * @description
     * A unique identifier passed into the directive from the parent (A DatagridColumn).
     *
     *  @type {string}
     */
    columnId: string;
    /**
     * @property column
     *
     * @description
     * An instance of the DatagridHideableColumn Utility class that is used to:
     * 1. Create an instance of HideableColumn that will manage the TemplateRef, state and communication
     * 2. Manage the hidden/shown state for the column to which this directive is applied
     * 3. track the id of the hidden column so it can be used in cells as well as on the column
     */
    column: DatagridHideableColumn;
    /**
     * @description
     * Used the DatagridColumn to get and set an id for this HiddenColumn
     *
     * @param templateRef
     * @param viewContainerRef
     * @param hideableColumnService
     * @param dgColumn
     */
    constructor(templateRef: TemplateRef<any>, viewContainerRef: ViewContainerRef, dgColumn: DatagridColumn);
}
