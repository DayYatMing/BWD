import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import {LsiodfportData} from "./lsiodfport.model";

@Injectable({ providedIn: 'root' })
export class LsiodfportService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/correlation/lsiodf');

  findAllData(): Observable<HttpResponse<LsiodfportData>> {
    return this.http.get<LsiodfportData>(this.resourceUrl, { observe: 'response' });
  }
}
