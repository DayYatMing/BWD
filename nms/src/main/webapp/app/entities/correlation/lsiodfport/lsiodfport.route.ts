import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import {LsiodfportComponent} from "./lsiodfport.component";

const LsiodfportRoute: Route = {
  path: 'lsiodfport',
  component: LsiodfportComponent,
  title: 'entity.correlation.lsiodfport.title',
  canActivate: [UserRouteAccessService],
};

export default LsiodfportRoute;
