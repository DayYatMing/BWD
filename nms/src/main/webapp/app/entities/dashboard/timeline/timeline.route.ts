import { Route } from '@angular/router';
import { TimelineComponent } from './timeline.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const timelineRoute: Route = {
  path: 'timeline',
  component: TimelineComponent,
  title: 'entity.dashboard.timeline.title',
  canActivate: [UserRouteAccessService],
};

export default timelineRoute;
