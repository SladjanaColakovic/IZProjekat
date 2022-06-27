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
  motherboardsForProcessor: any[];
  motherboardsForHDD: any[];
  processors: any[];
  selectedMotherboardForRam = "";
  selectedMotherboardForProcessor = "";
  rams: any[];
  hdds: any[];
  selectedMotherboardForHDD = "";
  selectedComputerForHDD = "";


  ngOnInit(): void {
    this.service.getMotherboards().subscribe((response: any) => {
      this.motherboardsForRam = response;
      this.motherboardsForProcessor = response;
      this.motherboardsForHDD = response;
      console.log(this.motherboardsForRam)
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

  suggestHDD(){
    console.log(this.selectedMotherboardForHDD)
    let data = {
      motherboard: this.selectedMotherboardForHDD,
      computer: this.selectedComputerForHDD
    }
    this.service.hddSuggestion(data).subscribe((response: any) => {
      this.hdds = response;
      console.log(this.hdds)
    })
  }

}
