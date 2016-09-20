package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class HistoriaPatologicaDTO implements GARequest {

	private String idPessoa;
	
	private String cirurgias;
	
	private String sintomasDoencas;
	
	private String medicamentos;
	
	private String lesoes;
	
	private String alergias;
	
	private String outros;
	
	private boolean cardiopatia;
	
	private boolean hipertensao;
	
	public HistoriaPatologicaDTO() {}
	
	public HistoriaPatologicaDTO(String cirurgias, String sintomasDoencas,
			String medicamentos, String lesoes, String alergias, String outros,
			Boolean cardiopatia, Boolean hipertensao) {
		
		this.cirurgias = cirurgias;
		this.sintomasDoencas = sintomasDoencas;
		this.medicamentos = medicamentos;
		this.lesoes = lesoes;
		this.alergias = alergias;
		this.outros = outros;
		this.cardiopatia = cardiopatia == null ? false : cardiopatia;
		this.hipertensao = hipertensao == null ? false : hipertensao;
	}

	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "idPessoa", idPessoa);
		utils.addStringToJson(json, "cirurgias", cirurgias);
		utils.addStringToJson(json, "sintomasDoencas", sintomasDoencas);
		utils.addStringToJson(json, "medicamentos", medicamentos);
		utils.addStringToJson(json, "lesoes", lesoes);
		utils.addStringToJson(json, "alergias", alergias);
		utils.addStringToJson(json, "outros", outros);
		utils.addStringToJson(json, "cardiopatia", String.valueOf(cardiopatia));
		utils.addStringToJson(json, "hipertensao", String.valueOf(hipertensao));
		
		json.append("}");
		
		return json.toString();
	}
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final HistoriaPatologicaDTO dto = (HistoriaPatologicaDTO) t;
		
		dto.setIdPessoa(Utils.getInstance().getValorFromJsonString("idPessoa", jsonString));
		dto.setCirurgias(Utils.getInstance().getValorFromJsonString("cirurgias", jsonString));
		dto.setSintomasDoencas(Utils.getInstance().getValorFromJsonString("sintomasDoencas", jsonString));
		dto.setMedicamentos(Utils.getInstance().getValorFromJsonString("medicamentos", jsonString));
		dto.setLesoes(Utils.getInstance().getValorFromJsonString("lesoes", jsonString));
		dto.setAlergias(Utils.getInstance().getValorFromJsonString("alergias", jsonString));
		dto.setOutros(Utils.getInstance().getValorFromJsonString("outros", jsonString));
		
		String val = Utils.getInstance().getValorFromJsonString("cardiopatia", jsonString);
		
		if (val != null && !val.isEmpty()){
			dto.setCardiopatia(Boolean.valueOf(val));
		}
		
		val = Utils.getInstance().getValorFromJsonString("hipertensao", jsonString);
		if (val != null && !val.isEmpty()){
			dto.setHipertensao(Boolean.valueOf(val));
		}
		
		return t;
	}

	public String getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
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

	public boolean isCardiopatia() {
		return cardiopatia;
	}

	public void setCardiopatia(boolean cardiopatia) {
		this.cardiopatia = cardiopatia;
	}

	public boolean isHipertensao() {
		return hipertensao;
	}

	public void setHipertensao(boolean hipertensao) {
		this.hipertensao = hipertensao;
	}

}
