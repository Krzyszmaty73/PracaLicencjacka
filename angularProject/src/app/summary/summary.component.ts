import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SummaryService } from '../services/summary.service';
import { roomSummary } from '../roomSummary'

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {

  roomID: number;
  roomS: roomSummary[];
  subRoom: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private summaryService: SummaryService
  ) { }

  ngOnInit(): void {

    this.subRoom = this.route.params.subscribe(params => {
      this.roomID = params['roomID'];


      const summaryObservable = this.summaryService.getAllParticipantsByRoom(this.roomID);
      summaryObservable.subscribe((data: roomSummary[]) => {
        this.roomS = data;
        console.log(data)
      })
      return summaryObservable;
    })

  }

  homeNavigation(){
    this.router.navigate(['home']);
  }

}
