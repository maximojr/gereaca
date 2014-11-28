package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

import com.badlogic.gdx.utils.Array;

public class AReceberPaginaDTO extends GAResponse {

	private Array<AReceberDTO> aReceber;
	
	private Array<String> datasRef;
	
	private String qtdPaginas;
	
	public AReceberPaginaDTO(){}
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addGARequestCollectionToJson(json, "aReceber", aReceber);
		json.append(",");
		utils.addCollectionToJson(json, "datasRef", datasRef);
		
		utils.addStringToJson(json, "qtdPaginas", qtdPaginas);
		
		json.append(",").append(super.toJson().replace("{", "").replace("}", ""));
		
		json.append("}");
		
		return json.toString();
	}

	public Array<AReceberDTO> getaReceber() {
		return aReceber;
	}

	public void setaReceber(Array<AReceberDTO> aReceber) {
		this.aReceber = aReceber;
	}

	public Array<String> getDatasRef() {
		return datasRef;
	}

	public void setDatasRef(Array<String> datasRef) {
		this.datasRef = datasRef;
	}

	public String getQtdPaginas() {
		return qtdPaginas;
	}

	public void setQtdPaginas(String qtdPaginas) {
		this.qtdPaginas = qtdPaginas;
	}

}
