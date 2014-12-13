package br.com.tapananuca.gereacademia.telas;

import java.util.Date;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTOResponse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.TimeUtils;

public class MedidasTab extends Tab {

	private Stage stage;
	private Utils utils;
	private CadastroPessoaScreen cadastroPessoaScreen;
	
	private SelectBox<String> dataReferenteMedida;
	
	private TextField maPesoCorporal;
	private TextField maAltura;
	private TextField maImc;
	private TextField maCintura;
	private TextField maQuadril;
	
	private TextField mcTorax;
	private TextField mcAbdomen;
	private TextField mcCintura;
	private TextField mcBiceps;
	private TextField mcTriceps;
	private TextField mcCoxa;
	private TextField mcAntebraco;
	
	private TextField dcBiceps;
	private TextField dcTriceps;
	private TextField dcSubAxilar;
	private TextField dcSupraIliacas;
	private TextField dcSubEscapular;
	private TextField dcToraxica;
	private TextField dcAbdominal;
	private TextField dcCoxa;
	private TextField dcPerna;
	
	private Window janelaNovaDataMedida;
	
	private final ChangeListener dataRefChangeListener;
	
	@SuppressWarnings("deprecation")
	public MedidasTab(final Button button, CadastroPessoaScreen cadastroPessoaScreen, int alinhamento){
		
		button.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (button.isChecked()){
					MedidasTab.this.carregarMedidas();
				}
			}
		});
		
		this.dataRefChangeListener = new DataReferenteChangeListener();
		
		this.setBotao(button);
		this.skin = cadastroPessoaScreen.skin;
		this.stage = cadastroPessoaScreen.stage;
		this.utils = Utils.getInstance();
		this.cadastroPessoaScreen = cadastroPessoaScreen;
		
		this.alinhamento = alinhamento;
		
		this.conteudo = new Table(skin);
		
		Table inTable = new Table(skin);
		inTable.add("Data referente: ").left();
		
		dataReferenteMedida = new SelectBox<String>(skin);
		Date d = new Date(TimeUtils.millis());
		final String data = d.getDate() + "/" + (d.getMonth() + 1) + "/" + (d.getYear() + 1900);
		dataReferenteMedida.setItems(data);
		dataReferenteMedida.addListener(dataRefChangeListener);
		
		inTable.add(dataReferenteMedida).left();
		
		final TextButton addData = new TextButton("+", skin);
		addData.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				janelaNovaDataMedida = new Window("Data nova medida", skin);
				janelaNovaDataMedida.setModal(true);
				
				final TextField campoData = new TextField("", skin);
				campoData.setTextFieldFilter(utils.dateFilter);
				
				janelaNovaDataMedida.add(campoData).colspan(2).row();
				
				final TextButton btnOk = new TextButton("Ok", skin);
				btnOk.addListener(new ChangeListener() {
					
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						
						MedidasTab.this.adicionarDataMedidas(campoData.getText());
					}
				});
				janelaNovaDataMedida.add(btnOk);
				
				final TextButton btnCancelar = new TextButton("Cancelar", skin);
				btnCancelar.addListener(new ChangeListener() {
					
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						
						janelaNovaDataMedida.remove();
					}
				});
				janelaNovaDataMedida.add(btnCancelar);
				
				janelaNovaDataMedida.setWidth(300);
				janelaNovaDataMedida.setHeight(300);
				
				janelaNovaDataMedida.setPosition(
						(int)((Gdx.graphics.getWidth() / 2) - (janelaNovaDataMedida.getWidth() / 2)) ,
						(int)((Gdx.graphics.getHeight() / 2) - (janelaNovaDataMedida.getHeight() / 2)));
				
				stage.addActor(janelaNovaDataMedida);
			}
		});
		
		inTable.add(addData).padLeft(5);
		
		conteudo.add(inTable).left().row();
		
		conteudo.add("Medidas Antropométricas:").left().padTop(15).row();
		
		inTable = new Table(skin);
		inTable.add("Peso corporal:").left();
		inTable.add("Altura:").padLeft(10).left();
		inTable.add("I.M.C.:").padLeft(10).left().row();
		
		maPesoCorporal = new TextField("", skin);
		maPesoCorporal.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maPesoCorporal).left();
		maPesoCorporal.addListener(new FocusListener(){
			
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
				
				if (!focused){
					
					MedidasTab.this.calcularIMC();
				}
			}
		});
		cadastroPessoaScreen.elementosFocaveis.add(maPesoCorporal);
		
		maAltura = new TextField("", skin);
		maAltura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maAltura).padLeft(10).left();
		maAltura.addListener(new FocusListener(){
			
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
				
				if (!focused){
					
					MedidasTab.this.calcularIMC();
				}
			}
		});
		cadastroPessoaScreen.elementosFocaveis.add(maAltura);
		
		maImc = new TextField("", skin);
		maImc.setTextFieldFilter(utils.currencyFilter);
		maImc.setDisabled(true);
		inTable.add(maImc).padLeft(10).left().row();
		
		inTable.add("Cintura:").left();
		inTable.add("Quadril:").padLeft(10).left().row();
		
		maCintura = new TextField("", skin);
		maCintura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maCintura).left();
		cadastroPessoaScreen.elementosFocaveis.add(maCintura);
		
		maQuadril = new TextField("", skin);
		maQuadril.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maQuadril).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(maQuadril);
		
		conteudo.add(inTable).left().row();
		
		conteudo.add("Medidas Circunferênciais:").padTop(15).left().row();
		
		inTable = new Table(skin);
		inTable.add("Tórax:").left();
		inTable.add("Abdômen:").padLeft(10).left();
		inTable.add("Cintura:").padLeft(10).left();
		inTable.add("Bíceps:").padLeft(10).left().row();
		
		mcTorax = new TextField("", skin);
		mcTorax.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcTorax).left();
		cadastroPessoaScreen.elementosFocaveis.add(mcTorax);
		
		mcAbdomen = new TextField("", skin);
		mcAbdomen.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcAbdomen).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(mcAbdomen);
		
		mcCintura = new TextField("", skin);
		mcCintura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcCintura).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(mcCintura);
		
		mcBiceps = new TextField("", skin);
		mcBiceps.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcBiceps).padLeft(10).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(mcBiceps);
		
		inTable.add("Tríceps:").left();
		inTable.add("Coxa:").padLeft(10).left();
		inTable.add("Antebraço:").padLeft(10).left().row();
		
		mcTriceps = new TextField("", skin);
		mcTriceps.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcTriceps).left();
		cadastroPessoaScreen.elementosFocaveis.add(mcTriceps);
		
		mcCoxa = new TextField("", skin);
		mcCoxa.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcCoxa).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(mcCoxa);
		
		mcAntebraco = new TextField("", skin);
		mcAntebraco.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcAntebraco).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(mcAntebraco);
		
		conteudo.add(inTable).left().row();
		
		conteudo.add("Dobras Cutâneas").padTop(15).left().row();
		
		inTable = new Table(skin);
		inTable.add("Bíceps:").left();
		inTable.add("Tríceps:").padLeft(10).left();
		inTable.add("Sub-axilar:").padLeft(10).left().row();
		
		dcBiceps = new TextField("", skin);
		dcBiceps.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcBiceps).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcBiceps);
		
		dcTriceps = new TextField("", skin);
		dcTriceps.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcTriceps).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcTriceps);
		
		dcSubAxilar = new TextField("", skin);
		dcSubAxilar.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcSubAxilar).padLeft(10).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(dcSubAxilar);
		
		inTable.add("Supra-ilíacas:").left();
		inTable.add("Subescapular:").padLeft(10).left();
		inTable.add("Toráxica:").padLeft(10).left().row();
		
		dcSupraIliacas = new TextField("", skin);
		dcSupraIliacas.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcSupraIliacas).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcSupraIliacas);
		
		dcSubEscapular = new TextField("", skin);
		dcSubEscapular.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcSubEscapular).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcSubEscapular);
		
		dcToraxica = new TextField("", skin);
		dcToraxica.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcToraxica).padLeft(10).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(dcToraxica);
		
		inTable.add("Abdominal:").left();
		inTable.add("Coxa:").padLeft(10).left();
		inTable.add("Perna:").padLeft(10).left().row();
		
		dcAbdominal = new TextField("", skin);
		dcAbdominal.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcAbdominal).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcAbdominal);
		
		dcCoxa = new TextField("", skin);
		dcCoxa.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcCoxa).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcCoxa);
		
		dcPerna = new TextField("", skin);
		dcPerna.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcPerna).padLeft(10).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(dcPerna);
		
		conteudo.add(inTable).left().row();
		
		final TextButton botaoSalvarMedidas = new TextButton("Salvar", skin);
		botaoSalvarMedidas.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				MedidasTab.this.salvarMedidas();
			}
		});
		
		conteudo.add(botaoSalvarMedidas).left();
		
		
		this.inicializar();
	}
	
	class DataReferenteChangeListener extends ChangeListener{

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			
			MedidasTab.this.carregarMedidas();
		}
	}

	private void carregarMedidas() {
		
		//can't happen
		if (this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final MedidaDTO dto = new MedidaDTO();
		dto.setIdPessoa(this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		dto.setDataReferente(this.dataReferenteMedida.getSelected());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_MEDIDAS, dto);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final MedidaDTOResponse resp = 
						utils.fromJson(MedidaDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					final MedidaDTO dto = resp.getMedidaDTO();
					
					if (dto != null){
						
						MedidasTab.this.maPesoCorporal.setText(utils.emptyOrString(dto.getMaPesoCorporal()).replace(".", ","));
						MedidasTab.this.maAltura.setText(utils.emptyOrString(dto.getMaAltura()).replace(".", ","));
						MedidasTab.this.maCintura.setText(utils.emptyOrString(dto.getMaCintura()).replace(".", ","));
						MedidasTab.this.maQuadril.setText(utils.emptyOrString(dto.getMaQuadril()).replace(".", ","));
						
						MedidasTab.this.mcTorax.setText(utils.emptyOrString(dto.getMcTorax()).replace(".", ","));
						MedidasTab.this.mcAbdomen.setText(utils.emptyOrString(dto.getMcAbdomen()).replace(".", ","));
						MedidasTab.this.mcCintura.setText(utils.emptyOrString(dto.getMcCintura()).replace(".", ","));
						MedidasTab.this.mcBiceps.setText(utils.emptyOrString(dto.getMcBiceps()).replace(".", ","));
						MedidasTab.this.mcTriceps.setText(utils.emptyOrString(dto.getMcTriceps()).replace(".", ","));
						MedidasTab.this.mcCoxa.setText(utils.emptyOrString(dto.getMcCoxa()).replace(".", ","));
						MedidasTab.this.mcAntebraco.setText(utils.emptyOrString(dto.getMcAntebraco()).replace(".", ","));
						
						MedidasTab.this.dcBiceps.setText(utils.emptyOrString(dto.getDcBiceps()).replace(".", ","));
						MedidasTab.this.dcTriceps.setText(utils.emptyOrString(dto.getDcTriceps()).replace(".", ","));
						MedidasTab.this.dcSubAxilar.setText(utils.emptyOrString(dto.getDcSubAxilar()).replace(".", ","));
						MedidasTab.this.dcSupraIliacas.setText(utils.emptyOrString(dto.getDcSupraIliacas()).replace(".", ","));
						MedidasTab.this.dcSubEscapular.setText(utils.emptyOrString(dto.getDcSubEscapular()).replace(".", ","));
						MedidasTab.this.dcToraxica.setText(utils.emptyOrString(dto.getDcToraxica()).replace(".", ","));
						MedidasTab.this.dcAbdominal.setText(utils.emptyOrString(dto.getDcAbdominal()).replace(".", ","));
						MedidasTab.this.dcCoxa.setText(utils.emptyOrString(dto.getDcCoxa()).replace(".", ","));
						MedidasTab.this.dcPerna.setText(utils.emptyOrString(dto.getDcPerna()).replace(".", ","));
						
						MedidasTab.this.calcularIMC();
					}
					
					final String ultimaSelecao = MedidasTab.this.dataReferenteMedida.getSelected();
					
					MedidasTab.this.dataReferenteMedida.removeListener(dataRefChangeListener);
					MedidasTab.this.dataReferenteMedida.clearItems();
					
					final String[] es = new String[resp.getDatasRef().size];
					
					int index = 0;
					for (String e : resp.getDatasRef()){
						es[index] = e;
						index++;
					}
					
					MedidasTab.this.dataReferenteMedida.setItems(es);
					MedidasTab.this.dataReferenteMedida.setSelected(ultimaSelecao);
					MedidasTab.this.dataReferenteMedida.addListener(dataRefChangeListener);
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar carregar medidas: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}
	
	private void salvarMedidas() {
		
		//cant happen no mater what, but...
		if (this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final MedidaDTO medidaDTO = new MedidaDTO();
		medidaDTO.setIdPessoa(this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		medidaDTO.setDataReferente(this.dataReferenteMedida.getSelected());
		
		medidaDTO.setMaPesoCorporal(this.maPesoCorporal.getText().replace(",", "."));
		medidaDTO.setMaAltura(this.maAltura.getText().replace(",", "."));
		medidaDTO.setMaCintura(this.maCintura.getText().replace(",", "."));
		medidaDTO.setMaQuadril(this.maQuadril.getText().replace(",", "."));
		
		medidaDTO.setMcTorax(this.mcTorax.getText().replace(",", "."));
		medidaDTO.setMcAbdomen(this.mcAbdomen.getText().replace(",", "."));
		medidaDTO.setMcCintura(this.mcCintura.getText().replace(",", "."));
		medidaDTO.setMcBiceps(this.mcBiceps.getText().replace(",", "."));
		medidaDTO.setMcTriceps(this.mcTriceps.getText().replace(",", "."));
		medidaDTO.setMcCoxa(this.mcCoxa.getText().replace(",", "."));
		medidaDTO.setMcAntebraco(this.mcAntebraco.getText().replace(",", "."));
		
		medidaDTO.setDcBiceps(this.dcBiceps.getText().replace(",", "."));
		medidaDTO.setDcTriceps(this.dcTriceps.getText().replace(",", "."));
		medidaDTO.setDcSubAxilar(this.dcSubAxilar.getText().replace(",", "."));
		medidaDTO.setDcSupraIliacas(this.dcSupraIliacas.getText().replace(",", "."));
		medidaDTO.setDcSubEscapular(this.dcSubEscapular.getText().replace(",", "."));
		medidaDTO.setDcToraxica(this.dcToraxica.getText().replace(",", "."));
		medidaDTO.setDcAbdominal(this.dcAbdominal.getText().replace(",", "."));
		medidaDTO.setDcCoxa(this.dcCoxa.getText().replace(",", "."));
		medidaDTO.setDcPerna(this.dcPerna.getText().replace(",", "."));
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_MEDIDAS_SALVAR, medidaDTO);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					utils.mostarAlerta(
							null, 
							"Medidas da data " + MedidasTab.this.dataReferenteMedida.getSelected() + "salvas com sucesso.", stage, skin);
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar salvar medidas: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}


	private void calcularIMC() {
		
		final String peso = this.maPesoCorporal.getText().replace(",", ".");
		final String altura = this.maAltura.getText().replace(",", ".");
		
		if (!peso.isEmpty() && !altura.isEmpty()){
			
			this.maImc.setText(
				String.valueOf(
					Double.valueOf(peso) / (Double.valueOf(altura) * Double.valueOf(altura)
				)).replace(".", ","));
		}
	}

	private void adicionarDataMedidas(final String textData) {
		
		//can't happen
		if (this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final MedidaDTO dto = new MedidaDTO();
		dto.setIdPessoa(this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		dto.setDataReferente(textData);
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_MEDIDAS_NOVA_DATA, dto);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final MedidaDTOResponse resp = 
						utils.fromJson(MedidaDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					
							
							MedidasTab.this.dataReferenteMedida.removeListener(dataRefChangeListener);
							
							final String[] es = new String[MedidasTab.this.dataReferenteMedida.getItems().size + 1];
							
							int index = 0;
							for (String e : MedidasTab.this.dataReferenteMedida.getItems()){
								es[index] = e;
								index++;
							}
							es[index] = textData;
							
							MedidasTab.this.dataReferenteMedida.clearItems();
							MedidasTab.this.dataReferenteMedida.setItems(es);
							MedidasTab.this.dataReferenteMedida.setSelected(textData);
							MedidasTab.this.dataReferenteMedida.addListener(dataRefChangeListener);
							
							janelaNovaDataMedida.remove();
						
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar adicionar nova data: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}
}
