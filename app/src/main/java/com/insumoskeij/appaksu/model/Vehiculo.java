package com.insumoskeij.appaksu.model;

public class Vehiculo {

    private String txtMarca;
    private String txtModelo;
    private String txtserie;
    private String txtMotor;
    private String txtAnno;
    private String txtKwPotencia;

    public Vehiculo(String txtMarca, String txtModelo, String txtserie, String txtMotor, String txtAnno, String txtKwPotencia) {
        this.txtMarca = txtMarca;
        this.txtModelo = txtModelo;
        this.txtserie = txtserie;
        this.txtMotor = txtMotor;
        this.txtAnno = txtAnno;
        this.txtKwPotencia = txtKwPotencia;
    }

    public Vehiculo() {
    }

    public String getTxtMarca() {
        return txtMarca;
    }

    public void setTxtMarca(String txtMarca) {
        this.txtMarca = txtMarca;
    }

    public String getTxtModelo() {
        return txtModelo;
    }

    public void setTxtModelo(String txtModelo) {
        this.txtModelo = txtModelo;
    }

    public String getTxtserie() {
        return txtserie;
    }

    public void setTxtserie(String txtserie) {
        this.txtserie = txtserie;
    }

    public String getTxtMotor() {
        return txtMotor;
    }

    public void setTxtMotor(String txtMotor) {
        this.txtMotor = txtMotor;
    }

    public String getTxtAnno() {
        return txtAnno;
    }

    public void setTxtAnno(String txtAnno) {
        this.txtAnno = txtAnno;
    }

    public String getTxtKwPotencia() {
        return txtKwPotencia;
    }

    public void setTxtKwPotencia(String txtKwPotencia) {
        this.txtKwPotencia = txtKwPotencia;
    }
}
