import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderEditComponent } from './order-edit.component';

const OrderEditRoute: Route = {
  path: 'services/order/e/id/:id',
  component: OrderEditComponent,
  title: 'services.order.title',
  canActivate: [UserRouteAccessService],
};

export default OrderEditRoute;
