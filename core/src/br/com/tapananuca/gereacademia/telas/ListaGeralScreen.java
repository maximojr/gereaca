package br.com.tapananuca.gereacademia.telas;

import java.util.Date;

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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

public class ListaGeralScreen extends Tela {

	private final Skin skin;
	
	private final Table conteudo, nomes;
	
	private final Container<Actor> container;
	
	private final Drawable cinza;
	
	public ListaGeralScreen(GereAcademia applicationListener) {
		super(applicationListener);
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		final Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
		pm1.setColor(Color.GRAY);
		pm1.fill();
		cinza = new TextureRegionDrawable(new TextureRegion(new Texture(pm1)));
		
		nomes = new Table(skin);
		container = new Container<Actor>();
		container.setActor(nomes);
		container.align(Align.top);
		
		conteudo = new Table(skin);
		
		conteudo.setFillParent(true);
		conteudo.top();
		stage.addActor(conteudo);
		
		conteudo.add("Pessoas cadastradas").center().row();
		
		conteudo.add(container).width(520).row();
		
		final TextButton botaoVoltar = new TextButton("Voltar", skin);
		botaoVoltar.addListener(new ChangeListener(){
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				final GereAcademia gereAcademia = ListaGeralScreen.this.applicationListener;
				MenuPrincipalScreen menuPrincipalScreen = new MenuPrincipalScreen(gereAcademia);
				gereAcademia.setTelaAtual(menuPrincipalScreen);
				
				final Date d = new Date(TimeUtils.millis());
				@SuppressWarnings("deprecation")
				final String dataRef = (d.getMonth() + 1) + "/" + (d.getYear() + 1900);
				
				menuPrincipalScreen.carregarTabelasMenuPrincipal(1, dataRef);
			}
		});
		
		conteudo.add(botaoVoltar).padTop(30).left().row();
		
		this.carregarLista(1);
	}
	
	private void carregarLista(final Integer pagina){
		
		final Utils utils = Utils.getInstance();
		
		final AReceberDTO dto = new AReceberDTO();
		dto.setPaginaAtual(String.valueOf(pagina));
		
		final HttpRequest request = utils.criarRequest(Utils.URL_CADASTRADOS, dto);
		
		utils.enviarRequest(request, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final AReceberPaginaDTO aReceberPaginaDTO = utils.fromJson(AReceberPaginaDTO.class, httpResponse.getResultAsString());
				
				if (aReceberPaginaDTO.isSucesso()){
					
					if (aReceberPaginaDTO.getaReceber() != null && aReceberPaginaDTO.getaReceber().size != 0){
						
						nomes.clearChildren();
						
						int index = 0;
						for (AReceberDTO dto : aReceberPaginaDTO.getaReceber()){
							
							if (index %2 != 0){
								
								nomes.add(dto.getNome()).left().width(520);
							} else {
								
								final Table inTable = new Table(skin);
								inTable.setBackground(cinza);
								inTable.add(dto.getNome()).left();
								nomes.add(inTable.left()).width(520).fill();
							}
							
							nomes.row();
							
							index++;
						}
					
						final Table inTablePaginacao = new Table(skin);
						
						TextButton btnPaginacao = new TextButton("<<", skin);
						btnPaginacao.addListener(new ChangeListener() {
							
							@Override
							public void changed(ChangeEvent event, Actor actor) {
								
								if (pagina > 1){
									
									ListaGeralScreen.this.carregarLista(pagina - 1);
								}
							}
						});
						
						inTablePaginacao.add(btnPaginacao);
						
						inTablePaginacao.add("Página " + pagina + " de " + aReceberPaginaDTO.getQtdPaginas());
						
						btnPaginacao = new TextButton(">>", skin);
						btnPaginacao.addListener(new ChangeListener() {
							
							@Override
							public void changed(ChangeEvent event, Actor actor) {
								
								if (pagina < Integer.valueOf(aReceberPaginaDTO.getQtdPaginas())){
									
									ListaGeralScreen.this.carregarLista(pagina + 1);
								}
							}
						});
						
						inTablePaginacao.add(btnPaginacao);
						
						nomes.add(inTablePaginacao).padTop(10).left();
					} else {
						
						nomes.add("Nenhuma pessoa cadastrada");
					}
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
