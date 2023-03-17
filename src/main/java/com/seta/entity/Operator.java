/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.entity;

import com.seta.activity.Action;

/**
 *
 * @author dolivo
 */
public class Operator {

    int idoperatore, tipo, stato, ente;
    String username, password, nome, cognome, email, telefono, dataregistrazione;
    Ente entePromotore;

    public Operator() {
    }

    public Operator(int idoperatore, int tipo, int stato, String username, String password, String nome, String cognome, String email, String telefono, String dataregistrazione) {
        this.idoperatore = idoperatore;
        this.tipo = tipo;
        this.stato = stato;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.dataregistrazione = dataregistrazione;
    }

    public int getIdoperatore() {
        return idoperatore;
    }

    public void setIdoperatore(int idoperatore) {
        this.idoperatore = idoperatore;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDataregistrazione() {
        return dataregistrazione;
    }

    public void setDataregistrazione(String dataregistrazione) {
        this.dataregistrazione = dataregistrazione;
    }

    public int getEnte() {
        return ente;
    }

    public void setEnte(int ente) {
        this.ente = ente;
        entePromotore = Action.getEnteById(ente);
    }

    public Ente getEntePromotore() {
        return entePromotore;
    }

    public void setEntePromotore(Ente entePromotore) {
        this.entePromotore = entePromotore;
    }    
}
