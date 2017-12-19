import { LoadingListener } from "../../utils/loading/loading-listener";
export declare class LoadingButton implements LoadingListener {
    loading: Boolean;
    startLoading(): void;
    doneLoading(): void;
}
