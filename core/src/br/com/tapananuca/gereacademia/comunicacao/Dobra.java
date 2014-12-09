package br.com.tapananuca.gereacademia.comunicacao;

public enum Dobra {

	DUAS("2 dobras", 2),
	TRES("3 dobras", 3),
	SETE("7 dobras", 7);
	
	private String descricao;
	
	private int peso;
	
	private Dobra(String descricao, int peso){
		this.descricao = descricao;
		this.peso = peso;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getPeso() {
		return peso;
	}
	
	public static Dobra getEnumByValue(String value){
		
		for (Dobra e : Dobra.values()){
			
			if (e.getDescricao().equals(value)){
				
				return e;
			}
		}
		
		return null;
	}
}
