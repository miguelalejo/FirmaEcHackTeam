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
package io.rubrica.sign.pdf;

import java.util.Properties;
import java.util.logging.Logger;

import com.lowagie.text.Rectangle;

public class PdfUtil {

    public static final String POSITION_ON_PAGE_LOWER_LEFT_X = "PositionOnPageLowerLeftX";
    public static final String POSITION_ON_PAGE_LOWER_LEFT_Y = "PositionOnPageLowerLeftY";
    public static final String POSITION_ON_PAGE_UPPER_RIGHT_X = "PositionOnPageUpperRightX";
    public static final String POSITION_ON_PAGE_UPPER_RIGHT_Y = "PositionOnPageUpperRightY";

    private static final Logger logger = Logger.getLogger(PdfUtil.class.getName());

    public static Rectangle getPositionOnPage(Properties extraParams) {
        if (extraParams == null) {
            logger.severe("Se ha pedido una posicion para un elemento grafico nulo");
            return null;
        }

        if (extraParams.getProperty(POSITION_ON_PAGE_LOWER_LEFT_X) != null
                && extraParams.getProperty(POSITION_ON_PAGE_LOWER_LEFT_Y) != null
                && extraParams.getProperty(POSITION_ON_PAGE_UPPER_RIGHT_X) != null
                && extraParams.getProperty(POSITION_ON_PAGE_UPPER_RIGHT_Y) != null) {
            try {
                return new Rectangle(Integer.parseInt(extraParams.getProperty(POSITION_ON_PAGE_LOWER_LEFT_X).trim()),
                        Integer.parseInt(extraParams.getProperty(POSITION_ON_PAGE_LOWER_LEFT_Y).trim()),
                        Integer.parseInt(extraParams.getProperty(POSITION_ON_PAGE_UPPER_RIGHT_X).trim()),
                        Integer.parseInt(extraParams.getProperty(POSITION_ON_PAGE_UPPER_RIGHT_Y).trim()));
            } catch (final Exception e) {
                logger.severe("Se ha indicado una posicion invalida para la firma: " + e);
            }
        }

        if (extraParams.getProperty(POSITION_ON_PAGE_LOWER_LEFT_X) != null
                && extraParams.getProperty(POSITION_ON_PAGE_LOWER_LEFT_Y) != null
                && extraParams.getProperty(POSITION_ON_PAGE_UPPER_RIGHT_X) == null
                && extraParams.getProperty(POSITION_ON_PAGE_UPPER_RIGHT_Y) == null) {
            try {
                return new Rectangle(Integer.parseInt(extraParams.getProperty(POSITION_ON_PAGE_LOWER_LEFT_X).trim()),
                        Integer.parseInt(extraParams.getProperty(POSITION_ON_PAGE_LOWER_LEFT_Y).trim()),
                        Integer.parseInt(extraParams.getProperty(POSITION_ON_PAGE_LOWER_LEFT_X).trim()) + 110,
                        Integer.parseInt(extraParams.getProperty(POSITION_ON_PAGE_LOWER_LEFT_Y).trim()) - 36);
            } catch (final Exception e) {
                logger.severe("Se ha indicado una posicion invalida para la firma: " + e);
            }
        }

        return null;
    }
}
