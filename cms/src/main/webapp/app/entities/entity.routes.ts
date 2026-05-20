import { Routes } from '@angular/router';
import ticketManagementRoute from './ticket-management/ticket-management.route';
import ticketManagementDetailRoute from './ticket-management/ticket-management-detail.route';
import ticketManagementCreateRoute from './ticket-management/ticket-management-create.route';
import serviceRoute from './service/service.route';
import ticketManagementRatioRoute from './ticket-management/ticket-management-ratio.route';
import statsRoute from './stats/stats.route';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'cmsApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */

  ticketManagementRoute,
  ticketManagementDetailRoute,
  ticketManagementCreateRoute,
  ticketManagementRatioRoute,
  serviceRoute,
  statsRoute,
];

export default routes;
