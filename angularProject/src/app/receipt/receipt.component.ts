import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Receipt } from './../receipt';
import { Friend } from './../friend';
import { FriendService } from '../services/friend.service'
import { FormBuilder } from '@angular/forms';
import { ReceiptService } from '../services/receipt.service'

@Component({
  selector: 'app-add-receipt',
  templateUrl: './receipt.component.html',
  styleUrls: ['./receipt.component.css']
})
export class ReceiptComponent implements OnInit {

  sub: any;
  receipt: Receipt = new Receipt();

  subFriends: any;
  selected: any;
  friends: Friend[];

  myMap = new Map();

  currency = [{
    name: 'PLN'
  },
  {
    name: 'USD'
  },
  {
    name: 'EUR'
  }];


  selectedItemsList = [];
  checkedIDs = [];
  tmpPaidBy: any

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private friendService: FriendService,
    private fb: FormBuilder, 
    private receiptService: ReceiptService
  ) {
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.receipt.receiptID = params['receiptID'];
      this.receipt.roomID = params['roomID'];

    });


    this.subFriends = this.route.params.subscribe(params => {
      const friendsObservable = this.friendService.getAllFriendsByRoom(this.receipt.roomID);
      friendsObservable.subscribe((data: Friend[]) => {
        this.friends = data;
        this.friends.forEach(friend => this.myMap.set(friend, false))
      })
      return friendsObservable;
    })

    this.selected = 'PLN'
  }

  onSubmit() {
    //console.log(this.receipt)
    this.receiptService.addNewReceipt(this.receipt).subscribe(data => console.log(data), error => console.log(error));

    //dodanie wartosci do bazy
    this.receiptService.addValue(this.receipt.roomID, this.receipt).subscribe(data => console.log("Dodałem wartosc"), error => console.log(error));
    
    this.router.navigate(['home/create/' + this.receipt.roomID]);
  };

  //do obslugi walut
  onChange(selectedCurrency) {
    this.selected = selectedCurrency;
    this.receipt.currency = selectedCurrency.name;
  }

  onChangePaidBy(){
    //this.receipt.paidBy = this.tmpPaidBy;
    console.log("Zaplacil: " + this.receipt.paidBy)
  }

  //do obslugi uczestnikow
  changeSelection(x: Friend) {
    let currentValue = this.myMap.get(x)
    if (currentValue == false)
      this.myMap.set(x, true)
    else
      this.myMap.set(x, false)


    this.receipt.participantsIDs = this.fetchSelectedItems();

  }

  fetchSelectedItems() {
    this.selectedItemsList = []
    for (let [key, value] of this.myMap) {
      if (value == true)
        this.selectedItemsList.push(key)
    }
    return this.selectedItemsList;
  }



}
