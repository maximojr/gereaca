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

import br.com.tapananuca.gereacademia.comunicacao.Dieta;
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
	private String numero;
	
	@Column(name="BAIRRO")
	private String bairro;
	
	@Column(name="TELEFONE")
	private String telefone;
	
	@Column(name="ESTADO_CIVIL")
	private EstadoCivil estadoCivil;
	
	//objetivos
	@Column(name="ESTETICA")
	private Boolean estetica;
	
	@Column(name="LAZER")
	private Boolean lazer;
	
	@Column(name="SAUDE")
	private Boolean saude;
	
	@Column(name="TERAPEUTICO")
	private Boolean terapeutico;
	
	@Column(name="COND_FISICO")
	private Boolean condFisico;
	
	@Column(name="PREP_FISICA")
	private Boolean prepFisica;
	
	@Column(name="AUTO_REND")
	private Boolean autoRend;
	
	@Column(name="HIPERTROFIA")
	private Boolean hipertrofia;
	
	//historia patologica
	@Column(name="CIRURGIAS")
	private String cirurgias;
	
	@Column(name="SINTOMAS_DOENCAS")
	private String sintomasDoencas;
	
	@Column(name="MEDICAMENTOS")
	private String medicamentos;
	
	@Column(name="LESOES")
	private String lesoes;
	
	@Column(name="ALERGIAS")
	private String alergias;
	
	@Column(name="OUTROS")
	private String outros;
	
	@Column(name="CARDIOPATIA")
	private Boolean cardiopatia;
	
	@Column(name="HIPERTENSAO")
	private Boolean hipertensao;
	
	//habitos
	@Column(name="DIETA")
	private Dieta dieta;
	
	@Column(name="PRATICA_ATIV_FISICA")
	private String praticaAtivFisica;
	
	@Column(name="DATA_ULTIMO_EXAME_MEDICO")
	@Temporal(TemporalType.DATE)
	private Date dataUltimoExameMedico;
	
	@Column(name="PERIODO_EXAME_MEDICO")
	private Integer periodExameMedico;
	
	@Column(name="LEMBRETE")
	private String lembrete;

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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
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

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Boolean getEstetica() {
		return estetica;
	}

	public void setEstetica(Boolean estetica) {
		this.estetica = estetica;
	}

	public Boolean getLazer() {
		return lazer;
	}

	public void setLazer(Boolean lazer) {
		this.lazer = lazer;
	}

	public Boolean getSaude() {
		return saude;
	}

	public void setSaude(Boolean saude) {
		this.saude = saude;
	}

	public Boolean getTerapeutico() {
		return terapeutico;
	}

	public void setTerapeutico(Boolean terapeutico) {
		this.terapeutico = terapeutico;
	}

	public Boolean getCondFisico() {
		return condFisico;
	}

	public void setCondFisico(Boolean condFisico) {
		this.condFisico = condFisico;
	}

	public Boolean getPrepFisica() {
		return prepFisica;
	}

	public void setPrepFisica(Boolean prepFisica) {
		this.prepFisica = prepFisica;
	}

	public Boolean getAutoRend() {
		return autoRend;
	}

	public void setAutoRend(Boolean autoRend) {
		this.autoRend = autoRend;
	}

	public Boolean getHipertrofia() {
		return hipertrofia;
	}

	public void setHipertrofia(Boolean hipertrofia) {
		this.hipertrofia = hipertrofia;
	}

	public String getCirurgias() {
		return cirurgias;
	}

	public void setCirurgias(String cirurgias) {
		this.cirurgias = cirurgias;
	}

	public String getSintomasDoencas() {
		return sintomasDoencas;
	}

	public void setSintomasDoencas(String sintomasDoencas) {
		this.sintomasDoencas = sintomasDoencas;
	}

	public String getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(String medicamentos) {
		this.medicamentos = medicamentos;
	}

	public String getLesoes() {
		return lesoes;
	}

	public void setLesoes(String lesoes) {
		this.lesoes = lesoes;
	}

	public String getAlergias() {
		return alergias;
	}

	public void setAlergias(String alergias) {
		this.alergias = alergias;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	public Boolean getCardiopatia() {
		return cardiopatia;
	}

	public void setCardiopatia(Boolean cardiopatia) {
		this.cardiopatia = cardiopatia;
	}

	public Boolean getHipertensao() {
		return hipertensao;
	}

	public void setHipertensao(Boolean hipertensao) {
		this.hipertensao = hipertensao;
	}

	public Dieta getDieta() {
		return dieta;
	}

	public void setDieta(Dieta dieta) {
		this.dieta = dieta;
	}

	public String getPraticaAtivFisica() {
		return praticaAtivFisica;
	}

	public void setPraticaAtivFisica(String praticaAtivFisica) {
		this.praticaAtivFisica = praticaAtivFisica;
	}

	public Date getDataUltimoExameMedico() {
		return dataUltimoExameMedico;
	}

	public void setDataUltimoExameMedico(Date dataUltimoExameMedico) {
		this.dataUltimoExameMedico = dataUltimoExameMedico;
	}

	public Integer getPeriodExameMedico() {
		return periodExameMedico;
	}

	public void setPeriodExameMedico(Integer periodExameMedico) {
		this.periodExameMedico = periodExameMedico;
	}

	public String getLembrete() {
		return lembrete;
	}

	public void setLembrete(String lembrete) {
		this.lembrete = lembrete;
	}
}
