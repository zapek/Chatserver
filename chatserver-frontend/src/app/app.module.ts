import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MenuComponent} from './menu/menu.component';
import {InviteComponent} from './invite/invite.component';
import {LinksComponent} from './links/links.component';
import {FormsModule} from "@angular/forms";
import {TrimPipe} from './trim.pipe';
import {HttpClientModule} from "@angular/common/http";
import {checkCircleFill, exclamationCircleFill, clipboard, NgxBootstrapIconsModule} from "ngx-bootstrap-icons";
import { ResultComponent } from './result/result.component';

const icons = {
	checkCircleFill,
	exclamationCircleFill,
	clipboard
};

@NgModule({
	declarations: [
		AppComponent,
		MenuComponent,
		InviteComponent,
		LinksComponent,
		TrimPipe,
		ResultComponent
	],
	imports: [
		BrowserModule,
		HttpClientModule,
		AppRoutingModule,
		NgbModule,
		FormsModule,
		NgxBootstrapIconsModule.pick(icons)
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule {
}
