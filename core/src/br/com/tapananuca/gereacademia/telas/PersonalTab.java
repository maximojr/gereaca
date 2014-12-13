package br.com.tapananuca.gereacademia.telas;

import java.util.ArrayList;
import java.util.List;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.Dobra;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

public class PersonalTab extends Tab {

	private Stage stage;
	private Utils utils;
	private CadastroPessoaScreen cadastroPessoaScreen;
	
	private Table datasAulas, datasMedidas;
	private List<CheckBox> listCheckDatasMedidas;
	private SelectBox<String> dobrasCalc;
	private Slider sliderPercentualPesoMaxRec;
	
	public PersonalTab(final Button button, CadastroPessoaScreen cadastroPessoaScreen, int alinhamento){
		
		button.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (button.isChecked()){
					if (PersonalTab.this.cadastroPessoaScreen.indSexo == 'M'){
						
						sliderPercentualPesoMaxRec.setValue(15f);
					} else {
						
						sliderPercentualPesoMaxRec.setValue(23f);
					}
					PersonalTab.this.carregarPersonal();
				}
			}
		});
		
		this.setBotao(button);
		this.skin = cadastroPessoaScreen.skin;
		this.stage = cadastroPessoaScreen.stage;
		this.utils = Utils.getInstance();
		this.cadastroPessoaScreen = cadastroPessoaScreen;
		
		this.alinhamento = alinhamento;
		
		this.listCheckDatasMedidas = new ArrayList<CheckBox>();
		
		this.conteudo = new Table(skin);
		
		final Table tableAulas = new Table(skin);
		tableAulas.add("Aulas:").row();
		
		datasAulas = new Table(skin);
		datasAulas.align(Align.topLeft);
		final ScrollPane scrollDatasAulas = new ScrollPane(datasAulas, skin);
		
		tableAulas.add(scrollDatasAulas).height(200).width(150);
		
		final Table inTableAddAula = new Table(skin);
		
		final TextField novaAula = new TextField("", skin);
		novaAula.setTextFieldFilter(utils.dateFilter);
		inTableAddAula.add(novaAula).row();
		
		final TextButton btnAddAula = new TextButton("Adicionar aula", skin);
		btnAddAula.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (PersonalTab.this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
					
					utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
					
					return;
				}
				
				final MedidaDTO medidaDTO = new MedidaDTO();
				medidaDTO.setIdPessoa(PersonalTab.this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
				medidaDTO.setDataReferente(novaAula.getText());
				
				final HttpRequest request = utils.criarRequest(Utils.URL_PERSONAL_ADD_AULA, medidaDTO);
				
				utils.enviarRequest(request, stage, skin, new HttpResponseListener() {
					
					@Override
					public void handleHttpResponse(HttpResponse httpResponse) {
						
						final GAResponse resp = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
						
						if (resp.isSucesso()){
							
							datasAulas.add(medidaDTO.getDataReferente()).row();
						} else {
							
							utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
						}
					}
					
					@Override
					public void failed(Throwable t) {
						
						utils.mostarAlerta("Erro:", "Erro ao tentar adicionar aula: " + t.getMessage() , stage, skin);
					}
					
					@Override
					public void cancelled() {
						
						utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
					}
				});
			}
		});
		
		inTableAddAula.add(btnAddAula).padTop(5);
		
		tableAulas.add(inTableAddAula).padLeft(5);
		
		conteudo.add(tableAulas).row().padTop(15);
		
		final Table tableMedidas = new Table(skin);
		tableMedidas.add("Medidas:").row();
		
		datasMedidas = new Table(skin);
		datasMedidas.align(Align.topLeft);
		final ScrollPane scrollDatasMedidas = new ScrollPane(datasMedidas, skin);
		
		tableMedidas.add(scrollDatasMedidas).height(200).width(150);
		
		final Table inTableCalcMedidas = new Table(skin);
		
		dobrasCalc = new SelectBox<String>(skin);
		
		final String[] dobras = new String[Dobra.values().length];
		int index = 0;
		for (Dobra d : Dobra.values()){
			dobras[index] = d.getDescricao();
			index++;
		}
		
		dobrasCalc.setItems(dobras);
		
		inTableCalcMedidas.add(dobrasCalc).colspan(2).padLeft(5).padBottom(5).row();
		
		inTableCalcMedidas.add("Percentual peso máximo: ").colspan(2).row();
		
		sliderPercentualPesoMaxRec = new Slider(1, 100, 1, false, skin);
		final Label percentualPesoMaxTexto = new Label("", skin);
		sliderPercentualPesoMaxRec.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				percentualPesoMaxTexto.setText(
						String.valueOf(sliderPercentualPesoMaxRec.getValue()) + " %");
			}
		});
		
		sliderPercentualPesoMaxRec.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			      event.stop();
			      return false;
			   }
		});
		inTableCalcMedidas.add(sliderPercentualPesoMaxRec);
		
		inTableCalcMedidas.add(percentualPesoMaxTexto).padLeft(5).padBottom(5).row();
		
		final Table btns = new Table(skin);
		
		final TextButton btnCalcular = new TextButton("Calcular", skin);
		btnCalcular.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				PersonalTab.this.criarRelatorioAvaliacao();
			}
		});
		btns.add(btnCalcular);
		
		final TextButton btnEnviarEmail = new TextButton("Enviar por email", skin);
		btnEnviarEmail.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				PersonalTab.this.enviarRelatorioAvaliacao();
			}
		});
		btns.add(btnEnviarEmail).padLeft(5);
		
		inTableCalcMedidas.add(btns).colspan(2);
		
		tableMedidas.add(inTableCalcMedidas).padLeft(5);
		
		conteudo.add(tableMedidas).padTop(15);
		
		
		this.inicializar();
	}

	private void carregarPersonal() {
		
		if (PersonalTab.this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		datasAulas.clearChildren();
		datasMedidas.clearChildren();
		listCheckDatasMedidas.clear();
		
		final PessoaDTO pessoaDTO = new PessoaDTO(PersonalTab.this.cadastroPessoaScreen.getPessoaEdicaoId(), null);
		
		final HttpRequest request = utils.criarRequest(Utils.URL_PERSONAL_DATAS, pessoaDTO);
		
		utils.enviarRequest(request, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final MedidaPersonalDTOResponse dto = utils.fromJson(MedidaPersonalDTOResponse.class, httpResponse.getResultAsString());
				
				if (dto.isSucesso()){
					
					if (dto.getDatasAulas() != null && dto.getDatasAulas().size > 0){
						
						for (String dt : dto.getDatasAulas()){
							
							datasAulas.add(dt).row();
						}
					}
					
					if (dto.getDatasMedidas() != null && dto.getDatasMedidas().size > 0){
						
						for (String dt : dto.getDatasMedidas()){
							
							final CheckBox checkBox = new CheckBox(dt, skin);
							listCheckDatasMedidas.add(checkBox);
							datasMedidas.add(checkBox).row();
						}
					}
				} else {
					
					utils.mostarAlerta("Atenção:", dto.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar adicionar aula: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}

	private void criarRelatorioAvaliacao() {
		
		if (this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			return;
		}
		
		final MedidaPersonalDTO dto = new MedidaPersonalDTO();
		dto.setIdPessoa(this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		dto.setDobra(Dobra.getEnumByValue(this.dobrasCalc.getSelected()));
		dto.setPercentualPesoMaximoRec(String.valueOf(this.sliderPercentualPesoMaxRec.getValue()));
		
		final Array<String> datas = new Array<String>();
		
		for (CheckBox c : this.listCheckDatasMedidas){
			
			if (c.isChecked()){
				
				datas.add(c.getText().toString());
			}
		}
		
		dto.setDatasMedidas(datas);
		
		final HttpRequest request = utils.criarRequest(Utils.URL_PERSONAL_GERAR_RELATORIO, dto);
		
		utils.enviarRequest(request, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse ga = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (ga.isSucesso()){
					
					Gdx.net.openURI(
						utils.getPropertie(Utils.WEB_APP_END_POINT) + 
						Utils.URL_PERSONAL_ABRIR_RELATORIO + "?" + Utils.URL_PERSONAL_KEY_RELATORIO + "=" + ga.getMsg());
				} else {
					
					utils.mostarAlerta("Atenção:", ga.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar criar relatório de avaliação: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}
	
	private void enviarRelatorioAvaliacao() {
		
		//TODO cade o request?
		final HttpRequest request = utils.criarRequest(Utils.URL_PERSONAL_ENVIAR_RELATORIO, null);
		
		utils.enviarRequest(request, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse ga = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (ga.isSucesso()){
					
					utils.mostarAlerta(null, "Avaliação enviada com sucesso.", stage, skin);
				} else {
					
					utils.mostarAlerta("Atenção:", ga.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar enviar avaliação: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}
}
