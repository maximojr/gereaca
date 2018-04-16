package br.com.tapananuca.gereacademia.telas;

import java.util.Date;

import br.com.tapananuca.gereacademia.GereAcademia;
import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.LoginDTO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;


public class LoginScreen extends Tela {

	final Skin skin;
	
	final TextField campoUsuario;
	
	final TextField campoSenha;
	
	public LoginScreen(GereAcademia applicationListener) {
		
		super(applicationListener);
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		final Table table = new Table();
		//table.debugAll();
		stage.addActor(table);
		
		table.setSkin(skin);
		table.setFillParent(true);
		table.center();
		
		table.add("Usuário: ").right().padBottom(20);
		
		campoUsuario = new TextField("", skin);
		table.add(campoUsuario).width(200).left().padBottom(20).row();
		elementosFocaveis.add(campoUsuario);
		campoUsuario.addListener(new ClickListener(){
			public void clicked(InputEvent e, float x, float y) {
	            Gdx.input.setOnscreenKeyboardVisible(true);
	        }
		});
		
		table.add("Senha: ").right().padBottom(40);
		
		campoSenha = new TextField("", skin);
		campoSenha.setPasswordMode(true);
		campoSenha.setPasswordCharacter('*');
		table.add(campoSenha).width(200).left().padBottom(40).row();
		elementosFocaveis.add(campoSenha);
		
		campoSenha.addListener(new InputListener(){

			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				
				if (keycode == Keys.ENTER){
					
					LoginScreen.this.efetuarLogin();
				}
				
				return super.keyUp(event, keycode);
			}
		});
		
		final TextButton b = new TextButton("Login", skin);
		b.addListener(new ChangeListener(){
			
			public void changed(ChangeEvent event, Actor actor) {
				
				LoginScreen.this.efetuarLogin();
			}
		});
		
		table.add(b).colspan(2).padBottom(40).row();
		
		this.stage.setKeyboardFocus(campoUsuario);
	}

	private void efetuarLogin() {
		
		final Utils utilsInstance = Utils.getInstance();
		
		final LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsuario(campoUsuario.getText());
		loginDTO.setSenha(campoSenha.getText());
		
		final HttpRequest request = utilsInstance.criarRequest(Utils.URL_LOGIN, loginDTO);
		
		utilsInstance.enviarRequest(request, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				try {
					
					final GAResponse resp = utilsInstance.fromJson(new GAResponse(), httpResponse.getResultAsString());
					
					if (resp.isSucesso()){
						
						utilsInstance.setSessionId(resp.getSessionId());
						
						Gdx.app.postRunnable(new Runnable() {
							
							@Override
							public void run() {
								
								final GereAcademia gereAcademia = LoginScreen.this.applicationListener;
								final MenuPrincipalScreen menuPrincipalScreen = new MenuPrincipalScreen(gereAcademia);
								gereAcademia.setTelaAtual(menuPrincipalScreen);
								
								final Date d = new Date(TimeUtils.millis());
								@SuppressWarnings("deprecation")
								final String dataRef = (d.getMonth() + 1) + "/" + (d.getYear() + 1900);
								
								menuPrincipalScreen.carregarTabelasMenuPrincipal(1, dataRef);
							}
						});
						
					} else {
						
						utilsInstance.mostarAlerta("Atenção", resp.getMsg(), stage, skin);
					}
					
				} catch (Exception e) {
					
					utilsInstance.mostarAlerta("Erro", e.getMessage(), stage, skin);
					e.printStackTrace();
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utilsInstance.mostarAlerta("Erro", t.getMessage(), stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utilsInstance.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}
}
