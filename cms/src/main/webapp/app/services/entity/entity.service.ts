import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Entity } from './entity.model';

@Injectable({ providedIn: 'root' })
export class EntityService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/entity');

  find(): Observable<HttpResponse<Entity[]>> {
    return this.http.get<Entity[]>(this.resourceUrl + '/active', { observe: 'response' });
  }

  findAll(): Observable<HttpResponse<Entity[]>> {
    return this.http.get<Entity[]>(this.resourceUrl, { observe: 'response' });
  }

  findId(entityId: string): Observable<HttpResponse<Entity>> {
    return this.http.get<Entity>(this.resourceUrl + '/' + entityId, { observe: 'response' });
  }

  edit(entity: Entity): Observable<HttpResponse<void>> {
    return this.http.put<void>(this.resourceUrl, entity, { observe: 'response' });
  }

  create(entity: Entity): Observable<HttpResponse<void>> {
    return this.http.post<void>(this.resourceUrl, entity, { observe: 'response' });
  }

  delete(entity: Entity): Observable<HttpResponse<void>> {
    return this.http.delete<void>(this.resourceUrl + '/' + entity.id, { observe: 'response' });
  }
}
