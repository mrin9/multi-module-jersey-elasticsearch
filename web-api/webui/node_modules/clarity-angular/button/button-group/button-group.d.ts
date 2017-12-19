import { ElementRef, QueryList } from "@angular/core";
import { Point } from "../../popover/common/popover";
import { ButtonInGroupService } from "../providers/buttonInGroup.service";
import { Button } from "./button";
export declare class ButtonGroup {
    buttonGroupNewService: ButtonInGroupService;
    private elementRef;
    buttons: QueryList<Button>;
    constructor(buttonGroupNewService: ButtonInGroupService, elementRef: ElementRef);
    inlineButtons: Button[];
    menuButtons: Button[];
    /**
     * 1. Initializes the initial Button Group View
     * 2. Subscribes to changes on the ContentChildren
     *    in case the user content projection changes
     */
    ngAfterContentInit(): void;
    /**
     * Moves the button into the other ViewContainer
     * when an update is received.
     *
     * @param button
     */
    rearrangeButton(button: Button): void;
    /**
     * Author: Eudes
     *
     * Finds the order of a button w.r.t other buttons
     *
     * @param buttonToMove
     * @returns {number}
     */
    getMoveIndex(buttonToMove: Button): number;
    /**
     * Finds where each button belongs based on
     * the ContentChildren
     */
    initializeButtons(): void;
    /**
     * Overflow Menu
     *
     */
    private _menuPosition;
    menuPosition: string;
    private _openMenu;
    openMenu: boolean;
    anchorPoint: Point;
    popoverPoint: Point;
    /**
     * Toggle the Dropdown Menu when the Dropdown Toggle is
     * clicked. Also set a flag that indicates that the toggle
     * was clicked so that we don't traverse the DOM to find the
     * location of the click.
     */
    toggleMenu(): void;
    /**
     * Flag with indicates if the overflow menu toggle was clicked.
     * If true, this can save us traversing the DOM to find
     * whether the click was withing the button group toggle
     * or menu in the onMouseClick method
     * @type {boolean}
     * @private
     */
    private _overflowMenuToggleClicked;
    /**
     * Called on mouse clicks anywhere in the DOM.
     * Checks to see if the mouseclick happened on the host or outside
     * @param target
     */
    onMouseClick(target: any): void;
}
