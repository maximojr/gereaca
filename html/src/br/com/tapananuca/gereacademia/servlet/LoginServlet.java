package br.com.tapananuca.gereacademia.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.GAResponse;
import br.com.tapananuca.gereacademia.comunicacao.LoginDTO;
import br.com.tapananuca.gereacademia.model.Usuario;
import br.com.tapananuca.gereacademia.service.UsuarioService;

@WebServlet(name="login", urlPatterns=Utils.URL_LOGIN)
public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9130839514619461875L;
	
	public static final String PARAM_LOGED_USER = "dados_usuario_logado";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		final Utils utilsInstace = Utils.getInstance();
		
		final LoginDTO loginDTO = utilsInstace.fromJson(LoginDTO.class, req.getReader().readLine());
		
		final GAResponse ga = new GAResponse();
		
		if (loginDTO == null || loginDTO.getSenha() == null || loginDTO.getUsuario() == null){
			
			req.getSession().invalidate();
			
		} else {
		
			final UsuarioService usuarioService = new UsuarioService();
			final Usuario usuario = usuarioService.login(loginDTO);
			
			if (usuario != null){
	
				final HttpSession session = req.getSession();
				session.setAttribute(LoginServlet.PARAM_LOGED_USER, usuario);
				
				ga.setSessionId(session.getId());
				ga.setMsg("Teste login: user: " + req.getParameter("user") + ", pass: " + req.getParameter("password"));
			} else {
				
				ga.setSucesso(false);
				ga.setMsg("Usuário e/ou senha incorreto(s)");
			}
		
		}
		
		final OutputStream out = resp.getOutputStream();
		out.write(utilsInstace.toJson(ga).getBytes());
		out.flush();
		out.close();
	}
}
