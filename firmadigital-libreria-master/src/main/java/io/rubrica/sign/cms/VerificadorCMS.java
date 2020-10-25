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
package io.rubrica.sign.cms;

import io.rubrica.certificate.CertEcUtils;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;

import io.rubrica.exceptions.SignatureVerificationException;
import io.rubrica.utils.BouncyCastleUtils;
import java.util.Date;

/**
 * Verifica datos CMS.
 *
 * @author Ricardo Arguello <ricardo.arguello@soportelibre.com>
 */
public class VerificadorCMS {

    public List<DatosUsuario> listaDatosUsuario = new ArrayList<>();
    public List<X509Certificate> certificados;
    public List<Date> fechasFirmados;

    static {
        BouncyCastleUtils.initializeBouncyCastle();
    }

    public VerificadorCMS() {
    }

    public byte[] verify(byte[] signedBytes) throws SignatureVerificationException {
        try {
            CMSSignedData signedData = new CMSSignedData(signedBytes);

            Store<X509CertificateHolder> certStore = signedData.getCertificates();
            SignerInformationStore signerInformationStore = signedData.getSignerInfos();
            Collection<SignerInformation> collection = signerInformationStore.getSigners();
            String fechaFirma = "";

            certificados = new ArrayList<>();
            fechasFirmados = new ArrayList<>();

            for (SignerInformation signer : collection) {
                @SuppressWarnings("unchecked")
                Collection<X509CertificateHolder> certCollection = certStore.getMatches(signer.getSID());

                Iterator<X509CertificateHolder> certIt = certCollection.iterator();
                X509CertificateHolder certificateHolder = certIt.next();

                JcaX509CertificateConverter jcaX509CertificateConverter = new JcaX509CertificateConverter();
                X509Certificate x509Certificate = jcaX509CertificateConverter.setProvider("BC")
                        .getCertificate(certificateHolder);

                certificados.add(x509Certificate);

                AttributeTable attributes = signer.getSignedAttributes();

                if (attributes != null) {
                    Attribute messageDigestAttribute = attributes.get(CMSAttributes.signingTime);
                    ASN1UTCTime dt = (ASN1UTCTime) messageDigestAttribute.getAttrValues().getObjectAt(0);

                    try {
                        SimpleDateFormat f_DateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String dateStr = f_DateTime.format(dt.getDate());
                        fechasFirmados.add(dt.getDate());
                        System.out.println("Fecha Firma:" + dateStr);
                        fechaFirma = dateStr;
                    } catch (ParseException ex) {
                        Logger.getLogger(VerificadorCMS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (!signer
                        .verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(certificateHolder))) {
                    System.out.println("  --> " + certificateHolder.getSerialNumber());
                    System.out.println("  --> " + certificateHolder.getNotAfter());
                    throw new SignatureVerificationException("La firma no verifico con " + signer.getSID());
                }

                DatosUsuario datosUsuario = CertEcUtils.getDatosUsuarios(x509Certificate);
                datosUsuario.setSerial(x509Certificate.getSerialNumber().toString());
                datosUsuario.setFechaFirmaArchivo(fechaFirma);
                listaDatosUsuario.add(datosUsuario);
            }

            CMSProcessable signedContent = signedData.getSignedContent();
            return (byte[]) signedContent.getContent();
        } catch (GeneralSecurityException | CMSException e) {
            throw new RuntimeException(e);
        } catch (OperatorCreationException ex) {
            Logger.getLogger(VerificadorCMS.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
}
