// public-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PlayoutComponent } from './playout/playout.component';

// Routage des pages du module Public 
const routes: Routes = [
  {
    path: '', component: PlayoutComponent, children: [ 
      { path: '', redirectTo: 'home', pathMatch: 'full' }, // Redirige vers la page d'accueil lorsque le chemin est vide
      { path: 'home', component: HomeComponent }, // Charge le composant HomeComponent lorsque le chemin est '/home'
    ]
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
