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
public class Decreto {
    String fk_idrimborso, num_repertorio, data_repertorio, decreto, data_decreto, num_decreto, timestamp;

    public Decreto() {
    }

    public String getFk_idrimborso() {
        return fk_idrimborso;
    }

    public void setFk_idrimborso(String fk_idrimborso) {
        this.fk_idrimborso = fk_idrimborso;
    }

    public String getNum_repertorio() {
        return num_repertorio;
    }

    public void setNum_repertorio(String num_repertorio) {
        this.num_repertorio = num_repertorio;
    }

    public String getData_repertorio() {
        return data_repertorio;
    }

    public void setData_repertorio(String data_repertorio) {
        this.data_repertorio = data_repertorio;
    }

    public String getData_decreto() {
        return data_decreto;
    }

    public void setData_decreto(String data_decreto) {
        this.data_decreto = data_decreto;
    }

    public String getNum_decreto() {
        return num_decreto;
    }

    public void setNum_decreto(String num_decreto) {
        this.num_decreto = num_decreto;
    }

    public String getDecreto() {
        return decreto;
    }

    public void setDecreto(String decreto) {
        this.decreto = decreto;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

   
    
}
