package br.com.tapananuca.gereacademia.comunicacao;

public enum Dieta {

	NAO_FAZ_DIETA("Não faz dieta"),
	PERDER_PESO("Para perder peso"),
	GANHAR_PESO("Para ganhar peso");
	
	private String descricao;
	
	private Dieta(String descricao){
		
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		
		return this.descricao;
	}
	
	public static Dieta getEnumByValue(String value){
		
		for (Dieta e : Dieta.values()){
			
			if (e.getDescricao().equals(value)){
				
				return e;
			}
		}
		
		return null;
	}
}
