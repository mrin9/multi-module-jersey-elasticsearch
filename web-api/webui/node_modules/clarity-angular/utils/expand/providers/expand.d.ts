import { Observable } from "rxjs/Observable";
import { LoadingListener } from "../../../utils/loading/loading-listener";
export declare class Expand implements LoadingListener {
    expandable: number;
    replace: boolean;
    private _loading;
    loading: boolean;
    private _expanded;
    expanded: boolean;
    private _animate;
    readonly animate: Observable<boolean>;
    private _expandChange;
    readonly expandChange: Observable<boolean>;
    startLoading(): void;
    doneLoading(): void;
}
