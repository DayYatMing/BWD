import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ServiceComponent } from './service.component';

const serviceRoute: Route = {
  path: 'entities/service',
  component: ServiceComponent,
  title: 'entity.service.title',
  canActivate: [UserRouteAccessService],
};

export default serviceRoute;
