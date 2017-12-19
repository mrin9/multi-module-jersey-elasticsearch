import { OnDestroy, TemplateRef } from "@angular/core";
import { Observable } from "rxjs/Observable";
import { Subscription } from "rxjs/Subscription";
import { WizardPage } from "../wizard-page";
import { ButtonHubService } from "./button-hub";
import { PageCollectionService } from "./page-collection";
/**
 * Performs navigation functions for a wizard and manages the current page. Presented as a
 * separate service to encapsulate the behavior of navigating and completing the wizard so
 * that it can be shared across the wizard and its sub-components.
 *
 * The easiest way to access the navigation service is there a reference on your wizard. The
 * Following example would allow you to access your instance of the wizard from your host
 * component and thereby access the navigation service via YourHostComponent.wizard.navService.
 *
 * @example
 * <clr-wizard #wizard ...>
 *
 * @example
 * export class YourHostComponent {
 *   @ViewChild("wizard") wizard: Wizard;
 *   ...
 * }
 *
 * @export
 * @class WizardNavigationService
 * @implements {OnDestroy}
 */
export declare class WizardNavigationService implements OnDestroy {
    pageCollection: PageCollectionService;
    buttonService: ButtonHubService;
    /**
     * Is notified when a previous button is clicked in the wizard. Performs checks
     * before alerting the current page of the button click. Enacts navigation to
     * the previous page if not overridden at the page level.
     *
     * @type {Subscription}
     * @memberof WizardNavigationService
     */
    previousButtonSubscription: Subscription;
    /**
     * Is notified when a Next button is clicked in the wizard.
     *
     * @type {Subscription}
     * @memberof WizardNavigationService
     */
    nextButtonSubscription: Subscription;
    /**
     * Is notified when a danger button is clicked in the wizard.
     *
     * @type {Subscription}
     * @memberof WizardNavigationService
     */
    dangerButtonSubscription: Subscription;
    /**
     * Is notified when a  finish button is clicked in the wizard.
     *
     * @type {Subscription}
     * @memberof WizardNavigationService
     */
    finishButtonSubscription: Subscription;
    /**
     * Is notified when a Custom button is clicked in the wizard.
     *
     * @type {Subscription}
     * @memberof WizardNavigationService
     */
    customButtonSubscription: Subscription;
    /**
     * Is notified when a Cancel button is clicked in the wizard. Notifies the wizard,
     * which handles all cancel functionality, if cancel is not overridden at the page
     * level.
     *
     * @type {Subscription}
     * @memberof WizardNavigationService
     */
    cancelButtonSubscription: Subscription;
    /**
     * Resets navigation to make the first page current when the page collection service
     * emits an event notifying WizardNavigationService that it has reset all pages
     * to their pristine, incomplete state.
     *
     * @type {Subscription}
     * @memberof WizardNavigationService
     */
    pagesResetSubscription: Subscription;
    /**
     * Creates an instance of WizardNavigationService. Also sets up subscriptions
     * that listen to the button service to determine when a button has been clicked
     * in the wizard. Is also responsible for taking action when the page collection
     * requests that navigation be reset to its pristine state.
     *
     * @param {PageCollectionService} pageCollection
     * @param {ButtonHubService} buttonService
     *
     * @memberof WizardNavigationService
     */
    constructor(pageCollection: PageCollectionService, buttonService: ButtonHubService);
    /**
     *
     * @ignore
     *
     * @memberof WizardNavigationService
     */
    ngOnDestroy(): void;
    /**
     *
     * @ignore
     * @private
     *
     * @memberof WizardNavigationService
     */
    private _currentChanged;
    /**
     * An Observable that is predominantly used amongst the subcomponents and services
     * of the wizard. It is recommended that users listen to the WizardPage.onLoad
     * (clrWizardPageOnLoad) output instead of this Observable.
     *
     * @private
     *
     * @memberof WizardNavigationService
     */
    readonly currentPageChanged: Observable<WizardPage>;
    /**
     * A Boolean flag used by the WizardPage to avoid a race condition when pages are
     * loading and there is no current page defined.
     *
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    navServiceLoaded: boolean;
    /**
     * A boolean flag shared across the Wizard subcomponents that follows the value
     * of the Wizard.forceForward (clrWizardForceForwardNavigation) input. When true,
     * navigating backwards in the stepnav menu will reset any skipped pages' completed
     * state to false.
     *
     * This is useful when a wizard executes validation on a page-by-page basis when
     * the next button is clicked.
     *
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    forceForwardNavigation: boolean;
    /**
     *
     * @ignore
     * @readonly
     * @type {TemplateRef<any>}
     * @memberof WizardNavigationService
     */
    readonly currentPageTitle: TemplateRef<any>;
    /**
     * Returns a Boolean that tells you whether or not the current page is the first
     * page in the Wizard.
     *
     * This is helpful for determining whether a page is navigable.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    readonly currentPageIsFirst: boolean;
    /**
     * Returns a Boolean that tells you whether or not the current page is the
     * next to last page in the Wizard.
     *
     * This is used to determine the animation state of ghost pages.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    readonly currentPageIsNextToLast: boolean;
    /**
     * Returns a Boolean that tells you whether or not the current page is the
     * last page in the Wizard.
     *
     * This is used to determine the animation state of ghost pages as well as
     * which buttons should display in the wizard footer.
     *
     * @readonly
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    readonly currentPageIsLast: boolean;
    /**
     *
     * @ignore
     * @private
     * @type {WizardPage}
     * @memberof WizardNavigationService
     */
    private _currentPage;
    /**
     * Returns the WizardPage object of the current page or null.
     *
     * @type {WizardPage}
     * @memberof WizardNavigationService
     */
    /**
     * Accepts a WizardPage object, since that object to be the current/active
     * page in the wizard, and emits the WizardPage.onLoad (clrWizardPageOnLoad)
     * event for that page.
     *
     * Note that all of this work is bypassed if the WizardPage object is already
     * the current page.
     *
     * @memberof WizardNavigationService
     */
    currentPage: WizardPage;
    /**
     * (DEPRECATED) A legacy means of setting the current page in the wizard.
     * Deprecated in 0.9.4. Accepts a WizardPage object as a parameter and then
     * tries to set that page to be the current page in the wizard.
     *
     * @param {WizardPage} page
     *
     * @memberof WizardNavigationService
     */
    setCurrentPage(page: WizardPage): void;
    /**
     *
     * @ignore
     * @private
     *
     * @memberof WizardNavigationService
     */
    private _movedToNextPage;
    /**
     * An observable used internally to alert the wizard that forward navigation
     * has occurred. It is recommended that you use the Wizard.onMoveNext
     * (clrWizardOnNext) output instead of this one.
     *
     * @readonly
     * @type {Observable<boolean>}
     * @memberof WizardNavigationService
     */
    readonly movedToNextPage: Observable<boolean>;
    /**
     *
     * @ignore
     * @private
     *
     * @memberof WizardNavigationService
     */
    private _wizardFinished;
    /**
     * An observable used internally to alert the wizard that the nav service
     * has approved completion of the wizard.
     *
     * It is recommended that you use the Wizard.wizardFinished (clrWizardOnFinish)
     * output instead of this one.
     *
     * @readonly
     * @type {Observable<boolean>}
     * @memberof WizardNavigationService
     */
    readonly wizardFinished: Observable<boolean>;
    /**
     * This is a public function that can be used to programmatically advance
     * the user to the next page.
     *
     * When invoked, this method will move the wizard to the next page after
     * successful validation. Note that this method goes through all checks
     * and event emissions as if Wizard.next(false) had been called.
     *
     * In most cases, it makes more sense to use Wizard.next(false).
     *
     * @returns {void}
     *
     * @memberof WizardNavigationService
     */
    next(): void;
    /**
     * Bypasses checks and most event emissions to force a page to navigate forward.
     *
     * Comparable to calling Wizard.next() or Wizard.forceNext().
     *
     * @memberof WizardNavigationService
     */
    forceNext(): void;
    /**
     * Accepts a button/action type as a parameter. Encapsulates all logic for
     * event emissions, state of the current page, and wizard and page level overrides.
     *
     * Avoid calling this function directly unless you really know what you're doing.
     *
     * @param {string} buttonType
     * @returns {void}
     *
     * @memberof WizardNavigationService
     */
    checkAndCommitCurrentPage(buttonType: string): void;
    /**
     * This is a public function that can be used to programmatically conclude
     * the wizard.
     *
     * When invoked, this method will  initiate the work involved with finalizing
     * and finishing the wizard workflow. Note that this method goes through all
     * checks and event emissions as if Wizard.finish(false) had been called.
     *
     * In most cases, it makes more sense to use Wizard.finish(false).
     *
     * @memberof WizardNavigationService
     */
    finish(): void;
    /**
     *
     * @ignore
     * @private
     *
     * @memberof WizardNavigationService
     */
    private _movedToPreviousPage;
    /**
     * Notifies the wizard when backwards navigation has occurred via the
     * previous button.
     *
     * @readonly
     * @type {Observable<boolean>}
     * @memberof WizardNavigationService
     */
    readonly movedToPreviousPage: Observable<boolean>;
    /**
     * Programmatically moves the wizard to the page before the current page.
     *
     * In most instances, it makes more sense to call Wizard.previous()
     * which does the same thing.
     *
     * @returns {void}
     *
     * @memberof WizardNavigationService
     */
    previous(): void;
    /**
     *
     * @ignore
     * @private
     *
     * @memberof WizardNavigationService
     */
    private _cancelWizard;
    /**
     * Notifies the wizard that a user is trying to cancel it.
     *
     * @readonly
     * @type {Observable<any>}
     * @memberof WizardNavigationService
     */
    readonly notifyWizardCancel: Observable<any>;
    /**
     * Allows a hook into the cancel workflow of the wizard from the nav service. Note that
     * this route goes through all checks and event emissions as if a cancel button had
     * been clicked.
     *
     * In most cases, users looking for a hook into the cancel routine are actually looking
     * for a way to close the wizard from their host component because they have prevented
     * the default cancel action.
     *
     * In this instance, it is recommended that you use Wizard.close() to avoid any event
     * emission loop resulting from an event handler calling back into routine that will
     * again evoke the events it handles.
     *
     * @memberof WizardNavigationService
     */
    cancel(): void;
    /**
     * A boolean flag shared across the Wizard subcomponents that follows the value
     * of the Wizard.stopCancel (clrWizardPreventDefaultCancel) input. When true, the cancel
     * routine is subverted and must be reinstated in the host component calling Wizard.close()
     * at some point.
     *
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    wizardHasAltCancel: boolean;
    /**
     * A boolean flag shared across the Wizard subcomponents that follows the value
     * of the Wizard.stopNext (clrWizardPreventDefaultNext) input. When true, the next and finish
     * routines are subverted and must be reinstated in the host component calling Wizard.next(),
     * Wizard.forceNext(), Wizard.finish(), or Wizard.forceFinish().
     *
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    wizardHasAltNext: boolean;
    /**
     * A boolean flag shared across the Wizard subcomponents that follows the value
     * of the Wizard.stopNavigation (clrWizardPreventNavigation) input. When true, all
     * navigational elements in the wizard are disabled.
     *
     * This is intended to freeze the wizard in place. Events are not fired so this is
     * not a way to implement alternate functionality for navigation.
     *
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    wizardStopNavigation: boolean;
    /**
     * A boolean flag shared with the stepnav items that prevents user clicks on
     * stepnav items from navigating the wizard.
     *
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    wizardDisableStepnav: boolean;
    /**
     * Performs all required checks to determine if a user can navigate to a page. Checking at each
     * point if a page is navigable -- completed where the page immediately after the last completed
     * page.
     *
     * Takes two parameters. The first one must be either the WizardPage object or the ID of the
     * WizardPage object that you want to make the current page.
     *
     * The second parameter is optional and is a Boolean flag for "lazy completion". What this means
     * is the Wizard will mark all pages between the current page and the page you want to navigate
     * to as completed. This is useful for informational wizards that do not require user action,
     * allowing an easy means for users to jump ahead.
     *
     * To avoid checks on navigation, use WizardPage.makeCurrent() instead.
     *
     * @param {*} pageToGoToOrId
     * @param {boolean} [lazyComplete=false]
     * @returns
     *
     * @memberof WizardNavigationService
     */
    goTo(pageToGoToOrId: any, lazyComplete?: boolean): void;
    /**
     * Accepts a range of WizardPage objects as a parameter. Performs the work of checking
     * those objects to determine if navigation can be accomplished.
     *
     * @param {WizardPage[]} pagesToCheck
     * @returns {boolean}
     *
     * @memberof WizardNavigationService
     */
    canGoTo(pagesToCheck: WizardPage[]): boolean;
    /**
     * Looks through the collection of pages to find the first one that is incomplete
     * and makes that page the current/active page.
     *
     * @memberof WizardNavigationService
     */
    setLastEnabledPageCurrent(): void;
    /**
     * Finds the first page in the collection of pages and makes that page the
     * current/active page.
     *
     * @memberof WizardNavigationService
     */
    setFirstPageCurrent(): void;
    /**
     *
     * @ignore
     * @private
     * @type {string}
     * @memberof WizardNavigationService
     */
    private _wizardGhostPageState;
    /**
     *
     * @ignore
     * @type {string}
     * @memberof WizardNavigationService
     */
    /**
     *
     * @ignore
     *
     * @memberof WizardNavigationService
     */
    wizardGhostPageState: string;
    /**
     *
     * @ignore
     * @private
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    private _hideWizardGhostPages;
    /**
     *
     * @ignore
     * @type {boolean}
     * @memberof WizardNavigationService
     */
    /**
     *
     * @ignore
     *
     * @memberof WizardNavigationService
     */
    hideWizardGhostPages: boolean;
    /**
     * Updates the stepnav on the left side of the wizard when pages are dynamically
     * added or removed from the collection of pages.
     *
     * @memberof WizardNavigationService
     */
    updateNavigation(): void;
}
