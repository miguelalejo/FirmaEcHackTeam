/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.rubrica.ocsp;

import java.net.URI;
import java.security.*;
import java.security.cert.*;
import java.util.*;
import java.security.cert.X509Certificate;
import java.security.cert.PKIXParameters;

/**
 * Check the revocation status of a public key certificate using OCSP.
 */
public class ValidateCertUseOCSP {

    /**
     * Checks the revocation status of a public key certificate using OCSP.
     *
     * Usage: java ValidateCert <cert-file> [<OCSP-server>]
     * <cert-file> is the filename of the certificate to be checked. The
     * certificate must be in PEM format.
     * <OCSP-server> is the URL of the OCSP server to use. If not supplied then
     * the certificate must identify an OCSP server by means of its
     * AuthorityInfoAccess extension. If supplied then it overrides any URL
     * which may be present in the certificate's AuthorityInfoAccess extension.
     *
     * Example: java \ -Dhttp.proxyHost=proxy.example.net \
     * -Dhttp.proxyPort=8080 \ ValidateCert \ mycert.pem \
     * http://ocsp.openvalidation.org:80
     *
     * @param ocspCert
     * @param rootCACert
     * @param url
     */
    public static void validadorOCSP(X509Certificate ocspCert, X509Certificate rootCACert, String url) {
        try {
            CertPath cp = null;
            List<X509Certificate> certs = new ArrayList<>();
            URI ocspServer = null;

            // load the cert to be checked
            certs.add(ocspCert);

            // handle location of OCSP server
            ocspServer = new URI(url);
            //System.out.println("Using the OCSP server at: " + url);
            //System.out.println("to check the revocation status of: "+ certs.get(0));

            // init cert path
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            cp = (CertPath) cf.generateCertPath(certs);

            // init trusted certs
            TrustAnchor ta = new TrustAnchor(rootCACert, null);
            Set trustedCertsSet = new HashSet();
            trustedCertsSet.add(ta);

            // init cert store
            Set certSet = new HashSet();
            certSet.add(ocspCert);
            CertStoreParameters storeParams
                    = new CollectionCertStoreParameters(certSet);
            CertStore store = CertStore.getInstance("Collection", storeParams);

            // init PKIX parameters
            PKIXParameters params = null;
            params = new PKIXParameters(trustedCertsSet);
            params.addCertStore(store);
            params.setRevocationEnabled(false);

            // enable OCSP
            Security.setProperty("ocsp.enable", "true");
            if (ocspServer != null) {
                Security.setProperty("ocsp.responderURL", url);
                Security.setProperty("ocsp.responderCertSubjectName",
                        ocspCert.getSubjectX500Principal().getName());
            }

            // perform validation
            CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
            PKIXCertPathValidatorResult cpv_result
                    = (PKIXCertPathValidatorResult) cpv.validate(cp, params);
            X509Certificate trustedCert = (X509Certificate) cpv_result.getTrustAnchor().getTrustedCert();

            if (trustedCert == null) {
                System.out.println("Trsuted Cert = NULL");
            } else {
                System.out.println("Trusted CA DN = "
                        + trustedCert.getSubjectDN());
            }

        } catch (CertificateExpiredException | CertPathValidatorException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("CERTIFICATE VALIDATION SUCCEEDED");
        System.exit(0);
    }

}
