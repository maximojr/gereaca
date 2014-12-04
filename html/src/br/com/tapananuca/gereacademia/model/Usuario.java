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
@Table(name="USUARIO")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq_gen")
	@SequenceGenerator(name = "usuario_seq_gen", sequenceName = "usuario_id_seq", 
					   initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="SENHA")
	private String senha;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ULTIMO_ACESSO")
	private Date ultimoAcesso;
	
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}
}
