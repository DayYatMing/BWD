import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChatbotComponent } from './chatbot.component';

const chatbotRoute: Route = {
  path: 'chatbot',
  component: ChatbotComponent,
  title: 'entity.ai.chatbot.title',
  canActivate: [UserRouteAccessService],
};

export default chatbotRoute;
