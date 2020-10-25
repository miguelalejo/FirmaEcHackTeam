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
 * Certificado raiz de Consejo de la Judicatura, representado como un objeto
 * <code>X509Certificate</code>.
 *
 * @author mfernandez
 */
public class ConsejoJudicaturaCaCert extends X509Certificate {

    private X509Certificate certificate;

    public ConsejoJudicaturaCaCert() {
        super();

        StringBuilder cer = new StringBuilder();
        cer.append("-----BEGIN CERTIFICATE-----\n");
        cer.append("MIIGpjCCBI6gAwIBAgIIclcPl1CWUaYwDQYJKoZIhvcNAQELBQAwgboxCzAJBgNV\n");
        cer.append("BAYTAkVDMREwDwYDVQQHDAhETSBRVUlUTzEhMB8GA1UECgwYQ09OU0VKTyBERSBM\n");
        cer.append("QSBKVURJQ0FUVVJBMUQwQgYDVQQLDDtTVUJESVJFQ0NJT04gTkFDSU9OQUwgREUg\n");
        cer.append("U0VHVVJJREFEIERFIExBIElORk9STUFDSU9OIEROVElDUzEvMC0GA1UEAwwmSUNF\n");
        cer.append("UlQtRUMgRU5USURBRCBERSBDRVJUSUZJQ0FDSU9OIFJBSVowHhcNMTQxMDE2MTc0\n");
        cer.append("MDEzWhcNMzQxMDE2MTc0MDEzWjCBujELMAkGA1UEBhMCRUMxETAPBgNVBAcMCERN\n");
        cer.append("IFFVSVRPMSEwHwYDVQQKDBhDT05TRUpPIERFIExBIEpVRElDQVRVUkExRDBCBgNV\n");
        cer.append("BAsMO1NVQkRJUkVDQ0lPTiBOQUNJT05BTCBERSBTRUdVUklEQUQgREUgTEEgSU5G\n");
        cer.append("T1JNQUNJT04gRE5USUNTMS8wLQYDVQQDDCZJQ0VSVC1FQyBFTlRJREFEIERFIENF\n");
        cer.append("UlRJRklDQUNJT04gUkFJWjCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIB\n");
        cer.append("AMKpj1qmAlDFg+c2e1tCMij8c5+SAv6DIbDX7664wssD1baWNz7tjMLsHekxr5CR\n");
        cer.append("RgUq82leyxqFRpBaURxaRMwrys9sSPO5qfln5HM/4I8lRzR+RTD67JGqEI8U1YVf\n");
        cer.append("LwGYPqC5NTMaK/PKQ0ZoOfk6sgEx5o7XErB6CjSbr2d/ahlt8JL+KShvTkYqtHoM\n");
        cer.append("kg9a/1jPGKLHiLIxFstf+4L6ss/c4X6HwsjxEVTATuU3ByNVrVItPfxU2fxUsA7D\n");
        cer.append("JHPS2/mRFmHRuWMOzMoK2H0idavF0vqce1wWyaUbIeKE4NnLvE/Y0ONhVcNwRStl\n");
        cer.append("1lYix6Llf/5FW+AIJlwI3f8AvpS3sNGLGs27RxM0kklJS6iJx/q1GhoYIsglNJDO\n");
        cer.append("LDnRfEPwHx9oVa39xV0qvfwQsF4I+alUW2wafTcJtfv0NZHEmj2KcfgosCF0Zeji\n");
        cer.append("aA3lyA9gQaIARGzF74ui4vj4dkfdf7hOSEp8LSEz7zZH52O8X4rKEKzHQeoTxbzP\n");
        cer.append("vqLFAhORxpMSkx+n7xUJpGSDLwy3rsTX865j4fokH/IxJylcOeChPcKoMa+P02Zf\n");
        cer.append("FWi9TrUlyiGe0x2V/8n47IhPRQPiFFN90U3Ct96zAYYSw0jmHy6ktGHRJcGTlw1P\n");
        cer.append("PYqbcghot0/pB126IFgFx5oiN3VReTXo86yadAxkRm17AgMBAAGjga0wgaowHQYD\n");
        cer.append("VR0OBBYEFHPIw70P+VVA8WWYelijZ0gfcJ0bMA8GA1UdEwEB/wQFMAMBAf8waAYD\n");
        cer.append("VR0gBGEwXzBdBgRVHSAAMFUwUwYIKwYBBQUHAgEWR2h0dHA6Ly93d3cuaWNlcnQu\n");
        cer.append("ZmplLmdvYi5lYy9kcGMvZGVjbGFyYWNpb25fcHJhY3RpY2FzX2NlcnRpZmljYWNp\n");
        cer.append("b24ucGRmMA4GA1UdDwEB/wQEAwIBBjANBgkqhkiG9w0BAQsFAAOCAgEAkUMleJaH\n");
        cer.append("8gPVbe3fmLUvOGI5rWMaa3La2LRWkSCzh0TtyBnjMYsSSwEmnsHA71KXwC6rNVwK\n");
        cer.append("yA4Q9cCytLHb3ewMpuFRAIprrDoCeUxmX4/SlHUOlwJ9oE22dYa6+BiW7OYFMjcc\n");
        cer.append("EfwYKce9dXc73+TeyJKKZBYoHWDUSQ2+RbTAjLLxyd+pM5GPZe6/WdgjEhYHxWYu\n");
        cer.append("F7tDMmsk1KqHQWbzA50mPJ9KdrApYt/hf9zZ7Z6+iAdY8yUHzuuSuY2YxyUorPHl\n");
        cer.append("yOCT2snOqYMq5pyxECnzNyPmtghmVKSa6NE/mCL+VVWalejZsnwq4Bk5U3s/Lpau\n");
        cer.append("G7n6vpoo4chqNWzHMz+AGukE7Umvb+2UtAyTzZTYCDdwYQCuy0CnG5eM6qxJJbg5\n");
        cer.append("k+pqUexMvUDFTIuF+HZUVEZcNAhhXi+Fhg3MqJZoCRJHRFt+0D1QG6bNJ4Lq3oH7\n");
        cer.append("wuOf+wRY7JAaK1FT8lYqD2kLr2if8f0GlAYBUtEqeRdGRh0PYQgcHHM+Sf4QOUNP\n");
        cer.append("3JN8sVJQZLMWD5D6PHznd19im3T1+SJqWvUQespDi6IYnmlXyjoUtIIpuQGgrlfc\n");
        cer.append("OcZ17gRrAIbpPfZNTuakG2Q8q1MmSt1CCArLFpTvLoDigNRsMYSwdaeEjJQvIzSK\n");
        cer.append("yUlekcadxaa0YzaiQOdpExKqoTrZZtN9d9U=\n");
        cer.append("-----END CERTIFICATE-----");

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
