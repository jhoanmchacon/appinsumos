package com.insumoskeij.appaksu.model;

public class Modelo {

    private String id_modelo;
    private String nombre_modelo;

    public Modelo(String id_modelo, String nombre_modelo) {
        this.id_modelo = id_modelo;
        this.nombre_modelo = nombre_modelo;
    }

    public Modelo() {
    }

    public String getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(String id_modelo) {
        this.id_modelo = id_modelo;
    }

    public String getNombre_modelo() {
        return nombre_modelo;
    }

    public void setNombre_modelo(String nombre_modelo) {
        this.nombre_modelo = nombre_modelo;
    }

}
