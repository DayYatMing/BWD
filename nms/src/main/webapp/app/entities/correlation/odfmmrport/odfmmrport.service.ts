import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import {OdfmmrportData} from "./odfmmrport.model";

@Injectable({ providedIn: 'root' })
export class OdfmmrportService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/correlation/odfmmr');

  findAllData(): Observable<HttpResponse<OdfmmrportData>> {
    return this.http.get<OdfmmrportData>(this.resourceUrl, { observe: 'response' });
  }
}
