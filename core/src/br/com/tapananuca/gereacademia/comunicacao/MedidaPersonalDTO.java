package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

import com.badlogic.gdx.utils.Array;

public class MedidaPersonalDTO implements GARequest {

	private String idPessoa;
	
	private Array<String> datasMedidas;
	
	private Dobra dobra;
	
	private String percentualGorduraAlvo;
	
	public MedidaPersonalDTO() {
		
		datasMedidas = new Array<String>();
	}
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "idPessoa", idPessoa);
		json.append(",");
		utils.addCollectionToJson(json, "datasMedidas", datasMedidas);
		
		if (dobra != null){
			
			utils.addStringToJson(json, "dobra", dobra.name());
		}
		
		utils.addStringToJson(json, "percentualGorduraAlvo", percentualGorduraAlvo);
		
		json.append("}");
		
		return json.toString();
	}

	public String getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Array<String> getDatasMedidas() {
		return datasMedidas;
	}

	public void setDatasMedidas(Array<String> datasMedidas) {
		this.datasMedidas = datasMedidas;
	}

	public Dobra getDobra() {
		return dobra;
	}

	public void setDobra(Dobra dobra) {
		this.dobra = dobra;
	}

	public String getPercentualGorduraAlvo() {
		return percentualGorduraAlvo;
	}

	public void setPercentualGorduraAlvo(String percentualGorduraAlvo) {
		this.percentualGorduraAlvo = percentualGorduraAlvo;
	}
}