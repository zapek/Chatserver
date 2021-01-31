import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
	selector: 'app-result',
	templateUrl: './result.component.html',
	styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {
	serverInvite: string | null | undefined;

	constructor(private router: Router) {
	}

	ngOnInit(): void {
		this.serverInvite = history.state.serverInvite;
	}

	copyToClipboard(id: string): void {
		let textarea = document.getElementById(id);
		if (textarea) {
			(textarea as HTMLFormElement).select();
			document.execCommand('copy');
		}
	}
}
