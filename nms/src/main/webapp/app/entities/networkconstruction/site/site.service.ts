import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Site } from './site.model';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { createRequestOption } from '../../../core/request/request-util';

export type EntityResponseType = HttpResponse<Site>;

@Injectable({ providedIn: 'root' })
export class SiteService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sites');
  protected resourceSiteUrl = this.applicationConfigService.getEndpointFor('api/site');

  query(req?: any): Observable<HttpResponse<Site[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<Site[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: HttpResponse<Site[]>) => this.convertArrayResponse(res)));
  }

  private convertArrayResponse(res: HttpResponse<Site[]>): HttpResponse<Site[]> {
    let jsonResponse: Site[] | null = res.body;
    const body: Site[] = [];
    if (jsonResponse != null) {
      for (let i = 0; i < jsonResponse.length; i++) {
        body.push(this.convertItemFromServer(jsonResponse[i]));
      }
    }
    return res.clone({ body });
  }

  private convertItemFromServer(site: Site | null): Site {
    const copy: Site = Object.assign({}, site);
    return copy;
  }

  create(site: Site): Observable<EntityResponseType> {
    const copy = this.convert(site);
    return this.http.post<Site>(this.resourceUrl, copy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Site>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: Site = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  update(site: Site): Observable<EntityResponseType> {
    const copy = this.convert(site);
    return this.http.put<Site>(this.resourceUrl, copy, { observe: 'response' });
  }

  querySiteCount(sitename?: any): Observable<HttpResponse<number>> {
    return this.http.get<number>(`${this.resourceSiteUrl}/${sitename}`, { observe: 'response' });
  }

  delete(id: any): Observable<HttpResponse<any>> {
    var encodedVal = btoa(`${id}`);
    return this.http.delete<any>(`${this.resourceUrl}/${encodedVal}`, { observe: 'response' });
  }

  /**
   * Convert a returned JSON object to site.
   */
  /**
   * Convert a Customer to a JSON which can be sent to the server.
   */
  private convert(site: Site): Site {
    const copy: Site = Object.assign({}, site);
    return copy;
  }
}
