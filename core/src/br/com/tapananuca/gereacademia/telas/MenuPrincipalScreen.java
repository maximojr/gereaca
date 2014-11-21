package br.com.tapananuca.gereacademia.telas;


import br.com.tapananuca.gereacademia.GereAcademia;
import br.com.tapananuca.gereacademia.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuPrincipalScreen extends Tela {

	public MenuPrincipalScreen(GereAcademia applicationListener) {
		super(applicationListener);
		
		final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		final Table table = new Table();
		//table.debugAll();
		stage.addActor(table);
		table.setSkin(skin);
		table.setFillParent(true);
		table.top();
		
		final TextButton cadastrarPessoaButton = new TextButton("Cadastrar Pessoa", skin);
		cadastrarPessoaButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				final GereAcademia gereAcademia = MenuPrincipalScreen.this.applicationListener;
				final CadastroPessoaScreen cadastroPessoaScreen = new CadastroPessoaScreen(gereAcademia);
				gereAcademia.setTelaAtual(cadastroPessoaScreen);
			}
			
		});
		cadastrarPessoaButton.setSize(1000, 1000);
		table.add(cadastrarPessoaButton).colspan(3).row().padTop(50);
		
		table.add(new Label("A receber", skin)).colspan(3).row();
		
		Label nome = new Label("Nome", skin);
		nome.setAlignment(Align.center);
		
		table.add(nome).width(300);
		table.add(new Label("Valor", skin));
		table.add(new Label("Pago", skin));
		
		table.row().padTop(10);
		
		final Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
		pm1.setColor(Color.GRAY);
		pm1.fill();
		final Drawable cinza = new TextureRegionDrawable(new TextureRegion(new Texture(pm1)));
		
		for (int index = 0 ; index < 10 ; index++){
			
			if (index %2==0){
				
				final Label pessoa = new Label("Pessoa " + index, skin);
				table.add(pessoa).left().fill();
				
				final TextField campoValor = this.createCurrencyField(String.valueOf(10 * index), skin);
				table.add(campoValor).width(130).height(23);
				
				table.add(new CheckBox("", skin));
			} else {
				
				Table inTable = new Table();
				inTable.setBackground(cinza);
				
				inTable.add(new Label("Pessoa " + index, skin)).left();
				table.add(inTable.left()).fill();
				
				inTable = new Table();
				inTable.setBackground(cinza);
				final TextField campoValor = this.createCurrencyField(String.valueOf(10 * index), skin);
				inTable.add(campoValor).width(130).height(23);
				table.add(inTable).fill();
				
				inTable = new Table();
				inTable.setBackground(cinza);
				inTable.add(new CheckBox("", skin)).fill();
				table.add(inTable).fill();
			}
			
			table.row();
		}
		
		TextButton paginaAnteriorAReceber = new TextButton("<<", skin);
		paginaAnteriorAReceber.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
			}
		});
		
		TextButton proximaPaginaAReceber = new TextButton(">>", skin);
		proximaPaginaAReceber.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
			}
		});
		
		final Table paginacaoTable = new Table();
		paginacaoTable.add(paginaAnteriorAReceber,proximaPaginaAReceber);
		
		table.add(paginacaoTable).left().padTop(10);
		table.add(new TextButton("Salvar", skin)).colspan(2);
		
		table.row().padTop(50);
		
		final Table wrapAniversarios = new Table();
		
		table.add(wrapAniversarios).colspan(3).fill();
		
		wrapAniversarios.add(new Label("Aniversariantes", skin)).colspan(3).row();
		final Label nomeAnim = new Label("Nome", skin);
		nomeAnim.setAlignment(Align.center);
		wrapAniversarios.add(nomeAnim).width(300);
		wrapAniversarios.add(new Label("Dia", skin));
		
		wrapAniversarios.row().padTop(10);
		
		for (int index = 0 ; index < 10 ; index++){
			
			if (index % 2 == 0){
				
				wrapAniversarios.add(new Label("Pessoa " + index, skin)).left();
				wrapAniversarios.add(new Label("99/99/9999", skin));
			} else {
				
				Table inTable = new Table();
				inTable.setBackground(cinza);
				
				inTable.add(new Label("Pessoa " + index, skin)).left();
				wrapAniversarios.add(inTable.left()).fill();
				
				inTable = new Table();
				inTable.setBackground(cinza);
				
				inTable.add(new Label("99/99/9999", skin));
				wrapAniversarios.add(inTable.center()).fill();
			}
			
			wrapAniversarios.row();
		}
		
		TextButton paginaAnteriorAniversarios = new TextButton("<<", skin);
		paginaAnteriorAniversarios.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
			}
		});
		
		TextButton proximaPaginaAniversarios = new TextButton(">>", skin);
		proximaPaginaAniversarios.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
			}
		});
		
		final Table paginacaoTableAniversarios = new Table();
		paginacaoTableAniversarios.add(paginaAnteriorAniversarios,proximaPaginaAniversarios);
		
		wrapAniversarios.add(paginacaoTableAniversarios).left().padTop(10);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	private TextField createCurrencyField(String valor, Skin skin){
		
		final TextField campoValor = new TextField(valor, skin);
		campoValor.setRightAligned(true);
		campoValor.setTextFieldFilter(Utils.getInstance().currencyFilter);
		
		return campoValor;
	}

}
