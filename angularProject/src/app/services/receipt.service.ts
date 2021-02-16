import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Receipt } from '../receipt';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class ReceiptService {

  receipt: Receipt[] = []

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getReceipt(roomNumber: number): any {
    const titleObservable = new Observable(observer => {
      observer.next(this.selectReceiptsByRoomID(roomNumber));
    });
    return titleObservable;
  }

  private selectReceiptsByRoomID(roomNumber: number): Receipt[] {
    let selectedReceipts = this.receipt.filter(selected => { 
      if (selected.roomID == roomNumber) return true; else false });
    return selectedReceipts
  }

  addNewReceipt(newReceipt: Receipt) {
    this.receipt.push(newReceipt);
    return this.http.post<any>(`${this.apiUrl}/receipts`, newReceipt);
  }

  getAllReceiptsByRoom(roomNumber: number): any  {
    return this.http.get<Receipt[]>(`${this.apiUrl}/receipts/room/${roomNumber}`);
  }

  addValue(roomID: number, receiptInfo: Receipt){
    return this.http.patch<any>(`${this.apiUrl}/updateRoom/value/${roomID}`, receiptInfo);
  }

}
