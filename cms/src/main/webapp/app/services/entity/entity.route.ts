import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EntityComponent } from './entity.component';

const entityRoute: Route = {
  path: 'services/entity',
  component: EntityComponent,
  title: 'services.entity.title',
  canActivate: [UserRouteAccessService],
};

export default entityRoute;
