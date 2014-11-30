package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;


public class ObjetivoDTO implements GARequest {

	private String idPessoa;
	
	private boolean estetica;
	
	private boolean lazer;
	
	private boolean saude;
	
	private boolean terapeutico;
	
	private boolean condFisico;
	
	private boolean prepFisica;
	
	private boolean autoRend;
	
	private boolean hipertrofia;
	
	private boolean emagrecimento;
	
	public ObjetivoDTO() {}
	
	public ObjetivoDTO(Boolean estetica, Boolean lazer, Boolean saude, Boolean terapeutico, 
			Boolean condFisico, Boolean prepFisica, Boolean autoRend, Boolean hipertrofia,
			Boolean emagrecimento) {
		
		this.estetica = estetica == null ? false : estetica;
		this.lazer = lazer == null ? false : lazer;
		this.saude = saude == null ? false : saude;
		this.terapeutico = terapeutico == null ? false : terapeutico;
		this.condFisico = condFisico == null ? false : condFisico;
		this.prepFisica = prepFisica == null ? false : prepFisica;
		this.autoRend = autoRend == null ? false : autoRend;
		this.hipertrofia = hipertrofia == null ? false : hipertrofia;
		this.emagrecimento = emagrecimento == null ? false : emagrecimento;
	}
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "idPessoa", idPessoa);
		utils.addBooleanToJson(json, "estetica", estetica);
		utils.addBooleanToJson(json, "lazer", lazer);
		utils.addBooleanToJson(json, "saude", saude);
		utils.addBooleanToJson(json, "terapeutico", terapeutico);
		utils.addBooleanToJson(json, "condFisico", condFisico);
		utils.addBooleanToJson(json, "prepFisica", prepFisica);
		utils.addBooleanToJson(json, "autoRend", autoRend);
		utils.addBooleanToJson(json, "hipertrofia", hipertrofia);
		utils.addBooleanToJson(json, "emagrecimento", emagrecimento);
		
		json.append("}");
		
		return json.toString();
	}
	
	public String getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}

	public boolean isEstetica() {
		return estetica;
	}

	public void setEstetica(boolean estetica) {
		this.estetica = estetica;
	}

	public boolean isLazer() {
		return lazer;
	}

	public void setLazer(boolean lazer) {
		this.lazer = lazer;
	}

	public boolean isSaude() {
		return saude;
	}

	public void setSaude(boolean saude) {
		this.saude = saude;
	}

	public boolean isTerapeutico() {
		return terapeutico;
	}

	public void setTerapeutico(boolean terapeutico) {
		this.terapeutico = terapeutico;
	}

	public boolean isCondFisico() {
		return condFisico;
	}

	public void setCondFisico(boolean condFisico) {
		this.condFisico = condFisico;
	}

	public boolean isPrepFisica() {
		return prepFisica;
	}

	public void setPrepFisica(boolean prepFisica) {
		this.prepFisica = prepFisica;
	}

	public boolean isAutoRend() {
		return autoRend;
	}

	public void setAutoRend(boolean autoRend) {
		this.autoRend = autoRend;
	}

	public boolean isHipertrofia() {
		return hipertrofia;
	}

	public void setHipertrofia(boolean hipertrofia) {
		this.hipertrofia = hipertrofia;
	}

	public boolean isEmagrecimento() {
		return emagrecimento;
	}

	public void setEmagrecimento(boolean emagrecimento) {
		this.emagrecimento = emagrecimento;
	}
}
