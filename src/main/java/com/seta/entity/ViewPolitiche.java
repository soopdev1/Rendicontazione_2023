/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.entity;

import java.util.Date;

/**
 *
 * @author agodino
 */
public class ViewPolitiche {

    String codazioneformcal, stato, cf_ente, cod;
    int idpolitica, domanda_rimborso;
    Date dataavvio, datafine;

    public ViewPolitiche() {
    }

    public String getCodazioneformcal() {
        return codazioneformcal;
    }

    public void setCodazioneformcal(String codazioneformcal) {
        this.codazioneformcal = codazioneformcal;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getCf_ente() {
        return cf_ente;
    }

    public void setCf_ente(String cf_ente) {
        this.cf_ente = cf_ente;
    }

    public int getDomanda_rimborso() {
        return domanda_rimborso;
    }

    public void setDomanda_rimborso(int domanda_rimborso) {
        this.domanda_rimborso = domanda_rimborso;
    }

    public int getIdpolitica() {
        return idpolitica;
    }

    public void setIdpolitica(int idpolitica) {
        this.idpolitica = idpolitica;
    }

    public Date getDataavvio() {
        return dataavvio;
    }

    public void setDataavvio(Date dataavvio) {
        this.dataavvio = dataavvio;
    }

    public Date getDatafine() {
        return datafine;
    }

    public void setDatafine(Date datafine) {
        this.datafine = datafine;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

}
