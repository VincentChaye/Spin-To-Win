import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { SocketIoModule, SocketIoConfig } from 'ngx-socket-io';

import { PheaderComponent } from './pheader/pheader.component';
import { PlayoutComponent } from './playout/playout.component';
import { PublicRoutingModule } from './public-routing.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { CreditComponent } from './credit/credit.component';
import { JeuComponent } from './jeu/jeu.component';
import { TableComponent } from './table/table.component';
import { RouletteComponent } from './roulette/roulette.component';
import { TestComponent } from './test/test.component';  // Assurez-vous d'importer TestComponent
 

@NgModule({
  declarations: [
    PheaderComponent,
    PlayoutComponent,
    LoginComponent,
    RegisterComponent,
    CreditComponent,
    JeuComponent,
    TableComponent,
    RouletteComponent,
    TestComponent // Ajouter le TestComponent aux déclarations
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    FormsModule,
    PublicRoutingModule,
  ]
})
export class PublicModule { }
