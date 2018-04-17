package br.com.tapananuca.gereacademia;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import br.com.tapananuca.gereacademia.comunicacao.JsonSerializer;
import br.com.tapananuca.gereacademia.telas.Tela;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.ObjectMap;

public class Utils {
	
	private static Utils utils;

	public static final String WEB_APP_END_POINT = "web.app.endpoint";
	
	public static final String URL_LOGIN = "/login";
	public static final String URL_LOGOUT = URL_LOGIN + "/out";
	public static final String URL_TROCA_SENHA = URL_LOGIN + "/trsenha";
	
	public static final String URL_PESSOA = "/pessoa";
	public static final String URL_PESSOA_ANIVERSARIOS = URL_PESSOA + "/aniversarios";
	public static final String URL_PESSOA_DADOS_NOMES = URL_PESSOA + "/nomes";
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
	public static final String URL_PESSOA_MEDIDAS_NOVA_DATA = URL_PESSOA_MEDIDAS + "/novadata";
	public static final String URL_PESSOA_MEDIDAS_GERAR_RELATORIO = URL_PESSOA_MEDIDAS + "/calcular";
	public static final String URL_PESSOA_MEDIDAS_ENVIAR_RELATORIO = URL_PESSOA_MEDIDAS + "/enviar";
	public static final String URL_CADASTRADOS = URL_PESSOA + "/cadastrados";
	
	public static final String URL_PERSONAL = "/personal";
	public static final String URL_PERSONAL_DATAS = URL_PERSONAL + "/datas";
	public static final String URL_PERSONAL_ADD_AULA = URL_PERSONAL + "/addAula";
	public static final String URL_PERSONAL_GERAR_RELATORIO = URL_PERSONAL + "/calcular";
	public static final String URL_PERSONAL_ABRIR_RELATORIO = URL_PERSONAL + "/abrir";
	public static final String URL_PERSONAL_ENVIAR_RELATORIO = URL_PERSONAL + "/enviar";
	public static final String URL_PERSONAL_KEY_RELATORIO = "rel";
	public static final String URL_PERSONAL_DATAS_AULAS = URL_PERSONAL + "/datasaulas";
	
	public static final String URL_PAGAMENTOS = "/pagamentos";
	public static final String URL_A_RECEBER = URL_PAGAMENTOS + "/receber";
	public static final String URL_PAGAR = URL_PAGAMENTOS + "/pagar";
	
	
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
	
	public String getPropertie(String key){
		
		return properties.get(key);
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
	
	public <T extends JsonSerializer> T fromJson(T t, String jsonString){
		
		if (jsonString.isEmpty()){
			
			return t;
		}
		
		return t.fromJson(t, jsonString.replace("\n", "<br>"));
	}
	
	public String toJson(JsonSerializer jsonSerializer){
		
		return jsonSerializer.toJson();
	}
	
	public String getValorFromJsonString(String campo, String jsonString){
		
		if (campo == null || jsonString == null){
			
			return null;
		}
		
		if (jsonString.contains(campo+":")){
			
			int index = jsonString.indexOf(campo) + campo.length();
			
			String valorAux = jsonString.substring(index, jsonString.length());
			
			valorAux = valorAux.trim();
			
			valorAux = valorAux.replaceFirst(":", "").trim();
			
			jsonString = jsonString.replaceFirst(":", "");
			
			int indexFimValor = 0;
			
			if (valorAux.startsWith("\"")){
				
				index++;
				//termina em "
				indexFimValor = jsonString.indexOf("\"", index);
				
//				if (indexFimValor == -1){
//					
//					//termina no fim do json
//					indexFimValor = jsonString.indexOf("}", index);
//				}
			} else {
				
				//termina em virgula
				indexFimValor = jsonString.indexOf(",", index);
				
				if (indexFimValor == -1 || (jsonString.indexOf("}", index) != - 1 &&
						jsonString.indexOf("}", index) < indexFimValor)){
					
					//termina no fim do json
					indexFimValor = jsonString.indexOf("}", index);
				}
			}
			
			final String valorfinal = 
				jsonString.substring(index, (indexFimValor == -1 ? jsonString.length() : indexFimValor));
			
			return valorfinal.isEmpty() ? null : valorfinal.replace("]", "").replace("[", "");
		}
		
		return null;
	}
	
	public String replaceFirst(String src, String search, String replace){
		
		int index = src.indexOf(search);
		
		if (index == -1){
			
			return src;
		}
		
		return src.substring(0, index) + replace + src.substring(index + search.length(), src.length());
	}
	
	public HttpRequest criarRequest(String url, JsonSerializer parametros){
		
		final HttpRequest request = new HttpRequest(HttpMethods.POST);
		request.setUrl(properties.get(WEB_APP_END_POINT) + url);
		request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        
        //só seta cookie de sessão se já estiver logado e se não for versão web
        //se for web não precisa, browser da um jeito
        if (sessionId != null && Gdx.app.getType() != ApplicationType.WebGL){
        	request.setHeader("Cookie", "JSESSIONID=" + utils.sessionId);
        }
        
        if (parametros != null){
        
			request.setContent(utils.toJson(parametros));
        }
		
		return request;
	}
	
	public void enviarRequest(final HttpRequest request, final Stage stage, 
			final Skin skin, final HttpResponseListener callback){
		
		final Window telaBloqueio = new Window("", skin);
		telaBloqueio.setHeight(Tela.height);
		telaBloqueio.setWidth(Tela.width);
		telaBloqueio.setColor(telaBloqueio.getColor().r, telaBloqueio.getColor().g, telaBloqueio.getColor().b, 0.7f);
		telaBloqueio.add("Aguarde...").center();
		stage.addActor(telaBloqueio);
		
		Gdx.net.sendHttpRequest(request, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				
				try {
					
					callback.handleHttpResponse(httpResponse);
				} finally {
					
					telaBloqueio.addAction(Actions.sequence(Actions.fadeOut(0.4f, Interpolation.fade), Actions.removeActor()));
				}
			}
			
			@Override
			public void failed(Throwable t) {
				
				try {
					
					callback.failed(t);
				} finally {
					
					telaBloqueio.addAction(Actions.sequence(Actions.fadeOut(0.4f, Interpolation.fade), Actions.removeActor()));
				}
			}
			
			@Override
			public void cancelled() {
				
				try {
					
					callback.cancelled();
				} finally {
					
					telaBloqueio.addAction(Actions.sequence(Actions.fadeOut(0.4f, Interpolation.fade), Actions.removeActor()));
				}
			}
		});
	}

	public void setSessionId(String sessionId) {
		
		utils.sessionId = sessionId;
	}
	
	public String formatCurrency(BigDecimal valor){
		
		if (valor == null){
			
			return "0,00";
		}
		
		valor = valor.setScale(2, RoundingMode.HALF_UP);
		
		return valor.toString().replace(",", "").replace(".", ",");
	}
	
	public String formatCurrency(String valor){
		
		if (valor == null || valor.isEmpty()){
			
			return "0,00";
		}
		
		valor = valor.replace(",", "").replace(".", ",");
		
		if (valor.contains(",")){
			
			final String decimal = valor.split(",")[1];
			
			if (decimal.length() < 2){
				
				valor += "0";
			}
		} else {
			
			valor += ",00";
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
		window.setModal(true);
		
		final Table table = new Table(skin);
		final ScrollPane scroll = new ScrollPane(table, skin);
		table.add(msg);
		
		window.add(scroll).width(500).height(300).row();
		TextButton ok = new TextButton("Ok", skin);
		ok.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				window.addAction(Actions.sequence(Actions.fadeOut(0.4f, Interpolation.fade), Actions.removeActor()));
			}
		});
		window.add(ok);
		
		window.pack();
		window.setWidth(Tela.width);
		
		window.setPosition(
				(int)((Tela.width / 2) - (window.getWidth() / 2)) ,
				(int)((Tela.height / 2) - (window.getHeight() / 2)));
		
		stage.addActor(window);
	}
	
	public void addStringToJson(StringBuilder json, String nomeCampo, String valor){
		
		if (json == null){
			
			return;
		}
		
		if (valor != null && !valor.isEmpty()){
			
			final boolean hasWhiteSpace = valor.contains(" ");
			
			json.append(json.length() == 1 ? "" : ",")
				.append(nomeCampo)
				.append(":");
			
			if (hasWhiteSpace){
				
				json.append("\"");
			}
			
			json.append(valor);
			
			if (hasWhiteSpace){
				
				json.append("\"");
			}
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
	
	public void addBooleanToJson(StringBuilder json, String nomeCampo, Boolean valor){
		
		if (json == null){
			
			return;
		}
		
		if (valor != null && valor){
			
			json.append(json.length() == 1 ? "" : ",")
				.append(nomeCampo)
				.append(":")
				.append(valor);
		}
	}
	
	
	public void addJsonSerializerToJson(StringBuilder json, String nomeCampo, JsonSerializer valor){
		
		if (json == null){
			
			return;
		}
		
		if (valor != null){
			
			json.append(json.length() == 1 ? "" : ",")
				.append(nomeCampo)
				.append(":");
			
			json.append(valor.toJson());
		}
	}
	public <T extends JsonSerializer> void addGARequestCollectionToJson(StringBuilder json, String nomeCampo, List<T> collection){
		
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
	
	public <T extends Object> void addCollectionToJson(StringBuilder json, String nomeCampo, List<T> collection){
		
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
	
	public String emptyOrString(Object object){
		
		if (object == null){
			
			return "";
		}
		
		return object.toString();
	}
	
	public boolean valueOfFalse(String src){
		
		if (src == null){
			
			return false;
		}
		
		return Boolean.valueOf(src);
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
