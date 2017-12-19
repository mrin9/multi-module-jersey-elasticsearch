import { EventEmitter, OnDestroy } from "@angular/core";
import { Expand } from "../../utils/expand/providers/expand";
import { AbstractTreeSelection } from "./abstract-tree-selection";
import { TreeSelectionService } from "./providers/tree-selection.service";
export declare class TreeNode extends AbstractTreeSelection implements OnDestroy {
    nodeExpand: Expand;
    parent: TreeNode;
    treeSelectionService: TreeSelectionService;
    constructor(nodeExpand: Expand, parent: TreeNode, treeSelectionService: TreeSelectionService);
    private _children;
    readonly children: TreeNode[];
    checkIfChildNodeRegistered(node: TreeNode): boolean;
    register(node: TreeNode): void;
    unregister(node: TreeNode): void;
    activateSelection(): void;
    nodeSelected: boolean;
    nodeSelectedChange: EventEmitter<boolean>;
    selectedChanged(): void;
    readonly selectable: boolean;
    nodeIndeterminate: boolean;
    nodeIndeterminateChanged: EventEmitter<boolean>;
    indeterminateChanged(): void;
    toggleExpand(): void;
    readonly caretDirection: string;
    expanded: boolean;
    readonly state: string;
    ngOnDestroy(): void;
}
