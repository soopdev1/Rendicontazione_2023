/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import com.itextpdf.text.DocumentException;
import com.seta.activity.Action;
import com.seta.entity.Bando;
import com.seta.entity.DecreaseRefill;
import com.seta.entity.FileINPS;
import com.seta.util.Utility;
import static com.seta.util.Utility.redirect;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seta.entity.Convenzione;
import com.seta.entity.Ente;
import com.seta.entity.PrgFormativo;
import com.seta.entity.PrgFormativoDt;
import org.apache.commons.lang3.StringUtils;
import java.util.logging.*;
/**
 *
 * @author dolivo
 */
public class OperazioniRegional extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger( OperazioniRegional.class.getName() );
	
    public void caricaBando(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String titolo = Utility.CaratteriSpeciali(request.getParameter("titolo"));
        String datainizio = request.getParameter("datainizio");
        String datafine = request.getParameter("datafine");
        String flag_sportello = request.getParameter("flag_sportello");
        String tipo = request.getParameter("tipo");
        String decreto = Utility.CaratteriSpeciali(request.getParameter("decreto"));
        String budget = request.getParameter("budget");
        String centesimi = request.getParameter("centesimi");
        Part file = request.getPart("file");
        String path = "-";
        String random = UUID.randomUUID().toString().substring(0, 4);

        if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
            File savedir = new File(Action.getPath("documentibandi"));
            savedir.mkdirs();
            path = savedir.getAbsolutePath() + File.separator + "BANDO_" + titolo.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
            file.write(path);
        }
        if (centesimi == null || centesimi.equals("")) {
            centesimi = "00";
        }

        budget = budget + "." + centesimi;
        if ("on".equalsIgnoreCase(flag_sportello)) {
            flag_sportello = "Y";
            datafine = null;
        } else {
            flag_sportello = "N";
            try {
                datafine = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(datafine));
            } catch (ParseException ex) {
               LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        try {
            datainizio = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(datainizio));
        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        int id = Action.Insert_Bando(titolo, datainizio, datafine, flag_sportello, tipo, budget, path, decreto);
        if (id > 0) {
            Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert", "B000", String.valueOf(id));
            response.sendRedirect("upload_bando.jsp?esitoins=OK");
        } else {
            response.sendRedirect("upload_bando.jsp?esitoins=KO");
        }
    }

    public void modificaBando(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("idbando");
        String titolo = request.getParameter("titolo");
        String datainizio = request.getParameter("datainizio");
        String datafine = request.getParameter("datafine");
        String flag_sportello = request.getParameter("flag_sportello");
        String tipo = request.getParameter("tipo");
        String decreto = request.getParameter("decreto");
        String budget = request.getParameter("budget");
        String centesimi = request.getParameter("centesimi");
        Part file = request.getPart("file");
        String path = request.getParameter("old_path");

        String piu_meno = request.getParameter("piu_meno");
        String budgets = request.getParameter("budgets").replace(",", ".");
        String budgetp = request.getParameter("budgetp").replace(",", ".");
        String budgeta = request.getParameter("budgeta").replace(",", ".");
        //String path = "-";
        String random = UUID.randomUUID().toString().substring(0, 4);

        if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
            File savedir = new File(Action.getPath("documentibandi"));
            savedir.mkdirs();
            path = savedir.getAbsolutePath() + File.separator + "BANDO_" + titolo.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
            file.write(path);
        }

        if ("on".equalsIgnoreCase(flag_sportello)) {
            flag_sportello = "Y";
            datafine = null;
        } else {
            flag_sportello = "N";
            try {
                datafine = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(datafine));
            } catch (ParseException ex) {
               LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        if (centesimi == null || centesimi.equals("")) {
            centesimi = "00";
        }

        budget = budget + "." + centesimi;
        if (piu_meno == null) {
            Double a_pr = Double.parseDouble(budget);
            budget = String.valueOf(-a_pr);
        }

        //INCREMENTO O DECREMENTO BUDGET
        budgets = String.valueOf(Double.parseDouble(budgets) + Double.parseDouble(budget));
        if (Double.parseDouble(budgeta) < 0) {
            budgetp = "0";
        }
        budgetp = String.valueOf(Double.parseDouble(budgetp) + Double.parseDouble(budget));
        if (Double.parseDouble(budgetp) < 0) {
            budgetp = "0";
        }
        budgeta = String.valueOf(Double.parseDouble(budgeta) + Double.parseDouble(budget));
        if (Double.parseDouble(budgeta) < 0) {
            budgeta = "0";
        }

        try {
            datainizio = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(datainizio));
        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        if (Action.Modify_Bando(id, titolo, datainizio, datafine, flag_sportello, tipo, budgets, path, decreto, budgeta, budgetp)) {
            Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update", "B000", id);
            response.sendRedirect("modify_bando.jsp?esitomod=OK&idbando=" + id);
        } else {
            response.sendRedirect("modify_bando.jsp?esitomod=KO&idbando=" + id);
        }
    }

    public void configuraBando(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("idbando");
        String tipo = request.getParameter("tipo");
        //String flag = request.getParameter("flag");
        //ArrayList<String[]> tipopol = Action.getListTipoPolitica(tipo,flag);
        ArrayList<String[]> tipopol = Action.getListTipoPolitica(tipo);
        ArrayList<String[]> val = new ArrayList<String[]>();
        for (int i = 0; i < tipopol.size(); i++) {
            //String a = request.getParameter("budget" + i);
            String a = request.getParameter("budget" + tipopol.get(i)[0]);
            String b = request.getParameter("centesimi" + tipopol.get(i)[0]);
            if (a.equals("")) {
                a = "0";
            }
            if (b == null || b.equals("")) {
                b = "00";
            }
            a = a + "." + b;
            String[] v = {tipopol.get(i)[0], a};
            val.add(v);
//            if (!a.equals("") && !a.equals("0")) {
//                String[] v = {tipopol.get(i)[0], a};
//                val.add(v);
//            }
        }
        if (Action.insertBudgetPolitiche(id, val)) {
            Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Insert Config.Bando", "B000", id);
            response.sendRedirect("configuration_bando.jsp?esitomod=OK&idbando=" + id + "&tipo=" + tipo);
        } else {
            response.sendRedirect("configuration_bando.jsp?esitomod=KO&idbando=" + id + "&tipo=" + tipo);
        }
    }

    public void mod_configuraBando(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("idbando");
        String tipo = request.getParameter("tipo");
        //String flag = request.getParameter("flag");
        //ArrayList<String[]> tipopol = Action.getBudgetsPolitiche(id,flag,tipo);
        ArrayList<String[]> tipopol = Action.getBudgetsPolitiche(id, tipo);
        ArrayList<String[]> val = new ArrayList<String[]>();
        for (int i = 0; i < tipopol.size(); i++) {
            String a = request.getParameter("budget" + tipopol.get(i)[0]);
            String b = request.getParameter("centesimi" + tipopol.get(i)[0]);
            String piu_meno = request.getParameter("piu_meno" + tipopol.get(i)[0]);
            String budgetattuale = request.getParameter("budgeta" + tipopol.get(i)[0]).replace(",", ".");
            String budgetstanziato = request.getParameter("budgets" + tipopol.get(i)[0]).replace(",", ".");
            String budgetprevisionale = request.getParameter("budgetp" + tipopol.get(i)[0]).replace(",", ".");
            if (a.equals("")) {
                a = "0";
            }
            if (b == null || b.equals("")) {
                b = "00";
            }
            a = a + "." + b;

            if (piu_meno == null) {
                Double a_pr = Double.parseDouble(a);
                a = String.valueOf(-a_pr);
            }

            budgetattuale = String.valueOf(Double.parseDouble(budgetattuale) + Double.parseDouble(a));
            budgetstanziato = String.valueOf(Double.parseDouble(budgetstanziato) + Double.parseDouble(a));
            budgetprevisionale = String.valueOf(Double.parseDouble(budgetprevisionale) + Double.parseDouble(a));

            String[] v = {tipopol.get(i)[0], budgetstanziato, budgetprevisionale, budgetattuale};
            val.add(v);
//            if (!a.equals("") && !a.equals("0")) {
//                String[] v = {tipopol.get(i)[0], a};
//                val.add(v);
//            }
        }
        if (Action.modifyBudgetPolitiche(id, val)) {
            Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Update Config.Bando", "B000", id);
            response.sendRedirect("modify_configuration_bando.jsp?esitomod=OK&idbando=" + id + "&tipo=" + tipo);
        } else {
            response.sendRedirect("modify_configuration_bando.jsp?esitomod=KO&idbando=" + id + "&tipo=" + tipo);
        }
    }

    protected void attivaBando(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idbando = request.getParameter("idbando");
        String tipo = request.getParameter("tipob");
        String bandoattivo = Utility.correggi(Utility.CaratteriSpecialifromDB(Action.getBandoAttivo(tipo)));
        if (bandoattivo.equals("")) {
            if (Action.AttivaBando(idbando)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Attiva Bando", "B000", idbando);
                Action.AttivazioniBando(idbando);
                response.sendRedirect("search_bandi.jsp?esitoact=OK");
            } else {
                response.sendRedirect("search_bandi.jsp?esitoact=KO");
            }
        } else {
            response.sendRedirect("search_bandi.jsp?esitoact=KO&nomeb=" + bandoattivo + "&tipob=" + tipo);
        }
    }

    protected void sospendiBando(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idbando = request.getParameter("idbando");

        if (Action.SospendiBando(idbando)) {
            Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Sospendi Bando", "B000", idbando);
            Action.SospensioniBando(idbando);
            response.sendRedirect("search_bandi.jsp?esitoact=OK");
        } else {
            response.sendRedirect("search_bandi.jsp?esitoact=KO");
        }

    }

    protected void chiudiBando(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idbando = request.getParameter("idbando");

        if (Action.ChiudiBando(idbando)) {
            Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Chiudi Bando", "B000", idbando);
            response.sendRedirect("search_bandi.jsp?esitoact=OK");
        } else {
            response.sendRedirect("search_bandi.jsp?esitoact=KO");
        }
    }

    protected void anomaliaRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idpolitica = request.getParameter("idpolitica");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String idrimborso = request.getParameter("idrimborso");
        String pol = request.getParameter("pol");
        if (pol.equals("B1") || pol.equals("C1") || pol.equals("D2") || pol.equals("D5")) {
            if (Action.numPoliticheRimborso_DOTE(idrimborso, idpolitica) == 0) {
                if (Action.anomaliaRimborsoCondition_DOTE(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso DT", "RDT0", idpolitica);
                    Action.insertScartiPolitiche_DOTE(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=KO");
                }
            } else {
                if (Action.anomaliaRimborso_DOTE(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso DT", "RDT0", idpolitica);
                    Action.insertScartiPolitiche_DOTE(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=OK&idrimborso=" + idrimborso + "&politica=" + pol);
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=KO&idrimborso=" + idrimborso + "&politica=" + pol);
                }
            }
        } else if (pol.equals("B3")) {
            if (Action.numPoliticheRimborso_DOTE_B3(idrimborso, idpolitica) == 0) {
                if (Action.anomaliaRimborsoCondition_DOTE_B3(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso DT", "RDT0", idpolitica);
                    Action.insertScartiPolitiche_DOTE_B3(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=KO");
                }
            } else {
                if (Action.anomaliaRimborso_DOTE_B3(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso DT", "RDT0", idpolitica);
                    Action.insertScartiPolitiche_DOTE_B3(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=OK&idrimborso=" + idrimborso + "&politica=" + pol);
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=KO&idrimborso=" + idrimborso + "&politica=" + pol);
                }
            }
        } else if (pol.equals("B2") || pol.equals("C2")) {
            if (Action.numPoliticheRimborso_Voucher_DT(idrimborso, idpolitica) == 0) {
                if (Action.anomaliaRimborsoCondition_Voucher_DT(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso Voucher DT", "VDT0", idpolitica);
                    Action.insertScartiPolitiche_Voucher_DT(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showVoucher.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showVoucher.jsp?esito1=KO");
                }
            } else {
                if (Action.anomaliaRimborso_Voucher_DT(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso Voucher DT", "VDT0", idpolitica);
                    Action.insertScartiPolitiche_Voucher_DT(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showVoucher.jsp?esitocl=OK&idrimborso=" + idrimborso + "&politica=" + pol);
                } else {
                    response.sendRedirect("reg_showVoucher.jsp?esitocl=KO&idrimborso=" + idrimborso + "&politica=" + pol);
                }
            }
        } else {
            if (Action.numPoliticheRimborso(idrimborso, idpolitica) == 0) {
                if (Action.anomaliaRimborsoCondition(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso", "R000", idpolitica);
                    Action.insertScartiPolitiche(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=KO");
                }
            } else {
                if (Action.anomaliaRimborso(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso", "R000", idpolitica);
                    Action.insertScartiPolitiche(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=OK&idrimborso=" + idrimborso);
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=KO&idrimborso=" + idrimborso);
                }
            }
        }
    }

    protected void scartaRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idpolitica = request.getParameter("idpolitica");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String idrimborso = request.getParameter("idrimborso");
        String pol = request.getParameter("pol");
        if (pol.equals("B1") || pol.equals("C1") || pol.equals("D2") || pol.equals("D5")) {
            if (Action.numPoliticheRimborso_DOTE(idrimborso, idpolitica) == 0) {
                if (Action.scartoRimborsoCondition_DOTE(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso DT", "RDT0", idpolitica);
                    Action.insertScartiPolitiche_DOTE(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=KO");
                }
                //response.sendRedirect("reg_gestione1B.jsp?esitocl=OK");
            } else {
                if (Action.scartaRimborso_DOTE(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso DT", "RDT0", idpolitica);
                    Action.insertScartiPolitiche_DOTE(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    //INCREMENTA BUDGET BANDO
                    DecreaseRefill d = Action.DT_DecreaseRefillById_Single(idrimborso);
                    Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=OK&idrimborso=" + idrimborso + "&politica=" + pol);
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=KO&idrimborso=" + idrimborso + "&politica=" + pol);
                }
            }
        } else if (pol.equals("B3")) {
            if (Action.numPoliticheRimborso_DOTE_B3(idrimborso, idpolitica) == 0) {
                if (Action.scartoRimborsoCondition_DOTE_B3(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso B3 DT", "RB30", idpolitica);
                    Action.insertScartiPolitiche_DOTE_B3(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=KO");
                }
            } else {
                if (Action.scartaRimborso_DOTE_B3(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso B3 DT", "RB30", idpolitica);
                    Action.insertScartiPolitiche_DOTE_B3(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    //INCREMENTA BUDGET BANDO
                    DecreaseRefill d = Action.DT_B3_DecreaseRefillById_Single(idrimborso);
                    Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=OK&idrimborso=" + idrimborso + "&politica=" + pol);
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=KO&idrimborso=" + idrimborso + "&politica=" + pol);
                }
            }
        } else if (pol.equals("B2") || pol.equals("C2")) {
            if (Action.numPoliticheRimborso_Voucher_DT(idrimborso, idpolitica) == 0) {
                if (Action.scartoRimborsoCondition_Voucher_DT(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso Voucher DT", "RB30", idpolitica);
                    Action.insertScartiPolitiche_Voucher_DT(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showVoucher.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showVoucher.jsp?esito1=KO");
                }
            } else {
                if (Action.scartaRimborso_Voucher_DT(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso Voucher DT", "RB30", idpolitica);
                    Action.insertScartiPolitiche_Voucher_DT(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    //INCREMENTA BUDGET BANDO
                    DecreaseRefill d = Action.DT_Voucher_DecreaseRefillById_Single(idrimborso);
                    Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                    response.sendRedirect("reg_showVoucher.jsp?esitocl=OK&idrimborso=" + idrimborso + "&politica=" + pol);
                } else {
                    response.sendRedirect("reg_showVoucher.jsp?esitocl=KO&idrimborso=" + idrimborso + "&politica=" + pol);
                }
            }
        } else {
            if (Action.numPoliticheRimborso(idrimborso, idpolitica) == 0) {
                if (Action.scartoRimborsoCondition(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso", "R000", idpolitica);
                    Action.insertScartiPolitiche(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esito1=KO");
                }
            } else {
                if (Action.scartaRimborso1(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso", "R000", idpolitica);
                    Action.insertScartiPolitiche(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    //INCREMENTA BUDGET BANDO
                    DecreaseRefill d = Action.GG_DecreaseRefillById_Single(idrimborso);
                    Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=OK&idrimborso=" + idrimborso);
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborso.jsp?esitocl=KO&idrimborso=" + idrimborso);
                }
            }
        }

    }

    protected void rigettaRimborsi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String tipo = request.getParameter("tipo");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to");

        String pagina = "reg_gestione1B";
        if (tipo.equals("A1B")) {
            pagina = "reg_anomalie1B";
        } else if (tipo.equals("1C")) {
            pagina = "reg_gestione1C";
        } else if (tipo.equals("AC1")) {
            pagina = "reg_anomalie1C";
        } else if (tipo.equals("M3")) {
            pagina = "reg_gestioneM3";
        } else if (tipo.equals("AM3")) {
            pagina = "reg_anomalieM3";
        } else if (tipo.equals("M5")) {
            pagina = "regE_gestioneM5";
        } else if (tipo.equals("AM5")) {
            pagina = "regE_anomalieM5";
        } else if (tipo.equals("C06")) {
            pagina = "regE_gestioneDT";
        } else if (tipo.equals("AC06")) {
            pagina = "regE_anomalieDT";
        } else if (tipo.equals("B1")) {
            pagina = "reg_gestioneB1DT";
        } else if (tipo.equals("AB1")) {
            pagina = "reg_anomalieB1DT";
        } else if (tipo.equals("C1")) {
            pagina = "reg_gestioneC1DT";
        } else if (tipo.equals("AC1")) {
            pagina = "reg_anomalieC1DT";
        } else if (tipo.equals("D2")) {
            pagina = "reg_gestioneD2DT";
        } else if (tipo.equals("AD2")) {
            pagina = "reg_anomalieD2DT";
        } else if (tipo.equals("D5")) {
            pagina = "reg_gestioneD5DT";
        } else if (tipo.equals("AD5")) {
            pagina = "reg_anomalieD5DT";
        } else if (tipo.equals("B3")) {
            pagina = "reg_gestioneB3DT";
        } else if (tipo.equals("AB3")) {
            pagina = "reg_anomalieB3DT";
        } else if (tipo.equals("B2")) {
            pagina = "reg_gestioneB2DT";
        } else if (tipo.equals("AB2")) {
            pagina = "reg_anomalieB2DT";
        } else if (tipo.equals("C2")) {
            pagina = "reg_gestioneC2DT";
        } else if (tipo.equals("AC2")) {
            pagina = "reg_anomalieC2DT";
        }

        pagina += ".jsp";

        if (tipo.equals("M5") || tipo.equals("AM5")) {
            if (Action.rigettaRimborsiM5(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "rigetta Rimborsi M5", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06") || tipo.equals("AC06")) {
            if (Action.rigettaRimborsiDT(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "rigetta Rimborsi" + tipo, "RGDT", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("B1") || tipo.equals("C1") || tipo.equals("D2") || tipo.equals("D5") || tipo.equals("AB1") || tipo.equals("AC1") || tipo.equals("AD2") || tipo.equals("AD5")) {
            if (Action.rigettaRimborsi_DOTE(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "rigetta Rimborsi DT", "RDT0", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("B3")) {
            if (Action.rigettaRimborsi_DOTE_B3(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "rigetta Rimborsi B3 DT", "RB30", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("B2") || tipo.equals("AB2") || tipo.equals("C2") || tipo.equals("AC2")) {
            if (Action.rigettaRimborsi_Voucher_DT(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "rigetta Rimborsi VOUCHER DT", "VDT0", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else {
            if (Action.rigettaRimborsi(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "rigetta Rimborsi", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void accettaRimborsi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        String tipo = request.getParameter("tipo");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to");

        String pagina = "reg_gestione1B";
        if (tipo.equals("1C")) {
            pagina = "reg_gestione1C";
        } else if (tipo.equals("M3")) {
            pagina = "reg_gestioneM3";
        } else if (tipo.equals("M5")) {
            pagina = "regE_gestioneM5";
        } else if (tipo.equals("AM5")) {
            pagina = "regE_anomalieM5";
        } else if (tipo.equals("C06")) {
            pagina = "regE_gestioneDT";
        } else if (tipo.equals("AC06")) {
            pagina = "regE_anomalieDT";
        } else if (tipo.equals("B1")) {
            pagina = "reg_gestioneB1DT";
        } else if (tipo.equals("C1")) {
            pagina = "reg_gestioneC1DT";
        } else if (tipo.equals("D2")) {
            pagina = "reg_gestioneD2DT";
        } else if (tipo.equals("D5")) {
            pagina = "reg_gestioneD5DT";
        } else if (tipo.equals("AB1")) {
            pagina = "reg_anomalieB1DT";
        } else if (tipo.equals("AC1")) {
            pagina = "reg_anomalieC1DT";
        } else if (tipo.equals("AD2")) {
            pagina = "reg_anomalieD2DT";
        } else if (tipo.equals("AD5")) {
            pagina = "reg_anomalieD5DT";
        } else if (tipo.equals("B3")) {
            pagina = "reg_gestioneB3DT";
        } else if (tipo.equals("AB3")) {
            pagina = "reg_anomalieB3DT";
        } else if (tipo.equals("B2")) {
            pagina = "reg_gestioneB2DT";
        } else if (tipo.equals("AB2")) {
            pagina = "reg_anomalieB2DT";
        } else if (tipo.equals("C2")) {
            pagina = "reg_gestioneC2DT";
        } else if (tipo.equals("AC2")) {
            pagina = "reg_anomalieC2DT";
        }

        pagina += ".jsp";
        if (tipo.equals("M5") || tipo.equals("AM5")) {
            if (Action.accettaRimborsiM5(idrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Rimborsi M5", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06") || tipo.equals("AC06")) {
            if (Action.accettaRimborsiDT(idrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Rimborsi " + tipo, "RGDT", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("B1") || tipo.equals("C1") || tipo.equals("D2") || tipo.equals("D5") || tipo.equals("AB1") || tipo.equals("AC1") || tipo.equals("AD2") || tipo.equals("AD5")) {
            if (Action.accettaRimborsi_DOTE(idrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Rimborsi DT", "RDT0", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("B3") || tipo.equals("AB3")) {
            if (Action.accettaRimborsi_DOTE_B3(idrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Rimborsi B3 DT", "RB30", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("B2") || tipo.equals("AB2") || tipo.equals("C2") || tipo.equals("AC2")) {
            if (Action.accettaRimborsi_Voucher_DT(idrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Rimborsi VOUCHER DT", "VDT0", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else {
            if (Action.accettaRimborsi(idrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Rimborsi", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    public void caricaProtocollo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pol = request.getParameter("pol");
        String idrimborso = request.getParameter("idrimborso");
        String protocollo = request.getParameter("protocollo");
        Part file = request.getPart("file");
        String path = "-";
        String random = UUID.randomUUID().toString().substring(0, 4);

        if (pol != null && pol.equals("M5")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "RIMBORSOM5_" + (protocollo.replace("/", "-").replace("\\", "-")).replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.uploadProtocolloM5(idrimborso, protocollo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Protocollo M5", "R000", idrimborso);
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=KO");
            }
        } else if (pol != null && pol.equals("C06")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "RIMBORSO" + pol + "_" + (protocollo.replace("/", "-").replace("\\", "-")).replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.uploadProtocolloDT(idrimborso, protocollo, path, pol)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Protocollo " + pol, "RGDT", idrimborso);
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=KO");
            }
        } else if (pol != null && pol.equals("DT")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "RIMBORSO_DT_" + (protocollo.trim().replace("/", "-").replace("\\", "-")).replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.uploadProtocollo_DOTE(idrimborso, protocollo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Protocollo", "RDT0", idrimborso);
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=KO");
            }
        } else if (pol != null && pol.equals("B3")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "RIMBORSO_B3_" + (protocollo.trim().replace("/", "-").replace("\\", "-")).replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.uploadProtocollo_DOTE_B3(idrimborso, protocollo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Protocollo", "RB30", idrimborso);
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=KO");
            }
        } else if (pol != null && pol.equals("VOUCHER")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "RIMBORSO_VOUCHER_" + (protocollo.trim().replace("/", "-").replace("\\", "-")).replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.uploadProtocollo_Voucher_DT(idrimborso, protocollo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Protocollo", "VDT0", idrimborso);
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=KO");
            }
        } else {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "RIMBORSO_" + (protocollo.trim().replace("/", "-").replace("\\", "-")).replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.uploadProtocollo(idrimborso, protocollo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Protocollo", "R000", idrimborso);
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_protocollo1B.jsp?esitoedit=KO");
            }
        }

    }

    public void caricaDecreto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pol = request.getParameter("pol");
        String idrimborso = request.getParameter("idrimborso");
        String repertorio = request.getParameter("repertorio");
        String datar = request.getParameter("datar");
        String decreto = request.getParameter("decreto");
        String datad = request.getParameter("datad");
        //double ctrlrimborso = Double.parseDouble(request.getParameter("ctrlrimborso"));

        Part file = request.getPart("file");
        String path = "-";
        String random = UUID.randomUUID().toString().substring(0, 4);
        try {
            datar = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(datar));
            datad = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(datad));
        } catch (ParseException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        if (pol != null && pol.equals("M5")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "DECRETOM5_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.uploadDecretoM5(idrimborso, repertorio, datar, decreto, datad, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Decreto M5", "R000", idrimborso);
                //DECREMENTO BUDGET BANDO
                ArrayList<DecreaseRefill> dr_list = Action.GG_C06_DecreaseRefillById(idrimborso);
                for (DecreaseRefill d : dr_list) {
                    Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                }
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=KO");
            }
        } else if (pol != null && pol.equals("C06")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "DECRETO" + pol + "_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.uploadDecretoDT(idrimborso, repertorio, datar, decreto, datad, path, pol)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Decreto " + pol, "RGDT", idrimborso);
                //DECREMENTO BUDGET BANDO
                ArrayList<DecreaseRefill> dr_list = Action.DT_C06_DecreaseRefillById(idrimborso);
                for (DecreaseRefill d : dr_list) {
                    Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                }
//                DecreaseRefill d = Action.DT_C06_DecreaseRefillById(idrimborso);
//                Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=KO");
            }
        } else if (pol != null && pol.equals("DT")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "DECRETO_DT_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.uploadDecreto_DOTE(idrimborso, repertorio, datar, decreto, datad, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Decreto DT", "RDT0", idrimborso);
                //DECREMENTO BUDGET BANDO
//                DecreaseRefill d = Action.DT_DecreaseRefillById(idrimborso);
//                Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                ArrayList<DecreaseRefill> dr_list = Action.DT_DecreaseRefillById(idrimborso);
                for (DecreaseRefill d : dr_list) {
                    Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                }
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=KO");
            }
        } else if (pol != null && pol.equals("B3")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "DECRETO_B3_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.uploadDecreto_DOTE_B3(idrimborso, repertorio, datar, decreto, datad, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Decreto B3 DT", "RB30", idrimborso);
                //DECREMENTO BUDGET BANDO
//                DecreaseRefill d = Action.DT_B3_DecreaseRefillById(idrimborso);
//                Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                ArrayList<DecreaseRefill> dr_list = Action.DT_B3_DecreaseRefillById(idrimborso);
                for (DecreaseRefill d : dr_list) {
                    Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                }
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=KO");
            }
        } else if (pol != null && pol.equals("VOUCHER")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "DECRETO_VOUCHER_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.uploadDecreto_Voucher_DT(idrimborso, repertorio, datar, decreto, datad, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Decreto VOUCHER DT", "VDT0", idrimborso);
                //DECREMENTO BUDGET BANDO
//                DecreaseRefill d = Action.DT_Voucher_DecreaseRefillById(idrimborso);
//                Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                ArrayList<DecreaseRefill> dr_list = Action.DT_Voucher_DecreaseRefillById(idrimborso);
                for (DecreaseRefill d : dr_list) {
                    Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                }
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=KO");
            }
        } else {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "DECRETO_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.uploadDecreto(idrimborso, repertorio, datar, decreto, datad, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Carica Decreto", "R000", idrimborso);
                //DECREMENTO BUDGET BANDO
//                DecreaseRefill d = Action.GG_DecreaseRefillById(idrimborso);
//                Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                ArrayList<DecreaseRefill> dr_list = Action.GG_DecreaseRefillById(idrimborso);
                for (DecreaseRefill d : dr_list) {
                    Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                }
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=OK");
            } else {
                response.sendRedirect("upload_decreto1B.jsp?esitoedit=KO");
            }
        }
    }

    protected void anomaliaRimborsoM5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idpf = request.getParameter("idpf");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String idrimborso = request.getParameter("idrimborso");
        String idpf_dt = request.getParameter("idpf_dt");
        String idrimborso_dt = request.getParameter("idrimborso_dt");

        if (idpf != null) {
            if (Action.numPFRimborso(idrimborso, idpf) == 0) {
                if (Action.anomaliaRimborsoConditionM5(idrimborso, idpf, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso M5", "R000", idpf);
                    Action.insertScartiPF(idrimborso, idpf, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborsoM5.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborsoM5.jsp?esito1=KO");
                }
                //response.sendRedirect("reg_gestione1B.jsp?esitocl=OK");
            } else {
                if (Action.anomaliaRimborsoM5(idpf, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso M5", "R000", idpf);
                    Action.insertScartiPF(idrimborso, idpf, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborsoM5.jsp?esitocl=OK&idrimborso=" + idrimborso);
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborsoM5.jsp?esitocl=KO&idrimborso=" + idrimborso);
                }
            }
        } else if (idpf_dt != null) {
            if (Action.numPFRimborsoDT(idrimborso_dt, idpf_dt) == 0) {
                if (Action.anomaliaRimborsoConditionDT(idrimborso_dt, idpf_dt, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso DT", "PFDT", idpf_dt);
                    Action.insertScartiPF_DT(idrimborso_dt, idpf_dt, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborsoDT.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborsoDT.jsp?esito1=KO");
                }
                //response.sendRedirect("reg_gestione1B.jsp?esitocl=OK");
            } else {
                if (Action.anomaliaRimborsoDT(idpf_dt, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Anomalia Rimborso DT", "PFDT", idpf_dt);
                    Action.insertScartiPF_DT(idrimborso_dt, idpf_dt, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborsoDT.jsp?esitocl=OK&idrimborso=" + idrimborso_dt);
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborsoDT.jsp?esitocl=KO&idrimborso=" + idrimborso_dt);
                }
            }
        }

    }

    protected void scartaRimborsoM5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idpf = request.getParameter("idpf");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String idrimborso = request.getParameter("idrimborso");
        String idpf_dt = request.getParameter("idpf_dt");
        String idrimborso_dt = request.getParameter("idrimborso_dt");

        if (idpf != null) {
            if (Action.numPFRimborso(idrimborso, idpf) == 0) {
                if (Action.scartoRimborsoConditionM5(idrimborso, idpf, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso M5", "R000", idpf);
                    Action.insertScartiPF(idrimborso, idpf, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborsoM5.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborsoM5.jsp?esito1=KO");
                }
                //response.sendRedirect("reg_gestione1B.jsp?esitocl=OK");
            } else {
                if (Action.scartaRimborsoM5(idpf, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso M5", "R000", idpf);
                    Action.insertScartiPF(idrimborso, idpf, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    //INCREMENTA BUDGET BANDO
                    DecreaseRefill d = Action.GG_C06_DecreaseRefillById_Single(idrimborso);
                    Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                    response.sendRedirect("reg_showTirocinantiRimborsoM5.jsp?esitocl=OK&idrimborso=" + idrimborso);
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborsoM5.jsp?esitocl=KO&idrimborso=" + idrimborso);
                }
            }
        } else if (idpf_dt != null) {
            if (Action.numPFRimborsoDT(idrimborso_dt, idpf_dt) == 0) {
                if (Action.scartoRimborsoConditionDT(idrimborso_dt, idpf_dt, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso DT", "PFDT", idpf_dt);
                    Action.insertScartiPF_DT(idrimborso_dt, idpf_dt, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("reg_showTirocinantiRimborsoDT.jsp?esito1=OK");
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborsoDT.jsp?esito1=KO");
                }
            } else {
                if (Action.scartaRimborsoDT(idpf_dt, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Scarto Rimborso DT", "PFDT", idpf_dt);
                    Action.insertScartiPF_DT(idrimborso_dt, idpf_dt, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    //INCREMENTA BUDGET BANDO
                    DecreaseRefill d = Action.DT_C06_DecreaseRefillById_Single(idrimborso);
                    Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                    response.sendRedirect("reg_showTirocinantiRimborsoDT.jsp?esitocl=OK&idrimborso=" + idrimborso_dt);
                } else {
                    response.sendRedirect("reg_showTirocinantiRimborsoDT.jsp?esitocl=KO&idrimborso=" + idrimborso_dt);
                }
            }
        }

    }

    protected void scartaRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idregistri = request.getParameter("idregistri");
        String ore = request.getParameter("ore");
        String motivo = request.getParameter("motivo");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String tipo = request.getParameter("tipo");
        String pagina = "regT_elaboraM5";
        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to"),
                nome = request.getParameter("nome"),
                cognome = request.getParameter("cognome"),
                cf = request.getParameter("cf");

        if (tipo.equals("M5")) {
            String apagina = request.getParameter("pagina");//pagina di anomalia
            pagina = (apagina != null) ? apagina + ".jsp" : pagina + ".jsp";
            if (Action.scartaRegistro(idregistri, motivo, ore, Action.getRegistroById(idregistri).getProgetto_formativo())) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "scarta Registro M5", "REG0", idregistri);
                //INCREMENTA BUDGET BANDO
                DecreaseRefill d = Action.GG_Registri_DecreaseRefillById(idregistri);
                Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06")) {
            pagina = request.getParameter("pagina") + ".jsp";
            if (Action.scartaRegistroDT(idregistri, motivo, ore, Action.getRegistroByIdDT(idregistri).getProgetto_formativo_dt())) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "scarta Registro DT", "REG0", idregistri);
                //INCREMENTA BUDGET BANDO
                DecreaseRefill d = Action.DT_Registri_DecreaseRefillById(idregistri);
                Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void rigettaRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idregistri = request.getParameter("idregistri");
        String motivo = request.getParameter("motivo");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String tipo = request.getParameter("tipo");
        String pagina = "regT_elaboraM5";
        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to"),
                nome = request.getParameter("nome"),
                cognome = request.getParameter("cognome"),
                cf = request.getParameter("cf");

        if (tipo.equals("M5")) {
            String apagina = request.getParameter("pagina");//pagina di anomalia
            pagina = (apagina != null) ? apagina + ".jsp" : pagina + ".jsp";
            if (Action.rigettaRegistro(idregistri, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "rigetta Registro M5", "REG0", idregistri);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06")) {
            pagina = request.getParameter("pagina") + ".jsp";;
            if (Action.rigettaRegistroDT(idregistri, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "rigetta Registro " + tipo, "RGDT", idregistri);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void accettaRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idregistri = request.getParameter("idregistri");
        String tipo = request.getParameter("tipo");
        String pagina = "regT_elaboraM5";
        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to"),
                nome = request.getParameter("nome"),
                cognome = request.getParameter("cognome"),
                cf = request.getParameter("cf");

        if (tipo.equals("M5") || tipo.equals("AM5")) {
            if (Action.accettaRegistro(idregistri)) {
                String apagina = request.getParameter("pagina");//pagina di anomalia
                pagina = (apagina != null) ? apagina + ".jsp" : pagina + ".jsp";
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Registro M5", "REG0", idregistri);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06") || tipo.equals("AC06")) {
            pagina = request.getParameter("pagina") + ".jsp";
            if (Action.accettaRegistroDT(idregistri)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Registro " + tipo, "RGDT", idregistri);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void liquidaRegistri(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        String prestazione_sel = request.getParameter("prestazione_sel");
        String ente = request.getParameter("ente");
        String page = request.getParameter("page");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        if (ente == null) {
            ente = "";
        }
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

        if (page.equals("regT_liquidazioneM5.jsp")) {
            if (Action.liquidaRegistri(data)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Liquida Registri ", "REG0", data);
                Action.PrestazionePredefinita(prestazione_sel);
                String path = "";
                String filename = "";
                try {
                    path = Action.getPath("inps");
                    filename = "RIMBORSITIROCINI_INPS_" + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + ".xml";
                    File dir = new File(path);
                    dir.mkdirs();
                    path = path + filename;
                    createXML(Action.getInfoXML(data), path);
                } catch (FileNotFoundException | DocumentException | ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
                if (Action.savexmlRegistri(filename, data)) {
                    response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=OK&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&ente=" + ente + "&from=" + from + "&to=" + to + "&path=" + path);
                } else {
                    response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO");
                }
            } else {
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO");
            }
        } else if (page.equals("regT_liquidazioneDT.jsp")) {
            if (Action.liquidaRegistriDT(data)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Liquida Registri DT", "RGDT", data);
                Action.PrestazionePredefinita(prestazione_sel);
                String path = "";
                String filename = "";
                try {
                    path = Action.getPath("inps");
                    File dir = new File(path);
                    dir.mkdirs();
                    filename = "RIMBORSITIROCINI_INPS_DT_" + new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date()) + ".xml";
                    path = path + filename;
                    createXML(Action.getInfoXML_DT(data), path);
                } catch (FileNotFoundException | DocumentException | ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
                if (Action.savexmlRegistri_DT(filename, data)) {
                    response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=OK&nome=" + nome + "&cognome=" + cognome + "&cf=" + cf + "&ente=" + ente + "&from=" + from + "&to=" + to + "&path=" + path);
                } else {
                    response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO");
                }
            } else {
                response.sendRedirect("redirect.jsp?page=" + page + "&esitoins=KO");
            }
        }

    }

    private void createXML(ArrayList<FileINPS> pol, String path) throws FileNotFoundException, DocumentException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> prov = Action.getProvinciaByCode();
        Map<String, String> comune = Action.getComuneByCode();
        Map<String, String> stato = Action.getStatoByCode();
        Map<String, String> elementiXML = Action.getValueXmlINPS();
        int t = 0;
        try {
            //Elemento radice 
            Element root = new Element("domande");
            //root.setAttribute("xmlns","Schemas");
            //Documento 
            Document document = new Document(root);

            for (int i = 0; i < pol.size(); i++) {

                Element domanda = new Element("domanda");
                Element descr1 = new Element("data_domanda_prestazione");
                descr1.setText(sdf.format(sdf2.parse(pol.get(i).getData_up())));
                Element descr2 = new Element("codice_ente_autonomo");
                descr2.setText(elementiXML.get("codice_ente_autonomo"));
                Element descr3 = new Element("descrizione_ente_autonomo");
                descr3.setText(elementiXML.get("descrizione_ente_autonomo"));
                Element descr4 = new Element("matricola_inps_azienda_benficiario");
                descr4.setText("");
                Element descr5 = new Element("codice_fiscale_azienda_beneficiario");
                descr5.setText(pol.get(i).getCf().toUpperCase());//cf ente
                Element descr6 = new Element("ragione_sociale_azienda_del_beneficiario");
                descr6.setText("");
                Element descr7 = new Element("numero_delibera_ente_autonomo");
                descr7.setText("");
                Element descr8 = new Element("anno_delibera_ente_autonomo");
                descr8.setText("");
                Element descr9 = new Element("data_esito_delibera_ente_autonomo");
                descr9.setText("");
                Element descr10 = new Element("data_inizio_prestazione");
                descr10.setText(sdf.format(sdf2.parse(pol.get(i).getDatainizio())));
                Element descr11 = new Element("data_fine_prestazione");
                descr11.setText(sdf.format(sdf2.parse(pol.get(i).getDatafine())));
                Element descr12 = new Element("durata_prestazione");
                descr12.setText("");
                Element descr13 = new Element("tipo_prestazione");
                descr13.setText(elementiXML.get("tipo_prestazione"));  //D OR T ...
                Element descr14 = new Element("cognome");
                descr14.setText(pol.get(i).getCognome().toUpperCase());
                Element descr15 = new Element("nome");
                descr15.setText(pol.get(i).getNome().toUpperCase());
                Element descr16 = new Element("data_di_nascita");
                descr16.setText(sdf.format(sdf2.parse(pol.get(i).getNascita_data())));
                Element descr17 = new Element("codice_fiscale");
                descr17.setText(pol.get(i).getCodice_fiscale().toUpperCase());
                Element descr18 = new Element("comune_nascita");
                descr18.setText(comune.get(pol.get(i).getNascita_codice_catastale()).toUpperCase());
                Element descr19 = new Element("provincia_nascita");
                if (prov.get(pol.get(i).getNascita_codice_catastale()) == null) {
                    descr19.setText("EE");
                } else {
                    descr19.setText(prov.get(pol.get(i).getNascita_codice_catastale()).toUpperCase());
                }
                Element descr20 = new Element("stato_nascita");
                descr20.setText(stato.get(pol.get(i).getCittadinanza()).toUpperCase());
                Element descr21 = new Element("indirizzo");
                descr21.setText(pol.get(i).getResidenza_indirizzo().toUpperCase());
                Element descr22 = new Element("cap");
                //String a = pol.get(i).getResidenza_cap() + "";
                descr22.setText(pol.get(i).getResidenza_cap());
                Element descr23 = new Element("comune");
                descr23.setText(comune.get(pol.get(i).getResidenza_codice_catastale()).toUpperCase());
                Element descr24 = new Element("provincia");
                descr24.setText(prov.get(pol.get(i).getResidenza_codice_catastale()).toUpperCase());
                Element descr25 = new Element("numero_di_telefono_del_beneficiario");
                descr25.setText("");
                Element descr26 = new Element("indirizzo_email_del_beneficiario");
                descr26.setText("");
                Element descr27 = new Element("iban_nazione_lavoratore");
                descr27.setText("");
                Element descr28 = new Element("iban_controllo_lavoratore");
                descr28.setText("");
                Element descr29 = new Element("cin_lavoratore");
                descr29.setText("");
                Element descr30 = new Element("abi_lavoratore");
                descr30.setText("");
                Element descr31 = new Element("cab_lavoratore");
                descr31.setText("");
                Element descr32 = new Element("conto_corrente_lavoratore");
                descr32.setText("");
                Element descr33 = new Element("importo_lordo_giornaliero_prestazione");
                descr33.setText("");
                double as = Double.parseDouble(pol.get(i).getTot_erogato());
                as = roundDouble(as, 2);
                String b = as + "";
                if (b.endsWith(".0")) {
                    b = b.replace(".0", ".00");
                }
                Element descr34 = new Element("importo_lordo_complessivo_da_erogare_al_beneficiario");  //registri tot.erogato
                descr34.setText(b.replace(".", ","));
                domanda.addContent(descr1);
                domanda.addContent(descr2);
                domanda.addContent(descr3);
                domanda.addContent(descr4);
                domanda.addContent(descr5);
                domanda.addContent(descr6);
                domanda.addContent(descr7);
                domanda.addContent(descr8);
                domanda.addContent(descr9);
                domanda.addContent(descr10);
                domanda.addContent(descr11);
                domanda.addContent(descr12);
                domanda.addContent(descr13);
                domanda.addContent(descr14);
                domanda.addContent(descr15);
                domanda.addContent(descr16);
                domanda.addContent(descr17);
                domanda.addContent(descr18);
                domanda.addContent(descr19);
                domanda.addContent(descr20);
                domanda.addContent(descr21);
                domanda.addContent(descr22);
                domanda.addContent(descr23);
                domanda.addContent(descr24);
                domanda.addContent(descr25);
                domanda.addContent(descr26);
                domanda.addContent(descr27);
                domanda.addContent(descr28);
                domanda.addContent(descr29);
                domanda.addContent(descr30);
                domanda.addContent(descr31);
                domanda.addContent(descr32);
                domanda.addContent(descr33);
                domanda.addContent(descr34);
                root.addContent(domanda);
                t = i;
            }
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());
            outputter.output(document, new FileOutputStream(path));
        } catch (Exception ex) {
        	 LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private static double roundDouble(double d, int scale) {
        d = new BigDecimal(d).setScale(scale, RoundingMode.HALF_UP).doubleValue();
        return d;
    }

    protected void accettaRegINPS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idregistri = request.getParameter("idregistri");
        String tipo = request.getParameter("tipo");
        String pagina = "regI_elaboraM5.jsp";

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to"),
                nome = request.getParameter("nome"),
                cognome = request.getParameter("cognome"),
                cf = request.getParameter("cf");

        if (tipo.equals("M5")) {
            if (Action.accettaRegINPS(idregistri)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Registro INPS M5", "REG0", idregistri);
                //DECREMENTO BUDGET BANDO
                DecreaseRefill d = Action.GG_Registri_DecreaseRefillById(idregistri);
                Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06")) {
            pagina = "regI_elaboraDT.jsp";
            if (Action.accettaRegINPS_DT(idregistri)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Registro INPS DT", "RGDT", idregistri);
                //DECREMENTO BUDGET BANDO
                DecreaseRefill d = Action.DT_Registri_DecreaseRefillById(idregistri);
                Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }

    }

    protected void rifiutaRegINPS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idregistri = request.getParameter("idregistri");
        String tipo = request.getParameter("tipo");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to"),
                nome = request.getParameter("nome"),
                cognome = request.getParameter("cognome"),
                cf = request.getParameter("cf");

        String pagina = "regI_elaboraM5.jsp";
        if (tipo.equals("M5")) {
            if (Action.rifiutaRegINPS(idregistri)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rifiuta Registro INPS M5", "REG0", idregistri);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06")) {
            pagina = "regI_elaboraDT.jsp";
            if (Action.rifiutaRegINPS_DT(idregistri)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rifiuta Registro INPS DT", "RGDT", idregistri);
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }

    }

    protected void modificaResidenzaLavoratore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idlavoratore = request.getParameter("idlavoratore");
        //String prov = request.getParameter("prov");
        String comune = request.getParameter("comune");
        String indirizzo = request.getParameter("indirizzo");
        String cap = Action.getCapByComune().get(comune);
        if (cap == null) {
            cap = "-";
        }
        if (Action.modificaResidenzaLavoratore(idlavoratore, indirizzo, comune, cap)) { //prov, 
            Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Modifica Residenza Lavoratore", "L000", idlavoratore);
            response.sendRedirect("modResidenza_XML.jsp?esitoact=OK&id=" + idlavoratore);
        } else {
            response.sendRedirect("modResidenza_XML.jsp?esitoact=KO&id=" + idlavoratore);
        }

    }

    protected void scartaINPS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idregistri = request.getParameter("idregistri");
        // = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String tipo = request.getParameter("tipo");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to"),
                nome = request.getParameter("nome"),
                cognome = request.getParameter("cognome"),
                cf = request.getParameter("cf");

        String pagina = "regI_elaboraM5.jsp";
        if (tipo.equals("M5")) {
            if (Action.RigettaINPS(idregistri, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rigetta Registro INPS M5", "REG0", idregistri);
                //INCREMENTA BUDGET BANDO
                DecreaseRefill d = Action.GG_Registri_DecreaseRefillById(idregistri);
                Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06")) {
            pagina = "regI_elaboraDT.jsp";
            if (Action.RigettaINPS_DT(idregistri, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rigetta Registro INSP DT" + tipo, "RGDT", idregistri);
                //INCREMENTA BUDGET BANDO
                DecreaseRefill d = Action.DT_Registri_DecreaseRefillById(idregistri);
                Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void getBandi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String tipo = request.getParameter("tipo");
        ArrayList<Bando> bandi = Action.getbandiattivi(tipo);

        ObjectMapper mapper = new ObjectMapper();
        String sl = mapper.writeValueAsString(bandi);//json
        response.getWriter().write(sl);
    }

    protected void transferBandi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String iddonatore = request.getParameter("donatore");
        String idricevente = request.getParameter("ricevente");
        String budget = request.getParameter("budget") + "." + request.getParameter("centesimi");

        Double[] budget_sum = Action.getSumPolitiche(iddonatore);
        if (budget_sum[2] < Double.parseDouble(budget)) {
            if (Action.transferMoney(iddonatore, idricevente, budget)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Trasferimento budget", "B000", iddonatore + "-" + idricevente);
                response.sendRedirect("transfer_bandi.jsp?esitoact=OK");
            } else {
                response.sendRedirect("transfer_bandi.jsp?esitoact=KO");
            }
        } else {
            response.sendRedirect("transfer_bandi.jsp?esitoact=Eccessivo");
        }

    }

    protected void statoRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String codice = request.getParameter("cod");
        String idrimborso = request.getParameter("idrimborso");

        String stato = Action.getStatoRimborso(idrimborso, codice);
        response.getWriter().write(stato != null ? stato : "null");
    }

    protected void getPrg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        PrgFormativo prg = Action.getPrgFormativoById(id);
        Convenzione conv = Action.findConvenzioneById(String.valueOf(prg.getConvenzione()));

        prg.setPath_convenzione("OperazioniGeneral?type=5&path=" + StringUtils.replace(conv.getFile(), "\\", "/"));
        prg.setFile("OperazioniGeneral?type=5&path=" + StringUtils.replace(prg.getFile(), "\\", "/"));
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(prg);
        response.getWriter().write(json);
    }

    protected void getPrgDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        PrgFormativoDt prg = Action.getPrgFormativoDtById(id);
        Convenzione conv = Action.findConvenzioneById(String.valueOf(prg.getConvenzione()));

        prg.setPath_convenzione("OperazioniGeneral?type=5&path=" + StringUtils.replace(conv.getFile(), "\\", "/"));
        prg.setFile("OperazioniGeneral?type=5&path=" + StringUtils.replace(prg.getFile(), "\\", "/"));
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(prg);
        response.getWriter().write(json);
    }

    protected void accettaRegINPS_1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        String tipo = request.getParameter("tipo");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to"),
                nome = request.getParameter("nome"),
                cognome = request.getParameter("cognome"),
                cf = request.getParameter("cf");

        String pagina = "regI_elaboraM5.jsp";
        if (tipo.equals("M5")) {
            if (Action.convalidateRegistri(data)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Registri INPS M5", "REG0", data);
                //DECREMENTO BUDGET BANDO
                String[] string = data.split(",");
                for (String s : string) {
                    DecreaseRefill d = Action.GG_Registri_DecreaseRefillById(s);
                    Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                }
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06")) {//convalidateRegistriDt
            pagina = "regI_elaboraDT.jsp";
            if (Action.convalidateRegistriDt(data)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Accetta Registri INPS ST", "RGDT", data);
                //DECREMENTO BUDGET BANDO
                String[] string = data.split(",");
                for (String s : string) {
                    DecreaseRefill d = Action.DT_Registri_DecreaseRefillById(s);
                    Action.decreseBudgetAttualeBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                }
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + "&esitocl=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void getEntepromotore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String cf = request.getParameter("cf");

        Ente ente = Action.getEnteByCf(cf);
        response.getWriter().write(ente.getRagioneSociale() != null ? ente.getRagioneSociale() : "null");
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
                    if (tipo == 1) {
                        if (user == null && stato != 3) {
                            redirect(request, response, "Login?type=2");
                        } else if (type.equals("1")) {
                            caricaBando(request, response);
                        } else if (type.equals("2")) {
                            modificaBando(request, response);
                        } else if (type.equals("3")) {
                            configuraBando(request, response);
                        } else if (type.equals("4")) {
                            mod_configuraBando(request, response);
                        } else if (type.equals("5")) {
                            attivaBando(request, response);
                        } else if (type.equals("6")) {
                            chiudiBando(request, response);
                        } else if (type.equals("7")) {
                            anomaliaRimborso(request, response);
                        } else if (type.equals("8")) {
                            scartaRimborso(request, response);
                        } else if (type.equals("9")) {
                            accettaRimborsi(request, response);
                        } else if (type.equals("10")) {
                            rigettaRimborsi(request, response);
                        } else if (type.equals("11")) {
                            caricaProtocollo(request, response);
                        } else if (type.equals("12")) {
                            caricaDecreto(request, response);
                        } else if (type.equals("13")) {
                            sospendiBando(request, response);
                        } else if (type.equals("14")) {
                            anomaliaRimborsoM5(request, response);
                        } else if (type.equals("15")) {
                            scartaRimborsoM5(request, response);
                        } else if (type.equals("16")) {
                            accettaRegistro(request, response);
                        } else if (type.equals("17")) {
                            rigettaRegistro(request, response);
                        } else if (type.equals("18")) {
                            scartaRegistro(request, response);
                        } else if (type.equals("19")) {
                            liquidaRegistri(request, response);
                        } else if (type.equals("20")) {
                            accettaRegINPS(request, response);
                        } else if (type.equals("21")) {
                            rifiutaRegINPS(request, response);
                        } else if (type.equals("22")) {
                            modificaResidenzaLavoratore(request, response);
                        } else if (type.equals("23")) {
                            scartaINPS(request, response);
                        } else if (type.equals("24")) {
                            getBandi(request, response);
                        } else if (type.equals("25")) {
                            transferBandi(request, response);
                        } else if (type.equals("26")) {
                            statoRimborso(request, response);
                        } else if (type.equals("27")) {
                            getPrg(request, response);
                        } else if (type.equals("28")) {
                            getPrgDt(request, response);
                        } else if (type.equals("29")) {
                            accettaRegINPS_1(request, response);
                        } else if (type.equals("30")) {
                            getEntepromotore(request, response);
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
