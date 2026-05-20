import { Routes } from '@angular/router';
import customerRoute from './customer/customer.route';
import customerCreateRoute from './customer/customer-create.route';
import customerEditRoute from './customer/customer-edit.route';
import customerDeleteRoute from './customer/customer-delete.route';
import entityRoute from './entity/entity.route';
import entityCreateRoute from './entity/entity-create.route';
import entityEditRoute from './entity/entity-edit.route';
import entityDeleteRoute from './entity/entity-delete.route';
import orderRoute from './order/order.route';
import orderCreateRoute from './order/order-create.route';
import orderEditRoute from './order/order-edit.route';
import orderDeleteRoute from './order/order-delete.route';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'cmsApp.adminAuthority.home.title' },
    loadChildren: () => import('../entities/admin/authority/authority.routes'),
  },

  customerRoute,
  customerCreateRoute,
  customerEditRoute,
  customerDeleteRoute,
  entityRoute,
  entityCreateRoute,
  entityEditRoute,
  entityDeleteRoute,
  orderRoute,
  orderCreateRoute,
  orderEditRoute,
  orderDeleteRoute,
];

export default routes;
