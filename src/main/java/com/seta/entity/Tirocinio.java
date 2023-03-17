/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.entity;

/**
 *
 * @author srotella
 */
public class Tirocinio {
    String  ente, tipo, stato, data_assunzione, data_cessazione, nome, cognome, datanascita, motivo;
    int id, id_b, id_t;

    public Tirocinio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_b() {
        return id_b;
    }

    public void setId_b(int id_b) {
        this.id_b = id_b;
    }

    public int getId_t() {
        return id_t;
    }

    public void setId_t(int id_t) {
        this.id_t = id_t;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getData_assunzione() {
        return data_assunzione;
    }

    public void setData_assunzione(String data_assunzione) {
        this.data_assunzione = data_assunzione;
    }

    public String getData_cessazione() {
        return data_cessazione;
    }

    public void setData_cessazione(String data_cessazione) {
        this.data_cessazione = data_cessazione;
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

    public String getDatanascita() {
        return datanascita;
    }

    public void setDatanascita(String datanascaita) {
        this.datanascita = datanascaita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    
}
