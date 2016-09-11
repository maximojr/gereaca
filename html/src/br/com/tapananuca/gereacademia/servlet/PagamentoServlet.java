package br.com.tapananuca.gereacademia.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tapananuca.gereacademia.Util;
import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.AReceberDTO;
import br.com.tapananuca.gereacademia.comunicacao.AReceberPaginaDTO;
import br.com.tapananuca.gereacademia.comunicacao.DadosBaixa;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.service.PagamentoService;

@WebServlet(name="pagamento", urlPatterns=Utils.URL_PAGAMENTOS + "/*")
public class PagamentoServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3699000353788670670L;

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
		
		if (url.endsWith(Utils.URL_A_RECEBER)){
			
			ga = this.buscarPagamentosAReceber(dados.toString());
			
		} else if (url.endsWith(Utils.URL_PAGAR)){
			
			final Long idUsuarioLogado = (Long) req.getSession().getAttribute(LoginServlet.PARAM_ID_LOGED_USER);
			
			ga = this.darBaixaPagamento(dados.toString(), idUsuarioLogado);
			
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
	
	private GAResponse darBaixaPagamento(String dados, Long idUusuarioLogado) {
		
		final DadosBaixa dadosBaixa = Utils.getInstance().fromJson(DadosBaixa.class, dados);
		
		final PagamentoService pagamentoService = new PagamentoService();
		
		final GAResponse ga = new GAResponse();
		
		String msg = null;
		try {
			
			msg = pagamentoService.efetuarBaixa(dadosBaixa, idUusuarioLogado);
			
		} catch (Exception e) {
			
			msg = e.getLocalizedMessage();
			e.printStackTrace();
			
			Util.enviarEmailErro(e);
		}
		
		if (msg != null){
			
			ga.setSucesso(false);
			ga.setMsg(msg);
		}
		
		return ga;
	}

	private GAResponse buscarPagamentosAReceber(String dados) {
		
		final AReceberDTO aReceberDTO = Utils.getInstance().fromJson(AReceberDTO.class, dados);
		
		final PagamentoService pagamentoService = new PagamentoService();
		
		final String[] strData = aReceberDTO.getDataRef().split("/");
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(strData[1]), Integer.valueOf(strData[0]) - 1, 1);
		
		try {
			final AReceberPaginaDTO resp = 
					pagamentoService.buscarPagamentos(
							calendar.getTime(), 
							null, 
							10, 
							Integer.valueOf(aReceberDTO.getPaginaAtual()), 
							true,
							aReceberDTO.getTipoAtv());
			
			return resp;
		} catch (Exception e) {
			
			Util.enviarEmailErro(e);
			
			final GAResponse ga = new GAResponse();
			ga.setSucesso(false);
			ga.setMsg(e.getMessage());
			
			return ga;
		}
	}
}
