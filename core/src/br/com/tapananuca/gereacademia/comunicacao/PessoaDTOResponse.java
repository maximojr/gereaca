package br.com.tapananuca.gereacademia.comunicacao;

import java.util.ArrayList;
import java.util.List;

import br.com.tapananuca.gereacademia.Utils;

public class PessoaDTOResponse extends GAResponse {

	
	private List<PessoaDTO> pessoasDTO;

	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		json.append(super.toJson().replace("{", "").replace("}", ""));
		
		if (this.pessoasDTO != null){
			json.append(",");
			utils.addGARequestCollectionToJson(json, "pessoasDTO", pessoasDTO);
		}
		
		json.append("}");
		
		return json.toString();
	}
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final PessoaDTOResponse dto = (PessoaDTOResponse) t;
		
		dto.setSucesso(Boolean.valueOf(Utils.getInstance().getValorFromJsonString("sucesso", jsonString)));
		dto.setSessionId(Utils.getInstance().getValorFromJsonString("sessionId", jsonString));
		dto.setMsg(Utils.getInstance().getValorFromJsonString("msg", jsonString));
		
		if (jsonString.contains("pessoasDTO")){
			
			dto.setPessoasDTO(new ArrayList<PessoaDTO>());
			
			jsonString = Utils.getInstance().replaceFirst(jsonString, "{", "");
			
			int indexCol = jsonString.indexOf("{");
			
			while (indexCol != -1){
				
				int indexEndCol = jsonString.indexOf("}");
				
				String aux = jsonString.substring(indexCol, indexEndCol);
				PessoaDTO b = new PessoaDTO();
				dto.getPessoasDTO().add(b.fromJson(b, aux));
				
				jsonString = Utils.getInstance().replaceFirst(jsonString, "{", "");
				jsonString = Utils.getInstance().replaceFirst(jsonString, "}", "");
				
				indexCol = jsonString.indexOf("{");
			}
		}
		
		return t;
	}

	public List<PessoaDTO> getPessoasDTO() {
		return pessoasDTO;
	}

	public void setPessoasDTO(List<PessoaDTO> pessoasDTO) {
		this.pessoasDTO = pessoasDTO;
	}
}
