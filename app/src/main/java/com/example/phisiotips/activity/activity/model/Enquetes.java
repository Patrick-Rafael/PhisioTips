package com.example.phisiotips.activity.activity.model;

import java.util.ArrayList;
import java.util.List;

public class Enquetes {

    String titulo;
    String resumo;
    String autor;


    public Enquetes(String titulo, String resumo, String autor) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.autor = autor;


    }

    public Enquetes() {
    }


    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }
}
