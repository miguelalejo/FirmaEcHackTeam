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
package io.rubrica.utils;

import io.rubrica.exceptions.HoraServidorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author mfernandez
 */
public class TiempoUtils {

    private static final Logger LOGGER = Logger.getLogger(TiempoUtils.class.getName());
    private static final int TIME_OUT = 5000; //set timeout to 5 seconds

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static Date getFechaHora() throws HoraServidorException {
        String fechaHora;
        try {
            fechaHora = getFechaHoraServidor();
        } catch (IOException e) {
            LOGGER.severe("No se puede obtener la fecha del servidor: " + e.getMessage());
            throw new HoraServidorException(PropertiesUtils.getMessages().getProperty("mensaje.error.problema_red"));
        }
        try {
            TemporalAccessor accessor = DATE_TIME_FORMATTER.parse(fechaHora);
            return Date.from(Instant.from(accessor));
        } catch (DateTimeParseException e) {
            LOGGER.severe("La fecha indicada ('" + fechaHora + "') no sigue el patron ISO-8601: " + e);
            return new Date();
        }
    }

    public static String getFechaHoraServidor() throws IOException {
        String fecha_hora_url = PropertiesUtils.getConfig().getProperty("fecha_hora_url");
        System.out.println("fecha_hora_url: " + fecha_hora_url);
        if (fecha_hora_url.isEmpty()) {
            // La fecha actual en formato ISO-8601 (2017-08-27T17:54:43.562-05:00)
            return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } else {
            URL url = new URL(fecha_hora_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(TIME_OUT);
            int responseCode = con.getResponseCode();
            LOGGER.fine("GET Response Code: " + responseCode);
            System.out.println("GET Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream is = con.getInputStream();) {
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader in = new BufferedReader(reader);

                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    return response.toString();
                }
            } else {
                throw new RuntimeException(PropertiesUtils.getMessages().getProperty("mensaje.error.problema_red"));
            }
        }
    }
}
