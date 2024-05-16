import { Component } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';

@Component({
  selector: 'app-vegastudio', 
  standalone: false, 
  templateUrl: './vegastudio.component.html',
  styleUrls: ['./vegastudio.component.css']
})
export class VegastudioComponent {
  constructor(public PLAYERINFO: PlayoutComponent) {
    // Définir PLAYERINFO.pageCharger à 1 lorsque le composant est chargé
    this.PLAYERINFO.pageCharger = 1;
  }
}
