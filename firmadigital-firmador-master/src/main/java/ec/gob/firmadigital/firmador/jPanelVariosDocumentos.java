/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.firmadigital.firmador;

import ec.gob.firmadigital.utils.WordWrapCellRenderer;
import io.rubrica.utils.FileUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author mfernandez
 */
public class jPanelVariosDocumentos extends javax.swing.JPanel {

    java.util.List<String> documentos = new java.util.ArrayList<>();
    private DefaultTableModel tableModelDocumentos;
    java.util.List<String> ruta = new java.util.ArrayList<>();

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

        java.util.List<String> documentos = new java.util.ArrayList<>();
        documentos.add("documento 1");
        documentos.add("documento 2");
        documentos.add("documento 3");
        jPanelVariosDocumentos jPanelVariosDocumentos = new jPanelVariosDocumentos(documentos);
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

    /**
     * Creates new form jPanelVariosDocumentos
     * @param documentos
     */
    public jPanelVariosDocumentos(List<String> documentos) {
        this.documentos = documentos;
        initComponents();
        inicializarTabla();
        eventos();
        llenarTabla();
        jblMensaje.setText("<html><b>Total: "+documentos.size()+"</b></html>");
    }
    
    private void eventos() {
        jtblDocumentos.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
                    String ruta = (String) tableModelDocumentos.getValueAt(jtblDocumentos.convertRowIndexToModel(jtblDocumentos.getSelectedRow()), 0);
                    if (jtblDocumentos.getSelectedRows().length > 0 && ruta != null && !ruta.isEmpty()) {
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
        jtblDocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String ruta = (String) tableModelDocumentos.getValueAt(jtblDocumentos.convertRowIndexToModel(jtblDocumentos.getSelectedRow()), 0);
                    if (jtblDocumentos.getSelectedRows().length > 0 && ruta != null && !ruta.isEmpty()) {
                        try {
                            FileUtils.abrirDocumento(ruta);
                        } catch (IOException ex) {
                            Logger.getLogger(JPanelVisualizadorPdf.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }

    private void inicializarTabla() {
        jtblDocumentos.getColumnModel().getColumn(0).setCellRenderer(new WordWrapCellRenderer());
    }

    private void llenarTabla() {
        tableModelDocumentos = (DefaultTableModel) jtblDocumentos.getModel();
        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModelDocumentos);
        jtblDocumentos.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);

        tableModelDocumentos.setRowCount(0);

        documentos.stream().map((documento) -> {
            String[] dataCert = new String[1];
            dataCert[0] = documento;
            return dataCert;
        }).forEachOrdered((dataCert) -> {
            tableModelDocumentos.addRow(dataCert);
        });

        jtblDocumentos.setModel(tableModelDocumentos);
        tableModelDocumentos.fireTableDataChanged();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblDocumentos = new javax.swing.JTable();
        jblMensaje = new javax.swing.JLabel();

        jtblDocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
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
        jScrollPane3.setViewportView(jtblDocumentos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
            .addComponent(jblMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jblMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jblMensaje;
    private javax.swing.JTable jtblDocumentos;
    // End of variables declaration//GEN-END:variables
}
