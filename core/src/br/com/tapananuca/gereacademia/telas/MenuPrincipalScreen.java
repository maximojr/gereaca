package br.com.tapananuca.gereacademia.telas;


import java.util.ArrayList;
import java.util.List;

import br.com.tapananuca.gereacademia.GereAcademia;
import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.AReceberDTO;
import br.com.tapananuca.gereacademia.comunicacao.AReceberPaginaDTO;
import br.com.tapananuca.gereacademia.comunicacao.Baixa;
import br.com.tapananuca.gereacademia.comunicacao.DadosBaixa;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.LoginDTO;
import br.com.tapananuca.gereacademia.comunicacao.MesUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuPrincipalScreen extends Tela {

	private final Pixmap pm1;
	private final Drawable cinza;
	private final Skin skin;
	private final Table tablePrincipal;
	private final Table tablePagamentos;
	private final Table tableAniversarios;
	private final Utils utils;
	
	private final SelectBox<String> datasRefPagamento;
	private final SelectBox<String> datasRefAniversario;
	
	private List<DataHolder> dataHolderBaixa;
	
	private final ChangeListener dataRefPgto, dataRefAnin;
	
	public MenuPrincipalScreen(GereAcademia applicationListener) {
		super(applicationListener);
		
		pm1 = new Pixmap(1, 1, Format.RGB565);
		pm1.setColor(Color.GRAY);
		pm1.fill();
		cinza = new TextureRegionDrawable(new TextureRegion(new Texture(pm1)));
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		dataHolderBaixa = new ArrayList<MenuPrincipalScreen.DataHolder>();
		
		utils = Utils.getInstance();
		
		datasRefPagamento = new SelectBox<String>(skin);
		dataRefPgto = new DataRefPagamentoChangeListener();
		datasRefPagamento.addListener(dataRefPgto);
		
		datasRefAniversario = new SelectBox<String>(skin);
		
		final String[] es = new String[MesUtil.values().length];
		
		int index = 0;
		for (MesUtil e : MesUtil.values()){
			es[index] = e.getNome();
			index++;
		}
		
		datasRefAniversario.setItems(es);
		dataRefAnin = new DataRefAniversarioChangeListener();
		datasRefAniversario.addListener(dataRefAnin);
		
		tablePrincipal = new Table();
		//tablePrincipal.debugAll();
		stage.addActor(tablePrincipal);
		tablePrincipal.setSkin(skin);
		tablePrincipal.setFillParent(true);
		tablePrincipal.top();
		tablePrincipal.padTop(10);
		
		final Table tableBotoes = new Table(skin);
		//tableBotoes.debugAll();
		final TextButton cadastrarPessoaButton = new TextButton("Cadastrar Pessoa", skin);
		cadastrarPessoaButton.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				final GereAcademia gereAcademia = MenuPrincipalScreen.this.applicationListener;
				final CadastroPessoaScreen cadastroPessoaScreen = new CadastroPessoaScreen(gereAcademia);
				gereAcademia.setTelaAtual(cadastroPessoaScreen);
			}
		});
		
		tableBotoes.add(cadastrarPessoaButton);
		
		final TextButton trocarSenha = new TextButton("Trocar senha", skin);
		trocarSenha.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				final Window janela = new Window("Trocar senha: ", skin);
				janela.setModal(true);
				
				final Table tableMudarSenha = new Table(skin);
				
				tableMudarSenha.add("Senha atual: ").right().padBottom(20);
				
				final TextField campoAtual = new TextField("", skin);
				campoAtual.setPasswordMode(true);
				campoAtual.setPasswordCharacter('*');
				tableMudarSenha.add(campoAtual).width(200).left().padBottom(20).row();
				
				tableMudarSenha.add("Nova senha: ").right().padBottom(20);
				
				final TextField campoNova = new TextField("", skin);
				campoNova.setPasswordMode(true);
				campoNova.setPasswordCharacter('*');
				tableMudarSenha.add(campoNova).width(200).left().padBottom(20).row();
				
				final Table tableBtns = new Table(skin);
				final TextButton botaoOk = new TextButton("Ok", skin);
				botaoOk.addListener(new ChangeListener() {
					
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						
						final LoginDTO loginDTO = new LoginDTO();
						loginDTO.setSenha(campoAtual.getText());
						loginDTO.setNovaSenha(campoNova.getText());
						
						final HttpRequest httpRequest = utils.criarRequest(Utils.URL_TROCA_SENHA, loginDTO);
						
						utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
							
							@Override
							public void handleHttpResponse(HttpResponse httpResponse) {
								
								final GAResponse ga = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
								
								if (ga.isSucesso()){
									
									janela.remove();
									utils.mostarAlerta(null, "Senha trocada com sucesso", stage, skin);
									
								} else {
									
									utils.mostarAlerta("Atenção", ga.getMsg(), stage, skin);
								}
							}
							
							@Override
							public void failed(Throwable t) {
								
								utils.mostarAlerta("Erro ao tentar troca de senha", t.getLocalizedMessage(), stage, skin);
							}
							
							@Override
							public void cancelled() {
								
								utils.mostarAlerta(null, "Requisição de troca de senha cancelada", stage, skin);
							}
						});
					}
				});
				
				tableBtns.add(botaoOk);
				
				final TextButton botaoCancelar = new TextButton("Cancelar", skin);
				botaoCancelar.addListener(new ChangeListener() {
					
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						janela.remove();
					}
				});
				
				tableBtns.add(botaoCancelar);
				
				tableMudarSenha.add(tableBtns).colspan(2);
				
				janela.add(tableMudarSenha).width(400).height(200);
				
				janela.pack();
				
				janela.setPosition(
						(int)((Gdx.graphics.getWidth() / 2) - (janela.getWidth() / 2)) ,
						(int)((Gdx.graphics.getHeight() / 2) - (janela.getHeight() / 2)));
				
				MenuPrincipalScreen.this.stage.addActor(janela);
			}
		});
		
		tableBotoes.add(trocarSenha);
		
		final TextButton logOut = new TextButton("Sair", skin);
		logOut.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				final HttpRequest httpRequest = utils.criarRequest(Utils.URL_LOGOUT, null);
				
				utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
					
					@Override
					public void handleHttpResponse(HttpResponse httpResponse) {
						
						final GAResponse ga = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
						
						if (ga != null && ga.isSucesso()){
							
							Gdx.app.postRunnable(new Runnable() {
								
								@Override
								public void run() {
							
									utils.setSessionId(null);
									final GereAcademia ger = MenuPrincipalScreen.this.applicationListener;
									final LoginScreen loginScreen = new LoginScreen(ger);
									ger.setTelaAtual(loginScreen);
								}
							});
							
						} else {
							
							utils.mostarAlerta("Erro", "Erro ao tentar logout O.o", stage, skin);
						}
					}
					
					@Override
					public void failed(Throwable t) {
						
						utils.mostarAlerta("Erro ao tentar logout O.o", t.getLocalizedMessage(), stage, skin);
					}
					
					@Override
					public void cancelled() {
						
						utils.mostarAlerta("Eeeepa", "requisição de logout cancelada O.o", stage, skin);
					}
				});
			}
		});
		
		tableBotoes.add(logOut).padLeft(5);
		tableBotoes.align(Align.left);
		tablePrincipal.add(tableBotoes).fill().row().padTop(50);
		
		tablePagamentos = new Table(skin);
		
		tablePrincipal.add(tablePagamentos);
		
		tablePrincipal.row().padTop(30);
		
		tableAniversarios = new Table(skin);
		
		tablePrincipal.add(tableAniversarios).colspan(3).fill();
	}
	
	class DataHolder{
		
		private DataHolder(){}
		
		public String id;
		public TextField valor;
		public CheckBox chec;
	}
	
	class DataRefPagamentoChangeListener extends ChangeListener{

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			
			MenuPrincipalScreen.this.carregarPagamentosAReceber(1, datasRefPagamento.getSelected());
		}
	}
	
	class DataRefAniversarioChangeListener extends ChangeListener{

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			
			MenuPrincipalScreen.this.carregarAniversariantes(1, 
					MesUtil.getEnumByValue(datasRefAniversario.getSelected()));
		}
	}
	
	private TextField createCurrencyField(String valor, Skin skin){
		
		final TextField campoValor = new TextField(utils.formatCurrency(valor), skin);
		campoValor.setRightAligned(true);
		campoValor.setTextFieldFilter(Utils.getInstance().currencyFilter);
		
		return campoValor;
	}

	public void carregarTabelasMenuPrincipal(int pagina, String dataRef) {
		
		this.carregarPagamentosAReceber(pagina, dataRef);
		
		this.datasRefAniversario.setSelected(MesUtil.getEnumByCodigo(Integer.valueOf(dataRef.split("/")[0])).getNome());
	}
	
	private void carregarPagamentosAReceber(final int pagina, final String dataRef){
		
		tablePagamentos.clear();
		dataHolderBaixa.clear();
		
		final Table inTable = new Table(skin);
		
		inTable.add("A receber: ");
		inTable.add(datasRefPagamento);
		
		tablePagamentos.add(inTable).colspan(3).row();
		
		final AReceberDTO dto = new AReceberDTO();
		dto.setPaginaAtual(String.valueOf(pagina));
		dto.setDataRef(dataRef);
		
		final HttpRequest req = this.utils.criarRequest(Utils.URL_A_RECEBER, dto);
		
		utils.enviarRequest(req, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final AReceberPaginaDTO aReceberPaginaDTO = utils.fromJson(AReceberPaginaDTO.class, httpResponse.getResultAsString());
				
				if (aReceberPaginaDTO.isSucesso()){
					
					if (aReceberPaginaDTO.getaReceber() != null && aReceberPaginaDTO.getaReceber().size != 0){
						
						tablePagamentos.add("Nome").center().width(300);
						tablePagamentos.add("Valor");
						tablePagamentos.add("Pago").row();
						
						int index = 0;
						for (AReceberDTO dto : aReceberPaginaDTO.getaReceber()){
							
							final DataHolder dh = new DataHolder();
							
							dh.id = dto.getId();
							
							if (index %2 != 0){
								
								tablePagamentos.add(dto.getNome()).left().fill();
								
								final TextField campoValor = MenuPrincipalScreen.this.createCurrencyField(String.valueOf(dto.getValor()), skin);
								tablePagamentos.add(campoValor).width(130).height(23);
								
								dh.valor = campoValor;
								
								final CheckBox checkBox = new CheckBox("", skin);
								tablePagamentos.add(checkBox);
								
								dh.chec = checkBox;
							} else {
								
								Table inTable = new Table(skin);
								inTable.setBackground(cinza);
								
								inTable.add(dto.getNome()).left();
								tablePagamentos.add(inTable.left()).fill();
								
								inTable = new Table();
								inTable.setBackground(cinza);
								final TextField campoValor = MenuPrincipalScreen.this.createCurrencyField(String.valueOf(dto.getValor()), skin);
								inTable.add(campoValor).width(130).height(23);
								tablePagamentos.add(inTable).fill();
								
								dh.valor = campoValor;
								
								inTable = new Table();
								inTable.setBackground(cinza);
								
								final CheckBox checkBox = new CheckBox("", skin);
								
								inTable.add(checkBox);
								tablePagamentos.add(inTable).fill();
								
								dh.chec = checkBox;
							}
							
							tablePagamentos.row();
							
							MenuPrincipalScreen.this.dataHolderBaixa.add(dh);
							
							index++;
						}
						
						final Table paginacaoTable = new Table(skin);
						
						final TextButton paginaAnteriorAReceber = new TextButton("<<", skin);
						paginaAnteriorAReceber.addListener(new ChangeListener(){

							@Override
							public void changed(ChangeEvent event, Actor actor) {
								
								if (pagina > 1){
									
									MenuPrincipalScreen.this.carregarPagamentosAReceber(pagina - 1, datasRefPagamento.getSelected());
								}
							}
						});
						paginacaoTable.add(paginaAnteriorAReceber);
						
						paginacaoTable.add(" Página " + pagina + " de " + aReceberPaginaDTO.getQtdPaginas() + " ");
						
						final TextButton proximaPaginaAReceber = new TextButton(">>", skin);
						proximaPaginaAReceber.addListener(new ChangeListener(){

							@Override
							public void changed(ChangeEvent event, Actor actor) {
								
								if (pagina < Integer.valueOf(aReceberPaginaDTO.getQtdPaginas())){
									
									MenuPrincipalScreen.this.carregarPagamentosAReceber(pagina + 1, datasRefPagamento.getSelected());
								}
							}
						});
						paginacaoTable.add(proximaPaginaAReceber);
						
						tablePagamentos.add(paginacaoTable).left().padTop(10);
						
						final TextButton botaoPagar = new TextButton("Pagar", skin);
						botaoPagar.addListener(new ChangeListener() {
							
							@Override
							public void changed(ChangeEvent event, Actor actor) {
								
								MenuPrincipalScreen.this.efetuarBaixa();
							}
						});
						
						tablePagamentos.add(botaoPagar).colspan(2);
						
						tablePagamentos.row().padTop(50);
					} else {

						tablePagamentos.add("Todos os pagamentos do mês " + dataRef + " foram efetuados.");
					}
					
					tablePagamentos.validate();
					
					datasRefPagamento.removeListener(dataRefPgto);
					datasRefPagamento.clearItems();
					
					if (!aReceberPaginaDTO.getDatasRef().contains(dataRef, false)){
						
						aReceberPaginaDTO.getDatasRef().add(dataRef);
					}
					
					final String[] es = new String[aReceberPaginaDTO.getDatasRef().size];
					
					int index = 0;
					for (String e : aReceberPaginaDTO.getDatasRef()){
						es[index] = e;
						index++;
					}
					
					datasRefPagamento.setItems(es);
					datasRefPagamento.setSelected(dataRef);
					
					datasRefPagamento.addListener(dataRefPgto);
					
				} else {
					
					utils.mostarAlerta("Atenção", aReceberPaginaDTO.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Atenção", t.getMessage(), stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Requisição ao servidor cancelada.", stage, skin);
			}
		});
	}

	private void efetuarBaixa() {
		
		final DadosBaixa dadosBaixa = new DadosBaixa();
		
		Baixa baixa = null;
		for (DataHolder data : this.dataHolderBaixa){
			
			if (data.chec.isChecked()){
				
				baixa = new Baixa();
				baixa.setId(data.id);
				baixa.setValor(data.valor.getText().replace(",", "."));
				
				dadosBaixa.getBaixas().add(baixa);
			}
		}
		
		if (dadosBaixa.getBaixas().size == 0){
			return;
		}
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PAGAR, dadosBaixa);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse ga = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (ga != null && ga.isSucesso()){
					
					utils.mostarAlerta(null, "Baixa efetuada com sucesso.", stage, skin);
				} else {
					
					utils.mostarAlerta(null, "Erro ao efetuar baixa " + ga != null ? ga.getMsg() : "", stage, skin);
				}
				
				MenuPrincipalScreen.this.carregarPagamentosAReceber(1, datasRefPagamento.getSelected());
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta(null, "Erro ao efetuar baixa: " + t.getLocalizedMessage(), stage, skin);
				MenuPrincipalScreen.this.carregarPagamentosAReceber(1, datasRefPagamento.getSelected());
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Requisição cancelada", stage, skin);
				MenuPrincipalScreen.this.carregarPagamentosAReceber(1, datasRefPagamento.getSelected());
			}
		});
	}

	private void carregarAniversariantes(final int pagina, final MesUtil dataRef) {
		
		tableAniversarios.clear();
		
		Table inTable = new Table(skin);
		
		inTable.add("Aniversariantes: ");
		inTable.add(datasRefAniversario);
		
		tableAniversarios.add(inTable).colspan(3).row();
		
		final AReceberDTO dto = new AReceberDTO();
		dto.setPaginaAtual(String.valueOf(pagina));
		dto.setDataRef(String.valueOf(dataRef.getCodigo()));
		
		final HttpRequest req = this.utils.criarRequest(Utils.URL_PESSOA_ANIVERSARIOS, dto);
		
		utils.enviarRequest(req, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final AReceberPaginaDTO aReceberPaginaDTO = utils.fromJson(AReceberPaginaDTO.class, httpResponse.getResultAsString());
				
				if (aReceberPaginaDTO.isSucesso()){
					
					if (aReceberPaginaDTO.getaReceber() != null && aReceberPaginaDTO.getaReceber().size != 0){
						
						tableAniversarios.add("Nome").width(300);
						tableAniversarios.add("Dia").row().padTop(10);
						
						int index = 0;
						for (AReceberDTO dto : aReceberPaginaDTO.getaReceber()){
							
							if (index % 2 != 0){
								
								tableAniversarios.add(dto.getNome()).left();
								tableAniversarios.add(dto.getDataRef());
							} else {
								
								Table inTable = new Table(skin);
								inTable.setBackground(cinza);
								
								inTable.add(dto.getNome()).left();
								tableAniversarios.add(inTable.left()).fill();
								
								inTable = new Table(skin);
								inTable.setBackground(cinza);
								
								inTable.add(dto.getDataRef());
								tableAniversarios.add(inTable.center()).fill();
							}
							
							tableAniversarios.row();
							
							index++;
						}
						
						final Table paginacaoTableAniversarios = new Table(skin);
						
						final TextButton paginaAnteriorAniversarios = new TextButton("<<", skin);
						paginaAnteriorAniversarios.addListener(new ChangeListener(){
							
							@Override
							public void changed(ChangeEvent event, Actor actor) {
								
								if (pagina > 1){
									
									MenuPrincipalScreen.this.carregarAniversariantes(pagina - 1, 
											MesUtil.getEnumByValue(datasRefAniversario.getSelected()));
								}
							}
						});
						paginacaoTableAniversarios.add(paginaAnteriorAniversarios);
						
						paginacaoTableAniversarios.add("Página " + pagina + " de " + aReceberPaginaDTO.getQtdPaginas() + " ");
						
						final TextButton proximaPaginaAniversarios = new TextButton(">>", skin);
						proximaPaginaAniversarios.addListener(new ChangeListener(){
							
							@Override
							public void changed(ChangeEvent event, Actor actor) {
								
								if (pagina < Integer.valueOf(aReceberPaginaDTO.getQtdPaginas())){
									
									MenuPrincipalScreen.this.carregarAniversariantes(pagina + 1, 
											MesUtil.getEnumByValue(datasRefAniversario.getSelected()));
								}
							}
						});
						paginacaoTableAniversarios.add(proximaPaginaAniversarios);
						
						tableAniversarios.add(paginacaoTableAniversarios).left().padTop(10);
						
					} else {
						
						tableAniversarios.add("Nenhum aniversariante no mês de " + datasRefAniversario.getSelected());
					}
					
					tableAniversarios.validate();
				} else {
					
					utils.mostarAlerta("Atenção", aReceberPaginaDTO.getMsg(), stage, skin);
				}
			}

			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Atenção", t.getMessage(), stage, skin);
			}

			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Requisição ao servidor cancelada.", stage, skin);
			}
		});
	}

}
