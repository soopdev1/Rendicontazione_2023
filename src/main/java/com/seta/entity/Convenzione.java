/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.entity;

/**
 *
 * @author agodino
 */
public class Convenzione {

    int id, ente, bando;
    String codice, inizio, fine, politica, d_politica, file, d_bando;

    public Convenzione() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getInizio() {
        return inizio;
    }

    public void setInizio(String inizio) {
        this.inizio = inizio;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getPolitica() {
        return politica;
    }

    public void setPolitica(String politica) {
        this.politica = politica;
    }

    public int getEnte() {
        return ente;
    }

    public void setEnte(int ente) {
        this.ente = ente;
    }

    public String getD_politica() {
        return d_politica;
    }

    public void setD_politica(String d_politica) {
        this.d_politica = d_politica;
    }

    public int getBando() {
        return bando;
    }

    public void setBando(int bando) {
        this.bando = bando;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getD_bando() {
        return d_bando;
    }

    public void setD_bando(String d_bando) {
        this.d_bando = d_bando;
    }

}
