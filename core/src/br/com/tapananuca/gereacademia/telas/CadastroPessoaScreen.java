package br.com.tapananuca.gereacademia.telas;

import java.util.Date;

import br.com.tapananuca.gereacademia.GereAcademia;
import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.Dieta;
import br.com.tapananuca.gereacademia.comunicacao.EstadoCivil;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTO;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTO;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTO;
import br.com.tapananuca.gereacademia.comunicacao.Periodicidade;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;

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
	private SelectBox<String> dataReferente;
	private TextField pesoCorporal;
	private TextField altura;
	private TextField pesoMagro;
	private TextField pesoGordura;
	private TextField porcentagemPG;
	private TextField imc;
	private TextField cintura;
	private TextField quadril;
	private TextField pmrc;
	
	private TextField torax;
	private TextField abdomen;
	//private TextField cintura;
	private TextField biceps;
	private TextField triceps;
	private TextField coxa;
	private TextField antebraco;
	
	private TextField dcBiceps;
	private TextField dcTriceps;
	private TextField dcSubAxilar;
	private TextField dcSupraIliacas;
	private TextField dcSubEscapular;
	private TextField dcToraxica;
	private TextField dcAbdominal;
	private TextField dcCoxa;
	private TextField dcPerna;
	
	//botões tabbed pannel
	private TextButton botaoTabObjetivos;
	private TextButton botaoTabHistPat;
	private TextButton botaoTabHabitos;
	private TextButton botaoTabMedidas;
	
	public CadastroPessoaScreen(GereAcademia applicationListener) {
		super(applicationListener);
		
		utils = Utils.getInstance();
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
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
	
	public void editar(PessoaDTO pessoaDTO){
		
		this.pessoaEdicaoId = pessoaDTO.getId() == null ? null : Long.valueOf(pessoaDTO.getId());
		this.nome.setText(pessoaDTO.getNome());
		this.dataNasc.setText(pessoaDTO.getDataNascimento());
		this.listSexo.setSelected(pessoaDTO.getSexo());
		this.endereco.setText(pessoaDTO.getEndereco());
		this.numero.setText(pessoaDTO.getNumero() == null ? "" : pessoaDTO.getNumero().toString());
		this.bairro.setText(pessoaDTO.getBairro());
		this.telefone.setText(pessoaDTO.getTelefone());
		this.email.setText(pessoaDTO.getEmail());
		this.valorMensal.setText(pessoaDTO.getValorMensal().toString());
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
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated method stub
				
				if (botaoTabObjetivos.isChecked()){
					System.out.println("wow");
				}
			}
		});
		
		return new Tab(botaoTabObjetivos, conteudo, skin, Align.center);
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
				// TODO Auto-generated method stub
				
			}
		});
		
		return new Tab(botaoTabHistPat, conteudo, skin, Align.topLeft);
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
				// TODO Auto-generated method stub
				
			}
		});
		
		return new Tab(botaoTabHabitos, conteudo, skin, Align.center);
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

	private Tab montarMedidas() {
		
		final Table conteudo = new Table(skin);
		
		Table inTable = new Table(skin);
		inTable.add("Data referente: ").left();
		
		dataReferente = new SelectBox<String>(skin);
//		Date d = new Date(TimeUtils.millis());
//		final String data = d.getDate() + "/" + (d.getMonth() + 1) + "/" + (d.getYear() + 1900);
//		dataReferente.setItems(data);
		dataReferente.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				// TODO Auto-generated method stub
			}
		});
		
		inTable.add(dataReferente).left();
		
		conteudo.add(inTable).left().row();
		
		conteudo.add("Medidas Antropométricas:").left().padTop(15).row();
		
		inTable = new Table(skin);
		inTable.add("Peso corporal:").left();
		inTable.add("Peso magro:").padLeft(10).left();
		inTable.add("Peso gordura:").padLeft(10).left().row();
		
		pesoCorporal = new TextField("", skin);
		pesoCorporal.setTextFieldFilter(utils.currencyFilter);
		inTable.add(pesoCorporal).left();
		
		pesoMagro = new TextField("", skin);
		pesoMagro.setTextFieldFilter(utils.currencyFilter);
		inTable.add(pesoMagro).padLeft(10).left();
		
		pesoGordura = new TextField("", skin);
		pesoGordura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(pesoGordura).padLeft(10).left().row();
		
		inTable.add("Altura:").left();
		inTable.add("P.G. %:").padLeft(10).left();
		inTable.add("I.M.C.:").padLeft(10).left().row();
		
		altura = new TextField("", skin);
		altura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(altura).left();
		
		porcentagemPG = new TextField("", skin);
		porcentagemPG.setTextFieldFilter(utils.currencyFilter);
		inTable.add(porcentagemPG).padLeft(10).left();
		
		imc = new TextField("", skin);
		imc.setTextFieldFilter(utils.currencyFilter);
		inTable.add(imc).padLeft(10).left().row();
		
		inTable.add("Cintura:").left();
		inTable.add("Quadril:").padLeft(10).left();
		inTable.add("P.M.R.C.:").padLeft(10).left().row();
		
		cintura = new TextField("", skin);
		cintura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(cintura).left();
		
		quadril = new TextField("", skin);
		quadril.setTextFieldFilter(utils.currencyFilter);
		inTable.add(quadril).padLeft(10).left();
		
		pmrc = new TextField("", skin);
		pmrc.setTextFieldFilter(utils.currencyFilter);
		inTable.add(pmrc).padLeft(10).left();
		
		conteudo.add(inTable).left().row();
		
		conteudo.add("Medidas Circunferênciais:").padTop(15).left().row();
		
		inTable = new Table(skin);
		inTable.add("Tórax:").left();
		inTable.add("Abdômen:").padLeft(10).left();
		inTable.add("Cintura:").padLeft(10).left();
		inTable.add("Bíceps:").padLeft(10).left().row();
		
		torax = new TextField("", skin);
		torax.setTextFieldFilter(utils.currencyFilter);
		inTable.add(torax).left();
		
		abdomen = new TextField("", skin);
		abdomen.setTextFieldFilter(utils.currencyFilter);
		inTable.add(abdomen).padLeft(10).left();
		
		cintura = new TextField("", skin);
		cintura.setTextFieldFilter(utils.currencyFilter);
		inTable.add(cintura).padLeft(10).left();
		
		biceps = new TextField("", skin);
		biceps.setTextFieldFilter(utils.currencyFilter);
		inTable.add(biceps).padLeft(10).left().row();
		
		inTable.add("Tríceps:").left();
		inTable.add("Coxa:").padLeft(10).left();
		inTable.add("Antebraço:").padLeft(10).left().row();
		
		triceps = new TextField("", skin);
		triceps.setTextFieldFilter(utils.currencyFilter);
		inTable.add(triceps).left();
		
		coxa = new TextField("", skin);
		coxa.setTextFieldFilter(utils.currencyFilter);
		inTable.add(coxa).padLeft(10).left();
		
		antebraco = new TextField("", skin);
		antebraco.setTextFieldFilter(utils.currencyFilter);
		inTable.add(antebraco).padLeft(10).left();
		
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
				// TODO Auto-generated method stub
				
			}
		});
		
		return new Tab(botaoTabMedidas, conteudo, skin, Align.topLeft);
	}

	private void salvarMedidas() {
		// TODO Auto-generated method stub
		
	}
}
