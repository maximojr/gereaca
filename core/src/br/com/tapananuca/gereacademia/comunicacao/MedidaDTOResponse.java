package br.com.tapananuca.gereacademia.comunicacao;


import java.util.ArrayList;
import java.util.List;

import br.com.tapananuca.gereacademia.Utils;

public class MedidaDTOResponse extends GAResponse {

	private MedidaDTO medidaDTO;
	
	private List<String> datasRef;

	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		if (this.medidaDTO != null){
			utils.addStringToJson(json, "medidaDTO", this.medidaDTO.toJson());
			json.append(",");
		}
		
		utils.addCollectionToJson(json, "datasRef", datasRef);
		json.append(",");
		
		json.append(super.toJson().replace("{", "").replace("}", ""));
		
		json.append("}");
		
		return json.toString();
	}
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final MedidaDTOResponse dto = (MedidaDTOResponse) t;
		
		dto.setSucesso(Boolean.valueOf(Utils.getInstance().getValorFromJsonString("sucesso", jsonString)));
		dto.setSessionId(Utils.getInstance().getValorFromJsonString("sessionId", jsonString));
		dto.setMsg(Utils.getInstance().getValorFromJsonString("msg", jsonString));
		
		MedidaDTO hab = new MedidaDTO();
		hab.fromJson(hab, jsonString);
		
		dto.setMedidaDTO(hab);
		
		jsonString = jsonString.substring(jsonString.indexOf("datasRef")+10, jsonString.length());
		int indexCol = 0;
		
		dto.setDatasRef(new ArrayList<String>());
		
		while (indexCol < jsonString.indexOf("]")){
			
			String aux = jsonString.substring(indexCol, jsonString.indexOf(",")).replace("]", "");
			dto.getDatasRef().add(aux);
			//areceber.add(b.fromJson(b, aux));
			
			indexCol = jsonString.indexOf(",");
			
			jsonString = Utils.getInstance().replaceFirst(jsonString, ",", "");
			
			//areceber.add(b);
		}
		
		return t;
	}
	
	public MedidaDTO getMedidaDTO() {
		return medidaDTO;
	}

	public void setMedidaDTO(MedidaDTO medidaDTO) {
		this.medidaDTO = medidaDTO;
	}

	public List<String> getDatasRef() {
		return datasRef;
	}

	public void setDatasRef(List<String> datasRef) {
		this.datasRef = datasRef;
	}
}
