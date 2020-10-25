package ec.gob.firmadigital.servicio.cliente.servicio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import ec.gob.firmadigital.servicio.cliente.modelo.DTOParametroFirma;
import io.rubrica.certificate.CertUtils;
import io.rubrica.exceptions.RubricaException;
import io.rubrica.keystore.Alias;
import io.rubrica.keystore.KeyStoreUtilities;
import io.rubrica.sign.SignConstants;
import io.rubrica.sign.Signer;
import io.rubrica.sign.word.WordSigner;

@Service("servicioTotalVentaImpl")
@SessionAttributes("user")
public class ServicioFirma {

	public String firmar(DTOParametroFirma parametroFirma) throws RubricaException {
		try {
			byte[] certificado = decode(parametroFirma.getCertficado());
			InputStream input = new ByteArrayInputStream(certificado);
			KeyStore keyStore = crearStore(input, parametroFirma.getPassowrd());
			Signer signer = new WordSigner();
			PrivateKey key = null;
			String alias = CertUtils.seleccionarAlias(keyStore);

			Properties params = new Properties();
			params.setProperty("format", SignConstants.SIGN_FORMAT_OOXML);
			params.setProperty("signatureReason", "Comentario : Razon de firma");
			key = (PrivateKey) keyStore.getKey(alias, parametroFirma.getPassowrd().toCharArray());
			Certificate[] certChain = keyStore.getCertificateChain(alias);
			byte[] archivo = decode(parametroFirma.getDocumento());
			byte[] archivoFirmado = signer.sign(archivo, SignConstants.SIGN_ALGORITHM_SHA1WITHRSA, key, certChain,
					params);
			return encode(archivoFirmado);

		} catch (UnrecoverableKeyException e) {
			throw new RubricaException(e.getMessage());
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			throw new RubricaException(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			throw new RubricaException(e.getMessage());
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			throw new RubricaException(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RubricaException(e.getMessage());
		} catch (RubricaException e) {
			// TODO Auto-generated catch block
			throw new RubricaException(e.getMessage());
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			throw new RubricaException(e.getMessage());
		}

	}
	
	private KeyStore crearStore(InputStream certficado, String password)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore keyStore = KeyStore.getInstance("pkcs12");
		keyStore.load(certficado, password.toCharArray());
		return keyStore;
	}

	public static String seleccionarAlias(KeyStore keyStore) throws RubricaException {
		String aliasString = null;
		// Con que certificado firmar?
		List<Alias> signingAliases = KeyStoreUtilities.getSigningAliases(keyStore);

		if (signingAliases.isEmpty()) {
			throw new RubricaException("No se encuentran certificados para firmar\nPuede estar caducado o revocado");
		}

		if (signingAliases.size() == 1) {
			aliasString = signingAliases.get(0).getAlias();
		} else {
			Alias alias = (Alias) JOptionPane.showInputDialog(null, "Escoja...", "Certificado para firmar",
					JOptionPane.QUESTION_MESSAGE, null, signingAliases.toArray(), signingAliases.get(0));
			if (alias != null) {
				aliasString = alias.getAlias();
			}
		}
		return aliasString;
	}

	public String encode(byte[] bytes) throws DecoderException {
		return Hex.encodeHexString(bytes);
	}

	public byte[] decode(String hexString) throws DecoderException {
		return Hex.decodeHex(hexString);
	}

}
