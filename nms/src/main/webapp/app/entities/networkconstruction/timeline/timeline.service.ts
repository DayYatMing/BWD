import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Timeline } from './timeline.model';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { createRequestOption } from '../../../core/request/request-util';

export type EntityResponseType = HttpResponse<Timeline>;

@Injectable({ providedIn: 'root' })
export class TimelineService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/timeline');
  protected resourceSiteUrl = this.applicationConfigService.getEndpointFor('api/timeline');

  query(req?: any): Observable<HttpResponse<Timeline[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<Timeline[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: HttpResponse<Timeline[]>) => this.convertArrayResponse(res)));
  }

  private convertArrayResponse(res: HttpResponse<Timeline[]>): HttpResponse<Timeline[]> {
    let jsonResponse: Timeline[] | null = res.body;
    const body: Timeline[] = [];
    if (jsonResponse != null) {
      for (let i = 0; i < jsonResponse.length; i++) {
        body.push(this.convertItemFromServer(jsonResponse[i]));
      }
    }
    return res.clone({ body });
  }

  private convertItemFromServer(site: Timeline | null): Timeline {
    const copy: Timeline = Object.assign({}, site);
    return copy;
  }

}


