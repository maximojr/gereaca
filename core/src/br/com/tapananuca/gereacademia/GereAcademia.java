package br.com.tapananuca.gereacademia;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GereAcademia extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Stage stage;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));	
		
		final TextButton b = new TextButton("teste chamada server", skin);
		
		b.addListener(new ClickListener(){
			
			public void clicked(InputEvent event, float x, float y) {
				
				HttpRequest h = new HttpRequest(HttpMethods.POST);
				h.setUrl("https://gereacademia.herokuapp.com/teste");
				
				Gdx.net.sendHttpRequest(h, new HttpResponseListener() {
					
					@Override
					public void handleHttpResponse(HttpResponse httpResponse) {
						b.setText(httpResponse.getResultAsString());
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
		
		stage.addActor(b);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}
	
	@Override
	public void dispose(){
		stage.dispose();
		img.dispose();
		batch.dispose();
	}
}
