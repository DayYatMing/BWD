import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Offnet } from './offnet.model'
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { createRequestOption } from '../../../core/request/request-util';

export type EntityResponseType = HttpResponse<Offnet>;

@Injectable({ providedIn: 'root' })
export class OffnetService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/offnets');
  protected resourceOffnetUrl = this.applicationConfigService.getEndpointFor('api/offnet');

  query(req?: any): Observable<HttpResponse<Offnet[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<Offnet[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: HttpResponse<Offnet[]>) => this.convertArrayResponse(res)));
  }

  private convertArrayResponse(res: HttpResponse<Offnet[]>): HttpResponse<Offnet[]> {
    let jsonResponse: Offnet[] | null = res.body;
    const body: Offnet[] = [];
    if (jsonResponse != null) {
      for (let i = 0; i < jsonResponse.length; i++) {
        body.push(this.convertItemFromServer(jsonResponse[i]));
      }
    }
    return res.clone({ body });
  }

  private convertItemFromServer(offnet: Offnet | null): Offnet {
    const copy: Offnet = Object.assign({}, offnet);
    return copy;
  }

  create(offnet: Offnet): Observable<EntityResponseType> {
    const copy = this.convert(offnet);
    return this.http.post<Offnet>(this.resourceUrl, copy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Offnet>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: Offnet = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  update(offnet: Offnet): Observable<EntityResponseType> {
    const copy = this.convert(offnet);
    return this.http.put<Offnet>(this.resourceUrl, copy, { observe: 'response' });
  }

  queryOffnetCount(offnetname?: any): Observable<HttpResponse<number>> {
    return this.http.get<number>(`${this.resourceOffnetUrl}/${offnetname}`, { observe: 'response' });
  }

  delete(id: any): Observable<HttpResponse<any>> {
    var encodedVal = btoa(`${id}`);
    return this.http.delete<any>(`${this.resourceUrl}/${encodedVal}`, { observe: 'response' });
  }

  /**
   * Convert a returned JSON object to offnet.
   */
  /**
   * Convert a Customer to a JSON which can be sent to the server.
   */
  private convert(offnet: Offnet): Offnet {
    const copy: Offnet = Object.assign({}, offnet);
    return copy;
  }
}
