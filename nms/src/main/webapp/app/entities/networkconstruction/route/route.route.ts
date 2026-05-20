import { Route } from '@angular/router';
import { RouteComponent } from './route.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const routeRoute: Route = {
  path: 'route',
  component: RouteComponent,
  title: 'entity.network-construction.route.title',
  canActivate: [UserRouteAccessService],
};

export default routeRoute;
