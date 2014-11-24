package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;


public class ObjetivoDTO implements GARequest {

	private Long id;
	
	private boolean estetica;
	
	private boolean lazer;
	
	private boolean saude;
	
	private boolean terapeutico;
	
	private boolean condFisico;
	
	private boolean prepFisica;
	
	private boolean autoRend;
	
	private boolean hipertrofia;
	
	private boolean emagrecimento;
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "id", String.valueOf(id));
		utils.addStringToJson(json, "estetica", String.valueOf(estetica));
		utils.addStringToJson(json, "lazer", String.valueOf(lazer));
		utils.addStringToJson(json, "saude", String.valueOf(saude));
		utils.addStringToJson(json, "terapeutico", String.valueOf(terapeutico));
		utils.addStringToJson(json, "condFisico", String.valueOf(condFisico));
		utils.addStringToJson(json, "prepFisica", String.valueOf(prepFisica));
		utils.addStringToJson(json, "autoRend", String.valueOf(autoRend));
		utils.addStringToJson(json, "hipertrofia", String.valueOf(hipertrofia));
		utils.addStringToJson(json, "emagrecimento", String.valueOf(emagrecimento));
		
		json.append("}");
		
		return json.toString();
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
