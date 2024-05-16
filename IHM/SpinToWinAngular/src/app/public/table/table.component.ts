import { Component } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {
  
  constructor(public PLAYERINFO: PlayoutComponent) {
    // Définir PLAYERINFO.pageCharger à 1 lorsque le composant est chargé
    this.PLAYERINFO.pageCharger = 0;
  }
  
  nbr: number = 100; // Exemple de crédits, peut être modifié selon votre logique
  jetonSelectionne: number = 1; // Valeur par défaut du jeton

  selectJeton(jeton: number) {
    this.jetonSelectionne = jeton;
    console.log('Jeton sélectionné:', this.jetonSelectionne);
  }
}
