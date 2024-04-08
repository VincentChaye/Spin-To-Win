// public.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PheaderComponent } from './pheader/pheader.component';
import { PlayoutComponent } from './playout/playout.component';
import { PublicRoutingModule } from './public-routing.module';

@NgModule({
  declarations: [
    PheaderComponent,
    PlayoutComponent
  ],
  imports: [
    CommonModule,
    RouterModule, // Assurez-vous que RouterModule est importé
    PublicRoutingModule // Assurez-vous que PublicRoutingModule est importé
  ]
})
export class PublicModule { }
