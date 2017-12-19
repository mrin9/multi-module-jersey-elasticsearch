import { DoCheck, IterableDiffers, OnChanges, SimpleChanges, TemplateRef, TrackByFunction } from "@angular/core";
import { Items } from "./providers/items";
export declare class DatagridItems implements OnChanges, DoCheck {
    template: TemplateRef<any>;
    private _differs;
    private _items;
    private _rawItems;
    rawItems: any[];
    private _differ;
    constructor(template: TemplateRef<any>, _differs: IterableDiffers, _items: Items);
    ngOnChanges(changes: SimpleChanges): void;
    trackBy: TrackByFunction<any>;
    ngDoCheck(): void;
}
