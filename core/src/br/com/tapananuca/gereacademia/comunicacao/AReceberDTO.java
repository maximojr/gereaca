package br.com.tapananuca.gereacademia.comunicacao;

import java.math.BigDecimal;

import br.com.tapananuca.gereacademia.Utils;

/**
 * usada tanto para exibição de dados na tela 
 * quanto para envio de info de pagamento ao servidor
 */
public class AReceberDTO implements JsonSerializer {

	private String id;
	
	private String nome;
	
	private String valor;
	
	private String dataRef;
	
	private String paginaAtual;
	
	private String multa;
	
	private String tipoAtv;
	
	public AReceberDTO(){}
	
	public AReceberDTO(Long id, String nome, BigDecimal valor, boolean multa){
		
		this.id = String.valueOf(id);
		this.nome = nome;
		
		if (multa){
			this.multa = "5";
			this.valor = String.valueOf(valor.add(new BigDecimal(5)));
		} else {
			this.valor = String.valueOf(valor);
		}
	}
	
	public AReceberDTO(String nome, String data){
		
		this.nome = nome;
		this.dataRef = data;
	}
	
	@Override
	public String toJson() {
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		utils.addStringToJson(json, "id", id);
		utils.addStringToJson(json, "nome", nome);
		utils.addStringToJson(json, "valor", valor);
		utils.addStringToJson(json, "dataRef", dataRef);
		utils.addStringToJson(json, "paginaAtual", paginaAtual);
		utils.addStringToJson(json, "multa", multa);
		utils.addStringToJson(json, "tipoAtv", tipoAtv);
		
		json.append("}");
		
		return json.toString();
	}
	
	@Override
	public <T extends JsonSerializer> T fromJson(T t, String jsonString) {
		
		final AReceberDTO dto = (AReceberDTO) t;
		
		dto.setId(Utils.getInstance().getValorFromJsonString("id", jsonString));
		dto.setNome(Utils.getInstance().getValorFromJsonString("nome", jsonString));
		dto.setValor(Utils.getInstance().getValorFromJsonString("valor", jsonString));
		dto.setDataRef(Utils.getInstance().getValorFromJsonString("dataRef", jsonString));
		dto.setPaginaAtual(Utils.getInstance().getValorFromJsonString("paginaAtual", jsonString));
		dto.setMulta(Utils.getInstance().getValorFromJsonString("multa", jsonString));
		dto.setTipoAtv(Utils.getInstance().getValorFromJsonString("tipoAtv", jsonString));
		
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDataRef() {
		return dataRef;
	}

	public void setDataRef(String dataRef) {
		this.dataRef = dataRef;
	}

	public String getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(String paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

	public String getMulta() {
		return multa;
	}

	public void setMulta(String multa) {
		this.multa = multa;
	}

	public String getTipoAtv() {
		return tipoAtv;
	}

	public void setTipoAtv(String tipoAtv) {
		this.tipoAtv = tipoAtv;
	}
}
