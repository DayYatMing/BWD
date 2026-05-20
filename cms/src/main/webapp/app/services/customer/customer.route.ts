import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomerComponent } from './customer.component';

const customerRoute: Route = {
  path: 'services/customer',
  component: CustomerComponent,
  title: 'services.customer.title',
  canActivate: [UserRouteAccessService],
};

export default customerRoute;
