import { ElementRef, Injector } from "@angular/core";
import { AbstractPopover } from "../common/abstract-popover";
export declare class SignpostContent extends AbstractPopover {
    constructor(injector: Injector, parentHost: ElementRef);
    /**********
     * @function close
     *
     * @description
     * Close function that uses the signpost instance to toggle the state of the content popover.
     *
     */
    close(): void;
    private _position;
    /*********
     * @function set position
     *
     * @description
     * A setter for the position of the SignpostContent popover. This is a combination of the following:
     * - anchorPoint - where on the trigger to anchor the SignpostContent
     * - popoverPoint - where on the SignpostContent container to align with the anchorPoint
     * - offsetY - where on the Y axis to align the SignpostContent so it meets specs
     * - offsetX - where on the X axis to align the SignpostContent so it meets specs
     * There are 12 possible positions to place a SignpostContent container:
     * - top-left
     * - top-middle
     * - top-right
     * - right-top
     * - right-middle
     * - right-bottom
     * - bottom-right
     * - bottom-middle
     * - bottom-left
     * - left-bottom
     * - left-middle
     * - left-top
     *
     * I think of it as follows for 'top-left' -> CONTAINER_SIDE-SIDE_POSITION. In this case CONTAINER_SIDE is 'top'
     * meaning the top of the trigger icon (above the icon that hides/shows) the SignpostContent. And, SIDE_POSITION is
     * 'left' meaning two things: 1) the SignpostContent container extends to the left and 2) the 'arrow/pointer'
     * linking the SingpostContent to the trigger points down at the horizontal center of the trigger icon.
     *
     * @param newPosition
     */
    position: string;
}
