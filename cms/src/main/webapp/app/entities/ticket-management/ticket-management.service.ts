import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TicketManagement } from './ticket-management.model';
import { ApplicationConfigService } from '../../core/config/application-config.service';

export type EntityResponseType = HttpResponse<TicketManagement>;

@Injectable({ providedIn: 'root' })
export class TicketManagementService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ticket');

  find(cusShortName: string, statetype: string): Observable<HttpResponse<TicketManagement[]>> {
    return this.http.get<TicketManagement[]>(this.resourceUrl + '/' + cusShortName + '&' + statetype, { observe: 'response' });
  }

  findCusShortName(login: any): Observable<HttpResponse<string>> {
    return this.http.get<string>(this.resourceUrl + '/cus-acronym/' + login, { observe: 'response', responseType: 'text' as 'json' });
  }

  findRatio(cusShortName: string): Observable<HttpResponse<TicketManagement>> {
    return this.http.get<TicketManagement>(this.resourceUrl + '/ratio/' + cusShortName, { observe: 'response' });
  }

  findId(id: string): Observable<HttpResponse<TicketManagement>> {
    return this.http.get<TicketManagement>(this.resourceUrl + '/id/' + id, { observe: 'response' });
  }

  update(ticketManagement: TicketManagement, cusShortName: any, email: any): Observable<HttpResponse<TicketManagement>> {
    const ticketMgmt: TicketManagement = Object.assign({}, ticketManagement);
    ticketMgmt.Login = cusShortName;
    ticketMgmt.CustomerUser = email;
    return this.http.put<TicketManagement>(this.resourceUrl, ticketMgmt, { observe: 'response' });
  }

  create(ticketManagement: TicketManagement, cusShortName: any, email: any): Observable<EntityResponseType> {
    const ticketMgmt: TicketManagement = Object.assign({}, ticketManagement);
    ticketMgmt.Login = cusShortName;
    ticketMgmt.CustomerUser = email;
    return this.http.post<TicketManagement>(this.resourceUrl, ticketMgmt, { observe: 'response' });
  }
}
