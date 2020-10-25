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
public class AnfAc37442SubCaCert20192029 extends X509Certificate {

    private X509Certificate certificate;

    public AnfAc37442SubCaCert20192029() {
        super();

        StringBuilder cer = new StringBuilder();
        cer.append("-----BEGIN CERTIFICATE-----\n");
        cer.append("MIIHtjCCBZ6gAwIBAgIIAWrbYUtHhHswDQYJKoZIhvcNAQELBQAwgaYxFjAUBgNV\n");
        cer.append("BAUTDTE3OTI2MDEyMTUwMDExCzAJBgNVBAYTAkVDMTYwNAYDVQQKEy1BTkZBQyBB\n");
        cer.append("VVRPUklEQUQgREUgQ0VSVElGSUNBQ0lPTiBFQ1VBRE9SIEMuQS4xGjAYBgNVBAsT\n");
        cer.append("EUFORiBDbGFzZSAxIENBIEVDMSswKQYDVQQDEyJBTkYgSGlnaCBBc3N1cmFuY2Ug\n");
        cer.append("RWN1YWRvciBSb290IENBMB4XDTE5MTAxNzE4MDc0OFoXDTI5MTAxNDE4MDc0OFow\n");
        cer.append("gbkxFjAUBgNVBAUTDTE3OTI2MDEyMTUwMDExCzAJBgNVBAYTAkVDMTYwNAYDVQQK\n");
        cer.append("Ey1BTkZBQyBBVVRPUklEQUQgREUgQ0VSVElGSUNBQ0lPTiBFQ1VBRE9SIEMuQS4x\n");
        cer.append("JTAjBgNVBAsTHEFORiBBdXRvcmlkYWQgaW50ZXJtZWRpYSAgRUMxMzAxBgNVBAMT\n");
        cer.append("KkFORiBIaWdoIEFzc3VyYW5jZSBFY3VhZG9yIEludGVybWVkaWF0ZSBDQTCCAiIw\n");
        cer.append("DQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAMQItdJmd8gin5IZpUBpoQN2NFDc\n");
        cer.append("mpMjAKn4K/Y3MjzneYwO6iKb43WyTyEvHe7LdxahpmMKAy9xMHPyQHgCGUxEI9tP\n");
        cer.append("tDEtbWWuzWYT7zc2mzczt4LcsvCFSRvZEp2mGzPcUwXhyFTP4K23qsl8rkuzizaT\n");
        cer.append("k/k4qsGcT3Vy1OKknVDjZWmww3ZgoUiEZbwfqRPXK/cN7Fub5fPrBcE0FThmkGCi\n");
        cer.append("KMx5tT0hrTvMQwkKylA8nKroVJBAGGz7X06zqzsFCxGB/8s+3MubnqbyW6kWgcIS\n");
        cer.append("8Vl6kknkzE4aG3Dk9tlFXkY7GUaUNQv46ZmE26HmCdNNNG1FFkCRCk9CBIxgB9Fz\n");
        cer.append("YPfK5YgXzjzdO+YeLxnSI5UAWskDyv94MhjFfGVMqeFUoh9CzHcmQfaxdPaXrM7/\n");
        cer.append("ra1HpYJkRjx11vIm08z3gmGX86mbYEVWdR1qHPi8HBNM5pVGP4ydezxDrtckYvwQ\n");
        cer.append("Fw5npfMGNuljJ4CXK/+xfIlKHhgAZxus3Uq6gAB0jFc2Xbnz0zzV3oczJq1Z11ep\n");
        cer.append("leTbYv6lEOwU07Srt1y3LGZGB0PdBPXowG7Sp2AoYCEPgNTZIVDRbdzvUSyHYwCZ\n");
        cer.append("5e2JR62VXRbh7up4Y8Rc2+yX/MI1rv4voeaVxSoHw+7KwEdWG6ysaF3qF9Bk6sFM\n");
        cer.append("0G+BkL7eQE5/8VW3AgMBAAGjggHRMIIBzTAfBgNVHSMEGDAWgBRqECUeEbYIj9VT\n");
        cer.append("sepUr2ttmvrIwjAdBgNVHQ4EFgQUkq7Uy4K0mcbRexFsZerFTWgh+awwDgYDVR0P\n");
        cer.append("AQH/BAQDAgGGMIGRBgNVHR8EgYkwgYYwQaA/oD2GO2h0dHA6Ly93d3cuYW5mLmVz\n");
        cer.append("L2NybC9BTkZIaWdoQXNzdXJhbmNlRWN1YWRvclJvb3RDQS1hcmwuY3JsMEGgP6A9\n");
        cer.append("hjtodHRwOi8vY3JsLmFuZi5lcy9jcmwvQU5GSGlnaEFzc3VyYW5jZUVjdWFkb3JS\n");
        cer.append("b290Q0EtYXJsLmNybDBABgNVHSAEOTA3MDUGBFUdIAAwLTArBggrBgEFBQcCARYf\n");
        cer.append("aHR0cDovL3d3dy5hbmYuZXMvZXMvZG9jdW1lbnRvczCBkwYIKwYBBQUHAQEEgYYw\n");
        cer.append("gYMwJwYIKwYBBQUHMAGGG2h0dHA6Ly9vY3NwLmFuZi5lcy9zcGFpbi9BVjBYBggr\n");
        cer.append("BgEFBQcwAoZMaHR0cDovL3d3dy5hbmYuZXMvZXMvY2VydGlmaWNhdGVzLWRvd25s\n");
        cer.append("b2FkL0FORkhpZ2hBc3N1cmFuY2VFY3VhZG9yUm9vdENBLmNlcjAPBgNVHRMBAf8E\n");
        cer.append("BTADAQH/MA0GCSqGSIb3DQEBCwUAA4ICAQAJkT3DRmzQX1ZH8iQQrGB06q3Wf+fT\n");
        cer.append("X3P7x/0lQUdWe8BGIdaQ2iVfOm1l11J1E87yn2DjiFjveLRigvDq7e7O8qs1nlQj\n");
        cer.append("GUwJ8picVYGAmCkwXLGF8Iceoeon7anD6V/qip5XSzMa3i0HH9b49IfTxMq6LpPH\n");
        cer.append("y1onLLeI87+VxF3Svvl6UQW9ZHuHHMKejFwTpeSccovjgMdk428gVrVT3hAs4Imc\n");
        cer.append("2F01pYoNKe76EXDuZeX4cy2wvJyms+SB/8cxVIhPtofu4W+Hb+jqxlyETEt7UPF+\n");
        cer.append("vzaxSUBnKl8nMDOJj9AvUAWz9obMfxFZb/xfLTK25C9mduW9M/XKguMOEPTL/bxS\n");
        cer.append("CLWHA6iJ9tQNTE4E6brkuNUr6edVw9+H7kmR/AfeiY7MVKT/i7v8I2pyqEHxHRgc\n");
        cer.append("GWl3W877A1v7sTq/uNaVqt5bFVwlJ/0ZjXHdQ3kdvQZox+f0vxeSU2lpFEttWrAG\n");
        cer.append("FeVRCwQAw9ode2KwOIoUPyUZBnjvG3EGM4jZJMcg1ZRHtXvfCdm/3sDRSC/6RcjR\n");
        cer.append("vBdMNXKkcqvXHh4+IFYDi2VbEr0fbmDAhrMMRvJi00Kgf98WW2xjpgHtdrSDHYt1\n");
        cer.append("QGgBy5KYeYsYGT4grJN0U5qG682u9k5si3Y6zAeT80jHCWRa3e2HPKQlb/0jg0YX\n");
        cer.append("YiVd+EZfmiN2gw==\n");
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
