import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomerEditComponent } from './customer-edit.component';

const customerEditRoute: Route = {
  path: 'services/customer/e/id/:id',
  component: CustomerEditComponent,
  title: 'services.customer.title',
  canActivate: [UserRouteAccessService],
};

export default customerEditRoute;
