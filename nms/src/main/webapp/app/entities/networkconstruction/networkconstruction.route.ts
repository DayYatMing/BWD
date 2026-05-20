import { Routes } from '@angular/router';
import siteRoute from './site/site.route';
import segmentRoute from "./segment/segment.route";
import backhaulRoute from "./backhaul/backhaul.route";
import offnetRoute from "./offnet/offnet.route";
import routeRoute from "./route/route.route";
import roomlocationRoute from "./roomlocation/roomlocation.route";
import cardRoute from "./card/card.route";
import portRoute from "./port/port.route";

const routes: Routes = [siteRoute, segmentRoute, backhaulRoute, offnetRoute, routeRoute, roomlocationRoute, cardRoute, portRoute];

export default routes;
