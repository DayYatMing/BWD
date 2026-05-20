import { Route } from '@angular/router';
import { RoomlocationComponent } from './roomlocation.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const roomlocationRoute: Route = {
  path: 'roomlocation',
  component: RoomlocationComponent,
  title: 'entity.network-construction.roomlocation.title',
  canActivate: [UserRouteAccessService],
};

export default roomlocationRoute;
