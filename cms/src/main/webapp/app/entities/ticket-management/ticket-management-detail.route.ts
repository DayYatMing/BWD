import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TicketManagementDetailComponent } from './ticket-management-detail.component';

const ticketManagementDetailsRoute: Route = {
  path: 'entities/ticket-management/id/:id',
  component: TicketManagementDetailComponent,
  title: 'entity.ticketManagement.title',
  canActivate: [UserRouteAccessService],
};

export default ticketManagementDetailsRoute;
