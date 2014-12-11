package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

import com.badlogic.gdx.utils.Array;

public class MedidaPersonalDTOResponse extends GAResponse {

	private Array<String> datasAulas;
	
	private Array<String> datasMedidas;
	
	public MedidaPersonalDTOResponse(){}

	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		utils.addCollectionToJson(json, "datasAulas", datasAulas);
		json.append(",");
		utils.addCollectionToJson(json, "datasMedidas", datasMedidas);
		json.append("}");
		
		return json.toString();
	}
	
	public Array<String> getDatasAulas() {
		return datasAulas;
	}

	public void setDatasAulas(Array<String> datasAulas) {
		this.datasAulas = datasAulas;
	}

	public Array<String> getDatasMedidas() {
		return datasMedidas;
	}

	public void setDatasMedidas(Array<String> datasMedidas) {
		this.datasMedidas = datasMedidas;
	}
}
