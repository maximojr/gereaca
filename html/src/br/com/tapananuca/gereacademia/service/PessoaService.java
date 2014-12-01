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
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTO;
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTO;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTO;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTOResponse;
import br.com.tapananuca.gereacademia.model.Medida;
import br.com.tapananuca.gereacademia.model.Pagamento;
import br.com.tapananuca.gereacademia.model.Pessoa;

import com.badlogic.gdx.utils.Array;

public class PessoaService extends Service{

	@SuppressWarnings("unchecked")
	public AReceberPaginaDTO buscarPagamentos(Date dataRef, Long idPessoa, Integer qtdRegistros, Integer pagina, boolean emAberto){
		
		final EntityManager em = new Conexao().getEntityManager();
		
		StringBuilder hql = new StringBuilder("select new ");
		hql.append(AReceberDTO.class.getCanonicalName())
		   .append(" (pag.id, ")
		   .append(" pes.nome, ")
		   .append(" pag.valorDevido) ")
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
		
		hql.append(" order by pag.dataReferente, pes.nome ");
		
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
		
		dto.setaReceber(this.getArrayFromList(query.getResultList()));
		
		hql = new StringBuilder("select ");
		hql.append(" count(pag.id) ")
		   .append(" from Pagamento pag ")
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
		
		query = em.createQuery(hql.toString());
		
		if (dataRef != null){
			
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataRef);
			
			query.setParameter("mes", calendar.get(Calendar.MONTH) + 1);
			query.setParameter("ano", calendar.get(Calendar.YEAR));
		}
		
		if (idPessoa != null){
			
			query.setParameter("idPessoa", idPessoa);
		}
		
		dto.setQtdPaginas(String.valueOf(((Long)query.getSingleResult() / qtdRegistros) + 1));
		
		hql = new StringBuilder("select ");
		hql.append(" concat(month(pag.dataReferente), '/', year(pag.dataReferente)) ")
		   .append(" from Pagamento pag ")
		   .append(" order by pag.dataReferente ");
		
		query = em.createQuery(hql.toString());
		
		dto.setDatasRef(this.getArrayFromList(query.getResultList()));
		
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
		
		String[] strData = pessoaDTO.getDataNascimento().split("/");
		
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(strData[2]), Integer.valueOf(strData[1]) - 1, Integer.valueOf(strData[0]));
		pessoa.setDataNascimento(calendar.getTime());
		
		strData = pessoaDTO.getDataInicio().split("/");
		calendar.set(Integer.valueOf(strData[2]), Integer.valueOf(strData[1]) - 1, Integer.valueOf(strData[0]));
		pessoa.setInicio(calendar.getTime());
		
		pessoa.setBairro(pessoaDTO.getBairro());
		pessoa.setEmail(pessoaDTO.getEmail());
		pessoa.setEndereco(pessoaDTO.getEndereco());
		pessoa.setNome(pessoaDTO.getNome());
		pessoa.setNumero(pessoaDTO.getNumero());
		pessoa.setSexo(pessoaDTO.getSexo());
		pessoa.setTelefone(pessoaDTO.getTelefone());
		pessoa.setValorMensal(new BigDecimal(pessoaDTO.getValorMensal()).setScale(2, RoundingMode.HALF_UP));
		pessoa.setEstadoCivil(pessoaDTO.getEstadoCivil());
		
		em.getTransaction().begin();
		
		if (pessoa.getId() == null){
			
			em.persist(pessoa);
			
			final Pagamento pagamento = new Pagamento();
			pagamento.setPessoa(pessoa);
			pagamento.setValorDevido(pessoa.getValorMensal());
			pagamento.setDataReferente(pessoa.getInicio());
			
			em.persist(pagamento);
			
		} else {
			
			pessoa = em.merge(pessoa);
		}
		
		em.getTransaction().commit();
		
		return pessoa.getId();
	}

	@SuppressWarnings("unchecked")
	public AReceberPaginaDTO buscarAniversarios(int mes, Integer qtdRegistros, Integer pagina) {
		
		final EntityManager em = new Conexao().getEntityManager();
		
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
		
		final AReceberPaginaDTO dto = new AReceberPaginaDTO();
		dto.setaReceber(this.getArrayFromList(query.getResultList()));
		
		query = em.createQuery("select " +
				" count(pes.id) " +
				" from Pessoa pes " +
				" where month(pes.dataNascimento) = :mes ");
		
		query.setParameter("mes", mes);
		
		dto.setQtdPaginas(String.valueOf(((Long)query.getSingleResult() / qtdRegistros) + 1));
		
		return dto;
	}

	public String salvarObjetivos(ObjetivoDTO objetivoDTO) {
		
		if (objetivoDTO == null || objetivoDTO.getIdPessoa() == null || objetivoDTO.getIdPessoa().isEmpty()){
			
			return "Dados insuficientes para salvar objetivos.";
		}
		
		final EntityManager em = new Conexao().getEntityManager();
		
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
		pessoa.setEmagrecimento(objetivoDTO.isEmagrecimento());
		
		em.getTransaction().begin();
		em.merge(pessoa);
		em.getTransaction().commit();
		
		return null;
	}

	public ObjetivoDTOResponse buscarObjetivos(Long idPessoa) {
		
		final ObjetivoDTOResponse resp = new ObjetivoDTOResponse();
		
		if (idPessoa == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar objetivos.");
			
			return resp;
		}
		
		final EntityManager em = new Conexao().getEntityManager();
		
		final Query query = em.createQuery("select new " + 
				ObjetivoDTO.class.getCanonicalName() + 
				"(p.estetica, p.lazer, p.saude, p.terapeutico, p.condFisico, p.prepFisica, p.autoRend, p.hipertrofia, p.emagrecimento) " +
				" from Pessoa p " +
				" where p.id = :id ");
		
		query.setParameter("id", idPessoa);
		
		try {
			resp.setObjetivoDTO((ObjetivoDTO) query.getSingleResult());
		} catch (NoResultException e) {
			
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
		
		final EntityManager em = new Conexao().getEntityManager();
		
		final StringBuilder hql = new StringBuilder("select new ");
		hql.append(PessoaDTO.class.getCanonicalName())
		   .append("(p.id, p.nome)")
		   .append(" from Pessoa p ")
		   .append(" where upper(p.nome) like upper(:nome) ");
		
		final Query query = em.createQuery(hql.toString());
		query.setParameter("nome", "%" + nome + "%");
		
		resp.setPessoasDTO(this.getArrayFromList(query.getResultList()));
		
		return resp;
	}
	
	public PessoaDTOResponse buscarDadosPessoa(Long id) {
		
		final PessoaDTOResponse resp = new PessoaDTOResponse();
		
		if (id == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar dados.");
			
			return resp;
		}
		
		final EntityManager em = new Conexao().getEntityManager();
		
		final StringBuilder hql = new StringBuilder("select new ");
		hql.append(PessoaDTO.class.getCanonicalName())
		   .append("(p.nome, day(p.dataNascimento), month(p.dataNascimento), year(p.dataNascimento), p.estadoCivil, p.sexo, ")
		   .append("p.endereco, p.numero, p.bairro, p.telefone, p.email, ")
		   .append("day(p.inicio), month(p.inicio), year(p.inicio), p.valorMensal)")
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
		
		return resp;
	}

	public String salvarHistoriaPatologica(HistoriaPatologicaDTO historiaPatologicaDTO) {
		
		if (historiaPatologicaDTO == null || historiaPatologicaDTO.getIdPessoa() == null || historiaPatologicaDTO.getIdPessoa().isEmpty()){
			
			return "Dados insuficientes para salvar história patológica.";
		}
		
		final EntityManager em = new Conexao().getEntityManager();
		
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
	}

	public HistoriaPatologicaDTOResponse buscarHistPatologica(Long id) {
		
		final HistoriaPatologicaDTOResponse resp = new HistoriaPatologicaDTOResponse();
		
		if (id == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar história patológica");
		}
		
		final EntityManager em = new Conexao().getEntityManager();
		
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
		
		return resp;
	}

	public HabitosDTOResponse buscarHabitos(Long id) {
		
		final HabitosDTOResponse resp = new HabitosDTOResponse();
		
		if (id == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar hábitos.");
		}
		
		final EntityManager em = new Conexao().getEntityManager();
		
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
		
		return resp;
	}

	public String salvarHabitos(HabitosDTO habitosDTO) {
		
		if (habitosDTO == null || habitosDTO.getIdPessoa() == null || habitosDTO.getIdPessoa().isEmpty()){
			
			return "Dados insuficientes para salvar hábitos.";
		}
		
		final EntityManager em = new Conexao().getEntityManager();
		
		Pessoa pessoa = em.find(Pessoa.class, Long.valueOf(habitosDTO.getIdPessoa()));
		
		if (pessoa == null){
			
			return "Dados básicos da pessoa não encontrados.";
		}
		
		pessoa.setDieta(habitosDTO.getDieta());
		pessoa.setPraticaAtivFisica(habitosDTO.getPraticaAtivFisica());
		
		final String[] strData = habitosDTO.getDataUltimoExameMedico().split("/");
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(strData[2]), Integer.valueOf(strData[1]) - 1, Integer.valueOf(strData[0]));
		pessoa.setDataNascimento(calendar.getTime());
		
		pessoa.setDataUltimoExameMedico(calendar.getTime());
		
		if (habitosDTO.getPeriodExameMedico() != null && !habitosDTO.getPeriodExameMedico().isEmpty()){
			
			pessoa.setPeriodExameMedico(Integer.valueOf(habitosDTO.getPeriodExameMedico()));
		}
		
		em.getTransaction().begin();
		em.merge(pessoa);
		em.getTransaction().commit();
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public MedidaDTOResponse buscarMedidas(Long idPessoa, Date dataRef) {
		
		final MedidaDTOResponse resp = new MedidaDTOResponse();
		
		if (idPessoa == null || dataRef == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar hábitos.");
		}
		
		final EntityManager em = new Conexao().getEntityManager();
		
		Query query = em.createQuery("select new " +
				MedidaDTO.class.getCanonicalName() +
				"(m.maPesoCorporal, m.maAltura, m.maPesoMagro, m.maPesoGordura, m.maPorcentagemPG, " +
				" m.maImc, m.maCintura, m.maQuadril, m.maPmrc, m.mcTorax, m.mcAbdomen, m.mcCintura, " +
				" m.mcBiceps, m.mcTriceps, m.mcCoxa, m.mcAntebraco, m.dcBiceps, m.dcTriceps, " +
				" m.dcSubAxilar, m.dcSupraIliacas, m.dcSubEscapular, m.dcToraxica, m.dcAbdominal, m.dcCoxa, m.dcPerna) " +
				" from Medida m join m.pessoa p where p.id = :id and m.dataReferente = :dataRef ");
		
		query.setParameter("id", idPessoa);
		query.setParameter("dataRef", dataRef);
		
		try {
			resp.setMedidaDTO((MedidaDTO) query.getSingleResult());
		} catch (NoResultException e) {
			
		}
		
		query = em.createQuery("select concat(day(m.dataReferente), '/', month(m.dataReferente), '/', year(m.dataReferente)) " +
				" from Medida m join m.pessoa p where p.id = :id " +
				" union " +
				" select concat(day(current_date()), '/', month(current_date()), '/', year(current_date())) " +
				" order by 1 ");
		
		query.setParameter("id", idPessoa);
		
		final Array<String> dts = this.getArrayFromList(query.getResultList());
		
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataRef);
		
		final String dtStr = 
				calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH + 1) + "/" + calendar.get(Calendar.YEAR);
		
		if (!dts.contains(dtStr, false)){
			
			dts.add(dtStr);
		}
		
		resp.setDatasRef(dts);
		
		return resp;
	}

	public String salvarMedidas(MedidaDTO medidaDTO) {
		
		if (medidaDTO == null || medidaDTO.getIdPessoa() == null || medidaDTO.getIdPessoa().isEmpty() ||
				medidaDTO.getDataReferente() == null || medidaDTO.getDataReferente().isEmpty()){
			
			return "Dados insuficientes para salvar medidas";
		}
		
		final Long idPessoa = Long.valueOf(medidaDTO.getIdPessoa());
		
		final String[] strData = medidaDTO.getDataReferente().split("/");
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(strData[2]), Integer.valueOf(strData[1]) - 1, Integer.valueOf(strData[0]));
		
		final EntityManager em = new Conexao().getEntityManager();
		
		final Pessoa pessoa = em.find(Pessoa.class, idPessoa);
		
		if (pessoa == null){
			
			return "Pessoa não encontrada para cadastro de medidas.";
		}
		
		final Query query = 
			em.createQuery(
				"select med from Medida med join med.pessoa pes where pes.id = :idPessoa and med.dataRef = :dataRef");
		
		query.setParameter("idPessoa", idPessoa);
		query.setParameter("dataRef", calendar.getTime());
		
		Medida medida = null;
		
		try {
			medida = (Medida) query.getSingleResult();
		} catch (NoResultException e) {
			
		}
		
		if (medida == null){
			
			medida = new Medida();
			medida.setPessoa(pessoa);
			medida.setDataReferente(calendar.getTime());
		}
		
		medida.setDcAbdominal(this.floatOrNull(medidaDTO.getDcAbdominal()));
		medida.setDcBiceps(this.floatOrNull(medidaDTO.getDcBiceps()));
		medida.setDcCoxa(this.floatOrNull(medidaDTO.getDcCoxa()));
		medida.setDcPerna(this.floatOrNull(medidaDTO.getDcPerna()));
		medida.setDcSubAxilar(this.floatOrNull(medidaDTO.getDcSubAxilar()));
		medida.setDcSubEscapular(this.floatOrNull(medidaDTO.getDcSubEscapular()));
		medida.setDcSupraIliacas(this.floatOrNull(medidaDTO.getDcSupraIliacas()));
		medida.setDcToraxica(this.floatOrNull(medidaDTO.getDcToraxica()));
		medida.setDcTriceps(this.floatOrNull(medidaDTO.getDcTriceps()));
		medida.setMaAltura(this.floatOrNull(medidaDTO.getMaAltura()));
		medida.setMaCintura(this.floatOrNull(medidaDTO.getMaCintura()));
		medida.setMaImc(this.floatOrNull(medidaDTO.getMaImc()));
		medida.setMaPesoCorporal(this.floatOrNull(medidaDTO.getMaPesoCorporal()));
		medida.setMaPesoGordura(this.floatOrNull(medidaDTO.getMaPesoGordura()));
		medida.setMaPesoMagro(this.floatOrNull(medidaDTO.getMaPesoMagro()));
		medida.setMaPmrc(this.floatOrNull(medidaDTO.getMaPmrc()));
		medida.setMaPorcentagemPG(this.floatOrNull(medidaDTO.getMaPorcentagemPG()));
		medida.setMaQuadril(this.floatOrNull(medidaDTO.getMaQuadril()));
		medida.setMcAbdomen(this.floatOrNull(medidaDTO.getMcAbdomen()));
		medida.setMcAntebraco(this.floatOrNull(medidaDTO.getMcAntebraco()));
		medida.setMcBiceps(this.floatOrNull(medidaDTO.getMcBiceps()));
		medida.setMcCintura(this.floatOrNull(medidaDTO.getMcCintura()));
		medida.setMcCoxa(this.floatOrNull(medidaDTO.getMcCoxa()));
		medida.setMcTorax(this.floatOrNull(medidaDTO.getMcTorax()));
		medida.setMcTriceps(this.floatOrNull(medidaDTO.getMcTriceps()));
		
		em.getTransaction().begin();
		
		if (medida.getId() == null){
			
			em.persist(medida);
		} else {
			
			em.merge(medida);
		}
		
		em.getTransaction().commit();
		
		return null;
	}
	
	private Float floatOrNull(String valor){
		
		return valor == null ? null : Float.valueOf(valor);
	}
}
