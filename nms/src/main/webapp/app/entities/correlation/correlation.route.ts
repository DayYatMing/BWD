import { Routes } from '@angular/router';
import OdfmmrportRoute from "./odfmmrport/odfmmrport.route";
import LsiodfportRoute from "./lsiodfport/lsiodfport.route";
import ClientportRoute from "./clientport/clientport.route";
import DaccorrelationRoute from "./daccorrelation/daccorrelation.route";
import NetworkstateRoute from "./network/networkstate.route";
import OperationalstateRoute from "./operationalstate/operationalstate.route";
import ServicecorrelationRoute from "./servicecorrelation/servicecorrelation.route";

const routes: Routes = [OdfmmrportRoute, LsiodfportRoute, ClientportRoute, DaccorrelationRoute, NetworkstateRoute, OperationalstateRoute, ServicecorrelationRoute ];

export default routes;
