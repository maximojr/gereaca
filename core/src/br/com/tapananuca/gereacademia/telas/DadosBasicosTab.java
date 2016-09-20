package br.com.tapananuca.gereacademia.telas;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.EstadoCivil;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTOResponse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class DadosBasicosTab extends Tab {

	private Stage stage;
	private Utils utils;
	private CadastroPessoaScreen cadastroPessoaScreen;
	
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
	private TextField lembrete;
	private TextField valorMensal;
	private CheckBox ativo;
	
	private CheckBox checkMusculacao;
	private CheckBox checkFuncional;
	private CheckBox checkDanca;
	
	public DadosBasicosTab(final Button button, CadastroPessoaScreen cadastroPessoaScreen, int alinhamento){
		
		button.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (button.isChecked()){
					
					DadosBasicosTab.this.stage.setKeyboardFocus(nome);
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
		
		conteudo.add(new Label("Nome:", skin)).left().colspan(2).row();
		
		final Table inTableNome = new Table();
		
		nome = new TextField("", skin);
		inTableNome.add(nome).colspan(2).width(450).left();
		cadastroPessoaScreen.elementosFocaveis.add(nome);
		
		nome.addListener(new InputListener(){
			
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				
				if (keycode == Keys.ENTER){
					
					DadosBasicosTab.this.abrirDialogPesquisaPessoa();
				}
				
				return super.keyUp(event, keycode);
			}
		});
		
		final TextButton pesquisar = new TextButton("Pesquisar", skin);
		pesquisar.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				DadosBasicosTab.this.abrirDialogPesquisaPessoa();
			}
		});
		
		inTableNome.add(pesquisar);
		
		conteudo.add(inTableNome).left().colspan(2).row();
		
		final Table inTableDataNasc = new Table();
		inTableDataNasc.setSkin(skin);
		
		inTableDataNasc.add("Data nasc.:").left();
		inTableDataNasc.add("Estado civil:").padLeft(10).left();
		inTableDataNasc.add("Sexo").left().padLeft(10).row();
		
		dataNasc = new TextField("", skin);
		dataNasc.setTextFieldFilter(utils.dateFilter);
		inTableDataNasc.add(dataNasc).left();
		cadastroPessoaScreen.elementosFocaveis.add(dataNasc);
		
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
		listSexo.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				DadosBasicosTab.this.cadastroPessoaScreen.indSexo = listSexo.getSelected();
			}
		});
		inTableDataNasc.add(listSexo).padLeft(10).row();
		
		conteudo.add(inTableDataNasc).left().row();
		
		final Table inTableEndereco = new Table(skin);
		
		inTableEndereco.add("Endereço:").left();
		inTableEndereco.add("Número:").padLeft(10).left().row();
		
		endereco = new TextField("", skin);
		inTableEndereco.add(endereco).width(400).left();
		cadastroPessoaScreen.elementosFocaveis.add(endereco);
		
		numero = new TextField("", skin);
		numero.setTextFieldFilter(utils.numbersOnlyFilter);
		inTableEndereco.add(numero).padLeft(10).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(numero);
		
		conteudo.add(inTableEndereco).left().row();
		
		conteudo.add("Bairro:").left().row();
		
		bairro = new TextField("", skin);
		conteudo.add(bairro).width(300).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(bairro);
		
		final Table inTableTelefone = new Table();
		inTableTelefone.setSkin(skin);
		
		inTableTelefone.add("Telefone:").left();
		inTableTelefone.add("E-mail").padLeft(10).left();
		inTableTelefone.add("Data início:").padLeft(10).left().row();
		
		telefone = new TextField("", skin);
		telefone.setTextFieldFilter(utils.phoneFilter);
		inTableTelefone.add(telefone).left();
		cadastroPessoaScreen.elementosFocaveis.add(telefone);
		
		email = new TextField("", skin);
		inTableTelefone.add(email).padLeft(10).width(200).left();
		cadastroPessoaScreen.elementosFocaveis.add(email);
		
		dataInicio = new TextField("", skin);
		inTableTelefone.add(dataInicio).padLeft(10).left();
		dataInicio.setTextFieldFilter(utils.dateFilter);
		cadastroPessoaScreen.elementosFocaveis.add(dataInicio);
		
		conteudo.add(inTableTelefone).left().row();
		
		conteudo.add("Valor mensal:").left().row();
		
		valorMensal = new TextField("", skin);
		valorMensal.setTextFieldFilter(utils.currencyFilter);
		conteudo.add(valorMensal).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(valorMensal);
		
		conteudo.add("Lembrete:").left().row();
		
		lembrete = new TextField("", skin);
		conteudo.add(lembrete).width(400).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(lembrete);
		
		conteudo.add(new Label("Atividades:", skin)).left().row();
		
		final Table inTableAtv = new Table();
		
		checkMusculacao = new CheckBox("Musculação", skin);
		inTableAtv.add(checkMusculacao).left();
		
		checkFuncional = new CheckBox("Funcional", skin);
		inTableAtv.add(checkFuncional).padLeft(10).left();
		
		checkDanca = new CheckBox("Dança", skin);
		inTableAtv.add(checkDanca).padLeft(10).left();
		
		conteudo.add(inTableAtv).left().row();
		
		ativo = new CheckBox("Ativo", skin);
		ativo.setChecked(true);
		conteudo.add(ativo).left().padTop(20).row();
		
		final TextButton botaoSalvar = new TextButton("Salvar", skin);
		botaoSalvar.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				DadosBasicosTab.this.salvarDadosCadastrais();
			}
		});
		
		final Table tbBtn = new Table(skin);
		
		tbBtn.add(botaoSalvar).left();
		
		final TextButton botaoNovo = new TextButton("Novo", skin);
		botaoNovo.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				DadosBasicosTab.this.cadastroPessoaScreen.setPessoaEdicaoId(null);
				DadosBasicosTab.this.nome.setText("");
				DadosBasicosTab.this.dataNasc.setText("");
				DadosBasicosTab.this.listEstadoCivil.setSelectedIndex(0);
				DadosBasicosTab.this.listSexo.setSelectedIndex(0);
				DadosBasicosTab.this.endereco.setText("");
				DadosBasicosTab.this.numero.setText("");
				DadosBasicosTab.this.bairro.setText("");
				DadosBasicosTab.this.telefone.setText("");
				DadosBasicosTab.this.email.setText("");
				DadosBasicosTab.this.dataInicio.setText("");
				DadosBasicosTab.this.valorMensal.setText("");
				DadosBasicosTab.this.lembrete.setText("");
				DadosBasicosTab.this.ativo.setChecked(true);
				DadosBasicosTab.this.checkMusculacao.setChecked(false);
				DadosBasicosTab.this.checkFuncional.setChecked(false);
				DadosBasicosTab.this.checkDanca.setChecked(false);
				DadosBasicosTab.this.cadastroPessoaScreen.cabecalho.setText("Novo cliente");
				
				DadosBasicosTab.this.cadastroPessoaScreen.botaoTabObjetivos.setDisabled(true);
				DadosBasicosTab.this.cadastroPessoaScreen.botaoTabHistPat.setDisabled(true);
				DadosBasicosTab.this.cadastroPessoaScreen.botaoTabHabitos.setDisabled(true);
				DadosBasicosTab.this.cadastroPessoaScreen.botaoTabMedidas.setDisabled(true);
				DadosBasicosTab.this.cadastroPessoaScreen.botaoPersonal.setDisabled(true);
			}
		});
		
		tbBtn.add(botaoNovo).row();
		
		conteudo.add(tbBtn).left().padTop(20);
		
		this.inicializar();
	}

	private void abrirDialogPesquisaPessoa() {
		
		final Window window = new Window("Pesquisa por nome: ", skin);
		window.setModal(true);
		final Table table = new Table(skin);
		final ScrollPane scroll = new ScrollPane(table, skin);
		window.add(scroll).width(400).height(400);
		
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
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final PessoaDTOResponse resp = utils.fromJson(new PessoaDTOResponse(), httpResponse.getResultAsString());
				
				if (resp != null && resp.isSucesso()){
					
					if (resp.getPessoasDTO() != null && resp.getPessoasDTO().size() != 0){
						
						for (final PessoaDTO p : resp.getPessoasDTO()){
							
							final Label label = new Label(p.getNome(), skin);
							label.addListener(new ClickListener(){

								@Override
								public void clicked(InputEvent event, float x, float y) {
									
									window.remove();
									
									DadosBasicosTab.this.cadastroPessoaScreen.setPessoaEdicaoId(Long.valueOf(p.getId()));
									DadosBasicosTab.this.editar();
								}
							});
							
							table.add(label).row();
						}
					} else {
						
						table.add("Nenhuma pessoa encontrada.").row();
					}
					
					window.row();
					window.add(botaoCancelar);
					
					window.pack();
					
					window.setPosition(
							(int)((Gdx.graphics.getWidth() / 2) - (window.getWidth() / 2)) ,
							(int)((Gdx.graphics.getHeight() / 2) - (window.getHeight() / 2)));
					
					stage.addActor(window);
				} else {
					
					utils.mostarAlerta("Atenção:", resp == null ? "null" : resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar pesquisar por nome: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}

	private void salvarDadosCadastrais() {
		
		final Long pessoaId = this.cadastroPessoaScreen.getPessoaEdicaoId();
		
		final PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setId(pessoaId == null ? null : pessoaId.toString());
		pessoaDTO.setNome(this.nome.getText());
		pessoaDTO.setDataNascimento(this.dataNasc.getText());
		pessoaDTO.setSexo(this.listSexo.getSelected().toString().charAt(0));
		pessoaDTO.setEndereco(this.endereco.getText());
		pessoaDTO.setNumero(this.numero.getText());
		pessoaDTO.setBairro(this.bairro.getText());
		pessoaDTO.setTelefone(this.telefone.getText());
		pessoaDTO.setEmail(this.email.getText());
		pessoaDTO.setValorMensal(this.valorMensal.getText().replace(",", "."));
		pessoaDTO.setLembrete(this.lembrete.getText());
		pessoaDTO.setEstadoCivil(EstadoCivil.getEnumByValue(this.listEstadoCivil.getSelected()));
		pessoaDTO.setDataInicio(this.dataInicio.getText());
		pessoaDTO.setAtivo(this.ativo.isChecked());
		pessoaDTO.setAtividades(this.montarValorAtividades());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_DADOS_BASICOS_SALVAR, pessoaDTO);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(new GAResponse(), httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					utils.mostarAlerta(null, "Dados salvos com sucesso.", stage, skin);
					
					DadosBasicosTab.this.cadastroPessoaScreen.botaoTabObjetivos.setDisabled(false);
					DadosBasicosTab.this.cadastroPessoaScreen.botaoTabHistPat.setDisabled(false);
					DadosBasicosTab.this.cadastroPessoaScreen.botaoTabHabitos.setDisabled(false);
					DadosBasicosTab.this.cadastroPessoaScreen.botaoTabMedidas.setDisabled(false);
					DadosBasicosTab.this.cadastroPessoaScreen.botaoPersonal.setDisabled(false);
					
					DadosBasicosTab.this.cadastroPessoaScreen.cabecalho.setText("Editar dados cliente");
					
					DadosBasicosTab.this.cadastroPessoaScreen.setPessoaEdicaoId(Long.valueOf(resp.getSessionId()));
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
	
	private String montarValorAtividades() {
		
		return (
				(this.checkMusculacao.isChecked() ? "1" : "0") + 
				(this.checkFuncional.isChecked() ? "1" : "0") +
				(this.checkDanca.isChecked() ? "1" : "0")
		);
	}

	private void editar() {
		
		this.checkMusculacao.setChecked(false);
		this.checkFuncional.setChecked(false);
		this.checkDanca.setChecked(false);
		
		final PessoaDTO dto = new PessoaDTO(DadosBasicosTab.this.cadastroPessoaScreen.getPessoaEdicaoId(), null);
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_DADOS_BASICOS, dto);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final PessoaDTOResponse resp = utils.fromJson(new PessoaDTOResponse(), httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					if (resp.getPessoasDTO() != null && resp.getPessoasDTO().size() != 0){
						
						final PessoaDTO pessoaDTO = resp.getPessoasDTO().get(0);
						
						DadosBasicosTab.this.nome.setText(pessoaDTO.getNome());
						DadosBasicosTab.this.dataNasc.setText(pessoaDTO.getDataNascimento());
						DadosBasicosTab.this.listSexo.setSelected(pessoaDTO.getSexo());
						DadosBasicosTab.this.endereco.setText(utils.emptyOrString(pessoaDTO.getEndereco()));
						DadosBasicosTab.this.numero.setText(utils.emptyOrString(pessoaDTO.getNumero()));
						DadosBasicosTab.this.bairro.setText(utils.emptyOrString(pessoaDTO.getBairro()));
						DadosBasicosTab.this.telefone.setText(utils.emptyOrString(pessoaDTO.getTelefone()));
						DadosBasicosTab.this.email.setText(utils.emptyOrString(pessoaDTO.getEmail()));
						DadosBasicosTab.this.valorMensal.setText(utils.formatCurrency(pessoaDTO.getValorMensal()));
						DadosBasicosTab.this.lembrete.setText(utils.emptyOrString(pessoaDTO.getLembrete()));
						DadosBasicosTab.this.dataInicio.setText(pessoaDTO.getDataInicio());
						DadosBasicosTab.this.listEstadoCivil.setSelected(pessoaDTO.getEstadoCivil().getDescricao());
						DadosBasicosTab.this.ativo.setChecked(pessoaDTO.isAtivo());
						DadosBasicosTab.this.setarChecksAtividades(pessoaDTO.getAtividades());
						
						DadosBasicosTab.this.cadastroPessoaScreen.cabecalho.setText("Editar dados cliente");
						
						DadosBasicosTab.this.cadastroPessoaScreen.botaoTabObjetivos.setDisabled(false);
						DadosBasicosTab.this.cadastroPessoaScreen.botaoTabHistPat.setDisabled(false);
						DadosBasicosTab.this.cadastroPessoaScreen.botaoTabHabitos.setDisabled(false);
						DadosBasicosTab.this.cadastroPessoaScreen.botaoTabMedidas.setDisabled(false);
						DadosBasicosTab.this.cadastroPessoaScreen.botaoPersonal.setDisabled(false);
					}
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar carregar dados: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}

	protected void setarChecksAtividades(String atvs) {
		
		if (atvs != null && !atvs.isEmpty()){
			
			atvs = atvs.replaceAll("_", "");
			
			this.checkMusculacao.setChecked(atvs.charAt(0) == '1');
			
			if (atvs.length() > 1){
				this.checkFuncional.setChecked(atvs.charAt(1) == '1');
			}
			
			if (atvs.length() > 2){
				this.checkDanca.setChecked(atvs.charAt(2) == '1');
			}
		}
	}

	public void setFocoInicial() {
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run() {
				DadosBasicosTab.this.stage.setKeyboardFocus(nome);
			}
		});
	}
}
