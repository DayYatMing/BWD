import {Injectable, inject} from '@angular/core'
import {HttpClient, HttpResponse } from '@angular/common/http'
import {Observable} from 'rxjs'
import {ApplicationConfigService} from "../../core/config/application-config.service";
import {retry} from "rxjs/operators";
import {Data} from "./managereservedcap.model";

export interface ManagereservedcapStatus {
  total: string;
  id: number;
  segmentName: string;
  fiberPair: string;
  customerName: string;
  capacityName: string;
  dlsName: string;
  reserveCapId: number;
  segmentid: number;
  dlsid: number;
  fiberpairid: number;
  capacityid: number;
  capSegFpDlsId: number;
}


@Injectable({
  providedIn:'root'
})

export class ManagereservedcapService {

  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl             = this.applicationConfigService.getEndpointFor('api/managereservedcap');
  protected resourceUrlCap          = this.applicationConfigService.getEndpointFor('api/managereservedcap/customer');
  protected resourceUrlSave         = this.applicationConfigService.getEndpointFor('api/managereservedcap/save');
  protected resourceUrlUpdate       = this.applicationConfigService.getEndpointFor( 'api/managereservedcap/update');
  protected resourceUrlDlsCapacity  = this.applicationConfigService.getEndpointFor('api/managereservedcap/dlscapacity');
  protected resourceUrlColHeaders   = this.applicationConfigService.getEndpointFor('api/managereservedcap/colHeaders');
  protected resourceUrlDelete       = this.applicationConfigService.getEndpointFor( 'api/managereservedcap/delete');

  getGridData(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}` ,{ observe: 'response' })
      .pipe(
        retry(3)
      );
  }

  getGridCustomer(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrlCap}` ,{ observe: 'response' })
      .pipe(
        retry(3)
      );
  }

  getDlsCapacity(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrlDlsCapacity}` ,{ observe: 'response' })
      .pipe(
        retry(3)
      );
  }

  getColHeaders(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrlColHeaders}` ,{ observe: 'response' })
      .pipe(
        retry(3)
      );
  }

  saveData(data?: Data): Observable<HttpResponse<Data>> {
    return this.http.post<Data>(`${this.resourceUrlSave}`, data,{observe: 'response' });
  }

  updateData(data?: Data): Observable<HttpResponse<Data>> {
    return this.http.post<Data>(`${this.resourceUrlUpdate}`, data,{observe: 'response' });
  }


  deleteItem(id: number): Observable<HttpResponse<void>> {
    return this.http.delete<void>(`${this.resourceUrlDelete}?id=${id}`, { observe: 'response' });
  }
}

