import { AfterContentInit, AfterViewChecked, ElementRef, OnDestroy, QueryList, Renderer2 } from "@angular/core";
import { Items } from "../providers/items";
import { Page } from "../providers/page";
import { DomAdapter } from "./dom-adapter";
import { DatagridHeaderRenderer } from "./header-renderer";
import { DatagridRenderOrganizer } from "./render-organizer";
export declare class DatagridMainRenderer implements AfterContentInit, AfterViewChecked, OnDestroy {
    private organizer;
    private items;
    private page;
    private domAdapter;
    private el;
    private renderer;
    constructor(organizer: DatagridRenderOrganizer, items: Items, page: Page, domAdapter: DomAdapter, el: ElementRef, renderer: Renderer2);
    headers: QueryList<DatagridHeaderRenderer>;
    ngAfterContentInit(): void;
    ngAfterViewChecked(): void;
    private _heightSet;
    private shouldComputeHeight();
    /**
     * Computes the height of the datagrid.
     *
     * NOTE: We had to choose to set the height instead of the min-height because
     * IE 11 requires the height on the parent for the children flex grow/shrink properties to work.
     * When we used min-height, 1 1 auto doesn't used to work in IE11 :-(
     * But this doesn't affect the fix. It works in both fixed & variable height datagrids.
     *
     * Refer: http://stackoverflow.com/questions/24396205/flex-grow-not-working-in-internet-explorer-11-0
     */
    private computeDatagridHeight();
    private resetDatagridHeight();
    private _subscriptions;
    ngOnDestroy(): void;
    /**
     * Makes each header compute its width.
     */
    private computeHeadersWidth();
    /**
     * Indicates if we want to re-compute columns width. This should only happen:
     * 1) When headers change, with columns being added or removed
     * 2) When rows are lazily loaded for the first time
     */
    private columnsSizesStable;
    private shouldStabilizeColumns;
    /**
     * Triggers a whole re-rendring cycle to set column sizes, if needed.
     */
    private stabilizeColumns();
}
