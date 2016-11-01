package br.com.tapananuca.gereacademia.comunicacao;

import java.util.ArrayList;
import java.util.List;

import br.com.tapananuca.gereacademia.Utils;

public class MedidaPersonalDTOResponse extends GAResponse {

	private List<Integer> dias;
	
	private List<String> datasMedidas;
	
	private List<String> datasRefAulas;
	
	private int inicioSemana, qtdDias;
	
	public MedidaPersonalDTOResponse(){}

	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		json.append(super.toJson().replace("{", "").replace("}", ""));
		
		utils.addCollectionToJson(json, "dias", dias);
		json.append(",");
		utils.addCollectionToJson(json, "datasMedidas", datasMedidas);
		json.append(",");
		utils.addCollectionToJson(json, "datasRefAulas", datasRefAulas);
		
		utils.addNumberToJson(json, "inicioSemana", inicioSemana);
		
		utils.addNumberToJson(json, "qtdDias", qtdDias);
		
		json.append("}");
		
		return json.toString();
	}
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final MedidaPersonalDTOResponse dto = (MedidaPersonalDTOResponse) t;
		
		dto.setSucesso(Boolean.valueOf(Utils.getInstance().getValorFromJsonString("sucesso", jsonString)));
		dto.setSessionId(Utils.getInstance().getValorFromJsonString("sessionId", jsonString));
		dto.setMsg(Utils.getInstance().getValorFromJsonString("msg", jsonString));
		dto.setInicioSemana(Integer.parseInt(Utils.getInstance().getValorFromJsonString("inicioSemana", jsonString)));
		dto.setQtdDias(Integer.parseInt(Utils.getInstance().getValorFromJsonString("qtdDias", jsonString)));
		
		
		String auxLista = jsonString.substring(jsonString.indexOf("dias")+6, jsonString.indexOf("datasMedidas"));
		int indexCol = 0;
		
		dto.setDias(new ArrayList<Integer>());
		
		while (indexCol != -1 && indexCol < auxLista.indexOf("]")){
			
			String aux = auxLista.substring(indexCol, auxLista.indexOf(",")).replace("]", "");
			if (!aux.isEmpty()){
				dto.getDias().add(Integer.valueOf(aux));
			}
			indexCol = auxLista.indexOf(",");
			
			auxLista = Utils.getInstance().replaceFirst(auxLista, ",", "");
		}
		
		
		
		
		auxLista = jsonString.substring(jsonString.indexOf("datasMedidas")+14, jsonString.indexOf("datasRefAulas"));
		indexCol = 0;
		
		dto.setDatasMedidas(new ArrayList<String>());
		
		while (indexCol != -1 && indexCol < auxLista.indexOf("]")){
			
			String aux = auxLista.substring(indexCol, auxLista.indexOf(",")).replace("]", "");
			if (!aux.isEmpty()){
				dto.getDatasMedidas().add(aux);
			}
			indexCol = auxLista.indexOf(",");
			
			auxLista = Utils.getInstance().replaceFirst(auxLista, ",", "");
		}
		
		
		
		
		auxLista = jsonString.substring(jsonString.indexOf("datasRefAulas")+15, jsonString.length());
		indexCol = 0;
		
		dto.setDatasRefAulas(new ArrayList<String>());
		
		while (indexCol != -1 && indexCol < auxLista.indexOf("]")){
			
			String aux = auxLista.substring(indexCol, auxLista.indexOf(",")).replace("]", "");
			if (!aux.isEmpty()){
				dto.getDatasRefAulas().add(aux);
			}
			indexCol = auxLista.indexOf(",");
			
			auxLista = Utils.getInstance().replaceFirst(auxLista, ",", "");
		}
		
		
		return t;
	}
	
	public List<Integer> getDias() {
		return dias;
	}

	public void setDias(List<Integer> dias) {
		this.dias = dias;
	}

	public List<String> getDatasMedidas() {
		return datasMedidas;
	}

	public void setDatasMedidas(List<String> datasMedidas) {
		this.datasMedidas = datasMedidas;
	}

	public List<String> getDatasRefAulas() {
		return datasRefAulas;
	}

	public void setDatasRefAulas(List<String> datasRefAulas) {
		this.datasRefAulas = datasRefAulas;
	}

	public int getInicioSemana() {
		return inicioSemana;
	}

	public void setInicioSemana(int inicioSemana) {
		this.inicioSemana = inicioSemana;
	}

	public int getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(int qtdDias) {
		this.qtdDias = qtdDias;
	}
}
