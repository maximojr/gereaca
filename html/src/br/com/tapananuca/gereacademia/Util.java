package br.com.tapananuca.gereacademia;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.sendgrid.SendGrid;

public final class Util {

	public static void enviarEmailErro(Throwable t){
		
		final String user = System.getenv("SENDGRID_USERNAME");
		final String password = System.getenv("SENDGRID_PASSWORD");
		
		final SendGrid sendGrid = new SendGrid(user, password);
		
		final SendGrid.Email email = new SendGrid.Email();
		email.addTo("maximojr@gmail.com");
		email.setFrom("academia@ortobem.com.br");
		email.setSubject("Erro na aplicação Gereacademia");
		
		String textoEmail = "Erro: " + t.getMessage();
		
		try {
			
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			
			t.printStackTrace(pw);
			
			pw.close();
			sw.close();
			
			textoEmail += " Stacktrace: " + sw.toString();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		email.setText(textoEmail);
		
		try {
			
			sendGrid.send(email);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
