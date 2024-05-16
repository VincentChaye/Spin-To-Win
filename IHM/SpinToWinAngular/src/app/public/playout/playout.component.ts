import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PheaderComponent } from '../pheader/pheader.component';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Component({
  selector: 'app-playout',
  templateUrl: './playout.component.html',
  styleUrls: ['./playout.component.css']
})
@Injectable()
export class PlayoutComponent {
  
  playerInfo : any;
  pageCharger : number = 1;
  private reloaderSubject: BehaviorSubject<number> = new BehaviorSubject<number>(1);
  public reloader$: Observable<number> = this.reloaderSubject.asObservable();

  updateReloader(num: number): void {
    this.reloaderSubject.next(num);
  }
}

