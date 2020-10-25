package ec.gob.firmadigital.servicio.cliente.store;

import java.util.HashMap;

import ec.gob.firmadigital.servicio.cliente.modelo.SignedDocument;

public class SignedSingleton {

	private static SignedSingleton instance;

	public static SignedSingleton getInstance() {
		if (instance == null) {
			instance = new SignedSingleton();
		}
		return instance;

	}

	private HashMap<String, SignedDocument> signedMap;

	private SignedSingleton() {
		this.signedMap = new HashMap<String, SignedDocument>();
	}

	public void putSigned(SignedDocument signedDocument) {
		signedMap.put(signedDocument.getUuid(), signedDocument);
	}

	public SignedDocument get(String uud) {
		return signedMap.get(uud);
	}

}
