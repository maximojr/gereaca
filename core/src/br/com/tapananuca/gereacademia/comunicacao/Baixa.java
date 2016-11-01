package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class Baixa implements JsonSerializer {

	private String id;
	
	private String valor;
	
	private String multa;
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "id", id);
		utils.addStringToJson(json, "valor", valor);
		utils.addStringToJson(json, "multa", multa);
		
		json.append("}");
		
		return json.toString();
	}
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final Baixa dto = (Baixa) t;
		
		dto.setId(Utils.getInstance().getValorFromJsonString("id", jsonString));
		dto.setValor(Utils.getInstance().getValorFromJsonString("valor", jsonString));
		dto.setMulta(Utils.getInstance().getValorFromJsonString("multa", jsonString));
		
		return t;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getMulta() {
		return multa;
	}

	public void setMulta(String multa) {
		this.multa = multa;
	}
}
