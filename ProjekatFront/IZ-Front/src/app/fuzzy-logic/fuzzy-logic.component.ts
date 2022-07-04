import { Component, OnInit } from '@angular/core';
import { FuzzyLogicService } from '../services/fuzzy-logic.service';

@Component({
  selector: 'app-fuzzy-logic',
  templateUrl: './fuzzy-logic.component.html',
  styleUrls: ['./fuzzy-logic.component.css']
})
export class FuzzyLogicComponent implements OnInit {

  selectedProcessor = ""
  selectedGC = ""
  selectedRAM = ""
  selectedHD = ""
  comparingResults : any[]
  processors: any[]
  gcs: any[]

  constructor(public service: FuzzyLogicService) { }

  ngOnInit(): void {
    this.service.getProcessors().subscribe((response: any) => {
      this.processors = response
      console.log(this.processors)
      this.service.getGCs().subscribe((response: any) => {
        this.gcs = response
        console.log(response)
      })
    })
  }

  fuzzy(){
    console.log("Processor:", this.selectedProcessor)
    console.log("GC:", this.selectedGC)
    console.log("RAM:", this.selectedRAM)
    console.log("HD", this.selectedHD)

    let data = {
      processor: this.selectedProcessor,
      gc: this.selectedGC,
      ram: this.selectedRAM,
      hd: this.selectedHD,
    }

    this.service.fuzzy(data).subscribe((response: any) => {
      this.comparingResults = response;
      console.log(this.comparingResults)
    }) 
  }

}
