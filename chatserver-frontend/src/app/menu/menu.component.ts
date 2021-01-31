import {Component, OnInit} from '@angular/core';
import {ChatserverService} from "../chatserver.service";
import {ServerState} from "../server-state.enum";

@Component({
	selector: 'app-menu',
	templateUrl: './menu.component.html',
	styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
	isCollapsed: boolean = true;
	serverState: ServerState = ServerState.UNKNOWN;
	eServerState = ServerState;
	serverErrorMessage: string = "";

	constructor(private chatserverService: ChatserverService) {
	}

	toggleCollapse() {
		this.isCollapsed = !this.isCollapsed;
	}

	ngOnInit(): void {
		this.checkServer();
	}

	checkServer(): void {
		this.chatserverService.isOnline()
				.subscribe(data => this.serverState = data.status === "UP" ? ServerState.UP : ServerState.DOWN,
						error => {
							this.serverState = ServerState.DOWN;
							this.serverErrorMessage = error.message;
						});

		setTimeout(() => {
			this.checkServer();
		}, 30000);
	}
}
