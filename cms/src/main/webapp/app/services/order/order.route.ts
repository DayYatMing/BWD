import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderComponent } from './order.component';

const orderRoute: Route = {
  path: 'services/order',
  component: OrderComponent,
  title: 'services.order.title',
  canActivate: [UserRouteAccessService],
};

export default orderRoute;
