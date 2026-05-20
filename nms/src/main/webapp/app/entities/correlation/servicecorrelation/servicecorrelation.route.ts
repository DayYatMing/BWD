import { Route } from '@angular/router';
import { ServicecorrelationComponent } from './servicecorrelation.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const ServicecorrelationRoute: Route = {
  path: 'servicecorrelation',
  component: ServicecorrelationComponent,
  title: 'entity.correlation.servicecorrelation.title',
  canActivate: [UserRouteAccessService],
};

export default ServicecorrelationRoute;
