package br.com.tapananuca.gereacademia.comunicacao;

import java.math.BigDecimal;

import br.com.tapananuca.gereacademia.Utils;

/**
 * usada tanto para exibição de dados na tela 
 * quanto para envio de info de pagamento ao servidor
 */
public class AReceberDTO implements JsonSerializer {

	public String id;
	
	public String nome;
	
	public String valor;
	
	public String dataRef;
	
	public String paginaAtual;
	
	public AReceberDTO(){}
	
	public AReceberDTO(Long id, String nome, BigDecimal valor){
		
		this.id = String.valueOf(id);
		this.nome = nome;
		this.valor = String.valueOf(valor);
	}
	
	public AReceberDTO(String nome, String data){
		
		this.nome = nome;
		this.dataRef = data;
	}
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "id", id);
		utils.addStringToJson(json, "nome", nome);
		utils.addStringToJson(json, "valor", valor);
		utils.addStringToJson(json, "dataRef", dataRef);
		utils.addStringToJson(json, "paginaAtual", paginaAtual);
		
		json.append("}");
		
		return json.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDataRef() {
		return dataRef;
	}

	public void setDataRef(String dataRef) {
		this.dataRef = dataRef;
	}

	public String getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(String paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

}
