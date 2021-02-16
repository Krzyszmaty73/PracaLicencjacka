import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Friend } from '../friend'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FriendService {

  private apiUrl = 'http://localhost:8080';

  friends: Friend[] = []

  constructor(private http: HttpClient) {
        this.http.get<Friend[]>(`${this.apiUrl}/friends`);
   }

  public getFriends(roomID: number): any {
    const friendsObservable = new Observable(observer => {
      observer.next(this.selectFriendsByRoomID(roomID));
    });
    return friendsObservable;
  }

  private selectFriendsByRoomID(roomID: number): Friend[] {

    let selectedFriends = this.friends.filter(friend => this.pickFriend(friend, roomID));
    return selectedFriends
  }

  public pickFriend(friend: Friend, roomID: number): boolean {
    let result = friend.roomIDs
    if (result == 0)
      return false
    else
      return true
  }

  public addNewFriend(newFriend: Friend) {
    return this.http.post<any>(`${this.apiUrl}/friends`, newFriend);
  }

  getAll() {
    return this.http.get<Friend[]>(`${this.apiUrl}/friends`);
}

  getAllFriendsByRoom(roomNumber: number): any{
    return this.http.get<Friend[]>(`${this.apiUrl}/friends/room/${roomNumber}`);
  }

  checFriendExist(email: string, roomID: number){
    return this.http.get<Boolean>(`${this.apiUrl}/friends/existing/${email}/${roomID}`);
  }

  //dodanie do pokoju w bazie
  addFriends(roomID: Number, email: String){
    return this.http.patch<any>(`${this.apiUrl}/updateRoom/addParticipant/${roomID}`, email);
  }

}
