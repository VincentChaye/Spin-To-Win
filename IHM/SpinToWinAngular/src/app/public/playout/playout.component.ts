import { Component } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

import { Bet } from '../table/table.component';

@Component({
  selector: 'app-playout',
  templateUrl: './playout.component.html',
  styleUrls: ['./playout.component.css']
})
export class PlayoutComponent {
  
  playerInfo: any;
  pageCharger: number = 1;
  pedro: boolean = false;
  oldCredit : number | undefined;
  tableauparie: Bet[] | undefined; // Utilisation de l'interface Bet
  private reloaderSubject: BehaviorSubject<number> = new BehaviorSubject<number>(1);
  public reloader$: Observable<number> = this.reloaderSubject.asObservable();

  updateReloader(num: number): void {
    this.reloaderSubject.next(num);
  }
}
