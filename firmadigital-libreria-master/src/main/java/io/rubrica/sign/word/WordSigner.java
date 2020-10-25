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
package io.rubrica.sign.word;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.poifs.crypt.dsig.SignatureConfig;
import org.apache.poi.poifs.crypt.dsig.SignatureInfo;
import org.apache.poi.poifs.crypt.dsig.SignaturePart;
import org.apache.poi.poifs.crypt.dsig.facets.EnvelopedSignatureFacet;
import org.apache.poi.poifs.crypt.dsig.facets.KeyInfoSignatureFacet;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

import com.lowagie.text.exceptions.BadPasswordException;

import io.rubrica.exceptions.RubricaException;
import io.rubrica.sign.SignInfo;
import io.rubrica.sign.Signer;

public class WordSigner implements Signer {

	private static final Logger logger = Logger.getLogger(WordSigner.class.getName());

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
		X509Certificate x509Certificate = (X509Certificate) certChain[0];
		SignatureConfig signatureConfig = new SignatureConfig();
		signatureConfig.setKey((PrivateKey) key);
		signatureConfig.setExecutionTime(new Date());
		signatureConfig.setDigestAlgo(HashAlgorithm.sha1);
		signatureConfig.addSignatureFacet(new EnvelopedSignatureFacet());
		signatureConfig.addSignatureFacet(new KeyInfoSignatureFacet());
		signatureConfig.setSigningCertificateChain(Collections.singletonList(x509Certificate));
		try {
			InputStream targetStream = new ByteArrayInputStream(data);
			OPCPackage pkg = OPCPackage.open(targetStream);
			signatureConfig.setOpcPackage(pkg);			
			SignatureInfo si = new SignatureInfo();
			si.setSignatureConfig(signatureConfig);
			si.confirmSignature();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			pkg.save(byteArrayOutputStream);
			for (SignaturePart sp : si.getSignatureParts()) {
				assert sp.validate();
			}
			assert si.verifySignature();
			pkg.close();
			return byteArrayOutputStream.toByteArray();
		} catch (InvalidOperationException e) {
			throw new RubricaException(e.getMessage());
		} catch (InvalidFormatException e) {
			throw new RubricaException(e.getMessage());
		} catch (XMLSignatureException e) {
			throw new RubricaException(e.getMessage());
		} catch (MarshalException e) {
			throw new RubricaException(e.getMessage());
		}		
	}

	@Override
	public List<SignInfo> getSigners(byte[] sign) throws io.rubrica.exceptions.InvalidFormatException, IOException {
		InputStream targetStream = new ByteArrayInputStream(sign);
		SignatureConfig signatureConfig = new SignatureConfig();
		OPCPackage pkg;
		try {
			pkg = OPCPackage.open(targetStream);
			signatureConfig.setOpcPackage(pkg);
			SignatureInfo si = new SignatureInfo();
			si.setSignatureConfig(signatureConfig);
			boolean isValid = si.verifySignature();
			List<SignInfo> signInfos = new ArrayList<>();
			logger.info("isValid " + isValid);
			for (SignaturePart sp : si.getSignatureParts()) {
				assert sp.validate();
				SignatureConfig config = si.getSignatureConfig();
				List<X509Certificate> list = sp.getCertChain();
				for (X509Certificate cc : list) {					
					logger.info("getSigAlgName " + cc.getSigAlgName());
					logger.info("getSigAlgOID " + cc.getSigAlgOID());
					logger.info("getNotAfter " + cc.getNotAfter());
					logger.info("getNotBefore " + cc.getNotBefore());
					logger.info("fechaFirma " + config.getExecutionTime());
				}				
				X509Certificate[] certChain = new X509Certificate[list.size()];
				certChain = list.toArray(certChain);				
				SignInfo signInfo = new SignInfo(certChain, config.getExecutionTime());
				for (int i = 0; i < certChain.length; i++) {
					certChain[i] = (X509Certificate) list.get(i);					
					PublicKey pub = certChain[i].getPublicKey();
					byte[] pubBytes = pub.getEncoded();
					SubjectPublicKeyInfo spkInfo = SubjectPublicKeyInfo.getInstance(pubBytes);
					ASN1Primitive primitive = spkInfo.parsePublicKey();
					byte[] publicKeyPKCS1 = primitive.getEncoded();
					signInfo.setPkcs1((byte[]) publicKeyPKCS1);
				}
				signInfos.add(signInfo);				
			}
			return signInfos;
		} catch (InvalidFormatException e) {
			throw new io.rubrica.exceptions.InvalidFormatException(e.getMessage());
		} catch (IOException e) {
			throw new io.rubrica.exceptions.InvalidFormatException(e.getMessage());
		}


	}

}
