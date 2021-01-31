import {Injectable} from '@angular/core';
import {environment} from "../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Health} from "./health";
import {InviteRequest} from "./invite-request";
import {InviteResponse} from "./invite-response";

@Injectable({
	providedIn: 'root'
})
export class ChatserverService {
	private apiUrl = environment.apiUrl;

	constructor(private http: HttpClient) {
	}

	public isOnline(): Observable<Health> {
		return this.http.get<Health>(this.apiUrl + "/v1/actuator/health");
	}

	public addFriend(invite: string): Observable<InviteResponse> {
		let inviteRequest = new InviteRequest(invite);
		return this.http.post<InviteResponse>(this.apiUrl + "/v1/invitation", inviteRequest);
	}
}
