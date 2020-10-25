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
public class SecurityDataSubCaCert20192031 extends X509Certificate {

    private X509Certificate certificate;

    public SecurityDataSubCaCert20192031() {
        super();

        StringBuilder cer = new StringBuilder();
        cer.append("-----BEGIN CERTIFICATE-----\n");
        cer.append("MIIGrzCCBZegAwIBAgIEW/cwWTANBgkqhkiG9w0BAQsFADCBlDELMAkGA1UEBhMC\n");
        cer.append("RUMxGzAZBgNVBAoTElNFQ1VSSVRZIERBVEEgUy5BLjEwMC4GA1UECxMnRU5USURB\n");
        cer.append("RCBERSBDRVJUSUZJQ0FDSU9OIERFIElORk9STUFDSU9OMTYwNAYDVQQDEy1BVVRP\n");
        cer.append("UklEQUQgREUgQ0VSVElGSUNBQ0lPTiBSQUlaIFNFQ1VSSVRZIERBVEEwHhcNMTkw\n");
        cer.append("MjA3MTYwNjQ4WhcNMzEwMjA3MTYzNjQ4WjCBmTELMAkGA1UEBhMCRUMxHTAbBgNV\n");
        cer.append("BAoMFFNFQ1VSSVRZIERBVEEgUy5BLiAxMTAwLgYDVQQLDCdFTlRJREFEIERFIENF\n");
        cer.append("UlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04xOTA3BgNVBAMMMEFVVE9SSURBRCBE\n");
        cer.append("RSBDRVJUSUZJQ0FDSU9OIFNVQkNBLTEgU0VDVVJJVFkgREFUQTCCAiIwDQYJKoZI\n");
        cer.append("hvcNAQEBBQADggIPADCCAgoCggIBAJQ+jIthzA0Qqr8kkOAcr69u/9dGcajyp/+E\n");
        cer.append("Khve5AmGaA3lSF1A4L5q7+seYrS6Xisd4Ttbdcw3g55G7HR8zfVra5TunD8BiGMj\n");
        cer.append("DP7uTJHQ+N6hckYRckXJ4Vx0lKuGfsNYfM3t/hsQjKRHoXyFTeUNhXKFPJSy2O+h\n");
        cer.append("SNs2Oj3b6cRZEVJyOOIDcv+rfC0o6fPOmLUN4w/dkB9jSSHBnAKKsIOOQ6orDUZX\n");
        cer.append("Lin1eMhJj0S2glClkQKLiHMWTBwFgc+0PZuL9tNkugZi27Mw0ugNFvwijlgxcZeZ\n");
        cer.append("U6V878CVPukB7TxIv0AgySVZDjqMFDgE2HPF5Eham85kWs5RupTywKUQUoO7O4/e\n");
        cer.append("id3pBX4eEzyNEyKq/5H/t3ZZ9PLgU4+znBuaJgxcqeq6xitSduO8JCNLORplBB3y\n");
        cer.append("WovWBZM6GDa1WeiU8QhABTeixpiC0RrLfsbIUi1+uFd2Uqz1IFEJJWnaljk0iTp+\n");
        cer.append("4RLU1y8z3A096lx4Lgaxs8zEr1ETPRz1TysvRQLkr6gFhAC2SDMErXiDtKwTFNQh\n");
        cer.append("JyqaVLmG8yeRhUfheV6DJSPON1WAdoq1BYhl8g9lPiut+0YrtzpMw6wlg87qOGG1\n");
        cer.append("xTHE5FvJJXlCcN8ef0jtS649R8HdSjQwrzs5+mWtTE9azoVQghOJdE6BY7EEQUf6\n");
        cer.append("ZoE91qknAgMBAAGjggIAMIIB/DCCAZ0GA1UdHwSCAZQwggGQMIIBjKCCAYigggGE\n");
        cer.append("hoHYbGRhcDovL1NJU0xEQVAuU0VDVVJJVFlEQVRBLk5FVC5FQzoxMzg5L2NuPUNS\n");
        cer.append("TDEsY249QVVUT1JJREFEJTIwREUlMjBDRVJUSUZJQ0FDSU9OJTIwUkFJWiUyMFNF\n");
        cer.append("Q1VSSVRZJTIwREFUQSxvdT1FTlRJREFEJTIwREUlMjBDRVJUSUZJQ0FDSU9OJTIw\n");
        cer.append("REUlMjBJTkZPUk1BQ0lPTixvPVNFQ1VSSVRZJTIwREFUQSUyMFMuQS4sYz1FQz9h\n");
        cer.append("dXRob3JpdHlSZXZvY2F0aW9uTGlzdD9iYXNlpIGmMIGjMQswCQYDVQQGEwJFQzEb\n");
        cer.append("MBkGA1UEChMSU0VDVVJJVFkgREFUQSBTLkEuMTAwLgYDVQQLEydFTlRJREFEIERF\n");
        cer.append("IENFUlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04xNjA0BgNVBAMTLUFVVE9SSURB\n");
        cer.append("RCBERSBDRVJUSUZJQ0FDSU9OIFJBSVogU0VDVVJJVFkgREFUQTENMAsGA1UEAxME\n");
        cer.append("Q1JMMTALBgNVHQ8EBAMCAQYwHwYDVR0jBBgwFoAUlgOI1huMRCFc4mButfelH3Wh\n");
        cer.append("be4wHQYDVR0OBBYEFFwPhaR0EPytASwaAD2TFwn6OQHWMAwGA1UdEwQFMAMBAf8w\n");
        cer.append("DQYJKoZIhvcNAQELBQADggEBAKH5jIyxhY7NWb3MVFSINbyLchYaZ+1ns/M3Jc4K\n");
        cer.append("7/G2HzYonIcdlD31d1RqByUXBnmED5h5Clj19sXlaBJ0dEiEvnNNLwSYp+DQ1bMz\n");
        cer.append("TzJb/dRTpK9LcE8+VqZtFj2vDQ20IuoNbpe6ydnVtASv3wWn0jjjORoVJI5eaehr\n");
        cer.append("piAZn1QJLWS/3Uwq8O3aaJG23sP3gRALGCBwP4nc6xRVBMru23fzKeDoKee498hS\n");
        cer.append("XpN3eF1wpEdQNCVhXldbg3NvPiiBOKlLZAwwH5vArydgKZSDg+PiWewcyr35dZZr\n");
        cer.append("MmsWx83dy2T+PYgaDYSE+wzKszz3EDoR0qMyBBXAHspU7Kk=\n");
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
