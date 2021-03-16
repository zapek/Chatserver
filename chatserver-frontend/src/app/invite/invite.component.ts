import {Component, OnInit} from '@angular/core';
import {ChatserverService} from "../chatserver.service";
import {Router} from "@angular/router";

@Component({
	selector: 'app-invite',
	templateUrl: './invite.component.html',
	styleUrls: ['./invite.component.css']
})
export class InviteComponent implements OnInit {
	rsId: string | undefined;
	sending: boolean = false;
	errorMessage: string | null = null;

	constructor(private chatserverService: ChatserverService,
	            private router: Router) {
	}

	ngOnInit(): void {
	}

	send(): void {
		this.errorMessage = null;
		this.sending = true;
		if (this.rsId === undefined) {
			throw "Illegal argument";
		}
		this.chatserverService.addFriend(this.rsId)
				.subscribe(data => this.router.navigate(["/result"], {state: {serverInvite: data.serverInvite}}),
						error => {
							if (error.status == 422) {
								this.errorMessage = "wrong ID or old certificate (Retroshare 0.6.6+ required)";
							} else if (error.status == 409) {
								this.errorMessage = "ID was already submitted"
							} else {
								this.errorMessage = error.message;
							}
							this.sending = false;
						});
	}
}
