package br.com.tapananuca.gereacademia.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.tapananuca.gereacademia.comunicacao.AReceberDTO;
import br.com.tapananuca.gereacademia.comunicacao.AReceberPaginaDTO;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.model.Pagamento;
import br.com.tapananuca.gereacademia.model.Pessoa;

public class PessoaService {

	@SuppressWarnings("unchecked")
	public AReceberPaginaDTO buscarPagamentos(Date dataRef, Long idPessoa, Integer qtdRegistros, Integer pagina, boolean emAberto){
		
		final EntityManager em = new Conexao().getEntityManager();
		
		StringBuilder hql = new StringBuilder("select new ");
		hql.append(AReceberDTO.class.getCanonicalName())
		   .append(" (pag.id as id, ")
		   .append(" pes.nome as nome, ")
		   .append(" pag.valorDevido as valor) ")
		   .append(" from Pagamento pag ")
		   .append(" join pag.pessoa pes ")
		   .append(" where ");
		
		if (emAberto){
			
			hql.append(" pag.dataBaixa is null ");
		} else {
			
			hql.append(" pag.dataBaixa is not null ");
		}
		
		if (dataRef != null){
			
			hql.append(" and month(pag.dataRef) = :mes and year(pag.dataRef) = :ano");
		}
		
		if (idPessoa != null){
			
			hql.append(" and pes.id = :idPessoa ");
		}
		
		hql.append(" order by pag.dataRef, pes.nome ");
		
		Query query = em.createQuery(hql.toString());
		
		if (dataRef != null){
			
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataRef);
			
			query.setParameter("mes", calendar.get(Calendar.MONTH) + 1);
			query.setParameter("ano", calendar.get(Calendar.YEAR));
		}
		
		if (idPessoa != null){
			
			query.setParameter("idPessoa", idPessoa);
		}
		
		if (pagina != null && qtdRegistros != null){
			
			query.setFirstResult((pagina - 1) * qtdRegistros);
			query.setMaxResults(qtdRegistros);
		}
		
		final AReceberPaginaDTO dto = new AReceberPaginaDTO();
		dto.setaReceber(query.getResultList());
		
		hql = new StringBuilder("select ");
		hql.append(" count(pag.id) ")
		   .append(" from Pagamento pag ");
		
		dto.setQtdPaginas((Long)query.getSingleResult() + 1);
		
		hql = new StringBuilder("select ");
		hql.append(" concat(month(pag.dataRef), '/', year(pag.dataRef)) ")
		   .append(" from Pagamento pag ")
		   .append(" order by pag.dataRef ");
		
		query = em.createQuery(hql.toString());
		
		dto.setDatasRef(query.getResultList());
		
		return dto;
	}
	
	public Long salvarPessoa(PessoaDTO pessoaDTO){
		
		if (pessoaDTO == null){
			
			return null;
		}
		
		final EntityManager em = new Conexao().getEntityManager();
		
		Pessoa pessoa;
		
		if (pessoaDTO.getId() == null){
			
			pessoa = new Pessoa();
			pessoa.setAtivo(true);
		} else {
			
			final Query query = em.createQuery("select p from Pessoa p where p.id = :id");
			query.setParameter("id", pessoaDTO.getId());
			
			try {
				pessoa = (Pessoa) query.getSingleResult();
			} catch (NoResultException e) {
				
				pessoa = new Pessoa();
			}
		}
		
		final String[] strData = pessoaDTO.getDataNascimento().split("/");
		
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(strData[2]), Integer.valueOf(strData[1]) - 1, Integer.valueOf(strData[0]));
		
		pessoa.setDataNascimento(calendar.getTime());
		pessoa.setBairro(pessoaDTO.getBairro());
		pessoa.setEmail(pessoaDTO.getEmail());
		pessoa.setEndereco(pessoaDTO.getEndereco());
		pessoa.setInicio(new Date());
		pessoa.setNome(pessoaDTO.getNome());
		pessoa.setNumero(Integer.valueOf(pessoaDTO.getNumero()));
		pessoa.setSexo(pessoaDTO.getSexo());
		pessoa.setTelefone(pessoaDTO.getTelefone());
		pessoa.setValorMensal(new BigDecimal(pessoaDTO.getValorMensal()).setScale(2, RoundingMode.HALF_UP));
		
		em.getTransaction().begin();
		
		if (pessoa.getId() == null){
			
			em.persist(pessoa);
			
			final Pagamento pagamento = new Pagamento();
			pagamento.setPessoa(pessoa);
			pagamento.setValorDevido(pessoa.getValorMensal());
			pagamento.setDataReferente(new Date());
			
			em.persist(pagamento);
			
		} else {
			
			pessoa = em.merge(pessoa);
		}
		
		em.getTransaction().commit();
		
		return pessoa.getId();
	}
}
