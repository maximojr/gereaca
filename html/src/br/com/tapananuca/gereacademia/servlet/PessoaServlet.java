package br.com.tapananuca.gereacademia.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tapananuca.gereacademia.Util;
import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.AReceberDTO;
import br.com.tapananuca.gereacademia.comunicacao.AReceberPaginaDTO;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTO;
import br.com.tapananuca.gereacademia.comunicacao.HabitosDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTO;
import br.com.tapananuca.gereacademia.comunicacao.HistoriaPatologicaDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTO;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTO;
import br.com.tapananuca.gereacademia.comunicacao.ObjetivoDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTOResponse;
import br.com.tapananuca.gereacademia.service.MedidaService;
import br.com.tapananuca.gereacademia.service.PessoaService;

@WebServlet(name="pessoa", urlPatterns=Utils.URL_PESSOA + "/*")
public class PessoaServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2214635285042562002L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		final Utils utilsInstace = Utils.getInstance();
		
		final StringBuilder dados = new StringBuilder();
		
		String aux = req.getReader().readLine();
		while (aux != null){
			
			dados.append(aux);
			aux = req.getReader().readLine();
			
			if (aux != null){
				dados.append("\n");
			}
		}
		
		final String url = req.getRequestURI();
		
		GAResponse ga = null;
		
		if (url.endsWith(Utils.URL_PESSOA_ANIVERSARIOS)){
			
			ga = this.buscarAniversarios(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_DADOS_NOMES)){
			
			ga = this.buscarNomesPessoa(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_DADOS_BASICOS)){
			
			ga = this.buscarDadosPessoa(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_DADOS_BASICOS_SALVAR)){
			
			ga = this.salvarDadosPessoa(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_OBJETIVOS)){
			
			ga = this.buscarObjetivosPessoa(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_OBJETIVOS_SALVAR)){
			
			ga = this.salvarObjetivosPessoa(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_HIST_PAT)){
			
			ga = this.buscarHistPatologicaPessoa(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_HIST_PAT_SALVAR)){
			
			ga = this.salvarHistPatologicaPessoa(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_HABITOS)){
			
			ga = this.buscarHabitosPessoa(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_HABITO_SALVAR)){
			
			ga = this.salvarHabitosPessoa(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_MEDIDAS)){
			
			ga = this.buscarMedidasPessoa(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PESSOA_MEDIDAS_SALVAR)){
			
			final MedidaDTO medidaDTO = Utils.getInstance().fromJson(new MedidaDTO(), dados.toString());
			
			ga = this.salvarMedidasPessoa(medidaDTO);
		} else if (url.endsWith(Utils.URL_PESSOA_MEDIDAS_NOVA_DATA)){
			
			ga = this.adicionarNovaDataMedida(dados.toString());
		} else if (url.endsWith(Utils.URL_PESSOA_MEDIDAS_GERAR_RELATORIO)){
			
			final Long idUsuario = (Long) req.getSession().getAttribute(LoginServlet.PARAM_ID_LOGED_USER);
			
			ga = this.gerarRelatorio(dados.toString(), idUsuario);
		} else if (url.endsWith(Utils.URL_PESSOA_MEDIDAS_ENVIAR_RELATORIO)){
			
			final Long idUsuario = (Long) req.getSession().getAttribute(LoginServlet.PARAM_ID_LOGED_USER);
			
			ga = this.enviarRelatorio(dados.toString(), idUsuario);
		} else if (url.endsWith(Utils.URL_CADASTRADOS)){
			
			ga = this.buscarPessoas(dados.toString());
		} else {
			
			ga = new GAResponse();
			ga.setSucesso(false);
			ga.setMsg("Não foi possível determinar a ação a ser executada.");
		}
		
		final OutputStream out = resp.getOutputStream();
		out.write(utilsInstace.toJson(ga).getBytes());
		out.flush();
		out.close();
	}
	
	private GAResponse buscarPessoas(String dados) {
		
		final AReceberDTO dto = Utils.getInstance().fromJson(new AReceberDTO(), dados);
		
		final PessoaService pessoaService = new PessoaService();
		
		try {
			
			return pessoaService.buscarPessoas(30, Integer.valueOf(dto.getPaginaAtual()));
		} catch (Exception e){
			
			Util.enviarEmailErro(e);
			
			final GAResponse ga = new GAResponse();
			ga.setSucesso(false);
			ga.setMsg(e.getLocalizedMessage());
			
			return ga;
		}
	}

	private GAResponse gerarRelatorio(String dados, Long idUsuario) {
		
		final MedidaDTO medidaDTO = Utils.getInstance().fromJson(new MedidaDTO(), dados.toString());
		
		GAResponse ga = this.salvarMedidasPessoa(medidaDTO);
		
		if (!ga.isSucesso()){
			
			return ga;
		}
		
		final MedidaPersonalDTO dto = this.montarPersonalDTO(medidaDTO);
		
		final MedidaService medidaService = new MedidaService();
		
		final ReportHelper helper = medidaService.avaliarComposicaoCorporal(dto, idUsuario);
		
		if (helper.getMsg() != null){
			
			ga.setSucesso(false);
			ga.setMsg(helper.getMsg());
		} else if (helper.getDados() != null){
			
			final String key = String.valueOf(System.currentTimeMillis());
			PersonalServlet.relatorios.put(key, helper);
			
			ga.setMsg(key);
		}
		
		return ga;
	}

	private GAResponse enviarRelatorio(String dados, Long idUsuario) {
		
		final MedidaDTO medidaDTO = Utils.getInstance().fromJson(new MedidaDTO(), dados.toString());
		
		GAResponse ga = this.salvarMedidasPessoa(medidaDTO);
		
		if (!ga.isSucesso()){
			
			return ga;
		}
		
		final MedidaPersonalDTO dto = this.montarPersonalDTO(medidaDTO);
		
		final MedidaService medidaService = new MedidaService();
		
		String msg = null;
		try {
			
			msg = medidaService.enviarAvaliacaoEmail(dto, idUsuario);
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			msg = e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		if (msg != null){
			
			ga.setSucesso(false);
			ga.setMsg(msg);
		}
		
		return ga;
	}
	
	private MedidaPersonalDTO montarPersonalDTO(MedidaDTO medidaDTO) {
		
		final MedidaPersonalDTO dto = new MedidaPersonalDTO();
		dto.setIdPessoa(medidaDTO.getIdPessoa());
		
		final List<String> data = new ArrayList<String>();
		data.add(medidaDTO.getDataReferente());
		dto.setDatasMedidas(data);
		
		dto.setDobra(medidaDTO.getDobra());
		dto.setNivelMaturacao(medidaDTO.getNivelMaturacao());
		dto.setPercentualPesoMaximoRec(medidaDTO.getPercentualPesoMaximoRec());
		
		return dto;
	}

	private GAResponse buscarAniversarios(String dados){
		
		final AReceberDTO aReceberDTO = Utils.getInstance().fromJson(new AReceberDTO(), dados);
		
		final PessoaService pessoaService = new PessoaService();
		
		try {
			final AReceberPaginaDTO resp = 
					pessoaService.buscarAniversarios(
							Integer.valueOf(aReceberDTO.getDataRef()), 
							10, 
							Integer.valueOf(aReceberDTO.getPaginaAtual()));
			
			return resp;
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			GAResponse ga = new GAResponse();
			ga.setSucesso(false);
			ga.setMsg(e.getMessage());
			
			return ga;
		}
	}
	
	private GAResponse buscarNomesPessoa(String dados){
		
		final PessoaDTO pessoaDTO = Utils.getInstance().fromJson(new PessoaDTO(), dados);
		
		if (pessoaDTO == null){
			
			final GAResponse ga = new GAResponse();
			ga.setSucesso(false);
			ga.setMsg("Dados insuficientes para buscar nomes.");
			
			return ga;
		}
		
		final PessoaService pessoaService = new PessoaService();
		
		try {
			return pessoaService.buscarNomesPessoa(pessoaDTO.getNome());
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			final GAResponse ga = new GAResponse();
			ga.setSucesso(false);
			ga.setMsg(e.getLocalizedMessage());
			
			return ga;
		}
	}

	private GAResponse buscarDadosPessoa(String dados) {
		
		final PessoaDTO pessoaDTO = Utils.getInstance().fromJson(new PessoaDTO(), dados);
		
		PessoaDTOResponse rs = null;
		if (pessoaDTO == null || pessoaDTO.getId() == null /*|| objetivoDTO.getIdPessoa().isEmpty()*/){
			
			rs = new PessoaDTOResponse();
			rs.setSucesso(false);
			rs.setMsg("Dados insuficientes para buscar informações básicas.");
			
			return rs;
		}
		
		final PessoaService pessoaService = new PessoaService();
		
		try {
			rs = pessoaService.buscarDadosPessoa(Long.valueOf(pessoaDTO.getId()));
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			rs = new PessoaDTOResponse();
			rs.setSucesso(false);
			rs.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return rs;
	}

	private GAResponse salvarDadosPessoa(String dados) {
		
		final PessoaDTO pessoaDTO = Utils.getInstance().fromJson(new PessoaDTO(), dados);
		
		if (pessoaDTO.getAtividades() != null){
			
			pessoaDTO.setAtividades(pessoaDTO.getAtividades().replaceAll("_", ""));
		}
		
		final PessoaService pessoaService = new PessoaService();
		
		GAResponse ga = null;
		
		try {
			ga = pessoaService.salvarPessoa(pessoaDTO);
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			e.printStackTrace();
			
			ga = new GAResponse();
			ga.setSucesso(false);
			ga.setMsg(e.getLocalizedMessage());
		}
		
		return ga;
	}

	private GAResponse buscarObjetivosPessoa(String dados) {
		
		final ObjetivoDTO objetivoDTO = Utils.getInstance().fromJson(new ObjetivoDTO(), dados);
		
		ObjetivoDTOResponse rs = null;
		if (objetivoDTO == null || objetivoDTO.getIdPessoa() == null || objetivoDTO.getIdPessoa().isEmpty()){
			
			rs = new ObjetivoDTOResponse();
			rs.setSucesso(false);
			rs.setMsg("Dados insuficientes para buscar objetivos.");
			
			return rs;
		}
		
		final PessoaService pessoaService = new PessoaService();
		
		try {
			rs = pessoaService.buscarObjetivos(Long.valueOf(objetivoDTO.getIdPessoa()));
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			rs = new ObjetivoDTOResponse();
			rs.setSucesso(false);
			rs.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return rs;
	}

	private GAResponse salvarObjetivosPessoa(String dados) {
		
		final ObjetivoDTO objetivoDTO = Utils.getInstance().fromJson(new ObjetivoDTO(), dados);
		
		final PessoaService pessoaService = new PessoaService();
		
		String msg = null;
		
		try {
			msg = pessoaService.salvarObjetivos(objetivoDTO);
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			msg = e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		final GAResponse ga = new GAResponse();
		if (msg != null){
			
			ga.setSucesso(false);
			ga.setMsg(msg);
		}
		
		return ga;
	}

	private GAResponse buscarHistPatologicaPessoa(String dados) {
		
		final HistoriaPatologicaDTO historiaPatologicaDTO = Utils.getInstance().fromJson(new HistoriaPatologicaDTO(), dados);
		
		HistoriaPatologicaDTOResponse rs = null;
		if (historiaPatologicaDTO == null || 
				historiaPatologicaDTO.getIdPessoa() == null || 
				historiaPatologicaDTO.getIdPessoa().isEmpty()){
			
			rs = new HistoriaPatologicaDTOResponse();
			rs.setSucesso(false);
			rs.setMsg("Dados insuficientes para buscar história patológica.");
			
			return rs;
		}
		
		final PessoaService pessoaService = new PessoaService();
		
		try {
			rs = pessoaService.buscarHistPatologica(Long.valueOf(historiaPatologicaDTO.getIdPessoa()));
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			rs = new HistoriaPatologicaDTOResponse();
			rs.setSucesso(false);
			rs.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return rs;
	}

	private GAResponse salvarHistPatologicaPessoa(String dados) {
		
		final HistoriaPatologicaDTO historiaPatologicaDTO = Utils.getInstance().fromJson(new HistoriaPatologicaDTO(), dados);
		
		final PessoaService pessoaService = new PessoaService();
		
		String msg = null;
		
		try {
			msg = pessoaService.salvarHistoriaPatologica(historiaPatologicaDTO);
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			msg = e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		final GAResponse ga = new GAResponse();
		if (msg != null){
			
			ga.setSucesso(false);
			ga.setMsg(msg);
		}
		
		return ga;
	}

	private GAResponse buscarHabitosPessoa(String dados) {
		
		final HabitosDTO habitosDTO = Utils.getInstance().fromJson(new HabitosDTO(), dados);
		
		HabitosDTOResponse rs = new HabitosDTOResponse();
		if (habitosDTO == null || habitosDTO.getIdPessoa() == null || habitosDTO.getIdPessoa().isEmpty()){
			
			rs.setSucesso(false);
			rs.setMsg("Dados insuficientes para buscar hábitos.");
			
			return rs;
		}
		
		final PessoaService pessoaService = new PessoaService();
		
		try {
			rs = pessoaService.buscarHabitos(Long.valueOf(habitosDTO.getIdPessoa()));
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			rs.setSucesso(false);
			rs.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return rs;
	}

	private GAResponse salvarHabitosPessoa(String dados) {
		
		final HabitosDTO habitosDTO = Utils.getInstance().fromJson(new HabitosDTO(), dados);
		
		final PessoaService pessoaService = new PessoaService();
		
		String msg = null;
		
		try {
			msg = pessoaService.salvarHabitos(habitosDTO);
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			msg = e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		final GAResponse ga = new GAResponse();
		if (msg != null){
			
			ga.setSucesso(false);
			ga.setMsg(msg);
		}
		
		return ga;
	}

	private GAResponse buscarMedidasPessoa(String dados) {
		
		final MedidaDTO medidaDTO = Utils.getInstance().fromJson(new MedidaDTO(), dados);
		
		MedidaDTOResponse rs = null;
		
		if (medidaDTO == null || medidaDTO.getIdPessoa() == null || medidaDTO.getIdPessoa().isEmpty() ||
				medidaDTO.getDataReferente() == null || medidaDTO.getDataReferente().isEmpty()){
			
			rs = new MedidaDTOResponse();
			rs.setSucesso(false);
			rs.setMsg("Dados insuficientes para buscar medidas.");
			
			return rs;
		}
		
		final MedidaService medidaService = new MedidaService();
		
		try {
			
			final String[] strData = medidaDTO.getDataReferente().split("/");
			
			final Calendar calendar = Calendar.getInstance();
			calendar.set(Integer.valueOf(strData[2]), Integer.valueOf(strData[1]) - 1, Integer.valueOf(strData[0]));
			
			rs = medidaService.buscarMedidas(
					Long.valueOf(medidaDTO.getIdPessoa()),
					calendar.getTime());
			
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			rs = new MedidaDTOResponse();
			rs.setSucesso(false);
			rs.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return rs;
	}

	private GAResponse salvarMedidasPessoa(MedidaDTO medidaDTO) {
		
		final MedidaService medidaService = new MedidaService();
		
		String msg = null;
		
		try {
			msg = medidaService.salvarMedidas(medidaDTO);
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			msg = e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		final GAResponse ga = new GAResponse();
		if (msg != null){
			
			ga.setSucesso(false);
			ga.setMsg(msg);
		}
		
		return ga;
	}
	
	private GAResponse adicionarNovaDataMedida(String dados) {
		
		final MedidaDTO medidaDTO = Utils.getInstance().fromJson(new MedidaDTO(), dados);
		
		final MedidaService medidaService = new MedidaService();
		
		String msg = null;
		
		try {
			msg = medidaService.adicionarData(medidaDTO);
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			msg = e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		final GAResponse ga = new GAResponse();
		if (msg != null){
			
			ga.setSucesso(false);
			ga.setMsg(msg);
		}
		
		return ga;
	}
}
