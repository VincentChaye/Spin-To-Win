import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private httpClient: HttpClient, public PLAYERINFO: PlayoutComponent) {}

  Nom: string = '';
  Prenom: string = '';
  Email: string = '';
  Age: string = ''; // Initialisation avec une chaîne vide

  Username: string = '';
  Password: string = '';
  testdate(){
    console.log(this.Age)
  }

  submitForm(): void {
    const url = 'http://localhost:8000/player/new';
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
  
    // Convertir la date au format ISO
    let dateNaissanceISO: string | null = null;
    if (typeof this.Age === 'string') {
      const parts = this.Age.split('-');
      const year = parseInt(parts[0], 10);
      const month = parseInt(parts[1], 10) - 1; // Les mois sont indexés à partir de zéro (0 = janvier, 1 = février, etc.)
      const day = parseInt(parts[2], 10);
      const date = new Date(year, month, day);
      dateNaissanceISO = date.toISOString().split('T')[0];
    }
  
    // Créer le corps de la requête
    const body = {
      pseudo: this.Username,
      nom: this.Nom,
      prenom: this.Prenom,
      email: this.Email,
      dateNaissance: dateNaissanceISO,
      credit: 100.0,
      mot_de_passe_hash: this.Password
    };
  
    // Envoyer la requête HTTP POST
    this.httpClient.post(url, body, { headers })
      .subscribe(
        (response: any) => {
          response.dateNaissance = new Date(response.dateNaissance).toISOString().split('T')[0];
          console.log('API POST réussi :', response);
           this.PLAYERINFO.playerInfo = response;
        },
        (error: any) => {
          console.error('Erreur lors de l\'appel API POST :', error);
        }
      );
  }
  
  
}
