/*
 * ManejoFichero.java
 *
 * Created on 17 de mayo de 2008, 9:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.zkcrud.generador;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 *
 * @author FERNEY JIMENEZ LOPEZ - TEGNOLOGO EN SISTEMAS
 */
public class ManejoFichero {

    File fichero;

    /** Creates a new instance of ManejoFichero */
    public ManejoFichero() {
    }

    public void crearFile(String direccion, String[] carpetas, String nombreFichero) throws Exception {
        // Creamos el objeto que encapsula el fichero
        for (int i = 0; i < carpetas.length; i++) {
            direccion += "/" + carpetas[i];
        }
        fichero = new File(direccion);
        fichero.mkdirs();
        direccion += "/" + nombreFichero;
        fichero = new File(direccion);

        DataOutputStream salida;
        salida = new DataOutputStream(new FileOutputStream(direccion));
        salida.writeBytes(direccion);
    }

    public void writeFile(String mensaje) throws Exception {
        FileOutputStream fileOutput = new FileOutputStream(fichero.getAbsolutePath());
        fileOutput.write(mensaje.getBytes());
        fileOutput.close();
    }
}
