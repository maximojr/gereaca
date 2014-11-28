package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class HabitosDTO implements GARequest {

	private String idPessoa;
	
	private Dieta dieta;
	
	private String praticaAtivFisica;
	
	private String dataUltimoExameMedico;
	
	private String periodExameMedico;
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "idPessoa", idPessoa);
		utils.addStringToJson(json, "dieta", dieta.name());
		utils.addStringToJson(json, "praticaAtivFisica", praticaAtivFisica);
		utils.addStringToJson(json, "dataUltimoExameMedico", dataUltimoExameMedico);
		utils.addStringToJson(json, "periodExameMedico", periodExameMedico);
		
		json.append("}");
		
		return json.toString();
	}

	public String getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
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

	public String getDataUltimoExameMedico() {
		return dataUltimoExameMedico;
	}

	public void setDataUltimoExameMedico(String dataUltimoExameMedico) {
		this.dataUltimoExameMedico = dataUltimoExameMedico;
	}

	public String getPeriodExameMedico() {
		return periodExameMedico;
	}

	public void setPeriodExameMedico(String periodExameMedico) {
		this.periodExameMedico = periodExameMedico;
	}

}
