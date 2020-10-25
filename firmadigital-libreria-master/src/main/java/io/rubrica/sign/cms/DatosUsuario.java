/*
 * Firma Digital: Servicio
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.rubrica.sign.cms;

/**
 * Datos del usuario para contruir la validacion CMS.
 *
 * @author Ricardo Arguello <ricardo.arguello@soportelibre.com>
 */
public class DatosUsuario {

    private String cedula;
    private String nombre;
    private String apellido;
    private String institucion = "";
    private String cargo = "";
    private String serial;
    private String fechaFirmaArchivo;
    private String entidadCertificadora;

    public DatosUsuario() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getFechaFirmaArchivo() {
        return fechaFirmaArchivo;
    }

    public void setFechaFirmaArchivo(String fechaFirmaArchivo) {
        this.fechaFirmaArchivo = fechaFirmaArchivo;
    }

    public String getEntidadCertificadora() {
        return entidadCertificadora;
    }

    public void setEntidadCertificadora(String entidadCertificadora) {
        this.entidadCertificadora = entidadCertificadora;
    }

    @Override
    public String toString() {
        return "\tDatosUsuario\n"
                + "\t\t[cedula=" + cedula + "\n"
                + "\t\tnombre=" + nombre + "\n"
                + "\t\tapellido=" + apellido + "\n"
                + "\t\tinstitucion=" + institucion + "\n"
                + "\t\tcargo=" + cargo + "\n"
                + "\t\tentidadCertificadora=" + entidadCertificadora + "\n"
                + "\t\t]";
    }
}
