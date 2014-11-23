package br.com.tapananuca.gereacademia.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.model.Pagamento;
import br.com.tapananuca.gereacademia.model.Pessoa;

public class PessoaService {

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
