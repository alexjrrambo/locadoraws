package me.locadoraws.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Alex Junior Rambo 
 */

@Entity
public class Filme {
	
	//Annotations utilzados para dizer ao spring que este campo é uma primary key e que seu valor é gerado automaticamente
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String titulo;
	private String diretor;
	private int quantidade;
	private int quantidadeDisponivel;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDiretor() {
		return diretor;
	}
	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}
	public void setQuantidadeDisponivel(int quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}	
}
