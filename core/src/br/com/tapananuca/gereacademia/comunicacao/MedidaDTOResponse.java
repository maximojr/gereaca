package br.com.tapananuca.gereacademia.comunicacao;

import com.badlogic.gdx.utils.Array;

import br.com.tapananuca.gereacademia.Utils;

public class MedidaDTOResponse extends GAResponse {

	private MedidaDTO medidaDTO;
	
	private Array<String> datasRef;

	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		if (this.medidaDTO != null){
			utils.addStringToJson(json, "medidaDTO", this.medidaDTO.toJson());
			json.append(",");
		}
		
		utils.addCollectionToJson(json, "datasRef", datasRef);
		
		json.append(super.toJson().replace("{", "").replace("}", ""));
		
		json.append("}");
		
		return json.toString();
	}
	
	public MedidaDTO getMedidaDTO() {
		return medidaDTO;
	}

	public void setMedidaDTO(MedidaDTO medidaDTO) {
		this.medidaDTO = medidaDTO;
	}

	public Array<String> getDatasRef() {
		return datasRef;
	}

	public void setDatasRef(Array<String> datasRef) {
		this.datasRef = datasRef;
	}
}
