import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TicketManagementRatioComponent } from './ticket-management-ratio.component';

const ticketManagementRatioRoute: Route = {
  path: 'entities/ticket-management-r',
  component: TicketManagementRatioComponent,
  title: 'entity.ticketManagement.title',
  canActivate: [UserRouteAccessService],
};

export default ticketManagementRatioRoute;
