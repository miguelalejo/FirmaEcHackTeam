package ec.gob.firmadigital.servicio.cliente.utils;

/**
 * @author Edison Lomas Almeida @ elomas@appshandler.com
 */
public class Utils {

	public static final String SUFIX = "_signed";

	public static String buildName(String name) {
		if (name != null && !name.isEmpty()) {
			String[] namePart = name.split("\\.");
			return String.format("%s%s.%s", namePart[0], SUFIX, namePart[1]);
		}
		return String.format("%s.docx", SUFIX);
	}

}
