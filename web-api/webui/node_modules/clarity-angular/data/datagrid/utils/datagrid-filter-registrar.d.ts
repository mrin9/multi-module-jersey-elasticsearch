import { OnDestroy } from "@angular/core";
import { Filter } from "../interfaces/filter";
import { FiltersProvider, RegisteredFilter } from "../providers/filters";
export declare abstract class DatagridFilterRegistrar<F extends Filter<any>> implements OnDestroy {
    private filters;
    constructor(filters: FiltersProvider);
    registered: RegisteredFilter<F>;
    readonly filter: F;
    setFilter(filter: F | RegisteredFilter<F>): void;
    deleteFilter(): void;
    ngOnDestroy(): void;
}
