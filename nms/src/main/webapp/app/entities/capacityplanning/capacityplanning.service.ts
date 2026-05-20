import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import {capplanModel} from "./capacityplanning.model";

@Injectable({ providedIn: 'root' })
export class CapPlanningServ {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/capplanning');

  findCapplanning(): Observable<HttpResponse<capplanModel[]>> {
    return this.http.get<capplanModel[]>(`${this.resourceUrl}/partialcapplanningdata`, { observe: 'response' })
  }

}
