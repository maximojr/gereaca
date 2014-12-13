package br.com.tapananuca.gereacademia.telas;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTO;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTOResponse;

import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ObjetivosTab extends Tab {

	private Stage stage;
	private Utils utils;
	private CadastroPessoaScreen cadastroPessoaScreen;
	
	private CheckBox checkEstetica;
	private CheckBox checkLazer;
	private CheckBox checkSaude;
	private CheckBox checkTerapeutico;
	private CheckBox checkCondFisico;
	private CheckBox checkPrepFisica;
	private CheckBox checkAutoRend;
	private CheckBox checkHipertrofia;
	
	public ObjetivosTab(final Button button, CadastroPessoaScreen cadastroPessoaScreen, int alinhamento){
		
		button.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (button.isChecked()){
					ObjetivosTab.this.carregarObjetivos();
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
		
		checkEstetica = new CheckBox("Estética", skin);
		conteudo.add(checkEstetica).left().padRight(50);
		
		checkTerapeutico = new CheckBox("Terapeutico", skin);
		conteudo.add(checkTerapeutico).left().padRight(130);
		
		checkAutoRend = new CheckBox("Alto Rendimento", skin);
		conteudo.add(checkAutoRend).left().row();
		
		checkLazer = new CheckBox("Lazer", skin);
		conteudo.add(checkLazer).left();
		
		checkCondFisico = new CheckBox("Condicionamento Físico", skin);
		conteudo.add(checkCondFisico).left();
		
		checkHipertrofia = new CheckBox("Hipertrofia", skin);
		conteudo.add(checkHipertrofia).left().row();
		
		checkSaude = new CheckBox("Saúde", skin);
		conteudo.add(checkSaude).left();
		
		checkPrepFisica = new CheckBox("Preparação Física", skin);
		conteudo.add(checkPrepFisica).left().row();
		
		final TextButton botaoSalvarObjetivos = new TextButton("Salvar", skin);
		botaoSalvarObjetivos.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				ObjetivosTab.this.salvarObjetivos();
			}
		});
		
		conteudo.add(botaoSalvarObjetivos).left();
		
		this.inicializar();
	}

	private void carregarObjetivos() {
		
		final Long pessoaEdicaoId = this.cadastroPessoaScreen.getPessoaEdicaoId();
		
		if (pessoaEdicaoId == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final ObjetivoDTO dto = new ObjetivoDTO();
		dto.setIdPessoa(pessoaEdicaoId.toString());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_OBJETIVOS, dto);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final ObjetivoDTOResponse resp = utils.fromJson(ObjetivoDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					if (resp.getObjetivoDTO() != null){
						
						final ObjetivoDTO dto = resp.getObjetivoDTO();
						
						ObjetivosTab.this.checkEstetica.setChecked(dto.isEstetica());
						ObjetivosTab.this.checkLazer.setChecked(dto.isLazer());
						ObjetivosTab.this.checkSaude.setChecked(dto.isSaude());
						ObjetivosTab.this.checkTerapeutico.setChecked(dto.isTerapeutico());
						ObjetivosTab.this.checkCondFisico.setChecked(dto.isCondFisico());
						ObjetivosTab.this.checkPrepFisica.setChecked(dto.isPrepFisica());
						ObjetivosTab.this.checkAutoRend.setChecked(dto.isAutoRend());
						ObjetivosTab.this.checkHipertrofia.setChecked(dto.isHipertrofia());
					}
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar salvar objetivos: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}

	private void salvarObjetivos() {
		
		//cant happen no mater what, but...
		if (this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final ObjetivoDTO objetivoDTO = new ObjetivoDTO();
		objetivoDTO.setIdPessoa(this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		objetivoDTO.setAutoRend(this.checkAutoRend.isChecked());
		objetivoDTO.setCondFisico(this.checkCondFisico.isChecked());
		objetivoDTO.setEstetica(this.checkEstetica.isChecked());
		objetivoDTO.setHipertrofia(this.checkHipertrofia.isChecked());
		objetivoDTO.setLazer(this.checkLazer.isChecked());
		objetivoDTO.setPrepFisica(this.checkPrepFisica.isChecked());
		objetivoDTO.setSaude(this.checkSaude.isChecked());
		objetivoDTO.setTerapeutico(this.checkTerapeutico.isChecked());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_OBJETIVOS_SALVAR, objetivoDTO);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					utils.mostarAlerta(null, "Objetivos salvos com sucesso.", stage, skin);
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar salvar objetivos: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}
}
