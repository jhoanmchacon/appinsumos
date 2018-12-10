package com.insumoskeij.appaksu.model;

public class Anno {

    String id_anno;
    String anno;

    public Anno(String id_anno, String anno) {
        this.id_anno = id_anno;
        this.anno = anno;
    }

    public Anno() {
    }

    public String getId_anno() {
        return id_anno;
    }

    public void setId_anno(String id_anno) {
        this.id_anno = id_anno;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }
}
