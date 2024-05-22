import { Component } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';

@Component({
  selector: 'app-credit', 
 
  templateUrl: './credit.component.html',
  styleUrls: ['./credit.component.css']
})
export class CreditComponent {
 
  constructor( public PLAYERINFO: PlayoutComponent) {}
  
  deconexion() {
    this.PLAYERINFO.playerInfo = null; // Vider playerInfo
  }

}
