package ec.gob.firmadigital.servicio.cliente.modelo;

import java.util.Date;
import java.util.UUID;

/**
 * @author Edison Lomas Almeida @ elomas@appshandler.com
 */
public class SignedDocument {

	private String uuid;
	private String name;
	private String path;
	private long timestamp;

	public SignedDocument() {

	}

	public SignedDocument(String name, String directoryPath) {
		this.uuid = UUID.randomUUID().toString();
		this.name = name;
		this.path = String.format("%s/%s", directoryPath, this.uuid);
		this.timestamp = new Date().getTime();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
