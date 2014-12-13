package br.com.tapananuca.gereacademia.telas;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTO;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTOResponse;

import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class HistoriaPatologicaTab extends Tab {

	private Stage stage;
	private Utils utils;
	private CadastroPessoaScreen cadastroPessoaScreen;
	
	private TextArea cirurgias;
	private TextArea sintomasDoencas;
	private TextArea medicamentos;
	private TextArea lesoes;
	private TextArea alergias;
	private TextArea outros;
	
	private CheckBox cardiopatia;
	private CheckBox hipertensao;
	
	public HistoriaPatologicaTab(final Button button, CadastroPessoaScreen cadastroPessoaScreen, int alinhamento){
		
		button.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (button.isChecked()){
					HistoriaPatologicaTab.this.carregarHistPat();
				}
			}
		});
		
		this.setBotao(button);
		this.skin = cadastroPessoaScreen.skin;
		this.stage = cadastroPessoaScreen.stage;
		this.utils = Utils.getInstance();
		this.cadastroPessoaScreen = cadastroPessoaScreen;
		
		this.alinhamento = alinhamento;
		
		this.conteudo = new Table(skin);
		
		final Table leftTable = new Table(skin);
		
		leftTable.add("Pessoal:").left().padBottom(10).row();
		
		leftTable.add("Cirurgias:").left().row();
		cirurgias = new TextArea("", skin);
		cirurgias.setPrefRows(3);
		leftTable.add(cirurgias).left().width(500).row();
		this.cadastroPessoaScreen.elementosFocaveis.add(cirurgias);
		
		leftTable.add("Sintomas/Doenças:").left().row();
		sintomasDoencas = new TextArea("", skin);
		sintomasDoencas.setPrefRows(3);
		leftTable.add(sintomasDoencas).left().width(500).row();
		this.cadastroPessoaScreen.elementosFocaveis.add(sintomasDoencas);
		
		leftTable.add("Medicamentos:").left().row();
		medicamentos = new TextArea("", skin);
		medicamentos.setPrefRows(3);
		leftTable.add(medicamentos).left().width(500).row();
		this.cadastroPessoaScreen.elementosFocaveis.add(medicamentos);
		
		leftTable.add("Lesões:").left().row();
		lesoes = new TextArea("", skin);
		lesoes.setPrefRows(3);
		leftTable.add(lesoes).left().width(500).row();
		this.cadastroPessoaScreen.elementosFocaveis.add(lesoes);
		
		leftTable.add("Alergias:").left().row();
		alergias = new TextArea("", skin);
		alergias.setPrefRows(3);
		leftTable.add(alergias).left().width(500).row();
		this.cadastroPessoaScreen.elementosFocaveis.add(alergias);
		
		leftTable.add("Outras informações:").left().row();
		outros = new TextArea("", skin);
		outros.setPrefRows(3);
		leftTable.add(outros).left().width(500);
		this.cadastroPessoaScreen.elementosFocaveis.add(outros);
		
		conteudo.add(leftTable);
		
		final Table rightTable = new Table(skin);
		
		rightTable.add("Familiar:").padBottom(10).left().row();
		
		cardiopatia = new CheckBox("Cardiopatia", skin);
		rightTable.add(cardiopatia).left().row();
		
		hipertensao = new CheckBox("Hipertensão", skin);
		rightTable.add(hipertensao).left();
		
		conteudo.add(rightTable).padLeft(10).top().row();
		
		final TextButton botaoSalvarHistPat = new TextButton("Salvar", skin);
		botaoSalvarHistPat.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				HistoriaPatologicaTab.this.salvarHistPat();
			}
		});
		
		conteudo.add(botaoSalvarHistPat).left();
		
		this.inicializar();
	}

	private void salvarHistPat() {
		
		//cant happen no mater what, but...
		if (this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final HistoriaPatologicaDTO historiaPatologicaDTO = new HistoriaPatologicaDTO();
		historiaPatologicaDTO.setIdPessoa(this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		historiaPatologicaDTO.setAlergias(this.alergias.getText());
		historiaPatologicaDTO.setCirurgias(this.cirurgias.getText());
		historiaPatologicaDTO.setLesoes(this.lesoes.getText());
		historiaPatologicaDTO.setMedicamentos(this.medicamentos.getText());
		historiaPatologicaDTO.setOutros(this.outros.getText());
		historiaPatologicaDTO.setSintomasDoencas(this.sintomasDoencas.getText());
		historiaPatologicaDTO.setCardiopatia(this.cardiopatia.isChecked());
		historiaPatologicaDTO.setHipertensao(this.hipertensao.isChecked());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_HIST_PAT_SALVAR, historiaPatologicaDTO);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					utils.mostarAlerta(null, "História patológica salva com sucesso.", stage, skin);
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar salvar história patológica: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}

	private void carregarHistPat() {
		
		//can't happen
		if (this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final HistoriaPatologicaDTO dto = new HistoriaPatologicaDTO();
		dto.setIdPessoa(this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_HIST_PAT, dto);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final HistoriaPatologicaDTOResponse resp = 
						utils.fromJson(HistoriaPatologicaDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					final HistoriaPatologicaDTO dto = resp.getHistoriaPatologicaDTO();
					
					if (dto != null){
						
						HistoriaPatologicaTab.this.cirurgias.setText(utils.emptyOrString(dto.getCirurgias()).replace("<br>", "\n"));
						HistoriaPatologicaTab.this.sintomasDoencas.setText(utils.emptyOrString(dto.getSintomasDoencas()).replace("<br>", "\n"));
						HistoriaPatologicaTab.this.medicamentos.setText(utils.emptyOrString(dto.getMedicamentos()).replace("<br>", "\n"));
						HistoriaPatologicaTab.this.lesoes.setText(utils.emptyOrString(dto.getLesoes()).replace("<br>", "\n"));
						HistoriaPatologicaTab.this.alergias.setText(utils.emptyOrString(dto.getAlergias()).replace("<br>", "\n"));
						HistoriaPatologicaTab.this.outros.setText(utils.emptyOrString(dto.getOutros()).replace("<br>", "\n"));
						
						HistoriaPatologicaTab.this.cardiopatia.setChecked(dto.isCardiopatia());
						HistoriaPatologicaTab.this.hipertensao.setChecked(dto.isHipertensao());
					}
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar carregar história patológica: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}
}
