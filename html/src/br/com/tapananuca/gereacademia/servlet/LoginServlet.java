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

@WebServlet(name="login", urlPatterns=Utils.URL_LOGIN + "/*")
public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9130839514619461875L;
	
	public static final String PARAM_ID_LOGED_USER = "dados_usuario_logado";

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
		
		if (url.endsWith(Utils.URL_LOGIN)){
			
			ga = this.efetuarLogin(dados.toString(), req);
			
		} else if (url.endsWith(Utils.URL_LOGOUT)){
			
			ga = this.efetuarLogOut(req);
			
		} else if (url.endsWith(Utils.URL_TROCA_SENHA)){
			
			ga = this.trocarSenha(dados.toString(), req);
			
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

	private GAResponse efetuarLogin(String dados, HttpServletRequest req) {
		
		final LoginDTO loginDTO = Utils.getInstance().fromJson(LoginDTO.class, dados);
		
		final GAResponse ga = new GAResponse();
		
		final UsuarioService usuarioService = new UsuarioService();
		final Usuario usuario = usuarioService.login(loginDTO);
		
		if (usuario != null){

			final HttpSession session = req.getSession();
			session.setAttribute(LoginServlet.PARAM_ID_LOGED_USER, usuario.getId());
			
			ga.setSessionId(session.getId());
		} else {
			
			ga.setSucesso(false);
			ga.setMsg("Usuário e/ou senha incorreto(s)");
		}
		
		return ga;
	}

	private GAResponse efetuarLogOut(HttpServletRequest req) {
		
		final GAResponse ga = new GAResponse();
		
		final HttpSession sessao = req.getSession(false);
		
		if (sessao != null){
			
			req.getSession().invalidate();
		}
		
		return ga;
	}

	private GAResponse trocarSenha(String dados, HttpServletRequest req) {
		
		final GAResponse ga = new GAResponse();
		
		final LoginDTO loginDTO = Utils.getInstance().fromJson(LoginDTO.class, dados);
		
		final UsuarioService usuarioService = new UsuarioService();
		
		final HttpSession session = req.getSession();
		
		final String msg = usuarioService.trocarSenha(loginDTO, (Long) session.getAttribute(PARAM_ID_LOGED_USER));
		
		if (msg != null){
			
			ga.setSucesso(false);
			ga.setMsg(msg);
		}
		
		return ga;
	}
}
