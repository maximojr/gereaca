package br.com.tapananuca.gereacademia.comunicacao;

public enum Periodicidade {

	DIAS("Dias", 1),
	SEMANAS("Semanas", 7),
	MESES("Meses", 30);
	
	private String descricao;
	
	private int peso;
	
	private Periodicidade(String descricao, int peso){
		
		this.descricao = descricao;
		this.peso = peso;
	}
	
	public String getDescricao(){
		
		return this.descricao;
	}
	
	public int getPeso(){
		
		return this.peso;
	}
	
	public static Periodicidade getEnumByValue(String value){
		
		for (Periodicidade e : Periodicidade.values()){
			
			if (e.getDescricao().equals(value)){
				
				return e;
			}
		}
		
		return null;
	}
}
