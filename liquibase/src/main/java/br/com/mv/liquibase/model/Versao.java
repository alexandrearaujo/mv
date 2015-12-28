package br.com.mv.liquibase.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Versao {
	
	private static String ATUAL = null;
	
	public static String getVersaoAtual() {
		if (ATUAL == null) {
			Properties versaoAtual = new Properties();
			ClassLoader classLoader = Versao.class.getClassLoader();
			
			URL versaoFile = classLoader.getResource("config/versao.properties");
			InputStream in = null;
			try {
				if (versaoFile != null) {
					in = versaoFile.openStream();
					versaoAtual.load(in);
					String o = (String) versaoAtual.get("atual");
					
					if (o != null) {
						ATUAL = o;
					}
				}
			} catch (IOException e) {
				// This is not a fatal exception.
				// Build info will be returned as 'UNKNOWN'        }
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Log this error and remove the RuntimeException.
						throw new RuntimeException("Failed to close InputStream in LiquibaseUtil.", e);
					}
				}
			}
		} 

        return ATUAL;
    }
}