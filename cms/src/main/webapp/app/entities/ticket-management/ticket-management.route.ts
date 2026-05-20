import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TicketManagementComponent } from './ticket-management.component';

const ticketManagementRoute: Route = {
  path: 'entities/ticket-management',
  component: TicketManagementComponent,
  title: 'entity.ticketManagement.title',
  canActivate: [UserRouteAccessService],
};

export default ticketManagementRoute;
