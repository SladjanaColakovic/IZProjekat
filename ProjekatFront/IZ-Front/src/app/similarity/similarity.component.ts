import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { SimilarityService } from '../services/similarity.service';

@Component({
  selector: 'app-similarity',
  templateUrl: './similarity.component.html',
  styleUrls: ['./similarity.component.css']
})
export class SimilarityComponent implements OnInit {

  constructor(public service: SimilarityService) { }

  list: any[]
  selectedComp: any
  processorBrand = ""
  processorName = ""
  cores = ""
  frequency = ""
  ramType = ""
  ramCapacity = ""
  hdType = ""
  hdCapacity = ""
  gcType = ""
  gcModel = ""
  chipset = ""
  similarComputers: any[]

  ngOnInit(): void {

    this.list = [
      "Intel Core i3", "Intel Core i5", "AMD 3020e", "AMD Ryzen"
    ]
    this.selectedComp = null
  }

  show(){
    console.log(this.selectedComp)

    if (this.selectedComp == 'Intel Core i3'){
      this.processorBrand = 'Intel'
      this.processorName = "Core i3"
      this.cores = '4'
      this.frequency = '2.6'
      this.ramType = 'DDR4'
      this.ramCapacity = '8'
      this.hdType = 'SSD'
      this.hdCapacity = '240'
      this.gcType = 'integrated'
      this.gcModel = 'Intel UHD 610'
      this.chipset = 'Intel H410'
    }

    if (this.selectedComp == 'Intel Core i5'){
      this.processorBrand = 'Intel'
      this.processorName = "Core i5"
      this.cores = '4'
      this.frequency = '2.4'
      this.ramType = 'DDR4'
      this.ramCapacity = '32'
      this.hdType = 'SSD'
      this.hdCapacity = '256'
      this.gcType = 'discrete'
      this.gcModel = 'nVidia GeForce GTX 1650'
      this.chipset = 'Intel HM 370'
    }

    if (this.selectedComp == 'AMD 3020e'){
      this.processorBrand = 'AMD'
      this.processorName = "3020e"
      this.cores = '2'
      this.frequency = '1.2'
      this.ramType = 'DDR4'
      this.ramCapacity = '4'
      this.hdType = 'SSD'
      this.hdCapacity = '256'
      this.gcType = 'integrated'
      this.gcModel = 'AMD Radeon Graphics'
      this.chipset = 'AMD SoC Platform'
    }

    if (this.selectedComp == 'AMD Ryzen'){
      this.processorBrand = 'AMD'
      this.processorName = "Ryzen 5 5600X"
      this.cores = '6'
      this.frequency = '3.7'
      this.ramType = 'DDR4'
      this.ramCapacity = '32'
      this.hdType = 'SSD'
      this.hdCapacity = '1'
      this.gcType = 'discrete'
      this.gcModel = 'GeForce RTX 3060 LHR'
      this.chipset = 'AMD B550'
    }
  }

  similarity() {
    this.service.similarity(this.processorBrand, this.processorName, this.cores, this.frequency, this.ramType, this.ramCapacity, this.hdType, this.hdCapacity, this.gcType, this.gcModel, this.chipset)
    .subscribe((response: any) => {
      this.similarComputers = response;
      console.log(this.similarComputers)
    })

  }

  

}
