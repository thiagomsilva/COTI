import { Component, OnInit, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Usuario } from './Usuario';

@Component({
    templateUrl : './template/app.usuario.novocomponent.html'
})
@Injectable()
export class AppUsuarioNovoComponent implements OnInit {
    resp:any = '';
    usuario:Usuario = {
        id : null, nome : "Usuario 01", senha : null,
            login : null
    };

    constructor(private http:HttpClient){

    }

    ngOnInit():void{
        console.log("UsuarioNovoComponent executado");
    }

    enviar():void{
        console.log("Login: " + this.usuario.login);

        let body = new HttpParams()
            .set('login', this.usuario.login.toString())
            .set('senha', this.usuario.senha.toString())
            .set('nome', this.usuario.nome.toString());

        this.http.post("http://localhost:8082/aula3java/usuario/cadastrar", body,
        {
            headers: new HttpHeaders()
                .set('Content-Type', 'application/x-www-form-urlencoded'),
            responseType: 'text' 
          })
        .subscribe( 
        value => {
            console.log(value);
            this.resp = value;
        },
        err => {
            console.log('Error: ' + JSON.stringify(err));
        });
    }

}