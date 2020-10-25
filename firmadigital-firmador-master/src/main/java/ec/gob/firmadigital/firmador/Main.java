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

import io.rubrica.certificate.Certificado;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ec.gob.firmadigital.cliente.FirmaDigital;
import io.rubrica.exceptions.CertificadoInvalidoException;
import io.rubrica.utils.DocumentoException;
import ec.gob.firmadigital.exceptions.DocumentoNoExistenteException;
import ec.gob.firmadigital.exceptions.DocumentoNoPermitidoException;
import ec.gob.firmadigital.exceptions.RazonFirma;
import ec.gob.firmadigital.exceptions.TokenNoConectadoException;
import ec.gob.firmadigital.exceptions.TokenNoEncontradoException;
import ec.gob.firmadigital.firmador.update.Update;
import ec.gob.firmadigital.utils.Log;
import io.rubrica.utils.FileUtils;
import ec.gob.firmadigital.utils.PropertiesUtils;
import ec.gob.firmadigital.utils.SwingLink;
import ec.gob.firmadigital.utils.WordWrapCellRenderer;
import io.rubrica.certificate.CertEcUtils;
import io.rubrica.certificate.CertUtils;
import io.rubrica.exceptions.EntidadCertificadoraNoValidaException;
import io.rubrica.exceptions.HoraServidorException;
import io.rubrica.exceptions.RubricaException;
import io.rubrica.keystore.FileKeyStoreProvider;
import io.rubrica.keystore.KeyStoreProvider;
import io.rubrica.keystore.KeyStoreProviderFactory;
import io.rubrica.sign.cms.DatosUsuario;
import io.rubrica.utils.OsUtils;
import io.rubrica.utils.Utils;
import io.rubrica.utils.X509CertificateUtils;
import io.rubrica.keystore.Bit4IdWindosKeyStoreProvider;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FocusTraversalPolicy;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.security.InvalidKeyException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ProgressMonitor;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author jdcalle
 */
public class Main extends javax.swing.JFrame {

    private File documento;
    private static String version;
    private String alias;
    private String razonFirma;
    private Point point;
    private int pagina;
    private java.util.List<String> extensionesCertificados;
    private java.util.List<String> extensionesDocumentos;
    private final FileNameExtensionFilter filtroDocumentos = new FileNameExtensionFilter("Documentos de Oficina", "pdf", "p7m", "odt", "ods", "odp", "xml","docx", "xlsx");
//    private final FileNameExtensionFilter filtroDocumentos = new FileNameExtensionFilter("Documentos de Oficina", "pdf", "p7m", "docx", "xlsx", "pptx", "odt", "ods", "odp", "xml");
    private final FileNameExtensionFilter filtroCertificados = new FileNameExtensionFilter("Certificado Digital", "pfx", "p12");
    private static final String OS = System.getProperty("os.name").toLowerCase();
    X509CertificateUtils x509CertificateUtils = null;

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static Properties messages;
    private static Properties config;

    private final JButton btnSi = new JButton();
    private final JButton btnNo = new JButton();
    private final JButton btnAceptar = new JButton();

    /**
     * Creates new form Main
     *
     * @param args
     */
    public Main(String[] args) {
        initComponents();
        inicializarTabla();
        mnemonic();
        tabulacion();
        eventos();
        dragAndDrop();
        setTitle("FirmaEC " + version);
    }

    private void mnemonic() {
        //mainPanel
        mainPanel.setMnemonicAt(0, KeyEvent.VK_1);
        mainPanel.setMnemonicAt(1, KeyEvent.VK_2);
        mainPanel.setMnemonicAt(2, KeyEvent.VK_3);
        //jplFirmarDocumento
        jbtnFirmarExaminarCertificado.setMnemonic(java.awt.event.KeyEvent.VK_E);
        jbtnFirmarExaminarDocumentos.setMnemonic(java.awt.event.KeyEvent.VK_X);
        jbtnFirmarEliminarDocumentos.setMnemonic(java.awt.event.KeyEvent.VK_L);
        jbtnFirmar.setMnemonic(java.awt.event.KeyEvent.VK_F);
        jbtnRestablecerFirmar.setMnemonic(java.awt.event.KeyEvent.VK_R);
        //jplVerificarDocumento
        jbtnVerificar.setMnemonic(java.awt.event.KeyEvent.VK_V);
        jbtnVerificarExaminar.setMnemonic(java.awt.event.KeyEvent.VK_E);
        jbtnRestablecerVerificar.setMnemonic(java.awt.event.KeyEvent.VK_R);
        //jplValidarCertificado
        jbtnValidar.setMnemonic(java.awt.event.KeyEvent.VK_V);
        jbtnValidarExaminar.setMnemonic(java.awt.event.KeyEvent.VK_E);
        jbtnValidarRestablecer.setMnemonic(java.awt.event.KeyEvent.VK_R);
    }

    private void tabulacion() {
        //jplFirmarDocumento
        jpfFirmarClave.setNextFocusableComponent(jchkBoxFirmaInvisible);
        jchkBoxFirmaInvisible.setNextFocusableComponent(jbtnFirmarExaminarDocumentos);
        jbtnFirmarExaminarDocumentos.setNextFocusableComponent(jbtnFirmarEliminarDocumentos);
        jbtnFirmarEliminarDocumentos.setNextFocusableComponent(jbtnFirmar);
        jbtnFirmar.setNextFocusableComponent(jbtnRestablecerFirmar);
        //jplVerificarDocumento
        jtxtVerificarRuta.setNextFocusableComponent(jbtnVerificarExaminar);
        //jplValidarCertificado
        jtxtValidarRuta.setNextFocusableComponent(jbtnValidarExaminar);
        jbtnValidarExaminar.setNextFocusableComponent(jpfValidarClave);
        jpfValidarClave.setNextFocusableComponent(jbtnValidar);
        jbtnValidar.setNextFocusableComponent(jbtnValidarRestablecer);
    }

    private void eventos() {
        jtblFirmarDocumentos.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
                    String ruta = (String) tableModelDocumentos.getValueAt(jtblFirmarDocumentos.convertRowIndexToModel(jtblFirmarDocumentos.getSelectedRow()), 0);
                    if (jtblFirmarDocumentos.getSelectedRows().length > 0 && ruta != null && !ruta.isEmpty()) {
                        try {
                            FileUtils.abrirDocumento(ruta);
                        } catch (IOException ex) {
                            Logger.getLogger(JPanelVisualizadorPdf.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    evt.consume();
                }
            }
        });
        jtblFirmarDocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String ruta = (String) tableModelDocumentos.getValueAt(jtblFirmarDocumentos.convertRowIndexToModel(jtblFirmarDocumentos.getSelectedRow()), 0);
                    if (jtblFirmarDocumentos.getSelectedRows().length > 0 && ruta != null && !ruta.isEmpty()) {
                        try {
                            FileUtils.abrirDocumento(ruta);
                        } catch (IOException ex) {
                            Logger.getLogger(JPanelVisualizadorPdf.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        jtblFirmarDocumentos.getModel().addTableModelListener((TableModelEvent evt) -> {
            jlblDocumentosFirmar.setText("<html><b>" + tableModelDocumentos.getRowCount() + " DOCUMENTO(S) SELECCIONADO(S)</b></html>");
        });
    }

    private void inicializarTabla() {
        tableModelDocumentos = (DefaultTableModel) jtblFirmarDocumentos.getModel();
        jtblFirmarDocumentos.getColumnModel().getColumn(0).setCellRenderer(new WordWrapCellRenderer());

        jtblVerificarDatosFirmante.getColumnModel().getColumn(0).setCellRenderer(new WordWrapCellRenderer());
        jtblVerificarDatosFirmante.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());
        jtblVerificarDatosFirmante.getColumnModel().getColumn(2).setCellRenderer(new WordWrapCellRenderer());
        jtblVerificarDatosFirmante.getColumnModel().getColumn(3).setCellRenderer(new WordWrapCellRenderer());
        jtblVerificarDatosFirmante.getColumnModel().getColumn(4).setCellRenderer(new WordWrapCellRenderer());
        jtblVerificarDatosFirmante.getColumnModel().getColumn(5).setCellRenderer(new WordWrapCellRenderer());
    }

    private void dragAndDrop() {
        //DRAG & DROP
        extensionesCertificados = new java.util.ArrayList<>(Arrays.asList(filtroCertificados.getExtensions()));
        extensionesDocumentos = new java.util.ArrayList<>(Arrays.asList(filtroDocumentos.getExtensions()));
        //FIRMAR DOCUMENTO
        jtxtFirmarRuta.setTransferHandler(new ec.gob.firmadigital.utils.TextFieldTransferHandlerComponent(extensionesCertificados));
        //VERIFICAR DOCUMENTO
        jtxtVerificarRuta.setTransferHandler(new ec.gob.firmadigital.utils.TextFieldTransferHandlerComponent(extensionesDocumentos));
        //VALIDAR CERTIFICADO DE FIRMA ELECTRÓNICA
        jtxtValidarRuta.setTransferHandler(new ec.gob.firmadigital.utils.TextFieldTransferHandlerComponent(extensionesCertificados));
        //VALIDAR DOCUMENTOS
        jScrollPaneFirmarDocumentos.setTransferHandler(new ec.gob.firmadigital.utils.TableTransferHandlerComponent(extensionesDocumentos));
        //DRAG & DROP
        if (!OsUtils.isMac()) {
            ec.gob.firmadigital.utils.TableTransferHandlerComponent.iconInformation(jtblFirmarDocumentos, jplDocumentos, "documento(s)");
            ec.gob.firmadigital.utils.TextFieldTransferHandlerComponent.iconInformation(jtxtFirmarRuta, jplCertificadoFirmar, "certificado digital");
            ec.gob.firmadigital.utils.TextFieldTransferHandlerComponent.iconInformation(jtxtVerificarRuta, jplDocumentoVerificar, "documento firmado");
            ec.gob.firmadigital.utils.TextFieldTransferHandlerComponent.iconInformation(jtxtValidarRuta, jplCertificadoValidar, "certificado digital");
        }
    }

    private void nullDragAndDrop() {
        //FIRMAR DOCUMENTO
        jtxtFirmarRuta.setTransferHandler(null);
        //VERIFICAR DOCUMENTO
        jtxtVerificarRuta.setTransferHandler(null);
        //VALIDAR CERTIFICADO DE FIRMA ELECTRÓNICA
        jtxtValidarRuta.setTransferHandler(null);
        //VALIDAR DOCUMENTOS
        jScrollPaneFirmarDocumentos.setTransferHandler(null);
        //DRAG & DROP
        if (!OsUtils.isMac()) {
            ec.gob.firmadigital.utils.TableTransferHandlerComponent.iconInformation(jtblFirmarDocumentos, jplDocumentos, "documento(s)");
            ec.gob.firmadigital.utils.TextFieldTransferHandlerComponent.iconInformation(jtxtFirmarRuta, jplCertificadoFirmar, "certificado digital");
            ec.gob.firmadigital.utils.TextFieldTransferHandlerComponent.iconInformation(jtxtVerificarRuta, jplDocumentoVerificar, "documento firmado");
            ec.gob.firmadigital.utils.TextFieldTransferHandlerComponent.iconInformation(jtxtValidarRuta, jplCertificadoValidar, "certificado digital");
        }
    }

    public static Component findPrevFocus() {
        Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        Container root = c.getFocusCycleRootAncestor();

        FocusTraversalPolicy policy = root.getFocusTraversalPolicy();
        Component prevFocus = policy.getComponentBefore(root, c);
        if (prevFocus == null) {
            prevFocus = policy.getDefaultComponent(root);
        }
        return prevFocus;
    }

    private void resetFirmarDocumento() {
        this.documento = null;
        this.tipoFirmaBtnGRP.clearSelection();
        this.jbtnFirmar.setEnabled(false);
        this.jpfFirmarClave.setText("");
        this.jpfFirmarClave.setEnabled(false);
        this.jtxtFirmarRuta.setEnabled(false);
        this.jtxtFirmarRuta.setText("");
        this.jchkBoxFirmaInvisible.setSelected(false);

        this.jbtnFirmarExaminarCertificado.setEnabled(false);
    }

    private void selFirmarConArchivo() {
        this.jbtnFirmar.setEnabled(true);
        this.jtxtFirmarRuta.setEnabled(true);
        this.jbtnFirmarExaminarCertificado.setEnabled(true);
        // Si es windows no hay que habilitar el campo de contraseña
        this.jpfFirmarClave.setEnabled(true);
    }

    private void selValidarArchivo() {
        jtxtValidarRuta.setEnabled(true);
        jbtnValidarExaminar.setEnabled(true);
        jpfValidarClave.setEnabled(true);
        jpfValidarClave.setText("");
    }

    private void selFirmarConToken() {
        this.jbtnFirmar.setEnabled(true);
        this.jtxtFirmarRuta.setEnabled(false);
        this.jtxtFirmarRuta.setText("");
        this.jpfFirmarClave.setEnabled(false);
        this.jpfFirmarClave.setText("");
        this.jbtnFirmarExaminarCertificado.setEnabled(false);

        if (!esWindows()) {
            this.jpfFirmarClave.setEnabled(true);
        } else {
            this.jpfFirmarClave.setEnabled(false);
        }
    }
    
    private void selFirmarConHSM() {
        this.jbtnFirmar.setEnabled(true);
        this.jtxtFirmarRuta.setEnabled(false);
        this.jtxtFirmarRuta.setText("");
        this.jpfFirmarClave.setEnabled(false);
        this.jpfFirmarClave.setText("");
        this.jbtnFirmarExaminarCertificado.setEnabled(false);

        if (!esWindows()) {
            this.jpfFirmarClave.setEnabled(true);
        } else {
            this.jpfFirmarClave.setEnabled(false);
        }
    }

    private void selValidarToken() {
        jtxtValidarRuta.setEnabled(false);
        jtxtValidarRuta.setText("");
        jbtnValidarExaminar.setEnabled(false);
        jpfValidarClave.setText("");

        if (OS.contains("linux") || OS.contains("mac")) {
            this.jpfValidarClave.setEnabled(true);
        } else {
            this.jpfValidarClave.setEnabled(false);
        }
    }

    private boolean esWindows() {
        return (OS.contains("win"));
    }

    /*
     Valida que esten los campos necesarios para firmar
     */
    private void validacionPdf() throws DocumentoException, RazonFirma {
        // Digital signature insertion point verification ===============================
        if (point != null && (point.getX() == 0 && point.getY() == 0)) {//preguntar
            throw new DocumentoException(messages.getProperty("mensaje.error.documento_sin_firma"));
        }
        if (razonFirma != null && (razonFirma.length() > 70)) {
            throw new RazonFirma(MessageFormat.format(messages.getProperty("mensaje.error.razonfirma_limite"), Integer.parseInt(messages.getProperty("limite.razon_firma"))));
        }
        if (point == null) {
            throw new DocumentoException(messages.getProperty("mensaje.error.documento_sin_firma"));
        }
    }

    private void validacionPreFirmar() throws DocumentoNoExistenteException, TokenNoConectadoException, DocumentoNoPermitidoException, CertificadoInvalidoException, DocumentoException, RazonFirma {
        //Revisamos si existe el documento a firmar
        // TODO no hacer un return directamente, se podria validarCertificado todos los parametros e ir aumentando los errores
        if (!documento.exists()) {
            throw new DocumentoNoExistenteException(MessageFormat.format(messages.getProperty("mensaje.error.documento_inexistente"), documento.getPath()));
        }
        if (jrbFirmarToken.isSelected() && !esWindows() && jpfFirmarClave.getPassword().length == 0) {
            throw new CertificadoInvalidoException(messages.getProperty("mensaje.error.certificado_clave_vacia"));
        }
        if (documento.length() == 0) {
            throw new DocumentoException(messages.getProperty("mensaje.error.documento_vacio"));
        }
        if ("p7m".equals(FileUtils.getFileExtension(documento).toLowerCase())) {
            throw new DocumentoException(messages.getProperty("mensaje.error.documento_p7m"));
        }
        tipoDeDocumentPermitido(documento);
    }

    /*
     verificar documento
     */
    private void verificarDocumento() throws Exception {
        // Vemos si existe
        System.out.println("Verificando Docs");
        tipoDeDocumentPermitido(documento);
        List<Certificado> certificados = Utils.verificarDocumento(documento);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DefaultTableModel tableModelCertificados = (DefaultTableModel) jtblVerificarDatosFirmante.getModel();
        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModelCertificados);
        jtblVerificarDatosFirmante.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(4, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);

        tableModelCertificados.setRowCount(0);

        certificados.stream().map((certificado) -> {
            String[] dataCert = new String[6];
            dataCert[0] = certificado.getDatosUsuario().getCedula();
            String apellido = certificado.getDatosUsuario().getApellido();
            if (certificado.getDatosUsuario().getApellido() == null) {
                apellido = "";
            }
            String nombre = certificado.getDatosUsuario().getNombre();
            if (certificado.getDatosUsuario().getNombre() == null) {
                nombre = "";
            }
            dataCert[1] = nombre + " " + apellido + "\n" + certificado.getDatosUsuario().getInstitucion() + "\n" + certificado.getDatosUsuario().getCargo();
            dataCert[2] = certificado.getDocReason();
            dataCert[3] = certificado.getDatosUsuario().getEntidadCertificadora();
            dataCert[4] = format1.format(certificado.getGenerated().getTime());

            String validarFirma = Utils.validarFirma(certificado.getValidFrom(), certificado.getValidTo(), certificado.getGenerated(), certificado.getRevocated());
            if (certificado.getDocVerify() != null && !certificado.getDocVerify()) {
                validarFirma = messages.getProperty("mensaje.firma_invalida");
            }
            if (certificado.getDatosUsuario().getEntidadCertificadora().equals("desconocida")) {
                validarFirma = messages.getProperty("mensaje.firma_invalida");
            }

            dataCert[5] = validarFirma;
            return dataCert;
        }).forEachOrdered((dataCert) -> {
            tableModelCertificados.addRow(dataCert);
        });

        if (certificados != null || !certificados.isEmpty()) {
            if (certificados.get(0).getDocVerify() != null && !certificados.get(0).getDocVerify()) {
                JOptionPane.showMessageDialog(null, messages.getProperty("mensaje.error.documento_modificado"), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }

        jtblVerificarDatosFirmante.setModel(tableModelCertificados);
        tableModelCertificados.fireTableDataChanged();
    }

    // Se podria verificar el mimetype
    // Talvez eliminar el if
    private void tipoDeDocumentPermitido(File documento) throws DocumentoNoPermitidoException {
        String extDocumento = FileUtils.getFileExtension(documento).toLowerCase();
        if (!documento.getName().contains(".")) {
            throw new DocumentoNoPermitidoException(messages.getProperty("mensaje.error.extension_vacia"));
        }

        if (!extensionesDocumentos.stream().anyMatch((extension) -> (extension.equals(extDocumento)))) {
            throw new DocumentoNoPermitidoException(MessageFormat.format(messages.getProperty("mensaje.error.extension_no_permitida"), extDocumento));
        }
    }

    private String tipoFirma;

    private KeyStore getKeyStore() throws TokenNoEncontradoException {
        KeyStore ks = null;
        try {
            if (jrbFirmarToken.isSelected()) {
                ks =  KeyStoreProviderFactory.getKeyStore(new String(jpfFirmarClave.getPassword()));
                if (ks == null) {
                    throw new TokenNoEncontradoException(messages.getProperty("mensaje.error.token_contrasena_invalida") + " o " + messages.getProperty("mensaje.error.token_no_encontrado"));
                }
            }
            if (jrbFirmarHSM.isSelected()) {
                KeyStoreProvider ksp = new Bit4IdWindosKeyStoreProvider();
                ks = ksp.getKeystore();
                if (ks == null) {
                    throw new TokenNoEncontradoException(messages.getProperty("mensaje.error.token_contrasena_invalida") + " o " + messages.getProperty("mensaje.error.token_no_encontrado"));
                }
            }
            if (jrbFirmarArchivo.isSelected()) {
                File llave = new File(jtxtFirmarRuta.getText());
                if (llave.exists() == true) {
                    KeyStoreProvider ksp = new FileKeyStoreProvider(jtxtFirmarRuta.getText());
                    ks = ksp.getKeystore(jpfFirmarClave.getPassword());
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "No se encontró el certificado digital " + llave.getPath(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (KeyStoreException e) {
            setCursor(Cursor.getDefaultCursor());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            if (e.getMessage().equals("java.io.IOException: keystore password was incorrect")) {
                JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.clave_incorrecta"), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.certificado_formato_invalido"), "Error", JOptionPane.ERROR_MESSAGE);
            }
            jplCertificadoValidar.setEnabled(true);
        }
        return ks;
    }

    private void enableBotones(boolean estado) {
        //jplFirmarDocumento
        jbtnFirmarExaminarCertificado.setEnabled(estado);
        jbtnFirmarExaminarDocumentos.setEnabled(estado);
        jbtnFirmarEliminarDocumentos.setEnabled(estado);
        jbtnFirmar.setEnabled(estado);
        jbtnRestablecerFirmar.setEnabled(estado);
        //jplVerificarDocumento
        jbtnVerificar.setEnabled(estado);
        jbtnVerificarExaminar.setEnabled(estado);
        jbtnRestablecerVerificar.setEnabled(estado);
        //jplValidarCertificado
        jbtnValidar.setEnabled(estado);
        jbtnValidarExaminar.setEnabled(estado);
        jbtnValidarRestablecer.setEnabled(estado);
    }

    private void enableControles(boolean estado) {
        jtxtFirmarRuta.setEnabled(estado);
        jpfFirmarClave.setEnabled(estado);
        jtxtValidarRuta.setEnabled(estado);
        jpfValidarClave.setEnabled(estado);
        enableBotones(estado);
        if (estado) {
            setCursor(Cursor.getDefaultCursor());
            dragAndDrop();
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            nullDragAndDrop();
        }
    }

    private void firmarDocumento(List<String> rutaDocumentos) {
        List<String> documentosFirmados = new ArrayList<>();
        try {
            // Vemos si es un documento permitido primero
            validacionPreFirmar();
            KeyStore ks = getKeyStore();

            if (ks != null) {
                alias = null;
                alias = CertUtils.seleccionarAlias(ks);
                X509Certificate x509Certificate = CertUtils.getCert(ks, alias);

                x509CertificateUtils = null;
                x509CertificateUtils = new X509CertificateUtils();
                if (x509CertificateUtils.validarX509Certificate(x509Certificate)) {
                    if (x509Certificate != null && alias != null) {
                        if (FileUtils.getFileExtension(documento).toLowerCase().equals("pdf")) {
                            if (!jchkBoxFirmaInvisible.isSelected()) {
                                tipoFirma = "Firma Visible";
                                previewPdf();
                                validacionPdf();
                            } else {
                                tipoFirma = null;
                            }
                        }
                        razonFirma = razonFirma == null ? "" : razonFirma;
                        char[] password = jpfFirmarClave.getPassword();

                        new Thread(() -> {
                            enableControles(false);
                            //creating ProgressMonitor instance
                            ProgressMonitor pm = new ProgressMonitor(this, "Firmando", "Task starting", 1, rutaDocumentos.size());
                            int i = 0;
                            for (String rutaDocumento : rutaDocumentos) {
                                try {
                                    i++;
                                    File documento = new File(rutaDocumento);
                                    String extDocumento = FileUtils.getFileExtension(documento);
                                    //updating ProgressMonitor note
                                    pm.setNote("<html><b>" + i + " de " + rutaDocumentos.size() + " documento(s)</b>"
                                            + "<br>" + documento.toString() + "</html>");
                                    //updating ProgressMonitor progress
                                    pm.setProgress(i);
                                    if (pm.isCanceled()) {
                                        //finalizar loop
                                        break;
                                    }
                                    byte[] docSigned = null;
                                    docSigned = FirmaDigital.firmar(
                                            ks,
                                            alias,
                                            documento,
                                            password,
                                            point,
                                            pagina,
                                            razonFirma,
                                            tipoFirma);
                                    if (docSigned != null) {
                                    	if(extDocumento=="pdf") {
                                    		String nombreDocFirmado = FileUtils.crearNombreFirmado(documento, FileUtils.getExtension(FileUtils.fileConvertToByteArray(documento)));
                                            FileUtils.saveByteArrayToDisc(docSigned, nombreDocFirmado);
                                            documentosFirmados.add(nombreDocFirmado);
                                    	}else{
                                    		String nombreDocFirmado = FileUtils.crearNombreFirmado(documento, "."+extDocumento);
                                            FileUtils.saveByteArrayToDisc(docSigned, nombreDocFirmado);
                                            documentosFirmados.add(nombreDocFirmado);
                                    	}
                                    	
                                        // Información del documento firmado y firmante
                                        
                                    }
                                } catch (Exception e) {
                                    enableControles(true);
                                    //Reseteamos el campo de archivo firmado y las tablas de informacion para que no hay confusión
                                    resetFirmarDocumento();
                                    resetDocumentos();
                                    String mensaje = (String) e.getMessage();
                                    System.out.println("Exception Normal " + mensaje);
                                    e.printStackTrace();
                                    if (mensaje != null) {
                                        if (mensaje.contains("org.xml.sax.SAXParseException")) {
                                            mensaje = messages.getProperty("mensaje.error.documento_corrupto");
                                        }
                                        if (mensaje.contains("IllegalStateException") && mensaje.contains("Content_Types")) {
                                            mensaje = messages.getProperty("mensaje.error.documento_corrupto");
                                        }
                                        if (mensaje.contains("Las firmas XAdES Enveloped solo pueden realizarse sobre datos XML")) {
                                            mensaje = messages.getProperty("mensaje.error.documento_xml");
                                        }
                                        if (e.getClass() == java.net.UnknownHostException.class
                                                || e.getClass() == java.net.NoRouteToHostException.class
                                                || e.getClass() == java.net.SocketTimeoutException.class
                                                || e.getClass() == java.net.SocketException.class
                                                || e.getClass() == java.net.ConnectException.class) {
                                            mensaje = messages.getProperty("mensaje.error.problema_red");
                                        }
                                    } else {
                                        mensaje = messages.getProperty("mensaje.error.documento_problemas");
                                    }
                                    JOptionPane.showMessageDialog(this, mensaje + "\n" + rutaDocumento, "Error", JOptionPane.ERROR_MESSAGE);
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                                    System.err.println("Error no se pudo firmar ");
                                }
                            }
                            if (documentosFirmados.size() > 0) {
                                if (documentosFirmados.size() > 0) {
                                    jPanelVariosDocumentos jPanelVariosDocumentos = new jPanelVariosDocumentos(documentosFirmados);
                                    javax.swing.JButton btnSalir = new javax.swing.JButton();
                                    btnSalir.setText("Salir");
                                    btnSalir.setMnemonic(java.awt.event.KeyEvent.VK_S);
                                    Object[] options = {btnSalir};
                                    btnSalir.addActionListener((java.awt.event.ActionEvent evt1) -> {
                                        java.awt.Component component1 = (java.awt.Component) evt1.getSource();
                                        javax.swing.JDialog dialog = (javax.swing.JDialog) javax.swing.SwingUtilities.getRoot(component1);
                                        dialog.dispose();
                                    });
                                    JOptionPane.showOptionDialog(null, jPanelVariosDocumentos, "Documentos firmados",
                                            JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                                }
                            }
                            enableControles(true);
                            //Reseteamos el campo de archivo firmado y las tablas de informacion para que no hay confusión
                            resetFirmarDocumento();
                            resetDocumentos();
                        }).start();
                        //información firmante
                        DatosUsuario datosUsuario = CertEcUtils.getDatosUsuarios(x509Certificate);
                        System.out.println("datosUsuario: " + datosUsuario.toString());
                        //información firmante
                        resetFirmarDocumento();
                    }
                }
            }
        } catch (KeyStoreException e) {
            enableControles(true);
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            if (e.getMessage().equals("java.io.IOException: keystore password was incorrect")) {
                JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.clave_incorrecta"), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.certificado_formato_invalido"), "Error", JOptionPane.ERROR_MESSAGE);
            }
            jpfValidarClave.setText("");
            jplCertificadoValidar.setEnabled(true);
        } catch (TokenNoEncontradoException | CertificadoInvalidoException | EntidadCertificadoraNoValidaException | HoraServidorException | RubricaException | HeadlessException | IOException | InvalidKeyException
                | DocumentoNoExistenteException | TokenNoConectadoException | DocumentoNoPermitidoException | DocumentoException | RazonFirma ex) {
            enableControles(true);
            //Reseteamos el campo de archivo firmado y las tablas de informacion para que no hay confusión
            resetFirmarDocumento();
            resetDocumentos();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void firmarDocumentoHSM(List<String> rutaDocumentos) {
        List<String> documentosFirmados = new ArrayList<>();
        try {
            // Vemos si es un documento permitido primero
            validacionPreFirmar();
            KeyStore ks = getKeyStore();

            if (ks != null) {
                alias = null;
                alias = CertUtils.seleccionarAlias(ks);
                X509Certificate x509Certificate = CertUtils.getCert(ks, alias);

                x509CertificateUtils = null;
                x509CertificateUtils = new X509CertificateUtils();
                if (x509CertificateUtils.validarX509Certificate(x509Certificate)) {
                    if (x509Certificate != null && alias != null) {
                        if (FileUtils.getFileExtension(documento).toLowerCase().equals("pdf")) {
                            if (!jchkBoxFirmaInvisible.isSelected()) {
                                tipoFirma = "Firma Visible";
                                previewPdf();
                                validacionPdf();
                            } else {
                                tipoFirma = null;
                            }
                        }
                        razonFirma = razonFirma == null ? "" : razonFirma;
                        char[] password = jpfFirmarClave.getPassword();

                        new Thread(() -> {
                            enableControles(false);
                            //creating ProgressMonitor instance
                            ProgressMonitor pm = new ProgressMonitor(this, "Firmando", "Task starting", 1, rutaDocumentos.size());
                            int i = 0;
                            for (String rutaDocumento : rutaDocumentos) {
                                try {
                                    i++;
                                    File documento = new File(rutaDocumento);
                                    String extDocumento = FileUtils.getFileExtension(documento);
                                    //updating ProgressMonitor note
                                    pm.setNote("<html><b>" + i + " de " + rutaDocumentos.size() + " documento(s)</b>"
                                            + "<br>" + documento.toString() + "</html>");
                                    //updating ProgressMonitor progress
                                    pm.setProgress(i);
                                    if (pm.isCanceled()) {
                                        //finalizar loop
                                        break;
                                    }
                                    byte[] docSigned = null;
                                    docSigned = FirmaDigital.firmar(
                                            ks,
                                            alias,
                                            documento,
                                            password,
                                            point,
                                            pagina,
                                            razonFirma,
                                            tipoFirma);
                                    if (docSigned != null) {
                                    	if(extDocumento=="pdf") {
                                    		String nombreDocFirmado = FileUtils.crearNombreFirmado(documento, FileUtils.getExtension(FileUtils.fileConvertToByteArray(documento)));
                                            FileUtils.saveByteArrayToDisc(docSigned, nombreDocFirmado);
                                            documentosFirmados.add(nombreDocFirmado);
                                    	}else{
                                    		String nombreDocFirmado = FileUtils.crearNombreFirmado(documento, "."+extDocumento);
                                            FileUtils.saveByteArrayToDisc(docSigned, nombreDocFirmado);
                                            documentosFirmados.add(nombreDocFirmado);
                                    	}
                                    	
                                        // Información del documento firmado y firmante
                                        
                                    }
                                } catch (Exception e) {
                                    enableControles(true);
                                    //Reseteamos el campo de archivo firmado y las tablas de informacion para que no hay confusión
                                    resetFirmarDocumento();
                                    resetDocumentos();
                                    String mensaje = (String) e.getMessage();
                                    System.out.println("Exception Normal " + mensaje);
                                    e.printStackTrace();
                                    if (mensaje != null) {
                                        if (mensaje.contains("org.xml.sax.SAXParseException")) {
                                            mensaje = messages.getProperty("mensaje.error.documento_corrupto");
                                        }
                                        if (mensaje.contains("IllegalStateException") && mensaje.contains("Content_Types")) {
                                            mensaje = messages.getProperty("mensaje.error.documento_corrupto");
                                        }
                                        if (mensaje.contains("Las firmas XAdES Enveloped solo pueden realizarse sobre datos XML")) {
                                            mensaje = messages.getProperty("mensaje.error.documento_xml");
                                        }
                                        if (e.getClass() == java.net.UnknownHostException.class
                                                || e.getClass() == java.net.NoRouteToHostException.class
                                                || e.getClass() == java.net.SocketTimeoutException.class
                                                || e.getClass() == java.net.SocketException.class
                                                || e.getClass() == java.net.ConnectException.class) {
                                            mensaje = messages.getProperty("mensaje.error.problema_red");
                                        }
                                    } else {
                                        mensaje = messages.getProperty("mensaje.error.documento_problemas");
                                    }
                                    JOptionPane.showMessageDialog(this, mensaje + "\n" + rutaDocumento, "Error", JOptionPane.ERROR_MESSAGE);
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                                    System.err.println("Error no se pudo firmar ");
                                }
                            }
                            if (documentosFirmados.size() > 0) {
                                if (documentosFirmados.size() > 0) {
                                    jPanelVariosDocumentos jPanelVariosDocumentos = new jPanelVariosDocumentos(documentosFirmados);
                                    javax.swing.JButton btnSalir = new javax.swing.JButton();
                                    btnSalir.setText("Salir");
                                    btnSalir.setMnemonic(java.awt.event.KeyEvent.VK_S);
                                    Object[] options = {btnSalir};
                                    btnSalir.addActionListener((java.awt.event.ActionEvent evt1) -> {
                                        java.awt.Component component1 = (java.awt.Component) evt1.getSource();
                                        javax.swing.JDialog dialog = (javax.swing.JDialog) javax.swing.SwingUtilities.getRoot(component1);
                                        dialog.dispose();
                                    });
                                    JOptionPane.showOptionDialog(null, jPanelVariosDocumentos, "Documentos firmados",
                                            JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                                }
                            }
                            enableControles(true);
                            //Reseteamos el campo de archivo firmado y las tablas de informacion para que no hay confusión
                            resetFirmarDocumento();
                            resetDocumentos();
                        }).start();
                        //información firmante
                        DatosUsuario datosUsuario = CertEcUtils.getDatosUsuarios(x509Certificate);
                        System.out.println("datosUsuario: " + datosUsuario.toString());
                        //información firmante
                        resetFirmarDocumento();
                    }
                }
            }
        } catch (KeyStoreException e) {
            enableControles(true);
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            if (e.getMessage().equals("java.io.IOException: keystore password was incorrect")) {
                JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.clave_incorrecta"), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.certificado_formato_invalido"), "Error", JOptionPane.ERROR_MESSAGE);
            }
            jpfValidarClave.setText("");
            jplCertificadoValidar.setEnabled(true);
        } catch (TokenNoEncontradoException | CertificadoInvalidoException | EntidadCertificadoraNoValidaException | HoraServidorException | RubricaException | HeadlessException | IOException | InvalidKeyException
                | DocumentoNoExistenteException | TokenNoConectadoException | DocumentoNoPermitidoException | DocumentoException | RazonFirma ex) {
            enableControles(true);
            //Reseteamos el campo de archivo firmado y las tablas de informacion para que no hay confusión
            resetFirmarDocumento();
            resetDocumentos();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DefaultTableModel tableModelDocumentos;

    private void agregarDocumentos(List<String> documentos) throws Exception {
        tableModelDocumentos = (DefaultTableModel) jtblFirmarDocumentos.getModel();
        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModelDocumentos);
        jtblFirmarDocumentos.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);

        for (int i = 0; i < tableModelDocumentos.getRowCount(); i++) {
            boolean repetido = false;
            String rutaDocumento = (String) tableModelDocumentos.getValueAt(i, 0);
            for (String documento : documentos) {
                if (documento.equals(rutaDocumento)) {
                    repetido = true;
                    break;
                }
            }
            if (!repetido) {
                documentos.add(rutaDocumento);
            }
        }

        tableModelDocumentos.setRowCount(0);
        documentos.stream().map((documento) -> {
            String[] dataCert = new String[1];
            dataCert[0] = documento;
            return dataCert;
        }).forEachOrdered((dataCert) -> {
            tableModelDocumentos.addRow(dataCert);
        });

        jtblFirmarDocumentos.setModel(tableModelDocumentos);
        tableModelDocumentos.fireTableDataChanged();
    }

    private void resetDocumentos() {
        DefaultTableModel tableModelCert = (DefaultTableModel) jtblFirmarDocumentos.getModel();
        tableModelCert.setRowCount(0);
        jtblFirmarDocumentos.setModel(tableModelCert);
        tableModelCert.fireTableDataChanged();
    }

    private void setearInfoValidacionCertificado(X509Certificate cert) throws CertificadoInvalidoException, InvalidKeyException, io.rubrica.exceptions.EntidadCertificadoraNoValidaException {
        if (cert != null) {
            String emisor = CertEcUtils.getNombreCA(cert);

            DatosUsuario datosUsuario = CertEcUtils.getDatosUsuarios(cert);

            if (datosUsuario == null && (jpfValidarClave.getPassword() == null || jpfValidarClave.getPassword().length == 0)) {
                throw new CertificadoInvalidoException(messages.getProperty("mensaje.error.extraer_datos_certificados"));
            }

            if (datosUsuario == null) {
                throw new CertificadoInvalidoException("No se pudo extraer los datos del certificados.");
            }

            DefaultTableModel tableModel = (DefaultTableModel) jtblValidarDatosCertificados.getModel();

            tableModel.setRowCount(0);

            //Actualizamos los datos del archivo
            String[] data = new String[1];

            //Si el certificado no es null
            System.out.println(messages.getProperty("tabla.certificado.emitido_por"));
            data[0] = MessageFormat.format(messages.getProperty("tabla.certificado.emitido_por"), CertEcUtils.getNombreCA(cert));
            tableModel.addRow(data);

            data[0] = messages.getProperty("tabla.certificado.cedula") + " " + datosUsuario.getCedula();
            tableModel.addRow(data);

            data[0] = messages.getProperty("tabla.certificado.nombres") + " " + datosUsuario.getNombre();
            tableModel.addRow(data);

            data[0] = messages.getProperty("tabla.certificado.apellidos") + " " + datosUsuario.getApellido();
            tableModel.addRow(data);

            data[0] = messages.getProperty("tabla.certificado.institucion") + " " + datosUsuario.getInstitucion();
            tableModel.addRow(data);

            data[0] = messages.getProperty("tabla.certificado.cargo") + " " + datosUsuario.getCargo();
            tableModel.addRow(data);

            data[0] = messages.getProperty("tabla.certificado.fecha_de_emision") + " " + cert.getNotBefore();
            tableModel.addRow(data);

            data[0] = messages.getProperty("tabla.certificado.fecha_de_expiracion") + " " + cert.getNotAfter();
            tableModel.addRow(data);

            jtblValidarDatosCertificados.setModel(tableModel);
            tableModel.fireTableDataChanged();
        }
        //TOdo botar error si es null
    }

    private void agregarValidezCertificado(X509CertificateUtils x509CertificateUtils) {
        DefaultTableModel tableModel = (DefaultTableModel) jtblValidarDatosCertificados.getModel();
        //Actualizamos los datos del archivo
        String[] data = new String[1];
        if (x509CertificateUtils.isDesconocido()) {
            data[0] = MessageFormat.format(messages.getProperty("tabla.certificado.emitido_por"), messages.getProperty("tabla.certificado.desconocido"));
            tableModel.setValueAt(data[0], 0, 0);
        }
        data[0] = messages.getProperty("tabla.certificado.caducado") + " " + (x509CertificateUtils.isCaducado() ? messages.getProperty("mensaje.si") : messages.getProperty("mensaje.no"));
        tableModel.addRow(data);
        data[0] = messages.getProperty("tabla.certificado.revocado") + (x509CertificateUtils.getRevocado() != null ? x509CertificateUtils.getRevocado() : messages.getProperty("mensaje.no"));

        tableModel.addRow(data);
        jtblValidarDatosCertificados.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    private void resetInfoValidacionCertificado() {
        DefaultTableModel tableModel = (DefaultTableModel) jtblValidarDatosCertificados.getModel();
        tableModel.setRowCount(0);
        jtblValidarDatosCertificados.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    private void resetValidacionCertificado() {
        jtxtValidarRuta.setText("");
        jpfValidarClave.setText("");
        resetInfoValidacionCertificado();
    }

    public void actualizar() {
        btnSi.setText("Sí");
        btnSi.setMnemonic(KeyEvent.VK_S);
        btnNo.setText("No");
        btnNo.setMnemonic(KeyEvent.VK_N);
        btnAceptar.setText("Aceptar");
        btnAceptar.setMnemonic(KeyEvent.VK_A);

        btnSi.addActionListener((java.awt.event.ActionEvent evt) -> {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Object[] optionsAceptar = {btnAceptar};
            try {
                Update update = new Update();
                Update.setAmbiente("--update");
                File jar = update.actualizarFirmador();
                update.updateFirmador(jar);

                if (!OsUtils.isMac()) {
                    File clienteJar = update.actualizarCliente();
                    update.updateCliente(clienteJar);
                }

                JOptionPane.showOptionDialog(getParent(), messages.getProperty("mensaje.actualizado"), "Mensaje",
                        JOptionPane.OK_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, optionsAceptar, btnAceptar);

                System.exit(0);
            } catch (IllegalArgumentException e) {
                JOptionPane.showOptionDialog(getParent(), messages.getProperty("mensaje.error.actualizar_administracion"), "Error",
                        JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, optionsAceptar, btnAceptar);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error al actualizar:", e);
                JOptionPane.showOptionDialog(getParent(), messages.getProperty("mensaje.error.actualizar") + ": " + e.getMessage(), "Error",
                        JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, optionsAceptar, btnAceptar);
            }

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        });

        btnNo.addActionListener((java.awt.event.ActionEvent evt) -> {
            Component component1 = (Component) evt.getSource();
            JDialog dialog = (JDialog) SwingUtilities.getRoot(component1);
            dialog.dispose();
        });

        btnAceptar.addActionListener((java.awt.event.ActionEvent evt) -> {
            Component component1 = (Component) evt.getSource();
            JDialog dialog = (JDialog) SwingUtilities.getRoot(component1);
            dialog.dispose();
        });

        Object[] options = {btnNo, btnSi};

        String version = "La versión actual es " + config.getProperty("version");
        version = version + " de la fecha " + config.getProperty("fecha") + ". ";
        JOptionPane.showOptionDialog(getParent(), version + messages.getProperty("mensaje.desea_actualizar"), messages.getProperty("mensaje.confirmar"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
    }

    private List<String> existeDocumentos() {
        List<String> documentos = new java.util.ArrayList<>();
        for (int i = 0; i < tableModelDocumentos.getRowCount(); i++) {
            String rutaDocumento = (String) tableModelDocumentos.getValueAt(i, 0);
            if (rutaDocumento != null) {
                documento = new File(rutaDocumento);
                documentos.add(rutaDocumento);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, MessageFormat.format(messages.getProperty("mensaje.error.documento_inexistente"), rutaDocumento), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
        return documentos;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tipoFirmaBtnGRP = new javax.swing.ButtonGroup();
        bgDocumentos = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JTabbedPane();
        jplFirmarDocumento = new javax.swing.JPanel();
        jplCertificadoFirmar = new javax.swing.JPanel();
        jblCertificadoEnFimador = new javax.swing.JLabel();
        jrbFirmarArchivo = new javax.swing.JRadioButton();
        jrbFirmarToken = new javax.swing.JRadioButton();
        jblCertificadoFirmar = new javax.swing.JLabel();
        jtxtFirmarRuta = new javax.swing.JTextField();
        jbtnFirmarExaminarCertificado = new javax.swing.JButton();
        jblClave = new javax.swing.JLabel();
        jpfFirmarClave = new javax.swing.JPasswordField();
        jrbFirmarHSM = new javax.swing.JRadioButton();
        jplDocumentos = new javax.swing.JPanel();
        jlblDocumentosFirmar = new javax.swing.JLabel();
        jScrollPaneFirmarDocumentos = new javax.swing.JScrollPane();
        jtblFirmarDocumentos = new javax.swing.JTable();
        jbtnFirmarExaminarDocumentos = new javax.swing.JButton();
        jbtnFirmarEliminarDocumentos = new javax.swing.JButton();
        jchkBoxFirmaInvisible = new javax.swing.JCheckBox();
        jbtnFirmar = new javax.swing.JButton();
        jbtnRestablecerFirmar = new javax.swing.JButton();
        jplVerificarDocumento = new javax.swing.JPanel();
        jplDocumentoVerificar = new javax.swing.JPanel();
        jlbArchivoFirmadoVerficar = new javax.swing.JLabel();
        jtxtVerificarRuta = new javax.swing.JTextField();
        jbtnVerificarExaminar = new javax.swing.JButton();
        jplVerificacion = new javax.swing.JPanel();
        jlblResultadoVerificacion = new javax.swing.JLabel();
        jScrollPaneVerificarDatosFirmante = new javax.swing.JScrollPane();
        jtblVerificarDatosFirmante = new javax.swing.JTable();
        jbtnRestablecerVerificar = new javax.swing.JButton();
        jbtnVerificar = new javax.swing.JButton();
        jplValidarCertificado = new javax.swing.JPanel();
        jplCertificadoValidar = new javax.swing.JPanel();
        jlbCertificadoValidar = new javax.swing.JLabel();
        jrbValidarArchivo = new javax.swing.JRadioButton();
        jrbValidarToken = new javax.swing.JRadioButton();
        jlbCertificadoVldCert = new javax.swing.JLabel();
        jtxtValidarRuta = new javax.swing.JTextField();
        jbtnValidarExaminar = new javax.swing.JButton();
        jlbCertificadoValidarCert = new javax.swing.JLabel();
        jpfValidarClave = new javax.swing.JPasswordField();
        jplValidacion = new javax.swing.JPanel();
        jlblResultadoValidacion = new javax.swing.JLabel();
        jScrollPaneValidarDatosCertificados = new javax.swing.JScrollPane();
        jtblValidarDatosCertificados = new javax.swing.JTable();
        jbtnValidarRestablecer = new javax.swing.JButton();
        jbtnValidar = new javax.swing.JButton();
        jmbMenuPrincipal = new javax.swing.JMenuBar();
        jmConfiguracion = new javax.swing.JMenu();
        jmiPanelConfiguracion = new javax.swing.JMenuItem();
        jmiActualizar = new javax.swing.JMenuItem();
        jmAyuda = new javax.swing.JMenu();
        jmiAyuda = new javax.swing.JMenuItem();
        jmiAcerca = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jplFirmarDocumento.setName(""); // NOI18N

        jplCertificadoFirmar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jblCertificadoEnFimador.setText("Certificado en");

        tipoFirmaBtnGRP.add(jrbFirmarArchivo);
        jrbFirmarArchivo.setText("Archivo");
        jrbFirmarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbFirmarArchivoActionPerformed(evt);
            }
        });

        tipoFirmaBtnGRP.add(jrbFirmarToken);
        jrbFirmarToken.setText("Token");
        jrbFirmarToken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbFirmarTokenActionPerformed(evt);
            }
        });

        jblCertificadoFirmar.setText("Certificado");

        jtxtFirmarRuta.setEditable(false);
        jtxtFirmarRuta.setEnabled(false);

        jbtnFirmarExaminarCertificado.setText("Examinar");
        jbtnFirmarExaminarCertificado.setEnabled(false);
        jbtnFirmarExaminarCertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFirmarExaminarCertificadoActionPerformed(evt);
            }
        });

        jblClave.setText("Contraseña");

        jpfFirmarClave.setEnabled(false);

        tipoFirmaBtnGRP.add(jrbFirmarHSM);
        jrbFirmarHSM.setText("HSM");
        jrbFirmarHSM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbFirmarHSMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jplCertificadoFirmarLayout = new javax.swing.GroupLayout(jplCertificadoFirmar);
        jplCertificadoFirmar.setLayout(jplCertificadoFirmarLayout);
        jplCertificadoFirmarLayout.setHorizontalGroup(
            jplCertificadoFirmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplCertificadoFirmarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplCertificadoFirmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jplCertificadoFirmarLayout.createSequentialGroup()
                        .addGroup(jplCertificadoFirmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jblCertificadoEnFimador)
                            .addComponent(jblCertificadoFirmar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jplCertificadoFirmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jplCertificadoFirmarLayout.createSequentialGroup()
                                .addComponent(jtxtFirmarRuta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtnFirmarExaminarCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jplCertificadoFirmarLayout.createSequentialGroup()
                                .addComponent(jrbFirmarArchivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jrbFirmarToken)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jrbFirmarHSM)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jplCertificadoFirmarLayout.createSequentialGroup()
                        .addComponent(jblClave)
                        .addGap(20, 20, 20)
                        .addComponent(jpfFirmarClave)
                        .addGap(102, 102, 102))))
        );
        jplCertificadoFirmarLayout.setVerticalGroup(
            jplCertificadoFirmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jplCertificadoFirmarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplCertificadoFirmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbFirmarArchivo)
                    .addComponent(jrbFirmarToken)
                    .addComponent(jblCertificadoEnFimador)
                    .addComponent(jrbFirmarHSM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jplCertificadoFirmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jblCertificadoFirmar)
                    .addComponent(jtxtFirmarRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnFirmarExaminarCertificado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jplCertificadoFirmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpfFirmarClave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jblClave))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jplDocumentos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblDocumentosFirmar.setText("<html><b>0 DOCUMENTO(S) SELECCIONADO(S)</b></html>");

        jtblFirmarDocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Doble clic sobre el documento para visualizar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneFirmarDocumentos.setViewportView(jtblFirmarDocumentos);

        jbtnFirmarExaminarDocumentos.setText("Examinar");
        jbtnFirmarExaminarDocumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFirmarExaminarDocumentosActionPerformed(evt);
            }
        });

        jbtnFirmarEliminarDocumentos.setText("Eliminar");
        jbtnFirmarEliminarDocumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFirmarEliminarDocumentosActionPerformed(evt);
            }
        });

        jchkBoxFirmaInvisible.setText("Firma invisible (sólo para documentos PDF)");

        javax.swing.GroupLayout jplDocumentosLayout = new javax.swing.GroupLayout(jplDocumentos);
        jplDocumentos.setLayout(jplDocumentosLayout);
        jplDocumentosLayout.setHorizontalGroup(
            jplDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplDocumentosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblDocumentosFirmar, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPaneFirmarDocumentos, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jplDocumentosLayout.createSequentialGroup()
                        .addComponent(jchkBoxFirmaInvisible)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnFirmarExaminarDocumentos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnFirmarEliminarDocumentos)))
                .addContainerGap())
        );
        jplDocumentosLayout.setVerticalGroup(
            jplDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplDocumentosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblDocumentosFirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneFirmarDocumentos, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jplDocumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnFirmarExaminarDocumentos)
                    .addComponent(jbtnFirmarEliminarDocumentos)
                    .addComponent(jchkBoxFirmaInvisible))
                .addContainerGap())
        );

        jbtnFirmar.setText("Firmar");
        jbtnFirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFirmarActionPerformed(evt);
            }
        });

        jbtnRestablecerFirmar.setText("Restablecer");
        jbtnRestablecerFirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRestablecerFirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jplFirmarDocumentoLayout = new javax.swing.GroupLayout(jplFirmarDocumento);
        jplFirmarDocumento.setLayout(jplFirmarDocumentoLayout);
        jplFirmarDocumentoLayout.setHorizontalGroup(
            jplFirmarDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jplFirmarDocumentoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnFirmar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnRestablecerFirmar)
                .addContainerGap())
            .addGroup(jplFirmarDocumentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplFirmarDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jplFirmarDocumentoLayout.createSequentialGroup()
                        .addComponent(jplDocumentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8))
                    .addGroup(jplFirmarDocumentoLayout.createSequentialGroup()
                        .addComponent(jplCertificadoFirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jplFirmarDocumentoLayout.setVerticalGroup(
            jplFirmarDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplFirmarDocumentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jplCertificadoFirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jplDocumentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jplFirmarDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnFirmar)
                    .addComponent(jbtnRestablecerFirmar))
                .addContainerGap())
        );

        mainPanel.addTab("<html><b>FIRMAR DOCUMENTO </b>(<u>1</u>)</html>", jplFirmarDocumento);

        jplDocumentoVerificar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jplDocumentoVerificar.setPreferredSize(new java.awt.Dimension(454, 71));

        jlbArchivoFirmadoVerficar.setText("Archivo Firmado:");

        jtxtVerificarRuta.setEditable(false);

        jbtnVerificarExaminar.setText("Examinar");
        jbtnVerificarExaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnVerificarExaminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jplDocumentoVerificarLayout = new javax.swing.GroupLayout(jplDocumentoVerificar);
        jplDocumentoVerificar.setLayout(jplDocumentoVerificarLayout);
        jplDocumentoVerificarLayout.setHorizontalGroup(
            jplDocumentoVerificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplDocumentoVerificarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbArchivoFirmadoVerficar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtVerificarRuta)
                .addGap(18, 18, 18)
                .addComponent(jbtnVerificarExaminar)
                .addContainerGap())
        );
        jplDocumentoVerificarLayout.setVerticalGroup(
            jplDocumentoVerificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplDocumentoVerificarLayout.createSequentialGroup()
                .addGroup(jplDocumentoVerificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtVerificarRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnVerificarExaminar)
                    .addComponent(jlbArchivoFirmadoVerficar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jplVerificacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblResultadoVerificacion.setText("<html><b>RESULTADOS DE LA VERIFICACIÓN DEL ARCHIVO FIRMADO ELECTRÓNICAMENTE</b></html>");

        jtblVerificarDatosFirmante.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Nombres", "Razón", "Entidad Certificadora", "Fecha Firmado", "Firma"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneVerificarDatosFirmante.setViewportView(jtblVerificarDatosFirmante);

        javax.swing.GroupLayout jplVerificacionLayout = new javax.swing.GroupLayout(jplVerificacion);
        jplVerificacion.setLayout(jplVerificacionLayout);
        jplVerificacionLayout.setHorizontalGroup(
            jplVerificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplVerificacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplVerificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jlblResultadoVerificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPaneVerificarDatosFirmante, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE))
                .addContainerGap())
        );
        jplVerificacionLayout.setVerticalGroup(
            jplVerificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplVerificacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblResultadoVerificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneVerificarDatosFirmante, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addContainerGap())
        );

        jbtnRestablecerVerificar.setText("Restablecer");
        jbtnRestablecerVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRestablecerVerificarActionPerformed(evt);
            }
        });

        jbtnVerificar.setMnemonic('v');
        jbtnVerificar.setText("Verificar Archivo");
        jbtnVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnVerificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jplVerificarDocumentoLayout = new javax.swing.GroupLayout(jplVerificarDocumento);
        jplVerificarDocumento.setLayout(jplVerificarDocumentoLayout);
        jplVerificarDocumentoLayout.setHorizontalGroup(
            jplVerificarDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplVerificarDocumentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplVerificarDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jplVerificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jplDocumentoVerificar, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE))
                .addGap(8, 8, 8))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jplVerificarDocumentoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnVerificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnRestablecerVerificar)
                .addContainerGap())
        );
        jplVerificarDocumentoLayout.setVerticalGroup(
            jplVerificarDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplVerificarDocumentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jplDocumentoVerificar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jplVerificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jplVerificarDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnRestablecerVerificar)
                    .addComponent(jbtnVerificar))
                .addContainerGap())
        );

        mainPanel.addTab("<html><b>VERIFICAR DOCUMENTO </b>(<u>2</u>)</html>", jplVerificarDocumento);

        jplValidarCertificado.setName(""); // NOI18N

        jplCertificadoValidar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbCertificadoValidar.setText("Certificado en");

        tipoFirmaBtnGRP.add(jrbValidarArchivo);
        jrbValidarArchivo.setText("Archivo");
        jrbValidarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbValidarArchivoActionPerformed(evt);
            }
        });

        tipoFirmaBtnGRP.add(jrbValidarToken);
        jrbValidarToken.setText("Token");
        jrbValidarToken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbValidarTokenActionPerformed(evt);
            }
        });

        jlbCertificadoVldCert.setText("Certificado");

        jtxtValidarRuta.setEditable(false);
        jtxtValidarRuta.setEnabled(false);

        jbtnValidarExaminar.setMnemonic('E');
        jbtnValidarExaminar.setText("Examinar");
        jbtnValidarExaminar.setEnabled(false);
        jbtnValidarExaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnValidarExaminarActionPerformed(evt);
            }
        });

        jlbCertificadoValidarCert.setText("Contraseña");

        jpfValidarClave.setEnabled(false);

        javax.swing.GroupLayout jplCertificadoValidarLayout = new javax.swing.GroupLayout(jplCertificadoValidar);
        jplCertificadoValidar.setLayout(jplCertificadoValidarLayout);
        jplCertificadoValidarLayout.setHorizontalGroup(
            jplCertificadoValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplCertificadoValidarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplCertificadoValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlbCertificadoValidar)
                    .addComponent(jlbCertificadoVldCert)
                    .addComponent(jlbCertificadoValidarCert))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jplCertificadoValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jplCertificadoValidarLayout.createSequentialGroup()
                        .addComponent(jrbValidarArchivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbValidarToken)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jplCertificadoValidarLayout.createSequentialGroup()
                        .addGroup(jplCertificadoValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jpfValidarClave, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtValidarRuta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnValidarExaminar)))
                .addContainerGap())
        );
        jplCertificadoValidarLayout.setVerticalGroup(
            jplCertificadoValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jplCertificadoValidarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplCertificadoValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbValidarArchivo)
                    .addComponent(jrbValidarToken)
                    .addComponent(jlbCertificadoValidar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jplCertificadoValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbCertificadoVldCert)
                    .addComponent(jtxtValidarRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnValidarExaminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jplCertificadoValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpfValidarClave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbCertificadoValidarCert))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jplValidacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblResultadoValidacion.setText("<html><b>RESULTADOS DE VERIFICACIÓN DE CERTIFICADO ELECTRÓNICO</b></html>");

        jtblValidarDatosCertificados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Datos del Certificado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneValidarDatosCertificados.setViewportView(jtblValidarDatosCertificados);

        javax.swing.GroupLayout jplValidacionLayout = new javax.swing.GroupLayout(jplValidacion);
        jplValidacion.setLayout(jplValidacionLayout);
        jplValidacionLayout.setHorizontalGroup(
            jplValidacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplValidacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplValidacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jplValidacionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jlblResultadoValidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPaneValidarDatosCertificados, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE))
                .addContainerGap())
        );
        jplValidacionLayout.setVerticalGroup(
            jplValidacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplValidacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblResultadoValidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneValidarDatosCertificados, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addContainerGap())
        );

        jbtnValidarRestablecer.setMnemonic('r');
        jbtnValidarRestablecer.setText("Restablecer");
        jbtnValidarRestablecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnValidarRestablecerActionPerformed(evt);
            }
        });

        jbtnValidar.setMnemonic('v');
        jbtnValidar.setText("Validar");
        jbtnValidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnValidarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jplValidarCertificadoLayout = new javax.swing.GroupLayout(jplValidarCertificado);
        jplValidarCertificado.setLayout(jplValidarCertificadoLayout);
        jplValidarCertificadoLayout.setHorizontalGroup(
            jplValidarCertificadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplValidarCertificadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplValidarCertificadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jplCertificadoValidar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jplValidacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jplValidarCertificadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnValidar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnValidarRestablecer)
                .addContainerGap())
        );
        jplValidarCertificadoLayout.setVerticalGroup(
            jplValidarCertificadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplValidarCertificadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jplCertificadoValidar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jplValidacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jplValidarCertificadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnValidarRestablecer)
                    .addComponent(jbtnValidar))
                .addContainerGap())
        );

        mainPanel.addTab("<html><b>VALIDAR CERTIFICADO </b>(<u>3</u>)</html>", jplValidarCertificado);

        jScrollPane1.setViewportView(mainPanel);
        mainPanel.getAccessibleContext().setAccessibleParent(this);

        jmConfiguracion.setMnemonic('c');
        jmConfiguracion.setText("Configuración");
        jmConfiguracion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jmConfiguracion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jmConfiguracion.setInheritsPopupMenu(true);

        jmiPanelConfiguracion.setMnemonic('p');
        jmiPanelConfiguracion.setText("Panel de Configuración");
        jmiPanelConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiPanelConfiguracionActionPerformed(evt);
            }
        });
        jmConfiguracion.add(jmiPanelConfiguracion);

        jmiActualizar.setMnemonic('z');
        jmiActualizar.setText("Actualizar");
        jmiActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiActualizarActionPerformed(evt);
            }
        });
        jmConfiguracion.add(jmiActualizar);

        jmbMenuPrincipal.add(jmConfiguracion);

        jmAyuda.setMnemonic('a');
        jmAyuda.setText("Ayuda");
        jmAyuda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jmAyuda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jmAyuda.setInheritsPopupMenu(true);

        jmiAyuda.setMnemonic('y');
        jmiAyuda.setText("Ayuda en línea");
        jmiAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAyudaActionPerformed(evt);
            }
        });
        jmAyuda.add(jmiAyuda);

        jmiAcerca.setMnemonic('d');
        jmiAcerca.setText("Acerca de");
        jmiAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAcercaActionPerformed(evt);
            }
        });
        jmAyuda.add(jmiAcerca);

        jmbMenuPrincipal.add(jmAyuda);

        setJMenuBar(jmbMenuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAcercaActionPerformed
        JPanelAcercaDe jplAcercaDe = new JPanelAcercaDe();
        JButton btnOkAcercaDe = new JButton();
        btnOkAcercaDe.setText("Aceptar");
        btnOkAcercaDe.setMnemonic(KeyEvent.VK_A);

        btnOkAcercaDe.addActionListener((java.awt.event.ActionEvent evt1) -> {
            Component component1 = (Component) evt1.getSource();
            JDialog dialog = (JDialog) SwingUtilities.getRoot(component1);
            dialog.dispose();
        });

        Object[] options = {btnOkAcercaDe};
        btnOkAcercaDe.addActionListener((java.awt.event.ActionEvent evt1) -> {
            Component component1 = (Component) evt1.getSource();
            JDialog dialog = (JDialog) SwingUtilities.getRoot(component1);
            dialog.dispose();
        });

        JOptionPane.showOptionDialog(getParent(), jplAcercaDe, "Acerca de FirmaEC",
                JOptionPane.OK_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }//GEN-LAST:event_jmiAcercaActionPerformed

    private void jmiActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiActualizarActionPerformed
        try {
            Update update = new Update();
            Update.setAmbiente("--update");
            if (update.update() == null) {
                JOptionPane.showMessageDialog(rootPane, "Se encuentra en su última versión, no es necesario actualizar");
            } else {
                actualizar();
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jmiActualizarActionPerformed

    private void resetVerificarDocumento() {
        jtxtVerificarRuta.setText("");
        documento = null;
        DefaultTableModel tableModelCert = (DefaultTableModel) jtblVerificarDatosFirmante.getModel();
        tableModelCert.setRowCount(0);
        jtblVerificarDatosFirmante.setModel(tableModelCert);
        tableModelCert.fireTableDataChanged();
    }

    private void jmiAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAyudaActionPerformed
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                java.awt.Desktop dk = java.awt.Desktop.getDesktop();
                dk.browse(new java.net.URI(PropertiesUtils.getMessages().getProperty("url_ayuda_online")));
            } catch (IOException | URISyntaxException e) {
                System.out.println("Error al abrir URL: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jmiAyudaActionPerformed

    private void jmiPanelConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiPanelConfiguracionActionPerformed
        JPanelConfiguracion jPanelConfiguracion = new JPanelConfiguracion();
        javax.swing.JButton btnSalir = new javax.swing.JButton();
        btnSalir.setText("Salir");
        btnSalir.setMnemonic(java.awt.event.KeyEvent.VK_S);
        Object[] options = {btnSalir};
        btnSalir.addActionListener((java.awt.event.ActionEvent evt1) -> {
            java.awt.Component component1 = (java.awt.Component) evt1.getSource();
            javax.swing.JDialog dialog = (javax.swing.JDialog) javax.swing.SwingUtilities.getRoot(component1);
            dialog.dispose();
        });

        JOptionPane.showOptionDialog(null, jPanelConfiguracion, "Panel de Configuración",
                JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }//GEN-LAST:event_jmiPanelConfiguracionActionPerformed

    private void jbtnValidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnValidarActionPerformed
        if (jrbValidarToken.isSelected() || jrbValidarArchivo.isSelected()||jrbFirmarHSM.isSelected()) {
            KeyStoreProvider ksp;
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            KeyStore ks = null;
            try {
                // Si es linux y quiere validarCertificado token debe tener por lo menos una clave
                if ((OS.contains("linux") || OS.contains("mac")) && this.jrbValidarToken.isSelected() && jpfValidarClave.getPassword().length == 0) {
                    throw new CertificadoInvalidoException(messages.getProperty("mensaje.error.linux_clave"));
                }

                jplCertificadoValidar.setEnabled(false);
                if (this.jrbValidarToken.isSelected()) {
                    ks = KeyStoreProviderFactory.getKeyStore(new String(jpfValidarClave.getPassword()));
                    if (ks == null) {
                        throw new TokenNoEncontradoException(messages.getProperty("mensaje.error.token_contrasena_invalida") + " o " + messages.getProperty("mensaje.error.token_no_encontrado"));
                    }
                }
                if (this.jrbValidarArchivo.isSelected()) {
                    File llave = new File(jtxtValidarRuta.getText());
                    if (llave.exists() == true) {
                        ksp = new FileKeyStoreProvider(jtxtValidarRuta.getText());
                        ks = ksp.getKeystore(jpfValidarClave.getPassword());
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "No se encontró el certificado digital: " + llave.getPath(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (this.jrbFirmarHSM.isSelected()) {
                	 ksp = new Bit4IdWindosKeyStoreProvider();
                     ks = ksp.getKeystore();
                     if (ks == null) {
                         throw new TokenNoEncontradoException(messages.getProperty("mensaje.error.token_contrasena_invalida") + " o " + messages.getProperty("mensaje.error.token_no_encontrado"));
                     }                 
                       
                    
                }

                if (ks != null) {
                    X509Certificate x509Certificate = CertUtils.getCert(ks);
                    x509CertificateUtils = null;
                    x509CertificateUtils = new X509CertificateUtils();
                    x509CertificateUtils.validarX509Certificate(x509Certificate);
                    setearInfoValidacionCertificado(x509Certificate);
                    if (x509Certificate != null) {
                        agregarValidezCertificado(x509CertificateUtils);
                    }
                    jpfValidarClave.setText("");
                    jplCertificadoValidar.setEnabled(true);
                    setCursor(Cursor.getDefaultCursor());
                }
            } catch (KeyStoreException e) {
                setCursor(Cursor.getDefaultCursor());
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                if (e.getMessage().equals("java.io.IOException: keystore password was incorrect")) {
                    JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.clave_incorrecta"), "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.certificado_formato_invalido"), "Error", JOptionPane.ERROR_MESSAGE);
                }
                jpfValidarClave.setText("");
                jplCertificadoValidar.setEnabled(true);
            } catch (TokenNoEncontradoException | CertificadoInvalidoException | EntidadCertificadoraNoValidaException | HoraServidorException | RubricaException | HeadlessException | IOException | InvalidKeyException ex) {
                setCursor(Cursor.getDefaultCursor());
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                resetValidacionCertificado();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione tipo de certificado", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbtnValidarActionPerformed

    private void jbtnValidarRestablecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnValidarRestablecerActionPerformed
        resetValidacionCertificado();
    }//GEN-LAST:event_jbtnValidarRestablecerActionPerformed

    private void jbtnValidarExaminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnValidarExaminarActionPerformed
        jtxtValidarRuta.setText(FileUtils.rutaFichero(filtroCertificados));
    }//GEN-LAST:event_jbtnValidarExaminarActionPerformed

    private void jrbValidarTokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbValidarTokenActionPerformed
        selValidarToken();
    }//GEN-LAST:event_jrbValidarTokenActionPerformed

    private void jrbValidarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbValidarArchivoActionPerformed
        selValidarArchivo();
        if (PropertiesUtils.getPath().containsKey("user.home")) {
            jtxtValidarRuta.setText(PropertiesUtils.getPath().getProperty("user.home"));
        }
    }//GEN-LAST:event_jrbValidarArchivoActionPerformed

    private void jbtnVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnVerificarActionPerformed
        if (!jtxtVerificarRuta.getText().isEmpty()) {
            documento = new File(jtxtVerificarRuta.getText());
            if (documento.exists() == true) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                try {
                    jplDocumentoVerificar.setEnabled(false);
                    verificarDocumento();
                    jplDocumentoVerificar.setEnabled(true);
                    setCursor(Cursor.getDefaultCursor());
                } catch (NoSuchFileException ex) {
                    setCursor(Cursor.getDefaultCursor());
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.archivo_no_encontrado") + ": " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    jplDocumentoVerificar.setEnabled(true);
                } catch (RubricaException ex) {
                    setCursor(Cursor.getDefaultCursor());
                    System.err.println("Error no se pudo conectar al servicio de OSCP para verificar el certificado ");
                    String msgError = ex.getMessage();
                    if (msgError.contains("Los datos indicados no se corresponden ")) {
                        msgError = "El archivo puede que este corrupto o que no contenga una firma";
                    }
                    if (msgError.contains("Los datos indicados no son una firma")) {
                        msgError = "El archivo puede que este corrupto o que no contenga una firma";
                    }
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, msgError, "Error", JOptionPane.ERROR_MESSAGE);
                    jplDocumentoVerificar.setEnabled(true);
                    resetVerificarDocumento();
                } catch (Exception ex) {
                    setCursor(Cursor.getDefaultCursor());
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    jplDocumentoVerificar.setEnabled(true);
                    resetVerificarDocumento();
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "No se encontró el documento: " + documento.getPath(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione documento", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbtnVerificarActionPerformed

    private void jbtnRestablecerVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRestablecerVerificarActionPerformed
        resetVerificarDocumento();
    }//GEN-LAST:event_jbtnRestablecerVerificarActionPerformed

    private void jbtnVerificarExaminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnVerificarExaminarActionPerformed
        jtxtVerificarRuta.setText(FileUtils.rutaFichero(filtroDocumentos));
    }//GEN-LAST:event_jbtnVerificarExaminarActionPerformed

    private void jbtnRestablecerFirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRestablecerFirmarActionPerformed
        resetFirmarDocumento();
        resetDocumentos();
    }//GEN-LAST:event_jbtnRestablecerFirmarActionPerformed

    private void jbtnFirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFirmarActionPerformed
        if (jrbFirmarToken.isSelected() || jrbFirmarArchivo.isSelected()) {
            if (tableModelDocumentos.getRowCount() >= 0) {
                List<String> rutaDocumentos = existeDocumentos();
                if (rutaDocumentos.size() > 0) {
                    firmarDocumento(rutaDocumentos);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Seleccione documento", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        } if(jrbFirmarHSM.isSelected()){
            if (tableModelDocumentos.getRowCount() >= 0) {
                List<String> rutaDocumentos = existeDocumentos();
                if (rutaDocumentos.size() > 0) {
                    firmarDocumento(rutaDocumentos);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Seleccione documento", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        else {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione tipo de certificado", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbtnFirmarActionPerformed

    private void jbtnFirmarExaminarCertificadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFirmarExaminarCertificadoActionPerformed
        jtxtFirmarRuta.setText(FileUtils.rutaFichero(filtroCertificados));
    }//GEN-LAST:event_jbtnFirmarExaminarCertificadoActionPerformed

    private void jrbFirmarTokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbFirmarTokenActionPerformed
        System.out.println("Firmar con Token");
        this.selFirmarConToken();
    }//GEN-LAST:event_jrbFirmarTokenActionPerformed

    private void jrbFirmarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbFirmarArchivoActionPerformed
        System.out.println("Firmar con llave");
        this.selFirmarConArchivo();
        if (PropertiesUtils.getPath().containsKey("user.home")) {
            jtxtFirmarRuta.setText(PropertiesUtils.getPath().getProperty("user.home"));
        }
    }//GEN-LAST:event_jrbFirmarArchivoActionPerformed

    private void jbtnFirmarEliminarDocumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFirmarEliminarDocumentosActionPerformed
        if (jtblFirmarDocumentos.getSelectedRow() >= 0) {
            tableModelDocumentos.removeRow(jtblFirmarDocumentos.convertRowIndexToModel(jtblFirmarDocumentos.getSelectedRow()));
        } else {
            JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.seleccion_documento"), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbtnFirmarEliminarDocumentosActionPerformed

    private void jbtnFirmarExaminarDocumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFirmarExaminarDocumentosActionPerformed
        try {
            agregarDocumentos(FileUtils.rutaFicheros(filtroDocumentos));
        } catch (Exception ex) {
            Logger.getLogger(jPanelVariosDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbtnFirmarExaminarDocumentosActionPerformed

    private void jrbFirmarHSMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbFirmarHSMActionPerformed
        System.out.println("Firmar con HSM");
        this.selFirmarConHSM();
    }//GEN-LAST:event_jrbFirmarHSMActionPerformed

    private void previewPdf() {
        razonFirma = null;
        point = null;
        try {
            JPanelVisualizadorPdf jPanelVisualizadorPdf = new JPanelVisualizadorPdf(documento, 70, 153, 50);
            JButton btnEstampar = new JButton();
            btnEstampar.setText("Estampar");
            btnEstampar.setMnemonic(KeyEvent.VK_A);
            Object[] options = {btnEstampar};
            btnEstampar.addActionListener((java.awt.event.ActionEvent evt) -> {
                try {
                    documento = jPanelVisualizadorPdf.getDocumento();
                    razonFirma = jPanelVisualizadorPdf.getRazonFirma().trim();
                    pagina = jPanelVisualizadorPdf.getPagina();
                    point = jPanelVisualizadorPdf.getPoint();
                    Component component1 = (Component) evt.getSource();
                    JDialog dialog = (JDialog) SwingUtilities.getRoot(component1);
                    dialog.dispose();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            JOptionPane.showOptionDialog(getParent(), jPanelVisualizadorPdf, "Visualizador PDF para estampado de firma en formato A4",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        } catch (HeadlessException e) {
            LOGGER.log(Level.SEVERE, messages.getProperty("mensaje.error.documento_problemas") + "\n" + documento, e);
            JOptionPane.showMessageDialog(this, messages.getProperty("mensaje.error.documento_problemas") + "\n" + documento, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void cargarPropiedades() {
        messages = PropertiesUtils.getMessages();
        config = PropertiesUtils.getConfig();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Log.initLogging();
        cargarPropiedades();
        Update update = new Update();
        if (args != null && args.length > 0 && ("--update".equals(args[0]) || "--updatedes".equals(args[0]))) {
            try {
                Update.setAmbiente(args[0]);
                File jar = update.actualizarFirmador();
                update.updateFirmador(jar);

                if (!isMac()) {
                    File clienteJar = update.actualizarCliente();
                    update.updateCliente(clienteJar);
                }
                System.exit(0);
            } catch (IllegalArgumentException | IOException e) {
                LOGGER.log(Level.SEVERE, "Error al actualizar:", e);
                System.exit(1);
            }
        } else {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             /* For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            version = PropertiesUtils.getConfig().getProperty("version");
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(() -> {
                new Main(null).setVisible(true);
            });
            update(version);
            LOGGER.log(Level.INFO, "Firmador: {0} JRE: {1} Sistema Operativo: {2}", new Object[]{version, OsUtils.getJavaVersion(), OsUtils.getOs()});
        }
    }

    public static boolean isMac() {
        String osName = System.getProperty("os.name");
        LOGGER.log(Level.INFO, "Operating System:{0}", osName);
        return osName.toUpperCase().contains("MAC");
    }

    private static void update(String version) {
        Update update = new Update();
        if (!version.contains("BETA")) {
            try {
                Update.setAmbiente("--update");
                String mensaje = update.update();
                if (mensaje != null) {
                    SwingLink link = new SwingLink(mensaje, "FirmaEC", PropertiesUtils.getMessages().getProperty("url_ayuda_online"));
                    JOptionPane.showMessageDialog(null, link);
                }
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "No se puede comprobar actualizaciones", ex);
                JOptionPane.showMessageDialog(null, "No se puede comprobar actualizaciones", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgDocumentos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneFirmarDocumentos;
    private javax.swing.JScrollPane jScrollPaneValidarDatosCertificados;
    private javax.swing.JScrollPane jScrollPaneVerificarDatosFirmante;
    private javax.swing.JLabel jblCertificadoEnFimador;
    private javax.swing.JLabel jblCertificadoFirmar;
    private javax.swing.JLabel jblClave;
    private javax.swing.JButton jbtnFirmar;
    private javax.swing.JButton jbtnFirmarEliminarDocumentos;
    private javax.swing.JButton jbtnFirmarExaminarCertificado;
    private javax.swing.JButton jbtnFirmarExaminarDocumentos;
    private javax.swing.JButton jbtnRestablecerFirmar;
    private javax.swing.JButton jbtnRestablecerVerificar;
    private javax.swing.JButton jbtnValidar;
    private javax.swing.JButton jbtnValidarExaminar;
    private javax.swing.JButton jbtnValidarRestablecer;
    private javax.swing.JButton jbtnVerificar;
    private javax.swing.JButton jbtnVerificarExaminar;
    private javax.swing.JCheckBox jchkBoxFirmaInvisible;
    private javax.swing.JLabel jlbArchivoFirmadoVerficar;
    private javax.swing.JLabel jlbCertificadoValidar;
    private javax.swing.JLabel jlbCertificadoValidarCert;
    private javax.swing.JLabel jlbCertificadoVldCert;
    private javax.swing.JLabel jlblDocumentosFirmar;
    private javax.swing.JLabel jlblResultadoValidacion;
    private javax.swing.JLabel jlblResultadoVerificacion;
    private javax.swing.JMenu jmAyuda;
    private javax.swing.JMenu jmConfiguracion;
    private javax.swing.JMenuBar jmbMenuPrincipal;
    private javax.swing.JMenuItem jmiAcerca;
    private javax.swing.JMenuItem jmiActualizar;
    private javax.swing.JMenuItem jmiAyuda;
    private javax.swing.JMenuItem jmiPanelConfiguracion;
    private javax.swing.JPasswordField jpfFirmarClave;
    private javax.swing.JPasswordField jpfValidarClave;
    private javax.swing.JPanel jplCertificadoFirmar;
    private javax.swing.JPanel jplCertificadoValidar;
    private javax.swing.JPanel jplDocumentoVerificar;
    private javax.swing.JPanel jplDocumentos;
    private javax.swing.JPanel jplFirmarDocumento;
    private javax.swing.JPanel jplValidacion;
    private javax.swing.JPanel jplValidarCertificado;
    private javax.swing.JPanel jplVerificacion;
    private javax.swing.JPanel jplVerificarDocumento;
    private javax.swing.JRadioButton jrbFirmarArchivo;
    private javax.swing.JRadioButton jrbFirmarHSM;
    private javax.swing.JRadioButton jrbFirmarToken;
    private javax.swing.JRadioButton jrbValidarArchivo;
    private javax.swing.JRadioButton jrbValidarToken;
    private javax.swing.JTable jtblFirmarDocumentos;
    private javax.swing.JTable jtblValidarDatosCertificados;
    private javax.swing.JTable jtblVerificarDatosFirmante;
    private javax.swing.JTextField jtxtFirmarRuta;
    private javax.swing.JTextField jtxtValidarRuta;
    private javax.swing.JTextField jtxtVerificarRuta;
    private javax.swing.JTabbedPane mainPanel;
    private javax.swing.ButtonGroup tipoFirmaBtnGRP;
    // End of variables declaration//GEN-END:variables
}
