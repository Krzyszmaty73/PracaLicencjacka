import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Room } from '../room'

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private apiUrl = 'http://localhost:8080';

  rooms: Room[] = []

  constructor(private http: HttpClient) { }

  getAllRoomsByLogedUser(ownerEmail: string){
    return this.http.get<Room[]>(`${this.apiUrl}/rooms/owner/${ownerEmail}`);
  }

  createRoom(newRoom: Room) {
    return this.http.post<any>(`${this.apiUrl}/rooms`, newRoom);
  }

  closeRoom(roomID: Number) {
    return this.http.post<any>(`${this.apiUrl}/receipts/close/room`, roomID);
  }

  updateRoomDescription(roomID: Number, description: String){
    return this.http.patch<any>(`${this.apiUrl}/updateRoom/description/${roomID}`, description);
  }

  getRoomDescription(roomID: Number){
    return this.http.get<any>(`${this.apiUrl}/rooms/description/${roomID}`);
  }

  getRoomOwner(roomID: Number){
    return this.http.get<any>(`${this.apiUrl}/room/owner/${roomID}`);
  }

 updateRoomStatus(roomID: Number, status: String){
    return this.http.patch<any>(`${this.apiUrl}/updateRoom/status/${roomID}`, status)
 }

}
