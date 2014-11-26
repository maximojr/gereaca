package br.com.tapananuca.gereacademia.comunicacao;

public enum EstadoCivil {

	SOLTEIRO("Solteiro(a)"),
	CASADO("Casado(a)"),
	DIVORCIADO("Divorciado(a)"), 
	VIUVO("Viúvo(a)");
	
	private String descricao;
	
	private EstadoCivil(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoCivil getEnumByValue(String value){
		
		for (EstadoCivil e : EstadoCivil.values()){
			
			if (e.getDescricao().equals(value)){
				
				return e;
			}
		}
		
		return null;
	}
}
