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
package io.rubrica.sign.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.logging.Logger;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.TSAClient;


/**
 * Clase para firmar documentos PDF usando la libreria iText.
 *
 * @author Ricardo Arguello <ricardo.arguello@soportelibre.com>
 * @deprecated
 */
public class FirmaPDF {

    private static final float POSICION_FIRMA_Y_DEFECTO = 0.90736342f;
    private static final float POSICION_FIRMA_X_DEFECTO = 0.151260504f;
    private static final Logger logger = Logger.getLogger(FirmaPDF.class.getName());

    public static byte[] firmar(byte[] pdf, PrivateKey pk, Certificate[] chain, TSAClient tsaClient)
            throws IOException {
        return firmar(pdf, pk, chain, tsaClient, 1, POSICION_FIRMA_X_DEFECTO, POSICION_FIRMA_Y_DEFECTO);
    }

    public static byte[] firmar(byte[] pdf, PrivateKey pk, Certificate[] chain, TSAClient tsaClient, int pagina,
            float posicionUnitariaX, float posicionUnitariaY) throws IOException {
        try {
            // Creating the reader and the stamper
            PdfReader reader = new PdfReader(pdf);

            ByteArrayOutputStream signedPdf = new ByteArrayOutputStream();
            PdfStamper stamper = PdfStamper.createSignature(reader, signedPdf, '\0');

            Rectangle rectanguloPararFirmar = RectanguloParaFirmar.obtenerRectangulo(reader.getPageSize(pagina),
                    posicionUnitariaX, posicionUnitariaY);

            // Creating the appearance
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            appearance.setReason("Firma");
            appearance.setLocation("");
            // appearance.setVisibleSignature(rectanguloPararFirmar,
            // pagina,"sig");

            // Creating the signature
            // PrivateKeySignature pks = new PrivateKeySignature(pk,
            // DigestAlgorithms.SHA1, null);
            // OcspClient ocsp = new OcspClientBouncyCastle();
            // MakeSignature.signDetached(appearance, pks, chain, null, ocsp,
            // tsaClient,
            // BouncyCastleProvider.PROVIDER_NAME, 0, MakeSignature.CMS);
            return signedPdf.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } // catch (GeneralSecurityException e) {
        // throw new RuntimeException(e);
        // }
    }
}
