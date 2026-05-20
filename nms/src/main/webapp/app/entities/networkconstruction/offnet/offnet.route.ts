import { Route } from '@angular/router';
import { OffnetComponent } from './offnet.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const offnetRoute: Route = {
  path: 'offnet',
  component: OffnetComponent,
  title: 'entity.network-construction.offnet.title',
  canActivate: [UserRouteAccessService],
};

export default offnetRoute;
