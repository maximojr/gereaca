package br.com.tapananuca.gereacademia.comunicacao;



public class PessoaDTO implements GARequest {

	private Long id;
	
	private String nome;
	
	private Float peso;
	
	private Character sexo;
	
	private String email;
	
	private String dataNascimento;
	
	private String inicio;
	
	private Float valorMensal;
	
	private String endereco;
	
	private Integer numero;
	
	private String bairro;
	
	private String telefone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public Float getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(Float valorMensal) {
		this.valorMensal = valorMensal;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toJson() {
		
		final StringBuilder json = new StringBuilder("{");
		
		if (id != null){
			
			json.append("id")
				.append(":")
				.append(id);
		}
		
		if (nome != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("nome:")
		    	.append("\"")
		    	.append(nome)
		    	.append("\"");
		}
		
		if (peso != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("peso:")
		    	.append(peso);
		}
		
		if (sexo != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("sexo:")
		    	.append(sexo);
		}
		
		if (email != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("email:")
		    	.append(email);
		}
		
		if (dataNascimento != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("dataNascimento:")
		    	.append(dataNascimento);
		}
		
		if (inicio != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("inicio:")
		    	.append(inicio);
		}
		
		if (valorMensal != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("valorMensal:")
		    	.append(valorMensal);
		}
		
		if (endereco != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("endereco:")
		    	.append("\"")
		    	.append(endereco)
		    	.append("\"");
		}
		
		if (numero != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("numero:")
		    	.append(numero);
		}
		
		if (bairro != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("bairro:")
		    	.append("\"")
		    	.append(bairro)
		    	.append("\"");
		}
		
		if (telefone != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append("telefone:")
				.append("\"")
		    	.append(telefone)
		    	.append("\"");
		}
		
		json.append("}");
		
		return json.toString();
	}
}
