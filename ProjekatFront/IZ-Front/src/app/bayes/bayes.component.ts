import { Component, OnInit } from '@angular/core';
import { BayesService } from '../services/bayes.service';

@Component({
  selector: 'app-bayes',
  templateUrl: './bayes.component.html',
  styleUrls: ['./bayes.component.css']
})
export class BayesComponent implements OnInit {

  selectedMalfunction = "";
  results: any[];

  constructor(public service: BayesService) { }

  ngOnInit(): void {
  }

  bayes() {

    this.service.bayes(this.selectedMalfunction).subscribe((response: any) => {
      this.results = response;
    })

  }

}
