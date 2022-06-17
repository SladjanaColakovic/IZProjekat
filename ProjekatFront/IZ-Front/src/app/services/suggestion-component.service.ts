import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SuggestionComponentService {

  baseURL = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  getMotherboards() {
    return this.http.get(this.baseURL + "/api/suggestion/motherboards");
  }

  ramSuggestion(data:any) {
    return this.http.post(this.baseURL + "/api/suggestion/ramSuggestion", data);      
  }

  getProcessors() {
    return this.http.get(this.baseURL + "/api/suggestion/processors");
  }


}


