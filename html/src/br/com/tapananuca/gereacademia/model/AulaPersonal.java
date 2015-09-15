package br.com.tapananuca.gereacademia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="AULA_PERSONAL")
public class AulaPersonal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aula_personal_seq_gen")
	@SequenceGenerator(name = "aula_personal_seq_gen", sequenceName = "aula_personal_id_seq", 
					   initialValue = 1, allocationSize = 1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="PESSOA_ID")
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name="USUARIO_ID")
	private Usuario usuario;
	
	@Column(name="DATA")
	@Temporal(TemporalType.DATE)
	private Date data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
