package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class ObjetivoDTOResponse extends GAResponse {

	private ObjetivoDTO objetivoDTO;

	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		if (this.objetivoDTO != null){
			utils.addStringToJson(json, "objetivoDTO", this.objetivoDTO.toJson());
		}
		
		json.append(",").append(super.toJson().replace("{", "").replace("}", ""));
		
		json.append("}");
		
		return json.toString();
	}
	
	public ObjetivoDTO getObjetivoDTO() {
		return objetivoDTO;
	}

	public void setObjetivoDTO(ObjetivoDTO objetivoDTO) {
		this.objetivoDTO = objetivoDTO;
	}
}
