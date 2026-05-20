import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import {DaccorrelationComponent} from "./daccorrelation.component";

const DaccorrelationRoute: Route = {
  path: 'daccorrelation',
  component: DaccorrelationComponent,
  title: 'entity.correlation.daccorrelation.title',
  canActivate: [UserRouteAccessService],
};

export default DaccorrelationRoute;
