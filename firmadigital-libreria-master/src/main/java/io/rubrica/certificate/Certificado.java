/* 
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
package io.rubrica.certificate;

import io.rubrica.sign.cms.DatosUsuario;
import java.util.Calendar;

/**
 * Objeto para acceder informacion legible del certificado digital
 * 
 * @author mfernandez
 */
public class Certificado {

    private String issuedTo;
    private String issuedBy;
    private Calendar validFrom;
    private Calendar validTo;
    private Calendar generated;
    private Calendar revocated;
    private Boolean validated;
    private DatosUsuario datosUsuario;
    private Boolean docVerify;
    private String docReason;
    private String docLocation;

    public Certificado() {
    }

    public Certificado(String issuedTo, String issuedBy, Calendar validFrom, Calendar validTo, Calendar generated, Calendar revocated, Boolean validated, DatosUsuario datosUsuario) {
        this.issuedTo = issuedTo;
        this.issuedBy = issuedBy;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.generated = generated;
        this.revocated = revocated;
        this.validated = validated;
        this.datosUsuario = datosUsuario;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Calendar getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Calendar validFrom) {
        this.validFrom = validFrom;
    }

    public Calendar getValidTo() {
        return validTo;
    }

    public void setValidTo(Calendar validTo) {
        this.validTo = validTo;
    }

    public Calendar getGenerated() {
        return generated;
    }

    public void setGenerated(Calendar generated) {
        this.generated = generated;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public Calendar getRevocated() {
        return revocated;
    }

    public void setRevocated(Calendar revocated) {
        this.revocated = revocated;
    }

    public DatosUsuario getDatosUsuario() {
        return datosUsuario;
    }

    public Boolean getDocVerify() {
        return docVerify;
    }

    public void setDocVerify(Boolean docVerify) {
        this.docVerify = docVerify;
    }

    public String getDocReason() {
        return docReason;
    }

    public void setDocReason(String docReason) {
        this.docReason = docReason;
    }
    
    public String getDocLocation() {
        return docLocation;
    }
    
    public void setDocLocation(String docLocation) {
        this.docLocation = docLocation;
    }

    public void setDatosUsuario(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }

    @Override
    public String toString() {
        return "\tCertificado\n"
                + "\t[issuedTo=" + issuedTo + "\n"
                + "\tissuedBy=" + issuedBy + "\n"
                + "\tvalidFrom=" + validFrom + "\n"
                + "\tvalidTo=" + validTo + "\n"
                + "\tgenerated=" + generated + "\n"
                + "\trevocated=" + revocated + "\n"
                + "\tvalidated=" + validated + "\n"
                + "\tdocVerify=" + docVerify + "\n"
                + "\tdocReason=" + docReason + "\n"
                + "\tdocLocation=" + docLocation + "\n"
                + "\t" + (datosUsuario == null ? "\tDatosUsuario[Sin información de usuario]" : datosUsuario.toString()) + "\n"
                + "\t]"
                + "\n----------";
    }

}
