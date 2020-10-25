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
package ec.gob.firmadigital.firmador;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author mfernandez
 */
public final class JPanelVisualizadorPdf extends javax.swing.JPanel {

    private final File documento;
    private final int longitudRazon;
    private final int signatureWidth;
    private final int signatureHeight;
    private int tmpSignatureWidth;
    private int tmpSignatureHeight;
    private PDDocument pdfDocument;
    private PDFRenderer pdfRenderer;
    private BufferedImage bufferedImage = null;
    private ImageIcon iconImage;
    private final Point point = new Point();
    private SpinnerModel spNumPagesModel;
    private final Image image = new ImageIcon(this.getClass().getResource("/images/firmadigital.png")).getImage();
    private int width;
    private int height;
    private JLabel label;
    private int tmpPage = 0;
    private int tmpZoom = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        File documento = new File("/home/mfernandez/Test/Horizontal.pdf");
//        File documento = new File("/home/mfernandez/Descargas/Informe Tecnico Proyecto No 5400 BIESS.pdf");
//        File documento = new File("/home/mfernandez/Test/variosFormatos.pdf");
        File documento = new File("/home/mfernandez/Test/test.pdf");
        JPanelVisualizadorPdf jPanelFirmaPdf = new JPanelVisualizadorPdf(documento, 100, 153, 50);
        javax.swing.JButton btnEstampar = new javax.swing.JButton();
        btnEstampar.setText("Estampar");
        btnEstampar.setMnemonic(java.awt.event.KeyEvent.VK_E);
        btnEstampar.addActionListener((java.awt.event.ActionEvent evt) -> {
            java.awt.Component component = (java.awt.Component) evt.getSource();
            javax.swing.JDialog dialog = (javax.swing.JDialog) javax.swing.SwingUtilities.getRoot(component);
            dialog.dispose();
        });
        Object[] options = {btnEstampar};
        JOptionPane.showOptionDialog(null, jPanelFirmaPdf, "Visualizador PDF para estampado de firma en formato A4",
                JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    /**
     * Creates new form jPanelFirmaPdf
     *
     * @param documento
     * @param longitudRazon
     * @param signatureWidth
     * @param signatureHeight
     */
    public JPanelVisualizadorPdf(File documento, int longitudRazon, int signatureWidth, int signatureHeight) {
        this.documento = documento;
        this.longitudRazon = longitudRazon;
        this.signatureWidth = signatureWidth;
        this.signatureHeight = signatureHeight;
        initComponents();
        jtaRuta.setText(this.documento.getPath());
        getDocumento();
        // ===================== load preview =====================
        SpinnerModel spZoom = new SpinnerNumberModel(90, 45, 100, 5);
        jspZoom.setModel(spZoom);
        jspNumPages.setValue(pdfDocument.getNumberOfPages());
        loadPreview(getPagina(), getZoom());
        // ===================== load preview =====================
        eventos();
    }

    private void eventos() {
        //============= SET DIGITAL SIGN POSITION =====================
        jScrollPanel.getViewport().addChangeListener(e -> {
            if (tmpPage > 0 && tmpPage == getPagina()) {
                width = (preview.getWidth() - label.getWidth()) / 2;
                height = (preview.getHeight() - label.getHeight()) / 2;
                preview.getGraphics().drawImage(image, ((point.x * getZoom()) / tmpZoom) + width, ((point.y * getZoom()) / tmpZoom) + height, tmpSignatureWidth, tmpSignatureHeight, null);
            }
        });
        //============= SET PAGE =====================
        jspNumPages.addChangeListener(e -> {
            JSpinner jSpinner = (JSpinner) e.getSource();
            loadPreview((int) jSpinner.getValue(), (int) jspZoom.getValue());
            jspNumPages.requestFocus();
        });
        //============= SET ZOOM =====================
        jspZoom.addChangeListener(e -> {
            JSpinner jSpinner = (JSpinner) e.getSource();
            loadPreview((int) jspNumPages.getValue(), (int) jSpinner.getValue());
            jspZoom.requestFocus();
        });
        jtxRazonFirma.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (jtxRazonFirma.getText().length() < longitudRazon) {
                    ec.gob.firmadigital.utils.Presionar.presionarAlfanumericoEspacio(evt);
                } else {
                    evt.consume();
                }
                if (evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
                    evt.consume();
                }
            }
        });
    }

    private void eventoLabel() {
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            Point hotSpot = new Point(0, 0);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Cursor cursor = toolkit.createCustomCursor(image.getScaledInstance(tmpSignatureWidth, tmpSignatureHeight, Image.SCALE_DEFAULT), hotSpot, "");

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (Cursor.DEFAULT_CURSOR == getCursor().getType()) {
                    setCursor(cursor);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                switch (e.getButton()) {
                    case java.awt.event.MouseEvent.BUTTON1: {
                        setCursor(cursor);
                        preview.repaint();
                        width = (preview.getWidth() - label.getWidth()) / 2;
                        height = (preview.getHeight() - label.getHeight()) / 2;
                        if ((e.getPoint().x < iconImage.getIconWidth() - tmpSignatureWidth) && (e.getPoint().y < iconImage.getIconHeight() - tmpSignatureHeight)) {
                            javax.swing.JButton jbtnAceptar = new javax.swing.JButton();
                            jbtnAceptar.setText("Aceptar");
                            jbtnAceptar.setMnemonic(java.awt.event.KeyEvent.VK_A);
                            javax.swing.JButton jbtnCancelar = new javax.swing.JButton();
                            jbtnCancelar.setText("Cancelar");
                            jbtnCancelar.setMnemonic(java.awt.event.KeyEvent.VK_C);
                            Object[] options = {jbtnAceptar, jbtnCancelar};
                            jbtnAceptar.addActionListener((java.awt.event.ActionEvent evt) -> {
                                point.setLocation(e.getPoint().x, e.getPoint().y);
                                java.awt.Component component1 = (java.awt.Component) evt.getSource();
                                javax.swing.JDialog dialog = (javax.swing.JDialog) javax.swing.SwingUtilities.getRoot(component1);
                                dialog.dispose();
                            });
                            jbtnCancelar.addActionListener((java.awt.event.ActionEvent evt) -> {
                                point.setLocation(0, 0);
                                java.awt.Component component1 = (java.awt.Component) evt.getSource();
                                javax.swing.JDialog dialog = (javax.swing.JDialog) javax.swing.SwingUtilities.getRoot(component1);
                                dialog.dispose();
                            });
                            JOptionPane.showOptionDialog(null, "¿Insertar firma digital aquí?", "Datos de firma digital",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                        } else {
                            JOptionPane.showMessageDialog(preview, "Fuera de margen,\nSe estampa la firma en la posición superior izquierda de la hoja actual.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                            point.setLocation(0, 0);
                        }
                        tmpPage = getPagina();
                        tmpZoom = getZoom();
                        preview.getGraphics().drawImage(image, point.x + width, point.y + height, tmpSignatureWidth, tmpSignatureHeight, null);
                    }
                    System.out.println("zoom-> " + getZoom());
                    System.out.println("X-> " + point.getX() + ", Y->" + point.getY());
                    System.out.println("lblW-> " + label.getWidth() + ", lblH->" + label.getHeight());
                }
            }
        });
    }

    public File getDocumento() {
        try {
            pdfDocument = PDDocument.load(documento);
        } catch (IllegalArgumentException iae) {
            javax.swing.JOptionPane.showMessageDialog(null, "Problema en membrete del documento (cabecera y/o pie de pagina)\n" + documento, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger(JPanelVisualizadorPdf.class.getName()).log(java.util.logging.Level.SEVERE, null, iae);
        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Se ha detectado problemas al cargar el documento\n" + documento, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger(JPanelVisualizadorPdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return documento;
    }

    public String getRazonFirma() {
        return jtxRazonFirma.getText();
    }

    public int getPagina() {
        return (int) jspNumPages.getValue();
    }

    public int getZoom() {
        return (int) jspZoom.getValue();
    }

    public Point getPoint() throws IOException {
        PDDocument pdf = PDDocument.load(documento);
        PDPage page = pdf.getPage(getPagina() == 0 ? getPagina() : getPagina() - 1);
        PDRectangle pageSize = page.getMediaBox();
        int porcentajeX = (point.x * 72) / (int) (getZoom());
        int porcentajeY = (point.y * 72) / (int) (getZoom());
        System.out.println("porcentajeX-> " + porcentajeX + ", porcentajeY->" + porcentajeY);
        return new Point((porcentajeX), (int) (pageSize.getHeight() - (porcentajeY)));
    }

    //================= PREVIEW PDF ======================================================
    private BufferedImage loadPagePreview(int page, int zoom) {
        BufferedImage bim = null;
        try {
            bim = pdfRenderer.renderImageWithDPI(page == 0 ? page : page - 1, zoom, ImageType.RGB);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bim;
    }

    private boolean a4(PDRectangle pageSize) {
        int aproximado = 2;
        boolean a4Vertical = false;
        boolean a4Horizontal = false;
        /*A4 vertical*/
        if (pageSize.getWidth() >= (int) PDRectangle.A4.getWidth() - aproximado && pageSize.getWidth() <= (int) PDRectangle.A4.getWidth() + aproximado) {
            if (pageSize.getHeight() >= (int) PDRectangle.A4.getHeight() - aproximado && pageSize.getHeight() <= (int) PDRectangle.A4.getHeight() + aproximado) {
                a4Vertical = true;
            }
        }
        /*A4 horizontal*/
        if (pageSize.getWidth() >= (int) PDRectangle.A4.getHeight() - aproximado && pageSize.getWidth() <= (int) PDRectangle.A4.getHeight() + aproximado) {
            if (pageSize.getHeight() >= (int) PDRectangle.A4.getWidth() - aproximado && pageSize.getHeight() <= (int) PDRectangle.A4.getWidth() + aproximado) {
                a4Horizontal = true;
            }
        }
        return a4Vertical || a4Horizontal;
    }

    private void loadPreview(int page, int zoom) {
        tmpSignatureWidth = (signatureWidth * getZoom()) / 100;
        tmpSignatureHeight = (signatureHeight * getZoom()) / 100;

        pdfRenderer = new PDFRenderer(pdfDocument);
        bufferedImage = loadPagePreview(page, zoom);
        PDRectangle pageSize = pdfDocument.getPage(getPagina() == 0 ? getPagina() : getPagina() - 1).getMediaBox();
        if (!a4(pageSize)) {
            javax.swing.JOptionPane.showMessageDialog(null, "La hoja del documento no cumple con el formato A4 Horizontal (595 x 842 pixeles) o Vertical (842 x 595 pixeles)\n\nEl tamaño actual es: " + (int) pageSize.getHeight() + " x " + (int) pageSize.getWidth() + " pixeles", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        }

        spNumPagesModel = new SpinnerNumberModel(page, 1, pdfDocument.getNumberOfPages(), 1);
        jblNroPaginaTotal.setText("/ " + pdfDocument.getNumberOfPages());
        jspNumPages.setModel(spNumPagesModel);

        assert bufferedImage != null;
        iconImage = new ImageIcon(bufferedImage);
        label = new JLabel(iconImage, JLabel.CENTER);
        label.setSize(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));
        add(new JScrollPane(label));

        if (preview.getComponentCount() > 0) {
            preview.remove(preview.getComponent(0));
        }
        preview.setSize(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));
        preview.add(label, BorderLayout.PAGE_START);
        preview.setLayout(new FlowLayout());
        preview.setOpaque(true);
        preview.setBorder(BorderFactory.createLoweredBevelBorder());
        preview.repaint();
        eventoLabel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlblRazon = new javax.swing.JLabel();
        jtxRazonFirma = new javax.swing.JTextField();
        jblNroPagina = new javax.swing.JLabel();
        jspNumPages = new javax.swing.JSpinner();
        jblNroPaginaTotal = new javax.swing.JLabel();
        jblZoom = new javax.swing.JLabel();
        jspZoom = new javax.swing.JSpinner();
        jblNroZoom = new javax.swing.JLabel();
        jlblMensaje = new javax.swing.JLabel();
        jScrollPanel = new javax.swing.JScrollPane();
        preview = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaRuta = new javax.swing.JTextArea();
        jlblRuta = new javax.swing.JLabel();

        jlblRazon.setText("Razón de firma");

        jblNroPagina.setText("N° Página");

        jblNroPaginaTotal.setText("/");

        jblZoom.setText("Zoom");

        jblNroZoom.setText("/ 100%");

        jlblMensaje.setText("Favor, seleccione con el puntero el lugar donde estampará la firma");

        javax.swing.GroupLayout previewLayout = new javax.swing.GroupLayout(preview);
        preview.setLayout(previewLayout);
        previewLayout.setHorizontalGroup(
            previewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );
        previewLayout.setVerticalGroup(
            previewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 429, Short.MAX_VALUE)
        );

        jScrollPanel.setViewportView(preview);

        jtaRuta.setEditable(false);
        jtaRuta.setColumns(20);
        jScrollPane1.setViewportView(jtaRuta);

        jlblRuta.setText("<html>Ruta del<br>documento</html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblRazon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxRazonFirma)
                            .addComponent(jScrollPane1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jlblMensaje)
                        .addGap(18, 18, 18)
                        .addComponent(jblNroPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jspNumPages, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jblNroPaginaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jblZoom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jspZoom, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jblNroZoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxRazonFirma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblRazon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jspZoom, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jblZoom)
                    .addComponent(jblNroZoom)
                    .addComponent(jspNumPages, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jblNroPagina)
                    .addComponent(jblNroPaginaTotal)
                    .addComponent(jlblMensaje))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlblRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPanel;
    private javax.swing.JLabel jblNroPagina;
    private javax.swing.JLabel jblNroPaginaTotal;
    private javax.swing.JLabel jblNroZoom;
    private javax.swing.JLabel jblZoom;
    private javax.swing.JLabel jlblMensaje;
    private javax.swing.JLabel jlblRazon;
    private javax.swing.JLabel jlblRuta;
    private javax.swing.JSpinner jspNumPages;
    private javax.swing.JSpinner jspZoom;
    private javax.swing.JTextArea jtaRuta;
    private javax.swing.JTextField jtxRazonFirma;
    private javax.swing.JPanel preview;
    // End of variables declaration//GEN-END:variables
}
