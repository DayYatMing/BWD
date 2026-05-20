import { Route } from '@angular/router';
import { BackhaulComponent } from './backhaul.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const backhaulRoute: Route = {
  path: 'backhaul',
  component: BackhaulComponent,
  title: 'entity.network-construction.backhaul.title',
  canActivate: [UserRouteAccessService],
};

export default backhaulRoute;
