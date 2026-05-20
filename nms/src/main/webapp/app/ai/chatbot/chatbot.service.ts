import { Injectable, inject } from '@angular/core';
import { HttpResponse, HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Chatbot } from './chatbot.model';
import { ApplicationConfigService } from '../../core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class ChatbotService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/chatbot');

  getResponse(chatbot: Chatbot): Observable<any> {
    return this.http.post<any>(`${this.resourceUrl}/response`, chatbot);
  }
}
