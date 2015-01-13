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
	
	@Column(name="DC_BICEPS_1")
	private Float dcBiceps1;
	
	@Column(name="DC_TRICEPS_1")
	private Float dcTriceps1;
	
	@Column(name="DC_SUB_AXILAR_1")
	private Float dcSubAxilar1;
	
	@Column(name="DC_SUPRA_ILIACAS_1")
	private Float dcSupraIliacas1;
	
	@Column(name="DC_SUB_ESCAPULAR_1")
	private Float dcSubEscapular1;
	
	@Column(name="DC_TORAXICA_1")
	private Float dcToraxica1;
	
	@Column(name="DC_ABDOMINAL_1")
	private Float dcAbdominal1;
	
	@Column(name="DC_COXA_1")
	private Float dcCoxa1;
	
	@Column(name="DC_PERNA_1")
	private Float dcPerna1;
	
	@Column(name="DC_BICEPS_2")
	private Float dcBiceps2;
	
	@Column(name="DC_TRICEPS_2")
	private Float dcTriceps2;
	
	@Column(name="DC_SUB_AXILAR_2")
	private Float dcSubAxilar2;
	
	@Column(name="DC_SUPRA_ILIACAS_2")
	private Float dcSupraIliacas2;
	
	@Column(name="DC_SUB_ESCAPULAR_2")
	private Float dcSubEscapular2;
	
	@Column(name="DC_TORAXICA_2")
	private Float dcToraxica2;
	
	@Column(name="DC_ABDOMINAL_2")
	private Float dcAbdominal2;
	
	@Column(name="DC_COXA_2")
	private Float dcCoxa2;
	
	@Column(name="DC_PERNA_2")
	private Float dcPerna2;
	
	@Column(name="DC_BICEPS_3")
	private Float dcBiceps3;
	
	@Column(name="DC_TRICEPS_3")
	private Float dcTriceps3;
	
	@Column(name="DC_SUB_AXILAR_3")
	private Float dcSubAxilar3;
	
	@Column(name="DC_SUPRA_ILIACAS_3")
	private Float dcSupraIliacas3;
	
	@Column(name="DC_SUB_ESCAPULAR_3")
	private Float dcSubEscapular3;
	
	@Column(name="DC_TORAXICA_3")
	private Float dcToraxica3;
	
	@Column(name="DC_ABDOMINAL_3")
	private Float dcAbdominal3;
	
	@Column(name="DC_COXA_3")
	private Float dcCoxa3;
	
	@Column(name="DC_PERNA_3")
	private Float dcPerna3;

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

	public Float getDcBiceps1() {
		return dcBiceps1;
	}

	public void setDcBiceps1(Float dcBiceps1) {
		this.dcBiceps1 = dcBiceps1;
	}

	public Float getDcTriceps1() {
		return dcTriceps1;
	}

	public void setDcTriceps1(Float dcTriceps1) {
		this.dcTriceps1 = dcTriceps1;
	}

	public Float getDcSubAxilar1() {
		return dcSubAxilar1;
	}

	public void setDcSubAxilar1(Float dcSubAxilar1) {
		this.dcSubAxilar1 = dcSubAxilar1;
	}

	public Float getDcSupraIliacas1() {
		return dcSupraIliacas1;
	}

	public void setDcSupraIliacas1(Float dcSupraIliacas1) {
		this.dcSupraIliacas1 = dcSupraIliacas1;
	}

	public Float getDcSubEscapular1() {
		return dcSubEscapular1;
	}

	public void setDcSubEscapular1(Float dcSubEscapular1) {
		this.dcSubEscapular1 = dcSubEscapular1;
	}

	public Float getDcToraxica1() {
		return dcToraxica1;
	}

	public void setDcToraxica1(Float dcToraxica1) {
		this.dcToraxica1 = dcToraxica1;
	}

	public Float getDcAbdominal1() {
		return dcAbdominal1;
	}

	public void setDcAbdominal1(Float dcAbdominal1) {
		this.dcAbdominal1 = dcAbdominal1;
	}

	public Float getDcCoxa1() {
		return dcCoxa1;
	}

	public void setDcCoxa1(Float dcCoxa1) {
		this.dcCoxa1 = dcCoxa1;
	}

	public Float getDcPerna1() {
		return dcPerna1;
	}

	public void setDcPerna1(Float dcPerna1) {
		this.dcPerna1 = dcPerna1;
	}

	public Float getDcBiceps2() {
		return dcBiceps2;
	}

	public void setDcBiceps2(Float dcBiceps2) {
		this.dcBiceps2 = dcBiceps2;
	}

	public Float getDcTriceps2() {
		return dcTriceps2;
	}

	public void setDcTriceps2(Float dcTriceps2) {
		this.dcTriceps2 = dcTriceps2;
	}

	public Float getDcSubAxilar2() {
		return dcSubAxilar2;
	}

	public void setDcSubAxilar2(Float dcSubAxilar2) {
		this.dcSubAxilar2 = dcSubAxilar2;
	}

	public Float getDcSupraIliacas2() {
		return dcSupraIliacas2;
	}

	public void setDcSupraIliacas2(Float dcSupraIliacas2) {
		this.dcSupraIliacas2 = dcSupraIliacas2;
	}

	public Float getDcSubEscapular2() {
		return dcSubEscapular2;
	}

	public void setDcSubEscapular2(Float dcSubEscapular2) {
		this.dcSubEscapular2 = dcSubEscapular2;
	}

	public Float getDcToraxica2() {
		return dcToraxica2;
	}

	public void setDcToraxica2(Float dcToraxica2) {
		this.dcToraxica2 = dcToraxica2;
	}

	public Float getDcAbdominal2() {
		return dcAbdominal2;
	}

	public void setDcAbdominal2(Float dcAbdominal2) {
		this.dcAbdominal2 = dcAbdominal2;
	}

	public Float getDcCoxa2() {
		return dcCoxa2;
	}

	public void setDcCoxa2(Float dcCoxa2) {
		this.dcCoxa2 = dcCoxa2;
	}

	public Float getDcPerna2() {
		return dcPerna2;
	}

	public void setDcPerna2(Float dcPerna2) {
		this.dcPerna2 = dcPerna2;
	}

	public Float getDcBiceps3() {
		return dcBiceps3;
	}

	public void setDcBiceps3(Float dcBiceps3) {
		this.dcBiceps3 = dcBiceps3;
	}

	public Float getDcTriceps3() {
		return dcTriceps3;
	}

	public void setDcTriceps3(Float dcTriceps3) {
		this.dcTriceps3 = dcTriceps3;
	}

	public Float getDcSubAxilar3() {
		return dcSubAxilar3;
	}

	public void setDcSubAxilar3(Float dcSubAxilar3) {
		this.dcSubAxilar3 = dcSubAxilar3;
	}

	public Float getDcSupraIliacas3() {
		return dcSupraIliacas3;
	}

	public void setDcSupraIliacas3(Float dcSupraIliacas3) {
		this.dcSupraIliacas3 = dcSupraIliacas3;
	}

	public Float getDcSubEscapular3() {
		return dcSubEscapular3;
	}

	public void setDcSubEscapular3(Float dcSubEscapular3) {
		this.dcSubEscapular3 = dcSubEscapular3;
	}

	public Float getDcToraxica3() {
		return dcToraxica3;
	}

	public void setDcToraxica3(Float dcToraxica3) {
		this.dcToraxica3 = dcToraxica3;
	}

	public Float getDcAbdominal3() {
		return dcAbdominal3;
	}

	public void setDcAbdominal3(Float dcAbdominal3) {
		this.dcAbdominal3 = dcAbdominal3;
	}

	public Float getDcCoxa3() {
		return dcCoxa3;
	}

	public void setDcCoxa3(Float dcCoxa3) {
		this.dcCoxa3 = dcCoxa3;
	}

	public Float getDcPerna3() {
		return dcPerna3;
	}

	public void setDcPerna3(Float dcPerna3) {
		this.dcPerna3 = dcPerna3;
	}
}
