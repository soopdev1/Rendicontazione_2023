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
public class Registro {

    int id, ore, ore_rev, progetto_formativo;
    double tot_erogato;
    String from, to, mese, doc_quietanza, doc_tutor, doc_lavoratore, motivo, stato, dataup, timestamp, file, datainizio, datafine;
    String ente, lavoratore, cf_lavoratore, tutor, checklist,xml_liquidazione,id_lavoratore;

    public Registro() {
    }

    public String getXml_liquidazione() {
        return xml_liquidazione;
    }

    public void setXml_liquidazione(String xml_liquidazione) {
        this.xml_liquidazione = xml_liquidazione;
    }

    public String getId_lavoratore() {
        return id_lavoratore;
    }

    public void setId_lavoratore(String id_lavoratore) {
        this.id_lavoratore = id_lavoratore;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public int getProgetto_formativo() {
        return progetto_formativo;
    }

    public void setProgetto_formativo(int progetto_formativo) {
        this.progetto_formativo = progetto_formativo;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getLavoratore() {
        return lavoratore;
    }

    public void setLavoratore(String lavoratore) {
        this.lavoratore = lavoratore;
    }

    public String getCf_lavoratore() {
        return cf_lavoratore;
    }

    public void setCf_lavoratore(String cf_lavoratore) {
        this.cf_lavoratore = cf_lavoratore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOre() {
        return ore;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public int getOre_rev() {
        return ore_rev;
    }

    public void setOre_rev(int ore_rev) {
        this.ore_rev = ore_rev;
    }

    public String getMese() {
        return mese;
    }

    public void setMese(String mese) {
        this.mese = mese;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDoc_quietanza() {
        return doc_quietanza;
    }

    public void setDoc_quietanza(String doc_quietanza) {
        this.doc_quietanza = doc_quietanza;
    }

    public String getDoc_tutor() {
        return doc_tutor;
    }

    public void setDoc_tutor(String doc_tutor) {
        this.doc_tutor = doc_tutor;
    }

    public String getDoc_lavoratore() {
        return doc_lavoratore;
    }

    public void setDoc_lavoratore(String doc_lavoratore) {
        this.doc_lavoratore = doc_lavoratore;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getDataup() {
        return dataup;
    }

    public void setDataup(String dataup) {
        this.dataup = dataup;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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

    public double getTot_erogato() {
        return tot_erogato;
    }

    public void setTot_erogato(double tot_erogato) {
        this.tot_erogato = tot_erogato;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

}
