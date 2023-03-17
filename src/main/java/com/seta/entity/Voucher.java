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
public class Voucher {

    String nome, cognome, codazioneformcal, dataavvio, datafine, stato, motivo, doc_ragazzo, doc_registro, doc_allegato, doc_attestato, doc_delega, cf;
    int id, prgpercorso, prgcolloquio, rimborso, profiling, ore, bando;
    double importo_rimborso, voucher;

    public Voucher() {
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

    public String getDoc_registro() {
        return doc_registro;
    }

    public void setDoc_registro(String doc_registro) {
        this.doc_registro = doc_registro;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
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

    public int getRimborso() {
        return rimborso;
    }

    public void setRimborso(int rimborso) {
        this.rimborso = rimborso;
    }

    public int getProfiling() {
        return profiling;
    }

    public void setProfiling(int profiling) {
        this.profiling = profiling;
    }

    public String getDoc_allegato() {
        return doc_allegato;
    }

    public void setDoc_allegato(String doc_allegato) {
        this.doc_allegato = doc_allegato;
    }

    public String getDoc_attestato() {
        return doc_attestato;
    }

    public void setDoc_attestato(String doc_attestato) {
        this.doc_attestato = doc_attestato;
    }

    public String getDoc_delega() {
        return doc_delega;
    }

    public void setDoc_delega(String doc_delega) {
        this.doc_delega = doc_delega;
    }

    public int getOre() {
        return ore;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public double getVoucher() {
        return voucher;
    }

    public void setVoucher(double voucher) {
        this.voucher = voucher;
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

}
