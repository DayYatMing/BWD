import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import {DaccorrelationData} from "./daccorrelation.model";

@Injectable({ providedIn: 'root' })
export class DaccorrelationService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/correlation/daccorrelation');

  findAllData(): Observable<HttpResponse<DaccorrelationData>> {
    return this.http.get<DaccorrelationData>(this.resourceUrl, { observe: 'response' });
  }

  updateData(dacData: DaccorrelationData):Observable<HttpResponse<void>> {
    return this.http.put<void>(this.resourceUrl, dacData, { observe: 'response' });
  }

  insertData(dacData: DaccorrelationData):Observable<HttpResponse<void>> {
    return this.http.post<void>(this.resourceUrl, dacData, { observe: 'response' });
  }
}
