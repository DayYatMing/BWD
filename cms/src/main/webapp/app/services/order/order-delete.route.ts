import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderDeleteComponent } from './order-delete.component';

const OrderDeleteRoute: Route = {
  path: 'services/order/d/id/:id',
  component: OrderDeleteComponent,
  title: 'services.order.title',
  canActivate: [UserRouteAccessService],
};

export default OrderDeleteRoute;
