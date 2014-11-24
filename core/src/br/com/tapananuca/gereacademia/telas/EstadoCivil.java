package br.com.tapananuca.gereacademia.telas;

public enum EstadoCivil {

	SOLTEIRO("Solteiro(a)"),
	CASADO("Casado(a)"),
	DIVORCIADO("Divorciado(a)"), 
	VIUVO("Viúvo(a)");
	
	private String descricao;
	
	private EstadoCivil(String descricao){
		this.descricao = descricao;
	}

	public String toString() {
		return descricao;
	}
	
	public EstadoCivil getEnumByValue(String value){
		
		for (EstadoCivil e : EstadoCivil.values()){
			
			if (e.toString().equals(value)){
				
				return e;
			}
		}
		
		return null;
	}
}
