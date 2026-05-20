import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomerDeleteComponent } from './customer-delete.component';

const customerDeleteRoute: Route = {
  path: 'services/customer/d/id/:id',
  component: CustomerDeleteComponent,
  title: 'services.customer.title',
  canActivate: [UserRouteAccessService],
};

export default customerDeleteRoute;
