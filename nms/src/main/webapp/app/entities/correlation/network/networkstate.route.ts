import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import {NetworkstateComponent} from "./networkstate.component";

const NetworkstateRoute: Route = {
  path: 'networkstate',
  component: NetworkstateComponent,
  title: 'entity.correlation.networkstate.title',
  canActivate: [UserRouteAccessService],
};

export default NetworkstateRoute;
