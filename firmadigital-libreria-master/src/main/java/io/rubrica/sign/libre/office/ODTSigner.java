/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.rubrica.sign.libre.office;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X500Name;

import com.lowagie.text.exceptions.BadPasswordException;

import io.rubrica.exceptions.RubricaException;
import io.rubrica.sign.SignInfo;
import io.rubrica.sign.Signer;

public class ODTSigner implements Signer {

	private static final Logger logger = Logger.getLogger(ODTSigner.class.getName());

	// ETSI TS 102 778-1 V1.1.1 (2009-07)
	// PAdES Basic - Profile based on ISO 32000-1
	/**
	 * Algoritmos soportados:
	 *
	 * <li><i>SHA1withRSA</i></li>
	 * <li><i>SHA256withRSA</i></li>
	 * <li><i>SHA384withRSA</i></li>
	 * <li><i>SHA512withRSA</i></li>
	 *
	 * @param xParams
	 * @throws io.rubrica.exceptions.RubricaException
	 * @throws java.io.IOException
	 * @throws com.lowagie.text.exceptions.BadPasswordException
	 */
	@Override
	public byte[] sign(byte[] data, String algorithm, PrivateKey key, Certificate[] certChain, Properties xParams)
			throws RubricaException, IOException, BadPasswordException {
		X509Certificate c = (X509Certificate) certChain[0];

		// Data to sign
		byte[] dataToSign = "SigmaWorld".getBytes();
		// compute signature:
		Signature signature;
		try {
			signature = Signature.getInstance("Sha1WithRSA");

			signature.initSign(key);
			signature.update(dataToSign);
			byte[] signedData = signature.sign();

			// load X500Name
			X500Name xName = X500Name.asX500Name(c.getSubjectX500Principal());
			// load serial number
			BigInteger serial = c.getSerialNumber();
			// laod digest algorithm
			AlgorithmId digestAlgorithmId = new AlgorithmId(AlgorithmId.SHA_oid);
			// load signing algorithm
			AlgorithmId signAlgorithmId = new AlgorithmId(AlgorithmId.RSAEncryption_oid);

			// Create SignerInfo:
			SignerInfo sInfo = new SignerInfo(xName, serial, digestAlgorithmId, signAlgorithmId, signedData);
			// Create ContentInfo:
			ContentInfo cInfo = new ContentInfo(ContentInfo.DIGESTED_DATA_OID,
					new DerValue(DerValue.tag_OctetString, dataToSign));
			// Create PKCS7 Signed data
			PKCS7 p7 = new PKCS7(new AlgorithmId[] { digestAlgorithmId }, cInfo,
					new java.security.cert.X509Certificate[] { c }, new SignerInfo[] { sInfo });
			// Write PKCS7 to bYteArray
			ByteArrayOutputStream bOut = new DerOutputStream();
			p7.encodeSignedData(bOut);
			return signedData;
		} catch (NoSuchAlgorithmException e) {
			throw new RubricaException(e);
		} catch (InvalidKeyException e) {
			throw new RubricaException(e);
		} catch (SignatureException e) {
			throw new RubricaException(e);
		}
	}

	@Override
	public List<SignInfo> getSigners(byte[] sign) throws io.rubrica.exceptions.InvalidFormatException, IOException {
		return null;
	}

}
