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
public class CO {
    String id, codice, data_inizio, data_fine, mansione, tipo_movimento, data_avvio_CO, lavoratore, idlav, cf_datorelavoro, contratto, cf_utilizzatore;

    public CO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
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

    public String getMansione() {
        return mansione;
    }

    public void setMansione(String mansione) {
        this.mansione = mansione;
    }

    public String getTipo_movimento() {
        return tipo_movimento;
    }

    public void setTipo_movimento(String tipo_movimento) {
        this.tipo_movimento = tipo_movimento;
    }

    public String getData_avvio_CO() {
        return data_avvio_CO;
    }

    public void setData_avvio_CO(String data_avvio_CO) {
        this.data_avvio_CO = data_avvio_CO;
    }

    public String getLavoratore() {
        return lavoratore;
    }

    public void setLavoratore(String lavoratore) {
        this.lavoratore = lavoratore;
    }

    public String getIdlav() {
        return idlav;
    }

    public void setIdlav(String idlav) {
        this.idlav = idlav;
    }

    public String getCf_datorelavoro() {
        return cf_datorelavoro;
    }

    public void setCf_datorelavoro(String cf_datorelavoro) {
        this.cf_datorelavoro = cf_datorelavoro;
    }

    public String getContratto() {
        return contratto;
    }

    public void setContratto(String contratto) {
        this.contratto = contratto;
    }

    public String getCf_utilizzatore() {
        return cf_utilizzatore;
    }

    public void setCf_utilizzatore(String cf_utilizzatore) {
        this.cf_utilizzatore = cf_utilizzatore;
    }
    
}
