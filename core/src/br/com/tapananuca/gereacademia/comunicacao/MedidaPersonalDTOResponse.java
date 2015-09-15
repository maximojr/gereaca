package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

import com.badlogic.gdx.utils.Array;

public class MedidaPersonalDTOResponse extends GAResponse {

	private Array<Integer> dias;
	
	private Array<String> datasMedidas;
	
	private Array<String> datasRefAulas;
	
	private int inicioSemana, qtdDias;
	
	public MedidaPersonalDTOResponse(){}

	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		utils.addCollectionToJson(json, "dias", dias);
		json.append(",");
		utils.addCollectionToJson(json, "datasMedidas", datasMedidas);
		json.append(",");
		utils.addCollectionToJson(json, "datasRefAulas", datasRefAulas);
		
		utils.addNumberToJson(json, "inicioSemana", inicioSemana);
		
		utils.addNumberToJson(json, "qtdDias", qtdDias);
		
		json.append("}");
		
		return json.toString();
	}
	
	public Array<Integer> getDias() {
		return dias;
	}

	public void setDias(Array<Integer> dias) {
		this.dias = dias;
	}

	public Array<String> getDatasMedidas() {
		return datasMedidas;
	}

	public void setDatasMedidas(Array<String> datasMedidas) {
		this.datasMedidas = datasMedidas;
	}

	public Array<String> getDatasRefAulas() {
		return datasRefAulas;
	}

	public void setDatasRefAulas(Array<String> datasRefAulas) {
		this.datasRefAulas = datasRefAulas;
	}

	public int getInicioSemana() {
		return inicioSemana;
	}

	public void setInicioSemana(int inicioSemana) {
		this.inicioSemana = inicioSemana;
	}

	public int getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(int qtdDias) {
		this.qtdDias = qtdDias;
	}
}
