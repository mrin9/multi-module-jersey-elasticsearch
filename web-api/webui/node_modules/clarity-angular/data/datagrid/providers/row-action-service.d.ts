export declare class RowActionService {
    private actionableCount;
    register(): void;
    unregister(): void;
    /**
     * false means no rows with action
     */
    readonly hasActionableRow: boolean;
}
