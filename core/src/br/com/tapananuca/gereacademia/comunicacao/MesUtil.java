package br.com.tapananuca.gereacademia.comunicacao;

public enum MesUtil {

	JANEIRO(1, "Janeiro"),
	FEVEREIRO(2, "Fevereiro"),
	MARCO(3, "Março"),
	ABRIL(4, "Abril"),
	MAIO(5, "Maio"),
	JUNHO(6, "Junho"),
	JULHO(7, "Julho"),
	AGOSTO(8, "Agosto"),
	SETEMBRO(9, "Setembro"),
	OUTUBRO(10, "Outubro"),
	NOVEMBRO(11, "Novembro"),
	DEZEMBRO(12, "Dezembro");
	
	private int codigo;
	private String nome;
	
	private MesUtil(int codigo, String nome){
		this.codigo = codigo;
		this.nome = nome;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String toString() {
		return nome;
	}
	
	public static MesUtil getEnumByValue(String value){
		
		for (MesUtil e : MesUtil.values()){
			
			if (e.getNome().equals(value)){
				
				return e;
			}
		}
		
		return null;
	}
	
	public static MesUtil getEnumByCodigo(int codigo){
		
		switch (codigo) {
		case 1:
			return JANEIRO;
		case 2:
			return FEVEREIRO;
		case 3:
			return MARCO;
		case 4:
			return ABRIL;
		case 5:
			return MAIO;
		case 6:
			return JUNHO;
		case 7:
			return JULHO;
		case 8:
			return AGOSTO;
		case 9:
			return SETEMBRO;
		case 10:
			return OUTUBRO;
		case 11:
			return NOVEMBRO;
		case 12:
			return DEZEMBRO;
			
		default:
			return null;
		}
	}
}
