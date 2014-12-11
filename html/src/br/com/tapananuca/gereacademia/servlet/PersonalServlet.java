package br.com.tapananuca.gereacademia.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.service.MedidaService;

@WebServlet(name="personal", urlPatterns=Utils.URL_PERSONAL + "/*")
public class PersonalServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8004202272031420174L;
	
	private static final Map<String, byte[]> relatorios = new HashMap<String, byte[]>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.doPost(req, resp);
	}
	
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
		
		if (url.endsWith(Utils.URL_PERSONAL_DATAS)){
			
			ga = this.carregarDatas(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PERSONAL_ADD_AULA)){
			
			final Long idUsuarioLogado = (Long) req.getSession().getAttribute(LoginServlet.PARAM_ID_LOGED_USER);
			
			ga = this.addAula(dados.toString(), idUsuarioLogado);
			
		} else if (url.endsWith(Utils.URL_PERSONAL_GERAR_RELATORIO)){
			
			ga = new GAResponse();
			
			final Long idUsuario = (Long) req.getSession().getAttribute(LoginServlet.PARAM_ID_LOGED_USER);
			
			final ReportHelper helper = this.gerarRelatorioAvaliacao(dados.toString(), idUsuario);
			
			if (helper.getMsg() != null){
				
				ga.setSucesso(false);
				ga.setMsg(helper.getMsg());
			} else if (helper.getDados() != null){
				
				final String key = String.valueOf(System.currentTimeMillis());
				relatorios.put(key, helper.getDados());
				
				ga.setMsg(key);
			}
			
		} else if (url.endsWith(Utils.URL_PERSONAL_ABRIR_RELATORIO)){
			
			final String key = req.getParameter(Utils.URL_PERSONAL_KEY_RELATORIO);
			
			final byte[] dadosRelatorio = relatorios.get(key);
			
			resp.setContentType("application/pdf");
			resp.addHeader("Content-Disposition", "attachment; filename=relatorio.pdf");
			resp.setContentLength(dadosRelatorio.length);
			
			final OutputStream responseOutputStream = resp.getOutputStream();
			responseOutputStream.write(dadosRelatorio);
			responseOutputStream.flush();
			responseOutputStream.close();
			
			relatorios.remove(key);
			
			return;
		} else if (url.endsWith(Utils.URL_PERSONAL_ENVIAR_RELATORIO)){
			
			//TODO
			
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

	private GAResponse carregarDatas(String dados) {
		
		final PessoaDTO pessoaDTO = Utils.getInstance().fromJson(PessoaDTO.class, dados);
		
		final MedidaService medidaService = new MedidaService();
		
		MedidaPersonalDTOResponse ga = null;
		
		try {
			
			ga = medidaService.buscarDatas(pessoaDTO);
		} catch (Exception e) {
			
			e.printStackTrace();
			ga = new MedidaPersonalDTOResponse();
			ga.setSucesso(false);
			ga.setMsg(e.getLocalizedMessage());
		}
		
		return ga;
	}

	private GAResponse addAula(String dados, Long idUsuarioLogado) {
		
		final MedidaDTO medidaDTO = Utils.getInstance().fromJson(MedidaDTO.class, dados);
		
		final MedidaService medidaService = new MedidaService();
		
		final GAResponse ga = new GAResponse();
		
		try {
			
			final String msg = medidaService.adicionarAulaPersonal(medidaDTO, idUsuarioLogado);
			
			if (msg != null){
				
				ga.setMsg(msg);
				ga.setSucesso(false);
			}
			
		} catch (Exception e){
			
			e.printStackTrace();
			
			ga.setSucesso(false);
			ga.setMsg(e.getLocalizedMessage());
		}
		
		return ga;
	}

	private ReportHelper gerarRelatorioAvaliacao(String dados, Long idUsuario) {
		
		final MedidaPersonalDTO medidaPersonalDTO = Utils.getInstance().fromJson(MedidaPersonalDTO.class, dados);
		
		final MedidaService medidaService = new MedidaService();
		
		ReportHelper res = null;
		
		try {
			
			res = medidaService.avaliarComposicaoCorporal(medidaPersonalDTO, idUsuario);
		} catch (Exception e) {
			
			res = new ReportHelper();
			res.setMsg(e.getLocalizedMessage());
		}
		
		return res;
	}
}
