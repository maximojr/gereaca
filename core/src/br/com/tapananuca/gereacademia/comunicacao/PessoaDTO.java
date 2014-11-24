package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.telas.EstadoCivil;



public class PessoaDTO implements GARequest {

	private Long id;
	
	private String nome;
	
	private Character sexo;
	
	private String email;
	
	private String dataNascimento;
	
	private String dataInicio;
	
	private Float valorMensal;
	
	private String endereco;
	
	private String numero;
	
	private String bairro;
	
	private String telefone;
	
	private EstadoCivil estadoCivil;
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addNumberToJson(json, "id", id);
		utils.addStringToJson(json, "nome", nome);
		utils.addStringToJson(json, "sexo", String.valueOf(sexo));
		utils.addStringToJson(json, "email", email);
		utils.addStringToJson(json, "dataNascimento", dataNascimento);
		utils.addStringToJson(json, "dataInicio", dataInicio);
		utils.addNumberToJson(json, "valorMensal", valorMensal);
		utils.addStringToJson(json, "endereco", endereco);
		utils.addStringToJson(json, "numero", numero);
		utils.addStringToJson(json, "bairro", bairro);
		utils.addStringToJson(json, "telefone", telefone);
		utils.addStringToJson(json, "estadoCivil", estadoCivil.name());
		
		json.append("}");
		
		return json.toString();
	}

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

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
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

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
}
