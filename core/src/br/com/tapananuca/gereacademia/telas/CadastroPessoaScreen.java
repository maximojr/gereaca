package br.com.tapananuca.gereacademia.telas;

import java.util.Date;

import br.com.tapananuca.gereacademia.GereAcademia;
import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.Dieta;
import br.com.tapananuca.gereacademia.comunicacao.EstadoCivil;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTO;
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTO;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTO;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.Periodicidade;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTOResponse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

public class CadastroPessoaScreen extends Tela {

	private final Utils utils;
	
	private final Skin skin;
	
	private Long pessoaEdicaoId = null;
	
	//campos dados cadastrais
	private TextField nome;
	private TextField dataNasc;
	private SelectBox<String> listEstadoCivil;
	private SelectBox<Character> listSexo;
	private TextField endereco;
	private TextField numero;
	private TextField bairro;
	private TextField telefone;
	private TextField email;
	private TextField dataInicio;
	private TextField valorMensal;
	
	//campos objetivos com o personal
	private CheckBox checkEstetica;
	private CheckBox checkLazer;
	private CheckBox checkSaude;
	private CheckBox checkTerapeutico;
	private CheckBox checkCondFisico;
	private CheckBox checkPrepFisica;
	private CheckBox checkAutoRend;
	private CheckBox checkHipertrofia;
	private CheckBox checkEmagrecimento;
	
	//campos história patologica
	private TextArea cirurgias;
	private TextArea sintomasDoencas;
	private TextArea medicamentos;
	private TextArea lesoes;
	private TextArea alergias;
	private TextArea outros;
	
	private CheckBox cardiopatia;
	private CheckBox hipertensao;
	
	//campos hábitos
	private SelectBox<String> dieta;
	
	private TextField atividadeFisica;
	private TextField ultimoExame;
	
	private TextField qtdTempoPeriodoExame;
	private SelectBox<String> periodoExame;
	
	//campos medidas
	private SelectBox<String> dataReferenteMedida;
	
	private TextField maPesoCorporal;
	private TextField maAltura;
	private TextField maPesoMagro;
	private TextField maPesoGordura;
	private TextField maPorcentagemPG;
	private TextField maImc;
	private TextField maCintura;
	private TextField maQuadril;
	private TextField maPmrc;
	
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
	
	private final ChangeListener dataRefChangeListener;
	
	//botões tabbed pannel
	private TextButton botaoTabObjetivos;
	private TextButton botaoTabHistPat;
	private TextButton botaoTabHabitos;
	private TextButton botaoTabMedidas;
	
	public CadastroPessoaScreen(GereAcademia applicationListener) {
		super(applicationListener);
		
		utils = Utils.getInstance();
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		this.dataRefChangeListener = new DataReferenteChangeListener();
		
		final Table table = new Table();
		//table.debugAll();
		table.setFillParent(true);
		table.top();
		stage.addActor(table);
		table.setSkin(skin);
		
		table.add("Novo cliente").colspan(2).row();
		
		final TabbedPanel tabbedPanel = new TabbedPanel(skin, 650f, 700f);
		table.add(tabbedPanel).row();
		
		tabbedPanel.addTab(this.montarTabDadosCadastrais());
		tabbedPanel.addTab(this.montarObjetivos());
		tabbedPanel.addTab(this.montarHistoriaPatologica());
		tabbedPanel.addTab(this.montarHabitos());
		tabbedPanel.addTab(this.montarMedidas());
		
		//tabbedPanel.debugAll();
		
		final TextButton botaoVoltar = new TextButton("Voltar", skin);
		botaoVoltar.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				CadastroPessoaScreen.this.pessoaEdicaoId = null;
				final GereAcademia gereAcademia = CadastroPessoaScreen.this.applicationListener;
				MenuPrincipalScreen menuPrincipalScreen = new MenuPrincipalScreen(gereAcademia);
				gereAcademia.setTelaAtual(menuPrincipalScreen);
				
				final Date d = new Date(TimeUtils.millis());
				@SuppressWarnings("deprecation")
				final String dataRef = (d.getMonth() + 1) + "/" + (d.getYear() + 1900);
				
				menuPrincipalScreen.carregarTabelasMenuPrincipal(1, dataRef);
			}
		});
		
		table.add(botaoVoltar).left().row();
	}
	
	public void editar(){
		
		final PessoaDTO dto = new PessoaDTO(this.pessoaEdicaoId, null);
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_DADOS_BASICOS, dto);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final PessoaDTOResponse resp = utils.fromJson(PessoaDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					if (resp.getPessoasDTO() != null && resp.getPessoasDTO().size != 0){
						
						final PessoaDTO pessoaDTO = resp.getPessoasDTO().first();
						
						CadastroPessoaScreen.this.nome.setText(pessoaDTO.getNome());
						CadastroPessoaScreen.this.dataNasc.setText(pessoaDTO.getDataNascimento());
						CadastroPessoaScreen.this.listSexo.setSelected(pessoaDTO.getSexo());
						CadastroPessoaScreen.this.endereco.setText(pessoaDTO.getEndereco());
						CadastroPessoaScreen.this.numero.setText(pessoaDTO.getNumero() == null ? "" : pessoaDTO.getNumero().toString());
						CadastroPessoaScreen.this.bairro.setText(pessoaDTO.getBairro());
						CadastroPessoaScreen.this.telefone.setText(pessoaDTO.getTelefone());
						CadastroPessoaScreen.this.email.setText(pessoaDTO.getEmail());
						CadastroPessoaScreen.this.valorMensal.setText(pessoaDTO.getValorMensal().toString().replace(".", ","));
						CadastroPessoaScreen.this.dataInicio.setText(pessoaDTO.getDataInicio());
						CadastroPessoaScreen.this.listEstadoCivil.setSelected(pessoaDTO.getEstadoCivil().getDescricao());
						
						CadastroPessoaScreen.this.botaoTabObjetivos.setDisabled(false);
						CadastroPessoaScreen.this.botaoTabHistPat.setDisabled(false);
						CadastroPessoaScreen.this.botaoTabHabitos.setDisabled(false);
						CadastroPessoaScreen.this.botaoTabMedidas.setDisabled(false);
					}
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar salvar dados: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}
	
	private void salvarDadosCadastrais(){
		
		final PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setId(this.pessoaEdicaoId);
		pessoaDTO.setNome(this.nome.getText());
		pessoaDTO.setDataNascimento(this.dataNasc.getText());
		pessoaDTO.setSexo(this.listSexo.getSelected().toString().charAt(0));
		pessoaDTO.setEndereco(this.endereco.getText());
		pessoaDTO.setNumero(this.numero.getText());
		pessoaDTO.setBairro(this.bairro.getText());
		pessoaDTO.setTelefone(this.telefone.getText());
		pessoaDTO.setEmail(this.email.getText());
		pessoaDTO.setValorMensal(Float.valueOf(this.valorMensal.getText().replace(",", ".")));
		pessoaDTO.setEstadoCivil(EstadoCivil.getEnumByValue(this.listEstadoCivil.getSelected()));
		pessoaDTO.setDataInicio(this.dataInicio.getText());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_DADOS_BASICOS_SALVAR, pessoaDTO);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					utils.mostarAlerta(null, "Dados salvos com sucesso.", stage, skin);
					
					CadastroPessoaScreen.this.botaoTabObjetivos.setDisabled(false);
					CadastroPessoaScreen.this.botaoTabHistPat.setDisabled(false);
					CadastroPessoaScreen.this.botaoTabHabitos.setDisabled(false);
					CadastroPessoaScreen.this.botaoTabMedidas.setDisabled(false);
					
					CadastroPessoaScreen.this.pessoaEdicaoId = Long.valueOf(resp.getSessionId());
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar salvar dados: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}
	
	private Tab montarTabDadosCadastrais(){
		
		final Table conteudo = new Table(skin);
		
		conteudo.add(new Label("Nome:", skin)).left().colspan(2).row();
		
		final Table inTableNome = new Table();
		
		nome = new TextField("", skin);
		inTableNome.add(nome).colspan(2).width(450).left();
		elementosFocaveis.add(nome);
		
		final TextButton pesquisar = new TextButton("Pesquisar", skin);
		pesquisar.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				CadastroPessoaScreen.this.abrirDialogPesquisaPessoa();
			}
		});
		
		inTableNome.add(pesquisar);
		
		conteudo.add(inTableNome).left().colspan(2).row();
		
		final Table inTableDataNasc = new Table();
		inTableDataNasc.setSkin(skin);
		
		inTableDataNasc.add("Data nasc.:").left();
		inTableDataNasc.add("Estado cívil:").padLeft(10).left();
		inTableDataNasc.add("Sexo").left().padLeft(10).row();
		
		dataNasc = new TextField("", skin);
		dataNasc.setTextFieldFilter(utils.dateFilter);
		inTableDataNasc.add(dataNasc).left();
		elementosFocaveis.add(dataNasc);
		
		listEstadoCivil = new SelectBox<String>(skin);
		final String[] es = new String[EstadoCivil.values().length];
		
		int index = 0;
		for (EstadoCivil e : EstadoCivil.values()){
			es[index] = e.getDescricao();
			index++;
		}
		
		listEstadoCivil.setItems(es);
		inTableDataNasc.add(listEstadoCivil).padLeft(10);
		
		listSexo = new SelectBox<Character>(skin);
		listSexo.setItems('M', 'F');
		inTableDataNasc.add(listSexo).padLeft(10).row();
		
		conteudo.add(inTableDataNasc).left().row();
		
		final Table inTableEndereco = new Table(skin);
		
		inTableEndereco.add("Endereço:").left();
		inTableEndereco.add("Número:").padLeft(10).left().row();
		
		endereco = new TextField("", skin);
		inTableEndereco.add(endereco).width(400).left();
		elementosFocaveis.add(endereco);
		
		numero = new TextField("", skin);
		numero.setTextFieldFilter(utils.numbersOnlyFilter);
		inTableEndereco.add(numero).padLeft(10).left().row();
		elementosFocaveis.add(numero);
		
		conteudo.add(inTableEndereco).left().row();
		
		conteudo.add("Bairro:").left().row();
		
		bairro = new TextField("", skin);
		conteudo.add(bairro).width(300).left().row();
		elementosFocaveis.add(bairro);
		
		final Table inTableTelefone = new Table();
		inTableTelefone.setSkin(skin);
		
		inTableTelefone.add("Telefone:").left();
		inTableTelefone.add("E-mail").padLeft(10).left();
		inTableTelefone.add("Data início:").padLeft(10).left().row();
		
		telefone = new TextField("", skin);
		telefone.setTextFieldFilter(utils.phoneFilter);
		inTableTelefone.add(telefone).left();
		elementosFocaveis.add(telefone);
		
		email = new TextField("", skin);
		inTableTelefone.add(email).padLeft(10).width(200).left();
		elementosFocaveis.add(email);
		
		dataInicio = new TextField("", skin);
		inTableTelefone.add(dataInicio).padLeft(10).left();
		dataInicio.setTextFieldFilter(utils.dateFilter);
		elementosFocaveis.add(dataInicio);
		
		conteudo.add(inTableTelefone).left().row();
		
		conteudo.add("Valor Mensal:").left().row();
		
		valorMensal = new TextField("", skin);
		valorMensal.setTextFieldFilter(utils.currencyFilter);
		conteudo.add(valorMensal).left().row().padTop(30);
		elementosFocaveis.add(valorMensal);
		
		final TextButton botaoSalvar = new TextButton("Salvar", skin);
		botaoSalvar.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				CadastroPessoaScreen.this.salvarDadosCadastrais();
			}
		});
		
		conteudo.add(botaoSalvar).left().row().padTop(20);
		
		final TextButton botao = new TextButton("Dados básicos", skin, "toggle");
		botao.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return new Tab(botao, conteudo, skin, Align.topLeft);
	}
	
	private void abrirDialogPesquisaPessoa() {
		
		final Window window = new Window("Escolha: ", skin);
		final Table table = new Table(skin);
		
		window.add(table);
		
		final TextButton botaoCancelar = new TextButton("Cancelar", skin);
		botaoCancelar.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				window.remove();
			}
		});
		
		final PessoaDTO dto = new PessoaDTO();
		dto.setNome(this.nome.getText());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_DADOS_NOMES, dto);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final PessoaDTOResponse resp = utils.fromJson(PessoaDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					if (resp.getPessoasDTO() != null && resp.getPessoasDTO().size != 0){
						
						for (final PessoaDTO p : resp.getPessoasDTO()){
							
							final Label label = new Label(p.getNome(), skin);
							label.addListener(new ClickListener(){

								@Override
								public void clicked(InputEvent event, float x, float y) {
									
									window.remove();
									
									CadastroPessoaScreen.this.pessoaEdicaoId = Long.valueOf(p.getId());
									CadastroPessoaScreen.this.editar();
								}
							});
							
							table.add(label).row();
						}
					} else {
						
						table.add("Nenhuma pessoa encontrada.").row();
					}
					
					table.add(botaoCancelar);
					
					//window.pack();
					table.pack();
					window.setWidth(table.getPrefWidth() + 10);
					window.setHeight(table.getHeight() + 20);
					
					window.setPosition(
							(Gdx.graphics.getWidth() / 2) - (window.getWidth() / 2) ,
							(Gdx.graphics.getHeight() / 2) - (window.getHeight() / 2));
					
					CadastroPessoaScreen.this.stage.addActor(window);
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

	private Tab montarObjetivos(){
		
		final Table conteudo = new Table(skin);
		//conteudo.debugAll();
		
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
		conteudo.add(checkPrepFisica).left();
		
		checkEmagrecimento = new CheckBox("Emagrecimento", skin);
		conteudo.add(checkEmagrecimento).left().row();
		
		final TextButton botaoSalvarObjetivos = new TextButton("Salvar", skin);
		botaoSalvarObjetivos.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				CadastroPessoaScreen.this.salvarObjetivos();
			}
		});
		
		conteudo.add(botaoSalvarObjetivos).left();
		
		botaoTabObjetivos = new TextButton("Objetivos", skin, "toggle");
		botaoTabObjetivos.setDisabled(true);
		botaoTabObjetivos.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (botaoTabObjetivos.isChecked()){
					CadastroPessoaScreen.this.carregarObjetivos();
				}
			}
		});
		
		return new Tab(botaoTabObjetivos, conteudo, skin, Align.center);
	}
	
	private void carregarObjetivos() {
		
		if (this.pessoaEdicaoId == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final ObjetivoDTO dto = new ObjetivoDTO();
		dto.setIdPessoa(this.pessoaEdicaoId.toString());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_OBJETIVOS, dto);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final ObjetivoDTOResponse resp = utils.fromJson(ObjetivoDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					if (resp.getObjetivoDTO() != null){
						
						final ObjetivoDTO dto = resp.getObjetivoDTO();
						
						CadastroPessoaScreen.this.checkEstetica.setChecked(dto.isEstetica());
						CadastroPessoaScreen.this.checkLazer.setChecked(dto.isLazer());
						CadastroPessoaScreen.this.checkSaude.setChecked(dto.isSaude());
						CadastroPessoaScreen.this.checkTerapeutico.setChecked(dto.isTerapeutico());
						CadastroPessoaScreen.this.checkCondFisico.setChecked(dto.isCondFisico());
						CadastroPessoaScreen.this.checkPrepFisica.setChecked(dto.isPrepFisica());
						CadastroPessoaScreen.this.checkAutoRend.setChecked(dto.isAutoRend());
						CadastroPessoaScreen.this.checkHipertrofia.setChecked(dto.isHipertrofia());
						CadastroPessoaScreen.this.checkEmagrecimento.setChecked(dto.isEmagrecimento());
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
		if (this.pessoaEdicaoId == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final ObjetivoDTO objetivoDTO = new ObjetivoDTO();
		objetivoDTO.setIdPessoa(this.pessoaEdicaoId.toString());
		objetivoDTO.setAutoRend(this.checkAutoRend.isChecked());
		objetivoDTO.setCondFisico(this.checkCondFisico.isChecked());
		objetivoDTO.setEmagrecimento(this.checkEmagrecimento.isChecked());
		objetivoDTO.setEstetica(this.checkEstetica.isChecked());
		objetivoDTO.setHipertrofia(this.checkHipertrofia.isChecked());
		objetivoDTO.setLazer(this.checkLazer.isChecked());
		objetivoDTO.setPrepFisica(this.checkPrepFisica.isChecked());
		objetivoDTO.setSaude(this.checkSaude.isChecked());
		objetivoDTO.setTerapeutico(this.checkTerapeutico.isChecked());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_OBJETIVOS_SALVAR, objetivoDTO);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
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

	private Tab montarHistoriaPatologica() {
		
		final Table conteudo = new Table(skin);
		
		final Table leftTable = new Table(skin);
		
		leftTable.add("Pessoal:").left().padBottom(10).row();
		
		leftTable.add("Cirurgias:").left().row();
		cirurgias = new TextArea("", skin);
		cirurgias.setPrefRows(3);
		leftTable.add(cirurgias).left().width(500).row();
		
		leftTable.add("Sintomas/Doenças:").left().row();
		sintomasDoencas = new TextArea("", skin);
		sintomasDoencas.setPrefRows(3);
		leftTable.add(sintomasDoencas).left().width(500).row();
		
		leftTable.add("Medicamentos:").left().row();
		medicamentos = new TextArea("", skin);
		medicamentos.setPrefRows(3);
		leftTable.add(medicamentos).left().width(500).row();
		
		leftTable.add("Lesões:").left().row();
		lesoes = new TextArea("", skin);
		lesoes.setPrefRows(3);
		leftTable.add(lesoes).left().width(500).row();
		
		leftTable.add("Alergias:").left().row();
		alergias = new TextArea("", skin);
		alergias.setPrefRows(3);
		leftTable.add(alergias).left().width(500).row();
		
		leftTable.add("Outras informações:").left().row();
		outros = new TextArea("", skin);
		outros.setPrefRows(3);
		leftTable.add(outros).left().width(500);
		
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
				
				CadastroPessoaScreen.this.salvarHistPat();
			}
		});
		
		conteudo.add(botaoSalvarHistPat).left();
		
		botaoTabHistPat = new TextButton("História patológica", skin, "toggle");
		botaoTabHistPat.setDisabled(true);
		botaoTabHistPat.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (botaoTabHistPat.isChecked()){
					CadastroPessoaScreen.this.carregarHistPat();
				}
			}
		});
		
		return new Tab(botaoTabHistPat, conteudo, skin, Align.topLeft);
	}
	
	private void carregarHistPat() {
		
		//can't happen
		if (this.pessoaEdicaoId == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final HistoriaPatologicaDTO dto = new HistoriaPatologicaDTO();
		dto.setIdPessoa(this.pessoaEdicaoId.toString());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_HIST_PAT, dto);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final HistoriaPatologicaDTOResponse resp = 
						utils.fromJson(HistoriaPatologicaDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					final HistoriaPatologicaDTO dto = resp.getHistoriaPatologicaDTO();
					
					if (dto != null){
						
						CadastroPessoaScreen.this.cirurgias.setText(utils.emptyOrString(dto.getCirurgias()));
						CadastroPessoaScreen.this.sintomasDoencas.setText(utils.emptyOrString(dto.getSintomasDoencas()));
						CadastroPessoaScreen.this.medicamentos.setText(utils.emptyOrString(dto.getMedicamentos()));
						CadastroPessoaScreen.this.lesoes.setText(utils.emptyOrString(dto.getLesoes()));
						CadastroPessoaScreen.this.alergias.setText(utils.emptyOrString(dto.getAlergias()));
						CadastroPessoaScreen.this.outros.setText(utils.emptyOrString(dto.getOutros()));
						
						CadastroPessoaScreen.this.cardiopatia.setChecked(dto.isCardiopatia());
						CadastroPessoaScreen.this.hipertensao.setChecked(dto.isHipertensao());
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

	private void salvarHistPat() {
		
		//cant happen no mater what, but...
		if (this.pessoaEdicaoId == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final HistoriaPatologicaDTO historiaPatologicaDTO = new HistoriaPatologicaDTO();
		historiaPatologicaDTO.setIdPessoa(this.pessoaEdicaoId.toString());
		historiaPatologicaDTO.setAlergias(this.alergias.getText());
		historiaPatologicaDTO.setCirurgias(this.cirurgias.getText());
		historiaPatologicaDTO.setLesoes(this.lesoes.getText());
		historiaPatologicaDTO.setMedicamentos(this.medicamentos.getText());
		historiaPatologicaDTO.setOutros(this.outros.getText());
		historiaPatologicaDTO.setSintomasDoencas(this.sintomasDoencas.getText());
		historiaPatologicaDTO.setCardiopatia(this.cardiopatia.isChecked());
		historiaPatologicaDTO.setHipertensao(this.hipertensao.isChecked());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_HIST_PAT_SALVAR, historiaPatologicaDTO);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
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

	private Tab montarHabitos() {
		
		final Table conteudo = new Table(skin);
		
		conteudo.add("Dieta:").left().row();
		dieta = new SelectBox<String>(skin);
		dieta.setItems("Não faz dieta", "Para perder peso", "Para ganhar peso");
		conteudo.add(dieta).left().row();
		
		conteudo.add("Pratica atividade física durante quanto tempo?").left().row();
		atividadeFisica = new TextField("Não pratica", skin);
		conteudo.add(atividadeFisica).left().row();
		
		conteudo.add("Data último exame médico:").left().row();
		ultimoExame = new TextField("", skin);
		ultimoExame.setTextFieldFilter(utils.dateFilter);
		conteudo.add(ultimoExame).left().row();
		
		conteudo.add("Periodicidade exame médico:").left().row();
		
		final Table inTable = new Table(skin);
		
		inTable.add("A cada: ").left();
		
		qtdTempoPeriodoExame = new TextField("", skin);
		qtdTempoPeriodoExame.setTextFieldFilter(utils.numbersOnlyFilter);
		inTable.add(qtdTempoPeriodoExame).left();
		
		periodoExame = new SelectBox<String>(skin);
		
		final String[] es = new String[Periodicidade.values().length + 1];
		
		es[0] = "";
		int index = 1;
		for (Periodicidade e : Periodicidade.values()){
			es[index] = e.getDescricao();
			index++;
		}
		
		periodoExame.setItems(es);
		inTable.add(periodoExame).left();
		
		conteudo.add(inTable).left().row();
		
		final TextButton botaoSalvarHabitos = new TextButton("Salvar", skin);
		botaoSalvarHabitos.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				CadastroPessoaScreen.this.salvarHabitos();
			}
		});
		
		conteudo.add(botaoSalvarHabitos).left();
		
		botaoTabHabitos = new TextButton("Hábitos", skin, "toggle");
		botaoTabHabitos.setDisabled(true);
		botaoTabHabitos.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (botaoTabHabitos.isChecked()){
					
					CadastroPessoaScreen.this.carregarHabitos();
				}
			}
		});
		
		return new Tab(botaoTabHabitos, conteudo, skin, Align.center);
	}
	
	private void carregarHabitos() {
		
		//can't happen
		if (this.pessoaEdicaoId == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final HabitosDTO dto = new HabitosDTO();
		dto.setIdPessoa(this.pessoaEdicaoId.toString());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_HABITOS, dto);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final HabitosDTOResponse resp = 
						utils.fromJson(HabitosDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					final HabitosDTO dto = resp.getHabitosDTO();
					
					if (dto != null){
						
						CadastroPessoaScreen.this.dieta.setSelected(utils.emptyOrString(dto.getDieta().getDescricao()));
						CadastroPessoaScreen.this.atividadeFisica.setText(utils.emptyOrString(dto.getPraticaAtivFisica()));
						CadastroPessoaScreen.this.ultimoExame.setText(utils.emptyOrString(dto.getDataUltimoExameMedico()));
						
						if (dto.getPeriodExameMedico() != null){
							
							final Integer qtdDias = Integer.valueOf(dto.getPeriodExameMedico());
							
							if (qtdDias % Periodicidade.MESES.getPeso() == 0){
								
								CadastroPessoaScreen.this.qtdTempoPeriodoExame.setText(String.valueOf(qtdDias / Periodicidade.MESES.getPeso()));
								CadastroPessoaScreen.this.periodoExame.setSelected(Periodicidade.MESES.getDescricao());
							} else if (qtdDias % Periodicidade.SEMANAS.getPeso() == 0) {
								
								CadastroPessoaScreen.this.qtdTempoPeriodoExame.setText(String.valueOf(qtdDias / Periodicidade.SEMANAS.getPeso()));
								CadastroPessoaScreen.this.periodoExame.setSelected(Periodicidade.SEMANAS.getDescricao());
							} else {
								
								CadastroPessoaScreen.this.qtdTempoPeriodoExame.setText(qtdDias.toString());
								CadastroPessoaScreen.this.periodoExame.setSelected(Periodicidade.DIAS.getDescricao());
							}
							
						} else {
							
							CadastroPessoaScreen.this.qtdTempoPeriodoExame.setText("");
							CadastroPessoaScreen.this.periodoExame.setSelected("");
						}
					}
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar carregar hábitos: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}

	private void salvarHabitos() {
		
		//cant happen no mater what, but...
		if (this.pessoaEdicaoId == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final HabitosDTO habitosDTO = new HabitosDTO();
		habitosDTO.setIdPessoa(this.pessoaEdicaoId.toString());
		habitosDTO.setDataUltimoExameMedico(this.ultimoExame.getText());
		habitosDTO.setDieta(Dieta.getEnumByValue(this.dieta.getSelected()));
		
		final Periodicidade periodicidade = Periodicidade.getEnumByValue(this.periodoExame.getSelected());
		if (periodicidade != null && !this.qtdTempoPeriodoExame.getText().isEmpty()){
			
			final int total = Integer.valueOf(this.qtdTempoPeriodoExame.getText()) * periodicidade.getPeso();
			
			habitosDTO.setPeriodExameMedico(String.valueOf(total));
		}
		
		habitosDTO.setPraticaAtivFisica(this.atividadeFisica.getText());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_HABITO_SALVAR, habitosDTO);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					utils.mostarAlerta(null, "Hábitos salvos com sucesso.", stage, skin);
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar salvar hábitos: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}

	class DataReferenteChangeListener extends ChangeListener{

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			
			CadastroPessoaScreen.this.carregarMedidas();
		}
	}
	
	@SuppressWarnings("deprecation")
	private Tab montarMedidas() {
		
		final Table conteudo = new Table(skin);
		
		Table inTable = new Table(skin);
		inTable.add("Data referente: ").left();
		
		dataReferenteMedida = new SelectBox<String>(skin);
		Date d = new Date(TimeUtils.millis());
		final String data = d.getDate() + "/" + (d.getMonth() + 1) + "/" + (d.getYear() + 1900);
		dataReferenteMedida.setItems(data);
		dataReferenteMedida.addListener(dataRefChangeListener);
		
		inTable.add(dataReferenteMedida).left();
		
		conteudo.add(inTable).left().row();
		
		conteudo.add("Medidas Antropométricas:").left().padTop(15).row();
		
		inTable = new Table(skin);
		inTable.add("Peso corporal:").left();
		inTable.add("Peso magro:").padLeft(10).left();
		inTable.add("Peso gordura:").padLeft(10).left().row();
		
		maPesoCorporal = new TextField("", skin);
		maPesoCorporal.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maPesoCorporal).left();
		
		maPesoMagro = new TextField("", skin);
		maPesoMagro.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maPesoMagro).padLeft(10).left();
		
		maPesoGordura = new TextField("", skin);
		maPesoGordura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maPesoGordura).padLeft(10).left().row();
		
		inTable.add("Altura:").left();
		inTable.add("P.G. %:").padLeft(10).left();
		inTable.add("I.M.C.:").padLeft(10).left().row();
		
		maAltura = new TextField("", skin);
		maAltura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maAltura).left();
		
		maPorcentagemPG = new TextField("", skin);
		maPorcentagemPG.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maPorcentagemPG).padLeft(10).left();
		
		maImc = new TextField("", skin);
		maImc.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maImc).padLeft(10).left().row();
		
		inTable.add("Cintura:").left();
		inTable.add("Quadril:").padLeft(10).left();
		inTable.add("P.M.R.C.:").padLeft(10).left().row();
		
		maCintura = new TextField("", skin);
		maCintura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maCintura).left();
		
		maQuadril = new TextField("", skin);
		maQuadril.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maQuadril).padLeft(10).left();
		
		maPmrc = new TextField("", skin);
		maPmrc.setTextFieldFilter(utils.currencyFilter);
		inTable.add(maPmrc).padLeft(10).left();
		
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
		
		mcAbdomen = new TextField("", skin);
		mcAbdomen.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcAbdomen).padLeft(10).left();
		
		mcCintura = new TextField("", skin);
		mcCintura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcCintura).padLeft(10).left();
		
		mcBiceps = new TextField("", skin);
		mcBiceps.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcBiceps).padLeft(10).left().row();
		
		inTable.add("Tríceps:").left();
		inTable.add("Coxa:").padLeft(10).left();
		inTable.add("Antebraço:").padLeft(10).left().row();
		
		mcTriceps = new TextField("", skin);
		mcTriceps.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcTriceps).left();
		
		mcCoxa = new TextField("", skin);
		mcCoxa.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcCoxa).padLeft(10).left();
		
		mcAntebraco = new TextField("", skin);
		mcAntebraco.setTextFieldFilter(utils.currencyFilter);
		inTable.add(mcAntebraco).padLeft(10).left();
		
		conteudo.add(inTable).left().row();
		
		conteudo.add("Dobras Cut�neas").padTop(15).left().row();
		
		inTable = new Table(skin);
		inTable.add("Bíceps:").left();
		inTable.add("Tríceps:").padLeft(10).left();
		inTable.add("Sub-axilar:").padLeft(10).left().row();
		
		dcBiceps = new TextField("", skin);
		dcBiceps.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcBiceps).left();
		
		dcTriceps = new TextField("", skin);
		dcTriceps.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcTriceps).padLeft(10).left();
		
		dcSubAxilar = new TextField("", skin);
		dcSubAxilar.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcSubAxilar).padLeft(10).left().row();
		
		inTable.add("Supra-ilíacas:").left();
		inTable.add("Subescapular:").padLeft(10).left();
		inTable.add("Toráxica:").padLeft(10).left().row();
		
		dcSupraIliacas = new TextField("", skin);
		dcSupraIliacas.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcSupraIliacas).left();
		
		dcSubEscapular = new TextField("", skin);
		dcSubEscapular.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcSubEscapular).padLeft(10).left();
		
		dcToraxica = new TextField("", skin);
		dcToraxica.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcToraxica).padLeft(10).left().row();
		
		inTable.add("Abdominal:").left();
		inTable.add("Coxa:").padLeft(10).left();
		inTable.add("Perna:").padLeft(10).left().row();
		
		dcAbdominal = new TextField("", skin);
		dcAbdominal.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcAbdominal).left();
		
		dcCoxa = new TextField("", skin);
		dcCoxa.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcCoxa).padLeft(10).left();
		
		dcPerna = new TextField("", skin);
		dcPerna.setTextFieldFilter(utils.currencyFilter);
		inTable.add(dcPerna).padLeft(10).left().row();
		
		conteudo.add(inTable).left().row();
		
		final TextButton botaoSalvarMedidas = new TextButton("Salvar", skin);
		botaoSalvarMedidas.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				CadastroPessoaScreen.this.salvarMedidas();
			}
		});
		
		conteudo.add(botaoSalvarMedidas).left();
		
		botaoTabMedidas = new TextButton("Medidas", skin, "toggle");
		botaoTabMedidas.setDisabled(true);
		botaoTabMedidas.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (botaoTabMedidas.isChecked()){
					
					CadastroPessoaScreen.this.carregarMedidas();
				}
			}
		});
		
		return new Tab(botaoTabMedidas, conteudo, skin, Align.topLeft);
	}

	private void carregarMedidas() {
		
		//can't happen
		if (this.pessoaEdicaoId == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final MedidaDTO dto = new MedidaDTO();
		dto.setIdPessoa(this.pessoaEdicaoId.toString());
		dto.setDataReferente(this.dataReferenteMedida.getSelected());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_MEDIDAS, dto);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final MedidaDTOResponse resp = 
						utils.fromJson(MedidaDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					final MedidaDTO dto = resp.getMedidaDTO();
					
					if (dto != null){
						
						CadastroPessoaScreen.this.maPesoCorporal.setText(utils.emptyOrString(dto.getMaPesoCorporal()));
						CadastroPessoaScreen.this.maAltura.setText(utils.emptyOrString(dto.getMaAltura()));
						CadastroPessoaScreen.this.maPesoMagro.setText(utils.emptyOrString(dto.getMaPesoMagro()));
						CadastroPessoaScreen.this.maPesoGordura.setText(utils.emptyOrString(dto.getMaPesoGordura()));
						CadastroPessoaScreen.this.maPorcentagemPG.setText(utils.emptyOrString(dto.getMaPorcentagemPG()));
						CadastroPessoaScreen.this.maImc.setText(utils.emptyOrString(dto.getMaImc()));
						CadastroPessoaScreen.this.maCintura.setText(utils.emptyOrString(dto.getMaCintura()));
						CadastroPessoaScreen.this.maQuadril.setText(utils.emptyOrString(dto.getMaQuadril()));
						CadastroPessoaScreen.this.maPmrc.setText(utils.emptyOrString(dto.getMaPmrc()));
						
						CadastroPessoaScreen.this.mcTorax.setText(utils.emptyOrString(dto.getMcTorax()));
						CadastroPessoaScreen.this.mcAbdomen.setText(utils.emptyOrString(dto.getMcAbdomen()));
						CadastroPessoaScreen.this.mcCintura.setText(utils.emptyOrString(dto.getMcCintura()));
						CadastroPessoaScreen.this.mcBiceps.setText(utils.emptyOrString(dto.getMcBiceps()));
						CadastroPessoaScreen.this.mcTriceps.setText(utils.emptyOrString(dto.getMcTriceps()));
						CadastroPessoaScreen.this.mcCoxa.setText(utils.emptyOrString(dto.getMcCoxa()));
						CadastroPessoaScreen.this.mcAntebraco.setText(utils.emptyOrString(dto.getMcAntebraco()));
						
						CadastroPessoaScreen.this.dcBiceps.setText(utils.emptyOrString(dto.getDcBiceps()));
						CadastroPessoaScreen.this.dcTriceps.setText(utils.emptyOrString(dto.getDcTriceps()));
						CadastroPessoaScreen.this.dcSubAxilar.setText(utils.emptyOrString(dto.getDcSubAxilar()));
						CadastroPessoaScreen.this.dcSupraIliacas.setText(utils.emptyOrString(dto.getDcSupraIliacas()));
						CadastroPessoaScreen.this.dcSubEscapular.setText(utils.emptyOrString(dto.getDcSubEscapular()));
						CadastroPessoaScreen.this.dcToraxica.setText(utils.emptyOrString(dto.getDcToraxica()));
						CadastroPessoaScreen.this.dcAbdominal.setText(utils.emptyOrString(dto.getDcAbdominal()));
						CadastroPessoaScreen.this.dcCoxa.setText(utils.emptyOrString(dto.getDcCoxa()));
						CadastroPessoaScreen.this.dcPerna.setText(utils.emptyOrString(dto.getDcPerna()));
					}
					
					final String ultimaSelecao = CadastroPessoaScreen.this.dataReferenteMedida.getSelected();
					
					CadastroPessoaScreen.this.dataReferenteMedida.removeListener(dataRefChangeListener);
					CadastroPessoaScreen.this.dataReferenteMedida.clearItems();
					
					final String[] es = new String[resp.getDatasRef().size];
					
					int index = 0;
					for (String e : resp.getDatasRef()){
						es[index] = e;
						index++;
					}
					
					CadastroPessoaScreen.this.dataReferenteMedida.setItems(es);
					CadastroPessoaScreen.this.dataReferenteMedida.setSelected(ultimaSelecao);
					CadastroPessoaScreen.this.dataReferenteMedida.addListener(dataRefChangeListener);
					
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
		if (this.pessoaEdicaoId == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final MedidaDTO medidaDTO = new MedidaDTO();
		medidaDTO.setIdPessoa(this.pessoaEdicaoId.toString());
		medidaDTO.setDataReferente(this.dataReferenteMedida.getSelected());
		
		medidaDTO.setMaPesoCorporal(this.maPesoCorporal.getText().replace(",", "."));
		medidaDTO.setMaAltura(this.maAltura.getText().replace(",", "."));
		medidaDTO.setMaPesoMagro(this.maPesoMagro.getText().replace(",", "."));
		medidaDTO.setMaPesoGordura(this.maPesoGordura.getText().replace(",", "."));
		medidaDTO.setMaPorcentagemPG(this.maPorcentagemPG.getText().replace(",", "."));
		medidaDTO.setMaImc(this.maImc.getText().replace(",", "."));
		medidaDTO.setMaCintura(this.maCintura.getText().replace(",", "."));
		medidaDTO.setMaQuadril(this.maQuadril.getText().replace(",", "."));
		medidaDTO.setMaPmrc(this.maPmrc.getText().replace(",", "."));
		
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
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					utils.mostarAlerta(
							null, 
							"Medidas da data " + CadastroPessoaScreen.this.dataReferenteMedida.getSelected() + "salvas com sucesso.", stage, skin);
					
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
}
