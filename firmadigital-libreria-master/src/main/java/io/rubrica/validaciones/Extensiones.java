/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.rubrica.validaciones;

import java.io.File;

/**
 *
 * @author mfernandez
 */
public class Extensiones {

    public final static String ODS = "ods";
    public final static String ODT = "odt";
    public final static String PDF = "pdf";
    public final static String TXT = "txt";
    public final static String XLS = "xls";
    public final static String XML = "xml";

    /*
	 * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
