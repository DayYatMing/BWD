import { Routes } from '@angular/router';

import dashboardRoute from './dashboard/dashboard.route';
import pmRoute from './pm/pm.route';
import capacityplanningRoute from './capacityplanning/capacityplanning.route';
import ManagereservedcapRoute from "./managereservedcap/managereservedcap.route";

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'nmsApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */

  {
    path: 'networkconstruction',
    loadChildren: () => import('./networkconstruction/networkconstruction.route'),
  },
  {
    path: 'correlation',
    loadChildren: () => import('./correlation/correlation.route'),
  },

  dashboardRoute,
  pmRoute,
  capacityplanningRoute,
  ManagereservedcapRoute
];

export default routes;
