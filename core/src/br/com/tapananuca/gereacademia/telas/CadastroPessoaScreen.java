package br.com.tapananuca.gereacademia.telas;

import java.util.Date;

import br.com.tapananuca.gereacademia.GereAcademia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

public class CadastroPessoaScreen extends Tela {

	protected final Skin skin;
	
	private Long pessoaEdicaoId = null;
	
	Label cabecalho;
	
	//botões tabbed pannel
	TextButton botaoTabObjetivos;
	TextButton botaoTabHistPat;
	TextButton botaoTabHabitos;
	TextButton botaoTabMedidas;
	TextButton botaoPersonal;
	
	public CadastroPessoaScreen(GereAcademia applicationListener) {
		super(applicationListener);
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		final Table table = new Table();
		//table.debugAll();
		table.setFillParent(true);
		table.top();
		stage.addActor(table);
		table.setSkin(skin);
		
		cabecalho = new Label("Novo cliente", skin);
		
		table.add(cabecalho).colspan(2).row();
		
		final TabbedPanel tabbedPanel = new TabbedPanel(skin, 650f, 700f);
		table.add(tabbedPanel).row();
		
		tabbedPanel.addTab(this.montarTabDadosCadastrais());
		tabbedPanel.addTab(this.montarObjetivos());
		tabbedPanel.addTab(this.montarHistoriaPatologica());
		tabbedPanel.addTab(this.montarHabitos());
		tabbedPanel.addTab(this.montarMedidas());
		tabbedPanel.addTab(this.montarPersoanal());
		
		//tabbedPanel.debugAll();
		
		final TextButton botaoVoltar = new TextButton("Voltar", skin);
		botaoVoltar.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				CadastroPessoaScreen.this.pessoaEdicaoId = null;
				final GereAcademia gereAcademia = CadastroPessoaScreen.this.applicationListener;
				MenuPrincipalScreen menuPrincipalScreen = new MenuPrincipalScreen(gereAcademia);
				gereAcademia.setTelaAtual(menuPrincipalScreen);
				
				final Date d = new Date(TimeUtils.millis());
				@SuppressWarnings("deprecation")
				final String dataRef = (d.getMonth() + 1) + "/" + (d.getYear() + 1900);
				
				menuPrincipalScreen.carregarTabelasMenuPrincipal(1, dataRef);
			}
		});
		
		table.add(botaoVoltar).left().row();
	}
	
	private Tab montarTabDadosCadastrais(){
		
		final TextButton botao = new TextButton("Dados básicos", skin, "toggle");
		return new DadosBasicosTab(botao, this, Align.topLeft);
	}

	private Tab montarObjetivos(){
		
		botaoTabObjetivos = new TextButton("Objetivos", skin, "toggle");
		botaoTabObjetivos.setDisabled(true);
		
		return new ObjetivosTab(botaoTabObjetivos, this, Align.center);
	}

	private Tab montarHistoriaPatologica() {
		
		botaoTabHistPat = new TextButton("História patológica", skin, "toggle");
		botaoTabHistPat.setDisabled(true);
		
		return new HistoriaPatologicaTab(botaoTabHistPat, this, Align.topLeft);
	}

	private Tab montarHabitos() {
		
		botaoTabHabitos = new TextButton("Hábitos", skin, "toggle");
		botaoTabHabitos.setDisabled(true);
		
		return new HabitosTab(botaoTabHabitos, this, Align.center);
	}

	private Tab montarMedidas() {
		
		botaoTabMedidas = new TextButton("Medidas", skin, "toggle");
		botaoTabMedidas.setDisabled(true);
		
		return new MedidasTab(botaoTabMedidas, this, Align.topLeft);
	}

	private Tab montarPersoanal() {
		
		botaoPersonal = new TextButton("Personal", skin, "toggle");
		botaoPersonal.setDisabled(true);
		
		return new PersonalTab(botaoPersonal, this, Align.top);
	}
	
	public Long getPessoaEdicaoId() {
		return pessoaEdicaoId;
	}

	public void setPessoaEdicaoId(Long pessoaEdicaoId) {
		this.pessoaEdicaoId = pessoaEdicaoId;
	}
}
