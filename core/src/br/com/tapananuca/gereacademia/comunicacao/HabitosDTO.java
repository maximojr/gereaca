package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class HabitosDTO implements GARequest {

	private Long id;
	
	private Dieta dieta;
	
	private String praticaAtivFisica;
	
	private String dataUltimoExameMedico;
	
	private Integer periodExameMedico;
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addNumberToJson(json, "id", id);
		utils.addStringToJson(json, "dieta", dieta.name());
		utils.addStringToJson(json, "praticaAtivFisica", praticaAtivFisica);
		utils.addStringToJson(json, "dataUltimoExameMedico", dataUltimoExameMedico);
		utils.addNumberToJson(json, "periodExameMedico", periodExameMedico);
		
		json.append("}");
		
		return json.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getPeriodExameMedico() {
		return periodExameMedico;
	}

	public void setPeriodExameMedico(Integer periodExameMedico) {
		this.periodExameMedico = periodExameMedico;
	}

}
