import { ElementRef } from "@angular/core";
import { Dropdown } from "./dropdown";
import { RootDropdownService } from "./providers/dropdown.service";
export declare class DropdownItem {
    private dropdown;
    private el;
    private _dropdownService;
    constructor(dropdown: Dropdown, el: ElementRef, _dropdownService: RootDropdownService);
    onDropdownItemClick(): void;
}
