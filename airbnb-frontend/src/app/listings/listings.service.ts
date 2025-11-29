import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ListingsService {
  
  private API = 'http://localhost:8080/api/listings';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.API);
  }

  getOne(id: string): Observable<any> {
    return this.http.get<any>(`${this.API}/${id}`);
  }

  search(location: string, start: string, end: string): Observable<any[]> {
    let params = new HttpParams();

    if (location) params = params.set('location', location);
    if (start) params = params.set('start', start);
    if (end) params = params.set('end', end);

    return this.http.get<any[]>(`${this.API}/search`, { params });
  }
}
