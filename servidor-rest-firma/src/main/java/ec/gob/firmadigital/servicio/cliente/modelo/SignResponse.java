package ec.gob.firmadigital.servicio.cliente.modelo;

/**
 * @author Edison Lomas Almeida @ elomas@appshandler.com
 */
public class SignResponse {

	public static final String CODE_ERROR = "999";
	public static final String CODE_OK = "000";

	private String document;
	private String message;
	private String code;

	public SignResponse() {

	}

	public SignResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public SignResponse(String document) {
		this.document = document;
		this.code = CODE_OK;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
