package br.com.tapananuca.gereacademia.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.badlogic.gdx.utils.Array;

public abstract class Service {

	private static final String UNIT_NAME = "PU";
	
	private static EntityManagerFactory emf;
	
	private static final Queue<EntityManager> emPoll = new LinkedList<EntityManager>();
	
	<T extends Object> Array<T> getArrayFromList(List<T> lista){
		
		final Array<T> ret = new Array<T>();
		
		for (T objeto : lista){
			
			ret.add(objeto);
		}
		
		return ret;
	}
	
	public static void iniciarPool(int poolSize){
		
		if (emPoll.isEmpty()){
			
			try {
				
				final String herokuParams = System.getenv("DATABASE_URL");
				
				if (herokuParams == null){
					
					emf = Persistence.createEntityManagerFactory(UNIT_NAME);
					
				} else {
					
					URI dbUri = new URI(herokuParams);
					
					final String username = dbUri.getUserInfo().split(":")[0];
				    final String password = dbUri.getUserInfo().split(":")[1];
				    final String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
				    
				    final Map<String, String> params = new HashMap<String, String>();
				    params.put("javax.persistence.jdbc.user", username);
				    params.put("javax.persistence.jdbc.password", password);
				    params.put("javax.persistence.jdbc.url", dbUrl);
					
					emf = Persistence.createEntityManagerFactory(UNIT_NAME, params);
				}
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    emf = Persistence.createEntityManagerFactory(UNIT_NAME);
			
			for (int index = 0 ; index < poolSize ; index++){
				
				emPoll.add(emf.createEntityManager());
			}
		}
	}
	
	public static void fecharConexoes(){
		
		EntityManager em = emPoll.poll();
		
		while (em != null){
			
			em.close();
			
			em = emPoll.poll();
		}
		
		emf.close();
	}
	
	public EntityManager getEm(){
		
		if (emPoll.isEmpty()){
			
			Service.iniciarPool(3);
		}
		
		EntityManager em = emPoll.poll();
		
		while (em == null){
			
			em = emPoll.poll();
		}
		
		return em;
	}
	
	public void returnEm(EntityManager em){
		
		if (em.getTransaction().isActive()){
			em.getTransaction().commit();
		}
		
		emPoll.add(em);
	}
}
