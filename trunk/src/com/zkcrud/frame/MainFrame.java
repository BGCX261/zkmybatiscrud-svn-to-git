/*
 * MainFrame.java
 *
 * Created on __DATE__, __TIME__
 */
package com.zkcrud.frame;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.zkcrud.dialog.GenerarServicioDialog;
import com.zkcrud.model.DataDAO;
import com.zkcrud.model.Model;

/**
 *
 * @author __USER__
 */
public class MainFrame extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4435997500686131809L;
	/**
     * Creates new form MainFrame
     */
    public MainFrame() {
        try {
            javax.swing.UIManager
                    .setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tf_proyecto = new javax.swing.JTextField();
        tf_url = new javax.swing.JTextField();
        cbx_driver = new javax.swing.JComboBox();
        tf_esquema = new javax.swing.JTextField();
        tf_usuario = new javax.swing.JTextField();
        pf_password = new javax.swing.JPasswordField();
        bt_cancelar = new javax.swing.JButton();
        bt_aceptar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        tf_tabla = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Generador zk-spring-mybatis");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Proyecto:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Driver:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Url:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Esquema:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Usuario:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Password:");

        tf_proyecto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tf_proyecto.setText("contaweb");
        tf_proyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_proyectoActionPerformed(evt);
            }
        });

        tf_url.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tf_url.setText("jdbc:postgresql://localhost:5433/contawebhm_test");
        tf_url.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_urlActionPerformed(evt);
            }
        });

        cbx_driver.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbx_driver.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "org.postgresql.Driver" }));

        tf_esquema.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tf_esquema.setText("public");
        tf_esquema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_esquemaActionPerformed(evt);
            }
        });

        tf_usuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tf_usuario.setText("postgres");
        tf_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usuarioActionPerformed(evt);
            }
        });

        pf_password.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pf_password.setText("123456");

        bt_cancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bt_cancelar.setText("Cancelar");
        bt_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cancelarActionPerformed(evt);
            }
        });

        bt_aceptar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bt_aceptar.setText("Aceptar");
        bt_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_aceptarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("TABLA:");

        tf_tabla.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tf_tabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_tablaActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(243, 243, 243)
                        .add(bt_aceptar)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bt_cancelar))
                    .add(layout.createSequentialGroup()
                        .add(78, 78, 78)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, tf_proyecto)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, tf_esquema)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, tf_usuario)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, tf_url)
                            .add(cbx_driver, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(tf_tabla)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, pf_password))))
                .add(71, 71, 71))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(tf_proyecto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(cbx_driver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(tf_url, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(tf_esquema, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(tf_usuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(pf_password, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7)
                    .add(tf_tabla, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(bt_cancelar)
                    .add(bt_aceptar))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("deprecation")
	private void bt_aceptarActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        try {

            if (tf_proyecto.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Digite el nombre del proyecto a generar", "Error!!!",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tf_url.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Digite el nombre de la url a generar", "Error!!!",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tf_usuario.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Digite el nombre del usuario de la base de datos",
                        "Error!!!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tf_tabla.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Digite el nombre de la tabla a generar", "Error!!!",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String driver = cbx_driver.getSelectedItem().toString();
            String proyecto = tf_proyecto.getText().trim();
            String url = tf_url.getText().trim();
            String esquema = tf_esquema.getText().trim();
            String username = tf_usuario.getText().trim();
            String password = pf_password.getText().trim();
            String tabla = tf_tabla.getText().trim();

            Model model = new Model();
            model.setJdbcDriver(driver);
            model.setDatabaseUrl(url);
            model.setUserName(username);
            model.setPassword(password);
            model.connect();
            System.out.println("conexion ok");

            DataDAO dao = new DataDAO(model);

            GenerarServicioDialog dialog = new GenerarServicioDialog(
                    this, true);
            dialog.setProyecto(proyecto);
            dialog.setEsquema(esquema);
            dialog.setTabla(tabla);
            dialog.setDao(dao);
            dialog.generarListaCampos();
            dialog.cargarDatosTabla();
            //dialog.generarServicios();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            //Generator generator = new Generator(proyecto, dao, table);
            //generator.generator();


            //System.exit(EXIT_ON_CLOSE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(this, e.getMessage(), "error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bt_cancelarActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.exit(EXIT_ON_CLOSE);
    }

    private void tf_tablaActionPerformed(java.awt.event.ActionEvent evt) {
    }

    public void tf_proyectoActionPerformed(java.awt.event.ActionEvent evt) {
    }

    public void tf_urlActionPerformed(java.awt.event.ActionEvent evt) {
    }

    public void tf_esquemaActionPerformed(java.awt.event.ActionEvent evt) {
    }

    public void tf_usuarioActionPerformed(java.awt.event.ActionEvent evt) {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_aceptar;
    private javax.swing.JButton bt_cancelar;
    private javax.swing.JComboBox cbx_driver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPasswordField pf_password;
    private javax.swing.JTextField tf_esquema;
    private javax.swing.JTextField tf_proyecto;
    private javax.swing.JTextField tf_tabla;
    private javax.swing.JTextField tf_url;
    private javax.swing.JTextField tf_usuario;
    // End of variables declaration//GEN-END:variables
}