import { AfterViewInit, ElementRef, EventEmitter, Renderer2 } from "@angular/core";
import { DatagridFilter } from "../../datagrid-filter";
import { StringFilter } from "../../interfaces/string-filter";
import { CustomFilter } from "../../providers/custom-filter";
import { FiltersProvider, RegisteredFilter } from "../../providers/filters";
import { DomAdapter } from "../../render/dom-adapter";
import { DatagridFilterRegistrar } from "../../utils/datagrid-filter-registrar";
import { DatagridStringFilterImpl } from "./datagrid-string-filter-impl";
export declare class DatagridStringFilter extends DatagridFilterRegistrar<DatagridStringFilterImpl> implements CustomFilter, AfterViewInit {
    private renderer;
    private domAdapter;
    constructor(renderer: Renderer2, filters: FiltersProvider, domAdapter: DomAdapter);
    /**
     * Customizable filter logic based on a search text
     */
    customStringFilter: StringFilter<any> | RegisteredFilter<DatagridStringFilterImpl>;
    /**
     * Indicates if the filter dropdown is open
     */
    open: boolean;
    /**
     * We need the actual input element to automatically focus on it
     */
    input: ElementRef;
    /**
     * We grab the DatagridFilter we wrap to register this StringFilter to it.
     */
    filterContainer: DatagridFilter;
    ngAfterViewInit(): void;
    /**
     * Common setter for the input value
     */
    value: string;
    filterValueChange: EventEmitter<{}>;
    close(): void;
}
