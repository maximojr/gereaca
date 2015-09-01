package br.com.tapananuca.gereacademia.dtoreport;

import java.util.Date;
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
	
	private Double percentualPesoMaximoRecomendado;
	
	private Double pesoAtual;
	
	private Double estaturaAtual;
	
	private Date dataRef;

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

	public Double getPercentualPesoMaximoRecomendado() {
		return percentualPesoMaximoRecomendado;
	}

	public void setPercentualPesoMaximoRecomendado(Double percentualPesoMaximoRecomendado) {
		this.percentualPesoMaximoRecomendado = percentualPesoMaximoRecomendado;
	}

	public Double getPesoAtual() {
		return pesoAtual;
	}

	public void setPesoAtual(Double pesoAtual) {
		this.pesoAtual = pesoAtual;
	}

	public Double getEstaturaAtual() {
		return estaturaAtual;
	}

	public void setEstaturaAtual(Double estaturaAtual) {
		this.estaturaAtual = estaturaAtual;
	}

	public Date getDataRef() {
		return dataRef;
	}

	public void setDataRef(Date dataRef) {
		this.dataRef = dataRef;
	}
}
