package br.com.tapananuca.gereacademia.comunicacao;

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
		
		final StringBuilder json = new StringBuilder("{");
		
		json.append("sucesso:").append(sucesso);
		
		if (sessionId != null){
			
			json.append(",")
				.append("sessionId:")
				.append(sessionId);
		}
		
		if (msg != null){
			
			json.append(",")
				.append("msg:")
				.append("\"")
				.append(msg)
				.append("\"");
		}
		
		json.append("}");
		return json.toString();
	}
}
