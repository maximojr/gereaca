package br.com.tapananuca.gereacademia.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tapananuca.gereacademia.comunicacao.GAResponse;

import com.badlogic.gdx.utils.Json;

@WebServlet(name="login", urlPatterns="/login")
public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9130839514619461875L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		OutputStream out = resp.getOutputStream();
		
		GAResponse ga = new GAResponse();
		ga.setToken(String.valueOf(Math.random() * 100));
		ga.setMsg("Teste login: user: " + req.getParameter("user") + ", pass: " + req.getParameter("password"));
		
		Json json = new Json();
		
		out.write(json.toJson(ga).getBytes());
		
		out.flush();
		out.close();
	}
}
