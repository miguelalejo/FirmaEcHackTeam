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
package io.rubrica.certificate.ec.cj;

import java.security.cert.X509Certificate;

import io.rubrica.certificate.ec.CertificadoPersonaJuridica;

/**
 * Certificado de Persona Juridica emitido por el Consejo de la Judicatura.
 *
 * @author mfernandez
 */
public class CertificadoPersonaJuridicaPublicaConsejoJudicatura extends CertificadoConsejoJudicatura
        implements CertificadoPersonaJuridica {

    public CertificadoPersonaJuridicaPublicaConsejoJudicatura(X509Certificate certificado) {
        super(certificado);
    }

    @Override
    public String getRazonSocial() {
        return obtenerExtension(OID_RAZON_SOCIAL);
    }

    @Override
    public String getRuc() {
        return obtenerExtension(OID_RUC);
    }

    @Override
    public String getCedulaPasaporte() {
        return obtenerExtension(OID_CEDULA_PASAPORTE);
    }

    @Override
    public String getNombres() {
        return obtenerExtension(OID_NOMBRES);
    }

    @Override
    public String getPrimerApellido() {
        return obtenerExtension(OID_APELLIDO_1);
    }

    @Override
    public String getSegundoApellido() {
        return obtenerExtension(OID_APELLIDO_2);
    }

    @Override
    public String getCargo() {
        return obtenerExtension(OID_CARGO);
    }

    @Override
    public String getDireccion() {
        return obtenerExtension(OID_DIRECCION);
    }

    @Override
    public String getTelefono() {
        return obtenerExtension(OID_TELEFONO);
    }

    @Override
    public String getCiudad() {
        return obtenerExtension(OID_CIUDAD);
    }

    @Override
    public String getPais() {
        return obtenerExtension(OID_PAIS);
    }
}
