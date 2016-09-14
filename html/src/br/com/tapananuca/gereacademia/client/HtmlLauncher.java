package br.com.tapananuca.gereacademia.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import br.com.tapananuca.gereacademia.GereAcademia;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(800, 900);
        }

		@Override
		public ApplicationListener createApplicationListener() {
			return new GereAcademia();
		}
}