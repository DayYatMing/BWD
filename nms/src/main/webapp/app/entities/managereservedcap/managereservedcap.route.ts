
import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ManagereservedcapComponent } from './managereservedcap.component';

const ManagereservedcapRoute: Route = {
  path: 'managereservedcap',
  component: ManagereservedcapComponent,
  title: 'entity.managereservedcap.title',
  canActivate: [UserRouteAccessService],
};

export default ManagereservedcapRoute;
