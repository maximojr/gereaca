package br.com.tapananuca.gereacademia.comunicacao;

import java.math.BigDecimal;

import br.com.tapananuca.gereacademia.Utils;


public class MedidaDTO implements GARequest {

	private String idPessoa;
	
	private String dataReferente;
	
	private String maPesoCorporal;
	private String maAltura;
	private String maPesoMagro;
	private String maPesoGordura;
	private String maPorcentagemPG;
	private String maImc;
	private String maCintura;
	private String maQuadril;
	private String maPmrc;
	
	private String mcTorax;
	private String mcAbdomen;
	private String mcCintura;
	private String mcBiceps;
	private String mcTriceps;
	private String mcCoxa;
	private String mcAntebraco;
	
	private String dcBiceps;
	private String dcTriceps;
	private String dcSubAxilar;
	private String dcSupraIliacas;
	private String dcSubEscapular;
	private String dcToraxica;
	private String dcAbdominal;
	private String dcCoxa;
	private String dcPerna;
	
	public MedidaDTO() {}
	
	public MedidaDTO(BigDecimal maPesoCorporal, BigDecimal maAltura,
			BigDecimal maPesoMagro, BigDecimal maPesoGordura, BigDecimal maPorcentagemPG,
			BigDecimal maImc, BigDecimal maCintura, BigDecimal maQuadril, BigDecimal maPmrc,
			BigDecimal mcTorax, BigDecimal mcAbdomen, BigDecimal mcCintura,
			BigDecimal mcBiceps, BigDecimal mcTriceps, BigDecimal mcCoxa,
			BigDecimal mcAntebraco, BigDecimal dcBiceps, BigDecimal dcTriceps,
			BigDecimal dcSubAxilar, BigDecimal dcSupraIliacas, BigDecimal dcSubEscapular,
			BigDecimal dcToraxica, BigDecimal dcAbdominal, BigDecimal dcCoxa, BigDecimal dcPerna) {
		
		this.maPesoCorporal = this.nullOrString(maPesoCorporal);
		this.maAltura = this.nullOrString(maAltura);
		this.maPesoMagro = this.nullOrString(maPesoMagro);
		this.maPesoGordura = this.nullOrString(maPesoGordura);
		this.maPorcentagemPG = this.nullOrString(maPorcentagemPG);
		this.maImc = this.nullOrString(maImc);
		this.maCintura = this.nullOrString(maCintura);
		this.maQuadril = this.nullOrString(maQuadril);
		this.maPmrc = this.nullOrString(maPmrc);
		this.mcTorax = this.nullOrString(mcTorax);
		this.mcAbdomen = this.nullOrString(mcAbdomen);
		this.mcCintura = this.nullOrString(mcCintura);
		this.mcBiceps = this.nullOrString(mcBiceps);
		this.mcTriceps = this.nullOrString(mcTriceps);
		this.mcCoxa = this.nullOrString(mcCoxa);
		this.mcAntebraco = this.nullOrString(mcAntebraco);
		this.dcBiceps = this.nullOrString(dcBiceps);
		this.dcTriceps = this.nullOrString(dcTriceps);
		this.dcSubAxilar = this.nullOrString(dcSubAxilar);
		this.dcSupraIliacas = this.nullOrString(dcSupraIliacas);
		this.dcSubEscapular = this.nullOrString(dcSubEscapular);
		this.dcToraxica = this.nullOrString(dcToraxica);
		this.dcAbdominal = this.nullOrString(dcAbdominal);
		this.dcCoxa = this.nullOrString(dcCoxa);
		this.dcPerna = this.nullOrString(dcPerna);
	}
	
	private String nullOrString(BigDecimal valor){
		
		return valor == null ? null : String.valueOf(valor);
	}
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "idPessoa", idPessoa);
		utils.addStringToJson(json, "dataReferente", dataReferente);
		
		utils.addStringToJson(json, "maPesoCorporal", maPesoCorporal);
		utils.addStringToJson(json, "maAltura", maAltura);
		utils.addStringToJson(json, "maPesoMagro", maPesoMagro);
		utils.addStringToJson(json, "maPesoGordura", maPesoGordura);
		utils.addStringToJson(json, "maPorcentagemPG", maPorcentagemPG);
		utils.addStringToJson(json, "maImc", maImc);
		utils.addStringToJson(json, "maCintura", maCintura);
		utils.addStringToJson(json, "maQuadril", maQuadril);
		utils.addStringToJson(json, "maPmrc", maPmrc);
		
		utils.addStringToJson(json, "mcTorax", mcTorax);
		utils.addStringToJson(json, "mcAbdomen", mcAbdomen);
		utils.addStringToJson(json, "mcCintura", mcCintura);
		utils.addStringToJson(json, "mcBiceps", mcBiceps);
		utils.addStringToJson(json, "mcTriceps", mcTriceps);
		utils.addStringToJson(json, "mcCoxa", mcCoxa);
		utils.addStringToJson(json, "mcAntebraco", mcAntebraco);
		
		utils.addStringToJson(json, "dcBiceps", dcBiceps);
		utils.addStringToJson(json, "dcTriceps", dcTriceps);
		utils.addStringToJson(json, "dcSubAxilar", dcSubAxilar);
		utils.addStringToJson(json, "dcSupraIliacas", dcSupraIliacas);
		utils.addStringToJson(json, "dcSubEscapular", dcSubEscapular);
		utils.addStringToJson(json, "dcToraxica", dcToraxica);
		utils.addStringToJson(json, "dcAbdominal", dcAbdominal);
		utils.addStringToJson(json, "dcCoxa", dcCoxa);
		utils.addStringToJson(json, "dcPerna", dcPerna);
		
		json.append("}");
		
		return json.toString();
	}

	public String getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getDataReferente() {
		return dataReferente;
	}

	public void setDataReferente(String dataReferente) {
		this.dataReferente = dataReferente;
	}

	public String getMaPesoCorporal() {
		return maPesoCorporal;
	}

	public void setMaPesoCorporal(String maPesoCorporal) {
		this.maPesoCorporal = maPesoCorporal;
	}

	public String getMaAltura() {
		return maAltura;
	}

	public void setMaAltura(String maAltura) {
		this.maAltura = maAltura;
	}

	public String getMaPesoMagro() {
		return maPesoMagro;
	}

	public void setMaPesoMagro(String maPesoMagro) {
		this.maPesoMagro = maPesoMagro;
	}

	public String getMaPesoGordura() {
		return maPesoGordura;
	}

	public void setMaPesoGordura(String maPesoGordura) {
		this.maPesoGordura = maPesoGordura;
	}

	public String getMaPorcentagemPG() {
		return maPorcentagemPG;
	}

	public void setMaPorcentagemPG(String maPorcentagemPG) {
		this.maPorcentagemPG = maPorcentagemPG;
	}

	public String getMaImc() {
		return maImc;
	}

	public void setMaImc(String maImc) {
		this.maImc = maImc;
	}

	public String getMaCintura() {
		return maCintura;
	}

	public void setMaCintura(String maCintura) {
		this.maCintura = maCintura;
	}

	public String getMaQuadril() {
		return maQuadril;
	}

	public void setMaQuadril(String maQuadril) {
		this.maQuadril = maQuadril;
	}

	public String getMaPmrc() {
		return maPmrc;
	}

	public void setMaPmrc(String maPmrc) {
		this.maPmrc = maPmrc;
	}

	public String getMcTorax() {
		return mcTorax;
	}

	public void setMcTorax(String mcTorax) {
		this.mcTorax = mcTorax;
	}

	public String getMcAbdomen() {
		return mcAbdomen;
	}

	public void setMcAbdomen(String mcAbdomen) {
		this.mcAbdomen = mcAbdomen;
	}

	public String getMcCintura() {
		return mcCintura;
	}

	public void setMcCintura(String mcCintura) {
		this.mcCintura = mcCintura;
	}

	public String getMcBiceps() {
		return mcBiceps;
	}

	public void setMcBiceps(String mcBiceps) {
		this.mcBiceps = mcBiceps;
	}

	public String getMcTriceps() {
		return mcTriceps;
	}

	public void setMcTriceps(String mcTriceps) {
		this.mcTriceps = mcTriceps;
	}

	public String getMcCoxa() {
		return mcCoxa;
	}

	public void setMcCoxa(String mcCoxa) {
		this.mcCoxa = mcCoxa;
	}

	public String getMcAntebraco() {
		return mcAntebraco;
	}

	public void setMcAntebraco(String mcAntebraco) {
		this.mcAntebraco = mcAntebraco;
	}

	public String getDcBiceps() {
		return dcBiceps;
	}

	public void setDcBiceps(String dcBiceps) {
		this.dcBiceps = dcBiceps;
	}

	public String getDcTriceps() {
		return dcTriceps;
	}

	public void setDcTriceps(String dcTriceps) {
		this.dcTriceps = dcTriceps;
	}

	public String getDcSubAxilar() {
		return dcSubAxilar;
	}

	public void setDcSubAxilar(String dcSubAxilar) {
		this.dcSubAxilar = dcSubAxilar;
	}

	public String getDcSupraIliacas() {
		return dcSupraIliacas;
	}

	public void setDcSupraIliacas(String dcSupraIliacas) {
		this.dcSupraIliacas = dcSupraIliacas;
	}

	public String getDcSubEscapular() {
		return dcSubEscapular;
	}

	public void setDcSubEscapular(String dcSubEscapular) {
		this.dcSubEscapular = dcSubEscapular;
	}

	public String getDcToraxica() {
		return dcToraxica;
	}

	public void setDcToraxica(String dcToraxica) {
		this.dcToraxica = dcToraxica;
	}

	public String getDcAbdominal() {
		return dcAbdominal;
	}

	public void setDcAbdominal(String dcAbdominal) {
		this.dcAbdominal = dcAbdominal;
	}

	public String getDcCoxa() {
		return dcCoxa;
	}

	public void setDcCoxa(String dcCoxa) {
		this.dcCoxa = dcCoxa;
	}

	public String getDcPerna() {
		return dcPerna;
	}

	public void setDcPerna(String dcPerna) {
		this.dcPerna = dcPerna;
	}

}
