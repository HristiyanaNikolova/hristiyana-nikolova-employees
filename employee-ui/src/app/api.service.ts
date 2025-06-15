import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
return this.http.post(`${this.BASE_URL}/upload`, formData, {
  responseType: 'text' as 'json' 
});
}

  getResults(): Observable<any[]> {
    return this.http.get<any[]>(`${this.BASE_URL}/results`);
  }
}
