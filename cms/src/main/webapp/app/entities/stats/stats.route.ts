import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StatsComponent } from './stats.component';

const statsRoute: Route = {
  path: 'entities/stats',
  component: StatsComponent,
  title: 'entity.stats.title',
  canActivate: [UserRouteAccessService],
};

export default statsRoute;
