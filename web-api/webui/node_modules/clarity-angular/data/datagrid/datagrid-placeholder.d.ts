import { Items } from "./providers/items";
import { Page } from "./providers/page";
export declare class DatagridPlaceholder {
    private items;
    private page;
    constructor(items: Items, page: Page);
    /**
     * Tests if the datagrid is empty, meaning it doesn't contain any items
     */
    readonly emptyDatagrid: boolean;
}
