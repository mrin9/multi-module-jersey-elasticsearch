import { Observable } from "rxjs/Observable";
export declare class ButtonHubService {
    buttonsReady: boolean;
    private _previousBtnClicked;
    readonly previousBtnClicked: Observable<any>;
    private _nextBtnClicked;
    readonly nextBtnClicked: Observable<any>;
    private _dangerBtnClicked;
    readonly dangerBtnClicked: Observable<any>;
    private _cancelBtnClicked;
    readonly cancelBtnClicked: Observable<any>;
    private _finishBtnClicked;
    readonly finishBtnClicked: Observable<any>;
    private _customBtnClicked;
    readonly customBtnClicked: Observable<any>;
    buttonClicked(buttonType: string): void;
}
