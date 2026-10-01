import {Injectable} from "@angular/core";
import {HttpResponse, HttpClient, HttpParams} from "@angular/common/http";
import {SERVER_API_URL} from "../../app.constants";
import {Observable} from "rxjs";
import {Chatbot} from "./chatbot.model";

@Injectable()
export class ChatbotService {

    private resourceUrl = SERVER_API_URL + 'api/chatbot';

    constructor(private http: HttpClient) { }

    getResponse(message: string): Observable<Chatbot> {
        const params = new HttpParams().set('message', message);
        return this.http.get<Chatbot>(`${this.resourceUrl}/response`, {params, responseType: 'json'});
    }
}



