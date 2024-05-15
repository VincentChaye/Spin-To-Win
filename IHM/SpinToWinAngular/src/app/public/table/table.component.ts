import { Component } from '@angular/core';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {
  nbr: number = 100; // Exemple de crédits, peut être modifié selon votre logique
  jetonSelectionne: number = 1; // Valeur par défaut du jeton

  selectJeton(jeton: number) {
    this.jetonSelectionne = jeton;
    console.log('Jeton sélectionné:', this.jetonSelectionne);
  }
}
