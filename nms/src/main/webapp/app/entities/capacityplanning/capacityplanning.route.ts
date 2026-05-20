import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CapacityplanningComponent } from './capacityplanning.component';

const capacityplanningRoute: Route = {
  path: 'capacityplanning',
  component: CapacityplanningComponent,
  title: 'entity.capacity-planning.title',
  canActivate: [UserRouteAccessService],
};

export default capacityplanningRoute;
