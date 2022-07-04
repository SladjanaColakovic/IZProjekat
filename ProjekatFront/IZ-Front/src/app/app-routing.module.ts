import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComponentSuggestionComponent } from './component-suggestion/component-suggestion.component';
import { FuzzyLogicComponent } from './fuzzy-logic/fuzzy-logic.component';

const routes: Routes = [
  { path: 'componentSuggestion', component: ComponentSuggestionComponent},
  { path: 'fuzzy', component: FuzzyLogicComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
