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
package ec.gob.firmadigital.utils;

import ec.gob.firmadigital.firmador.Main;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfernandez
 */
public class PropertiesUtils {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static Properties path;
    private static final String NAME_PROPERTIES = "/firmadigital.properties";
    private static final String DIRECTORY = System.getProperty("user.home");
    private static final String MESSAGES = "messages.firmador.properties";
    private static final String CONFIG = "config.firmador.properties";
    private static Properties messages;
    private static Properties config;

    public static Properties getMessages() {
        messages = new Properties();
        try {
            messages.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(MESSAGES));
        } catch (IOException ex) {
            Logger.getLogger(PropertiesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return messages;
    }

    public static Properties getConfig() {
        config = new Properties();
        try {
            config.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(CONFIG));
        } catch (IOException ex) {
            Logger.getLogger(PropertiesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config;
    }

    public static Properties getPath() {
        path = loadLastConfig();
        if (path.isEmpty()) {
            createLastConfig();
            LOGGER.info("Creating configuration file in: " + DIRECTORY);
        }
        return path;
    }

    public static String getDirectory() {
        return DIRECTORY;
    }

    public static Properties loadLastConfig() {
        Properties propTemp = new Properties();
        LOGGER.info("Base directory: " + DIRECTORY);
        InputStream input = null;
        try {
            input = new FileInputStream(DIRECTORY + NAME_PROPERTIES);
            // load a properties file
            propTemp.load(input);
        } catch (IOException ex) {
            LOGGER.severe("Reading file error: " + DIRECTORY + " " + ex.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.severe("Error on close file: " + e.getMessage());
                }
            }
        }
        return propTemp;
    }

    public static void createLastConfig() {
        Properties propTemp = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream(DIRECTORY + NAME_PROPERTIES);
            propTemp.store(output, "Temporal configurations");
            LOGGER.info("Creating temporal config file in: " + DIRECTORY);
        } catch (IOException io) {
            LOGGER.severe("Write to file error: +" + io.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void updateLastConfig(String pathTemp) {
        Properties propTemp = new Properties();
        propTemp.setProperty("user.home", pathTemp);

        OutputStream output = null;
        try {
            output = new FileOutputStream(DIRECTORY + NAME_PROPERTIES);
            propTemp.store(output, "Temporal configurations");
            LOGGER.info("Updating temporal config file in: " + DIRECTORY);
        } catch (IOException io) {
            LOGGER.severe("Write to file error: +" + io.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File loadLastCertFile() {
        return new File(path.getProperty("user.home"));
    }
}
