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

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;

/**
 *
 * @author jdc
 */
public class FileUtils {

    public static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] fileConvertToByteArray(File file) throws IOException {
        Path documentoPath = Paths.get(file.getAbsolutePath());
        return Files.readAllBytes(documentoPath);
    }

    public static void saveByteArrayToDisc(byte[] archivo, String rutaNombre) throws FileNotFoundException, IOException {
        // TODO validar si hay otro archivo de momento lo sobre escribe
        FileOutputStream fos = new FileOutputStream(rutaNombre);
        File arc = new File(rutaNombre);

        Long espacio = arc.getFreeSpace();

        if (archivo != null) {
            System.out.println("bytes: " + archivo.length + " espacio " + espacio);

            if (espacio < archivo.length) {
                throw new IOException("No se puede crear el archivo firmado. No hay espacio suficiente en el disco");
            }
            fos.write(archivo);
            fos.close();
        } else {
            throw new IOException("No se puede firmar un documento de 0 bytes");
        }
    }

    public static String crearNombreFirmado(File documento, String extension) throws IOException {
        String nombre = crearNombre(documento) + "-signed" + extension;
        if (new File(nombre).exists()) {
            nombre = crearNombreFirmado(new File(nombre + "_new"), extension);
        }
        return nombre;
    }

    public static String crearNombreVerificado(File documento, String extension) throws IOException {
        String hora = (TiempoUtils.getFechaHoraServidor().replace(":", "").replace(" ", "").replace(".", "").replace("-", "")).substring(0, 20);
        String nombre = crearNombre(documento);
        if (extension.isEmpty()) {
            extension = getExtension(nombre);
        }
        return nombre + "-verified-" + hora + extension;
    }

    private static String crearNombre(File documento) {
        String nombreCompleto = documento.getAbsolutePath();
        String nombre = nombreCompleto.replaceFirst("[.][^.]+$", "");
        return nombre;
    }

    public static String getExtension(byte[] documento) throws IOException, MimeTypeException {
        TikaConfig tikaConfig = TikaConfig.getDefaultConfig();
        Detector detector = new DefaultDetector();
        MediaType mediaType = detector.detect(new ByteArrayInputStream(documento), new Metadata());
        MimeType mimeType = tikaConfig.getMimeRepository().forName(mediaType.toString());
        return mimeType.getExtension();
    }

    public static String getExtension(String fileName) {
        String extension = "";
        if (fileName.contains(".")) {
            extension = "." + fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        return extension;
    }

    public static void abrirDocumento(String documento) throws IOException {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            String cmd = "rundll32 url.dll,FileProtocolHandler " + documento;
            Runtime.getRuntime().exec(cmd);
        } else {
            File doc = new File(documento);
            Desktop.getDesktop().open(doc);
        }
    }

    public static String rutaFichero(javax.swing.filechooser.FileNameExtensionFilter filtro) {
        String ruta = "";
        javax.swing.JFileChooser jFileChooser = new javax.swing.JFileChooser(new java.io.File(System.getProperty("user.home")));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.setEnabled(false);
        jFileChooser.setFileFilter(filtro);

        int resultado = jFileChooser.showOpenDialog(null);
        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = new java.io.File(jFileChooser.getSelectedFile().toString());
            if (file.exists() && file.isFile()) {
                for (String filtre : filtro.getExtensions()) {
                    if (getFileExtension(file).equals(filtre)) {
                        ruta = file.toString();
                    }
                }
            }
        }
        return ruta;
    }

    public static java.util.List<String> rutaFicheros(javax.swing.filechooser.FileNameExtensionFilter filtro) {
        java.util.List<String> ruta = new java.util.ArrayList<>();
        javax.swing.JFileChooser jFileChooser = new javax.swing.JFileChooser(new java.io.File(System.getProperty("user.home")));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.setEnabled(false);
        jFileChooser.setFileFilter(filtro);
        jFileChooser.setMultiSelectionEnabled(true);

        int resultado = jFileChooser.showOpenDialog(null);
        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File[] files = jFileChooser.getSelectedFiles();
            for (File file : files) {
                if (file.exists() && file.isFile()) {
                    for (String filtre : filtro.getExtensions()) {
                        if (getFileExtension(file).equals(filtre)) {
                            ruta.add(file.toString());
                        }
                    }
                }
            }
        }
        return ruta;
    }
}
