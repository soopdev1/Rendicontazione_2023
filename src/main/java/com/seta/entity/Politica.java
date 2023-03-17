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
public class Politica {

    String nome, cognome, codazioneformcal, dataavvio, datafine, stato, motivo, doc_ragazzo, doc_m5, doc_tutor, cf, nomeTutor, new_datafine, idlav, idente, sil;
    int id, prgpercorso, prgcolloquio, durataeffettiva, tutor, rimborso, profiling, bando;
    double importo_rimborso;
    Contratto contratto;

    public Politica() {
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

    public String getCodazioneformcal() {
        return codazioneformcal;
    }

    public void setCodazioneformcal(String codazioneformcal) {
        this.codazioneformcal = codazioneformcal;
    }

    public String getDataavvio() {
        return dataavvio;
    }

    public void setDataavvio(String dataavvio) {
        this.dataavvio = dataavvio;
    }

    public String getDatafine() {
        return datafine;
    }

    public void setDatafine(String datafine) {
        this.datafine = datafine;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDoc_ragazzo() {
        return doc_ragazzo;
    }

    public void setDoc_ragazzo(String doc_ragazzo) {
        this.doc_ragazzo = doc_ragazzo;
    }

    public String getDoc_m5() {
        return doc_m5;
    }

    public void setDoc_m5(String doc_m5) {
        this.doc_m5 = doc_m5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrgpercorso() {
        return prgpercorso;
    }

    public void setPrgpercorso(int prgpercorso) {
        this.prgpercorso = prgpercorso;
    }

    public int getPrgcolloquio() {
        return prgcolloquio;
    }

    public void setPrgcolloquio(int prgcolloquio) {
        this.prgcolloquio = prgcolloquio;
    }

    public int getDurataeffettiva() {
        return durataeffettiva;
    }

    public void setDurataeffettiva(int durataeffettiva) {
        this.durataeffettiva = durataeffettiva;
    }

    public int getTutor() {
        return tutor;
    }

    public void setTutor(int tutor) {
        this.tutor = tutor;
    }

    public int getRimborso() {
        return rimborso;
    }

    public void setRimborso(int rimborso) {
        this.rimborso = rimborso;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getNomeTutor() {
        return nomeTutor;
    }

    public void setNomeTutor(String nomeTutor) {
        this.nomeTutor = nomeTutor;
    }

    public Contratto getContratto() {
        return contratto;
    }

    public void setContratto(Contratto contratto) {
        this.contratto = contratto;
    }

    public String getDoc_tutor() {
        return doc_tutor;
    }

    public void setDoc_tutor(String doc_tutor) {
        this.doc_tutor = doc_tutor;
    }

    public int getProfiling() {
        return profiling;
    }

    public void setProfiling(int profiling) {
        this.profiling = profiling;
    }

    public String getNew_datafine() {
        return new_datafine;
    }

    public void setNew_datafine(String new_datafine) {
        this.new_datafine = new_datafine;
    }

    public String getIdlav() {
        return idlav;
    }

    public void setIdlav(String idlav) {
        this.idlav = idlav;
    }

    public String getIdente() {
        return idente;
    }

    public void setIdente(String idente) {
        this.idente = idente;
    }

    public int getBando() {
        return bando;
    }

    public void setBando(int bando) {
        this.bando = bando;
    }

    public double getImporto_rimborso() {
        return importo_rimborso;
    }

    public void setImporto_rimborso(double importo_rimborso) {
        this.importo_rimborso = importo_rimborso;
    }

    public String getSil() {
        return sil;
    }

    public void setSil(String sil) {
        this.sil = sil;
    }

}
