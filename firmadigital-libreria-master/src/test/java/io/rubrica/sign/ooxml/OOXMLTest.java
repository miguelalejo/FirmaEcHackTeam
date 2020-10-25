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
package io.rubrica.sign.ooxml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Properties;

import org.junit.Ignore;

import io.rubrica.sign.SignConstants;
import io.rubrica.sign.SignInfo;
import io.rubrica.sign.Signer;
import io.rubrica.sign.TestHelper;
import io.rubrica.utils.Utils;

public class OOXMLTest {

    private static final String DATA_FILE = "prueba.docx";

    @Ignore
    public void testOdfsignature() throws Exception {
        byte[] ooxml = Utils.getDataFromInputStream(ClassLoader.getSystemResourceAsStream(DATA_FILE));
        File tempFile = File.createTempFile("ooxmlSign", "." + DATA_FILE);
        System.out.println("Temporal para comprobacion manual: " + tempFile.getAbsolutePath());

        KeyPair kp = TestHelper.createKeyPair();
        Certificate[] chain = TestHelper.createCertificate(kp);

        Properties p1 = new Properties();
        p1.setProperty("format", SignConstants.SIGN_FORMAT_OOXML);
        p1.setProperty("signatureReason", "Comentario : Razon de firma");
        p1.setProperty("commitmentTypeIndications", "1");
        p1.setProperty("commitmentTypeIndication0Identifier", "1");
        p1.setProperty("commitmentTypeIndication0Description", "Cre\u00F3 y aprob\u00F3 este documento");
        p1.setProperty("commitmentTypeIndication0CommitmentTypeQualifiers", "RAZON-PRUEBA");

        try (FileOutputStream fos = new FileOutputStream(tempFile);) {
            Signer signer = new OOXMLSigner();
            byte[] result = signer.sign(ooxml, SignConstants.SIGN_ALGORITHM_SHA1WITHRSA, kp.getPrivate(), chain, p1);

            fos.write(result);
            fos.flush();
            assertNotNull(result);

            List<SignInfo> firmantes = signer.getSigners(result);
            X509Certificate[] certs = firmantes.get(0).getCerts();
            assertTrue(((X509Certificate) chain[0]).getSerialNumber().equals(certs[0].getSerialNumber()));
        }
    }
}
