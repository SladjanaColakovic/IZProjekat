import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComponentSuggestionComponent } from './component-suggestion/component-suggestion.component';

const routes: Routes = [
  { path: 'componentSuggestion', component: ComponentSuggestionComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
