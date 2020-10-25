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
package io.rubrica.certificate.ec.anfac;

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
 * Certificado intermedio ANF AC Ecuador, representado como un objeto
 * <code>X509Certificate</code>.
 *
 * @author mfernandez
 */
public class AnfAc18332SubCaCert20162032 extends X509Certificate {

    private X509Certificate certificate;

    public AnfAc18332SubCaCert20162032() {
        super();

        StringBuilder cer = new StringBuilder();
        cer.append("-----BEGIN CERTIFICATE-----\n");
        cer.append("MIIIRDCCBiygAwIBAgIIBLekSNqUl4AwDQYJKoZIhvcNAQELBQAwgYAxEjAQBgNV\n");
        cer.append("BAUTCUc2MzI4NzUxMDEbMBkGA1UEAwwSQU5GIEdsb2JhbCBSb290IENBMQswCQYD\n");
        cer.append("VQQGEwJFUzEXMBUGA1UECwwOQU5GIENsYXNlIDEgQ0ExJzAlBgNVBAoMHkFORiBB\n");
        cer.append("dXRvcmlkYWQgZGUgQ2VydGlmaWNhY2lvbjAeFw0xNjA3MjExNDIzMTZaFw0zMjEy\n");
        cer.append("MjQxNDIzMTZaMIGaMRYwFAYDVQQFEw0xNzkyNjAxMjE1MDAxMRgwFgYDVQQDDA9B\n");
        cer.append("TkYgRWN1YWRvciBDQTExCzAJBgNVBAYTAkVDMSMwIQYDVQQLDBpBTkYgQXV0b3Jp\n");
        cer.append("ZGFkIFJhaXogRWN1YWRvcjE0MDIGA1UECgwrQU5GQUMgQXV0b3JpZGFkIGRlIENl\n");
        cer.append("cnRpZmljYWNpb24gRWN1YWRvciBDQTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCC\n");
        cer.append("AgoCggIBALtSqchZDvn46NfPJ3JGNKyKUXg1JAmdXA87mTCOTeWxNskBdAAmQGdl\n");
        cer.append("jL+WCZaNypxzVfdK+96dURIkNhKJ+Y+kmFr3NJmQO2RVbOKgWVS1JW/l4kVJI1s2\n");
        cer.append("OVJwyr/7Fy5fCPNZF7YI3jvGRmIsTE9sGgwBEgWiTKhBJPlwHNpw7inhrJuTak1P\n");
        cer.append("4iK15bR9qeAeDQPo/0JOcjhznb7ckMGLQ4uNAjsHFrRT5QZjQVp7wtn6rSkdfc0q\n");
        cer.append("WezaWeyi7D6PyBBSZmZP0adslU5kXdzXP7uE3b/RcHFyX67C6QZqqyCZV/R+UBge\n");
        cer.append("uOQ+pFVYUJEtX7DSUYUvEXZkOcStNOoSAroGnM7TEEDg0bVeCuSI0XtqDC3ZqvTx\n");
        cer.append("m5Iv1x2cr8584Pfm5Pk7c/3I5jNGrSnPPf4VwbonD4fzC012Urs+IVMTRF68/GmZ\n");
        cer.append("EilSMfNWURk9rafu5OyRD1i9ufAWqoiMP3F2YawjNGb9/x7YlxWJ7+CJptakkIEk\n");
        cer.append("3QlDqy6p1ETGupcwGn8IkICRo7om/k+3hhfGM/rpxQY7BWRfgRNSTumn9Vd/xoL1\n");
        cer.append("KeeMsZXteM6/qYc9dw57VURyYEZR+NFK+K3LTEQtzfAnq0pq93OpldqqNkU7BvfV\n");
        cer.append("FiwwhTOiTKPRw0al96zBa+sa3Lbe8CeSU+bxzXIyBUXejIRC3p6JAgMBAAGjggKk\n");
        cer.append("MIICoDAdBgNVHQ4EFgQUNJyTJrWWWDBIiFe3LtzUMecNHEswHwYDVR0jBBgwFoAU\n");
        cer.append("h/qe31J2dexJSiBvW3CWj53vuxcwDwYDVR0TAQH/BAUwAwEB/zAOBgNVHQ8BAf8E\n");
        cer.append("BAMCAQYwggEoBgNVHSAEggEfMIIBGzCCARcGBFUdIAAwggENMIHdBggrBgEFBQcC\n");
        cer.append("AjCB0BqBzUFORiBBQyBlbWlzb3IgZGUgY2VydGlmaWNhZG9zIHJlY29ub2NpZG9z\n");
        cer.append("IGNvbmZvcm1lIGxhIGxlZ2lzbGFjaW9uIGRlbCBFY3VhZG9yLiBDdWFscXVpZXIg\n");
        cer.append("dXNvIGRlIGVzdGUgY2VydGlmaWNhZG8gaW1wbGljYSBsYSBhY2VwdGFjaW9uIGRl\n");
        cer.append("IGxhIENQUyB5IGRlIGxhcyBsaW1pdGFjaW9uZXMgZGUgcmVzcG9uc2FiaWxpZGFk\n");
        cer.append("IGVuIGVsIGVzdGFibGVjaWRhcy4wKwYIKwYBBQUHAgEWH2h0dHA6Ly93d3cuYW5m\n");
        cer.append("LmVzL2VjL2RvY3VtZW50b3MweQYDVR0fBHIwcDA2oDSgMoYwaHR0cDovL3d3dy5h\n");
        cer.append("bmYuZXMvY3JsL0FORl9HbG9iYWxfUm9vdF9DQV9hcmwuY3JsMDagNKAyhjBodHRw\n");
        cer.append("Oi8vY3JsLmFuZi5lcy9jcmwvQU5GX0dsb2JhbF9Sb290X0NBX2FybC5jcmwwgZUG\n");
        cer.append("CCsGAQUFBwEBBIGIMIGFMCcGCCsGAQUFBzABhhtodHRwOi8vb2NzcC5hbmYuZXMv\n");
        cer.append("c3BhaW4vQVYwWgYIKwYBBQUHMAKGTmh0dHBzOi8vd3d3LmFuZi5lcy9lcy9jZXJ0\n");
        cer.append("aWZpY2F0ZXNfZG93bmxvYWQvQU5GX0dsb2JhbF9Sb290X0NBX1NIQTI1Nl8yMDM2\n");
        cer.append("LmNlcjANBgkqhkiG9w0BAQsFAAOCAgEACMP/vdCkGSswesLPnw3ofu5c2XJ476Y9\n");
        cer.append("c02QOuA5XBf1IYfxCYa/xlhGzzhUQrE22lwkIeHNThpXNSc043NAF6Y0gS1ixNmd\n");
        cer.append("NFoy10aW5GMX84Q/wkhkr1ZmyC5sCWoIyW06FizxgzEK3SBHwbEnZSCuYDlQVpp4\n");
        cer.append("KHUsVzjd6VanUW4As8WWI8TRuTQ/xDtR56ixwUw3BvCUUKo2iEyqJ/LHdhFoqpGR\n");
        cer.append("BQUk+sSUDw7MhMTePA3TfiL/7vmlvIjqDK24CGRYQXAkxaVnDfZh0cCVMykoxVme\n");
        cer.append("GjZpiY7vC8uvAd22k4a7uuF5yIz5niuxBjATSy6TLOQLx2mCSrVK3kCtktXiR633\n");
        cer.append("VKNaih9DbNFeNZmEGUJ2uQZ6ZlramW6DQSipF17rX5xeRjbTDo6iSB5Ippo5waZg\n");
        cer.append("5Lhrif4/B/6E6ZZm/v6VQCuhq8ALJsPFAIKLpFscg9ApRMOWyRKp4WFSZpXAGte1\n");
        cer.append("UUci5SX1aT1ab+1yy3hUpABgo0dYFphb0/dLmvU1b7HMGm5sWhqVA/zgAL8v01jb\n");
        cer.append("EGzfSSACEzD+/nYDb/A/OnEO37RAXTykPL7QBQUT24qJ+/yKbBiM1PgebLZAx3zP\n");
        cer.append("x57rVZhISEt6QFHKGwnYg2wtD/NGdIgfmf8p7hW8Fyn6omHvqr2wPZxUi4GmEzfL\n");
        cer.append("w6AuhQx1Q3A=\n");
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
