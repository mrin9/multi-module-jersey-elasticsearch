import { IfActiveService } from "../../utils/conditional/if-active.service";
import { TabContent } from "./tab-content";
import { TabLinkDirective } from "./tab-link.directive";
import { TabsService } from "./tabs-service";
export declare class Tab {
    ifActiveService: IfActiveService;
    id: number;
    private tabsService;
    tabLink: TabLinkDirective;
    tabContent: TabContent;
    constructor(ifActiveService: IfActiveService, id: number, tabsService: TabsService);
    ngOnDestroy(): void;
    readonly active: boolean;
}
