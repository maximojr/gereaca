package br.com.tapananuca.gereacademia;

import br.com.tapananuca.gereacademia.telas.LoginScreen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GereAcademia extends ApplicationAdapter {
	
	private LoginScreen loginScreen;
	
	@Override
	public void create () {
		
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
