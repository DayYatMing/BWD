import {Injectable, inject} from '@angular/core'
import {HttpClient, HttpResponse } from '@angular/common/http'
import {Observable} from 'rxjs'
import {retry, map} from "rxjs/operators";
import {ApplicationConfigService} from "../../../core/config/application-config.service";
import { createRequestOption } from '../../../core/request/request-util';
import { OperationalStateData } from "./operationalstate.model";
import { TicketResponse,TicketRequest } from "./ticket.model";

@Injectable({
  providedIn:'root'
})
export class OperationalstateService {

  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/operationalstate');
  protected resourceUrlTicket= this.applicationConfigService.getEndpointFor('api/ticket');


  query(req?: any): Observable<HttpResponse<OperationalStateData[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<OperationalStateData[]>(`${this.resourceUrl}/events`, { params: options, observe: 'response' })
      .pipe(
        retry(3),
        map((res: HttpResponse<OperationalStateData[]>) => {
          const transformedBody = this.convertArrayResponse(res);
          return res.clone({ body: transformedBody });
        })
      );
  }

  private convertArrayResponse(res: HttpResponse<OperationalStateData[]>): OperationalStateData[] {
    return res.body || [];
  }

  findTickets(req?: any): Observable<HttpResponse<TicketResponse[]>> {
    const options = createRequestOption(req);
    return this.http.get<TicketResponse[]>(`${this.resourceUrlTicket}`, { params: options, observe: 'response' });
  }

  createTicket(ticketRequest: TicketRequest): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.resourceUrlTicket}/create`, ticketRequest, { observe: 'response' });
  }

}

