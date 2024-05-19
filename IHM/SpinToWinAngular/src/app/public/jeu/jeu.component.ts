import { Component } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';

@Component({
  selector: 'app-jeu',
  templateUrl: './jeu.component.html',
  styleUrl: './jeu.component.css'
})
export class JeuComponent {
  constructor(public PLAYERINFO: PlayoutComponent) {
    // Définir PLAYERINFO.pageCharger à 1 lorsque le composant est chargé
    this.PLAYERINFO.pageCharger = 1;
  }
}
