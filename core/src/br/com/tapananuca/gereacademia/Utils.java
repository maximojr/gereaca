package br.com.tapananuca.gereacademia;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import br.com.tapananuca.gereacademia.comunicacao.JsonSerializer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.ObjectMap;

public class Utils {
	
	private static Utils utils;

	public static final String WEB_APP_END_POINT = "web.app.endpoint";
	
	public static final String URL_LOGIN = "/login";
	public static final String URL_PESSOA = "/pessoa";
	public static final String URL_PESSOA_A_RECEBER = URL_PESSOA + "/receber";
	public static final String URL_PESSOA_PAGAR = URL_PESSOA + "/pagar";
	public static final String URL_PESSOA_ANIVERSARIOS = URL_PESSOA + "/aniversarios";
	public static final String URL_PESSOA_DADOS_BASICOS = URL_PESSOA + "/basicos";
	public static final String URL_PESSOA_DADOS_BASICOS_SALVAR = URL_PESSOA_DADOS_BASICOS + "/salvar";
	public static final String URL_PESSOA_OBJETIVOS = URL_PESSOA + "/objetivo";
	public static final String URL_PESSOA_OBJETIVOS_SALVAR = URL_PESSOA_OBJETIVOS + "/salvar";
	public static final String URL_PESSOA_HIST_PAT = URL_PESSOA + "/histpat";
	public static final String URL_PESSOA_HIST_PAT_SALVAR = URL_PESSOA_HIST_PAT + "/salvar";
	public static final String URL_PESSOA_HABITOS = URL_PESSOA + "/habito";
	public static final String URL_PESSOA_HABITO_SALVAR = URL_PESSOA_HABITOS + "/salvar";
	public static final String URL_PESSOA_MEDIDAS = URL_PESSOA + "/medida";
	public static final String URL_PESSOA_MEDIDAS_SALVAR = URL_PESSOA_MEDIDAS + "/salvar";
	
	private final Json json;
	
	private String sessionId;
	
	private static ObjectMap<String, String> properties;
	
	private Utils(){
		
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		json.setIgnoreUnknownFields(true);
		json.setOutputType(OutputType.json);
	}
	
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
	
	public HttpRequest criarRequest(String url, JsonSerializer parametros){
		
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
	
	public String formatCurrency(String valor){
		
		if (valor == null){
			
			return "0,00";
		}
		
		valor = valor.toString().replace(",", "").replace(".", ",");
		
		if (valor.endsWith(",0")){
			
			valor += "0";
		}
		
		return valor;
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
		
		final Table table = new Table(skin);
		final Label label = new Label(msg, skin);
		label.setWrap(true);
		
		table.add(label).width(Gdx.graphics.getWidth()).height(label.getPrefHeight());
		
		window.add(table);
		window.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				window.remove();
			}
		});
		//window.pack();
		window.setHeight(window.getPrefHeight());
		window.setPosition(
				(Gdx.graphics.getWidth() / 2) - (window.getWidth() / 2) ,
				(Gdx.graphics.getHeight() / 2) - (window.getHeight() / 2));
		
		stage.addActor(window);
	}
	
	public void addStringToJson(StringBuilder json, String nomeCampo, String valor){
		
		if (json == null){
			
			return;
		}
		
		if (valor != null && !valor.isEmpty()){
			
			json.append(json.length() == 1 ? "" : ",")
				.append(nomeCampo)
				.append(":")
				.append("\"")
				.append(valor)
				.append("\"");
		}
	}
	
	public void addNumberToJson(StringBuilder json, String nomeCampo, Number valor){
		
		if (json == null){
			
			return;
		}
		
		if (valor != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append(nomeCampo)
				.append(":")
				.append(valor);
		}
	}
	
	public <T extends JsonSerializer> void addGARequestCollectionToJson(StringBuilder json, String nomeCampo, Array<T> collection){
		
		json.append(nomeCampo)
			.append(":[");
		
		if (collection != null){
		
			final Iterator<T> ite = collection.iterator();
			
			while (ite.hasNext()){
				
				json.append(ite.next().toJson());
				
				if (ite.hasNext()){
					
					json.append(",");
				}
			}
		}
		
		json.append("]");
	}
	
	public <T extends Object> void addCollectionToJson(StringBuilder json, String nomeCampo, Array<T> collection){
		
		json.append(nomeCampo)
			.append(":[");
		
		if (collection != null){
		
			final Iterator<T> ite = collection.iterator();
			
			while (ite.hasNext()){
				
				json.append(ite.next().toString());
				
				if (ite.hasNext()){
					
					json.append(",");
				}
			}
		}
		
		json.append("]");
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
