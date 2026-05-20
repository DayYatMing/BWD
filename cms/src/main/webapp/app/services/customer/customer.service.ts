import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Customer } from './customer.model';

@Injectable({ providedIn: 'root' })
export class CustomerService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/customer');

  find(): Observable<HttpResponse<Customer[]>> {
    return this.http.get<Customer[]>(this.resourceUrl, { observe: 'response' });
  }

  findId(customerId: string): Observable<HttpResponse<Customer>> {
    return this.http.get<Customer>(this.resourceUrl + '/' + customerId, { observe: 'response' });
  }

  create(customer: Customer): Observable<HttpResponse<void>> {
    return this.http.post<void>(this.resourceUrl, customer, { observe: 'response' });
  }

  edit(customer: Customer): Observable<HttpResponse<void>> {
    return this.http.put<void>(this.resourceUrl, customer, { observe: 'response' });
  }

  delete(customer: Customer): Observable<HttpResponse<void>> {
    return this.http.delete<void>(this.resourceUrl + '/' + customer.id, { observe: 'response' });
  }
}
