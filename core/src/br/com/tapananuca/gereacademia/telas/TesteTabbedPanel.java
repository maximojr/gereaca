package br.com.tapananuca.gereacademia.telas;

import br.com.tapananuca.gereacademia.GereAcademia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class TesteTabbedPanel extends Tela {

	public TesteTabbedPanel(GereAcademia applicationListener) {
		super(applicationListener);
		
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json")); 
		
		TabbedPanel tabbedPanel = new TabbedPanel(skin, 100f, 100f);
		tabbedPanel.setFillParent(true);
		tabbedPanel.top();
		
		Table conteudoTab1 = new Table(skin);
		conteudoTab1.add("TEste tab 1");
		Tab tab = new Tab("tab 1", conteudoTab1, skin, Align.center);
		tabbedPanel.addTab(tab);
		
		Table conteudoTab2 = new Table(skin);
		conteudoTab2.add("TEste tab 2");
		Tab tab2 = new Tab("tab 2", conteudoTab2, skin, Align.topLeft);
		tabbedPanel.addTab(tab2);
		
		stage.addActor(tabbedPanel);
	}

}
