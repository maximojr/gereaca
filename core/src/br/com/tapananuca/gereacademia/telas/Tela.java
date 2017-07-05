package br.com.tapananuca.gereacademia.telas;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import br.com.tapananuca.gereacademia.GereAcademia;

public abstract class Tela implements Screen, InputProcessor {

	protected GereAcademia applicationListener;
	protected Stage stage;
	protected List<Actor> elementosFocaveis = new ArrayList<Actor>();
	
	public static int width = 530;
	public static int height = 1000;
	
	private boolean shiftDown = false;
	
	public Tela(GereAcademia applicationListener){
		this.applicationListener = applicationListener;
		
		if (Gdx.app.getType().equals(Application.ApplicationType.Android)){
			this.stage = new Stage(new FitViewport(width, height));
		} else {
			width = Gdx.graphics.getWidth();
			height = Gdx.graphics.getHeight();
			this.stage = new Stage(new ScreenViewport());
		}
		
		final InputMultiplexer in = new InputMultiplexer();
		in.addProcessor(this);
		in.addProcessor(stage);
		
		Gdx.input.setInputProcessor(in);
	}
	
	@Override
	public void render(float delta) {
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		
		if (ApplicationType.WebGL == Gdx.app.getType() && 
				(keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT)){
			
			this.shiftDown = true;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if (ApplicationType.WebGL == Gdx.app.getType()){
			
			if (keycode == Input.Keys.TAB){
				
				final Actor focused = this.stage.getKeyboardFocus();
				
				for (int index = 0 ; index < this.elementosFocaveis.size() ; index++){
					
					if (this.elementosFocaveis.get(index) == focused){
						
						if (this.shiftDown){
							
							if (index == 0){
								
								this.stage.setKeyboardFocus(this.elementosFocaveis.get(this.elementosFocaveis.size() - 1));
							} else {
								
								this.stage.setKeyboardFocus(this.elementosFocaveis.get(index - 1));
							}
						} else {
							
							if (index == this.elementosFocaveis.size() - 1){
								
								this.stage.setKeyboardFocus(this.elementosFocaveis.get(0));
							} else {
								
								this.stage.setKeyboardFocus(this.elementosFocaveis.get(index + 1));
							}
						}
						
						break;
					}
				}
				
			} else if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT){
				
				this.shiftDown = false;
			}
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		
		return false;
	}
	
	public Stage getStage(){
		
		return stage;
	}
}
