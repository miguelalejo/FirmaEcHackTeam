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
package io.rubrica.certificate.ec.securitydata;

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
 * Certificado intermedio del Security Data, representado como un objeto
 * <code>X509Certificate</code>.
 *
 * @author mfernandez
 */
public class SecurityDataSubCaCert20202039 extends X509Certificate {

    private X509Certificate certificate;

    public SecurityDataSubCaCert20202039() {
        super();

        StringBuilder cer = new StringBuilder();
        cer.append("-----BEGIN CERTIFICATE-----\n");
        cer.append("MIIIJDCCBgygAwIBAgIEWJrokDANBgkqhkiG9w0BAQsFADCBmzELMAkGA1UEBhMC\n");
        cer.append("RUMxHTAbBgNVBAoMFFNFQ1VSSVRZIERBVEEgUy5BLiAyMTAwLgYDVQQLDCdFTlRJ\n");
        cer.append("REFEIERFIENFUlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04xOzA5BgNVBAMMMkFV\n");
        cer.append("VE9SSURBRCBERSBDRVJUSUZJQ0FDSU9OIFJBSVogQ0EtMiBTRUNVUklUWSBEQVRB\n");
        cer.append("MB4XDTE5MTAxNTIyMTU1N1oXDTM5MDQwNzIyMTU1N1owgZkxCzAJBgNVBAYTAkVD\n");
        cer.append("MR0wGwYDVQQKDBRTRUNVUklUWSBEQVRBIFMuQS4gMjEwMC4GA1UECwwnRU5USURB\n");
        cer.append("RCBERSBDRVJUSUZJQ0FDSU9OIERFIElORk9STUFDSU9OMTkwNwYDVQQDDDBBVVRP\n");
        cer.append("UklEQUQgREUgQ0VSVElGSUNBQ0lPTiBTVUJDQS0yIFNFQ1VSSVRZIERBVEEwggIi\n");
        cer.append("MA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQDIEvPSI5HT5FJ6T+kkuTbOr4qV\n");
        cer.append("6A+HtdPCurs4p51KBQ0+qawKxV9PaL/lMxBMlBF5IYr8rYOzUD2NL6Gj3/bRdFkK\n");
        cer.append("R2HdVrSh3VTUQCq9F5KbTk0wIDlUARdt2GGdY/frbNH9f8lK1LwyU/3sGOGLiF27\n");
        cer.append("RJJI4dcS/qkhz25IlPvvV0kORHOT7Ar0GFTX4fGnBX4c+zEL+/trNKN7SPgswYL5\n");
        cer.append("lRHKQXXTpgDv0eWecBWCTz+4VDD0xX/VYYDRPmczMzIhCH+3nJmCPaTrmTMiL2MG\n");
        cer.append("Zx0rgam9ckStqvGXyKurE0Hkn0uciaux4HcQDcdqkXJKCyyd7QSN87FMIUKjTttt\n");
        cer.append("2KfO667rMrDK2h9QyzCMWPJ5NXjJY71NJQX6KLYOm9PydioN9H5owF6AcGW0QFiH\n");
        cer.append("RgPsbhREnDL4ZflDmok+S/9TrcJQVrLjfnuoGRtqnPErKNeMebtZGx5crqjc2+fW\n");
        cer.append("gBvdJtLldZryfi5nrcdYGEKYiU4b349aXWNWmwpP/Tcew0dYiWaA3NHoJRifOVM+\n");
        cer.append("Wswiv94OXqZCABxKdEXbql9iCnAUiow1LYZmgEg8GwnFXseTw4zwtObPh6rKuQVl\n");
        cer.append("2yySywk2zeDmAZ9hAh7IBmKVhWE/lfWrdhifaKtHuyulPrdfPKKOIR6Lb4ZAluDa\n");
        cer.append("/a5rp98tKdQogeuA4QIDAQABo4ICbjCCAmowDwYDVR0TAQH/BAUwAwEB/zAfBgNV\n");
        cer.append("HSMEGDAWgBSEv4ZkaGZ6bB/rpKK4RMOVAB3xbjCBhgYDVR0gBH8wfTB7BgorBgEE\n");
        cer.append("AYKmcgEJMG0wawYIKwYBBQUHAgEWX2h0dHBzOi8vd3d3LnNlY3VyaXR5ZGF0YS5u\n");
        cer.append("ZXQuZWMvd3AtY29udGVudC9kb3dubG9hZHMvTm9ybWF0aXZhcy9wX2NlcnRpZmlj\n");
        cer.append("YWNpb24vZGVjbGFyYWNpb24ucGRmMIIBfAYDVR0fBIIBczCCAW8wggFroIHEoIHB\n");
        cer.append("hoG+bGRhcDovL2xkYXBzZGNhMi5zZWN1cml0eWRhdGEubmV0LmVjL0NOPUFVVE9S\n");
        cer.append("SURBRCBERSBDRVJUSUZJQ0FDSU9OIFJBSVogQ0EtMiBTRUNVUklUWSBEQVRBLE9V\n");
        cer.append("PUVOVElEQUQgREUgQ0VSVElGSUNBQ0lPTiBERSBJTkZPUk1BQ0lPTixPPVNFQ1VS\n");
        cer.append("SVRZIERBVEEgUy5BLiAyLEM9RUM/YXV0aG9yaXR5UmV2b2NhdGlvbkxpc3Q/YmFz\n");
        cer.append("ZaKBoaSBnjCBmzE7MDkGA1UEAwwyQVVUT1JJREFEIERFIENFUlRJRklDQUNJT04g\n");
        cer.append("UkFJWiBDQS0yIFNFQ1VSSVRZIERBVEExMDAuBgNVBAsMJ0VOVElEQUQgREUgQ0VS\n");
        cer.append("VElGSUNBQ0lPTiBERSBJTkZPUk1BQ0lPTjEdMBsGA1UECgwUU0VDVVJJVFkgREFU\n");
        cer.append("QSBTLkEuIDIxCzAJBgNVBAYTAkVDMB0GA1UdDgQWBBSMusoRV3glgB1rCktVv42u\n");
        cer.append("Yt29jzAOBgNVHQ8BAf8EBAMCAYYwDQYJKoZIhvcNAQELBQADggIBAICvaRPs/FWq\n");
        cer.append("rIBCtnaIEH/PE+IpOBxG2AAzkkZ4M+zAObtTW7G8dTCBFBX22KWMt3O63jhrwWen\n");
        cer.append("5sCn/acs1sbyax21lBjgRWGHd1tCYgghb7BYRr/QoBhQ8E4z1M26HsEN2lPri7oP\n");
        cer.append("gEQkpTZJQhP9mNLYUwdIZa64pcTJHyM3+z+sT/Qisa4MP4cmd/UQVuAmydlU7sbA\n");
        cer.append("e8pFE2md/5R9PDQd29vs7OLb6tHTQAxkBoS3faEVXJhW6Kwa6jvFRgr5P4BVIGbo\n");
        cer.append("F9yooW/F7xiukyvHwSOLx8odf0X0gpg3mWIEe3hGe/HM/miHCob8KEDPnsFd1E99\n");
        cer.append("l+EPiLvjt2Q5pyAhk7/9WwivWmFeUPjt9FBjnRRMLr94utLCx2dnYEl+EHWV9KlS\n");
        cer.append("jRJI+LL9q/ekfnFUWI/r5IBh325WR7EC/hQPLuzpH54/ZKBgDs/0TqMN44Yb2N/u\n");
        cer.append("7sWZEHxlraQIZ6TPjK3jOV0U1lVO+cc9IblwSlONReYh8IAeqBgTuLT2UQONXjyp\n");
        cer.append("PdGuS1DFnSI3BsERswHalw392Xsz68+lJnTm89YreRMUD7C7X0iTTGILUP6t2EO1\n");
        cer.append("7NXdYr7iCfAzXjGQnoUwBf/b/331sweZLzXvII9YKSdWfWlGb7o8A8Gs5qCXxHz7\n");
        cer.append("SEm7ouyiw+vZY46dwodz3rnMb6hZkYTy\n");
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
