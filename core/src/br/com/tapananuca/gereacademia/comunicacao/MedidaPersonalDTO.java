package br.com.tapananuca.gereacademia.comunicacao;

import com.badlogic.gdx.utils.Array;

public class MedidaPersonalDTO implements GARequest {

	private String idPessoa;
	
	private Array<String> datasMedidas;
	
	private Dobra dobra;
	
	public MedidaPersonalDTO() {
		
		datasMedidas = new Array<String>();
	}
	
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Array<String> getDatasMedidas() {
		return datasMedidas;
	}

	public void setDatasMedidas(Array<String> datasMedidas) {
		this.datasMedidas = datasMedidas;
	}

	public Dobra getDobra() {
		return dobra;
	}

	public void setDobra(Dobra dobra) {
		this.dobra = dobra;
	}
}
