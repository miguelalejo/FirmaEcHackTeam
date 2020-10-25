/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package io.rubrica.keystore;

import java.io.File;

/**
 * KeyStoreProvider para tokens de Bit4id.
 *
 * @author mfernandez
 */
public class Bit4IdAppleKeyStoreProvider extends PKCS11KeyStoreProvider {

    private static final String CONFIG;
    private static final String DRIVER_FILE = "/Applications/FourIdentity/4identity.app/Contents/Resources/pkcs11/libbit4xpki.dylib";

    static {
        StringBuilder config = new StringBuilder();
        config.append("name=Bit4Id\n");
        config.append("library=" + DRIVER_FILE);
        CONFIG = config.toString();
    }

    @Override
    public String getConfig() {
        return CONFIG;
    }

    @Override
    public boolean existeDriver() {
        File driver = new File(DRIVER_FILE);
        return driver.exists();
    }
}
