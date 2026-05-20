import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EntityDeleteComponent } from './entity-delete.component';

const entityDeleteRoute: Route = {
  path: 'services/entity/d/id/:id',
  component: EntityDeleteComponent,
  title: 'services.entity.title',
  canActivate: [UserRouteAccessService],
};

export default entityDeleteRoute;
