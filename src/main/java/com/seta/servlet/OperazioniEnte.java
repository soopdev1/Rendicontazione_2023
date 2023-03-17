/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.seta.activity.Action;
import com.seta.ejb.EmailManagerEJB;
import com.seta.entity.B3;
import com.seta.entity.Politica;
import com.seta.entity.PrgFormativo;
import com.seta.entity.PrgFormativoDt;
import com.seta.entity.Registro;
import com.seta.entity.RegistroDt;
import com.seta.entity.Tutor;
import com.seta.entity.Voucher;
import com.seta.util.Pdf_new;
import com.seta.util.Utility;
import static com.seta.util.Utility.redirect;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.logging.*;
/**
 *
 * @author agodino
 */
public class OperazioniEnte extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger( OperazioniEnte.class.getName() );
	private static final long serialVersionUID = 1010665646789891477L;

	@EJB
	private EmailManagerEJB emailManagerEJB;

    protected void addTutor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cognome = request.getParameter("cognome");
        String nome = request.getParameter("nome");
        String ruolo = request.getParameter("ruolo");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String scadenza = request.getParameter("scadenza");
        String cf = request.getParameter("cf");
        Part file = request.getPart("file");

        int idente = (int) request.getSession().getAttribute("idente");
        String ente = (String) request.getSession().getAttribute("ente");

        if (email.equals("")) {
            email = "-";
        }
        if (telefono.equals("")) {
            telefono = "-";
        }

        try {
            scadenza = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(scadenza));
        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        String random = RandomStringUtils.randomAlphabetic(8);

        String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
        File savedir = new File(path);
        savedir.mkdirs();
        String doc = path + "CI_" + cognome + "_" + nome + "_" + random + ".pdf";

        doc = doc.replace("'", "_").replace("\"", "_");

        try {
            file.write(doc);
            if (Action.insertTutor(ruolo, nome, cognome, email, telefono, doc, scadenza, idente, cf)) {
                response.sendRedirect("addTutor.jsp?esitoins=OK");
            } else {
                new File(doc).delete();
                response.sendRedirect("addTutor.jsp?esitoins=KO");
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.sendRedirect("addTutor.jsp?esitoins=KO");
        }

    }

    protected void deleteTutor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idtutor = request.getParameter("idtutor");

        if (Action.deleteTutor(idtutor)) {
            Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Delete", "T000", idtutor);
            response.sendRedirect("searchTutor.jsp?esitodel=KO");
        } else {
            response.sendRedirect("searchTutor.jsp?esitodel=KO");
        }
    }

    protected void updateDocId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idtutor = request.getParameter("idtutor");
        Part file = request.getPart("file");
        String scadenza = request.getParameter("scadenza");
        String cognome = request.getParameter("cognome");
        String nome = request.getParameter("nome");
//        String cognome = request.getParameter("cognome");
//        String nome = request.getParameter("nome");
        //String doc = request.getParameter("doc");

        int idente = (int) request.getSession().getAttribute("idente");
        String ente = (String) request.getSession().getAttribute("ente");
        String random = RandomStringUtils.randomAlphabetic(8);

        String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
        File savedir = new File(path);
        savedir.mkdirs();

        String doc = path + "CI_" + cognome + "_" + nome + "_" + random + ".pdf";
        doc = doc.replace("'", "_").replace("\"", "_");

        try {
            scadenza = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(scadenza));
        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        try {
            file.write(doc);
            if (Action.updateDocId(idtutor, doc, scadenza)) {
                Action.updateAllTutorDocs(doc, idtutor);
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update", "T000", idtutor);
                response.sendRedirect("modifyTutor.jsp?esitoedit=OK&idtutor=" + idtutor);
            } else {
                response.sendRedirect("modifyTutor.jsp?esitoedit=KOidtutor=" + idtutor);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.sendRedirect("modifyTutor.jsp?esitoedit=KO");
        }
    }

    protected void insertConvenzione(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codice = request.getParameter("codice");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String politica = request.getParameter("politica");
        String bando = request.getParameter("bando");

        Part file = request.getPart("file");

        String random = RandomStringUtils.randomAlphabetic(8);
        int idente = (int) request.getSession().getAttribute("idente");
        String ente = (String) request.getSession().getAttribute("ente");
        String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
        File savedir = new File(path);
        savedir.mkdirs();

        bando = bando != null ? bando : "2";

        codice = StringUtils.replace(codice, "\\", "-");
        codice = StringUtils.replace(codice, "/", "-");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        try {
            from = sdf1.format(sdf2.parse(from));
            to = sdf1.format(sdf2.parse(to));
        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        path = savedir.getPath() + File.separator + "CONV_" + codice + "_" + random + ".pdf";
        int id = Action.insertConvenzione(codice, from, to, politica, (int) request.getSession().getAttribute("idente"), bando, path);

        try {
            if (id > 0) {
                file.write(path);
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert", "C000", String.valueOf(id));
                response.sendRedirect("addConvenzione.jsp?esitoins=OK");
            } else {
                response.sendRedirect("addConvenzione.jsp?esitoins=KO");
            }
        } catch (IOException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            response.sendRedirect("addConvenzione.jsp?esitoins=KO");
        }

    }

    protected void updateConvenzione(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codice = request.getParameter("codice");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String file_old = request.getParameter("file_old");
        String politica = request.getParameter("politica");
        String id = request.getParameter("idconvenzione");
        Part file = request.getPart("file");
        codice = StringUtils.replace(codice, "\\", "/");

        String bando = request.getParameter("bando");
        bando = bando != null ? bando : "2";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        try {
            from = sdf1.format(sdf2.parse(from));
            to = sdf1.format(sdf2.parse(to));
        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        boolean delete = true;// serve per fare il delete del file solo se ce ne era uno precedente.

        if (file != null && file.getSubmittedFileName() != null && file.getSubmittedFileName().length() > 0) {
            if (file_old == null || file_old.equals("null") || file_old.equals("")) {
                delete = false;
                int idente = (int) request.getSession().getAttribute("idente");
                String random = RandomStringUtils.randomAlphabetic(8);
                String ente = (String) request.getSession().getAttribute("ente");
                file_old = Action.getPath("documentdir").replace("@ente", idente + "_" + ente) + "CONV_" + codice + "_" + random + ".pdf";;
            }
            if (delete) {
                new File(file_old).delete();
            }
            file.write(file_old);
        }
        if (Action.updateConvenzione(id, codice, from, to, politica, bando, file_old)) {
            Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update", "C000", id);
            response.sendRedirect("modifyConvenzione.jsp?esitoedit=OK");
        } else {
            response.sendRedirect("modifyConvenzione.jsp?esitoedit=KO");
        }
    }

    protected void insertProgettoFormativo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titolo = request.getParameter("titolo");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String ore = request.getParameter("ore");
        String ccdlav = request.getParameter("lavoratore");
        String scadenza = request.getParameter("scadenza");
        Part file = request.getPart("file");
        String tutor = request.getParameter("tutor");
        String convenzione = request.getParameter("convenzione");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        int idente = (int) request.getSession().getAttribute("idente");
        String ente = (String) request.getSession().getAttribute("ente");

        int m1 = 0, m2 = 0;

        try {
            Date date_f = sdf2.parse(from);
            Date date_t = sdf2.parse(to);

            m1 = date_f.getYear() * 12 + date_f.getMonth();
            m2 = date_t.getYear() * 12 + date_t.getMonth();
            from = sdf1.format(date_f);
            to = sdf1.format(date_t);

        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        int ore_mese = Integer.parseInt(ore) / (m2 - m1 + 1);

        String random = RandomStringUtils.randomAlphabetic(8);

        String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
        File savedir = new File(path);
        savedir.mkdirs();

        try {
            String doc = path + "CI_" + ccdlav + "_Lav_" + random + ".pdf";
            int id = Action.insertPrgFormativo(titolo, ore, String.valueOf(ore_mese), from, to, tutor, ccdlav, doc, scadenza, String.valueOf(idente), convenzione);

            if (id > 0) {
                file.write(savedir.getPath() + File.separator + "CI_" + id + "_Lav_" + random + ".pdf");
                response.sendRedirect("addProgettoFormativo.jsp?esitoins=OK");
            } else {
                response.sendRedirect("addProgettoFormativo.jsp?esitoins=KO");
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.sendRedirect("addProgettoFormativo.jsp?esitoins=KO");
        }
    }

    protected void uploadDocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String tutor = request.getParameter("tutor");
        Part m5 = request.getPart("m5");
        Part file = request.getPart("file");
        Tutor t = Action.findTutorById(tutor);

        if (request.getParameter("tipo").equals("new")) {
            int idente = (int) request.getSession().getAttribute("idente");
            String ente = (String) request.getSession().getAttribute("ente");

            String random = RandomStringUtils.randomAlphabetic(8);

            String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
            File dir = new File(path);
            dir.mkdirs();

            String doc_m5 = dir.getPath() + File.separator + id + "_M5_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_file = dir.getPath() + File.separator + id + "_Doc_" + random + ".pdf";

            try {
                m5.write(doc_m5);
                file.write(doc_file);

                if (Action.uploadDoc(id, tutor, doc_m5, doc_file, t.getDocumento())) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc", "P000", id);
                    response.sendRedirect("upDocRimborsoPolitiche.jsp?esitoedit=OK");
                } else {
                    try {
                        new File(doc_m5).delete();
                        new File(doc_file).delete();
                    } catch (Exception ex) {
                       LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                    response.sendRedirect("upDocRimborsoPolitiche.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocRimborsoPolitiche.jsp?esitoedit=KO");
            }
        } else if (request.getParameter("tipo").equals("mod")) {
            String m5_old = request.getParameter("m5_old");
            String file_old = request.getParameter("file_old");

            try {
                if (m5 != null && m5.getSubmittedFileName() != null && m5.getSubmittedFileName().length() > 0) {
                    new File(m5_old).delete();
                    m5.write(m5_old);
                }
                if (file != null && file.getSubmittedFileName() != null && file.getSubmittedFileName().length() > 0) {
                    new File(file_old).delete();
                    file.write(file_old);
                }

                if (Action.uploadDoc(id, tutor, m5_old, file_old, t.getDocumento())) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc", "P000", id);
                    response.sendRedirect("upDocRimborsoPolitiche.jsp?esitoedit=OK");
                } else {
                    response.sendRedirect("upDocRimborsoPolitiche.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocRimborsoPolitiche.jsp?esitoedit=KO");
            }
        }

    }

    protected void insertRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        String tutor = request.getParameter("tutor");
        Tutor t = Action.findTutorById(tutor);
        String page = request.getParameter("page");
        String politica = request.getParameter("politica");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        if (nome == null) {
            nome = "";
        }
        if (cognome == null) {
            cognome = "";
        }
        if (cf == null) {
            cf = "";
        }
        if (from == null) {
            from = "";
        }
        if (to == null) {
            to = "";
        }
        int id = Action.insertRimborso(tutor, politica, t.getDocumento());

        if (id > 0) {
            if (Action.associaPoliticheRimborso(data, id)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert", "R000", String.valueOf(id));
                String path = "";
                try {
                    path = Action.getPath("temp");
                    File dir = new File(path);
                    dir.mkdirs();
                    path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso.pdf";
                    Pdf_new.writePDF(path, Action.getListPolitichePDF(data), politica, id);
                } catch (FileNotFoundException | DocumentException | ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=OK&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to + "&path=" + path);
            } else {
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO");
            }
        } else {
            response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to);
        }
    }

    protected void updateDocCont(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        //String tutor = request.getParameter("tutor");
        String inizio = request.getParameter("inizio");
        String fine = request.getParameter("fine");
        String indeterminato = request.getParameter("indeterminato");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (indeterminato != null) {
                indeterminato = "Y";
                fine = null;
            } else {
                indeterminato = "N";
                fine = sdf1.format(sdf2.parse(fine));
            }
            inizio = sdf1.format(sdf2.parse(inizio));
        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        Part m5 = request.getPart("m5");
        Part file = request.getPart("file");
        Part contratto = request.getPart("contratto");
        if (request.getParameter("tipo").equals("new")) {
            int idente = (int) request.getSession().getAttribute("idente");
            String ente = (String) request.getSession().getAttribute("ente");

            String random = RandomStringUtils.randomAlphabetic(8);

            String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
            File dir = new File(path);
            dir.mkdirs();

            String doc_m5 = dir.getPath() + File.separator + id + "_M5_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_file = dir.getPath() + File.separator + id + "_Doc_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_contratto = dir.getPath() + File.separator + id + "_Contratto_" + random + ".pdf";

            try {
                m5.write(doc_m5);
                file.write(doc_file);
                contratto.write(doc_contratto);

                if (Action.uploadDocContratto(id, null, doc_m5, doc_file, inizio, fine, doc_contratto, indeterminato, request.getParameter("tipo"))) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc_contratto", "P000", id);
                    response.sendRedirect("upDocContratto.jsp?esitoedit=OK");
                } else {
                    try {
                        new File(doc_m5).delete();
                        new File(doc_file).delete();
                        new File(doc_contratto).delete();
                    } catch (Exception ex) {
                       LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                    response.sendRedirect("upDocContratto.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocContratto.jsp?esitoedit=KO");
            }
        } else if (request.getParameter("tipo").equals("mod")) {
            String m5_old = request.getParameter("m5_old");
            String file_old = request.getParameter("file_old");
            String contratto_old = request.getParameter("contratto_old");

            try {
                if (m5 != null && m5.getSubmittedFileName() != null && m5.getSubmittedFileName().length() > 0) {
                    new File(m5_old).delete();
                    m5.write(m5_old);
                }
                if (file != null && file.getSubmittedFileName() != null && file.getSubmittedFileName().length() > 0) {
                    new File(file_old).delete();
                    file.write(file_old);
                }
                if (contratto != null && contratto.getSubmittedFileName() != null && contratto.getSubmittedFileName().length() > 0) {
                    new File(contratto_old).delete();
                    contratto.write(contratto_old);
                }

                if (Action.uploadDocContratto(id, null, m5_old, file_old, inizio, fine, contratto_old, indeterminato, request.getParameter("tipo"))) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc_contratto", "P000", id);
                    response.sendRedirect("upDocContratto.jsp?esitoedit=OK");
                } else {
                    response.sendRedirect("upDocContratto.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocContratto.jsp?esitoedit=KO");
            }
        }
    }

    protected void scaricaListaPolitiche(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("politica");

        ArrayList<Politica> polt = Action.getListPolitiche(idrimborso);

        String path = Action.getPath("temp");
        File dir = new File(path);
        dir.mkdirs();
        path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso.pdf";

        try {
            Pdf_new.writePDF(path, polt, politica, Integer.parseInt(idrimborso));
        } catch (FileNotFoundException | DocumentException | ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        File downloadFile = new File(path);

        if (downloadFile.exists()) {
            FileInputStream inStream = new FileInputStream(downloadFile);
            String mimeType = Files.probeContentType(downloadFile.toPath());
            if (mimeType == null) {
                mimeType = "application/pdf";
            }
            response.setContentType(mimeType);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attach; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096 * 4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
//            
        }
    }

    protected void uploadDocumentPrgFormativo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String tutor = request.getParameter("tutor");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String scadenza = request.getParameter("scadenza");
        String ore_tot = request.getParameter("ore");
        String convenzione = request.getParameter("convenzione");

        Part file = request.getPart("file");
        Part prg = request.getPart("prg");
        Tutor t = Action.findTutorById(tutor);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            from = sdf1.format(sdf2.parse(from));
//            to = sdf1.format(sdf2.parse(to));
//        } catch (ParseException ex) {
//        }
        int idente = (int) request.getSession().getAttribute("idente");
        String ente = (String) request.getSession().getAttribute("ente");

        int m1 = 0, m2 = 0;

        try {//calcolo durata in mesi del tirocinio.
            scadenza = sdf1.format(sdf2.parse(scadenza));

            Date date_f = sdf2.parse(from);
            Date date_t = sdf2.parse(to);

            m1 = date_f.getYear() * 12 + date_f.getMonth();
            m2 = date_t.getYear() * 12 + date_t.getMonth();
            from = sdf1.format(date_f);
            to = sdf1.format(date_t);

        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        int mesi = m2 - m1;
        int ore_mese = 0;
        if (mesi > 0) {
            ore_mese = Integer.parseInt(ore_tot) / mesi;
        }

        if (request.getParameter("tipo").equals("new")) {

            String random = RandomStringUtils.randomAlphabetic(8);

            String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
            File dir = new File(path);
            dir.mkdirs();

            random = RandomStringUtils.randomAlphabetic(8);
            String doc_file = dir.getPath() + File.separator + id + "_IdDoc_" + "Prg_" + random + ".pdf";
            String doc_prg = null;

            try {
                file.write(doc_file);

                if (prg != null && !prg.getSubmittedFileName().equals("") && prg.getSubmittedFileName().length() > 0) {
                    random = RandomStringUtils.randomAlphabetic(8);
                    doc_prg = dir.getPath() + File.separator + id + "_Prg_" + "Doc_" + random + ".pdf";
                    prg.write(doc_prg);
                }
                String stato = mesi > 0 ? "S" : "C";
//                convenzione = null;//rimuovi quando sblocchi modifica
                if (Action.uploadDocPrg(id, tutor, doc_file, t.getDocumento(), scadenza, ore_tot, mesi, ore_mese, from, to, stato, null, convenzione, doc_prg)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc", "PRG0", id);
                    response.sendRedirect("upDocProgettoFormativo.jsp?esitoedit=OK");
                } else {
                    try {
                        new File(doc_file).delete();
                        new File(doc_prg).delete();
                    } catch (Exception ex) {
                       LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                    response.sendRedirect("upDocProgettoFormativo.jsp?esitoedit=KO");
                }
            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocProgettoFormativo.jsp?esitoedit=KO");
            }
        } else if (request.getParameter("tipo").equals("mod")) {
            String file_old = request.getParameter("file_old");
            String prg_old = request.getParameter("prg_old");
            String comp_old = request.getParameter("comp_old");
            String stato = request.getParameter("stato");
            if (!stato.equals("S")) {
                stato = "C";
            }
            if (comp_old == null || comp_old.equals("null") || comp_old.equals("-")) {
                String random = RandomStringUtils.randomAlphabetic(8);
                String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
                File dir = new File(path);
                comp_old = dir.getPath() + File.separator + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + "_Cer_" + "Prg_" + random + ".pdf";
            }

            Part comp = request.getPart("cert");
            try {
                if (file != null && file.getSubmittedFileName() != null && file.getSubmittedFileName().length() > 0) {
                    File dir = new File(Action.getPath("documentdir").replace("@ente", idente + "_" + ente));
                    dir.mkdirs();
                    String random = RandomStringUtils.randomAlphabetic(8);
                    file_old = dir.getPath() + File.separator + id + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_Doc_" + "Prg_" + random + ".pdf";
                    file.write(file_old);
                }
                if (prg != null && prg.getSubmittedFileName() != null && prg.getSubmittedFileName().length() > 0) {
                    if (prg_old == null || prg_old.equals("null") || prg_old.equals("null")) {
                        File dir = new File(Action.getPath("documentdir").replace("@ente", idente + "_" + ente));
                        dir.mkdirs();
                        String random = RandomStringUtils.randomAlphabetic(8);
                        prg_old = dir.getPath() + File.separator + id + "_Prg_" + "Doc_" + random + ".pdf";
                        prg.write(prg_old);
                    } else {
                        new File(prg_old).delete();
                        prg.write(prg_old);
                    }
                } else if (prg_old.equals("null")) {
                    prg_old = null;
                }
                if (comp != null && comp.getSubmittedFileName() != null && comp.getSubmittedFileName().length() > 0) {
                    new File(comp_old).delete();
                    comp.write(comp_old);
                }

//                convenzione = null;//rimuovi quando sblocchi modifica
//                prg_old = null;//rimuovi quando sblocchi modifica
                if (Action.uploadDocPrg(id, tutor, file_old, t.getDocumento(), scadenza, ore_tot, mesi, ore_mese, from, to, stato, comp_old, convenzione, prg_old)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc", "P000", id);
                    response.sendRedirect("upDocProgettoFormativo.jsp?esitoedit=OK");
                } else {
                    response.sendRedirect("upDocProgettoFormativo.jsp?esitoedit=KO");
                }
            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocProgettoFormativo.jsp?esitoedit=KO");
            }
        }

    }

    protected void insertRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String doc_t = request.getParameter("t_doc");
        String doc_r = request.getParameter("r_doc");
        String idprg = request.getParameter("idprg");
        String mese = request.getParameter("mese");
        String ore = request.getParameter("ore");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        Part reg = request.getPart("file");
        Part quietanza = request.getPart("quietanza");
        Part cert = request.getPart("cert");

        Registro last_reg = Action.getLastRegister(idprg);
        if (last_reg == null || !last_reg.getMese().equals(mese)) {//evito doppio inserimento
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                from = sdf1.format(sdf2.parse(from));
                to = sdf1.format(sdf2.parse(to));
            } catch (ParseException ex) {
               LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }

            int idente = (int) request.getSession().getAttribute("idente");
            String ente = (String) request.getSession().getAttribute("ente");

            String random = RandomStringUtils.randomAlphabetic(8);

            String path = Action.getPath("registri").replace("@ente", idente + "_" + ente);
            File dir = new File(path);
            dir.mkdirs();
            String doc_quiet = dir.getPath() + File.separator + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + "_Quiet_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_file = dir.getPath() + File.separator + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + "_Reg_" + random + ".pdf";
            String doc_cert = "";
            try {
                reg.write(doc_file);
                quietanza.write(doc_quiet);
                boolean last = false;
                if (cert != null && cert.getSubmittedFileName().length() > 0) {
                    random = RandomStringUtils.randomAlphabetic(8);
                    doc_cert = dir.getPath() + File.separator + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + "_Cert_" + random + ".pdf";
                    cert.write(doc_cert);
                    last = true;
                }
                String esito = "esitoins";
                String res = "OK";
                int id = Action.insertRtegistro(ore, mese, doc_file, doc_quiet, doc_t, doc_r, idprg, from, to);
                if (id > 0) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert", "REG0", String.valueOf(id));
                    if (last) {
                        if (Action.updatePrgCert(idprg, doc_cert)) {
                            esito = "esitolast";
                            res = "OK";
                        } else {
                            Action.deleteRegister(id);
                            res = "KO";
                        }
                    }
                    response.sendRedirect("upRegistro.jsp?" + esito + "=" + res);
                } else {
                    new File(doc_file).delete();
                    new File(doc_quiet).delete();
                    response.sendRedirect("upRegistro.jsp?esitoins=KO");
                }

            } catch (IOException ex) {
               LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                response.sendRedirect("upRegistro.jsp?esitoins=KO");
            }
        } else {
            emailManagerEJB.sendEmail(new String[]{"agodino@setacom.it"}, "registro doppio", "registro doppio: prg: " + idprg + " mese: " + mese);
            Utility.redirect(request, response, "upRegistro.jsp?esitoins=doppio");
        }
    }

    protected void insertRimborsoPrg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        String tutor = request.getParameter("tutor");
        Tutor t = Action.findTutorById(tutor);
        String page = request.getParameter("page");
        String politica = request.getParameter("politica");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        if (nome == null) {
            nome = "";
        }
        if (cognome == null) {
            cognome = "";
        }
        if (cf == null) {
            cf = "";
        }
        if (from == null) {
            from = "";
        }
        if (to == null) {
            to = "";
        }

        int id = Action.insertRimborsoPrg(tutor, politica, t.getDocumento());

        if (id > 0) {
            if (Action.associaPrgRimborso(data, id)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert", "RPRG", String.valueOf(id));
                String path = "";
                try {
                    path = Action.getPath("temp");
                    File dir = new File(path);
                    dir.mkdirs();
                    path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso_prg.pdf";
                    Pdf_new.writePrgPDF(path, Action.getListPrgPDF(data), politica, id);
                } catch (FileNotFoundException | DocumentException | ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=OK&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to + "&path=" + path);
            } else {
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO");
            }
        } else {
            response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to);
        }
    }

    protected void scaricaListaPrg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("polica");

        ArrayList<PrgFormativo> polt = Action.getListPrg(idrimborso);
        String path = Action.getPath("temp");
        File dir = new File(path);
        dir.mkdirs();
        path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso.pdf";
        try {
            Pdf_new.writePrgPDF(path, polt, politica, Integer.parseInt(idrimborso));
        } catch (FileNotFoundException | DocumentException | ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        File downloadFile = new File(path);

        if (downloadFile.exists()) {
            FileInputStream inStream = new FileInputStream(downloadFile);
            String mimeType = Files.probeContentType(downloadFile.toPath());
            if (mimeType == null) {
                mimeType = "application/pdf";
            }
            response.setContentType(mimeType);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attach; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096 * 4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
//            
        }
    }

    protected void modifyRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String doc_t = request.getParameter("t_doc");
        String doc_r = request.getParameter("r_doc");
        String old_file = request.getParameter("old_file");
        String old_q = request.getParameter("old_q");
        String idrg = request.getParameter("idrg");
//        String mese = request.getParameter("mese");
        String ore = request.getParameter("ore");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        Part reg = request.getPart("file");
        Part quietanza = request.getPart("quietanza");
        Part doc = request.getPart("doc");
//        Part cert = request.getPart("cert");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            from = sdf1.format(sdf2.parse(from));
            to = sdf1.format(sdf2.parse(to));
        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        int idente = (int) request.getSession().getAttribute("idente");
        String ente = (String) request.getSession().getAttribute("ente");
        String path = Action.getPath("registri").replace("@ente", idente + "_" + ente);
        File dir = new File(path);
        dir.mkdirs();

        try {
            if (reg != null && reg.getSubmittedFileName() != null && reg.getSubmittedFileName().length() > 0) {
                new File(old_file).delete();
                reg.write(old_file);
            }
            if (quietanza != null && quietanza.getSubmittedFileName() != null && quietanza.getSubmittedFileName().length() > 0) {
                new File(old_q).delete();
                quietanza.write(old_q);
            }
            if (doc != null && doc.getSubmittedFileName() != null && doc.getSubmittedFileName().length() > 0) {
                File dir_doc = new File(Action.getPath("documentdir").replace("@ente", idente + "_" + ente));
                dir_doc.mkdirs();
                String random = RandomStringUtils.randomAlphabetic(8);
                doc_r = dir.getPath() + File.separator + idrg + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_Doc_" + "Prg_" + random + ".pdf";
                doc.write(doc_r);
            }
            if (Action.updateRegistro(idrg, ore, doc_t, doc_r, from, to)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update", "REG0", idrg);
                response.sendRedirect("modifyRegistro.jsp?esitoins=OK");
            } else {
                response.sendRedirect("modifyRegistro.jsp?esitoins=KO");
            }

        } catch (IOException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            Action.insertTracking("test SIM", ex.getMessage());
            response.sendRedirect("modifyRegistro.jsp?esitoins=KO");
        }
    }

    protected void uploadDocumentPrgFormativoDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String tutor = request.getParameter("tutor");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String scadenza = request.getParameter("scadenza");
        String ore_tot = request.getParameter("ore");
        Part file = request.getPart("file");
        Part m5 = request.getPart("m5");
        Part prg = request.getPart("prg");
        String convenzione = request.getParameter("convenzione");
        Tutor t = Action.findTutorById(tutor);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            from = sdf1.format(sdf2.parse(from));
//            to = sdf1.format(sdf2.parse(to));
//        } catch (ParseException ex) {
//        }
        int idente = (int) request.getSession().getAttribute("idente");
        String ente = (String) request.getSession().getAttribute("ente");

        int m1 = 0, m2 = 0;

        try {//calcolo durata in mesi del tirocinio.
            scadenza = sdf1.format(sdf2.parse(scadenza));

            Date date_f = sdf2.parse(from);
            Date date_t = sdf2.parse(to);

            m1 = date_f.getYear() * 12 + date_f.getMonth();
            m2 = date_t.getYear() * 12 + date_t.getMonth();
            from = sdf1.format(date_f);
            to = sdf1.format(date_t);

        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        int mesi = m2 - m1;
        int ore_mese = 0;
        if (mesi > 0) {
            ore_mese = Integer.parseInt(ore_tot) / mesi;
        }

        if (request.getParameter("tipo").equals("new")) {

            String random = RandomStringUtils.randomAlphabetic(8);

            String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
            File dir = new File(path);
            dir.mkdirs();

            random = RandomStringUtils.randomAlphabetic(8);
            String doc_file = dir.getPath() + File.separator + id + "_Doc_" + "Prg_" + id + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_m5 = dir.getPath() + File.separator + id + "_M5_" + "Prg_" + id + random + ".pdf";

            String doc_prg = null;

            try {
                file.write(doc_file);
                m5.write(doc_m5);

                if (prg != null && !prg.getSubmittedFileName().equals("") && prg.getSubmittedFileName().length() > 0) {
                    random = RandomStringUtils.randomAlphabetic(8);
                    doc_prg = dir.getPath() + File.separator + id + "_Prg_" + "Doc_" + random + ".pdf";
                    prg.write(doc_prg);
                }

//                convenzione = null;//rimuovi quando sblocchi modifica
                String stato = mesi > 0 ? "S" : "C";
                if (Action.uploadDocPrgDt(id, tutor, doc_file, t.getDocumento(), scadenza, ore_tot, mesi, ore_mese, from, to, stato, doc_m5, null, convenzione, doc_prg)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc", "PFDT", id);
                    response.sendRedirect("upDocProgettoFormativo_Dt.jsp?esitoedit=OK");
                } else {
                    try {
                        new File(doc_file).delete();
                        new File(doc_m5).delete();
                        new File(doc_prg).delete();
                    } catch (Exception ex) {
                       LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                    response.sendRedirect("upDocProgettoFormativo_Dt.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocProgettoFormativo_Dt.jsp?esitoedit=KO");
            }
        } else if (request.getParameter("tipo").equals("mod")) {
            String file_old = request.getParameter("file_old");
            String comp_old = request.getParameter("comp_old");
            String m5_old = request.getParameter("m5_old");
            String prg_old = request.getParameter("prg_old");
            String stato = request.getParameter("stato");
            if (!stato.equals("S")) {
                stato = "C";
            }
            if (comp_old == null || comp_old.equals("null") || comp_old.equals("-")) {
                String random = RandomStringUtils.randomAlphabetic(8);
                String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
                File dir = new File(path);
                comp_old = dir.getPath() + File.separator + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + "_Cer_" + "Prg_" + random + ".pdf";
            }
            Part comp = request.getPart("cert");
            try {
                if (file != null && file.getSubmittedFileName() != null && file.getSubmittedFileName().length() > 0) {
                    File dir = new File(Action.getPath("documentdir").replace("@ente", idente + "_" + ente));
                    dir.mkdirs();
                    String random = RandomStringUtils.randomAlphabetic(8);
                    file_old = dir.getPath() + File.separator + id + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_Doc_" + "Prg_" + random + ".pdf";
                    file.write(file_old);
                }
                if (prg != null && prg.getSubmittedFileName() != null && prg.getSubmittedFileName().length() > 0) {
                    if (prg_old == null || prg_old.equals("null") || prg_old.equals("")) {
                        File dir = new File(Action.getPath("documentdir").replace("@ente", idente + "_" + ente));
                        dir.mkdirs();
                        String random = RandomStringUtils.randomAlphabetic(8);
                        prg_old = dir.getPath() + File.separator + id + "_Prg_" + "Doc_" + random + ".pdf";
                        prg.write(prg_old);
                    } else {
                        new File(prg_old).delete();
                        prg.write(prg_old);
                    }
                } else if (prg_old.equals("null")) {
                    prg_old = null;
                }
                if (m5 != null && m5.getSubmittedFileName() != null && m5.getSubmittedFileName().length() > 0) {
                    new File(m5_old).delete();
                    m5.write(m5_old);
                }
                if (comp != null && comp.getSubmittedFileName() != null && comp.getSubmittedFileName().length() > 0) {
                    new File(comp_old).delete();
                    comp.write(comp_old);
                }

//                convenzione = null;//rimuovi quando sblocchi modifica
//                prg_old = null;//rimuovi quando sblocchi modifica
                if (Action.uploadDocPrgDt(id, tutor, file_old, t.getDocumento(), scadenza, ore_tot, mesi, ore_mese, from, to, stato, m5_old, comp_old, convenzione, prg_old)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc", "PFDT", id);
                    response.sendRedirect("upDocProgettoFormativo_Dt.jsp?esitoedit=OK");
                } else {
                    response.sendRedirect("upDocProgettoFormativo_Dt.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocProgettoFormativo_Dt.jsp?esitoedit=KO");
            }
        }

    }

    protected void insertRimborsoPrgDT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        String tutor = request.getParameter("tutor");
        Tutor t = Action.findTutorById(tutor);
        String page = request.getParameter("page");
        String politica = request.getParameter("politica");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        if (nome == null) {
            nome = "";
        }
        if (cognome == null) {
            cognome = "";
        }
        if (cf == null) {
            cf = "";
        }
        if (from == null) {
            from = "";
        }
        if (to == null) {
            to = "";
        }
        if (politica == null || politica.equals("null") || politica.equals("")) {
            politica = "C06";
        }

        int id = Action.insertRimborsoPrgDt(tutor, politica, t.getDocumento());

        if (id > 0) {
            if (Action.associaPrgRimborsoDt(data, id)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert", "RPDT", String.valueOf(id));
                String path = "";
                try {
                    path = Action.getPath("temp");
                    File dir = new File(path);
                    dir.mkdirs();
                    path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso_prg.pdf";
                    Pdf_new.writePrgPDF_Dt(path, Action.getListPrgPDF_dt(data), politica, id);
                } catch (FileNotFoundException | DocumentException | ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=OK&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to + "&path=" + path);
            } else {
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO");
            }
        } else {
            response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to);
        }
    }

    protected void scaricaListaPrgDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("polica");

        ArrayList<PrgFormativoDt> polt = Action.getListPrgDt(idrimborso);
        String path = Action.getPath("temp");
        File dir = new File(path);
        dir.mkdirs();
        path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso.pdf";
        try {
            Pdf_new.writePrgPDF_Dt(path, polt, politica, Integer.parseInt(idrimborso));
        } catch (FileNotFoundException | DocumentException | ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        File downloadFile = new File(path);

        if (downloadFile.exists()) {
            FileInputStream inStream = new FileInputStream(downloadFile);
            String mimeType = Files.probeContentType(downloadFile.toPath());
            if (mimeType == null) {
                mimeType = "application/pdf";
            }
            response.setContentType(mimeType);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attach; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096 * 4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
//            
        }
    }

    protected void insertRegistroDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String doc_t = request.getParameter("t_doc");
        String doc_r = request.getParameter("r_doc");
        String idprg = request.getParameter("idprg");
        String mese = request.getParameter("mese");
        String ore = request.getParameter("ore");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        Part reg = request.getPart("file");
        Part cert = request.getPart("cert");

        RegistroDt last_reg = Action.getLastRegisterDt(idprg);
        if (last_reg == null || !last_reg.getMese().equals(mese)) {//evito doppio inserimento

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                from = sdf1.format(sdf2.parse(from));
                to = sdf1.format(sdf2.parse(to));
            } catch (ParseException ex) {
               LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }

            int idente = (int) request.getSession().getAttribute("idente");
            String ente = (String) request.getSession().getAttribute("ente");

            String random = RandomStringUtils.randomAlphabetic(8);

            String path = Action.getPath("registri").replace("@ente", idente + "_" + ente);
            File dir = new File(path);
            dir.mkdirs();
            String doc_file = dir.getPath() + File.separator + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + "_Reg_" + random + ".pdf";
            String doc_cert = "";
            try {
                reg.write(doc_file);

                boolean last = false;
                if (cert != null && cert.getSubmittedFileName().length() > 0) {
                    random = RandomStringUtils.randomAlphabetic(8);
                    doc_cert = dir.getPath() + File.separator + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + "_Cert_" + random + ".pdf";
                    cert.write(doc_cert);
                    last = true;
                }
                String esito = "esitoins";
                String res = "OK";
                int id = Action.insertRtegistroDt(ore, mese, doc_file, doc_t, doc_r, idprg, from, to);
                if (id > 0) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert", "RGDT", String.valueOf(id));
                    if (last) {
                        if (Action.updatePrgCertDt(idprg, doc_cert)) {
                            esito = "esitolast";
                            res = "OK";
                        } else {
                            Action.deleteRegister(id);
                            res = "KO";
                        }
                    }
                    Utility.redirect(request, response, "upRegistroDt.jsp?" + esito + "=" + res);
//                response.sendRedirect("upRegistroDt.jsp?" + esito + "=" + res);
                } else {
                    new File(doc_file).delete();
                    Utility.redirect(request, response, "upRegistroDt.jsp?esitoins=KO");
//                response.sendRedirect("upRegistroDt.jsp?esitoins=KO");
                }

            } catch (IOException ex) {
               LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                Utility.redirect(request, response, "upRegistroDt.jsp?esitoins=KO");
//            response.sendRedirect("upRegistroDt.jsp?esitoins=KO");
            }
        } else {
            emailManagerEJB.sendEmail(new String[]{"agodino@setacom.it"}, "registro doppio", "registro doppio: prg: " + idprg + " mese: " + mese);
            Utility.redirect(request, response, "upRegistro.jsp?esitoins=doppio");
        }
    }

    protected void modifyRegistroDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String doc_t = request.getParameter("t_doc");
        String doc_r = request.getParameter("r_doc");
        String old_file = request.getParameter("old_file");
        String idrg = request.getParameter("idrg");
//        String mese = request.getParameter("mese");
        String ore = request.getParameter("ore");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        Part reg = request.getPart("file");
        Part doc = request.getPart("doc");

//        Part cert = request.getPart("cert");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            from = sdf1.format(sdf2.parse(from));
            to = sdf1.format(sdf2.parse(to));
        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        int idente = (int) request.getSession().getAttribute("idente");
        String ente = (String) request.getSession().getAttribute("ente");
        String path = Action.getPath("registri").replace("@ente", idente + "_" + ente);
        File dir = new File(path);
        dir.mkdirs();

        try {
            if (reg != null && reg.getSubmittedFileName() != null && reg.getSubmittedFileName().length() > 0) {
                new File(old_file).delete();
                reg.write(old_file);
            }
            if (doc != null && doc.getSubmittedFileName() != null && doc.getSubmittedFileName().length() > 0) {
                File dir_doc = new File(Action.getPath("documentdir").replace("@ente", idente + "_" + ente));
                dir_doc.mkdirs();
                String random = RandomStringUtils.randomAlphabetic(8);
                doc_r = dir.getPath() + File.separator + idrg + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_Doc_" + "Prg_" + random + ".pdf";
                doc.write(doc_r);
            }

            if (Action.updateRegistroDt(idrg, ore, doc_t, doc_r, from, to)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update", "REG0", idrg);
                response.sendRedirect("modifyRegistroDt.jsp?esitoins=OK");
            } else {
                response.sendRedirect("modifyRegistroDt.jsp?esitoins=KO");
            }

        } catch (IOException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            response.sendRedirect("modifyRegistroDt.jsp?esitoins=KO");
        }
    }

    protected void uploadDocumentDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String tutor = request.getParameter("tutor");
        Part m5 = request.getPart("m5");
        Part file = request.getPart("file");
        Tutor t = Action.findTutorById(tutor);

        if (request.getParameter("tipo").equals("new")) {
            int idente = (int) request.getSession().getAttribute("idente");
            String ente = (String) request.getSession().getAttribute("ente");

            String random = RandomStringUtils.randomAlphabetic(8);

            String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
            File dir = new File(path);
            dir.mkdirs();

            String doc_m5 = dir.getPath() + File.separator + id + "_M5_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_file = dir.getPath() + File.separator + id + "_Doc_" + random + ".pdf";

            try {
                m5.write(doc_m5);
                file.write(doc_file);

                if (Action.uploadDocDt(id, tutor, doc_m5, doc_file, t.getDocumento())) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc", "PDT0", id);
                    response.sendRedirect("upDocRimborsoPoliticheDt.jsp?esitoedit=OK");
                } else {
                    try {
                        new File(doc_m5).delete();
                        new File(doc_file).delete();
                    } catch (Exception ex) {
                       LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                    response.sendRedirect("upDocRimborsoPoliticheDt.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocRimborsoPoliticheDt.jsp?esitoedit=KO");
            }
        } else if (request.getParameter("tipo").equals("mod")) {
            String m5_old = request.getParameter("m5_old");
            String file_old = request.getParameter("file_old");

            try {
                if (m5 != null && m5.getSubmittedFileName() != null && m5.getSubmittedFileName().length() > 0) {
                    new File(m5_old).delete();
                    m5.write(m5_old);
                }
                if (file != null && file.getSubmittedFileName() != null && file.getSubmittedFileName().length() > 0) {
                    new File(file_old).delete();
                    file.write(file_old);
                }

                if (Action.uploadDocDt(id, tutor, m5_old, file_old, t.getDocumento())) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc", "PDT0", id);
                    response.sendRedirect("upDocRimborsoPoliticheDt.jsp?esitoedit=OK");
                } else {
                    response.sendRedirect("upDocRimborsoPoliticheDt.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocRimborsoPoliticheDt.jsp?esitoedit=KO");
            }
        }
    }

    protected void insertRimborsoDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        String tutor = request.getParameter("tutor");
        Tutor t = Action.findTutorById(tutor);
        String page = request.getParameter("page");
        String politica = request.getParameter("politica");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        if (nome == null) {
            nome = "";
        }
        if (cognome == null) {
            cognome = "";
        }
        if (cf == null) {
            cf = "";
        }
        if (from == null) {
            from = "";
        }
        if (to == null) {
            to = "";
        }
        int id = Action.insertRimborsoDt(tutor, politica, t.getDocumento());

        if (id > 0) {
            if (Action.associaPoliticheRimborsoDt(data, id)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert", "RDT0", String.valueOf(id));
                String path = "";
                try {
                    path = Action.getPath("temp");
                    File dir = new File(path);
                    dir.mkdirs();
                    path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso.pdf";
                    Pdf_new.writePDFDt(path, Action.getListPolitichePDFDt(data), politica, id);
                } catch (FileNotFoundException | DocumentException | ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=OK&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to + "&path=" + path);
            } else {
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO");
            }
        } else {
            response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to);
        }
    }

    protected void scaricaListaPoliticheDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("politica");

        ArrayList<Politica> polt = Action.getListPoliticheDt(idrimborso);

        String path = Action.getPath("temp");
        File dir = new File(path);
        dir.mkdirs();
        path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso.pdf";

        try {
            Pdf_new.writePDFDt(path, polt, politica, Integer.parseInt(idrimborso));
        } catch (FileNotFoundException | DocumentException | ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        File downloadFile = new File(path);

        if (downloadFile.exists()) {
            FileInputStream inStream = new FileInputStream(downloadFile);
            String mimeType = Files.probeContentType(downloadFile.toPath());
            if (mimeType == null) {
                mimeType = "application/pdf";
            }
            response.setContentType(mimeType);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attach; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096 * 4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
//            
        }
    }

    protected void updateDocContDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        //String tutor = request.getParameter("tutor");
//        String inizio = request.getParameter("inizio");
//        String fine = request.getParameter("fine");
//        String indeterminato = request.getParameter("indeterminato");
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
//
//        try {
//            if (indeterminato != null) {
//                indeterminato = "Y";
//                fine = null;
//            } else {
//                indeterminato = "N";
//                fine = sdf1.format(sdf2.parse(fine));
//            }
//            inizio = sdf1.format(sdf2.parse(inizio));
//        } catch (ParseException ex) {
//
//        }
        Part m5 = request.getPart("m5");
        Part file = request.getPart("file");
        Part contratto = request.getPart("contratto");
        if (request.getParameter("tipo").equals("new")) {
            int idente = (int) request.getSession().getAttribute("idente");
            String ente = (String) request.getSession().getAttribute("ente");

            String random = RandomStringUtils.randomAlphabetic(8);

            String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
            File dir = new File(path);
            dir.mkdirs();

            String doc_m5 = dir.getPath() + File.separator + id + "_M5_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_file = dir.getPath() + File.separator + id + "_Doc_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_contratto = dir.getPath() + File.separator + id + "_Contratto_" + random + ".pdf";

            try {
                m5.write(doc_m5);
                file.write(doc_file);
                contratto.write(doc_contratto);

                if (Action.uploadDocContrattoDt(id, null, doc_m5, doc_file, doc_contratto, request.getParameter("tipo"))) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc_contratto", "PDT0", id);
                    response.sendRedirect("upDocContratto_dt.jsp?esitoedit=OK");
                } else {
                    try {
                        new File(doc_m5).delete();
                        new File(doc_file).delete();
                        new File(doc_contratto).delete();
                    } catch (Exception ex) {
                       LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                    response.sendRedirect("upDocContratto_dt.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocContratto_dt.jsp?esitoedit=KO");
            }
        } else if (request.getParameter("tipo").equals("mod")) {
            String m5_old = request.getParameter("m5_old");
            String file_old = request.getParameter("file_old");
            String contratto_old = request.getParameter("contratto_old");

            try {
                if (m5 != null && m5.getSubmittedFileName() != null && m5.getSubmittedFileName().length() > 0) {
                    new File(m5_old).delete();
                    m5.write(m5_old);
                }
                if (file != null && file.getSubmittedFileName() != null && file.getSubmittedFileName().length() > 0) {
                    new File(file_old).delete();
                    file.write(file_old);
                }
                if (contratto != null && contratto.getSubmittedFileName().length() > 0) {
                    new File(contratto_old).delete();
                    contratto.write(contratto_old);
                }

                if (Action.uploadDocContrattoDt(id, null, m5_old, file_old, contratto_old, request.getParameter("tipo"))) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc_contratto", "PDT0", id);
                    response.sendRedirect("upDocContratto_dt.jsp?esitoedit=OK");
                } else {
                    response.sendRedirect("upDocContratto_dt.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocContratto_dt.jsp?esitoedit=KO");
            }
        }
    }

    protected void updateDocB3Dt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        //String tutor = request.getParameter("tutor");
//        String inizio = request.getParameter("inizio");
//        String fine = request.getParameter("fine");
//        String indeterminato = request.getParameter("indeterminato");
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
//
//        try {
//            if (indeterminato != null) {
//                indeterminato = "Y";
//                fine = null;
//            } else {
//                indeterminato = "N";
//                fine = sdf1.format(sdf2.parse(fine));
//            }
//            inizio = sdf1.format(sdf2.parse(inizio));
//        } catch (ParseException ex) {
//
//        }
        Part registro = request.getPart("registro");
        Part file = request.getPart("file");
        Part business = request.getPart("business");
        Part timesheet = request.getPart("timesheet");

        if (request.getParameter("tipo").equals("new")) {
            int idente = (int) request.getSession().getAttribute("idente");
            String ente = (String) request.getSession().getAttribute("ente");

            String random = RandomStringUtils.randomAlphabetic(8);

            String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
            File dir = new File(path);
            dir.mkdirs();

            String doc_registro = dir.getPath() + File.separator + id + "_Registro_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_file = dir.getPath() + File.separator + id + "_Doc_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_business = dir.getPath() + File.separator + id + "_Business_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_timesheet = dir.getPath() + File.separator + id + "_Timesheet_" + random + ".pdf";

            try {
                registro.write(doc_registro);
                file.write(doc_file);
                business.write(doc_business);
                timesheet.write(doc_timesheet);

                if (Action.uploadDocB3(id, doc_file, doc_registro, doc_business, doc_timesheet)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc_contratto", "PDT0", id);
                    response.sendRedirect("upDocB3_dt.jsp?esitoedit=OK");
                } else {
                    try {
                        new File(doc_registro).delete();
                        new File(doc_file).delete();
                        new File(doc_business).delete();
                        new File(doc_timesheet).delete();
                    } catch (Exception ex) {
                       LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                    response.sendRedirect("upDocB3_dt.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocB3_dt.jsp?esitoedit=KO");
            }
        } else if (request.getParameter("tipo").equals("mod")) {
            String registro_old = request.getParameter("registro_old");
            String file_old = request.getParameter("file_old");
            String business_old = request.getParameter("business_old");
            String timesheet_old = request.getParameter("timesheet_old");

            try {
                if (registro != null && registro.getSubmittedFileName() != null && registro.getSubmittedFileName().length() > 0) {
                    new File(registro_old).delete();
                    registro.write(registro_old);
                }
                if (file != null && file.getSubmittedFileName() != null && file.getSubmittedFileName().length() > 0) {
                    new File(file_old).delete();
                    file.write(file_old);
                }
                if (business != null && business.getSubmittedFileName() != null && business.getSubmittedFileName().length() > 0) {
                    new File(business_old).delete();
                    business.write(business_old);
                }
                if (timesheet != null && timesheet.getSubmittedFileName() != null && timesheet.getSubmittedFileName().length() > 0) {
                    new File(timesheet_old).delete();
                    timesheet.write(timesheet_old);
                }

                if (Action.uploadDocB3(id, file_old, registro_old, business_old, timesheet_old)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc", "B300", id);
                    response.sendRedirect("upDocB3_dt.jsp?esitoedit=OK");
                } else {
                    response.sendRedirect("upDocB3_dt.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocB3_dt.jsp?esitoedit=KO");
            }
        }
    }

    protected void insertRimborsoB3Dt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        String tutor = request.getParameter("tutor");
        Tutor t = Action.findTutorById(tutor);
        String page = request.getParameter("page");
        String politica = request.getParameter("politica");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        if (nome == null) {
            nome = "";
        }
        if (cognome == null) {
            cognome = "";
        }
        if (cf == null) {
            cf = "";
        }
        if (from == null) {
            from = "";
        }
        if (to == null) {
            to = "";
        }
        int id = Action.insertRimborsoB3Dt(tutor, politica, t.getDocumento());

        if (id > 0) {
            if (Action.associaPoliticheRimborsoB3Dt(data, id)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert", "RB30", String.valueOf(id));
                String path = "";
                try {
                    path = Action.getPath("temp");
                    File dir = new File(path);
                    dir.mkdirs();
                    path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso.pdf";
                    Pdf_new.writePDFB3Dt(path, Action.getListB3PDFDt(data), politica, id);
                } catch (FileNotFoundException | DocumentException | ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=OK&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to + "&path=" + path);
            } else {
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO");
            }
        } else {
            response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to);
        }
    }

    protected void scaricaListaB3Dt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("politica");

        ArrayList<B3> polt = Action.getListB3Dt(idrimborso);

        String path = Action.getPath("temp");
        File dir = new File(path);
        dir.mkdirs();
        path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso.pdf";

        try {
            Pdf_new.writePDFB3Dt(path, polt, politica, Integer.parseInt(idrimborso));
        } catch (FileNotFoundException | DocumentException | ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        File downloadFile = new File(path);

        if (downloadFile.exists()) {
            FileInputStream inStream = new FileInputStream(downloadFile);
            String mimeType = Files.probeContentType(downloadFile.toPath());
            if (mimeType == null) {
                mimeType = "application/pdf";
            }
            response.setContentType(mimeType);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attach; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096 * 4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
//            
        }
    }

    protected void updateDocVoucherDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String voucher = request.getParameter("budget") + "." + request.getParameter("centesimi");
        String ore = request.getParameter("ore");
        Part registro = request.getPart("registro");
        Part file = request.getPart("file");
        Part allegato = request.getPart("allegato");
        Part attestato = request.getPart("attestato");
        Part delega = request.getPart("delega");

        if (voucher.equals(".")) {
            voucher = "0.00";
        } else if (voucher.endsWith(".")) {
            voucher = voucher + "00";
        }

        if (request.getParameter("tipo").equals("new")) {
            int idente = (int) request.getSession().getAttribute("idente");
            String ente = (String) request.getSession().getAttribute("ente");

            String random = RandomStringUtils.randomAlphabetic(8);

            String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
            File dir = new File(path);
            dir.mkdirs();

            String doc_registro = dir.getPath() + File.separator + id + "_Registro_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_file = dir.getPath() + File.separator + id + "_Doc_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_allegato = dir.getPath() + File.separator + id + "_Allegato_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_attestato = dir.getPath() + File.separator + id + "_Attestato_" + random + ".pdf";
            random = RandomStringUtils.randomAlphabetic(8);
            String doc_delega = dir.getPath() + File.separator + id + "_Delega_" + random + ".pdf";

            try {
                registro.write(doc_registro);
                file.write(doc_file);
                allegato.write(doc_allegato);
                attestato.write(doc_attestato);
                delega.write(doc_delega);

                if (Action.uploadDocVoucher(id, doc_file, doc_registro, doc_attestato, doc_allegato, doc_delega, voucher, ore)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc_contratto", "PDT0", id);
                    response.sendRedirect("upDocB3_dt.jsp?esitoedit=OK");
                } else {
                    try {
                        new File(doc_registro).delete();
                        new File(doc_file).delete();
                        new File(doc_attestato).delete();
                        new File(doc_allegato).delete();
                        new File(doc_delega).delete();
                    } catch (Exception ex) {
                       LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                    response.sendRedirect("upDocVoucher_dt.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocVoucher_dt.jsp?esitoedit=KO");
            }
        } else if (request.getParameter("tipo").equals("mod")) {
            String registro_old = request.getParameter("registro_old");
            String file_old = request.getParameter("file_old");
            String allegato_old = request.getParameter("allegato_old");
            String attestato_old = request.getParameter("attestato_old");
            String delega_old = request.getParameter("delega_old");

            try {
                if (registro != null && registro.getSubmittedFileName() != null && registro.getSubmittedFileName().length() > 0) {
                    new File(registro_old).delete();
                    registro.write(registro_old);
                }
                if (file != null && file.getSubmittedFileName() != null && file.getSubmittedFileName().length() > 0) {
                    new File(file_old).delete();
                    file.write(file_old);
                }
                if (allegato != null && allegato.getSubmittedFileName() != null && allegato.getSubmittedFileName().length() > 0) {
                    new File(allegato_old).delete();
                    allegato.write(allegato_old);
                }
                if (attestato != null && attestato.getSubmittedFileName() != null && attestato.getSubmittedFileName().length() > 0) {
                    new File(attestato_old).delete();
                    attestato.write(attestato_old);
                }
                if (delega != null && delega.getSubmittedFileName() != null && delega.getSubmittedFileName().length() > 0) {
                    new File(delega_old).delete();
                    delega.write(delega_old);
                }

                if (Action.uploadDocVoucher(id, file_old, registro_old, attestato_old, allegato_old, delega_old, voucher, ore)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update_doc", "VDT0", id);
                    response.sendRedirect("upDocVoucher_dt.jsp?esitoedit=OK");
                } else {
                    response.sendRedirect("upDocVoucher_dt.jsp?esitoedit=KO");
                }

            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("upDocVoucher_dt.jsp?esitoedit=KO");
            }
        }
    }

    protected void insertRimborsoVoucherDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        String tutor = request.getParameter("tutor");
        Tutor t = Action.findTutorById(tutor);
        String page = request.getParameter("page");
        String politica = request.getParameter("politica");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        if (nome == null) {
            nome = "";
        }
        if (cognome == null) {
            cognome = "";
        }
        if (cf == null) {
            cf = "";
        }
        if (from == null) {
            from = "";
        }
        if (to == null) {
            to = "";
        }
        int id = Action.insertRimborsoVoucherDt(tutor, politica, t.getDocumento());

        if (id > 0) {
            if (Action.associaRimborsoVoucherDt(data, id)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert", "RB30", String.valueOf(id));
                String path = "";
                try {
                    path = Action.getPath("temp");
                    File dir = new File(path);
                    dir.mkdirs();
                    path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso.pdf";
                    Pdf_new.writePDFVoucherDt(path, Action.getListVoucherPDFDt(data), politica, id);
                } catch (FileNotFoundException | DocumentException | ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=OK&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to + "&path=" + path);
            } else {
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO");
            }
        } else {
            response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&from=" + from + "&to=" + to);
        }
    }

    protected void scaricaListaVoucherDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("politica");

        ArrayList<Voucher> polt = Action.getListVoucherDt(idrimborso);

        String path = Action.getPath("temp");
        File dir = new File(path);
        dir.mkdirs();
        path = path + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + "_" + (String) request.getSession().getAttribute("ente") + "_lista_rimborso.pdf";

        try {
            Pdf_new.writePDFVoucherDt(path, polt, politica, Integer.parseInt(idrimborso));
        } catch (FileNotFoundException | DocumentException | ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        File downloadFile = new File(path);

        if (downloadFile.exists()) {
            FileInputStream inStream = new FileInputStream(downloadFile);
            String mimeType = Files.probeContentType(downloadFile.toPath());
            if (mimeType == null) {
                mimeType = "application/pdf";
            }
            response.setContentType(mimeType);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attach; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096 * 4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
//            
        }
    }

    protected void getBandoPolitiche(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String bando = request.getParameter("bando");
        ArrayList<String[]> politica = Action.getListTipoPolitica(bando);

        ObjectMapper mapper = new ObjectMapper();
        String sl = mapper.writeValueAsString(politica);//json
        response.getWriter().write(sl);
    }

    protected void statoRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String codice = request.getParameter("cod");
        String idrimborso = request.getParameter("idrimborso");

        String stato = Action.getStatoRimborso(idrimborso, codice);
        response.getWriter().write(stato != null ? stato : "null");
    }

    protected void updateTutor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cognome = request.getParameter("cognome");
        String nome = request.getParameter("nome");
        String ruolo = request.getParameter("ruolo");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String cf = request.getParameter("cf");
        String idtutor = request.getParameter("idtutor");

        if (email.equals("")) {
            email = "-";
        }
        if (telefono.equals("")) {
            telefono = "-";
        }

        int idente = (int) request.getSession().getAttribute("idente");
        String ente = (String) request.getSession().getAttribute("ente");
        String random = RandomStringUtils.randomAlphabetic(8);

        String path = Action.getPath("documentdir").replace("@ente", idente + "_" + ente);
        File savedir = new File(path);
        savedir.mkdirs();

        String doc = path + "CI_" + cognome + "_" + nome + "_" + random + ".pdf";
        doc = doc.replace("'", "_").replace("\"", "_");

        if (Action.updateTutor(idtutor, nome, cognome, cf, email, telefono, ruolo)) {
            Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update", "T000", idtutor);
            response.sendRedirect("modifyTutor.jsp?esitoedit=OK&idtutor=" + idtutor);
        } else {
            response.sendRedirect("modifyTutor.jsp?esitoedit=KOidtutor=" + idtutor);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String link_value = Utility.verifySession(request);
        if (link_value != null) {
            redirect(request, response, link_value);
        } else {
            try {
                response.setContentType("text/html;charset=UTF-8");
                String type = request.getParameter("type");
                if ((String) request.getSession().getAttribute("username") == null) {
                    response.sendRedirect("login.jsp");
                } else {
                    String user = (String) request.getSession().getAttribute("username");
                    int stato = (int) request.getSession().getAttribute("status");
                    int tipo = (int) request.getSession().getAttribute("tipo");
                    if (tipo == 2) {
                        if (user == null && stato != 3) {
                            redirect(request, response, "Login?type=2");
                        } else if (type.equals("1")) {
                            addTutor(request, response);
                        } else if (type.equals("2")) {
                            deleteTutor(request, response);
                        } else if (type.equals("3")) {
                            updateDocId(request, response);
                        } else if (type.equals("4")) {
                            insertConvenzione(request, response);
                        } else if (type.equals("5")) {
                            updateConvenzione(request, response);
                        } else if (type.equals("6")) {
                            insertProgettoFormativo(request, response);
                        } else if (type.equals("7")) {
                            uploadDocument(request, response);
                        } else if (type.equals("8")) {
                            insertRimborso(request, response);
                        } else if (type.equals("9")) {
                            updateDocCont(request, response);
                        } else if (type.equals("10")) {
                            scaricaListaPolitiche(request, response);
                        } else if (type.equals("11")) {
                            uploadDocumentPrgFormativo(request, response);
                        } else if (type.equals("12")) {
                            insertRegistro(request, response);
                        } else if (type.equals("13")) {
                            insertRimborsoPrg(request, response);
                        } else if (type.equals("14")) {
                            scaricaListaPrg(request, response);
                        } else if (type.equals("15")) {
                            modifyRegistro(request, response);
                        } else if (type.equals("16")) {
                            uploadDocumentPrgFormativoDt(request, response);
                        } else if (type.equals("17")) {
                            insertRimborsoPrgDT(request, response);
                        } else if (type.equals("18")) {
                            scaricaListaPrgDt(request, response);
                        } else if (type.equals("19")) {
                            insertRegistroDt(request, response);
                        } else if (type.equals("20")) {
                            modifyRegistroDt(request, response);
                        } else if (type.equals("21")) {
                            uploadDocumentDt(request, response);
                        } else if (type.equals("22")) {
                            insertRimborsoDt(request, response);
                        } else if (type.equals("23")) {
                            scaricaListaPoliticheDt(request, response);
                        } else if (type.equals("24")) {
                            updateDocContDt(request, response);
                        } else if (type.equals("25")) {
                            updateDocB3Dt(request, response);
                        } else if (type.equals("26")) {
                            insertRimborsoB3Dt(request, response);
                        } else if (type.equals("27")) {
                            scaricaListaB3Dt(request, response);
                        } else if (type.equals("28")) {
                            updateDocVoucherDt(request, response);
                        } else if (type.equals("29")) {
                            insertRimborsoVoucherDt(request, response);
                        } else if (type.equals("30")) {
                            scaricaListaVoucherDt(request, response);
                        } else if (type.equals("31")) {
                            getBandoPolitiche(request, response);
                        } else if (type.equals("32")) {
                            statoRimborso(request, response);
                        } else if (type.equals("33")) {
                            updateTutor(request, response);
                        }
                    } else {
                        redirect(request, response, "Login?type=2");
                    }
                }
            } catch (ServletException | IOException ex) {
               LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
