import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';
import { map } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: false, // Cette propriété standalone est invalide, Angular ne la reconnaît pas
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private _httpClient: HttpClient, public PLAYERINFO: PlayoutComponent) {} // Injection du service HttpClient + liason de avec les valeurs de PlayoutComponent

  private allServerURL = 'http://localhost:8000/player/auth'; // URL de votre API
  username: string = '';
  password: string = '';

  // Méthode appelée lors de la soumission du formulaire
  sendData(): void {
    // Récupérer les valeurs des champs d'entrée
    const username = (document.getElementById('username') as HTMLInputElement).value;
    const password = (document.getElementById('password') as HTMLInputElement).value;

    // Construire l'objet JSON à envoyer au serveur
    const data = {
      pseudo: username,
      motDePasse: password
    };
    console.log(data);

    // Appeler la fonction sendDataToServer avec l'objet JSON en paramètre
    this.sendDataToServer(data);
  }

  // Méthode pour envoyer les données au serveur
  sendDataToServer(data: any) {
    // Définition des en-têtes HTTP
    const headers = new HttpHeaders().set('Content-Type', 'Authorization');

    // Envoi de la requête HTTP PUT
    this._httpClient.put<any>(this.allServerURL, data, { headers: headers })
      .subscribe(
        (response) => {
          console.log('PUT request successful:', response);
          
          // Supprimer la propriété 'mot_de_passe_hash' de l'objet response
          delete response.mot_de_passe_hash;
          
          // Assigner la réponse au joueurInfo dans le composant PlayoutComponent
          this.PLAYERINFO.playerInfo = response;
          
          console.log('trans', this.PLAYERINFO.playerInfo);
        },
        (error) => {
          console.error('PUT request error:', error);
          // Gérer l'erreur si nécessaire
        }
      );
  }

  // // Méthode pour récupérer toutes les données du serveur
  // getAllServers() {
  //   this._httpClient.get(this.allServerURL)
  //     .pipe(
  //       map((response: any) => response.filter((server: any) => server.srv_server_commissionner === true || server.srv_server_commissionner === false)),
  //       map((filteredResponse: any[]) => {
  //         filteredResponse.forEach((server: any) => {
  //           // Traitement supplémentaire des données si nécessaire
  //         });
  //         return filteredResponse;
  //       })
  //     )
  //     .subscribe(filteredResponse => {
  //       // Assigner les données récupérées à une variable ou effectuer un traitement supplémentaire si nécessaire
  //     });
  // }
}
