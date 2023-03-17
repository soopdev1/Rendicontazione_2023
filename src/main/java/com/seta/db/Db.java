/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.db;

import com.seta.activity.Action;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import java.util.logging.*;

/**
 *
 * @author dolivo
 */
public class Db {

    private static final Logger LOGGER = Logger.getLogger(Db.class.getName());
    private static final String JNDI_DS = "java:jboss/datasources/rendicontazioneDS";
    private Connection c = null;

    public Db() {
        try {
            javax.naming.InitialContext ctx = new javax.naming.InitialContext();
            DataSource dbDS = (DataSource) ctx.lookup(JNDI_DS);
            this.c = dbDS.getConnection();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException("Eccezione nell'accesso al database. " + ex.getMessage(), ex);
        }

    }

    public void closeDB() {
        try {
            if (c != null) {
                this.c.close();
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "DB" + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public Operator getOperatore(String user, String pwd) {//FATTO
        Operator op = new Operator();
        try {
            String sql = "SELECT idoperatore,username,password,nome,cognome,email,telefono,tipo,stato,dataregistrazione,ente FROM operatore WHERE username = ? AND password = ?;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                op.setIdoperatore(rs.getInt("idoperatore"));
                op.setUsername(rs.getString("username"));
                op.setPassword(rs.getString("password"));
                op.setNome(rs.getString("nome"));
                op.setCognome(rs.getString("cognome"));
                op.setEmail(rs.getString("email"));
                op.setTelefono(rs.getString("telefono"));
                op.setTipo(rs.getInt("tipo"));
                op.setStato(rs.getInt("stato"));
                op.setEnte(rs.getInt("ente"));
                op.setDataregistrazione(rs.getString("dataregistrazione"));
                return op;
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "getOperatore " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public Operator Operatore(int idoperatore) {//FATTO
        Operator ug = null;
        try {
            String sql = "SELECT * FROM operatore WHERE idoperatore = ? ";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, idoperatore);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ug = new Operator(rs.getInt("idoperatore"), rs.getInt("tipo"), rs.getInt("stato"), rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getString("dataregistrazione"));
            }
            return ug;
        } catch (SQLException ex) {
            Action.insertTracking("", "Operatore " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean updateOperatore(int id, String name, String surname, String email, String phone) {//SI FA COL MARGE
        try {
            String sql = "UPDATE operatore SET nome=?, cognome=?, email=?, telefono=? WHERE idoperatore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Action.insertTracking("", "updateOperatore " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public boolean verifyOldPassword(String password, String idoperatore) {//FATTO
        try {
            String sql = "SELECT password FROM operatore WHERE password=md5(?) AND idoperatore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, idoperatore);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            Action.insertTracking("", "Metodo: verifyOldPassword " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public boolean changePassword(String idoperatore, String new_psw) {//fatto
        try {
            String sql = "UPDATE operatore SET password=md5(?) WHERE idoperatore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, new_psw);
            ps.setString(2, idoperatore);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            Action.insertTracking("Rendicontazione", "Metodo: changePassword " + e.getMessage());
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    public Operator getDataReset(String mail, String user) {//fatto
        Operator reset = new Operator();
        try {
            String sql = "SELECT nome,cognome,stato,username,email FROM operatore WHERE email = ? AND username = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, mail);
            ps.setString(2, user);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reset.setNome(rs.getString("nome"));
                reset.setCognome(rs.getString("cognome"));
                reset.setStato(rs.getInt("stato"));
                reset.setUsername(rs.getString("username"));
                reset.setEmail(rs.getString("email"));
                return reset;
            }

        } catch (SQLException ex) {
            Action.insertTracking("", "getDataReset " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean resetPassword(String user, String newpsw) {//fatto
        int ris = 0;
        try {
            String sql = "UPDATE operatore SET password=md5(?), stato='2' WHERE username = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, newpsw);
            ps.setString(2, user);
            ris = ps.executeUpdate();
            return ris > 0;
        } catch (SQLException ex) {
            Action.insertTracking("", "getDataReset" + ex.getMessage());
            return false;
        }
    }

    public String getPasswordFromUser(String user) {//fatto
        String psw = "";
        try {
            String sql = "SELECT password FROM operatore WHERE username = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                psw = rs.getString("password");
            }
            return psw;
        } catch (SQLException ex) {
            Action.insertTracking("", "getPasswordFromUser " + ex.getMessage());
            return psw;
        }
    }

    public boolean changePasswordFA(String user, String actualpsw, String newpsw) {//fatto
        int esito = 0;
        try {
            String sql = "UPDATE operatore SET password = md5(?) WHERE username= ? AND password = md5(?) ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, newpsw);
            ps.setString(2, user);
            ps.setString(3, actualpsw);
            esito = ps.executeUpdate();

            return esito > 0;
        } catch (SQLException ex) {
            ////log.severe(ex.getMessage());
            Action.insertTracking("service", "changePasswordFA " + ex.getMessage());
            return false;
        }
    }//reset password

    public boolean setStatusFA(String user) {//fatto
        int esito = 0;
        try {
            String sql = "UPDATE operatore SET stato ='1' WHERE username= ? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, user);

            esito = ps.executeUpdate();
            return esito > 0;
        } catch (SQLException ex) {
            ////log.severe(ex.getMessage());
            Action.insertTracking("service", "setStatusFA " + ex.getMessage());
            return false;
        }
    }

    public String getPath(String id) {//fatto
        try {
            String sql = "SELECT url FROM path WHERE idpath = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("url");
            }
        } catch (SQLException ex) {
            ////log.severe(ex.getMessage());
            Action.insertTracking("", "getPath " + ex.getMessage());
        }
        return "-";
    }
//    public String getPath(String id) {//fatto
//        try {
//            String sql = "SELECT url FROM deploy_path WHERE idpath = ?";
//            PreparedStatement ps = this.c.prepareStatement(sql);
//            ps.setString(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getString("url");
//            }
//        } catch (SQLException ex) {
//            ////log.severe(ex.getMessage());
//            Action.insertTracking("", "getPath " + ex.getMessage());
//        }
//        return "-";
//    }

    public String getUserPermission(String name) {//fatto
        try {
            String sql = "SELECT permessi FROM pagina WHERE nome = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("permessi");
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getUserPermission " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return "";
    }

    public void insertTracking(String id_user, String action) {//fatto
        try {
            String sql = "INSERT INTO tracking (idoperatore,azione) VALUES (?,?) ";
            PreparedStatement ps = this.c.prepareStatement(sql);

            if (id_user == null || id_user.equals("")) {
                id_user = "Rendicontazione";
            }
            ps.setString(1, id_user);
            ps.setString(2, action);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void insertModTracking(int id_user, String action, String cod_risorsa, String id_risorsa) {//fatto
        try {
            String sql = "INSERT INTO ins_mod_tracking (id_operatore, codice_risorsa, id_risorsa, azione) VALUES (?,?,?,?) ";
            PreparedStatement ps = this.c.prepareStatement(sql);

            ps.setInt(1, id_user);
            ps.setString(2, cod_risorsa);
            ps.setString(3, id_risorsa);
            ps.setString(4, action);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "insertModTracking " + ex.getMessage());
        }
    }

    public boolean ctrlUsername(String user) {//fatto
        try {
            String sql = "SELECT username FROM operatore WHERE username=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Action.insertTracking("", "ctrlUsername " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public boolean ctrlEmailOp(String email) {//fatto
        try {
            String sql = "SELECT email FROM operatore WHERE email=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Action.insertTracking("", "ctrlEmailOp " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public Ente getEnteById(int ente) {
        Ente out = new Ente();
        try {
            String sql = "SELECT * FROM ente_promotore where idente_promotore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, ente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setRagioneSociale(rs.getString("ragionesociale"));
                out.setId(rs.getInt("idente_promotore"));
                out.setCf(rs.getString("cf"));
                out.setPiva(rs.getString("piva"));
                out.setEmail(rs.getString("email"));
                out.setTelefono(rs.getString("telefono"));
                out.setComune(rs.getString("comune"));
                out.setIndirizzo(rs.getString("indirizzo"));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getEnteById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    //SEZIONE REGIONAL INIZIO
    public ArrayList<String[]> getList_TipoBando() {
        try {
            ArrayList<String[]> out = new ArrayList<>();
            String sql = "SELECT idtipo_bando,descrizione FROM tipo_bando";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("idtipo_bando"), rs.getString("descrizione")};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getList_TipoBando" + ex.getMessage());
        }
        return null;
    }

    public int Insert_Bando(String titolo, String data_inizio, String data_fine, String flag_sportello, String tipo_bando, String budget, String path, String anno) {
        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO bando (titolo,data_inizio,data_fine,flag_sportello,tipo_bando,budget,path,budget_attuale,decreto,stato, budget_previsione) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, titolo);
            ps.setString(2, data_inizio);
            ps.setString(3, data_fine);
            ps.setString(4, flag_sportello);
            ps.setString(5, tipo_bando);
            ps.setString(6, budget);
            ps.setString(7, path);
            ps.setString(8, budget);
            ps.setString(9, anno);
            ps.setString(10, "I");
            ps.setString(11, budget);
            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;
//            int x = ps.executeUpdate();
//            return x > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Inserimento bando:" + ex.getMessage());
        }
        return 0;
    }

    public ArrayList<Bando> searchBandi(String titolo, String tipo, String from, String to) {
        ArrayList<Bando> out = new ArrayList<>();
        try {
            String sql = "SELECT * FROM bando ";

            boolean b = true;
            ArrayList<String> parameter = new ArrayList<>();
            if (!titolo.equals("")) {
                if (b) {
                    sql += "WHERE titolo LIKE ?";
                    b = false;
                } else {
                    sql += "AND titolo LIKE ?";
                }
                parameter.add("%" + titolo + "%");
            }
            if (!tipo.equals("")) {
                if (b) {
                    sql += "WHERE tipo_bando =?";
                    b = false;
                } else {
                    sql += "AND tipo_bando =?";
                }
                parameter.add(tipo);
            }
            if (!from.equals("") && !to.equals("")) {
                if (b) {
                    sql += "WHERE data_inizio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y')";
                    b = false;
                } else {
                    sql += "AND data_inizio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y')";
                }
                parameter.add(from);
                parameter.add(to);
            }

            sql += " LIMIT 1000 ";
            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < parameter.size(); i++) {
                ps.setString((i + 1), parameter.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bando tmp = new Bando();
                tmp.setIdbando(rs.getString("idbando"));
                tmp.setTitolo(rs.getString("titolo"));
                tmp.setData_fine(rs.getString("data_fine"));
                tmp.setData_inizio(rs.getString("data_inizio"));
                tmp.setFlag_sportello(rs.getString("flag_sportello"));
                tmp.setTipo_bando(rs.getString("tipo_bando"));
                tmp.setBudget(rs.getString("budget"));
                tmp.setData_creazione(rs.getString("data_creazione"));
                tmp.setPath(rs.getString("path"));
                tmp.setDecreto(rs.getString("decreto"));
                tmp.setStato(rs.getString("stato"));
                tmp.setBudget_attuale(rs.getString("budget_attuale"));
                out.add(tmp);
            }

            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "searchBandi " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public Map<String, String> getTipoBandoById() {
        Map<String, String> result = new HashMap<>();
        try {
            String sql = "SELECT idtipo_bando,descrizione FROM tipo_bando";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("idtipo_bando"), rs.getString("descrizione"));
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "getTipoBandoById:" + e.getMessage());
            return null;
        }
    }

    public Map<String, String> getPoliticaById() {
        Map<String, String> result = new HashMap<>();
        try {
            String sql = "SELECT idtipo_politica,descrizione FROM tipo_politica";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("idtipo_politica"), rs.getString("descrizione"));
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "getPoliticaById:" + e.getMessage());
            return null;
        }
    }

    public Map<String, String> getPoliticaByIdDT() {
        Map<String, String> result = new HashMap<>();
        try {
            String sql = "SELECT idtipo_politica,descrizione FROM tipo_politica WHERE tipo_bando=1";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("idtipo_politica"), rs.getString("descrizione"));
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "getPoliticaById:" + e.getMessage());
            return null;
        }
    }

    public Map<String, String> getPoliticaByIdGG() {
        Map<String, String> result = new HashMap<>();
        try {
            String sql = "SELECT idtipo_politica,descrizione FROM tipo_politica WHERE tipo_bando=2";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("idtipo_politica"), rs.getString("descrizione"));
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "getPoliticaByIdGG:" + e.getMessage());
            return null;
        }
    }

    public Bando getBandoById(String id) {
        Bando out = new Bando();
        try {
            String sql = "SELECT * FROM bando where idbando=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setIdbando(rs.getString("idbando"));
                out.setTitolo(rs.getString("titolo"));
                out.setData_fine(rs.getString("data_fine"));
                out.setData_inizio(rs.getString("data_inizio"));
                out.setFlag_sportello(rs.getString("flag_sportello"));
                out.setTipo_bando(rs.getString("tipo_bando"));
                out.setBudget(String.valueOf(rs.getDouble("budget")));
                out.setData_creazione(rs.getString("data_creazione"));
                out.setPath(rs.getString("path"));
                out.setDecreto(rs.getString("decreto"));
                out.setStato(rs.getString("stato"));
                out.setBudget_attuale(String.valueOf(rs.getDouble("budget_attuale")));
                out.setBudget_previsione(String.valueOf(rs.getDouble("budget_previsione")));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getBandoById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    //SEZIONE REGIONAL FINE  
    // SEZIONE TUTOR      
    // ADD SIMONE
    public boolean insertTutor(String ruolo, String nome, String cognome, String email, String telefono, String documento, String scadenza_documento, int ente, String cf) {
        try {
            String sql = "INSERT INTO tutor (ruolo,nome,cognome,email,telefono,documento,scadenza_documento, ente_promotore, codice_fiscale) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, ruolo);
            ps.setString(2, nome);
            ps.setString(3, cognome);
            ps.setString(4, email);
            ps.setString(5, telefono);
            ps.setString(6, documento);
            ps.setString(7, scadenza_documento);
            ps.setInt(8, ente);
            ps.setString(9, cf);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Inserimento tutor:" + e.getMessage());
        }
        return false;
    }

    public ArrayList<String[]> getListTipoTutor() {
        try {
            ArrayList<String[]> out = new ArrayList<>();
            String sql = "SELECT idtipo,descrizione FROM tipo_tutor";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("idtipo"), rs.getString("descrizione")};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListTipoTutor" + ex.getMessage());
        }
        return null;
    }

    public ArrayList<Tutor> getTutorEnte(int idente) {
        ArrayList<Tutor> out = new ArrayList<>();

        try {
            String sql = "SELECT tutor.*,descrizione FROM tutor, tipo_tutor where ruolo=idtipo and stato='A' and ente_promotore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, idente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tutor temp = new Tutor();
                temp.setNome(rs.getString("nome"));
                temp.setCognome(rs.getString("cognome"));
                temp.setEmail(rs.getString("email"));
                temp.setTelefono(rs.getString("telefono"));
                temp.setStato(rs.getString("stato"));
                temp.setDocumento(rs.getString("documento"));
                temp.setScadenzaDoc(rs.getString("scadenza_documento"));
                temp.setIdente(rs.getInt("ente_promotore"));
                temp.setId(rs.getInt("idtutor"));
                temp.setRuolo(rs.getInt("ruolo"));
                temp.setRuolo_s(rs.getString("descrizione"));
                temp.setCf(rs.getString("codice_fiscale"));
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getTutorEnte" + ex.getMessage());
        }
        return null;
    }

    public boolean deleteTutor(String id) {
        try {
            String sql = "UPDATE tutor SET stato='D' WHERE idtutor=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getTutorEnte" + ex.getMessage());
        }
        return false;
    }

    public Tutor findTutorById(String id) {
        try {
            String sql = "SELECT tutor.*,descrizione FROM tutor, tipo_tutor where ruolo=idtipo AND idtutor=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Tutor temp = new Tutor();
                temp.setNome(rs.getString("nome"));
                temp.setCognome(rs.getString("cognome"));
                temp.setEmail(rs.getString("email"));
                temp.setTelefono(rs.getString("telefono"));
                temp.setStato(rs.getString("stato"));
                temp.setDocumento(rs.getString("documento"));
                temp.setScadenzaDoc(rs.getString("scadenza_documento"));
                temp.setIdente(rs.getInt("ente_promotore"));
                temp.setId(rs.getInt("idtutor"));
                temp.setRuolo(rs.getInt("ruolo"));
                temp.setCf(rs.getString("codice_fiscale"));
                temp.setRuolo_s(rs.getString("descrizione"));
                return temp;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getTutorEnte" + ex.getMessage());
        }
        return null;
    }

    public boolean updateDocId(String id, String doc, String scadenza) {
        try {
            String sql = "UPDATE tutor SET documento=?, scadenza_documento=? WHERE idtutor=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, doc);
            ps.setString(2, scadenza);
            ps.setString(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "updateDocId" + ex.getMessage());
        }
        return false;
    }

    public boolean Modify_Bando(String id, String titolo, String data_inizio, String data_fine, String flag_sportello, String tipo_bando, String budget, String path, String decreto, String budgeta, String budgetp) {
        try {
            String sql = "UPDATE bando SET titolo = ?,data_inizio = ?,data_fine = ?,flag_sportello = ?,tipo_bando = ?,budget = ?,path= ?,decreto = ?, budget_attuale = ?, budget_previsione = ? WHERE idbando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, titolo);
            ps.setString(2, data_inizio);
            ps.setString(3, data_fine);
            ps.setString(4, flag_sportello);
            ps.setString(5, tipo_bando);
            ps.setString(6, budget);
            ps.setString(7, path);
            ps.setString(8, decreto);
            ps.setString(9, budgeta);
            ps.setString(10, budgetp);
            ps.setString(11, id);
            int x = ps.executeUpdate();
            return x > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Modify_Bando:" + e.getMessage());
        }
        return false;
    }

//    public ArrayList<String[]> getListTipoPolitica(String tipo,String flag) {
//        try {
//            ArrayList<String[]> out = new ArrayList<>();
//            String sql;
//            if(flag.equals("N")){
//            sql = "SELECT idtipo_politica,descrizione FROM tipo_politica WHERE tipo_bando = ? AND tipo_politica.idtipo_politica NOT IN (SELECT budget_politiche.tipo_politica FROM budget_politiche,bando WHERE bando.idbando = budget_politiche.bando AND bando.data_fine > curdate())";
//            }else{
//            sql = "SELECT idtipo_politica,descrizione FROM tipo_politica WHERE tipo_bando = ? AND tipo_politica.idtipo_politica NOT IN (SELECT budget_politiche.tipo_politica FROM budget_politiche,bando WHERE bando.idbando = budget_politiche.bando AND bando.budget > 0)";
//            }
//            PreparedStatement ps = this.c.prepareStatement(sql);
//            ps.setString(1, tipo);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                String[] temp = {rs.getString("idtipo_politica"), rs.getString("descrizione")};
//                out.add(temp);
//            }
//            return out;
//        } catch (SQLException ex) {
//            Action.insertTracking("", "getListTipoPolitica" + ex.getMessage());
//        }
//        return null;
//    }
    public ArrayList<String[]> getListTipoPolitica(String tipo) {
        try {
            ArrayList<String[]> out = new ArrayList<>();
            String sql = "SELECT idtipo_politica,descrizione FROM tipo_politica WHERE tipo_bando = ? order by descrizione";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("idtipo_politica"), rs.getString("descrizione")};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListTipoPolitica" + ex.getMessage());
        }
        return null;
    }

    public ArrayList<String[]> getListTipoPolitica(String tipo, String tirocinio) {
        try {
            ArrayList<String[]> out = new ArrayList<>();
            String sql = "SELECT idtipo_politica,descrizione FROM tipo_politica WHERE tipo_bando = ? AND misura = ? ";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, tipo);
            ps.setString(2, tirocinio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("idtipo_politica"), rs.getString("descrizione")};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListTipoPolitica" + ex.getMessage());
        }
        return null;
    }

    public int insertConvenzione(String codice, String from, String to, String politica, int ente, String bando, String file) {

        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO convenzione SET codice=?, inizio=?, fine=?, tipo_politica=?,ente=?, bando=?, file=?";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, codice);
            ps.setString(2, from);
            ps.setString(3, to);
            ps.setString(4, politica);
            ps.setInt(5, ente);
            ps.setString(6, bando);
            ps.setString(7, file);
            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "insertConvenzione" + ex.getMessage());
        }

        return 0;
    }

    public boolean insertBudgetPolitiche(String idbando, ArrayList<String[]> val) {
        try {
            this.c.setAutoCommit(false);
            for (int x = 0; x < val.size(); x++) {
                PreparedStatement pst = this.c.prepareStatement("INSERT INTO budget_politiche (bando,tipo_politica,budget_politica,budget_politica_prev,budget_politica_attuale) values (?,?,?,?,?)");
                pst.setString(1, idbando);
                pst.setString(2, val.get(x)[0]);
                pst.setString(3, val.get(x)[1]);
                pst.setString(4, val.get(x)[1]);
                pst.setString(5, val.get(x)[1]);
                pst.executeUpdate();
            }
            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "insertBudgetPolitiche:" + e.getMessage());
        }
        return false;
    }

    public ArrayList<String[]> getBudgetsPolitiche(String idbando, String tipo) {
        try {
            ArrayList<String[]> out = new ArrayList<>();
            String sql = "SELECT tipo_politica AS tip,tipo_politica.descrizione AS descr,budget_politica AS bud, budget_politica_prev AS bud_p, budget_politica_attuale AS bud_a "
                    + "FROM budget_politiche,tipo_politica "
                    + "WHERE bando = ? AND tipo_bando = ? AND tipo_politica.idtipo_politica = budget_politiche.tipo_politica";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idbando);
            ps.setString(2, tipo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] temp = {rs.getString("tip"), rs.getString("descr"), String.valueOf(rs.getDouble("bud")), String.valueOf(rs.getDouble("bud_p")), String.valueOf(rs.getDouble("bud_a"))};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getBudgetsPolitiche" + ex.getMessage());
        }
        return null;
    }

    public boolean ctrl_BudgetsPolitiche(String idbando) {
        try {
            String sql = "SELECT * FROM budget_politiche WHERE bando=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idbando);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Action.insertTracking("", "ctrl_BudgetsPolitiche " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public ArrayList<Convenzione> getConvenzioni(int ente, String tipo_bando) {
        ArrayList<Convenzione> out = new ArrayList<>();
        try {
            String sql = "SELECT convenzione.*, tipo_politica.descrizione FROM convenzione,tipo_politica where ente=? and stato='A' and tipo_politica=idtipo_politica and tipo_bando=bando and tipo_bando=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, ente);
            ps.setString(2, tipo_bando);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Convenzione t = new Convenzione();
                t.setId(rs.getInt("idconvenzione"));
                t.setCodice(rs.getString("codice"));
                t.setInizio(rs.getString("inizio"));
                t.setFine(rs.getString("fine"));
                t.setPolitica(rs.getString("tipo_politica"));
                t.setEnte(rs.getInt("ente"));
                t.setBando(rs.getInt("bando"));
                t.setD_politica(rs.getString("descrizione"));
                t.setFile(rs.getString("file"));
                out.add(t);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getConvenzioni " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }

    public ArrayList<Convenzione> getConvenzioni(int ente) {
        ArrayList<Convenzione> out = new ArrayList<>();
        try {
            String sql = "SELECT convenzione.*, t.descrizione, tb.descrizione "
                    + "FROM convenzione,tipo_politica as t, tipo_bando as tb "
                    + "WHERE ente=? AND stato='A' AND tipo_politica=idtipo_politica AND tipo_bando=bando AND idtipo_bando=bando ORDER BY idconvenzione";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, ente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Convenzione t = new Convenzione();
                t.setId(rs.getInt("idconvenzione"));
                t.setCodice(rs.getString("codice"));
                t.setInizio(rs.getString("inizio"));
                t.setFine(rs.getString("fine"));
                t.setPolitica(rs.getString("tipo_politica"));
                t.setEnte(rs.getInt("ente"));
                t.setBando(rs.getInt("bando"));
                t.setD_politica(rs.getString("t.descrizione"));
                t.setD_bando(rs.getString("tb.descrizione"));
                t.setFile(rs.getString("file"));
                out.add(t);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getConvenzioni " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }

    public boolean modifyBudgetPolitiche(String idbando, ArrayList<String[]> val) {
        try {
            this.c.setAutoCommit(false);

            String sql1 = "DELETE FROM budget_politiche WHERE bando = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, idbando);
            ps1.execute();

            for (int x = 0; x < val.size(); x++) {
                PreparedStatement pst = this.c.prepareStatement("INSERT INTO budget_politiche (bando,tipo_politica,budget_politica,budget_politica_prev, budget_politica_attuale) values (?,?,?,?,?);");
                pst.setString(1, idbando);
                pst.setString(2, val.get(x)[0]);
                pst.setString(3, val.get(x)[1]);
                pst.setString(4, val.get(x)[2]);
                pst.setString(5, val.get(x)[3]);
                pst.executeUpdate();
            }
            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "modifyBudgetPolitiche:" + e.getMessage());
        }
        return false;
    }

    public String getBudgetStanziato(String bando) {
        try {
            String sql = "SELECT sum(budget_politica) as TOTALE FROM RegCal_Reporting.budget_politiche WHERE bando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bando);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TOTALE");
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getBudgetStanziato " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return "0";
    }

    public Convenzione findConvenzioneById(String id) {
        Convenzione out = new Convenzione();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String sql = "SELECT * FROM convenzione WHERE idconvenzione=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setId(rs.getInt("idconvenzione"));
                out.setCodice(rs.getString("codice"));
                out.setInizio(sdf2.format(sdf1.parse(rs.getString("inizio"))));
                out.setFine(sdf2.format(sdf1.parse(rs.getString("fine"))));
                out.setPolitica(rs.getString("tipo_politica"));
                out.setEnte(rs.getInt("ente"));
                out.setBando(rs.getInt("bando"));
                out.setFile(rs.getString("file"));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("service", "findConvenzioneById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ParseException ex) {

        }

        return null;
    }

    public boolean updateConvenzione(String id, String codice, String from, String to, String politica, String bando, String path) {
        try {
            String sql = "UPDATE convenzione SET codice=?, inizio=?, fine=?, tipo_politica=?, bando=?, file=? WHERE idconvenzione=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, codice);
            ps.setString(2, from);
            ps.setString(3, to);
            ps.setString(4, politica);
            ps.setString(5, bando);
            ps.setString(6, path);
            ps.setString(7, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "updateConvenzione" + ex.getMessage());
        }
        return false;
    }

    public ArrayList<String> getTipoBando() {
        ArrayList<String> out = new ArrayList<>();
        try {
            String sql = "SELECT descrizione FROM tipo_bando order by descrizione";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.add(rs.getString("regione"));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "descrizione " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<String[]> getPolitiche() {
        ArrayList<String[]> out = new ArrayList<>();
        try {
            String sql = "SELECT tipo_bando.descrizione, tipo_politica.descrizione,tipo_politica.idtipo_politica FROM tipo_bando,tipo_politica WHERE tipo_bando.idtipo_bando = tipo_politica.tipo_bando;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("tipo_bando.descrizione"), rs.getString("tipo_politica.descrizione"), rs.getString("tipo_politica.idtipo_politica")};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getPolitiche " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<String[]> getBandi() {
        ArrayList<String[]> out = new ArrayList<>();
        try {
            String sql = "SELECT tipo_politica.descrizione,bando.titolo FROM budget_politiche,bando,tipo_politica WHERE budget_politiche.bando = bando.idbando AND tipo_politica.idtipo_politica = budget_politiche.tipo_politica;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("tipo_politica.descrizione"), rs.getString("bando.titolo")};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getBandi " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Tutor> getListTutorEnte(int idente) {
        ArrayList<Tutor> out = new ArrayList<>();

        try {
            String sql = "SELECT * FROM tutor where (ruolo='1' or ruolo= '3') and stato='A' and ente_promotore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, idente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tutor temp = new Tutor();
                temp.setNome(rs.getString("nome"));
                temp.setCognome(rs.getString("cognome"));
                temp.setEmail(rs.getString("email"));
                temp.setTelefono(rs.getString("telefono"));
                temp.setStato(rs.getString("stato"));
                temp.setDocumento(rs.getString("documento"));
                temp.setScadenzaDoc(rs.getString("scadenza_documento"));
                temp.setIdente(rs.getInt("ente_promotore"));
                temp.setId(rs.getInt("idtutor"));
                temp.setRuolo(rs.getInt("ruolo"));

                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getTutorEnte" + ex.getMessage());
        }
        return null;
    }

    public ArrayList<Tutor> getListTutorEnte(int idente, String ruolo) {
        ArrayList<Tutor> out = new ArrayList<>();

        try {
            String sql = "SELECT * FROM tutor where ruolo=? and stato='A' and ente_promotore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, ruolo);
            ps.setInt(2, idente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tutor temp = new Tutor();
                temp.setNome(rs.getString("nome"));
                temp.setCognome(rs.getString("cognome"));
                temp.setEmail(rs.getString("email"));
                temp.setTelefono(rs.getString("telefono"));
                temp.setStato(rs.getString("stato"));
                temp.setDocumento(rs.getString("documento"));
                temp.setScadenzaDoc(rs.getString("scadenza_documento"));
                temp.setIdente(rs.getInt("ente_promotore"));
                temp.setId(rs.getInt("idtutor"));
                temp.setRuolo(rs.getInt("ruolo"));

                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getTutorEnte" + ex.getMessage());
        }
        return null;
    }

//    public ArrayList<Lavoratore> getEnteLavoratoriAttivi(String cfente) {
//        ArrayList<Lavoratore> out = new ArrayList<>();
//        try {
//            String sql = "SELECT cdnlavoratore,nome,cognome FROM Lavoratore WHERE cdnlavoratore "
//                    + "NOT IN (SELECT lavoratore_cdnlavoratore FROM RegCal_Reporting.Comunicazione_Obbligatoria where tipo_movimento='CES' and datore_lavoro_codice_fiscale=?)"
//                    + "AND cdnlavoratore IN(SELECT lavoratore_cdnlavoratore FROM RegCal_Reporting.Comunicazione_Obbligatoria where tipo_movimento='AVV' and datore_lavoro_codice_fiscale=?)";
//            PreparedStatement ps = this.c.prepareStatement(sql);
//            ps.setString(1, cfente);
//            ps.setString(2, cfente);
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Lavoratore temp = new Lavoratore();
//                temp.setCdnlavoratore(rs.getInt("cdnlavoratore"));
////                temp.setCodice_provincia(rs.getInt("codice_provincia"));
////                temp.setCittadinanza(rs.getInt("cittadinanza"));
////                temp.setDomicilio_cap(rs.getInt("domicilio_cap"));
////                temp.setCodice_fiscale(rs.getString("codice_fiscale"));
////                temp.setValidita_cf(rs.getString("validita_cf"));
////                temp.setCodice_fiscale_originale(rs.getString("codice_fiscale_originale"));
//                temp.setCognome(rs.getString("cognome"));
//                temp.setNome(rs.getString("nome"));
////                temp.setSesso(rs.getString("sesso"));
////                temp.setNascita_codice_catastale(rs.getString("nascita_codice_catastale"));
////                temp.setRecapito_telefonico(rs.getString("recapito_telefonico"));
////                temp.setEmail(rs.getString("email"));
////                temp.setResidenza_codice_catastale(rs.getString("residenza_codice_catastale"));
////                temp.setResidenza_indirizzo(rs.getString("residenza_indirizzo"));
////                temp.setDomicilio_codice_catastale(rs.getString("domicilio_codice_catastale"));
////                temp.setDomicilio_indirizzo(rs.getString("domicilio_indirizzo"));
////                temp.setNascita_data(new Date(rs.getDate("nascita_data").getTime()));
////                temp.setDt_mod_anagrafica(new Date(rs.getDate("dt_mod_anagrafica").getTime()));
//                out.add(temp);
//            }
//            return out;
//        } catch (SQLException ex) {
//            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
//            Action.insertTracking("", "getTutorEnte" + ex.getMessage());
//        }
//        return null;
//    }
    public boolean ChiudiBando(String id) {
        try {
            String sql = "UPDATE bando SET stato='C' WHERE idbando=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "ChiudiBando" + ex.getMessage());
        }
        return false;
    }

    public int insertPrgFormativo(String titolo, String ore_tot, String ore_mensili, String data_inizio, String data_fine, String tutor, String lavoratore, String carta_identità, String scadenza, String ente, String convenzione) {
        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO progetto_formativo SET ititolo=?, ore_tot=?, ore_mensili=?, data_inizio=?, data_fine=?, tutor=?, lavoratore=?, carta_identità=?, scadenza=?, ente=?, convenzione=?";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, titolo);
            ps.setString(2, ore_tot);
            ps.setString(3, ore_mensili);
            ps.setString(4, data_inizio);
            ps.setString(5, data_fine);
            ps.setString(6, tutor);
            ps.setString(7, lavoratore);
            ps.setString(8, carta_identità);
            ps.setString(9, scadenza);
            ps.setString(10, ente);
            ps.setString(11, convenzione);

            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "insertPrgFormativo " + ex.getMessage());
        }

        return 0;
    }

    public ArrayList<String[]> getMotivi() {
        try {
            ArrayList<String[]> out = new ArrayList<>();
            String sql = "SELECT * FROM tipo_rigetto";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] tmp = {rs.getString("idmotivazione"), rs.getString("motivazione")};
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getMotivi " + ex.getMessage());
        }
        return null;
    }

    public ArrayList<Politica> getPolitiche_I(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(politica);
            pm.add(cfente);
            pm.add(stato);

            String sql = "SELECT p.idpolitica,l.nome, l.cognome,p.dataavvio, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_ragazzo, p.doc_tutor, p.doc_m5, p.codsil"
                    + " FROM politica as p, Lavoratore as l "
                    + "WHERE p.codazioneformcal=? and p.cf_ente=? "
                    + "and p.lavoratore=l.cdnlavoratore and stato=?  ";
            if (politica.equals("B03") && !stato.equals("I")) {
                sql = "SELECT p.idpolitica,l.nome, l.cognome,p.dataavvio, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_ragazzo, p.doc_tutor, p.doc_m5, p.codsil, c.file"
                        + " FROM politica as p, Lavoratore as l, contratti as c "
                        + "WHERE p.codazioneformcal=? and p.cf_ente=? "
                        + "and c.idcontratti=p.idpolitica and p.lavoratore=l.cdnlavoratore and stato=?  ";
            }

            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            if (nome.equals("") && cognome.equals("") && cf.equals("") && from.equals("") && to.equals("")) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setDoc_m5(rs.getString("doc_m5"));
                tmp.setDoc_ragazzo(rs.getString("doc_tutor"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setSil(rs.getString("codsil"));
                if (politica.equals("B03") && !stato.equals("I")) {
                    Contratto c = new Contratto();
                    c.setFile(rs.getString("file"));
                    tmp.setContratto(c);
                }

                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getPolitiche_I " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Rimborso> getListRimborsi(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);

            String sql = "SELECT r.* FROM rimborso_politica as r, tutor as t, ente_promotore as e WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }

            sql += " ORDER BY data_up ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setRimborso(rs.getString("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsi " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public Map<String, String[]> get_ADInfos() {
        Map<String, String[]> result = new HashMap<>();
        try {
            String sql = "SELECT rimborso_politica.idrimborso,tutor.nome,tutor.cognome,ente_promotore.ragionesociale,rimborso_politica.doc_ad_au,ente_promotore.idente_promotore FROM rimborso_politica,tutor,ente_promotore WHERE tutor.idtutor = rimborso_politica.ad_au AND tutor.ente_promotore = ente_promotore.idente_promotore;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("ente_promotore.ragionesociale"), rs.getString("tutor.cognome") + " " + rs.getString("tutor.nome"), rs.getString("rimborso_politica.doc_ad_au"), rs.getString("ente_promotore.idente_promotore")};
                result.put(rs.getString("rimborso_politica.idrimborso"), temp);
            }
            return result;
        } catch (SQLException ex) {
            Action.insertTracking("", "get_ADInfos " + ex.getMessage());
            return null;
        }
    }

    public Map<String, String[]> get_ADInfosProgettoFormativo() {
        Map<String, String[]> result = new HashMap<>();
        try {
            String sql = "SELECT rimborso_prgformativo.id_rimborso_prg,tutor.nome,tutor.cognome,ente_promotore.ragionesociale,rimborso_prgformativo.doc_ad_au,ente_promotore.idente_promotore "
                    + " FROM rimborso_prgformativo,tutor,ente_promotore "
                    + " WHERE tutor.idtutor = rimborso_prgformativo.ad_au AND tutor.ente_promotore = ente_promotore.idente_promotore AND ruolo = 2";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("ente_promotore.ragionesociale"), rs.getString("tutor.cognome") + " " + rs.getString("tutor.nome"), rs.getString("rimborso_prgformativo.doc_ad_au"), rs.getString("ente_promotore.idente_promotore")};
                result.put(rs.getString("rimborso_prgformativo.id_rimborso_prg"), temp);
            }
            return result;
        } catch (SQLException ex) {
            Action.insertTracking("", "get_ADInfosProgettoFormativo " + ex.getMessage());
            return null;
        }
    }

    public Map<String, String[]> get_ADInfosProgettoFormativo_DT() {
        Map<String, String[]> result = new HashMap<>();
        try {
            String sql = "SELECT rimborso_prgformativo_dt.id_rimborso_prg_dt,tutor.nome,tutor.cognome,ente_promotore.ragionesociale,rimborso_prgformativo_dt.doc_ad_au,ente_promotore.idente_promotore "
                    + " FROM rimborso_prgformativo_dt,tutor,ente_promotore "
                    + " WHERE tutor.idtutor = rimborso_prgformativo_dt.ad_au AND tutor.ente_promotore = ente_promotore.idente_promotore AND ruolo = 2";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("ente_promotore.ragionesociale"), rs.getString("tutor.cognome") + " " + rs.getString("tutor.nome"), rs.getString("rimborso_prgformativo_dt.doc_ad_au"), rs.getString("ente_promotore.idente_promotore")};
                result.put(rs.getString("rimborso_prgformativo_dt.id_rimborso_prg_dt"), temp);
            }
            return result;
        } catch (SQLException ex) {
            Action.insertTracking("", "get_ADInfosProgettoFormativo_DT " + ex.getMessage());
            return null;
        }
    }

    public ArrayList<Politica> getListPolitiche(String domanda_rimborso) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica,l.nome,l.cognome,p.dataavvio,p.datafine,l.codice_fiscale,p.doc_ragazzo,p.tutor,p.doc_m5,p.domanda_rimborso,p.codazioneformcal,p.durataeffettiva,p.profiling, p.new_datafine,p.lavoratore,p.cf_ente, p.rimborso "
                    + "FROM politica as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore "
                    + "AND p.domanda_rimborso=? AND p.stato='A'  ;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                tmp.setTutor(rs.getInt("tutor"));
                tmp.setDoc_m5(rs.getString("doc_m5"));
                tmp.setRimborso(rs.getInt("domanda_rimborso"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setNew_datafine(rs.getString("new_datafine"));
                tmp.setIdlav(rs.getString("lavoratore"));
                tmp.setIdente(rs.getString("cf_ente"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListPolitiche " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public String[] getListDocsTirocinanteM5(String id) {
        try {
            //String[] out = new ArrayList<>();
            String sql = "SELECT p.idprogetto_formativo,l.nome, l.cognome,l.codice_fiscale,p.doc_lavoratore,p.doc_tutor, p.doc_competenze, p.file "
                    + "FROM progetto_formativo as p, Lavoratore as l, tutor as t "
                    + "WHERE p.lavoratore=l.cdnlavoratore  AND t.idtutor = p.tutor "
                    + "AND p.idprogetto_formativo = ? AND p.stato='A'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] tmp = {rs.getString("p.idprogetto_formativo"), rs.getString("l.nome"), rs.getString("l.cognome"), rs.getString("l.codice_fiscale"), rs.getString("p.doc_lavoratore"), rs.getString("p.doc_tutor"), rs.getString("p.doc_competenze"), rs.getString("p.file")};
                return tmp;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getListDocsTirocinanteM5 " + ex.getMessage());
        }
        return null;
    }

    public String[] getListDocsTirocinanteDT(String id) {
        try {
            //String[] out = new ArrayList<>();
            String sql = "SELECT p.idprogetto_formativo_dt,l.nome, l.cognome,l.codice_fiscale,p.doc_lavoratore,p.doc_tutor, p.doc_competenze, p.doc_m5, p.file "
                    + "FROM progetto_formativo_dt as p, Lavoratore as l, tutor as t "
                    + "WHERE p.lavoratore=l.cdnlavoratore  AND t.idtutor = p.tutor "
                    + "AND p.idprogetto_formativo_dt = ? AND p.stato='A'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] tmp = {rs.getString("p.idprogetto_formativo_dt"), rs.getString("l.nome"), rs.getString("l.cognome"), rs.getString("l.codice_fiscale"), rs.getString("p.doc_lavoratore"), rs.getString("p.doc_tutor"), rs.getString("p.doc_competenze"), rs.getString("p.doc_m5"), rs.getString("p.file")};
                return tmp;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getListDocsTirocinanteDT " + ex.getMessage());
        }
        return null;
    }

    public String[] getListDocsTirocinante(String politica) {
        try {
            //String[] out = new ArrayList<>();
            String sql = "SELECT p.idpolitica,l.nome, l.cognome,l.codice_fiscale,p.doc_ragazzo,p.doc_tutor,p.doc_m5 "
                    + "FROM politica as p, Lavoratore as l, tutor as t "
                    + "WHERE p.lavoratore=l.cdnlavoratore AND p.idpolitica = ? AND p.stato='A' AND t.idtutor = p.tutor";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, politica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] tmp = {rs.getString("p.idpolitica"), rs.getString("l.nome"), rs.getString("l.cognome"), rs.getString("l.codice_fiscale"), rs.getString("p.doc_ragazzo"), rs.getString("p.doc_tutor"), rs.getString("p.doc_m5")};
                return tmp;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getListDocsTirocinante " + ex.getMessage());
        }
        return null;
    }

    public String[] getListDocsTirocinante_DOTE(String politica) {
        try {
            //String[] out = new ArrayList<>();
            String sql = "SELECT p.idpolitica_dt,l.nome, l.cognome,l.codice_fiscale,p.doc_ragazzo,p.doc_tutor,p.doc_m5 "
                    + "FROM politica_dt as p, Lavoratore as l, tutor as t "
                    + "WHERE p.lavoratore=l.cdnlavoratore AND p.idpolitica_dt = ? AND p.stato='A' AND t.idtutor = p.tutor";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, politica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] tmp = {rs.getString("p.idpolitica_dt"), rs.getString("l.nome"), rs.getString("l.cognome"), rs.getString("l.codice_fiscale"), rs.getString("p.doc_ragazzo"), rs.getString("p.doc_tutor"), rs.getString("p.doc_m5")};
                return tmp;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getListDocsTirocinante_DOTE " + ex.getMessage());
        }
        return null;
    }

    public String[] getListDocsTirocinanteM3(String politica) {
        try {
            //String[] out = new ArrayList<>();
            String sql = "SELECT p.idpolitica,l.nome, l.cognome,l.codice_fiscale,p.doc_ragazzo,p.doc_m5 "
                    + "FROM politica as p, Lavoratore as l W"
                    + "HERE p.lavoratore=l.cdnlavoratore AND p.idpolitica = ? AND p.stato='A' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, politica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] tmp = {rs.getString("p.idpolitica"), rs.getString("l.nome"), rs.getString("l.cognome"), rs.getString("l.codice_fiscale"), rs.getString("p.doc_ragazzo"), rs.getString("p.doc_m5")};
                return tmp;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getListDocsTirocinante " + ex.getMessage());
        }
        return null;
    }

    public boolean uploadDoc(String id, String tutor, String m5, String file, String doc_tutor) {
        try {
            String sql = "UPDATE politica SET tutor=?, doc_m5=?, doc_ragazzo=?, doc_tutor=?, stato='S' WHERE idpolitica=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, tutor);
            ps.setString(2, m5);
            ps.setString(3, file);
            ps.setString(4, doc_tutor);
            ps.setString(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "uploadDoc " + ex.getMessage());
        }
        return false;
    }

    public boolean anomaliaRimborso(String id, String motivo) {
        try {
            String sql = "UPDATE politica SET stato='E',domanda_rimborso=NULL,motivo = ? WHERE idpolitica = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "anomaliaRimborso" + ex.getMessage());
        }
        return false;
    }

    public boolean anomaliaRimborsoM5(String id, String motivo) {
        try {
            String sql = "UPDATE progetto_formativo SET stato='E',rimborso_prg=NULL,motivo = ? WHERE idprogetto_formativo = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "anomaliaRimborsoM5" + ex.getMessage());
        }
        return false;
    }

    public boolean anomaliaRimborsoDT(String id, String motivo) {
        try {
            String sql = "UPDATE progetto_formativo_dt SET stato='E',rimborso_prg=NULL,motivo = ? WHERE idprogetto_formativo_dt = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "anomaliaRimborsoDT" + ex.getMessage());
        }
        return false;
    }

    public boolean scartaRimborso(String id) {
        try {
            String sql = "UPDATE politica SET stato='K',domanda_rimborso=NULL WHERE idpolitica=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "scartaRimborso" + ex.getMessage());
        }
        return false;
    }

    public boolean scartaRimborso1(String id, String motivo) {
        try {
            String sql = "UPDATE politica SET stato='K',domanda_rimborso=NULL, motivo=? WHERE idpolitica=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "scartaRimborso" + ex.getMessage());
        }
        return false;
    }

    public boolean scartaRimborsoM5(String id, String motivo) {
        try {
            String sql = "UPDATE progetto_formativo SET stato='K',rimborso_prg=NULL, motivo=? WHERE idprogetto_formativo=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "scartaRimborsoM5" + ex.getMessage());
        }
        return false;
    }

    public boolean scartaRimborsoDT(String id, String motivo) {
        try {
            String sql = "UPDATE progetto_formativo_dt SET stato='K',rimborso_prg=NULL, motivo=? WHERE idprogetto_formativo_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "scartaRimborsoDT" + ex.getMessage());
        }
        return false;
    }

    public boolean rigettaRimborsi(String id, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica SET stato='K',motivo=? WHERE idrimborso=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            ps.execute();

            String sql1 = "UPDATE politica SET stato='E',motivo=?,domanda_rimborso=NULL WHERE domanda_rimborso=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "rigettaRimborsi:" + e.getMessage());
        }
        return false;
    }

    public boolean rigettaRimborsiDT(String id, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_prgformativo_dt SET stato='K',motivo=? WHERE id_rimborso_prg_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            ps.execute();

            String sql1 = "UPDATE progetto_formativo_dt SET stato='E',motivo=?,rimborso_prg=NULL WHERE rimborso_prg=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "rigettaRimborsiDT:" + e.getMessage());
        }
        return false;
    }

    public boolean rigettaRimborsiM5(String id, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_prgformativo SET stato='K',motivo=? WHERE id_rimborso_prg=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            ps.execute();

            String sql1 = "UPDATE progetto_formativo SET stato='E',motivo=?,rimborso_prg=NULL WHERE rimborso_prg=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "rigettaRimborsiM5:" + e.getMessage());
        }
        return false;
    }

    public boolean accettaRimborsi(String id) {
        try {
            String sql = "UPDATE rimborso_politica SET stato='R' WHERE idrimborso=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "accettaRimborsi" + ex.getMessage());
        }
        return false;
    }

    public boolean accettaRimborsiDT(String id) {
        try {
            String sql = "UPDATE rimborso_prgformativo_dt SET stato='R' WHERE id_rimborso_prg_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "accettaRimborsiDT" + ex.getMessage());
        }
        return false;
    }

    public boolean accettaRimborsiM5(String id) {
        try {
            String sql = "UPDATE rimborso_prgformativo SET stato='R' WHERE id_rimborso_prg=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "accettaRimborsiM5" + ex.getMessage());
        }
        return false;
    }

    public int insertRimborso(String tutor, String politica, String doc_ad_au) {

        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO rimborso_politica SET ad_au=?, data_up= CURDATE(), politica=?, doc_ad_au=?";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tutor);
            ps.setString(2, politica);
            ps.setString(3, doc_ad_au);

            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "insertRimborso" + ex.getMessage());
        }

        return 0;
    }

    public boolean associaPoliticheRimborso(String data, int id) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE politica SET domanda_rimborso=?, stato='A' WHERE idpolitica=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idpolitica=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            ps.setInt(1, id);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 2, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "associaPoliticheRimborso " + ex.getMessage());
        }
        return false;
    }

    public Politica getPoliticaByID(String id) {
        Politica out = new Politica();
        try {
            String sql = "SELECT tutor, doc_ragazzo, doc_m5, dataavvio, datafine, new_datafine FROM politica WHERE idpolitica = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setId(Integer.parseInt(id));
                out.setDoc_m5(rs.getString("doc_m5"));
                out.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                out.setDataavvio(rs.getString("dataavvio"));
                out.setDatafine(rs.getString("datafine"));
                out.setNew_datafine(rs.getString("new_datafine"));
                out.setTutor(rs.getInt("tutor"));
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getPoliticaByID " + ex.getMessage());
        }
        return out;
    }

    public ArrayList<Rimborso> getListRimborsiEnte(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        ArrayList<Rimborso> out = new ArrayList<>();
        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);
            pm.add(String.valueOf(idente));

            String sql = "";
            if (!stato.equals("P")) {
                sql = "SELECT r.idrimborso, r.data_up, r.motivo, r.stato, r.ad_au, r.timestamp, r.politica, r.protocollo, r.documento, r.tot_erogato, r.check_list "
                        + "FROM rimborso_politica as r, tutor as t, ente_promotore as e "
                        + "WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            } else if (stato.equals("P")) {
                sql = "SELECT r.idrimborso, r.data_up, r.motivo, r.stato, r.ad_au, r.timestamp, r.politica, r.protocollo, r.documento, r.tot_erogato, r.check_list, d.path_decreto "
                        + "FROM rimborso_politica as r, tutor as t, ente_promotore as e, decreto as d "
                        + "WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore AND d.fk_idrimborso=r.idrimborso AND idente_promotore=? ";
            }
            if (!protocollo.equals("")) {
                sql += " AND r.protocollo=? ";
                pm.add(protocollo);
                flag = true;
            }
            if (!from_up.equals("") && !to_up.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_up);
                pm.add(to_up);
                flag = true;
            }
            if (!from_mod.equals("") && !to_mod.equals("")) {
                sql += " AND r.timestamp BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_mod);
                pm.add(to_mod);
                flag = true;
            }
            sql += " ORDER BY timestamp DESC ";

            if (!flag) {
                sql += "LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                if (stato.equals("P")) {
                    tmp.setPath_decreto(rs.getString("path_decreto"));
                }
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsiEnte " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean uploadProtocollo(String id, String protocollo, String path) {
        try {
            String sql = "UPDATE rimborso_politica SET protocollo=?, documento=?,stato='N' WHERE idrimborso=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, protocollo);
            ps.setString(2, path);
            ps.setString(3, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadProtocollo:" + e.getMessage());
        }
        return false;
    }

    public boolean uploadProtocolloM5(String id, String protocollo, String path) {
        try {
            String sql = "UPDATE rimborso_prgformativo SET protocollo=?, documento=?,stato='N' WHERE id_rimborso_prg=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, protocollo);
            ps.setString(2, path);
            ps.setString(3, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadProtocolloM5:" + e.getMessage());
        }
        return false;
    }

    public boolean uploadProtocolloDT(String id, String protocollo, String path, String pol) {
        try {
            String sql = "UPDATE rimborso_prgformativo_dt SET protocollo=?, documento=?,stato='N' WHERE id_rimborso_prg_dt=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, protocollo);
            ps.setString(2, path);
            ps.setString(3, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadProtocollo" + pol + ":" + e.getMessage());
        }
        return false;
    }

    public ArrayList<Politica> getRimborsiPolitiche(String politica, String idrimborso) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica,p.dataavvio, l.nome, l.cognome, l.codice_fiscale, t.nome as t_nome, t.cognome as t_cognome, p.durataeffettiva "
                    + "FROM politica as p, tutor as t, Lavoratore as l "
                    + "WHERE p.domanda_rimborso=? and p.codazioneformcal=? and p.tutor=t.idtutor "
                    + "and p.lavoratore=l.cdnlavoratore and p.stato='A' ";
            if (politica.equals("B03")) {
                sql = "SELECT p.idpolitica,p.dataavvio, l.nome, l.cognome, l.codice_fiscale, p.durataeffettiva "
                        + "FROM politica as p, Lavoratore as l "
                        + "WHERE p.domanda_rimborso=? and p.codazioneformcal=? "
                        + "and p.lavoratore=l.cdnlavoratore AND p.stato='A'  ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, politica);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                if (!politica.equals("B03")) {
                    tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                }
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiPolitiche " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public boolean Rev_liquidaRimborso(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        try {
            String sql = "UPDATE rimborso_politica SET motivo=?, check_list=?,tot_erogato=?,rimborso=?,stato='R2' WHERE idrimborso=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, descrizione);
            ps.setString(2, path);
            ps.setString(3, totale);
            ps.setString(4, ctrlrimborso);
            ps.setString(5, idrimborso);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Rev_liquidaRimborso:" + e.getMessage());
        }
        return false;
    }

    public boolean Rev_liquidaRimborsoM5(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        try {
            String sql = "UPDATE rimborso_prgformativo SET motivo=?, check_list=?,tot_erogato=?,rimborso=?,stato='R2' WHERE id_rimborso_prg=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, descrizione);
            ps.setString(2, path);
            ps.setString(3, totale);
            ps.setString(4, ctrlrimborso);
            ps.setString(5, idrimborso);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Rev_liquidaRimborsoM5:" + e.getMessage());
        }
        return false;
    }

    public boolean Rev_liquidaRimborsoDT(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        try {
            String sql = "UPDATE rimborso_prgformativo_dt SET motivo=?, check_list=?,tot_erogato=?,rimborso=?,stato='R2' WHERE id_rimborso_prg_dt=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, descrizione);
            ps.setString(2, path);
            ps.setString(3, totale);
            ps.setString(4, ctrlrimborso);
            ps.setString(5, idrimborso);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Rev_liquidaRimborsoDT:" + e.getMessage());
        }
        return false;
    }

    public boolean Rev_rigettaRimborso(String id, String motivo) {
        try {
            String sql = "UPDATE rimborso_politica SET stato='E2',motivo=? WHERE idrimborso=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_rigettaRimborso" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_rigettaRimborsoM5(String id, String motivo) {
        try {
            String sql = "UPDATE rimborso_prgformativo SET stato='E2',motivo=? WHERE id_rimborso_prg =?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_rigettaRimborso M5" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_rigettaRimborsoDT(String id, String motivo) {
        try {
            String sql = "UPDATE rimborso_prgformativo_dt SET stato='E2',motivo=? WHERE id_rimborso_prg_dt =?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_rigettaRimborsoDT" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_scartaTuttoRimborso(String id, String motivo, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica SET stato='K',motivo=?,check_list=? WHERE idrimborso=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, path);
            ps.setString(3, id);
            ps.execute();

            String sql1 = "UPDATE politica SET stato='E',domanda_rimborso=NULL,motivo = ? WHERE domanda_rimborso = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "scartaTuttoRimborso:" + e.getMessage());
        }
        return false;
    }

    public boolean Rev_scartaTuttoRimborsoM5(String id, String motivo, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_prgformativo SET stato='K',motivo=?,check_list=? WHERE id_rimborso_prg=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, path);
            ps.setString(3, id);
            ps.execute();

            String sql1 = "UPDATE progetto_formativo SET stato='E',rimborso_prg=NULL,motivo = ? WHERE rimborso_prg = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "scartaTuttoRimborsoM5:" + e.getMessage());
        }
        return false;
    }

    public boolean Rev_scartaTuttoRimborsoDT(String id, String motivo, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_prgformativo_dt SET stato='K',motivo=?,check_list=? WHERE id_rimborso_prg_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, path);
            ps.setString(3, id);
            ps.execute();

            String sql1 = "UPDATE progetto_formativo_dt SET stato='E',rimborso_prg=NULL,motivo = ? WHERE rimborso_prg = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Rev_scartaTuttoRimborsoDT:" + e.getMessage());
        }
        return false;
    }

    public boolean uploadDecreto(String idrimborso, String repertorio, String datar, String decreto, String datad, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO decreto (fk_idrimborso,num_assunzione,data_assunzione,num_decreto,data_decreto,path_decreto) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, repertorio);
            ps.setString(3, datar);
            ps.setString(4, decreto);
            ps.setString(5, datad);
            ps.setString(6, path);
            ps.execute();

            String sql1 = "UPDATE rimborso_politica SET stato='P' WHERE idrimborso=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, idrimborso);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadDecreto:" + e.getMessage());
        }
        return false;
    }

    public boolean uploadDecretoM5(String idrimborso, String repertorio, String datar, String decreto, String datad, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO decreto_prgformativo (fk_idrimborsopf,num_assunzione,data_assunzione,num_decreto,data_decreto,path_decreto) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, repertorio);
            ps.setString(3, datar);
            ps.setString(4, decreto);
            ps.setString(5, datad);
            ps.setString(6, path);
            ps.execute();

            String sql1 = "UPDATE rimborso_prgformativo SET stato='P' WHERE id_rimborso_prg=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, idrimborso);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadDecretoM5:" + e.getMessage());
        }
        return false;
    }

    public boolean uploadDecretoDT(String idrimborso, String repertorio, String datar, String decreto, String datad, String path, String pol) {
        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO decreto_prgformativo_dt (fk_idrimborsopf_dt,num_assunzione,data_assunzione,num_decreto,data_decreto,path_decreto) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, repertorio);
            ps.setString(3, datar);
            ps.setString(4, decreto);
            ps.setString(5, datad);
            ps.setString(6, path);
            ps.execute();

            String sql1 = "UPDATE rimborso_prgformativo_dt SET stato='P' WHERE id_rimborso_prg_dt=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, idrimborso);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadDecretoDT" + pol + ":" + e.getMessage());
        }
        return false;
    }

    public int numPoliticheRimborso(String id, String idpolitica) {
        int n = 0;
        try {
            String sql = "SELECT count(*) as n FROM politica WHERE domanda_rimborso = ? AND stato = 'A' AND idpolitica <> ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, idpolitica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                n = rs.getInt("n");
            }
            return n;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "numPoliticheRimborso" + ex.getMessage());
        }
        return n;
    }

    public int numPFRimborso(String id, String idpf) {
        int n = 0;
        try {
            String sql = "SELECT count(*) as n FROM progetto_formativo WHERE rimborso_prg = ? AND stato = 'A' AND idprogetto_formativo <> ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, idpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                n = rs.getInt("n");
            }
            return n;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "numPFRimborso" + ex.getMessage());
        }
        return n;
    }

    public int numPFRimborsoDT(String id, String idpf) {
        int n = 0;
        try {
            String sql = "SELECT count(*) as n FROM progetto_formativo_dt WHERE rimborso_prg = ? AND stato = 'A' AND idprogetto_formativo_dt <> ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, idpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                n = rs.getInt("n");
            }
            return n;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "numPFRimborsoDT" + ex.getMessage());
        }
        return n;
    }

    public boolean rigettaRimborsoCondition(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica SET stato='K',motivo=? WHERE idrimborso=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            ps.execute();

            String sql1 = "UPDATE politica SET stato='E',domanda_rimborso=NULL,motivo = ? WHERE idpolitica = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);

            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "rigettrigettaRimborsoConditionaRimborsi:" + e.getMessage());
        }
        return false;

    }

    public ArrayList<String[]> getList_Enti() {
        try {
            ArrayList<String[]> out = new ArrayList<>();
            String sql = "SELECT idente_promotore,ragionesociale FROM ente_promotore";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("idente_promotore"), rs.getString("ragionesociale")};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getList_Enti" + ex.getMessage());
        }
        return null;
    }

    public ArrayList<String[]> get_ComuniResidenza() {
        try {
            ArrayList<String[]> out = new ArrayList<>();
            String sql = "SELECT descrizione,codcom,codprovincia FROM de_comune WHERE codcom NOT LIKE 'Z%' AND codcom NOT LIKE 'N%' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("codcom"), rs.getString("descrizione"), rs.getString("codprovincia")};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "get_ComuniResidenza" + ex.getMessage());
        }
        return null;
    }

    public ArrayList<String[]> get_ProvinciaResidenza() {
        try {
            ArrayList<String[]> out = new ArrayList<>();
            String sql = "SELECT descrizione,codprovincia FROM de_provincia WHERE codprovincia < 150 AND codprovincia > 0 ORDER BY codregione, descrizione ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("codprovincia"), rs.getString("descrizione")};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "get_ProvinciaResidenza" + ex.getMessage());
        }
        return null;
    }

    public boolean AttivaBando(String id) {
        try {
            String sql = "UPDATE bando SET stato='A' WHERE idbando=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "AttivoBando " + ex.getMessage());
        }
        return false;
    }

    public boolean SospendiBando(String id) {
        try {
            String sql = "UPDATE bando SET stato='S' WHERE idbando=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "SospendiBando " + ex.getMessage());
        }
        return false;
    }

    public void AttivazioniBando(String id) {
        try {
            String sql = "INSERT INTO attivazioni SET idbando = ?, data_attivazione = CURDATE()";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "AttivazioniBando " + ex.getMessage());
        }
    }

    public void SospensioniBando(String idb) {
        try {
            String sql = "UPDATE attivazioni SET data_chiusura = SUBDATE(CURDATE(),1) WHERE idbando = ? ORDER BY id DESC";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idb);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "AttivazioniBando " + ex.getMessage());
        }
    }

    public String getBandoAttivo(String tipo) {//fatto
        String c = "";
        try {
            String sql = "SELECT titolo,decreto FROM bando WHERE tipo_bando = ? AND stato = 'A';";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                c = rs.getString("titolo") + " - " + rs.getString("decreto");
                return c;
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getBandoAttivo " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return c;
    }

    public boolean insertContratto(String inizio, String fine, String file, String indeterminato, String idpolitica) {
        try {
            String sql = "INSERT INTO contratto SET idcontratti=?, data_inizio=?, data_fine=?, file=?, indeterminato=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idpolitica);
            ps.setString(2, inizio);
            ps.setString(3, fine);
            ps.setString(3, file);
            ps.setString(3, indeterminato);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Action.insertTracking("service", "insertContratto " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public Contratto getContrattoById(int id) {
        Contratto out = new Contratto();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String sql = "SELECT * FROM contratti WHERE idcontratti=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("data_fine") != null) {
                    out.setData_fine(sdf2.format(sdf1.parse(rs.getString("data_fine"))));
                } else {
                    out.setData_fine(rs.getString("data_fine"));
                }
                out.setData_inizio(sdf2.format(sdf1.parse(rs.getString("data_inizio"))));
                out.setFile(rs.getString("file"));
                out.setIndeterminato(rs.getString("indeterminato"));
                return out;
            }
            return null;
        } catch (SQLException ex) {
            Action.insertTracking("service", "getContrattoById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean uploadDocContratto(String id, String tutor, String m5, String file, String inizio, String fine, String contratto, String indeterminato, String tipo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE politica SET tutor=?, doc_m5=?, doc_ragazzo=?, stato='S' WHERE idpolitica=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, tutor);
            ps.setString(2, m5);
            ps.setString(3, file);
            ps.setString(4, id);
            ps.execute();

            if (tipo.equals("new")) {
                sql = "INSERT INTO contratti SET idcontratti=?, data_inizio=?, data_fine=?, file=?, indeterminato=?";
                PreparedStatement ps2 = this.c.prepareStatement(sql);
                ps2.setString(1, id);
                ps2.setString(2, inizio);
                ps2.setString(3, fine);
                ps2.setString(4, contratto);
                ps2.setString(5, indeterminato);
                ps2.executeUpdate();
            } else if (tipo.equals("mod")) {
                sql = "UPDATE contratti SET data_inizio=?, data_fine=?, file=?, indeterminato=? WHERE idcontratti=?";
                PreparedStatement ps2 = this.c.prepareStatement(sql);
                ps2.setString(1, inizio);
                ps2.setString(2, fine);
                ps2.setString(3, contratto);
                ps2.setString(4, indeterminato);
                ps2.setString(5, id);
                ps2.executeUpdate();
            }
            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "uploadDoc" + ex.getMessage());
        }
        return false;
    }

    public ArrayList<Politica> getScartiPolitiche(String idrimborso, String politica) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica,p.dataavvio,p.durataeffettiva, l.nome, l.cognome, l.codice_fiscale, t.nome as t_nome, t.cognome as t_cognome, s.motivo "
                    + "FROM scarti_politiche as s , politica as p, tutor as t, Lavoratore as l "
                    + "WHERE id_politica=idpolitica and id_rimborso=? and p.tutor=t.idtutor "
                    + "and p.lavoratore=l.cdnlavoratore;";
            if (politica.equals("B03")) {
                sql = "SELECT p.idpolitica,p.dataavvio,p.durataeffettiva, l.nome, l.cognome, l.codice_fiscale, s.motivo "
                        + "FROM scarti_politiche as s , politica as p, Lavoratore as l "
                        + "WHERE id_politica=idpolitica and id_rimborso=? "
                        + "and p.lavoratore=l.cdnlavoratore;";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                if (!politica.equals("B03")) {
                    tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                }
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiPolitiche " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public boolean AnomalieRimborsoCondition(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE idrimborso=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE politica SET stato='E',domanda_rimborso=NULL,motivo = ? WHERE idpolitica = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "AnomalieRimborsoCondition:" + e.getMessage());
        }
        return false;

    }

    public boolean AnomalieRimborsoConditionM5(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_prgformativo SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE id_rimborso_prg = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE progetto_formativo SET stato='E',rimborso_prg=NULL,motivo = ? WHERE idprogetto_formativo = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "AnomalieRimborsoConditionM5:" + e.getMessage());
        }
        return false;

    }

    public boolean AnomalieRimborsoConditionDT(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_prgformativo_dt SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE id_rimborso_prg_dt = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE progetto_formativo_dt SET stato='E',rimborso_prg=NULL,motivo = ? WHERE idprogetto_formativo_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "AnomalieRimborsoConditionDT:" + e.getMessage());
        }
        return false;

    }

    public boolean scartoRimborsoCondition(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE idrimborso=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE politica SET stato='K',domanda_rimborso=NULL,motivo = ? WHERE idpolitica = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "scartoRimborsoCondition:" + e.getMessage());
        }
        return false;

    }

    public boolean scartoRimborsoConditionM5(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_prgformativo SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE id_rimborso_prg=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE progetto_formativo SET stato='K',rimborso_prg=NULL,motivo = ? WHERE idprogetto_formativo = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "scartoRimborsoConditionM5:" + e.getMessage());
        }
        return false;

    }

    public boolean scartoRimborsoConditionDT(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_prgformativo_dt SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE id_rimborso_prg_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE progetto_formativo_dt SET stato='K',rimborso_prg=NULL,motivo = ? WHERE idprogetto_formativo_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "scartoRimborsoConditionDT:" + e.getMessage());
        }
        return false;

    }

    public boolean insertScartiPolitiche(String id_rimborso, String id_politica, String motivo) {
        try {
            String sql = "INSERT INTO scarti_politiche SET id_rimborso=?, id_politica=?, motivo=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id_rimborso);
            ps.setString(2, id_politica);
            ps.setString(3, motivo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Action.insertTracking("service", "insertScartiPolitiche " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public boolean insertScartiPF(String id_rimborso, String idpf, String motivo) {
        try {
            String sql = "INSERT INTO scarti_progformativi SET id_rimborso=?, id_pf=?, motivo=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id_rimborso);
            ps.setString(2, idpf);
            ps.setString(3, motivo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Action.insertTracking("service", "insertScartiPF " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public boolean insertScartiPF_DT(String id_rimborso, String idpf, String motivo) {
        try {
            String sql = "INSERT INTO scarti_progformativi_dt SET id_rimborso_dt=?, id_pf_dt=?, motivo=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id_rimborso);
            ps.setString(2, idpf);
            ps.setString(3, motivo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Action.insertTracking("service", "insertScartiPF_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public ArrayList<Politica> getListPolitichePDF(String ids) {
        ArrayList<Politica> out = new ArrayList<>();
        String[] s = ids.split(",");
        try {
            String sql = "";

            sql = "SELECT p.idpolitica,l.nome, l.cognome, p.dataavvio, p.datafine, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.profiling, p.new_datafine, p.rimborso "
                    + "FROM politica as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore  and (";

            for (int i = 1; i < s.length + 1; i++) {
                sql += " idpolitica=? OR";
            }
            sql = sql.substring(0, sql.length() - 3);
            sql += " )";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < s.length; i++) {

                ps.setString(i + 1, s[i]);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setNew_datafine(rs.getString("new_datafine"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getListPolitichePDF " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<PrgFormativo> getPrgFormativi(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        ArrayList<PrgFormativo> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(politica);
            pm.add(cfente);
            pm.add(stato);

            String sql = "SELECT p.idprogetto_formativo,l.nome, l.cognome,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_lavoratore, p.doc_tutor, p.doc_competenze, p.scadenza_doc, p.file, p.convenzione "
                    + "FROM progetto_formativo as p, Lavoratore as l "
                    + "WHERE p.codazioneformcal=? and p.cf_ente=? and p.lavoratore=l.cdnlavoratore and stato=? ";
            if (stato.equals("C")) {
                sql = "SELECT p.idprogetto_formativo,l.nome, l.cognome,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_lavoratore, p.doc_tutor, p.doc_competenze, p.scadenza_doc, p.file, p.convenzione "
                        + "FROM progetto_formativo as p, Lavoratore as l "
                        + "WHERE p.codazioneformcal=? and p.cf_ente=?  "
                        + "and p.lavoratore=l.cdnlavoratore and stato=?  AND (p.ore_effettuate/p.ore_tot)>=? ";
                pm.add(Action.getPath("soglia_prg"));
            }
            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            if (nome.equals("") && cognome.equals("") && cf.equals("") && from.equals("") && to.equals("")) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativo tmp = new PrgFormativo();
                tmp.setId(rs.getInt("idprogetto_formativo"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_competenze(rs.getString("doc_competenze"));
                tmp.setScadenza_doc(rs.getString("scadenza_doc"));
                tmp.setFile(rs.getString("file"));
                tmp.setConvenzione(rs.getInt("convenzione"));

                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("service", "getPrgFormativi " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public PrgFormativo getPrgFormativoById(String id) {
        PrgFormativo out = new PrgFormativo();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String sql = "SELECT tutor, doc_lavoratore, dataavvio, ore_tot, datafine, scadenza_doc, ore_mese, durata_mesi, doc_competenze, stato, file, convenzione, ore_effettuate, profiling "
                    + "FROM progetto_formativo "
                    + "WHERE idprogetto_formativo = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setId(Integer.parseInt(id));
                out.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                out.setTutor(rs.getInt("tutor"));
                out.setOre_tot(rs.getInt("ore_tot"));
                out.setOre_effettuate(rs.getInt("ore_effettuate"));
                out.setDataavvio(sdf.format(rs.getDate("dataavvio")));
                out.setDatafine(sdf.format(rs.getDate("datafine")));
                out.setStato(rs.getString("stato"));
                out.setProfiling(rs.getInt("profiling"));
                if (out.getTutor() != 0) {
                    out.setScadenza_doc(sdf.format(rs.getDate("scadenza_doc")));
                    out.setDurata_mesi(rs.getInt("durata_mesi"));
                    out.setDoc_competenze(rs.getString("doc_competenze"));
                    out.setFile(rs.getString("file"));
                    out.setConvenzione(rs.getInt("convenzione"));
                }
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getPoliticaByID " + ex.getMessage());
        }
        return out;
    }

    public boolean uploadDocPrg(String id, String tutor, String file, String doc_tutor, String scadenza, String ore_tot, int mesi, int ore_mese,
            String from, String to, String stato, String competenze, String convenzione, String file_prg) {
        try {
            String sql = "UPDATE progetto_formativo SET tutor=?, doc_tutor=?, doc_lavoratore=?, scadenza_doc=?, ore_tot=?, durata_mesi=?, ore_mese=?, dataavvio=?, datafine=?, "
                    + "stato=?, doc_competenze=?, dataup=curdate(), file=?, convenzione=? "
                    + " WHERE idprogetto_formativo=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, tutor);
            ps.setString(2, doc_tutor);
            ps.setString(3, file);
            ps.setString(4, scadenza);
            ps.setString(5, ore_tot);
            ps.setInt(6, mesi);
            ps.setInt(7, ore_mese);
            ps.setString(8, from);
            ps.setString(9, to);
            ps.setString(10, stato);
            ps.setString(11, competenze);
            ps.setString(12, file_prg);
            ps.setString(13, convenzione);
            ps.setString(14, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "uploadDocPrg " + ex.getMessage());
        }
        return false;
    }

    public int getRegisterNumber(String id) {
        try {
            String sql = "SELECT count(*) as c FROM registri WHERE progetto_formativo=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("c");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getRegisterNumber " + ex.getMessage());
        }
        return 0;
    }

    public String[] getMese(int id) {
        try {
            String sql = "SELECT * FROM mesi WHERE id=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] tmp = {rs.getString("id"), rs.getString("descrizione")};
                return tmp;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getMese " + ex.getMessage());
        }
        return null;
    }

    public ArrayList<Rimborso_PrgFormativo> getListRimborsi_PrgFormativo(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso_PrgFormativo> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);

            String sql = "SELECT r.* FROM rimborso_prgformativo as r, tutor as t, ente_promotore as e WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            sql += " ORDER BY data_up ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso_PrgFormativo tmp = new Rimborso_PrgFormativo();
                tmp.setIdrimborso_prg(rs.getString("id_rimborso_prg"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setRimborso(rs.getString("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsi " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public int insertRtegistro(String ore, String mese, String file, String quietanza, String doc_t, String doc_r, String idprg, String from, String to) {
        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO registri SET mese=?, ore=?, file=?, doc_quietanza=?, doc_tutor=?, doc_lavoratore=?, data_up=curdate(), progetto_formativo =?, datainizio=?, datafine=?";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, mese);
            ps.setString(2, ore);
            ps.setString(3, file);
            ps.setString(4, quietanza);
            ps.setString(5, doc_t);
            ps.setString(6, doc_r);
            ps.setString(7, idprg);
            ps.setString(8, from);
            ps.setString(9, to);

            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;
//            int x = ps.executeUpdate();
//            return x > 0;
        } catch (SQLException ex) {
            Action.insertTracking("", "insertRtegistro " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public Lavoratore getLavoratoreById(int id) {
        Lavoratore lav = new Lavoratore();
        try {
            String sql = "SELECT nome, cognome, cdnlavoratore,residenza_indirizzo, residenza_codice_catastale, codice_provincia, domicilio_codice_catastale, domicilio_indirizzo FROM Lavoratore WHERE cdnlavoratore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lav.setNome(rs.getString("nome"));
                lav.setCognome(rs.getString("cognome"));
                lav.setCdnlavoratore(rs.getInt("cdnlavoratore"));
                lav.setResidenza_indirizzo(rs.getString("residenza_indirizzo"));
                lav.setResidenza_codice_catastale(rs.getString("residenza_codice_catastale"));
                //lav.setResidenza_cap(rs.getInt("residenza_cap"));
                lav.setCodice_provincia(rs.getInt("codice_provincia"));
                lav.setDomicilio_codice_catastale(rs.getString("domicilio_codice_catastale"));
                lav.setDomicilio_indirizzo(rs.getString("domicilio_indirizzo"));
                return lav;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getLavoratoreById " + ex.getMessage());
        }
        return null;
    }

    public ArrayList<PrgFormativo> getListPrgFormativo(String domanda_rimborso) {
        ArrayList<PrgFormativo> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idprogetto_formativo,l.nome, l.cognome,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.codice_fiscale, p.motivo,"
                    + "p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_lavoratore, p.doc_tutor, p.durata_mesi,p.rimborso_prg, p.profiling "
                    + "FROM progetto_formativo as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore "
                    + "AND stato='A' AND p.rimborso_prg = ? ";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativo tmp = new PrgFormativo();
                tmp.setId(rs.getInt("idprogetto_formativo"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setDurata_mesi(rs.getInt("durata_mesi"));
                tmp.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setRimborso(rs.getInt("rimborso_prg"));
                tmp.setProfiling(rs.getInt("profiling"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListPrgFormativo " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<PrgFormativoDt> getListPrgFormativo_DT(String domanda_rimborso) {
        ArrayList<PrgFormativoDt> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idprogetto_formativo_dt,l.nome, l.cognome,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, "
                    + "l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_lavoratore, p.doc_tutor, p.durata_mesi,p.rimborso_prg, p.profiling "
                    + "FROM progetto_formativo_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore "
                    + "AND stato='A' AND p.rimborso_prg = ? ";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativoDt tmp = new PrgFormativoDt();
                tmp.setId(rs.getInt("idprogetto_formativo_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setDurata_mesi(rs.getInt("durata_mesi"));
                tmp.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setRimborso(rs.getInt("rimborso_prg"));
                tmp.setProfiling(rs.getInt("profiling"));
                //tmp.setDoc_m5(rs.getString("doc_m5"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListPrgFormativo " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public Registro getLastRegister(String idprg) {
        Registro reg = new Registro();
        try {
            String sql = "SELECT datainizio,datafine,mese  FROM registri WHERE progetto_formativo=? order by idregistri desc limit 1";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idprg);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reg.setDatainizio(rs.getString("datainizio"));
                reg.setDatafine(rs.getString("datafine"));
                reg.setMese(rs.getString("mese"));
                return reg;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getLastRegister " + ex.getMessage());
        }

        return null;
    }

    public int getTotlHourPrg(String idprg) {
        try {
            String sql = "SELECT SUM(ore) as s FROM registri WHERE progetto_formativo=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idprg);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("s");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getTotlHourPrg " + ex.getMessage());
        }
        return 0;
    }

    public boolean updatePrgCert(String idprg, String doc_cert) {
        try {
            String sql = "UPDATE progetto_formativo SET stato='C', doc_competenze=? WHERE idprogetto_formativo=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, doc_cert);
            ps.setString(2, idprg);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "updatePrgCert " + ex.getMessage());
        }
        return false;
    }

    public void deleteRegister(int id) {
        try {
            String sql = "DELETE FROM registri WHERE idregistri=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "deleteRegister " + ex.getMessage());
        }
    }

    public int insertRimborsoPrg(String tutor, String politica, String doc_ad_au) {

        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO rimborso_prgformativo SET ad_au=?, data_up= CURDATE(), politica=?, doc_ad_au=?";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tutor);
            ps.setString(2, politica);
            ps.setString(3, doc_ad_au);

            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "insertRimborsoprg " + ex.getMessage());
        }

        return 0;
    }

    public ArrayList<PrgFormativo> getScartiPF(String idrimborso) {
        ArrayList<PrgFormativo> out = new ArrayList<>();
        try {

            String sql = "SELECT p.idprogetto_formativo,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.nome, l.cognome, l.codice_fiscale,t.nome as t_nome, t.cognome as t_cognome, s.motivo "
                    + "FROM progetto_formativo as p, Lavoratore as l,scarti_progformativi as s, tutor as t "
                    + "WHERE s.id_pf=p.idprogetto_formativo AND p.tutor=t.idtutor AND p.lavoratore=l.cdnlavoratore AND s.id_rimborso = ? ";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativo tmp = new PrgFormativo();
                tmp.setId(rs.getInt("idprogetto_formativo"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getScartiPF " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<PrgFormativoDt> getScartiPF_DT(String idrimborso) {
        ArrayList<PrgFormativoDt> out = new ArrayList<>();
        try {

            String sql = "SELECT p.idprogetto_formativo_dt,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.nome, l.cognome, l.codice_fiscale,t.nome as t_nome,"
                    + " t.cognome as t_cognome, s.motivo, p.profiling "
                    + "FROM progetto_formativo_dt as p, Lavoratore as l,scarti_progformativi_dt as s, tutor as t "
                    + "WHERE s.id_pf_dt=p.idprogetto_formativo_dt AND p.tutor=t.idtutor AND p.lavoratore=l.cdnlavoratore AND s.id_rimborso_dt = ? ";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativoDt tmp = new PrgFormativoDt();
                tmp.setId(rs.getInt("idprogetto_formativo_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                tmp.setProfiling(rs.getInt("profiling"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getScartiPF_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public boolean associaPrgRimborso(String data, int id) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE progetto_formativo SET rimborso_prg=?, stato='A' WHERE idprogetto_formativo=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idprogetto_formativo=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            ps.setInt(1, id);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 2, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "associaPrgRimborso " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<PrgFormativo> getListPrgPDF(String ids) {
        ArrayList<PrgFormativo> out = new ArrayList<>();
        String[] s = ids.split(",");
        try {
            String sql = "SELECT p.idprogetto_formativo,l.nome, l.cognome,p.dataavvio,p.datafine, l.codice_fiscale, p.profiling, p.codazioneformcal, p.rimborso "
                    + "FROM progetto_formativo as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore and (";

            for (int i = 1; i < s.length + 1; i++) {
                sql += " idprogetto_formativo=? OR";
            }
            sql = sql.substring(0, sql.length() - 3);
            sql += " )";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 1, s[i]);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PrgFormativo tmp = new PrgFormativo();
                tmp.setId(rs.getInt("idprogetto_formativo"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getListPrgPDF " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Rimborso_PrgFormativo> getListRimborsiEntePrg(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        ArrayList<Rimborso_PrgFormativo> out = new ArrayList<>();
        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);
            pm.add(String.valueOf(idente));
            String sql = "";

            if (!stato.equals("P")) {
                sql = "SELECT r.* FROM rimborso_prgformativo as r, tutor as t, ente_promotore as e "
                        + "WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            } else if (stato.equals("P")) {
                sql = "SELECT r.*, d.path_decreto FROM rimborso_prgformativo as r, tutor as t, ente_promotore as e, decreto_prgformativo as d "
                        + "WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND fk_idrimborsopf=id_rimborso_prg AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            }

            if (!protocollo.equals("")) {
                sql += " AND r.protocollo=? ";
                pm.add(protocollo);
                flag = true;
            }
            if (!from_up.equals("") && !to_up.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_up);
                pm.add(to_up);
                flag = true;
            }
            if (!from_mod.equals("") && !to_mod.equals("")) {
                sql += " AND r.timestamp BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_mod);
                pm.add(to_mod);
                flag = true;
            }
            sql += " ORDER BY timestamp DESC ";

            if (!flag) {
                sql += "LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso_PrgFormativo tmp = new Rimborso_PrgFormativo();
                tmp.setIdrimborso_prg(rs.getString("id_rimborso_prg"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                if (stato.equals("P")) {
                    tmp.setPath_decreto(rs.getString("path_decreto"));
                }
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsiEntePrg " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<PrgFormativo> getListPrg(String domanda_rimborso) {
        ArrayList<PrgFormativo> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idprogetto_formativo,l.nome,l.cognome,p.dataavvio,p.datafine,l.codice_fiscale,p.doc_lavoratore,p.tutor,p.rimborso_prg,p.codazioneformcal,p.ore_tot,p.ore_effettuate,p.profiling, p.rimborso "
                    + "FROM progetto_formativo as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore AND p.rimborso_prg=? AND p.stato='A'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativo tmp = new PrgFormativo();
                tmp.setId(rs.getInt("idprogetto_formativo"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                tmp.setTutor(rs.getInt("tutor"));
                tmp.setRimborso(rs.getInt("rimborso_prg"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListPrg " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<PrgFormativo> getRimborsiPrg(String politica, String idrimborso) {
        ArrayList<PrgFormativo> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idprogetto_formativo,p.dataavvio,p.datafine, p.ore_tot, p.ore_effettuate, l.nome, l.cognome, l.codice_fiscale, t.nome as t_nome, t.cognome as t_cognome, p.durataeffettiva "
                    + "FROM progetto_formativo as p, tutor as t, Lavoratore as l "
                    + "WHERE p.rimborso_prg=? AND p.codazioneformcal=? AND p.tutor=t.idtutor "
                    + "AND p.lavoratore=l.cdnlavoratore AND p.stato='A'";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, politica);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativo tmp = new PrgFormativo();
                tmp.setId(rs.getInt("idprogetto_formativo"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiPrg " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<PrgFormativo> getScartiPrg(String idrimborso) {
        ArrayList<PrgFormativo> out = new ArrayList<>();
        try {

            String sql = "SELECT p.idprogetto_formativo,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.nome, l.cognome, l.codice_fiscale,t.nome as t_nome, t.cognome as t_cognome, s.motivo "
                    + "FROM progetto_formativo as p, Lavoratore as l,scarti_progformativi as s, tutor as t "
                    + "WHERE s.id_pf=p.idprogetto_formativo AND p.tutor=t.idtutor AND p.lavoratore=l.cdnlavoratore AND s.id_rimborso = ?";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativo tmp = new PrgFormativo();
                tmp.setId(rs.getInt("idprogetto_formativo"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getScartiPrg " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Registro> getListRegistri(String politica, String stato, String ente, String from, String to, String nome, String cognome, String cf) {
        ArrayList<Registro> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);

            String sql = "SELECT r.*,l.nome,l.cognome,l.codice_fiscale,e.ragionesociale,t.nome as t_nome, t.cognome as t_cognome, l.cdnlavoratore "
                    + " FROM registri as r, progetto_formativo as p, tutor as t, Lavoratore as l, ente_promotore as e "
                    + " WHERE r.progetto_formativo = p.idprogetto_formativo AND "
                    + " p.tutor=t.idtutor AND "
                    + " p.lavoratore=l.cdnlavoratore AND "
                    + " e.idente_promotore = t.ente_promotore AND "
                    + " p.codazioneformcal = ? ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!stato.equals("")) {
                sql += " AND r.stato = ? ";
                pm.add(stato);
                flag = true;
            } else {
                sql += " AND (r.stato = 'P' OR r.stato='K') ";
            }
            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
                flag = true;
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
                flag = true;
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            sql += " ORDER BY data_up ";

            sql += " LIMIT 1000";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Registro tmp = new Registro();
                tmp.setId(rs.getInt("idregistri"));
                tmp.setMese(rs.getString("mese"));
                tmp.setOre(rs.getInt("ore"));
                tmp.setOre_rev(rs.getInt("ore_rev"));
                tmp.setDatainizio(rs.getString("datainizio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setFile(rs.getString("file"));
                tmp.setDoc_quietanza(rs.getString("doc_quietanza"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_lavoratore(rs.getString("doc_lavoratore"));
                tmp.setProgetto_formativo(rs.getInt("progetto_formativo"));
                tmp.setDataup(rs.getString("data_up"));
                tmp.setLavoratore(rs.getString("nome") + " " + rs.getString("cognome"));
                tmp.setTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                tmp.setEnte(rs.getString("ragionesociale"));
                tmp.setCf_lavoratore(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setChecklist(rs.getString("check_list"));
                tmp.setTot_erogato(rs.getDouble("tot_erogato"));
                tmp.setXml_liquidazione(rs.getString("xml_liquidazione"));
                tmp.setId_lavoratore(rs.getString("l.cdnlavoratore"));
                tmp.setStato(rs.getString("r.stato"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRegistri " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Registro> getRegistriEnte(int idente, String stato, String nome, String cognome, String cf, String from, String to) {
        ArrayList<Registro> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(stato);
            pm.add(String.valueOf(idente));

            String sql = "SELECT r.*, t.nome as t_nome, t.cognome as t_cognome, l.nome, l.cognome, l.codice_fiscale, m.descrizione "
                    + "FROM registri as r , progetto_formativo as p, tutor as t, Lavoratore as l, mesi as m "
                    + "WHERE progetto_formativo=idprogetto_formativo AND idtutor=tutor AND cdnlavoratore=p.lavoratore AND r.mese=m.id "
                    + "AND r.stato=? AND ente_promotore=? ";

            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            if (nome.equals("") && cognome.equals("") && cf.equals("") && from.equals("") && to.equals("")) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Registro tmp = new Registro();
                tmp.setId(rs.getInt("idregistri"));
                tmp.setMese(rs.getString("descrizione"));
                tmp.setLavoratore(rs.getString("nome") + " " + rs.getString("cognome"));
                tmp.setOre(rs.getInt("ore"));
                tmp.setOre_rev(rs.getInt("ore_rev"));
                tmp.setDatainizio(rs.getString("datainizio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setFile(rs.getString("file"));
                tmp.setDoc_quietanza(rs.getString("doc_quietanza"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_lavoratore(rs.getString("doc_lavoratore"));
                tmp.setProgetto_formativo(rs.getInt("progetto_formativo"));
                tmp.setDataup(rs.getString("data_up"));
                tmp.setChecklist(rs.getString("check_list"));
                tmp.setTot_erogato(rs.getDouble("tot_erogato"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCf_lavoratore(rs.getString("codice_fiscale"));
                tmp.setTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                tmp.setMotivo(rs.getString("motivo"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRegistriEnte " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean accettaRegistro(String id) {
        try {
            String sql = "UPDATE registri SET stato='R' WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "accettaRegistro" + ex.getMessage());
        }
        return false;
    }

    public boolean accettaRegistroDT(String id) {
        try {
            String sql = "UPDATE registri_dt SET stato='R' WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "accettaRegistroDT" + ex.getMessage());
        }
        return false;
    }

    public boolean rigettaRegistro(String id, String motivo) {
        try {
            String sql = "UPDATE registri SET stato='E', motivo =? WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "rigettaRegistro" + ex.getMessage());
        }
        return false;
    }

    public boolean rigettaRegistroDT(String id, String motivo) {
        try {
            String sql = "UPDATE registri_dt SET stato='E', motivo =? WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "rigettaRegistroDT" + ex.getMessage());
        }
        return false;
    }

    public boolean scartaRegistro(String id, String motivo, String ore, int idpf) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE registri SET stato='K', motivo =?, ore_rev = ? WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, ore);
            ps.setString(3, id);
            ps.execute();

            sql = "UPDATE progetto_formativo SET ore_effettuate = ore_effettuate + ? WHERE idprogetto_formativo = ? ";
            ps = this.c.prepareStatement(sql);
            ps.setString(1, ore);
            ps.setInt(2, idpf);
            ps.executeUpdate();

            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "scartaRegistro" + ex.getMessage());
        }
        return false;
    }

    public boolean scartaRegistroDT(String id, String motivo, String ore, int idpf) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE registri_dt SET stato='K', motivo =?, ore_rev = ? WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, ore);
            ps.setString(3, id);
            ps.execute();

            sql = "UPDATE progetto_formativo_dt SET ore_effettuate = ore_effettuate + ? WHERE idprogetto_formativo_dt = ? ";
            ps = this.c.prepareStatement(sql);
            ps.setString(1, ore);
            ps.setInt(2, idpf);
            ps.executeUpdate();

            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "scartaRegistroDT" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_accettaRegistro(String id, String totale, String descrizione, String path, String ore, int idpf) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE registri SET stato='R2',motivo=?,check_list=?,tot_erogato=?,ore_rev=? WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, descrizione);
            ps.setString(2, path);
            ps.setString(3, totale);
            ps.setString(4, ore);
            ps.setString(5, id);
            ps.execute();

            sql = "UPDATE progetto_formativo SET ore_effettuate = ore_effettuate + ? WHERE idprogetto_formativo = ?";
            ps = this.c.prepareStatement(sql);
            ps.setString(1, ore);
            ps.setInt(2, idpf);
            ps.executeUpdate();
            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_accettaRegistro" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_accettaRegistroDT(String id, String totale, String descrizione, String path, String ore, int idpf) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE registri_dt SET stato='R2',motivo=?,check_list=?,tot_erogato=?,ore_rev=? WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, descrizione);
            ps.setString(2, path);
            ps.setString(3, totale);
            ps.setString(4, ore);
            ps.setString(5, id);
            ps.execute();

            sql = "UPDATE progetto_formativo_dt SET ore_effettuate = ore_effettuate + ? WHERE idprogetto_formativo_dt = ?";
            ps = this.c.prepareStatement(sql);
            ps.setString(1, ore);
            ps.setInt(2, idpf);
            ps.executeUpdate();
            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_accettaRegistroDT" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_rigettaRegistro(String id, String motivo) {
        try {
            String sql = "UPDATE registri SET stato='E2', motivo =? WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_rigettaRegistro" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_rigettaRegistroDT(String id, String motivo) {
        try {
            String sql = "UPDATE registri_dt SET stato='E2', motivo =? WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_rigettaRegistroDT" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_scartaRegistro(String id, String motivo, String ore, int idpf) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE registri SET stato='K', motivo =?,ore_rev=? WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, ore);
            ps.setString(3, id);
            ps.execute();

            sql = "UPDATE progetto_formativo SET ore_effettuate = ore_effettuate + ? WHERE idprogetto_formativo = ?";
            ps = this.c.prepareStatement(sql);
            ps.setString(1, ore);
            ps.setInt(2, idpf);
            ps.executeUpdate();

            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_scartaRegistro" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_scartaRegistroDT(String id, String motivo, String ore, int idpf) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE registri_dt SET stato='K', motivo =?, ore_rev = ? WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, ore);
            ps.setString(3, id);
            ps.execute();

            sql = "UPDATE progetto_formativo_dt SET ore_effettuate = ore_effettuate + ? WHERE idprogetto_formativo_dt = ?";
            ps = this.c.prepareStatement(sql);
            ps.setString(1, ore);
            ps.setInt(2, idpf);
            ps.executeUpdate();

            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_scartaRegistroDT" + ex.getMessage());
        }
        return false;
    }

    public Registro getRegistroById(String id) {
        Registro reg = new Registro();
        try {
            String sql = "SELECT * FROM registri WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reg.setId(rs.getInt("idregistri"));
                reg.setOre(rs.getInt("ore"));
                reg.setMese(rs.getString("mese"));
                reg.setDatainizio(rs.getString("datainizio"));
                reg.setDatafine(rs.getString("datafine"));
                reg.setFile(rs.getString("file"));
                reg.setDoc_quietanza(rs.getString("doc_quietanza"));
                reg.setProgetto_formativo(rs.getInt("progetto_formativo"));
            }
            return reg;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRegistroById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean updateRegistro(String idr, String ore, String doc_t, String doc_r, String from, String to) {
        try {
            String sql = "UPDATE registri SET ore=?, doc_tutor=?, doc_lavoratore=?,datainizio=?, datafine=?, stato='S' WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, ore);
            ps.setString(2, doc_t);
            ps.setString(3, doc_r);
            ps.setString(4, from);
            ps.setString(5, to);
            ps.setString(6, idr);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Action.insertTracking("", "updateRegistro " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public ArrayList<FileINPS> getInfoXML(String ids) {
        ArrayList<FileINPS> out = new ArrayList<>();
        String[] s = ids.split(",");
        try {
            String sql = "SELECT r.idregistri,l.cittadinanza,r.data_up,e.cf,r.datainizio,r.datafine,l.nome,l.cognome,l.nascita_data,l.codice_fiscale,l.nascita_codice_catastale,l.codice_provincia,l.residenza_indirizzo,l.residenza_cap,l.residenza_codice_catastale,r.tot_erogato "
                    + "FROM registri as r, progetto_formativo as p, Lavoratore as l, ente_promotore as e , tutor as t "
                    + "WHERE r.progetto_formativo = p.idprogetto_formativo AND p.lavoratore=l.cdnlavoratore AND t.idtutor=p.tutor AND t.ente_promotore = e.idente_promotore AND (";

            for (int i = 1; i < s.length + 1; i++) {
                sql += " idregistri=? OR";
            }
            sql = sql.substring(0, sql.length() - 3);
            sql += " )";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 1, s[i]);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FileINPS tmp = new FileINPS();
                tmp.setId(rs.getInt("idregistri"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setCf(rs.getString("cf"));
                tmp.setDatainizio(rs.getString("datainizio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setNascita_data(rs.getString("nascita_data"));
                tmp.setCodice_fiscale(rs.getString("codice_fiscale"));
                tmp.setNascita_codice_catastale(rs.getString("nascita_codice_catastale"));
                tmp.setCodice_provincia(rs.getString("codice_provincia"));
                tmp.setResidenza_indirizzo(rs.getString("residenza_indirizzo"));
                tmp.setResidenza_cap(rs.getString("residenza_cap"));
                tmp.setResidenza_codice_catastale(rs.getString("residenza_codice_catastale"));
                tmp.setCittadinanza(rs.getString("cittadinanza"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                out.add(tmp);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getInfoXML " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<FileINPS> getInfoXML_DT(String ids) {
        ArrayList<FileINPS> out = new ArrayList<>();
        String[] s = ids.split(",");
        try {
            String sql = "SELECT r.idregistri_dt,l.cittadinanza,r.data_up,e.cf,r.datainizio,r.datafine,l.nome,l.cognome,l.nascita_data,l.codice_fiscale,l.nascita_codice_catastale,l.codice_provincia,l.residenza_indirizzo,l.residenza_cap,l.residenza_codice_catastale,r.tot_erogato "
                    + "FROM registri_dt as r, progetto_formativo_dt as p, Lavoratore as l, ente_promotore as e , tutor as t "
                    + "WHERE r.progetto_formativo_dt = p.idprogetto_formativo_dt AND p.lavoratore=l.cdnlavoratore AND t.idtutor=p.tutor AND t.ente_promotore = e.idente_promotore AND (";

            for (int i = 1; i < s.length + 1; i++) {
                sql += " idregistri_dt=? OR";
            }
            sql = sql.substring(0, sql.length() - 3);
            sql += " )";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 1, s[i]);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FileINPS tmp = new FileINPS();
                tmp.setId(rs.getInt("idregistri_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setCf(rs.getString("cf"));
                tmp.setDatainizio(rs.getString("datainizio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setNascita_data(rs.getString("nascita_data"));
                tmp.setCodice_fiscale(rs.getString("codice_fiscale"));
                tmp.setNascita_codice_catastale(rs.getString("nascita_codice_catastale"));
                tmp.setCodice_provincia(rs.getString("codice_provincia"));
                tmp.setResidenza_indirizzo(rs.getString("residenza_indirizzo"));
                tmp.setResidenza_cap(rs.getString("residenza_cap"));
                tmp.setResidenza_codice_catastale(rs.getString("residenza_codice_catastale"));
                tmp.setCittadinanza(rs.getString("cittadinanza"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                out.add(tmp);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getInfoXML_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public Map<String, String> getComuneByCode() {
        Map<String, String> result = new HashMap<>();
        try {
            String sql = "SELECT codcom,descrizione FROM de_comune";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("codcom"), rs.getString("descrizione"));
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "getComuneByCode:" + e.getMessage());
            return null;
        }
    }

    public Map<String, String> getStatoByCode() {
        Map<String, String> result = new HashMap<>();
        try {
            String sql = "SELECT codcittadinanza,descrizione FROM de_cittadinanza";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("codcittadinanza"), rs.getString("descrizione"));
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "getStatoByCode:" + e.getMessage());
            return null;
        }
    }

    public Map<String, String> getProvinciaByCode() {
        Map<String, String> result = new HashMap<>();
        try {
            String sql = "SELECT codcom,targa FROM de_comune as c,de_provincia as p WHERE c.codprovincia = p.codprovincia";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("codcom"), rs.getString("targa"));
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "getProvinciaByCode:" + e.getMessage());
            return null;
        }
    }

    //public Map<String, String> getProvinciaByCode() {
    //    Map<String, String> result = new HashMap<>();
    //    try {
    //        String sql = "SELECT codprovincia,targa FROM de_provincia";
    //        PreparedStatement ps = this.c.prepareStatement(sql);
    //        ResultSet rs = ps.executeQuery();
    //        while (rs.next()) {
    //            result.put(rs.getString("codprovincia"), rs.getString("targa"));
    //       }
    //        return result;
    //    } catch (Exception e) {
    //        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    //        Action.insertTracking("", "getProvinciaByCode:" + e.getMessage());
    //        return null;
    //    }
    //}
    public Map<String, String> getCapByComune() {
        Map<String, String> result = new HashMap<>();
        try {
            String sql = "SELECT codcom,cap FROM de_comune";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("codcom"), rs.getString("cap"));
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "getCapByComune:" + e.getMessage());
            return null;
        }
    }

    public boolean liquidaRegistri(String data) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE registri SET stato='W' WHERE idregistri=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idregistri=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 1, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "liquidaRegistri " + ex.getMessage());
        }
        return false;
    }

    public boolean liquidaRegistriDT(String data) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE registri_dt SET stato='W' WHERE idregistri_dt=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idregistri_dt=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 1, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "liquidaRegistriDT " + ex.getMessage());
        }
        return false;
    }

    public boolean savexmlRegistri(String filename, String data) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE registri SET xml_liquidazione=? WHERE idregistri=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idregistri=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, filename);
            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 2, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "savexmlRegistri " + ex.getMessage());
        }
        return false;
    }

    public boolean savexmlRegistri_DT(String filename, String data) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE registri_dt SET xml_liquidazione=? WHERE idregistri_dt=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idregistri_dt=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, filename);
            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 2, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "savexmlRegistri_DT " + ex.getMessage());
        }
        return false;
    }

    public Map<String, String> getValueXmlINPS() {
        Map<String, String> result = new HashMap<>();
        try {
            String sql = "SELECT elemento,valore FROM xml_inps";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("elemento"), rs.getString("valore"));
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "getValueXmlINPS:" + e.getMessage());
            return null;
        }
    }

    public boolean accettaRegINPS(String id) {
        try {
            String sql = "UPDATE registri SET stato='P' WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "accettaRegINPS" + ex.getMessage());
        }
        return false;
    }

    public boolean rifiutaRegINPS(String id) {
        try {
            String sql = "UPDATE registri SET stato='R2' WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "rifiutaRegINPS" + ex.getMessage());
        }
        return false;
    }

    public boolean accettaRegINPS_DT(String id) {
        try {
            String sql = "UPDATE registri_dt SET stato='P' WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "accettaRegINPS_DT" + ex.getMessage());
        }
        return false;
    }

    public boolean rifiutaRegINPS_DT(String id) {
        try {
            String sql = "UPDATE registri_dt SET stato='R2' WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "rifiutaRegINPS_DT" + ex.getMessage());
        }
        return false;
    }

    public ArrayList<PrgFormativoDt> getPrgFormativiDt(String cfente, String stato, String nome, String cognome, String cf, String from, String to, String politica) {
        ArrayList<PrgFormativoDt> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(cfente);
            pm.add(politica);
            pm.add(stato);

            String sql = "SELECT p.idprogetto_formativo_dt,l.nome, l.cognome,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.codice_fiscale, p.motivo,p.stato,"
                    + " p.codazioneformcal, p.durataeffettiva, p.doc_lavoratore, p.doc_tutor, p.doc_m5, p.doc_competenze, p.scadenza_doc, p.file, p.convenzione "
                    + "FROM progetto_formativo_dt as p, Lavoratore as l "
                    + "WHERE p.cf_ente=? and p.codazioneformcal=? "
                    + "and p.lavoratore=l.cdnlavoratore and stato=? ";
            if (stato.equals("C")) {
                sql = "SELECT p.idprogetto_formativo_dt,l.nome, l.cognome,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.codice_fiscale, p.motivo,p.stato,"
                        + " p.codazioneformcal, p.durataeffettiva, p.doc_lavoratore, p.doc_tutor, p.doc_m5, doc_competenze, p.scadenza_doc, p.file, p.convenzione "
                        + "FROM progetto_formativo_dt as p, Lavoratore as l "
                        + "WHERE p.cf_ente=? and p.codazioneformcal=? "
                        + "and p.lavoratore=l.cdnlavoratore and stato=?  AND (p.ore_effettuate/p.ore_tot)>=? ";
                pm.add(Action.getPath("soglia_prg"));
            }
            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
//            if (!politica.equals("")) {
//                sql += " AND p.codazioneformcal=? ";
//                pm.add(politica);
//            }
            if (nome.equals("") && cognome.equals("") && cf.equals("") && from.equals("") && to.equals("")) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativoDt tmp = new PrgFormativoDt();
                tmp.setId(rs.getInt("idprogetto_formativo_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_m5(rs.getString("doc_m5"));
                tmp.setDoc_competenze(rs.getString("doc_competenze"));
                tmp.setScadenza_doc(rs.getString("scadenza_doc"));
                tmp.setFile(rs.getString("file"));
                tmp.setConvenzione(rs.getInt("convenzione"));

                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("service", "getPrgFormativiDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Rimborso_PrgFormativo_Dt> getListRimborsi_PrgFormativoDT(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso_PrgFormativo_Dt> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);

            String sql = "SELECT r.* FROM rimborso_prgformativo_dt as r, tutor as t, ente_promotore as e WHERE  r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            sql += " ORDER BY data_up ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso_PrgFormativo_Dt tmp = new Rimborso_PrgFormativo_Dt();
                tmp.setIdrimborso_prg_dt(rs.getString("id_rimborso_prg_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setRimborso(rs.getString("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsi_PrgFormativoDT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public PrgFormativoDt getPrgFormativoByIdDt(String id) {
        PrgFormativoDt out = new PrgFormativoDt();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String sql = "SELECT tutor, doc_lavoratore, dataavvio, ore_tot, datafine, scadenza_doc, ore_mese, durata_mesi, doc_competenze, stato, doc_m5, file, convenzione, profiling "
                    + "FROM progetto_formativo_dt "
                    + "WHERE idprogetto_formativo_dt = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setId(Integer.parseInt(id));
                out.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                out.setTutor(rs.getInt("tutor"));
                out.setOre_tot(rs.getInt("ore_tot"));
                out.setDataavvio(sdf.format(rs.getDate("dataavvio")));
                out.setDatafine(sdf.format(rs.getDate("datafine")));
                out.setStato(rs.getString("stato"));
                out.setProfiling(rs.getInt("profiling"));
                if (out.getTutor() != 0) {
                    out.setScadenza_doc(sdf.format(rs.getDate("scadenza_doc")));
                    out.setDurata_mesi(rs.getInt("durata_mesi"));
                    out.setDoc_competenze(rs.getString("doc_competenze"));
                    out.setDoc_m5(rs.getString("doc_m5"));
                    out.setFile(rs.getString("file"));
                    out.setConvenzione(rs.getInt("convenzione"));
                }
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getPoliticaByIdDt " + ex.getMessage());
        }
        return out;
    }

    public boolean uploadDocPrgDt(String id, String tutor, String file, String doc_tutor, String scadenza, String ore_tot, int mesi,
            int ore_mese, String from, String to, String stato, String doc_m5, String competenze, String convenzione, String file_prg) {
        try {
            String sql = "UPDATE progetto_formativo_dt SET tutor=?, doc_tutor=?, doc_lavoratore=?, scadenza_doc=?, ore_tot=?, durata_mesi=?, ore_mese=?, "
                    + "dataavvio=?, datafine=?, stato=?, doc_m5=?, doc_competenze=?, dataup=curdate(), file=?, convenzione=? "
                    + "WHERE idprogetto_formativo_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, tutor);
            ps.setString(2, doc_tutor);
            ps.setString(3, file);
            ps.setString(4, scadenza);
            ps.setString(5, ore_tot);
            ps.setInt(6, mesi);
            ps.setInt(7, ore_mese);
            ps.setString(8, from);
            ps.setString(9, to);
            ps.setString(10, stato);
            ps.setString(11, doc_m5);
            ps.setString(12, competenze);
            ps.setString(13, file_prg);
            ps.setString(14, convenzione);
            ps.setString(15, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "uploadDocPrgDt " + ex.getMessage());
        }
        return false;
    }

    public int insertRimborsoPrgDt(String tutor, String politica, String doc_ad_au) {

        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO rimborso_prgformativo_dt SET ad_au=?, data_up= CURDATE(), politica=?, doc_ad_au=?";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tutor);
            ps.setString(2, politica);
            ps.setString(3, doc_ad_au);

            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "insertRimborsoprgDt " + ex.getMessage());
        }
        return 0;
    }

    public boolean associaPrgRimborsoDt(String data, int id) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE progetto_formativo_dt SET rimborso_prg=?, stato='A' WHERE idprogetto_formativo_dt=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idprogetto_formativo_dt=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            ps.setInt(1, id);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 2, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "associaPrgRimborsoDt " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<RegistroDt> getListRegistriDt(String politica, String stato, String ente, String from, String to, String nome, String cognome, String cf) {
        ArrayList<RegistroDt> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);

            String sql = "SELECT r.*,l.nome,l.cognome,l.codice_fiscale,e.ragionesociale,t.nome as t_nome, t.cognome as t_cognome,p.codazioneformcal,l.cdnlavoratore "
                    + " FROM registri_dt as r, progetto_formativo_dt as p, tutor as t, Lavoratore as l, ente_promotore as e "
                    + " WHERE r.progetto_formativo_dt = p.idprogetto_formativo_dt AND "
                    + " p.tutor = t.idtutor AND "
                    + " p.lavoratore = l.cdnlavoratore AND "
                    + " e.idente_promotore = t.ente_promotore AND p.codazioneformcal = ? AND r.stato = ? ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
                flag = true;
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
                flag = true;
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            sql += " ORDER BY data_up ";

            sql += " LIMIT 1000";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RegistroDt tmp = new RegistroDt();
                tmp.setId(rs.getInt("idregistri_dt"));
                tmp.setMese(rs.getString("mese"));
                tmp.setOre(rs.getInt("ore"));
                tmp.setOre_rev(rs.getInt("ore_rev"));
                tmp.setDatainizio(rs.getString("datainizio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setFile(rs.getString("file"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_lavoratore(rs.getString("doc_lavoratore"));
                tmp.setProgetto_formativo_dt(rs.getInt("progetto_formativo_dt"));
                tmp.setDataup(rs.getString("data_up"));
                tmp.setLavoratore(rs.getString("nome") + " " + rs.getString("cognome"));
                tmp.setTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                tmp.setEnte(rs.getString("ragionesociale"));
                tmp.setCf_lavoratore(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setChecklist(rs.getString("check_list"));
                tmp.setTot_erogato(rs.getDouble("tot_erogato"));
                tmp.setXml_liquidazione(rs.getString("xml_liquidazione"));
                tmp.setPolitica(rs.getString("codazioneformcal"));
                tmp.setId_lavoratore(rs.getString("l.cdnlavoratore"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRegistriDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public RegistroDt getRegistroByIdDT(String id) {
        RegistroDt reg = new RegistroDt();
        try {
            String sql = "SELECT * FROM registri_dt WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reg.setId(rs.getInt("idregistri_dt"));
                reg.setOre(rs.getInt("ore"));
                reg.setMese(rs.getString("mese"));
                reg.setDatainizio(rs.getString("datainizio"));
                reg.setDatafine(rs.getString("datafine"));
                reg.setFile(rs.getString("file"));
                reg.setProgetto_formativo_dt(rs.getInt("progetto_formativo_dt"));
            }
            return reg;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRegistroById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<PrgFormativoDt> getListPrgPDF_dt(String ids) {
        ArrayList<PrgFormativoDt> out = new ArrayList<>();
        String[] s = ids.split(",");
        try {
            String sql = "SELECT p.idprogetto_formativo_dt,l.nome, l.cognome,p.dataavvio,p.datafine, l.codice_fiscale, p.profiling, p.codazioneformcal, p.rimborso "
                    + "FROM progetto_formativo_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore and (";

            for (int i = 1; i < s.length + 1; i++) {
                sql += " idprogetto_formativo_dt=? OR";
            }
            sql = sql.substring(0, sql.length() - 3);
            sql += " )";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 1, s[i]);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PrgFormativoDt tmp = new PrgFormativoDt();
                tmp.setId(rs.getInt("idprogetto_formativo_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getListPrgPDF_dt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Rimborso_PrgFormativo_Dt> getListRimborsiEntePrgDt(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        ArrayList<Rimborso_PrgFormativo_Dt> out = new ArrayList<>();
        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
//            pm.add(politica);
            pm.add(stato);
            pm.add(String.valueOf(idente));
            String sql = "";
            if (!stato.equals("P")) {
                sql = "SELECT r.* "
                        + "FROM rimborso_prgformativo_dt as r, tutor as t, ente_promotore as e "
                        + "WHERE r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            } else if (stato.equals("P")) {
                sql = "SELECT r.*, d.path_decreto "
                        + "FROM rimborso_prgformativo_dt as r, tutor as t, ente_promotore as e, decreto_prgformativo_dt as d "
                        + "WHERE r.stato = ? AND ad_au=idtutor AND d.fk_idrimborsopf_dt=r.id_rimborso_prg_dt AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            }
            if (!protocollo.equals("")) {
                sql += " AND r.protocollo=? ";
                pm.add(protocollo);
                flag = true;
            }
            if (!politica.equals("")) {
                sql += " AND r.politica=? ";
                pm.add(politica);
                flag = true;
            }
            if (!from_up.equals("") && !to_up.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_up);
                pm.add(to_up);
                flag = true;
            }
            if (!from_mod.equals("") && !to_mod.equals("")) {
                sql += " AND r.timestamp BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_mod);
                pm.add(to_mod);
                flag = true;
            }
            sql += " ORDER BY timestamp DESC ";

            if (!flag) {
                sql += "LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso_PrgFormativo_Dt tmp = new Rimborso_PrgFormativo_Dt();
                tmp.setIdrimborso_prg_dt(rs.getString("id_rimborso_prg_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                if (stato.equals("P")) {
                    tmp.setPath_decreto(rs.getString("path_decreto"));
                }
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsiEntePrgDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<PrgFormativoDt> getRimborsiPrgDt(String idrimborso) {
        ArrayList<PrgFormativoDt> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idprogetto_formativo_dt,p.dataavvio,p.datafine, p.codazioneformcal, p.profiling, p.ore_tot, p.ore_effettuate, l.nome, l.cognome, l.codice_fiscale, t.nome as t_nome, t.cognome as t_cognome, p.durataeffettiva "
                    + "FROM progetto_formativo_dt as p, tutor as t, Lavoratore as l "
                    + "WHERE p.rimborso_prg=? AND p.tutor=t.idtutor "
                    + "AND p.lavoratore=l.cdnlavoratore AND p.stato='A'";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativoDt tmp = new PrgFormativoDt();
                tmp.setId(rs.getInt("idprogetto_formativo_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiPrgDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<PrgFormativoDt> getListPrgDt(String domanda_rimborso) {
        ArrayList<PrgFormativoDt> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idprogetto_formativo_dt,l.nome,l.cognome,p.dataavvio,p.datafine,l.codice_fiscale,p.doc_lavoratore,p.tutor,p.rimborso_prg,p.codazioneformcal,p.ore_tot,p.ore_effettuate, p.profiling, p.rimborso, p.bando "
                    + "FROM progetto_formativo_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore AND p.rimborso_prg=? AND p.stato='A'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativoDt tmp = new PrgFormativoDt();
                tmp.setId(rs.getInt("idprogetto_formativo_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                tmp.setTutor(rs.getInt("tutor"));
                tmp.setRimborso(rs.getInt("rimborso_prg"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                tmp.setBando(rs.getInt("bando"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListPrgDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<PrgFormativoDt> getScartiPrgDt(String idrimborso) {
        ArrayList<PrgFormativoDt> out = new ArrayList<>();
        try {

            String sql = "SELECT p.idprogetto_formativo_dt ,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.nome, l.cognome, l.codice_fiscale,t.nome as t_nome, t.cognome as t_cognome, s.motivo "
                    + "FROM progetto_formativo_dt as p, Lavoratore as l,scarti_progformativi_dt as s, tutor as t "
                    + "WHERE s.id_pf_dt=p.idprogetto_formativo_dt AND p.tutor=t.idtutor AND p.lavoratore=l.cdnlavoratore AND s.id_rimborso_dt = ?";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativoDt tmp = new PrgFormativoDt();
                tmp.setId(rs.getInt("idprogetto_formativo_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getScartiPrgDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public PrgFormativoDt getPrgFormativoDtById(String id) {

        PrgFormativoDt out = new PrgFormativoDt();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String sql = "SELECT tutor, doc_lavoratore, dataavvio, ore_tot, datafine, scadenza_doc, ore_mese, durata_mesi, doc_competenze, stato, ore_effettuate, convenzione, file, profiling "
                    + "FROM progetto_formativo_dt WHERE idprogetto_formativo_dt = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setId(Integer.parseInt(id));
                out.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                out.setTutor(rs.getInt("tutor"));
                out.setOre_tot(rs.getInt("ore_tot"));
                out.setOre_effettuate(rs.getInt("ore_effettuate"));
                out.setDataavvio(sdf.format(rs.getDate("dataavvio")));
                out.setDatafine(sdf.format(rs.getDate("datafine")));
                out.setStato(rs.getString("stato"));
                out.setFile(rs.getString("file"));
                out.setConvenzione(rs.getInt("convenzione"));
                out.setProfiling(rs.getInt("profiling"));
                if (out.getTutor() != 0) {
                    out.setScadenza_doc(sdf.format(rs.getDate("scadenza_doc")));
                    out.setDurata_mesi(rs.getInt("durata_mesi"));
                    out.setDoc_competenze(rs.getString("doc_competenze"));
                }
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getPrgFormativoDtById " + ex.getMessage());
        }
        return out;
    }

    public int getRegisterNumberDt(String id) {
        try {
            String sql = "SELECT count(*) as c FROM registri_dt WHERE progetto_formativo_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("c");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getRegisterNumberDt " + ex.getMessage());
        }
        return 0;
    }

    public RegistroDt getLastRegisterDt(String idprg) {
        RegistroDt reg = new RegistroDt();
        try {
            String sql = "SELECT datainizio,datafine,mese FROM registri_dt WHERE progetto_formativo_dt=? order by idregistri_dt desc limit 1";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idprg);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reg.setDatainizio(rs.getString("datainizio"));
                reg.setDatafine(rs.getString("datafine"));
                reg.setMese(rs.getString("mese"));
                return reg;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getLastRegister " + ex.getMessage());
        }

        return null;
    }

    public int insertRtegistroDt(String ore, String mese, String file, String doc_t, String doc_r, String idprg, String from, String to) {
        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO registri_dt SET mese=?, ore=?, file=?, doc_tutor=?, doc_lavoratore=?, data_up=curdate(), progetto_formativo_dt =?, datainizio=?, datafine=?";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, mese);
            ps.setString(2, ore);
            ps.setString(3, file);
            ps.setString(4, doc_t);
            ps.setString(5, doc_r);
            ps.setString(6, idprg);
            ps.setString(7, from);
            ps.setString(8, to);

            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;
//            int x = ps.executeUpdate();
//            return x > 0;
        } catch (SQLException ex) {
            Action.insertTracking("", "insertRtegistroDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public boolean updatePrgCertDt(String idprg, String doc_cert) {
        try {
            String sql = "UPDATE progetto_formativo_dt SET stato='C', doc_competenze=? WHERE idprogetto_formativo_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, doc_cert);
            ps.setString(2, idprg);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "updatePrgCertDt " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<RegistroDt> getRegistriEnteDt(int idente, String stato, String nome, String cognome, String cf, String from, String to) {
        ArrayList<RegistroDt> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(stato);
            pm.add(String.valueOf(idente));

            String sql = "SELECT r.*, t.nome as t_nome, t.cognome as t_cognome, l.nome, l.cognome, l.codice_fiscale, m.descrizione "
                    + "FROM registri_dt as r , progetto_formativo_dt as p, tutor as t, Lavoratore as l, mesi as m "
                    + "WHERE progetto_formativo_dt=idprogetto_formativo_dt AND idtutor=tutor AND cdnlavoratore=p.lavoratore AND r.mese=m.id "
                    + "AND r.stato=? AND ente_promotore=? ";

            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            if (nome.equals("") && cognome.equals("") && cf.equals("") && from.equals("") && to.equals("")) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RegistroDt tmp = new RegistroDt();
                tmp.setId(rs.getInt("idregistri_dt"));
                tmp.setMese(rs.getString("descrizione"));
                tmp.setLavoratore(rs.getString("nome") + " " + rs.getString("cognome"));
                tmp.setOre(rs.getInt("ore"));
                tmp.setOre_rev(rs.getInt("ore_rev"));
                tmp.setDatainizio(rs.getString("datainizio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setFile(rs.getString("file"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_lavoratore(rs.getString("doc_lavoratore"));
                tmp.setProgetto_formativo_dt(rs.getInt("progetto_formativo_dt"));
                tmp.setDataup(rs.getString("data_up"));
                tmp.setChecklist(rs.getString("check_list"));
                tmp.setTot_erogato(rs.getDouble("tot_erogato"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCf_lavoratore(rs.getString("codice_fiscale"));
                tmp.setTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                tmp.setMotivo(rs.getString("motivo"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRegistriEnteDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean updateRegistroDt(String idr, String ore, String doc_t, String doc_r, String from, String to) {
        try {
            String sql = "UPDATE registri_dt SET ore=?, doc_tutor=?, doc_lavoratore=?,datainizio=?, datafine=?, stato='S' WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, ore);
            ps.setString(2, doc_t);
            ps.setString(3, doc_r);
            ps.setString(4, from);
            ps.setString(5, to);
            ps.setString(6, idr);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Action.insertTracking("", "updateRegistroDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public boolean modificaResidenzaLavoratore(String id, String indirizzo, String comune, String cap) {//String provincia,codice_provincia=?, ps.setString(3, provincia);
        try {
            String sql = "UPDATE Lavoratore SET residenza_indirizzo=?,residenza_codice_catastale=?,residenza_cap=? WHERE cdnlavoratore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, indirizzo);
            ps.setString(2, comune);
            ps.setString(3, cap);
            ps.setString(4, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "modificaResidenzaLavoratore " + ex.getMessage());
        }
        return false;
    }

    public HashMap<String, double[]> getPrezziario(String t_bando) {
        HashMap<String, double[]> out = new HashMap<>();

        try {
            String sql = "SELECT * FROM prezziario WHERE tipo_bando=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, t_bando);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double[] tmp = {rs.getDouble("1"), rs.getDouble("2"), rs.getDouble("3"), rs.getDouble("4")};
                out.put(rs.getString("cod_politica"), tmp);
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getPrezziario " + ex.getMessage());
        }

        return null;
    }

    public String getProvinciaResidenza(String cod) {
        try {
            String sql = "SELECT codprovincia FROM de_comune WHERE codcom = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("codprovincia");
            }
        } catch (SQLException ex) {
            ////log.severe(ex.getMessage());
            Action.insertTracking("", "getProvinciaResidenza " + ex.getMessage());
        }
        return "-";
    }

    public ArrayList<String[]> getTipiPrestazione() {
        try {
            ArrayList<String[]> out = new ArrayList<>();
            String sql = "SELECT valore,elemento FROM xml_inps WHERE elemento like 'tp_%'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("valore"), rs.getString("elemento")};
                out.add(temp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getTipiPrestazione" + ex.getMessage());
        }
        return null;
    }

    public void PrestazionePredefinita(String val) {
        try {
            String sql = "UPDATE xml_inps SET valore=? WHERE elemento = 'tipo_prestazione'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, val);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "PrestazionePredefinita " + ex.getMessage());
        }
    }

    public int getTotlHourPrgDt(String idprg) {
        try {
            String sql = "SELECT SUM(ore) as s FROM registri_dt WHERE progetto_formativo_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idprg);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("s");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getTotlHourPrg_dt " + ex.getMessage());
        }
        return 0;
    }

    public boolean RigettaINPS(String id, String motivo) {
        try {
            String sql = "UPDATE registri SET stato='K', motivo =? WHERE idregistri=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_rigettaINPS" + ex.getMessage());
        }
        return false;
    }

    public boolean RigettaINPS_DT(String id, String motivo) {
        try {
            String sql = "UPDATE registri_dt SET stato='K', motivo =? WHERE idregistri_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_rigettaINPS_DT" + ex.getMessage());
        }
        return false;
    }

    public ArrayList<Politica> getPolitiche_DT(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(politica);
            pm.add(cfente);
            pm.add(stato);

            String sql = "SELECT p.idpolitica_dt,l.nome, l.cognome,p.dataavvio, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_ragazzo, p.doc_tutor, p.doc_m5"
                    + " FROM politica_dt as p, Lavoratore as l "
                    + "WHERE p.codazioneformcal=? and p.cf_ente=? "
                    + "and p.lavoratore=l.cdnlavoratore and stato=?  ";
            if ((politica.equals("D2") || politica.equals("D5")) && !stato.equals("I")) {
                sql = "SELECT p.idpolitica_dt,l.nome, l.cognome,p.dataavvio, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_ragazzo, p.doc_tutor, p.doc_m5, c.file"
                        + " FROM politica_dt as p, Lavoratore as l, contratti_dt as c "
                        + "WHERE p.codazioneformcal=? and p.cf_ente=? "
                        + "and c.idcontratti_dt=p.idpolitica_dt and p.lavoratore=l.cdnlavoratore and stato=?  ";
            }

            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            if (nome.equals("") && cognome.equals("") && cf.equals("") && from.equals("") && to.equals("")) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setDoc_m5(rs.getString("doc_m5"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                if ((politica.equals("D2") || politica.equals("D5")) && !stato.equals("I")) {
                    Contratto c = new Contratto();
                    c.setFile(rs.getString("file"));
                    tmp.setContratto(c);
                }

                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getPolitiche_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Rimborso> getListRimborsiEnteDt(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        ArrayList<Rimborso> out = new ArrayList<>();
        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);
            pm.add(String.valueOf(idente));

            String sql = "";

            if (!stato.equals("P")) {
                sql = "SELECT r.idrimborso_dt, r.data_up, r.motivo, r.stato, r.ad_au, r.timestamp, r.politica, r.protocollo, r.documento, r.tot_erogato, r.check_list "
                        + "FROM rimborso_politica_dt as r, tutor as t, ente_promotore as e "
                        + "WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            } else if (stato.equals("P")) {
                sql = "SELECT r.idrimborso_dt, r.data_up, r.motivo, r.stato, r.ad_au, r.timestamp, r.politica, r.protocollo, r.documento, r.tot_erogato, r.check_list, d.path_decreto "
                        + "FROM rimborso_politica_dt as r, tutor as t, ente_promotore as e, decreto_dt as d "
                        + "WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND d.fk_idrimborso_dt=r.idrimborso_dt AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            }
            if (!protocollo.equals("")) {
                sql += " AND r.protocollo=? ";
                pm.add(protocollo);
                flag = true;
            }
            if (!from_up.equals("") && !to_up.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_up);
                pm.add(to_up);
                flag = true;
            }
            if (!from_mod.equals("") && !to_mod.equals("")) {
                sql += " AND r.timestamp BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_mod);
                pm.add(to_mod);
                flag = true;
            }
            sql += " ORDER BY timestamp DESC ";

            if (!flag) {
                sql += "LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                if (stato.equals("P")) {
                    tmp.setPath_decreto(rs.getString("path_decreto"));
                }
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsiEnteDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean uploadDocDt(String id, String tutor, String m5, String file, String doc_tutor) {
        try {
            String sql = "UPDATE politica_dt SET tutor=?, doc_m5=?, doc_ragazzo=?, doc_tutor=?, stato='S' WHERE idpolitica_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, tutor);
            ps.setString(2, m5);
            ps.setString(3, file);
            ps.setString(4, doc_tutor);
            ps.setString(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "uploadDocDt " + ex.getMessage());
        }
        return false;
    }

    public Politica getPoliticaByIDDt(String id) {
        Politica out = new Politica();
        try {
            String sql = "SELECT tutor, doc_ragazzo, doc_m5 FROM politica_dt WHERE idpolitica_dt = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setId(Integer.parseInt(id));
                out.setDoc_m5(rs.getString("doc_m5"));
                out.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                out.setTutor(rs.getInt("tutor"));
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getPoliticaByIDDt " + ex.getMessage());
        }
        return out;
    }

    public Map<String, String[]> get_ADInfos_DOTE() {
        Map<String, String[]> result = new HashMap<>();
        try {
            String sql = "SELECT rimborso_politica_dt.idrimborso_dt,tutor.nome,tutor.cognome,ente_promotore.ragionesociale,rimborso_politica_dt.doc_ad_au,ente_promotore.idente_promotore FROM rimborso_politica_dt,tutor,ente_promotore WHERE tutor.idtutor = rimborso_politica_dt.ad_au AND tutor.ente_promotore = ente_promotore.idente_promotore;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("ente_promotore.ragionesociale"), rs.getString("tutor.cognome") + " " + rs.getString("tutor.nome"), rs.getString("rimborso_politica_dt.doc_ad_au"), rs.getString("ente_promotore.idente_promotore")};
                result.put(rs.getString("rimborso_politica_dt.idrimborso_dt"), temp);
            }
            return result;
        } catch (SQLException ex) {
            Action.insertTracking("", "get_ADInfos_DOTE " + ex.getMessage());
            return null;
        }
    }

    public ArrayList<Rimborso> getListRimborsi_DOTE(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);

            String sql = "SELECT r.* FROM rimborso_politica_dt as r, tutor as t, ente_promotore as e WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            sql += " ORDER BY data_up ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setRimborso(rs.getString("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsi " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public int insertRimborsoDt(String tutor, String politica, String doc_ad_au) {

        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO rimborso_politica_dt SET ad_au=?, data_up= CURDATE(), politica=?, doc_ad_au=?";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tutor);
            ps.setString(2, politica);
            ps.setString(3, doc_ad_au);

            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "insertRimborsoDt" + ex.getMessage());
        }

        return 0;
    }

    public boolean associaPoliticheRimborsoDt(String data, int id) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE politica_dt SET domanda_rimborso_dt=?, stato='A' WHERE idpolitica_dt=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idpolitica_dt=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            ps.setInt(1, id);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 2, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "associaPoliticheRimborsoDt " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<Politica> getListPolitichePDFDt(String ids) {
        ArrayList<Politica> out = new ArrayList<>();
        String[] s = ids.split(",");
        try {
            String sql = "SELECT p.idpolitica_dt,l.nome, l.cognome,p.dataavvio, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.profiling, p.rimborso "
                    + "FROM politica_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore  and (";

            for (int i = 1; i < s.length + 1; i++) {
                sql += " idpolitica_dt=? OR";
            }
            sql = sql.substring(0, sql.length() - 3);
            sql += " )";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < s.length; i++) {

                ps.setString(i + 1, s[i]);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getListPolitichePDFDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public boolean uploadProtocollo_DOTE(String id, String protocollo, String path) {
        try {
            String sql = "UPDATE rimborso_politica_dt SET protocollo=?, documento=?,stato='N' WHERE idrimborso_dt=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, protocollo);
            ps.setString(2, path);
            ps.setString(3, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadProtocollo_DOTE:" + e.getMessage());
        }
        return false;
    }

    public ArrayList<Politica> getListPolitiche_DOTE(String domanda_rimborso) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_dt,l.nome,l.cognome,p.dataavvio,p.datafine,l.codice_fiscale,p.doc_ragazzo,p.tutor,p.doc_m5,p.domanda_rimborso_dt,p.codazioneformcal,p.durataeffettiva,p.profiling,p.lavoratore,p.cf_ente "
                    + "FROM politica_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore AND p.domanda_rimborso_dt=? AND p.stato='A'  ;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                tmp.setTutor(rs.getInt("tutor"));
                tmp.setDoc_m5(rs.getString("doc_m5"));
                tmp.setRimborso(rs.getInt("domanda_rimborso_dt"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setIdlav(rs.getString("lavoratore"));
                tmp.setIdente(rs.getString("cf_ente"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListPolitiche_DOTE " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Politica> getRimborsiPoliticheDt(String politica, String idrimborso) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_dt,p.dataavvio, l.nome, l.cognome, l.codice_fiscale, t.nome as t_nome, t.cognome as t_cognome, p.durataeffettiva "
                    + "FROM politica_dt as p, tutor as t, Lavoratore as l "
                    + "WHERE p.domanda_rimborso_dt=? and p.codazioneformcal=? and p.tutor=t.idtutor "
                    + "and p.lavoratore=l.cdnlavoratore AND p.stato='A'";
            if (politica.equals("D2") || politica.equals("D5")) {
                sql = "SELECT p.idpolitica_dt,p.dataavvio, l.nome, l.cognome, l.codice_fiscale, p.durataeffettiva "
                        + "FROM politica_dt as p, Lavoratore as l "
                        + "WHERE p.domanda_rimborso_dt=? and p.codazioneformcal=? "
                        + "and p.lavoratore=l.cdnlavoratore AND p.stato='A'";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, politica);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                if (politica.equals("B1") || politica.equals("C1")) {
                    tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                }
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiPoliticheDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Politica> getScartiPoliticheDt(String idrimborso, String politica) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_dt,p.dataavvio,p.durataeffettiva, l.nome, l.cognome, l.codice_fiscale, t.nome as t_nome, t.cognome as t_cognome, s.motivo "
                    + "FROM scarti_politiche_dt as s , politica_dt as p, tutor as t, Lavoratore as l "
                    + "WHERE id_politica_dt=idpolitica_dt and id_rimborso_dt=? and p.tutor=t.idtutor "
                    + "and p.lavoratore=l.cdnlavoratore;";
            if (politica.equals("D2") || politica.equals("D5")) {
                sql = "SELECT p.idpolitica_dt,p.dataavvio,p.durataeffettiva, l.nome, l.cognome, l.codice_fiscale, s.motivo "
                        + "FROM scarti_politiche_dt as s , politica_dt as p, Lavoratore as l "
                        + "WHERE id_politica_dt=idpolitica_dt and id_rimborso_dt=? "
                        + "and p.lavoratore=l.cdnlavoratore;";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                if (politica.equals("B1") || politica.equals("C1")) {
                    tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                }
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiPoliticheDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Politica> getListPoliticheDt(String domanda_rimborso) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_dt,l.nome,l.cognome,p.dataavvio,p.datafine,l.codice_fiscale,p.doc_ragazzo,p.tutor,p.doc_m5,p.domanda_rimborso_dt,p.codazioneformcal,p.durataeffettiva,p.profiling,p.rimborso "
                    + "FROM politica_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore "
                    + "AND p.domanda_rimborso_dt=? AND p.stato='A'  ;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                tmp.setTutor(rs.getInt("tutor"));
                tmp.setDoc_m5(rs.getString("doc_m5"));
                tmp.setRimborso(rs.getInt("domanda_rimborso_dt"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListPoliticheDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public boolean accettaRimborsi_DOTE(String id) {
        try {
            String sql = "UPDATE rimborso_politica_dt SET stato='R' WHERE idrimborso_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "accettaRimborsi_DOTE" + ex.getMessage());
        }
        return false;
    }

    public boolean rigettaRimborsi_DOTE(String id, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica_dt SET stato='K',motivo=? WHERE idrimborso_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            ps.execute();

            String sql1 = "UPDATE politica_dt SET stato='E',motivo=?,domanda_rimborso_dt=NULL WHERE domanda_rimborso_dt=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "rigettaRimborsi_DOTE:" + e.getMessage());
        }
        return false;
    }

    public boolean uploadDecreto_DOTE(String idrimborso, String repertorio, String datar, String decreto, String datad, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO decreto_dt (fk_idrimborso_dt,num_assunzione,data_assunzione,num_decreto,data_decreto,path_decreto) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, repertorio);
            ps.setString(3, datar);
            ps.setString(4, decreto);
            ps.setString(5, datad);
            ps.setString(6, path);
            ps.execute();

            String sql1 = "UPDATE rimborso_politica_dt SET stato='P' WHERE idrimborso_dt=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, idrimborso);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadDecreto_DOTE:" + e.getMessage());
        }
        return false;
    }

    public boolean insertScartiPolitiche_DOTE(String id_rimborso, String id_politica, String motivo) {
        try {
            String sql = "INSERT INTO scarti_politiche_dt SET id_rimborso_dt=?, id_politica_dt=?, motivo=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id_rimborso);
            ps.setString(2, id_politica);
            ps.setString(3, motivo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Action.insertTracking("service", "insertScartiPolitiche_DOTE " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public int numPoliticheRimborso_DOTE(String id, String idpolitica) {
        int n = 0;
        try {
            String sql = "SELECT count(*) as n FROM politica_dt WHERE domanda_rimborso_dt = ? AND stato = 'A' AND idpolitica_dt <> ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, idpolitica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                n = rs.getInt("n");
            }
            return n;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "numPoliticheRimborso_DOTE" + ex.getMessage());
        }
        return n;
    }

    public boolean AnomalieRimborsoCondition_DOTE(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica_dt SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE idrimborso_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE politica_dt SET stato='E',domanda_rimborso_dt=NULL,motivo = ? WHERE idpolitica_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "AnomalieRimborsoCondition_DOTE:" + e.getMessage());
        }
        return false;

    }

    public boolean anomaliaRimborso_DOTE(String id, String motivo) {
        try {
            String sql = "UPDATE politica_dt SET stato='E',domanda_rimborso_dt=NULL,motivo = ? WHERE idpolitica_dt = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "anomaliaRimborso_DOTE" + ex.getMessage());
        }
        return false;
    }

    public boolean scartoRimborsoCondition_DOTE(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica_dt SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE idrimborso_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE politica_dt SET stato='K',domanda_rimborso_dt=NULL,motivo = ? WHERE idpolitica_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "scartoRimborsoCondition_DOTE:" + e.getMessage());
        }
        return false;

    }

    public boolean scartaRimborso_DOTE(String id, String motivo) {
        try {
            String sql = "UPDATE politica_dt SET stato='K',domanda_rimborso_dt=NULL, motivo=? WHERE idpolitica_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "scartaRimborso_DOTE" + ex.getMessage());
        }
        return false;
    }

    public ArrayList<Politica> getRimborsiPolitiche_DOTE(String politica, String idrimborso) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_dt,p.dataavvio, l.nome, l.cognome, l.codice_fiscale, t.nome as t_nome, t.cognome as t_cognome, p.durataeffettiva "
                    + "FROM politica_dt as p, tutor as t, Lavoratore as l "
                    + "WHERE p.domanda_rimborso_dt=? and p.codazioneformcal=? and p.tutor=t.idtutor and p.lavoratore=l.cdnlavoratore and p.stato='A' ";
            if (politica.equals("D2") || politica.equals("D5")) {
                sql = "SELECT p.idpolitica_dt,p.dataavvio, l.nome, l.cognome, l.codice_fiscale, p.durataeffettiva "
                        + "FROM politica_dt as p, Lavoratore as l "
                        + "WHERE p.domanda_rimborso_dt=? and p.codazioneformcal=? and p.lavoratore=l.cdnlavoratore AND p.stato='A' ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, politica);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                if (!politica.equals("D2") && !politica.equals("D5")) {
                    tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                }
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiPolitichegetRimborsiPolitiche_DOTE " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Politica> getScartiPolitiche_DOTE(String idrimborso, String politica) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_dt,p.dataavvio,p.durataeffettiva, l.nome, l.cognome, l.codice_fiscale, t.nome as t_nome, t.cognome as t_cognome, s.motivo "
                    + "FROM scarti_politiche_dt as s , politica_dt as p, tutor as t, Lavoratore as l "
                    + "WHERE id_politica_dt=idpolitica_dt and id_rimborso_dt=? and p.tutor=t.idtutor and p.lavoratore=l.cdnlavoratore;";
            if (politica.equals("D2") || politica.equals("D5")) {
                sql = "SELECT p.idpolitica_dt,p.dataavvio,p.durataeffettiva, l.nome, l.cognome, l.codice_fiscale, s.motivo "
                        + "FROM scarti_politiche_dt as s , politica_dt as p, Lavoratore as l "
                        + "WHERE id_politica_dt=idpolitica_dt and id_rimborso_dt=? and p.lavoratore=l.cdnlavoratore;";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                if (!politica.equals("D2") && !politica.equals("D5")) {
                    tmp.setNomeTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                }
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getScartiPolitiche_DOTE " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public boolean uploadDocContrattoDt(String id, String tutor, String m5, String file, String contratto, String tipo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE politica_dt SET tutor=?, doc_m5=?, doc_ragazzo=?, stato='S' WHERE idpolitica_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, tutor);
            ps.setString(2, m5);
            ps.setString(3, file);
            ps.setString(4, id);
            ps.execute();

            if (tipo.equals("new")) {
                sql = "INSERT INTO contratti_dt SET idcontratti_dt=?, file=?";
                PreparedStatement ps2 = this.c.prepareStatement(sql);
                ps2.setString(1, id);
                ps2.setString(2, contratto);
                ps2.executeUpdate();
            } else if (tipo.equals("mod")) {
                sql = "UPDATE contratti_dt SET file=? WHERE idcontratti_dt=?";
                PreparedStatement ps2 = this.c.prepareStatement(sql);
                ps2.setString(1, contratto);
                ps2.setString(2, id);
                ps2.executeUpdate();
            }
            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "uploadDoc_Dt" + ex.getMessage());
        }
        return false;
    }

    public Contratto getContrattoByIdDt(int id) {
        Contratto out = new Contratto();
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String sql = "SELECT * FROM contratti_dt WHERE idcontratti_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
//                if (rs.getString("data_fine") != null) {
//                    out.setData_fine(sdf2.format(sdf1.parse(rs.getString("data_fine"))));
//                } else {
//                    out.setData_fine(rs.getString("data_fine"));
//                }
//                out.setData_inizio(sdf2.format(sdf1.parse(rs.getString("data_inizio"))));
                out.setFile(rs.getString("file"));
//                out.setIndeterminato(rs.getString("indeterminato"));
                return out;
            }
            return null;
        } catch (SQLException ex) {
            Action.insertTracking("service", "getContrattoByIdDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
//        } catch (ParseException ex) {
//            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean Rev_rigettaRimborso_DOTE(String id, String motivo) {
        try {
            String sql = "UPDATE rimborso_politica_dt SET stato='E2',motivo=? WHERE idrimborso_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_rigettaRimborso_DOTE" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_scartaTuttoRimborso_DOTE(String id, String motivo, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica_dt SET stato='K',motivo=?,check_list=? WHERE idrimborso_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, path);
            ps.setString(3, id);
            ps.execute();

            String sql1 = "UPDATE politica_dt SET stato='E',domanda_rimborso_dt=NULL,motivo = ? WHERE domanda_rimborso_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Rev_scartaTuttoRimborso_DOTE:" + e.getMessage());
        }
        return false;
    }

    public boolean Rev_liquidaRimborso_DOTE(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        try {
            String sql = "UPDATE rimborso_politica_dt SET motivo=?, check_list=?,tot_erogato=?,rimborso=?,stato='R2' WHERE idrimborso_dt=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, descrizione);
            ps.setString(2, path);
            ps.setString(3, totale);
            ps.setString(4, ctrlrimborso);
            ps.setString(5, idrimborso);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Rev_liquidaRimborso_DOTE:" + e.getMessage());
        }
        return false;
    }

    public String[] getListDocsTirocinanteD2D5_DOTE(String politica) {
        try {
            //String[] out = new ArrayList<>();
            String sql = "SELECT p.idpolitica_dt,l.nome, l.cognome,l.codice_fiscale,p.doc_ragazzo,p.doc_m5 "
                    + "FROM politica_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore AND p.idpolitica_dt = ? AND p.stato='A' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, politica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] tmp = {rs.getString("p.idpolitica_dt"), rs.getString("l.nome"), rs.getString("l.cognome"), rs.getString("l.codice_fiscale"), rs.getString("p.doc_ragazzo"), rs.getString("p.doc_m5")};
                return tmp;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getListDocsTirocinanteD2D5_DOTE " + ex.getMessage());
        }
        return null;
    }

    public ArrayList<B3> getPoliticaB3_DT(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        ArrayList<B3> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(politica);
            pm.add(cfente);
            pm.add(stato);

            String sql = "SELECT p.idpolitica_B3_dt,l.nome, l.cognome,p.dataavvio, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.doc_ragazzo, p.doc_registro, p.doc_timesheet, doc_businessplan "
                    + "FROM politica_B3_dt as p, Lavoratore as l "
                    + "WHERE p.codazioneformcal=? and p.cf_ente=? "
                    + "and p.lavoratore=l.cdnlavoratore and stato=?  ";

            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            if (nome.equals("") && cognome.equals("") && cf.equals("") && from.equals("") && to.equals("")) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                B3 tmp = new B3();
                tmp.setId(rs.getInt("idpolitica_B3_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setDoc_registro(rs.getString("doc_registro"));
                tmp.setDoc_timesheet(rs.getString("doc_timesheet"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                tmp.setDoc_businessplan(rs.getString("doc_businessplan"));

                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getPoliticaB3_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public Map<String, String[]> get_ADInfos_DOTE_B3() {
        Map<String, String[]> result = new HashMap<>();
        try {
            String sql = "SELECT B3.idrimborso_B3_dt,tutor.nome,tutor.cognome,ente_promotore.ragionesociale,B3.doc_ad_au,ente_promotore.idente_promotore "
                    + "FROM rimborso_politica_B3_dt as B3,tutor,ente_promotore "
                    + "WHERE tutor.idtutor = B3.ad_au AND tutor.ente_promotore = ente_promotore.idente_promotore";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("ente_promotore.ragionesociale"), rs.getString("tutor.cognome") + " " + rs.getString("tutor.nome"), rs.getString("B3.doc_ad_au"), rs.getString("ente_promotore.idente_promotore")};
                result.put(rs.getString("B3.idrimborso_B3_dt"), temp);
            }
            return result;
        } catch (SQLException ex) {
            Action.insertTracking("", "get_ADInfos_DOTE_B3 " + ex.getMessage());
            return null;
        }
    }

    public ArrayList<Rimborso> getListRimborsi_DOTE_B3(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);

            String sql = "SELECT r.* FROM rimborso_politica_B3_dt as r, tutor as t, ente_promotore as e WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            sql += " ORDER BY data_up ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso_B3_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setRimborso(rs.getString("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsi_DOTE_B3 " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean uploadProtocollo_DOTE_B3(String id, String protocollo, String path) {
        try {
            String sql = "UPDATE rimborso_politica_B3_dt SET protocollo=?, documento=?,stato='N' WHERE idrimborso_B3_dt=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, protocollo);
            ps.setString(2, path);
            ps.setString(3, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadProtocollo_DOTE_B3:" + e.getMessage());
        }
        return false;
    }

    public ArrayList<Politica> getListPolitiche_DOTE_B3(String domanda_rimborso) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_B3_dt,l.nome,l.cognome,p.dataavvio,l.codice_fiscale,p.doc_ragazzo,p.domanda_rimborso_B3_dt,p.codazioneformcal,p.profiling,p.lavoratore,p.cf_ente "
                    + "FROM politica_B3_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore AND p.domanda_rimborso_B3_dt= ? "
                    + "AND p.stato='A'  ;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_B3_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                //tmp.setDoc_m5(rs.getString("doc_m5"));
                tmp.setRimborso(rs.getInt("domanda_rimborso_B3_dt"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setIdlav(rs.getString("lavoratore"));
                tmp.setIdente(rs.getString("cf_ente"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListPolitiche_DOTE_B3 " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public String[] getListDocsTirocinante_DOTE_B3(String politica) {
        try {
            String sql = "SELECT p.idpolitica_B3_dt,l.nome, l.cognome,l.codice_fiscale,p.doc_ragazzo,p.doc_registro,p.doc_timesheet,p.doc_businessplan "
                    + "FROM politica_B3_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore "
                    + "AND p.idpolitica_B3_dt = ? AND p.stato='A' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, politica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] tmp = {rs.getString("p.idpolitica_B3_dt"), rs.getString("l.nome"), rs.getString("l.cognome"), rs.getString("l.codice_fiscale"), rs.getString("p.doc_ragazzo"), rs.getString("p.doc_registro"), rs.getString("p.doc_timesheet"), rs.getString("p.doc_businessplan")};
                return tmp;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getListDocsTirocinante_DOTE_B3 " + ex.getMessage());
        }
        return null;
    }

    public boolean accettaRimborsi_DOTE_B3(String id) {
        try {
            String sql = "UPDATE rimborso_politica_B3_dt SET stato='R' WHERE idrimborso_B3_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "accettaRimborsi_DOTE_B3" + ex.getMessage());
        }
        return false;
    }

    public boolean rigettaRimborsi_DOTE_B3(String id, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica_B3_dt SET stato='K',motivo=? WHERE idrimborso_B3_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            ps.execute();

            String sql1 = "UPDATE politica_B3_dt SET stato='E',motivo=?,domanda_rimborso_B3_dt=NULL WHERE domanda_rimborso_B3_dt=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "rigettaRimborsi_DOTE_B3:" + e.getMessage());
        }
        return false;
    }

    public int numPoliticheRimborso_DOTE_B3(String id, String idpolitica) {
        int n = 0;
        try {
            String sql = "SELECT count(*) as n FROM politica_B3_dt WHERE domanda_rimborso_B3_dt = ? AND stato = 'A' AND idpolitica_B3_dt <> ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, idpolitica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                n = rs.getInt("n");
            }
            return n;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "numPoliticheRimborso_DOTE_B3" + ex.getMessage());
        }
        return n;
    }

    public boolean scartoRimborsoCondition_DOTE_B3(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica_B3_dt SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE idrimborso_B3_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE politica_B3_dt SET stato='K',domanda_rimborso_B3_dt=NULL,motivo = ? WHERE idpolitica_B3_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "scartoRimborsoCondition_DOTE_B3:" + e.getMessage());
        }
        return false;

    }

    public boolean insertScartiPolitiche_DOTE_B3(String id_rimborso, String id_politica, String motivo) {
        try {
            String sql = "INSERT INTO scarti_politiche_B3_dt SET id_rimborso_B3_dt=?, id_politica_B3_dt=?, motivo=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id_rimborso);
            ps.setString(2, id_politica);
            ps.setString(3, motivo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Action.insertTracking("service", "insertScartiPolitiche_DOTE_B3 " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public boolean scartaRimborso_DOTE_B3(String id, String motivo) {
        try {
            String sql = "UPDATE politica_B3_dt SET stato='K',domanda_rimborso_B3_dt=NULL, motivo=? WHERE idpolitica_B3_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "scartaRimborso_DOTE_B3" + ex.getMessage());
        }
        return false;
    }

    public B3 getB3ByIdDt(String id) {
        B3 out = new B3();
        try {
            String sql = "SELECT doc_ragazzo, doc_registro, doc_timesheet, doc_businessplan FROM politica_B3_dt WHERE idpolitica_B3_dt = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setId(Integer.parseInt(id));
                out.setDoc_businessplan(rs.getString("doc_businessplan"));
                out.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                out.setDoc_timesheet(rs.getString("doc_timesheet"));
                out.setDoc_registro(rs.getString("doc_registro"));
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getB3ByIdDt " + ex.getMessage());
        }
        return out;
    }

    public boolean uploadDecreto_DOTE_B3(String idrimborso, String repertorio, String datar, String decreto, String datad, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO decreto_B3_dt (fk_idrimborso_B3_dt,num_assunzione,data_assunzione,num_decreto,data_decreto,path_decreto) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, repertorio);
            ps.setString(3, datar);
            ps.setString(4, decreto);
            ps.setString(5, datad);
            ps.setString(6, path);
            ps.execute();

            String sql1 = "UPDATE rimborso_politica_B3_dt SET stato='P' WHERE idrimborso_B3_dt=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, idrimborso);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadDecreto_DOTE_B3:" + e.getMessage());
        }
        return false;
    }

    public boolean Rev_scartaTuttoRimborso_DOTE_B3(String id, String motivo, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica_B3_dt SET stato='K',motivo=?,check_list=? WHERE idrimborso_B3_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, path);
            ps.setString(3, id);
            ps.execute();

            String sql1 = "UPDATE politica_B3_dt SET stato='E',domanda_rimborso_B3_dt=NULL,motivo = ? WHERE domanda_rimborso_B3_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Rev_scartaTuttoRimborso_DOTE_B3:" + e.getMessage());
        }
        return false;
    }

    public boolean Rev_rigettaRimborso_DOTE_B3(String id, String motivo) {
        try {
            String sql = "UPDATE rimborso_politica_B3_dt SET stato='E2',motivo=? WHERE idrimborso_B3_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_rigettaRimborso_DOTE_B3" + ex.getMessage());
        }
        return false;
    }

    public boolean Rev_liquidaRimborso_DOTE_B3(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        try {
            String sql = "UPDATE rimborso_politica_B3_dt SET motivo=?, check_list=?,tot_erogato=?,rimborso=?,stato='R2' WHERE idrimborso_B3_dt=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, descrizione);
            ps.setString(2, path);
            ps.setString(3, totale);
            ps.setString(4, ctrlrimborso);
            ps.setString(5, idrimborso);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Rev_liquidaRimborso_DOTE_B3:" + e.getMessage());
        }
        return false;
    }

    public boolean uploadDocB3(String id, String file, String registro, String business, String timesheet) {
        try {
            String sql = "UPDATE politica_B3_dt SET doc_ragazzo=?, doc_registro=?, doc_businessplan=?, doc_timesheet=?, stato='S' WHERE idpolitica_B3_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, file);
            ps.setString(2, registro);
            ps.setString(3, business);
            ps.setString(4, timesheet);
            ps.setString(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "uploadDocB3 " + ex.getMessage());
        }
        return false;
    }

    public boolean associaPoliticheRimborsoB3Dt(String data, int id) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE politica_B3_dt SET domanda_rimborso_B3_dt=?, stato='A' WHERE idpolitica_B3_dt=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idpolitica_B3_dt=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            ps.setInt(1, id);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 2, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "associaPoliticheRimborsoB3Dt " + ex.getMessage());
        }
        return false;
    }

    public int insertRimborsoB3Dt(String tutor, String politica, String doc_ad_au) {

        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO rimborso_politica_B3_dt SET ad_au=?, data_up= CURDATE(), politica=?, doc_ad_au=?";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tutor);
            ps.setString(2, politica);
            ps.setString(3, doc_ad_au);

            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "insertRimborsoB3Dt" + ex.getMessage());
        }

        return 0;
    }

    public ArrayList<B3> getListB3PDFDt(String ids) {
        ArrayList<B3> out = new ArrayList<>();
        String[] s = ids.split(",");
        try {
            String sql = "SELECT p.idpolitica_B3_dt,l.nome, l.cognome,p.dataavvio, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.profiling, p.rimborso "
                    + "FROM politica_B3_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore  and (";

            for (int i = 1; i < s.length + 1; i++) {
                sql += " idpolitica_B3_dt=? OR";
            }
            sql = sql.substring(0, sql.length() - 3);
            sql += " )";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < s.length; i++) {

                ps.setString(i + 1, s[i]);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                B3 tmp = new B3();
                tmp.setId(rs.getInt("idpolitica_B3_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getListB3PDFDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Politica> getRimborsiPolitiche_DOTE_B3(String politica, String idrimborso) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_B3_dt,p.dataavvio, l.nome, l.cognome, l.codice_fiscale "
                    + "FROM politica_B3_dt as p, Lavoratore as l "
                    + "WHERE p.domanda_rimborso_B3_dt=? and p.codazioneformcal=? and p.lavoratore=l.cdnlavoratore AND p.stato='A' ";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, politica);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_B3_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiPolitiche_DOTE_B3 " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Politica> getScartiPolitiche_DOTE_B3(String idrimborso) {
        ArrayList<Politica> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_B3_dt,p.dataavvio, l.nome, l.cognome, l.codice_fiscale, s.motivo "
                    + "FROM scarti_politiche_B3_dt as s , politica_B3_dt as p, Lavoratore as l "
                    + "WHERE id_politica_B3_dt=idpolitica_B3_dt AND id_rimborso_B3_dt=? AND p.lavoratore=l.cdnlavoratore;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Politica tmp = new Politica();
                tmp.setId(rs.getInt("idpolitica_B3_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getScartiPolitiche_DOTE_B3 " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Rimborso> getListRimborsiEnteB3Dt(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        ArrayList<Rimborso> out = new ArrayList<>();
        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);
            pm.add(String.valueOf(idente));

            String sql = "";

            if (!stato.equals("P")) {
                sql = "SELECT r.idrimborso_B3_dt, r.data_up, r.motivo, r.stato, r.ad_au, r.timestamp, r.politica, r.protocollo, r.documento, r.tot_erogato, r.check_list "
                        + "FROM rimborso_politica_B3_dt as r, tutor as t, ente_promotore as e "
                        + "WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            } else if (stato.equals("P")) {
                sql = "SELECT r.idrimborso_B3_dt, r.data_up, r.motivo, r.stato, r.ad_au, r.timestamp, r.politica, r.protocollo, r.documento, r.tot_erogato, r.check_list, d.path_decreto "
                        + "FROM rimborso_politica_B3_dt as r, tutor as t, ente_promotore as e, decreto_B3_dt as d "
                        + "WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND r.idrimborso_B3_dt=d.fk_idrimborso_B3_dt AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            }

            if (!protocollo.equals("")) {
                sql += " AND r.protocollo=? ";
                pm.add(protocollo);
                flag = true;
            }
            if (!from_up.equals("") && !to_up.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_up);
                pm.add(to_up);
                flag = true;
            }
            if (!from_mod.equals("") && !to_mod.equals("")) {
                sql += " AND r.timestamp BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_mod);
                pm.add(to_mod);
                flag = true;
            }
            sql += " ORDER BY timestamp DESC ";

            if (!flag) {
                sql += "LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso_B3_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                if (stato.equals("P")) {
                    tmp.setPath_decreto(rs.getString("path_decreto"));
                }
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsiEnteB3Dt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<B3> getListB3Dt(String domanda_rimborso) {
        ArrayList<B3> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_B3_dt,l.nome,l.cognome,p.dataavvio,l.codice_fiscale,p.doc_ragazzo,doc_registro,doc_timesheet,p.doc_businessplan,p.domanda_rimborso_B3_dt,p.codazioneformcal,p.profiling,p.rimborso "
                    + "FROM politica_B3_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore "
                    + "AND p.domanda_rimborso_B3_dt=? AND p.stato='A'  ;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                B3 tmp = new B3();
                tmp.setId(rs.getInt("idpolitica_B3_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                tmp.setDoc_timesheet(rs.getString("doc_timesheet"));
                tmp.setDoc_registro(rs.getString("doc_registro"));
                tmp.setDoc_businessplan(rs.getString("doc_businessplan"));
                tmp.setRimborso(rs.getInt("domanda_rimborso_B3_dt"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListB3Dt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public boolean anomaliaRimborso_DOTE_B3(String id, String motivo) {
        try {
            String sql = "UPDATE politica_B3_dt SET stato='E',domanda_rimborso_B3_dt=NULL,motivo = ? WHERE idpolitica_B3_dt = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "anomaliaRimborso_DOTE_B3" + ex.getMessage());
        }
        return false;
    }

    public boolean AnomalieRimborsoCondition_DOTE_B3(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_politica_B3_dt SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE idrimborso_B3_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE politica_B3_dt SET stato='E',domanda_rimborso_B3_dt=NULL,motivo = ? WHERE idpolitica_B3_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "AnomalieRimborsoCondition_DOTE_B3:" + e.getMessage());
        }
        return false;

    }

    public ArrayList<B3> getRimborsiPoliticheB3Dt(String politica, String idrimborso) {
        ArrayList<B3> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_B3_dt,p.dataavvio, l.nome, l.cognome, l.codice_fiscale "
                    + "FROM politica_B3_dt as p, Lavoratore as l "
                    + "WHERE p.domanda_rimborso_B3_dt=? and p.codazioneformcal=? "
                    + "and p.lavoratore=l.cdnlavoratore and p.stato='A'";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, politica);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                B3 tmp = new B3();
                tmp.setId(rs.getInt("idpolitica_B3_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiPoliticheB3Dt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<B3> getScartiB3Dt(String idrimborso, String politica) {
        ArrayList<B3> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idpolitica_B3_dt,p.dataavvio, l.nome, l.cognome, l.codice_fiscale, s.motivo "
                    + "FROM scarti_politiche_B3_dt as s , politica_B3_dt as p, Lavoratore as l "
                    + "WHERE id_politica_B3_dt=idpolitica_B3_dt and id_rimborso_B3_dt=? "
                    + "and p.lavoratore=l.cdnlavoratore;";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                B3 tmp = new B3();
                tmp.setId(rs.getInt("idpolitica_B3_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getScartiB3Dt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Voucher> getVoucher_DT(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        ArrayList<Voucher> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(politica);
            pm.add(cfente);
            pm.add(stato);

            String sql = "SELECT p.idvoucher_dt,l.nome, l.cognome, p.dataavvio, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.doc_registro, p.doc_ragazzo, p.doc_allegato, p.doc_attestato, p.doc_delega, p.ore, p.voucher "
                    + "FROM voucher_dt as p, Lavoratore as l "
                    + "WHERE p.codazioneformcal=? and p.cf_ente=? "
                    + "and p.lavoratore=l.cdnlavoratore and stato=?  ";

            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            if (nome.equals("") && cognome.equals("") && cf.equals("") && from.equals("") && to.equals("")) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher tmp = new Voucher();
                tmp.setId(rs.getInt("idvoucher_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setDoc_registro(rs.getString("doc_registro"));
                tmp.setDoc_allegato(rs.getString("doc_allegato"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                tmp.setDoc_attestato(rs.getString("doc_attestato"));
                tmp.setDoc_delega(rs.getString("doc_delega"));
                tmp.setOre(rs.getInt("ore"));
                tmp.setVoucher(rs.getDouble("voucher"));

                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getVoucher_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public Voucher getVoucherByIdDt(String id) {
        Voucher out = new Voucher();
        try {
            String sql = "SELECT doc_ragazzo, doc_registro, doc_allegato, doc_attestato, doc_delega, ore, voucher FROM voucher_dt WHERE idvoucher_dt = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setId(Integer.parseInt(id));
                out.setDoc_attestato(rs.getString("doc_attestato"));
                out.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                out.setDoc_delega(rs.getString("doc_delega"));
                out.setDoc_registro(rs.getString("doc_registro"));
                out.setDoc_allegato(rs.getString("doc_allegato"));
                out.setOre(rs.getInt("ore"));
                out.setVoucher(rs.getDouble("voucher"));
            }
            return out;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getVoucherByIdDt " + ex.getMessage());
        }
        return out;
    }

    public Map<String, String[]> get_ADInfos_Voucher_DT() {
        Map<String, String[]> result = new HashMap<>();
        try {
            String sql = "SELECT v.idrimborso_voucher_dt,tutor.nome,tutor.cognome,ente_promotore.ragionesociale,v.doc_ad_au,ente_promotore.idente_promotore "
                    + "FROM rimborso_voucher_dt as v,tutor,ente_promotore "
                    + "WHERE tutor.idtutor = v.ad_au AND tutor.ente_promotore = ente_promotore.idente_promotore";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] temp = {rs.getString("ente_promotore.ragionesociale"), rs.getString("tutor.cognome") + " " + rs.getString("tutor.nome"), rs.getString("v.doc_ad_au"), rs.getString("ente_promotore.idente_promotore")};
                result.put(rs.getString("v.idrimborso_voucher_dt"), temp);
            }
            return result;
        } catch (SQLException ex) {
            Action.insertTracking("", "get_ADInfos_Voucher_DT " + ex.getMessage());
            return null;
        }
    }

    public boolean uploadDocVoucher(String id, String file, String registro, String attestato, String allegato, String delega, String voucher, String ore) {
        try {
            String sql = "UPDATE voucher_dt SET doc_ragazzo=?, doc_registro=?, doc_attestato=?, doc_allegato=?, doc_delega=?, voucher=?, ore=?, stato='S' WHERE idvoucher_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, file);
            ps.setString(2, registro);
            ps.setString(3, attestato);
            ps.setString(4, allegato);
            ps.setString(5, delega);
            ps.setString(6, voucher);
            ps.setString(7, ore);
            ps.setString(8, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "uploadDocVoucher " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<Rimborso> getListRimborsi_Voucher_DT(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);

            String sql = "SELECT v.* FROM rimborso_voucher_dt as v, tutor as t, ente_promotore as e WHERE v.politica = ? AND v.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND v.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            sql += " ORDER BY data_up ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso_voucher_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setRimborso(rs.getString("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsi_Voucher_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean uploadProtocollo_Voucher_DT(String id, String protocollo, String path) {
        try {
            String sql = "UPDATE rimborso_voucher_dt SET protocollo=?, documento=?,stato='N' WHERE idrimborso_voucher_dt=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, protocollo);
            ps.setString(2, path);
            ps.setString(3, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadProtocollo_Voucher_DT: " + e.getMessage());
        }
        return false;
    }

    public boolean accettaRimborsi_Voucher_DT(String id) {
        try {
            String sql = "UPDATE rimborso_voucher_dt SET stato='R' WHERE idrimborso_voucher_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "accettaRimborsi_Voucher_DT " + ex.getMessage());
        }
        return false;
    }

    public boolean rigettaRimborsi_Voucher_DT(String id, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_voucher_dt SET stato='K',motivo=? WHERE idrimborso_voucher_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            ps.execute();

            String sql1 = "UPDATE voucher_dt SET stato='E',motivo=?,domanda_rimborso_voucher_dt=NULL WHERE domanda_rimborso_voucher_dt=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "rigettaRimborsi_Voucher_DT: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<Voucher> getListVouchers(String domanda_rimborso) {
        ArrayList<Voucher> out = new ArrayList<>();
        try {
            String sql = "SELECT v.idvoucher_dt,l.nome,l.cognome,v.dataavvio,l.codice_fiscale,v.doc_ragazzo,v.domanda_rimborso_voucher_dt,v.codazioneformcal,v.profiling,v.voucher,v.ore "
                    + "FROM voucher_dt as v, Lavoratore as l "
                    + "WHERE v.lavoratore = l.cdnlavoratore "
                    + "AND v.domanda_rimborso_voucher_dt = ? AND v.stato='A' ;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher tmp = new Voucher();
                tmp.setId(rs.getInt("idvoucher_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                tmp.setRimborso(rs.getInt("domanda_rimborso_voucher_dt"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setOre(rs.getInt("ore"));
                tmp.setVoucher(rs.getDouble("voucher"));
                //tmp.setDatafine(rs.getString("datafine"));
                //tmp.setDurataeffettiva(rs.getInt("durataeffettiva"));
                //tmp.setTutor(rs.getInt("tutor"));
                //tmp.setDoc_m5(rs.getString("doc_m5"));

                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListVouchers " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public String[] getListDocsTirocinante_Voucher(String politica) {
        try {
            //String[] out = new ArrayList<>();
            String sql = "SELECT v.idvoucher_dt,l.nome, l.cognome,l.codice_fiscale,v.doc_ragazzo,v.doc_allegato,v.doc_attestato,v.doc_delega "
                    + "FROM voucher_dt as v, Lavoratore as l "
                    + "WHERE v.lavoratore=l.cdnlavoratore AND v.idvoucher_dt = ? AND v.stato='A' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, politica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] tmp = {rs.getString("v.idvoucher_dt"), rs.getString("l.nome"), rs.getString("l.cognome"), rs.getString("l.codice_fiscale"), rs.getString("v.doc_ragazzo"), rs.getString("v.doc_allegato"), rs.getString("v.doc_attestato"), rs.getString("v.doc_delega")};
                return tmp;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getListDocsTirocinante_Voucher " + ex.getMessage());
        }
        return null;
    }

    public int numPoliticheRimborso_Voucher_DT(String id, String idpolitica) {
        int n = 0;
        try {
            String sql = "SELECT count(*) as n FROM voucher_dt WHERE domanda_rimborso_voucher_dt = ? AND stato = 'A' AND idvoucher_dt <> ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, idpolitica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                n = rs.getInt("n");
            }
            return n;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "numPoliticheRimborso_Voucher_DT:" + ex.getMessage());
        }
        return n;
    }

    public boolean anomaliaRimborsoCondition_Voucher_DT(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_voucher_dt SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE idrimborso_voucher_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE voucher_dt SET stato='E',domanda_rimborso_voucher_dt=NULL,motivo = ? WHERE idvoucher_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "anomaliaRimborsoCondition_Voucher_DT: " + e.getMessage());
        }
        return false;

    }

    public boolean insertScartiPolitiche_Voucher_DT(String id_rimborso, String id_politica, String motivo) {
        try {
            String sql = "INSERT INTO scarti_voucher_dt SET id_rimborso_voucher_dt=?, id_voucher_dt=?, motivo=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id_rimborso);
            ps.setString(2, id_politica);
            ps.setString(3, motivo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Action.insertTracking("service", "insertScartiPolitiche_Voucher_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public boolean anomaliaRimborso_Voucher_DT(String id, String motivo) {
        try {
            String sql = "UPDATE voucher_dt SET stato='E',domanda_rimborso_voucher_dt=NULL,motivo = ? WHERE idvoucher_dt = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "anomaliaRimborso_Voucher_DT " + ex.getMessage());
        }
        return false;
    }

    public boolean scartoRimborsoCondition_Voucher_DT(String id, String idp, String motivo) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_voucher_dt SET stato='K',motivo='Tutti i singoli rimborsi sono stati scartati' WHERE idrimborso_voucher_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();

            String sql1 = "UPDATE voucher_dt SET stato='K',domanda_rimborso_voucher_dt=NULL,motivo = ? WHERE idvoucher_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, idp);
            ps1.executeUpdate();

            this.c.commit();
            return true;
        } catch (Exception e) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "scartoRimborsoCondition_Voucher_DT: " + e.getMessage());
        }
        return false;

    }

    public boolean scartaRimborso_Voucher_DT(String id, String motivo) {
        try {
            String sql = "UPDATE voucher_dt SET stato='K',domanda_rimborso_voucher_dt=NULL, motivo=? WHERE idvoucher_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "scartaRimborso_Voucher_DT" + ex.getMessage());
        }
        return false;
    }

    public boolean uploadDecreto_Voucher_DT(String idrimborso, String repertorio, String datar, String decreto, String datad, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO decreto_voucher_dt (fk_idrimborso_voucher_dt,num_assunzione,data_assunzione,num_decreto,data_decreto,path_decreto) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, repertorio);
            ps.setString(3, datar);
            ps.setString(4, decreto);
            ps.setString(5, datad);
            ps.setString(6, path);
            ps.execute();

            String sql1 = "UPDATE rimborso_voucher_dt SET stato='P' WHERE idrimborso_voucher_dt=?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, idrimborso);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "uploadDecreto_Voucher_DT: " + e.getMessage());
        }
        return false;
    }

    public int insertRimborsoVoucherDt(String tutor, String politica, String doc_ad_au) {

        try {
            this.c.setAutoCommit(false);
            String sql = "INSERT INTO rimborso_voucher_dt SET ad_au=?, data_up= CURDATE(), politica=?, doc_ad_au=?";
            PreparedStatement ps = this.c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tutor);
            ps.setString(2, politica);
            ps.setString(3, doc_ad_au);

            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            int id = 0;
            while (keys.next()) {
                id = keys.getInt(1);
            }
            this.c.commit();
            return id;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "insertRimborsoVoucherDt " + ex.getMessage());
        }

        return 0;
    }

    public boolean associaRimborsoVoucherDt(String data, int id) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE voucher_dt SET domanda_rimborso_voucher_dt=?, stato='A' WHERE idvoucher_dt=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idvoucher_dt=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            ps.setInt(1, id);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 2, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "associaRimborsoVoucherDt " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<Voucher> getListVoucherPDFDt(String ids) {
        ArrayList<Voucher> out = new ArrayList<>();
        String[] s = ids.split(",");
        try {
            String sql = "SELECT p.idvoucher_dt,l.nome, l.cognome,p.dataavvio, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.profiling, p.ore, p.voucher, p.rimborso "
                    + "FROM voucher_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore  and (";

            for (int i = 1; i < s.length + 1; i++) {
                sql += " idvoucher_dt=? OR";
            }
            sql = sql.substring(0, sql.length() - 3);
            sql += " )";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < s.length; i++) {

                ps.setString(i + 1, s[i]);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Voucher tmp = new Voucher();
                tmp.setId(rs.getInt("idvoucher_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setOre(rs.getInt("ore"));
                tmp.setVoucher(rs.getDouble("voucher"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getListVoucherPDFDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Voucher> getRimborsiVoucher(String politica, String idrimborso) {
        ArrayList<Voucher> out = new ArrayList<>();
        try {
            String sql = "SELECT v.idvoucher_dt,v.dataavvio, l.nome, l.cognome, l.codice_fiscale, v.ore "
                    + "FROM voucher_dt as v, Lavoratore as l "
                    + "WHERE v.domanda_rimborso_voucher_dt = ? and v.codazioneformcal= ? "
                    + "AND v.lavoratore=l.cdnlavoratore and v.stato='A' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, politica);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher tmp = new Voucher();
                tmp.setId(rs.getInt("idvoucher_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setOre(rs.getInt("ore"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiVoucher " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Voucher> getScartiPolitiche_Voucher_DT(String idrimborso) {
        ArrayList<Voucher> out = new ArrayList<>();
        try {
            String sql = "SELECT v.idvoucher_dt,v.dataavvio,v.ore, l.nome, l.cognome, l.codice_fiscale, sv.motivo "
                    + "FROM scarti_voucher_dt as sv , voucher_dt as v, Lavoratore as l "
                    + "WHERE sv.id_voucher_dt=v.idvoucher_dt and sv.id_rimborso_voucher_dt= ? "
                    + "AND v.lavoratore=l.cdnlavoratore;";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher tmp = new Voucher();
                tmp.setId(rs.getInt("idvoucher_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setOre(rs.getInt("ore"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getScartiPolitiche_Voucher_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Rimborso> getListRimborsiEnteVoucherDt(String politica, String stato, int idente, String protocollo, String from_up, String to_up, String from_mod, String to_mod) {
        ArrayList<Rimborso> out = new ArrayList<>();
        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            pm.add(stato);
            pm.add(String.valueOf(idente));

            String sql = "";

            if (!stato.equals("P")) {
                sql = "SELECT r.idrimborso_voucher_dt, r.data_up, r.motivo, r.stato, r.ad_au, r.timestamp, r.politica, r.protocollo, r.documento, r.tot_erogato, r.check_list "
                        + "FROM rimborso_voucher_dt as r, tutor as t, ente_promotore as e "
                        + "WHERE r.politica = ? AND r.stato = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            } else if (stato.equals("P")) {
                sql = "SELECT r.idrimborso_voucher_dt, r.data_up, r.motivo, r.stato, r.ad_au, r.timestamp, r.politica, r.protocollo, r.documento, r.tot_erogato, r.check_list, d.path_decreto "
                        + "FROM rimborso_voucher_dt as r, tutor as t, ente_promotore as e, decreto_voucher_dt as d "
                        + "WHERE r.politica = ? AND r.stato = ? AND r.idrimborso_voucher_dt=d.fk_idrimborso_voucher_dt AND ad_au=idtutor AND idente_promotore=t.ente_promotore AND idente_promotore=? ";
            }

            if (!protocollo.equals("")) {
                sql += " AND r.protocollo=? ";
                pm.add(protocollo);
                flag = true;
            }
            if (!from_up.equals("") && !to_up.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_up);
                pm.add(to_up);
                flag = true;
            }
            if (!from_mod.equals("") && !to_mod.equals("")) {
                sql += " AND r.timestamp BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from_mod);
                pm.add(to_mod);
                flag = true;
            }
            sql += " ORDER BY timestamp DESC ";

            if (!flag) {
                sql += "LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso_voucher_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                if (stato.equals("P")) {
                    tmp.setPath_decreto(rs.getString("path_decreto"));
                }
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsiEnteVoucherDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Voucher> getListVoucherDt(String domanda_rimborso) {
        ArrayList<Voucher> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idvoucher_dt,l.nome,l.cognome,p.dataavvio,l.codice_fiscale,p.doc_ragazzo,doc_registro,doc_allegato,p.doc_attestato,p.doc_delega,p.domanda_rimborso_voucher_dt,p.codazioneformcal,p.profiling,p.ore,p.voucher,p.rimborso "
                    + "FROM voucher_dt as p, Lavoratore as l "
                    + "WHERE p.lavoratore=l.cdnlavoratore "
                    + "AND p.domanda_rimborso_voucher_dt=? AND p.stato='A'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, domanda_rimborso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher tmp = new Voucher();
                tmp.setId(rs.getInt("idvoucher_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDoc_ragazzo(rs.getString("doc_ragazzo"));
                tmp.setDoc_allegato(rs.getString("doc_allegato"));
                tmp.setDoc_attestato(rs.getString("doc_attestato"));
                tmp.setDoc_delega(rs.getString("doc_delega"));
                tmp.setDoc_registro(rs.getString("doc_registro"));
                tmp.setRimborso(rs.getInt("domanda_rimborso_voucher_dt"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setProfiling(rs.getInt("profiling"));
                tmp.setOre(rs.getInt("ore"));
                tmp.setVoucher(rs.getInt("voucher"));
                tmp.setImporto_rimborso(rs.getDouble("rimborso"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListVoucherDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public boolean Rev_liquidaRimborso_Voucher_DT(String idrimborso, String totale, String descrizione, String path, String ctrlrimborso) {
        try {
            String sql = "UPDATE rimborso_voucher_dt SET motivo=?, check_list=?,tot_erogato=?,rimborso=?,stato='R2' WHERE idrimborso_voucher_dt=? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, descrizione);
            ps.setString(2, path);
            ps.setString(3, totale);
            ps.setString(4, ctrlrimborso);
            ps.setString(5, idrimborso);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Rev_liquidaRimborso_Voucher_DT:" + e.getMessage());
        }
        return false;
    }

    public boolean Rev_scartaTuttoRimborso_Voucher_DT(String id, String motivo, String path) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE rimborso_voucher_dt SET stato='K',motivo=?,check_list=? WHERE idrimborso_voucher_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, path);
            ps.setString(3, id);
            ps.execute();

            String sql1 = "UPDATE voucher_dt SET stato='E',domanda_rimborso_voucher_dt=NULL,motivo = ? WHERE domanda_rimborso_voucher_dt = ?";
            PreparedStatement ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, motivo);
            ps1.setString(2, id);
            ps1.executeUpdate();
            this.c.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Action.insertTracking("", "Rev_scartaTuttoRimborso_Voucher_DT:" + e.getMessage());
        }
        return false;
    }

    public boolean Rev_rigettaRimborso_Voucher_DT(String id, String motivo) {
        try {
            String sql = "UPDATE rimborso_voucher_dt SET stato='E2',motivo=? WHERE idrimborso_voucher_dt=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, motivo);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "Rev_rigettaRimborso_Voucher_DT" + ex.getMessage());
        }
        return false;
    }

    public ArrayList<Voucher> getRimborsiVoucherDt(String politica, String idrimborso) {
        ArrayList<Voucher> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idvoucher_dt,p.dataavvio, l.nome, l.cognome, l.codice_fiscale "
                    + "FROM voucher_dt as p, Lavoratore as l "
                    + "WHERE p.domanda_rimborso_voucher_dt=? and p.codazioneformcal=? "
                    + "and p.lavoratore=l.cdnlavoratore and p.stato='A'";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, politica);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher tmp = new Voucher();
                tmp.setId(rs.getInt("idvoucher_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsiVoucherDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<Voucher> getScartiVoucherDt(String idrimborso, String politica) {
        ArrayList<Voucher> out = new ArrayList<>();
        try {
            String sql = "SELECT p.idvoucher_dt,p.dataavvio, l.nome, l.cognome, l.codice_fiscale, s.motivo "
                    + "FROM scarti_voucher_dt as s , voucher_dt as p, Lavoratore as l "
                    + "WHERE id_voucher_dt=idvoucher_dt and id_rimborso_voucher_dt=? "
                    + "and p.lavoratore=l.cdnlavoratore;";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher tmp = new Voucher();
                tmp.setId(rs.getInt("idvoucher_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getScartiVoucherDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<CO> getListCO(String idente, String idlav) {
        ArrayList<CO> out = new ArrayList<>();
        try {
            PreparedStatement ps;
            if (idente.equals("nessuno")) {
                String sql = "SELECT CO.prgMovimentoSil,CO.codice_comunicazione_avviamento,CO.data_inizio,CO.data_fine,CO.mansioni,CO.tipo_movimento,CO.data_invio_co, L.cognome, L.nome, CO.lavoratore_cdnlavoratore, CO.utilizzatore_codice_fiscale, CO.datore_lavoro_codice_fiscale, CO.tipo_contratto "
                        + "FROM Comunicazione_Obbligatoria AS CO, Lavoratore as L "
                        + "WHERE CO.lavoratore_cdnlavoratore = L.cdnlavoratore AND CO.lavoratore_cdnlavoratore = ? ORDER BY CO.data_invio_co;";
                ps = this.c.prepareStatement(sql);
                ps.setString(1, idlav);
            } else {
                String sql = "SELECT CO.prgMovimentoSil,CO.codice_comunicazione_avviamento,CO.data_inizio,CO.data_fine,CO.mansioni,CO.tipo_movimento,CO.data_invio_co, L.cognome, L.nome, CO.lavoratore_cdnlavoratore, CO.utilizzatore_codice_fiscale, CO.datore_lavoro_codice_fiscale, CO.tipo_contratto "
                        + "FROM Comunicazione_Obbligatoria AS CO, Lavoratore as L "
                        + "WHERE CO.lavoratore_cdnlavoratore = L.cdnlavoratore AND CO.lavoratore_cdnlavoratore = ? AND (CO.datore_lavoro_codice_fiscale =  ? OR CO.utilizzatore_codice_fiscale = ?) ORDER BY CO.data_invio_co;";
                ps = this.c.prepareStatement(sql);
                ps.setString(1, idlav);
                ps.setString(2, idente);
                ps.setString(3, idente);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CO tmp = new CO();
                tmp.setId(rs.getString("prgMovimentoSil"));
                tmp.setCodice(rs.getString("codice_comunicazione_avviamento"));
                tmp.setData_inizio(rs.getString("data_inizio"));
                tmp.setData_fine(rs.getString("data_fine"));
                tmp.setMansione(rs.getString("mansioni"));
                tmp.setTipo_movimento(rs.getString("tipo_movimento"));
                tmp.setData_avvio_CO(rs.getString("data_invio_co"));
                tmp.setLavoratore(rs.getString("nome") + " " + rs.getString("cognome"));
                tmp.setIdlav(rs.getString("lavoratore_cdnlavoratore"));
                tmp.setCf_datorelavoro(rs.getString("datore_lavoro_codice_fiscale"));
                tmp.setCf_utilizzatore(rs.getString("utilizzatore_codice_fiscale"));
                tmp.setContratto(rs.getString("tipo_contratto"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListCO " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public int getNumberAnomaliePolitiche(String cf) {
        try {
            String sql = "SELECT COUNT(a.id) as cont FROM AnomalieFlusso as a, PoliticaAttiva as pa, Patto as p "
                    + "WHERE a.politicaAttiva_prgColloquio=pa.prgColloquio and a.politicaAttiva_prgPercorso=pa.prgPercorso and news='Y' "//and code_error=1 "
                    + "and pa.patto_prgpatto= p.prgpatto and p.cf_soggetto_promotore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("cont");
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "getNumberAnomaliePolitiche " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public ArrayList<String[]> getAnomaliePolitiche(String cf) {
        ArrayList<String[]> out = new ArrayList<>();
        try {
            String sql = "SELECT  a.id, l.nome, l.cognome, pa.codazioneformcal, a.descrizione, politicaAttiva_prgColloquio, politicaAttiva_prgPercorso, a.timestamp "
                    + "FROM AnomalieFlusso as a, PoliticaAttiva as pa, Patto as p, Lavoratore as l "
                    + "WHERE a.politicaAttiva_prgColloquio=pa.prgColloquio and a.politicaAttiva_prgPercorso=pa.prgPercorso "//and code_error=1 "
                    + "and p.lavoratore_cdnlavoratore=l.cdnlavoratore "
                    + "and pa.patto_prgpatto= p.prgpatto and p.cf_soggetto_promotore=? group by a.id order by timestamp desc limit 25";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] tmp = {rs.getString("nome") + " " + rs.getString("cognome"), rs.getString("codazioneformcal"), rs.getString("descrizione"), rs.getString("id"), rs.getString("politicaAttiva_prgColloquio") + " - " + rs.getString("politicaAttiva_prgPercorso"), rs.getString("timestamp")};
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getAnomaliePolitiche " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public void updateAnomalie(String ids) {
        try {
            String sql = "UPDATE AnomalieFlusso SET news='N' where id in (" + ids + ")";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Action.insertTracking("", "updateAnomalie " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public HashMap<String, Integer> getNumberAllPoliticheE(String cf) {
        HashMap<String, Integer> out = new HashMap<>();
        try {
            String sql = "SELECT codazioneformcal, count(idpolitica) as c FROM politica WHERE cf_ente=? and  stato='E' group by codazioneformcal "
                    + "union "
                    + "SELECT codazioneformcal, count(idpolitica_dt) as c FROM politica_dt WHERE cf_ente=? and  stato='E' group by codazioneformcal "
                    + "union "
                    + "SELECT codazioneformcal, count(idpolitica_B3_dt) as c FROM politica_B3_dt WHERE cf_ente=? and  stato='E' group by codazioneformcal "
                    + "union "
                    + "SELECT codazioneformcal, count(idvoucher_dt) as c FROM voucher_dt WHERE cf_ente=? and  stato='E' group by codazioneformcal";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ps.setString(2, cf);
            ps.setString(3, cf);
            ps.setString(4, cf);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.put(rs.getString("codazioneformcal"), rs.getInt("c"));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getNumberAllPoliticheE " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public int getNumberPrgE(String cf, String codazioneformcal) {
        try {
            String sql = "SELECT count(idprogetto_formativo) as c FROM progetto_formativo WHERE cf_ente=? and codazioneformcal=? and stato='E'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ps.setString(2, codazioneformcal);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("c");
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "getNumberPrgE " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public int getNumberPrgDtE(String cf, String codazioneformcal) {
        try {
            String sql = "SELECT count(idprogetto_formativo_dt) as c FROM progetto_formativo_dt WHERE cf_ente=? and codazioneformcal=? and stato='E'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ps.setString(2, codazioneformcal);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("c");
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "getNumberPrgDtE " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public int getNumberIdCardTutorScadute(int idente) {
        try {
            String sql = "SELECT count(idtutor) as c FROM tutor WHERE scadenza_documento<curdate() and stato='A' and ente_promotore=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, idente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("c");
            }
            return 0;
        } catch (SQLException ex) {
            Action.insertTracking("", "getNumberIdCardTutorScadute " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public HashMap<String, Integer> getRimborsi_DT() {
        HashMap<String, Integer> out = new HashMap<>();
        try {
            String sql = "SELECT CONCAT(politica,'-',stato,'-') as n, count(id_rimborso_prg_dt) as c FROM rimborso_prgformativo_dt group by politica , stato "
                    + "UNION "
                    + "SELECT CONCAT(politica,'-',stato,'-') as n, count(idrimborso_dt) as c FROM rimborso_politica_dt group by politica , stato  "
                    + "UNION "
                    + "SELECT CONCAT(politica,'-',stato,'-') as n, count(idrimborso_voucher_dt) as c FROM rimborso_voucher_dt group by politica , stato  "
                    + "UNION "
                    + "SELECT CONCAT(politica,'-',stato,'-') as n, count(idrimborso_B3_dt) as c FROM rimborso_politica_B3_dt group by politica , stato  ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.put(rs.getString("n"), rs.getInt("c"));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsi_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public HashMap<String, Integer> getRimborsi_GG() {
        HashMap<String, Integer> out = new HashMap<>();
        try {
            String sql = "SELECT CONCAT(politica,'-',stato,'-') as n, count(id_rimborso_prg) as c FROM rimborso_prgformativo group by politica , stato "
                    + "UNION "
                    + "SELECT CONCAT(politica,'-',stato,'-') as n, count(idrimborso) as c FROM rimborso_politica group by politica , stato ";

            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.put(rs.getString("n"), rs.getInt("c"));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRimborsi_GG " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public HashMap<String, Integer> getRegistri_REG() {
        HashMap<String, Integer> out = new HashMap<>();
        try {
            String sql = "SELECT CONCAT(pf.codazioneformcal,'-',r.stato,'-DT') as n, count(idregistri_dt) as c FROM registri_dt as r, progetto_formativo_dt as pf WHERE r.progetto_formativo_dt=pf.idprogetto_formativo_dt group by pf.codazioneformcal , r.stato  "
                    + "UNION "
                    + "SELECT CONCAT(pf.codazioneformcal,'-',r.stato,'-GG') as n, count(idregistri) as c FROM registri as r, progetto_formativo as pf WHERE r.progetto_formativo=pf.idprogetto_formativo group by pf.codazioneformcal , r.stato ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.put(rs.getString("n"), rs.getInt("c"));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getRegistri_REG " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

//    public int getSumrimborsiStato(int id, String stato) {
//        try {
//            String sql = "SELECT SUM(c) as s FROM("
//                    + "SELECT count(*) as c FROM rimborso_politica as r, tutor where r.stato='" + stato + "' and ente_promotore='" + id + "' and ad_au=idtutor "
//                    + "union "
//                    + "SELECT count(*) as c FROM rimborso_politica_dt as r, tutor where r.stato='" + stato + "' and ente_promotore='" + id + "' and ad_au=idtutor "
//                    + "union "
//                    + "SELECT count(*) as c FROM rimborso_politica_B3_dt as r, tutor where r.stato='" + stato + "' and ente_promotore='" + id + "' and ad_au=idtutor "
//                    + "union "
//                    + "SELECT count(*) as c FROM rimborso_prgformativo as r, tutor where r.stato='" + stato + "' and ente_promotore='" + id + "' and ad_au=idtutor "
//                    + "union "
//                    + "SELECT count(*) as c FROM rimborso_prgformativo_dt as r, tutor where r.stato='" + stato + "' and ente_promotore='" + id + "' and ad_au=idtutor "
//                    + "	union "
//                    + "SELECT count(*) as c FROM rimborso_voucher_dt as r, tutor where r.stato='" + stato + "' and ente_promotore='" + id + "' and ad_au=idtutor) as q";
//
//            PreparedStatement ps = this.c.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("s");
//            }
//            return 0;
//        } catch (SQLException ex) {
//            Action.insertTracking("", "getSumrimborsiStato " + ex.getMessage());
//            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
//        }
//        return 0;
//    }
    public int getNumberAnomaliRegister(String cf) {
        try {
            String sql = "SELECT count(idregistri) as c FROM registri as r, progetto_formativo where r.stato='E' and idprogetto_formativo=progetto_formativo and cf_ente=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("c");
            }
            return 0;
        } catch (SQLException ex) {
            Action.insertTracking("", "getNumberAnomaliRegister " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public int getNumberAnomaliRegisterDt(String cf) {
        try {
            String sql = "SELECT count(idregistri_dt) as c FROM registri_dt as r, progetto_formativo_dt where r.stato='E' and idprogetto_formativo_dt=progetto_formativo_dt and cf_ente=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("c");
            }
            return 0;
        } catch (SQLException ex) {
            Action.insertTracking("", "getNumberAnomaliRegisterDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public HashMap<String, Integer> getNumberAllPoliticheI(String cf) {
        HashMap<String, Integer> out = new HashMap<>();
        try {
            String sql = "SELECT codazioneformcal, count(idpolitica) as c FROM politica WHERE cf_ente=? and  stato='I' group by codazioneformcal "
                    + "union "
                    + "SELECT codazioneformcal, count(idpolitica_dt) as c FROM politica_dt WHERE cf_ente=? and  stato='I' group by codazioneformcal "
                    + "union "
                    + "SELECT codazioneformcal, count(idpolitica_B3_dt) as c FROM politica_B3_dt WHERE cf_ente=? and  stato='I' group by codazioneformcal "
                    + "union "
                    + "SELECT codazioneformcal, count(idvoucher_dt) as c FROM voucher_dt WHERE cf_ente=? and  stato='I' group by codazioneformcal";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ps.setString(2, cf);
            ps.setString(3, cf);
            ps.setString(4, cf);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.put(rs.getString("codazioneformcal"), rs.getInt("c"));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getNumberAllPoliticheI " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public int getNumberPrgI(String cf, String codazioneformcal) {
        try {
            String sql = "SELECT count(idprogetto_formativo) as c FROM progetto_formativo WHERE cf_ente=? and codazioneformcal=? and stato='I'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ps.setString(2, codazioneformcal);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("c");
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "getNumberPrgI " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public int getNumberPrgDtI(String cf, String codazioneformcal) {
        try {
            String sql = "SELECT count(idprogetto_formativo_dt) as c FROM progetto_formativo_dt WHERE cf_ente=? and codazioneformcal=? and stato='I'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ps.setString(2, codazioneformcal);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("c");
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "getNumberPrgDtI " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public ArrayList<Bando> bandiattivi() {
        ArrayList<Bando> out = new ArrayList<>();
        try {
            String sql = "SELECT idbando,titolo, budget,budget_attuale,data_inizio,data_fine,flag_sportello,tipo_bando,budget_previsione FROM bando WHERE stato = 'A' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bando tmp = new Bando();
                tmp.setIdbando(rs.getString("idbando"));
                tmp.setTitolo(rs.getString("titolo"));
                tmp.setData_fine(rs.getString("data_fine"));
                tmp.setData_inizio(rs.getString("data_inizio"));
                tmp.setFlag_sportello(rs.getString("flag_sportello"));
                tmp.setTipo_bando(rs.getString("tipo_bando"));
                tmp.setBudget(rs.getString("budget"));
                tmp.setBudget_attuale(rs.getString("budget_attuale"));
                tmp.setBudget_previsione(rs.getString("budget_previsione"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "bandiattivi " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public HashMap<String, Integer> getSumRimborsiMese(int id, String stato) {
        HashMap<String, Integer> out = new HashMap<>();
        try {
            String sql = "SELECT count(c) as s,YEAR(timestamp), MONTH(timestamp) as m FROM( "
                    + " SELECT idrimborso as c, timestamp FROM rimborso_politica as r, tutor where r.stato='" + stato + "' and ente_promotore=" + id + " and ad_au=idtutor and YEAR(timestamp)=YEAR(curdate())"
                    + " union "
                    + " SELECT idrimborso_dt as c, timestamp FROM rimborso_politica_dt as r, tutor where r.stato='" + stato + "' and ente_promotore=" + id + " and ad_au=idtutor and YEAR(timestamp)=YEAR(curdate())"
                    + " union "
                    + " SELECT idrimborso_B3_dt as c, timestamp FROM rimborso_politica_B3_dt as r, tutor where r.stato='" + stato + "' and ente_promotore=" + id + " and ad_au=idtutor and YEAR(timestamp)=YEAR(curdate())"
                    + " union "
                    + " SELECT id_rimborso_prg as c, timestamp FROM rimborso_prgformativo as r, tutor where r.stato='" + stato + "' and ente_promotore=" + id + " and ad_au=idtutor and YEAR(timestamp)=YEAR(curdate())"
                    + " union "
                    + " SELECT id_rimborso_prg_dt as c, timestamp FROM rimborso_prgformativo_dt as r, tutor where r.stato='" + stato + "' and ente_promotore=" + id + " and ad_au=idtutor and YEAR(timestamp)=YEAR(curdate())"
                    + " union "
                    + " SELECT idrimborso_voucher_dt as c, timestamp FROM rimborso_voucher_dt as r, tutor where r.stato='" + stato + "' and ente_promotore=" + id + " and ad_au=idtutor) as q WHERE YEAR(timestamp)=YEAR(curdate()) GROUP BY MONTH(timestamp)";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.put(rs.getString("m"), rs.getInt("s"));
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "getSumRimborsiMese " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public HashMap<String, String> getDecodificaPolitiche() {
        HashMap<String, String> out = new HashMap<>();
        try {
            String sql = "SELECT * FROM decodifica_politiche";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.put(rs.getString("cod"), rs.getString("descrizione"));
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "getDecodificaPolitiche " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public boolean decreseBudgetAttualeBando(double value, int id, String cod_politica) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE bando SET budget_attuale = budget_attuale-? where idbando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setDouble(1, value);
            ps.setInt(2, id);
            ps.execute();
            sql = " UPDATE budget_politiche SET budget_politica_attuale = budget_politica_attuale-? where bando = ? and tipo_politica=?";
            PreparedStatement ps2 = this.c.prepareStatement(sql);
            ps2.setDouble(1, value);
            ps2.setInt(2, id);
            ps2.setString(3, cod_politica);
            ps2.executeUpdate();
            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            Action.insertTracking("", "decreseBudgetAttualeBando " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public boolean refillBudgetPrevisionaleBando(double value, int id, String cod_politica) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE bando SET budget_previsione = budget_previsione+? where idbando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setDouble(1, value);
            ps.setInt(2, id);
            ps.execute();
            sql = "UPDATE budget_politiche SET budget_politica_prev = budget_politica_prev+? where bando = ? and tipo_politica=?";
            PreparedStatement ps2 = this.c.prepareStatement(sql);
            ps2.setDouble(1, value);
            ps2.setInt(2, id);
            ps2.setString(3, cod_politica);
            ps2.executeUpdate();
            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            Action.insertTracking("", "decreseBudgetAttualeBando " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public double sumRimborsoValue(String idrimborso, String tabella, String campo, String politica) {
        try {
            String sql = "SELECT SUM(rimborso) as somma FROM " + tabella + " WHERE " + campo + "=? and codazioneformcal=? and stato='A'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ps.setString(2, politica);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("somma");
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "sumRimborsoValue " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public ArrayList<Rimborso> getListEsiti(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);

            String sql = "SELECT r.* FROM rimborso_politica as r, tutor as t, ente_promotore as e WHERE r.politica = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            if (stato.equals("")) {
                sql += " AND (r.stato = 'P' OR r.stato = 'K') ";
                flag = true;
            } else {
                sql += " AND r.stato = ? ";
                pm.add(stato);
            }

            sql += " ORDER BY data_up desc ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListEsiti " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Rimborso> getListEsiti_DOTE(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);

            String sql = "SELECT r.* FROM rimborso_politica_dt as r, tutor as t, ente_promotore as e WHERE r.politica = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            if (stato.equals("")) {
                sql += " AND (r.stato = 'P' OR r.stato = 'K') ";
                flag = true;
            } else {
                sql += " AND r.stato = ? ";
                pm.add(stato);
            }

            sql += " ORDER BY data_up desc ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListEsiti_DOTE " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Rimborso> getListEsiti_DOTE_B3(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);

            String sql = "SELECT r.* FROM rimborso_politica_B3_dt as r, tutor as t, ente_promotore as e WHERE r.politica = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            if (stato.equals("")) {
                sql += " AND (r.stato = 'P' OR r.stato = 'K') ";
                flag = true;
            } else {
                sql += " AND r.stato = ? ";
                pm.add(stato);
            }

            sql += " ORDER BY data_up desc ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso_B3_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListEsiti_DOTE_B3 " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Rimborso> getListEsiti_Voucher_DT(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);

            String sql = "SELECT v.* FROM rimborso_voucher_dt as v, tutor as t, ente_promotore as e WHERE v.politica = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND v.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            if (stato.equals("")) {
                sql += " AND (v.stato = 'P' OR v.stato = 'K') ";
                flag = true;
            } else {
                sql += " AND v.stato = ? ";
                pm.add(stato);
            }

            sql += " ORDER BY data_up desc ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso tmp = new Rimborso();
                tmp.setIdrimborso(rs.getString("idrimborso_voucher_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListEsiti_Voucher_DT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Rimborso_PrgFormativo> getListEsiti_PrgFormativo(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso_PrgFormativo> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            //pm.add(stato);

            String sql = "SELECT r.* FROM rimborso_prgformativo as r, tutor as t, ente_promotore as e WHERE r.politica = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            if (stato.equals("")) {
                sql += " AND (r.stato = 'P' OR r.stato = 'K') ";
                flag = true;
            } else {
                sql += " AND r.stato = ? ";
                pm.add(stato);
            }

            sql += " ORDER BY data_up desc ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso_PrgFormativo tmp = new Rimborso_PrgFormativo();
                tmp.setIdrimborso_prg(rs.getString("id_rimborso_prg"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListEsiti_PrgFormativo " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Rimborso_PrgFormativo_Dt> getListEsiti_PrgFormativoDT(String politica, String stato, String ente, String from, String to) {
        ArrayList<Rimborso_PrgFormativo_Dt> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);

            String sql = "SELECT r.* FROM rimborso_prgformativo_dt as r, tutor as t, ente_promotore as e WHERE  r.politica = ? AND ad_au=idtutor AND idente_promotore=t.ente_promotore ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            if (stato.equals("")) {
                sql += " AND (r.stato = 'P' OR r.stato = 'K') ";
                flag = true;
            } else {
                sql += " AND r.stato = ? ";
                pm.add(stato);
            }
            sql += " ORDER BY data_up desc ";

            if (!flag) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rimborso_PrgFormativo_Dt tmp = new Rimborso_PrgFormativo_Dt();
                tmp.setIdrimborso_prg_dt(rs.getString("id_rimborso_prg_dt"));
                tmp.setData_up(rs.getString("data_up"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setStato(rs.getString("stato"));
                tmp.setAd_au(rs.getString("ad_au"));
                tmp.setPolitica(rs.getString("politica"));
                tmp.setTimestamp(rs.getString("timestamp"));
                tmp.setProtocollo(rs.getString("protocollo"));
                tmp.setDocumento(rs.getString("documento"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                tmp.setCheckList(rs.getString("check_list"));
                tmp.setDoc_ad_au(rs.getString("doc_ad_au"));
                tmp.setTot_erogato(rs.getString("tot_erogato"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListRimborsi_PrgFormativoDT " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Registro> getEsitiRegistri(String politica, String stato, String ente, String from, String to, String nome, String cognome, String cf) {
        ArrayList<Registro> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);

            String sql = "SELECT r.*,l.nome,l.cognome,l.codice_fiscale,e.ragionesociale,t.nome as t_nome, t.cognome as t_cognome, l.cdnlavoratore "
                    + " FROM registri as r, progetto_formativo as p, tutor as t, Lavoratore as l, ente_promotore as e "
                    + " WHERE r.progetto_formativo = p.idprogetto_formativo AND "
                    + " p.tutor=t.idtutor AND "
                    + " p.lavoratore=l.cdnlavoratore AND "
                    + " e.idente_promotore = t.ente_promotore AND "
                    + " p.codazioneformcal = ? ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
                flag = true;
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
                flag = true;
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            if (stato.equals("")) {
                sql += " AND (r.stato = 'P' OR r.stato = 'K') ";
                flag = true;
            } else {
                sql += " AND r.stato = ? ";
                pm.add(stato);
            }
            sql += " ORDER BY data_up desc ";

            sql += " LIMIT 1000";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Registro tmp = new Registro();
                tmp.setId(rs.getInt("idregistri"));
                tmp.setMese(rs.getString("mese"));
                tmp.setOre(rs.getInt("ore"));
                tmp.setOre_rev(rs.getInt("ore_rev"));
                tmp.setDatainizio(rs.getString("datainizio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setFile(rs.getString("file"));
                tmp.setDoc_quietanza(rs.getString("doc_quietanza"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_lavoratore(rs.getString("doc_lavoratore"));
                tmp.setProgetto_formativo(rs.getInt("progetto_formativo"));
                tmp.setDataup(rs.getString("data_up"));
                tmp.setLavoratore(rs.getString("nome") + " " + rs.getString("cognome"));
                tmp.setTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                tmp.setEnte(rs.getString("ragionesociale"));
                tmp.setCf_lavoratore(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setChecklist(rs.getString("check_list"));
                tmp.setTot_erogato(rs.getDouble("tot_erogato"));
                tmp.setXml_liquidazione(rs.getString("xml_liquidazione"));
                tmp.setId_lavoratore(rs.getString("l.cdnlavoratore"));
                tmp.setTot_erogato(rs.getDouble("tot_erogato"));
                tmp.setStato(rs.getString("stato"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getEsitiRegistri " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<RegistroDt> getListEsitiDT(String politica, String stato, String ente, String from, String to, String nome, String cognome, String cf) {
        ArrayList<RegistroDt> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;
            pm.add(politica);
            //pm.add(stato);

            String sql = "SELECT r.*,l.nome,l.cognome,l.codice_fiscale,e.ragionesociale,t.nome as t_nome, t.cognome as t_cognome,p.codazioneformcal,l.cdnlavoratore "
                    + " FROM registri_dt as r, progetto_formativo_dt as p, tutor as t, Lavoratore as l, ente_promotore as e "
                    + " WHERE r.progetto_formativo_dt = p.idprogetto_formativo_dt AND "
                    + " p.tutor = t.idtutor AND "
                    + " p.lavoratore = l.cdnlavoratore AND "
                    + " e.idente_promotore = t.ente_promotore AND p.codazioneformcal = ? ";

            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
                flag = true;
            }
            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
                flag = true;
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
                flag = true;
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
                flag = true;
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND r.data_up BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
                flag = true;
            }
            if (stato.equals("")) {
                sql += " AND (r.stato = 'P' OR r.stato = 'K') ";
                flag = true;
            } else {
                sql += " AND r.stato = ? ";
                pm.add(stato);
            }
            sql += " ORDER BY data_up desc ";

            sql += " LIMIT 1000";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RegistroDt tmp = new RegistroDt();
                tmp.setId(rs.getInt("idregistri_dt"));
                tmp.setMese(rs.getString("mese"));
                tmp.setOre(rs.getInt("ore"));
                tmp.setOre_rev(rs.getInt("ore_rev"));
                tmp.setDatainizio(rs.getString("datainizio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setFile(rs.getString("file"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_lavoratore(rs.getString("doc_lavoratore"));
                tmp.setProgetto_formativo_dt(rs.getInt("progetto_formativo_dt"));
                tmp.setDataup(rs.getString("data_up"));
                tmp.setLavoratore(rs.getString("nome") + " " + rs.getString("cognome"));
                tmp.setTutor(rs.getString("t_nome") + " " + rs.getString("t_cognome"));
                tmp.setEnte(rs.getString("ragionesociale"));
                tmp.setCf_lavoratore(rs.getString("codice_fiscale"));
                tmp.setMotivo(rs.getString("motivo"));
                tmp.setChecklist(rs.getString("check_list"));
                tmp.setTot_erogato(rs.getDouble("tot_erogato"));
                tmp.setXml_liquidazione(rs.getString("xml_liquidazione"));
                tmp.setPolitica(rs.getString("codazioneformcal"));
                tmp.setId_lavoratore(rs.getString("l.cdnlavoratore"));
                tmp.setTot_erogato(rs.getDouble("tot_erogato"));
                tmp.setStato(rs.getString("stato"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getListEsitiDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public double[] getPrezziario(String code, String bando) {
        try {
            String sql = "SELECT * FROM prezziario WHERE cod_politica=? and tipo_bando=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, code);
            ps.setString(2, bando);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double[] temp = {rs.getDouble("1"), rs.getDouble("2"), rs.getDouble("3"), rs.getDouble("4")};
                return temp;
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "getPrezziario " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Lavoratore> getLavoratori(String nome, String cognome, String cf) {
        ArrayList<Lavoratore> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {
            boolean flag = false;

            String sql = "SELECT * FROM Lavoratore ";

            if (!nome.equals("")) {
                sql += " WHERE nome LIKE ? ";
                pm.add(nome + "%");
                flag = true;
            }
            if (!cognome.equals("")) {
                if (flag) {
                    sql += " AND cognome LIKE ? ";
                    pm.add(cognome + "%");
                } else {
                    sql += " WHERE cognome LIKE ? ";
                    pm.add(cognome + "%");
                    flag = true;
                }
            }
            if (!cf.equals("")) {
                if (flag) {
                    sql += " AND codice_fiscale LIKE ? ";
                    pm.add(cf + "%");
                } else {
                    sql += " WHERE codice_fiscale LIKE ? ";
                    pm.add(cf + "%");
                    flag = true;
                }
            }

            sql += " ORDER BY cognome ";

            sql += " LIMIT 1000";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lavoratore tmp = new Lavoratore();
                tmp.setCdnlavoratore(rs.getInt("cdnlavoratore"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCodice_fiscale(rs.getString("codice_fiscale"));
                tmp.setDomicilio_cap(rs.getInt("domicilio_cap"));
                tmp.setDomicilio_codice_catastale(rs.getString("domicilio_codice_catastale"));
                tmp.setDomicilio_indirizzo(rs.getString("domicilio_indirizzo"));
                tmp.setEmail(rs.getString("email"));
                tmp.setNascita_data(rs.getDate("nascita_data"));
                tmp.setRecapito_telefonico(rs.getString("recapito_telefonico"));
                tmp.setResidenza_cap(rs.getInt("residenza_cap"));
                tmp.setResidenza_codice_catastale(rs.getString("residenza_codice_catastale"));
                tmp.setResidenza_indirizzo(rs.getString("residenza_indirizzo"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getLavoratori " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<DecreaseRefill> GG_C06_DecreaseRefillById(String id) {
        ArrayList<DecreaseRefill> dr = new ArrayList<>();
        try {
            //String sql = "SELECT rimborso, codazioneformcal, bando FROM progetto_formativo WHERE rimborso_prg = ? ;";
            String sql = "SELECT SUM(rimborso) as somma,codazioneformcal,bando FROM progetto_formativo WHERE rimborso_prg = ? GROUP BY bando;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DecreaseRefill out = new DecreaseRefill();
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("somma"));
                out.setPolitica(rs.getString("codazioneformcal"));
                dr.add(out);
            }
            return dr;
        } catch (SQLException ex) {
            Action.insertTracking("service", "GG_C06_DecreaseRefillById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<DecreaseRefill> DT_C06_DecreaseRefillById(String id) {
        ArrayList<DecreaseRefill> dr = new ArrayList<>();
        try {
            String sql = "SELECT SUM(rimborso) as somma, codazioneformcal, bando FROM progetto_formativo_dt WHERE rimborso_prg = ? GROUP BY bando;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DecreaseRefill out = new DecreaseRefill();
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("somma"));
                out.setPolitica(rs.getString("codazioneformcal"));
                dr.add(out);
            }
            return dr;
        } catch (SQLException ex) {
            Action.insertTracking("service", "DT_C06_DecreaseRefillById_Single " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<DecreaseRefill> DT_B3_DecreaseRefillById(String id) {
        ArrayList<DecreaseRefill> dr = new ArrayList<>();
        try {
            String sql = "SELECT SUM(rimborso) as somma, codazioneformcal, bando FROM politica_B3_dt WHERE domanda_rimborso_B3_dt= ? GROUP BY bando; ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DecreaseRefill out = new DecreaseRefill();
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("somma"));
                out.setPolitica(rs.getString("codazioneformcal"));
                dr.add(out);
            }
            return dr;
        } catch (SQLException ex) {
            Action.insertTracking("service", "DT_B3_DecreaseRefillById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<DecreaseRefill> GG_DecreaseRefillById(String id) {
        ArrayList<DecreaseRefill> dr = new ArrayList<>();
        try {
            String sql = "SELECT SUM(rimborso) as somma, codazioneformcal, bando FROM politica WHERE domanda_rimborso = ? GROUP BY bando;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DecreaseRefill out = new DecreaseRefill();
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("somma"));
                out.setPolitica(rs.getString("codazioneformcal"));
                dr.add(out);
            }
            return dr;
        } catch (SQLException ex) {
            Action.insertTracking("service", "GG_DecreaseRefillById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<DecreaseRefill> DT_DecreaseRefillById(String id) {
        ArrayList<DecreaseRefill> dr = new ArrayList<>();
        try {
            String sql = "SELECT SUM(rimborso) as somma, codazioneformcal, bando FROM politica_dt WHERE domanda_rimborso_dt = ? GROUP BY bando;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DecreaseRefill out = new DecreaseRefill();
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("somma"));
                out.setPolitica(rs.getString("codazioneformcal"));
                dr.add(out);
            }
            return dr;
        } catch (SQLException ex) {
            Action.insertTracking("service", "DT_DecreaseRefillById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<DecreaseRefill> DT_Voucher_DecreaseRefillById(String id) {
        ArrayList<DecreaseRefill> dr = new ArrayList<>();
        try {
            String sql = "SELECT SUM(rimborso) as somma, codazioneformcal, bando FROM voucher_dt WHERE domanda_rimborso_voucher_dt = ? GROUP BY bando; ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DecreaseRefill out = new DecreaseRefill();
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("somma"));
                out.setPolitica(rs.getString("codazioneformcal"));
                dr.add(out);
            }
            return dr;
        } catch (SQLException ex) {
            Action.insertTracking("service", "DT_Voucher_DecreaseRefillById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public DecreaseRefill DT_B3_DecreaseRefillById_Single(String id) {
        DecreaseRefill out = new DecreaseRefill();
        try {
            String sql = "SELECT rimborso, codazioneformcal, bando FROM politica_B3_dt WHERE domanda_rimborso_B3_dt= ? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("rimborso"));
                out.setPolitica(rs.getString("codazioneformcal"));
                return out;
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "DT_B3_DecreaseRefillById_Single " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public DecreaseRefill GG_DecreaseRefillById_Single(String id) {
        DecreaseRefill out = new DecreaseRefill();
        try {
            String sql = "SELECT rimborso, codazioneformcal, bando FROM politica WHERE domanda_rimborso = ? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("rimborso"));
                out.setPolitica(rs.getString("codazioneformcal"));
                return out;
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "GG_DecreaseRefillById_Single " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public DecreaseRefill DT_DecreaseRefillById_Single(String id) {
        DecreaseRefill out = new DecreaseRefill();
        try {
            String sql = "SELECT rimborso, codazioneformcal, bando FROM politica_dt WHERE domanda_rimborso_dt = ?  ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("rimborso"));
                out.setPolitica(rs.getString("codazioneformcal"));
                return out;
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "DT_DecreaseRefillById_Single " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public DecreaseRefill DT_Voucher_DecreaseRefillById_Single(String id) {
        DecreaseRefill out = new DecreaseRefill();
        try {
            String sql = "SELECT rimborso, codazioneformcal, bando FROM voucher_dt WHERE domanda_rimborso_voucher_dt = ?  ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("rimborso"));
                out.setPolitica(rs.getString("codazioneformcal"));
                return out;
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "DT_Voucher_DecreaseRefillById_Single " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public DecreaseRefill GG_C06_DecreaseRefillById_Single(String id) {
        DecreaseRefill out = new DecreaseRefill();
        try {
            String sql = "SELECT rimborso, codazioneformcal, bando FROM progetto_formativo WHERE rimborso_prg = ? ;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("somma"));
                out.setPolitica(rs.getString("codazioneformcal"));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("service", "GG_C06_DecreaseRefillById_Single " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public DecreaseRefill DT_C06_DecreaseRefillById_Single(String id) {
        DecreaseRefill out = new DecreaseRefill();
        try {
            String sql = "SELECT rimborso, codazioneformcal, bando FROM progetto_formativo_dt WHERE rimborso_prg = ? ;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(rs.getDouble("rimborso"));
                out.setPolitica(rs.getString("codazioneformcal"));
                return out;
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "DT_C06_DecreaseRefillById_Single " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public DecreaseRefill GG_Registri_DecreaseRefillById(String id) {
        DecreaseRefill out = new DecreaseRefill();
        try {
            String sql = "SELECT codazioneformcal, bando FROM progetto_formativo, registri WHERE idprogetto_formativo = progetto_formativo and idregistri=? ;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(Action.getPrezziario("2").get("registro")[0]);
                out.setPolitica(rs.getString("codazioneformcal"));
                return out;
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "GG_C06_DecreaseRefillById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public DecreaseRefill DT_Registri_DecreaseRefillById(String id) {
        DecreaseRefill out = new DecreaseRefill();
        try {
            String sql = "SELECT rimborso, codazioneformcal, bando FROM progetto_formativo_dt, registri_dt WHERE idprogetto_formativo_dt =progetto_formativo_dt and idregistri_dt = ? ;";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setIdbando(rs.getInt("bando"));
                out.setTot_erogato(Action.getPrezziario("1").get("registro_dt")[0]);
                out.setPolitica(rs.getString("codazioneformcal"));
                return out;
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "DT_C06_DecreaseRefillById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Bando> getbandiattivi(String tipo) {
        ArrayList<Bando> out = new ArrayList<>();
        try {
            String sql = "SELECT idbando,titolo, budget,budget_attuale,data_inizio,data_fine,flag_sportello,tipo_bando,budget_previsione, decreto FROM bando WHERE tipo_bando=?";//stato <> 'C' AND per ora possono spostare anche nei chiusi
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bando tmp = new Bando();
                tmp.setIdbando(rs.getString("idbando"));
                tmp.setTitolo(rs.getString("titolo"));
                tmp.setData_fine(rs.getString("data_fine"));
                tmp.setData_inizio(rs.getString("data_inizio"));
                tmp.setFlag_sportello(rs.getString("flag_sportello"));
                tmp.setTipo_bando(rs.getString("tipo_bando"));
                tmp.setBudget(rs.getString("budget"));
                tmp.setBudget_attuale(rs.getString("budget_attuale"));
                tmp.setBudget_previsione(rs.getString("budget_previsione"));
                tmp.setDecreto(rs.getString("decreto"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "bandiattivi " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean transferMoney(String iddonatore, String idricevente, String budget) {
        try {
            this.c.setAutoCommit(false);
            String sql = "UPDATE bando SET budget = budget - ?, budget_attuale = budget_attuale - ?, budget_previsione = budget_previsione - ? WHERE idbando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, budget);
            ps.setString(2, budget);
            ps.setString(3, budget);
            ps.setString(4, iddonatore);
            ps.execute();

            sql = "UPDATE bando SET budget = budget + ?, budget_attuale = budget_attuale + ?, budget_previsione = budget_previsione + ? WHERE idbando = ?";
            PreparedStatement ps2 = this.c.prepareStatement(sql);
            ps2.setString(1, budget);
            ps2.setString(2, budget);
            ps2.setString(3, budget);
            ps2.setString(4, idricevente);
            ps2.executeUpdate();
            this.c.commit();
            return true;
        } catch (SQLException ex) {
            if (this.c != null) {
                try {
                    this.c.rollback();
                } catch (SQLException ex1) {
                }
            }
            Action.insertTracking("service", "transferMoney " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public Double[] getSumPolitiche(String id) {
        try {
            String sql = "SELECT sum(budget_politica)AS budget, sum(budget_politica_attuale)AS attuale, sum(budget_politica_prev)AS previsionale FROM budget_politiche where bando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Double[] tmp = {rs.getDouble("budget"), rs.getDouble("attuale"), rs.getDouble("previsionale")};
                return tmp;
            }
        } catch (SQLException ex) {
            Action.insertTracking("", "getSumPolitiche " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<PrgFormativo> getPrgFormativiClosed(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        ArrayList<PrgFormativo> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(politica);
            pm.add(cfente);
            pm.add(stato);

            String sql = "SELECT p.idprogetto_formativo,l.nome, l.cognome,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_lavoratore, p.doc_tutor, p.doc_competenze, p.scadenza_doc, p.file "
                    + "FROM progetto_formativo as p, Lavoratore as l "
                    + "WHERE p.codazioneformcal=? and p.cf_ente=?  "
                    + "and p.lavoratore=l.cdnlavoratore and stato=? ";

            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            if (nome.equals("") && cognome.equals("") && cf.equals("") && from.equals("") && to.equals("")) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativo tmp = new PrgFormativo();
                tmp.setId(rs.getInt("idprogetto_formativo"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_competenze(rs.getString("doc_competenze"));
                tmp.setScadenza_doc(rs.getString("scadenza_doc"));
                tmp.setFile(rs.getString("file"));

                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("service", "getPrgFormativiClosed " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<PrgFormativoDt> getPrgFormativiDtClosed(String cfente, String politica, String stato, String nome, String cognome, String cf, String from, String to) {
        ArrayList<PrgFormativoDt> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(politica);
            pm.add(cfente);
            pm.add(stato);

            String sql = "SELECT p.idprogetto_formativo_dt,l.nome, l.cognome,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, l.codice_fiscale,"
                    + " p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_lavoratore, p.doc_tutor, p.doc_competenze, p.scadenza_doc, doc_m5, p.file "
                    + "FROM progetto_formativo_dt as p, Lavoratore as l "
                    + "WHERE p.codazioneformcal=? and p.cf_ente=?  "
                    + "and p.lavoratore=l.cdnlavoratore and stato=? ";

            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            if (nome.equals("") && cognome.equals("") && cf.equals("") && from.equals("") && to.equals("")) {
                sql += " LIMIT 25";
            } else {
                sql += " LIMIT 500";
            }

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativoDt tmp = new PrgFormativoDt();
                tmp.setId(rs.getInt("idprogetto_formativo_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_competenze(rs.getString("doc_competenze"));
                tmp.setScadenza_doc(rs.getString("scadenza_doc"));
                tmp.setDoc_m5(rs.getString("doc_m5"));
                tmp.setFile(rs.getString("file"));

                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("service", "getPrgFormativiDtClosed " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<ViewPolitiche> getPoliticheLavoratore(String cdnlav) {
        ArrayList<ViewPolitiche> out = new ArrayList<>();
        try {
            String sql = "SELECT idpolitica,codazioneformcal,dataavvio, datafine, stato, cf_ente, domanda_rimborso, 'P000' as cod FROM politica where lavoratore='" + cdnlav + "' "
                    + "union "
                    + "SELECT idpolitica_dt,codazioneformcal,dataavvio, datafine, stato, cf_ente, domanda_rimborso_dt, 'PDT0' as cod FROM politica_dt where lavoratore='" + cdnlav + "' "
                    + "union "
                    + "SELECT idprogetto_formativo,codazioneformcal,dataavvio, datafine, stato, cf_ente, rimborso_prg, 'PRG0' as cod FROM progetto_formativo where lavoratore='" + cdnlav + "' "
                    + "union "
                    + "SELECT idprogetto_formativo_dt,codazioneformcal,dataavvio, datafine, stato, cf_ente, rimborso_prg, 'PFDT' as cod FROM progetto_formativo_dt where lavoratore='" + cdnlav + "' "
                    + "union "
                    + "SELECT idvoucher_dt,codazioneformcal,dataavvio, datafine, stato, cf_ente, domanda_rimborso_voucher_dt, 'VDT0' as cod FROM voucher_dt where lavoratore='" + cdnlav + "' "
                    + "union "
                    + "SELECT idpolitica_B3_dt,codazioneformcal,dataavvio, datafine, stato, cf_ente, domanda_rimborso_B3_dt, 'B300' as cod FROM politica_B3_dt where lavoratore='" + cdnlav + "'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ViewPolitiche tmp = new ViewPolitiche();
                tmp.setIdpolitica(rs.getInt(1));
                tmp.setCodazioneformcal(rs.getString(2));
                tmp.setDataavvio(rs.getDate(3));
                tmp.setDatafine(rs.getDate(4));
                tmp.setStato(rs.getString(5));
                tmp.setCf_ente(rs.getString(6));
                tmp.setDomanda_rimborso(rs.getInt(7));
                tmp.setCod(rs.getString(8));
                out.add(tmp);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getPoliticheLavoratore " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public ArrayList<ViewPolitiche> getPoliticheLavoratore(String cdnlav, String cf_ente) {
        ArrayList<ViewPolitiche> out = new ArrayList<>();
        try {
            String sql = "SELECT idpolitica,codazioneformcal,dataavvio, datafine, stato, cf_ente, domanda_rimborso, 'P000' as cod FROM politica where lavoratore='" + cdnlav + "' and cf_ente='" + cf_ente + "'"
                    + "union "
                    + "SELECT idpolitica_dt,codazioneformcal,dataavvio, datafine, stato, cf_ente, domanda_rimborso_dt, 'PDT0' as cod FROM politica_dt where lavoratore='" + cdnlav + "' and cf_ente='" + cf_ente + "'"
                    + "union "
                    + "SELECT idprogetto_formativo,codazioneformcal,dataavvio, datafine, stato, cf_ente, rimborso_prg, 'PRG0' as cod FROM progetto_formativo where lavoratore='" + cdnlav + "' and cf_ente='" + cf_ente + "'"
                    + "union "
                    + "SELECT idprogetto_formativo_dt,codazioneformcal,dataavvio, datafine, stato, cf_ente, rimborso_prg, 'PFDT' as cod FROM progetto_formativo_dt where lavoratore='" + cdnlav + "' and cf_ente='" + cf_ente + "'"
                    + "union "
                    + "SELECT idvoucher_dt,codazioneformcal,dataavvio, datafine, stato, cf_ente, domanda_rimborso_voucher_dt, 'VDT0' as cod FROM voucher_dt where lavoratore='" + cdnlav + "' and cf_ente='" + cf_ente + "'"
                    + "union "
                    + "SELECT idpolitica_B3_dt,codazioneformcal,dataavvio, datafine, stato, cf_ente, domanda_rimborso_B3_dt, 'B300' as cod FROM politica_B3_dt where lavoratore='" + cdnlav + "' and cf_ente='" + cf_ente + "'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ViewPolitiche tmp = new ViewPolitiche();
                tmp.setIdpolitica(rs.getInt(1));
                tmp.setCodazioneformcal(rs.getString(2));
                tmp.setDataavvio(rs.getDate(3));
                tmp.setDatafine(rs.getDate(4));
                tmp.setStato(rs.getString(5));
                tmp.setCf_ente(rs.getString(6));
                tmp.setDomanda_rimborso(rs.getInt(7));
                tmp.setCod(rs.getString(8));
                out.add(tmp);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getPoliticheLavoratore " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return out;
    }

    public String getStatoRimborso(String idrimborso, String cod_risorsa) {
        try {
            String sql = "";
            if (cod_risorsa.equals("P000")) {
                sql = "SELECT stato FROM rimborso_politica WHERE idrimborso=?";
            } else if (cod_risorsa.equals("PDT0")) {
                sql = "SELECT stato FROM rimborso_politica_dt WHERE idrimborso_dt=?";
            } else if (cod_risorsa.equals("PRG0")) {
                sql = "SELECT stato FROM rimborso_prgformativo WHERE id_rimborso_prg=?";
            } else if (cod_risorsa.equals("PFDT")) {
                sql = "SELECT stato FROM rimborso_prgformativo_dt WHERE id_rimborso_prg_dt=?";
            } else if (cod_risorsa.equals("VDT0")) {
                sql = "SELECT stato FROM rimborso_voucher_dt WHERE idrimborso_voucher_dt=?";
            } else if (cod_risorsa.equals("B300")) {
                sql = "SELECT stato FROM rimborso_politica_B3_dt WHERE idrimborso_B3_dt=?";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, idrimborso);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Action.insertTracking("service", "getStatoRimborso " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Lavoratore> getLavoratori(String nome, String cognome, String cf, String cf_ente) {
        ArrayList<Lavoratore> out = new ArrayList<>();

        ArrayList<String> pm = new ArrayList<>();
        try {

            String sql = "SELECT l.* FROM Lavoratore as l, lav_ente as le WHERE le.cdnlavoratore=l.cdnlavoratore and cf_ente=?";

            pm.add(cf_ente);

            if (!nome.equals("")) {
                sql += " AND nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND codice_fiscale LIKE ? ";
                pm.add(cf + "%");
            }

            sql += " ORDER BY cognome desc ";

            sql += " LIMIT 100";

            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lavoratore tmp = new Lavoratore();
                tmp.setCdnlavoratore(rs.getInt("cdnlavoratore"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCodice_fiscale(rs.getString("codice_fiscale"));
                tmp.setDomicilio_cap(rs.getInt("domicilio_cap"));
                tmp.setDomicilio_codice_catastale(rs.getString("domicilio_codice_catastale"));
                tmp.setDomicilio_indirizzo(rs.getString("domicilio_indirizzo"));
                tmp.setEmail(rs.getString("email"));
                tmp.setNascita_data(rs.getDate("nascita_data"));
                tmp.setRecapito_telefonico(rs.getString("recapito_telefonico"));
                tmp.setResidenza_cap(rs.getInt("residenza_cap"));
                tmp.setResidenza_codice_catastale(rs.getString("residenza_codice_catastale"));
                tmp.setResidenza_indirizzo(rs.getString("residenza_indirizzo"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getLavoratori " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public void updateAllTutorDocs(String doc, String idtutor) {
        try {
            String sql = "UPDATE politica SET doc_tutor = '" + doc + "' WHERE tutor =" + idtutor + " AND stato='S'; ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.executeUpdate();

            sql = "UPDATE politica_dt SET doc_tutor = '" + doc + "' WHERE tutor =" + idtutor + "  AND stato='S'; ";
            ps = this.c.prepareStatement(sql);
            ps.executeUpdate();

            sql = "UPDATE progetto_formativo SET doc_tutor ='" + doc + "' WHERE tutor =" + idtutor + "  AND (stato='S' OR stato='C'); ";
            ps = this.c.prepareStatement(sql);
            ps.executeUpdate();

            sql = "UPDATE progetto_formativo_dt SET doc_tutor = '" + doc + "' WHERE tutor =" + idtutor + " AND (stato='S' OR stato='C'); ";
            ps = this.c.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Action.insertTracking("", "updateAllTutorDocs " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public ArrayList<PrgFormativo> getPrgFormativiReg(String ente, String politica, String nome, String cognome, String cf, String from, String to) {
        ArrayList<PrgFormativo> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(politica);

            String sql = "SELECT p.idprogetto_formativo,l.nome, l.cognome,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, "
                    + "l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_lavoratore, p.doc_tutor, p.doc_competenze, "
                    + "p.scadenza_doc, e.ragionesociale "
                    + "FROM progetto_formativo as p, Lavoratore as l, ente_promotore as e "
                    + "WHERE e.cf=p.cf_ente and p.codazioneformcal=? and p.lavoratore=l.cdnlavoratore ";

            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            sql += " ORDER BY timestamp desc LIMIT 200";
            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativo tmp = new PrgFormativo();
                tmp.setId(rs.getInt("idprogetto_formativo"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_competenze(rs.getString("doc_competenze"));
                tmp.setScadenza_doc(rs.getString("scadenza_doc"));
                tmp.setDe_ente(rs.getString("ragionesociale"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("service", "getPrgFormativiReg " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<PrgFormativoDt> getPrgFormativiRegDt(String ente, String politica, String nome, String cognome, String cf, String from, String to) {
        ArrayList<PrgFormativoDt> out = new ArrayList<>();
        try {
            ArrayList<String> pm = new ArrayList<>();
            pm.add(politica);

            String sql = "SELECT p.idprogetto_formativo_dt, l.nome, l.cognome,p.dataavvio, p.datafine, p.ore_effettuate, p.ore_tot, "
                    + "l.codice_fiscale, p.motivo,p.stato, p.codazioneformcal, p.durataeffettiva, p.doc_lavoratore, p.doc_tutor, p.doc_competenze, "
                    + "p.scadenza_doc, e.ragionesociale "
                    + "FROM progetto_formativo_dt as p, Lavoratore as l, ente_promotore as e "
                    + "WHERE e.cf=p.cf_ente and p.codazioneformcal=? and p.lavoratore=l.cdnlavoratore ";

            if (!nome.equals("")) {
                sql += " AND l.nome LIKE ? ";
                pm.add(nome + "%");
            }
            if (!cognome.equals("")) {
                sql += " AND l.cognome LIKE ? ";
                pm.add(cognome + "%");
            }
            if (!cf.equals("")) {
                sql += " AND l.codice_fiscale=? ";
                pm.add(cf);
            }
            if (!ente.equals("")) {
                sql += " AND e.idente_promotore=? ";
                pm.add(ente);
            }
            if (!from.equals("") && !to.equals("")) {
                sql += " AND p.dataavvio BETWEEN STR_TO_DATE( ?,'%d/%m/%Y') AND STR_TO_DATE( ?,'%d/%m/%Y') ";
                pm.add(from);
                pm.add(to);
            }
            sql += " ORDER BY timestamp desc LIMIT 200";
            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < pm.size(); i++) {
                ps.setString(i + 1, pm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrgFormativoDt tmp = new PrgFormativoDt();
                tmp.setId(rs.getInt("idprogetto_formativo_dt"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                tmp.setCf(rs.getString("codice_fiscale"));
                tmp.setDataavvio(rs.getString("dataavvio"));
                tmp.setDatafine(rs.getString("datafine"));
                tmp.setStato(rs.getString("stato"));
                tmp.setCodazioneformcal(rs.getString("codazioneformcal"));
                tmp.setOre_effettuate(rs.getInt("ore_effettuate"));
                tmp.setOre_tot(rs.getInt("ore_tot"));
                tmp.setDoc_ragazzo(rs.getString("doc_lavoratore"));
                tmp.setDoc_tutor(rs.getString("doc_tutor"));
                tmp.setDoc_competenze(rs.getString("doc_competenze"));
                tmp.setScadenza_doc(rs.getString("scadenza_doc"));
                tmp.setDe_ente(rs.getString("ragionesociale"));
                out.add(tmp);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("service", "getPrgFormativiRegDt " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public boolean updateTutor(String id, String nome, String cognome, String cf, String email, String telefono, String ruolo) {
        try {
            String sql = "UPDATE tutor SET nome=?, cognome=?, codice_fiscale=?, email=?, telefono=?, ruolo=? WHERE idtutor=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, cf);
            ps.setString(4, email);
            ps.setString(5, telefono);
            ps.setString(6, ruolo);
            ps.setString(7, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "updateTutor" + ex.getMessage());
        }
        return false;
    }

    public boolean convalidateRegistri(String data) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE registri SET stato='P' WHERE idregistri=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idregistri=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 1, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "convalidateRegistri " + ex.getMessage());
        }
        return false;
    }

    public boolean convalidateRegistriDt(String data) {
        String[] s = data.split(",");
        try {
            String sql = "UPDATE registri_dt SET stato='P' WHERE idregistri_dt=? ";

            for (int i = 1; i < s.length; i++) {
                sql += "OR idregistri_dt=? ";
            }
            PreparedStatement ps = this.c.prepareStatement(sql);

            for (int i = 0; i < s.length; i++) {
                ps.setString(i + 1, s[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "convalidateRegistri " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<String> getMessageToUser() {
        ArrayList<String> out = new ArrayList<>();
        try {
            String sql = "SELECT testo FROM message_to_user WHERE inizio<=CURDATE() AND fine>=CURDATE()";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.add(rs.getString("testo"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("", "getMessageToUser " + ex.getMessage());
        }
        return out;
    }

    public ArrayList<Convenzione> getConvenzioni(int ente, String tipo_bando, String politica) {
        ArrayList<Convenzione> out = new ArrayList<>();
        try {
            String sql = "SELECT convenzione.*, tipo_politica.descrizione FROM convenzione,tipo_politica where ente=? and stato='A' and tipo_politica=idtipo_politica and tipo_bando=bando and tipo_bando=? and tipo_politica=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setInt(1, ente);
            ps.setString(2, tipo_bando);
            ps.setString(3, politica);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Convenzione t = new Convenzione();
                t.setId(rs.getInt("idconvenzione"));
                t.setCodice(rs.getString("codice"));
                t.setInizio(rs.getString("inizio"));
                t.setFine(rs.getString("fine"));
                t.setPolitica(rs.getString("tipo_politica"));
                t.setEnte(rs.getInt("ente"));
                t.setBando(rs.getInt("bando"));
                t.setD_politica(rs.getString("descrizione"));
                t.setFile(rs.getString("file"));
                out.add(t);
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getConvenzioni " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }

    public Ente getEnteByCf(String cf) {
        Ente out = new Ente();
        try {
            String sql = "SELECT * FROM ente_promotore where cf=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.setRagioneSociale(rs.getString("ragionesociale"));
                out.setId(rs.getInt("idente_promotore"));
                out.setCf(rs.getString("cf"));
                out.setPiva(rs.getString("piva"));
                out.setEmail(rs.getString("email"));
                out.setTelefono(rs.getString("telefono"));
                out.setComune(rs.getString("comune"));
                out.setIndirizzo(rs.getString("indirizzo"));
            }
            return out;
        } catch (SQLException ex) {
            Action.insertTracking("", "getEnteById " + ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

}
