import { Component, OnInit, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Usuario } from './Usuario';

@Component({
    templateUrl : './template/app.usuario.listarcomponent.html'
})
@Injectable()
export class AppUsuarioListarComponent implements OnInit{

    lista:Usuario[];

    constructor(private http:HttpClient){

    }

    ngOnInit():void{
        this.http.get('http://localhost:8082/aula3java/usuario/listar',{
            responseType: 'text' 
        })
        .subscribe(value => {
            console.log(value);
            this.lista = JSON.parse(value);
        });
            
    }

}