import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Backhaul } from './backhaul.model'
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { createRequestOption } from '../../../core/request/request-util';

export type EntityResponseType = HttpResponse<Backhaul>;

@Injectable({ providedIn: 'root' })
export class BackhaulService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/backhauls');
  protected resourceBackhaulUrl = this.applicationConfigService.getEndpointFor('api/backhaul');

  query(req?: any): Observable<HttpResponse<Backhaul[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<Backhaul[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: HttpResponse<Backhaul[]>) => this.convertArrayResponse(res)));
  }

  private convertArrayResponse(res: HttpResponse<Backhaul[]>): HttpResponse<Backhaul[]> {
    let jsonResponse: Backhaul[] | null = res.body;
    const body: Backhaul[] = [];
    if (jsonResponse != null) {
      for (let i = 0; i < jsonResponse.length; i++) {
        body.push(this.convertItemFromServer(jsonResponse[i]));
      }
    }
    return res.clone({ body });
  }

  private convertItemFromServer(backhaul: Backhaul | null): Backhaul {
    const copy: Backhaul = Object.assign({}, backhaul);
    return copy;
  }

  create(backhaul: Backhaul): Observable<EntityResponseType> {
    const copy = this.convert(backhaul);
    return this.http.post<Backhaul>(this.resourceUrl, copy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Backhaul>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: Backhaul = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  update(backhaul: Backhaul): Observable<EntityResponseType> {
    const copy = this.convert(backhaul);
    return this.http.put<Backhaul>(this.resourceUrl, copy, { observe: 'response' });
  }

  queryBackhaulCount(backhaulname?: any): Observable<HttpResponse<number>> {
    return this.http.get<number>(`${this.resourceBackhaulUrl}/${backhaulname}`, { observe: 'response' });
  }

  delete(id: any): Observable<HttpResponse<any>> {
    var encodedVal = btoa(`${id}`);
    return this.http.delete<any>(`${this.resourceUrl}/${encodedVal}`, { observe: 'response' });
  }

  /**
   * Convert a returned JSON object to Backhaul.
   */
  /**
   * Convert a Customer to a JSON which can be sent to the server.
   */
  private convert(backhaul: Backhaul): Backhaul {
    const copy: Backhaul = Object.assign({}, backhaul);
    return copy;
  }
}
