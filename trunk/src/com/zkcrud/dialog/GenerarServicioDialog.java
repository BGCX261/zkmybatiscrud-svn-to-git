/*
 * GenerarServicioDialog.java
 *
 * Created on __DATE__, __TIME__
 */
package com.zkcrud.dialog;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.zkcrud.generador.Generador;
import com.zkcrud.model.Contenedor;
import com.zkcrud.model.DataDAO;
import com.zkcrud.tablemodel.TableModel;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author __USER__
 */
public class GenerarServicioDialog extends javax.swing.JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8636742539969166156L;
	private String proyecto;
    private String esquema;
    private String tabla;
    private DataDAO dao;
    private TableModel tableModel;
    private List fields;
    public static final String[] DATA = {"Textbox", "Textbox_area", "Intbox",
        "Doublebox", "Listbox", "Checkbox", "Radio", "Datebox"};
    public static final String[] DATA_TIPOS = {"Defecto", "Grid_radio1", "Grid_checkbox1",
        "Grid_tabla1", "Grid_tabla2", "Grid_tabla3"};
    public static final Integer[] DATA_COLUMNAS_RADIO = {3, 6, 9, 12};
    public static final Integer[] DATA_COLUMNAS_CHECKBOX = {2, 4, 6, 8, 10, 12};
    public static final Integer[] DATA_COLUMNAS_TABLA1 = {2, 4, 6, 8, 10, 12};
    public static final Integer[] DATA_COLUMNAS_TABLA = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private TableModel tableModel_contenedores;
    private List<Contenedor> listado_contenedores = new ArrayList<Contenedor>();

    /**
     * Creates new form GenerarServicioDialog
     */
    public GenerarServicioDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        tableModel = new TableModel(true);
        tableModel_contenedores = new TableModel(false);

        tableModel_contenedores.addColumn("CONTENEDOR");
        tableModel_contenedores.addColumn("TIPO");
        tableModel_contenedores.addColumn("COLUMNAS");

        initComponents();

        JComboBox comboBox = new JComboBox(DATA_TIPOS);
        DefaultCellEditor defaultCellEditor = new DefaultCellEditor(
                comboBox);
        tableModel_contenedores.addRow(new Object[]{"Defecto", "Defecto", "0"});

        Contenedor contenedor = new Contenedor("Defecto", "Defecto", 0);
        listado_contenedores.add(contenedor);

        jTable2.getColumnModel().getColumn(1)
                .setCellEditor(defaultCellEditor);

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        bt_cerrar = new javax.swing.JButton();
        bt_generar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Generación de Servicios");
        setModal(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Servicio para la tabla ");

        jTable1.setModel(tableModel);
        jScrollPane1.setViewportView(jTable1);

        bt_cerrar.setText("Cerrar");
        bt_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cerrarActionPerformed(evt);
            }
        });

        bt_generar.setText("Generar Servicios");
        bt_generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_generarActionPerformed(evt);
            }
        });

        jTable2.setModel(tableModel_contenedores);
        jScrollPane2.setViewportView(jTable2);

        jButton1.setText("Agregar contedor");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Quitar contenedor");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane2)
                    .add(jScrollPane1)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(bt_generar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 127, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bt_cerrar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 611, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createSequentialGroup()
                                .add(jButton1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButton2)))
                        .add(0, 389, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(jButton2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(bt_cerrar)
                    .add(bt_generar)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String nombre = "";

            try {
                nombre = JOptionPane.showInputDialog(this, "Digite nombre del contenedor",
                        "Contenedor", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                nombre = "contenedor1";
            }
            if (nombre != null && !nombre.isEmpty()) {

                boolean existe = false;

                for (Contenedor contenedor : listado_contenedores) {
                    if (nombre.equals(contenedor.getNombre())) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    String tipo = "";
                    try {
                        tipo = (String) JOptionPane.showInputDialog(this, "Seleccione tipo de contenedor", "Tipo",
                                JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Grid_radio1", "Grid_checkbox1", "Grid_tabla1", "Grid_tabla2", "Grid_tabla3"}, "Grid_radio1");
                    } catch (Exception e) {
                        tipo = "Grid_radio1";
                    }
                    
                    if(tipo == null){
                        tipo = "Grid_radio1";
                    }

                    Integer columnas = 3;

                    if (tipo.equals("Grid_radio1")) {
                        try {
                            columnas = (Integer) JOptionPane.showInputDialog(this, "Seleccione cantidad de columnas", "Columnas",
                                    JOptionPane.INFORMATION_MESSAGE, null, DATA_COLUMNAS_RADIO, 3);
                        } catch (Exception e) {
                            columnas = 3;
                        }
                    } else if (tipo.equals("Grid_checkbox1")) {
                        try {
                            columnas = (Integer) JOptionPane.showInputDialog(this, "Seleccione cantidad de columnas", "Columnas",
                                    JOptionPane.INFORMATION_MESSAGE, null, DATA_COLUMNAS_CHECKBOX, 2);
                        } catch (Exception e) {
                            columnas = 2;
                        }
                    } else if (tipo.equals("Grid_tabla1")) {
                        try {
                            columnas = (Integer) JOptionPane.showInputDialog(this, "Seleccione cantidad de columnas", "Columnas",
                                    JOptionPane.INFORMATION_MESSAGE, null, DATA_COLUMNAS_TABLA1, 2);
                        } catch (Exception e) {
                            columnas = 2;
                        }
                    } else if (tipo.equals("Grid_tabla2") || tipo.equals("Grid_tabla3")) {
                        try {
                            columnas = (Integer) JOptionPane.showInputDialog(this, "Seleccione cantidad de columnas", "Columnas",
                                    JOptionPane.INFORMATION_MESSAGE, null, DATA_COLUMNAS_TABLA, 1);
                        } catch (Exception e) {
                            columnas = 1;
                        }
                    }

                    tableModel_contenedores = (TableModel) jTable2.getModel();
                    jTable2.setModel(new DefaultTableModel());
                    tableModel_contenedores.addRow(new Object[]{nombre, tipo, columnas});
                    jTable2.setModel(tableModel_contenedores);

                    Contenedor contenedor = new Contenedor(nombre, tipo, columnas);
                    listado_contenedores.add(contenedor);

                    tableModel = new TableModel(true);
                    jTable1.setModel(tableModel);
                    generarListaCampos();
                    cargarDatosTabla();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            tableModel_contenedores = (TableModel) jTable2.getModel();
            jTable2.setModel(new DefaultTableModel());
            if (tableModel_contenedores.getRowCount() > 1) {
                tableModel_contenedores.removeRow(tableModel_contenedores.getRowCount() - 1);
            }
            listado_contenedores.remove(listado_contenedores.size() - 1);
            jTable2.setModel(tableModel_contenedores);
            tableModel = new TableModel(true);
            jTable1.setModel(tableModel);
            generarListaCampos();
            cargarDatosTabla();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void bt_generarActionPerformed(java.awt.event.ActionEvent evt) {
        generarServicios();
    }

    private void bt_cerrarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    public void generarServicios() {
        try {
            List lista_campos = new LinkedList();
            for (int i = 0; i < fields.size(); i++) {
                Vector aux = (Vector) fields.get(i);
                Vector v = new Vector();
                v.add(aux.get(0).toString());
                v.add(aux.get(1).toString());
                v.add(tableModel.getValueAt(i, 1).toString());
                v.add(tableModel.getValueAt(i, 2));
                v.add(tableModel.getValueAt(i, 3));
                v.add(tableModel.getValueAt(i, 4));
                v.add(aux.get(2).toString());
                v.add(tableModel.getValueAt(i, 5));
                lista_campos.add(v);

            }
            //System.out.println(lista_campos.toString());
            Generador generador = new Generador(proyecto, lista_campos, tabla, esquema);
            generador.generarCrud(dao);

            JOptionPane.showMessageDialog(this,
                    "Los datos se generaron", "Informacion",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(this, e.getMessage(), "error",
                    JOptionPane.ERROR_MESSAGE);
        }

        //JOptionPane.showMessageDialog(this, jLabel1.getText());
    }

    public void generarListaCampos() throws Exception {
        fields = dao.getFieldsTable(esquema, tabla);
    }

    public void cargarDatosTabla() throws Exception {
        for (int i = 0; i < fields.size(); i++) {
            Vector v = (Vector) fields.get(i);
            // Cargar los valores para la fila
            boolean obligatorio = false, consulta = true, registro = true;
            String defecto = "Textbox";
            JComboBox comboBox = new JComboBox(DATA);
            if (v.get(0).toString().equals("codigo_dane") || v.get(0).toString().equals("codigo_empresa") || v.get(0).toString().equals("cons_sede") || v.get(0).toString().equals("codigo_sucursal") || v.get(0).toString().equals("creacion_date") || v.get(0).toString().equals("ultimo_update") || v.get(0).toString().equals("creacion_user") || v.get(0).toString().equals("ultimo_user")) {
                obligatorio = false;
                consulta = false;
                registro = false;
                //comboBox.setEnabled(false);
                //defecto = "";
            } else {
                //comboBox.setEnabled(true);
                obligatorio = false;
                consulta = false;
                registro = true;
            }

            if (v.get(1).toString().toLowerCase().startsWith("varchar")) {
                defecto = "Textbox";
            } else if (v.get(1).toString().toLowerCase().startsWith("text")) {
                defecto = "Textbox_area";
            } else if (v.get(1).toString().toLowerCase().startsWith("int")) {
                defecto = "Intbox";
            } else if (v.get(1).toString().toLowerCase().startsWith("numeric") || v.get(1).toString().toLowerCase().startsWith("double")) {
                defecto = "Doublebox";
            } else if (v.get(1).toString().toLowerCase().startsWith("timestamp") || v.get(1).toString().toLowerCase().startsWith("date")) {
                defecto = "Datebox";
            } else if (v.get(1).toString().toLowerCase().startsWith("bool")) {
                defecto = "Checkbox";
            }

            String contenedor = "Defecto";

            DefaultCellEditor defaultCellEditor = new DefaultCellEditor(
                    comboBox);

            JComboBox comboBox2 = new JComboBox(listado_contenedores.toArray());

            DefaultCellEditor defaultCellEditor2 = new DefaultCellEditor(
                    comboBox2);

            Object[] datos = new Object[]{v.get(0).toString(), defecto, obligatorio, consulta, registro, contenedor};
            tableModel.addRow(datos);
            jTable1.getColumnModel().getColumn(1)
                    .setCellEditor(defaultCellEditor);

            jTable1.getColumnModel().getColumn(5)
                    .setCellEditor(defaultCellEditor2);

            //defaultTableModel.addRow(datos); 
        }
    }

    /**
     * @param proyecto the proyecto to set
     */
    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * @return the proyecto
     */
    public String getProyecto() {
        return proyecto;
    }

    /**
     * @param esquema the esquema to set
     */
    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }

    /**
     * @return the esquema
     */
    public String getEsquema() {
        return esquema;
    }

    /**
     * @param tabla the tabla to set
     */
    public void setTabla(String tabla) {
        this.tabla = tabla;
        jLabel1.setText("Servicios para la tabla " + tabla);
    }

    /**
     * @return the tabla
     */
    public String getTabla() {
        return tabla;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(DataDAO dao) {
        this.dao = dao;
    }

    /**
     * @return the dao
     */
    public DataDAO getDao() {
        return dao;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_cerrar;
    private javax.swing.JButton bt_generar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}