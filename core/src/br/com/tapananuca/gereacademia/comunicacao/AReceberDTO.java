package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

/**
 * usada tanto para exibição de dados na tela 
 * quanto para envio de info de pagamento ao servidor
 */
public class AReceberDTO implements JsonSerializer {

	private Long id;
	
	private String nome;
	
	private Float valor;
	
	private String dataRef;
	
	private int paginaAtual;
	
	public AReceberDTO(){}
	
	public AReceberDTO(Long id, String nome, Float valor){
		
		this.id = id;
		this.nome = nome;
		this.valor = valor;
	}
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addNumberToJson(json, "id", id);
		utils.addStringToJson(json, "nome", nome);
		utils.addNumberToJson(json, "valor", valor);
		utils.addStringToJson(json, "dataRef", dataRef);
		utils.addNumberToJson(json, "paginaAtual", paginaAtual);
		
		json.append("}");
		
		return json.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public String getDataRef() {
		return dataRef;
	}

	public void setDataRef(String dataRef) {
		this.dataRef = dataRef;
	}

	public int getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(int paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

}
