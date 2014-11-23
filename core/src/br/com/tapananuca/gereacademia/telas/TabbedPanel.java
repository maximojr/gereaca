package br.com.tapananuca.gereacademia.telas;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TabbedPanel extends Table {

	private Table tabelaBotoes, tabelaConteudos;
	
	private ButtonGroup grupoBotoes;
	
	private List<Tab> tabs;
	
	private Float width, height;
	
	public TabbedPanel(Skin skin, Float width, Float height){
		super(skin);
		
		this.tabs = new ArrayList<Tab>();
		
		this.tabelaBotoes = new Table(skin);
		this.tabelaConteudos = new Table(skin);
		this.grupoBotoes = new ButtonGroup();
		this.grupoBotoes.setMaxCheckCount(1);
		this.grupoBotoes.setUncheckLast(true);
		
		this.add(tabelaBotoes).left().row();
		this.add(tabelaConteudos);
		
		this.width = width;
		this.height = height;
	}
	
	public void addTab(Tab tab){
		
		this.tabs.add(tab);
		this.tabelaBotoes.add(tab.getBotao());
		this.grupoBotoes.add(tab.getBotao());
		
		tab.setTabbedPanel(this);
		
		if (this.tabs.size() == 1){
			
			this.botaoClick(tab);
		}
	}
	
	public void botaoClick(Tab clickedTab){
		
		this.tabelaConteudos.clearChildren();
		this.tabelaConteudos.add(clickedTab.getScrollPane()).width(this.width).height(this.height);
	}
}
