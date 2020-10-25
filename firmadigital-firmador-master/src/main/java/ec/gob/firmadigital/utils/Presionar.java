/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.firmadigital.utils;

/**
 *
 * @author mfernandez
 */
public class Presionar {

    final private static char COMILLA_SIMPLE = 39;// '
    final private static char MINUS = 45;//-
    final private static char UNDERSCORE = 95;//_
    final private static char AMPERSAND = 38;//&
    final private static char ARROBA = 64;//@
    final private static char SLASH = 47;// /
    final private static char ASTERISCO = 42;// *

    /**
     * @param evt
     * @autor Misayo Metodo que permite presionar valores alfanuméricos
     */
    public static void presionarAlfanumericoEspacio(java.awt.event.KeyEvent evt) {
        char key = evt.getKeyChar();
        if ((key != java.awt.event.KeyEvent.VK_SPACE)
                && (key != UNDERSCORE
                && key != COMILLA_SIMPLE
                && key != MINUS
                && key != ARROBA
                && key != SLASH
                && key != ASTERISCO
                && key != AMPERSAND)
                && (key < '0' || key > '9')
                && (key < 'a' || key > 'z') && (key != 'ñ')
                && (key != 'á') && (key != 'é') && (key != 'í') && (key != 'ó') && (key != 'ú')
                && (key < 'A' || key > 'Z') && (key != 'Ñ')
                && (key != 'Á') && (key != 'É') && (key != 'Í') && (key != 'Ó') && (key != 'Ú')) {
            evt.consume();
        }
    }

    /**
     * @param evt
     * @autor Misayo Metodo que permite presionar solo numeros
     */
    public static void presionarNumeros(java.awt.event.KeyEvent evt) {
        char key = evt.getKeyChar();
        if ((key < '0' || key > '9')
                && (key != java.awt.event.KeyEvent.VK_DELETE)
                && (key != java.awt.event.KeyEvent.VK_BACK_SPACE)
                && (key != java.awt.event.KeyEvent.VK_ENTER)) {
            evt.consume();
        }
    }
}
