import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SimilarityService {

  baseURL = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  similarity(processorBrand: any, processorName: any, cores: any, frequency: any, ramType: any, ramCapacity: any, hdType: any, hdCapacity: any, gcType: any, gcModel: any, chipset: any) {
    return this.http.get(this.baseURL + "/api/similarity/" + processorBrand + "/" + processorName + "/" + cores + "/" + frequency + "/" + ramType + "/" + ramCapacity + "/" + hdType + "/" + hdCapacity + "/" + gcType + "/" + gcModel + "/" + chipset);
  }
}
