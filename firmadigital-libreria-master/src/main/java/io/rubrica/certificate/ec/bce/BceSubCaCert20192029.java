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
package io.rubrica.certificate.ec.bce;

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
 * Certificado intermedio del Banco Central del Ecuador, representado como un
 * objeto <code>X509Certificate</code>.
 *
 * @author mfernandez
 */
public class BceSubCaCert20192029 extends X509Certificate {

    private X509Certificate certificate;

    public BceSubCaCert20192029() {
        super();

        StringBuilder cer = new StringBuilder();
        cer.append("-----BEGIN CERTIFICATE-----\n");
        cer.append("MIII8zCCBtugAwIBAgIEW1yBoTANBgkqhkiG9w0BAQsFADCBwjELMAkGA1UEBhMC\n");
        cer.append("RUMxIjAgBgNVBAoTGUJBTkNPIENFTlRSQUwgREVMIEVDVUFET1IxNzA1BgNVBAsT\n");
        cer.append("LkVOVElEQUQgREUgQ0VSVElGSUNBQ0lPTiBERSBJTkZPUk1BQ0lPTi1FQ0lCQ0Ux\n");
        cer.append("DjAMBgNVBAcTBVFVSVRPMUYwRAYDVQQDEz1BVVRPUklEQUQgREUgQ0VSVElGSUNB\n");
        cer.append("Q0lPTiBSQUlaIERFTCBCQU5DTyBDRU5UUkFMIERFTCBFQ1VBRE9SMB4XDTE5MDcy\n");
        cer.append("NzEzMDIwMFoXDTI5MDcyNzEzMzIwMFowgaExCzAJBgNVBAYTAkVDMSIwIAYDVQQK\n");
        cer.append("ExlCQU5DTyBDRU5UUkFMIERFTCBFQ1VBRE9SMTcwNQYDVQQLEy5FTlRJREFEIERF\n");
        cer.append("IENFUlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04tRUNJQkNFMQ4wDAYDVQQHEwVR\n");
        cer.append("VUlUTzElMCMGA1UEAxMcQUMgQkFOQ08gQ0VOVFJBTCBERUwgRUNVQURPUjCCAiIw\n");
        cer.append("DQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAM0jqNjiN6TxK5NevkojfVPjV2BP\n");
        cer.append("2dPGxevvAfbp9hrZVcUXPHP85sqKLol1ZQwDXV6sfWKpY1t4NsTftMYcKV1IZwwP\n");
        cer.append("HYaEqH40/arWW97cvLpfJ3FoAhoAAnJWPGWdDg+UApceIfvoQiSMiR7hCFerPyJ3\n");
        cer.append("PHkYMpYUOb74XjNQbq/ApuLtPvVLGqPeB10vrXf9LEHYHeoOrN0I1FR1OJU9rFdd\n");
        cer.append("G/POMGLueUXlbd8VHs/rvymU4so2MkCewbHpT70L1se/AZUcxN8zwNNsAhdfWCOQ\n");
        cer.append("ADV7/gsM6HaDm91gDNipYDkaCrqC/wwfbvliN3mVzgJ6p/ff2uwzSXl1Btq18+4M\n");
        cer.append("xnwagrDMouo18noYm1QHeFYauuYzZmCzLl5M7A6Gw6klGPsaWIvFpWmHzC9PDzi5\n");
        cer.append("BiYWAadCIzKfbSPwW4AznfDbyIhF3+OfH+x9VBFFkFt1H9q9RLU5r+Hw7ZYH3Ehd\n");
        cer.append("1ZMdkG6+G129w5k8UeKF33nv6WtS8HQuNMfSk4iVaFPGadRtl3SuhnDG4zb12olB\n");
        cer.append("wdmJUc/rd5d4tGA0iyrGLYeQsLaA0GSf1+gi4c62RplhibNjAsMn9zrdyAWhkqfd\n");
        cer.append("umsKd/1SfdHQAatCzwwOBOjBzAtnDlDrt/NcZsUMTjItsIpabX+qJf9vdSDHOLlp\n");
        cer.append("UPOzPY3wJrLVOMjnAgMBAAGjggMOMIIDCjB8BgNVHSAEdTBzMHEGCisGAQQBgqg7\n");
        cer.append("AQEwYzBhBggrBgEFBQcCARZVaHR0cDovL3d3dy5lY2kuYmNlLmVjL2F1dG9yaWRh\n");
        cer.append("ZC1jZXJ0aWZpY2FjaW9uL2RlY2xhcmFjaW9uLXByYWN0aWNhcy1jZXJ0aWZpY2Fj\n");
        cer.append("aW9uLnBkZjCCAhIGA1UdHwSCAgkwggIFMIICAaCCAf2gggH5pIHUMIHRMQswCQYD\n");
        cer.append("VQQGEwJFQzEiMCAGA1UEChMZQkFOQ08gQ0VOVFJBTCBERUwgRUNVQURPUjE3MDUG\n");
        cer.append("A1UECxMuRU5USURBRCBERSBDRVJUSUZJQ0FDSU9OIERFIElORk9STUFDSU9OLUVD\n");
        cer.append("SUJDRTEOMAwGA1UEBxMFUVVJVE8xRjBEBgNVBAMTPUFVVE9SSURBRCBERSBDRVJU\n");
        cer.append("SUZJQ0FDSU9OIFJBSVogREVMIEJBTkNPIENFTlRSQUwgREVMIEVDVUFET1IxDTAL\n");
        cer.append("BgNVBAMTBENSTDGGgfpsZGFwOi8vYmNlcWxkYXByYWl6cC5iY2UuZWMvY249Q1JM\n");
        cer.append("MSxjbj1BVVRPUklEQUQlMjBERSUyMENFUlRJRklDQUNJT04lMjBSQUlaJTIwREVM\n");
        cer.append("JTIwQkFOQ08lMjBDRU5UUkFMJTIwREVMJTIwRUNVQURPUixsPVFVSVRPLG91PUVO\n");
        cer.append("VElEQUQlMjBERSUyMENFUlRJRklDQUNJT04lMjBERSUyMElORk9STUFDSU9OLUVD\n");
        cer.append("SUJDRSxvPUJBTkNPJTIwQ0VOVFJBTCUyMERFTCUyMEVDVUFET1IsYz1FQz9hdXRo\n");
        cer.append("b3JpdHlSZXZvY2F0aW9uTGlzdD9iYXNlhiNodHRwOi8vd3d3LmVjaS5iY2UuZWMv\n");
        cer.append("Q1JML2NhY3JsLmNybDALBgNVHQ8EBAMCAQYwHwYDVR0jBBgwFoAUqBAVqN+gmczo\n");
        cer.append("6M/ubUbv6hbSCswwHQYDVR0OBBYEFEii3yMfHfgsUXqMA81JMqUJwZSrMAwGA1Ud\n");
        cer.append("EwQFMAMBAf8wGQYJKoZIhvZ9B0EABAwwChsEVjguMAMCAIEwDQYJKoZIhvcNAQEL\n");
        cer.append("BQADggIBAJCQ+HLjd1hRLQhBGbpKdLnlHsYG7mmXNt9lDpCNUpY5iASFWEhvR9DP\n");
        cer.append("nBRwy+54Z6VnY7tZSQVqRKFgwYifnOQ2r3n93h9ZyaES/Avxp6zfrFSIGHpiuEd7\n");
        cer.append("7bEx3S0dR5QQ0Z/BJg86/DyobU3p8sO5+09yFsi1tH4YH+jQEhHgxvEEp+yQmsrf\n");
        cer.append("lMan60pJq5dG5uIDEh59SV/nzp1XYguqvlq/710ktJkdn0L3kiHmh7MbOxsgwSDr\n");
        cer.append("OIF2A2Nz3P20kR0jkkA4bm5CUsnRRi4TMjALEUGb4i6XHgGk1+uG8mTz6Vn3qomX\n");
        cer.append("W5JxIkgxTRG1y821GFn3pxtdALMCF5nrhWttr2Q8IKVj9fI4ShXPiIaDAz5yh0aA\n");
        cer.append("iVISj5xJlVxv+qJmgluC5bRG4ElxiqDlrDfAokAygjq2f7sQfe4kWdGuA5Xg3zbR\n");
        cer.append("V7SKJDc8xCEeJjsMvQq2nLzfAWHq3wVPxGuK2FOY6b7qYSCw9ojMXqwPrOvaalE9\n");
        cer.append("UjpNcvkkdp7KrfVcZLXi6F3i49plCycsogHEWixcawn2VcBs7uTH0iqllozwlVFG\n");
        cer.append("unGgqX5Z0E25t6Ipmk2WSwFB7rqMmk552QhMvrwK6UFYu8Yw3RS3zxsUk7tKoR+j\n");
        cer.append("P6LjqQ4/Z+8loHvvLYdXvm7u2GoxzHWMgWzExdUhsMCocBnUVTDB\n");
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
