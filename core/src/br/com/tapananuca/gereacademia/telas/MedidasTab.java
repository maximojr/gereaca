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
	
	private TextField dcBiceps1;
	private TextField dcBiceps2;
	private TextField dcBiceps3;
	private TextField dcTriceps1;
	private TextField dcTriceps2;
	private TextField dcTriceps3;
	private TextField dcSubAxilar1;
	private TextField dcSubAxilar2;
	private TextField dcSubAxilar3;
	private TextField dcSupraIliacas1;
	private TextField dcSupraIliacas2;
	private TextField dcSupraIliacas3;
	private TextField dcSubEscapular1;
	private TextField dcSubEscapular2;
	private TextField dcSubEscapular3;
	private TextField dcToraxica1;
	private TextField dcToraxica2;
	private TextField dcToraxica3;
	private TextField dcAbdominal1;
	private TextField dcAbdominal2;
	private TextField dcAbdominal3;
	private TextField dcCoxa1;
	private TextField dcCoxa2;
	private TextField dcCoxa3;
	private TextField dcPerna1;
	private TextField dcPerna2;
	private TextField dcPerna3;
	
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
		inTable.add("Peso corporal (kg):").left();
		inTable.add("Altura (m):").padLeft(10).left();
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
		
		inTable.add("Cintura (cm):").left();
		inTable.add("Quadril (cm):").padLeft(10).left().row();
		
		maCintura = new TextField("", skin);
		maCintura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maCintura).left();
		cadastroPessoaScreen.elementosFocaveis.add(maCintura);
		
		maQuadril = new TextField("", skin);
		maQuadril.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maQuadril).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(maQuadril);
		
		conteudo.add(inTable).left().row();
		
		conteudo.add("Medidas Circunferênciais (cm):").padTop(15).left().row();
		
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
		
		conteudo.add("Dobras Cutâneas (mm)").padTop(15).left().row();
		
		inTable = new Table(skin);
		inTable.add("Bíceps:").left();
		inTable.add("Tríceps:").padLeft(10).left();
		inTable.add("Sub-axilar:").padLeft(10).left().row();
		
		dcBiceps1 = new TextField("", skin);
		dcBiceps1.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcBiceps1).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcBiceps1);
		
		dcTriceps1 = new TextField("", skin);
		dcTriceps1.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcTriceps1).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcTriceps1);
		
		dcSubAxilar1 = new TextField("", skin);
		dcSubAxilar1.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcSubAxilar1).padLeft(10).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(dcSubAxilar1);
		
		inTable.add("Supra-ilíacas:").left();
		inTable.add("Subescapular:").padLeft(10).left();
		inTable.add("Toráxica:").padLeft(10).left().row();
		
		dcSupraIliacas1 = new TextField("", skin);
		dcSupraIliacas1.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcSupraIliacas1).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcSupraIliacas1);
		
		dcSubEscapular1 = new TextField("", skin);
		dcSubEscapular1.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcSubEscapular1).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcSubEscapular1);
		
		dcToraxica1 = new TextField("", skin);
		dcToraxica1.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcToraxica1).padLeft(10).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(dcToraxica1);
		
		inTable.add("Abdominal:").left();
		inTable.add("Coxa:").padLeft(10).left();
		inTable.add("Perna:").padLeft(10).left().row();
		
		dcAbdominal1 = new TextField("", skin);
		dcAbdominal1.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcAbdominal1).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcAbdominal1);
		
		dcCoxa1 = new TextField("", skin);
		dcCoxa1.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcCoxa1).padLeft(10).left();
		cadastroPessoaScreen.elementosFocaveis.add(dcCoxa1);
		
		dcPerna1 = new TextField("", skin);
		dcPerna1.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcPerna1).padLeft(10).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(dcPerna1);
		
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
			
			MedidasTab.this.maPesoCorporal.setText("");
			MedidasTab.this.maAltura.setText("");
			MedidasTab.this.maCintura.setText("");
			MedidasTab.this.maQuadril.setText("");
			MedidasTab.this.maImc.setText("");
			
			MedidasTab.this.mcTorax.setText("");
			MedidasTab.this.mcAbdomen.setText("");
			MedidasTab.this.mcCintura.setText("");
			MedidasTab.this.mcBiceps.setText("");
			MedidasTab.this.mcTriceps.setText("");
			MedidasTab.this.mcCoxa.setText("");
			MedidasTab.this.mcAntebraco.setText("");
			
			MedidasTab.this.dcBiceps1.setText("");
			MedidasTab.this.dcBiceps2.setText("");
			MedidasTab.this.dcBiceps3.setText("");
			MedidasTab.this.dcTriceps1.setText("");
			MedidasTab.this.dcTriceps2.setText("");
			MedidasTab.this.dcTriceps3.setText("");
			MedidasTab.this.dcSubAxilar1.setText("");
			MedidasTab.this.dcSubAxilar2.setText("");
			MedidasTab.this.dcSubAxilar3.setText("");
			MedidasTab.this.dcSupraIliacas1.setText("");
			MedidasTab.this.dcSupraIliacas2.setText("");
			MedidasTab.this.dcSupraIliacas3.setText("");
			MedidasTab.this.dcSubEscapular1.setText("");
			MedidasTab.this.dcSubEscapular2.setText("");
			MedidasTab.this.dcSubEscapular3.setText("");
			MedidasTab.this.dcToraxica1.setText("");
			MedidasTab.this.dcToraxica2.setText("");
			MedidasTab.this.dcToraxica3.setText("");
			MedidasTab.this.dcAbdominal1.setText("");
			MedidasTab.this.dcAbdominal2.setText("");
			MedidasTab.this.dcAbdominal3.setText("");
			MedidasTab.this.dcCoxa1.setText("");
			MedidasTab.this.dcCoxa2.setText("");
			MedidasTab.this.dcCoxa3.setText("");
			MedidasTab.this.dcPerna1.setText("");
			MedidasTab.this.dcPerna2.setText("");
			MedidasTab.this.dcPerna3.setText("");
			
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
						
						MedidasTab.this.dcBiceps1.setText(utils.emptyOrString(dto.getDcBiceps1()).replace(".", ","));
						MedidasTab.this.dcTriceps1.setText(utils.emptyOrString(dto.getDcTriceps1()).replace(".", ","));
						MedidasTab.this.dcSubAxilar1.setText(utils.emptyOrString(dto.getDcSubAxilar1()).replace(".", ","));
						MedidasTab.this.dcSupraIliacas1.setText(utils.emptyOrString(dto.getDcSupraIliacas1()).replace(".", ","));
						MedidasTab.this.dcSubEscapular1.setText(utils.emptyOrString(dto.getDcSubEscapular1()).replace(".", ","));
						MedidasTab.this.dcToraxica1.setText(utils.emptyOrString(dto.getDcToraxica1()).replace(".", ","));
						MedidasTab.this.dcAbdominal1.setText(utils.emptyOrString(dto.getDcAbdominal1()).replace(".", ","));
						MedidasTab.this.dcCoxa1.setText(utils.emptyOrString(dto.getDcCoxa1()).replace(".", ","));
						MedidasTab.this.dcPerna1.setText(utils.emptyOrString(dto.getDcPerna1()).replace(".", ","));
						
						MedidasTab.this.dcBiceps2.setText(utils.emptyOrString(dto.getDcBiceps2()).replace(".", ","));
						MedidasTab.this.dcTriceps2.setText(utils.emptyOrString(dto.getDcTriceps2()).replace(".", ","));
						MedidasTab.this.dcSubAxilar2.setText(utils.emptyOrString(dto.getDcSubAxilar2()).replace(".", ","));
						MedidasTab.this.dcSupraIliacas2.setText(utils.emptyOrString(dto.getDcSupraIliacas2()).replace(".", ","));
						MedidasTab.this.dcSubEscapular2.setText(utils.emptyOrString(dto.getDcSubEscapular2()).replace(".", ","));
						MedidasTab.this.dcToraxica2.setText(utils.emptyOrString(dto.getDcToraxica2()).replace(".", ","));
						MedidasTab.this.dcAbdominal2.setText(utils.emptyOrString(dto.getDcAbdominal2()).replace(".", ","));
						MedidasTab.this.dcCoxa2.setText(utils.emptyOrString(dto.getDcCoxa2()).replace(".", ","));
						MedidasTab.this.dcPerna2.setText(utils.emptyOrString(dto.getDcPerna2()).replace(".", ","));
						
						MedidasTab.this.dcBiceps3.setText(utils.emptyOrString(dto.getDcBiceps3()).replace(".", ","));
						MedidasTab.this.dcTriceps3.setText(utils.emptyOrString(dto.getDcTriceps3()).replace(".", ","));
						MedidasTab.this.dcSubAxilar3.setText(utils.emptyOrString(dto.getDcSubAxilar3()).replace(".", ","));
						MedidasTab.this.dcSupraIliacas3.setText(utils.emptyOrString(dto.getDcSupraIliacas3()).replace(".", ","));
						MedidasTab.this.dcSubEscapular3.setText(utils.emptyOrString(dto.getDcSubEscapular3()).replace(".", ","));
						MedidasTab.this.dcToraxica3.setText(utils.emptyOrString(dto.getDcToraxica3()).replace(".", ","));
						MedidasTab.this.dcAbdominal3.setText(utils.emptyOrString(dto.getDcAbdominal3()).replace(".", ","));
						MedidasTab.this.dcCoxa3.setText(utils.emptyOrString(dto.getDcCoxa3()).replace(".", ","));
						MedidasTab.this.dcPerna3.setText(utils.emptyOrString(dto.getDcPerna3()).replace(".", ","));
						
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
		
		medidaDTO.setDcBiceps1(this.dcBiceps1.getText().replace(",", "."));
		medidaDTO.setDcTriceps1(this.dcTriceps1.getText().replace(",", "."));
		medidaDTO.setDcSubAxilar1(this.dcSubAxilar1.getText().replace(",", "."));
		medidaDTO.setDcSupraIliacas1(this.dcSupraIliacas1.getText().replace(",", "."));
		medidaDTO.setDcSubEscapular1(this.dcSubEscapular1.getText().replace(",", "."));
		medidaDTO.setDcToraxica1(this.dcToraxica1.getText().replace(",", "."));
		medidaDTO.setDcAbdominal1(this.dcAbdominal1.getText().replace(",", "."));
		medidaDTO.setDcCoxa1(this.dcCoxa1.getText().replace(",", "."));
		medidaDTO.setDcPerna1(this.dcPerna1.getText().replace(",", "."));
		
		medidaDTO.setDcBiceps2(this.dcBiceps2.getText().replace(",", "."));
		medidaDTO.setDcTriceps2(this.dcTriceps2.getText().replace(",", "."));
		medidaDTO.setDcSubAxilar2(this.dcSubAxilar2.getText().replace(",", "."));
		medidaDTO.setDcSupraIliacas2(this.dcSupraIliacas2.getText().replace(",", "."));
		medidaDTO.setDcSubEscapular2(this.dcSubEscapular2.getText().replace(",", "."));
		medidaDTO.setDcToraxica2(this.dcToraxica2.getText().replace(",", "."));
		medidaDTO.setDcAbdominal2(this.dcAbdominal2.getText().replace(",", "."));
		medidaDTO.setDcCoxa2(this.dcCoxa2.getText().replace(",", "."));
		medidaDTO.setDcPerna2(this.dcPerna2.getText().replace(",", "."));
		
		medidaDTO.setDcBiceps3(this.dcBiceps3.getText().replace(",", "."));
		medidaDTO.setDcTriceps3(this.dcTriceps3.getText().replace(",", "."));
		medidaDTO.setDcSubAxilar3(this.dcSubAxilar3.getText().replace(",", "."));
		medidaDTO.setDcSupraIliacas3(this.dcSupraIliacas3.getText().replace(",", "."));
		medidaDTO.setDcSubEscapular3(this.dcSubEscapular3.getText().replace(",", "."));
		medidaDTO.setDcToraxica3(this.dcToraxica3.getText().replace(",", "."));
		medidaDTO.setDcAbdominal3(this.dcAbdominal3.getText().replace(",", "."));
		medidaDTO.setDcCoxa3(this.dcCoxa3.getText().replace(",", "."));
		medidaDTO.setDcPerna3(this.dcPerna3.getText().replace(",", "."));
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_MEDIDAS_SALVAR, medidaDTO);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					utils.mostarAlerta(
							null, 
							"Medidas da data " + MedidasTab.this.dataReferenteMedida.getSelected() + " salvas com sucesso.", stage, skin);
					
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
					Double.valueOf(peso) / ((Double.valueOf(altura) * Double.valueOf(altura))
				)).replace(".", ","));
		} else {
			
			this.maImc.setText("");
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
