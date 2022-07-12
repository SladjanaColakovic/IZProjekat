import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ComponentSuggestionComponent } from './component-suggestion/component-suggestion.component';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FuzzyLogicComponent } from './fuzzy-logic/fuzzy-logic.component';
import { BayesComponent } from './bayes/bayes.component';

@NgModule({
  declarations: [
    AppComponent,
    ComponentSuggestionComponent,
    FuzzyLogicComponent,
    BayesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
