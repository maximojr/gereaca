package br.com.tapananuca.gereacademia.telas;

import br.com.tapananuca.gereacademia.GereAcademia;
import br.com.tapananuca.gereacademia.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CadastroPessoaScreen extends Tela {

	public CadastroPessoaScreen(GereAcademia applicationListener) {
		super(applicationListener);
		
		final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		final Utils utils = Utils.getInstance();
		
		final Table table = new Table();
		//table.debugAll();
		stage.addActor(table);
		table.setSkin(skin);
		table.setFillParent(true);
		table.top();
		
		table.add("Novo cliente").colspan(2).row();
		
		table.add(new Label("Nome:", skin)).left().colspan(2).row();
		
		final Table inTableNome = new Table();
		
		final TextField nome = new TextField("", skin);
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
		inTableDataNasc.add("Peso:").left().row();
		
		final TextField dataNasc = new TextField("", skin);
		dataNasc.setTextFieldFilter(utils.dateFilter);
		inTableDataNasc.add(dataNasc).left();
		
		final TextField peso = new TextField("", skin);
		peso.setTextFieldFilter(utils.currencyFilter);
		inTableDataNasc.add(peso).row();
		
		table.add(inTableDataNasc).left().row();
		
		table.add("Endereço:").left();
		table.add("Número:").left().row();
		
		final TextField endereco = new TextField("", skin);
		table.add(endereco).width(400).left();
		
		final TextField numero = new TextField("", skin);
		numero.setTextFieldFilter(utils.numbersOnlyFilter);
		table.add(numero).left().row();
		
		table.add("Bairro:").left().row();
		
		final TextField bairro = new TextField("", skin);
		table.add(bairro).width(300).left().row();
		
		table.add("Telefone:").left().row();
		
		final TextField telefone = new TextField("", skin);
		telefone.setTextFieldFilter(utils.phoneFilter);
		table.add(telefone).left().row();
		
		table.add("Valor Mensal:").left().row();
		
		final TextField valorMensal = new TextField("", skin);
		valorMensal.setTextFieldFilter(utils.currencyFilter);
		table.add(valorMensal).left().row().padTop(30);
		
		final TextButton botaoSalvar = new TextButton("Salvar", skin);
		botaoSalvar.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		
		table.add(botaoSalvar).left().row().padTop(20);
		
		final TextButton botaoVoltar = new TextButton("Voltar", skin);
		botaoVoltar.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				final GereAcademia gereAcademia = CadastroPessoaScreen.this.applicationListener;
				MenuPrincipalScreen menuPrincipalScreen = new MenuPrincipalScreen(gereAcademia);
				gereAcademia.setTelaAtual(menuPrincipalScreen);
			}
		});
		
		table.add(botaoVoltar).left().row();
		
		Gdx.input.setInputProcessor(stage);
	}

}
