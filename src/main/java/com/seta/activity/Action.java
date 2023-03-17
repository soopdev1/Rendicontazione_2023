/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.activity;

import com.seta.db.Db;
import com.seta.entity.B3;
import com.seta.entity.Bando;
import com.seta.entity.CO;
import com.seta.entity.Contratto;
import com.seta.entity.Convenzione;
import com.seta.entity.DecreaseRefill;
import com.seta.entity.Ente;
import com.seta.entity.FileINPS;
import com.seta.entity.Lavoratore;
import com.seta.entity.Operator;
import com.seta.entity.Politica;
import com.seta.entity.PrgFormativo;
import com.seta.entity.PrgFormativoDt;
import com.seta.entity.Registro;
import com.seta.entity.RegistroDt;
import com.seta.entity.Rimborso;
import com.seta.entity.Rimborso_PrgFormativo;
import com.seta.entity.Rimborso_PrgFormativo_Dt;
import com.seta.entity.Tutor;
import com.seta.entity.ViewPolitiche;
import com.seta.entity.Voucher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author dolivo
 */
public class Action {
    
    public static Operator getOperatore(String user, String pwd) {
        Operator ug;
        Db db = new Db();
        ug = db.getOperatore(user, pwd);
        db.closeDB();
        return ug;
    }
    
    public static String changePasswordProfile(String idoperatore, String oldPassword, String newPassword) {
        Db db = new Db();
        if (db.verifyOldPassword(oldPassword, idoperatore)) {
            if (db.changePassword(idoperatore, newPassword)) {
                db.closeDB();
                return "OK";
            } else {
                db.closeDB();
                return "updateFail";
            }
        } else {
            db.closeDB();
            return "passErr";
        }
    }
    
    public static Operator Operatore(int idoperatore) {
        Db db = new Db();
        Operator operator = db.Operatore(idoperatore);
        db.closeDB();
        return operator;
    }
    
    public static boolean updateOperatore(int id, String name, String surname, String email, String phone) {
        Db db = new Db();
        boolean out = db.updateOperatore(id, name, surname, email, phone);
        db.closeDB();
        return out;
    }
    
    public static boolean isVisibile(String gruppo, String page) {
        Db db = new Db();
        String elenco = db.getUserPermission(page);
        db.closeDB();
        StringTokenizer st = new StringTokenizer(elenco, "-");
        while (st.hasMoreTokens()) {
            if (gruppo.equals(st.nextToken())) {
                return true;
            }
        }
        return false;
    }
    
    public static String getPath(String id) {
        Db db = new Db();
        String path = db.getPath(id);
        db.closeDB();
        return path;
    }
    
    public static Operator getDataReset(String mail, String user) {
        Db db = new Db();
        Operator reset = db.getDataReset(mail, user);
        db.closeDB();
        return reset;
    }
    
    public static boolean resetPassword(String newpsw, String user) {
        Db db = new Db();
        boolean rst = db.resetPassword(user, newpsw);
        db.closeDB();
        return rst;
    }
    
    public static String getPasswordFromUser(String user) {
        Db db = new Db();
        String psw = db.getPasswordFromUser(user);
        db.closeDB();
        return psw;
    }
    
    public static boolean changePasswordFA(String user, String actualpsw, String newpsw) {
        Db db = new Db();
        boolean rst = db.changePasswordFA(user, actualpsw, newpsw);
        db.closeDB();
        return rst;
    }
    
    public static boolean setStatusFA(String user) {
        Db db = new Db();
        boolean rst = db.setStatusFA(user);
        db.closeDB();
        return rst;
    }
    
    public static void insertTracking(String id_user, String action) {
        Db db = new Db();
        db.insertTracking(id_user, action);
        db.closeDB();
    }
    
    public static void insertModTracking(int id_user, String action, String cod_risorsa, String id_risorsa) {
        Db db = new Db();
        db.insertModTracking(id_user, action, cod_risorsa, id_risorsa);
        db.closeDB();
    }
    
    public static boolean ctrlEmailOp(String email) {
        Db db = new Db();
        boolean e = db.ctrlEmailOp(email);
        db.closeDB();
        return e;
    }
    
    public static boolean ctrlUsername(String user) {
        Db db = new Db();
        boolean u = db.ctrlUsername(user);
        db.closeDB();
        return u;
    }
    
    public static Ente getEnteById(int ente) {
        Db db = new Db();
        Ente out = db.getEnteById(ente);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<String[]> getList_TipoBando() {
        ArrayList<String[]> out = new ArrayList<>();
        Db db = new Db();
        out = db.getList_TipoBando();
        db.closeDB();
        return out;
    }

    // SEZIONE TUTOR
    public static boolean insertTutor(String ruolo, String nome, String cognome, String email, String telefono, String documento, String scadenza_documento, int ente, String cf) {
        Db db = new Db();
        boolean insertTutor = db.insertTutor(ruolo, nome, cognome, email, telefono, documento, scadenza_documento, ente, cf);
        db.closeDB();
        return insertTutor;
    }
    // FINE SEZIONE TUTOR

    public static ArrayList<String[]> getListTipoTutor() {
        ArrayList<String[]> out = new ArrayList<>();
        Db db = new Db();
        out = db.getListTipoTutor();
        db.closeDB();
        return out;
    }
    
    public static int Insert_Bando(String titolo, String data_inizio, String data_fine, String flag_sportello, String tipo_bando, String budget, String path, String anno) {
        Db db = new Db();
        int Insert_Bando = db.Insert_Bando(titolo, data_inizio, data_fine, flag_sportello, tipo_bando, budget, path, anno);
        db.closeDB();
        return Insert_Bando;
    }
    
    public static ArrayList<Tutor> getTutorEnte(int idente) {
        Db db = new Db();
        ArrayList<Tutor> out = db.getTutorEnte(idente);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Bando> searchBandi(String titolo, String tipo, String from, String to) {
        Db db = new Db();
        ArrayList<Bando> out = db.searchBandi(titolo, tipo, from, to);
        db.closeDB();
        return out;
    }
    
    public static Map<String, String> getTipoBandoById() {
        Db db = new Db();
        Map<String, String> out = db.getTipoBandoById();
        db.closeDB();
        return out;
    }
    
    public static Map<String, String> getPoliticaById() {
        Db db = new Db();
        Map<String, String> out = db.getPoliticaById();
        db.closeDB();
        return out;
    }
    
    public static Map<String, String> getPoliticaByIdDT() {
        Db db = new Db();
        Map<String, String> out = db.getPoliticaByIdDT();
        db.closeDB();
        return out;
    }
    
    public static Map<String, String> getPoliticaByIdGG() {
        Db db = new Db();
        Map<String, String> out = db.getPoliticaByIdGG();
        db.closeDB();
        return out;
    }
    
    public static boolean deleteTutor(String id) {
        Db db = new Db();
        boolean out = db.deleteTutor(id);
        db.closeDB();
        return out;
    }
    
    public static Bando getBandoById(String id) {
        Db db = new Db();
        Bando out = db.getBandoById(id);
        db.closeDB();
        return out;
    }
    
    public static Tutor findTutorById(String id) {
        Db db = new Db();
        Tutor out = db.findTutorById(id);
        db.closeDB();
        return out;
    }
    
    public static boolean updateDocId(String id, String doc, String scadenza) {
        Db db = new Db();
        boolean out = db.updateDocId(id, doc, scadenza);
        db.closeDB();
        return out;
    }
    
    public static boolean Modify_Bando(String id, String titolo, String data_inizio, String data_fine, String flag_sportello, String tipo_bando, String budget, String path, String anno, String budgeta, String budgetp) {
        Db db = new Db();
        boolean Modify_Bando = db.Modify_Bando(id, titolo, data_inizio, data_fine, flag_sportello, tipo_bando, budget, path, anno, budgeta, budgetp);
        db.closeDB();
        return Modify_Bando;
    }

//    public static ArrayList<String[]> getListTipoPolitica(String tipo, String flag) {
//        ArrayList<String[]> out = new ArrayList<>();
//        Db db = new Db();
//        out = db.getListTipoPolitica(tipo, flag);
//        db.closeDB();
//        return out;
//    }
    public static ArrayList<String[]> getListTipoPolitica(String tipo) {
        ArrayList<String[]> out = new ArrayList<>();
        Db db = new Db();
        out = db.getListTipoPolitica(tipo);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<String[]> getListTipoPolitica(String tipo, String tirocinio) {
        ArrayList<String[]> out = new ArrayList<>();
        Db db = new Db();
        out = db.getListTipoPolitica(tipo, tirocinio);
        db.closeDB();
        return out;
    }
    
    public static int insertConvenzione(String codice, String from, String to, String politica, int ente, String bando, String file) {
        Db db = new Db();
        int out = db.insertConvenzione(codice, from, to, politica, ente, bando, file);
        db.closeDB();
        return out;
    }
    
    public static boolean insertBudgetPolitiche(String id, ArrayList<String[]> v) {
        Db db = new Db();
        boolean Modify_Bando = db.insertBudgetPolitiche(id, v);
        db.closeDB();
        return Modify_Bando;
    }

//    public static ArrayList<String[]> getBudgetsPolitiche(String id, String flag, String tipo) {
//        ArrayList<String[]> out = new ArrayList<>();
//        Db db = new Db();
//        out = db.getBudgetsPolitiche(id, flag, tipo);
//        db.closeDB();
//        return out;
//    }
    public static ArrayList<String[]> getBudgetsPolitiche(String id, String tipo) {
        ArrayList<String[]> out = new ArrayList<>();
        Db db = new Db();
        out = db.getBudgetsPolitiche(id, tipo);
        db.closeDB();
        return out;
    }
    
    public static boolean ctrl_BudgetsPolitiche(String id) {
        Db db = new Db();
        boolean out = db.ctrl_BudgetsPolitiche(id);
        db.closeDB();
        return out;
    }
    
    public static boolean modifyBudgetPolitiche(String id, ArrayList<String[]> v) {
        Db db = new Db();
        boolean out = db.modifyBudgetPolitiche(id, v);
        db.closeDB();
        return out;
    }
    
    public static String getBudgetStanziato(String id) {
        Db db = new Db();
        String tot = db.getBudgetStanziato(id);
        db.closeDB();
        return tot;
    }
    
    public static ArrayList<Convenzione> getConvenzioni(int ente, String tipo_bando) {
        Db db = new Db();
        ArrayList<Convenzione> out = db.getConvenzioni(ente, tipo_bando);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Convenzione> getConvenzioni(int ente) {
        Db db = new Db();
        ArrayList<Convenzione> out = db.getConvenzioni(ente);
        db.closeDB();
        return out;
    }
    
    public static Convenzione findConvenzioneById(String id) {
        Db db = new Db();
        Convenzione out = db.findConvenzioneById(id);
        db.closeDB();
        return out;
    }
    
    public static boolean updateConvenzione(String id, String codice, String from, String to, String politica, String bando, String path) {
        Db db = new Db();
        boolean out = db.updateConvenzione(id, codice, from, to, politica, bando, path);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Tutor> getListTutorEnte(int idente) {
        Db db = new Db();
        ArrayList<Tutor> out = db.getListTutorEnte(idente);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Tutor> getListTutorEnte(int idente, String ruolo) {
        Db db = new Db();
        ArrayList<Tutor> out = db.getListTutorEnte(idente, ruolo);
        db.closeDB();
        return out;
    }

//    public static ArrayList<Lavoratore> getEnteLavoratoriAttivi(String cfente) {
//        Db db = new Db();
//        ArrayList<Lavoratore> out = db.getEnteLavoratoriAttivi(cfente);
//        db.closeDB();
//        return out;
//    }
    public static boolean ChiudiBando(String id) {
        Db db = new Db();
        boolean out = db.ChiudiBando(id);
        db.closeDB();
        return out;
    }
    
    public static int insertPrgFormativo(String titolo, String ore_tot, String ore_mensili, String data_inizio, String data_fine, String tutor, String lavoratore, String carta_identità, String scadenza, String ente, String convenzione) {
        Db db = new Db();
        int out = db.insertPrgFormativo(titolo, ore_tot, ore_mensili, data_inizio, data_fine, tutor, lavoratore, carta_identità, scadenza, ente, convenzione);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<String[]> getMotivi() {
        Db db = new Db();
        ArrayList<String[]> out = db.getMotivi();
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getPolitiche_I(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<Politica> out = db.getPolitiche_I(cfente, politica, stato, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListRimborsi(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListRimborsi(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso_PrgFormativo> getListRimborsi_PrgFormativo(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso_PrgFormativo> out = db.getListRimborsi_PrgFormativo(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static Map<String, String[]> get_ADInfos() {
        Db db = new Db();
        Map<String, String[]> out = db.get_ADInfos();
        db.closeDB();
        return out;
    }
    
    public static Map<String, String[]> get_ADInfosProgettoFormativo() {
        Db db = new Db();
        Map<String, String[]> out = db.get_ADInfosProgettoFormativo();
        db.closeDB();
        return out;
    }
    
    public static Map<String, String[]> get_ADInfosProgettoFormativo_DT() {
        Db db = new Db();
        Map<String, String[]> out = db.get_ADInfosProgettoFormativo_DT();
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getListPolitiche(String dom_rimb) {
        Db db = new Db();
        ArrayList<Politica> out = db.getListPolitiche(dom_rimb);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDoc(String id, String tutor, String m5, String file, String doc_tutor) {
        Db db = new Db();
        boolean out = db.uploadDoc(id, tutor, m5, file, doc_tutor);
        db.closeDB();
        return out;
    }
    
    public static String[] getListDocsTirocinante(String id) {
        Db db = new Db();
        String[] out = db.getListDocsTirocinante(id);
        db.closeDB();
        return out;
    }
    
    public static String[] getListDocsTirocinante_DOTE(String id) {
        Db db = new Db();
        String[] out = db.getListDocsTirocinante_DOTE(id);
        db.closeDB();
        return out;
    }
    
    public static String[] getListDocsTirocinanteM3(String id) {
        Db db = new Db();
        String[] out = db.getListDocsTirocinanteM3(id);
        db.closeDB();
        return out;
    }
    
    public static String[] getListDocsTirocinanteM5(String id) {
        Db db = new Db();
        String[] out = db.getListDocsTirocinanteM5(id);
        db.closeDB();
        return out;
    }
    
    public static String[] getListDocsTirocinanteDT(String id) {
        Db db = new Db();
        String[] out = db.getListDocsTirocinanteDT(id);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborso(String id, String motivo) {
        Db db = new Db();
        boolean out = db.anomaliaRimborso(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborsoM5(String id, String motivo) {
        Db db = new Db();
        boolean out = db.anomaliaRimborsoM5(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborsoDT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.anomaliaRimborsoDT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartaRimborso(String id) {
        Db db = new Db();
        boolean out = db.scartaRimborso(id);
        db.closeDB();
        return out;
    }
    
    public static boolean scartaRimborso1(String id, String motivo) {
        Db db = new Db();
        boolean out = db.scartaRimborso1(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartaRimborsoM5(String id, String motivo) {
        Db db = new Db();
        boolean out = db.scartaRimborsoM5(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartaRimborsoDT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.scartaRimborsoDT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean accettaRimborsi(String id) {
        Db db = new Db();
        boolean out = db.accettaRimborsi(id);
        db.closeDB();
        return out;
    }
    
    public static boolean accettaRimborsiDT(String id) {
        Db db = new Db();
        boolean out = db.accettaRimborsiDT(id);
        db.closeDB();
        return out;
    }
    
    public static boolean accettaRimborsiM5(String id) {
        Db db = new Db();
        boolean out = db.accettaRimborsiM5(id);
        db.closeDB();
        return out;
    }
    
    public static boolean rigettaRimborsi(String id, String motivo) {
        Db db = new Db();
        boolean out = db.rigettaRimborsi(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean rigettaRimborsiM5(String id, String motivo) {
        Db db = new Db();
        boolean out = db.rigettaRimborsiM5(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean rigettaRimborsiDT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.rigettaRimborsiDT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static int insertRimborso(String tutor, String politica, String doc_ad_au) {
        Db db = new Db();
        int out = db.insertRimborso(tutor, politica, doc_ad_au);
        db.closeDB();
        return out;
    }
    
    public static boolean associaPoliticheRimborso(String data, int id) {
        Db db = new Db();
        boolean out = db.associaPoliticheRimborso(data, id);
        db.closeDB();
        return out;
    }
    
    public static Politica getPoliticaByID(String id) {
        Db db = new Db();
        Politica out = db.getPoliticaByID(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListRimborsiEnte(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListRimborsiEnte(politica, stato, idente, protocollo, from_up, to_up, from_mod, to_mod);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadProtocollo(String id, String protocollo, String path) {
        Db db = new Db();
        boolean out = db.uploadProtocollo(id, protocollo, path);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadProtocolloM5(String id, String protocollo, String path) {
        Db db = new Db();
        boolean out = db.uploadProtocolloM5(id, protocollo, path);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadProtocolloDT(String id, String protocollo, String path, String pol) {
        Db db = new Db();
        boolean out = db.uploadProtocolloDT(id, protocollo, path, pol);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getRimborsiPolitiche(String politica, String idrimborso) {
        Db db = new Db();
        ArrayList<Politica> out = db.getRimborsiPolitiche(politica, idrimborso);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_liquidaRimborso(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        Db db = new Db();
        boolean out = db.Rev_liquidaRimborso(idrimborso, totale, descrizione, path, ctrlrimborso);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_liquidaRimborsoM5(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        Db db = new Db();
        boolean out = db.Rev_liquidaRimborsoM5(idrimborso, totale, descrizione, path, ctrlrimborso);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_liquidaRimborsoDT(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        Db db = new Db();
        boolean out = db.Rev_liquidaRimborsoDT(idrimborso, totale, descrizione, path, ctrlrimborso);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_rigettaRimborso(String id, String motivo) {
        Db db = new Db();
        boolean out = db.Rev_rigettaRimborso(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_rigettaRimborsoM5(String id, String motivo) {
        Db db = new Db();
        boolean out = db.Rev_rigettaRimborsoM5(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_rigettaRimborsoDT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.Rev_rigettaRimborsoDT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_scartaTuttoRimborso(String id, String motivo, String path) {
        Db db = new Db();
        boolean out = db.Rev_scartaTuttoRimborso(id, motivo, path);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_scartaTuttoRimborsoM5(String id, String motivo, String path) {
        Db db = new Db();
        boolean out = db.Rev_scartaTuttoRimborsoM5(id, motivo, path);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_scartaTuttoRimborsoDT(String id, String motivo, String path) {
        Db db = new Db();
        boolean out = db.Rev_scartaTuttoRimborsoDT(id, motivo, path);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDecreto(String idrimborso, String repertorio, String datar, String decreto, String datad, String path) {
        Db db = new Db();
        boolean out = db.uploadDecreto(idrimborso, repertorio, datar, decreto, datad, path);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDecretoM5(String idrimborso, String repertorio, String datar, String decreto, String datad, String path) {
        Db db = new Db();
        boolean out = db.uploadDecretoM5(idrimborso, repertorio, datar, decreto, datad, path);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDecretoDT(String idrimborso, String repertorio, String datar, String decreto, String datad, String path, String pol) {
        Db db = new Db();
        boolean out = db.uploadDecretoDT(idrimborso, repertorio, datar, decreto, datad, path, pol);
        db.closeDB();
        return out;
    }
    
    public static int numPoliticheRimborso(String id, String idr) {
        Db db = new Db();
        int out = db.numPoliticheRimborso(id, idr);
        db.closeDB();
        return out;
    }
    
    public static int numPFRimborso(String id, String idr) {
        Db db = new Db();
        int out = db.numPFRimborso(id, idr);
        db.closeDB();
        return out;
    }
    
    public static int numPFRimborsoDT(String id, String idr) {
        Db db = new Db();
        int out = db.numPFRimborsoDT(id, idr);
        db.closeDB();
        return out;
    }
    
    public static boolean rigettaRimborsoCondition(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.rigettaRimborsoCondition(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborsoCondition(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.AnomalieRimborsoCondition(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborsoConditionM5(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.AnomalieRimborsoConditionM5(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborsoConditionDT(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.AnomalieRimborsoConditionDT(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartoRimborsoCondition(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.scartoRimborsoCondition(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartoRimborsoConditionM5(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.scartoRimborsoConditionM5(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartoRimborsoConditionDT(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.scartoRimborsoConditionDT(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<String[]> getList_Enti() {
        ArrayList<String[]> out = new ArrayList<>();
        Db db = new Db();
        out = db.getList_Enti();
        db.closeDB();
        return out;
    }
    
    public static ArrayList<String[]> get_ComuniResidenza() {
        ArrayList<String[]> out = new ArrayList<>();
        Db db = new Db();
        out = db.get_ComuniResidenza();
        db.closeDB();
        return out;
    }
    
    public static ArrayList<String[]> get_ProvinciaResidenza() {
        ArrayList<String[]> out = new ArrayList<>();
        Db db = new Db();
        out = db.get_ProvinciaResidenza();
        db.closeDB();
        return out;
    }
    
    public static boolean AttivaBando(String id) {
        Db db = new Db();
        boolean out = db.AttivaBando(id);
        db.closeDB();
        return out;
    }
    
    public static boolean SospendiBando(String id) {
        Db db = new Db();
        boolean out = db.SospendiBando(id);
        db.closeDB();
        return out;
    }
    
    public static void AttivazioniBando(String id) {
        Db db = new Db();
        db.AttivazioniBando(id);
        db.closeDB();
    }
    
    public static void SospensioniBando(String id) {
        Db db = new Db();
        db.SospensioniBando(id);
        db.closeDB();
    }
    
    public static String getBandoAttivo(String tipo) {
        Db db = new Db();
        String titolo = db.getBandoAttivo(tipo);
        db.closeDB();
        return titolo;
    }
    
    public static Contratto getContrattoById(int id) {
        Db db = new Db();
        Contratto out = db.getContrattoById(id);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDocContratto(String id, String tutor, String m5, String file, String inizio, String fine, String contratto, String indeterminato, String tipo) {
        Db db = new Db();
        boolean out = db.uploadDocContratto(id, tutor, m5, file, inizio, fine, contratto, indeterminato, tipo);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getScartiPolitiche(String idrimborso, String politica) {
        Db db = new Db();
        ArrayList<Politica> out = db.getScartiPolitiche(idrimborso, politica);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativo> getScartiPF(String idrimborso) {
        Db db = new Db();
        ArrayList<PrgFormativo> out = db.getScartiPF(idrimborso);
        db.closeDB();
        return out;
    }
    
    public static boolean insertScartiPolitiche(String idrimb, String idpol, String motivo) {
        Db db = new Db();
        boolean out = db.insertScartiPolitiche(idrimb, idpol, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean insertScartiPF(String idrimb, String idpf, String motivo) {
        Db db = new Db();
        boolean out = db.insertScartiPF(idrimb, idpf, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean insertScartiPF_DT(String idrimb, String idpf, String motivo) {
        Db db = new Db();
        boolean out = db.insertScartiPF_DT(idrimb, idpf, motivo);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getListPolitichePDF(String ids) {
        Db db = new Db();
        ArrayList<Politica> out = db.getListPolitichePDF(ids);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativo> getPrgFormativi(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<PrgFormativo> out = db.getPrgFormativi(cfente, politica, stato, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static PrgFormativo getPrgFormativoById(String id) {
        Db db = new Db();
        PrgFormativo out = db.getPrgFormativoById(id);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDocPrg(String id, String tutor, String file, String doc_tutor, String scadenza, String ore_tot, int mesi, 
            int ore_mese, String from, String to, String stato, String competenze, String convenzione, String file_prg) {
        Db db = new Db();
        boolean out = db.uploadDocPrg(id, tutor, file, doc_tutor, scadenza, ore_tot, mesi, ore_mese, from, to, stato, competenze,convenzione, file_prg);
        db.closeDB();
        return out;
    }
    
    public static int getRegisterNumber(String id) {
        Db db = new Db();
        int out = db.getRegisterNumber(id);
        db.closeDB();
        return out;
    }
    
    public static String[] getMese(int id) {
        Db db = new Db();
        String[] out = db.getMese(id);
        db.closeDB();
        return out;
    }
    
    public static int insertRtegistro(String ore, String mese, String file, String quietanza, String doc_t, String doc_r, String idprg, String from, String to) {
        Db db = new Db();
        int out = db.insertRtegistro(ore, mese, file, quietanza, doc_t, doc_r, idprg, from, to);
        db.closeDB();
        return out;
    }
    
    public static Lavoratore getLavoratoreById(int id) {
        Db db = new Db();
        Lavoratore out = db.getLavoratoreById(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativo> getListPrgFormativo(String dom_rimb) {
        Db db = new Db();
        ArrayList<PrgFormativo> out = db.getListPrgFormativo(dom_rimb);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativoDt> getListPrgFormativo_DT(String dom_rimb) {
        Db db = new Db();
        ArrayList<PrgFormativoDt> out = db.getListPrgFormativo_DT(dom_rimb);
        db.closeDB();
        return out;
    }
    
    public static Registro getLastRegister(String idprg) {
        Db db = new Db();
        Registro out = db.getLastRegister(idprg);
        db.closeDB();
        return out;
    }
    
    public static int getTotlHourPrg(String idprg) {
        Db db = new Db();
        int out = db.getTotlHourPrg(idprg);
        db.closeDB();
        return out;
    }
    
    public static int getTotlHourPrgDt(String idprg) {
        Db db = new Db();
        int out = db.getTotlHourPrgDt(idprg);
        db.closeDB();
        return out;
    }
    
    public static boolean updatePrgCert(String idprg, String doc_cert) {
        Db db = new Db();
        boolean out = db.updatePrgCert(idprg, doc_cert);
        db.closeDB();
        return out;
    }
    
    public static void deleteRegister(int id) {
        Db db = new Db();
        db.deleteRegister(id);
        db.closeDB();
    }
    
    public static int insertRimborsoPrg(String tutor, String politica, String doc_ad_au) {
        Db db = new Db();
        int out = db.insertRimborsoPrg(tutor, politica, doc_ad_au);
        db.closeDB();
        return out;
    }
    
    public static int insertRimborsoPrgDt(String tutor, String politica, String doc_ad_au) {
        Db db = new Db();
        int out = db.insertRimborsoPrgDt(tutor, politica, doc_ad_au);
        db.closeDB();
        return out;
    }
    
    public static boolean associaPrgRimborso(String data, int id) {
        Db db = new Db();
        boolean out = db.associaPrgRimborso(data, id);
        db.closeDB();
        return out;
    }
    
    public static boolean associaPrgRimborsoDt(String data, int id) {
        Db db = new Db();
        boolean out = db.associaPrgRimborsoDt(data, id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativo> getListPrgPDF(String ids) {
        Db db = new Db();
        ArrayList<PrgFormativo> out = db.getListPrgPDF(ids);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso_PrgFormativo> getListRimborsiEntePrg(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        Db db = new Db();
        ArrayList<Rimborso_PrgFormativo> out = db.getListRimborsiEntePrg(politica, stato, idente, protocollo, from_up, to_up, from_mod, to_mod);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativo> getListPrg(String domanda_rimborso) {
        Db db = new Db();
        ArrayList<PrgFormativo> out = db.getListPrg(domanda_rimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativo> getRimborsiPrg(String politica, String idrimborso) {
        Db db = new Db();
        ArrayList<PrgFormativo> out = db.getRimborsiPrg(politica, idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativo> getScartiPrg(String idrimborso) {
        Db db = new Db();
        ArrayList<PrgFormativo> out = db.getScartiPrg(idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Registro> getListRegistri(String politica, String stato, String ente, String from, String to, String nome, String cognome, String cf) {
        Db db = new Db();
        ArrayList<Registro> out = db.getListRegistri(politica, stato, ente, from, to, nome, cognome, cf);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Registro> getRegistriEnte(int idente, String stato, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<Registro> out = db.getRegistriEnte(idente, stato, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static boolean accettaRegistro(String id) {
        Db db = new Db();
        boolean out = db.accettaRegistro(id);
        db.closeDB();
        return out;
    }
    
    public static boolean accettaRegistroDT(String id) {
        Db db = new Db();
        boolean out = db.accettaRegistroDT(id);
        db.closeDB();
        return out;
    }
    
    public static boolean rigettaRegistro(String id, String motivo) {
        Db db = new Db();
        boolean out = db.rigettaRegistro(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean rigettaRegistroDT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.rigettaRegistroDT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartaRegistro(String id, String motivo, String ore, int idpf) {
        Db db = new Db();
        boolean out = db.scartaRegistro(id, motivo, ore, idpf);
        db.closeDB();
        return out;
    }
    
    public static boolean scartaRegistroDT(String id, String motivo, String ore, int idpf) {
        Db db = new Db();
        boolean out = db.scartaRegistroDT(id, motivo, ore, idpf);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_accettaRegistro(String id, String totale, String descrizione, String path, String ore, int idpf) {
        Db db = new Db();
        boolean out = db.Rev_accettaRegistro(id, totale, descrizione, path, ore, idpf);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_accettaRegistroDT(String id, String totale, String descrizione, String path, String ore, int idpf) {
        Db db = new Db();
        boolean out = db.Rev_accettaRegistroDT(id, totale, descrizione, path, ore, idpf);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_rigettaRegistro(String id, String motivo) {
        Db db = new Db();
        boolean out = db.Rev_rigettaRegistro(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_rigettaRegistroDT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.Rev_rigettaRegistroDT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_scartaRegistro(String id, String motivo, String ore, int idpf) {
        Db db = new Db();
        boolean out = db.Rev_scartaRegistro(id, motivo, ore, idpf);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_scartaRegistroDT(String id, String motivo, String ore, int idpf) {
        Db db = new Db();
        boolean out = db.Rev_scartaRegistroDT(id, motivo, ore, idpf);
        db.closeDB();
        return out;
    }
    
    public static Registro getRegistroById(String id) {
        Db db = new Db();
        Registro out = db.getRegistroById(id);
        db.closeDB();
        return out;
    }
    
    public static RegistroDt getRegistroByIdDT(String id) {
        Db db = new Db();
        RegistroDt out = db.getRegistroByIdDT(id);
        db.closeDB();
        return out;
    }
    
    public static boolean updateRegistro(String idr, String ore, String doc_t, String doc_r, String from, String to) {
        Db db = new Db();
        boolean out = db.updateRegistro(idr, ore, doc_t, doc_r, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<FileINPS> getInfoXML(String ids) {
        Db db = new Db();
        ArrayList<FileINPS> out = db.getInfoXML(ids);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<FileINPS> getInfoXML_DT(String ids) {
        Db db = new Db();
        ArrayList<FileINPS> out = db.getInfoXML_DT(ids);
        db.closeDB();
        return out;
    }
    
    public static Map<String, String> getComuneByCode() {
        Db db = new Db();
        Map<String, String> out = db.getComuneByCode();
        db.closeDB();
        return out;
    }
    
    public static Map<String, String> getStatoByCode() {
        Db db = new Db();
        Map<String, String> out = db.getStatoByCode();
        db.closeDB();
        return out;
    }
    
    public static Map<String, String> getProvinciaByCode() {
        Db db = new Db();
        Map<String, String> out = db.getProvinciaByCode();
        db.closeDB();
        return out;
    }
    
    public static Map<String, String> getValueXmlINPS() {
        Db db = new Db();
        Map<String, String> out = db.getValueXmlINPS();
        db.closeDB();
        return out;
    }
    
    public static boolean liquidaRegistri(String data) {
        Db db = new Db();
        boolean out = db.liquidaRegistri(data);
        db.closeDB();
        return out;
    }
    
    public static boolean liquidaRegistriDT(String data) {
        Db db = new Db();
        boolean out = db.liquidaRegistriDT(data);
        db.closeDB();
        return out;
    }
    
    public static boolean savexmlRegistri(String filename, String data) {
        Db db = new Db();
        boolean out = db.savexmlRegistri(filename, data);
        db.closeDB();
        return out;
    }
    
    public static boolean savexmlRegistri_DT(String filename, String data) {
        Db db = new Db();
        boolean out = db.savexmlRegistri_DT(filename, data);
        db.closeDB();
        return out;
    }
    
    public static boolean accettaRegINPS(String id) {
        Db db = new Db();
        boolean out = db.accettaRegINPS(id);
        db.closeDB();
        return out;
    }
    
    public static boolean rifiutaRegINPS(String id) {
        Db db = new Db();
        boolean out = db.rifiutaRegINPS(id);
        db.closeDB();
        return out;
    }
    
    public static boolean accettaRegINPS_DT(String id) {
        Db db = new Db();
        boolean out = db.accettaRegINPS_DT(id);
        db.closeDB();
        return out;
    }
    
    public static boolean rifiutaRegINPS_DT(String id) {
        Db db = new Db();
        boolean out = db.rifiutaRegINPS_DT(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativoDt> getPrgFormativiDt(String cfente, String stato, String nome, String cognome, String cf, String from, String to, String politica) {
        Db db = new Db();
        ArrayList<PrgFormativoDt> out = db.getPrgFormativiDt(cfente, stato, nome, cognome, cf, from, to, politica);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso_PrgFormativo_Dt> getListRimborsi_PrgFormativoDT(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso_PrgFormativo_Dt> out = db.getListRimborsi_PrgFormativoDT(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static PrgFormativoDt getPrgFormativoByIdDt(String id) {
        Db db = new Db();
        PrgFormativoDt out = db.getPrgFormativoByIdDt(id);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDocPrgDt(String id, String tutor, String file, String doc_tutor, String scadenza, String ore_tot, int mesi, int ore_mese, String from, String to, 
            String stato, String doc_m5, String competenze, String convenzione, String file_prg) {
        Db db = new Db();
        boolean out = db.uploadDocPrgDt(id, tutor, file, doc_tutor, scadenza, ore_tot, mesi, ore_mese, from, to, stato, doc_m5, competenze,convenzione, file_prg);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<RegistroDt> getListRegistriDT(String politica, String stato, String ente, String from, String to, String nome, String cognome, String cf) {
        Db db = new Db();
        ArrayList<RegistroDt> out = db.getListRegistriDt(politica, stato, ente, from, to, nome, cognome, cf);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativoDt> getListPrgPDF_dt(String ids) {
        Db db = new Db();
        ArrayList<PrgFormativoDt> out = db.getListPrgPDF_dt(ids);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso_PrgFormativo_Dt> getListRimborsiEntePrgDt(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        Db db = new Db();
        ArrayList<Rimborso_PrgFormativo_Dt> out = db.getListRimborsiEntePrgDt(politica, stato, idente, protocollo, from_up, to_up, from_mod, to_mod);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativoDt> getRimborsiPrgDt(String idrimborso) {
        Db db = new Db();
        ArrayList<PrgFormativoDt> out = db.getRimborsiPrgDt(idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativoDt> getScartiPF_DT(String idrimborso) {
        Db db = new Db();
        ArrayList<PrgFormativoDt> out = db.getScartiPF_DT(idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativoDt> getListPrgDt(String domanda_rimborso) {
        Db db = new Db();
        ArrayList<PrgFormativoDt> out = db.getListPrgDt(domanda_rimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativoDt> getScartiPrgDt(String idrimborso) {
        Db db = new Db();
        ArrayList<PrgFormativoDt> out = db.getScartiPrgDt(idrimborso);
        db.closeDB();
        return out;
    }
    
    public static PrgFormativoDt getPrgFormativoDtById(String id) {
        Db db = new Db();
        PrgFormativoDt out = db.getPrgFormativoDtById(id);
        db.closeDB();
        return out;
    }
    
    public static int getRegisterNumberDt(String id) {
        Db db = new Db();
        int out = db.getRegisterNumberDt(id);
        db.closeDB();
        return out;
    }
    
    public static RegistroDt getLastRegisterDt(String idprg) {
        Db db = new Db();
        RegistroDt out = db.getLastRegisterDt(idprg);
        db.closeDB();
        return out;
    }
    
    public static int insertRtegistroDt(String ore, String mese, String file, String doc_t, String doc_r, String idprg, String from, String to) {
        Db db = new Db();
        int out = db.insertRtegistroDt(ore, mese, file, doc_t, doc_r, idprg, from, to);
        db.closeDB();
        return out;
    }
    
    public static boolean updatePrgCertDt(String idprg, String doc_cert) {
        Db db = new Db();
        boolean out = db.updatePrgCertDt(idprg, doc_cert);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<RegistroDt> getRegistriEnteDt(int idente, String stato, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<RegistroDt> out = db.getRegistriEnteDt(idente, stato, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static boolean updateRegistroDt(String idr, String ore, String doc_t, String doc_r, String from, String to) {
        Db db = new Db();
        boolean out = db.updateRegistroDt(idr, ore, doc_t, doc_r, from, to);
        db.closeDB();
        return out;
    }
    
    public static boolean modificaResidenzaLavoratore(String id, String indirizzo, String comune, String cap) {//String provincia,provincia,
        Db db = new Db();
        boolean out = db.modificaResidenzaLavoratore(id, indirizzo, comune, cap);
        db.closeDB();
        return out;
    }
    
    public static Map<String, String> getCapByComune() {
        Db db = new Db();
        Map<String, String> out = db.getCapByComune();
        db.closeDB();
        return out;
    }
    
    public static HashMap<String, double[]> getPrezziario(String t_bando) {
        Db db = new Db();
        HashMap<String, double[]> out = db.getPrezziario(t_bando);
        db.closeDB();
        return out;
    }
    
    public static String getProvinciaResidenza(String cod) {
        Db db = new Db();
        String path = db.getProvinciaResidenza(cod);
        db.closeDB();
        return path;
    }
    
    public static ArrayList<String[]> getTipiPrestazione() {
        ArrayList<String[]> out = new ArrayList<>();
        Db db = new Db();
        out = db.getTipiPrestazione();
        db.closeDB();
        return out;
    }
    
    public static void PrestazionePredefinita(String val) {
        Db db = new Db();
        db.PrestazionePredefinita(val);
        db.closeDB();
    }
    
    public static boolean RigettaINPS(String id, String motivo) {
        Db db = new Db();
        boolean out = db.RigettaINPS(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean RigettaINPS_DT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.RigettaINPS_DT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getPolitiche_DT(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<Politica> out = db.getPolitiche_DT(cfente, politica, stato, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListRimborsiEnteDt(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListRimborsiEnteDt(politica, stato, idente, protocollo, from_up, to_up, from_mod, to_mod);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDocDt(String id, String tutor, String m5, String file, String doc_tutor) {
        Db db = new Db();
        boolean out = db.uploadDocDt(id, tutor, m5, file, doc_tutor);
        db.closeDB();
        return out;
    }
    
    public static Politica getPoliticaByIDDt(String id) {
        Db db = new Db();
        Politica out = db.getPoliticaByIDDt(id);
        db.closeDB();
        return out;
    }
    
    public static Map<String, String[]> get_ADInfos_DOTE() {
        Db db = new Db();
        Map<String, String[]> out = db.get_ADInfos_DOTE();
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListRimborsi_DOTE(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListRimborsi_DOTE(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static int insertRimborsoDt(String tutor, String politica, String doc_ad_au) {
        Db db = new Db();
        int out = db.insertRimborsoDt(tutor, politica, doc_ad_au);
        db.closeDB();
        return out;
    }
    
    public static boolean associaPoliticheRimborsoDt(String data, int id) {
        Db db = new Db();
        boolean out = db.associaPoliticheRimborsoDt(data, id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getListPolitichePDFDt(String ids) {
        Db db = new Db();
        ArrayList<Politica> out = db.getListPolitichePDFDt(ids);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadProtocollo_DOTE(String id, String protocollo, String path) {
        Db db = new Db();
        boolean out = db.uploadProtocollo_DOTE(id, protocollo, path);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getListPolitiche_DOTE(String dom_rimb) {
        Db db = new Db();
        ArrayList<Politica> out = db.getListPolitiche_DOTE(dom_rimb);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getRimborsiPoliticheDt(String politica, String idrimborso) {
        Db db = new Db();
        ArrayList<Politica> out = db.getRimborsiPoliticheDt(politica, idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getScartiPoliticheDt(String idrimborso, String politica) {
        Db db = new Db();
        ArrayList<Politica> out = db.getScartiPoliticheDt(idrimborso, politica);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getListPoliticheDt(String dom_rimb) {
        Db db = new Db();
        ArrayList<Politica> out = db.getListPoliticheDt(dom_rimb);
        db.closeDB();
        return out;
    }
    
    public static boolean accettaRimborsi_DOTE(String id) {
        Db db = new Db();
        boolean out = db.accettaRimborsi_DOTE(id);
        db.closeDB();
        return out;
    }
    
    public static boolean rigettaRimborsi_DOTE(String id, String motivo) {
        Db db = new Db();
        boolean out = db.rigettaRimborsi_DOTE(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDecreto_DOTE(String idrimborso, String repertorio, String datar, String decreto, String datad, String path) {
        Db db = new Db();
        boolean out = db.uploadDecreto_DOTE(idrimborso, repertorio, datar, decreto, datad, path);
        db.closeDB();
        return out;
    }
    
    public static boolean insertScartiPolitiche_DOTE(String idrimb, String idpol, String motivo) {
        Db db = new Db();
        boolean out = db.insertScartiPolitiche_DOTE(idrimb, idpol, motivo);
        db.closeDB();
        return out;
    }
    
    public static int numPoliticheRimborso_DOTE(String id, String idr) {
        Db db = new Db();
        int out = db.numPoliticheRimborso_DOTE(id, idr);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborsoCondition_DOTE(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.AnomalieRimborsoCondition_DOTE(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborso_DOTE(String id, String motivo) {
        Db db = new Db();
        boolean out = db.anomaliaRimborso_DOTE(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartoRimborsoCondition_DOTE(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.scartoRimborsoCondition_DOTE(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartaRimborso_DOTE(String id, String motivo) {
        Db db = new Db();
        boolean out = db.scartaRimborso_DOTE(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getRimborsiPolitiche_DOTE(String politica, String idrimborso) {
        Db db = new Db();
        ArrayList<Politica> out = db.getRimborsiPolitiche_DOTE(politica, idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getScartiPolitiche_DOTE(String idrimborso, String politica) {
        Db db = new Db();
        ArrayList<Politica> out = db.getScartiPolitiche_DOTE(idrimborso, politica);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDocContrattoDt(String id, String tutor, String m5, String file, String contratto, String tipo) {
        Db db = new Db();
        boolean out = db.uploadDocContrattoDt(id, tutor, m5, file, contratto, tipo);
        db.closeDB();
        return out;
    }
    
    public static Contratto getContrattoByIdDt(int id) {
        Db db = new Db();
        Contratto out = db.getContrattoByIdDt(id);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_rigettaRimborso_DOTE(String id, String motivo) {
        Db db = new Db();
        boolean out = db.Rev_rigettaRimborso_DOTE(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_scartaTuttoRimborso_DOTE(String id, String motivo, String path) {
        Db db = new Db();
        boolean out = db.Rev_scartaTuttoRimborso_DOTE(id, motivo, path);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_liquidaRimborso_DOTE(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        Db db = new Db();
        boolean out = db.Rev_liquidaRimborso_DOTE(idrimborso, totale, descrizione, path, ctrlrimborso);
        db.closeDB();
        return out;
    }
    
    public static String[] getListDocsTirocinanteD2D5_DOTE(String id) {
        Db db = new Db();
        String[] out = db.getListDocsTirocinanteD2D5_DOTE(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<B3> getPoliticaB3_DT(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<B3> out = db.getPoliticaB3_DT(cfente, politica, stato, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static Map<String, String[]> get_ADInfos_DOTE_B3() {
        Db db = new Db();
        Map<String, String[]> out = db.get_ADInfos_DOTE_B3();
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListRimborsi_DOTE_B3(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListRimborsi_DOTE_B3(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadProtocollo_DOTE_B3(String id, String protocollo, String path) {
        Db db = new Db();
        boolean out = db.uploadProtocollo_DOTE_B3(id, protocollo, path);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getListPolitiche_DOTE_B3(String dom_rimb) {
        Db db = new Db();
        ArrayList<Politica> out = db.getListPolitiche_DOTE_B3(dom_rimb);
        db.closeDB();
        return out;
    }
    
    public static String[] getListDocsTirocinante_DOTE_B3(String id) {
        Db db = new Db();
        String[] out = db.getListDocsTirocinante_DOTE_B3(id);
        db.closeDB();
        return out;
    }
    
    public static boolean rigettaRimborsi_DOTE_B3(String id, String motivo) {
        Db db = new Db();
        boolean out = db.rigettaRimborsi_DOTE_B3(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean accettaRimborsi_DOTE_B3(String id) {
        Db db = new Db();
        boolean out = db.accettaRimborsi_DOTE_B3(id);
        db.closeDB();
        return out;
    }
    
    public static int numPoliticheRimborso_DOTE_B3(String id, String idr) {
        Db db = new Db();
        int out = db.numPoliticheRimborso_DOTE_B3(id, idr);
        db.closeDB();
        return out;
    }
    
    public static boolean scartoRimborsoCondition_DOTE_B3(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.scartoRimborsoCondition_DOTE_B3(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean insertScartiPolitiche_DOTE_B3(String idrimb, String idpol, String motivo) {
        Db db = new Db();
        boolean out = db.insertScartiPolitiche_DOTE_B3(idrimb, idpol, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartaRimborso_DOTE_B3(String id, String motivo) {
        Db db = new Db();
        boolean out = db.scartaRimborso_DOTE_B3(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDecreto_DOTE_B3(String idrimborso, String repertorio, String datar, String decreto, String datad, String path) {
        Db db = new Db();
        boolean out = db.uploadDecreto_DOTE_B3(idrimborso, repertorio, datar, decreto, datad, path);
        db.closeDB();
        return out;
    }
    
    public static B3 getB3ByIdDt(String id) {
        Db db = new Db();
        B3 out = db.getB3ByIdDt(id);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_liquidaRimborso_DOTE_B3(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        Db db = new Db();
        boolean out = db.Rev_liquidaRimborso_DOTE_B3(idrimborso, totale, descrizione, path, ctrlrimborso);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_rigettaRimborso_DOTE_B3(String id, String motivo) {
        Db db = new Db();
        boolean out = db.Rev_rigettaRimborso_DOTE_B3(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_scartaTuttoRimborso_DOTE_B3(String id, String motivo, String path) {
        Db db = new Db();
        boolean out = db.Rev_scartaTuttoRimborso_DOTE_B3(id, motivo, path);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDocB3(String id, String file, String registro, String business, String timesheet) {
        Db db = new Db();
        boolean out = db.uploadDocB3(id, file, registro, business, timesheet);
        db.closeDB();
        return out;
    }
    
    public static boolean associaPoliticheRimborsoB3Dt(String data, int id) {
        Db db = new Db();
        boolean out = db.associaPoliticheRimborsoB3Dt(data, id);
        db.closeDB();
        return out;
    }
    
    public static int insertRimborsoB3Dt(String tutor, String politica, String doc_ad_au) {
        Db db = new Db();
        int out = db.insertRimborsoB3Dt(tutor, politica, doc_ad_au);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<B3> getListB3PDFDt(String ids) {
        Db db = new Db();
        ArrayList<B3> out = db.getListB3PDFDt(ids);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getRimborsiPolitiche_DOTE_B3(String politica, String idrimborso) {
        Db db = new Db();
        ArrayList<Politica> out = db.getRimborsiPolitiche_DOTE_B3(politica, idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Politica> getScartiPolitiche_DOTE_B3(String idrimborso) {
        Db db = new Db();
        ArrayList<Politica> out = db.getScartiPolitiche_DOTE_B3(idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListRimborsiEnteB3Dt(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListRimborsiEnteB3Dt(politica, stato, idente, protocollo, from_up, to_up, from_mod, to_mod);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<B3> getListB3Dt(String dom_rimb) {
        Db db = new Db();
        ArrayList<B3> out = db.getListB3Dt(dom_rimb);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborso_DOTE_B3(String id, String motivo) {
        Db db = new Db();
        boolean out = db.anomaliaRimborso_DOTE_B3(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborsoCondition_DOTE_B3(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.AnomalieRimborsoCondition_DOTE_B3(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<B3> getRimborsiPoliticheB3Dt(String politica, String idrimborso) {
        Db db = new Db();
        ArrayList<B3> out = db.getRimborsiPoliticheB3Dt(politica, idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<B3> getScartiB3Dt(String idrimborso, String politica) {
        Db db = new Db();
        ArrayList<B3> out = db.getScartiB3Dt(idrimborso, politica);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Voucher> getVoucher_DT(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<Voucher> out = db.getVoucher_DT(cfente, politica, stato, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static Voucher getVoucherByIdDt(String id) {
        Db db = new Db();
        Voucher out = db.getVoucherByIdDt(id);
        db.closeDB();
        return out;
    }
    
    public static Map<String, String[]> get_ADInfos_Voucher_DT() {
        Db db = new Db();
        Map<String, String[]> out = db.get_ADInfos_Voucher_DT();
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDocVoucher(String id, String file, String registro, String attestato, String allegato, String delega, String voucher, String ore) {
        Db db = new Db();
        boolean out = db.uploadDocVoucher(id, file, registro, attestato, allegato, delega, voucher, ore);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListRimborsi_Voucher_DT(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListRimborsi_Voucher_DT(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadProtocollo_Voucher_DT(String id, String protocollo, String path) {
        Db db = new Db();
        boolean out = db.uploadProtocollo_Voucher_DT(id, protocollo, path);
        db.closeDB();
        return out;
    }
    
    public static boolean accettaRimborsi_Voucher_DT(String id) {
        Db db = new Db();
        boolean out = db.accettaRimborsi_Voucher_DT(id);
        db.closeDB();
        return out;
    }
    
    public static boolean rigettaRimborsi_Voucher_DT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.rigettaRimborsi_Voucher_DT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Voucher> getListVouchers(String dom_rimb) {
        Db db = new Db();
        ArrayList<Voucher> out = db.getListVouchers(dom_rimb);
        db.closeDB();
        return out;
    }
    
    public static String[] getListDocsTirocinante_Voucher(String id) {
        Db db = new Db();
        String[] out = db.getListDocsTirocinante_Voucher(id);
        db.closeDB();
        return out;
    }
    
    public static int numPoliticheRimborso_Voucher_DT(String id, String idr) {
        Db db = new Db();
        int out = db.numPoliticheRimborso_Voucher_DT(id, idr);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborsoCondition_Voucher_DT(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.anomaliaRimborsoCondition_Voucher_DT(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean insertScartiPolitiche_Voucher_DT(String idrimb, String idpol, String motivo) {
        Db db = new Db();
        boolean out = db.insertScartiPolitiche_Voucher_DT(idrimb, idpol, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean anomaliaRimborso_Voucher_DT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.anomaliaRimborso_Voucher_DT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartoRimborsoCondition_Voucher_DT(String id, String idp, String motivo) {
        Db db = new Db();
        boolean out = db.scartoRimborsoCondition_Voucher_DT(id, idp, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean scartaRimborso_Voucher_DT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.scartaRimborso_Voucher_DT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static boolean uploadDecreto_Voucher_DT(String idrimborso, String repertorio, String datar, String decreto, String datad, String path) {
        Db db = new Db();
        boolean out = db.uploadDecreto_Voucher_DT(idrimborso, repertorio, datar, decreto, datad, path);
        db.closeDB();
        return out;
    }
    
    public static int insertRimborsoVoucherDt(String tutor, String politica, String doc_ad_au) {
        Db db = new Db();
        int out = db.insertRimborsoVoucherDt(tutor, politica, doc_ad_au);
        db.closeDB();
        return out;
    }
    
    public static boolean associaRimborsoVoucherDt(String data, int id) {
        Db db = new Db();
        boolean out = db.associaRimborsoVoucherDt(data, id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Voucher> getListVoucherPDFDt(String ids) {
        Db db = new Db();
        ArrayList<Voucher> out = db.getListVoucherPDFDt(ids);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Voucher> getRimborsiVoucher(String politica, String idrimborso) {
        Db db = new Db();
        ArrayList<Voucher> out = db.getRimborsiVoucher(politica, idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Voucher> getScartiPolitiche_Voucher_DT(String idrimborso) {
        Db db = new Db();
        ArrayList<Voucher> out = db.getScartiPolitiche_Voucher_DT(idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListRimborsiEnteVoucherDt(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListRimborsiEnteVoucherDt(politica, stato, idente, protocollo, from_up, to_up, from_mod, to_mod);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Voucher> getListVoucherDt(String dom_rimb) {
        Db db = new Db();
        ArrayList<Voucher> out = db.getListVoucherDt(dom_rimb);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_liquidaRimborso_Voucher_DT(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        Db db = new Db();
        boolean out = db.Rev_liquidaRimborso_Voucher_DT(idrimborso, totale, descrizione, path, ctrlrimborso);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_scartaTuttoRimborso_Voucher_DT(String id, String motivo, String path) {
        Db db = new Db();
        boolean out = db.Rev_scartaTuttoRimborso_Voucher_DT(id, motivo, path);
        db.closeDB();
        return out;
    }
    
    public static boolean Rev_rigettaRimborso_Voucher_DT(String id, String motivo) {
        Db db = new Db();
        boolean out = db.Rev_rigettaRimborso_Voucher_DT(id, motivo);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Voucher> getRimborsiVoucherDt(String politica, String idrimborso) {
        Db db = new Db();
        ArrayList<Voucher> out = db.getRimborsiVoucherDt(politica, idrimborso);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Voucher> getScartiVoucherDt(String idrimborso, String politica) {
        Db db = new Db();
        ArrayList<Voucher> out = db.getScartiVoucherDt(idrimborso, politica);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<CO> getListCO(String idente, String idlav) {
        Db db = new Db();
        ArrayList<CO> out = db.getListCO(idente, idlav);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<String[]> getAnomaliePolitiche(String cf) {
        Db db = new Db();
        ArrayList<String[]> out = db.getAnomaliePolitiche(cf);
        db.closeDB();
        return out;
    }
    
    public static void updateAnomalie(String ids) {
        Db db = new Db();
        db.updateAnomalie(ids);
        db.closeDB();
    }
    
    public static int getNumberAnomaliePolitiche(String cf) {
        Db db = new Db();
        int out = db.getNumberAnomaliePolitiche(cf);
        db.closeDB();
        return out;
    }
    
    public static HashMap<String, Integer> getNumberAllPoliticheE(String cf) {
        Db db = new Db();
        HashMap<String, Integer> out = db.getNumberAllPoliticheE(cf);
        db.closeDB();
        return out;
    }
    
    public static int getNumberPrgDtE(String cf, String codazioneformcal) {
        Db db = new Db();
        int out = db.getNumberPrgDtE(cf, codazioneformcal);
        db.closeDB();
        return out;
    }
    
    public static int getNumberPrgE(String cf, String codazioneformcal) {
        Db db = new Db();
        int out = db.getNumberPrgE(cf, codazioneformcal);
        db.closeDB();
        return out;
    }
    
    public static int getNumberIdCardTutorScadute(int idente) {
        Db db = new Db();
        int out = db.getNumberIdCardTutorScadute(idente);
        db.closeDB();
        return out;
    }
    
    public static HashMap<String, Integer> getRimborsi_DT() {
        Db db = new Db();
        HashMap<String, Integer> out = db.getRimborsi_DT();
        db.closeDB();
        return out;
    }
    
    public static HashMap<String, Integer> getRimborsi_GG() {
        Db db = new Db();
        HashMap<String, Integer> out = db.getRimborsi_GG();
        db.closeDB();
        return out;
    }

//    public static int getSumrimborsiStato(int id, String stato) {
//        Db db = new Db();
//        int out = db.getSumrimborsiStato(id, stato);
//        db.closeDB();
//        return out;
//    }
    public static int getNumberAnomaliRegister(String cf) {
        Db db = new Db();
        int out = db.getNumberAnomaliRegister(cf);
        db.closeDB();
        return out;
    }
    
    public static int getNumberAnomaliRegisterDt(String cf) {
        Db db = new Db();
        int out = db.getNumberAnomaliRegisterDt(cf);
        db.closeDB();
        return out;
    }
    
    public static HashMap<String, Integer> getNumberAllPoliticheI(String cf) {
        Db db = new Db();
        HashMap<String, Integer> out = db.getNumberAllPoliticheI(cf);
        db.closeDB();
        return out;
    }
    
    public static int getNumberPrgDtI(String cf, String codazioneformcal) {
        Db db = new Db();
        int out = db.getNumberPrgDtI(cf, codazioneformcal);
        db.closeDB();
        return out;
    }
    
    public static int getNumberPrgI(String cf, String codazioneformcal) {
        Db db = new Db();
        int out = db.getNumberPrgI(cf, codazioneformcal);
        db.closeDB();
        return out;
    }
    
    public static HashMap<String, Integer> getRegistri_REG() {
        Db db = new Db();
        HashMap<String, Integer> out = db.getRegistri_REG();
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Bando> bandiattivi() {
        Db db = new Db();
        ArrayList<Bando> out = db.bandiattivi();
        db.closeDB();
        return out;
    }
    
    public static HashMap<String, Integer> getSumRimborsiMese(int id, String stato) {
        Db db = new Db();
        HashMap<String, Integer> out = db.getSumRimborsiMese(id, stato);
        db.closeDB();
        return out;
    }
    
    public static HashMap<String, String> getDecodificaPolitiche() {
        Db db = new Db();
        HashMap<String, String> out = db.getDecodificaPolitiche();
        db.closeDB();
        return out;
    }
    
    public static boolean decreseBudgetAttualeBando(double value, int id, String cod_politica) {
        Db db = new Db();
        boolean out = db.decreseBudgetAttualeBando(value, id, cod_politica);
        db.closeDB();
        return out;
    }
    
    public static boolean refillBudgetPrevisionaleBando(double value, int id, String cod_politica) {
        Db db = new Db();
        boolean out = db.refillBudgetPrevisionaleBando(value, id, cod_politica);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListEsiti(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListEsiti(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListEsiti_DOTE(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListEsiti_DOTE(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListEsiti_DOTE_B3(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListEsiti_DOTE_B3(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso> getListEsiti_Voucher_DT(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso> out = db.getListEsiti_Voucher_DT(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static double sumRimborsoValue(String idrimborso, String tabella, String campo, String politica) {
        Db db = new Db();
        double out = db.sumRimborsoValue(idrimborso, tabella, campo, politica);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso_PrgFormativo> getListEsiti_PrgFormativo(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso_PrgFormativo> out = db.getListEsiti_PrgFormativo(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Rimborso_PrgFormativo_Dt> getListEsiti_PrgFormativoDT(String politica, String stato, String ente, String from, String to) {
        Db db = new Db();
        ArrayList<Rimborso_PrgFormativo_Dt> out = db.getListEsiti_PrgFormativoDT(politica, stato, ente, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Registro> getEsitiRegistri(String politica, String stato, String ente, String from, String to, String nome, String cognome, String cf) {
        Db db = new Db();
        ArrayList<Registro> out = db.getEsitiRegistri(politica, stato, ente, from, to, nome, cognome, cf);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<RegistroDt> getListEsitiDT(String politica, String stato, String ente, String from, String to, String nome, String cognome, String cf) {
        Db db = new Db();
        ArrayList<RegistroDt> out = db.getListEsitiDT(politica, stato, ente, from, to, nome, cognome, cf);
        db.closeDB();
        return out;
    }
    
    public static double[] getPrezziario(String code, String bando) {
        Db db = new Db();
        double[] out = db.getPrezziario(code, bando);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Lavoratore> getLavoratori(String nome, String cognome, String cf) {
        Db db = new Db();
        ArrayList<Lavoratore> out = db.getLavoratori(nome, cognome, cf);
        db.closeDB();
        return out;
    }
    
    public static DecreaseRefill DT_Registri_DecreaseRefillById(String id) {
        Db db = new Db();
        DecreaseRefill out = db.DT_Registri_DecreaseRefillById(id);
        db.closeDB();
        return out;
    }
    
    public static DecreaseRefill GG_Registri_DecreaseRefillById(String id) {
        Db db = new Db();
        DecreaseRefill out = db.GG_Registri_DecreaseRefillById(id);
        db.closeDB();
        return out;
    }
    
    public static DecreaseRefill DT_B3_DecreaseRefillById_Single(String id) {
        Db db = new Db();
        DecreaseRefill out = db.DT_B3_DecreaseRefillById_Single(id);
        db.closeDB();
        return out;
    }
    
    public static DecreaseRefill DT_DecreaseRefillById_Single(String id) {
        Db db = new Db();
        DecreaseRefill out = db.DT_DecreaseRefillById_Single(id);
        db.closeDB();
        return out;
    }
    
    public static DecreaseRefill GG_C06_DecreaseRefillById_Single(String id) {
        Db db = new Db();
        DecreaseRefill out = db.GG_C06_DecreaseRefillById_Single(id);
        db.closeDB();
        return out;
    }
    
    public static DecreaseRefill GG_DecreaseRefillById_Single(String id) {
        Db db = new Db();
        DecreaseRefill out = db.GG_DecreaseRefillById_Single(id);
        db.closeDB();
        return out;
    }
    
    public static DecreaseRefill DT_Voucher_DecreaseRefillById_Single(String id) {
        Db db = new Db();
        DecreaseRefill out = db.DT_Voucher_DecreaseRefillById_Single(id);
        db.closeDB();
        return out;
    }
    
    public static DecreaseRefill DT_C06_DecreaseRefillById_Single(String id) {
        Db db = new Db();
        DecreaseRefill out = db.DT_C06_DecreaseRefillById_Single(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<DecreaseRefill> DT_B3_DecreaseRefillById(String id) {
        Db db = new Db();
        ArrayList<DecreaseRefill> out = db.DT_B3_DecreaseRefillById(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<DecreaseRefill> DT_DecreaseRefillById(String id) {
        Db db = new Db();
        ArrayList<DecreaseRefill> out = db.DT_DecreaseRefillById(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<DecreaseRefill> GG_DecreaseRefillById(String id) {
        Db db = new Db();
        ArrayList<DecreaseRefill> out = db.GG_DecreaseRefillById(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<DecreaseRefill> DT_Voucher_DecreaseRefillById(String id) {
        Db db = new Db();
        ArrayList<DecreaseRefill> out = db.DT_Voucher_DecreaseRefillById(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<DecreaseRefill> DT_C06_DecreaseRefillById(String id) {
        Db db = new Db();
        ArrayList<DecreaseRefill> out = db.DT_C06_DecreaseRefillById(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<DecreaseRefill> GG_C06_DecreaseRefillById(String id) {
        Db db = new Db();
        ArrayList<DecreaseRefill> out = db.GG_C06_DecreaseRefillById(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Bando> getbandiattivi(String tipo) {
        Db db = new Db();
        ArrayList<Bando> out = db.getbandiattivi(tipo);
        db.closeDB();
        return out;
    }
    
    public static boolean transferMoney(String iddonatore, String idricevente, String budget) {
        Db db = new Db();
        boolean out = db.transferMoney(iddonatore, idricevente, budget);
        db.closeDB();
        return out;
    }
    
    public static Double[] getSumPolitiche(String id) {
        Db db = new Db();
        Double[] out = db.getSumPolitiche(id);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativo> getPrgFormativiClosed(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<PrgFormativo> out = db.getPrgFormativiClosed(cfente, politica, stato, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativoDt> getPrgFormativiDtClosed(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<PrgFormativoDt> out = db.getPrgFormativiDtClosed(cfente, politica, stato, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<ViewPolitiche> getPoliticheLavoratore(String cdnlav) {
        Db db = new Db();
        ArrayList<ViewPolitiche> out = db.getPoliticheLavoratore(cdnlav);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<ViewPolitiche> getPoliticheLavoratore(String cdnlav, String cf_ente) {
        Db db = new Db();
        ArrayList<ViewPolitiche> out = db.getPoliticheLavoratore(cdnlav, cf_ente);
        db.closeDB();
        return out;
    }
    
    public static String getStatoRimborso(String idrimborso, String cod_risorsa) {
        Db db = new Db();
        String s = db.getStatoRimborso(idrimborso, cod_risorsa);
        db.closeDB();
        return s;
    }
    
    public static ArrayList<Lavoratore> getLavoratori(String nome, String cognome, String cf, String cf_ente) {
        Db db = new Db();
        ArrayList<Lavoratore> out = db.getLavoratori(nome, cognome, cf, cf_ente);
        db.closeDB();
        return out;
    }
    
    public static void updateAllTutorDocs(String doc, String idtutor) {
        Db db = new Db();
        db.updateAllTutorDocs(doc, idtutor);
        db.closeDB();
    }
    
    public static ArrayList<PrgFormativo> getPrgFormativiReg(String ente, String politica, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<PrgFormativo> out = db.getPrgFormativiReg(ente, politica, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<PrgFormativoDt> getPrgFormativiRegDt(String ente, String politica, String nome, String cognome, String cf, String from, String to) {
        Db db = new Db();
        ArrayList<PrgFormativoDt> out = db.getPrgFormativiRegDt(ente, politica, nome, cognome, cf, from, to);
        db.closeDB();
        return out;
    }
    
    public static boolean updateTutor(String id, String nome, String cognome, String cf, String email, String telefono, String ruolo) {
        Db db = new Db();
        boolean out = db.updateTutor(id, nome, cognome, cf, email, telefono, ruolo);
        db.closeDB();
        return out;
    }
    
    public static boolean convalidateRegistri(String data) {
        Db db = new Db();
        boolean out = db.convalidateRegistri(data);
        db.closeDB();
        return out;
    }
    
    public static boolean convalidateRegistriDt(String data) {
        Db db = new Db();
        boolean out = db.convalidateRegistriDt(data);
        db.closeDB();
        return out;
    }
    
    public static ArrayList<String> getMessageToUser() {
        Db db = new Db();
        ArrayList<String> out = db.getMessageToUser();
        db.closeDB();
        return out;
    }
    
    public static ArrayList<Convenzione> getConvenzioni(int ente, String tipo_bando, String politica) {
        Db db = new Db();
        ArrayList<Convenzione> out = db.getConvenzioni(ente, tipo_bando, politica);
        db.closeDB();
        return out;
    }
    
    public static Ente getEnteByCf(String cf) {
        Db db = new Db();
        Ente out = db.getEnteByCf(cf);
        db.closeDB();
        return out;
    }
}
