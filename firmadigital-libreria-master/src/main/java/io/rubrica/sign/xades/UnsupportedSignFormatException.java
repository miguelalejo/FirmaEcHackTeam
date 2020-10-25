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
package io.rubrica.sign.xades;

import io.rubrica.exceptions.RubricaException;

/**
 * Excepci&oacute;n lanzada cuando se detecta una firma con un formato no
 * reconocido o se indica un formato de firma no soportado.
 */
public final class UnsupportedSignFormatException extends RubricaException {

    private static final long serialVersionUID = -1;

    /**
     * Crea la excepci&oacute;n con un mensaje determinado.
     *
     * @param msg Mensaje descriptivo de la excepci&oacute;n.
     */
    public UnsupportedSignFormatException(final String msg) {
        super(msg);
    }

    /**
     * Crea la excepci&oacute;n con un mensaje determinado.
     *
     * @param msg Mensaje descriptivo de la excepci&oacute;n.
     * @param e Excepci&oacute;n que ha causado el lanzamiento de esta.
     */
    public UnsupportedSignFormatException(final String msg, final Exception e) {
        super(msg, e);
    }
}
