import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EntityEditComponent } from './entity-edit.component';

const EntityEditRoute: Route = {
  path: 'services/entity/e/id/:id',
  component: EntityEditComponent,
  title: 'services.entity.title',
  canActivate: [UserRouteAccessService],
};

export default EntityEditRoute;
