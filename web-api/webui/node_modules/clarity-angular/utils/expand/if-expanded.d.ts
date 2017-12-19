import { EventEmitter, OnDestroy, OnInit, TemplateRef, ViewContainerRef } from "@angular/core";
import { Expand } from "./providers/expand";
/**
 * TODO: make this a reusable directive outside of Datagrid, like [clrLoading].
 */
export declare class IfExpanded implements OnInit, OnDestroy {
    private template;
    private container;
    private expand;
    private _expanded;
    expanded: boolean;
    expandedChange: EventEmitter<boolean>;
    constructor(template: TemplateRef<any>, container: ViewContainerRef, expand: Expand);
    /**
     * Subscriptions to all the services and queries changes
     */
    private _subscriptions;
    private updateView();
    ngOnInit(): void;
    ngOnDestroy(): void;
}
