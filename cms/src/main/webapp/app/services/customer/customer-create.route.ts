import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomerCreateComponent } from './customer-create.component';

const customerCreateRoute: Route = {
  path: 'services/customer/c',
  component: CustomerCreateComponent,
  title: 'services.customer.title',
  canActivate: [UserRouteAccessService],
};

export default customerCreateRoute;
