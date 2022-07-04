import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FuzzyLogicService {

  baseURL = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  getProcessors() {
    return this.http.get(this.baseURL + "/api/fuzzy/processors");
  }
  getGCs() {
    return this.http.get(this.baseURL + "/api/fuzzy/gcs");
  }
  fuzzy(data: any) {
    return this.http.post(this.baseURL + "/api/fuzzy/fuzzy", data);
  }


}
