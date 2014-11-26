package br.com.tapananuca.gereacademia.telas;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.tapananuca.gereacademia.GereAcademia;
import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.AReceberDTO;
import br.com.tapananuca.gereacademia.comunicacao.AReceberPaginaDTO;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

public class MenuPrincipalScreen extends Tela {

	final Pixmap pm1;
	final Drawable cinza;
	final Skin skin;
	final Table tablePrincipal;
	final Utils utils;
	
	private List<DataHolder> dados;
	
	public MenuPrincipalScreen(GereAcademia applicationListener) {
		super(applicationListener);
		
		pm1 = new Pixmap(1, 1, Format.RGB565);
		pm1.setColor(Color.GRAY);
		pm1.fill();
		cinza = new TextureRegionDrawable(new TextureRegion(new Texture(pm1)));
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		dados = new ArrayList<MenuPrincipalScreen.DataHolder>();
		
		utils = Utils.getInstance();
		
		tablePrincipal = new Table();
		//table.debugAll();
		stage.addActor(tablePrincipal);
		tablePrincipal.setSkin(skin);
		tablePrincipal.setFillParent(true);
		tablePrincipal.top();
		
		final TextButton cadastrarPessoaButton = new TextButton("Cadastrar Pessoa", skin);
		cadastrarPessoaButton.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				final GereAcademia gereAcademia = MenuPrincipalScreen.this.applicationListener;
				final CadastroPessoaScreen cadastroPessoaScreen = new CadastroPessoaScreen(gereAcademia);
				gereAcademia.setTelaAtual(cadastroPessoaScreen);
			}
		});
		
		tablePrincipal.add(cadastrarPessoaButton).colspan(3).row().padTop(50);
		
		tablePrincipal.add("A receber").colspan(3).row();
		
		tablePrincipal.add("Nome").center().width(300);
		tablePrincipal.add("Valor");
		tablePrincipal.add("Pago");
		
		tablePrincipal.row().padTop(10);
		
		
		
		final Table wrapAniversarios = new Table();
		
		tablePrincipal.add(wrapAniversarios).colspan(3).fill();
		
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
	
	class DataHolder{
		
		private DataHolder(){}
		
		public Long id;
		public TextField valor;
		public CheckBox chec;
	}
	
	private TextField createCurrencyField(String valor, Skin skin){
		
		final TextField campoValor = new TextField(valor, skin);
		campoValor.setRightAligned(true);
		campoValor.setTextFieldFilter(Utils.getInstance().currencyFilter);
		
		return campoValor;
	}

	@SuppressWarnings("deprecation")
	public void carregarPagamentosAReceber(int pagina, String dataRef) {
		
		if (dataRef == null){
			
			Date d = new Date(TimeUtils.millis());
			dataRef = (d.getMonth() + 1) + "/" + (d.getYear() + 1900);
		}
		
		final HttpRequest req = this.utils.criarRequest(Utils.URL_PESSOA_A_RECEBER, null);
		
		Gdx.net.sendHttpRequest(req, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final AReceberPaginaDTO aReceberPaginaDTO = utils.fromJson(AReceberPaginaDTO.class, httpResponse.getResultAsString());
				
				if (aReceberPaginaDTO.isSucesso()){
					
					if (aReceberPaginaDTO.getaReceber() != null && !aReceberPaginaDTO.getaReceber().isEmpty()){
						
						int index = 0;
						for (AReceberDTO dto : aReceberPaginaDTO.getaReceber()){
							
							final DataHolder dh = new DataHolder();
							
							dh.id = dto.getId();
							
							if (index %2==0){
								
								tablePrincipal.add(dto.getNome()).left().fill();
								
								final TextField campoValor = MenuPrincipalScreen.this.createCurrencyField(String.valueOf(dto.getValor()), skin);
								tablePrincipal.add(campoValor).width(130).height(23);
								
								dh.valor = campoValor;
								
								final CheckBox checkBox = new CheckBox("", skin);
								tablePrincipal.add(checkBox);
								
								dh.chec = checkBox;
							} else {
								
								Table inTable = new Table();
								inTable.setBackground(cinza);
								
								inTable.add(dto.getNome()).left();
								tablePrincipal.add(inTable.left()).fill();
								
								inTable = new Table();
								inTable.setBackground(cinza);
								final TextField campoValor = MenuPrincipalScreen.this.createCurrencyField(String.valueOf(dto.getValor()), skin);
								inTable.add(campoValor).width(130).height(23);
								tablePrincipal.add(inTable).fill();
								
								dh.valor = campoValor;
								
								inTable = new Table();
								inTable.setBackground(cinza);
								
								final CheckBox checkBox = new CheckBox("", skin);
								
								inTable.add(checkBox).fill();
								tablePrincipal.add(inTable).fill();
								
								dh.chec = checkBox;
							}
							
							tablePrincipal.row();
							
							MenuPrincipalScreen.this.dados.add(dh);
							
							index++;
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
						
						tablePrincipal.add(paginacaoTable).left().padTop(10);
						tablePrincipal.add(new TextButton("Salvar", skin)).colspan(2);
						
						tablePrincipal.row().padTop(50);
					} else {

						//TODO tudo pago
					}
				} else {
					
					//TODO deu merda na req
				}
			}
			
			@Override
			public void failed(Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
