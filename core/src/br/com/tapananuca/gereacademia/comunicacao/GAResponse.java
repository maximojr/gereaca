package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class GAResponse implements JsonSerializer{

	private String sessionId, msg;
	
	private boolean sucesso = true;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "sucesso", String.valueOf(sucesso));
		utils.addStringToJson(json, "sessionId", String.valueOf(sessionId));
		utils.addStringToJson(json, "msg", String.valueOf(msg));
		
		json.append("}");
		
		return json.toString();
	}

	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final GAResponse dto = (GAResponse) t;
		
		dto.setSucesso(Boolean.valueOf(Utils.getInstance().getValorFromJsonString("sucesso", jsonString)));
		dto.setSessionId(Utils.getInstance().getValorFromJsonString("sessionId", jsonString));
		dto.setMsg(Utils.getInstance().getValorFromJsonString("msg", jsonString));
		
		return t;
	}
}
