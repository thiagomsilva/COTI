import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppUsuarioNovoComponent } from './usuario/app.usuario.novocomponent';
import { AppUsuarioListarComponent } from './usuario/app.usuario.listarcomponent';

import { AppFuncionarioNovoComponent } from './funcionario/app.funcionario.novocomponent';

//Vamos criar nossas rotas
const routes: Routes = [
  { path : 'novo', component: AppUsuarioNovoComponent },
  { path : 'listar', component: AppUsuarioListarComponent },
  { path : 'funcionario/novo', component: AppFuncionarioNovoComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
