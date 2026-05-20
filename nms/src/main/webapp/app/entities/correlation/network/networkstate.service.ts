import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import {Networkstate} from './networkstate.model'

@Injectable({ providedIn: 'root' })
export class NetworkstateService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/correlation/networkstate');

  find(): Observable<HttpResponse<Networkstate>> {
    return this.http.get<Networkstate>(this.resourceUrl, { observe: 'response' });
  }

  upload(formData: FormData): Observable<HttpResponse<void>> {
    return this.http.post<void>(this.resourceUrl, formData, { observe: 'response' });
  }

  process(id: any, withDetails: boolean): Observable<HttpResponse<Networkstate>> {
    const params = { id: id, withDetails: withDetails };
    return this.http.post<Networkstate>(this.resourceUrl + "/update", null, { params, observe: 'response' });
  }

}
