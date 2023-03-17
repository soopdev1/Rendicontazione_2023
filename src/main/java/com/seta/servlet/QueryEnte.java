/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import com.seta.activity.Action;
import com.seta.entity.B3;
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
import com.seta.entity.Tutor;
import com.seta.entity.ViewPolitiche;
import com.seta.entity.Voucher;
import com.seta.util.Utility;
import static com.seta.util.Utility.correggi;
import static com.seta.util.Utility.redirect;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @author agodino
 */
public class QueryEnte extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger( QueryEnte.class.getName() );
    protected void searchTutor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        ArrayList<Tutor> list = Action.getTutorEnte((int) request.getSession().getAttribute("idente"));
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String disactive = "";
        String modifica = "";
        String updoc = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Tutor t : list) {
                String az = "<div class='btn-group'>";
                try {
                    if (sdf1.parse(t.getScadenzaDoc()).before(new Date())) {
                        az += "<a style='color: white;' class='btn btn-default bg-yellow-gold btn-outline btn btn-default popovers' data-toggle='dropdown' data-trigger='hover' data-placement='top' data-content='Documento Scaduto'><i class='fa fa-exclamation-circle'></i></a>";
                    } else {
                        az += "<a class='btn btn-default blue btn-default' href='javascript:;' data-toggle='dropdown'><i class='fa fa-wrench'></i></a>";
                    }
                    az += "<ul class='dropdown-menu'>";

                    valore = valore + " [ \"";
                    disactive = "<li><a data-toggle='modal' href='#disactivemodal' onclick='return setIDDisactive(" + t.getId() + ")' class='popovers' data-trigger='hover' data-placement='top' data-content='Elimina'><i class='btn btn-default bg-red fa fa-ban' style='padding-right:23px'></i>Elimina</a></li>";
                    updoc = "<li><a href='updateDocTutor.jsp?idtutor=" + t.getId() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Aggiorna Documenti'><i class='btn btn-default bg-green fa fa-arrow-circle-up' style='padding-right:23px'></i>Aggiorna Documenti</a></li>";
                    modifica = "<li><a href='modifyTutor.jsp?idtutor=" + t.getId() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica Agrafica'><i class='btn btn-default bg-blue-hoki fa fa-pencil font-white' style='padding-right:23px'></i>Modifica</a></li>";

                    az += modifica + updoc + disactive + "</ul>";

                    az += "</div>";

                    valore += az + "\",\""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + t.getRuolo_s() + "\",\""
                            + t.getEmail() + "\",\""
                            + t.getTelefono() + "\",\""
                            + sdf2.format(sdf1.parse(t.getScadenzaDoc())) + "\",\""
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

    protected void searchConvenzioni(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String bando = request.getParameter("bando");

//        ArrayList<Convenzione> list = Action.getConvenzioni((int) request.getSession().getAttribute("idente"), bando);
        ArrayList<Convenzione> list = Action.getConvenzioni((int) request.getSession().getAttribute("idente"));
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
//        String disactive = "";
        String modifica = "";
        String doc = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Convenzione t : list) {
//                String az = "<div class='btn-group'><a class='btn btn-default blue btn-outline' href='javascript:;' data-toggle='dropdown'>"
//                        + "<i class='fa fa-wrench'></i></a>"
//                        + "<ul class='dropdown-menu '>";
                try {
                    valore = valore + " [ \"";
                    //disactive = "<li><a data-toggle='modal' href='#disactivemodal' onclick='return setIDDisactive(" + t.getId() + ")' class='popovers' data-trigger='hover' data-placement='top' data-content='Elimina'><i class='btn btn-default bg-red fa fa-ban' style='padding-right:23px'></i>Elimina</a></li>";
                    modifica = "<a href='modifyConvenzione.jsp?idconvenzione=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-pencil-square-o fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>";
                    doc = "<a style='color: white;' class='bg-blue-dark fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento' ><font color='white' face='verdana'></font> </a> ";
//                    az += modifica + disactive + "</ul></div>";
                    valore += modifica + "\",\""
                            + Utility.correggi(t.getCodice()) + "\",\""
                            + sdf2.format(sdf1.parse(t.getInizio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getFine())) + "\",\""
                            + t.getD_bando() + "\",\""
                            + t.getD_politica() + "\",\""
                            + (t.getFile() == null ? "-" : doc) + "\"],";
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

    protected void searchPolitiche(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

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

        ArrayList<Politica> list = Action.getPolitiche_I((String) request.getSession().getAttribute("cf_ente"), politica, stato, nome, cognome, cf, from, to);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String modifica = "";
        String motivo = "";
        String doc = "";
        String sil = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Politica t : list) {
                try {
                    valore = valore + " [ \"";

                    if (t.getCodazioneformcal().equals("B05")) {
                        sil = t.getSil() + "\",\"";
                    }

                    if (t.getStato().equals("I")) {
                        if (t.getCodazioneformcal().equals("B03")) {//crea i bottoni per caricare i documetni diverso in misura 3
                            modifica = "<a  href='upDocContratto.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-upload fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica'> <font color='white' face='verdana'>Carica Doc.</font></a>" + "\",\"";
                        } else {
                            modifica = "<a  href='upDocRimborsoPolitiche.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-upload fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica'> <font color='white' face='verdana'>Carica Doc.</font></a>" + "\",\"";
                        }
                    } else if (t.getStato().equals("E")) {//tasti modifica per stato E diverso in misura 3(B03)
                        if (t.getCodazioneformcal().equals("B03")) {
                            modifica = "<a  href='upDocContratto.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default bg-red fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                            doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario'><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Modulo M5' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-blue fa fa-black-tie btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getContratto().getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Contratto' ><font color='white' face='verdana'></font> </a> ";
                        } else {
                            modifica = "<a  href='upDocRimborsoPolitiche.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default bg-red fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                            doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Modulo M5' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> " + "\",\"";
                        }
                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    } else if (t.getStato().equals("S")) {//check per le politiche in S
                        valore += "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                                + "<input type='checkbox' class='group-checkable' data-set='#sample_2 .checkboxes' id='" + t.getId() + "' />"//onclick='return seleriga(this.id);'
                                + "<span></span>"
                                + "</label>" + "\",\"";
                        if (t.getCodazioneformcal().equals("B03")) {//tasto modifica per stato S diverso me misura 3(B03) e altre misure 
                            modifica = "<a  href='upDocContratto.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-edit fancyBoxRafRef' data-trigger='hover' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                            doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario'><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Modulo M5' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-blue fa fa-black-tie btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getContratto().getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Contratto' ><font color='white' face='verdana'></font> </a> ";
                        } else {
                            modifica = "<a  href='upDocRimborsoPolitiche.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-pencil fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                            doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Modulo M5' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> " + "\",\"";
                        }
                        //valore += modifica + "\",\"";
                    } else if (t.getStato().equals("K")) {
                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    }
                    valore += modifica
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + motivo
                            + t.getCf() + "\",\""
                            + sil;

                    if (t.getCodazioneformcal().equals("B05")) {//ore per 1c(B05)
                        valore += t.getDurataeffettiva() + " h" + "\",\"";
                    }

                    valore += sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + doc;

//                    if (t.getStato().equals("S")) {//serve per la visualizzazione dei DOC
//                        if (t.getCodazioneformcal().equals("B03")) {//DOC misura 3
//                            doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario'><font color='white' face='verdana'></font> </a> "
//                                    + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Modulo M5' ><font color='white' face='verdana'></font> </a> "
//                                    + "<a style='color: white;' class='bg-blue fa fa-black-tie btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getContratto().getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Contratto' ><font color='white' face='verdana'></font> </a> ";
//                        } else {//doc misura 1b 1c
//                            doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
//                                    + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Modulo M5' ><font color='white' face='verdana'></font> </a> "
//                                    + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> " + "\",\"";
//                        }
//                    }
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

    protected void searchRimborsi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String protocollo = request.getParameter("protocollo");
        String from_up = request.getParameter("from_up");
        String to_up = request.getParameter("to_up");
        String from_mod = request.getParameter("from_mod");
        String to_mod = request.getParameter("to_mod");

        if (protocollo == null || protocollo.equals("null")) {
            protocollo = "";
        }
        if (from_up == null || from_up.equals("null")) {
            from_up = "";
        }
        if (to_up == null || to_up.equals("null")) {
            to_up = "";
        }
        if (from_mod == null || from_mod.equals("null")) {
            from_mod = "";
        }
        if (to_mod == null || to_mod.equals("null")) {
            to_mod = "";
        }

        ArrayList<Rimborso> list = Action.getListRimborsiEnte(politica, stato, (int) request.getSession().getAttribute("idente"), protocollo, from_up, to_up, from_mod, to_mod);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        String file = "";
        String check = "";
        String modifica = "";
        String decreto = "";

        if (list.size() > 0) {
            for (Rimborso t : list) {

                if ((t.getDocumento() == null || t.getDocumento().equals("null")) && !t.getStato().equals("S")) {
                    file = "";
                } else {
                    file = "<a style='color: white;' class='blue fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Domanda Rimborso' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDocumento(), "\\", "/") + "'></a>";
                }
                if (t.getCheckList() == null || t.getCheckList().equals("null")) {
                    check = "";
                } else {
                    check = "<a style='color: white;' class='green fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf popovers' data-trigger='hover' data-placement='top' data-content='Check List' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getCheckList(), "\\", "/") + "'></a>";
                }
                try {
                    valore = valore + " [ \"";

                    if (t.getStato().equals("P") || t.getStato().equals("S")) {
                        modifica = "<a  href='showPoliticheRimborso.jsp?id=" + t.getIdrimborso() + "&politica=" + t.getPolitica() + "&protocollo=" + t.getProtocollo() + "&stato=" + t.getStato() + "' class='popovers btn btn-default blue-madison fa fa-list-ul fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Singole Politiche'> <font color='white' face='verdana'>Singole Politiche</font></a>";
                        valore += modifica + "\",\"";
                    }

                    if (t.getStato().equals("P") || t.getStato().equals("K")) {
                        valore += correggi(t.getProtocollo()) + "\",\""
                                + Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    }

                    valore += sdf2.format(sdf1.parse(t.getData_up())) + "\",\"";

                    if (t.getStato().equals("P")) {
                        decreto = "<a style='color: white;' class='blue-dark fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Decreto' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getPath_decreto(), "\\", "/") + "'></a>";
                        valore += "&euro; " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato())) + "\",\"";
                    }

                    if (t.getStato().equals("P") || t.getStato().equals("K")) {
                        if (!file.equals("") || !check.equals("")) {
                            valore += file + check + decreto;
                        } else {
                            valore += "No DOC.";
                        }
                    } else if (t.getStato().equals("S")) {
                        modifica = "<a  href='OperazioniEnte?type=10&idrimborso=" + t.getIdrimborso() + "&politica=" + t.getPolitica() + "' class='popovers btn btn-default bg-blue fa fa-download' data-trigger='hover' data-placement='top' data-content='Scarica Elenco'> <font color='white' face='verdana'></font></a>";
                        valore += modifica;
                    }
                    valore += "\",\"" + "\"],";
                } catch (ParseException e) {
                    e.printStackTrace();
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

        String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");

        ArrayList<Politica> list = Action.getRimborsiPolitiche(politica, idrimborso);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Politica t : list) {

                try {
                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\"";
                    if (!request.getParameter("politica").equals("B03")) {
                        valore += t.getNomeTutor() + "\",\"";
                    }
                    if (politica.equals("B05")) {
                        valore += t.getDurataeffettiva() + " h" + "\",\"";
                    }
                    valore += sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
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

    protected void searchScartiPolitiche(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("politica");

        ArrayList<Politica> list = Action.getScartiPolitiche(idrimborso, politica);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Politica t : list) {
                try {
                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\"";

                    if (!request.getParameter("politica").equals("B03")) {
                        valore += t.getNomeTutor() + "\",\"";
                    }
                    if (politica.equals("B05")) {
                        valore += t.getDurataeffettiva() + " h" + "\",\"";
                    }

                    valore += Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\""
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

    protected void searchPrgFormativi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

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

        ArrayList<PrgFormativo> list = Action.getPrgFormativi((String) request.getSession().getAttribute("cf_ente"), politica, stato, nome, cognome, cf, from, to);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String modifica = "";
        String registro = "";
        String motivo = "";
        String doc = "";
        String tot_ore = "";
        String ore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (PrgFormativo t : list) {
                try {
                    Date scadenza = new Date();
                    try {
                        scadenza = sdf1.parse(t.getScadenza_doc());
                    } catch (ParseException | NullPointerException e) {
                    }
                    Date oggi = new Date();
                    valore = valore + " [ \"";
                    tot_ore = String.valueOf(t.getOre_tot()) + "\",\"";
                    ore = String.valueOf(t.getOre_effettuate()) + "\",\"";
                    if (t.getStato().equals("I")) {//tasto modifica 
                        modifica = "<a  href='upDocProgettoFormativo.jsp?idprg=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-upload fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica'> <font color='white' face='verdana'>Carica Doc.</font></a>" + "\",\"";
                        tot_ore = "";
                        ore = "";
                    } else if (t.getStato().equals("E")) {//tasti modifica per stato E
                        modifica = "<a  href='upDocProgettoFormativo.jsp?idprg=" + t.getId() + "' class='popovers btn btn-default bg-red fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                        doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Prg Formativo' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-green-soft fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_competenze(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Doc. Competenze' ><font color='white' face='verdana'></font> </a> " + "\",\"";
                    } else if (t.getStato().equals("S")) {//tasti modifica per stato S
                        modifica = "<a  href='upDocProgettoFormativo.jsp?idprg=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                        if (scadenza.before(oggi)) {
                            registro = "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Documento Ragazzo Scaduto'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else if (t.getFile() == null) {
                            registro = "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Caricare PDF Prg. Formativo più Convenzione Ente-Azienda'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else if (t.getConvenzione() == 0) {
                            registro = "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Associare Convenzione al Prg. Formativo'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else {
                            registro = "<a  href='upRegistro.jsp?idprg=" + t.getId() + "' class='popovers btn btn-default green-seagreen fa fa-upload fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica Registro'> <font color='white' face='verdana'></font></a>" + "\",\"";
                        }
                        doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Prg Formativo' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> " + "\",\"";
                    } else if (t.getStato().equals("C")) {//check per stato in C + modifica
                        if (scadenza.before(oggi)) {
                            valore += "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Documento Ragazzo Scaduto'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else if (t.getFile() == null) {
                            valore += "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Caricare PDF Prg. Formativo più Convenzione Ente-Azienda'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else if (t.getConvenzione() == 0) {
                            valore += "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Associare Convenzione al Prg. Formativo'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else if (t.getDoc_competenze() != null) {
                            valore += "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                                    + "<input type='checkbox' class='group-checkable' data-set='#sample_2 .checkboxes' id='" + t.getId() + "' />"//onclick='return seleriga(this.id);'
                                    + "<span></span>"
                                    + "</label>" + "\",\"";
                        } else {
                            valore += "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Attestato Delle Competenze Mancante'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        }
                        modifica = "<a  href='upDocProgettoFormativo.jsp?idprg=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-edit fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                        doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Prg Formativo' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-green-soft fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_competenze(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Doc. Competenze' ><font color='white' face='verdana'></font> </a> " + "\",\"";
                    } else if (t.getStato().equals("K")) {
                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    }

                    valore += modifica
                            + registro
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + motivo
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + tot_ore
                            + ore
                            + doc;

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

    protected void searchRimborsiPrg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String protocollo = request.getParameter("protocollo");
        String from_up = request.getParameter("from_up");
        String to_up = request.getParameter("to_up");
        String from_mod = request.getParameter("from_mod");
        String to_mod = request.getParameter("to_mod");

        if (protocollo == null || protocollo.equals("null")) {
            protocollo = "";
        }
        if (from_up == null || from_up.equals("null")) {
            from_up = "";
        }
        if (to_up == null || to_up.equals("null")) {
            to_up = "";
        }
        if (from_mod == null || from_mod.equals("null")) {
            from_mod = "";
        }
        if (to_mod == null || to_mod.equals("null")) {
            to_mod = "";
        }

        ArrayList<Rimborso_PrgFormativo> list = Action.getListRimborsiEntePrg(politica, stato, (int) request.getSession().getAttribute("idente"), protocollo, from_up, to_up, from_mod, to_mod);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        String file = "";
        String check = "";
        String down = "";
        String prot = "";
        String motivo = "";
        String showPrg = "";
        String erogato = "";
        String doc = "";
        String decreto = "";

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo t : list) {

                if ((t.getDocumento() == null || t.getDocumento().equals("null")) && !t.getStato().equals("S")) {
                    file = "";
                } else {
                    file = "<a style='color: white;' class='blue fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Domanda Rimborso' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDocumento(), "\\", "/") + "'></a>";
                }
                if (t.getCheckList() == null || t.getCheckList().equals("null")) {
                    check = "";
                } else {
                    check = "<a style='color: white;' class='green fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf popovers' data-trigger='hover' data-placement='top' data-content='Check List' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getCheckList(), "\\", "/") + "'></a>";
                }

                if (t.getStato().equals("S")) {
                    down = "<a  href='OperazioniEnte?type=14&idrimborso=" + t.getIdrimborso_prg() + "&politica=" + t.getPolitica() + "' class='popovers btn btn-default bg-blue fa fa-download' data-trigger='hover' data-placement='top' data-content='Scarica Elenco'> <font color='white' face='verdana'></font></a>" + "\",\"";
                    showPrg = "<a  href='showPrgRimborso.jsp?id=" + t.getIdrimborso_prg() + "&politica=" + t.getPolitica() + "&protocollo=" + t.getProtocollo() + "&stato=" + t.getStato() + "' class='popovers btn btn-default blue-madison fa fa-list-ul fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Singole Politiche'> <font color='white' face='verdana'>Singole Politiche</font></a>" + "\",\"";
                } else if (t.getStato().equals("P")) {
                    decreto = "<a style='color: white;' class='blue-dark fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Decreto' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getPath_decreto(), "\\", "/") + "'></a>";
                    showPrg = "<a  href='showPrgRimborso.jsp?id=" + t.getIdrimborso_prg() + "&politica=" + t.getPolitica() + "&protocollo=" + t.getProtocollo() + "&stato=" + t.getStato() + "' class='popovers btn btn-default blue-madison fa fa-list-ul fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Singole Politiche'> <font color='white' face='verdana'>Singole Politiche</font></a>" + "\",\"";
                    prot = correggi(t.getProtocollo()) + "\",\"";
                    motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    erogato = "&euro; " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato())) + "\",\"";
                    if (!file.equals("") || !check.equals("")) {
                        doc = file + check;
                    } else {
                        doc = "No DOC.";
                    }
                } else if (t.getStato().equals("K")) {
                    if (!file.equals("") || !check.equals("")) {
                        doc = file + check;
                    } else {
                        doc = "No DOC.";
                    }
                    prot = correggi(t.getProtocollo()) + "\",\"";
                    motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                }

                try {
                    valore += " [ \""
                            + showPrg
                            + prot
                            + motivo
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + erogato
                            + doc + decreto
                            + down
                            + "\"],";

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();

    }

    protected void searchPrgRimborsi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");

        ArrayList<PrgFormativo> list = Action.getRimborsiPrg(politica, idrimborso);

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
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getOre_tot() + "\",\""
                            + t.getOre_effettuate() + "\",\""
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

    protected void searchScartiPrg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idrimborso = request.getParameter("idrimborso");

        ArrayList<PrgFormativo> list = Action.getScartiPrg(idrimborso);

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
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\""
                            + t.getOre_tot() + "\",\""
                            + t.getOre_effettuate() + "\",\""
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

        //String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

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

        ArrayList<Registro> list = Action.getRegistriEnte((int) request.getSession().getAttribute("idente"), stato, nome, cognome, cf, from, to);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {

            String modifica = "";
            String doc = "";
            String erogato = "";
            String ore = "";

            for (Registro t : list) {

                if (t.getStato().equals("E")) {
                    modifica = "<a  href='modifyRegistro.jsp?idprg=" + t.getProgetto_formativo() + "&idrg=" + t.getId() + "' class='popovers btn btn-default bg-red fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                    doc = "<a style='color: white;' class='bg-yellow fa fa-calendar btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Registro' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_quietanza(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Quietanza Liquidazione' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-olive fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_lavoratore(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-grey-gallery fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "\",\"";
                    ore = t.getOre() + " h" + "\",\"";
                } else if ((t.getStato().equals("K"))) {
                    if (t.getChecklist() != null) {
                        doc = "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getChecklist(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Ckecklist' ><font color='white' face='verdana'></font> </a> "
                                + "\",\"";
                    } else {
                        doc = "<strong>No Doc.</strong>" + "\",\"";
                    }
                    ore = t.getOre() + " h" + "\",\"";
                } else if ((t.getStato().equals("P"))) {
                    erogato = "&euro; " + String.format("%1$,.2f", t.getTot_erogato()) + "\",\"";
                    if (t.getChecklist() != null) {
                        doc = "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getChecklist(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Ckecklist' ><font color='white' face='verdana'></font> </a> "
                                + "\",\"";
                    } else {
                        doc = "<strong>No Doc.</strong>" + "\",\"";
                    }

                    ore = t.getOre_rev() + " h" + "\",\"";
                }

                try {
                    valore = valore + " [ \""
                            + modifica
                            + t.getLavoratore() + "\",\""
                            + t.getCf_lavoratore() + "\",\""
                            + t.getTutor() + "\",\""
                            + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + ore
                            + Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\""
                            + erogato
                            + sdf2.format(sdf1.parse(t.getDataup())) + "\",\""
                            + doc
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

    protected void searchRimborsiPoliticheDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");

        ArrayList<Politica> list = Action.getRimborsiPoliticheDt(politica, idrimborso);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Politica t : list) {

                try {
                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\"";
                    if (politica.equals("B1") || politica.equals("C1")) {
                        valore += t.getNomeTutor() + "\",\"";
                    }
                    if (politica.equals("B1") || politica.equals("C1")) {
                        valore += t.getDurataeffettiva() + " h" + "\",\"";
                    }
                    valore += sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
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

    protected void searchPrgFormativiDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        //String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String politica = request.getParameter("politica");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

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
        if (politica == null || politica.equals("null")) {
            politica = "";
        }

        ArrayList<PrgFormativoDt> list = Action.getPrgFormativiDt((String) request.getSession().getAttribute("cf_ente"), stato, nome, cognome, cf, from, to, politica);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String modifica = "";
        String registro = "";
        String motivo = "";
        String doc = "";
        String tot_ore = "";
        String ore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (PrgFormativoDt t : list) {
                try {
                    Date scadenza = new Date();
                    try {
                        scadenza = sdf1.parse(t.getScadenza_doc());
                    } catch (ParseException | NullPointerException e) {
                    }
                    Date oggi = new Date();
                    valore = valore + " [ \"";
                    tot_ore = String.valueOf(t.getOre_tot()) + "\",\"";
                    ore = String.valueOf(t.getOre_effettuate()) + "\",\"";
                    if (t.getStato().equals("I")) {//tasto modifica 
                        modifica = "<a  href='upDocProgettoFormativo_Dt.jsp?idprg=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-upload fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica'> <font color='white' face='verdana'>Carica Doc.</font></a>" + "\",\"";
                        tot_ore = "";
                        ore = "";
                    } else if (t.getStato().equals("E")) {//tasti modifica per stato E
                        modifica = "<a  href='upDocProgettoFormativo_Dt.jsp?idprg=" + t.getId() + "' class='popovers btn btn-default bg-red fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                        doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Prg Formativo' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-grey-mint fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-green-soft fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_competenze(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Doc. Competenze' ><font color='white' face='verdana'></font> </a> " + "\",\"";
                    } else if (t.getStato().equals("S")) {//tasti modifica per stato S
                        modifica = "<a  href='upDocProgettoFormativo_Dt.jsp?idprg=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                        if (scadenza.before(oggi)) {
                            registro = "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Documento Ragazzo Scaduto'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else if (t.getFile() == null) {
                            registro = "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Caricare PDF Prg. Formativo più Convenzione Ente-Azienda'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else if (t.getConvenzione() == 0) {
                            registro = "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Associare Convenzione al Prg. Formativo'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else {
                            registro = "<a href='upRegistroDt.jsp?idprg=" + t.getId() + "' "
                                    + "class='popovers btn btn-default green-seagreen fa fa-upload fancyBoxRafRef' "
                                    + "data-trigger='hover' data-placement='top' data-content='Carica Registro'>"
                                    + "<font color='white' face='verdana'></font></a>" + "\",\"";
                        }
                        doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Prg Formativo' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-grey-mint fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> " + "\",\"";
                    } else if (t.getStato().equals("C")) {//check per stato in C + modifica
                        if (scadenza.before(oggi)) {
                            valore += "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Documento Ragazzo Scaduto'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else if (t.getFile() == null) {
                            valore += "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Caricare PDF Prg. Formativo più Convenzione Ente-Azienda'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else if (t.getConvenzione() == 0) {
                            valore += "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Associare Convenzione al Prg. Formativo'><font color='white' face='verdana'></font> </a>" + "\",\"";
                        } else if (t.getDoc_competenze() != null) {
                            valore += "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                                    + "<input type='checkbox' class='group-checkable' data-set='#sample_2 .checkboxes' id='" + t.getId() + "' />"//onclick='return seleriga(this.id);'
                                    + "<span></span>"
                                    + "</label>" + "\",\"";
                        } else {
                            valore += "<a style='color: white;cursor: default;' class='bg-red-thunderbird fa fa-exclamation-triangle btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Attestato Delle Competenze Mancante' ><font color='white' face='verdana'></font> </a>" + "\",\"";
                        }
                        modifica = "<a  href='upDocProgettoFormativo_Dt.jsp?idprg=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-edit fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                        doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Prg Formativo' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-grey-mint fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-green-soft fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_competenze(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Doc. Competenze' ><font color='white' face='verdana'></font> </a> " + "\",\"";
                    } else if (t.getStato().equals("K")) {
                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    }

                    valore += modifica
                            + registro
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + motivo
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getCodazioneformcal() + "\",\""
                            + tot_ore
                            + ore
                            + doc;

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

    protected void searchRimborsiPrgDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String protocollo = request.getParameter("protocollo");
        String from_up = request.getParameter("from_up");
        String to_up = request.getParameter("to_up");
        String from_mod = request.getParameter("from_mod");
        String to_mod = request.getParameter("to_mod");

        if (protocollo == null || protocollo.equals("null")) {
            protocollo = "";
        }
        if (from_up == null || from_up.equals("null")) {
            from_up = "";
        }
        if (to_up == null || to_up.equals("null")) {
            to_up = "";
        }
        if (from_mod == null || from_mod.equals("null")) {
            from_mod = "";
        }
//        if (politica == null || politica.equals("null")) {
//            politica = "";
//        }

        ArrayList<Rimborso_PrgFormativo_Dt> list = Action.getListRimborsiEntePrgDt(politica, stato, (int) request.getSession().getAttribute("idente"), protocollo, from_up, to_up, from_mod, to_mod);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        String file = "";
        String check = "";
        String down = "";
        String prot = "";
        String motivo = "";
        String showPrg = "";
        String erogato = "";
        String doc = "";
        String decreto = "";

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo_Dt t : list) {

                if ((t.getDocumento() == null || t.getDocumento().equals("null")) && !t.getStato().equals("S")) {
                    file = "";
                } else {
                    file = "<a style='color: white;' class='blue fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Domanda Rimborso' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDocumento(), "\\", "/") + "'></a>";
                }
                if (t.getCheckList() == null || t.getCheckList().equals("null")) {
                    check = "";
                } else {
                    check = "<a style='color: white;' class='green fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf popovers' data-trigger='hover' data-placement='top' data-content='Check List' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getCheckList(), "\\", "/") + "'></a>";
                }

                if (t.getStato().equals("S")) {
                    down = "<a  href='OperazioniEnte?type=18&idrimborso=" + t.getIdrimborso_prg_dt() + "&politica=" + t.getPolitica() + "' class='popovers btn btn-default bg-blue fa fa-download' data-trigger='hover' data-placement='top' data-content='Scarica Elenco'> <font color='white' face='verdana'></font></a>" + "\",\"";
                    showPrg = "<a  href='showPrgRimborsoDt.jsp?id=" + t.getIdrimborso_prg_dt() + "&stato=" + t.getStato() + "' class='popovers btn btn-default blue-madison fa fa-list-ul fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Singole Politiche'> <font color='white' face='verdana'>Singole Politiche</font></a>" + "\",\"";
                } else if (t.getStato().equals("P")) {
                    decreto = "<a style='color: white;' class='blue-dark fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Decreto' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getPath_decreto(), "\\", "/") + "'></a>";
                    showPrg = "<a  href='showPrgRimborsoDt.jsp?id=" + t.getIdrimborso_prg_dt() + "&protocollo=" + t.getProtocollo() + "&stato=" + t.getStato() + "' class='popovers btn btn-default blue-madison fa fa-list-ul fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Singole Politiche'> <font color='white' face='verdana'>Singole Politiche</font></a>" + "\",\"";
                    prot = correggi(t.getProtocollo()) + "\",\"";
                    motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    erogato = "&euro; " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato())) + "\",\"";
                    if (!file.equals("") || !check.equals("")) {
                        doc = file + check;
                    } else {
                        doc = "No DOC.";
                    }
                } else if (t.getStato().equals("K")) {
                    if (!file.equals("") || !check.equals("")) {
                        doc = file + check;
                    } else {
                        doc = "No DOC.";
                    }
                    prot = correggi(t.getProtocollo()) + "\",\"";
                    motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                }

                try {
                    valore += " [ \""
                            + showPrg
                            + prot
                            + motivo
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + erogato
                            + doc + decreto
                            + down
                            + "\"],";

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();

    }

    protected void searchPrgRimborsiDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        //String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");

        ArrayList<PrgFormativoDt> list = Action.getRimborsiPrgDt(idrimborso);

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
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getProfiling() + "\",\""
                            + t.getOre_tot() + "\",\""
                            + t.getOre_effettuate() + "\",\""
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

    protected void searchScartiPrgDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idrimborso = request.getParameter("idrimborso");

        ArrayList<PrgFormativoDt> list = Action.getScartiPrgDt(idrimborso);

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
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\""
                            + t.getOre_tot() + "\",\""
                            + t.getOre_effettuate() + "\",\""
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

    protected void searchRegistriDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        //String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

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

        ArrayList<RegistroDt> list = Action.getRegistriEnteDt((int) request.getSession().getAttribute("idente"), stato, nome, cognome, cf, from, to);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {

            String modifica = "";
            String doc = "";
            String erogato = "";
            String ore = "";

            for (RegistroDt t : list) {

                if (t.getStato().equals("E")) {
                    modifica = "<a  href='modifyRegistroDt.jsp?idprg=" + t.getProgetto_formativo_dt() + "&idrg=" + t.getId() + "' class='popovers btn btn-default bg-red fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                    doc = "<a style='color: white;' class='bg-yellow fa fa-calendar btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Registro' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-olive fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_lavoratore(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-grey-gallery fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "\",\"";
                    ore = t.getOre() + " h" + "\",\"";
                } else if ((t.getStato().equals("K"))) {
                    if (t.getChecklist() != null) {
                        doc = "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getChecklist(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Ckecklist' ><font color='white' face='verdana'></font> </a> "
                                + "\",\"";
                    } else {
                        doc = "<strong>No Doc.</strong>" + "\",\"";
                    }
                    ore = t.getOre() + " h" + "\",\"";
                } else if ((t.getStato().equals("P"))) {
                    erogato = "&euro; " + String.format("%1$,.2f", t.getTot_erogato()) + "\",\"";
                    if (t.getChecklist() != null) {
                        doc = "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getChecklist(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Ckecklist' ><font color='white' face='verdana'></font> </a> "
                                + "\",\"";
                    } else {
                        doc = "<strong>No Doc.</strong>" + "\",\"";
                    }
                    ore = t.getOre_rev() + " h" + "\",\"";
                }

                try {
                    valore = valore + " [ \""
                            + modifica
                            + t.getLavoratore() + "\",\""
                            + t.getCf_lavoratore() + "\",\""
                            + t.getTutor() + "\",\""
                            + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + ore
                            + Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\""
                            + erogato
                            + sdf2.format(sdf1.parse(t.getDataup())) + "\",\""
                            + doc
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

    protected void searchPoliticheDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

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

        ArrayList<Politica> list = Action.getPolitiche_DT((String) request.getSession().getAttribute("cf_ente"), politica, stato, nome, cognome, cf, from, to);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String modifica = "";
        String motivo = "";
        String doc = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Politica t : list) {
                try {
                    valore = valore + " [ \"";
                    if (t.getStato().equals("I")) {
                        if (t.getCodazioneformcal().equals("D2") || t.getCodazioneformcal().equals("D5")) {//crea i bottoni per caricare i documetni diverso in misura 3
                            modifica = "<a  href='upDocContratto_dt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-upload fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica'> <font color='white' face='verdana'>Carica Doc.</font></a>" + "\",\"";
                        } else {
                            modifica = "<a  href='upDocRimborsoPoliticheDt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-upload fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica'> <font color='white' face='verdana'>Carica Doc.</font></a>" + "\",\"";
                        }
                    } else if (t.getStato().equals("E")) {//tasti modifica per stato E diverso in misura 3(B03)
                        if (t.getCodazioneformcal().equals("D2") || t.getCodazioneformcal().equals("D5")) {
                            modifica = "<a  href='upDocContratto_dt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default bg-red fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                            doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario'><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-blue fa fa-black-tie btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getContratto().getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Contratto' ><font color='white' face='verdana'></font> </a> ";
                        } else {
                            modifica = "<a  href='upDocRimborsoPoliticheDt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default bg-red fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                            doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> " + "\",\"";
                        }
                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    } else if (t.getStato().equals("S")) {//check per le politiche in S
                        valore += "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                                + "<input type='checkbox' class='group-checkable' data-set='#sample_2 .checkboxes' id='" + t.getId() + "' />"//onclick='return seleriga(this.id);'
                                + "<span></span>"
                                + "</label>" + "\",\"";
                        if (t.getCodazioneformcal().equals("D2") || t.getCodazioneformcal().equals("D5")) {//tasto modifica per stato S diverso me misura 3(B03) e altre misure 
                            modifica = "<a  href='upDocContratto_dt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-edit fancyBoxRafRef' data-trigger='hover' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                            doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario'><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-blue fa fa-black-tie btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getContratto().getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Contratto' ><font color='white' face='verdana'></font> </a> ";
                        } else {
                            modifica = "<a  href='upDocRimborsoPoliticheDt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-pencil fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                            doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                                    + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> " + "\",\"";
                        }
                        //valore += modifica + "\",\"";
                    } else if (t.getStato().equals("K")) {
                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    }
                    valore += modifica
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + motivo
                            + t.getCf() + "\",\"";
                    if (t.getCodazioneformcal().equals("B1") || t.getCodazioneformcal().equals("C1")) {//ore per 1c(B05)
                        valore += t.getDurataeffettiva() + " h" + "\",\"";
                    }
                    valore += sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + doc
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

    protected void searchRimborsiDT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String protocollo = request.getParameter("protocollo");
        String from_up = request.getParameter("from_up");
        String to_up = request.getParameter("to_up");
        String from_mod = request.getParameter("from_mod");
        String to_mod = request.getParameter("to_mod");

        if (protocollo == null || protocollo.equals("null")) {
            protocollo = "";
        }
        if (from_up == null || from_up.equals("null")) {
            from_up = "";
        }
        if (to_up == null || to_up.equals("null")) {
            to_up = "";
        }
        if (from_mod == null || from_mod.equals("null")) {
            from_mod = "";
        }
        if (to_mod == null || to_mod.equals("null")) {
            to_mod = "";
        }

        ArrayList<Rimborso> list = Action.getListRimborsiEnteDt(politica, stato, (int) request.getSession().getAttribute("idente"), protocollo, from_up, to_up, from_mod, to_mod);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        String file = "";
        String check = "";
        String modifica = "";
        String decreto = "";

        if (list.size() > 0) {
            for (Rimborso t : list) {

                if ((t.getDocumento() == null || t.getDocumento().equals("null")) && !t.getStato().equals("S")) {
                    file = "";
                } else {
                    file = "<a style='color: white;' class='blue fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Domanda Rimborso' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDocumento(), "\\", "/") + "'></a>";
                }
                if (t.getCheckList() == null || t.getCheckList().equals("null")) {
                    check = "";
                } else {
                    check = "<a style='color: white;' class='green fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf popovers' data-trigger='hover' data-placement='top' data-content='Check List' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getCheckList(), "\\", "/") + "'></a>";
                }
                try {
                    valore = valore + " [ \"";

                    if (t.getStato().equals("P") || t.getStato().equals("S")) {
                        modifica = "<a  href='showPoliticheRimborsoDt.jsp?id=" + t.getIdrimborso() + "&politica=" + t.getPolitica() + "&protocollo=" + t.getProtocollo() + "&stato=" + t.getStato() + "' class='popovers btn btn-default blue-madison fa fa-list-ul fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Singole Politiche'> <font color='white' face='verdana'>Singole Politiche</font></a>";
                        valore += modifica + "\",\"";
                    }

                    if (t.getStato().equals("P") || t.getStato().equals("K")) {
                        valore += correggi(t.getProtocollo()) + "\",\""
                                + Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    }

                    valore += sdf2.format(sdf1.parse(t.getData_up())) + "\",\"";

                    if (t.getStato().equals("P")) {
                        decreto = "<a style='color: white;' class='blue-dark fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Decreto' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getPath_decreto(), "\\", "/") + "'></a>";
                        valore += "&euro; " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato())) + "\",\"";
                    }

                    if (t.getStato().equals("P") || t.getStato().equals("K")) {
                        if (!file.equals("") || !check.equals("")) {
                            valore += file + check + decreto;
                        } else {
                            valore += "No DOC.";
                        }
                    } else if (t.getStato().equals("S")) {
                        modifica = "<a  href='OperazioniEnte?type=23&idrimborso=" + t.getIdrimborso() + "&politica=" + t.getPolitica() + "' class='popovers btn btn-default bg-blue fa fa-download' data-trigger='hover' data-placement='top' data-content='Scarica Elenco'> <font color='white' face='verdana'></font></a>";
                        valore += modifica;
                    }
                    valore += "\",\"" + "\"],";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();

    }

    protected void searchScartiPoliticheDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("politica");

        ArrayList<Politica> list = Action.getScartiPoliticheDt(idrimborso, politica);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Politica t : list) {
                try {
                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\"";
                    if (politica.equals("B1") || politica.equals("C1")) {
                        valore += t.getNomeTutor() + "\",\"";
                    }
                    if (politica.equals("B1") || politica.equals("C1")) {
                        valore += t.getDurataeffettiva() + " h" + "\",\"";
                    }
                    valore += Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\""
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

    protected void searchPoliticaB3Dt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

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

        ArrayList<B3> list = Action.getPoliticaB3_DT((String) request.getSession().getAttribute("cf_ente"), politica, stato, nome, cognome, cf, from, to);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String modifica = "";
        String motivo = "";
        String doc = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (B3 t : list) {
                try {
                    valore = valore + " [ \"";
                    if (t.getStato().equals("I")) {
                        modifica = "<a  href='upDocB3_dt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-upload fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica'> <font color='white' face='verdana'>Carica Doc.</font></a>" + "\",\"";
                    } else if (t.getStato().equals("E")) {//tasti modifica per stato E diverso in misura 3(B03)
                        modifica = "<a  href='upDocB3_dt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default bg-red fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                        doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario'><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-yellow fa fa-calendar btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_registro(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Registro' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_businessplan(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Businessplan' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_timesheet(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Timesheet' ><font color='white' face='verdana'></font> </a> ";

                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    } else if (t.getStato().equals("S")) {//check per le politiche in S
                        valore += "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                                + "<input type='checkbox' class='group-checkable' data-set='#sample_2 .checkboxes' id='" + t.getId() + "' />"//onclick='return seleriga(this.id);'
                                + "<span></span>"
                                + "</label>" + "\",\"";

                        modifica = "<a  href='upDocB3_dt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-edit fancyBoxRafRef' data-trigger='hover' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                        doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario'><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-yellow fa fa-calendar btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_registro(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Registro' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_businessplan(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Businessplan' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_timesheet(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Timesheet' ><font color='white' face='verdana'></font> </a> ";

                        //valore += modifica + "\",\"";
                    } else if (t.getStato().equals("K")) {
                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    }
                    valore += modifica
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + motivo
                            + t.getCf() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + doc
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

    protected void searchRimborsiB3DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String protocollo = request.getParameter("protocollo");
        String from_up = request.getParameter("from_up");
        String to_up = request.getParameter("to_up");
        String from_mod = request.getParameter("from_mod");
        String to_mod = request.getParameter("to_mod");

        if (protocollo == null || protocollo.equals("null")) {
            protocollo = "";
        }
        if (from_up == null || from_up.equals("null")) {
            from_up = "";
        }
        if (to_up == null || to_up.equals("null")) {
            to_up = "";
        }
        if (from_mod == null || from_mod.equals("null")) {
            from_mod = "";
        }
        if (to_mod == null || to_mod.equals("null")) {
            to_mod = "";
        }

        ArrayList<Rimborso> list = Action.getListRimborsiEnteB3Dt(politica, stato, (int) request.getSession().getAttribute("idente"), protocollo, from_up, to_up, from_mod, to_mod);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        String file = "";
        String check = "";
        String modifica = "";
        String decreto = "";

        if (list.size() > 0) {
            for (Rimborso t : list) {

                if ((t.getDocumento() == null || t.getDocumento().equals("null")) && !t.getStato().equals("S")) {
                    file = "";
                } else {
                    file = "<a style='color: white;' class='blue fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Domanda Rimborso' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDocumento(), "\\", "/") + "'></a>";
                }
                if (t.getCheckList() == null || t.getCheckList().equals("null")) {
                    check = "";
                } else {
                    check = "<a style='color: white;' class='green fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf popovers' data-trigger='hover' data-placement='top' data-content='Check List' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getCheckList(), "\\", "/") + "'></a>";
                }
                try {
                    valore = valore + " [ \"";

                    if (t.getStato().equals("P") || t.getStato().equals("S")) {
                        modifica = "<a  href='showPoliticheRimborsoB3Dt.jsp?id=" + t.getIdrimborso() + "&politica=" + t.getPolitica() + "&protocollo=" + correggi(t.getProtocollo()) + "&stato=" + t.getStato() + "' class='popovers btn btn-default blue-madison fa fa-list-ul fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Singole Politiche'> <font color='white' face='verdana'>Singole Politiche</font></a>";
                        valore += modifica + "\",\"";
                    }

                    if (t.getStato().equals("P") || t.getStato().equals("K")) {
                        valore += correggi(t.getProtocollo()) + "\",\""
                                + Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    }

                    valore += sdf2.format(sdf1.parse(t.getData_up())) + "\",\"";

                    if (t.getStato().equals("P")) {
                        decreto = "<a style='color: white;' class='blue-dark fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Decreto' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getPath_decreto(), "\\", "/") + "'></a>";
                        valore += "&euro; " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato())) + "\",\"";
                    }

                    if (t.getStato().equals("P") || t.getStato().equals("K")) {
                        if (!file.equals("") || !check.equals("")) {
                            valore += file + check + decreto;
                        } else {
                            valore += "No DOC.";
                        }
                    } else if (t.getStato().equals("S")) {
                        modifica = "<a  href='OperazioniEnte?type=27&idrimborso=" + t.getIdrimborso() + "&politica=" + t.getPolitica() + "' class='popovers btn btn-default bg-blue fa fa-download' data-trigger='hover' data-placement='top' data-content='Scarica Elenco'> <font color='white' face='verdana'></font></a>";
                        valore += modifica;
                    }
                    valore += "\",\"" + "\"],";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();

    }

    protected void searchRimborsiPoliticheB3Dt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");

        ArrayList<B3> list = Action.getRimborsiPoliticheB3Dt(politica, idrimborso);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (B3 t : list) {

                try {
                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
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

    protected void searchScartiB3Dt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("politica");

        ArrayList<B3> list = Action.getScartiB3Dt(idrimborso, politica);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (B3 t : list) {
                try {
                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\""
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

    protected void searchVoucherDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

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

        ArrayList<Voucher> list = Action.getVoucher_DT((String) request.getSession().getAttribute("cf_ente"), politica, stato, nome, cognome, cf, from, to);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String modifica = "";
        String motivo = "";
        String doc = "";
        String ore = "";
        String voucher = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Voucher t : list) {
                try {
                    valore = valore + " [ \"";
                    if (t.getStato().equals("I")) {
                        modifica = "<a  href='upDocVoucher_dt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-upload fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica'> <font color='white' face='verdana'>Carica Doc.</font></a>" + "\",\"";
                    } else if (t.getStato().equals("E")) {//tasti modifica per stato E diverso in misura 3(B03)
                        modifica = "<a  href='upDocVoucher_dt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default bg-red fa fa-exclamation-triangle fancyBoxRafRef' data-trigger='hover' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                        doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario'><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-yellow fa fa-calendar btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_registro(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Registro' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_allegato(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-yellow-casablanca fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_attestato(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Attestato Di Formazione' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_delega(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Delega' ><font color='white' face='verdana'></font> </a> ";

                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                        voucher = String.format("%1$,.2f", t.getVoucher()) + "\",\"";
                        ore = t.getOre() + "\",\"";
                    } else if (t.getStato().equals("S")) {//check per le politiche in S
                        valore += "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                                + "<input type='checkbox' class='group-checkable' data-set='#sample_2 .checkboxes' id='" + t.getId() + "' />"//onclick='return seleriga(this.id);'
                                + "<span></span>"
                                + "</label>" + "\",\"";

                        modifica = "<a  href='upDocVoucher_dt.jsp?idpolitica=" + t.getId() + "' class='popovers btn btn-default blue-madison fa fa-edit fancyBoxRafRef' data-trigger='hover' data-trigger='hover' data-placement='top' data-content='Modifica'> <font color='white' face='verdana'>Modifica</font></a>" + "\",\"";
                        doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario'><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-yellow fa fa-calendar btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_registro(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Registro' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-yellow-casablanca fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_allegato(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_attestato(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Attestato Di Formazione' ><font color='white' face='verdana'></font> </a> "
                                + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_delega(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Delega' ><font color='white' face='verdana'></font> </a> ";

                        //valore += modifica + "\",\"";
                        voucher = String.format("%1$,.2f", t.getVoucher()) + "\",\"";
                        ore = t.getOre() + "\",\"";
                    } else if (t.getStato().equals("K")) {
                        motivo = Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                        voucher = String.format("%1$,.2f", t.getVoucher()) + "\",\"";
                        ore = t.getOre() + "\",\"";
                    }
                    valore += modifica
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + motivo
                            + t.getCf() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + ore
                            + voucher
                            + doc
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

    protected void searchRimborsiVoucherDT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String protocollo = request.getParameter("protocollo");
        String from_up = request.getParameter("from_up");
        String to_up = request.getParameter("to_up");
        String from_mod = request.getParameter("from_mod");
        String to_mod = request.getParameter("to_mod");

        if (protocollo == null || protocollo.equals("null")) {
            protocollo = "";
        }
        if (from_up == null || from_up.equals("null")) {
            from_up = "";
        }
        if (to_up == null || to_up.equals("null")) {
            to_up = "";
        }
        if (from_mod == null || from_mod.equals("null")) {
            from_mod = "";
        }
        if (to_mod == null || to_mod.equals("null")) {
            to_mod = "";
        }

        ArrayList<Rimborso> list = Action.getListRimborsiEnteVoucherDt(politica, stato, (int) request.getSession().getAttribute("idente"), protocollo, from_up, to_up, from_mod, to_mod);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        String file = "";
        String check = "";
        String modifica = "";
        String decreto = "";

        if (list.size() > 0) {
            for (Rimborso t : list) {

                if ((t.getDocumento() == null || t.getDocumento().equals("null")) && !t.getStato().equals("S")) {
                    file = "";
                } else {
                    file = "<a style='color: white;' class='blue fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Domanda Rimborso' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDocumento(), "\\", "/") + "'></a>";
                }
                if (t.getCheckList() == null || t.getCheckList().equals("null")) {
                    check = "";
                } else {
                    check = "<a style='color: white;' class='green fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf popovers' data-trigger='hover' data-placement='top' data-content='Check List' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getCheckList(), "\\", "/") + "'></a>";
                }
                try {
                    valore = valore + " [ \"";

                    if (t.getStato().equals("P") || t.getStato().equals("S")) {
                        modifica = "<a  href='showVoucherRimborsoDt.jsp?id=" + t.getIdrimborso() + "&politica=" + t.getPolitica() + "&protocollo=" + t.getProtocollo() + "&stato=" + t.getStato() + "' class='popovers btn btn-default blue-madison fa fa-list-ul fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Singole Politiche'> <font color='white' face='verdana'>Singole Politiche</font></a>";
                        valore += modifica + "\",\"";
                    }

                    if (t.getStato().equals("P") || t.getStato().equals("K")) {
                        valore += correggi(t.getProtocollo()) + "\",\""
                                + Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\"";
                    }

                    valore += sdf2.format(sdf1.parse(t.getData_up())) + "\",\"";

                    if (t.getStato().equals("P")) {
                        decreto = "<a style='color: white;' class='blue-dark fa fa-file-pdf-o btn btn-lg popovers fancyBoxRaf' data-trigger='hover' data-placement='top' data-content='Decreto' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getPath_decreto(), "\\", "/") + "'></a>";
                        valore += "&euro; " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato())) + "\",\"";
                    }

                    if (t.getStato().equals("P") || t.getStato().equals("K")) {
                        if (!file.equals("") || !check.equals("")) {
                            valore += file + check + decreto;
                        } else {
                            valore += "No DOC.";
                        }
                    } else if (t.getStato().equals("S")) {
                        modifica = "<a  href='OperazioniEnte?type=30&idrimborso=" + t.getIdrimborso() + "&politica=" + t.getPolitica() + "' class='popovers btn btn-default bg-blue fa fa-download' data-trigger='hover' data-placement='top' data-content='Scarica Elenco'> <font color='white' face='verdana'></font></a>";
                        valore += modifica;
                    }
                    valore += "\",\"" + "\"],";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();

    }

    protected void searchRimborsiVoucherDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String idrimborso = request.getParameter("idrimborso");

        ArrayList<Voucher> list = Action.getRimborsiVoucherDt(politica, idrimborso);

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
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
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

    protected void searchScartiVoucherDt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idrimborso = request.getParameter("idrimborso");
        String politica = request.getParameter("politica");

        ArrayList<Voucher> list = Action.getScartiVoucherDt(idrimborso, politica);

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
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + Utility.correggi(Utility.CaratteriSpeciali(t.getMotivo())) + "\",\""
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

    protected void getAnomaliePolitiche(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        ArrayList<String[]> list = Action.getAnomaliePolitiche((String) request.getSession().getAttribute("cf_ente"));
        HashMap<String, String> map = Action.getDecodificaPolitiche();
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String check = "";

        if (list.size() > 0) {
            for (String[] t : list) {
                try {
                    valore = valore + " [ \""
                            + t[0] + "\",\""
                            + ((map.get(t[1]) != null) ? map.get(t[1]) : t[1]) + "\",\""
                            + t[4] + "\",\""
                            + t[2] + "\",\""
                            + new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(t[5])) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                }

                check += t[3] + ",";
            }
            check = check.substring(0, check.lastIndexOf(','));
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            Action.updateAnomalie(check);
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void searchPrgFormativiClosed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

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

        ArrayList<PrgFormativo> list = Action.getPrgFormativiClosed((String) request.getSession().getAttribute("cf_ente"), politica, stato, nome, cognome, cf, from, to);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String doc = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (PrgFormativo t : list) {
                try {

                    doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Prg Formativo' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-soft fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_competenze(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Doc. Competenze' ><font color='white' face='verdana'></font> </a> " + "\",\"";

                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + String.valueOf(t.getOre_tot()) + "\",\""
                            + String.valueOf(t.getOre_effettuate()) + "\",\""
                            + doc;

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

    protected void searchPrgFormativiClosed_dt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String politica = request.getParameter("politica");
        String stato = request.getParameter("stato");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cf = request.getParameter("cf");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

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

        ArrayList<PrgFormativoDt> list = Action.getPrgFormativiDtClosed((String) request.getSession().getAttribute("cf_ente"), politica, stato, nome, cognome, cf, from, to);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String doc = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (PrgFormativoDt t : list) {
                try {

                    doc = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_ragazzo(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Prg Formativo' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-grey-mint fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_m5(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-soft fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_competenze(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Doc. Competenze' ><font color='white' face='verdana'></font> </a>" + "\",\"";

                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDataavvio())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + String.valueOf(t.getOre_tot()) + "\",\""
                            + String.valueOf(t.getOre_effettuate()) + "\",\""
                            + doc;

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
        ArrayList<Lavoratore> list = Action.getLavoratori(nome, cognome, cf, (String) request.getSession().getAttribute("cf_ente"));
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String politiche = "";
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Lavoratore t : list) {

                String az = "<div class='btn-group'><a class='btn btn-default blue btn-outline' href='javascript:;' data-toggle='dropdown'>"
                        + "<i class='fa fa-wrench'></i></a>"
                        + "<ul class='dropdown-menu '>";
                politiche = "<li><a  href='show_politichelavoratore_ente.jsp?idlav=" + t.getCdnlavoratore() + "' class='fancyBoxRaf' ><i class='btn btn-default bg-yellow-gold fa fa-list-ul' style='padding-right:23px; color:white;'></i>Visualizza Politiche</a></li>";
                az += politiche + "</ul></div>";

                valore = valore + " [ \"";
                valore += az + "\",\""
                        + Utility.correggi(t.getCognome()) + "\",\""
                        + Utility.correggi(t.getNome()) + "\",\""
                        + t.getCodice_fiscale() + "\",\""
                        + sdf2.format(t.getNascita_data()) + "\",\""
                        + (t.getRecapito_telefonico() == null ? "-" : t.getRecapito_telefonico()) + "\",\""
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

    protected void showpoliticheLavoratore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String idlav = request.getParameter("idlav");
        ArrayList<ViewPolitiche> list = Action.getPoliticheLavoratore(idlav, (String) request.getSession().getAttribute("cf_ente"));
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
                } else if (c.getStato().equals("C")) {
                    stato = "Tirocinio Concluso";
                } else {
                    stato = " - ";
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
                    rimborso = "<a style='color: white;display: block;' class='bg-blue fa fa-hourglass-1 btn btn-default popovers' data-toggle='modal' onclick='return showStatoRimborso(" + c.getDomanda_rimborso() + ",&quot;" + c.getCod() + "&quot;)' data-trigger='hover' data-placement='top' data-content='Mostra Stato Rimborso' ><font color='white' face='verdana'> Stato Rimborso</font></a> ";
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

                valore = valore + " [ \"";

                valore += pol.get(c.getCodazioneformcal()) + "\",\""
                        + dinizio + "\",\""
                        + dfine + "\",\""
                        + Utility.correggi(cf_datore) + "\",\""
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
                            searchTutor(request, response);
                        } else if (type.equals("2")) {
                            searchConvenzioni(request, response);
                        } else if (type.equals("3")) {
                            searchPolitiche(request, response);
                        } else if (type.equals("4")) {
                            searchRimborsi(request, response);
                        } else if (type.equals("5")) {
                            searchRimborsiPolitiche(request, response);
                        } else if (type.equals("6")) {
                            searchPrgFormativi(request, response);
                        } else if (type.equals("7")) {
                            searchScartiPolitiche(request, response);
                        } else if (type.equals("8")) {
                            searchRimborsiPrg(request, response);
                        } else if (type.equals("9")) {
                            searchPrgRimborsi(request, response);
                        } else if (type.equals("10")) {
                            searchScartiPrg(request, response);
                        } else if (type.equals("11")) {
                            searchRegistri(request, response);
                        } else if (type.equals("12")) {
                            searchPrgFormativiDt(request, response);
                        } else if (type.equals("13")) {
                            searchRimborsiPrgDt(request, response);
                        } else if (type.equals("14")) {
                            searchPrgRimborsiDt(request, response);
                        } else if (type.equals("15")) {
                            searchScartiPrgDt(request, response);
                        } else if (type.equals("16")) {
                            searchRegistriDt(request, response);
                        } else if (type.equals("17")) {
                            searchPoliticheDt(request, response);
                        } else if (type.equals("18")) {
                            searchRimborsiDT(request, response);
                        } else if (type.equals("19")) {
                            searchRimborsiPoliticheDt(request, response);
                        } else if (type.equals("20")) {
                            searchScartiPoliticheDt(request, response);
                        } else if (type.equals("21")) {
                            searchPoliticaB3Dt(request, response);
                        } else if (type.equals("22")) {
                            searchRimborsiB3DT(request, response);
                        } else if (type.equals("23")) {
                            searchRimborsiPoliticheB3Dt(request, response);
                        } else if (type.equals("24")) {
                            searchScartiB3Dt(request, response);
                        } else if (type.equals("25")) {
                            searchVoucherDt(request, response);
                        } else if (type.equals("26")) {
                            searchRimborsiVoucherDT(request, response);
                        } else if (type.equals("27")) {
                            searchRimborsiVoucherDt(request, response);
                        } else if (type.equals("28")) {
                            searchScartiVoucherDt(request, response);
                        } else if (type.equals("29")) {
                            getAnomaliePolitiche(request, response);
                        } else if (type.equals("30")) {
                            searchPrgFormativiClosed(request, response);
                        } else if (type.equals("31")) {
                            searchPrgFormativiClosed_dt(request, response);
                        } else if (type.equals("32")) {
                            searchLavoratori(request, response);
                        } else if (type.equals("33")) {
                            showpoliticheLavoratore(request, response);
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
