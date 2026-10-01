import { Route } from '@angular/router';
import {ChatbotComponent} from "./chatbot.component";


export const HOME_ROUTE: Route = {
    path: 'chatbot',
    component: ChatbotComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
