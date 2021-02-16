import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { RoomService } from '../services/room.service'
import { Room } from '../room'
import { Friend } from '../friend';
import { AuthenticationService } from '../services/authentication.service';
import { FriendService } from '../services/friend.service';
import { SummaryService } from '../services/summary.service'

@Component({
  selector: 'app-home-room',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  roomID: number;
  sub: any;
  rooms: Room[];
  email: string;
  friends: Friend[];
  subFriends: any;
  joinRoomID: number;


  constructor(
    private route: ActivatedRoute,
    private router: Router, 
    private roomService: RoomService,
    private authenticationService: AuthenticationService,
    private friendService: FriendService,
    private summaryService: SummaryService

  ) {
    this.email = authenticationService.currentUserValue.email;

  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.roomID = params['roomID'];

      const roomObservable = this.roomService.getAllRoomsByLogedUser(this.email);
      roomObservable.subscribe((data: Room[]) =>{
        this.rooms = data;
      })
      return roomObservable;
    })

    this.subFriends = this.route.params.subscribe(params => {
      this.roomID = params['roomID'];


      const friendsObservable = this.friendService.getAllFriendsByRoom(this.roomID);
      friendsObservable.subscribe((data: Friend[]) => {
        this.friends = data;
      })
      return friendsObservable;
    })

}
  

  createRoomID() {
    this.roomID = Math.floor(100000 + Math.random() * 900000);
    this.createRoom();
    this.router.navigate(['home/create/' + this.roomID]);
  }

  addMyself() {
    let friend = new Friend();
    friend.email = this.email;
    friend.roomIDs = this.roomID
    this.friends.push(friend)
    
    this.friendService.addNewFriend(friend).subscribe(data => console.log(data), error => console.log(error));
    //dodanie frienda do pokoju w bazie
    this.friendService.addFriends(this.roomID, friend.email).subscribe(data => console.log(data), error => console.log(error));
  }

  OnInput(value) {
    this.joinRoomID = value;


    this.friendService.checFriendExist(this.email, this.joinRoomID).subscribe(data => 
      {if(data == true){
        this.router.navigate(['home/create/' + this.joinRoomID]);
      } else{
        
      }}
      , error => console.log(error))
  }

  //dodawanie pokoju do bazy 
  createRoom(){
    let room = new Room();
    room.ownerEmail = this.email;
    room.roomID = this.roomID;
    this.rooms.push(room);
    
    this.roomService.createRoom(room).subscribe(data => this.addMyself(), error => console.log(error));
  }

  getSummary(roomSummaryID: number){
    this.router.navigate(['/room/summary/' + roomSummaryID])
  }

  checkStatus(status: string){
    if(status == 'active'){
      return false;
    } else {
      return true;
    }
  
  }
}

