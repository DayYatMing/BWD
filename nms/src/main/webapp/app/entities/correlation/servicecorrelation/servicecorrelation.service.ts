import {Injectable, inject} from '@angular/core'
import {HttpClient, HttpResponse } from '@angular/common/http'
import {Observable} from 'rxjs'
import {retry, map} from "rxjs/operators";
import {ApplicationConfigService} from "../../../core/config/application-config.service";
import { createRequestOption } from '../../../core/request/request-util';
import { ServicecorrelationModel } from "./servicecorrelation.model";
import { TicketResponse,TicketRequest } from "./ticket.model";

@Injectable({
  providedIn:'root'
})
export class ServicecorrelationService {

  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  private resourceUrl =  SERVER_API_URL + 'api/correlation';
  private resourceUrlCustomerService = SERVER_API_URL + 'api/correlations/update';
  protected resourceUrlTicket= this.applicationConfigService.getEndpointFor('api/ticket');


  query(req?: any): Observable<HttpResponse<ServicecorrelationModel[]>> {
    const options = createRequestOption(req);
    return this.http.get<ServicecorrelationModel[]>(`${this.resourceUrl}/servicecorrelation`, { params: options, observe: 'response' })
      .pipe(
        retry(3)
      );
  }

  findTickets(req?: any): Observable<HttpResponse<TicketResponse[]>> {
    const options = createRequestOption(req);
    return this.http.get<TicketResponse[]>(`${this.resourceUrlTicket}`, { params: options, observe: 'response' });
  }

  createTicket(ticketRequest: TicketRequest): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.resourceUrlTicket}/create`, ticketRequest, { observe: 'response' });
  }

  queryForNE(data?: ServicecorrelationModel): Observable<HttpResponse<ServicecorrelationModel>> {
    return this.http.post<ServicecorrelationModel>(`${this.resourceUrl}/correlation/ne`, data,{observe: 'response' });
  }

  queryForConnectorTypes(req?: any): Observable<HttpResponse<ServicecorrelationModel[]>> {
    return this.http.get<ServicecorrelationModel[]>(`${this.resourceUrl}/connectors`, {  observe: 'response' });
  }

  updateCustomerServiceData(data: Object[]): Observable<HttpResponse<void>> {
    return this.http.put<void>(this.resourceUrlCustomerService, data, { observe: 'response' })
      .pipe(
        map((response: HttpResponse<void>) => {
          return response;
        })
      );
  }
  updateCustomerServiceDataDacPort(data: Object[]): Observable<HttpResponse<void>> {
    return this.http.put<void>(`${this.resourceUrl}/correlations/updateDac`, data, { observe: 'response' })
      .pipe(
        map((response: HttpResponse<void>) => {
          return response;
        })
      );
  }
  private convert(data: ServicecorrelationModel): ServicecorrelationModel {
    return Object.assign({}, data);
  }

}

