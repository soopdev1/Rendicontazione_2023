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
public class Lavoratore {

    int cdnlavoratore;
    String codice_fiscale;
    String validita_cf;
    String codice_fiscale_originale;
    int codice_provincia;
    String cognome;
    String nome;
    String sesso;
    Date nascita_data;
    String nascita_codice_catastale;
    int cittadinanza;
    String recapito_telefonico;
    String email;
    String residenza_codice_catastale;
    String residenza_indirizzo;
    int residenza_cap;
    String domicilio_codice_catastale;
    String domicilio_indirizzo;
    int domicilio_cap;
    Date dt_mod_anagrafica;

    public Lavoratore() {
    }

    public int getCdnlavoratore() {
        return cdnlavoratore;
    }

    public void setCdnlavoratore(int cdnlavoratore) {
        this.cdnlavoratore = cdnlavoratore;
    }

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    public void setCodice_fiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    public String getValidita_cf() {
        return validita_cf;
    }

    public void setValidita_cf(String validita_cf) {
        this.validita_cf = validita_cf;
    }

    public String getCodice_fiscale_originale() {
        return codice_fiscale_originale;
    }

    public void setCodice_fiscale_originale(String codice_fiscale_originale) {
        this.codice_fiscale_originale = codice_fiscale_originale;
    }

    public int getCodice_provincia() {
        return codice_provincia;
    }

    public void setCodice_provincia(int codice_provincia) {
        this.codice_provincia = codice_provincia;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public Date getNascita_data() {
        return nascita_data;
    }

    public void setNascita_data(Date nascita_data) {
        this.nascita_data = nascita_data;
    }

    public String getNascita_codice_catastale() {
        return nascita_codice_catastale;
    }

    public void setNascita_codice_catastale(String nascita_codice_catastale) {
        this.nascita_codice_catastale = nascita_codice_catastale;
    }

    public int getCittadinanza() {
        return cittadinanza;
    }

    public void setCittadinanza(int cittadinanza) {
        this.cittadinanza = cittadinanza;
    }

    public String getRecapito_telefonico() {
        return recapito_telefonico;
    }

    public void setRecapito_telefonico(String recapito_telefonico) {
        this.recapito_telefonico = recapito_telefonico;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResidenza_codice_catastale() {
        return residenza_codice_catastale;
    }

    public void setResidenza_codice_catastale(String residenza_codice_catastale) {
        this.residenza_codice_catastale = residenza_codice_catastale;
    }

    public String getResidenza_indirizzo() {
        return residenza_indirizzo;
    }

    public void setResidenza_indirizzo(String residenza_indirizzo) {
        this.residenza_indirizzo = residenza_indirizzo;
    }

    public int getResidenza_cap() {
        return residenza_cap;
    }

    public void setResidenza_cap(int residenza_cap) {
        this.residenza_cap = residenza_cap;
    }

    public String getDomicilio_codice_catastale() {
        return domicilio_codice_catastale;
    }

    public void setDomicilio_codice_catastale(String domicilio_codice_catastale) {
        this.domicilio_codice_catastale = domicilio_codice_catastale;
    }

    public String getDomicilio_indirizzo() {
        return domicilio_indirizzo;
    }

    public void setDomicilio_indirizzo(String domicilio_indirizzo) {
        this.domicilio_indirizzo = domicilio_indirizzo;
    }

    public int getDomicilio_cap() {
        return domicilio_cap;
    }

    public void setDomicilio_cap(int domicilio_cap) {
        this.domicilio_cap = domicilio_cap;
    }

    public Date getDt_mod_anagrafica() {
        return dt_mod_anagrafica;
    }

    public void setDt_mod_anagrafica(Date dt_mod_anagrafica) {
        this.dt_mod_anagrafica = dt_mod_anagrafica;
    }

}
