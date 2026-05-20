import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Segment } from './segment.model'
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { createRequestOption } from '../../../core/request/request-util';

export type EntityResponseType = HttpResponse<Segment>;

@Injectable({ providedIn: 'root' })
export class SegmentService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/segments');
  protected resourceSegmentUrl = this.applicationConfigService.getEndpointFor('api/segment');

  query(req?: any): Observable<HttpResponse<Segment[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<Segment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: HttpResponse<Segment[]>) => this.convertArrayResponse(res)));
  }

  private convertArrayResponse(res: HttpResponse<Segment[]>): HttpResponse<Segment[]> {
    let jsonResponse: Segment[] | null = res.body;
    const body: Segment[] = [];
    if (jsonResponse != null) {
      for (let i = 0; i < jsonResponse.length; i++) {
        body.push(this.convertItemFromServer(jsonResponse[i]));
      }
    }
    return res.clone({ body });
  }

  private convertItemFromServer(segment: Segment | null): Segment {
    const copy: Segment = Object.assign({}, segment);
    return copy;
  }

  create(segment: Segment): Observable<EntityResponseType> {
    const copy = this.convert(segment);
    return this.http.post<Segment>(this.resourceUrl, copy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Segment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: Segment = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  update(segment: Segment): Observable<EntityResponseType> {
    const copy = this.convert(segment);
    return this.http.put<Segment>(this.resourceUrl, copy, { observe: 'response' });
  }

  querySegmentCount(segmentname?: any): Observable<HttpResponse<number>> {
    return this.http.get<number>(`${this.resourceSegmentUrl}/${segmentname}`, { observe: 'response' });
  }

  delete(id: any): Observable<HttpResponse<any>> {
    var encodedVal = btoa(`${id}`);
    return this.http.delete<any>(`${this.resourceUrl}/${encodedVal}`, { observe: 'response' });
  }

  /**
   * Convert a returned JSON object to segment.
   */
  /**
   * Convert a Customer to a JSON which can be sent to the server.
   */
  private convert(segment: Segment): Segment {
    const copy: Segment = Object.assign({}, segment);
    return copy;
  }
}
