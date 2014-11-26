package br.com.tapananuca.gereacademia.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.AReceberDTO;
import br.com.tapananuca.gereacademia.comunicacao.AReceberPaginaDTO;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.service.PessoaService;

@WebServlet(name="pessoa", urlPatterns=Utils.URL_PESSOA + "/*")
public class PessoaServlet extends HttpServlet {

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
		
		final String dados = req.getReader().readLine();
		
		final String queryString = req.getRequestURI();
		
		GAResponse ga = null;
		
		if (queryString.endsWith(Utils.URL_PESSOA_A_RECEBER)){
			
			ga = this.buscarPagamentosAReceber(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_PAGAR)){
			
			ga = this.darBaixaPagamento(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_DADOS_BASICOS)){
			
			ga = this.buscarDadosPessoa(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_DADOS_BASICOS_SALVAR)){
			
			ga = this.salvarDadosPessoa(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_OBJETIVOS)){
			
			ga = this.buscarObjetivosPessoa(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_OBJETIVOS_SALVAR)){
			
			ga = this.salvarObjetivosPessoa(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_HIST_PAT)){
			
			ga = this.buscarHistPatologicaPessoa(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_HIST_PAT_SALVAR)){
			
			ga = this.salvarHistPatologicaPessoa(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_HABITOS)){
			
			ga = this.buscarHabitosPessoa(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_HABITO_SALVAR)){
			
			ga = this.salvarHabitosPessoa(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_MEDIDAS)){
			
			ga = this.buscarMedidasPessoa(dados);
			
		} else if (queryString.endsWith(Utils.URL_PESSOA_MEDIDAS_SALVAR)){
			
			ga = this.salvarMedidasPessoa(dados);
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

	private GAResponse buscarPagamentosAReceber(String dados) {
		
		final AReceberDTO aReceberDTO = Utils.getInstance().fromJson(AReceberDTO.class, dados);
		
		final PessoaService pessoaService = new PessoaService();
		
		final String[] strData = aReceberDTO.getDataRef().split("/");
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(strData[2]), Integer.valueOf(strData[1]) - 1, Integer.valueOf(strData[0]));
		
		final AReceberPaginaDTO resp = 
				pessoaService.buscarPagamentos(
						calendar.getTime(), 
						null, 
						10, 
						aReceberDTO.getPaginaAtual(), 
						true);
		
		return resp;
	}

	private GAResponse darBaixaPagamento(String dados) {
		// TODO Auto-generated method stub
		return null;
	}

	private GAResponse buscarDadosPessoa(String dados) {
		// TODO Auto-generated method stub
		return null;
	}

	private GAResponse salvarDadosPessoa(String dados) {
		
		final PessoaDTO pessoaDTO = Utils.getInstance().fromJson(PessoaDTO.class, dados);
		
		final PessoaService pessoaService = new PessoaService();
		
		Long idPessoa = null;
		String msg = null;
		try {
			idPessoa = pessoaService.salvarPessoa(pessoaDTO);
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		
		final GAResponse ga = new GAResponse();
		
		if (idPessoa != null){

			ga.setMsg("Pessoa cadastrada com sucesso.");
		} else {
			
			ga.setSucesso(false);
			ga.setMsg(msg);
		}
		
		return ga;
	}

	private GAResponse buscarObjetivosPessoa(String dados) {
		// TODO Auto-generated method stub
		return null;
	}

	private GAResponse salvarObjetivosPessoa(String dados) {
		// TODO Auto-generated method stub
		return null;
	}

	private GAResponse buscarHistPatologicaPessoa(String dados) {
		// TODO Auto-generated method stub
		return null;
	}

	private GAResponse salvarHistPatologicaPessoa(String dados) {
		// TODO Auto-generated method stub
		return null;
	}

	private GAResponse buscarHabitosPessoa(String dados) {
		// TODO Auto-generated method stub
		return null;
	}

	private GAResponse salvarHabitosPessoa(String dados) {
		// TODO Auto-generated method stub
		return null;
	}

	private GAResponse buscarMedidasPessoa(String dados) {
		// TODO Auto-generated method stub
		return null;
	}

	private GAResponse salvarMedidasPessoa(String dados) {
		// TODO Auto-generated method stub
		return null;
	}
}
