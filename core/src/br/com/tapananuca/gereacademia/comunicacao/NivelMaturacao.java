package br.com.tapananuca.gereacademia.comunicacao;

public enum NivelMaturacao {

	PREPUBENE("Pré-Púbene", 1.7),
	PUBENE("Púbene", 3.4),
	POSPUBENE("Pós-Púbene", 5.5);
	
	private String descricao;
	
	private double peso;
	
	private NivelMaturacao(String descricao, double peso){
		this.descricao = descricao;
		this.peso = peso;
	}

	public String getDescricao() {
		return descricao;
	}

	public double getPeso() {
		return peso;
	}
	
	public static NivelMaturacao getEnumByValue(String value){
		
		for (NivelMaturacao e : NivelMaturacao.values()){
			
			if (e.getDescricao().equals(value)){
				
				return e;
			}
		}
		
		return null;
	}
}
