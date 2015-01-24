package cl.redhat.poc.ticket.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.jboss.logging.Logger;

public class CargarProperties {
	
	static Logger log = Logger.getLogger(CargarProperties.class);
	
	@SuppressWarnings("unused")
	public static Properties cargaPropiedades(Properties propiedad, String rutaArchivo) {

		try {
			String propFileName = System.getProperty("jboss.server.config.dir")
					+ rutaArchivo;
			propiedad = new Properties();

			File propFile = new File(propFileName);
			if (propFile.exists()) {
				InputStream inputStream = new FileInputStream(propFile);
				propiedad.load(inputStream);

				if (inputStream == null) {
					log.warn("Archivo Propiedad'" + propFileName
							+ "' classpath no encontrado");
				}
			} else
				log.warn("El archivo " + propFileName + " no existe!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return propiedad;
	}

}
