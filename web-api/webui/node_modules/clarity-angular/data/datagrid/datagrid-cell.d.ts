import { QueryList } from "@angular/core";
import { Signpost } from "../../popover/signpost/signpost";
import { HideableColumnService } from "./providers/hideable-column.service";
export declare class DatagridCell {
    hideableColumnService: HideableColumnService;
    /*********
     * @property signpost
     *
     * @type {Signpost}
     *
     * @description
     * @ContentChild is used to detect the presence of a Signpost in the projected content.
     * On the host, we set the .datagrid-signpost-trigger class on the cell when signpost.length is greater than 0.
     *
     * @type {Querylist<Signpost>}
     */
    signpost: QueryList<Signpost>;
    /**
     * @property hidden
     *
     * @description
     * Property used to apply a css class to this cell that hides it when hidden = true.
     *
     * @type {boolean}
     */
    readonly hidden: boolean;
    /**
     * @property id
     *
     * @description
     * An identifier for an instance of this cell that maps it to a specific column
     *
     * @type {string}
     */
    id: string;
    constructor(hideableColumnService: HideableColumnService);
}
