package br.com.tapananuca.gereacademia.telas;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Tela implements Screen {

	protected ApplicationListener applicationListener;
	protected Stage stage;
	
	public Tela(ApplicationListener applicationListener){
		this.applicationListener = applicationListener;
		this.stage = new Stage();
	}
	
	@Override
	public void render(float delta) {
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
