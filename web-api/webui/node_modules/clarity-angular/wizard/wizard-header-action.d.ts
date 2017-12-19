import { EventEmitter } from "@angular/core";
export declare class WizardHeaderAction {
    title: string;
    _id: string;
    readonly id: string;
    disabled: boolean;
    headerActionClicked: EventEmitter<string>;
    click(): void;
}
