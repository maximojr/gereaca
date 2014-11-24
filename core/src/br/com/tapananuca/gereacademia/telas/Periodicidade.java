package br.com.tapananuca.gereacademia.telas;

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
	
	public String toString(){
		
		return this.descricao;
	}
	
	public int getPeso(){
		
		return this.peso;
	}
	
	public Periodicidade getEnumByValue(String value){
		
		for (Periodicidade e : Periodicidade.values()){
			
			if (e.toString().equals(value)){
				
				return e;
			}
		}
		
		return null;
	}
}
