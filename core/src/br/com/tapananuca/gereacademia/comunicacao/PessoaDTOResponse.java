package br.com.tapananuca.gereacademia.comunicacao;

import br.com.tapananuca.gereacademia.Utils;

import com.badlogic.gdx.utils.Array;

public class PessoaDTOResponse extends GAResponse {

	
	private Array<PessoaDTO> pessoasDTO;

	public String toJson(){
		
		final Utils utils = Utils.getInstance();
		
		final StringBuilder json = new StringBuilder("{");
		
		json.append(super.toJson().replace("{", "").replace("}", ""));
		
		if (this.pessoasDTO != null){
			json.append(",");
			utils.addGARequestCollectionToJson(json, "pessoasDTO", pessoasDTO);
		}
		
		json.append("}");
		
		return json.toString();
	}

	public Array<PessoaDTO> getPessoasDTO() {
		return pessoasDTO;
	}

	public void setPessoasDTO(Array<PessoaDTO> pessoasDTO) {
		this.pessoasDTO = pessoasDTO;
	}
}
