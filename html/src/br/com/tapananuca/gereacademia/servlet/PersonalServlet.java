package br.com.tapananuca.gereacademia.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.MedidaDTO;
import br.com.tapananuca.gereacademia.comunicacao.MedidaPersonalDTOResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.service.MedidaService;

@WebServlet(name="personal", urlPatterns=Utils.URL_PERSONAL + "/*")
public class PersonalServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8004202272031420174L;
	
	private static final String RELATORIO = "_relatorio";

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
			
			final FileInputStream relatorio = this.gerarRelatorio(dados.toString(), ga);
			
			if (relatorio != null){
				
				req.getSession().setAttribute(RELATORIO, relatorio);
			}
			
		} else if (url.endsWith(Utils.URL_PERSONAL_ABRIR_RELATORIO)){
			
			final FileInputStream pdfFile = (FileInputStream) req.getSession().getAttribute(RELATORIO);
			
			resp.setContentType("application/pdf");
			resp.addHeader("Content-Disposition", "attachment; filename=relatorio.pdf");
			resp.setContentLength((int) pdfFile.getChannel().size());
			
			final OutputStream responseOutputStream = resp.getOutputStream();
			int bytes;
			while ((bytes = pdfFile.read()) != -1) {
				responseOutputStream.write(bytes);
			}
			pdfFile.close();
			
			req.getSession().removeAttribute(RELATORIO);
			
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

	private FileInputStream gerarRelatorio(String dados, GAResponse ga) {
		// TODO Auto-generated method stub
		return null;
	}
}
