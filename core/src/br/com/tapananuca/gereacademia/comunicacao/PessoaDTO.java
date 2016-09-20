package br.com.tapananuca.gereacademia.comunicacao;

import java.math.BigDecimal;

import br.com.tapananuca.gereacademia.Utils;



public class PessoaDTO implements GARequest {

	private String id;
	
	private String nome;
	
	private Character sexo;
	
	private String email;
	
	private String dataNascimento;
	
	private String dataInicio;
	
	private String valorMensal;
	
	private String endereco;
	
	private String numero;
	
	private String bairro;
	
	private String telefone;
	
	private EstadoCivil estadoCivil;
	
	private boolean ativo;
	
	private String lembrete;
	
	private String atividades;
	
	public PessoaDTO() {}
	
	public PessoaDTO(Long id, String nome){
		
		this.id = id.toString();
		this.nome = nome;
	}
	
	public PessoaDTO(String nome, int diaDtNasc, int mesDtNasc, int anoDtNasc, EstadoCivil estadoCivil, Character sexo,
			String endereco, String numero, String bairro, String telefone, String email,
			int diaDtInic, int mesDtInic, int anoDtInic, BigDecimal valorMensal, Boolean ativo, String lembrete,
			String atividades) {
		
		this.nome = nome;
		this.dataNascimento = (diaDtNasc < 10 ? "0" + diaDtNasc : diaDtNasc) + "/" + 
				(mesDtNasc < 10 ? "0" + mesDtNasc : mesDtNasc) + "/" + 
				anoDtNasc;
		this.estadoCivil = estadoCivil;
		this.sexo = sexo;
		this.endereco = endereco;
		this.numero = numero;
		this.bairro = bairro;
		this.telefone = telefone;
		this.email = email;
		this.dataInicio = (diaDtInic < 10 ? "0" + diaDtInic : diaDtInic) + "/" + 
				(mesDtInic < 10 ? "0" + mesDtInic : mesDtInic) + "/" + 
				anoDtInic;
		this.valorMensal = String.valueOf(valorMensal.floatValue());
		this.ativo = ativo == null ? false : ativo;
		this.lembrete = lembrete;
		this.atividades = atividades;
	}
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "id", id);
		utils.addStringToJson(json, "nome", nome);
		
		if (sexo != null){
			
			utils.addStringToJson(json, "sexo", String.valueOf(sexo));
		}
			
		utils.addStringToJson(json, "email", email);
		utils.addStringToJson(json, "dataNascimento", dataNascimento);
		utils.addStringToJson(json, "dataInicio", dataInicio);
		utils.addStringToJson(json, "valorMensal", valorMensal);
		utils.addStringToJson(json, "endereco", endereco);
		utils.addStringToJson(json, "numero", numero);
		utils.addStringToJson(json, "bairro", bairro);
		utils.addStringToJson(json, "telefone", telefone);
		utils.addBooleanToJson(json, "ativo", ativo);
		utils.addStringToJson(json, "lembrete", lembrete);
		utils.addStringToJson(json, "atividades", "_" + atividades);
		
		if (estadoCivil != null){
		
			utils.addStringToJson(json, "estadoCivil", estadoCivil.name());
		}
		
		json.append("}");
		
		return json.toString();
	}
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final PessoaDTO dto = (PessoaDTO) t;
		
		dto.setId(Utils.getInstance().getValorFromJsonString("id", jsonString));
		dto.setNome(Utils.getInstance().getValorFromJsonString("nome", jsonString));
		
		String sexo = Utils.getInstance().getValorFromJsonString("sexo", jsonString);
		if (sexo != null && !sexo.isEmpty()){
			dto.setSexo(sexo.charAt(0));
		}
		
		dto.setEmail(Utils.getInstance().getValorFromJsonString("email", jsonString));
		dto.setDataNascimento(Utils.getInstance().getValorFromJsonString("dataNascimento", jsonString));
		dto.setDataInicio(Utils.getInstance().getValorFromJsonString("dataInicio", jsonString));
		dto.setValorMensal(Utils.getInstance().getValorFromJsonString("valorMensal", jsonString));
		dto.setEndereco(Utils.getInstance().getValorFromJsonString("endereco", jsonString));
		dto.setNumero(Utils.getInstance().getValorFromJsonString("numero", jsonString));
		dto.setBairro(Utils.getInstance().getValorFromJsonString("bairro", jsonString));
		dto.setTelefone(Utils.getInstance().getValorFromJsonString("telefone", jsonString));
		dto.setAtivo(Boolean.valueOf(Utils.getInstance().getValorFromJsonString("ativo", jsonString)));
		dto.setLembrete(Utils.getInstance().getValorFromJsonString("lembrete", jsonString));
		dto.setAtividades(Utils.getInstance().getValorFromJsonString("atividades", jsonString));
		
		String estadoCivil = Utils.getInstance().getValorFromJsonString("estadoCivil", jsonString);
		
		if (estadoCivil != null && !estadoCivil.isEmpty()){
			dto.setEstadoCivil(EstadoCivil.valueOf(
				Utils.getInstance().getValorFromJsonString("estadoCivil", jsonString)));
		} else {
			dto.setEstadoCivil(EstadoCivil.SOLTEIRO);
		}
		
		return t;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(String valorMensal) {
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getLembrete() {
		return lembrete;
	}

	public void setLembrete(String lembrete) {
		this.lembrete = lembrete;
	}

	public String getAtividades() {
		return atividades;
	}

	public void setAtividades(String atividades) {
		this.atividades = atividades;
	}
}
