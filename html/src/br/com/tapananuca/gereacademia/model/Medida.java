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
@Table(name="MEDIDA")
public class Medida {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medida_seq_gen")
	@SequenceGenerator(name = "medida_seq_gen", sequenceName = "medida_id_seq", 
					   initialValue = 1, allocationSize = 1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="PESSOA_ID")
	private Pessoa pessoa;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_REFERENTE")
	private Date dataReferente;
	
	@Column(name="MA_PESO_CORPORAL")
	private Float maPesoCorporal;
	
	@Column(name="MA_ALTURA")
	private Float maAltura;
	
	@Column(name="MA_CINTURA")
	private Float maCintura;
	
	@Column(name="MA_QUADRIL")
	private Float maQuadril;
	
	@Column(name="MC_TORAX")
	private Float mcTorax;
	
	@Column(name="MC_ABDOMEN")
	private Float mcAbdomen;
	
	@Column(name="MC_CINTURA")
	private Float mcCintura;
	
	@Column(name="MC_BICEPS")
	private Float mcBiceps;
	
	@Column(name="MC_TRICEPS")
	private Float mcTriceps;
	
	@Column(name="MC_COXA")
	private Float mcCoxa;
	
	@Column(name="MC_ANTEBRACO")
	private Float mcAntebraco;
	
	@Column(name="DC_BICEPS")
	private Float dcBiceps;
	
	@Column(name="DC_TRICEPS")
	private Float dcTriceps;
	
	@Column(name="DC_SUB_AXILAR")
	private Float dcSubAxilar;
	
	@Column(name="DC_SUPRA_ILIACAS")
	private Float dcSupraIliacas;
	
	@Column(name="DC_SUB_ESCAPULAR")
	private Float dcSubEscapular;
	
	@Column(name="DC_TORAXICA")
	private Float dcToraxica;
	
	@Column(name="DC_ABDOMINAL")
	private Float dcAbdominal;
	
	@Column(name="DC_COXA")
	private Float dcCoxa;
	
	@Column(name="DC_PERNA")
	private Float dcPerna;

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

	public Date getDataReferente() {
		return dataReferente;
	}

	public void setDataReferente(Date dataReferente) {
		this.dataReferente = dataReferente;
	}

	public Float getMaPesoCorporal() {
		return maPesoCorporal;
	}

	public void setMaPesoCorporal(Float maPesoCorporal) {
		this.maPesoCorporal = maPesoCorporal;
	}

	public Float getMaAltura() {
		return maAltura;
	}

	public void setMaAltura(Float maAltura) {
		this.maAltura = maAltura;
	}

	public Float getMaCintura() {
		return maCintura;
	}

	public void setMaCintura(Float maCintura) {
		this.maCintura = maCintura;
	}

	public Float getMaQuadril() {
		return maQuadril;
	}

	public void setMaQuadril(Float maQuadril) {
		this.maQuadril = maQuadril;
	}

	public Float getMcTorax() {
		return mcTorax;
	}

	public void setMcTorax(Float mcTorax) {
		this.mcTorax = mcTorax;
	}

	public Float getMcAbdomen() {
		return mcAbdomen;
	}

	public void setMcAbdomen(Float mcAbdomen) {
		this.mcAbdomen = mcAbdomen;
	}

	public Float getMcCintura() {
		return mcCintura;
	}

	public void setMcCintura(Float mcCintura) {
		this.mcCintura = mcCintura;
	}

	public Float getMcBiceps() {
		return mcBiceps;
	}

	public void setMcBiceps(Float mcBiceps) {
		this.mcBiceps = mcBiceps;
	}

	public Float getMcTriceps() {
		return mcTriceps;
	}

	public void setMcTriceps(Float mcTriceps) {
		this.mcTriceps = mcTriceps;
	}

	public Float getMcCoxa() {
		return mcCoxa;
	}

	public void setMcCoxa(Float mcCoxa) {
		this.mcCoxa = mcCoxa;
	}

	public Float getMcAntebraco() {
		return mcAntebraco;
	}

	public void setMcAntebraco(Float mcAntebraco) {
		this.mcAntebraco = mcAntebraco;
	}

	public Float getDcBiceps() {
		return dcBiceps;
	}

	public void setDcBiceps(Float dcBiceps) {
		this.dcBiceps = dcBiceps;
	}

	public Float getDcTriceps() {
		return dcTriceps;
	}

	public void setDcTriceps(Float dcTriceps) {
		this.dcTriceps = dcTriceps;
	}

	public Float getDcSubAxilar() {
		return dcSubAxilar;
	}

	public void setDcSubAxilar(Float dcSubAxilar) {
		this.dcSubAxilar = dcSubAxilar;
	}

	public Float getDcSupraIliacas() {
		return dcSupraIliacas;
	}

	public void setDcSupraIliacas(Float dcSupraIliacas) {
		this.dcSupraIliacas = dcSupraIliacas;
	}

	public Float getDcSubEscapular() {
		return dcSubEscapular;
	}

	public void setDcSubEscapular(Float dcSubEscapular) {
		this.dcSubEscapular = dcSubEscapular;
	}

	public Float getDcToraxica() {
		return dcToraxica;
	}

	public void setDcToraxica(Float dcToraxica) {
		this.dcToraxica = dcToraxica;
	}

	public Float getDcAbdominal() {
		return dcAbdominal;
	}

	public void setDcAbdominal(Float dcAbdominal) {
		this.dcAbdominal = dcAbdominal;
	}

	public Float getDcCoxa() {
		return dcCoxa;
	}

	public void setDcCoxa(Float dcCoxa) {
		this.dcCoxa = dcCoxa;
	}

	public Float getDcPerna() {
		return dcPerna;
	}

	public void setDcPerna(Float dcPerna) {
		this.dcPerna = dcPerna;
	}
}
