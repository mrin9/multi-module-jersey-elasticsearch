import { OnDestroy } from "@angular/core";
import { LoadingListener } from "./loading-listener";
export declare class Loading implements OnDestroy {
    private listener;
    constructor(listener: LoadingListener);
    private _loading;
    loading: boolean;
    ngOnDestroy(): void;
}
