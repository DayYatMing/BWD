import { Route } from '@angular/router';
import { SegmentComponent } from './segment.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const segmentRoute: Route = {
  path: 'segment',
  component: SegmentComponent,
  title: 'entity.network-construction.segment.title',
  canActivate: [UserRouteAccessService],
};

export default segmentRoute;
