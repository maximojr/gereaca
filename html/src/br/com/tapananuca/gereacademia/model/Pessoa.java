package br.com.tapananuca.gereacademia.model;

import java.math.BigDecimal;
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

import br.com.tapananuca.gereacademia.comunicacao.EstadoCivil;

/**
 * Dados referentes a endereço e telefone deviam estar tabelas separadas,
 * mas por enquanto não há necessidade
 */
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
	@Column(name="DATA_NASC")
	private Date dataNascimento;
	
	@Temporal(TemporalType.DATE)
	@Column(name="INICIO")
	private Date inicio;
	
	@Column(name="ATIVO")
	private boolean ativo;
	
	@Column(name="VALOR_MENSAL")
	private BigDecimal valorMensal;
	
	@Column(name="ENDERECO")
	private String endereco;
	
	@Column(name="NUMERO")
	private Integer numero;
	
	@Column(name="BAIRRO")
	private String bairro;
	
	@Column(name="TELEFONE")
	private String telefone;
	
	@Column(name="ESTADO_CIVIL")
	private EstadoCivil estadoCivil;
	
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

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public BigDecimal getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(BigDecimal valorMensal) {
		this.valorMensal = valorMensal;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
