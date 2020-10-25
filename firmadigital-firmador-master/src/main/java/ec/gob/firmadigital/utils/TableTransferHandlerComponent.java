/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.firmadigital.utils;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TableTransferHandlerComponent extends TransferHandler {

    private final List<String> listExtension;

    public TableTransferHandlerComponent(List<String> listExtension) {
        this.listExtension = listExtension;
    }

    @Override
    public boolean canImport(JComponent jComponent, DataFlavor[] dataFlavors) {
        for (DataFlavor flavor : dataFlavors) {
            if (flavor.equals(DataFlavor.javaFileListFlavor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean importData(JComponent jComponent, Transferable transferable) {
        List<String> rutaDocumentos = new ArrayList<>();
        try {
            for (java.awt.Component component : jComponent.getComponents()) {
                if (component instanceof javax.swing.JViewport) {
                    javax.swing.JViewport viewport = ((javax.swing.JViewport) component);
                    JTable jTable = (JTable) viewport.getView();

                    List list = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    Iterator iter = list.iterator();
                    while (iter.hasNext()) {
                        File file = (File) iter.next();
                        for (String str : listExtension) {
                            if (getFileExtension(file.getName()).toLowerCase().equals(str)) {
                                rutaDocumentos.add(file.getCanonicalPath());
                            }
                        }
                    }
                    agregarDocumentos(rutaDocumentos, jTable);
                    return true;
                }
            }
        } catch (IOException ex) {
            System.err.println("IOError getting data: " + ex);
        } catch (UnsupportedFlavorException e) {
            System.err.println("Unsupported Flavor: " + e);
        } catch (Exception ex) {
            Logger.getLogger(TableTransferHandlerComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void agregarDocumentos(List<String> documentos, JTable jTable) throws Exception {
        DefaultTableModel tableModelDocumentos = (DefaultTableModel) jTable.getModel();
        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModelDocumentos);
        jTable.setRowSorter(sorter);

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

        jTable.setModel(tableModelDocumentos);
        tableModelDocumentos.fireTableDataChanged();
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return null;
        }
    }

    public static void iconInformation(javax.swing.JTable jTable, javax.swing.JPanel jPanel, String nombreComponente) {
        String message = "Arrastre su " + (nombreComponente == null ? "archivo" : nombreComponente) + " a este campo (Drag&Drop)";
        int width = 15, height = 15;
        Icon icon = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("images/info.png")).getImage().getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT));
        JLabel lb = new JLabel(icon);
        lb.setBounds(
                jTable.getBounds().x,
                jTable.getBounds().y + width,
                width,
                height);
        lb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                javax.swing.JOptionPane.showMessageDialog(null, message, "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        });
        jTable.setToolTipText(message);
        lb.setToolTipText("Información");
        jPanel.add(lb);
    }
}
