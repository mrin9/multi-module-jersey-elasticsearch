import { IfOpenService } from "../../utils/conditional/if-open.service";
export declare class TooltipTrigger {
    private ifOpenService;
    constructor(ifOpenService: IfOpenService);
    showTooltip(): void;
    hideTooltip(): void;
}
