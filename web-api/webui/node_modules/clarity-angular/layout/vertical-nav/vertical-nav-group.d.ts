/**
 * Copyright (c) 2016-2017 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
import { AnimationEvent } from "@angular/animations";
import { AfterContentInit, EventEmitter, OnDestroy } from "@angular/core";
import { Expand } from "../../utils/expand/providers/expand";
import { VerticalNavGroupRegistrationService } from "./providers/vertical-nav-group-registration.service";
import { VerticalNavGroupService } from "./providers/vertical-nav-group.service";
import { VerticalNavService } from "./providers/vertical-nav.service";
export declare class VerticalNavGroup implements AfterContentInit, OnDestroy {
    private _itemExpand;
    private _navGroupRegistrationService;
    private _navGroupService;
    private _navService;
    constructor(_itemExpand: Expand, _navGroupRegistrationService: VerticalNavGroupRegistrationService, _navGroupService: VerticalNavGroupService, _navService: VerticalNavService);
    private wasExpanded;
    expanded: boolean;
    userExpandedInput: boolean;
    expandedChange: EventEmitter<boolean>;
    private _subscriptions;
    private _expandAnimationState;
    expandGroup(): void;
    collapseGroup(): void;
    expandAnimationDone($event: AnimationEvent): void;
    expandAnimationState: string;
    toggleExpand(): void;
    ngAfterContentInit(): void;
    ngOnDestroy(): void;
}
