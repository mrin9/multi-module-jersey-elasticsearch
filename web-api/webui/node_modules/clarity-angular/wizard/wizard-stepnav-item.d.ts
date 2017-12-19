import { PageCollectionService } from "./providers/page-collection";
import { WizardNavigationService } from "./providers/wizard-navigation";
import { WizardPage } from "./wizard-page";
export declare class WizardStepnavItem {
    navService: WizardNavigationService;
    pageCollection: PageCollectionService;
    page: WizardPage;
    constructor(navService: WizardNavigationService, pageCollection: PageCollectionService);
    private pageGuard();
    readonly id: string;
    readonly isDisabled: boolean;
    readonly isCurrent: boolean;
    readonly isComplete: boolean;
    readonly canNavigate: boolean;
    click(): void;
}
