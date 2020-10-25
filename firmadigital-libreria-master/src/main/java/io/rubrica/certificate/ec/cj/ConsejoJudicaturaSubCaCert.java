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
package io.rubrica.certificate.ec.cj;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

/**
 * Certificado intermedio de Consejo de la Judicatura, representado como un
 * objeto <code>X509Certificate</code>.
 *
 * @author mfernandez
 */
public class ConsejoJudicaturaSubCaCert extends X509Certificate {

    private X509Certificate certificate;

    public ConsejoJudicaturaSubCaCert() {
        super();

        StringBuilder cer = new StringBuilder();
        cer.append("-----BEGIN CERTIFICATE-----\n");
        cer.append("MIIHAzCCBOugAwIBAgIIVTHTgM5nqVQwDQYJKoZIhvcNAQELBQAwgboxCzAJBgNV\n");
        cer.append("BAYTAkVDMREwDwYDVQQHDAhETSBRVUlUTzEhMB8GA1UECgwYQ09OU0VKTyBERSBM\n");
        cer.append("QSBKVURJQ0FUVVJBMUQwQgYDVQQLDDtTVUJESVJFQ0NJT04gTkFDSU9OQUwgREUg\n");
        cer.append("U0VHVVJJREFEIERFIExBIElORk9STUFDSU9OIEROVElDUzEvMC0GA1UEAwwmSUNF\n");
        cer.append("UlQtRUMgRU5USURBRCBERSBDRVJUSUZJQ0FDSU9OIFJBSVowHhcNMTQxMDE2MTgz\n");
        cer.append("NDUyWhcNMzQxMDE1MTgzNDUyWjCBtTELMAkGA1UEBhMCRUMxETAPBgNVBAcMCERN\n");
        cer.append("IFFVSVRPMSEwHwYDVQQKDBhDT05TRUpPIERFIExBIEpVRElDQVRVUkExRDBCBgNV\n");
        cer.append("BAsMO1NVQkRJUkVDQ0lPTiBOQUNJT05BTCBERSBTRUdVUklEQUQgREUgTEEgSU5G\n");
        cer.append("T1JNQUNJT04gRE5USUNTMSowKAYDVQQDDCFFTlRJREFEIERFIENFUlRJRklDQUNJ\n");
        cer.append("T04gSUNFUlQtRUMwggIiMA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQCydJBT\n");
        cer.append("wK4TDgzh3aVnaXnvmOg5s3ryiaEE86Ue1VQisGH7w4JOkPyZz/wODeR4Jq5cXxYL\n");
        cer.append("opbJEOIHhuhgJ2NVoLwkxx9IIqKUY1Kw2ebcZ6YLtQAHDQxuoYJ+nyJcrYFipzB6\n");
        cer.append("UXo/Zj6nCo/K5cBkXyL63JhUccvzjW6f96US4J02Zk2F2CVIRlxKh61KbPmuaxwH\n");
        cer.append("WGfhReSUFXw70t9IMHgTxDwlMiDiWVfwaT7tgynYCBslH2ESFRCKjdcgF84GiWDT\n");
        cer.append("sc7aCnbCWFn//kBACs0zaaPguixn8NmY3MRUlYSpG79XIqLqMFAN1aDQhsI/xley\n");
        cer.append("QAeGreb4DvA2CSra+qpmFyPZ1663GhvU08k5BwUTrdbgd0vpCQB5zY3aif8L5GBj\n");
        cer.append("P8hap+e3PlymyGZ2pC+mg12fPowPHnGROPaY/3krIbLdIvpndmLSiPcr1bwyB91g\n");
        cer.append("ePIitgv9Vj43/3dgTOehji6nQbcF/HqqTVJc5QQTdhhN8WGHW280I2nbJndcvkoS\n");
        cer.append("/u6g6iSM90bo5UOvtLjl9jJjK0MD3yfAc153GOJ0MGaAjR5mcy/6wPhBT9/vws1w\n");
        cer.append("8YxYMRpaDqfNypSLzAP261kKrULNzHI5pXxO6YaEZpaEnw3ETgX63l1vqZXUNhHU\n");
        cer.append("PuFxW6CV/lQOR+LtNEUwQmo5E5eCQvA4TaVgcwIDAQABo4IBDjCCAQowHQYDVR0O\n");
        cer.append("BBYEFC8ievhebZSOakAUN8dua3LpOsI/MBIGA1UdEwEB/wQIMAYBAf8CAQAwHwYD\n");
        cer.append("VR0jBBgwFoAUc8jDvQ/5VUDxZZh6WKNnSB9wnRswaAYDVR0gBGEwXzBdBgRVHSAA\n");
        cer.append("MFUwUwYIKwYBBQUHAgEWR2h0dHA6Ly93d3cuaWNlcnQuZmplLmdvYi5lYy9kcGMv\n");
        cer.append("ZGVjbGFyYWNpb25fcHJhY3RpY2FzX2NlcnRpZmljYWNpb24ucGRmMDoGA1UdHwQz\n");
        cer.append("MDEwL6AtoCuGKWh0dHA6Ly93d3cuaWNlcnQuZmplLmdvYi5lYy9hcmxfaWNlcnQu\n");
        cer.append("Y3JsMA4GA1UdDwEB/wQEAwIBBjANBgkqhkiG9w0BAQsFAAOCAgEAmAYIAzMoT7iY\n");
        cer.append("TCluU3LAMWVcoqRyES/Aw2M1mqxkl2CpogiFJWSikj2jfHPfgVZR67ZmLdhAcA8R\n");
        cer.append("2a0Yz4ziTT4FwuJkcXK10lMJiF+sX6fMJ8t1abVpr8iYgsrF3Baoaw6p+W4pc/HY\n");
        cer.append("6ZasyQdi0s5unMCE9kDFmd9T6NwHh+iBVXLxwRUEDjw6iM+HWqCkWkV84NgbXWR7\n");
        cer.append("nrMjPFLLEYkzADCDld7mNQmBTEIyKmGpQRTypRoh0+P4npjRuob7wY4XVqGDWDOq\n");
        cer.append("PNx52qJXUVOj6i0xkI2d6mRd/w4A3waVv2gSyF7OD5iF6T8tizkJ9dnJEiAC8ZZ6\n");
        cer.append("xmBkwGezTrVw8zUk6ky2BWfn3dB2bIH6L4Pm+7+fI9VoactqUAAYv8niFvqff64/\n");
        cer.append("LtRW3IPNOmMatcUDrZs2we7lO6PxznuP38wcLkhO5+KDZw6/zY269Jw+aJ6OKGxr\n");
        cer.append("yuGJLCSA7m+xcbGVwikjXSZlqlbwRefHZzxLu68ORCg6KuiP31Dcc7FphhQxPgoS\n");
        cer.append("/G8tOoZlbjF8JPoH2PZcCIecx28+Ny/nBN6tNhQiMU0s9UkT1eB+E0nFSg8p61yJ\n");
        cer.append("rdiQ0jztV4LZuJIRg5xcVg3IX1rvJHFZ21PbMWBVi31jHp8OEX0k0RcjB7IToVj4\n");
        cer.append("tAp26GsfWS0uQGJAvY/Znn5/Qm1QzM0=\n");
        cer.append("-----END CERTIFICATE-----\n");

        try {
            InputStream is = new ByteArrayInputStream(cer.toString().getBytes("UTF-8"));
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            this.certificate = (X509Certificate) cf.generateCertificate(is);
        } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        certificate.checkValidity();
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        certificate.checkValidity(date);
    }

    @Override
    public int getBasicConstraints() {
        return certificate.getBasicConstraints();
    }

    @Override
    public Principal getIssuerDN() {
        return certificate.getIssuerDN();
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        return certificate.getIssuerUniqueID();
    }

    @Override
    public boolean[] getKeyUsage() {
        return certificate.getKeyUsage();
    }

    @Override
    public Date getNotAfter() {
        return certificate.getNotAfter();
    }

    @Override
    public Date getNotBefore() {
        return certificate.getNotBefore();
    }

    @Override
    public BigInteger getSerialNumber() {
        return certificate.getSerialNumber();
    }

    @Override
    public String getSigAlgName() {
        return certificate.getSigAlgName();
    }

    @Override
    public String getSigAlgOID() {
        return certificate.getSigAlgOID();
    }

    @Override
    public byte[] getSigAlgParams() {
        return certificate.getSigAlgParams();
    }

    @Override
    public byte[] getSignature() {
        return certificate.getSignature();
    }

    @Override
    public Principal getSubjectDN() {
        return certificate.getSubjectDN();
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        return certificate.getSubjectUniqueID();
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        return certificate.getTBSCertificate();
    }

    @Override
    public int getVersion() {
        return certificate.getVersion();
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return certificate.getEncoded();
    }

    @Override
    public PublicKey getPublicKey() {
        return certificate.getPublicKey();
    }

    @Override
    public String toString() {
        return certificate.toString();
    }

    @Override
    public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException,
            NoSuchProviderException, SignatureException {
        certificate.verify(key);
    }

    @Override
    public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException,
            InvalidKeyException, NoSuchProviderException, SignatureException {
        certificate.verify(key, sigProvider);
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        return certificate.getCriticalExtensionOIDs();
    }

    @Override
    public byte[] getExtensionValue(String oid) {
        return certificate.getExtensionValue(oid);
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        return certificate.getNonCriticalExtensionOIDs();
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        return certificate.hasUnsupportedCriticalExtension();
    }
}
