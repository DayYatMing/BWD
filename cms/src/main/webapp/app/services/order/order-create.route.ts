import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderCreateComponent } from './order-create.component';

const orderCreateRoute: Route = {
  path: 'services/order/c',
  component: OrderCreateComponent,
  title: 'services.order.title',
  canActivate: [UserRouteAccessService],
};

export default orderCreateRoute;
