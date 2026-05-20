import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EntityCreateComponent } from './entity-create.component';

const entityCreateRoute: Route = {
  path: 'services/entity/c',
  component: EntityCreateComponent,
  title: 'services.customer.title',
  canActivate: [UserRouteAccessService],
};

export default entityCreateRoute;
