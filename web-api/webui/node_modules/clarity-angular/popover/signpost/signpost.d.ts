import { SignpostTriggerDirective } from "./signpost-trigger.directive";
export declare class Signpost {
    /**********
     * @property useCustomTrigger
     *
     * @description
     * Flag used to determine if we need to use the default trigger or a user supplied trigger element.
     *
     * @type {boolean}
     */
    useCustomTrigger: boolean;
    /**********
     * @property signPostTrigger
     *
     * @description
     * Uses ContentChild to check for a user supplied element with the SignpostTriggerDirective on it.
     *
     * @type {SignpostTriggerDirective}
     */
    customTrigger: SignpostTriggerDirective;
}
