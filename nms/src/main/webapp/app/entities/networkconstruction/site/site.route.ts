import { Route } from '@angular/router';
import { SiteComponent } from './site.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const siteRoute: Route = {
  path: 'site',
  component: SiteComponent,
  title: 'entity.network-construction.site.title',
  canActivate: [UserRouteAccessService],
};

export default siteRoute;
