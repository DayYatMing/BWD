import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Stats, StatsInput } from './stats.model';

@Injectable({ providedIn: 'root' })
export class StatsService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stats');

  find(statsInput: StatsInput): Observable<HttpResponse<Stats[]>> {
    const headers = new HttpHeaders({
      customer: String(statsInput.customer ?? ''),
      serviceId: String(statsInput.serviceId ?? ''),
      pmSource: String(statsInput.pmSource ?? ''),
      dtFr: String(statsInput.dtFr ?? ''),
      dtTo: String(statsInput.dtTo ?? ''),
    });

    return this.http.get<Stats[]>(this.resourceUrl, { headers, observe: 'response' });
  }

  findServices(cusShortName: string): Observable<HttpResponse<string[]>> {
    const headers = new HttpHeaders({
      customer: String(cusShortName ?? ''),
    });
    return this.http.get<string[]>(this.resourceUrl + '/services', { headers, observe: 'response' });
  }

  findSources(serviceId: string): Observable<HttpResponse<string[]>> {
    const headers = new HttpHeaders({
      serviceId: String(serviceId ?? ''),
    });
    return this.http.get<string[]>(this.resourceUrl + '/sources', { headers, observe: 'response' });
  }
}
