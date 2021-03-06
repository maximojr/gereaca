package br.com.tapananuca.gereacademia;

public enum Atividade {

	TODAS("Todas", ""),
	MUSCULACAO("Musculação","1__"),
	FUNCIONAL("Funcional","_1_"),
	DANCA("Dança","__1");
	
	private String desc, codigo;
	
	private Atividade(String desc, String codigo){
		this.desc = desc;
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		
		return this.desc;
	}
	
	public String getCodigo(){
		return this.codigo;
	}
	
	public static String getCodigoByDesc(final String desc){
		
		for (Atividade atv : Atividade.values()){
			
			if (atv.toString().equals(desc)){
				return atv.getCodigo();
			}
		}
		
		return null;
	}
}
