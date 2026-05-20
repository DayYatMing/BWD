import { Route } from '@angular/router';
import { OperationalstateComponent } from './operationalstate.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const operationalState: Route = {
  path: 'operationalstate',
  component: OperationalstateComponent,
  title: 'entity.correlation.operationalstate.title',
  canActivate: [UserRouteAccessService],
};

export default operationalState;
