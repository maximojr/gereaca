package br.com.tapananuca.gereacademia.telas;

import br.com.tapananuca.gereacademia.GereAcademia;
import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CadastroPessoaScreen extends Tela {

	private final Utils utils;
	
	private final Skin skin;
	
	private Long pessoaEdicaoId = null;
	
	private final TextField nome;
	private final TextField dataNasc;
	private final TextField peso;
	private final SelectBox<Character> listSexo;
	private final TextField endereco;
	private final TextField numero;
	private final TextField bairro;
	private final TextField telefone;
	private final TextField email;
	private final TextField valorMensal;
	
	public CadastroPessoaScreen(GereAcademia applicationListener) {
		super(applicationListener);
		
		utils = Utils.getInstance();
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		final Table table = new Table();
		//table.debugAll();
		stage.addActor(table);
		table.setSkin(skin);
		table.setFillParent(true);
		table.top();
		
		table.add("Novo cliente").colspan(2).row();
		
		table.add(new Label("Nome:", skin)).left().colspan(2).row();
		
		final Table inTableNome = new Table();
		
		nome = new TextField("", skin);
		inTableNome.add(nome).colspan(2).width(400).left();
		
		final TextButton pesquisar = new TextButton("Pesquisar", skin);
		pesquisar.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
			}
		});
		
		inTableNome.add(pesquisar);
		
		table.add(inTableNome).left().colspan(2).row();
		
		final Table inTableDataNasc = new Table();
		inTableDataNasc.setSkin(skin);
		
		inTableDataNasc.add("Data nasc.:").left();
		inTableDataNasc.add("Peso:").left();
		inTableDataNasc.add("Sexo").left().row();
		
		dataNasc = new TextField("", skin);
		dataNasc.setTextFieldFilter(utils.dateFilter);
		inTableDataNasc.add(dataNasc).left();
		
		peso = new TextField("", skin);
		peso.setTextFieldFilter(utils.currencyFilter);
		inTableDataNasc.add(peso);
		
		listSexo = new SelectBox<Character>(skin);
		listSexo.setItems('M', 'F');
		
		inTableDataNasc.add(listSexo).row();
		
		table.add(inTableDataNasc).left().row();
		
		table.add("Endereço:").left();
		table.add("Número:").left().row();
		
		endereco = new TextField("", skin);
		table.add(endereco).width(400).left();
		
		numero = new TextField("", skin);
		numero.setTextFieldFilter(utils.numbersOnlyFilter);
		table.add(numero).left().row();
		
		table.add("Bairro:").left().row();
		
		bairro = new TextField("", skin);
		table.add(bairro).width(300).left().row();
		
		final Table inTableTelefone = new Table();
		inTableTelefone.setSkin(skin);
		
		inTableTelefone.add("Telefone:").left();
		inTableTelefone.add("E-mail").left().row();
		
		telefone = new TextField("", skin);
		telefone.setTextFieldFilter(utils.phoneFilter);
		inTableTelefone.add(telefone).left();
		
		email = new TextField("", skin);
		inTableTelefone.add(email).width(200).left();
		
		table.add(inTableTelefone).left().row();
		
		table.add("Valor Mensal:").left().row();
		
		valorMensal = new TextField("", skin);
		valorMensal.setTextFieldFilter(utils.currencyFilter);
		table.add(valorMensal).left().row().padTop(30);
		
		final TextButton botaoSalvar = new TextButton("Salvar", skin);
		botaoSalvar.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				CadastroPessoaScreen.this.salvar();
			}
		});
		
		table.add(botaoSalvar).left().row().padTop(20);
		
		final TextButton botaoVoltar = new TextButton("Voltar", skin);
		botaoVoltar.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				CadastroPessoaScreen.this.pessoaEdicaoId = null;
				final GereAcademia gereAcademia = CadastroPessoaScreen.this.applicationListener;
				MenuPrincipalScreen menuPrincipalScreen = new MenuPrincipalScreen(gereAcademia);
				gereAcademia.setTelaAtual(menuPrincipalScreen);
			}
		});
		
		table.add(botaoVoltar).left().row();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	public void editar(PessoaDTO pessoaDTO){
		
		this.pessoaEdicaoId = pessoaDTO.getId() == null ? null : Long.valueOf(pessoaDTO.getId());
		this.nome.setText(pessoaDTO.getNome());
		this.dataNasc.setText(pessoaDTO.getDataNascimento());
		this.peso.setText(pessoaDTO.getPeso() == null ? "" : pessoaDTO.getPeso().toString());
		this.listSexo.setSelected(pessoaDTO.getSexo());
		this.endereco.setText(pessoaDTO.getEndereco());
		this.numero.setText(pessoaDTO.getNumero() == null ? "" : pessoaDTO.getNumero().toString());
		this.bairro.setText(pessoaDTO.getBairro());
		this.telefone.setText(pessoaDTO.getTelefone());
		this.email.setText(pessoaDTO.getEmail());
		this.valorMensal.setText(pessoaDTO.getValorMensal().toString());
	}
	
	private void salvar(){
		
		final PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setId(this.pessoaEdicaoId);
		pessoaDTO.setNome(this.nome.getText());
		pessoaDTO.setDataNascimento(this.dataNasc.getText());
		pessoaDTO.setPeso(this.peso.getText().isEmpty() ? null : Float.valueOf(this.peso.getText().replace(",", ".")));
		pessoaDTO.setSexo(this.listSexo.getSelected().toString().charAt(0));
		pessoaDTO.setEndereco(this.endereco.getText());
		pessoaDTO.setNumero(this.numero.getText().isEmpty() ? null : Integer.valueOf(this.numero.getText()));
		pessoaDTO.setBairro(this.bairro.getText());
		pessoaDTO.setTelefone(this.telefone.getText());
		pessoaDTO.setEmail(this.email.getText());
		pessoaDTO.setValorMensal(Float.valueOf(this.valorMensal.getText().replace(",", ".")));
		
		final HttpRequest httpRequest = utils.criarRequest("salvarPessoa", pessoaDTO);
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					utils.mostarAlerta(null, "Dados salvos com sucesso.", stage, skin);
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
}
