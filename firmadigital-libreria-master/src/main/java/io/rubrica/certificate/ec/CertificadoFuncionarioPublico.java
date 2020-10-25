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
package io.rubrica.certificate.ec;

/**
 * Certificado que identifica al suscriptor como funcionario o servidor publico,
 * quien actuara a titulo de la Institucion publica que representa y sera
 * responsable de todo lo que firme electronicamente dentro del ambito de su
 * actividad y limites de uso que correspondan.
 *
 * @author Ricardo Arguello <ricardo.arguello@soportelibre.com>
 */
public interface CertificadoFuncionarioPublico {

    /**
     * @return Cedula o Pasaporte del funcionario publico
     */
    String getCedulaPasaporte();

    /**
     * @return Nombre(s) del funcionario publico
     */
    String getNombres();

    /**
     * @return Primer apellido del funcionario publico
     */
    String getPrimerApellido();

    /**
     * @return Segundo apellido del funcionario publico (si no tiene queda en
     * blanco)
     */
    String getSegundoApellido();

    /**
     * @return Cargo
     */
    String getCargo();

    /**
     * @return Institucion
     */
    String getInstitucion();

    /**
     * @return Direccion
     */
    String getDireccion();

    /**
     * @return Telefono
     */
    String getTelefono();

    /**
     * @return Ciudad
     */
    String getCiudad();

    /**
     * @return Pais
     */
    String getPais();

    /**
     * @return RUC de la Institucion
     */
    String getRuc();

    /**
     * @return Razon Social
     */
    String getRazonSocial();
}
