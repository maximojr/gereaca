package br.com.tapananuca.gereacademia.comunicacao;

import com.badlogic.gdx.utils.Array;

public class MedidaPersonalDTOResponse extends GAResponse {

	private Array<String> datasAulas;
	
	private Array<String> datasMedidas;
	
	public MedidaPersonalDTOResponse(){}

	public String toJson(){
		//TODO
		return null;
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
