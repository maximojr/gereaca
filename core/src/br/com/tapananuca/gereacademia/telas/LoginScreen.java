package br.com.tapananuca.gereacademia.telas;

import br.com.tapananuca.gereacademia.GereAcademia;
import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.LoginDTO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class LoginScreen extends Tela {

	public LoginScreen(GereAcademia applicationListener) {
		
		super(applicationListener);
		
		final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		final Table table = new Table();
		//table.debugAll();
		stage.addActor(table);
		
		table.setSkin(skin);
		table.setFillParent(true);
		table.center();
		
		Label label = new Label("Usuário: ", skin);
		table.add(label).right().padBottom(20);
		
		final TextField campoUsuario = new TextField("", skin);
		table.add(campoUsuario).left().padBottom(20).row();
		
		label = new Label("Senha: ", skin);
		table.add(label).right().padBottom(40);
		
		final TextField campoSenha = new TextField("", skin);
		campoSenha.setPasswordMode(true);
		table.add(campoSenha).left().padBottom(40).row();
		
		final Label rodape = new Label("Insira usuário e senha para entrar", skin);
		
		final TextButton b = new TextButton("Login", skin);
		b.setWidth(300);
		b.addListener(new ClickListener(){
			
			public void clicked(InputEvent event, float x, float y) {
				
				final Utils utilsInstance = Utils.getInstance();
				
				final LoginDTO loginDTO = new LoginDTO();
				loginDTO.setUsuario(campoUsuario.getText());
				loginDTO.setSenha(utilsInstance.criptografar(campoSenha.getText()));
				
				final HttpRequest request = utilsInstance.criarRequest("login", loginDTO);
				
				Gdx.net.sendHttpRequest(request, new HttpResponseListener() {
					
					@Override
					public void handleHttpResponse(HttpResponse httpResponse) {
						
						try {
							
							final GAResponse resp = utilsInstance.fromJson(GAResponse.class, httpResponse.getResultAsString());
							
							if (resp.isSucesso()){
								
								utilsInstance.setSessionId(resp.getSessionId());
								rodape.setText("Logado");
								
								Gdx.app.postRunnable(new Runnable() {
									
									@Override
									public void run() {
										
										final GereAcademia gereAcademia = LoginScreen.this.applicationListener;
										final MenuPrincipalScreen menuPrincipalScreen = new MenuPrincipalScreen(gereAcademia);
										gereAcademia.setTelaAtual(menuPrincipalScreen);
									}
								});
								
							} else {
								
								rodape.setText(resp.getMsg());
							}
							
						} catch (Exception e) {
							
							rodape.setText("Erro ao tentar login: " + e.getMessage());
							e.printStackTrace();
						}
					}
					
					@Override
					public void failed(Throwable t) {
						
						rodape.setText("fail: " + t.getMessage());
					}
					
					@Override
					public void cancelled() {
						
						rodape.setText("foi cancelado");
					}
				});
			}
		});
		
		table.add(b).colspan(2).padBottom(40).row();
		
		table.add(rodape).colspan(2);
		
		
		Gdx.input.setInputProcessor(stage);
	}
}
