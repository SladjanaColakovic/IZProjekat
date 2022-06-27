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

  processorSuggestion(data:any) {
    return this.http.post(this.baseURL + "/api/suggestion/processorSuggestion", data);      
  }

  hddSuggestion(data:any) {
    return this.http.post(this.baseURL + "/api/suggestion/hddSuggestion", data);      
  }

  gcSuggestion(data:any) {
    return this.http.post(this.baseURL + "/api/suggestion/gcSuggestion", data);      
  }

}


