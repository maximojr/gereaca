package br.com.tapananuca.gereacademia.dtoreport;

import java.util.List;

import br.com.tapananuca.gereacademia.model.Medida;

public class AvaliacaoFisicaDTO {

	private List<Medida> medidas;
	
	private Double densidadeCorporal;  
	
	private Double percentualGordura;
	
	private Double massaGorda;
	
	private Double massaMagra;
	
	private Double pesoMaximoRecomendavel;
	
	private Double objetivoEmagrecimento;
	
	private Double percentualGorduraAlvo;
	
	private Double pesoAtual;

	public List<Medida> getMedidas() {
		return medidas;
	}

	public void setMedidas(List<Medida> medidas) {
		this.medidas = medidas;
	}

	public Double getDensidadeCorporal() {
		return densidadeCorporal;
	}

	public void setDensidadeCorporal(Double densidadeCorporal) {
		this.densidadeCorporal = densidadeCorporal;
	}

	public Double getPercentualGordura() {
		return percentualGordura;
	}

	public void setPercentualGordura(Double percentualGordura) {
		this.percentualGordura = percentualGordura;
	}

	public Double getMassaGorda() {
		return massaGorda;
	}

	public void setMassaGorda(Double massaGorda) {
		this.massaGorda = massaGorda;
	}

	public Double getMassaMagra() {
		return massaMagra;
	}

	public void setMassaMagra(Double massaMagra) {
		this.massaMagra = massaMagra;
	}

	public Double getPesoMaximoRecomendavel() {
		return pesoMaximoRecomendavel;
	}

	public void setPesoMaximoRecomendavel(Double pesoMaximoRecomendavel) {
		this.pesoMaximoRecomendavel = pesoMaximoRecomendavel;
	}

	public Double getObjetivoEmagrecimento() {
		return objetivoEmagrecimento;
	}

	public void setObjetivoEmagrecimento(Double objetivoEmagrecimento) {
		this.objetivoEmagrecimento = objetivoEmagrecimento;
	}

	public Double getPercentualGorduraAlvo() {
		return percentualGorduraAlvo;
	}

	public void setPercentualGorduraAlvo(Double percentualGorduraAlvo) {
		this.percentualGorduraAlvo = percentualGorduraAlvo;
	}

	public Double getPesoAtual() {
		return pesoAtual;
	}

	public void setPesoAtual(Double pesoAtual) {
		this.pesoAtual = pesoAtual;
	}
}
