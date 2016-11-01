package br.com.tapananuca.gereacademia.comunicacao;

import java.util.ArrayList;
import java.util.List;

import br.com.tapananuca.gereacademia.Utils;

public class DadosBaixa implements GARequest {

	private List<Baixa> baixas;
	
	public DadosBaixa() {
		
		baixas = new ArrayList<Baixa>();
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
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final DadosBaixa dto = (DadosBaixa) t;
		
		final List<Baixa> baixas = new ArrayList<Baixa>();
		
		Baixa b = null;
		
		jsonString = Utils.getInstance().replaceFirst(jsonString, "{", "");
		
		int indexCol = jsonString.indexOf("[");
		
		while (indexCol != -1){
			
			int indexEndCol = jsonString.indexOf("}");
			
			String aux = jsonString.substring(indexCol, indexEndCol);
			b = new Baixa();
			baixas.add(b.fromJson(b, aux));
			
			//jsonString.replaceFirst("[", "");
			jsonString = jsonString.substring(indexEndCol + 1, jsonString.length());
			indexCol = jsonString.indexOf("{");
			
			//baixas.add(b);
		}
		
		dto.setBaixas(baixas);
		
		return t;
	}

	public List<Baixa> getBaixas() {
		return baixas;
	}

	public void setBaixas(List<Baixa> baixas) {
		this.baixas = baixas;
	}
}
