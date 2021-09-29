package com.example.phisiotips.activity.activity.model;

public class MainEnqutes {

    String titulo;
    String resumo;
    String chave;

    public MainEnqutes(){

    }

    public MainEnqutes(String titulo, String resumo) {
        this.titulo = titulo;
        this.resumo = resumo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String  getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getChave() {
        return chave;

    }

    public String setChave(String chave) {
        this.chave = chave;
        return chave;
    }
}
