/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import com.seta.activity.Action;
import com.seta.entity.Bando;
import com.seta.entity.CO;
import com.seta.entity.Contratto;
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
public class QueryRegional extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger( QueryRegional.class.getName() );
    protected void searchBandi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String titolo = request.getParameter("titolo");
        String tipo = request.getParameter("tipo");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        String sportello, datafine, file;

        if (titolo == null || titolo.equals("null")) {
            titolo = "";
        }
        if (tipo == null || tipo.equals("null") || tipo.equals("..")) {
            tipo = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fm2 = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<Bando> result = Action.searchBandi(titolo, tipo, from, to);
        Map<String, String> tipo_b = Action.getTipoBandoById();

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String op, disactive, active, modifica, closed, suspend, reactive, stato, report, noreport;
        if (result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                String az = "<div class='btn-group'><a class='btn btn-default blue btn-outline' href='javascript:;' data-toggle='dropdown'>"
                        + "<i class='fa fa-wrench'></i></a>"
                        + "<ul class='dropdown-menu '>";

                op = "Non disponibile";
                Bando temp = (Bando) result.get(i);
                valore = valore + " [ \"";
                disactive = "<li><a data-toggle='modal' href='#disactivemodal' onclick='return setIDDisactive(" + temp.getIdbando() + ")' ><i class='btn btn-default bg-blue-chambray fa fa-ban' style='padding-right:23px; color:white;'></i>Chiudi</a></li>";
                active = "<li><a data-toggle='modal' href='#activemodal' onclick='return setIDActive(" + temp.getIdbando() + "," + temp.getTipo_bando() + ")' ><i class='btn btn-default bg-green fa fa-check' style='padding-right:23px; color:white;'></i>Attiva</a></li>";
                reactive = "<li><a data-toggle='modal' href='#activemodal' onclick='return setIDActive(" + temp.getIdbando() + "," + temp.getTipo_bando() + ")' ><i class='btn btn-default bg-green-sharp fa fa-arrow-circle-up' style='padding-right:23px; color:white;'></i>Riattiva</a></li>";
                suspend = "<li><a data-toggle='modal' href='#suspendmodal' onclick='return setIDSuspend(" + temp.getIdbando() + ")'  ><i class='btn btn-default bg-red-thunderbird fa fa-arrow-circle-down' style='padding-right:23px; color:white;'></i>Sospendi</a></li>";
                modifica = "<li><a  href='modify_bando.jsp?&idbando=" + temp.getIdbando() + "' class=' fancyBoxRafRef' ><i class='btn btn-default bg-yellow-gold fa fa-pencil-square-o' style='padding-right:23px; color: white;'></i>Modifica</a></li>";
                closed = "<li><a style='cursor: default;'a data-toggle='modal' href='' onclick='' ><i class='btn btn-default bg-black fa fa-times-circle' style='padding-right:23px; cursor: default;'></i>Chiuso</a></li>";
                //stato = "<a style='cursor: default;'a data-toggle='modal' href='' onclick='' ><i class='btn btn-default bg-yellow-gold fa fa-exclamation-circle popovers' data-trigger='hover' data-placement='top' data-content='Da attivare' style='color:white; cursor: default;'></i></a>";
                stato = "<a style='color: white; cursor:default' class='bg-yellow-gold  fa fa-exclamation-circle btn btn-block btn-default popovers' ><font color='white' face='verdana'> Da attivare </font> </a>";
                noreport = "<a style='color: white; cursor:default' class='bg-blue-hoki  fa fa-close btn btn-block btn-default popovers' ><font color='white' face='verdana'> No Report </font> </a>";
                report = "<a class='bg-blue fa fa-bar-chart btn btn-block btn-default fancyBoxRafFull' href='bandoReport.jsp?idbando=" + temp.getIdbando() + "' ><font color='white' face='verdana'> Report </font></a>";
                try {

                    if (temp.getStato().equals("I")) {
                        az += active + "</ul></div>";
                    } else if (temp.getStato().equals("A")) {
                        az += modifica + disactive + suspend + "</ul></div>";
                        stato = "<a style='color: white; cursor:default' class='bg-green-jungle fa fa-check-circle btn btn-block btn-default popovers' ><font color='white' face='verdana'> Attivo </font> </a>";
                        //stato = "<a style='cursor: default;'a data-toggle='modal' href='' onclick='' ><i class='btn btn-default bg-green-jungle fa fa-check-circle popovers'  data-trigger='hover' data-placement='top' data-content='Attivo' style='color:white; cursor: default;'></i></a>";
                    } else if (temp.getStato().equals("C")) {
                        az += report + closed + "</ul></div>";
                        stato = "<a style='color: white; cursor:default' class='bg-black fa fa-times-circle btn btn-block btn-default popovers' ><font color='white' face='verdana'> Chiuso </font> </a>";
                        //stato = "<a style='cursor: default;'a data-toggle='modal' href='' onclick='' ><i class='btn btn-default bg-black fa fa-times-circle popovers' data-trigger='hover' data-placement='top' data-content='Chiuso' style='color:white; cursor: default;'></i></a>";
                    } else if (temp.getStato().equals("S")) {
                        stato = "<a style='color: white; cursor:default' class='bg-red-thunderbird fa fa-arrow-circle-up btn btn-block btn-default popovers' ><font color='white' face='verdana'> Sospeso </font> </a>";
                        //stato = "<a style='cursor: default;'a data-toggle='modal' href='' onclick='' ><i class='btn btn-default bg-red-thunderbird fa fa-arrow-circle-up popovers' data-trigger='hover' data-placement='top' data-content='Sospeso' style='color:white; cursor: default;'></i></a>";
                        az += modifica + reactive + "</ul></div>";
                    }
//                    modifica + disactive + active + suspend + "</ul></div>";
//                    if (temp.getStato().equals("A")) {
//                        az += modifica + disactive + "</ul></div>";
//                    } else {
//                        az += closed + "</ul></div>";
//                    }

                    if (temp.getFlag_sportello().equals("Y")) {
//                        sportello = "SI";
//                        datafine = "-";
                        datafine = "A sportello";
                    } else {
//                        sportello = "-";
//                        datafine = fm2.format(fm.parse(temp.getData_fine()));
                        datafine = fm2.format(fm.parse(temp.getData_fine()));
                    }
                    if (temp.getPath().equals("-")) {
                        //file = "<a style='color: white; cursor: default;' class='bg-red fa fa-close btn btn-default popovers fancyBoxRafRef' href=''><font color='white' face='verdana'> Non presente</font> </a>";
                        file = "-";
                    } else {
                        file = "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(temp.getPath(), "\\", "/") + "'><font color='white' face='verdana'></font> </a>";
                    }
                    if (temp.getStato().equals("C")) {
                        op = "<a style='color: white; cursor:default' class='bg-black fa fa-times-circle btn btn-block btn-default popovers' ><font color='white' face='verdana'> Chiuso </font> </a>";
                    } else {
                        ////////if (temp.getTipo_bando().equals("2")){//andrà poi tolto quando si sviluppa DOTE 
                        if (Action.ctrl_BudgetsPolitiche(temp.getIdbando())) {
                            op = "<a style='color: white;' class='bg-blue-chambray fa fa-pencil-square-o  btn btn-block btn-default popovers fancyBoxRafRef' href='modify_configuration_bando.jsp?&idbando=" + temp.getIdbando() + "&tipo=" + temp.getTipo_bando() + "'><font color='white' face='verdana'> Modifica </font> </a>";
                        } else {
                            op = "<a style='color: white;' class='bg-blue-dark fa fa-pencil btn btn-block btn-default popovers fancyBoxRafRef' href='configuration_bando.jsp?&idbando=" + temp.getIdbando() + "&tipo=" + temp.getTipo_bando() + "'><font color='white' face='verdana'> Inserisci </font> </a>";
                        }
                        ////////}
                    }
                    valore += az + "\",\""
                            + (temp.getStato().equals("I") ? noreport : report) + "\",\""
                            + stato + "\",\""
                            + Utility.CaratteriSpeciali(temp.getTitolo()) + "\",\""
                            + Utility.correggi(temp.getDecreto()) + "\",\""
                            + tipo_b.get(temp.getTipo_bando()) + "\",\""
                            + fm2.format(fm.parse(temp.getData_inizio())) + "\",\""
                            + datafine + "\",\""
                            + "&#8364 " + String.format("%1$,.2f", Double.parseDouble(temp.getBudget())) + "\",\""
                            + op + "\",\""
                            + file + "\",\""
                            + fm2.format(fm.parse(temp.getData_creazione())) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
                }

            }
            String x = inizio + valore.substring(0, valore.length() - 1) + fine;
            out.print(x);
        } else {
            out.print(inizio + fine);
        }
        out.close();
    }

    protected void search1B(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String titolo = request.getParameter("titolo");
        String tipo = request.getParameter("tipo");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        String sportello, datafine, file;

        if (titolo == null || titolo.equals("null")) {
            titolo = "";
        }
        if (tipo == null || tipo.equals("null") || tipo.equals("..")) {
            tipo = "";
        }
        if (from == null || from.equals("null")) {
            from = "";
        }
        if (to == null || to.equals("null")) {
            to = "";
        }

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fm2 = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<Bando> result = Action.searchBandi(titolo, tipo, from, to);
        Map<String, String> tipo_b = Action.getTipoBandoById();

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String op, disactive, active, modifica, closed;
        if (result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                String az = "<div class='btn-group'><a class='btn btn-default blue btn-outline' href='javascript:;' data-toggle='dropdown'>"
                        + "<i class='fa fa-wrench'></i></a>"
                        + "<ul class='dropdown-menu '>";

                op = "Non disponibile";
                Bando temp = (Bando) result.get(i);
                valore = valore + " [ \"";
                disactive = "<li><a data-toggle='modal' href='#scartamodal' onclick='return setScartaID(" + temp.getIdbando() + ")' class='popovers'  data-trigger='hover' data-placement='top' data-content='Scarta'><i class='btn btn-default bg-red fa fa-ban' style='padding-right:23px'></i>Scarta</a></li>";
                //active = "<li><a data-toggle='modal' href='#activemodal' onclick='return setIDActive(" + temp.getIdbando() + ")' class='popovers'  data-trigger='hover' data-placement='top' data-content='Attiva'><i class='btn btn-default bg-green fa fa-check' style='padding-right:23px'></i>Attiva</a></li>";
                modifica = "<li><a href='modify_bando.jsp?&idbando=" + temp.getIdbando() + "' class='popovers fancyBoxRafRef'  data-trigger='hover' data-placement='top' data-content='Modifica'><i class='btn btn-default bg-yellow-gold fa fa-pencil-square-o' style='padding-right:23px; color: white;'></i>Modifica</a></li>";
                closed = "<li><a style='cursor: default;'a data-toggle='modal' href='' onclick='' class='popovers'  data-trigger='hover' data-placement='top' data-content='Chiuso'><i class='btn btn-default bg-black fa fa-times-circle' style='padding-right:23px; cursor: default;'></i>Chiuso</a></li>";

                try {
                    if (temp.getStato().equals("A")) {
                        az += modifica + disactive + "</ul></div>";
                    } else {
                        az += closed + "</ul></div>";
                    }

                    if (temp.getFlag_sportello().equals("Y")) {
                        sportello = "SI";
                        datafine = "-";
                    } else {
                        sportello = "-";
                        datafine = fm2.format(fm.parse(temp.getData_fine()));
                    }
                    if (temp.getPath().equals("-")) {
                        //file = "<a style='color: white; cursor: default;' class='bg-red fa fa-close btn btn-default popovers fancyBoxRafRef' href=''><font color='white' face='verdana'> Non presente</font> </a>";
                        file = "-";
                    } else {
                        file = "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(temp.getPath(), "\\", "/") + "'><font color='white' face='verdana'></font> </a>";
                    }
                    if (temp.getStato().equals("C")) {
                        op = "<a style='color: white; cursor:default' class='bg-black fa fa-times-circle btn btn-default popovers' ><font color='white' face='verdana'> Chiuso </font> </a>";
                    } else {
                        if (temp.getTipo_bando().equals("2")) {//andrà poi tolto quando si sviluppa DOTE 
                            if (Action.ctrl_BudgetsPolitiche(temp.getIdbando())) {
                                op = "<a style='color: white;' class='bg-green-turquoise fa fa-gears btn btn-default popovers fancyBoxRaf' href='modify_configuration_bando.jsp?&idbando=" + temp.getIdbando() + "&tipo=" + temp.getTipo_bando() + "'><font color='white' face='verdana'> Modifica </font> </a>";
                            } else {
                                op = "<a style='color: white;' class='bg-green-seagreen fa fa-gears btn btn-default popovers fancyBoxRaf' href='configuration_bando.jsp?&idbando=" + temp.getIdbando() + "&tipo=" + temp.getTipo_bando() + "'><font color='white' face='verdana'> Inserisci </font> </a>";
                            }
                        }
                    }
                    valore += az + "\",\""
                            + op + "\",\""
                            + Utility.correggi(temp.getTitolo()) + "\",\""
                            + Utility.correggi(temp.getDecreto()) + "\",\""
                            + tipo_b.get(temp.getTipo_bando()) + "\",\""
                            + fm2.format(fm.parse(temp.getData_inizio())) + "\",\""
                            + datafine + "\",\""
                            + sportello + "\",\""
                            + "&#8364 " + String.format("%1$,.2f", Double.parseDouble(temp.getBudget())) + "\",\""
                            + file + "\",\""
                            + fm2.format(fm.parse(temp.getData_creazione())) + "\",\""
                            + "\"],";
                } catch (ParseException ex) {
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
        Map<String, String[]> adinfo = Action.get_ADInfos();
        Map<String, String> pol = Action.getPoliticaById();
        ArrayList<Rimborso> list = Action.getListRimborsi(politica, "S", ente, from, to);
        if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
            adinfo = Action.get_ADInfos_DOTE();
            list = Action.getListRimborsi_DOTE(politica, "S", ente, from, to);
        } else if (politica.equals("B3")) {
            adinfo = Action.get_ADInfos_DOTE_B3();
            list = Action.getListRimborsi_DOTE_B3(politica, "S", ente, from, to);
        } else if (politica.equals("B2") || politica.equals("C2")) {
            adinfo = Action.get_ADInfos_Voucher_DT();
            list = Action.getListRimborsi_Voucher_DT(politica, "S", ente, from, to);
        }

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String upload = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso t : list) {
                try {
                    valore = valore + " [ \"";
                    if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
                        upload = "<a href='upload_protocollo1B.jsp?idrimborso=" + t.getIdrimborso() + "&nome=" + adinfo.get(t.getIdrimborso())[0] + "&pol=DT' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica PDF Protocollo'><i class='btn btn-default bg-blue fa fa-legal' ><font face='verdana'>   Carica Protocollo</font></i></a>";
                    } else if (politica.equals("B3")) {
                        upload = "<a href='upload_protocollo1B.jsp?idrimborso=" + t.getIdrimborso() + "&nome=" + adinfo.get(t.getIdrimborso())[0] + "&pol=B3' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica PDF Protocollo'><i class='btn btn-default bg-blue fa fa-legal' ><font face='verdana'>   Carica Protocollo</font></i></a>";
                    } else if (politica.equals("B2") || politica.equals("C2")) {
                        upload = "<a href='upload_protocollo1B.jsp?idrimborso=" + t.getIdrimborso() + "&nome=" + adinfo.get(t.getIdrimborso())[0] + "&pol=VOUCHER' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica PDF Protocollo'><i class='btn btn-default bg-blue fa fa-legal' ><font face='verdana'>   Carica Protocollo</font></i></a>";
                    } else {
                        upload = "<a href='upload_protocollo1B.jsp?idrimborso=" + t.getIdrimborso() + "&nome=" + adinfo.get(t.getIdrimborso())[0] + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica PDF Protocollo'><i class='btn btn-default bg-blue fa fa-legal' ><font face='verdana'>   Carica Protocollo</font></i></a>";
                    }
                    valore += upload + "\",\""
                            + adinfo.get(t.getIdrimborso())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso())[1] + "\",\""
                            + pol.get(t.getPolitica()) + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
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

    protected void liquidaRimborsi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
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
        Map<String, String[]> adinfo = Action.get_ADInfos();
        Map<String, String> pol = Action.getPoliticaById();
        ArrayList<Rimborso> list = Action.getListRimborsi(politica, "R2", ente, from, to);
        if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
            adinfo = Action.get_ADInfos_DOTE();
            list = Action.getListRimborsi_DOTE(politica, "R2", ente, from, to);
        } else if (politica.equals("B3")) {
            adinfo = Action.get_ADInfos_DOTE_B3();
            list = Action.getListRimborsi_DOTE_B3(politica, "R2", ente, from, to);
        } else if (politica.equals("B2") || politica.equals("C2")) {
            adinfo = Action.get_ADInfos_Voucher_DT();
            list = Action.getListRimborsi_Voucher_DT(politica, "R2", ente, from, to);
        }

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String upload = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso t : list) {
                try {
                    valore = valore + " [ \"";
                    if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
                        upload = "<a href='upload_decreto1B.jsp?idrimborso=" + t.getIdrimborso() + "&nome=" + adinfo.get(t.getIdrimborso())[0] + "&pol=DT&ctrlrimborso=" + t.getRimborso() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica Decreto'><i class='btn btn-default bg-yellow-casablanca fa fa-cloud-upload' style='color: white;' ><font face='verdana'>   Carica Decreto Liquidazione</font></i></a>";
                    } else if (politica.equals("B3")) {
                        upload = "<a href='upload_decreto1B.jsp?idrimborso=" + t.getIdrimborso() + "&nome=" + adinfo.get(t.getIdrimborso())[0] + "&pol=B3&ctrlrimborso=" + t.getRimborso() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica Decreto'><i class='btn btn-default bg-yellow-casablanca fa fa-cloud-upload' style='color: white;' ><font face='verdana'>   Carica Decreto Liquidazione</font></i></a>";
                    } else if (politica.equals("B2") || politica.equals("C2")) {
                        upload = "<a href='upload_decreto1B.jsp?idrimborso=" + t.getIdrimborso() + "&nome=" + adinfo.get(t.getIdrimborso())[0] + "&pol=VOUCHER&ctrlrimborso=" + t.getRimborso() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica Decreto'><i class='btn btn-default bg-yellow-casablanca fa fa-cloud-upload' style='color: white;' ><font face='verdana'>   Carica Decreto Liquidazione</font></i></a>";
                    } else {
                        upload = "<a href='upload_decreto1B.jsp?idrimborso=" + t.getIdrimborso() + "&nome=" + adinfo.get(t.getIdrimborso())[0] + "&ctrlrimborso=" + t.getRimborso() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica Decreto'><i class='btn btn-default bg-yellow-casablanca fa fa-cloud-upload' style='color: white;' ><font face='verdana'>   Carica Decreto Liquidazione</font></i></a>";
                    }
                    valore += upload + "\",\""
                            + adinfo.get(t.getIdrimborso())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso())[1] + "\",\""
                            + pol.get(t.getPolitica()) + "\",\""
                            + "&#8364 " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato())) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getCheckList(), "\\", "/") + "'><font color='white' face='verdana'></font> </a>" + "\",\""
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

    protected void gestioneRimborsi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
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

        ArrayList<Rimborso> list = Action.getListRimborsi(politica, "N", ente, from, to);
        Map<String, String[]> adinfo = Action.get_ADInfos();
        if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
            adinfo = Action.get_ADInfos_DOTE();
            list = Action.getListRimborsi_DOTE(politica, "N", ente, from, to);
        } else if (politica.equals("B3")) {
            adinfo = Action.get_ADInfos_DOTE_B3();
            list = Action.getListRimborsi_DOTE_B3(politica, "N", ente, from, to);
        } else if (politica.equals("B2") || politica.equals("C2")) {
            adinfo = Action.get_ADInfos_Voucher_DT();
            list = Action.getListRimborsi_Voucher_DT(politica, "N", ente, from, to);
        }
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        String rimb_sing = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso t : list) {
                try {
                    valore = valore + " [ \"";
                    if (politica.equals("B2") || politica.equals("C2")) {
                        rimb_sing = "<a href='reg_showVoucher.jsp?idrimborso=" + t.getIdrimborso() + "&politica=" + politica + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Rimborsi Voucher'><i class='btn btn-default bg-blue fa fa-ticket' style='color: white;' ><font face='verdana'> Lista Rimborsi</font></i></a>";
                    } else {
                        rimb_sing = "<a href='reg_showTirocinantiRimborso.jsp?idrimborso=" + t.getIdrimborso() + "&politica=" + politica + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Rimborsi'><i class='btn btn-default bg-blue fa fa-list-ul' style='color: white;' ><font face='verdana'> Lista Rimborsi</font></i></a>";
                    }
                    az = "<a style='color: white;' class='bg-green-jungle fa fa-check btn btn-default popovers' data-toggle='modal' onclick='return setIDAccettaRimborsi(" + t.getIdrimborso() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta  Rimborsi' ><font color='white' face='verdana'> Accetta</font></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getIdrimborso() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Rigetta Rimborsi' ><font color='white' face='verdana'> Rigetta</font> </a>";
                    valore += az + "\",\""
                            + rimb_sing + "\",\""
                            + adinfo.get(t.getIdrimborso())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso())[1] + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(adinfo.get(t.getIdrimborso())[2], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. AD/AU' ><font color='white' face='verdana'></font> </a> " + "\",\""
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

    protected void politicheRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String esiti = request.getParameter("esi");
        String idrimborso = request.getParameter("idrimborso");
        ArrayList<Politica> list = Action.getListPolitiche(idrimborso);
        if (request.getParameter("politica").equals("B1") || request.getParameter("politica").equals("C1") || request.getParameter("politica").equals("D2") || request.getParameter("politica").equals("D5")) {
            list = Action.getListPolitiche_DOTE(idrimborso);
        } else if (request.getParameter("politica").equals("B3")) {
            list = Action.getListPolitiche_DOTE_B3(idrimborso);
        }

        Contratto contr = new Contratto();
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        String co = "";
        String docs = "-";
        String ore = "-";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Politica t : list) {
                if (t.getCodazioneformcal().equals("B03")) {
                    contr = Action.getContrattoById(t.getId());
                    String[] listdocsM3 = Action.getListDocsTirocinanteM3(String.valueOf(t.getId()));
                    docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Modulo M5' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-black-tie btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(contr.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Contratto Destinatario' ><font color='white' face='verdana'></font> </a> ";
                } else if (request.getParameter("politica").equals("D2") || request.getParameter("politica").equals("D5")) {
                    contr = Action.getContrattoByIdDt(t.getId());
                    String[] listdocsM3 = Action.getListDocsTirocinanteD2D5_DOTE(String.valueOf(t.getId()));
                    docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-black-tie btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(contr.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Contratto Destinatario' ><font color='white' face='verdana'></font> </a> ";
                } else if (request.getParameter("politica").equals("B3")) {
                    String[] listdocsM3 = Action.getListDocsTirocinante_DOTE_B3(String.valueOf(t.getId()));
                    docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Registro' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[6], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Timesheet' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue-soft fa fa-file-powerpoint-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocsM3[7], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Business Plan' ><font color='white' face='verdana'></font> </a> ";
                } else {
                    String modello = "Modulo M5";
                    ore = String.valueOf(t.getDurataeffettiva()) + " h";
                    String[] listdocs = Action.getListDocsTirocinante(String.valueOf(t.getId()));
                    if (request.getParameter("politica").equals("B1") || request.getParameter("politica").equals("C1")) {
                        listdocs = Action.getListDocsTirocinante_DOTE(String.valueOf(t.getId()));
                        modello = "Allegato 8";
                    }
                    docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[6], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='" + modello + "' ><font color='white' face='verdana'></font> </a> ";
                }
                valore = valore + " [ \"";
                //az = "<a style='color: white;' class='bg-yellow-gold  fa fa-exclamation btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId()+ ","+t.getRimborso()+")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Rigetta Rimborso Destinatario' ><font color='white' face='verdana'> Rigetta</font></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setIDDisactive(" + t.getId()+ ","+t.getRimborso()+")' href='#rifiutamodal' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Rimborso' ><font color='white' face='verdana'> Scarta</font> </a>";
                az = "<a style='color: white;' class='bg-yellow-gold  fa fa-exclamation btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità Rimborso' ><font color='white' face='verdana'> Rigetta</font></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal1' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Rimborso' ><font color='white' face='verdana'> Scarta</font> </a>";
                co = "<a href='show_CO.jsp?idente=" + t.getIdente() + "&idlav=" + t.getIdlav() + "' class='popovers fancyBoxDon' data-trigger='hover' data-placement='top' data-content='Visualizza CO Destinatario-Ente'><i class='btn btn-default bg-blue fa fa-clone' style='color: white;' ><font face='verdana'> Visualizza</font></i></a>";

                if (esiti.equals("OK")) {
                    valore += co + "\",\""
                            + t.getCognome() + " " + t.getNome() + "\",\""
                            + t.getCf() + "\",\""
                            + ore + "\",\""
                            + docs + "\",\""
                            + "\"],";
                } else {
                    valore += az + "\",\""
                            + co + "\",\""
                            + t.getCognome() + " " + t.getNome() + "\",\""
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

    protected void anomalieRimborsi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String politica = request.getParameter("politica");
        ArrayList<Rimborso> list = Action.getListRimborsi(politica, "E2", "", "", "");
        Map<String, String[]> adinfo = Action.get_ADInfos();
        if (politica.equals("B1") || politica.equals("C1") || politica.equals("D2") || politica.equals("D5")) {
            adinfo = Action.get_ADInfos_DOTE();
            list = Action.getListRimborsi_DOTE(politica, "E2", "", "", "");
        } else if (politica.equals("B3")) {
            adinfo = Action.get_ADInfos_DOTE_B3();
            list = Action.getListRimborsi_DOTE_B3(politica, "E2", "", "", "");
        } else if (politica.equals("B2") || politica.equals("C2")) {
            adinfo = Action.get_ADInfos_Voucher_DT();
            list = Action.getListRimborsi_Voucher_DT(politica, "E2", "", "", "");
        }

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        String rimb_sing = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        if (list.size() > 0) {
            for (Rimborso t : list) {
                try {
                    valore = valore + " [ \"";
                    if (politica.equals("B2") || politica.equals("C2")) {
                        rimb_sing = "<a  href='reg_showVoucher.jsp?idrimborso=" + t.getIdrimborso() + "&politica=" + politica + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Rimborsi Voucher'><i class='btn btn-default bg-blue fa fa-ticket' style='color: white;' ><font face='verdana'> Lista Rimborsi</font></i></a>";
                    } else {
                        rimb_sing = "<a href='reg_showTirocinantiRimborso.jsp?idrimborso=" + t.getIdrimborso() + "&politica=" + politica + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Rimborsi'><i class='btn btn-default bg-blue fa fa-list-ul' style='color: white;' ><font face='verdana'> Lista Rimborsi</font></i></a>";
                    }
                    az = "<a style='color: white;' class='bg-green-jungle fa fa-check btn btn-default popovers' data-toggle='modal' onclick='return setIDAccettaRimborsi(" + t.getIdrimborso() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Rimborsi' ><font color='white' face='verdana'> Accetta</font></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getIdrimborso() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Rigetta Rimborsi' ><font color='white' face='verdana'> Rigetta</font> </a>";
                    valore += az + "\",\""
                            + rimb_sing + "\",\""
                            + adinfo.get(t.getIdrimborso())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso())[1] + "\",\""
                            + t.getMotivo() + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(adinfo.get(t.getIdrimborso())[2], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. AD/AU' ><font color='white' face='verdana'></font> </a> " + "\",\""
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
        String tutor = "-";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Politica t : list) {
                try {
                    if (!politica.equals("B03")) {
                        tutor = t.getNomeTutor();
                    }

                    valore = valore + " [ \""
                            + t.getNome() + "\",\""
                            + t.getCognome() + "\",\""
                            + t.getCf() + "\",\""
                            + tutor + "\",\"";
                    if (politica.equals("B05")) {
                        valore += t.getDurataeffettiva() + "\",\"";
                    }
                    valore += t.getMotivo() + "\",\""
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

    protected void searchProgettoFormativo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
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
        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo();
        Map<String, String> pol = Action.getPoliticaById();
        ArrayList<Rimborso_PrgFormativo> list = Action.getListRimborsi_PrgFormativo(politica, "S", ente, from, to);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String upload = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo t : list) {
                try {
                    valore = valore + " [ \"";
                    upload = "<a href='upload_protocollo1B.jsp?idprgform=" + t.getIdrimborso_prg() + "&nome=" + adinfo.get(t.getIdrimborso_prg())[0] + "&pol=M5' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica PDF Protocollo'><i class='btn btn-default bg-blue fa fa-legal' ><font face='verdana'>   Carica Protocollo</font></i></a>";
                    valore += upload + "\",\""
                            + t.getIdrimborso_prg() + "\",\""
                            + Utility.correggi(adinfo.get(t.getIdrimborso_prg())[0]) + "\",\""
                            + Utility.correggi(adinfo.get(t.getIdrimborso_prg())[1]) + "\",\""
                            + Utility.correggi(pol.get(t.getPolitica())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
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

    protected void gestioneRimborsiPF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
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

        ArrayList<Rimborso_PrgFormativo> list = Action.getListRimborsi_PrgFormativo(politica, "N", ente, from, to);
        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo();

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo t : list) {
                try {
                    valore = valore + " [ \"";
                    az = "<a style='color: white;' class='bg-green-jungle fa fa-check btn btn-default popovers' data-toggle='modal' onclick='return setIDAccettaRimborsi(" + t.getIdrimborso_prg() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Rimborsi' ><font color='white' face='verdana'> Accetta</font></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getIdrimborso_prg() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Rigetta Rimborsi' ><font color='white' face='verdana'> Rigetta</font> </a>";
                    valore += az + "\",\""
                            + "<a href='reg_showTirocinantiRimborsoM5.jsp?idrimborso=" + t.getIdrimborso_prg() + "&politica=" + politica + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Progetti Formativi'><i class='btn btn-default bg-blue-chambray fa fa-file-text-o' style='color: white;' ><font face='verdana'> Progetti Formativi</font></i></a>" + "\",\""
                            + t.getProtocollo()+ "\",\""
                            + adinfo.get(t.getIdrimborso_prg())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso_prg())[1] + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(adinfo.get(t.getIdrimborso_prg())[2], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. AD/AU' ><font color='white' face='verdana'></font> </a> " + "\",\""
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

    protected void PrgFormativiRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String esiti = request.getParameter("esi");
        String idrimborso = request.getParameter("idrimborso");
        ArrayList<PrgFormativo> list = Action.getListPrgFormativo(idrimborso);
        //Contratto contr = new Contratto();
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        String docs = "-";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");

        if (list.size() > 0) {
            for (PrgFormativo t : list) {
                String[] listdocs = Action.getListDocsTirocinanteM5(String.valueOf(t.getId()));
                docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-meadow fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[7], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Prg Formativo' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[6], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Documento Competenze' ><font color='white' face='verdana'></font> </a> ";
                valore = valore + " [ \"";
                az = "<a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità Progetto Formativo' ></a> <a style='color: white;' class='bg-red-thunderbird fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal1' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Progetto Formativo' ></a>";

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

    protected void liquidaRimborsiPF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
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
        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo();
        Map<String, String> pol = Action.getPoliticaById();
        ArrayList<Rimborso_PrgFormativo> list = Action.getListRimborsi_PrgFormativo(politica, "R2", ente, from, to);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String upload = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo t : list) {
                try {
                    valore = valore + " [ \"";
                    upload = "<a href='upload_decreto1B.jsp?idprgform=" + t.getIdrimborso_prg() + "&nome=" + adinfo.get(t.getIdrimborso_prg())[0] + "&pol=M5&ctrlrimborso=" + t.getRimborso() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica Decreto'><i class='btn btn-default bg-yellow-casablanca fa fa-cloud-upload' style='color: white;' ><font face='verdana'>   Carica Decreto Liquidazione</font></i></a>";
                    valore += upload + "\",\""
                            + t.getIdrimborso_prg() + "\",\""
                            + t.getProtocollo() + "\",\""
                            + adinfo.get(t.getIdrimborso_prg())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso_prg())[1] + "\",\""
                            + pol.get(t.getPolitica()) + "\",\""
                            + "&#8364 " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato())) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getCheckList(), "\\", "/") + "'><font color='white' face='verdana'></font> </a>" + "\",\""
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

    protected void anomalieRimborsiPF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
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
        ArrayList<Rimborso_PrgFormativo> list = Action.getListRimborsi_PrgFormativo(politica, "E2", ente, from, to);
        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo();

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        if (list.size() > 0) {
            for (Rimborso_PrgFormativo t : list) {
                try {
                    valore = valore + " [ \"";
                    az = "<a style='color: white;' class='bg-green-jungle fa fa-check btn btn-default popovers' data-toggle='modal' onclick='return setIDAccettaRimborsi(" + t.getIdrimborso_prg() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Rimborsi' ><font color='white' face='verdana'> Accetta</font></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getIdrimborso_prg() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Rigetta Rimborsi' ><font color='white' face='verdana'> Rigetta</font> </a>";
                    valore += az + "\",\""
                            + "<a href='reg_showTirocinantiRimborsoM5.jsp?idrimborso=" + t.getIdrimborso_prg() + "&politica=" + politica + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Progetti Formativi'><i class='btn btn-default bg-blue-chambray fa fa-file-text-o' style='color: white;' ><font face='verdana'> Lista Progetti Formativi</font></i></a>" + "\",\""
                            + adinfo.get(t.getIdrimborso_prg())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso_prg())[1] + "\",\""
                            + t.getMotivo() + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(adinfo.get(t.getIdrimborso_prg())[2], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. AD/AU' ><font color='white' face='verdana'></font> </a> " + "\",\""
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
        if (cognome == null || ente.equals("cognome")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        ArrayList<Registro> list = Action.getListRegistri(politica, "S", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String prg = "";
        String az = "";
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
                    az = "<a style='color: white;' class='bg-green fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setAccettaID(" + t.getId() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Registro' ><font color='white' face='verdana'></font></a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white; ' class='bg-red fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getOre() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Scarta Registro Definitivamente' ><font color='white' face='verdana'></font> </a>";

                    prg = "<a style='color: white; ' class='bg-blue-hoki fa fa-folder btn btn-default popovers' onclick='return showPrg(" + t.getProgetto_formativo() + ")' data-trigger='hover' data-placement='top' data-content='Dati Progetto Formativo' ><font color='white' face='verdana'></font> </a>";

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

    protected void searchAnomalieRegistri(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if (cognome == null || ente.equals("cognome")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        ArrayList<Registro> list = Action.getListRegistri(politica, "E2", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String az = "";
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
                    az = "<a style='color: white;' class='bg-green fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setAccettaID(" + t.getId() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Registro' ><font color='white' face='verdana'></font></a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white; ' class='bg-red fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getOre() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Scarta Registro Definitivamente' ><font color='white' face='verdana'></font> </a>";
                    valore = valore + " [ \"";
                    valore += az + "\",\""
                            + Utility.correggi(t.getMotivo()) + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
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

    protected void liquidazioneRegistri(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if (cognome == null || ente.equals("cognome")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        ArrayList<Registro> list = Action.getListRegistri(politica, "R2", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String az = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Registro t : list) {
                try {
                    docs = "<a style='color: white;' class='bg-green-meadow fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getChecklist(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Checklist Revisore' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Registro' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue-chambray fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_lavoratore(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-file-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_quietanza(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Quietanza' ><font color='white' face='verdana'></font> </a> ";
                    //MODALS
                    az = "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                            + "<input type='checkbox' class='group-checkable' data-set='#sample_2 .checkboxes' id='" + t.getId() + "' />"//onclick='return seleriga(this.id);'
                            + "<span></span>"
                            + "</label>";
                    valore = valore + " [ \"";
                    valore += az + "\",\""
                            + "<a href='modResidenza_XML.jsp?id=" + t.getId_lavoratore() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica Residenza Destinatario'><i class='btn btn-default bg-red-flamingo fa fa-pencil-square-o' style='color: white;' ></i></a>" + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getOre_rev() + " h" + "\",\""
                            + "&euro; " + String.format("%1$,.2f", t.getTot_erogato()) + "\",\""
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

    protected void tirocinantiINPS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if (cognome == null || ente.equals("cognome")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        ArrayList<Registro> list = Action.getListRegistri(politica, "W", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String az = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String path = Action.getPath("inps");
        if (list.size() > 0) {
            for (Registro t : list) {
                try {
                    docs = "<a style='color: white;' class='bg-yellow-gold fa fa-file-text-o btn btn-default popovers' href='OperazioniGeneral?type=6&path=" + StringUtils.replace(path + t.getXml_liquidazione(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Scarica File XML INPS' ><font color='white' face='verdana'> File XML INPS</font> </a> ";

                    //MODALS
                    az = "<a style='color: white;' class='bg-green-jungle fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setAccettaID(" + t.getId() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Liquidato con successo' ><font color='white' face='verdana'> OK</font></a> "
                            + "<a style='color: white; ' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setRifiutaID(" + t.getId() + ")' href='#rifiutamodal' data-trigger='hover' data-placement='top' data-content='Non liquidato' ><font color='white' face='verdana'> KO</font> </a>"
                            + "<a style='color: white;' class='bg-red-thunderbird fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente' ><font color='white' face='verdana'> Scarta</font></a> ";
                    valore = valore + " [ \"";
                    valore += az + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + docs + "\",\""
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

    protected void registriLiquidatiOK(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if (cognome == null || ente.equals("cognome")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        ArrayList<Registro> list = Action.getListRegistri(politica, "P", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String xml = "";
        String az = "";
        String path = Action.getPath("inps");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Registro t : list) {
                try {
                    docs = "<a style='color: white;' class='bg-green-meadow fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getChecklist(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Checklist Revisore' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Registro' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue-chambray fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_lavoratore(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue fa fa-file-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_quietanza(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Quietanza' ><font color='white' face='verdana'></font> </a> ";
                    xml = "<a style='color: white;' class='bg-green-jungle fa fa-refresh btn btn-default popovers' href='OperazioniGeneral?type=6&path=" + StringUtils.replace(path + t.getXml_liquidazione(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Scarica File XML INPS' ><font color='white' face='verdana'> Consulta Xml</font> </a> ";

                    valore = valore + " [ \"";
                    valore += Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getOre_rev() + " h" + "\",\""
                            + "&euro; " + String.format("%1$,.2f", t.getTot_erogato()) + "\",\""
                            + docs + "\",\""
                            + xml + "\",\""
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

    protected void searchProgettoFormativo_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
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
        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo_DT();
        Map<String, String> pol = Action.getPoliticaByIdDT();
        ArrayList<Rimborso_PrgFormativo_Dt> list = Action.getListRimborsi_PrgFormativoDT(politica, "S", ente, from, to);

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String upload = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo_Dt t : list) {
                try {
                    valore = valore + " [ \"";
                    upload = "<a href='upload_protocollo1B.jsp?idprgform_dt=" + t.getIdrimborso_prg_dt() + "&nome=" + adinfo.get(t.getIdrimborso_prg_dt())[0] + "&pol=" + t.getPolitica() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica PDF Protocollo'><i class='btn btn-default bg-blue fa fa-legal' ><font face='verdana'>   Carica Protocollo</font></i></a>";
                    valore += upload + "\",\""
                            + Utility.correggi(adinfo.get(t.getIdrimborso_prg_dt())[0]) + "\",\""
                            + Utility.correggi(adinfo.get(t.getIdrimborso_prg_dt())[1]) + "\",\""
                            + Utility.correggi(pol.get(t.getPolitica())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
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

    protected void gestioneRimborsiPF_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
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

        ArrayList<Rimborso_PrgFormativo_Dt> list = Action.getListRimborsi_PrgFormativoDT(politica, "N", ente, from, to);
        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo_DT();
        Map<String, String> pol = Action.getPoliticaByIdDT();
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo_Dt t : list) {
                try {
                    valore = valore + " [ \"";
                    az = "<input type='hidden' id='" + t.getIdrimborso_prg_dt() + "_pol' value='" + t.getPolitica() + "' />"
                            + "<a style='color: white;' class='bg-green-jungle fa fa-check btn btn-default popovers' data-toggle='modal' onclick='return setIDAccettaRimborsi(" + t.getIdrimborso_prg_dt() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Rimborsi' ><font color='white' face='verdana'> Accetta</font></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getIdrimborso_prg_dt() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Rigetta Rimborsi' ><font color='white' face='verdana'> Rigetta</font> </a>";
                    valore += az + "\",\""
                            + "<a href='reg_showTirocinantiRimborsoDT.jsp?idrimborso=" + t.getIdrimborso_prg_dt() + "&politica=" + t.getPolitica() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Progetti Formativi'><i class='btn btn-default bg-blue-chambray fa fa-file-text-o' style='color: white;' ><font face='verdana'> Progetti Formativi</font></i></a>" + "\",\""
                            + adinfo.get(t.getIdrimborso_prg_dt())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso_prg_dt())[1] + "\",\""
                            + Utility.correggi(pol.get(t.getPolitica())) + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(adinfo.get(t.getIdrimborso_prg_dt())[2], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. AD/AU' ><font color='white' face='verdana'></font> </a> " + "\",\""
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

    protected void PrgFormativiRimborso_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String esiti = request.getParameter("esi");
        String idrimborso = request.getParameter("idrimborso");
        ArrayList<PrgFormativoDt> list = Action.getListPrgFormativo_DT(idrimborso);
        //Contratto contr = new Contratto();
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        String docs = "-";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");

        if (list.size() > 0) {
            for (PrgFormativoDt t : list) {
                String[] listdocs = Action.getListDocsTirocinanteDT(String.valueOf(t.getId()));
                docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[4], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-meadow fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[8], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Prg. Formativo' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-jungle fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[7], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Allegato 8' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[6], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Doc. Competenze' ><font color='white' face='verdana'></font> </a> ";
                valore = valore + " [ \"";
                az = "<a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Rigetta Singolo Progetto Formativo' ></a> <a style='color: white;' class='bg-red-thunderbird fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal1' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Progetto Formativo' ></a>";
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

    protected void liquidaRimborsiPF_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
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
        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo_DT();
        Map<String, String> pol = Action.getPoliticaByIdDT();
        ArrayList<Rimborso_PrgFormativo_Dt> list = Action.getListRimborsi_PrgFormativoDT(politica, "R2", ente, from, to);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String upload = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (Rimborso_PrgFormativo_Dt t : list) {
                try {
                    valore = valore + " [ \"";
                    upload = "<a href='upload_decreto1B.jsp?idprgform_dt=" + t.getIdrimborso_prg_dt() + "&nome=" + adinfo.get(t.getIdrimborso_prg_dt())[0] + "&pol=" + t.getPolitica() + "&ctrlrimborso=" + t.getRimborso() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Carica Decreto'><i class='btn btn-default bg-yellow-casablanca fa fa-cloud-upload' style='color: white;' ><font face='verdana'>   Carica Decreto Liquidazione</font></i></a>";
                    valore += upload + "\",\""
                            + adinfo.get(t.getIdrimborso_prg_dt())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso_prg_dt())[1] + "\",\""
                            + pol.get(t.getPolitica()) + "\",\""
                            + "&#8364 " + String.format("%1$,.2f", Double.parseDouble(t.getTot_erogato())) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getCheckList(), "\\", "/") + "'><font color='white' face='verdana'></font> </a>" + "\",\""
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

    protected void anomalieRimborsiPF_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
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

        ArrayList<Rimborso_PrgFormativo_Dt> list = Action.getListRimborsi_PrgFormativoDT(politica, "E2", ente, from, to);
        Map<String, String[]> adinfo = Action.get_ADInfosProgettoFormativo_DT();

        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        if (list.size() > 0) {
            for (Rimborso_PrgFormativo_Dt t : list) {
                try {
                    valore = valore + " [ \"";
                    az = "<input type='hidden' id='" + t.getIdrimborso_prg_dt() + "_pol' value='" + t.getPolitica() + "' />"
                            + "<a style='color: white;' class='bg-green-jungle fa fa-check btn btn-default popovers' data-toggle='modal' onclick='return setIDAccettaRimborsi(" + t.getIdrimborso_prg_dt() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Rimborsi' ><font color='white' face='verdana'> Accetta</font></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getIdrimborso_prg_dt() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Rigetta Rimborsi' ><font color='white' face='verdana'> Rigetta</font> </a>";
                    valore += az + "\",\""
                            + "<a href='reg_showTirocinantiRimborsoDT.jsp?idrimborso=" + t.getIdrimborso_prg_dt() + "&politica=" + t.getPolitica() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Progetti Formativi'><i class='btn btn-default bg-blue-chambray fa fa-file-text-o' style='color: white;' ><font face='verdana'> Lista Progetti Formativi</font></i></a>" + "\",\""
                            + adinfo.get(t.getIdrimborso_prg_dt())[0] + "\",\""
                            + adinfo.get(t.getIdrimborso_prg_dt())[1] + "\",\""
                            + t.getMotivo() + "\",\""
                            + sdf2.format(sdf1.parse(t.getData_up())) + "\",\""
                            + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(adinfo.get(t.getIdrimborso_prg_dt())[2], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. AD/AU' ><font color='white' face='verdana'></font> </a> " + "\",\""
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
        ArrayList<RegistroDt> list = Action.getListRegistriDT(politica, "S", ente, from, to, nome, cognome, cf);
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
                            + "<a style='color: white;' class='bg-green fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setAccettaID(" + t.getId() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Registro' ><font color='white' face='verdana'></font></a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white; ' class='bg-red fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getOre() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Scarta Registro Definitivamente' ><font color='white' face='verdana'></font> </a>";

                    prg = "<a style='color: white; ' class='bg-blue-hoki fa fa-folder btn btn-default popovers' onclick='return showPrg(" + t.getProgetto_formativo_dt() + ")' data-trigger='hover' data-placement='top' data-content='Dati Progetto Formativo' ><font color='white' face='verdana'></font> </a>";

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

    protected void searchAnomalieRegistri_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        ArrayList<RegistroDt> list = Action.getListRegistriDT(politica, "E2", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String az = "";
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
                            + "<a style='color: white;' class='bg-green fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setAccettaID(" + t.getId() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta Registro' ><font color='white' face='verdana'></font></a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white; ' class='bg-red fa fa-times-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getOre() + ")' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Scarta Registro Definitivamente' ><font color='white' face='verdana'></font> </a>";
                    valore = valore + " [ \"";
                    valore += az + "\",\""
                            + Utility.correggi(t.getMotivo()) + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
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

    protected void liquidazioneRegistri_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        ArrayList<RegistroDt> list = Action.getListRegistriDT(politica, "R2", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String az = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (RegistroDt t : list) {
                try {
                    docs = "<a style='color: white;' class='bg-green-meadow fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getChecklist(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Checklist Revisore' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Registro' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue-chambray fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_lavoratore(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> ";
                    //MODALS
                    az = "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                            + "<input type='checkbox' class='group-checkable' data-set='#sample_2 .checkboxes' id='" + t.getId() + "' />"//onclick='return seleriga(this.id);'
                            + "<span></span>"
                            + "</label>";
                    valore = valore + " [ \"";
                    valore += az + "\",\""
                            + "<a href='modResidenza_XML.jsp?id=" + t.getId_lavoratore() + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Modifica Residenza Destinatario'><i class='btn btn-default bg-red-flamingo fa fa-pencil-square-o' style='color: white;' ></i></a>" + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getOre_rev() + " h" + "\",\""
                            + "&euro; " + String.format("%1$,.2f", t.getTot_erogato()) + "\",\""
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

    protected void tirocinantiINPS_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        ArrayList<RegistroDt> list = Action.getListRegistriDT(politica, "W", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String az = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String path = Action.getPath("inps");
        if (list.size() > 0) {
            for (RegistroDt t : list) {
                try {
                    docs = "<a style='color: white;' class='bg-yellow-gold fa fa-file-text-o btn btn-default popovers' href='OperazioniGeneral?type=6&path=" + StringUtils.replace(path + t.getXml_liquidazione(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Scarica File XML INPS' ><font color='white' face='verdana'> File XML INPS</font> </a> ";

                    //MODALS
                    az = "<input type='hidden' id='" + t.getId() + "_pol' value='" + t.getPolitica() + "' />"
                            + "<a style='color: white;' class='bg-green-jungle fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setAccettaID(" + t.getId() + ")' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Liquidato con successo' ><font color='white' face='verdana'> OK</font></a> "
                            + "<a style='color: white; ' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setRifiutaID(" + t.getId() + ")' href='#rifiutamodal' data-trigger='hover' data-placement='top' data-content='Non liquidato' ><font color='white' face='verdana'> KO</font> </a>"
                            + "<a style='color: white;' class='bg-red-thunderbird fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente' ><font color='white' face='verdana'> Scarta</font></a> ";

                    valore = valore + " [ \"";
                    valore += az + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + docs + "\",\""
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

    protected void registriLiquidatiOK_DT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        ArrayList<RegistroDt> list = Action.getListRegistriDT(politica, "P", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String xml = "";
        String az = "";
        String path = Action.getPath("inps");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        if (list.size() > 0) {
            for (RegistroDt t : list) {
                try {
                    docs = "<a style='color: white;' class='bg-green-meadow fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getChecklist(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Checklist Revisore' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-yellow-gold fa fa-file-text-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getFile(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Registro' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-green-sharp fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_tutor(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Tutor' ><font color='white' face='verdana'></font> </a> "
                            + "<a style='color: white;' class='bg-blue-chambray fa fa-file-image-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(t.getDoc_lavoratore(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza C.I. Destinatario' ><font color='white' face='verdana'></font> </a> ";
                    xml = "<a style='color: white;' class='bg-green-jungle fa fa-refresh btn btn-default popovers' href='OperazioniGeneral?type=6&path=" + StringUtils.replace(path + t.getXml_liquidazione(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Scarica File XML INPS' ><font color='white' face='verdana'> Consulta Xml</font> </a> ";

                    valore = valore + " [ \"";
                    valore += Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + t.getOre_rev() + " h" + "\",\""
                            + "&euro; " + String.format("%1$,.2f", t.getTot_erogato()) + "\",\""
                            + docs + "\",\""
                            + xml + "\",\""
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

    protected void vouchersRimborso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String esiti = request.getParameter("esi");
        String idrimborso = request.getParameter("idrimborso");
        ArrayList<Voucher> list = Action.getListVouchers(idrimborso);

        Contratto contr = new Contratto();
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String az = "";
        String docs = "-";
        String ore = "-";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        if (list.size() > 0) {
            for (Voucher t : list) {
                ore = String.valueOf(t.getOre()) + " h";
                String[] listdocs = Action.getListDocsTirocinante_Voucher(String.valueOf(t.getId()));
                docs = "<a style='color: white;' class='bg-blue-chambray fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[5], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Allegato 8' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-green-sharp fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[6], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Attestato' ><font color='white' face='verdana'></font> </a> "
                        + "<a style='color: white;' class='bg-blue fa fa-file-pdf-o btn btn-default popovers fancyBoxRaf' href='OperazioniGeneral?type=5&path=" + StringUtils.replace(listdocs[7], "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Visualizza Delega' ><font color='white' face='verdana'></font> </a> ";

                valore = valore + " [ \"";
                //az = "<a style='color: white;' class='bg-yellow-gold  fa fa-exclamation btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId()+ ","+t.getRimborso()+")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Rigetta Rimborso Destinatario' ><font color='white' face='verdana'> Rigetta</font></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setIDDisactive(" + t.getId()+ ","+t.getRimborso()+")' href='#rifiutamodal' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Rimborso' ><font color='white' face='verdana'> Scarta</font> </a>";
                az = "<a style='color: white;' class='bg-yellow-gold  fa fa-exclamation btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Segnala Criticità Rimborso' ><font color='white' face='verdana'> Rigetta</font></a> <a style='color: white;' class='bg-red-thunderbird fa fa-ban btn btn-default popovers' data-toggle='modal' onclick='return setScartaID2(" + t.getId() + "," + t.getRimborso() + ")' href='#scartamodal1' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente Rimborso' ><font color='white' face='verdana'> Scarta</font> </a>";

                if (esiti.equals("OK")) {
                    valore += t.getCognome() + " " + t.getNome() + "\",\""
                            + t.getCf() + "\",\""
                            + ore + "\",\""
                            + "&euro; " + String.format("%1$,.2f", t.getVoucher()) + "\",\""
                            + docs + "\",\""
                            + "\"],";
                } else {
                    valore += az + "\",\""
                            + t.getCognome() + " " + t.getNome() + "\",\""
                            + t.getCf() + "\",\""
                            + ore + "\",\""
                            + "&euro; " + String.format("%1$,.2f", t.getVoucher()) + "\",\""
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
                        rimb_sing = "<a href='reg_showVoucher.jsp?idrimborso=" + t.getIdrimborso() + "&politica=" + politica + "&esi=" + esi + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Rimborsi Voucher'><i class='btn btn-default bg-blue fa fa-ticket' style='color: white; display: block;' ><font face='verdana'> Lista Rimborsi</font></i></a>";
                    } else {
                        rimb_sing = "<a href='reg_showTirocinantiRimborso.jsp?idrimborso=" + t.getIdrimborso() + "&politica=" + politica + "&esi=" + esi + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Rimborsi'><i class='btn btn-default bg-blue fa fa-list-ul' style='color: white; display: block;' ><font face='verdana'> Lista Rimborsi</font></i></a>";
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
                        rs = "<a href='reg_showTirocinantiRimborsoM5.jsp?idrimborso=" + t.getIdrimborso_prg() + "&politica=" + politica + "&esi=" + esi + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Progetti Formativi'><i class='btn btn-default bg-blue fa fa-file-text-o' style='color: white; cursor: default; display: block;' ><font face='verdana'> Progetti Formativi</font></i></a>";
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
                        rs = "<a href='reg_showTirocinantiRimborsoDT.jsp?idrimborso=" + t.getIdrimborso_prg_dt() + "&politica=" + t.getPolitica() + "&esi=" + esi + "' class='popovers fancyBoxRafRef' data-trigger='hover' data-placement='top' data-content='Controlla Singoli Progetti Formativi'><i class='btn btn-default bg-blue fa fa-file-text-o' style='color: white; cursor: default; display: block;' ><font face='verdana'> Progetti Formativi</font></i></a>";
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
                co = "<li><a href='show_CO.jsp?idente=nessuno&idlav=" + t.getCdnlavoratore() + "' class='fancyBoxDon' ><i class='btn btn-default bg-blue fa fa-clone' style='padding-right:23px; color:white;'></i>Visualizza CO</a></li>";
                politiche = "<li><a href='show_politichelavoratore.jsp?idlav=" + t.getCdnlavoratore() + "' class='fancyBoxRaf' ><i class='btn btn-default bg-yellow-gold fa fa-list-ul' style='padding-right:23px; color:white;'></i>Visualizza Politiche</a></li>";
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
                            + ((t.getStato().equals("I") || doc.equals("")) ? "No Doc." + "\",\"" : doc);

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

    protected void tirocinantiINPS_2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if (cognome == null || ente.equals("cognome")) {
            cognome = "";
        }
        if (nome == null || nome.equals("null")) {
            nome = "";
        }
        if (cf == null || cf.equals("null")) {
            cf = "";
        }
        ArrayList<Registro> list = Action.getListRegistri(politica, "W", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String az = "";
        String checkbox = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String path = Action.getPath("inps");
        if (list.size() > 0) {
            for (Registro t : list) {
                try {
                    docs = "<a style='color: white;' class='bg-yellow-gold fa fa-file-text-o btn btn-default popovers' href='OperazioniGeneral?type=6&path=" + StringUtils.replace(path + t.getXml_liquidazione(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Scarica File XML INPS' ><font color='white' face='verdana'> File XML INPS</font> </a> ";

                    //MODALS
                    checkbox = "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                            + "<input type='checkbox' class='group-checkable' data-set='#sample_2 .checkboxes' id='" + t.getId() + "' />"//onclick='return seleriga(this.id);'
                            + "<span></span>"
                            + "</label>" + "\",\"";

                    az = "<a style='color: white; ' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setRifiutaID(" + t.getId() + ")' href='#rifiutamodal' data-trigger='hover' data-placement='top' data-content='Non liquidato' ><font color='white' face='verdana'> KO</font> </a>"
                            + "<a style='color: white;' class='bg-red-thunderbird fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente' ><font color='white' face='verdana'> Scarta</font></a> ";
                    valore = valore + " [ \"";
                    valore += checkbox
                            + az + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + docs + "\",\""
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

    protected void tirocinantiINPS_DT_2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        ArrayList<RegistroDt> list = Action.getListRegistriDT(politica, "W", ente, from, to, nome, cognome, cf);
        PrintWriter out = response.getWriter();
        String inizio = "{ \"aaData\": [";
        String fine = "] }";
        String valore = "";
        String docs = "";
        String az = "";
        String checkbox = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String path = Action.getPath("inps");
        if (list.size() > 0) {
            for (RegistroDt t : list) {
                try {
                    docs = "<a style='color: white;' class='bg-yellow-gold fa fa-file-text-o btn btn-default popovers' href='OperazioniGeneral?type=6&path=" + StringUtils.replace(path + t.getXml_liquidazione(), "\\", "/") + "' data-trigger='hover' data-placement='top' data-content='Scarica File XML INPS' ><font color='white' face='verdana'> File XML INPS</font> </a> ";

                    //MODALS
                    az = "<input type='hidden' id='" + t.getId() + "_pol' value='" + t.getPolitica() + "' />"
                            + "<a style='color: white; ' class='bg-yellow-gold fa fa-exclamation-circle btn btn-default popovers' data-toggle='modal' onclick='return setRifiutaID(" + t.getId() + ")' href='#rifiutamodal' data-trigger='hover' data-placement='top' data-content='Non liquidato' ><font color='white' face='verdana'> KO</font> </a>"
                            + "<a style='color: white;' class='bg-red-thunderbird fa fa-check-circle btn btn-default popovers' data-toggle='modal' onclick='return setScartaID(" + t.getId() + ")' href='#scartamodal' data-trigger='hover' data-placement='top' data-content='Scarta Definitivamente' ><font color='white' face='verdana'> Scarta</font></a> ";

                    checkbox = "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                            + "<input type='checkbox' class='group-checkable' data-set='#sample_2 .checkboxes' id='" + t.getId() + "' />"//onclick='return seleriga(this.id);'
                            + "<span></span>"
                            + "</label>" + "\",\"";

                    valore = valore + " [ \"";
                    valore += checkbox
                            + az + "\",\""
                            + Utility.correggi(t.getLavoratore()) + "\",\""
                            + Utility.correggi(t.getCf_lavoratore()) + "\",\""
                            + Utility.correggi(t.getEnte()) + "\",\""
                            + "Mese n°" + t.getMese() + "\",\""
                            + sdf2.format(sdf1.parse(t.getDatainizio())) + "<br>" + sdf2.format(sdf1.parse(t.getDatafine())) + "\",\""
                            + docs + "\",\""
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
                            searchBandi(request, response);
                        }
                        else if (type.equals("2")) {//In 1reg_gestione1B.jsp (sostituita)
                            search1B(request, response);
                        }
                        else if (type.equals("3")) {
                            searchRimborsi(request, response);
                        }
                        else if (type.equals("4")) {
                            liquidaRimborsi(request, response);
                        }
                        else if (type.equals("5")) {
                            gestioneRimborsi(request, response);
                        }
                        else if (type.equals("6")) {
                            politicheRimborso(request, response);
                        }
                        else if (type.equals("7")) {
                            anomalieRimborsi(request, response);
                        }
                        else if (type.equals("8")) {
                            searchScartiPolitiche(request, response);
                        }
                        else if (type.equals("9")) {
                            searchProgettoFormativo(request, response);
                        }
                        else if (type.equals("10")) {
                            gestioneRimborsiPF(request, response);
                        }
                        else if (type.equals("11")) {
                            PrgFormativiRimborso(request, response);
                        }
                        else if (type.equals("12")) {
                            liquidaRimborsiPF(request, response);
                        }
                        else if (type.equals("13")) {
                            anomalieRimborsiPF(request, response);
                        }
                        else if (type.equals("14")) {
                            searchRegistri(request, response);
                        }
                        else if (type.equals("15")) {
                            searchAnomalieRegistri(request, response);
                        }
                        else if (type.equals("16")) {
                            liquidazioneRegistri(request, response);
                        }
                        else if (type.equals("17")) {
                            tirocinantiINPS(request, response);
                        }
                        else if (type.equals("18")) {
                            registriLiquidatiOK(request, response);
                        }
                        else if (type.equals("19")) {
                            searchProgettoFormativo_DT(request, response);
                        }
                        else if (type.equals("20")) {
                            gestioneRimborsiPF_DT(request, response);
                        }
                        else if (type.equals("21")) {
                            PrgFormativiRimborso_DT(request, response);
                        }
                        else if (type.equals("22")) {
                            liquidaRimborsiPF_DT(request, response);
                        }
                        else if (type.equals("23")) {
                            anomalieRimborsiPF_DT(request, response);
                        }
                        else if (type.equals("24")) {
                            searchRegistri_DT(request, response);
                        }
                        else if (type.equals("25")) {
                            searchAnomalieRegistri_DT(request, response);
                        }
                        else if (type.equals("26")) {
                            liquidazioneRegistri_DT(request, response);
                        }
                        else if (type.equals("27")) {
                            tirocinantiINPS_DT(request, response);
                        }
                        else if (type.equals("28")) {
                            registriLiquidatiOK_DT(request, response);
                        }
                        else if (type.equals("29")) {
                            vouchersRimborso(request, response);
                        }
                        else if (type.equals("30")) {
                            showCO(request, response);
                        }
                        else if (type.equals("31")) {
                            gestioneEsiti(request, response);
                        }
                        else if (type.equals("32")) {
                            gestioneEsitiPF(request, response);
                        }
                        else if (type.equals("33")) {
                            gestioneEsitiPF_DT(request, response);
                        }
                        else if (type.equals("34")) {
                            searchEsitiRegistri(request, response);
                        }
                        else if (type.equals("35")) {
                            searchEsitiRegistri_DT(request, response);
                        }
                        else if (type.equals("36")) {
                            searchLavoratori(request, response);
                        }
                        else if (type.equals("37")) {
                            showpoliticheLavoratore(request, response);
                        }
                        else if (type.equals("38")) {
                            searchPrgFormativi(request, response);
                        }
                        else if (type.equals("39")) {
                            searchPrgFormativiDt(request, response);
                        }
                        else if (type.equals("40")) {
                            tirocinantiINPS_2(request, response);
                        }
                        else if (type.equals("41")) {
                            tirocinantiINPS_DT_2(request, response);
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
