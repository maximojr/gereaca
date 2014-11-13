package br.com.tapananuca.gereacademia.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="teste", urlPatterns="/teste")
public class TesteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4132887838023973552L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		OutputStream out = resp.getOutputStream();
		
		out.write(("Teste random: " + Math.random() * 100).getBytes());
		
		out.flush();
		out.close();
	}

	
}
