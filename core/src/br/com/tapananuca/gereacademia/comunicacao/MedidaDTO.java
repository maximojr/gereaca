package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;


public class MedidaDTO implements GARequest {

	private String idPessoa;
	
	private String dataReferente;
	
	private String maPesoCorporal;
	private String maAltura;
	private String maCintura;
	private String maQuadril;
	
	private String mcTorax;
	private String mcAbdomen;
	private String mcCintura;
	private String mcBiceps;
	private String mcTriceps;
	private String mcCoxa;
	private String mcAntebraco;
	
	private String dcBiceps1;
	private String dcBiceps2;
	private String dcBiceps3;
	private String dcTriceps1;
	private String dcTriceps2;
	private String dcTriceps3;
	private String dcSubAxilar1;
	private String dcSubAxilar2;
	private String dcSubAxilar3;
	private String dcSupraIliacas1;
	private String dcSupraIliacas2;
	private String dcSupraIliacas3;
	private String dcSubEscapular1;
	private String dcSubEscapular2;
	private String dcSubEscapular3;
	private String dcToraxica1;
	private String dcToraxica2;
	private String dcToraxica3;
	private String dcAbdominal1;
	private String dcAbdominal2;
	private String dcAbdominal3;
	private String dcCoxa1;
	private String dcCoxa2;
	private String dcCoxa3;
	private String dcPerna1;
	private String dcPerna2;
	private String dcPerna3;
	
	private Dobra dobra;
	
	private String percentualPesoMaximoRec;
	
	private NivelMaturacao nivelMaturacao;
	
	public MedidaDTO() {}
	
	public MedidaDTO(Float maPesoCorporal, Float maAltura,
			Float maCintura, Float maQuadril,
			Float mcTorax, Float mcAbdomen, Float mcCintura,
			Float mcBiceps, Float mcTriceps, Float mcCoxa,
			Float mcAntebraco, Float dcBiceps1, Float dcTriceps1,
			Float dcSubAxilar1, Float dcSupraIliacas1, Float dcSubEscapular1,
			Float dcToraxica1, Float dcAbdominal1, Float dcCoxa1, Float dcPerna1,
			Float dcBiceps2, Float dcTriceps2,
			Float dcSubAxilar2, Float dcSupraIliacas2, Float dcSubEscapular2,
			Float dcToraxica2, Float dcAbdominal2, Float dcCoxa2, Float dcPerna2,
			Float dcBiceps3, Float dcTriceps3,
			Float dcSubAxilar3, Float dcSupraIliacas3, Float dcSubEscapular3,
			Float dcToraxica3, Float dcAbdominal3, Float dcCoxa3, Float dcPerna3) {
		
		this.maPesoCorporal = this.nullOrString(maPesoCorporal);
		this.maAltura = this.nullOrString(maAltura);
		this.maCintura = this.nullOrString(maCintura);
		this.maQuadril = this.nullOrString(maQuadril);
		this.mcTorax = this.nullOrString(mcTorax);
		this.mcAbdomen = this.nullOrString(mcAbdomen);
		this.mcCintura = this.nullOrString(mcCintura);
		this.mcBiceps = this.nullOrString(mcBiceps);
		this.mcTriceps = this.nullOrString(mcTriceps);
		this.mcCoxa = this.nullOrString(mcCoxa);
		this.mcAntebraco = this.nullOrString(mcAntebraco);
		this.dcBiceps1 = this.nullOrString(dcBiceps1);
		this.dcTriceps1 = this.nullOrString(dcTriceps1);
		this.dcSubAxilar1 = this.nullOrString(dcSubAxilar1);
		this.dcSupraIliacas1 = this.nullOrString(dcSupraIliacas1);
		this.dcSubEscapular1 = this.nullOrString(dcSubEscapular1);
		this.dcToraxica1 = this.nullOrString(dcToraxica1);
		this.dcAbdominal1 = this.nullOrString(dcAbdominal1);
		this.dcCoxa1 = this.nullOrString(dcCoxa1);
		this.dcPerna1 = this.nullOrString(dcPerna1);
		
		this.dcBiceps2 = this.nullOrString(dcBiceps2);
		this.dcTriceps2 = this.nullOrString(dcTriceps2);
		this.dcSubAxilar2 = this.nullOrString(dcSubAxilar2);
		this.dcSupraIliacas2 = this.nullOrString(dcSupraIliacas2);
		this.dcSubEscapular2 = this.nullOrString(dcSubEscapular2);
		this.dcToraxica2 = this.nullOrString(dcToraxica2);
		this.dcAbdominal2 = this.nullOrString(dcAbdominal2);
		this.dcCoxa2 = this.nullOrString(dcCoxa2);
		this.dcPerna2 = this.nullOrString(dcPerna2);
		
		this.dcBiceps3 = this.nullOrString(dcBiceps3);
		this.dcTriceps3 = this.nullOrString(dcTriceps3);
		this.dcSubAxilar3 = this.nullOrString(dcSubAxilar3);
		this.dcSupraIliacas3 = this.nullOrString(dcSupraIliacas3);
		this.dcSubEscapular3 = this.nullOrString(dcSubEscapular3);
		this.dcToraxica3 = this.nullOrString(dcToraxica3);
		this.dcAbdominal3 = this.nullOrString(dcAbdominal3);
		this.dcCoxa3 = this.nullOrString(dcCoxa3);
		this.dcPerna3 = this.nullOrString(dcPerna3);
	}
	
	private String nullOrString(Float valor){
		
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
		utils.addStringToJson(json, "maCintura", maCintura);
		utils.addStringToJson(json, "maQuadril", maQuadril);
		
		utils.addStringToJson(json, "mcTorax", mcTorax);
		utils.addStringToJson(json, "mcAbdomen", mcAbdomen);
		utils.addStringToJson(json, "mcCintura", mcCintura);
		utils.addStringToJson(json, "mcBiceps", mcBiceps);
		utils.addStringToJson(json, "mcTriceps", mcTriceps);
		utils.addStringToJson(json, "mcCoxa", mcCoxa);
		utils.addStringToJson(json, "mcAntebraco", mcAntebraco);
		
		utils.addStringToJson(json, "dcBiceps1", dcBiceps1);
		utils.addStringToJson(json, "dcTriceps1", dcTriceps1);
		utils.addStringToJson(json, "dcSubAxilar1", dcSubAxilar1);
		utils.addStringToJson(json, "dcSupraIliacas1", dcSupraIliacas1);
		utils.addStringToJson(json, "dcSubEscapular1", dcSubEscapular1);
		utils.addStringToJson(json, "dcToraxica1", dcToraxica1);
		utils.addStringToJson(json, "dcAbdominal1", dcAbdominal1);
		utils.addStringToJson(json, "dcCoxa1", dcCoxa1);
		utils.addStringToJson(json, "dcPerna1", dcPerna1);
		
		utils.addStringToJson(json, "dcBiceps2", dcBiceps2);
		utils.addStringToJson(json, "dcTriceps2", dcTriceps2);
		utils.addStringToJson(json, "dcSubAxilar2", dcSubAxilar2);
		utils.addStringToJson(json, "dcSupraIliacas2", dcSupraIliacas2);
		utils.addStringToJson(json, "dcSubEscapular2", dcSubEscapular2);
		utils.addStringToJson(json, "dcToraxica2", dcToraxica2);
		utils.addStringToJson(json, "dcAbdominal2", dcAbdominal2);
		utils.addStringToJson(json, "dcCoxa2", dcCoxa2);
		utils.addStringToJson(json, "dcPerna2", dcPerna2);
		
		utils.addStringToJson(json, "dcBiceps3", dcBiceps3);
		utils.addStringToJson(json, "dcTriceps3", dcTriceps3);
		utils.addStringToJson(json, "dcSubAxilar3", dcSubAxilar3);
		utils.addStringToJson(json, "dcSupraIliacas3", dcSupraIliacas3);
		utils.addStringToJson(json, "dcSubEscapular3", dcSubEscapular3);
		utils.addStringToJson(json, "dcToraxica3", dcToraxica3);
		utils.addStringToJson(json, "dcAbdominal3", dcAbdominal3);
		utils.addStringToJson(json, "dcCoxa3", dcCoxa3);
		utils.addStringToJson(json, "dcPerna3", dcPerna3);
		
		if (dobra != null){
			
			utils.addStringToJson(json, "dobra", dobra.name());
		}
		
		if (nivelMaturacao != null){
			
			utils.addStringToJson(json, "nivelMaturacao", nivelMaturacao.name());
		}
		
		utils.addStringToJson(json, "percentualPesoMaximoRec", percentualPesoMaximoRec);
		
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

	public String getDcBiceps1() {
		return dcBiceps1;
	}

	public void setDcBiceps1(String dcBiceps1) {
		this.dcBiceps1 = dcBiceps1;
	}

	public String getDcBiceps2() {
		return dcBiceps2;
	}

	public void setDcBiceps2(String dcBiceps2) {
		this.dcBiceps2 = dcBiceps2;
	}

	public String getDcBiceps3() {
		return dcBiceps3;
	}

	public void setDcBiceps3(String dcBiceps3) {
		this.dcBiceps3 = dcBiceps3;
	}

	public String getDcTriceps1() {
		return dcTriceps1;
	}

	public void setDcTriceps1(String dcTriceps1) {
		this.dcTriceps1 = dcTriceps1;
	}

	public String getDcTriceps2() {
		return dcTriceps2;
	}

	public void setDcTriceps2(String dcTriceps2) {
		this.dcTriceps2 = dcTriceps2;
	}

	public String getDcTriceps3() {
		return dcTriceps3;
	}

	public void setDcTriceps3(String dcTriceps3) {
		this.dcTriceps3 = dcTriceps3;
	}

	public String getDcSubAxilar1() {
		return dcSubAxilar1;
	}

	public void setDcSubAxilar1(String dcSubAxilar1) {
		this.dcSubAxilar1 = dcSubAxilar1;
	}

	public String getDcSubAxilar2() {
		return dcSubAxilar2;
	}

	public void setDcSubAxilar2(String dcSubAxilar2) {
		this.dcSubAxilar2 = dcSubAxilar2;
	}

	public String getDcSubAxilar3() {
		return dcSubAxilar3;
	}

	public void setDcSubAxilar3(String dcSubAxilar3) {
		this.dcSubAxilar3 = dcSubAxilar3;
	}

	public String getDcSupraIliacas1() {
		return dcSupraIliacas1;
	}

	public void setDcSupraIliacas1(String dcSupraIliacas1) {
		this.dcSupraIliacas1 = dcSupraIliacas1;
	}

	public String getDcSupraIliacas2() {
		return dcSupraIliacas2;
	}

	public void setDcSupraIliacas2(String dcSupraIliacas2) {
		this.dcSupraIliacas2 = dcSupraIliacas2;
	}

	public String getDcSupraIliacas3() {
		return dcSupraIliacas3;
	}

	public void setDcSupraIliacas3(String dcSupraIliacas3) {
		this.dcSupraIliacas3 = dcSupraIliacas3;
	}

	public String getDcSubEscapular1() {
		return dcSubEscapular1;
	}

	public void setDcSubEscapular1(String dcSubEscapular1) {
		this.dcSubEscapular1 = dcSubEscapular1;
	}

	public String getDcSubEscapular2() {
		return dcSubEscapular2;
	}

	public void setDcSubEscapular2(String dcSubEscapular2) {
		this.dcSubEscapular2 = dcSubEscapular2;
	}

	public String getDcSubEscapular3() {
		return dcSubEscapular3;
	}

	public void setDcSubEscapular3(String dcSubEscapular3) {
		this.dcSubEscapular3 = dcSubEscapular3;
	}

	public String getDcToraxica1() {
		return dcToraxica1;
	}

	public void setDcToraxica1(String dcToraxica1) {
		this.dcToraxica1 = dcToraxica1;
	}

	public String getDcToraxica2() {
		return dcToraxica2;
	}

	public void setDcToraxica2(String dcToraxica2) {
		this.dcToraxica2 = dcToraxica2;
	}

	public String getDcToraxica3() {
		return dcToraxica3;
	}

	public void setDcToraxica3(String dcToraxica3) {
		this.dcToraxica3 = dcToraxica3;
	}

	public String getDcAbdominal1() {
		return dcAbdominal1;
	}

	public void setDcAbdominal1(String dcAbdominal1) {
		this.dcAbdominal1 = dcAbdominal1;
	}

	public String getDcAbdominal2() {
		return dcAbdominal2;
	}

	public void setDcAbdominal2(String dcAbdominal2) {
		this.dcAbdominal2 = dcAbdominal2;
	}

	public String getDcAbdominal3() {
		return dcAbdominal3;
	}

	public void setDcAbdominal3(String dcAbdominal3) {
		this.dcAbdominal3 = dcAbdominal3;
	}

	public String getDcCoxa1() {
		return dcCoxa1;
	}

	public void setDcCoxa1(String dcCoxa1) {
		this.dcCoxa1 = dcCoxa1;
	}

	public String getDcCoxa2() {
		return dcCoxa2;
	}

	public void setDcCoxa2(String dcCoxa2) {
		this.dcCoxa2 = dcCoxa2;
	}

	public String getDcCoxa3() {
		return dcCoxa3;
	}

	public void setDcCoxa3(String dcCoxa3) {
		this.dcCoxa3 = dcCoxa3;
	}

	public String getDcPerna1() {
		return dcPerna1;
	}

	public void setDcPerna1(String dcPerna1) {
		this.dcPerna1 = dcPerna1;
	}

	public String getDcPerna2() {
		return dcPerna2;
	}

	public void setDcPerna2(String dcPerna2) {
		this.dcPerna2 = dcPerna2;
	}

	public String getDcPerna3() {
		return dcPerna3;
	}

	public void setDcPerna3(String dcPerna3) {
		this.dcPerna3 = dcPerna3;
	}

	public Dobra getDobra() {
		return dobra;
	}

	public void setDobra(Dobra dobra) {
		this.dobra = dobra;
	}

	public String getPercentualPesoMaximoRec() {
		return percentualPesoMaximoRec;
	}

	public void setPercentualPesoMaximoRec(String percentualPesoMaximoRec) {
		this.percentualPesoMaximoRec = percentualPesoMaximoRec;
	}

	public NivelMaturacao getNivelMaturacao() {
		return nivelMaturacao;
	}

	public void setNivelMaturacao(NivelMaturacao nivelMaturacao) {
		this.nivelMaturacao = nivelMaturacao;
	}
}
