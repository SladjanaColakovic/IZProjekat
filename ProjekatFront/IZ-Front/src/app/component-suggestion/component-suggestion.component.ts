import { Component, OnInit } from '@angular/core';
import { SuggestionComponentService } from '../services/suggestion-component.service';

@Component({
  selector: 'app-component-suggestion',
  templateUrl: './component-suggestion.component.html',
  styleUrls: ['./component-suggestion.component.css']
})
export class ComponentSuggestionComponent implements OnInit {

  constructor(public service: SuggestionComponentService) { }

  motherboards: any[];
  selectedMotherboard = "";
  rams: any[];


  ngOnInit(): void {
    this.service.getMotherboards().subscribe((response: any) => {
      this.motherboards = response;
      console.log(this.motherboards)
    })
  }

  suggest(){
    console.log(this.selectedMotherboard);
    let data = {
      motherboard: this.selectedMotherboard
    }
    this.service.ramSuggestion(data).subscribe((response: any) => {
      this.rams = response;
      console.log(this.rams)
    })
    
  }

}
