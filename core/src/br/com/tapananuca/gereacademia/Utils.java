package br.com.tapananuca.gereacademia;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.tapananuca.gereacademia.comunicacao.GARequest;
import br.com.tapananuca.gereacademia.comunicacao.JsonSerializer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
	
	public String toJson(JsonSerializer jsonSerializer){
		
		return jsonSerializer.toJson();
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
	
	public String formatCurrency(BigDecimal valor){
		
		if (valor == null){
			
			return "0,00";
		}
		
		valor = valor.setScale(2, RoundingMode.HALF_UP);
		
		return valor.toString().replace(",", "").replace(".", ",");
	}
	
	public BigDecimal StringToCurrency(String valor){
		
		if (valor == null || valor.trim().isEmpty()){
			
			return BigDecimal.ZERO;
		}
		
		return new BigDecimal(valor.replace(",", "."));
	}
	
	public void mostarAlerta(String titulo, String msg, Stage stage, Skin skin){
		
		final Window window = new Window(titulo == null ? "--- --- ---" : titulo, skin);
		window.setWidth(Gdx.graphics.getWidth());
		window.add(msg);
		window.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				window.remove();
			}
		});
		//window.pack();
		window.setPosition(
				(Gdx.graphics.getWidth() / 2) - (window.getWidth() / 2) ,
				(Gdx.graphics.getHeight() / 2) - (window.getHeight() / 2));
		
		stage.addActor(window);
	}
	
	public final TextFieldFilter currencyFilter = new TextFieldFilter() {
		
		@Override
		public boolean acceptChar(TextField textField, char c) {
			
			switch(c){
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case ',':
					return true;
			}
			
			return false;
		}
	};
	
	public final TextFieldFilter numbersOnlyFilter = new TextFieldFilter() {
		
		@Override
		public boolean acceptChar(TextField textField, char c) {
			
			switch(c){
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					return true;
			}
			
			return false;
		}
	};
	
	public final TextFieldFilter dateFilter = new TextFieldFilter() {
		
		@Override
		public boolean acceptChar(TextField textField, char c) {
			
			switch(c){
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case '/':
					return true;
			}
			
			return false;
		}
	};
	
	public final TextFieldFilter phoneFilter = new TextFieldFilter() {
		
		@Override
		public boolean acceptChar(TextField textField, char c) {
			
			switch(c){
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case '(':
				case ')':
				case '-':
					return true;
			}
			
			return false;
		}
	};
}
