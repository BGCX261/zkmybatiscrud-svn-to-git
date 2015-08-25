/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zkcrud.model;

/**
 *
 * @author Dario Perez Campillo
 */
public class Contenedor {
    private String nombre;
    private String tipo;
    private Integer columnas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getColumnas() {
        return columnas;
    }

    public void setColumnas(Integer columnas) {
        this.columnas = columnas;
    }

    public Contenedor(String nombre, String tipo, Integer columnas) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.columnas = columnas;
    }

    public Contenedor() {
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
}
