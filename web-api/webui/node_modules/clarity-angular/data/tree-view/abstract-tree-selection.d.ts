export declare abstract class AbstractTreeSelection {
    parent: AbstractTreeSelection;
    constructor(parent: AbstractTreeSelection);
    readonly abstract children: AbstractTreeSelection[];
    abstract selectedChanged(): void;
    abstract indeterminateChanged(): void;
    private _selected;
    private _indeterminate;
    selected: boolean;
    indeterminate: boolean;
    childChanged(): void;
    parentChanged(selected: boolean): void;
}
