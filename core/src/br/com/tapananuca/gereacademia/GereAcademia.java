package br.com.tapananuca.gereacademia;

import java.io.IOException;

import br.com.tapananuca.gereacademia.telas.LoginScreen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.PropertiesUtils;

public class GereAcademia extends ApplicationAdapter {
	
	private LoginScreen loginScreen;
	
	@Override
	public void create () {
		
		try {
			
			ObjectMap<String, String> o = new ObjectMap<String, String>();
			PropertiesUtils.load(o, Gdx.files.internal("config.properties").reader());
			Utils.starInstance(o);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		loginScreen = new LoginScreen(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		loginScreen.render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose(){
		loginScreen.dispose();
	}
}
