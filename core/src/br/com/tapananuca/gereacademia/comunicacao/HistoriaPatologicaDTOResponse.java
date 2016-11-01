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
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final HistoriaPatologicaDTOResponse dto = (HistoriaPatologicaDTOResponse) t;
		
		dto.setSucesso(Boolean.valueOf(Utils.getInstance().getValorFromJsonString("sucesso", jsonString)));
		dto.setSessionId(Utils.getInstance().getValorFromJsonString("sessionId", jsonString));
		dto.setMsg(Utils.getInstance().getValorFromJsonString("msg", jsonString));
		
		HistoriaPatologicaDTO hab = new HistoriaPatologicaDTO();
		hab.fromJson(hab, jsonString);
		
		dto.setHistoriaPatologicaDTO(hab);
		
		return t;
	}

	public HistoriaPatologicaDTO getHistoriaPatologicaDTO() {
		return historiaPatologicaDTO;
	}

	public void setHistoriaPatologicaDTO(HistoriaPatologicaDTO historiaPatologicaDTO) {
		this.historiaPatologicaDTO = historiaPatologicaDTO;
	}
}
