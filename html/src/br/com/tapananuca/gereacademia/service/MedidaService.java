package br.com.tapananuca.gereacademia.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.tapananuca.gereacademia.Util;
import br.com.tapananuca.gereacademia.comunicacao.Dobra;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.dtoreport.AvaliacaoFisicaDTO;
import br.com.tapananuca.gereacademia.model.AulaPersonal;
import br.com.tapananuca.gereacademia.model.Medida;
import br.com.tapananuca.gereacademia.model.Pessoa;
import br.com.tapananuca.gereacademia.model.Usuario;
import br.com.tapananuca.gereacademia.servlet.ReportHelper;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

public class MedidaService extends Service {

	@SuppressWarnings("unchecked")
	public MedidaDTOResponse buscarMedidas(Long idPessoa, Date dataRef) {
		
		final MedidaDTOResponse resp = new MedidaDTOResponse();
		
		if (idPessoa == null || dataRef == null){
			
			resp.setSucesso(false);
			resp.setMsg("Dados insuficientes para buscar hábitos.");
		}
		
		final EntityManager em = this.getEm();
		
		try {
			Query query = em.createQuery("select new " +
					MedidaDTO.class.getCanonicalName() +
					"(m.maPesoCorporal, m.maAltura, " +
					" m.maCintura, m.maQuadril, m.mcTorax, m.mcAbdomen, m.mcCintura, " +
					" m.mcBiceps, m.mcTriceps, m.mcCoxa, m.mcAntebraco, " +
					" m.dcBiceps1, m.dcTriceps1, m.dcSubAxilar1, m.dcSupraIliacas1, m.dcSubEscapular1, m.dcToraxica1, m.dcAbdominal1, m.dcCoxa1, m.dcPerna1, " +
					" m.dcBiceps2, m.dcTriceps2, m.dcSubAxilar2, m.dcSupraIliacas2, m.dcSubEscapular2, m.dcToraxica2, m.dcAbdominal2, m.dcCoxa2, m.dcPerna2, " +
					" m.dcBiceps3, m.dcTriceps3, m.dcSubAxilar3, m.dcSupraIliacas3, m.dcSubEscapular3, m.dcToraxica3, m.dcAbdominal3, m.dcCoxa3, m.dcPerna3) " +
					" from Medida m join m.pessoa p where p.id = :id and m.dataReferente = :dataRef ");
			
			query.setParameter("id", idPessoa);
			query.setParameter("dataRef", dataRef);
			
			try {
				resp.setMedidaDTO((MedidaDTO) query.getSingleResult());
			} catch (NoResultException e) {
				
			}
			
			query = em.createQuery("select concat(day(m.dataReferente), '/', month(m.dataReferente), '/', year(m.dataReferente)) " +
					" from Medida m join m.pessoa p where p.id = :id ");
			
			query.setParameter("id", idPessoa);
			
			final List<String> dts = query.getResultList();
			
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataRef);
			
			String dtStr = 
					calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
			
			if (!dts.contains(dtStr)){
				
				dts.add(dtStr);
			}
			
			calendar.setTimeInMillis(System.currentTimeMillis());
			dtStr = 
					calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
			
			if (!dts.contains(dtStr)){
				
				dts.add(dtStr);
			}
			
			resp.setDatasRef(dts);
			
			return resp;
		} finally {
			
			this.returnEm(em);
		}
	}

	public String salvarMedidas(MedidaDTO medidaDTO) {
		
		if (medidaDTO == null || medidaDTO.getIdPessoa() == null || medidaDTO.getIdPessoa().isEmpty() ||
				medidaDTO.getDataReferente() == null || medidaDTO.getDataReferente().isEmpty()){
			
			return "Dados insuficientes para salvar medidas";
		}
		
		final Long idPessoa = Long.valueOf(medidaDTO.getIdPessoa());
		
		Date dataRef;
		try {
			dataRef = new SimpleDateFormat("dd/MM/yyyy").parse(medidaDTO.getDataReferente());
		} catch (ParseException e1) {
			
			return "Data inválida";
		}
		
		final EntityManager em = this.getEm();
		
		try {
			final Pessoa pessoa = em.find(Pessoa.class, idPessoa);
			
			if (pessoa == null){
				
				return "Pessoa não encontrada para cadastro de medidas.";
			}
			
			final Query query = 
				em.createQuery(
					"select med from Medida med join med.pessoa pes where pes.id = :idPessoa and med.dataReferente = :dataRef");
			
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("dataRef", dataRef);
			
			Medida medida = null;
			
			try {
				medida = (Medida) query.getSingleResult();
			} catch (NoResultException e) {
				
			}
			
			if (medida == null){
				
				medida = new Medida();
				medida.setPessoa(pessoa);
				medida.setDataReferente(dataRef);
			}
			
			medida.setDcAbdominal1(this.floatOrNull(medidaDTO.getDcAbdominal1()));
			medida.setDcBiceps1(this.floatOrNull(medidaDTO.getDcBiceps1()));
			medida.setDcCoxa1(this.floatOrNull(medidaDTO.getDcCoxa1()));
			medida.setDcPerna1(this.floatOrNull(medidaDTO.getDcPerna1()));
			medida.setDcSubAxilar1(this.floatOrNull(medidaDTO.getDcSubAxilar1()));
			medida.setDcSubEscapular1(this.floatOrNull(medidaDTO.getDcSubEscapular1()));
			medida.setDcSupraIliacas1(this.floatOrNull(medidaDTO.getDcSupraIliacas1()));
			medida.setDcToraxica1(this.floatOrNull(medidaDTO.getDcToraxica1()));
			medida.setDcTriceps1(this.floatOrNull(medidaDTO.getDcTriceps1()));
			
			medida.setDcAbdominal2(this.floatOrNull(medidaDTO.getDcAbdominal2()));
			medida.setDcBiceps2(this.floatOrNull(medidaDTO.getDcBiceps2()));
			medida.setDcCoxa2(this.floatOrNull(medidaDTO.getDcCoxa2()));
			medida.setDcPerna2(this.floatOrNull(medidaDTO.getDcPerna2()));
			medida.setDcSubAxilar2(this.floatOrNull(medidaDTO.getDcSubAxilar2()));
			medida.setDcSubEscapular2(this.floatOrNull(medidaDTO.getDcSubEscapular2()));
			medida.setDcSupraIliacas2(this.floatOrNull(medidaDTO.getDcSupraIliacas2()));
			medida.setDcToraxica2(this.floatOrNull(medidaDTO.getDcToraxica2()));
			medida.setDcTriceps2(this.floatOrNull(medidaDTO.getDcTriceps2()));
			
			medida.setDcAbdominal3(this.floatOrNull(medidaDTO.getDcAbdominal3()));
			medida.setDcBiceps3(this.floatOrNull(medidaDTO.getDcBiceps3()));
			medida.setDcCoxa3(this.floatOrNull(medidaDTO.getDcCoxa3()));
			medida.setDcPerna3(this.floatOrNull(medidaDTO.getDcPerna3()));
			medida.setDcSubAxilar3(this.floatOrNull(medidaDTO.getDcSubAxilar3()));
			medida.setDcSubEscapular3(this.floatOrNull(medidaDTO.getDcSubEscapular3()));
			medida.setDcSupraIliacas3(this.floatOrNull(medidaDTO.getDcSupraIliacas3()));
			medida.setDcToraxica3(this.floatOrNull(medidaDTO.getDcToraxica3()));
			medida.setDcTriceps3(this.floatOrNull(medidaDTO.getDcTriceps3()));
			
			medida.setMaAltura(this.floatOrNull(medidaDTO.getMaAltura()));
			medida.setMaCintura(this.floatOrNull(medidaDTO.getMaCintura()));
			medida.setMaPesoCorporal(this.floatOrNull(medidaDTO.getMaPesoCorporal()));
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
		} finally {
			
			this.returnEm(em);
		}
	}
	
	private Float floatOrNull(String valor){
		
		return valor == null ? null : Float.valueOf(valor);
	}
	
	@SuppressWarnings("unchecked")
	public MedidaPersonalDTOResponse buscarDatas(PessoaDTO pessoaDTO){
		
		final MedidaPersonalDTOResponse res = new MedidaPersonalDTOResponse();
		
		if (pessoaDTO == null || pessoaDTO.getId() == null || pessoaDTO.getId().isEmpty()){
			
			res.setSucesso(false);
			res.setMsg("Dados insuficientes");
			return res;
		}
		
		final Long idPessoa = Long.valueOf(pessoaDTO.getId());
		
		final EntityManager em = this.getEm();
		
		try {
			
			final Query query = em.createQuery(
				"select concat(day(m.dataReferente), '/', month(m.dataReferente), '/', year(m.dataReferente)) " + 
				" from Medida m join m.pessoa p where p.id = :id order by m.dataReferente");
			
			query.setParameter("id", idPessoa);
			
			res.setDatasMedidas(query.getResultList());
			
			this.buscarDadosAulasPersonal(pessoaDTO, res, em);
			
		} finally {
			
			this.returnEm(em);
		}
		
		return res;
	}
	
	public MedidaPersonalDTOResponse buscarDatasAulasPersonal(PessoaDTO pessoaDTO){
		
		final MedidaPersonalDTOResponse res = new MedidaPersonalDTOResponse();
		
		if (pessoaDTO == null || pessoaDTO.getId() == null || pessoaDTO.getId().isEmpty()){
			
			res.setSucesso(false);
			res.setMsg("Dados insuficientes");
			return res;
		}
		
		final EntityManager em = this.getEm();
		
		try {
			
			this.buscarDadosAulasPersonal(pessoaDTO, res, em);
			
		} finally {
			
			this.returnEm(em);
		}
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private void buscarDadosAulasPersonal(PessoaDTO pessoaDTO,
			MedidaPersonalDTOResponse dto, EntityManager em){
		
		Query query = 
			em.createQuery(
				"select day(ap.data) " + 
				" from AulaPersonal ap join ap.pessoa p where p.id = :id " + 
				"  and month(ap.data) = :mes and year(ap.data) = :ano ");
		
		final String[] data = pessoaDTO.getDataInicio().split("/");
		
		final Long idPessoa = Long.valueOf(pessoaDTO.getId());
		
		query.setParameter("id", idPessoa);
		
		query.setParameter("mes", Integer.valueOf(data[0]));
		query.setParameter("ano", Integer.valueOf(data[1]));
		
		dto.setDias(query.getResultList());
		
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, Integer.valueOf(data[0]) - 1);
		dto.setQtdDias(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		dto.setInicioSemana(calendar.get(Calendar.DAY_OF_WEEK));
	}
	
	public String adicionarAulaPersonal(MedidaDTO medidaDTO, Long idUsuario) {
		
		if (medidaDTO == null || medidaDTO.getIdPessoa() == null || medidaDTO.getIdPessoa().isEmpty() ||
				medidaDTO.getDataReferente() == null || medidaDTO.getDataReferente().isEmpty()){
			
			return "Dados insuficientes para adicionar aula";
		}
		
		final Date data;
		
		try {
			
			data = new SimpleDateFormat("dd/MM/yyyy").parse(medidaDTO.getDataReferente());
		} catch (ParseException p){
			
			return "Data inválida";
		}
		
		final EntityManager em = this.getEm();
		
		try {
			
			final Pessoa pessoa = em.find(Pessoa.class, Long.valueOf(medidaDTO.getIdPessoa()));
			
			final Query query = 
				em.createQuery(
					"select ap from AulaPersonal ap join ap.pessoa p where ap.data = :data and p.id = :id");
			
			query.setParameter("id", pessoa.getId());
			query.setParameter("data", data);
			
			AulaPersonal aulaPersonal = null;
			try {
				
				aulaPersonal = (AulaPersonal) query.getSingleResult();
			} catch (NoResultException e) {}
			
			em.getTransaction().begin();
			
			if (aulaPersonal != null){
				
				em.remove(aulaPersonal);
			} else {
				
				final Usuario usuario = em.find(Usuario.class, idUsuario);
				
				aulaPersonal = new AulaPersonal();
				aulaPersonal.setUsuario(usuario);
				aulaPersonal.setPessoa(pessoa);
				aulaPersonal.setData(data);
				
				em.persist(aulaPersonal);
			}
			
			em.getTransaction().commit();
			
		} finally {
			
			this.returnEm(em);
		}
		
		return null;
	}
	
	public String adicionarData(MedidaDTO medidaDTO) {
		
		if (medidaDTO == null || medidaDTO.getIdPessoa() == null || medidaDTO.getIdPessoa().isEmpty() ||
				medidaDTO.getDataReferente() == null || medidaDTO.getDataReferente().isEmpty()){
			
			return "Dados insuficientes para salvar medidas";
		}
		
		Date data = null;
		try {
			data = new SimpleDateFormat("dd/MM/yyyy").parse(medidaDTO.getDataReferente());
		} catch (ParseException e) {
			
			return "Formato de data inválido";
		}
		
		final Long idPessoa = Long.valueOf(medidaDTO.getIdPessoa());
		
		final EntityManager em = this.getEm();
		
		try {
			
			final Query query = 
				em.createQuery(
					"select m from Medida m join m.pessoa p where m.dataReferente = :data and p.id = :idPessoa");
			
			query.setParameter("data", data);
			query.setParameter("idPessoa", idPessoa);
			
			Medida medida = null;
			
			try {
				
				medida = (Medida) query.getSingleResult();
			} catch (NoResultException n){}
			
			if (medida != null){
				
				return "Já existe medida cadastrada para esta data";
			}
			
			final Pessoa pessoa = em.find(Pessoa.class, idPessoa);
			
			medida = new Medida();
			medida.setDataReferente(data);
			medida.setPessoa(pessoa);
			
			em.getTransaction().begin();
			em.persist(medida);
			em.getTransaction().commit();
			
		} finally {
			
			this.returnEm(em);
		}
		
		return null;
	}
	
	public String enviarAvaliacaoEmail(MedidaPersonalDTO medidaPersonalDTO, Long idUsuario) throws IOException, SendGridException{
		
		if (medidaPersonalDTO == null || medidaPersonalDTO.getIdPessoa() == null || medidaPersonalDTO.getIdPessoa().isEmpty()){
			
			return "Dados insuficientes para efetuar cálculos";
		}
		
		String enderecoEmail = null;
		Usuario avaliador = null;
		
		final EntityManager em = this.getEm();
		
		try {
			
			Query query = em.createQuery("select p.email from Pessoa p where p.id = :id");
			query.setParameter("id", Long.valueOf(medidaPersonalDTO.getIdPessoa()));
			
			enderecoEmail = (String) query.getSingleResult();
			
			query = em.createQuery("select u from Usuario u where u.id = :id");
			query.setParameter("id", idUsuario);
			
			avaliador = (Usuario) query.getSingleResult();
			
		} catch(NoResultException e){
			
		} finally {
			
			this.returnEm(em);
		}
		
		if (enderecoEmail == null){
			
			return "Pessoa não possui email cadastrado";
		}
		
		final ReportHelper rh = this.avaliarComposicaoCorporal(medidaPersonalDTO, avaliador.getNome());
		
		if (rh.getMsg() != null){
			
			return rh.getMsg();
		} else if (rh.getDados() == null){
			
			return "Relatório não gerado";
		} 
		
		final String user = System.getenv("SENDGRID_USERNAME");
		final String password = System.getenv("SENDGRID_PASSWORD");
		
		final SendGrid sendGrid = new SendGrid(user, password);
		
		final SendGrid.Email email = new SendGrid.Email();
		email.addTo(enderecoEmail);
		email.setFrom(avaliador.getEmail());
		email.setSubject("Avaliação Física");
		email.setText("Olá, segue em anexo sua avaliação física. É uma mensagem automática, não responda esse email.");
		
		final File arquivo = new File(System.currentTimeMillis() + "_" + medidaPersonalDTO.getIdPessoa());
		final FileOutputStream fos = new FileOutputStream(arquivo);
		fos.write(rh.getDados());
		fos.flush();
		fos.close();
		
		email.addAttachment("avaliacao_fisica.pdf", arquivo);
		
		SendGrid.Response response = sendGrid.send(email);
		
		arquivo.delete();
		
		if (!response.getStatus()){
			
			return response.getCode() + " - " + response.getMessage();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ReportHelper avaliarComposicaoCorporal(MedidaPersonalDTO medidaPersonalDTO, String avaliador){
		
		final ReportHelper res = new ReportHelper();
		
		if (medidaPersonalDTO == null || medidaPersonalDTO.getIdPessoa() == null || 
				medidaPersonalDTO.getDatasMedidas() == null || medidaPersonalDTO.getDatasMedidas().size() == 0 ||
				medidaPersonalDTO.getDobra() == null){
			
			res.setMsg("Dados insuficientes para efetuar cálculos");
			
			return res;
		}
		
		final List<Date> datas = new ArrayList<Date>();
		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		for (String strData : medidaPersonalDTO.getDatasMedidas()){
			
			try {
				
				datas.add(df.parse(strData));
			} catch (Exception e){
				
				res.setMsg("Data inválida");
				
				return res;
			}
		}
		
		final EntityManager em = this.getEm();
		
		try {
			
			final Pessoa pessoa = em.find(Pessoa.class, Long.valueOf(medidaPersonalDTO.getIdPessoa()));
			
			if (pessoa == null){
				
				res.setMsg("Pessoa não encontrada");
				
				return res;
			}
			
			if (medidaPersonalDTO.getPercentualPesoMaximoRec() == null ||
					medidaPersonalDTO.getPercentualPesoMaximoRec().isEmpty()){
				
				if (pessoa.getSexo().equals('M')){
				
					medidaPersonalDTO.setPercentualPesoMaximoRec(String.valueOf((100 - 15) / 100));
				} else {
					
					medidaPersonalDTO.setPercentualPesoMaximoRec(String.valueOf((100 - 23) / 100));
				}
			}
			
			final Calendar hoje = Calendar.getInstance();
			final Calendar dtNasc = Calendar.getInstance();
			dtNasc.setTime(pessoa.getDataNascimento());
			hoje.add(Calendar.YEAR, dtNasc.get(Calendar.YEAR) * -1);
			
			final int idade = hoje.get(Calendar.YEAR) - 1;
			
			final boolean aduto = idade >= 18;
			
			Query query = 
				em.createQuery("select med from Medida med join med.pessoa p "
						+ " where p.id = :idPessoa and med.dataReferente in (:datas) "
						+ " order by med.dataReferente ");
			
			query.setParameter("idPessoa", pessoa.getId());
			query.setParameter("datas", datas);
			
			final List<Medida> lista = query.getResultList();
			
			if (aduto){
				
				switch (medidaPersonalDTO.getDobra()) {
					case DUAS:
						
						res.setMsg("Para adultos escolha 3 ou 7 dobras");
						return res;
					
					case TRES:
						
						if (pessoa.getSexo().equals('M')){
							
							res.setDados(this.protocolo3DobrasHomensAdultos(
									lista, 
									idade, 
									Double.parseDouble(medidaPersonalDTO.getPercentualPesoMaximoRec()),
									avaliador,
									pessoa));
						} else {
							
							res.setDados(this.protocolo3DobrasMulheresAdultas(
									lista, 
									idade, 
									Double.parseDouble(medidaPersonalDTO.getPercentualPesoMaximoRec()),
									avaliador,
									pessoa));
						}
					break;
					
					case SETE:
						
						if (pessoa.getSexo().equals('M')){
							
							res.setDados(this.protocolo7DobrasHomensAdultos(
									lista, 
									idade, 
									Double.parseDouble(medidaPersonalDTO.getPercentualPesoMaximoRec()),
									avaliador,
									pessoa));
						} else {
							
							res.setDados(this.protocolo7DobrasMulheresAdultas(
									lista, 
									idade, 
									Double.parseDouble(medidaPersonalDTO.getPercentualPesoMaximoRec()),
									avaliador,
									pessoa));
						}
					break;
				}
				
			} else {
				
				final double somatorioMedidas = this.calcularSomatorioMedidas(lista.get(lista.size() - 1), medidaPersonalDTO.getDobra());
				
				if (medidaPersonalDTO.getNivelMaturacao() == null){
					
					res.setMsg("Para menores de 18 anos escolha 2 dobras");
					return res;
				}
				
				
				final double nivelMaturacao = medidaPersonalDTO.getNivelMaturacao().getPeso();
				
				if (medidaPersonalDTO.getDobra() == Dobra.DUAS) {
					if (pessoa.getSexo().equals('M')){
						
						if (somatorioMedidas > 35){
							
							res.setDados(this.protocolo2DobrasHomensDobrasMaior35(
									lista, 
									idade, 
									Double.parseDouble(medidaPersonalDTO.getPercentualPesoMaximoRec()),
									somatorioMedidas,
									avaliador,
									pessoa));
							
						} else {
							
							res.setDados(this.protocolo2DobrasHomensDobrasMenor35(
									lista, 
									idade, 
									Double.parseDouble(medidaPersonalDTO.getPercentualPesoMaximoRec()),
									somatorioMedidas,
									nivelMaturacao,
									avaliador,
									pessoa));
						}
						
					} else {
						
						if (somatorioMedidas > 35){
							
							res.setDados(this.protocolo2DobrasMulheresDobrasMaior35(
									lista, 
									idade, 
									Double.parseDouble(medidaPersonalDTO.getPercentualPesoMaximoRec()),
									somatorioMedidas,
									avaliador,
									pessoa));
							
						} else {
							
							res.setDados(this.protocolo2DobrasMulheresDobrasMenor35(
									lista, 
									idade, 
									Double.parseDouble(medidaPersonalDTO.getPercentualPesoMaximoRec()),
									somatorioMedidas,
									avaliador,
									pessoa));
						}
					}
				} else {
					
					res.setMsg("Para menores de 18 anos escolha 2 dobras");
					return res;
				}
			}
			
			res.setNomeArquivo(pessoa.getNome().replace(" ", "_") + "_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
			
		} catch (Exception e){
			
			res.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
			
			Util.enviarEmailErro(e);
		} finally {
			
			this.returnEm(em);
		}
		
		return res;
	}
	
	/*Escalha o percentual de gordura alvo para o cálculo do peso máximo recomendável .  
	 * Heyward recomenda um percentual de 15% acima da Massa Magra. Há uma variação considerada dependendo da corrente de pesquisa.*/
	private byte[] protocolo7DobrasHomensAdultos(List<Medida> medidas, int idade, double percentualPesoMaximoRecomendado,
			String avaliador, Pessoa pessoa) throws JRException{
		
		final List<AvaliacaoFisicaDTO> dtos = new ArrayList<AvaliacaoFisicaDTO>();
		
		for (Medida med : medidas){
			double peso = this.doubleOrZero(med.getMaPesoCorporal());
			double estatura = this.doubleOrZero(med.getMaAltura());
			
			double somatorioMedias = this.calcularSomatorioMedidas(med, Dobra.SETE);
			
			double densidadeCorporal = 1.112 - (0.00043499 * somatorioMedias) + (0.00000055 * somatorioMedias * somatorioMedias) - (0.0002882 * idade);  
			
			double percentualGordura = ((4.95 / densidadeCorporal) - 4.5) * 100;
			
			double massaGorda = percentualGordura * peso / 100;
			double massaMagra = peso - massaGorda;
			double pesoMaximoRecomendavel = massaMagra / percentualPesoMaximoRecomendado;
			double objetivoEmagrecimento = peso - pesoMaximoRecomendavel;
			
			dtos.add(this.montarDTO(medidas, densidadeCorporal, massaGorda, 
							massaMagra, objetivoEmagrecimento, 
							percentualGordura, pesoMaximoRecomendavel, percentualPesoMaximoRecomendado, peso, estatura, med.getDataReferente()));
		}
		
		return this.gerarRelatorioAvaliacaoFisica(Dobra.SETE.getDescricao(), avaliador, pessoa.getNome(), idade, dtos);
	}
	
	private byte[] protocolo3DobrasHomensAdultos(List<Medida> medidas, int idade, double percentualPesoMaximoRecomendado,
			String avaliador, Pessoa pessoa) throws JRException{
		
		final List<AvaliacaoFisicaDTO> dtos = new ArrayList<AvaliacaoFisicaDTO>();
		
		for (Medida med : medidas){
			
			double peso = this.doubleOrZero(med.getMaPesoCorporal());
			double estatura = this.doubleOrZero(med.getMaAltura());
			
			double somatorioMedias = this.calcularSomatorioMedidas(med, Dobra.TRES);
			
			double densidadeCorporal = 1.1093800 - (0.0008267 * somatorioMedias) + (0.0000016 * somatorioMedias * somatorioMedias) - (0.0002574 * idade);
			
			double percentualGordura = ((4.95 / densidadeCorporal) - 4.5) * 100;
			
			double massaGorda = percentualGordura * peso / 100; 
			double massaMagra = peso - massaGorda; 
			double pesoMaximoRecomendavel = massaMagra / percentualPesoMaximoRecomendado; 
			double objetivoEmagrecimento = peso - pesoMaximoRecomendavel;
			
			dtos.add(this.montarDTO(medidas, densidadeCorporal, massaGorda, 
							massaMagra, objetivoEmagrecimento, 
							percentualGordura, pesoMaximoRecomendavel, percentualPesoMaximoRecomendado, peso, estatura, med.getDataReferente()));
		}
		
		
		
		return this.gerarRelatorioAvaliacaoFisica(Dobra.TRES.getDescricao(), avaliador, pessoa.getNome(), idade, dtos);
	}
	
	private byte[] protocolo7DobrasMulheresAdultas(List<Medida> medidas, int idade, double percentualPesoMaximoRecomendado,
			String avaliador, Pessoa pessoa) throws JRException{
		
		final List<AvaliacaoFisicaDTO> dtos = new ArrayList<AvaliacaoFisicaDTO>();
		
		for (Medida med : medidas){
			
			double peso = this.doubleOrZero(med.getMaPesoCorporal());
			double estatura = this.doubleOrZero(med.getMaAltura());
			
			double somatorioMedias = this.calcularSomatorioMedidas(med, Dobra.SETE);
			
			double densidadeCorporal = 1.0970 - (0.00046971 * somatorioMedias) + (0.00000056 * somatorioMedias * somatorioMedias) - (0.00012828 * idade );
			
			double percentualGordura = ((4.95 / densidadeCorporal) - 4.5) * 100;
			
			double massaGorda = percentualGordura * peso / 100; 
			double massaMagra = peso - massaGorda; 
			double pesoMaximoRecomendavel = massaMagra / percentualPesoMaximoRecomendado; 
			double objetivoEmagrecimento = peso - pesoMaximoRecomendavel;
			
			dtos.add(this.montarDTO(medidas, densidadeCorporal, massaGorda, 
							massaMagra, objetivoEmagrecimento, 
							percentualGordura, pesoMaximoRecomendavel, percentualPesoMaximoRecomendado, peso, estatura, med.getDataReferente()));
		}
		
		return this.gerarRelatorioAvaliacaoFisica(Dobra.SETE.getDescricao(), avaliador, pessoa.getNome(), idade, dtos);
	}
	
	private byte[] protocolo3DobrasMulheresAdultas(List<Medida> medidas, int idade, double percentualPesoMaximoRecomendado,
			String avaliador, Pessoa pessoa) throws JRException{
		
		final List<AvaliacaoFisicaDTO> dtos = new ArrayList<AvaliacaoFisicaDTO>();
		
		for (Medida med : medidas){
			
			double peso = this.doubleOrZero(med.getMaPesoCorporal());
			double estatura = this.doubleOrZero(med.getMaAltura());
			
			double somatorioMedias = this.calcularSomatorioMedidas(med, Dobra.TRES);
			
			double densidadeCorporal = 1.0994921 - (0.0009929 * somatorioMedias) + (0.0000023 * somatorioMedias * somatorioMedias) - (0.0001392 * idade);
			
			double percentualGordura = ((4.95 / densidadeCorporal) - 4.5) * 100; 
			
			double massaGorda = percentualGordura * peso / 100; 
			double massaMagra = peso - massaGorda;
			double pesoMaximoRecomendavel = massaMagra / percentualPesoMaximoRecomendado; 
			double objetivoEmagrecimento = peso - pesoMaximoRecomendavel;
			
			dtos.add(this.montarDTO(medidas, densidadeCorporal, massaGorda, 
							massaMagra, objetivoEmagrecimento, 
							percentualGordura, pesoMaximoRecomendavel, percentualPesoMaximoRecomendado, peso, estatura, med.getDataReferente()));
		}
		
		return this.gerarRelatorioAvaliacaoFisica(Dobra.TRES.getDescricao(), avaliador, pessoa.getNome(), idade, dtos);
	}
	
	private byte[] protocolo2DobrasHomensDobrasMaior35(List<Medida> medidas, int idade, double percentualPesoMaximoRecomendado, 
			double somatorioMedidas, String avaliador, Pessoa pessoa) throws JRException {
		
		final List<AvaliacaoFisicaDTO> dtos = new ArrayList<AvaliacaoFisicaDTO>();
		
		for (Medida med : medidas){
			
			double peso = this.doubleOrZero(med.getMaPesoCorporal());
			double estatura = this.doubleOrZero(med.getMaAltura());
			
			double percentualGordura = 0.783 * somatorioMedidas + 1.6;
			double massaGorda = percentualGordura * peso / 100;
			double massaMagra = peso - massaGorda;
			double pesoMaximoRecomendavel = massaMagra / percentualPesoMaximoRecomendado;
			double objetivoEmagrecimento = peso - pesoMaximoRecomendavel;
			
			dtos.add(this.montarDTO(medidas, null, massaGorda, 
							massaMagra, objetivoEmagrecimento, 
							percentualGordura, pesoMaximoRecomendavel, percentualPesoMaximoRecomendado, peso, estatura, med.getDataReferente()));
		}
		
		return this.gerarRelatorioAvaliacaoFisica(Dobra.DUAS.getDescricao(), avaliador, pessoa.getNome(), idade, dtos);
	}

	private byte[] protocolo2DobrasHomensDobrasMenor35(List<Medida> medidas, int idade, double percentualPesoMaximoRecomendado, 
			double somatorioMedidas, double nivelMaturacao, String avaliador, Pessoa pessoa) throws JRException {
		
		final List<AvaliacaoFisicaDTO> dtos = new ArrayList<AvaliacaoFisicaDTO>();
		
		for (Medida med : medidas){
			
			double peso = this.doubleOrZero(med.getMaPesoCorporal());
			double estatura = this.doubleOrZero(med.getMaAltura());
			
			double percentualGordura = 1.21 * somatorioMedidas - 0.008 * (somatorioMedidas * somatorioMedidas) - nivelMaturacao;
			double massaGorda = percentualGordura * peso / 100;
			double massaMagra = peso - massaGorda;
			double pesoMaximoRecomendavel = massaMagra / percentualPesoMaximoRecomendado;
			double objetivoEmagrecimento = peso - pesoMaximoRecomendavel;
			
			dtos.add(this.montarDTO(medidas, null, massaGorda, 
							massaMagra, objetivoEmagrecimento, 
							percentualGordura, pesoMaximoRecomendavel, percentualPesoMaximoRecomendado, peso, estatura, med.getDataReferente()));
		}
		
		return this.gerarRelatorioAvaliacaoFisica(Dobra.DUAS.getDescricao(), avaliador, pessoa.getNome(), idade, dtos);
	}

	private byte[] protocolo2DobrasMulheresDobrasMaior35(List<Medida> medidas, int idade, double percentualPesoMaximoRecomendado, 
			double somatorioMedidas, String avaliador, Pessoa pessoa) throws JRException {
		
		final List<AvaliacaoFisicaDTO> dtos = new ArrayList<AvaliacaoFisicaDTO>();
		
		for (Medida med : medidas){
			
			double peso = this.doubleOrZero(med.getMaPesoCorporal());
			double estatura = this.doubleOrZero(med.getMaAltura());
			
			double percentualGordura = 0.546 * somatorioMedidas + 9.7;
			double massaGorda = percentualGordura * peso / 100;
			double massaMagra = peso - massaGorda;
			double pesoMaximoRecomendavel = massaMagra / percentualPesoMaximoRecomendado;
			double objetivoEmagrecimento = peso - pesoMaximoRecomendavel;
			
			dtos.add(this.montarDTO(medidas, null, massaGorda, 
							massaMagra, objetivoEmagrecimento, 
							percentualGordura, pesoMaximoRecomendavel, percentualPesoMaximoRecomendado, peso, estatura, med.getDataReferente()));
		}
		
		return this.gerarRelatorioAvaliacaoFisica(Dobra.DUAS.getDescricao(), avaliador, pessoa.getNome(), idade, dtos);
	}

	private byte[] protocolo2DobrasMulheresDobrasMenor35(List<Medida> medidas, int idade, double percentualPesoMaximoRecomendado, 
			double somatorioMedidas, String avaliador, Pessoa pessoa) throws JRException {
		
		final List<AvaliacaoFisicaDTO> dtos = new ArrayList<AvaliacaoFisicaDTO>();
		
		for (Medida med : medidas){
			
			double peso = this.doubleOrZero(med.getMaPesoCorporal());
			double estatura = this.doubleOrZero(med.getMaAltura());
			
			double percentualGordura = 1.33 * somatorioMedidas - 0.013 * (somatorioMedidas * somatorioMedidas) - 2.5;
			double massaGorda = percentualGordura * peso / 100;
			double massaMagra = peso - massaGorda;
			double pesoMaximoRecomendavel = massaMagra / percentualPesoMaximoRecomendado;
			double objetivoEmagrecimento = peso - pesoMaximoRecomendavel;
			
			dtos.add(this.montarDTO(medidas, null, massaGorda, 
							massaMagra, objetivoEmagrecimento, 
							percentualGordura, pesoMaximoRecomendavel, percentualPesoMaximoRecomendado, peso, estatura, med.getDataReferente()));
		}
		
		
		return this.gerarRelatorioAvaliacaoFisica(Dobra.DUAS.getDescricao(), avaliador, pessoa.getNome(), idade, dtos);
	}
	
	private double calcularSomatorioMedidas(final Medida m, Dobra dobra){
		
		double subescapular = 0;
		double triceps = 0;
		double toraxica = 0;
		double subAxilar = 0;
		double supraIliaca = 0; 
		double abdominal = 0; 
		double coxa = 0;
		
		subescapular = (this.doubleOrZero(m.getDcSubEscapular1()) + this.doubleOrZero(m.getDcSubEscapular2()) + this.doubleOrZero(m.getDcSubEscapular3())) / 3;
		triceps = (this.doubleOrZero(m.getDcTriceps1()) + this.doubleOrZero(m.getDcTriceps2()) + this.doubleOrZero(m.getDcTriceps3())) / 3;
		toraxica = (this.doubleOrZero(m.getDcToraxica1()) + this.doubleOrZero(m.getDcToraxica2()) + this.doubleOrZero(m.getDcToraxica3())) / 3;
		subAxilar = (this.doubleOrZero(m.getDcSubAxilar1()) + this.doubleOrZero(m.getDcSubAxilar2()) + this.doubleOrZero(m.getDcSubAxilar3())) / 3;
		supraIliaca = (this.doubleOrZero(m.getDcSupraIliacas1()) + this.doubleOrZero(m.getDcSupraIliacas2()) + this.doubleOrZero(m.getDcSupraIliacas3())) / 3;
		abdominal = (this.doubleOrZero(m.getDcAbdominal1()) + this.doubleOrZero(m.getDcAbdominal2()) + this.doubleOrZero(m.getDcAbdominal3())) / 3;
		coxa = (this.doubleOrZero(m.getDcCoxa1()) + this.doubleOrZero(m.getDcCoxa2()) + this.doubleOrZero(m.getDcCoxa3())) / 3;
		
		switch (dobra) {
			case DUAS:
				return triceps + subescapular;
			case TRES:
				return toraxica + abdominal + coxa;
			case SETE:
				return subescapular + triceps + toraxica + subAxilar + supraIliaca + abdominal + coxa;
		}
		
		return 0;
	}
	
	private double doubleOrZero(Float valor){
		
		if (valor == null){
			
			return 0;
		}
		
		return valor;
	}
	
	private AvaliacaoFisicaDTO montarDTO(List<Medida> medidas, Double densidadeCorporal, Double massaGorda, 
			Double massaMagra, Double objetivoEmagrecimento, Double percentualGordura, Double pesoMaximoRecomendavel, 
			Double percentualPesoMaximoRecomendado, Double peso, Double estatura, Date dataRef){
		
		final AvaliacaoFisicaDTO dto = new AvaliacaoFisicaDTO();
		dto.setMedidas(medidas);
		dto.setDensidadeCorporal(densidadeCorporal);
		dto.setMassaGorda(massaGorda);
		dto.setMassaMagra(massaMagra);
		dto.setObjetivoEmagrecimento(objetivoEmagrecimento);
		dto.setPercentualGordura(percentualGordura);
		dto.setPesoMaximoRecomendavel(pesoMaximoRecomendavel);
		dto.setPercentualPesoMaximoRecomendado(100 - (percentualPesoMaximoRecomendado * 100));
		dto.setPesoAtual(peso);
		dto.setEstaturaAtual(estatura);
		dto.setDataRef(dataRef);
		
		return dto;
	}
	
	private byte[] gerarRelatorioAvaliacaoFisica(
			final String tipo,
			final String nomeAvalidor, 
			final String nomePessoa, 
			final Integer idade,
			final List<AvaliacaoFisicaDTO> historico) throws JRException{
		
		final URL url = Thread.currentThread().getContextClassLoader().getResource("/reports");
		
		final String path = url.getPath().replaceAll("%20", " ");
		
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("AVALIADOR", nomeAvalidor);
		parameters.put("NOME_PESSOA", nomePessoa);
		parameters.put("IDADE", idade);
		parameters.put("TIPO_AVALIACAO", tipo);
		parameters.put("SUBREPORT_DIR", path);
		parameters.put("HISTORICO", historico);
		
		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		final AvaliacaoFisicaDTO ultimaAvaliacao = historico.get(historico.size() - 1);
		
		String datas = "";
		for (Medida m : ultimaAvaliacao.getMedidas()){
			
			if (!datas.isEmpty()){
				
				datas += ", ";
			}
			
			datas += df.format(m.getDataReferente());
		}
		
		parameters.put("DATAS", datas);
		
		final List<AvaliacaoFisicaDTO> lista = new ArrayList<AvaliacaoFisicaDTO>(1);
		lista.add(ultimaAvaliacao);
		
		final JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(lista);
		
		final JasperPrint print = JasperFillManager.fillReport(path + "/avaliacao_fisica.jasper", parameters, beanColDataSource);
		return JasperExportManager.exportReportToPdf(print);
	}
}
