package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

import com.badlogic.gdx.utils.Array;

public class DadosBaixa implements GARequest {

	private Array<Baixa> baixas;
	
	public DadosBaixa() {
		
		baixas = new Array<Baixa>();
	}
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		if (this.baixas != null){
			utils.addGARequestCollectionToJson(json, "baixas", baixas);
		}
		
		json.append("}");
		
		return json.toString();
	}

	public Array<Baixa> getBaixas() {
		return baixas;
	}

	public void setBaixas(Array<Baixa> baixas) {
		this.baixas = baixas;
	}

}
