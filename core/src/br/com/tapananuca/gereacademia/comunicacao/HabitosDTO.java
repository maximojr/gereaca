package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class HabitosDTO implements GARequest {

	private String idPessoa;
	
	private Dieta dieta;
	
	private String praticaAtivFisica;
	
	private String dataUltimoExameMedico;
	
	private String periodExameMedico;
	
	public HabitosDTO() {}
	
	public HabitosDTO(Dieta dieta, String praticaAtivFisica, 
			Integer diaUltimoExame, Integer mesUltimoExame, Integer anoUltimoExame, 
			Integer periodExameMedico) {
		
		this.dieta = dieta;
		this.praticaAtivFisica = praticaAtivFisica;
		
		if (diaUltimoExame != null){
			
			this.dataUltimoExameMedico = diaUltimoExame + "/" + mesUltimoExame + "/" + anoUltimoExame;
		}
		
		this.periodExameMedico = periodExameMedico == null ? null : periodExameMedico.toString();
	}

	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "idPessoa", idPessoa);
		
		if (dieta != null){
			utils.addStringToJson(json, "dieta", dieta.name());
		}
		
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
