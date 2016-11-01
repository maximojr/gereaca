package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class HabitosDTOResponse extends GAResponse {

	private HabitosDTO habitosDTO;

	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		json.append(super.toJson().replace("{", "").replace("}", ""));
		
		utils.addJsonSerializerToJson(json, "habitosDTO", this.habitosDTO);
		
		json.append("}");
		
		return json.toString();
	}
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final HabitosDTOResponse dto = (HabitosDTOResponse) t;
		
		dto.setSucesso(Boolean.valueOf(Utils.getInstance().getValorFromJsonString("sucesso", jsonString)));
		dto.setSessionId(Utils.getInstance().getValorFromJsonString("sessionId", jsonString));
		dto.setMsg(Utils.getInstance().getValorFromJsonString("msg", jsonString));
		
		HabitosDTO hab = new HabitosDTO();
		hab.fromJson(hab, jsonString);
		
		dto.setHabitosDTO(hab);
		
		return t;
	}
	
	public HabitosDTO getHabitosDTO() {
		return habitosDTO;
	}

	public void setHabitosDTO(HabitosDTO habitosDTO) {
		this.habitosDTO = habitosDTO;
	}
}
