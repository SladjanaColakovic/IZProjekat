import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'IZ-Front';
  isHomePage = true;

  constructor(public router: Router) { }

  homePage(){
    this.router.navigate(['/']);
    this.isHomePage = true;
  }

  componentSuggestion(){
    this.router.navigate(['/componentSuggestion']);
    this.isHomePage = false;
  }

  fuzzy(){
    this.router.navigate(['/fuzzy']);
    this.isHomePage = false;
  }
}
