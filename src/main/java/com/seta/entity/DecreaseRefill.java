/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.entity;

/**
 *
 * @author dolivo
 */
public class DecreaseRefill {
    int idbando; 
    Double tot_erogato;
    String politica;

    public DecreaseRefill() {
    }

    public int getIdbando() {
        return idbando;
    }

    public void setIdbando(int idbando) {
        this.idbando = idbando;
    }

    public Double getTot_erogato() {
        return tot_erogato;
    }

    public void setTot_erogato(Double tot_erogato) {
        this.tot_erogato = tot_erogato;
    }

    public String getPolitica() {
        return politica;
    }

    public void setPolitica(String politica) {
        this.politica = politica;
    }
    
    
}
