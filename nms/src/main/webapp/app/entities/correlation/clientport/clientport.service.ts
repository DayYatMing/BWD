import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import {ClientportData} from "./clientport.model";

@Injectable({ providedIn: 'root' })
export class ClientportService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/correlation/clientport');

  findAllData(): Observable<HttpResponse<ClientportData>> {
    return this.http.get<ClientportData>(this.resourceUrl, { observe: 'response' });
  }
}
