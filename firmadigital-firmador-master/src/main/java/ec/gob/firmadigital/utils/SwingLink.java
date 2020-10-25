/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.firmadigital.utils;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandez
 */
public class SwingLink extends JLabel {

    private String text;
    private String uriText;

    public SwingLink(String text, String uriText, URI uri) {
        super();
        setup(text, uriText, uri);
    }

    public SwingLink(String text, String uriText, String uri) {
        super();
        setup(text, uriText, URI.create(uri));
    }

    public SwingLink(String uriText, URI uri) {
        super();
        setup("", uriText, uri);
    }

    public SwingLink(String uriText, String uri) {
        super();
        setup("", uriText, URI.create(uri));
    }

    public void setup(String text, String uriText, URI uri) {
        this.text = text;
        this.uriText = uriText;
        setText(uriText);
        setToolTipText(uri.toString());
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                open(uri);
            }

            public void mouseEntered(MouseEvent e) {
                setText(text, uriText, false);
            }

            public void mouseExited(MouseEvent e) {
                setText(text, uriText, true);
            }
        });
    }

    @Override
    public void setText(String uriText) {
        setText(text, uriText, true);
    }

    public void setText(String text, String uriText, boolean ul) {
        String link = ul ? "<u>" + uriText + "</u>" : uriText;
        super.setText("<html>" + text + " <span style=\"color: #000099;\">"
                + link + "</span></html>");
        this.uriText = uriText;
    }

    public String getRawUriText() {
        return uriText;
    }

    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(uri);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Failed to launch the link, your computer is likely misconfigured.",
                        "Cannot Launch Link", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Java is not able to launch links on your computer.",
                    "Cannot Launch Link", JOptionPane.WARNING_MESSAGE);
        }
    }
}
