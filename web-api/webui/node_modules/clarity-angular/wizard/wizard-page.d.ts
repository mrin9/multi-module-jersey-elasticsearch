import { EventEmitter, OnInit, TemplateRef } from "@angular/core";
import { WizardPageButtonsDirective } from "./directives/page-buttons";
import { WizardPageHeaderActionsDirective } from "./directives/page-header-actions";
import { WizardPageNavTitleDirective } from "./directives/page-navtitle";
import { WizardPageTitleDirective } from "./directives/page-title";
import { ButtonHubService } from "./providers/button-hub";
import { PageCollectionService } from "./providers/page-collection";
import { WizardNavigationService } from "./providers/wizard-navigation";
/**
 * The WizardPage component is responsible for displaying the content of each step
 * in the wizard workflow.
 *
 * WizardPage component has hooks into the navigation service (WizardPage.navService),
 * page collection (WizardPage.pageCollection), and button service
 * (WizardPage.buttonService). These three providers are shared across the components
 * within each instance of a Wizard.
 *
 * @export
 * @class WizardPage
 * @implements {OnInit}
 */
export declare class WizardPage implements OnInit {
    private navService;
    pageCollection: PageCollectionService;
    buttonService: ButtonHubService;
    /**
     * Creates an instance of WizardPage.
     *
     * @param {WizardNavigationService} navService
     * @param {PageCollectionService} pageCollection
     * @param {ButtonHubService} buttonService
     *
     * @memberof WizardPage
     */
    constructor(navService: WizardNavigationService, pageCollection: PageCollectionService, buttonService: ButtonHubService);
    /**
     * Contains a reference to the page title which is used for a number
     * of different tasks for display in the wizard.
     *
     * @type {WizardPageTitleDirective}
     * @memberof WizardPage
     */
    pageTitle: WizardPageTitleDirective;
    /**
     * Contains a reference to the desired title for the page's step in the
     * navigation on the left side of the wizard. Can be projected to change the
     * navigation link's text.
     *
     * If not defined, then WizardPage.pageTitle will be displayed in the stepnav.
     *
     * @
     * @type {WizardPageNavTitleDirective}
     * @memberof WizardPage
     */
    pageNavTitle: WizardPageNavTitleDirective;
    /**
     * Contains a reference to the buttons defined within the page. If not defined,
     * the wizard defaults to the set of buttons defined as a direct child of the
     * wizard.
     *
     * @type {WizardPageButtonsDirective}
     * @memberof WizardPage
     */
    _buttons: WizardPageButtonsDirective;
    /**
     * Contains a reference to the header actions defined within the page. If not defined,
     * the wizard defaults to the set of header actions defined as a direct child of the
     * wizard.
     *
     * @type {WizardPageHeaderActionsDirective}
     * @memberof WizardPage
     */
    _headerActions: WizardPageHeaderActionsDirective;
    /**
     * @private
     * @ignore
     * @memberof WizardPage
     */
    private _nextStepDisabled;
    /**
     * A getter that tells whether or not the wizard should be allowed
     * to move to the next page.
     *
     * Useful for in-page validation because it prevents forward navigation
     * and visibly disables the next button.
     *
     * Does not require that you re-implement navigation routines like you
     * would if you were using WizardPage.preventDefault or
     * Wizard.preventDefault.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardPage
     */
    /**
     * Sets whether the page should allow forward navigation.
     *
     * @memberof WizardPage
     */
    nextStepDisabled: boolean;
    /**
     * Emits when the value of WizardPage.nextStepDisabled changes.
     * Should emit the new value of nextStepDisabled.
     *
     * @type {EventEmitter <boolean>}
     * @memberof WizardPage
     */
    nextStepDisabledChange: EventEmitter<boolean>;
    /**
     * @private
     * @ignore
     * @memberof WizardPage
     */
    private _previousStepDisabled;
    /**
     * A getter that tells whether or not the wizard should be allowed
     * to move to the previous page.
     *
     * Useful for in-page validation because it prevents backward navigation
     * and visibly disables the previous button.
     *
     * Does not require that you re-implement navigation routines like you
     * would if you were using WizardPage.preventDefault or
     * Wizard.preventDefault.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardPage
     */
    /**
     * Sets whether the page should allow backward navigation.
     *
     * @memberof WizardPage
     */
    previousStepDisabled: boolean;
    /**
     * Emits when the value of WizardPage.previousStepDisabled changes.
     * Should emit the new value of previousStepDisabled.
     *
     * @type {EventEmitter <boolean>}
     * @memberof WizardPage
     */
    previousStepDisabledChange: EventEmitter<boolean>;
    /**
     * Overrides all actions from the page level, so you can use an alternate function for
     * validation or data-munging with a WizardPage.onCommit (clrWizardPageOnCommit output),
     * WizardPage.onCancel (clrWizardPageOnCancel output), or one
     * of the granular page-level button click event emitters.
     *
     * @type {boolean}
     * @memberof WizardPage
     */
    preventDefault: boolean;
    /**
     *
     * @ignore
     * @private
     *
     * @memberof WizardPage
     */
    private _stopCancel;
    /**
     * A getter that retrieves whether the page is preventing the cancel action.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardPage
     */
    /**
     * Overrides the cancel action from the page level. Allows you to use an
     * alternate function for validation or data-munging before cancelling the
     * wizard when combined with the WizardPage.onCancel
     * (the clrWizardPageOnCancel output).
     *
     * Requires that you manually close the wizard from your host component,
     * usually with a call to Wizard.forceNext() or wizard.next();
     *
     * @memberof WizardPage
     */
    stopCancel: boolean;
    /**
     *
     * @ignore
     * @type {EventEmitter <boolean>}
     * @memberof WizardPage
     */
    stopCancelChange: EventEmitter<boolean>;
    /**
     *
     *
     * @private
     * @ignore
     * @memberof WizardPage
     */
    private _stopNext;
    /**
     * A getter that tells you whether the page is preventing the next action.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardPage
     */
    /**
     * Overrides forward navigation from the page level. Allows you to use an
     * alternate function for validation or data-munging before moving the
     * wizard to the next pagewhen combined with the WizardPage.onCommit
     * (clrWizardPageOnCommit) or WizardPage.nextButtonClicked
     * (clrWizardPageNext) outputs.
     *
     * Requires that you manually tell the wizard to navigate forward from
     * the hostComponent, usually with a call to Wizard.forceNext() or
     * wizard.next();
     *
     * @memberof WizardPage
     */
    stopNext: boolean;
    /**
     * An event emitter carried over from a legacy version of WizardPage.
     * Fires an event on WizardPage whenever the next or finish buttons
     * are clicked and the page is the current page of the Wizard.
     *
     * Note that this does not automatically emit an event when a custom
     * button is used in place of a next or finish button.
     *
     * @type {EventEmitter <string>}
     * @memberof WizardPage
     */
    onCommit: EventEmitter<string>;
    /**
     * Emits an event when WizardPage becomes the current page of the
     * Wizard.
     *
     * @type {EventEmitter <string>}
     * @memberof WizardPage
     */
    onLoad: EventEmitter<string>;
    /**
     * Emits an event when the WizardPage invokes the cancel routine for the wizard.
     *
     * Can be used in conjunction with the WizardPage.stopCancel
     * (clrWizardPagePreventDefaultCancel) or WizardPage.preventDefault
     * (clrWizardPagePagePreventDefault) inputs to implement custom cancel
     * functionality at the page level. This is useful if you would like to do
     * validation, save data, or warn users before cancelling the wizard.
     *
     * Note that this requires you to call Wizard.close() from the host component.
     * This constitues a full replacement of the cancel functionality.
     *
     * @type {EventEmitter <WizardPage>}
     * @memberof WizardPage
     */
    pageOnCancel: EventEmitter<WizardPage>;
    /**
     * Emits an event when the finish button is clicked and the WizardPage is
     * the wizard's current page.
     *
     * Can be used in conjunction with the WizardPage.preventDefault
     * (clrWizardPagePagePreventDefault) input to implement custom finish
     * functionality at the page level. This is useful if you would like to do
     * validation, save data, or warn users before allowing them to complete
     * the wizard.
     *
     * Note that this requires you to call Wizard.finish() or Wizard.forceFinish()
     * from the host component. This combination creates a full replacement of
     * the finish functionality.
     *
     * @type {EventEmitter <WizardPage>}
     * @memberof WizardPage
     */
    finishButtonClicked: EventEmitter<WizardPage>;
    /**
     * Emits an event when the previous button is clicked and the WizardPage is
     * the wizard's current page.
     *
     * Can be used in conjunction with the WizardPage.preventDefault
     * (clrWizardPagePagePreventDefault) input to implement custom backwards
     * navigation at the page level. This is useful if you would like to do
     * validation, save data, or warn users before allowing them to go
     * backwards in the wizard.
     *
     * Note that this requires you to call Wizard.previous()
     * from the host component. This combination creates a full replacement of
     * the backwards navigation functionality.
     *
     * @type {EventEmitter <WizardPage>}
     * @memberof WizardPage
     */
    previousButtonClicked: EventEmitter<WizardPage>;
    /**
     * Emits an event when the next button is clicked and the WizardPage is
     * the wizard's current page.
     *
     * Can be used in conjunction with the WizardPage.preventDefault
     * (clrWizardPagePagePreventDefault) input to implement custom forwards
     * navigation at the page level. This is useful if you would like to do
     * validation, save data, or warn users before allowing them to go
     * to the next page in the wizard.
     *
     * Note that this requires you to call Wizard.forceNext() or Wizard.next()
     * from the host component. This combination creates a full replacement of
     * the forward navigation functionality.
     *
     * @type {EventEmitter <WizardPage>}
     * @memberof WizardPage
     */
    nextButtonClicked: EventEmitter<WizardPage>;
    /**
     * Emits an event when a danger button is clicked and the WizardPage is
     * the wizard's current page. By default, a danger button will act as
     * either a "next" or "finish" button depending on if the WizardPage is the
     * last page or not.
     *
     * Can be used in conjunction with the WizardPage.preventDefault
     * (clrWizardPagePagePreventDefault) input to implement custom forwards
     * or finish navigation at the page level when the danger button is clicked.
     * This is useful if you would like to do validation, save data, or warn
     * users before allowing them to go to the next page in the wizard or
     * finish the wizard.
     *
     * Note that this requires you to call Wizard.finish(), Wizard.forceFinish(),
     * Wizard.forceNext() or Wizard.next() from the host component. This
     * combination creates a full replacement of the forward navigation and
     * finish functionality.
     *
     * @type {EventEmitter <WizardPage>}
     * @memberof WizardPage
     */
    dangerButtonClicked: EventEmitter<WizardPage>;
    /**
     * Emits an event when a next, finish, or danger button is clicked and the
     * WizardPage is the wizard's current page.
     *
     * Can be used in conjunction with the WizardPage.preventDefault
     * (clrWizardPagePagePreventDefault) input to implement custom forwards
     * or finish navigation at the page level, regardless of the type of
     * primary button.
     *
     * This is useful if you would like to do validation, save data, or warn
     * users before allowing them to go to the next page in the wizard or
     * finish the wizard.
     *
     * Note that this requires you to call Wizard.finish(), Wizard.forceFinish(),
     * Wizard.forceNext() or Wizard.next() from the host component. This
     * combination creates a full replacement of the forward navigation and
     * finish functionality.
     *
     * @type {EventEmitter <WizardPage>}
     * @memberof WizardPage
     */
    primaryButtonClicked: EventEmitter<string>;
    customButtonClicked: EventEmitter<string>;
    /**
     * An input value that is used internally to generate the WizardPage ID as
     * well as the step nav item ID.
     *
     * Typed as any because it should be able to accept numbers as well as
     * strings. Passing an index for wizard whose pages are created with an
     * ngFor loop is a common use case.
     *
     * @type {*}
     * @memberof WizardPage
     */
    _id: any;
    /**
     * A read-only getter that generates an ID string for the wizard page from
     * either the value passed to the WizardPage "id" input or a wizard page
     * counter shared across all wizard pages in the application.
     *
     * Note that the value passed into the ID input Will be prefixed with
     * "clr-wizard-page-".
     *
     * @readonly
     *
     * @memberof WizardPage
     */
    readonly id: string;
    /**
     * A read-only getter that serves as a convenience for those who would rather
     * not think in the terms of !WizardPage.nextStepDisabled. For some use cases,
     * WizardPage.readyToComplete is more logical and declarative.
     *
     * @readonly
     *
     * @memberof WizardPage
     */
    readonly readyToComplete: boolean;
    /**
     *
     * @ignore
     * @private
     * @type {boolean}
     * @memberof WizardPage
     */
    private _complete;
    /**
     * A page is marked as completed if it is both readyToComplete and completed,
     * as in the next or finish action has been executed while this page was current.
     *
     * Note there is and open question about how to handle pages that are marked
     * complete but who are no longer readyToComplete. This might indicate an error
     * state for the WizardPage. Currently, the wizard does not acknowledge this state
     * and only returns that the page is incomplete.
     *
     * @type {boolean}
     * @memberof WizardPage
     */
    /**
     * A WizardPage can be manually set to completed using this boolean setter.
     * It is recommended that users rely on the convenience functions in the wizard
     * and navigation service instead of manually setting pages’ completion state.
     *
     * @memberof WizardPage
     */
    completed: boolean;
    /**
     * Checks with the navigation service to see if it is the current page.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardPage
     */
    readonly current: boolean;
    readonly disabled: boolean;
    /**
     * A read-only getter that returns whether or not the page is navigable
     * in the wizard. A wizard page can be navigated to if it is completed
     * or the page before it is completed.
     *
     * This getter handles the logic for enabling or disabling the links in
     * the step nav on the left Side of the wizard.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardPage
     */
    readonly enabled: boolean;
    /**
     * A read-only getter that returns whether or not the page before this
     * WizardPage is completed. This is useful for determining whether or not
     * a page is navigable if it is not current or already completed.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardPage
     */
    readonly previousCompleted: boolean;
    /**
     *
     * @ignore
     * @readonly
     * @type {TemplateRef < any >}
     * @memberof WizardPage
     */
    readonly title: TemplateRef<any>;
    /**
     *
     * @ignore
     * @readonly
     * @type {TemplateRef < any >}
     * @memberof WizardPage
     */
    readonly navTitle: TemplateRef<any>;
    /**
     *
     * @ignore
     * @readonly
     * @type {TemplateRef < any >}
     * @memberof WizardPage
     */
    readonly headerActions: TemplateRef<any>;
    /**
     *
     * @ignore
     * @readonly
     * @type {TemplateRef < any >}
     * @memberof WizardPage
     */
    readonly hasHeaderActions: boolean;
    /**
     *
     * @ignore
     * @readonly
     * @type {TemplateRef < any >}
     * @memberof WizardPage
     */
    readonly buttons: TemplateRef<any>;
    /**
     * A read-only getter that returns a boolean that says whether or
     * not the WizardPage includes buttons. Used to determine if the
     * Wizard should override the default button set defined as
     * its direct children.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardPage
     */
    readonly hasButtons: boolean;
    /**
     * Uses the nav service to make the WizardPage the current page in the
     * wizard. Bypasses all checks but still emits the WizardPage.onLoad
     * (clrWizardPageOnLoad) output.
     *
     * In most cases, it is better to use the default navigation functions
     * in Wizard.
     *
     * @memberof WizardPage
     */
    makeCurrent(): void;
    /**
     * Links the nav service and establishes the current page if one is not defined.
     *
     * @memberof WizardPage
     */
    ngOnInit(): void;
    /**
     * A read-only getter that returns the id used by the step nav item associated with the page.
     *
     * WizardPage needs this ID string for aria information.
     *
     * @readonly
     * @type {string}
     * @memberof WizardPage
     */
    readonly stepItemId: string;
}
