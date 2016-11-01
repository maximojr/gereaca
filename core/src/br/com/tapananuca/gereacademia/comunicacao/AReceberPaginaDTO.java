package br.com.tapananuca.gereacademia.comunicacao;

import java.util.ArrayList;
import java.util.List;

import br.com.tapananuca.gereacademia.Utils;

public class AReceberPaginaDTO extends GAResponse {

	private List<AReceberDTO> aReceber;
	
	private List<String> datasRef;
	
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
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final AReceberPaginaDTO dto = (AReceberPaginaDTO) t;
		
		dto.setSucesso(Boolean.valueOf(Utils.getInstance().getValorFromJsonString("sucesso", jsonString)));
		dto.setSessionId(Utils.getInstance().getValorFromJsonString("sessionId", jsonString));
		dto.setMsg(Utils.getInstance().getValorFromJsonString("msg", jsonString));
		dto.setQtdPaginas(Utils.getInstance().getValorFromJsonString("qtdPaginas", jsonString));
		
		final List<AReceberDTO> areceber = new ArrayList<AReceberDTO>();
		
		AReceberDTO b = null;
		
		jsonString = Utils.getInstance().replaceFirst(jsonString, "{", "");
		
		int indexCol = jsonString.indexOf("{");
		
		while (indexCol != -1 && indexCol < jsonString.indexOf("datasRef")){
			
			int indexEndCol = jsonString.indexOf("}");
			
			String aux = jsonString.substring(indexCol, indexEndCol);
			b = new AReceberDTO();
			areceber.add(b.fromJson(b, aux));
			
			jsonString = Utils.getInstance().replaceFirst(jsonString, "{", "");
			jsonString = Utils.getInstance().replaceFirst(jsonString, "}", "");
			
			indexCol = jsonString.indexOf("{");
			
			//areceber.add(b);
		}
		
		dto.setaReceber(areceber);
		jsonString = jsonString.substring(jsonString.indexOf("datasRef")+10, jsonString.length());
		indexCol = 0;
		
		dto.setDatasRef(new ArrayList<String>());
		
		while (indexCol < jsonString.indexOf("]")){
			
			String aux = jsonString.substring(indexCol, jsonString.indexOf(",")).replace("]", "");
			dto.getDatasRef().add(aux);
			//areceber.add(b.fromJson(b, aux));
			
			indexCol = jsonString.indexOf(",");
			
			jsonString = Utils.getInstance().replaceFirst(jsonString, ",", "");
			
			//areceber.add(b);
		}
		
		//dto.setaReceber(areceber);
		
		return t;
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

	public String getQtdPaginas() {
		return qtdPaginas;
	}

	public void setQtdPaginas(String qtdPaginas) {
		this.qtdPaginas = qtdPaginas;
	}

}
