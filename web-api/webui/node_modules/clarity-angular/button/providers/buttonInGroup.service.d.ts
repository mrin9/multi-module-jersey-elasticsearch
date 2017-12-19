import { Observable } from "rxjs/Observable";
import { Button } from "../button-group/button";
export declare class ButtonInGroupService {
    private _changes;
    readonly changes: Observable<Button>;
    updateButtonGroup(button: Button): void;
}
