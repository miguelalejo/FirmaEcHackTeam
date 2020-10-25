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
 * Certificado raiz de ANF AC Ecuador, representado como un objeto
 * <code>X509Certificate</code>.
 * 
 * @author mfernandez
 */
public class AnfAc18332CaCert20162036 extends X509Certificate {

    private X509Certificate certificate;

    public AnfAc18332CaCert20162036() {
        super();

        StringBuilder cer = new StringBuilder();
        cer.append("-----BEGIN CERTIFICATE-----\n");
        cer.append("MIIF5zCCA8+gAwIBAgIIAWSV7mGKB1AwDQYJKoZIhvcNAQELBQAwgYAxEjAQBgNV\n");
        cer.append("BAUTCUc2MzI4NzUxMDEbMBkGA1UEAwwSQU5GIEdsb2JhbCBSb290IENBMQswCQYD\n");
        cer.append("VQQGEwJFUzEXMBUGA1UECwwOQU5GIENsYXNlIDEgQ0ExJzAlBgNVBAoMHkFORiBB\n");
        cer.append("dXRvcmlkYWQgZGUgQ2VydGlmaWNhY2lvbjAeFw0xNjA1MjAxNDA4NDBaFw0zNjA1\n");
        cer.append("MTUxNDA4NDBaMIGAMRIwEAYDVQQFEwlHNjMyODc1MTAxGzAZBgNVBAMMEkFORiBH\n");
        cer.append("bG9iYWwgUm9vdCBDQTELMAkGA1UEBhMCRVMxFzAVBgNVBAsMDkFORiBDbGFzZSAx\n");
        cer.append("IENBMScwJQYDVQQKDB5BTkYgQXV0b3JpZGFkIGRlIENlcnRpZmljYWNpb24wggIi\n");
        cer.append("MA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQDHPi9xy4wynbcUbWjorVUgQKeU\n");
        cer.append("AVh937J7P37XmsfHZLOBZKIIlhhCtRwnDlg7x+BUvtJOTkIbEGMujDygUQ2s3HDY\n");
        cer.append("r5I41hTyM2Pl0cq2EuSGEbPIHb3dEX8NAguFexM0jqNjrreN3hM2/+TOkAxSdDJP\n");
        cer.append("2aMurlySC5zwl47KZLHtcVrkZnkDa0o5iN24hJT4vBDT4t2q9khQ+qb1D8KgCOb0\n");
        cer.append("2r1PxWXu3vfd6Ha2mkdB97iGuEh5gO2n4yOmFS5goFlVA2UdPbbhJsb8oKVKDd+Y\n");
        cer.append("dCKGQDCkQyG4AjmCYiNm3UPG/qtftTH5cWri67DlLtm6fyUFOMmO6NSh0RtR745p\n");
        cer.append("L8GyWJUanyq/Q4bFHQB21E+WtTsCaqjGaoFcrBunMypmCd+jUZXl27TYENRFbrwN\n");
        cer.append("dAh7m2UztcIyb+SgVJFyfvVsBQNvnp7GPimVxXZNc4VpxEXObRuPWQN1oZN/90Pc\n");
        cer.append("ZVqTia/SHzEyTryLckhiLG3jZiaFZ7pTZ5I9wti9Pn+4kOHvE3Y/4nEnUo4mTxPX\n");
        cer.append("9pOlinF+VCiybtV2u1KSlc+YaIM7VmuyndDZCJRXm3v0/qTE7t5A5fArZl9lvibi\n");
        cer.append("gMbWB8fpD+c1GpGHEo8NRY0lkaM+DkIqQoaziIsz3IKJrfdKaq9bQMSlIfameKBZ\n");
        cer.append("8fNYTBZrH9KZAIhzYwIDAQABo2MwYTAdBgNVHQ4EFgQUh/qe31J2dexJSiBvW3CW\n");
        cer.append("j53vuxcwHwYDVR0jBBgwFoAUh/qe31J2dexJSiBvW3CWj53vuxcwDwYDVR0TAQH/\n");
        cer.append("BAUwAwEB/zAOBgNVHQ8BAf8EBAMCAQYwDQYJKoZIhvcNAQELBQADggIBAIVowDGC\n");
        cer.append("cPL6Od4fDoZRanKV6Nduz6PGulXH76kMVFWWT9nmE0RX11pQnANrXLGmcz+bH23E\n");
        cer.append("/YFXPIlPXdzWLoBfMymxJAYt22IMPFvnTjjoP+C3vUC3H/88lrGlbKz66DCeScRm\n");
        cer.append("8bvNhQGGS9eohzOl+AFo6xnlzbKuxUcTpQpeXvvQha+YsAF7LpHantBmV0R/UQ3o\n");
        cer.append("BYutQRGtsl7AadpAWRolXEemufeS0VNWGxRoe1pgP3yctY/hgN1Km7S1IcNl2DHb\n");
        cer.append("kZT+HxZqsZLx1SQUufbSddCqCnvxcMUiAjtsf0hRnqs2ArpS9h6dDYCY++tew5su\n");
        cer.append("UV+hN4GuCg/pX6i4r9xbrGv4nj05A8kVo+OOERHmKpXxqnYoc+cx16fn54hWB5Fq\n");
        cer.append("L/X05xo+W9L8upuDimpf6NtjHAdpOCZlqwp0xZJcRAcYqg0wDl74BzTmH1PWBrj6\n");
        cer.append("f+ZAxgNgvt4A89lmUJP/kBXWeiKbjHRxwQp0I8wJRWKL4jMYSn71GDKE5FvNMOoi\n");
        cer.append("gvm05/rSf/ZlYPx36g8/0U22N/0CTCAsc4sK8GjqF4uMiGlo/eEgrK4aKQ2BuEuD\n");
        cer.append("E973CkA/qtg0kH1MJmBrbqPDZm4cBMfLTGvWl1r0l59pmEjEp538DF6rrLnPo/gK\n");
        cer.append("gZFzZdSx1n7yoUkc6RXshyqxmbX+0JTyy03S\n");
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
