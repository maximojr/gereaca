package br.com.tapananuca.gereacademia.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import br.com.tapananuca.gereacademia.GereAcademia;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 900;
		new LwjglApplication(new GereAcademia(), config);
	}
}
