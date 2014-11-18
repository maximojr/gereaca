package br.com.tapananuca.gereacademia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PESSOA")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq_gen")
	@SequenceGenerator(name = "pessoa_seq_gen", sequenceName = "pessoa_id_seq", 
					   initialValue = 1, allocationSize = 1)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="SEXO")
	private Character sexo;
	
	@Column(name="EMAIL")
	private String email;
	
	@Temporal(TemporalType.DATE)
	@Column(name="ANIVERSARIO")
	private Date aniversario;
	
	@Temporal(TemporalType.DATE)
	@Column(name="INICIO")
	private Date inicio;
	
	@Column(name="ATIVO")
	private boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}