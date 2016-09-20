package br.com.tapananuca.gereacademia.telas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.Dobra;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.MesUtil;
import br.com.tapananuca.gereacademia.comunicacao.NivelMaturacao;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class PersonalTab extends Tab {

	private Stage stage;
	private Utils utils;
	private CadastroPessoaScreen cadastroPessoaScreen;
	
	private Table datasMedidas;
	private List<CheckBox> listCheckDatasMedidas;
	private SelectBox<String> dobrasCalc;
	private SelectBox<String> nivelMaturacao;
	private Slider sliderPercentualPesoMaxRec;
	
	private final Table tableAulas;
	
	private final SelectBox<String> selectBoxMes;
	
	private final TextField ano;
	
	private final SelectBoxMesChangeListener mesChange;
	
	@SuppressWarnings("deprecation")
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
		
		tableAulas = new Table(skin);
		conteudo.add("Aulas:").padTop(10).colspan(7).row();
		
		selectBoxMes = new SelectBox<String>(skin);
		
		final String[] es = new String[MesUtil.values().length];
		
		int index = 0;
		for (MesUtil e : MesUtil.values()){
			es[index] = e.getNome();
			index++;
		}
		
		selectBoxMes.setItems(es);
		
		final Date dataRef = new Date(TimeUtils.millis());
		
		final String mesRef = String.valueOf(dataRef.getMonth() + 1);
		
		ano = new TextField(String.valueOf(dataRef.getYear() + 1900), skin);
		
		selectBoxMes.setSelected(MesUtil.getEnumByCodigo(Integer.valueOf(mesRef)).getNome());
		
		mesChange = new SelectBoxMesChangeListener();
		selectBoxMes.addListener(mesChange);
		
		final Table tabelaMesAno = new Table(skin);
		tabelaMesAno.add(selectBoxMes);
		tabelaMesAno.add(ano);
		
		conteudo.add(tabelaMesAno).row();
		
		conteudo.add(tableAulas).padTop(15).row();
		
		final Table tableMedidas = new Table(skin);
		tableMedidas.add("Medidas:").row();
		
		datasMedidas = new Table(skin);
		datasMedidas.align(Align.topLeft);
		final ScrollPane scrollDatasMedidas = new ScrollPane(datasMedidas, skin);
		
		tableMedidas.add(scrollDatasMedidas).height(200).width(150);
		
		final Table inTableCalcMedidas = new Table(skin);
		
		dobrasCalc = new SelectBox<String>(skin);
		
		String[] valores = new String[Dobra.values().length];
		index = 0;
		for (Dobra d : Dobra.values()){
			valores[index] = d.getDescricao();
			index++;
		}
		
		dobrasCalc.setItems(valores);
		
		dobrasCalc.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				nivelMaturacao.setVisible(Dobra.getEnumByValue(dobrasCalc.getSelected()) == Dobra.DUAS);
			}
		});
		
		inTableCalcMedidas.add(dobrasCalc);
		
		nivelMaturacao = new SelectBox<String>(skin);
		
		valores = new String[NivelMaturacao.values().length];
		index = 0;
		for (NivelMaturacao d : NivelMaturacao.values()){
			valores[index] = d.getDescricao();
			index++;
		}
		
		nivelMaturacao.setItems(valores);
		
		inTableCalcMedidas.add(nivelMaturacao).colspan(2).padLeft(5).padBottom(5).row();
		
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
		
		final TextButton btnEnviarEmail = new TextButton("Enviar por e-mail", skin);
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
	
	class SelectBoxMesChangeListener extends ChangeListener{

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			
			PersonalTab.this.montarCalendario();
		}
	}
	
	private void montarCalendario() {
		
		if (PersonalTab.this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final PessoaDTO pessoaDTO = new PessoaDTO(PersonalTab.this.cadastroPessoaScreen.getPessoaEdicaoId(), null);
		pessoaDTO.setDataInicio(
				String.valueOf(MesUtil.getEnumByValue(this.selectBoxMes.getSelected()).getCodigo()) + "/" +
						this.ano.getText());
		
		final HttpRequest request = utils.criarRequest(Utils.URL_PERSONAL_DATAS_AULAS, pessoaDTO);
		
		utils.enviarRequest(request, stage, skin, new HttpResponseListener() {

			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final MedidaPersonalDTOResponse dto = utils.fromJson(new MedidaPersonalDTOResponse(), httpResponse.getResultAsString());
				
				if (dto.isSucesso()){
					
					PersonalTab.this.montarCalendario(dto.getInicioSemana(), dto.getQtdDias(), dto.getDias());
					
				} else {
					
					utils.mostarAlerta("Atenção:", dto.getMsg(), stage, skin);
				}
			}

			@Override
			public void failed(Throwable t) {
				utils.mostarAlerta(null, "Erro ao carregar datas de aula: " + t.getLocalizedMessage(), stage, skin);
			}

			@Override
			public void cancelled() {
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}

	private void montarCalendario(int inicioSemana, int qtdDias, List<Integer> dias) {
		
		this.tableAulas.clear();
		
		this.tableAulas.add("Dom");
		this.tableAulas.add("Seg");
		this.tableAulas.add("Ter");
		this.tableAulas.add("Qua");
		this.tableAulas.add("Qui");
		this.tableAulas.add("Sex");
		this.tableAulas.add("Sab").row();
		
		int qtdDiasAdd = 0;
		
		while (inicioSemana > 1){
			this.tableAulas.add("");
			inicioSemana--;
			qtdDiasAdd++;
		}
		
		for (int index = 1 ; index <= qtdDias ; index++){
			
			if (qtdDiasAdd > 0 && qtdDiasAdd % 7 == 0){
				
				this.tableAulas.row();
			}
			
			final TextButton btnDia = new TextButton(String.valueOf(index), skin, "toggle");
			
			if (dias.contains(index)){
				btnDia.toggle();
			}
			
			final int dia = index;
			btnDia.addListener(new ChangeListener() {
				
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					
					PersonalTab.this.diaCalendarioClick(dia, btnDia);
				}
			});
			
			this.tableAulas.add(btnDia).width(50);
			
			qtdDiasAdd++;
		}
	}

	private void diaCalendarioClick(int dia, TextButton botao) {
		
		if (PersonalTab.this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final MedidaDTO medidaDTO = new MedidaDTO();
		medidaDTO.setIdPessoa(PersonalTab.this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		medidaDTO.setDataReferente(dia + "/" + MesUtil.getEnumByValue(this.selectBoxMes.getSelected()).getCodigo() + "/" + this.ano.getText());
		
		final HttpRequest request = utils.criarRequest(Utils.URL_PERSONAL_ADD_AULA, medidaDTO);
		
		utils.enviarRequest(request, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(new GAResponse(), httpResponse.getResultAsString());
				
				if (!resp.isSucesso()){
					
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

	private void carregarPersonal() {
		
		if (PersonalTab.this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		datasMedidas.clearChildren();
		listCheckDatasMedidas.clear();
		
		final PessoaDTO pessoaDTO = new PessoaDTO(PersonalTab.this.cadastroPessoaScreen.getPessoaEdicaoId(), null);
		pessoaDTO.setDataInicio(
				String.valueOf(MesUtil.getEnumByValue(this.selectBoxMes.getSelected()).getCodigo()) + "/" +
						this.ano.getText());
		
		final HttpRequest request = utils.criarRequest(Utils.URL_PERSONAL_DATAS, pessoaDTO);
		
		utils.enviarRequest(request, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final MedidaPersonalDTOResponse dto = utils.fromJson(new MedidaPersonalDTOResponse(), httpResponse.getResultAsString());
				
				if (dto.isSucesso()){
					
					Gdx.app.postRunnable(new Runnable() {
						
						@Override
						public void run() {
							
							PersonalTab.this.montarCalendario(dto.getInicioSemana(), dto.getQtdDias(), dto.getDias());
							
							if (dto.getDatasMedidas() != null && dto.getDatasMedidas().size() > 0){
								
								for (String dt : dto.getDatasMedidas()){
									
									final CheckBox checkBox = new CheckBox(dt, skin);
									listCheckDatasMedidas.add(checkBox);
									datasMedidas.add(checkBox).left().row();
								}
							}
						}
					});
					
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
		
		final MedidaPersonalDTO dto = this.montarDTO();
		
		final HttpRequest request = utils.criarRequest(Utils.URL_PERSONAL_GERAR_RELATORIO, dto);
		
		utils.enviarRequest(request, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse ga = utils.fromJson(new GAResponse(), httpResponse.getResultAsString());
				
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
		
		if (this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			return;
		}
		
		final MedidaPersonalDTO dto = this.montarDTO();
		
		final HttpRequest request = utils.criarRequest(Utils.URL_PERSONAL_ENVIAR_RELATORIO, dto);
		
		utils.enviarRequest(request, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse ga = utils.fromJson(new GAResponse(), httpResponse.getResultAsString());
				
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
	
	private MedidaPersonalDTO montarDTO() {
		
		final MedidaPersonalDTO dto = new MedidaPersonalDTO();
		dto.setIdPessoa(this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		dto.setDobra(Dobra.getEnumByValue(this.dobrasCalc.getSelected()));
		
		if (nivelMaturacao.isVisible()){
			dto.setNivelMaturacao(NivelMaturacao.getEnumByValue(nivelMaturacao.getSelected()));
		}
		
		dto.setPercentualPesoMaximoRec(
				String.valueOf(
						(100 - this.sliderPercentualPesoMaxRec.getValue()) / 100));
		
		final List<String> datas = new ArrayList<String>();
		
		for (CheckBox c : this.listCheckDatasMedidas){
			
			if (c.isChecked()){
				
				datas.add(c.getText().toString());
			}
		}
		
		dto.setDatasMedidas(datas);
		
		return dto;
	}
}
