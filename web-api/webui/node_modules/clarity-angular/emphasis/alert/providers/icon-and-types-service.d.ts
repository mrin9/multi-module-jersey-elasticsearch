import { AlertInfoObject } from "../utils/alert-info-object";
export declare class AlertIconAndTypesService {
    private defaultIconShape;
    private _alertIconShape;
    private _alertType;
    alertType: string;
    alertIconShape: string;
    iconInfoFromType(type: string, classOrShape?: string): AlertInfoObject;
}
