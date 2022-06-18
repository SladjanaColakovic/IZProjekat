import { Component, OnInit } from '@angular/core';
import { SuggestionComponentService } from '../services/suggestion-component.service';

@Component({
  selector: 'app-component-suggestion',
  templateUrl: './component-suggestion.component.html',
  styleUrls: ['./component-suggestion.component.css']
})
export class ComponentSuggestionComponent implements OnInit {

  constructor(public service: SuggestionComponentService) { }

  motherboardsForRam: any[];
  motherboardsForProcessor: any[]
  processors: any[];
  selectedMotherboardForRam = "";
  selectedMotherboardForProcessor = "";
  rams: any[];


  ngOnInit(): void {
    this.service.getMotherboards().subscribe((response: any) => {
      this.motherboardsForRam = response;
      console.log(this.motherboardsForRam)
      this.service.getMotherboards().subscribe((response: any) => {
        this.motherboardsForProcessor = response;
        console.log(this.motherboardsForProcessor)
      })
    })
  }

  suggestRam(){
    console.log(this.selectedMotherboardForRam);
    let data = {
      motherboard: this.selectedMotherboardForRam
    }
    this.service.ramSuggestion(data).subscribe((response: any) => {
      this.rams = response;
      console.log(this.rams)
    })
    
  }

  suggestProcessor(){
    console.log(this.selectedMotherboardForProcessor)
    let data = {
      motherboard: this.selectedMotherboardForProcessor
    }
    this.service.processorSuggestion(data).subscribe((response: any) => {
      this.processors = response;
      console.log(this.processors)
    })
  }

}
