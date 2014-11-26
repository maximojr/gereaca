package br.com.tapananuca.gereacademia.comunicacao;

import java.util.List;

import br.com.tapananuca.gereacademia.Utils;

public class AReceberPaginaDTO extends GAResponse {

	private List<AReceberDTO> aReceber;
	
	private List<String> datasRef;
	
	private Long qtdPaginas;
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addGARequestCollectionToJson(json, "aReceber", aReceber);
		utils.addCollectionToJson(json, "datasRef", datasRef);
		
		utils.addNumberToJson(json, "qtdPaginas", qtdPaginas);
		
		json.append(",").append(super.toJson().replace("{", "").replace("}", ""));
		
		json.append("}");
		
		return json.toString();
	}

	public List<AReceberDTO> getaReceber() {
		return aReceber;
	}

	public void setaReceber(List<AReceberDTO> aReceber) {
		this.aReceber = aReceber;
	}

	public List<String> getDatasRef() {
		return datasRef;
	}

	public void setDatasRef(List<String> datasRef) {
		this.datasRef = datasRef;
	}

	public Long getQtdPaginas() {
		return qtdPaginas;
	}

	public void setQtdPaginas(Long qtdPaginas) {
		this.qtdPaginas = qtdPaginas;
	}

}
