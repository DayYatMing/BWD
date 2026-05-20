import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CapacityreportComponent } from './capacityreport.component';

const capacityreportRoute: Route = {
    path: 'capacityreport',
    component: CapacityreportComponent,
    title: 'entity.capacity-report.title',
    canActivate: [UserRouteAccessService],
};
export default capacityreportRoute;



