import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NrmsSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import { PanelModule, TabViewModule} from 'primeng/primeng';
import {ServiceCorelationModule} from './servicecorelation/servicecorelation.module';
import { DashboardLayoutModule} from '@syncfusion/ej2-angular-layouts';
import {MapsModule} from '@syncfusion/ej2-angular-maps';
import {CommonModule} from "@angular/common";
import {TabModule} from "@syncfusion/ej2-angular-navigations";
import {SwitchModule} from "@syncfusion/ej2-angular-buttons";
import {PivotViewModule} from "@syncfusion/ej2-angular-pivotview";
import {CorrelationModule} from "./correlation/correlation.module";
import {AllcorrelationModule} from "./allcorelation/allcorrelation.module";
import {ProvisioningModule} from "./provisioning/provisioning.module";
import {OrderManagementModule} from "./ordermanagement/ordermanagement.module";
import {AnalyticsModule} from "./analytics/analytics.module";
import {NetworkModule} from "./networkconstruction/network.module";
import {ChartAllModule} from "@syncfusion/ej2-angular-charts";
import {NrmsSiteModule} from "../entities/site/site.module";
import {ServiceeventsModule} from "./serviceevents/serviceevents.module";
import {CapacityChartModule} from "./chart/chart.module";
import {WorldMapModule} from "../shared/worldmap/worldmap.module";
import {WorldmapPopupModule} from "../shared/worldmap-popup/worldmap-popup.module";
import {GridAllModule} from "@syncfusion/ej2-angular-grids";
import {NrmsTimelineModule} from "../entities/timeline/timeline.module";
import {EventsModule} from "./events/events.module";
import {TicketAnalyticsModule} from "./ticketanalytics/ticketanalytics.module";
import {NetworkdiagramModule} from "./network/networkdiagram.module";
import {AllticketsModule} from "./alltickets/alltickets.module";
import {AllticketanalyticsModule} from "./allticketanalytics/allticketanalytics.module";
import {AllTicketsDashbaordModule} from "./allticketsdashboard";
import {ChatbotModule} from "./chatbot/chatbot.module";

@NgModule({
    imports: [
        CommonModule,
        NrmsSharedModule,
        TabViewModule,
        SwitchModule,
        PivotViewModule,
        ServiceCorelationModule,
        CapacityChartModule,
        NrmsTimelineModule,
        CorrelationModule,
        EventsModule,
        NetworkdiagramModule,
        ServiceeventsModule,
        TicketAnalyticsModule,
        AllticketsModule,
        AllcorrelationModule,
        NetworkModule,
        OrderManagementModule,
        DashboardLayoutModule,
        PanelModule,
        AnalyticsModule,
        MapsModule,
        TabModule,
        AllticketanalyticsModule,
        AllTicketsDashbaordModule,
        ChartAllModule,
        RouterModule.forChild([HOME_ROUTE]),
        ProvisioningModule,
        NrmsSiteModule,
        WorldMapModule,
        WorldmapPopupModule,
        GridAllModule,
        ChatbotModule,
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [],
    providers: [],
    exports: [
        HomeComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NrmsHomeModule {}
