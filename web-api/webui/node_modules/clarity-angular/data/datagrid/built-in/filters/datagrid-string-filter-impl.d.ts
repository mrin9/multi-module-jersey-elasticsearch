import { Observable } from "rxjs/Observable";
import { Filter } from "../../interfaces/filter";
import { StringFilter } from "../../interfaces/string-filter";
export declare class DatagridStringFilterImpl implements Filter<any> {
    filterFn: StringFilter<any>;
    constructor(filterFn: StringFilter<any>);
    /**
     * The Observable required as part of the Filter interface
     */
    private _changes;
    readonly changes: Observable<string>;
    /**
     * Raw input value
     */
    private _rawValue;
    /**
     * Common setter for the input value
     */
    value: string;
    /**
     * Input value converted to lowercase
     */
    private _lowerCaseValue;
    readonly lowerCaseValue: string;
    /**
     * Indicates if the filter is currently active, meaning the input is not empty
     */
    isActive(): boolean;
    /**
     * Tests if an item matches a search text
     */
    accepts(item: any): boolean;
}
