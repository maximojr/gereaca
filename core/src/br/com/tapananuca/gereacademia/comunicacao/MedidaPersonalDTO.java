package br.com.tapananuca.gereacademia.comunicacao;

import java.util.ArrayList;
import java.util.List;

import br.com.tapananuca.gereacademia.Utils;

public class MedidaPersonalDTO implements GARequest {

	private String idPessoa;
	
	private List<String> datasMedidas;
	
	private Dobra dobra;
	
	private String percentualPesoMaximoRec;
	
	private NivelMaturacao nivelMaturacao;
	
	public MedidaPersonalDTO() {
		
		datasMedidas = new ArrayList<String>();
	}
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "idPessoa", idPessoa);
		utils.addStringToJson(json, "percentualPesoMaximoRec", percentualPesoMaximoRec);
		json.append(",");
		utils.addCollectionToJson(json, "datasMedidas", datasMedidas);
		
		if (dobra != null){
			
			utils.addStringToJson(json, "dobra", dobra.name());
		}
		
		if (nivelMaturacao != null){
			
			utils.addStringToJson(json, "nivelMaturacao", nivelMaturacao.name());
		}
		
		json.append("}");
		
		return json.toString();
	}
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final MedidaPersonalDTO dto = (MedidaPersonalDTO) t;
		
		dto.setIdPessoa(Utils.getInstance().getValorFromJsonString("idPessoa", jsonString));
		dto.setPercentualPesoMaximoRec(Utils.getInstance().getValorFromJsonString("percentualPesoMaximoRec", jsonString));
		
		String enumVal = Utils.getInstance().getValorFromJsonString("nivelMaturacao", jsonString);
		
		if (enumVal != null && !enumVal.isEmpty()){
			dto.setNivelMaturacao(NivelMaturacao.valueOf(enumVal));
		}
		
		enumVal = Utils.getInstance().getValorFromJsonString("dobra", jsonString);
		
		if (enumVal != null && !enumVal.isEmpty()){
			dto.setDobra(Dobra.valueOf(enumVal));
		}
		
		jsonString = jsonString.substring(jsonString.indexOf("datasMedidas")+14, jsonString.length());
		int indexCol = 0;
		
		dto.setDatasMedidas(new ArrayList<String>());
		
		while (indexCol != -1 && indexCol < jsonString.indexOf("]")){
			
			String aux = jsonString.substring(indexCol, jsonString.indexOf(",")).replace("]", "");
			
			if (!aux.isEmpty()){
				dto.getDatasMedidas().add(aux);
			}
			
			indexCol = jsonString.indexOf(",", indexCol);
			
			jsonString = Utils.getInstance().replaceFirst(jsonString, ",", "");
		}
		
		return t;
	}
	
	

	public String getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}

	public List<String> getDatasMedidas() {
		return datasMedidas;
	}

	public void setDatasMedidas(List<String> datasMedidas) {
		this.datasMedidas = datasMedidas;
	}

	public Dobra getDobra() {
		return dobra;
	}

	public void setDobra(Dobra dobra) {
		this.dobra = dobra;
	}

	public String getPercentualPesoMaximoRec() {
		return percentualPesoMaximoRec;
	}

	public void setPercentualPesoMaximoRec(String percentualPesoMaximoRec) {
		this.percentualPesoMaximoRec = percentualPesoMaximoRec;
	}

	public NivelMaturacao getNivelMaturacao() {
		return nivelMaturacao;
	}

	public void setNivelMaturacao(NivelMaturacao nivelMaturacao) {
		this.nivelMaturacao = nivelMaturacao;
	}
}
