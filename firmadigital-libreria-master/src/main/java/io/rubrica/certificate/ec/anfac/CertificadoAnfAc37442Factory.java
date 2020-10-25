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

import static io.rubrica.utils.BouncyCastleUtils.certificateHasPolicy;

import java.security.cert.X509Certificate;

/**
 * Permite construir certificados tipo Certificado ANF AC a partir de
 * certificados X509Certificate.
 * 
 * @author mfernandez
 */
public class CertificadoAnfAc37442Factory {
    public static boolean esCertificadoDeAnfAc37442(X509Certificate certificado) {
        byte[] valor = certificado.getExtensionValue(CertificadoAnfAc37442.OID_CEDULA_PASAPORTE);
        return (valor != null);
    }

    public static CertificadoAnfAc37442 construir(X509Certificate certificado) {
        if (!esCertificadoDeAnfAc37442(certificado)) {
            throw new IllegalStateException("Este no es un certificado emitido por ANF AC Ecuador");
        }

        if (certificateHasPolicy(certificado, CertificadoAnfAc37442.OID_CERTIFICADO_PERSONA_NATURAL)) {
            return new CertificadoPersonaNaturalAnfAc37442(certificado);
        } else if (certificateHasPolicy(certificado, CertificadoAnfAc37442.OID_CERTIFICADO_PERSONA_JURIDICA)) {
            return new CertificadoPersonaJuridicaAnfAc37442(certificado);
        } else if (certificateHasPolicy(certificado, CertificadoAnfAc37442.OID_CERTIFICADO_FUNCIONARIO_PUBLICO)) {
            return new CertificadoFuncionarioPublicoAnfAc37442(certificado);
        } else {
            throw new RuntimeException("Certificado ANF AC Ecuador de tipo desconocido!");
        }
    }
}
