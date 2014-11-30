package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class HabitosDTOResponse extends GAResponse {

	private HabitosDTO habitosDTO;

	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		if (this.habitosDTO != null){
			utils.addStringToJson(json, "habitosDTO", this.habitosDTO.toJson());
		}
		
		json.append(",").append(super.toJson().replace("{", "").replace("}", ""));
		
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
