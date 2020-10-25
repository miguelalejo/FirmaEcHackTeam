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
public class AnfAc37442CaCert20192039 extends X509Certificate {

    private X509Certificate certificate;

    public AnfAc37442CaCert20192039() {
        super();

        StringBuilder cer = new StringBuilder();
        cer.append("-----BEGIN CERTIFICATE-----\n");
        cer.append("MIIGMzCCBBugAwIBAgIIAWxSgelqUyEwDQYJKoZIhvcNAQELBQAwgaYxFjAUBgNV\n");
        cer.append("BAUTDTE3OTI2MDEyMTUwMDExCzAJBgNVBAYTAkVDMTYwNAYDVQQKEy1BTkZBQyBB\n");
        cer.append("VVRPUklEQUQgREUgQ0VSVElGSUNBQ0lPTiBFQ1VBRE9SIEMuQS4xGjAYBgNVBAsT\n");
        cer.append("EUFORiBDbGFzZSAxIENBIEVDMSswKQYDVQQDEyJBTkYgSGlnaCBBc3N1cmFuY2Ug\n");
        cer.append("RWN1YWRvciBSb290IENBMB4XDTE5MTAxNzE3MTAzNFoXDTM5MTAxMjE3MTAzNFow\n");
        cer.append("gaYxFjAUBgNVBAUTDTE3OTI2MDEyMTUwMDExCzAJBgNVBAYTAkVDMTYwNAYDVQQK\n");
        cer.append("Ey1BTkZBQyBBVVRPUklEQUQgREUgQ0VSVElGSUNBQ0lPTiBFQ1VBRE9SIEMuQS4x\n");
        cer.append("GjAYBgNVBAsTEUFORiBDbGFzZSAxIENBIEVDMSswKQYDVQQDEyJBTkYgSGlnaCBB\n");
        cer.append("c3N1cmFuY2UgRWN1YWRvciBSb290IENBMIICIjANBgkqhkiG9w0BAQEFAAOCAg8A\n");
        cer.append("MIICCgKCAgEAu55/r5nQe3kPrbCIxgWSTIfq8TBMpv3BvZenF9H/8rabNpwODQVt\n");
        cer.append("8xNcMjYwDns8n5mEYRG0RM3Cros6W7g6zZIsdRpDk0qYhWGQ4mnRrwB/pAEpktYU\n");
        cer.append("uf2RkJGxdGIbYo5xYMHdzVCXvVJhuWkfZ+l2f9IpMsvvvEytREHKf6yH9ET22dhk\n");
        cer.append("NSCc9Hla19rsrL4tjOVegwe2EbslLZaRJGTah4YBO4FnEpz0LVREfGKp0KcLk5I6\n");
        cer.append("MCVqp9Jz/UrC4WRyVXuVmqhpa98Ca7g5RP9XxuMN5b7RFVHgIfUQd0yw5/oStnqf\n");
        cer.append("hGWSzIbp6bkMBfgt5RvX/sTw+E8tpzfut0RF2bqL7mxUw0cCiglCPoY2VOhQ5kLw\n");
        cer.append("h+DG0yzoKRtYQTG+roKcCvPCeFEc+xc9szjTkyW8Qb8meERveLBRrj8HLrAViuE9\n");
        cer.append("gvLcbolT1tyWrnyGnZeCuGiXM8omYUSz0QiLyfyP3gyFtap1dCQztM3K+pTAb5mQ\n");
        cer.append("xtqe8G7NZulsn4XfBA/25FZ3SZXMYGZMFaH+ntnuVc1rs2/wErbafW9TZ3r4O1G/\n");
        cer.append("3MR3MAoXttsrrHvEEe8O3RU2ksYQ/+nhQb/PhyvXdsBNvnm8/d9Kasn3NpatxT2y\n");
        cer.append("+Uh0+3hG+fW2oqe4GGbpmm+GFoUszXTSFD1AEZm6bJU92RhOt2zMf+8CAwEAAaNj\n");
        cer.append("MGEwHwYDVR0jBBgwFoAUahAlHhG2CI/VU7HqVK9rbZr6yMIwHQYDVR0OBBYEFGoQ\n");
        cer.append("JR4RtgiP1VOx6lSva22a+sjCMA4GA1UdDwEB/wQEAwIBhjAPBgNVHRMBAf8EBTAD\n");
        cer.append("AQH/MA0GCSqGSIb3DQEBCwUAA4ICAQCMkFnSUzfAx+9stUdCgAykVYwC4F0SNoYr\n");
        cer.append("RNrFtG1jcA6YmqZqHrJgUr73ULpYdIHh6CkIyDImwYydhxVPHCgWs97UeKGlPzNT\n");
        cer.append("nB1sSKQzhurVEimE7KN//Gy+wJc7dV/ZH4KV59CdTP5Hs2ZaIv/sX+8NPS34q/Dl\n");
        cer.append("uB3aU9zFSn9+IxvYkD/tfTgZsCo8IbE5Vi1KUMj3cnqJb60rGPlLa1AGI0dSzXHp\n");
        cer.append("ToHJG/6yBjz5nzMriGddmG2bVb6K4ygbTKYNrsJOs3AqCYQz1wQswNURJXSQGZsB\n");
        cer.append("y/z896n88NcMSf1Lp02ioPg5CqusRHpv0mEqluF/cP5fC2HAduJp7QEPrBeeihsw\n");
        cer.append("sbKBSHJgJNhQDhiIQARlgAgCFRfR1QXzrw8hCBPm5kRBf1sBC57AR0hyKX2Z18ed\n");
        cer.append("8Zc748HHnO/p/NIVnyQBPPaxm85Xo1mFQGmBYBwAYQiwR0VXNSjNeJeZbhxnLSYV\n");
        cer.append("93LWe1pGV7O9I4o+HFVqCM05EkMUNco0KMUvW7TV9NOqp/Bam939ZDV1uNg0hNsD\n");
        cer.append("SGOuQvABfRPp8rdowTcNZFS7zK2wTNdptWK3b13O/XY2QVNahtAuzxK9Fp95RjtV\n");
        cer.append("ZaEqEvzVHActc64j9mbNR4gsIEi+L9FtNDha/3AB4MeQZeGrnFwXbPT8UkkjrTc6\n");
        cer.append("pDp1Nj4wdw==\n");
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
