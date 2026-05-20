import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TicketManagementCreateComponent } from './ticket-management-create.component';

const ticketManagementCreateRoute: Route = {
  path: 'entities/ticket-management/c',
  component: TicketManagementCreateComponent,
  title: 'entity.ticketManagement.title',
  canActivate: [UserRouteAccessService],
};

export default ticketManagementCreateRoute;
