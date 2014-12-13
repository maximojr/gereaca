package br.com.tapananuca.gereacademia.telas;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.Dieta;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTO;
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.Periodicidade;

import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class HabitosTab extends Tab {

	private Stage stage;
	private Utils utils;
	private CadastroPessoaScreen cadastroPessoaScreen;
	
	private SelectBox<String> dieta;
	
	private TextField atividadeFisica;
	private TextField ultimoExame;
	
	private TextField qtdTempoPeriodoExame;
	private SelectBox<String> periodoExame;
	
	public HabitosTab(final Button button, CadastroPessoaScreen cadastroPessoaScreen, int alinhamento){
		
		button.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if (button.isChecked()){
					
					HabitosTab.this.carregarHabitos();
				}
			}
		});
		
		this.setBotao(button);
		this.skin = cadastroPessoaScreen.skin;
		this.stage = cadastroPessoaScreen.stage;
		this.utils = Utils.getInstance();
		this.cadastroPessoaScreen = cadastroPessoaScreen;
		
		this.alinhamento = alinhamento;
		
		this.conteudo = new Table(skin);
		
		conteudo.add("Dieta:").left().row();
		dieta = new SelectBox<String>(skin);
		dieta.setItems("Não faz dieta", "Para perder peso", "Para ganhar peso");
		conteudo.add(dieta).left().row();
		
		conteudo.add("Pratica atividade física durante quanto tempo?").left().row();
		atividadeFisica = new TextField("Não pratica", skin);
		conteudo.add(atividadeFisica).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(atividadeFisica);
		
		conteudo.add("Data último exame médico:").left().row();
		ultimoExame = new TextField("", skin);
		ultimoExame.setTextFieldFilter(utils.dateFilter);
		conteudo.add(ultimoExame).left().row();
		cadastroPessoaScreen.elementosFocaveis.add(ultimoExame);
		
		conteudo.add("Periodicidade exame médico:").left().row();
		
		final Table inTable = new Table(skin);
		
		inTable.add("A cada: ").left();
		
		qtdTempoPeriodoExame = new TextField("", skin);
		qtdTempoPeriodoExame.setTextFieldFilter(utils.numbersOnlyFilter);
		inTable.add(qtdTempoPeriodoExame).left();
		cadastroPessoaScreen.elementosFocaveis.add(qtdTempoPeriodoExame);
		
		periodoExame = new SelectBox<String>(skin);
		
		final String[] es = new String[Periodicidade.values().length + 1];
		
		es[0] = "";
		int index = 1;
		for (Periodicidade e : Periodicidade.values()){
			es[index] = e.getDescricao();
			index++;
		}
		
		periodoExame.setItems(es);
		inTable.add(periodoExame).left();
		
		conteudo.add(inTable).left().row();
		
		final TextButton botaoSalvarHabitos = new TextButton("Salvar", skin);
		botaoSalvarHabitos.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				HabitosTab.this.salvarHabitos();
			}
		});
		
		conteudo.add(botaoSalvarHabitos).left();
		
		this.inicializar();
	}

	protected void carregarHabitos() {
		//can't happen
		if (this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final HabitosDTO dto = new HabitosDTO();
		dto.setIdPessoa(this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_HABITOS, dto);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				System.out.println("");
				final HabitosDTOResponse resp = 
						utils.fromJson(HabitosDTOResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					final HabitosDTO dto = resp.getHabitosDTO();
					
					if (dto != null){
						
						if (dto.getDieta() != null){
							
							dieta.setSelected(dto.getDieta().getDescricao());
						}
						
						if (dto.getPraticaAtivFisica() == null || dto.getPraticaAtivFisica().isEmpty()){
							
							atividadeFisica.setText("Não pratica");
						} else {
							
							atividadeFisica.setText(dto.getPraticaAtivFisica());
						}
						
						ultimoExame.setText(utils.emptyOrString(dto.getDataUltimoExameMedico()));
						
						if (dto.getPeriodExameMedico() != null){
							
							final Integer qtdDias = Integer.valueOf(dto.getPeriodExameMedico());
							
							if (qtdDias % Periodicidade.MESES.getPeso() == 0){
								
								qtdTempoPeriodoExame.setText(String.valueOf(qtdDias / Periodicidade.MESES.getPeso()));
								periodoExame.setSelected(Periodicidade.MESES.getDescricao());
							} else if (qtdDias % Periodicidade.SEMANAS.getPeso() == 0) {
								
								qtdTempoPeriodoExame.setText(String.valueOf(qtdDias / Periodicidade.SEMANAS.getPeso()));
								periodoExame.setSelected(Periodicidade.SEMANAS.getDescricao());
							} else {
								
								qtdTempoPeriodoExame.setText(qtdDias.toString());
								periodoExame.setSelected(Periodicidade.DIAS.getDescricao());
							}
							
						} else {
							
							qtdTempoPeriodoExame.setText("");
							periodoExame.setSelected("");
						}
					}
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar carregar hábitos: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}

	private void salvarHabitos() {
		
		//cant happen no mater what, but...
		if (this.cadastroPessoaScreen.getPessoaEdicaoId() == null){
			
			utils.mostarAlerta("Atenção", "Dados insuficientes, salve ou pesquise um cliente cadastrado.", stage, skin);
			
			return;
		}
		
		final HabitosDTO habitosDTO = new HabitosDTO();
		habitosDTO.setIdPessoa(this.cadastroPessoaScreen.getPessoaEdicaoId().toString());
		habitosDTO.setDataUltimoExameMedico(this.ultimoExame.getText());
		habitosDTO.setDieta(Dieta.getEnumByValue(this.dieta.getSelected()));
		
		final Periodicidade periodicidade = Periodicidade.getEnumByValue(this.periodoExame.getSelected());
		if (periodicidade != null && !this.qtdTempoPeriodoExame.getText().isEmpty()){
			
			final int total = Integer.valueOf(this.qtdTempoPeriodoExame.getText()) * periodicidade.getPeso();
			
			habitosDTO.setPeriodExameMedico(String.valueOf(total));
		}
		
		habitosDTO.setPraticaAtivFisica(this.atividadeFisica.getText());
		
		final HttpRequest httpRequest = utils.criarRequest(Utils.URL_PESSOA_HABITO_SALVAR, habitosDTO);
		
		utils.enviarRequest(httpRequest, stage, skin, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				final GAResponse resp = utils.fromJson(GAResponse.class, httpResponse.getResultAsString());
				
				if (resp.isSucesso()){
					
					utils.mostarAlerta(null, "Hábitos salvos com sucesso.", stage, skin);
					
				} else {
					
					utils.mostarAlerta("Atenção:", resp.getMsg(), stage, skin);
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				utils.mostarAlerta("Erro:", "Erro ao tentar salvar hábitos: " + t.getMessage() , stage, skin);
			}
			
			@Override
			public void cancelled() {
				
				utils.mostarAlerta(null, "Solicitação ao servidor cancelada.", stage, skin);
			}
		});
	}
}
