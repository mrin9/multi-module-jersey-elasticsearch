import { EventEmitter, TemplateRef } from "@angular/core";
import { ButtonInGroupService } from "../providers/buttonInGroup.service";
export declare class Button {
    buttonInGroupService: ButtonInGroupService;
    private _enableService;
    templateRef: TemplateRef<Button>;
    constructor(buttonInGroupService: ButtonInGroupService);
    private _inMenu;
    inMenu: boolean;
    private _classNames;
    classNames: string;
    private _name;
    name: string;
    private _type;
    type: string;
    private _disabled;
    disabled: any;
    _click: EventEmitter<boolean>;
    emitClick(): void;
    ngAfterViewInit(): void;
}
