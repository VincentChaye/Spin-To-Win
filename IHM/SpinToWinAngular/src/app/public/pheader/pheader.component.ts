import { Component } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';

@Component({
  selector: 'app-pheader',
  standalone: false, 
  templateUrl: './pheader.component.html',
  styleUrl: './pheader.component.css'
})
export class PheaderComponent {
  constructor( public PLAYERINFO: PlayoutComponent) {}

afficheJoueur(){
  console.log(this.PLAYERINFO.playerInfo)
}

}
