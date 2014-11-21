package br.com.tapananuca.gereacademia.comunicacao;

public class LoginDTO implements GARequest{

	private String usuario, senha;

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

	@Override
	public String toJson() {
		
		final StringBuilder json = new StringBuilder("{");
		json.append("\"usuario\"")
		    .append(":")
		    .append(usuario)
		    .append(",")
		    .append("\"senha\"")
		    .append(":")
		    .append(senha)
		    .append("}");
		
		return json.toString();
	}
}
