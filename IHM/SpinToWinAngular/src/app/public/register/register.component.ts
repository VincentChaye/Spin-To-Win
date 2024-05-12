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
  Age: number | undefined;
  Username: string = '';
  Password: string = '';

  submitForm(): void {
    const url = 'http://localhost:8000/player/new';
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    const body = {
      pseudo: this.Username,
      nom: this.Nom,
      prenom: this.Prenom,
      email: this.Email,
      dateNaissance: this.Age ? new Date(this.Age).toISOString().split('T')[0] : null,
      credit: 100.0,
      mot_de_passe_hash: this.Password
    };

    this.httpClient.post(url, body, { headers })
      .subscribe(
        (response: any) => {
          console.log('API POST réussi :', response);
        },
        (error: any) => {
          console.error('Erreur lors de l\'appel API POST :', error);
        }
      );
  }
}
