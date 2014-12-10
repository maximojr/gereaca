package br.com.tapananuca.gereacademia.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.tapananuca.gereacademia.comunicacao.MedidaDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.model.AulaPersonal;
import br.com.tapananuca.gereacademia.model.Medida;
import br.com.tapananuca.gereacademia.model.Pessoa;
import br.com.tapananuca.gereacademia.model.Usuario;

import com.badlogic.gdx.utils.Array;

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
					" from Medida m join m.pessoa p where p.id = :id ");
			
			query.setParameter("id", idPessoa);
			
			final Array<String> dts = this.getArrayFromList(query.getResultList());
			
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataRef);
			
			final String dtStr = 
					calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
			
			if (!dts.contains(dtStr, false)){
				
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
		
		final String[] strData = medidaDTO.getDataReferente().split("/");
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(strData[2]), Integer.valueOf(strData[1]) - 1, Integer.valueOf(strData[0]));
		
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
			medida.setMaPesoCorporal(this.floatOrNull(medidaDTO.getMaPesoCorporal()));
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
			
			Query query = 
				em.createQuery(
					"select concat(day(ap.data), '/', month(ap.data), '/', year(ap.data)) " + 
					" from AulaPersonal ap join ap.pessoa p where p.id = :id order by ap.data");
			
			query.setParameter("id", idPessoa);
			
			res.setDatasAulas(this.getArrayFromList(query.getResultList()));
			
			query = em.createQuery(
				"select concat(day(m.dataReferente), '/', month(m.dataReferente), '/', year(m.dataReferente)) " + 
				" from Medida m join ap.pessoa p where p.id = :id order by m.dataReferente");
			
			query.setParameter("id", idPessoa);
			
			res.setDatasMedidas(this.getArrayFromList(query.getResultList()));
			
		} finally {
			
			this.returnEm(em);
		}
		
		return res;
	}
	
	public String adicionarAulaPersonal(MedidaDTO medidaDTO, Long idUsuario) {
		
		if (medidaDTO == null || medidaDTO.getIdPessoa() == null || medidaDTO.getIdPessoa().isEmpty() ||
				medidaDTO.getDataReferente() == null || medidaDTO.getDataReferente().isEmpty()){
			
			return "Dados insuficientes para adicionar aula";
		}
		
		final EntityManager em = this.getEm();
		
		try {
			
			final Usuario usuario = em.find(Usuario.class, idUsuario);
			final Pessoa pessoa = em.find(Pessoa.class, Long.valueOf(medidaDTO.getIdPessoa()));
			
			final AulaPersonal aulaPersonal = new AulaPersonal();
			aulaPersonal.setUsuario(usuario);
			aulaPersonal.setPessoa(pessoa);
			aulaPersonal.setData(new SimpleDateFormat().parse(medidaDTO.getDataReferente()));
			
			em.getTransaction().begin();
			em.persist(aulaPersonal);
			em.getTransaction().commit();
			
		} catch (ParseException e) {
			
			return "Data inválida";
			
		} finally {
			
			this.returnEm(em);
		}
		
		return null;
	}
	
	public MedidaPersonalDTOResponse avaliarComposicaoCorporal(MedidaPersonalDTO medidaPersonalDTO){
		
		final MedidaPersonalDTOResponse res = new MedidaPersonalDTOResponse();
		
		if (medidaPersonalDTO == null || medidaPersonalDTO.getIdPessoa() == null || 
				medidaPersonalDTO.getDatasMedidas() == null || medidaPersonalDTO.getDatasMedidas().size == 0){
			
			res.setSucesso(false);
			res.setMsg("Dados insuficientes para efetuar cálculos");
			
			return res;
		}
		
		if (medidaPersonalDTO.getDatasMedidas().size != 3){
			
			res.setSucesso(false);
			res.setMsg("Selecione 3 datas");
			
			return res;
		}
		
		final EntityManager em = this.getEm();
		
		try {
			
			final Pessoa pessoa = em.find(Pessoa.class, Long.valueOf(medidaPersonalDTO.getIdPessoa()));
			
			if (pessoa == null){
				
				res.setSucesso(false);
				res.setMsg("Pessoa não encontrada");
				
				return res;
			}
			
			final Calendar hoje = Calendar.getInstance();
			final Calendar dtNasc = Calendar.getInstance();
			dtNasc.setTime(pessoa.getDataNascimento());
			hoje.add(Calendar.YEAR, dtNasc.get(Calendar.YEAR) * -1);
			
			final int idade = hoje.get(Calendar.YEAR) - 1;
			
			final boolean aduto = idade >= 18;
			
			if (aduto){
				
				switch (medidaPersonalDTO.getDobra()) {
					case DUAS:
						
						res.setSucesso(false);
						res.setMsg("Para adultos escolha 3 ou 7 dobras");
						return res;
					
					case TRES:
						
						if (pessoa.getSexo().equals('M')){
						
							this.protocolo3DobrasHomensAdultos();
						} else {
							
							this.protocolo3DobrasMulheresAdultas();
						}
					break;
					
					case SETE:
						
						if (pessoa.getSexo().equals('M')){
							
							this.protocolo7DobrasHomensAdultos();
						} else {
							
							this.protocolo7DobrasMulheresAdultas();
						}
					break;
				}
				
			} else {
				
				switch (medidaPersonalDTO.getDobra()) {
				case DUAS:
					
					if (pessoa.getSexo().equals('M')){
						
					} else {
						
					}
					
				break;

				default:
					res.setSucesso(false);
					res.setMsg("Para menores de 18 anos escolha 2 dobras");
					
					return res;
				}
			}
			
		} finally {
			
			this.returnEm(em);
		}
		
		return res;
	}
	
	private void protocolo7DobrasHomensAdultos(){
		
		//Subescapular
		double d1_1 = 0, d1_2 = 0, d1_3 = 0;
		
		//Tríceps
		double d2_1 = 0, d2_2 = 0, d2_3 = 0;
		
		//Peitoral
		double d3_1 = 0, d3_2 = 0, d3_3 = 0;
		
		//Axilar média
		double d4_1 = 0, d4_2 = 0, d4_3 = 0;
		
		//Supra-ilíaca
		double d5_1 = 0, d5_2 = 0, d5_3 = 0;
		
		//Abdôminal
		double d6_1 = 0, d6_2 = 0, d6_3 = 0;
		
		//Femural médio
		double d7_1 = 0, d7_2 = 0, d7_3 = 0;
		
		
		double idade = 0, peso = 0, perc = 15;
		
		
		double d1_m = (d1_1 + d1_2 + d1_3) / 3; 
		double d2_m = (d2_1 + d2_2 + d2_3) / 3; 
		double d3_m = (d3_1 + d3_2 + d3_3) / 3; 
		double d4_m = (d4_1 + d4_2 + d4_3) / 3; 
		double d5_m = (d5_1 + d5_2 + d5_3) / 3; 
		double d6_m = (d6_1 + d6_2 + d6_3) / 3; 
		double d7_m = (d7_1 + d7_2 + d7_3) / 3; 
		
		double sd = d1_m + d2_m + d3_m + d4_m + d5_m + d6_m + d7_m;
		
		double densidadeCorporal = 1.112 - (0.00043499 * sd) + (0.00000055 * sd * sd) - (0.0002882 * idade);  
		
		double percentualGordura = ((4.95 / densidadeCorporal) - 4.5) * 100;
		
		double massaGorda = percentualGordura * peso / 100;
		double massaMagra = peso - massaGorda;
		double pesoMaximoRecomendavel = massaMagra / perc;
		double objetivoEmagrecimento = peso - pesoMaximoRecomendavel;
	}
	
	private void protocolo3DobrasHomensAdultos(){
		
		//Peitoral
		double d1_1 = 0, d1_2 = 0, d1_3 = 0;
		
		//Abdôminal
		double d2_1 = 0, d2_2 = 0, d2_3 = 0;
		
		//Femural médio
		double d3_1 = 0, d3_2 = 0, d3_3 = 0;
		
		double idade = 0, peso = 0, perc = 15;
		
		double d1_m = (d1_1 + d1_2 + d1_3) / 3; 
		double d2_m = (d2_1 + d2_2 + d2_3 ) / 3; 
		double d3_m = (d3_1 + d3_2 + d3_3) / 3; 
		
		double sd = d1_m + d2_m + d3_m; 
		
		double densidadeCorporal = 1.1093800 - (0.0008267 * sd) + (0.0000016 * sd * sd) - (0.0002574 * idade);
		
		double percentualGordura = ((4.95 / densidadeCorporal) - 4.5) * 100;
		
		double massaGorda = percentualGordura * peso / 100; 
		double massaMagra = peso - massaGorda; 
		double pesoMaximoRecomendavel = massaMagra / perc; 
		double objetivoEmagrecimento = peso - pesoMaximoRecomendavel;
	}
	
	private void protocolo7DobrasMulheresAdultas(){
		
		//Subescapular
		double d1_1 = 0, d1_2 = 0, d1_3 = 0;
		
		//Tríceps
		double d2_1 = 0, d2_2 = 0, d2_3 = 0;
		
		//Peitoral
		double d3_1 = 0, d3_2 = 0, d3_3 = 0;
		
		//Axilar média
		double d4_1 = 0, d4_2 = 0, d4_3 = 0;
		
		//Supra-ilíaca
		double d5_1 = 0, d5_2 = 0, d5_3 = 0;
		
		//Abdôminal
		double d6_1 = 0, d6_2 = 0, d6_3 = 0;
		
		//Femural médio
		double d7_1 = 0, d7_2 = 0, d7_3 = 0;
		
		double idade = 0, peso = 0, perc = 23;
		
		double d1_m = (d1_1 + d1_2 + d1_3) / 3; 
		double d2_m = (d2_1 + d2_2 + d2_3) / 3; 
		double d3_m = (d3_1 + d3_2 + d3_3) / 3; 
		double d4_m = (d4_1 + d4_2 + d4_3) / 3; 
		double d5_m = (d5_1 + d5_2 + d5_3) / 3; 
		double d6_m = (d6_1 + d6_2 + d6_3) / 3; 
		double d7_m = (d7_1 + d7_2 + d7_3) / 3; 
		double sd = d1_m + d2_m + d3_m + d4_m + d5_m + d6_m + d7_m; 
		double d = 1.0970 - (0.00046971 * sd) + (0.00000056 * sd * sd) - (0.00012828 * idade );  
		double g = ((4.95 / d) - 4.5) * 100; 
		double mg = g * peso / 100; 
		double mm = peso - mg; 
		double pi = mm / perc; 
		double obj = peso - pi;
	}
	
	private void protocolo3DobrasMulheresAdultas(){
		
		//Tríceps
		double d1_1 = 0, d1_2 = 0, d1_3 = 0;
		
		//Supra-ilíaca
		double d2_1 = 0, d2_2 = 0, d2_3 = 0;
		
		//Femural médio
		double d3_1 = 0, d3_2 = 0, d3_3 = 0;
		
		double idade = 0, peso = 0, perc = 23;
		
		double d1_m = (d1_1 + d1_2 + d1_3) / 3;
		double d2_m = (d2_1 + d2_2 + d2_3) / 3; 
		double d3_m = (d3_1 + d3_2 + d3_3) / 3; 
		double sd = d1_m + d2_m + d3_m; 
		double d = 1.0994921 - (0.0009929 * sd) + (0.0000023 * sd * sd) - (0.0001392 * idade);  
		double g = ((4.95 / d) - 4.5) * 100; 
		double mg = g * peso / 100; 
		double mm = peso - mg; 
		double pi = mm / perc; 
		double obj = peso - pi;
	}
}
