import { Route } from '@angular/router';
import { CardComponent } from './card.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const cardRoute: Route = {
  path: 'card',
  component: CardComponent,
  title: 'entity.network-construction.card.title',
  canActivate: [UserRouteAccessService],
};

export default cardRoute;
