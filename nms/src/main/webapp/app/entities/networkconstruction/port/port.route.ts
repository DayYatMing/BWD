import { Route } from '@angular/router';
import { PortComponent } from './port.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const portRoute: Route = {
  path: 'port',
  component: PortComponent,
  title: 'entity.network-construction.port.title',
  canActivate: [UserRouteAccessService],
};

export default portRoute;
