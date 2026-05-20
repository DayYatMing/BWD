import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import {ClientportComponent} from "./clientport.component";

const ClientportRoute: Route = {
  path: 'clientport',
  component: ClientportComponent,
  title: 'entity.correlation.clientport.title',
  canActivate: [UserRouteAccessService],
};

export default ClientportRoute;
