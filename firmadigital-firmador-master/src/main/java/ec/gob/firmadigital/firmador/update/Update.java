/*
 * Firma Digital: Firmador
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
package ec.gob.firmadigital.firmador.update;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Permite actualizar el JAR de la aplicacion.
 *
 * @author Ricardo Arguello <ricardo.arguello@soportelibre.com>
 */
public class Update {

    private static final int TIME_OUT = 5000; //set timeout to 5 seconds
    private static final String JAR_BASE_URL = "https://www.firmadigital.gob.ec/firmaec";
    private static final String FIRMADOR_JAR_NAME = "firmador-jar-with-dependencies.jar";
    private static final String CLIENTE_JAR_NAME = "cliente-jar-with-dependencies.jar";

    private static String ambiente;
    private static String firmador_jar_url;
    private static String firmador_jar_sha256_url;
    private static String cliente_jar_url;
    private static String cliente_jar_sha256_url;

    private static final int BUFFER_SIZE = 8192;

    private static final Logger LOGGER = Logger.getLogger(Update.class.getName());

    private static final char[] HEXCODE = "0123456789ABCDEF".toCharArray();

    public static void setAmbiente(String ambiente) {
        Update.ambiente = ambiente;
        generarURL();
    }

    private static void generarURL() {
        firmador_jar_url = JAR_BASE_URL + "/" + (ambiente.equals("--updatedes") ? "des_" : "") + FIRMADOR_JAR_NAME;
        firmador_jar_sha256_url = JAR_BASE_URL + "/" + (ambiente.equals("--updatedes") ? "des_" : "") + FIRMADOR_JAR_NAME + ".sha256";
        cliente_jar_url = JAR_BASE_URL + "/" + (ambiente.equals("--updatedes") ? "des_" : "") + CLIENTE_JAR_NAME;
        cliente_jar_sha256_url = JAR_BASE_URL + "/" + (ambiente.equals("--updatedes") ? "des_" : "") + CLIENTE_JAR_NAME + ".sha256";
    }

    public File actualizarFirmador() throws IllegalArgumentException {
        // Se debe descargar?
        String path = rutaJar();
        LOGGER.info("path=" + path);

        File file = new File(path);
        LOGGER.info("file=" + file.getAbsolutePath() + "; canWrite=" + file.canWrite() + ";file.getName()="
                + file.getName());

        if (file.canWrite()) {
            return file;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public File actualizarCliente() throws IllegalArgumentException {
        // Se debe descargar?
        String path = rutaJar();
        LOGGER.info("path=" + path);

        File file = new File(path);
        String firmaDigitalJar = file.getParent() + File.separator + CLIENTE_JAR_NAME;
        File firmaDigital = new File(firmaDigitalJar);
        LOGGER.info("file=" + firmaDigital.getAbsolutePath() + "; canWrite=" + firmaDigital.canWrite() + ";file.getName()="
                + firmaDigital.getName());

        if (firmaDigital.canWrite()) {
            return firmaDigital;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String update() throws IOException {
        String hashBajado = new String(download(firmador_jar_sha256_url, true));
        String hash = hashBajado.split("\\s")[0];

        File file = new File(rutaJar());
        byte[] actualJar = Files.readAllBytes(file.toPath());
        String actualHash = generateHash(actualJar);

        String mensaje = null;
        if (!actualHash.equals(hash)) {
            mensaje = "Se encuentra disponible una actualización.<br>Mayor información en el manual de usuario disponible en ";
        }
        return mensaje;
    }

    public void updateFirmador(File file) throws IOException {
        String hashBajado = new String(download(firmador_jar_sha256_url, false));
        LOGGER.info("hashBajado=" + hashBajado);
        String hash = hashBajado.split("\\s")[0];

        byte[] actualJar = Files.readAllBytes(file.toPath());
        String actualHash = generateHash(actualJar);
        LOGGER.info("actualHash=" + actualHash);

        if (actualHash.equals(hash)) {
            LOGGER.info("Ya tiene el ultimo archivo!");
            return;
        } else {
            LOGGER.info("No tiene la ultima version, descargando...");
        }

        // Descargar JAR actualizado
        byte[] jar = download(firmador_jar_url, false);

        if (!verifyHash(hash, jar)) {
            LOGGER.severe("ERROR de verificacion de hash");
            return;
        }

        LOGGER.info("Hash comprobado OK");

        if (!file.getName().equals(FIRMADOR_JAR_NAME)) {
            LOGGER.severe("El nombre del archivo no es " + FIRMADOR_JAR_NAME);
            return;
        }

        if (!file.canWrite()) {
            LOGGER.severe("No se puede actualizar el archivo");
            return;
        }

        try (FileOutputStream fileOuputStream = new FileOutputStream(file)) {
            fileOuputStream.write(jar);
            LOGGER.info("Actualizado con exito!!!");
            return;
        }
    }

    public void updateCliente(File file) throws IOException {
        String hashBajado = new String(download(cliente_jar_sha256_url, false));
        LOGGER.info("hashBajado=" + hashBajado);
        String hash = hashBajado.split("\\s")[0];

        byte[] actualJar = Files.readAllBytes(file.toPath());
        String actualHash = generateHash(actualJar);
        LOGGER.info("actualHash=" + actualHash);

        if (actualHash.equals(hash)) {
            LOGGER.info("Ya tiene el ultimo archivo!");
            return;
        } else {
            LOGGER.info("No tiene la ultima version, descargando...");
        }

        // Descargar JAR actualizado
        byte[] jar = download(cliente_jar_url, false);

        if (!verifyHash(hash, jar)) {
            LOGGER.severe("ERROR de verificacion de hash");
            return;
        }

        LOGGER.info("Hash comprobado OK");

        if (!file.getName().equals(CLIENTE_JAR_NAME)) {
            LOGGER.severe("El nombre del archivo no es " + CLIENTE_JAR_NAME);
            return;
        }

        if (!file.canWrite()) {
            LOGGER.severe("No se puede actualizar el archivo");
            return;
        }

        try (FileOutputStream fileOuputStream = new FileOutputStream(file)) {
            fileOuputStream.write(jar);
            LOGGER.info("Actualizado con exito!!!");
            return;
        }
    }

    private byte[] download(String strUrl, boolean controlTiempo) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (controlTiempo == true) {
            con.setConnectTimeout(TIME_OUT);
        }

        int responseCode = con.getResponseCode();
        if (responseCode >= 300 && responseCode < 400) {
            con = (HttpURLConnection) new URL(con.getHeaderField("Location")).openConnection();
            if (controlTiempo == true) {
                con.setConnectTimeout(TIME_OUT);
            }
            responseCode = con.getResponseCode();
        }
        if (responseCode >= 400) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        byte[] buffer = new byte[BUFFER_SIZE];
        int count;
        long size = con.getContentLength();
        LOGGER.info("size=" + size);

        try (InputStream in = con.getInputStream(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }

            return out.toByteArray();
        }
    }

    private boolean verifyHash(String hash, byte[] jar) {
        return hash.equals(generateHash(jar));
    }

    private String generateHash(byte[] jar) {
        try {
            LOGGER.info("Hashing...");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(jar);
            byte[] digest = md.digest();
            return printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtener la ruta donde se almacena el JAR que contiene esta clase!
     *
     * @return
     */
    private String rutaJar() {
        try {
            return Update.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(HEXCODE[(b >> 4) & 0xF]);
            r.append(HEXCODE[(b & 0xF)]);
        }
        return r.toString();
    }
}
