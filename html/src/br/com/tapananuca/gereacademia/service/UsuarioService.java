package br.com.tapananuca.gereacademia.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.tapananuca.gereacademia.Utils;
import br.com.tapananuca.gereacademia.comunicacao.LoginDTO;
import br.com.tapananuca.gereacademia.model.Usuario;

public class UsuarioService extends Service {

	public Usuario login(LoginDTO loginDTO){
		
		if (loginDTO == null || loginDTO.getUsuario() == null || loginDTO.getSenha() == null){
			
			return null;
		}
		
		final EntityManager em = this.getEm();
		
		try {
			final Query query = 
					em.createQuery(
							"select u from Usuario u where u.email = :email and u.senha = :senha");
			
			query.setParameter("email", loginDTO.getUsuario());
			query.setParameter("senha", Utils.getInstance().criptografar(loginDTO.getSenha()));
			
			Usuario usuario = null;
			
			try {
				
				usuario = (Usuario) query.getSingleResult();
			} catch (NoResultException e) {
				
				return null;
			}
			
			em.getTransaction().begin();
			usuario.setUltimoAcesso(new Date());
			em.merge(usuario);
			em.getTransaction().commit();
			
			return usuario;
		} finally {
			
			this.returnEm(em);
		}
	}

	public String trocarSenha(LoginDTO loginDTO, Long idUsuarioLogado) {
		
		if (loginDTO == null || idUsuarioLogado == null || loginDTO.getSenha() == null || loginDTO.getSenha().trim().isEmpty()){
			
			return "Dados insuficientes para trocar senha";
		}
		
		if (loginDTO.getNovaSenha() == null || loginDTO.getNovaSenha().trim().isEmpty()){
			
			return "A nova senha não pode ser vazia";
		}
		
		final Utils utilsInstance = Utils.getInstance();
		
		final EntityManager em = this.getEm();
		
		try {
			
			final Query query = 
				em.createQuery(
					"select u from Usuario u where u.id = :id and u.senha = :senha");
			
			query.setParameter("id", idUsuarioLogado);
			query.setParameter("senha", utilsInstance.criptografar(loginDTO.getSenha()));
			
			Usuario usuario = null;
			try {
				usuario = (Usuario) query.getSingleResult();
			} catch (NoResultException e) {
				
			}
			
			if (usuario == null){
				
				return "A senha atual não confere com a senha informada";
			}
			
			if (loginDTO.getNovaSenha().equals(usuario.getSenha())){
				
				return "A nova senha não pode ser igual a senha atual";
			}
			
			usuario.setSenha(utilsInstance.criptografar(loginDTO.getNovaSenha()));
			
			em.getTransaction().begin();
			em.merge(usuario);
			em.getTransaction().commit();
			
		} finally {
			
			this.returnEm(em);
		}
		
		return null;
	}
}
