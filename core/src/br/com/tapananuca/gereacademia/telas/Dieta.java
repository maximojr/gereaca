package br.com.tapananuca.gereacademia.telas;

public enum Dieta {

	NAO_FAZ_DIETA("Não faz dieta"),
	PERDER_PESO("Para perder peso"),
	GANHAR_PESO("Para ganhar peso");
	
	private String descricao;
	
	private Dieta(String descricao){
		
		this.descricao = descricao;
	}
	
	public String toString(){
		
		return this.descricao;
	}
	
	public Dieta getEnumByValue(String value){
		
		for (Dieta e : Dieta.values()){
			
			if (e.toString().equals(value)){
				
				return e;
			}
		}
		
		return null;
	}
}
