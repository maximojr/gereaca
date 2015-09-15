package br.com.tapananuca.gereacademia.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/test")
public class TestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1037719858954444346L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		final ServletOutputStream out = resp.getOutputStream();
		
		out.write(("### --- charset: " + java.nio.charset.Charset.defaultCharset().toString() + " --- ###").getBytes());
		out.flush();
		out.close();
	}
}
