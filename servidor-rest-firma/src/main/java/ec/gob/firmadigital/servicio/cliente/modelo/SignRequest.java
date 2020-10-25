package ec.gob.firmadigital.servicio.cliente.modelo;

/**
 * @author Edison Lomas Almeida @ elomas@appshandler.com
 */
public class SignRequest {

	private String name;
	private String document;
	private String key;
	private String pin;
	private String reason;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
