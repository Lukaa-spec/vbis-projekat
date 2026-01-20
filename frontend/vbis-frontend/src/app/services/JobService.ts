import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { JobAd } from "../models/job";

@Injectable({
  providedIn: 'root'
})
export class JobService {
  private baseUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) { }

  //Pomoćna funkcija za dobijanje HTTP zaglavlja sa tokenom
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  
  search(query: string): Observable<JobAd[]> {
    const params = new HttpParams().set('query', query);
    return this.http.get<JobAd[]>(`${this.baseUrl}/search-jobs`, { 
      headers: this.getHeaders(), 
      params 
    });
  }

  
  postJobAd(jobAd: JobAd): Observable<JobAd> {
    return this.http.post<JobAd>(`${this.baseUrl}/job-ads`, jobAd, { 
      headers: this.getHeaders() 
    });
  }

  
    getStudentsLookingForJob(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/students-looking-for-job`, { 
    headers: this.getHeaders() 
  });
 }

  getMyAds(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/my-ads`, { headers: this.getHeaders() });
  }

  deleteAd(adId: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/job-ads/${adId}`, { headers: this.getHeaders() });
  }

  updateAd(updateRequest: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/job-ads`, updateRequest, { headers: this.getHeaders() });
  }

  getMyExams(): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseUrl}/my-exams`, { headers: this.getHeaders() });
  }
  
}