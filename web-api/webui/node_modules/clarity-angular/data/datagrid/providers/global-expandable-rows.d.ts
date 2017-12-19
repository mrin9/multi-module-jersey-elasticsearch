export declare class ExpandableRowsCount {
    private expandableCount;
    register(): void;
    unregister(): void;
    /**
     * false means no rows with action
     */
    readonly hasExpandableRow: boolean;
}
