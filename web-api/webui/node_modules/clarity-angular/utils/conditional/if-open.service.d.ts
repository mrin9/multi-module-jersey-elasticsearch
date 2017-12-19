import { Observable } from "rxjs/Observable";
export declare class IfOpenService {
    /********
     * @property _openChange
     *
     * @description
     * A RXJS Subject that updates and provides subscriptions to for the current open state of a component template
     * implemting the IfOpen structural directive.
     *
     * @type {Subject<boolean>}
     * @private
     */
    private _openChange;
    /*********
     * @property _open
     *
     * @description
     * A property holding the current value for open/closed state of an IfOpen structural directive.
     *
     * @type {boolean}
     * @private
     */
    private _open;
    /*********
     * @function openChange
     *
     * @description
     * A getter function that provides an observable for the _opened Subject.
     *
     * @returns {Observable<boolean>}
     */
    readonly openChange: Observable<boolean>;
    /*********
     *
     * @function open
     *
     * @description
     * A getter that returns the current value of this IfOpen instance.
     * @returns {boolean}
     */
    /*********
     * @function open
     *
     * @description
     * A setter function that updates the current state of _open for this instance of IfOpen structural directive. And,
     * broadcasts the new value to all subscribers.
     *
     * @param value
     */
    open: boolean;
    /**
     * Sometimes, we need to remember the event that triggered the toggling to avoid loops.
     * This is for instance the case of components that open on a click, but close on a click outside.
     */
    originalEvent: any;
    toggleWithEvent(event: any): void;
}
