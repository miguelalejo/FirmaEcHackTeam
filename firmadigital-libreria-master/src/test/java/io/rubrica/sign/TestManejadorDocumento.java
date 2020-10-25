package io.rubrica.sign;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class TestManejadorDocumento {
	 public static File obtenerArchivo(String nombreArchivo) throws FileNotFoundException {
			URL url = Thread.currentThread().getContextClassLoader().getResource(nombreArchivo);
			if (url == null) {
				throw new FileNotFoundException("No existe.");
			}
			return new File(url.getFile());
		}
}
