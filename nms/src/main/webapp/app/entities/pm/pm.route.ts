import { Route } from '@angular/router';
import { PMComponent } from './pm.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const pmRoute: Route = {
  path: 'pm',
  component: PMComponent,
  title: 'entity.pm.title',
  canActivate: [UserRouteAccessService],
};

export default pmRoute;
 
