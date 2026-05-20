import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import {OdfmmrportComponent} from "./odfmmrport.component";

const OdfmmrportRoute: Route = {
  path: 'odfmmrport',
  component: OdfmmrportComponent,
  title: 'entity.correlation.odfmmrport.title',
  canActivate: [UserRouteAccessService],
};

export default OdfmmrportRoute;
