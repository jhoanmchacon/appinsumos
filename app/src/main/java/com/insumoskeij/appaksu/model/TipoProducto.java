package com.insumoskeij.appaksu.model;

public class TipoProducto {

    private String idTipoProducto;
    private String tipoProducto;

    public TipoProducto(String idTipoProducto, String tipoProducto) {
        this.idTipoProducto = idTipoProducto;
        this.tipoProducto = tipoProducto;
    }

    public TipoProducto() {
    }

    @Override
    public String toString() {
        return "TipoProducto{" +
                "tipoProducto='" + tipoProducto + '\'' +
                '}';
    }

    public String getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(String idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getTipoProduto() {
        return tipoProducto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProducto = tipoProducto;
    }
}
