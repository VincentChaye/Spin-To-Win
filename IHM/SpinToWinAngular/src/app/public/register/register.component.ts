import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  constructor(private httpClient: HttpClient, public PLAYERINFO: PlayoutComponent, private router: Router) {}

  pseudos: string[] = [];
  emails: string[] = [];
  Nom: string = '';
  Prenom: string = '';
  Email: string = '';
  Age: string = '';
  Username: string = '';
  Password: string = '';
  isPseudoIncluded: boolean = false;
  isEmailIncluded: boolean = false;

  ngOnInit(): void {
    this.getAllPseudo();
    this.getAllEmails();
  }

  getAllEmails() {
    const url = 'http://localhost:8000/player/mail';
    this.httpClient.get<string[]>(url).subscribe(
      (response: string[]) => {
        this.emails = response;
        this.checkEmailValidity();
      },
      (error: any) => {
        console.error('Une erreur s\'est produite :', error);
      }
    );
  }

  checkEmailValidity() {
    this.isEmailIncluded = this.emails.includes(this.Email);
  }

  getAllPseudo() {
    const url = 'http://localhost:8000/player/pseudo';
    this.httpClient.get<string[]>(url).subscribe(
      (response: string[]) => {
        this.pseudos = response;
        this.checkPseudoValidity();
      },
      (error: any) => {
        console.error('Une erreur s\'est produite :', error);
      }
    );
  }

  checkPseudoValidity() {
    this.isPseudoIncluded = !this.pseudos.includes(this.Username);
  }

  isValidEmail(email: string): boolean {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
  }

  isFormValid(): boolean {
    return !!this.Nom && !!this.Prenom && this.isValidEmail(this.Email) && !!this.Age && !!this.Username && !!this.Password && this.isPseudoIncluded && !this.isEmailIncluded;
  }

  submitForm(event: Event): void {
    event.preventDefault();

    if (!this.isPseudoIncluded) {
      console.log('Le pseudo est déjà inclus dans la liste.');
      return;
    }

    if (this.isEmailIncluded) {
      console.log('L\'adresse email est déjà enregistrée.');
      return;
    }

    const url = 'http://localhost:8000/player/new';
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    let dateNaissanceISO: string | null = null;
    if (typeof this.Age === 'string') {
      const parts = this.Age.split('-');
      const year = parseInt(parts[0], 10);
      const month = parseInt(parts[1], 10) - 1;
      const day = parseInt(parts[2], 10);
      const date = new Date(year, month, day);
      dateNaissanceISO = date.toISOString().split('T')[0];
    }

    const body = {
      pseudo: this.Username,
      nom: this.Nom,
      prenom: this.Prenom,
      email: this.Email,
      dateNaissance: dateNaissanceISO,
      credit: 100.0,
      mot_de_passe_hash: this.Password
    };

    this.httpClient.post(url, body, { headers }).subscribe(
      (response: any) => {
        response.dateNaissance = new Date(response.dateNaissance).toISOString().split('T')[0];
        console.log('API POST réussi :', response);
        this.PLAYERINFO.playerInfo = response;
        this.router.navigate(['/Jeu']);
      },
      (error: any) => {
        console.error('Erreur lors de l\'appel API POST :', error);
      }
    );
  }

  isEmailTouched: boolean = false;
}
