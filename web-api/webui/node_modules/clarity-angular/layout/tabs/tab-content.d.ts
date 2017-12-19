import { TemplateRef } from "@angular/core";
import { IfActiveService } from "../../utils/conditional/if-active.service";
import { AriaService } from "./aria-service";
export declare class TabContent {
    ifActiveService: IfActiveService;
    id: number;
    private ariaService;
    templateRef: TemplateRef<TabContent>;
    constructor(ifActiveService: IfActiveService, id: number, ariaService: AriaService);
    readonly ariaLabelledBy: string;
    tabContentId: string;
    readonly active: boolean;
}
