package br.com.tapananuca.gereacademia.telas;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Tab {

	private TextButton botao;
	
	private Table conteudo;
	
	private ScrollPane scrollPane;
	
	private TabbedPanel tabbedPanel;
	
	public Tab(String titulo, Table conteudo, Skin skin, int alinhamento){
		
		this.botao = new TextButton(titulo, skin, "toggle");
		this.botao.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				Tab.this.tabbedPanel.botaoClick(Tab.this);
			}
		});
		
		this.conteudo = conteudo;
		
		this.scrollPane = new ScrollPane(conteudo, skin);
		this.scrollPane.setPosition(0, 0);
		conteudo.align(alinhamento);
	}

	public Button getBotao() {
		return botao;
	}

	public void setBotao(TextButton botao) {
		this.botao = botao;
	}

	public Table getConteudo() {
		return conteudo;
	}

	public void setConteudo(Table conteudo) {
		this.conteudo = conteudo;
	}

	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public void setTabbedPanel(TabbedPanel tabbedPanel) {
		this.tabbedPanel = tabbedPanel;
	}
}
