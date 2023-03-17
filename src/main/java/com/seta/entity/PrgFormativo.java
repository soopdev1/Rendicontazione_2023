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
public class PrgFormativo {

    String nome, cognome, codazioneformcal, dataavvio, datafine, stato, motivo, doc_ragazzo,
            doc_tutor, cf, nomeTutor, dataup, scadenza_doc, doc_competenze, de_ente, file,
            path_convenzione;
    int id, prgpercorso, prgcolloquio, durataeffettiva, tutor, rimborso, ore_tot, ore_mese,
            ore_effettuate, lavoratore, durata_mesi, profiling, bando, convenzione;
    double importo_rimborso;

    public PrgFormativo() {
    }

    public String getDoc_competenze() {
        return doc_competenze;
    }

    public void setDoc_competenze(String doc_competenze) {
        this.doc_competenze = doc_competenze;
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

    public String getDoc_tutor() {
        return doc_tutor;
    }

    public void setDoc_tutor(String doc_tutor) {
        this.doc_tutor = doc_tutor;
    }

    public String getDataup() {
        return dataup;
    }

    public void setDataup(String dataup) {
        this.dataup = dataup;
    }

    public String getScadenza_doc() {
        return scadenza_doc;
    }

    public void setScadenza_doc(String scadenza_doc) {
        this.scadenza_doc = scadenza_doc;
    }

    public int getOre_tot() {
        return ore_tot;
    }

    public void setOre_tot(int ore_tot) {
        this.ore_tot = ore_tot;
    }

    public int getOre_mese() {
        return ore_mese;
    }

    public void setOre_mese(int ore_mese) {
        this.ore_mese = ore_mese;
    }

    public int getOre_effettuate() {
        return ore_effettuate;
    }

    public void setOre_effettuate(int ore_effettuate) {
        this.ore_effettuate = ore_effettuate;
    }

    public int getLavoratore() {
        return lavoratore;
    }

    public void setLavoratore(int lavoratore) {
        this.lavoratore = lavoratore;
    }

    public int getDurata_mesi() {
        return durata_mesi;
    }

    public void setDurata_mesi(int durata_mesi) {
        this.durata_mesi = durata_mesi;
    }

    public int getProfiling() {
        return profiling;
    }

    public void setProfiling(int profiling) {
        this.profiling = profiling;
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

    public String getDe_ente() {
        return de_ente;
    }

    public void setDe_ente(String de_ente) {
        this.de_ente = de_ente;
    }

    public int getConvenzione() {
        return convenzione;
    }

    public void setConvenzione(int convenzione) {
        this.convenzione = convenzione;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getPath_convenzione() {
        return path_convenzione;
    }

    public void setPath_convenzione(String path_convenzione) {
        this.path_convenzione = path_convenzione;
    }

}
