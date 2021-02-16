import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Receipt } from '../receipt';
import { Friend } from '../friend';
import { Room } from '../room'
import { ReceiptService } from '../services/receipt.service';
import { AuthenticationService } from '../services/authentication.service';
import { FriendService } from '../services/friend.service'
import { RoomService } from '../services/room.service'

@Component({
  selector: 'app-create-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})

export class RoomComponent implements OnInit {

  receiptID: number;
  roomID: number;
  sub: any;
  subFriends: any;
  subDescription: any;
  subRoomOwner: any;
  showLabel: boolean;
  loggedUser: any;


  receipts: Receipt[];
  friends: Friend[];
  rooms: Room;
  email: string;
  loggedEmail: string;
  roomDescription: string;
  roomOwner: string;
  


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private receiptService: ReceiptService,
    private authenticationService: AuthenticationService,
    private friendService: FriendService,
    private roomService: RoomService

  ) {
    this.loggedUser = authenticationService.currentUserValue;
    this.loggedEmail = authenticationService.currentUserValue.email;
  }

  ngOnInit(): void {

    this.sub = this.route.params.subscribe(params => {
      this.roomID = params['roomID'];

      const rachunkiObservable = this.receiptService.getAllReceiptsByRoom(this.roomID);
      rachunkiObservable.subscribe((data: Receipt[]) => {
        this.receipts = data;
      })
      return rachunkiObservable;
    })

    this.subFriends = this.route.params.subscribe(params => {
      this.roomID = params['roomID'];


      const friendsObservable = this.friendService.getAllFriendsByRoom(this.roomID);
      friendsObservable.subscribe((data: Friend[]) => {
        this.friends = data;
      })
      return friendsObservable;
    })
    
    this.subDescription = this.route.params.subscribe(params => {
      this.roomID = params['roomID'];


      const descriptionObservable = this.roomService.getRoomDescription(this.roomID);
      descriptionObservable.subscribe((data: any) => {
        this.roomDescription = data;
      })
      return descriptionObservable;
    }) 

    this.sub = this.route.params.subscribe(params => {
      this.roomID = params['roomID'];

      const ownerObservable = this.roomService.getRoomOwner(this.roomID);
      ownerObservable.subscribe((data: any) => {
        this.roomOwner = data;
        if(this.roomOwner == this.loggedEmail){
          this.showLabel = true
        } else {
          this.showLabel = false
        }
      })
      return ownerObservable;
    }) 
    
 }

  createReceipID() {
    this.receiptID = Math.floor(100000 + Math.random() * 900000);
    this.router.navigate(['home/create/' + this.roomID + '/receipt/' + this.receiptID]);
  }

 
  addFriend() {
    let friend = new Friend();
    friend.email = this.email
    friend.roomIDs = this.roomID
    this.friends.push(friend)

    this.friendService.addNewFriend(friend).subscribe(data => console.log(data), error => console.log(error));
    //dodanie frienda do pokoju w bazie
    this.friendService.addFriends(this.roomID, friend.email).subscribe(data => console.log(data), error => console.log(error));
  }

  roomSummary(){
    //zmiana statusu pokoju na zamkniety
    this.roomService.updateRoomStatus(this.roomID, "closed").subscribe(data => console.log(data), error => console.log(error));
    console.log("Moj room id to : " + this.roomID)
    this.roomService.closeRoom(this.roomID).subscribe(data => console.log(data), error => console.log(error));

     setTimeout(() => {
       this.router.navigate(['room/summary/' + this.roomID]);
     }, 2000);
  }

  addDescription(){
    this.roomService.updateRoomDescription(this.roomID, this.roomDescription).subscribe(data => console.log(data), error => console.log(error));
  }

}
