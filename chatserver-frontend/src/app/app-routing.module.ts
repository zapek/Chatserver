import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InviteComponent } from "./invite/invite.component";
import { LinksComponent } from "./links/links.component";
import { ResultComponent } from "./result/result.component";

const routes: Routes = [
	{path: '', redirectTo: '/invite', pathMatch: 'full'},
	{path: 'result', component: ResultComponent },
	{path: 'invite', component: InviteComponent },
	{path: 'links', component: LinksComponent }
];

@NgModule({
	imports: [RouterModule.forRoot(routes, {})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
