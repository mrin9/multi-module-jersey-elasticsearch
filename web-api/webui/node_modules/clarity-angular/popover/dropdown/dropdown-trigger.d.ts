import { IfOpenService } from "../../utils/conditional/if-open.service";
import { Dropdown } from "./dropdown";
export declare class DropdownTrigger {
    private dropdown;
    private ifOpenService;
    private isRootLevelToggle;
    constructor(dropdown: Dropdown, ifOpenService: IfOpenService);
    readonly active: boolean;
    onDropdownTriggerClick(event: any): void;
}
