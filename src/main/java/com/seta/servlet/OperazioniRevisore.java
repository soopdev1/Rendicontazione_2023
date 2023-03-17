/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seta.activity.Action;
import com.seta.entity.Convenzione;
import com.seta.entity.DecreaseRefill;
import com.seta.entity.Ente;
import com.seta.entity.PrgFormativo;
import com.seta.entity.PrgFormativoDt;
import com.seta.util.Utility;
import static com.seta.util.Utility.redirect;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.lang3.StringUtils;
import java.util.logging.*;
/**
 *
 * @author dolivo
 */
public class OperazioniRevisore extends HttpServlet {
	
	private static final long serialVersionUID = 6248032368534393140L;
	private static final Logger LOGGER = Logger.getLogger( OperazioniRevisore.class.getName() );
	
    public void liquidaRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        String totale = request.getParameter("totale");
        String centesimi = request.getParameter("centesimi");
        String descrizione = Utility.CaratteriSpeciali(request.getParameter("descrizione"));
        String pagina = request.getParameter("pagina");
        String ctrlrimborso = request.getParameter("ctrlrimborso");
        Part file = request.getPart("file");
        String idtipo = request.getParameter("idtipo");
        String path = "-";

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to");

        if (centesimi == null || centesimi.equals("")) {
            centesimi = "00";
        }
        totale = totale + "." + centesimi;
        String random = UUID.randomUUID().toString().substring(0, 4);
        if (pagina.equals("rev_entiM5")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLISTM5_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.Rev_liquidaRimborsoM5(idrimborso, totale, descrizione, path, ctrlrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Liquida Rimborso M5", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);

            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (idtipo != null && idtipo.equals("C06")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLISTDT_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.Rev_liquidaRimborsoDT(idrimborso, totale, descrizione, path, ctrlrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Liquida Rimborso DT", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.Rev_liquidaRimborso(idrimborso, totale, descrizione, path, ctrlrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Liquida Rimborso", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void rigettaRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        //String motivo = Utility.CaratteriSpeciali(Utility.passaLinkDecodifica(request.getParameter("motivo")));
        String motivo = request.getParameter("motivo");
        String tipo = request.getParameter("pagina");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to");

        if (tipo.equals("rev_entiM5")) {
            if (Action.Rev_rigettaRimborsoM5(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Rigetta Rimborso M5", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06")) {
            String pag = "rev_entiDT";
            if (Action.Rev_rigettaRimborsoDT(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Rigetta Rimborso DT", "RGDT", idrimborso);
//                response.sendRedirect(pag + ".jsp?esitoact=OK");
                response.sendRedirect("redirect.jsp?page=" + pag + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
//                response.sendRedirect(pag + ".jsp?esitoact=KO");
                response.sendRedirect("redirect.jsp?page=" + pag + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else {
            if (Action.Rev_rigettaRimborso(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Rigetta Rimborso", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }

    }

    protected void scartaTuttoRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborsoscarto");
        //String motivo = Utility.CaratteriSpeciali(Utility.passaLinkDecodifica(request.getParameter("motivo")));
        String motivo = request.getParameter("motivoscarto");
        String tipo = request.getParameter("pagina");
        Part file = request.getPart("filescarto");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to");

        String path = "-";
        String random = UUID.randomUUID().toString().substring(0, 4);

        if (tipo.equals("rev_entiM5")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST_M5_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.Rev_scartaTuttoRimborsoM5(idrimborso, motivo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarta Intero Rimborso M5", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("rev_entiDT")) {
            //String pag = "rev_entiDT";
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST_DT_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.Rev_scartaTuttoRimborsoDT(idrimborso, motivo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarta Intero Rimborso DT", "RGDT", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.Rev_scartaTuttoRimborso(idrimborso, motivo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarta Intero Rimborso", "R000", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }

    }

    protected void anomaliaRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idpolitica = request.getParameter("idpolitica");
        String motivo = request.getParameter("motivo");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String idrimborso = request.getParameter("idrimborso");
        String protocollo = request.getParameter("protocollo");
        String pol = request.getParameter("politica");
        if (pol.equals("B1") || pol.equals("C1") || pol.equals("D2") || pol.equals("D5")) {
            if (Action.numPoliticheRimborso_DOTE(idrimborso, idpolitica) == 0) {
                if (Action.anomaliaRimborsoCondition_DOTE(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso DT", "RDT0", idpolitica);
                    Action.insertScartiPolitiche_DOTE(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            } else {
                if (Action.anomaliaRimborso_DOTE(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso DT", "RDT0", idpolitica);
                    Action.insertScartiPolitiche_DOTE(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            }
        } else if (pol.equals("B3")) {
            if (Action.numPoliticheRimborso_DOTE_B3(idrimborso, idpolitica) == 0) {
                if (Action.anomaliaRimborsoCondition_DOTE_B3(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso B3 DT", "RB30", idpolitica);
                    Action.insertScartiPolitiche_DOTE_B3(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            } else {
                if (Action.anomaliaRimborso_DOTE_B3(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso B3 DT", "RB30", idpolitica);
                    Action.insertScartiPolitiche_DOTE_B3(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            }
        } else if (pol.equals("C2") || pol.equals("B2")) {
            if (Action.numPoliticheRimborso_Voucher_DT(idrimborso, idpolitica) == 0) {
                if (Action.anomaliaRimborsoCondition_Voucher_DT(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso VOUCHER DT", "VDT0", idpolitica);
                    Action.insertScartiPolitiche_Voucher_DT(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showVoucher.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showVoucher.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            } else {
                if (Action.anomaliaRimborso_Voucher_DT(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso VOUCHER DT", "VDT0", idpolitica);
                    Action.insertScartiPolitiche_Voucher_DT(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showVoucher.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showVoucher.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            }
        } else {
            if (Action.numPoliticheRimborso(idrimborso, idpolitica) == 0) {
                if (Action.anomaliaRimborsoCondition(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso", "R000", idpolitica);
                    Action.insertScartiPolitiche(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            } else {
                if (Action.anomaliaRimborso(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso", "R000", idpolitica);
                    Action.insertScartiPolitiche(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            }
        }
    }

    protected void scartaRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idpolitica = request.getParameter("idpolitica");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String idrimborso = request.getParameter("idrimborso");
        String protocollo = request.getParameter("protocollo");
        String pol = request.getParameter("politica");
        if (pol.equals("B1") || pol.equals("C1") || pol.equals("D2") || pol.equals("D5")) {
            if (Action.numPoliticheRimborso_DOTE(idrimborso, idpolitica) == 0) {
                if (Action.scartoRimborsoCondition_DOTE(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso DT", "RDT0", idpolitica);
                    Action.insertScartiPolitiche_DOTE(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            } else {
                if (Action.scartaRimborso_DOTE(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso DT", "RDT0", idpolitica);
                    Action.insertScartiPolitiche_DOTE(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    //INCREMENTA BUDGET BANDO
                    DecreaseRefill d = Action.DT_DecreaseRefillById_Single(idrimborso);
                    Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            }
        } else if (pol.equals("B3")) {
            if (Action.numPoliticheRimborso_DOTE_B3(idrimborso, idpolitica) == 0) {
                if (Action.scartoRimborsoCondition_DOTE_B3(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso B3 DT", "RB30", idpolitica);
                    Action.insertScartiPolitiche_DOTE_B3(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            } else {
                if (Action.scartaRimborso_DOTE_B3(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso B3 DT", "RB30", idpolitica);
                    Action.insertScartiPolitiche_DOTE_B3(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    //INCREMENTA BUDGET BANDO
                    DecreaseRefill d = Action.DT_B3_DecreaseRefillById_Single(idrimborso);
                    Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            }
        } else if (pol.equals("C2") || pol.equals("B2")) {
            if (Action.numPoliticheRimborso_Voucher_DT(idrimborso, idpolitica) == 0) {
                if (Action.scartoRimborsoCondition_Voucher_DT(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso VOUCHER DT", "RB30", idpolitica);
                    Action.insertScartiPolitiche_Voucher_DT(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showVoucher.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showVoucher.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            } else {
                if (Action.scartaRimborso_Voucher_DT(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso VOUCHER DT", "RB30", idpolitica);
                    Action.insertScartiPolitiche_Voucher_DT(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    //INCREMENTA BUDGET BANDO
                    DecreaseRefill d = Action.DT_Voucher_DecreaseRefillById_Single(idrimborso);
                    Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                    response.sendRedirect("rev_showVoucher.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showVoucher.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            }
        } else {
            if (Action.numPoliticheRimborso(idrimborso, idpolitica) == 0) {
                if (Action.scartoRimborsoCondition(idrimborso, idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso", "R000", idpolitica);
                    Action.insertScartiPolitiche(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            } else {
                if (Action.scartaRimborso1(idpolitica, motivo)) {
                    Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso", "R000", idpolitica);
                    Action.insertScartiPolitiche(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                    //INCREMENTA BUDGET BANDO
                    DecreaseRefill d = Action.GG_DecreaseRefillById_Single(idrimborso);
                    Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                } else {
                    response.sendRedirect("rev_showPoliticheRimborso.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
                }
            }
        }
    }

    protected void anomaliaRimborsoM5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idpolitica = request.getParameter("idpolitica");
        String motivo = request.getParameter("motivo");
        String idrimborso = request.getParameter("idrimborso");
        String protocollo = request.getParameter("protocollo");
        String pol = request.getParameter("politica");
        if (Action.numPFRimborso(idrimborso, idpolitica) == 0) {
            if (Action.anomaliaRimborsoConditionM5(idrimborso, idpolitica, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso M5", "R000", idpolitica);
                Action.insertScartiPF(idrimborso, idpolitica, motivo);
                response.sendRedirect("rev_showPFRimborso.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            } else {
                response.sendRedirect("rev_showPFRimborso.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            }
            //response.sendRedirect("reg_gestione1B.jsp?esitoact=OK");
        } else {
            if (Action.anomaliaRimborsoM5(idpolitica, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso M5", "R000", idpolitica);
                Action.insertScartiPF(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                response.sendRedirect("rev_showPFRimborso.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            } else {
                response.sendRedirect("rev_showPFRimborso.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            }
        }

    }

    protected void scartaRimborsoM5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idpolitica = request.getParameter("idpolitica");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String idrimborso = request.getParameter("idrimborso");
        String protocollo = request.getParameter("protocollo");
        String pol = request.getParameter("politica");
        if (Action.numPFRimborso(idrimborso, idpolitica) == 0) {
            if (Action.scartoRimborsoConditionM5(idrimborso, idpolitica, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso M5", "R000", idpolitica);
                Action.insertScartiPF(idrimborso, idpolitica, motivo);
                response.sendRedirect("rev_showPFRimborso.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            } else {
                response.sendRedirect("rev_showPFRimborso.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            }
            //response.sendRedirect("reg_gestione1B.jsp?esitoact=OK");
        } else {
            if (Action.scartaRimborsoM5(idpolitica, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso M5", "R000", idpolitica);
                Action.insertScartiPF(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                //INCREMENTA BUDGET BANDO
                DecreaseRefill d = Action.GG_C06_DecreaseRefillById_Single(idrimborso);
                Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("rev_showPFRimborso.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            } else {
                response.sendRedirect("rev_showPFRimborso.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            }
        }

    }

    protected void scartaRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idregistri = request.getParameter("idregistri");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String ore = request.getParameter("ore");
        String tipo = request.getParameter("tipo");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to"),
                nome = request.getParameter("nome"),
                cognome = request.getParameter("cognome"),
                cf = request.getParameter("cf");

        String pagina = "rev_tirocinantiM5";
        if (tipo.equals("M5")) {
            if (Action.Rev_scartaRegistro(idregistri, motivo, ore, Action.getRegistroById(idregistri).getProgetto_formativo())) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.scarta Registro M5", "REG0", idregistri);
                //INCREMENTA BUDGET BANDO
                DecreaseRefill d = Action.GG_Registri_DecreaseRefillById(idregistri);
                Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06")) {
            pagina = "rev_tirocinantiDT";
            if (Action.Rev_scartaRegistroDT(idregistri, motivo, ore, Action.getRegistroByIdDT(idregistri).getProgetto_formativo_dt())) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.scarta Registro " + tipo, "RGDT", idregistri);
                //INCREMENTA BUDGET BANDO
                DecreaseRefill d = Action.DT_Registri_DecreaseRefillById(idregistri);
                Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }

    }

    protected void rigettaRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idregistri = request.getParameter("idregistri");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String tipo = request.getParameter("tipo");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to"),
                nome = request.getParameter("nome"),
                cognome = request.getParameter("cognome"),
                cf = request.getParameter("cf");

        String pagina = "rev_tirocinantiM5";
        if (tipo.equals("M5")) {
            if (Action.Rev_rigettaRegistro(idregistri, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.rigetta Registro M5", "REG0", idregistri);
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("C06")) {
            pagina = "rev_tirocinantiDT";
            if (Action.Rev_rigettaRegistroDT(idregistri, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.rigetta Registro " + tipo, "RGDT", idregistri);
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void accettaRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idregistri = request.getParameter("idregistri");
        String pagina = request.getParameter("pagina");
        String totale = request.getParameter("totale");
        String centesimi = request.getParameter("centesimi");
        String descrizione = Utility.CaratteriSpeciali(request.getParameter("descrizione"));
        String ore = request.getParameter("ore");
        String idore = request.getParameter("idore");
        Part file = request.getPart("file");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to"),
                nome = request.getParameter("nome"),
                cognome = request.getParameter("cognome"),
                cf = request.getParameter("cf");

        String path = "-";
        if (centesimi == null || centesimi.equals("")) {
            centesimi = "00";
        }
        if (ore == null || ore.equals("")) {
            ore = idore;
        }
        totale = totale + "." + centesimi;
        String random = UUID.randomUUID().toString().substring(0, 4);

        if (pagina.equals("rev_tirocinantiM5")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLISTM5_" + idregistri.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.Rev_accettaRegistro(idregistri, totale, descrizione, path, ore, Action.getRegistroById(idregistri).getProgetto_formativo())) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Accetta Registro M5", "REG0", idregistri);
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (pagina.equals("rev_tirocinantiDT")) {
            String tipo = request.getParameter("idtipo");

            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST" + tipo + "_" + idregistri.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.Rev_accettaRegistroDT(idregistri, totale, descrizione, path, ore, Action.getRegistroByIdDT(idregistri).getProgetto_formativo_dt())) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Accetta Registro " + tipo, "RGDT", idregistri);
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&nome=" + nome + "&cognome=" + cognome
                        + "&cf=" + cf + "&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void anomaliaRimborsoDT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idpolitica = request.getParameter("idpolitica");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String idrimborso = request.getParameter("idrimborso");
        String protocollo = request.getParameter("protocollo");
        String pol = request.getParameter("politica");
        if (Action.numPFRimborsoDT(idrimborso, idpolitica) == 0) {
            if (Action.anomaliaRimborsoConditionDT(idrimborso, idpolitica, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso DT", "RGDT", idpolitica);
                Action.insertScartiPF_DT(idrimborso, idpolitica, motivo);
                response.sendRedirect("rev_showPFRimborsoDT.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            } else {
                response.sendRedirect("rev_showPFRimborsoDT.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            }
            //response.sendRedirect("reg_gestione1B.jsp?esitoact=OK");
        } else {
            if (Action.anomaliaRimborsoDT(idpolitica, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Anomalia Rimborso DT", "RGDT", idpolitica);
                Action.insertScartiPF_DT(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                response.sendRedirect("rev_showPFRimborsoDT.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            } else {
                response.sendRedirect("rev_showPFRimborsoDT.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            }
        }

    }

    protected void scartaRimborsoDT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idpolitica = request.getParameter("idpolitica");
        //String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
        String motivo = request.getParameter("motivo");
        String idrimborso = request.getParameter("idrimborso");
        String protocollo = request.getParameter("protocollo");
        String pol = request.getParameter("politica");
        if (Action.numPFRimborsoDT(idrimborso, idpolitica) == 0) {
            if (Action.scartoRimborsoConditionDT(idrimborso, idpolitica, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso DT", "RGDT", idpolitica);
                Action.insertScartiPF_DT(idrimborso, idpolitica, motivo);
                response.sendRedirect("rev_showPFRimborsoDT.jsp?esito1=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            } else {
                response.sendRedirect("rev_showPFRimborsoDT.jsp?esito1=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            }
            //response.sendRedirect("reg_gestione1B.jsp?esitoact=OK");
        } else {
            if (Action.scartaRimborsoDT(idpolitica, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarto Rimborso DT", "RGDT", idpolitica);
                Action.insertScartiPF_DT(idrimborso, idpolitica, motivo);   //Tabella Scarti_Politiche (solo per singoli scarti/rigetti
                //INCREMENTA BUDGET BANDO
                DecreaseRefill d = Action.DT_C06_DecreaseRefillById_Single(idrimborso);
                Action.refillBudgetPrevisionaleBando(d.getTot_erogato(), d.getIdbando(), d.getPolitica());
                response.sendRedirect("rev_showPFRimborsoDT.jsp?esitoact=OK&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            } else {
                response.sendRedirect("rev_showPFRimborsoDT.jsp?esitoact=KO&id=" + idrimborso + "&protocollo=" + protocollo + "&politica=" + pol);
            }
        }

    }

    public void liquidaRimborso_DOTE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        String totale = request.getParameter("totale");
        String centesimi = request.getParameter("centesimi");
        String descrizione = Utility.CaratteriSpeciali(request.getParameter("descrizione"));
        String pagina = request.getParameter("pagina");
        String ctrlrimborso = request.getParameter("ctrlrimborso");
        Part file = request.getPart("file");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to");

        String path = "-";
        if (centesimi == null || centesimi.equals("")) {
            centesimi = "00";
        }
        totale = totale + "." + centesimi;
        String random = UUID.randomUUID().toString().substring(0, 4);

        if (pagina.equals("rev_liquidazioneB3DT")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST_B3_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.Rev_liquidaRimborso_DOTE_B3(idrimborso, totale, descrizione, path, ctrlrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Liquida Rimborso B3 DT", "RB30", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (pagina.equals("rev_liquidazioneB2DT") || pagina.equals("rev_liquidazioneC2DT")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST_VOUCHER_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.Rev_liquidaRimborso_Voucher_DT(idrimborso, totale, descrizione, path, ctrlrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Liquida Rimborso VOUCHER DT", "RB30", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST_DT_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }

            if (Action.Rev_liquidaRimborso_DOTE(idrimborso, totale, descrizione, path, ctrlrimborso)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Liquida Rimborso DT", "RDT0", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + pagina + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void rigettaRimborso_DOTE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborso");
        //String motivo = Utility.CaratteriSpeciali(Utility.passaLinkDecodifica(request.getParameter("motivo")));
        String motivo = request.getParameter("motivo");
        String tipo = request.getParameter("pagina");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to");

        if (tipo.equals("rev_liquidazioneB3DT")) {
            if (Action.Rev_rigettaRimborso_DOTE_B3(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Rigetta Rimborso B3 DT", "RB30", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("rev_liquidazioneC2DT") || tipo.equals("rev_liquidazioneB2DT")) {
            if (Action.Rev_rigettaRimborso_Voucher_DT(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Rigetta Rimborso VOUCHER DT", "RB30", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else {
            if (Action.Rev_rigettaRimborso_DOTE(idrimborso, motivo)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Rigetta Rimborso DT", "RDT0", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }
    }

    protected void scartaTuttoRimborso_DOTE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idrimborso = request.getParameter("idrimborsoscarto");
        //String motivo = Utility.CaratteriSpeciali(Utility.passaLinkDecodifica(request.getParameter("motivo")));
        String motivo = request.getParameter("motivoscarto");
        String tipo = request.getParameter("pagina");
        Part file = request.getPart("filescarto");

        String ente = request.getParameter("ente"),
                from = request.getParameter("from"),
                to = request.getParameter("to");

        String path = "-";
        String random = UUID.randomUUID().toString().substring(0, 4);

        if (tipo.equals("rev_liquidazioneB3DT")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST_B3_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.Rev_scartaTuttoRimborso_DOTE_B3(idrimborso, motivo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarta Intero Rimborso B3 DT ", "RB30", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else if (tipo.equals("rev_liquidazioneC2DT") || tipo.equals("rev_liquidazioneB2DT")) {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST_VOUCHER_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.Rev_scartaTuttoRimborso_Voucher_DT(idrimborso, motivo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarta Intero Rimborso VOUCHER DT ", "RB30", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        } else {
            if (file != null && file.getSubmittedFileName().length() > 0 && file.getSubmittedFileName() != null) {
                File savedir = new File(Action.getPath("documentibandi"));
                savedir.mkdirs();
                path = savedir.getAbsolutePath() + File.separator + "CHECKLIST_DT_" + idrimborso.replaceAll("[\\s|\\u00A0]+", "") + "_" + random + ".pdf";
                file.write(path);
            }
            if (Action.Rev_scartaTuttoRimborso_DOTE(idrimborso, motivo, path)) {
                Action.insertModTracking((int) request.getSession().getAttribute("id_operatore"), "Rev.Scarta Intero Rimborso DT ", "RDT0", idrimborso);
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=OK&from=" + from + "&to=" + to + "&ente=" + ente);
            } else {
                response.sendRedirect("redirect.jsp?page=" + tipo + ".jsp&esitoact=KO&from=" + from + "&to=" + to + "&ente=" + ente);
            }
        }

    }

    protected void getSumRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");
        String bando = request.getParameter("bando");
        String tabella = "", campo = "";
        if (politica.equals("A01") || politica.equals("B05") || politica.equals("B03")) {
            tabella = "politica";
            campo = "domanda_rimborso";
        } else if (politica.equals("C06") && bando.equals("2")) {
            tabella = "progetto_formativo";
            campo = "rimborso_prg";
        } else if (politica.equals("C06") && bando.equals("1")) {
            tabella = "progetto_formativo_dt";
            campo = "rimborso_prg";
        } else if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
            tabella = "politica_dt";
            campo = "domanda_rimborso_dt";
        } else if (politica.equals("B3")) {
            tabella = "politica_B3_dt";
            campo = "domanda_rimborso_B3_dt";
        } else if (politica.equals("B2") || politica.equals("C2")) {
            tabella = "voucher_dt";
            campo = "domanda_rimborso_voucher_dt";
        }

        double importo = Action.sumRimborsoValue(idrimborso, tabella, campo, politica);
        response.getWriter().write(String.valueOf(importo));
    }
//    protected void anomaliaRimborso_DOTE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String idpolitica = request.getParameter("idpolitica");
//        String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
//        String idrimborso = request.getParameter("idrimborso");
//        String protocollo = request.getParameter("protocollo");
//        String pol = request.getParameter("politica");
//
//    }
//
//    protected void scartaRimborso_DOTE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String idpolitica = request.getParameter("idpolitica");
//        String motivo = Utility.passaLinkDecodifica(request.getParameter("motivo"));
//        String idrimborso = request.getParameter("idrimborso");
//        String protocollo = request.getParameter("protocollo");
//        String pol = request.getParameter("politica");
//
//    }

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

    protected void statoRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String codice = request.getParameter("cod");
        String idrimborso = request.getParameter("idrimborso");

        String stato = Action.getStatoRimborso(idrimborso, codice);
        response.getWriter().write(stato != null ? stato : "null");
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
                    if (tipo == 3) {
                        if (user == null && stato != 3) {
                            redirect(request, response, "Login?type=2");
                        } else {
                            if (type.equals("1")) {
                                liquidaRimborso(request, response);
                            } else if (type.equals("2")) {
                                rigettaRimborso(request, response);
                            } else if (type.equals("3")) {
                                anomaliaRimborso(request, response);
                            } else if (type.equals("4")) {
                                scartaRimborso(request, response);
                            } else if (type.equals("5")) {
                                scartaTuttoRimborso(request, response);
                            } else if (type.equals("6")) {
                                anomaliaRimborsoM5(request, response);
                            } else if (type.equals("7")) {
                                scartaRimborsoM5(request, response);
                            } else if (type.equals("8")) {
                                accettaRegistro(request, response);
                            } else if (type.equals("9")) {
                                rigettaRegistro(request, response);
                            } else if (type.equals("10")) {
                                scartaRegistro(request, response);
                            } else if (type.equals("11")) {
                                anomaliaRimborsoDT(request, response);
                            } else if (type.equals("12")) {
                                scartaRimborsoDT(request, response);
                            } else if (type.equals("13")) {
                                liquidaRimborso_DOTE(request, response);
                            } else if (type.equals("14")) {
                                rigettaRimborso_DOTE(request, response);
                            } else if (type.equals("15")) {
                                scartaTuttoRimborso_DOTE(request, response);
                            } else if (type.equals("16")) {
                                getSumRimborso(request, response);
                            } else if (type.equals("17")) {
                                getPrg(request, response);
                            } else if (type.equals("18")) {
                                getPrgDt(request, response);
                            } else if (type.equals("19")) {
                                statoRimborso(request, response);
                            } else if (type.equals("20")) {
                                getEntepromotore(request, response);
                            }
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
