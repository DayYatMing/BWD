import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DashboardComponent } from './dashboard.component';

const dashboardRoute: Route = {
  path: 'dashboard',
  component: DashboardComponent,
  title: 'entity.dashboard.title',
  canActivate: [UserRouteAccessService],
};

export default dashboardRoute;
