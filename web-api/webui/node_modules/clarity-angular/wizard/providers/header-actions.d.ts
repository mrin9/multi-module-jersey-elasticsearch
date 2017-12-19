import { QueryList } from "@angular/core";
import { WizardHeaderAction } from "../wizard-header-action";
import { WizardNavigationService } from "./wizard-navigation";
export declare class HeaderActionService {
    navService: WizardNavigationService;
    constructor(navService: WizardNavigationService);
    wizardHeaderActions: QueryList<WizardHeaderAction>;
    readonly wizardHasHeaderActions: boolean;
    readonly currentPageHasHeaderActions: boolean;
    readonly showWizardHeaderActions: boolean;
    readonly displayHeaderActionsWrapper: boolean;
}
