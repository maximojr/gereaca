package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class HistoriaPatologicaDTOResponse extends GAResponse {

	private HistoriaPatologicaDTO historiaPatologicaDTO;
	
	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		if (this.historiaPatologicaDTO != null){
			utils.addStringToJson(json, "historiaPatologicaDTO", this.historiaPatologicaDTO.toJson());
		}
		
		json.append(",").append(super.toJson().replace("{", "").replace("}", ""));
		
		json.append("}");
		
		return json.toString();
	}

	public HistoriaPatologicaDTO getHistoriaPatologicaDTO() {
		return historiaPatologicaDTO;
	}

	public void setHistoriaPatologicaDTO(HistoriaPatologicaDTO historiaPatologicaDTO) {
		this.historiaPatologicaDTO = historiaPatologicaDTO;
	}
}
