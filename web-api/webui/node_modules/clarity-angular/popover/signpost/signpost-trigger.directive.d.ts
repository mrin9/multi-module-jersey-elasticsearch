import { ElementRef, OnDestroy, Renderer2 } from "@angular/core";
import { IfOpenService } from "../../utils/conditional/if-open.service";
export declare class SignpostTriggerDirective implements OnDestroy {
    private ifOpenService;
    private renderer;
    private el;
    private subscriptions;
    constructor(ifOpenService: IfOpenService, renderer: Renderer2, el: ElementRef);
    ngOnDestroy(): void;
    /**********
     * @function onSignpostTriggerClick
     *
     * @description
     * click handler for the Signpost trigger button used to hide/show SignpostContent.
     */
    onSignpostTriggerClick(event: Event): void;
}
