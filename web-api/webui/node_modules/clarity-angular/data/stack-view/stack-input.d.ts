import { StackControl } from "./stack-control";
import { StackView } from "./stack-view";
export declare class StackInput extends StackControl {
    stackView: StackView;
    type: string;
    constructor(stackView: StackView);
}
