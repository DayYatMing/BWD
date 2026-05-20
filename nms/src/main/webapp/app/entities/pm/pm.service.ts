import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PMCustomerModel } from './pmcustomer.model';
import { PMServiceModel } from './pmservice.model';
import { PMSourceModel } from './pmsource.model';
import { PMConfigurationModel } from './pmconfiguration.model';
import { ApplicationConfigService } from '../../core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class PMService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pm');

  findCustomers(): Observable<HttpResponse<any[]>> {
    return this.http.get<any[]>(`${this.resourceUrl}/customers`, { observe: 'response' });
  }

  updateOrAddCustomer(customer: PMCustomerModel): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.resourceUrl}/customer`, customer, { observe: 'response' });
  }

  findServices(customerId: any): Observable<HttpResponse<any[]>> {
    return this.http.get<any[]>(`${this.resourceUrl}/services/${customerId}`, { observe: 'response' });
  }

  updateOrAddService(service: PMServiceModel, customerId: any): Observable<HttpResponse<any>> {
    service.customerId = customerId;
    return this.http.post<any>(`${this.resourceUrl}/service`, service, { observe: 'response' });
  }

  findSources(serviceId: any): Observable<HttpResponse<any[]>> {
    return this.http.get<any[]>(`${this.resourceUrl}/sources/${serviceId}`, { observe: 'response' });
  }

  updateOrAddSource(source: PMSourceModel, name: any, serviceId: any): Observable<HttpResponse<any>> {
    source.customer = name;
    source.customerSid = serviceId;
    return this.http.post<any>(`${this.resourceUrl}/source`, source, { observe: 'response' });
  }

  findConfigurations(serviceId: any): Observable<HttpResponse<any[]>> {
    return this.http.get<any[]>(`${this.resourceUrl}/configurations/${serviceId}`, { observe: 'response' });
  }

  updateOrAddConfiguration(configuration: PMConfigurationModel, serviceId: any): Observable<HttpResponse<any>> {
    configuration.serviceId = serviceId;
    return this.http.post<any>(`${this.resourceUrl}/configuration`, configuration, { observe: 'response' });
  }
}
