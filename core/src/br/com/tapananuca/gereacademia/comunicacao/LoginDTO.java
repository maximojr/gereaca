package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

public class LoginDTO implements GARequest{

	private String usuario, senha, novaSenha;
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "usuario", usuario);
		utils.addStringToJson(json, "senha", senha);
		utils.addStringToJson(json, "novaSenha", novaSenha);
		
		json.append("}");
		
		return json.toString();
	}
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final LoginDTO dto = (LoginDTO) t;
		
		dto.setUsuario(Utils.getInstance().getValorFromJsonString("usuario", jsonString));
		dto.setSenha(Utils.getInstance().getValorFromJsonString("senha", jsonString));
		dto.setNovaSenha(Utils.getInstance().getValorFromJsonString("novaSenha", jsonString));
		
		return t;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
}
