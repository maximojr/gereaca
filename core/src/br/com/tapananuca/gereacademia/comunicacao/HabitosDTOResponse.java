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
	
	public HabitosDTO getHabitosDTO() {
		return habitosDTO;
	}

	public void setHabitosDTO(HabitosDTO habitosDTO) {
		this.habitosDTO = habitosDTO;
	}
}
