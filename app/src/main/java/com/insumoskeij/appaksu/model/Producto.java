package com.insumoskeij.appaksu.model;

import android.graphics.Bitmap;

public class Producto {
    private String txtserie;
    private String txtModelo;
    private String txtMotor;
    private String txtMarca;
    private String txtKwPotencia;
    private String txtDetalle;
    private String txtDetalleCodBarra;
    private String txtDetalleMedida;
    private String txtMedidaLP;
    private String txtDetallePeso;
    private String txtAnno;
    private String txtCodigoProd;
    private String txtTipoProd;
    private Bitmap imgProd;
    private String rutaImg;
    private String rutaImg_2;
    private  String txtDetalleVeh;
    private  String txtDetalleOMarca;
    private String txtDetalleOCod;
    private String txtDetalleAnno;
    private String txtDetalleMotor;
    private String getTxtDetalleHP;

    public Producto(String txtserie, String txtModelo, String txtMotor, String txtMarca, String txtKwPotencia, String txtDetalle, String txtDetalleCodBarra, String txtDetalleMedida, String txtMedidaLP, String txtDetallePeso, String txtAnno, String txtCodigoProd, String txtTipoProd, Bitmap imgProd, String rutaImg, String rutaImg_2, String txtDetalleVeh, String txtDetalleOMarca, String txtDetalleOCod, String txtDetalleAnno, String txtDetalleMotor, String getTxtDetalleHP) {
        this.txtserie = txtserie;
        this.txtModelo = txtModelo;
        this.txtMotor = txtMotor;
        this.txtMarca = txtMarca;
        this.txtKwPotencia = txtKwPotencia;
        this.txtDetalle = txtDetalle;
        this.txtDetalleCodBarra = txtDetalleCodBarra;
        this.txtDetalleMedida = txtDetalleMedida;
        this.txtMedidaLP = txtMedidaLP;
        this.txtDetallePeso = txtDetallePeso;
        this.txtAnno = txtAnno;
        this.txtCodigoProd = txtCodigoProd;
        this.txtTipoProd = txtTipoProd;
        this.imgProd = imgProd;
        this.rutaImg = rutaImg;
        this.rutaImg_2 = rutaImg_2;
        this.txtDetalleVeh = txtDetalleVeh;
        this.txtDetalleOMarca = txtDetalleOMarca;
        this.txtDetalleOCod = txtDetalleOCod;
        this.txtDetalleAnno = txtDetalleAnno;
        this.txtDetalleMotor = txtDetalleMotor;
        this.getTxtDetalleHP = getTxtDetalleHP;
    }

    public Producto () {
    }

    public String getTxtMedidaLP() {
        return txtMedidaLP;
    }

    public void setTxtMedidaLP(String txtMedidaLP) {
        this.txtMedidaLP = txtMedidaLP;
    }

    public String getTxtSerie() {
        return txtserie;
    }

    public void setTxtserie(String txtserie) {
        this.txtserie = txtserie;
    }

    public String getTxtDetalle() {
        return txtDetalle;
    }

    public void setTxtDetalle(String txtDetalle) {
        this.txtDetalle = txtDetalle;
    }

    public String getTxtDetalleCodBarra() {
        return txtDetalleCodBarra;
    }

    public void setTxtDetalleCodBarra(String txtDetalleCodBarra) {
        this.txtDetalleCodBarra = txtDetalleCodBarra;
    }

    public String getTxtDetalleMedida() {
        return txtDetalleMedida;
    }

    public void setTxtDetalleMedida(String txtDetalleMedida) {
        this.txtDetalleMedida = txtDetalleMedida;
    }

    public String getTxtDetallePeso() {
        return txtDetallePeso;
    }

    public void setTxtDetallePeso(String txtDetallePeso) {
        this.txtDetallePeso = txtDetallePeso;
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

    public String getTxtMotor() {
        return txtMotor;
    }

    public void setTxtMotor(String txtMotor) {
        this.txtMotor = txtMotor;
    }

    public String getTxtserie() {
        return txtserie;
    }

    public String getTxtKwPotencia() {
        return txtKwPotencia;
    }

    public void setTxtKwPotencia(String txtKwPotencia) {
        this.txtKwPotencia = txtKwPotencia;
    }

    public String getTxtAnno() {
        return txtAnno;
    }

    public void setTxtAnno(String txtAnno) {
        this.txtAnno = txtAnno;
    }

    public String getTxtCodigoProd() {return txtCodigoProd;
    }

    public void setTxtCodigoProd(String txtCodigoProd) {this.txtCodigoProd = txtCodigoProd; }

    public String getTxtTipoProd() {
        return txtTipoProd;
    }

    public void setTxtTipoProd(String txtTipoProd) {
        this.txtTipoProd = txtTipoProd;
    }

    public Bitmap getImgProd() {
        return imgProd;
    }

    public void setImgProd(Bitmap imgProd) {
        this.imgProd = imgProd;
    }

    public String getRutaImg() {
        return rutaImg;
    }

    public void setRutaImg(String rutaImg) {
        this.rutaImg = rutaImg;
    }

    public String getRutaImg_2() {
        return rutaImg_2;
    }

    public void setRutaImg_2(String rutaImg_2) {
        this.rutaImg_2 = rutaImg_2;
    }

    public String getTxtDetalleVeh() { return txtDetalleVeh;  }

    public void setTxtDetalleVeh(String txtDetalleVeh) { this.txtDetalleVeh = txtDetalleVeh;
    }

    public String getTxtDetalleOMarca() {
        return txtDetalleOMarca;
    }

    public void setTxtDetalleOMarca(String txtDetalleOMarca) {
        this.txtDetalleOMarca = txtDetalleOMarca;
    }

    public String getTxtDetalleOCod() {
        return txtDetalleOCod;
    }

    public void setTxtDetalleOCod(String txtDetalleOCod) {
        this.txtDetalleOCod = txtDetalleOCod;
    }

    public String getTxtDetalleAnno() {
        return txtDetalleAnno;
    }

    public void setTxtDetalleAnno(String txtDetalleAnno) {
        this.txtDetalleAnno = txtDetalleAnno;
    }

    public String getTxtDetalleMotor() {
        return txtDetalleMotor;
    }

    public void setTxtDetalleMotor(String txtDetalleMotor) {
        this.txtDetalleMotor = txtDetalleMotor;
    }

    public String getGetTxtDetalleHP() {
        return getTxtDetalleHP;
    }

    public void setGetTxtDetalleHP(String getTxtDetalleHP) {
        this.getTxtDetalleHP = getTxtDetalleHP;
    }


}

