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
public class FileINPS {

    String data_up, cf, datainizio, datafine, nome, cognome, nascita_data, codice_fiscale, nascita_codice_catastale, codice_provincia, residenza_indirizzo, residenza_cap, residenza_codice_catastale, tot_erogato, cittadinanza;
    int id;

    public FileINPS() {
    }

    public String getCittadinanza() {
        return cittadinanza;
    }

    public void setCittadinanza(String cittadinanza) {
        this.cittadinanza = cittadinanza;
    }

    public String getData_up() {
        return data_up;
    }

    public void setData_up(String data_up) {
        this.data_up = data_up;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getDatainizio() {
        return datainizio;
    }

    public void setDatainizio(String datainizio) {
        this.datainizio = datainizio;
    }

    public String getDatafine() {
        return datafine;
    }

    public void setDatafine(String datafine) {
        this.datafine = datafine;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNascita_data() {
        return nascita_data;
    }

    public void setNascita_data(String nascita_data) {
        this.nascita_data = nascita_data;
    }

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    public void setCodice_fiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    public String getNascita_codice_catastale() {
        return nascita_codice_catastale;
    }

    public void setNascita_codice_catastale(String nascita_codice_catastale) {
        this.nascita_codice_catastale = nascita_codice_catastale;
    }

    public String getCodice_provincia() {
        return codice_provincia;
    }

    public void setCodice_provincia(String codice_provincia) {
        this.codice_provincia = codice_provincia;
    }

    public String getResidenza_indirizzo() {
        return residenza_indirizzo;
    }

    public void setResidenza_indirizzo(String residenza_indirizzo) {
        this.residenza_indirizzo = residenza_indirizzo;
    }

    public String getResidenza_cap() {
        return residenza_cap;
    }

    public void setResidenza_cap(String residenza_cap) {
        this.residenza_cap = residenza_cap;
    }

    public String getResidenza_codice_catastale() {
        return residenza_codice_catastale;
    }

    public void setResidenza_codice_catastale(String residenza_codice_catastale) {
        this.residenza_codice_catastale = residenza_codice_catastale;
    }

    public String getTot_erogato() {
        return tot_erogato;
    }

    public void setTot_erogato(String tot_erogato) {
        this.tot_erogato = tot_erogato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
