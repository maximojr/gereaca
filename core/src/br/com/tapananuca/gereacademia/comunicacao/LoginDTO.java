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
}
