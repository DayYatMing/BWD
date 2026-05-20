import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { retry} from "rxjs/operators";
import {ClientportData} from "../correlation/clientport/clientport.model";


export interface CapacityRepLabel {
  id: number;
  segment_id: number;
  fiber_pair_id: number;
  segmentName: string;
  fiberPairName: string;
  dlsName: string;
}
@Injectable({ providedIn: 'root' })

export class CapacityreportService {

  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/capacityreport/capacityRepLabel');
  protected resourceEstimatedUrl = this.applicationConfigService.getEndpointFor('api/capacityreport/estimatedFpCapacity');
  protected resourceEstimatedUpdate =  this.applicationConfigService.getEndpointFor('api/capacityreport/updateEstimatedCapacity');

    getCapacityRepLabelData(): Observable<any> {
        return this.http.get<any>(`${this.resourceUrl}` ,{ observe: 'response' })
    .pipe(
            retry(3)
        );
    }

    getCapacityEstimatedData(): Observable<any> {
        return this.http.get<any>(`${this.resourceEstimatedUrl}` ,{ observe: 'response' })
            .pipe(
                retry(3)
            );
    }
    updateEstimatedData(data?: ClientportData): Observable<HttpResponse<ClientportData>> {
        return this.http.put<ClientportData>(`${this.resourceEstimatedUpdate}`, data,{observe: 'response' });
    }

}
