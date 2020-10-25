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
package io.rubrica.sign.excel;

import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import io.rubrica.sign.SignConstants;
import io.rubrica.sign.SignInfo;
import io.rubrica.sign.Signer;
import io.rubrica.sign.TestHelper;
import io.rubrica.sign.TestManejadorDocumento;
import io.rubrica.sign.word.WordSigner;

public class ExcelSignerTest {

    @Test
    public void testSignWord() throws Exception {
        File tempFile = File.createTempFile("excel", "." + "test1.xlsx");
        System.out.println("Temporal para comprobacion manual: " + tempFile.getAbsolutePath());

        File tempFile2 = File.createTempFile("excel", "." + "test2.xlsx");
        System.out.println("Temporal2 para comprobacion manual: " + tempFile2.getAbsolutePath());

        KeyPair kp = TestHelper.createKeyPair();
        Certificate[] chain = TestHelper.createCertificate(kp);

        byte[] archivoWord = readFileToByteArray(TestManejadorDocumento.obtenerArchivo("prueba.xlsx"));
        
        
        Properties params = new Properties();
        params.setProperty("format", SignConstants.SIGN_FORMAT_OOXML);
        params.setProperty("signatureReason", "Comentario : Razon de firma");

        byte[] result;

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            Signer signer = new ExcelSigner();
            result = signer.sign(archivoWord, SignConstants.SIGN_ALGORITHM_SHA1WITHRSA, kp.getPrivate(), chain, params);

            assertNotNull(result);
            fos.write(result);
            fos.flush();

            List<SignInfo> firmantes = signer.getSigners(result);
            X509Certificate[] certs = firmantes.get(0).getCerts();
            assertTrue(((X509Certificate) chain[0]).getSerialNumber().equals(certs[0].getSerialNumber()));
        }

        try (FileOutputStream fos = new FileOutputStream(tempFile2)) {
            Signer signer = new WordSigner();
            byte[] result2 = signer.sign(result, SignConstants.SIGN_ALGORITHM_SHA1WITHRSA, kp.getPrivate(), chain,
                    params);

            assertNotNull(result2);
            fos.write(result2);
            fos.flush();

            List<SignInfo> firmantes = signer.getSigners(result2);
            X509Certificate[] certs = firmantes.get(0).getCerts();
            assertTrue(((X509Certificate) chain[0]).getSerialNumber().equals(certs[0].getSerialNumber()));
        }
    }
}
