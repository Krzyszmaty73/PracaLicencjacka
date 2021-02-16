import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { roomSummary } from '../roomSummary';

@Injectable({
  providedIn: 'root'
})
export class SummaryService {

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getAllParticipantsByRoom(roomNumber: number){
    return this.http.get<roomSummary[]>(`${this.apiUrl}/summary/${roomNumber}`);
  }
}
