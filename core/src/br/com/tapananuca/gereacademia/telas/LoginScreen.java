package br.com.tapananuca.gereacademia.telas;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import br.com.tapananuca.gereacademia.comunicacao.GAResponse;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;


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
				
				HttpRequest h = new HttpRequest(HttpMethods.POST);
				h.setUrl("http://gereacademia.herokuapp.com/login");
				
				StringBuffer sb = new StringBuffer();
				
				try {
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.update(campoSenha.getText().getBytes());
					
					byte byteData[] = md.digest();
					 
					for (int i = 0; i < byteData.length; i++) {
						sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
					}
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put("user", campoUsuario.getText());
				parameters.put("password", sb.toString());
				
				h.setContent(HttpParametersUtils.convertHttpParameters(parameters));
				
				Gdx.net.sendHttpRequest(h, new HttpResponseListener() {
					
					@Override
					public void handleHttpResponse(HttpResponse httpResponse) {
						
						try {
							Json json = new Json();
							GAResponse resp = json.fromJson(GAResponse.class, httpResponse.getResultAsString());
							b.setText(resp.getToken() + ": " + resp.getMsg());
						} catch (Exception e) {
							
							b.setText("Erro ao tentar login: " + e.getMessage()	);
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
