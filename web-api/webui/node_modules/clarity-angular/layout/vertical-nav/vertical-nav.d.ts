/**
 * Copyright (c) 2016-2017 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
import { OnDestroy } from "@angular/core";
import { VerticalNavGroupRegistrationService } from "./providers/vertical-nav-group-registration.service";
import { VerticalNavIconService } from "./providers/vertical-nav-icon.service";
import { VerticalNavService } from "./providers/vertical-nav.service";
export declare class VerticalNav implements OnDestroy {
    private _navService;
    private _navIconService;
    private _navGroupRegistrationService;
    collapsible: boolean;
    collapsed: boolean;
    private _collapsedChanged;
    readonly hasNavGroups: boolean;
    readonly hasIcons: boolean;
    private _sub;
    constructor(_navService: VerticalNavService, _navIconService: VerticalNavIconService, _navGroupRegistrationService: VerticalNavGroupRegistrationService);
    toggleByButton(): void;
    ngOnDestroy(): void;
}
