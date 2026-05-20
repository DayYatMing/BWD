import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Order } from './order.model';

@Injectable({ providedIn: 'root' })
export class OrderService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order');

  find(): Observable<HttpResponse<Order[]>> {
    return this.http.get<Order[]>(this.resourceUrl, { observe: 'response' });
  }

  findId(orderId: string): Observable<HttpResponse<Order>> {
    return this.http.get<Order>(this.resourceUrl + '/' + orderId, { observe: 'response' });
  }

  create(formData: FormData): Observable<HttpResponse<void>> {
    return this.http.post<void>(this.resourceUrl, formData, { observe: 'response' });
  }

  update(formData: FormData): Observable<HttpResponse<void>> {
    return this.http.put<void>(this.resourceUrl, formData, { observe: 'response' });
  }

  delete(orderId: string): Observable<HttpResponse<void>> {
    return this.http.delete<void>(this.resourceUrl + '/' + orderId, { observe: 'response' });
  }

  findServices(code: string): Observable<HttpResponse<Order[]>> {
    return this.http.get<Order[]>(this.resourceUrl + '/cus-acronym/' + code, { observe: 'response' });
  }
}
