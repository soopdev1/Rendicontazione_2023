/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import com.seta.activity.Action;
import com.seta.entity.CO;
import com.seta.entity.Contratto;
import com.seta.entity.Convenzione;
import com.seta.entity.Lavoratore;
import com.seta.entity.Politica;
import com.seta.entity.PrgFormativo;
import com.seta.entity.PrgFormativoDt;
import com.seta.entity.Registro;
import com.seta.entity.RegistroDt;
import com.seta.entity.Rimborso;
import com.seta.entity.Rimborso_PrgFormativo;
import com.seta.entity.Rimborso_PrgFormativo_Dt;
import com.seta.entity.ViewPolitiche;
import com.seta.entity.Voucher;
import com.seta.util.Utility;
import static com.seta.util.Utility.redirect;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import java.util.logging.*;
/**
 *
 * @author dolivo
 */
public class QueryRevisore extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger( QueryRevisore.class.getName() );
    protected void rimborsiLiquidare(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String ente = request.getParameter("ente");
        String politica = request.getParameter("politica");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }
        Map<String, String[]> adinfo = Action.get_ADInfos();
        Map<String, String> pol = Action.getPoliticaById();
        ArrayList<Rimborso> list = Action.getListRimborsi(politica, "R", ente, from, to);

        String bando = "2";

        if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
            list = Action.getListRimborsi_DOTE(politica, "R", ente, from, to);
            adinfo = Action.get_ADInfos_DOTE();
            bando = "1";
        } else if (politica.equals("B3")) {
            list = Action.getListRimborsi_DOTE_B3(politica, "R", ente, from, to);
            adinfo = Action.get_ADInfos_DOTE_B3();
            bando = "1";
        } else if (politica.equals("B2") || politica.equals("C2")) {
            list = Action.getListRimborsi_Voucher_DT(politica, "R", ente, from, to);
            adinfo = Action.get_ADInfos_Voucher_DT();
            bando = "1";
        }

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String sing_rimb = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso t : list) {
                try {
                    if (politica.equals("B2") || politica.equals("C2")) {
                        sing_rimb = "<a style='color: white; display: block;' href='rev_showVoucher.jsp?id=" + t.getIdrimborso() + "&politica=" + t.getPolitica() + "&protocollo=" + Utility.correggi(t.getProtocollo()) + "' class='popovers btn btn-default bg-blue fa fa-ticket fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Singoli Rimborsi'> <font color='white' face='verdana'>Mostra</font></a>";
                    } else {
                        sing_rimb = "<a style='color: white; display: block;' href='rev_showPoliticheRimborso.jsp?id=" + t.getIdrimborso() + "&politica=" + t.getPolitica() + "&protocollo=" + Utility.correggi(t.getProtocollo()) + "' class='popovers btn btn-default bg-blue fa fa-list-ul fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Singoli Rimborsi'> <font color='white' face='verdana'>Mostra</font></a>";
                    }
                    valore = valore + " [ \"";
                    valore += "<a style='color: white;' class='bg-green fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return getSumRimborso(" + t.getIdrimborso() + ",&#34" + t.getPolitica() + "&#34," + bando + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Liquida Rimborso' ><font color='white' face='verdana'></font></a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getIdrimborso() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-red fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getIdrimborso() + ")' href='#scartamodal1' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Rimborso' ><font color='white' face='verdana'></font> </a>" + "\",\""
                            + sing_rimb + "\",\""
                            + "<a style='color: white; display: block;' href='rev_showConvenzioni.jsp?idente=" + adinfo.get(t.getIdrimborso())[3] + "&ente=" + adinfo.get(t.getIdrimborso())[0] + "&bando=" + bando + "&politica=" + t.getPolitica() + "' class='popovers btn btn-default bg-purple-wisteria fa fa-list-ul fancyBoxRaf' style='color: white;' data-trigger='hover' data-placement='top' data-content='Convenzioni Ente'> <font color='white' face='verdana'>Mostra</font></a>" + "\",\""
                            + Utility.correggi(adinfo.get(t.getIdrimborso())[0]) + "\",\""
                            + Utility.correggi(adinfo.get(t.getIdrimborso())[1]) + "\",\""
                            + Utility.correggi(pol.get(t.getPolitica())) + "\",\""
                            + Utility.correggi(t.getProtocollo()) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ad_au(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. AD/AU' ><font color='white' face='verdana'></font> </a> <a style='color: white;' class='bg-aqua fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDocumento(), "\\", "/") + "'  data-trigger='hover' data-placement='top' data-content='Visualizza Domanda Rimborso' ><font color='white' face='verdana'></font> </a>" + "\",\""
                            + sdf2.format(sdf1.parse(t.getTimestamp())) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchRimborsiPolitiche(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String esiti = request.getParameter("esi");

        String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");
        ArrayList<Politica> list = Action.getRimborsiPolitiche(politica, idrimborso);
        if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
            list = Action.getRimborsiPolitiche_DOTE(politica, idrimborso);
        } else if (politica.equals("B3")) {
            list = Action.getRimborsiPolitiche_DOTE_B3(politica, idrimborso);
        }

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        Contratto contr = new Contratto();
        String ore = "-";
        String tutor = "-";
        String docs = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Politica t : list) {
                az = "<a style='color: white;' class='bg-yellow-gold  fa fa-exclamation btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + "," + idrimborso + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Scarta Singolo Rimborso' ><font color='white' face='verdana'> Rigetta</font></a> "
                        + "<a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + idrimborso + ")' href='#scartamodal1'  data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Rimborso' ><font color='white' face='verdana'> Scarta</font> </a>";

                if (politica.equals("B03")) {
                    contr = Action.getContrattoById(t.getId());
                    String[] listdocsM3 = Action.getListDocsTirocinanteM3(String.valueOf(t.getId()));
                    docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento M5' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-black-tie btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(contr.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Contratto Destinatario' ><font color='white' face='verdana'></font> </a> ";
                } else if (politica.equals("D2") || politica.equals("D5")) {
                    contr = Action.getContrattoByIdDt(t.getId());
                    String[] listdocsM3 = Action.getListDocsTirocinanteD2D5_DOTE(String.valueOf(t.getId()));
                    docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-black-tie btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(contr.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Contratto Destinatario' ><font color='white' face='verdana'></font> </a> ";
                } else if (politica.equals("B3")) {
                    String[] listdocsM3 = Action.getListDocsTirocinante_DOTE_B3(String.valueOf(t.getId()));
                    docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Registro' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[6], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Timesheet' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue-soft fa fa-file-powerpoint-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[7], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Business Plan' ><font color='white' face='verdana'></font> </a> ";
                } else {
                    String modello = "Modello 5";
                    ore = String.valueOf(t.getDurataeffettiva()) + " h";
                    tutor = t.getNomeTutor();
                    String[] listdocs = Action.getListDocsTirocinante(String.valueOf(t.getId()));
                    if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
                        listdocs = Action.getListDocsTirocinante_DOTE(String.valueOf(t.getId()));
                        modello = "Allegato 8";
                    }
                    docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[6], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='" + modello + "' ><font color='white' face='verdana'></font> </a> ";
                }
                if (esiti.equals("OK")) {
                    valore = valore + " [ \""
                            + t.getNome() + " " + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + ore + "\",\""
                            + tutor + "\",\""
                            //+ sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + docs + "\",\""
                            + "\"],";
                } else {
                    valore = valore + " [ \""
                            + az + "\",\""
                            + t.getNome() + " " + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + ore + "\",\""
                            + tutor + "\",\""
                            //+ sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + docs + "\",\""
                            + "\"],";
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchConvenzioni(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idente = request.getParameter("idente");
        String bando = request.getParameter("bando");
        String politica = request.getParameter("politica");
        ArrayList<Convenzione> list = Action.getConvenzioni(Integer.parseInt(idente), bando, politica);
//        ArrayList<Convenzione> list = Action.getConvenzioni(Integer.parseInt(idente));
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String doc = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Convenzione t : list) {
                doc = "<a style='color: white;' class='bg-blue-dark fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento' ><font color='white' face='verdana'></font> </a> ";
                try {
                    valore = valore + " [ \""
                            + t.getCodice() + "\",\""
                            + t.getD_politica() + "\",\""
                            + sdf2.format(sdf1.parse(t.getInizio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getFine())) + "\",\""
                            + (t.getFile() == null ? "-" : doc) + "\"],";
                } catch (ParseException e) {
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchScartiPolitiche(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("politica");

        ArrayList<Politica> list = Action.getScartiPolitiche(idrimborso, politica);
        if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
            list = Action.getScartiPolitiche_DOTE(idrimborso, politica);
        } else if (politica.equals("B3")) {
            list = Action.getScartiPolitiche_DOTE_B3(idrimborso);
        }
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String tutor = "-";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Politica t : list) {
                try {

                    if (!politica.equals("B03") && !politica.equals("D2") && !politica.equals("D5") && !politica.equals("B3")) {
                        tutor = t.getNomeTutor();
                    }

                    valore = valore + " [ \""
                            + Utility.correggi(t.getNome()) + "\",\""
                            + Utility.correggi(t.getCognome()) + "\",\""
                            + Utility.correggi(t.getCf()) + "\",\""
                            + Utility.correggi(tutor) + "\",\"";

                    if (politica.equals("B05")) {
                        valore += t.getDurataeffettiva() + " h" + "\",\"";
                    }
                    valore += Utility.correggi(t.getMotivo()) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + "\"],";
                } catch (ParseException e) {
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void LiquidaRimborsiPF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String ente = request.getParameter("ente");
        String politica = request.getParameter("politica");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }

        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo();
        Map<String, String> pol = Action.getPoliticaById();
        ArrayList<Rimborso_PrgFormativo> list = Action.getListRimborsi_PrgFormativo(politica, "R", ente, from, to);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String bando = "2";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo t : list) {
                try {
                    valore = valore + " [ \"";
                    
                    valore += "<a style='color: white;' class='bg-green fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return getSumRimborso(" + t.getIdrimborso_prg() + ",&#34" + t.getPolitica() + "&#34," + bando + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Liquida Rimborso' ><font color='white' face='verdana'></font></a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getIdrimborso_prg() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white; ' class='bg-red fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getIdrimborso_prg() + ")' href='#scartamodal1' data-trigger='hover' data-placement='top' data-content='Scarta Rimborso Definitivamente' ><font color='white' face='verdana'></font> </a>" + "\",\""
                            + "<a style='color: white; display: block;' href='rev_showPFRimborso.jsp?id=" + t.getIdrimborso_prg() + "&politica=" + t.getPolitica() + "&protocollo=" + Utility.correggi(t.getProtocollo()) + "' class='popovers btn btn-default bg-blue fa fa-list-ul fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Singoli Progetti Formativi'> <font color='white' face='verdana'>Mostra</font></a>" + "\",\""
                            + "<a style='color: white; display: block;' href='rev_showConvenzioni.jsp?idente="
                            + adinfo.get(t.getIdrimborso_prg())[3] + "&ente="
                            + adinfo.get(t.getIdrimborso_prg())[0] + "&bando="
                            + bando + "&politica=C06' class='popovers btn btn-default bg-purple-wisteria fa fa-list-ul fancyBoxRaf' style='color: white' data-trigger='hover' data-placement='top' data-content='Convenzioni Ente'> <font color='white' face='verdana'>Mostra</font></a>" + "\",\""
                            + Utility.correggi(adinfo.get(t.getIdrimborso_prg())[0]) + "\",\""
                            + Utility.correggi(adinfo.get(t.getIdrimborso_prg())[1]) + "\",\""
                            + Utility.correggi(pol.get(t.getPolitica())) + "\",\""
                            + Utility.correggi(t.getIdrimborso_prg()) + "\",\""
                            + Utility.correggi(t.getProtocollo()) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ad_au(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. AD/AU' ><font color='white' face='verdana'></font> </a> <a style='color: white;' class='bg-aqua fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDocumento(), "\\", "/") + "'  data-trigger='hover' data-placement='top' data-content='Visualizza Domanda Rimborso' ><font color='white' face='verdana'></font> </a>" + "\",\""
                            + sdf2.format(sdf1.parse(t.getTimestamp())) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchRimborsiPF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        //String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");
        String esiti = request.getParameter("esi");

        ArrayList<PrgFormativo> list = Action.getListPrgFormativo(idrimborso);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        String docs = "";
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (PrgFormativo t : list) {

                String[] listdocs = Action.getListDocsTirocinanteM5(String.valueOf(t.getId()));
                docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-meadow fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[7], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Prg. Formativo' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[6], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Competenze' ><font color='white' face='verdana'></font> </a> ";
                valore = valore + " [ \"";
                az = "<a style='color: white;' class='bg-yellow-gold  fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Seganala Criticità Progetto Formativo' ></a> <a style='color: white;' class='bg-red-thunderbird fa fa-times-circle btn btn-default popovers' onclick='return setScartaID2(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal1' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Progetto Formativo' ></a>";

                if (esiti.equals("OK")) {
                    valore += Utility.correggi(t.getCognome() + " " + t.getNome()) + "\",\""
                            + t.getCf() + "\",\""
                            + t.getProfiling() + "\",\""
                            + t.getDurata_mesi() + " mesi" + "\",\""
                            + t.getOre_effettuate() + " h" + "\",\""
                            + t.getOre_tot() + " h" + "\",\""
                            + docs + "\",\""
                            + "\"],";

                } else {
                    valore += az + "\",\""
                            + Utility.correggi(t.getCognome() + " " + t.getNome()) + "\",\""
                            + t.getCf() + "\",\""
                            + t.getProfiling() + "\",\""
                            + t.getDurata_mesi() + " mesi" + "\",\""
                            + t.getOre_effettuate() + " h" + "\",\""
                            + t.getOre_tot() + " h" + "\",\""
                            + docs + "\",\""
                            + "\"],";

                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchScartiPF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idrimborso = request.getParameter("idrimborso");
        //String politica = request.getParameter("politica");

        ArrayList<PrgFormativo> list = Action.getScartiPF(idrimborso);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (PrgFormativo t : list) {
                try {
                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + t.getNomeTutor() + "\",\""
                            + t.getOre_effettuate() + " h" + "\",\""
                            + t.getMotivo() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + "\"],";
                } catch (ParseException e) {
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchRegistri(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String ente = request.getParameter("ente");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String politica = request.getParameter("politica");
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }
        if (cognome == null || cognome.equals("null")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        ArrayList<Registro> list = Action.getListRegistri(politica, "R", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String az = "";
        String prg = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Registro t : list) {
                try {
                    docs = "<a style='color: white;' class='bg-yellow-gold fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Registro' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue-chambray fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_lavoratore(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-file-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_quietanza(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Quietanza' ><font color='white' face='verdana'></font> </a> ";
                    //MODALS
                    az = "<a style='color: white;' class='bg-green fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setAccettaID(" + t.getId() + "," + t.getOre() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Registro' ><font color='white' face='verdana'></font></a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white; ' class='bg-red fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getOre() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Scarta Registro Definitivamente' ><font color='white' face='verdana'></font> </a>";

                    prg = "<a style='color: white; ' class='bg-blue-chambray fa fa-folder btn btn-default popovers' onclick='return showPrg(" + t.getProgetto_formativo() + ")' data-trigger='hover' data-placement='top' data-content='Dati Progetto Formativo' ><font color='white' face='verdana'></font> </a>";

                    valore = valore + " [ \"";
                    valore += az + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
                            + prg + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getOre() + " h" + "\",\""
                            + docs + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataup())) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchRegistri_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String ente = request.getParameter("ente");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String politica = request.getParameter("politica");
        if (politica == null || politica.equals("null") || politica.equals("...")) {
            politica = "";
        }
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }
        if (cognome == null || cognome.equals("null")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        ArrayList<RegistroDt> list = Action.getListRegistriDT(politica, "R", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String az = "";
        String prg = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (RegistroDt t : list) {
                try {
                    docs = "<a style='color: white;' class='bg-yellow-gold fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Registro' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue-chambray fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_lavoratore(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> ";
                    //MODALS
                    az = "<input type='hidden' id='" + t.getId() + "_pol' value='" + t.getPolitica() + "' />"
                            + "<a style='color: white;' class='bg-green fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setAccettaID(" + t.getId() + "," + t.getOre() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Registro' ><font color='white' face='verdana'></font></a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white; ' class='bg-red fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getOre() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Scarta Registro Definitivamente' ><font color='white' face='verdana'></font> </a>";

                    prg = "<a style='color: white; ' class='bg-blue-chambray fa fa-folder btn btn-default popovers' onclick='return showPrg(" + t.getProgetto_formativo_dt() + ")' data-trigger='hover' data-placement='top' data-content='Dati Progetto Formativo' ><font color='white' face='verdana'></font> </a>";

                    valore = valore + " [ \"";
                    valore += az + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
                            + prg + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getOre() + " h" + "\",\""
                            + docs + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataup())) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void LiquidaRimborsiPF_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String ente = request.getParameter("ente");
        String politica = request.getParameter("politica");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }

        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo_DT();
        Map<String, String> pol = Action.getPoliticaByIdDT();
        ArrayList<Rimborso_PrgFormativo_Dt> list = Action.getListRimborsi_PrgFormativoDT(politica, "R", ente, from, to);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String bando = "1";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo_Dt t : list) {
                try {
                    valore = valore + " [ \"";
                    valore += "<input type='hidden' id='" + t.getIdrimborso_prg_dt() + "_pol' value='" + t.getPolitica() + "' />"
                            + "<a style='color: white;' class='bg-green fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return getSumRimborso(" + t.getIdrimborso_prg_dt() + ",&#34" + t.getPolitica() + "&#34," + bando + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Liquida Rimborso' ><font color='white' face='verdana'></font></a> <a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getIdrimborso_prg_dt() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità' ><font color='white' face='verdana'></font> </a> <a style='color: white; ' class='bg-red fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getIdrimborso_prg_dt() + ")' href='#scartamodal1' data-trigger='hover' data-placement='top' data-content='Scarta Rimborso Definitivamente' ><font color='white' face='verdana'></font> </a>" + "\",\""
                            + "<a style='color: white; display: block;' href='rev_showPFRimborsoDT.jsp?id=" + t.getIdrimborso_prg_dt() + "&politica=" + t.getPolitica() + "&protocollo=" + Utility.correggi(t.getProtocollo()) + "' class='popovers btn btn-default bg-blue fa fa-list-ul fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Singoli Progetti Formativi'> <font color='white' face='verdana'>Mostra</font></a>" + "\",\""
                            + "<a style='color: white; display: block;' href='rev_showConvenzioni.jsp?idente=" + adinfo.get(t.getIdrimborso_prg_dt())[3] + "&ente=" + adinfo.get(t.getIdrimborso_prg_dt())[0] + "&bando=" + bando + "&politica=C06' class='popovers btn btn-default bg-purple-wisteria fa fa-list-ul fancyBoxRaf' style='color: white' data-trigger='hover' data-placement='top' data-content='Convenzioni Ente'> <font color='white' face='verdana'>Mostra</font></a>" + "\",\""
                            + Utility.correggi(adinfo.get(t.getIdrimborso_prg_dt())[0]) + "\",\""
                            + Utility.correggi(adinfo.get(t.getIdrimborso_prg_dt())[1]) + "\",\""
                            + Utility.correggi(pol.get(t.getPolitica())) + "\",\""
                            + Utility.correggi(t.getProtocollo()) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ad_au(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. AD/AU' ><font color='white' face='verdana'></font> </a> <a style='color: white;' class='bg-aqua fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDocumento(), "\\", "/") + "'  data-trigger='hover' data-placement='top' data-content='Visualizza Domanda Rimborso' ><font color='white' face='verdana'></font> </a>" + "\",\""
                            + sdf2.format(sdf1.parse(t.getTimestamp())) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchRimborsiPF_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        //String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");
        String esiti = request.getParameter("esi");
        ArrayList<PrgFormativoDt> list = Action.getListPrgFormativo_DT(idrimborso);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        String docs = "";
        //SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (PrgFormativoDt t : list) {

                String[] listdocs = Action.getListDocsTirocinanteDT(String.valueOf(t.getId()));
                docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;'  class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-meadow fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[8], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Prg. Formativo' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-jungle fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[7], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;'  class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[6], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Competenze' ><font color='white' face='verdana'></font> </a> ";
                valore = valore + " [ \"";
                az = "<a style='color: white;' class='bg-yellow-gold  fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità Progetto Formativo' ></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal1' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Progetto Formativo' ></a>";

                if (esiti.equals("OK")) {
                    valore += az + "\",\""
                            + Utility.correggi(t.getCognome() + " " + t.getNome()) + "\",\""
                            + t.getCf() + "\",\""
                            + t.getProfiling() + "\",\""
                            + t.getDurata_mesi() + " mesi" + "\",\""
                            + t.getOre_effettuate() + " h" + "\",\""
                            + t.getOre_tot() + " h" + "\",\""
                            + docs + "\",\""
                            + "\"],";
                } else {
                    valore += az + "\",\""
                            + Utility.correggi(t.getCognome() + " " + t.getNome()) + "\",\""
                            + t.getCf() + "\",\""
                            + t.getProfiling() + "\",\""
                            + t.getDurata_mesi() + " mesi" + "\",\""
                            + t.getOre_effettuate() + " h" + "\",\""
                            + t.getOre_tot() + " h" + "\",\""
                            + docs + "\",\""
                            + "\"],";

                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchScartiPF_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idrimborso = request.getParameter("idrimborso");
        //String politica = request.getParameter("politica");

        ArrayList<PrgFormativoDt> list = Action.getScartiPF_DT(idrimborso);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (PrgFormativoDt t : list) {
                try {
                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + t.getNomeTutor() + "\",\""
                            + t.getOre_effettuate() + " h" + "\",\""
                            + t.getMotivo() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + "\"],";
                } catch (ParseException e) {
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchRimborsiVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");
        String esiti = request.getParameter("esi");
        ArrayList<Voucher> list = Action.getRimborsiVoucher(politica, idrimborso);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        String ore = "-";
        String docs = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Voucher t : list) {
                ore = String.valueOf(t.getOre()) + " h";
                String[] listdocs = Action.getListDocsTirocinante_Voucher(String.valueOf(t.getId()));
                az = "<a style='color: white;' class='bg-yellow-gold  fa fa-exclamation btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + "," + idrimborso + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Scarta Singolo Rimborso' ><font color='white' face='verdana'> Rigetta</font></a> "
                        + "<a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + idrimborso + ")' href='#scartamodal1'  data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Rimborso' ><font color='white' face='verdana'> Scarta</font> </a>";

                docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[6], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[7], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento M5' ><font color='white' face='verdana'></font> </a> ";

                if (esiti.equals("OK")) {
                    valore = valore + " [ \""
                            + t.getNome() + " " + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + ore + "\",\""
                            + docs + "\",\""
                            + "\"],";
                } else {
                    valore = valore + " [ \""
                            + az + "\",\""
                            + t.getNome() + " " + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + ore + "\",\""
                            + docs + "\",\""
                            + "\"],";
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchScartiVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idrimborso = request.getParameter("idrimborso");

        ArrayList<Voucher> list = Action.getScartiPolitiche_Voucher_DT(idrimborso);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Voucher t : list) {
                try {
                    valore = valore + " [ \""
                            + Utility.correggi(t.getNome()) + "\",\""
                            + Utility.correggi(t.getCognome()) + "\",\""
                            + Utility.correggi(t.getCf()) + "\",\""
                            + t.getOre() + " h" + "\",\""
                            + Utility.correggi(t.getMotivo()) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + "\"],";
                } catch (ParseException e) {
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void gestioneEsiti(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String ente = request.getParameter("ente");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String esito = request.getParameter("esito");
        String politica = request.getParameter("politica");
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }
        if (esito == null || esito.equals("null") || esito.equals("...")) {
            esito = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }

        ArrayList<Rimborso> list = Action.getListEsiti(politica, esito, ente, from, to);
        Map<String, String[]> adinfo = Action.get_ADInfos();
        if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
            adinfo = Action.get_ADInfos_DOTE();
            list = Action.getListEsiti_DOTE(politica, esito, ente, from, to);
        } else if (politica.equals("B3")) {
            adinfo = Action.get_ADInfos_DOTE_B3();
            list = Action.getListEsiti_DOTE_B3(politica, esito, ente, from, to);
        } else if (politica.equals("B2") || politica.equals("C2")) {
            adinfo = Action.get_ADInfos_Voucher_DT();
            list = Action.getListEsiti_Voucher_DT(politica, esito, ente, from, to);
        }
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String es = "";
        String motivo = "-";
        String totale = "-";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String rimb_sing = "";
        String esi = "OK";

        if (list.size() > 0) {
            for (Rimborso t : list) {
                try {
                    valore = valore + " [ \"";

                    es = "<a style='color: white; cursor: default; display: block;' class='bg-red-thunderbird fa fa-ban btn btn-default' ><font color='white' face='verdana'> Rigettato</font> </a>";

                    if (politica.equals("B2") || politica.equals("C2")) {
                        rimb_sing = "<a href='rev_showVoucher.jsp?id=" + t.getIdrimborso() + "&politica=" + politica + "&protocollo=" + Utility.correggi(t.getProtocollo()) + "&esi=" + esi + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Rimborsi Voucher'><i class='btn btn-default bg-blue fa fa-ticket' style='color: white; display: block;' ><font face='verdana'> Lista Rimborsi</font></i></a>";
                    } else {
                        rimb_sing = "<a href='rev_showPoliticheRimborso.jsp?id=" + t.getIdrimborso() + "&politica=" + politica + "&protocollo=" + Utility.correggi(t.getProtocollo()) + "&esi=" + esi + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Rimborsi'><i class='btn btn-default bg-blue fa fa-list-ul' style='color: white; display: block;' ><font face='verdana'> Lista Rimborsi</font></i></a>";
                    }

                    if (t.getStato().equals("P")) {
                        es = "<a style='color: white; cursor: default; display: block;' class='bg-green-jungle fa fa-check btn btn-default' ><font color='white' face='verdana'> Pagato</font></a> ";
                        motivo = "-";
                    } else {
                        motivo = t.getMotivo();
                        rimb_sing = "<a style='color: white; cursor: default; display: block;' class='bg-blue-chambray fa fa-close btn btn-default' ><font color='white' face='verdana'> Non Disponibile</font></a>";
                    }

                    if (t.getTot_erogato() != null) {
                        totale = "&euro; " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato()));
                    }

                    valore += es + "\",\""
                            + rimb_sing + "\",\""
                            + adinfo.get(t.getIdrimborso())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso())[1] + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + totale + "\",\""
                            + Utility.correggi(motivo) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void gestioneEsitiPF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String ente = request.getParameter("ente");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String politica = request.getParameter("politica");
        String esito = request.getParameter("esito");
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }
        if (esito == null || esito.equals("null") || esito.equals("...")) {
            esito = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }

        ArrayList<Rimborso_PrgFormativo> list = Action.getListEsiti_PrgFormativo(politica, esito, ente, from, to);
        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo();

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String motivo = "-";
        String totale = "-";
        String es = "";
        String rs = "";
        String esi = "OK";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo t : list) {
                try {
                    es = "<a style='color: white; cursor: default; display: block;' class='bg-red-thunderbird fa fa-ban btn btn-default' ><font color='white' face='verdana'> Rigettato</font> </a>";

                    if (t.getStato().equals("P")) {
                        es = "<a style='color: white; cursor: default; display: block;' class='bg-green-jungle fa fa-check btn btn-default' ><font color='white' face='verdana'> Pagato</font></a> ";
                        motivo = "-";
                        rs = "<a href='rev_showPFRimborso.jsp?id=" + t.getIdrimborso_prg() + "&politica=" + politica + "&protocollo=" + Utility.correggi(t.getProtocollo()) + "&esi=" + esi + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Progetti Formativi'><i class='btn btn-default bg-blue fa fa-file-text-o' style='color: white; cursor: default; display: block;' ><font face='verdana'> Progetti Formativi</font></i></a>";
                    } else {
                        motivo = t.getMotivo();
                        rs = "<a style='color: white; cursor: default; display: block;' class='bg-blue-chambray fa fa-close btn btn-default' ><font color='white' face='verdana'> Non Disponibile</font></a>";
                    }

                    if (t.getTot_erogato() != null) {
                        totale = "&euro; " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato()));
                    }

                    valore = valore + " [ \"";
                    valore += es + "\",\""
                            + rs + "\",\""
                            + adinfo.get(t.getIdrimborso_prg())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso_prg())[1] + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + totale + "\",\""
                            + Utility.correggi(motivo) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void gestioneEsitiPF_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String ente = request.getParameter("ente");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String politica = request.getParameter("politica");
        String esito = request.getParameter("esito");
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }
        if (esito == null || esito.equals("null") || esito.equals("...")) {
            esito = "";
        }

        ArrayList<Rimborso_PrgFormativo_Dt> list = Action.getListEsiti_PrgFormativoDT(politica, esito, ente, from, to);
        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo_DT();
        //Map<String, String> pol = Action.getPoliticaByIdDT();
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String motivo = "-";
        String totale = "-";
        String es = "";
        String rs = "";
        String esi = "OK";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo_Dt t : list) {
                try {
                    es = "<a style='color: white; cursor: default; display: block;' class='bg-red-thunderbird fa fa-ban btn btn-default' ><font color='white' face='verdana'> Rigettato</font> </a>";

                    if (t.getStato().equals("P")) {
                        es = "<a style='color: white; cursor: default; display: block;' class='bg-green-jungle fa fa-check btn btn-default' ><font color='white' face='verdana'> Pagato</font></a> ";
                        motivo = "-";
                        rs = "<a href='rev_showPFRimborsoDT.jsp?id=" + t.getIdrimborso_prg_dt() + "&politica=" + t.getPolitica() + "&protocollo=" + Utility.correggi(t.getProtocollo()) + "&esi=" + esi + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Progetti Formativi'><i class='btn btn-default bg-blue fa fa-file-text-o' style='color: white; cursor: default; display: block;' ><font face='verdana'> Progetti Formativi</font></i></a>";
                    } else {
                        motivo = t.getMotivo();
                        rs = "<a style='color: white; cursor: default; display: block;' class='bg-blue-chambray fa fa-close btn btn-default' ><font color='white' face='verdana'> Non Disponibile</font></a>";
                    }

                    if (t.getTot_erogato() != null) {
                        totale = "&euro; " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato()));
                    }

                    valore = valore + " [ \"";

                    valore += es + "\",\""
                            + rs + "\",\""
                            + adinfo.get(t.getIdrimborso_prg_dt())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso_prg_dt())[1] + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + totale + "\",\""
                            + Utility.correggi(motivo) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchEsitiRegistri(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String ente = request.getParameter("ente");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String politica = request.getParameter("politica");
        String esito = request.getParameter("esito");
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }
        if (cognome == null || ente.equals("cognome")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        if (esito == null || esito.equals("null") || esito.equals("...")) {
            esito = "";
        }

        ArrayList<Registro> list = Action.getListRegistri(politica, esito, ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String motivo = "-";
        String totale = "-";
        String es = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Registro t : list) {
                try {
                    es = "<a style='color: white; cursor: default; display: block;' class='bg-red-thunderbird fa fa-ban btn btn-default' ><font color='white' face='verdana'> Rigettato</font> </a>";

                    if (t.getStato().equals("P")) {
                        es = "<a style='color: white; cursor: default; display: block;' class='bg-green-jungle fa fa-check btn btn-default' ><font color='white' face='verdana'> Pagato</font></a> ";
                        motivo = "-";
                    } else {
                        motivo = t.getMotivo();
                    }

                    if (String.valueOf(t.getTot_erogato()) != null) {
                        totale = "&euro; " + String.format("%1$,.2f", t.getTot_erogato());
                    }

                    valore = valore + " [ \"";
                    valore += es + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getOre() + " h" + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataup())) + "\",\""
                            + Utility.correggi(motivo) + "\",\""
                            + totale + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchEsitiRegistri_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String ente = request.getParameter("ente");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String politica = request.getParameter("politica");
        String esito = request.getParameter("esito");
        if (esito == null || esito.equals("null") || esito.equals("...")) {
            esito = "";
        }

        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }
        if (cognome == null || cognome.equals("null")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        ArrayList<RegistroDt> list = Action.getListEsitiDT(politica, esito, ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String motivo = "-";
        String totale = "-";
        String es = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (RegistroDt t : list) {
                try {
                    es = "<a style='color: white; cursor: default; display: block;' class='bg-red-thunderbird fa fa-ban btn btn-default' ><font color='white' face='verdana'> Rigettato</font> </a>";

                    if (t.getStato().equals("P")) {
                        es = "<a style='color: white; cursor: default; display: block;' class='bg-green-jungle fa fa-check btn btn-default' ><font color='white' face='verdana'> Pagato</font></a> ";
                        motivo = "-";
                    } else {
                        motivo = t.getMotivo();
                    }

                    if (String.valueOf(t.getTot_erogato()) != null) {
                        totale = "&euro; " + String.format("%1$,.2f", t.getTot_erogato());
                    }

                    valore = valore + " [ \"";
                    valore += es + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getOre() + " h" + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataup())) + "\",\""
                            + Utility.correggi(motivo) + "\",\""
                            + totale + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchLavoratori(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        if (cognome == null || cognome.equals("null")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        Map<String, String> prov = Action.getProvinciaByCode();
        Map<String, String> comune = Action.getComuneByCode();
        ArrayList<Lavoratore> list = Action.getLavoratori(nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String co = "";
        String politiche = "";
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Lavoratore t : list) {

                String az = "<div class='btn-group'><a class='btn btn-default blue btn-outline' href='javascript:;' data-toggle='dropdown'>"
                        + "<i class='fa fa-wrench'></i></a>"
                        + "<ul class='dropdown-menu '>";
                co = "<li><a href='show_CO_rev.jsp?idente=nessuno&idlav=" + t.getCdnlavoratore() + "' class='fancyBoxDon' ><i class='btn btn-default bg-blue fa fa-clone' style='padding-right:23px; color:white;'></i>Visualizza CO</a></li>";
                politiche = "<li><a href='show_politichelavoratore_rev.jsp?idlav=" + t.getCdnlavoratore() + "' class='fancyBoxRaf' ><i class='btn btn-default bg-yellow-gold fa fa-list-ul' style='padding-right:23px; color:white;'></i>Visualizza Politiche</a></li>";
                az += co + politiche + "</ul></div>";

                valore = valore + " [ \"";
                valore += az + "\",\""
                        + Utility.correggi(t.getCognome()) + "\",\""
                        + Utility.correggi(t.getNome()) + "\",\""
                        + t.getCodice_fiscale() + "\",\""
                        + sdf2.format(t.getNascita_data()) + "\",\""
                        + (t.getRecapito_telefonico() == null ? "Nd" : t.getRecapito_telefonico()) + "\",\""
                        + t.getEmail() + "\",\""
                        + Utility.correggi(t.getDomicilio_indirizzo()) + " (" + comune.get(t.getDomicilio_codice_catastale()) + " - " + prov.get(t.getDomicilio_codice_catastale()) + ")" + "\",\""
                        + Utility.correggi(t.getResidenza_indirizzo()) + " (" + comune.get(t.getResidenza_codice_catastale()) + " - " + prov.get(t.getResidenza_codice_catastale()) + ")" + "\",\""
                        + "\"],";

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void showCO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idente = request.getParameter("idente");
        String idlav = request.getParameter("idlav");
        ArrayList<CO> list = Action.getListCO(idente, idlav);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String mov = "";
        String cf_utilizzatore = "-";
        String cf_datore = "-";

        if (list.size() > 0) {
            for (CO c : list) {
                if (c.getTipo_movimento().equals("AVV")) {
                    mov = "Avviata";
                } else {
                    mov = "Cessata";
                }
                if (c.getCf_datorelavoro() != null) {
                    cf_datore = c.getCf_datorelavoro();
                }
                if (c.getCf_datorelavoro() != null) {
                    cf_utilizzatore = c.getCf_utilizzatore();
                }

                valore = valore + " [ \"";

                valore += Utility.formatStringtoStringDate(c.getData_inizio(), "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy") + "\",\""
                        + Utility.formatStringtoStringDate(c.getData_fine(), "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy") + "\",\""
                        + Utility.correggi(cf_datore) + "\",\""
                        + Utility.correggi(cf_utilizzatore) + "\",\""
                        + Utility.correggi(c.getMansione()) + "\",\""
                        + Utility.correggi(c.getContratto()) + "\",\""
                        + mov + "\",\""
                        + Utility.formatStringtoStringDate(c.getData_avvio_CO(), "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy") + "\",\""
                        + "\"],";

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void showpoliticheLavoratore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idlav = request.getParameter("idlav");
        ArrayList<ViewPolitiche> list = Action.getPoliticheLavoratore(idlav);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String stato = "";
        String cf_datore = "-";
        String rimborso = "";
        String dinizio = "";
        String dfine = "";
        //Map<String, String> pol = Action.getPoliticaById();
        Map<String, String> pol = new HashMap<String, String>();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String poldesc = "";
        if (list.size() > 0) {
            for (ViewPolitiche c : list) {
                if (c.getStato().equals("A")) {
                    stato = "Associata a domanda di rimborso";
                } else if (c.getStato().equals("E")) {
                    stato = "Riscontrato errore sanabile";
                } else if (c.getStato().equals("I")) {
                    stato = "Pronta per il caricamento dei documenti";
                } else if (c.getStato().equals("K")) {
                    stato = "Bocciata per errore non sanabile";
                } else if (c.getStato().equals("S")) {
                    stato = "Documenti caricati correttamente";
                } else if (c.getStato().equals("0")) {
                    stato = "Politica presente ma non ancora rendicontabile";
                }

                if (c.getCod().equals("P000") || c.getCod().equals("PRG0")) {
                    pol = Action.getPoliticaByIdGG();
                } else {
                    pol = Action.getPoliticaByIdDT();
                }

                if (c.getCf_ente() != null) {
                    cf_datore = c.getCf_ente();
                }
                if (c.getDomanda_rimborso() != 0) {
                    rimborso = "<a style='color: white;display: block;' class='bg-blue fa fa-hourglass-1 btn btn-default popovers' data-toggle='modal' onclick='return showStatoRimborso(" + c.getDomanda_rimborso() + ",&quot;" + c.getCod() + "&quot;)' data-trigger='hover' data-placement='top' data-content='Mostra Stato Rimborso' ><font color='white' face='verdana'> Stato Rimborso</font></a>";
                } else {
                    rimborso = "<a style='color: white ; cursor: default;display: block;' class='bg-blue-chambray fa fa-close btn btn-default popovers' data-toggle='modal' data-trigger='hover' data-placement='top' data-content='Nessun Rimborso Associato'><font color='white' face='verdana'> Non Disponibile</font></a> ";
                }
                if (c.getDataavvio() != null) {
                    dinizio = sdf2.format(c.getDataavvio());
                } else {
                    dinizio = "-";
                }
                if (c.getDatafine() != null) {
                    dfine = sdf2.format(c.getDatafine());
                } else {
                    dfine = "-";
                }
                cf_datore = "<a class='popovers' data-toggle='modal' data-trigger='hover' data-placement='top' data-content='Ragione Sociale' onclick='return showEnte(&quot;" + Utility.correggi(cf_datore) + "&quot;)'>" + Utility.correggi(cf_datore) + "</a>" + "\",\"";
                valore = valore + " [ \"";

                valore += pol.get(c.getCodazioneformcal()) + "\",\""
                        + dinizio + "\",\""
                        + dfine + "\",\""
                        + cf_datore
                        + stato + "\",\""
                        + rimborso + "\",\""
                        + "\"],";

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchPrgFormativi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String ente = request.getParameter("ente");

        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cognome == null || cognome.equals("null")) {
            cognome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }

        ArrayList<PrgFormativo> list = Action.getPrgFormativiReg(ente, politica, nome, cognome, cf, from, to);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String doc = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String doc_lav = "", comp = "", doc_tut = "";
        if (list.size() > 0) {
            for (PrgFormativo t : list) {
                try {

                    doc_lav = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> ";
                    doc_tut = "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> ";
                    comp = "<a style='color: white;' class='bg-green-soft fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_competenze(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Doc. Competenze' ><font color='white' face='verdana'></font> </a> " + "\",\"";

                    doc = "";

                    doc += t.getDoc_ragazzo() != null ? doc_lav : "";
                    doc += t.getDoc_tutor() != null ? doc_tut : "";
                    doc += t.getDoc_competenze() != null ? comp : "";

                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + t.getDe_ente() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + String.valueOf(t.getOre_tot()) + "\",\""
                            + String.valueOf(t.getOre_effettuate()) + "\",\""
                            + ((t.getStato().equals("I")) ? "No Doc." + "\",\"" : doc);

                    valore += "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchPrgFormativiDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String ente = request.getParameter("ente");

        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cognome == null || cognome.equals("null")) {
            cognome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }
        if (ente == null || ente.equals("null") || ente.equals("...")) {
            ente = "";
        }

        ArrayList<PrgFormativoDt> list = Action.getPrgFormativiRegDt(ente, politica, nome, cognome, cf, from, to);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String doc = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String doc_lav = "", comp = "", doc_tut = "";
        if (list.size() > 0) {
            for (PrgFormativoDt t : list) {
                try {

                    doc_lav = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> ";
                    doc_tut = "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> ";
                    comp = "<a style='color: white;' class='bg-green-soft fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_competenze(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Doc. Competenze' ><font color='white' face='verdana'></font> </a> " + "\",\"";

                    doc = "";

                    doc += t.getDoc_ragazzo() != null ? doc_lav : "";
                    doc += t.getDoc_tutor() != null ? doc_tut : "";
                    doc += t.getDoc_competenze() != null ? comp : "";

                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + t.getDe_ente() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + String.valueOf(t.getOre_tot()) + "\",\""
                            + String.valueOf(t.getOre_effettuate()) + "\",\""
                            + ((t.getStato().equals("I")) ? "No Doc." + "\",\"" : doc);

                    valore += "\"],";
                } catch (ParseException ex) {
                   LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
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
                        } else if (type.equals("1")) {
                            rimborsiLiquidare(request, response);
                        } else if (type.equals("2")) {
                            searchRimborsiPolitiche(request, response);
                        } else if (type.equals("3")) {
                            searchConvenzioni(request, response);
                        } else if (type.equals("4")) {
                            searchScartiPolitiche(request, response);
                        } else if (type.equals("5")) {
                            LiquidaRimborsiPF(request, response);
                        } else if (type.equals("6")) {
                            searchRimborsiPF(request, response);
                        } else if (type.equals("7")) {
                            searchScartiPF(request, response);
                        } else if (type.equals("8")) {
                            searchRegistri(request, response);
                        } else if (type.equals("9")) {
                            searchRegistri_DT(request, response);
                        } else if (type.equals("10")) {
                            LiquidaRimborsiPF_DT(request, response);
                        } else if (type.equals("11")) {
                            searchRimborsiPF_DT(request, response);
                        } else if (type.equals("12")) {
                            searchScartiPF_DT(request, response);
                        } else if (type.equals("13")) {
                            searchRimborsiVoucher(request, response);
                        } else if (type.equals("14")) {
                            searchScartiVoucher(request, response);
                        } else if (type.equals("15")) {
                            gestioneEsiti(request, response);
                        } else if (type.equals("16")) {
                            gestioneEsitiPF(request, response);
                        } else if (type.equals("17")) {
                            gestioneEsitiPF_DT(request, response);
                        } else if (type.equals("18")) {
                            searchEsitiRegistri(request, response);
                        } else if (type.equals("19")) {
                            searchEsitiRegistri_DT(request, response);
                        } else if (type.equals("20")) {
                            searchLavoratori(request, response);
                        } else if (type.equals("21")) {
                            showCO(request, response);
                        } else if (type.equals("22")) {
                            showpoliticheLavoratore(request, response);
                        } else if (type.equals("23")) {
                            searchPrgFormativi(request, response);
                        } else if (type.equals("24")) {
                            searchPrgFormativiDt(request, response);
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
