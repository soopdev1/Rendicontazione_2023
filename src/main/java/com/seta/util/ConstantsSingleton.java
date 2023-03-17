package com.seta.util;

import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public class ConstantsSingleton extends Properties {

	private static final long serialVersionUID = 3821072007855763508L;
	private static final Logger log = Logger.getLogger(ConstantsSingleton.class.getName());

	private ConstantsSingleton() {
		log.info("----- Init loading constants -----");
		loadConstants();
		log.info("----- End loading constants ------");
	}

	public static ConstantsSingleton getInstance() {
		return SingletonHelper.INSTANCE;
	}

	private static class SingletonHelper {
		private static final ConstantsSingleton INSTANCE = new ConstantsSingleton();
	}

	private void loadConstants() {
		try {
			URL urlConst = Thread.currentThread().getContextClassLoader().getResource("conf/const.properties");
			this.load(urlConst.openStream());

			// Print all properties
			this.keySet().stream().map(key -> key + ": " + this.getProperty(key.toString())).forEach(e -> log.info(e));

		} catch (java.io.IOException e) {
			log.severe("Errore durante l'inizializzazione del ConstantsSingleton. Impossibile avviare l'applicazione.");
		}
	}
}
