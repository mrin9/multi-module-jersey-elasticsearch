import { OnDestroy } from "@angular/core";
import { IfOpenService } from "../../utils/conditional/if-open.service";
import { RootDropdownService } from "./providers/dropdown.service";
export declare class Dropdown implements OnDestroy {
    parent: Dropdown;
    ifOpenService: IfOpenService;
    private _subscription;
    constructor(parent: Dropdown, ifOpenService: IfOpenService, dropdownService: RootDropdownService);
    isMenuClosable: boolean;
    ngOnDestroy(): void;
}
