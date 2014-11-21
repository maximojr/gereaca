package br.com.tapananuca.gereacademia.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.PessoaDTO;
import br.com.tapananuca.gereacademia.service.PessoaService;

@WebServlet(name="pessoa", urlPatterns="/salvarPessoa")
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
		
		final PessoaDTO pessoaDTO = utilsInstace.fromJson(PessoaDTO.class, dados);
		
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
		
		final OutputStream out = resp.getOutputStream();
		out.write(utilsInstace.toJson(ga).getBytes());
		out.flush();
		out.close();
	}

}
