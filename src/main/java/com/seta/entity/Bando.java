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
public class Bando {
   String idbando,titolo,data_inizio,data_fine,flag_sportello,tipo_bando,budget,data_creazione,path,decreto,stato,budget_attuale,budget_previsione;

    public Bando() {
    }

    public String getBudget_attuale() {
        return budget_attuale;
    }

    public void setBudget_attuale(String budget_attuale) {
        this.budget_attuale = budget_attuale;
    }

    public String getBudget_previsione() {
        return budget_previsione;
    }

    public void setBudget_previsione(String budget_previsione) {
        this.budget_previsione = budget_previsione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
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

    public String getFlag_sportello() {
        return flag_sportello;
    }

    public void setFlag_sportello(String flag_sportello) {
        this.flag_sportello = flag_sportello;
    }

    public String getTipo_bando() {
        return tipo_bando;
    }

    public void setTipo_bando(String tipo_bando) {
        this.tipo_bando = tipo_bando;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getData_creazione() {
        return data_creazione;
    }

    public void setData_creazione(String data_creazione) {
        this.data_creazione = data_creazione;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIdbando() {
        return idbando;
    }

    public void setIdbando(String idbando) {
        this.idbando = idbando;
    }

    public String getDecreto() {
        return decreto;
    }

    public void setDecreto(String decreto) {
        this.decreto = decreto;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    
}
