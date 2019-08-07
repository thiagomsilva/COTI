import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppUsuarioNovoComponent } from './usuario/app.usuario.novocomponent';
import { AppUsuarioListarComponent } from './usuario/app.usuario.listarcomponent';

import { AppFuncionarioNovoComponent } from './funcionario/app.funcionario.novocomponent';

@NgModule({
  declarations: [
    AppComponent,
    AppUsuarioNovoComponent,
    AppUsuarioListarComponent,
    AppFuncionarioNovoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
