/* 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.rubrica.utils;

import io.rubrica.exceptions.CRLValidationException;
import io.rubrica.exceptions.CertificadoInvalidoException;
import io.rubrica.exceptions.ConexionValidarCRLException;
import io.rubrica.certificate.CertEcUtils;
import io.rubrica.certificate.CrlUtils;
import io.rubrica.certificate.ValidationResult;
import io.rubrica.exceptions.RubricaException;
import io.rubrica.crl.ServicioCRL;
import io.rubrica.exceptions.ConexionApiException;
import io.rubrica.exceptions.EntidadCertificadoraNoValidaException;

import io.rubrica.ocsp.ValidadorOCSP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jdc
 */
public class UtilsCrlOcsp {

    private static final int TIME_OUT = 5000; //set timeout to 5 seconds
    private static final Logger LOGGER = Logger.getLogger(UtilsCrlOcsp.class.getName());

    public UtilsCrlOcsp() {
    }

    /**
     * Valida primero por OSCP, si falla lo hace por CRL
     *
     * @param cert
     * @return X509Certificate
     * @throws IOException
     * @throws RubricaException si hay un error de conexion con el CRL bota
     * esto, si es por OCSP y falla la conexion intenta por CRL
     * @throws io.rubrica.exceptions.CRLValidationException
     * @throws io.rubrica.exceptions.EntidadCertificadoraNoValidaException
     * @throws io.rubrica.exceptions.ConexionValidarCRLException
     */
    public static String validarCertificado(X509Certificate cert) throws EntidadCertificadoraNoValidaException, IOException, RubricaException, ConexionValidarCRLException, CRLValidationException {
        String fechaRevocado = null;
        try {
            BigInteger serial = cert.getSerialNumber();
            fechaRevocado = validarCrlServidorAPI(serial);
            if (fechaRevocado != null) {
                System.out.println("Fallo la validacion por el servicio del API, Ahora intentamos por OCSP");
                fechaRevocado = validarOCSP(cert);
                if (fechaRevocado.equals("unknownStatus")) {
                    System.out.println("Fallo la validacion por OCSP, Ahora intentamos por CRL");
                    fechaRevocado = validarCRL(cert);
                }
            }
        } catch (IOException | ConexionApiException ex) {
            System.out.println("Fallo la validacion por el servicio del API, Ahora intentamos por OCSP");
            try {
                fechaRevocado = validarOCSP(cert);
                if (fechaRevocado.equals("unknownStatus")) {
                    System.out.println("Fallo la validacion por OCSP, Ahora intentamos por CRL");
                    fechaRevocado = validarCRL(cert);
                }
            } catch (IOException | RubricaException ex1) {
                System.out.println("Fallo la validacion por OCSP, Ahora intentamos por CRL");
                fechaRevocado = validarCRL(cert);
            }
        }
        return fechaRevocado;
    }

    public static Date fechaString_Date(String fecha) throws ParseException {
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if (fecha != null) {
            date = (Date) formato.parse(fecha);
        }
        return date;
    }

    public static Date validarFechaRevocado(X509Certificate cert) throws CertificadoInvalidoException, IOException {
        Date fechaRevocado = null;
        try {
            fechaRevocado = fechaString_Date(validarCertificado(cert));
        } catch (ParseException | RubricaException | ConexionValidarCRLException | CRLValidationException | EntidadCertificadoraNoValidaException ex) {
            LOGGER.getLogger(UtilsCrlOcsp.class.getName()).log(Level.SEVERE, null, ex);
//            throw new ConexionFirmadorApiException("Fallo la validacion por el servicio del API");
        }
        return fechaRevocado;
    }

    private static String validarOCSP(X509Certificate cert) throws IOException, RubricaException, EntidadCertificadoraNoValidaException {
        List<String> ocspUrls = CertificateUtils.getAuthorityInformationAccess(cert);
        ocspUrls.forEach((ocsp) -> {
            System.out.println("OCSP=" + ocsp);
        });

        X509Certificate certRoot = CertEcUtils.getRootCertificate(cert);
        return ValidadorOCSP.ValidarOCSP(cert, certRoot, ocspUrls.get(0));
//        ValidateCertUseOCSP.validadorOCSP(cert, certRoot, ocspUrls.get(0));
    }

    private static String validarCRL(X509Certificate cert) throws IOException, EntidadCertificadoraNoValidaException, RubricaException, ConexionValidarCRLException, CRLValidationException {
        String nombreCA = CertEcUtils.getNombreCA(cert);

        String urlCrl = obtenerUrlCRL(CertificateUtils.getCrlDistributionPoints(cert));
        ValidationResult result = null;

        if (nombreCA.toLowerCase().equals("banco central del ecuador")) {
            urlCrl = ServicioCRL.BCE_CRL;
        }
        if (nombreCA.toLowerCase().equals("security data")) {
            urlCrl = ServicioCRL.SD_CRL;
        }
        if (nombreCA.toLowerCase().equals("consejo de la judicatura")) {
            urlCrl = ServicioCRL.CJ_CRL;
        }
        if (nombreCA.toLowerCase().equals("anf ac")) {
            urlCrl = ServicioCRL.ANFAC_CRL;
        }

        X509Certificate root = CertEcUtils.getRootCertificate(cert);
        CrlUtils crlUtils = new CrlUtils();
        result = crlUtils.verifyCertificateCRLs(cert, root.getPublicKey(),
                Arrays.asList(urlCrl));
        if (result == result.CANNOT_DOWNLOAD_CRL) {
            throw new ConexionValidarCRLException("No se puede validar contra la lista de revocación:" + urlCrl);
        }

        // Si el certificado no es valido lanzamos exception
        if (!result.isValid()) {
            throw new CRLValidationException("Certificado Inválido");
        }
        return crlUtils.getRevocationDate();
    }

    private static String validarCrlServidorAPI(BigInteger serial) throws IOException, ConexionApiException {
        String certificado_revocado_url = PropertiesUtils.getConfig().getProperty("certificado_revocado_url");
        System.out.println("certificado_revocado_url: " + certificado_revocado_url);
        if (!certificado_revocado_url.isEmpty()) {
            URL url = new URL(certificado_revocado_url + "/" + serial);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();

            if (responseCode >= 300 && responseCode < 400) {
                urlConnection = (HttpURLConnection) new URL(urlConnection.getHeaderField("Location")).openConnection();
                urlConnection.setConnectTimeout(TIME_OUT);
                responseCode = urlConnection.getResponseCode();
            }
            if (responseCode >= 400) {
                LOGGER.severe(certificado_revocado_url + "/" + serial + ": Response Code: " + responseCode);
                throw new ConexionApiException("No se pudo conectar API. " + certificado_revocado_url + " Response Code: " + responseCode);
            }

            try (InputStream is = urlConnection.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(reader);
                return in.readLine();
            }
        } else {
            return null;
        }
    }

    private static String obtenerUrlCRL(List<String> urls) {
        for (String url : urls) {
            if (url.toLowerCase().contains("crl")) {
                return url;
            }
        }
        return null;
    }

    private static String resultadosCRL(ValidationResult result) {
        if (result == result.CANNOT_DOWNLOAD_CRL) {
            return "No se pudo descargar el archivo CRL\nRevisar conexión de Internet";
        }
        if (result.isValid()) {
            return "Válido";
        }
        return "Inválido";
    }
}
