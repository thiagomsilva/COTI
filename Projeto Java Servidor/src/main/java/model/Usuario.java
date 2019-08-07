package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer idUsuario;
	@Column
	private String nome;
	@Column(unique=true)
	private String login;
	@Column
	private String senha;
	@Column
	private String token;
	
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getToken() {
		return this.token;
	}
	
}
