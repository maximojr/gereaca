package br.com.tapananuca.gereacademia.telas;

import java.util.ArrayList;
import java.util.List;

import br.com.tapananuca.gereacademia.GereAcademia;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Tela implements Screen, InputProcessor {

	protected GereAcademia applicationListener;
	protected Stage stage;
	protected List<Actor> elementosFocaveis = new ArrayList<Actor>();
	
	private boolean shiftDown = false;
	
	public Tela(GereAcademia applicationListener){
		this.applicationListener = applicationListener;
		this.stage = new Stage();
		
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
