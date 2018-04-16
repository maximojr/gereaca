package br.com.tapananuca.gereacademia.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;

public class BaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1961017026604916651L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		final HttpSession session = request.getSession(false);
		
		if (session == null && 
				!request.getRequestURI().endsWith(Utils.URL_LOGIN) && 
				!request.getRequestURI().endsWith(Utils.URL_LOGOUT) &&
				!request.getRequestURI().contains(Utils.URL_PERSONAL_ABRIR_RELATORIO)){
			
			final GAResponse ga = new GAResponse();
			ga.setSucesso(false);
			ga.setMsg("É necessário estar logado para executar a ação, volte a tela de login.");
			
			final OutputStream out = response.getOutputStream();
			out.write(Utils.getInstance().toJson(ga).getBytes());
			out.flush();
			out.close();
			
			return;
		}
		
		super.service(request, response);
	}

}
