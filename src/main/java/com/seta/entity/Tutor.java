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
public class Tutor {

    String nome, cognome, email, telefono, stato, documento, scadenzaDoc, ruolo_s, cf;
    int id, idente, ruolo;

    public Tutor() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getScadenzaDoc() {
        return scadenzaDoc;
    }

    public void setScadenzaDoc(String scadenzaDoc) {
        this.scadenzaDoc = scadenzaDoc;
    }

    public String getRuolo_s() {
        return ruolo_s;
    }

    public void setRuolo_s(String ruolo_s) {
        this.ruolo_s = ruolo_s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdente() {
        return idente;
    }

    public void setIdente(int idente) {
        this.idente = idente;
    }

    public int getRuolo() {
        return ruolo;
    }

    public void setRuolo(int ruolo) {
        this.ruolo = ruolo;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

}
