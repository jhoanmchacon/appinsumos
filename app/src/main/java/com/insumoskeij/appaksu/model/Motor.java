package com.insumoskeij.appaksu.model;

public class Motor {
    private String id_motor;
    private  String nombre_motor;

    public Motor(String id_motor, String nombre_motor) {
        this.id_motor = id_motor;
        this.nombre_motor = nombre_motor;
    }

    public Motor() {
    }

    public String getId_motor() {
        return id_motor;
    }

    public void setId_motor(String id_motor) {
        this.id_motor = id_motor;
    }

    public String getNombre_motor() {
        return nombre_motor;
    }

    public void setNombre_motor(String nombre_motor) {
        this.nombre_motor = nombre_motor;
    }
}
