package br.com.tapananuca.gereacademia.telas;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.LoginDTO;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class LoginScreen extends Tela {

	public LoginScreen(ApplicationListener applicationListener) {
		
		super(applicationListener);
		
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		Table table = new Table();
		stage.addActor(table);
		
		table.setSkin(skin);
		table.setFillParent(true);
		table.center();
		
		final TextField campoUsuario = new TextField("Usuario", skin);
		table.add(campoUsuario).padBottom(60).row();
		
		final TextField campoSenha = new TextField("Senha", skin);
		table.add(campoSenha).padBottom(60).row();
		
		final TextButton b = new TextButton("teste chamada server", skin);
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
							
							final String sessionId = resp.getSessionId() == null ? "" : resp.getSessionId();
							
							utilsInstance.setSessionId(sessionId);
							
							b.setText(resp.getMsg() + " - " + sessionId);
						} catch (Exception e) {
							
							b.setText("Erro ao tentar login: " + e.getMessage());
						}
					}
					
					@Override
					public void failed(Throwable t) {
						b.setText("fail: " + t.getMessage());
					}
					
					@Override
					public void cancelled() {
						b.setText("foi cancelado");
					}
				});
			}
		});
		
		table.add(b).padBottom(60).row();
		
		Gdx.input.setInputProcessor(stage);
	}
}
