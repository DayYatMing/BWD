import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {Port} from './port.model'
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { createRequestOption } from '../../../core/request/request-util';

export type EntityResponseType = HttpResponse<Port>;

@Injectable({ providedIn: 'root' })
export class PortService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ports');
  protected resourcePortUrl = this.applicationConfigService.getEndpointFor('api/port');

  query(req?: any): Observable<HttpResponse<Port[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<Port[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: HttpResponse<Port[]>) => this.convertArrayResponse(res)));
  }

  private convertArrayResponse(res: HttpResponse<Port[]>): HttpResponse<Port[]> {
    let jsonResponse: Port[] | null = res.body;
    const body: Port[] = [];
    if (jsonResponse != null) {
      for (let i = 0; i < jsonResponse.length; i++) {
        body.push(this.convertItemFromServer(jsonResponse[i]));
      }
    }
    return res.clone({ body });
  }

  private convertItemFromServer(port: Port | null): Port {
    const copy: Port = Object.assign({}, port);
    return copy;
  }

  create(port: Port): Observable<EntityResponseType> {
    const copy = this.convert(port);
    return this.http.post<Port>(this.resourceUrl, copy, { observe: 'response' });
  }

  createBulk(formData: FormData): Observable<EntityResponseType> {
    return this.http.post<Port>(this.resourceUrl+"/bulk", formData, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Port>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: Port = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  update(port: Port): Observable<EntityResponseType> {
    const copy = this.convert(port);
    return this.http.put<Port>(this.resourceUrl, copy, { observe: 'response' });
  }

  queryPortCount(portname?: any): Observable<HttpResponse<number>> {
    return this.http.get<number>(`${this.resourcePortUrl}/${portname}`, { observe: 'response' });
  }

  delete(id: any): Observable<HttpResponse<any>> {
    var encodedVal = btoa(`${id}`);
    return this.http.delete<any>(`${this.resourceUrl}/${encodedVal}`, { observe: 'response' });
  }

  /**
   * Convert a returned JSON object to Port.
   */
  /**
   * Convert a Customer to a JSON which can be sent to the server.
   */
  private convert(port: Port): Port {
    const copy: Port = Object.assign({}, port);
    return copy;
  }
}
