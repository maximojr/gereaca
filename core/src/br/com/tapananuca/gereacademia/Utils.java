package br.com.tapananuca.gereacademia;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.tapananuca.gereacademia.comunicacao.GARequest;

import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

public class Utils {
	
	private static Utils utils;

	public String WEB_APP_END_POINT = "web.app.endpoint";
	
	private final Json json = new Json();
	
	private String sessionId;
	
	private static ObjectMap<String, String> properties;
	
	private Utils(){}
	
	public static void starInstance(ObjectMap<String, String> properties){
		
		Utils.properties = properties;
	}
	
	public static Utils getInstance(){
		
		if (utils == null){
			
			utils = new Utils();
		}
		
		return utils;
	}
	
	public String criptografar(String msg){
		
		final StringBuffer sb = new StringBuffer();
		
		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(msg.getBytes());
			
			final byte byteData[] = md.digest();
			 
			for (int i = 0; i < byteData.length; i++) {
				
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
		} catch (NoSuchAlgorithmException e1) {
			
			throw new RuntimeException(e1.getMessage());
		}
		
		return sb.toString();
	}
	
	public <T> T fromJson(Class<T> clazz, String jsonString){
		return json.fromJson(clazz, jsonString);
	}
	
	public String toJson(Object object){
		return json.toJson(object);
	}
	
	public HttpRequest criarRequest(String url, GARequest parametros){
		
		final HttpRequest request = new HttpRequest(HttpMethods.POST);
		request.setUrl(properties.get(WEB_APP_END_POINT) + url);
		request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        
        if (sessionId != null){
        	request.setHeader("JSESSIONID", utils.sessionId);
        }
        
		request.setContent(utils.toJson(parametros));
		
		return request;
	}

	public void setSessionId(String sessionId) {
		
		if (utils.sessionId == null || utils.sessionId.isEmpty()){
			
			utils.sessionId = sessionId;
		}
	}
}
