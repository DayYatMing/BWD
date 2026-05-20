import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {Roomlocation} from './roomlocation.model'
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { createRequestOption } from '../../../core/request/request-util';

export type EntityResponseType = HttpResponse<Roomlocation>;

@Injectable({ providedIn: 'root' })
export class RoomlocationService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/roomlocations');
  protected resourceRoomlocationUrl = this.applicationConfigService.getEndpointFor('api/roomlocation');

  query(req?: any): Observable<HttpResponse<Roomlocation[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<Roomlocation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: HttpResponse<Roomlocation[]>) => this.convertArrayResponse(res)));
  }

  private convertArrayResponse(res: HttpResponse<Roomlocation[]>): HttpResponse<Roomlocation[]> {
    let jsonResponse: Roomlocation[] | null = res.body;
    const body: Roomlocation[] = [];
    if (jsonResponse != null) {
      for (let i = 0; i < jsonResponse.length; i++) {
        body.push(this.convertItemFromServer(jsonResponse[i]));
      }
    }
    return res.clone({ body });
  }

  private convertItemFromServer(roomlocation: Roomlocation | null): Roomlocation {
    const copy: Roomlocation = Object.assign({}, roomlocation);
    return copy;
  }

  create(roomlocation: Roomlocation): Observable<EntityResponseType> {
    const copy = this.convert(roomlocation);
    return this.http.post<Roomlocation>(this.resourceUrl, copy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Roomlocation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: Roomlocation = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  update(roomlocation: Roomlocation): Observable<EntityResponseType> {
    const copy = this.convert(roomlocation);
    return this.http.put<Roomlocation>(this.resourceUrl, copy, { observe: 'response' });
  }

  queryRoomlocationCount(roomlocationname?: any): Observable<HttpResponse<number>> {
    return this.http.get<number>(`${this.resourceRoomlocationUrl}/${roomlocationname}`, { observe: 'response' });
  }

  delete(id: any): Observable<HttpResponse<any>> {
    var encodedVal = btoa(`${id}`);
    return this.http.delete<any>(`${this.resourceUrl}/${encodedVal}`, { observe: 'response' });
  }

  /**
   * Convert a returned JSON object to Roomlocation.
   */
  /**
   * Convert a Customer to a JSON which can be sent to the server.
   */
  private convert(roomlocation: Roomlocation): Roomlocation {
    const copy: Roomlocation = Object.assign({}, roomlocation);
    return copy;
  }
}
