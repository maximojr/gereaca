package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class MedidasDTO implements GARequest {

	private Long id;
	
	private String dataRef;
	
	private Float maPesoCorporal;
	
	private Float maPesoMagro;
	
	private Float maPesoGordura;
	
	private Float maAltura;
	
	private Float maPg;
	
	private Float maImc;
	
	private Float maCintura;
	
	private Float maQuadril;
	
	private Float maPmrc;
	
	private Float mcTorax;
	
	private Float mcAbdomen;
	
	private Float mcCintura;
	
	private Float mcBiceps;
	
	private Float mcTriceps;
	
	private Float mcCoxa;
	
	private Float mcAntebraco;
	
	private Float dcBiceps;
	
	private Float dcTriceps;
	
	private Float dcSubAxilar;
	
	private Float dcSupraIliacas;
	
	private Float dcSubEscapular;
	
	private Float dcToraxica;
	
	private Float dcAbdominal;
	
	private Float dcCoxa;
	
	private Float dcPerna;
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addNumberToJson(json, "id", id);
		utils.addStringToJson(json, "dataRef", dataRef);
		utils.addNumberToJson(json, "maPesoCorporal", maPesoCorporal);
		utils.addNumberToJson(json, "maPesoMagro", maPesoMagro);
		utils.addNumberToJson(json, "maPesoGordura", maPesoGordura);
		utils.addNumberToJson(json, "maAltura", maAltura);
		utils.addNumberToJson(json, "maPg", maPg);
		utils.addNumberToJson(json, "maImc", maImc);
		utils.addNumberToJson(json, "maCintura", maCintura);
		utils.addNumberToJson(json, "maQuadril", maQuadril);
		utils.addNumberToJson(json, "maPmrc", maPmrc);
		utils.addNumberToJson(json, "mcTorax", mcTorax);
		utils.addNumberToJson(json, "mcAbdomen", mcAbdomen);
		utils.addNumberToJson(json, "mcCintura", mcCintura);
		utils.addNumberToJson(json, "mcBiceps", mcBiceps);
		utils.addNumberToJson(json, "mcTriceps", mcTriceps);
		utils.addNumberToJson(json, "mcCoxa", mcCoxa);
		utils.addNumberToJson(json, "mcAntebraco", mcAntebraco);
		utils.addNumberToJson(json, "dcBiceps", dcBiceps);
		utils.addNumberToJson(json, "dcTriceps", dcTriceps);
		utils.addNumberToJson(json, "dcSubAxilar", dcSubAxilar);
		utils.addNumberToJson(json, "dcSupraIliacas", dcSupraIliacas);
		utils.addNumberToJson(json, "dcSubEscapular", dcSubEscapular);
		utils.addNumberToJson(json, "dcToraxica", dcToraxica);
		utils.addNumberToJson(json, "dcAbdominal", dcAbdominal);
		utils.addNumberToJson(json, "dcCoxa", dcCoxa);
		utils.addNumberToJson(json, "dcPerna", dcPerna);
		
		json.append("}");
		
		return json.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataRef() {
		return dataRef;
	}

	public void setDataRef(String dataRef) {
		this.dataRef = dataRef;
	}

	public Float getMaPesoCorporal() {
		return maPesoCorporal;
	}

	public void setMaPesoCorporal(Float maPesoCorporal) {
		this.maPesoCorporal = maPesoCorporal;
	}

	public Float getMaPesoMagro() {
		return maPesoMagro;
	}

	public void setMaPesoMagro(Float maPesoMagro) {
		this.maPesoMagro = maPesoMagro;
	}

	public Float getMaPesoGordura() {
		return maPesoGordura;
	}

	public void setMaPesoGordura(Float maPesoGordura) {
		this.maPesoGordura = maPesoGordura;
	}

	public Float getMaAltura() {
		return maAltura;
	}

	public void setMaAltura(Float maAltura) {
		this.maAltura = maAltura;
	}

	public Float getMaPg() {
		return maPg;
	}

	public void setMaPg(Float maPg) {
		this.maPg = maPg;
	}

	public Float getMaImc() {
		return maImc;
	}

	public void setMaImc(Float maImc) {
		this.maImc = maImc;
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

	public Float getMaPmrc() {
		return maPmrc;
	}

	public void setMaPmrc(Float maPmrc) {
		this.maPmrc = maPmrc;
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
