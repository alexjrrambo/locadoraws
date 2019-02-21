package me.locadoraws.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Alex Junior Rambo 
 */

@Entity
public class Usuario {
	//Annotations utilzados para dizer ao spring que este campo é a primary key e que seu valor é gerado automaticamente
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String email;		
	private String senha;	
	private String nome;	
	private String acessToken;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
	public String getAcessToken() {
		if (acessToken == null)
			return "";
		else
			return acessToken;
	}
	public void setAcessToken(String acessToken) {
		this.acessToken = acessToken;
	}
}
