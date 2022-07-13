import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BayesService {

  baseURL = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  bayes(data: any) {
    return this.http.get(this.baseURL + "/api/bayes/bayes/" + data);
  }
}
