package br.com.tapananuca.gereacademia.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.tapananuca.gereacademia.comunicacao.AReceberDTO;
import br.com.tapananuca.gereacademia.comunicacao.AReceberPaginaDTO;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTO;
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTO;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTO;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTOResponse;
import br.com.tapananuca.gereacademia.model.Pagamento;
import br.com.tapananuca.gereacademia.model.Pessoa;

import com.badlogic.gdx.utils.Array;

public class PessoaService extends Service{

	private final BigDecimal CEM = new BigDecimal(100);
	
	public GAResponse salvarPessoa(PessoaDTO pessoaDTO){
		
		final GAResponse res = new GAResponse();
		
		if (pessoaDTO == null || pessoaDTO.getNome() == null || pessoaDTO.getNome().isEmpty() ||
				pessoaDTO.getDataNascimento() == null || pessoaDTO.getDataNascimento().isEmpty() ||
				pessoaDTO.getDataInicio() == null || pessoaDTO.getDataInicio().isEmpty() ||
				pessoaDTO.getValorMensal() == null || pessoaDTO.getValorMensal().isEmpty()){
			
			res.setSucesso(false);
			res.setMsg("Dados obrigatórios não preenchidos");
			
			return res;
		}
		
		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		Date nasc = null;
		try {
			nasc = df.parse(pessoaDTO.getDataNascimento());
		} catch (ParseException e1) {
			
			res.setSucesso(false);
			res.setMsg("Data de nascimento inválida");
		}
		
		Date inic = null;
		try {
			inic = df.parse(pessoaDTO.getDataInicio());
		} catch (ParseException e1) {
			
			res.setSucesso(false);
			res.setMsg("Data de início inválida");
		}
		
		final EntityManager em = this.getEm();
		
		try {
			Pessoa pessoa;
			
			if (pessoaDTO.getId() == null || pessoaDTO.getId().isEmpty()){
				
				pessoa = new Pessoa();
				pessoa.setAtivo(true);
			} else {
				
				final Query query = em.createQuery("select p from Pessoa p where p.id = :id");
				query.setParameter("id", Long.valueOf(pessoaDTO.getId()));
				
				try {
					pessoa = (Pessoa) query.getSingleResult();
				} catch (NoResultException e) {
					
					pessoa = new Pessoa();
				}
			}
			
			final Calendar calendar = Calendar.getInstance();
			
			pessoa.setDataNascimento(nasc);
			pessoa.setInicio(inic);
			pessoa.setBairro(pessoaDTO.getBairro());
			pessoa.setEmail(pessoaDTO.getEmail());
			pessoa.setEndereco(pessoaDTO.getEndereco());
			pessoa.setNome(pessoaDTO.getNome());
			pessoa.setNumero(pessoaDTO.getNumero());
			pessoa.setSexo(pessoaDTO.getSexo());
			pessoa.setTelefone(pessoaDTO.getTelefone());
			pessoa.setValorMensal(new BigDecimal(pessoaDTO.getValorMensal()).setScale(2, RoundingMode.HALF_UP));
			pessoa.setEstadoCivil(pessoaDTO.getEstadoCivil());
			pessoa.setAtivo(pessoaDTO.isAtivo());
			
			em.getTransaction().begin();
			
			if (pessoa.getId() == null){
				
				em.persist(pessoa);
				
				calendar.setTimeInMillis(System.currentTimeMillis());
				calendar.set(Calendar.AM_PM, Calendar.AM);
				calendar.set(Calendar.HOUR, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				
				final Calendar dtInicio = Calendar.getInstance();
				dtInicio.setTime(pessoa.getInicio());
				dtInicio.set(Calendar.AM_PM, Calendar.AM);
				dtInicio.set(Calendar.HOUR, 0);
				dtInicio.set(Calendar.MINUTE, 0);
				dtInicio.set(Calendar.SECOND, 0);
				dtInicio.set(Calendar.MILLISECOND, 0);
				
				if (pessoa.getInicio().before(calendar.getTime()) || dtInicio.getTime().equals(calendar.getTime())){
				
					final Pagamento pagamento = new Pagamento();
					pagamento.setPessoa(pessoa);
					
					final Calendar calInic = Calendar.getInstance();
					calInic.setTime(inic);
					
					//caso inicio se de depois do dia 20 o primeiro pagamento é proporcional
					if (calInic.get(Calendar.DATE) >= 20){
						
						final int dias = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - calInic.get(Calendar.DATE);
						
						final BigDecimal porcentagem = new BigDecimal(dias * 10 / 3);
						
						pagamento.setValorDevido(pessoa.getValorMensal().subtract(pessoa.getValorMensal().multiply(porcentagem).divide(CEM)));
						
					} else {
						
						pagamento.setValorDevido(pessoa.getValorMensal());
					}
					
					pagamento.setDataReferente(pessoa.getInicio());
					
					em.persist(pagamento);
				}
				
			} else {
				
				pessoa = em.merge(pessoa);
			}
			
			em.getTransaction().commit();
			
			res.setSessionId(pessoa.getId().toString());
			
			return res;
			
		} finally {
			this.returnEm(em);
		}
	}

	@SuppressWarnings("unchecked")
	public AReceberPaginaDTO buscarAniversarios(int mes, Integer qtdRegistros, Integer pagina) {
		
		final EntityManager em = this.getEm();
		
		AReceberPaginaDTO dto;
		try {
			Query query = em.createQuery("select new " +
					AReceberDTO.class.getCanonicalName() +
					" (pes.nome, " +
					" concat(day(pes.dataNascimento), '/', month(pes.dataNascimento), '/', year(pes.dataNascimento))) " +
					" from Pessoa pes " +
					" where month(pes.dataNascimento) = :mes " +
					" order by pes.dataNascimento, pes.nome ");
			
			query.setParameter("mes", mes);
			
			if (pagina != null && qtdRegistros != null){
				
				query.setFirstResult((pagina - 1) * qtdRegistros);
				query.setMaxResults(qtdRegistros);
			}
			
			dto = new AReceberPaginaDTO();
			dto.setaReceber(this.getArrayFromList(query.getResultList()));
			
			query = em.createQuery("select " +
					" count(pes.id) " +
					" from Pessoa pes " +
					" where month(pes.dataNascimento) = :mes ");
			
			query.setParameter("mes", mes);
			
			dto.setQtdPaginas(String.valueOf(((Long)query.getSingleResult() / qtdRegistros) + 1));
		} finally {
			
			this.returnEm(em);
		}
		
		return dto;
	}

	public String salvarObjetivos(ObjetivoDTO objetivoDTO) {
		
		if (objetivoDTO == null || objetivoDTO.getIdPessoa() == null || objetivoDTO.getIdPessoa().isEmpty()){
			
			return "Dados insuficientes para salvar objetivos.";
		}
		
		final EntityManager em = this.getEm();
		
		try {
			Pessoa pessoa = em.find(Pessoa.class, Long.valueOf(objetivoDTO.getIdPessoa()));
			
			if (pessoa == null){
				
				return "Dados básicos da pessoa não encontrados.";
			}
			
			pessoa.setEstetica(objetivoDTO.isEstetica());
			pessoa.setLazer(objetivoDTO.isLazer());
			pessoa.setSaude(objetivoDTO.isSaude());
			pessoa.setTerapeutico(objetivoDTO.isTerapeutico());
			pessoa.setCondFisico(objetivoDTO.isCondFisico());
			pessoa.setPrepFisica(objetivoDTO.isPrepFisica());
			pessoa.setAutoRend(objetivoDTO.isAutoRend());
			pessoa.setHipertrofia(objetivoDTO.isHipertrofia());
			
			em.getTransaction().begin();
			em.merge(pessoa);
			em.getTransaction().commit();
			
			return null;
		} finally {
			
			this.returnEm(em);
		}
	}

	public ObjetivoDTOResponse buscarObjetivos(Long idPessoa) {
		
		final ObjetivoDTOResponse resp = new ObjetivoDTOResponse();
		
		if (idPessoa == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar objetivos.");
			
			return resp;
		}
		
		final EntityManager em = this.getEm();
		
		try {
			final Query query = em.createQuery("select new " + 
					ObjetivoDTO.class.getCanonicalName() + 
					"(p.estetica, p.lazer, p.saude, p.terapeutico, p.condFisico, p.prepFisica, p.autoRend, p.hipertrofia) " +
					" from Pessoa p " +
					" where p.id = :id ");
			
			query.setParameter("id", idPessoa);
			
			try {
				resp.setObjetivoDTO((ObjetivoDTO) query.getSingleResult());
			} catch (NoResultException e) {
				
			}
		} finally {
			
			this.returnEm(em);
		}
		
		return resp;
	}

	@SuppressWarnings("unchecked")
	public PessoaDTOResponse buscarNomesPessoa(String nome){
		
		final PessoaDTOResponse resp = new PessoaDTOResponse();
		
		if (nome == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar dados.");
			
			return resp;
		}
		
		final EntityManager em = this.getEm();
		
		try {
			final StringBuilder hql = new StringBuilder("select new ");
			hql.append(PessoaDTO.class.getCanonicalName())
			   .append("(p.id, p.nome)")
			   .append(" from Pessoa p ")
			   .append(" where upper(p.nome) like upper(:nome) ");
			
			final Query query = em.createQuery(hql.toString());
			query.setParameter("nome", "%" + nome + "%");
			
			resp.setPessoasDTO(this.getArrayFromList(query.getResultList()));
		} finally {
			
			this.returnEm(em);
		}
		
		return resp;
	}
	
	public PessoaDTOResponse buscarDadosPessoa(Long id) {
		
		final PessoaDTOResponse resp = new PessoaDTOResponse();
		
		if (id == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar dados.");
			
			return resp;
		}
		
		final EntityManager em = this.getEm();
		
		try {
			final StringBuilder hql = new StringBuilder("select new ");
			hql.append(PessoaDTO.class.getCanonicalName())
			   .append("(p.nome, day(p.dataNascimento), month(p.dataNascimento), year(p.dataNascimento), p.estadoCivil, p.sexo, ")
			   .append("p.endereco, p.numero, p.bairro, p.telefone, p.email, ")
			   .append("day(p.inicio), month(p.inicio), year(p.inicio), p.valorMensal, p.ativo)")
			   .append(" from Pessoa p ")
			   .append(" where p.id = :id ");
			
			final Query query = em.createQuery(hql.toString());
			query.setParameter("id", id);
			
			PessoaDTO dto = null;
			try {
				dto = (PessoaDTO) query.getSingleResult();
			} catch (NoResultException e) {
				
			}
			
			final Array<PessoaDTO> ar = new Array<PessoaDTO>(1);
			ar.add(dto);
			
			resp.setPessoasDTO(ar);
		} finally {
			
			this.returnEm(em);
		}
		
		return resp;
	}

	public String salvarHistoriaPatologica(HistoriaPatologicaDTO historiaPatologicaDTO) {
		
		if (historiaPatologicaDTO == null || historiaPatologicaDTO.getIdPessoa() == null || historiaPatologicaDTO.getIdPessoa().isEmpty()){
			
			return "Dados insuficientes para salvar história patológica.";
		}
		
		final EntityManager em = this.getEm();
		
		try {
			Pessoa pessoa = em.find(Pessoa.class, Long.valueOf(historiaPatologicaDTO.getIdPessoa()));
			
			if (pessoa == null){
				
				return "Dados básicos da pessoa não encontrados.";
			}
			
			pessoa.setCirurgias(historiaPatologicaDTO.getCirurgias());
			pessoa.setSintomasDoencas(historiaPatologicaDTO.getSintomasDoencas());
			pessoa.setMedicamentos(historiaPatologicaDTO.getMedicamentos());
			pessoa.setLesoes(historiaPatologicaDTO.getLesoes());
			pessoa.setAlergias(historiaPatologicaDTO.getAlergias());
			pessoa.setOutros(historiaPatologicaDTO.getOutros());
			pessoa.setCardiopatia(historiaPatologicaDTO.isCardiopatia());
			pessoa.setHipertensao(historiaPatologicaDTO.isHipertensao());
			
			em.getTransaction().begin();
			em.merge(pessoa);
			em.getTransaction().commit();
			
			return null;
		} finally {
			
			this.returnEm(em);
		}
	}

	public HistoriaPatologicaDTOResponse buscarHistPatologica(Long id) {
		
		final HistoriaPatologicaDTOResponse resp = new HistoriaPatologicaDTOResponse();
		
		if (id == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar história patológica");
		}
		
		final EntityManager em = this.getEm();
		
		try {
			final Query query = em.createQuery("select new " +
					HistoriaPatologicaDTO.class.getCanonicalName() +
					"(pes.cirurgias, pes.sintomasDoencas, pes.medicamentos, pes.lesoes, pes.alergias, " +
					" pes.outros, pes.cardiopatia, pes.hipertensao)" +
					" from Pessoa pes where pes.id = :id ");
			
			query.setParameter("id", id);
			
			try {
				resp.setHistoriaPatologicaDTO((HistoriaPatologicaDTO) query.getSingleResult());
			} catch (NoResultException e) {
				
			}
		} finally {
			
			this.returnEm(em);
		}
		
		return resp;
	}

	public HabitosDTOResponse buscarHabitos(Long id) {
		
		final HabitosDTOResponse resp = new HabitosDTOResponse();
		
		if (id == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar hábitos.");
		}
		
		final EntityManager em = this.getEm();
		
		try {
			final Query query = em.createQuery("select new " +
					HabitosDTO.class.getCanonicalName() +
					"(pes.dieta, pes.praticaAtivFisica, " +
					" day(pes.dataUltimoExameMedico), month(pes.dataUltimoExameMedico), year(pes.dataUltimoExameMedico), " +
					" pes.periodExameMedico)" +
					" from Pessoa pes where pes.id = :id ");
			
			query.setParameter("id", id);
			
			try {
				resp.setHabitosDTO((HabitosDTO) query.getSingleResult());
			} catch (NoResultException e) {
				
			}
		} finally {
			
			this.returnEm(em);
		}
		
		return resp;
	}

	public String salvarHabitos(HabitosDTO habitosDTO) {
		
		if (habitosDTO == null || habitosDTO.getIdPessoa() == null || habitosDTO.getIdPessoa().isEmpty()){
			
			return "Dados insuficientes para salvar hábitos.";
		}
		
		final EntityManager em = this.getEm();
		
		try {
			Pessoa pessoa = em.find(Pessoa.class, Long.valueOf(habitosDTO.getIdPessoa()));
			
			if (pessoa == null){
				
				return "Dados básicos da pessoa não encontrados";
			}
			
			pessoa.setDieta(habitosDTO.getDieta());
			pessoa.setPraticaAtivFisica(habitosDTO.getPraticaAtivFisica());
			
			Date dtUltimoExame = null;
			try {
				dtUltimoExame = new SimpleDateFormat("dd/MM/yyyy").parse(habitosDTO.getDataUltimoExameMedico());
			} catch (ParseException e) {
				
				return "Data do último exame inválida";
			}
			
			pessoa.setDataUltimoExameMedico(dtUltimoExame);
			
			if (habitosDTO.getPeriodExameMedico() != null && !habitosDTO.getPeriodExameMedico().isEmpty()){
				
				pessoa.setPeriodExameMedico(Integer.valueOf(habitosDTO.getPeriodExameMedico()));
			}
			
			em.getTransaction().begin();
			em.merge(pessoa);
			em.getTransaction().commit();
			
			return null;
		} finally {
			
			this.returnEm(em);
		}
	}
}
