import { Tab } from "./tab";
export declare class TabsService {
    private _children;
    register(tab: Tab): void;
    readonly children: Tab[];
    readonly activeTab: Tab;
    readonly overflowTabs: Tab[];
    unregister(tab: Tab): void;
}
