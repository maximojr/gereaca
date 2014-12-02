package br.com.tapananuca.gereacademia.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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
			query.setParameter("senha", loginDTO.getSenha());
			
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
}
