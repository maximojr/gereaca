package br.com.tapananuca.gereacademia.telas;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Tab {

	protected Button botao;
	
	protected Table conteudo;
	
	private ScrollPane scrollPane;
	
	private TabbedPanel tabbedPanel;
	
	protected Skin skin;
	
	protected int alinhamento;
	
	public Tab(){}
	
	protected void inicializar() {
		
		this.botao.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (Tab.this.botao.isChecked()){
					Tab.this.tabbedPanel.botaoClick(Tab.this);
				}
			}
		});
		
		
		this.scrollPane = new ScrollPane(conteudo, skin);
		this.scrollPane.setPosition(0, 0);
		conteudo.align(alinhamento);
	}

	public Button getBotao() {
		return botao;
	}

	public void setBotao(Button botao) {
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
