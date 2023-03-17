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
public class Contratto {
    String data_inizio, data_fine, file, indeterminato;

    public Contratto() {
    }

    public String getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(String data_inizio) {
        this.data_inizio = data_inizio;
    }

    public String getData_fine() {
        return data_fine;
    }

    public void setData_fine(String data_fine) {
        this.data_fine = data_fine;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getIndeterminato() {
        return indeterminato;
    }

    public void setIndeterminato(String indeterminato) {
        this.indeterminato = indeterminato;
    }
    
}
