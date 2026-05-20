import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {Route} from './route.model'
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { createRequestOption } from '../../../core/request/request-util';

export type EntityResponseType = HttpResponse<Route>;

@Injectable({ providedIn: 'root' })
export class RouteService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/routes');
  protected resourceRouteUrl = this.applicationConfigService.getEndpointFor('api/route');

  query(req?: any): Observable<HttpResponse<Route[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<Route[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: HttpResponse<Route[]>) => this.convertArrayResponse(res)));
  }

  private convertArrayResponse(res: HttpResponse<Route[]>): HttpResponse<Route[]> {
    let jsonResponse: Route[] | null = res.body;
    const body: Route[] = [];
    if (jsonResponse != null) {
      for (let i = 0; i < jsonResponse.length; i++) {
        body.push(this.convertItemFromServer(jsonResponse[i]));
      }
    }
    return res.clone({ body });
  }

  private convertItemFromServer(route: Route | null): Route {
    const copy: Route = Object.assign({}, route);
    return copy;
  }

  create(route: Route): Observable<EntityResponseType> {
    const copy = this.convert(route);
    return this.http.post<Route>(this.resourceUrl, copy, { observe: 'response' }).pipe(
      map((res: EntityResponseType) => this.convertResponse(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Route>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: Route = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  update(route: Route): Observable<EntityResponseType> {
    const copy = this.convert(route);
    return this.http.put<Route>(this.resourceUrl, copy, { observe: 'response' }).pipe(
      map((res: EntityResponseType) => this.convertResponse(res)));
  }

  queryRouteCount(routename?: any): Observable<HttpResponse<number>> {
    return this.http.get<number>(`${this.resourceRouteUrl}/${routename}`, { observe: 'response' });
  }

  delete(id: any): Observable<HttpResponse<any>> {
    var encodedVal = btoa(`${id}`);
    return this.http.delete<any>(`${this.resourceUrl}/${encodedVal}`, { observe: 'response' });
  }

  /**
   * Convert a returned JSON object to route.
   */
  /**
   * Convert a Customer to a JSON which can be sent to the server.
   */
  private convert(route: Route): Route {
    const copy: Route = Object.assign({}, route);
    return copy;
  }
}
