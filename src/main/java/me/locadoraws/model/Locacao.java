package me.locadoraws.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Alex Junior Rambo 
 */

@Entity
public class Locacao {
	
	//Annotations utilzados para dizer ao spring que este campo é a primary key e que seu valor é gerado automaticamente
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int idFilme;
	private int idUsuario;
	//Locado ou Devolvido
	private String Situacao;
	
	public Locacao() {
				
	}
	public Locacao(int idFilme, int idUsuario, String situacao) {		
		this.setIdFilme(idFilme);
		this.setIdUsuario(idUsuario);
		this.setSituacao(situacao);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdFilme() {
		return idFilme;
	}
	public void setIdFilme(int idFilme) {
		this.idFilme = idFilme;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getSituacao() {
		return Situacao;
	}
	public void setSituacao(String situacao) {
		Situacao = situacao;
	}	
}
