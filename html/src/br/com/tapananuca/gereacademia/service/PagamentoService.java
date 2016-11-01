package br.com.tapananuca.gereacademia.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.tapananuca.gereacademia.Util;
import br.com.tapananuca.gereacademia.comunicacao.AReceberDTO;
import br.com.tapananuca.gereacademia.comunicacao.AReceberPaginaDTO;
import br.com.tapananuca.gereacademia.comunicacao.Baixa;
import br.com.tapananuca.gereacademia.comunicacao.DadosBaixa;
import br.com.tapananuca.gereacademia.model.Pagamento;
import br.com.tapananuca.gereacademia.model.Pessoa;
import br.com.tapananuca.gereacademia.model.Usuario;

public class PagamentoService extends Service {

	@SuppressWarnings("unchecked")
	public void gerarCobrancaMensal(){
		
		final EntityManager em = this.getEm();
		
		try {
			
			final Calendar calInstance = Calendar.getInstance();
			
			final Date dataRef = calInstance.getTime();
			
			final Query query = em.createQuery("select pes from Pessoa pes "
					+ " where pes.ativo = :ativo "
					+ " and pes.inicio <= :dataRef "
					+ " and pes.id not in ("
					+ "   select distinct p.id "
					+ "		from Pagamento pag "
					+ "		join pag.pessoa p "
					+ "  where month(pag.dataReferente) = :mesRef and year(pag.dataReferente) = :anoRef ) "
					+ ")");
			
			query.setParameter("ativo", true);
			query.setParameter("dataRef", dataRef);
			query.setParameter("mesRef", calInstance.get(Calendar.MONTH) + 1);
			query.setParameter("anoRef", calInstance.get(Calendar.YEAR));
			
			final List<Pessoa> pessoasACobrar = query.getResultList();
			
			Pagamento pagamento = null;
			
			em.getTransaction().begin();
			for (Pessoa p : pessoasACobrar){
				
				pagamento = new Pagamento();
				pagamento.setPessoa(p);
				pagamento.setValorDevido(p.getValorMensal());
				pagamento.setDataReferente(dataRef);
				
				em.persist(pagamento);
			}
			em.getTransaction().commit();
		} catch (Exception e){
			
			Util.enviarEmailErro(e);
			
			e.printStackTrace();
		} finally {
		
			this.returnEm(em);
		}
	}
	
	@SuppressWarnings("unchecked")
	public AReceberPaginaDTO buscarPagamentos(Date dataRef, Long idPessoa, Integer qtdRegistros, Integer pagina, 
			boolean emAberto, String tipoAtv){
		
		final EntityManager em = this.getEm();
		
		final Calendar calendar = Calendar.getInstance();
		
		//só se aplica multa caso o pagamento se dê depois do dia 15
		//e o dia da data referente do pagamento seja menor que 15
		StringBuilder hql = new StringBuilder("select new ");
		hql.append(AReceberDTO.class.getCanonicalName())
		   .append(" (pag.id, ")
		   .append(" pes.nome, ")
		   .append(" pag.valorDevido, ")
		   .append(" case when ((current_date - pag.dataReferente > 15) and (pag.dataBaixa is null)) then true else false end as multa) ")
		   .append(" from Pagamento pag ")
		   .append(" join pag.pessoa pes ")
		   .append(" where ");
		
		if (emAberto){
			
			hql.append(" pag.dataBaixa is null ");
		} else {
			
			hql.append(" pag.dataBaixa is not null ");
		}
		
		if (dataRef != null){
			
			hql.append(" and month(pag.dataReferente) = :mes and year(pag.dataReferente) = :ano ");
		}
		
		if (idPessoa != null){
			
			hql.append(" and pes.id = :idPessoa ");
		}
		
		if (tipoAtv != null && !tipoAtv.isEmpty()){
			
			hql.append(" and pes.atividades like :tipoAtv ");
		}
		
		hql.append(" order by pes.nome ");
		
		Query query = em.createQuery(hql.toString());
		
		if (dataRef != null){
			
			calendar.setTime(dataRef);
			
			query.setParameter("mes", calendar.get(Calendar.MONTH) + 1);
			query.setParameter("ano", calendar.get(Calendar.YEAR));
		}
		
		if (idPessoa != null){
			
			query.setParameter("idPessoa", idPessoa);
		}
		
		if (tipoAtv != null && !tipoAtv.isEmpty()){
			
			query.setParameter("tipoAtv", tipoAtv);
		}
		
		if (pagina != null && qtdRegistros != null){
			
			query.setFirstResult((pagina - 1) * qtdRegistros);
			query.setMaxResults(qtdRegistros);
		}
		
		final AReceberPaginaDTO dto = new AReceberPaginaDTO();
		
		dto.setaReceber(query.getResultList());
		
		hql = new StringBuilder("select ");
		hql.append(" count(pag.id) ")
		   .append(" from Pagamento pag ")
		   .append(" join pag.pessoa pes ")
		   .append(" where ");
		
		if (emAberto){
			
			hql.append(" pag.dataBaixa is null ");
		} else {
			
			hql.append(" pag.dataBaixa is not null ");
		}
		
		if (dataRef != null){
			
			hql.append(" and month(pag.dataReferente) = :mes and year(pag.dataReferente) = :ano ");
		}
		
		if (idPessoa != null){
			
			hql.append(" and pes.id = :idPessoa ");
		}
		
		if (tipoAtv != null && !tipoAtv.isEmpty()){
			
			hql.append(" and pes.atividades like :tipoAtv ");
		}
		
		query = em.createQuery(hql.toString());
		
		if (dataRef != null){
			
			calendar.setTime(dataRef);
			
			query.setParameter("mes", calendar.get(Calendar.MONTH) + 1);
			query.setParameter("ano", calendar.get(Calendar.YEAR));
		}
		
		if (idPessoa != null){
			
			query.setParameter("idPessoa", idPessoa);
		}
		
		if (tipoAtv != null && !tipoAtv.isEmpty()){
			
			query.setParameter("tipoAtv", tipoAtv);
		}
		
		final Long qtd = (Long)query.getSingleResult();
		
		if (qtd <= qtdRegistros){
			
			dto.setQtdPaginas("1");
		} else {
			
			dto.setQtdPaginas(String.valueOf((qtd / qtdRegistros) + 1));
		}
		
		query = em.createQuery("select distinct "
				+ " pag.dataReferente "
				+ " from Pagamento pag "
				+ " where pag.dataBaixa is null "
				+ " order by pag.dataReferente ");
		
		final List<Date> dts = query.getResultList();
		
		final List<String> sDts = new ArrayList<String>();
		
		for (Date d : dts){
			
			calendar.setTime(d);
			String strData = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
			
			if (!sDts.contains(strData)){
				
				sDts.add(strData);
			}
		}
		
		dto.setDatasRef(sDts);
		
		this.returnEm(em);
		
		return dto;
	}
	
	public String efetuarBaixa(DadosBaixa dadosBaixa, Long idUsuarioLogado){
		
		if (dadosBaixa == null || dadosBaixa.getBaixas() == null || dadosBaixa.getBaixas().size() == 0){
			
			return "Dados insuficientes para efetuar baixa.";
		}
		
		if (idUsuarioLogado == null){
			
			return "Faça login para executar essa ação.";
		}
		
		final EntityManager em = this.getEm();
		
		final Usuario usuarioLogado = em.find(Usuario.class, idUsuarioLogado);
		
		try{
			
			em.getTransaction().begin();
			
			Pagamento pagamento = null;
			for (Baixa baixa : dadosBaixa.getBaixas()){
				
				pagamento = em.find(Pagamento.class, Long.valueOf(baixa.getId()));
				
				if (pagamento != null){
					
					pagamento.setDataBaixa(new Date());
					pagamento.setValorPago(BigDecimal.valueOf(Double.valueOf(baixa.getValor())));
					
					if (baixa.getMulta() != null && !baixa.getMulta().isEmpty()){
						pagamento.setMulta(new BigDecimal(Double.valueOf(baixa.getMulta())));
					}
					
					pagamento.setUsuario(usuarioLogado);
					em.merge(pagamento);
				}
			}
			
			em.getTransaction().commit();
			
		} finally {
			
			this.returnEm(em);
		}
		
		return null;
	}
}
