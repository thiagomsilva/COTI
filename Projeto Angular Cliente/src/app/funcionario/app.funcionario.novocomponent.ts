import { Component, OnInit, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Funcionario } from './Funcionario';

@Component({
    templateUrl : './template/app.funcionario.novocomponent.html'
})
@Injectable()
export class AppFuncionarioNovoComponent {
    resp:any = '';
    

    constructor(private http:HttpClient){

    }

}