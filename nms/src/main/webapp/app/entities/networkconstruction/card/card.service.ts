import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {Card} from './card.model'
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { createRequestOption } from '../../../core/request/request-util';

export type EntityResponseType = HttpResponse<Card>;

@Injectable({ providedIn: 'root' })
export class CardService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cards');
  protected resourceCardUrl = this.applicationConfigService.getEndpointFor('api/card');

  query(req?: any): Observable<HttpResponse<Card[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<Card[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: HttpResponse<Card[]>) => this.convertArrayResponse(res)));
  }

  private convertArrayResponse(res: HttpResponse<Card[]>): HttpResponse<Card[]> {
    let jsonResponse: Card[] | null = res.body;
    const body: Card[] = [];
    if (jsonResponse != null) {
      for (let i = 0; i < jsonResponse.length; i++) {
        body.push(this.convertItemFromServer(jsonResponse[i]));
      }
    }
    return res.clone({ body });
  }

  private convertItemFromServer(card: Card | null): Card {
    const copy: Card = Object.assign({}, card);
    return copy;
  }

  create(card: Card): Observable<EntityResponseType> {
    const copy = this.convert(card);
    return this.http.post<Card>(this.resourceUrl, copy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Card>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: Card = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  update(card: Card): Observable<EntityResponseType> {
    const copy = this.convert(card);
    return this.http.put<Card>(this.resourceUrl, copy, { observe: 'response' });
  }

  queryCardCount(cardname?: any): Observable<HttpResponse<number>> {
    return this.http.get<number>(`${this.resourceCardUrl}/${cardname}`, { observe: 'response' });
  }

  delete(id: any): Observable<HttpResponse<any>> {
    var encodedVal = btoa(`${id}`);
    return this.http.delete<any>(`${this.resourceUrl}/${encodedVal}`, { observe: 'response' });
  }

  /**
   * Convert a returned JSON object to card.
   */
  /**
   * Convert a Customer to a JSON which can be sent to the server.
   */
  private convert(card: Card): Card {
    const copy: Card = Object.assign({}, card);
    return copy;
  }
}
