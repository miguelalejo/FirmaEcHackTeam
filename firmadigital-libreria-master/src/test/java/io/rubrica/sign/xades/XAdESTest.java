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
package io.rubrica.sign.xades;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

import org.junit.Test;

import io.rubrica.sign.SignInfo;
import io.rubrica.sign.TestHelper;

public class XAdESTest {

    @Test
    public void firmarXml() throws Exception {
        File tempFile = File.createTempFile("test", ".xml");
        System.out.println("Temporal para comprobacion manual: " + tempFile.getAbsolutePath());

        KeyPair kp = TestHelper.createKeyPair();
        Certificate[] chain = TestHelper.createCertificate(kp);
        byte[] xml = "<documento><parrafo>Hola mundo</parrafo></documento>".getBytes();

        try (FileOutputStream fos = new FileOutputStream(tempFile);) {
            XAdESSigner signer = new XAdESSigner();
            byte[] result = signer.sign(xml, "SHA1withRSA", kp.getPrivate(), chain, null);

            assertNotNull(result);
            fos.write(result);
            fos.flush();

            List<SignInfo> firmantes = signer.getSigners(result);
            X509Certificate[] certs = firmantes.get(0).getCerts();
            assertTrue(((X509Certificate) chain[0]).getSerialNumber().equals(certs[0].getSerialNumber()));
        }
    }
}
