package br.com.tapananuca.gereacademia.comunicacao;

public interface JsonSerializer {

	public String toJson();
	
	public <T extends JsonSerializer> T fromJson(T t, String jsonString);
}
