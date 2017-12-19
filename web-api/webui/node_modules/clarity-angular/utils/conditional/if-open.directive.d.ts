import { EventEmitter, OnDestroy, TemplateRef, ViewContainerRef } from "@angular/core";
import { IfOpenService } from "./if-open.service";
export declare class IfOpenDirective implements OnDestroy {
    private ifOpenService;
    private template;
    private container;
    private subscription;
    /********
     * @function open
     *
     * @description
     * A getter that returns the current IfOpenService.open value.
     * @returns {boolean}
     */
    /*********
     * @function open
     *
     * @description
     * A setter that updates IfOpenService.open with value.
     *
     * @param value
     */
    open: boolean;
    /**********
     * @property openChange
     *
     * @description
     * An event emitter that emits when the open property is set to allow for 2way binding when the directive is
     * used with de-structured / de-sugared syntax.
     *
     * @type {EventEmitter<boolean>}
     */
    openChange: EventEmitter<boolean>;
    constructor(ifOpenService: IfOpenService, template: TemplateRef<any>, container: ViewContainerRef);
    /*********
     * @function updateView
     *
     * @description
     * Function that takes a boolean value and either created an embedded view for the associated ViewContainerRef or,
     * Clears all views from the ViewContainerRef
     * @param value
     */
    updateView(value: boolean): void;
    ngOnDestroy(): void;
}
