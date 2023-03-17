
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.seta.entity.Bando"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.activity.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "index_Regional.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            ArrayList<Bando> b_att = Action.bandiattivi();
            ArrayList<String[]> DT = Action.getListTipoPolitica("1");
            ArrayList<String[]> GG = Action.getListTipoPolitica("2");
            HashMap<String, Integer> rimborsi_GG = Action.getRimborsi_GG();
            HashMap<String, Integer> rimborsi_DT = Action.getRimborsi_DT();
            HashMap<String, Integer> registri = Action.getRegistri_REG();

            int tot_protGG = 0;
            int tot_gestGG = 0;
            int tot_liqGG = 0;
            int tot_anomGG = 0;
            for (String k : rimborsi_GG.keySet()) {
                if (k.contains("-S-")) {
                    tot_protGG += rimborsi_GG.get(k);
                } else if (k.contains("-E2-")) {
                    tot_anomGG += rimborsi_GG.get(k);
                } else if (k.contains("-N-")) {
                    tot_gestGG += rimborsi_GG.get(k);
                } else if (k.contains("-R2-")) {
                    tot_liqGG += rimborsi_GG.get(k);
                }
            }

            int tot_protDT = 0;
            int tot_gestDT = 0;
            int tot_liqDT = 0;
            int tot_anomDT = 0;
            for (String k : rimborsi_DT.keySet()) {
                if (k.contains("-S-")) {
                    tot_protDT += rimborsi_DT.get(k);
                } else if (k.contains("-E2-")) {
                    tot_anomDT += rimborsi_DT.get(k);
                } else if (k.contains("-N-")) {
                    tot_gestDT += rimborsi_DT.get(k);
                } else if (k.contains("-R2-")) {
                    tot_liqDT += rimborsi_DT.get(k);
                }
            }

            int totR_liquGG = 0;
            int totR_gestGG = 0;
            int totR_anomGG = 0;
            int totR_inpsGG = 0;
            int totR_liquDT = 0;
            int totR_gestDT = 0;
            int totR_anomDT = 0;
            int totR_inpsDT = 0;
            for (String k : registri.keySet()) {
                if (k.contains("-W-GG")) {
                    totR_inpsGG += registri.get(k);
                } else if (k.contains("-E2-GG")) {
                    totR_anomGG += registri.get(k);
                } else if (k.contains("-S-GG")) {
                    totR_gestGG += registri.get(k);
                } else if (k.contains("-R2-GG")) {
                    totR_liquGG += registri.get(k);
                } else if (k.contains("-W-DT")) {
                    totR_inpsDT += registri.get(k);
                } else if (k.contains("-E2-DT")) {
                    totR_anomDT += registri.get(k);
                } else if (k.contains("-S-DT")) {
                    totR_gestDT += registri.get(k);
                } else if (k.contains("-R2-DT")) {
                    totR_liquDT += registri.get(k);
                }
            }

            int tot_R = totR_liquDT + totR_gestDT + totR_anomDT + totR_inpsDT + totR_liquGG + totR_gestGG + totR_anomGG + totR_inpsGG;

            int tot_GG = tot_anomGG + tot_liqGG + tot_gestGG + tot_protGG;
            int tot_DT = tot_anomDT + tot_liqDT + tot_gestDT + tot_protDT;

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

            ArrayList<String> avvisi = Action.getMessageToUser();
%>
<html>
    <head>
        <meta charset="utf-8">
        <meta charset="ISO-8859-1" />
        <title>Rendicontazione</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />

        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="assets/seta/fontg/fontsgoogle1.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/clockface/css/clockface.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />

        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <link href="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />

        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />

        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/themes/blue.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />

        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/seta/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/seta/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/seta/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/seta/js/fancy.js"></script> 

        <link rel="stylesheet" href="AdminLTE-2.4.2/dist/css/AdminLTE.css">
        <link rel="shortcut icon" href="assets/seta/img/favicon.ico" />
        <style>
            .visual{
                padding-top: 23px!important;
                padding-left: 45px!important;
            }
            .dashboard-stat .visual > i {
                color: #ffffff!important;
                opacity: .5!important;
                font-size: 155px;
            }
        </style>

        <link href="assets/marquee.css" rel="stylesheet" type="text/css" />

    </head>
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
        <div class="modal fade bs-modal-lg" id="large" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-purple-seance  alert-dismissible">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <font color="white"><h4 class="modal-title">Attenzione</h4></font>
                    </div>
                    <div class="modal-body" id="largetext">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn large btn-danger" data-dismiss="modal" onclick="return dismiss('large');">Chiudi</button>
                    </div>
                </div>
            </div>
        </div>
        <!--            BEGIN HEADER -->
        <%@ include file="menu/header.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu_regional/menu_home.jsp"%>
            <!-- END MENU -->

            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <!-- Main content -->
                    <div class="logo">
                        <img src="assets/seta/img/logos1.png" height="75"  align="right" /> 
                    </div>  
                    <h3 class="page-title" style="color:#2D5F8B"><strong> HOME </strong><small style="color:#2D5F8B"> REGIONE CALABRIA</small> </h3>  
                    <section class="content">
                        <div class="row">
                            <%if (avvisi.size() > 0) {
                                    String mex = "";
                                    for (String s : avvisi) {
                                        mex += "<i class='fa fa-circle'> </i> " + s + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                                    }%>
                            <div class="col-md-12" style="height: 35px;">
                                <div class="bg-yellow-crusta marquee">
                                    <div>
                                        <span><b><font size="4"><%=mex%></font></b></span>
                                        <span><b><font size="4"><%=mex%></font></b></span>
                                    </div>
                                </div>
                            </div>
                            <%}%>

                            <%for (int j = 0; j < b_att.size(); j++) {
                                    if (b_att.get(j).getTipo_bando().equals("2")) {%>
                            <div class="col-md-6">
                                <div class="dashboard-stat dashboard-stat-v2 bg-green-jungle" >
                                    <div>
                                        <a class="visual fancyBoxRafFull popovers" href="bandoReport.jsp?idbando=<%=b_att.get(j).getIdbando()%>" data-toggle="modal" data-trigger="hover" data-placement="top" data-content="Visualizza Report"><i class="fa fa-line-chart"></i></a>
                                    </div>
                                    <font color="white"> <h4 style="padding-left: 180px; font-size:2.5vh;">Bando Attivo Garanzia G. : <b><%=b_att.get(j).getTitolo()%></b></h4>
                                    <div class="details" style="position:inherit;">
                                        <div class="desc">
                                            <h4 style="font-size:2.5vh;">
                                                <%if (b_att.get(j).getFlag_sportello().equals("N")) {%>
                                                Data inizio: <b><%= sdf2.format(sdf1.parse(b_att.get(j).getData_inizio()))%></b><br>
                                                Data fine: <b><%= sdf2.format(sdf1.parse(b_att.get(j).getData_fine()))%></b>
                                                <%} else {%>
                                                Bando a sportello<br>
                                                Data inizio: <b><%= sdf2.format(sdf1.parse(b_att.get(j).getData_inizio()))%></b>
                                                <%}%>
                                            </h4>
                                        </div>
                                    </div>
                                    <div class="details" style="position:inherit;">
                                        <div class="desc">
                                            <h4 style="font-size:2.5vh;">
                                                Budget Stanziato: <b> &#8364 <%=String.format("%1$,.2f", Double.parseDouble(b_att.get(j).getBudget()))%></b><br>
                                                Budget Previsionale: <b> &#8364 <%=String.format("%1$,.2f", Double.parseDouble(b_att.get(j).getBudget_previsione()))%></b><br>
                                                Budget Attuale: <b> &#8364 <%=String.format("%1$,.2f", Double.parseDouble(b_att.get(j).getBudget_attuale()))%></b>
                                            </h4>
                                        </div>
                                    </div>
                                    </font>
                                </div>
                            </div>
                            <%} else if (b_att.get(j).getTipo_bando().equals("1")) {%>
                            <div class="col-md-6">
                                <div class="dashboard-stat dashboard-stat-v2 bg-green-jungle" >
                                    <div>
                                        <a class="visual fancyBoxRafFull popovers" href="bandoReport.jsp?idbando=<%=b_att.get(j).getIdbando()%>" data-toggle="modal" data-trigger="hover" data-placement="top" data-content="Visualizza Report"><i class="fa fa-line-chart"></i></a>
                                    </div>
                                    <font color="white"> <h4 style="padding-left: 180px; font-size:2.5vh;">Bando Attivo Dote Lavoro: <b><%=b_att.get(j).getTitolo()%></b></h4>
                                    <div class="details" style="position:inherit;">
                                        <div class="desc">
                                            <h4 style="font-size:2.5vh;">
                                                <%if (b_att.get(j).getFlag_sportello().equals("N")) {%>
                                                Data inizio: <b><%= sdf2.format(sdf1.parse(b_att.get(j).getData_inizio()))%></b><br>
                                                Data fine: <b><%= sdf2.format(sdf1.parse(b_att.get(j).getData_fine()))%></b>
                                                <%} else {%>
                                                Bando a sportello<br>
                                                Data inizio: <b><%= sdf2.format(sdf1.parse(b_att.get(j).getData_inizio()))%></b>
                                                <%}%>
                                            </h4>
                                        </div>
                                    </div>
                                    <div class="details" style="position:inherit;">
                                        <div class="desc">
                                            <h4 style="font-size:2.5vh;">
                                                Budget Stanziato: <b> &#8364 <%=String.format("%1$,.2f", Double.parseDouble(b_att.get(j).getBudget()))%></b><br>
                                                Budget Previsionale: <b> &#8364 <%=String.format("%1$,.2f", Double.parseDouble(b_att.get(j).getBudget_previsione()))%></b><br>
                                                Budget Attuale: <b> &#8364 <%=String.format("%1$,.2f", Double.parseDouble(b_att.get(j).getBudget_attuale()))%></b>
                                            </h4>
                                        </div>
                                    </div>
                                    </font>
                                </div>
                            </div>
                            <%}
                                }%> 
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="mt-element-list">
                                    <div class="mt-list-container list-simple ext-1 group">
                                        <div class="list-toggle-container" >
                                            <div class="list-toggle small-box bg-blue" style="margin-bottom: 0px;padding: 0px;">
                                                <div class="inner uppercase">                                        
                                                    <h4 style="font-size:2.5vh;"><strong>GARANZIA GIOVANI</strong></h4>
                                                    <%if (tot_GG > 0) {%>
                                                    <h3 class="circle bg-red-thunderbird" style="padding-top: 3px; width: 100px; font-size:5vh;">
                                                        <center><%=tot_GG%> <i class="fa fa-exclamation"></i></center>
                                                    </h3> 
                                                    <%} else {%>
                                                    <h3 style="padding-top: 3px; font-size:5vh;">&nbsp;</h3>
                                                    <%}%>
                                                </div>
                                                <div align="right" class="icon">
                                                    <%if (tot_GG > 0) {%>
                                                    <i><img src="assets/seta/img/GGKO.png" width="30%"/></i>
                                                        <%} else {%>
                                                    <i><img src="assets/seta/img/GGOK.png" width="30%" /></i>
                                                        <%}%>
                                                </div>
                                                <a href="#pending-simple" data-toggle="collapse" aria-expanded="false" class="small-box-footer" style="font: sans-serif;font-weight:normal;">
                                                    Visualizza <i class="fa fa-arrow-circle-down"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="panel-collapse collapse" id="pending-simple">
                                            <ul>
                                                <%if (tot_protGG > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <!--i class="fa fa-exclamation-circle" style="color:#E87E04;font-size: 30px;"></i-->
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=tot_protGG%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#protGG" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">Da Protocollare</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="protGG">
                                                    <ul>
                                                        <%int color = 0;
                                                            for (int i = 0; i < GG.size(); i++) {
                                                                for (String k : rimborsi_GG.keySet()) {
                                                                    if (k.equals(GG.get(i)[0] + "-S-")) {
                                                                        String pGG = "reg_protocollo";
                                                                        if (GG.get(i)[0].equals("A01")) {
                                                                            pGG += "1B.jsp";
                                                                        } else if (GG.get(i)[0].equals("B03")) {
                                                                            pGG += "M3.jsp";
                                                                        } else if (GG.get(i)[0].equals("B05")) {
                                                                            pGG += "1C.jsp";
                                                                        } else if (GG.get(i)[0].equals("C06")) {
                                                                            pGG = "regE_protocolloM5.jsp";
                                                                        }%>
                                                        <li class="mt-list-item" style="
                                                            <%if (color % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-legal" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=rimborsi_GG.get(k)%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=pGG%>" style="color:#1c6cc7;font-size: 15px;"><%=GG.get(i)[1]%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%color++;
                                                                    }
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <!--i class="fa fa-check-circle" style="color:#1BBC9B;font-size: 30px;"></i-->
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7; font-size:2vh;">Da Protocollare</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                                <%if (tot_gestGG > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=tot_gestGG%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#elabGG" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">In Attesa Di Verifica</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="elabGG">
                                                    <ul>
                                                        <%int color = 0;
                                                            for (int i = 0; i < GG.size(); i++) {
                                                                for (String k : rimborsi_GG.keySet()) {
                                                                    if (k.equals(GG.get(i)[0] + "-N-")) {
                                                                        String eGG = "reg_gestione";
                                                                        if (GG.get(i)[0].equals("A01")) {
                                                                            eGG += "1B.jsp";
                                                                        } else if (GG.get(i)[0].equals("B03")) {
                                                                            eGG += "M3.jsp";
                                                                        } else if (GG.get(i)[0].equals("B05")) {
                                                                            eGG += "1C.jsp";
                                                                        } else if (GG.get(i)[0].equals("C06")) {
                                                                            eGG = "regE_gestioneM5.jsp";
                                                                        }%>
                                                        <li class="mt-list-item" style="
                                                            <%if (color % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-list-ul" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=rimborsi_GG.get(k)%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=eGG%>" style="color:#1c6cc7;font-size: 15px;"><%=GG.get(i)[1]%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%color++;
                                                                    }
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7; font-size:2vh;">In Attesa Di Verifica</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                                <%if (tot_liqGG > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=tot_liqGG%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#liqGG" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">Da Liquidare</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="liqGG">
                                                    <ul>
                                                        <%int color = 0;
                                                            for (int i = 0; i < GG.size(); i++) {
                                                                for (String k : rimborsi_GG.keySet()) {
                                                                    if (k.equals(GG.get(i)[0] + "-R2-")) {
                                                                        String lGG = "reg_liquidazione";
                                                                        if (GG.get(i)[0].equals("A01")) {
                                                                            lGG += "1B.jsp";
                                                                        } else if (GG.get(i)[0].equals("B03")) {
                                                                            lGG += "M3.jsp";
                                                                        } else if (GG.get(i)[0].equals("B05")) {
                                                                            lGG += "1C.jsp";
                                                                        } else if (GG.get(i)[0].equals("C06")) {
                                                                            lGG = "regE_liquidazioneM5.jsp";
                                                                        }%>
                                                        <li class="mt-list-item" style="
                                                            <%if (color % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-money" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=rimborsi_GG.get(k)%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=lGG%>" style="color:#1c6cc7;font-size: 15px;"><%=GG.get(i)[1]%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%color++;
                                                                    }
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7;font-size:2vh;">Da Liquidare</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                                <%if (tot_anomGG > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=tot_anomGG%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#anomGG" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">Anomalie</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="anomGG">
                                                    <ul>
                                                        <%int color = 0;
                                                            for (int i = 0; i < GG.size(); i++) {
                                                                for (String k : rimborsi_GG.keySet()) {
                                                                    if (k.equals(GG.get(i)[0] + "-E2-")) {
                                                                        String aGG = "reg_anomalie";
                                                                        if (GG.get(i)[0].equals("A01")) {
                                                                            aGG += "1B.jsp";
                                                                        } else if (GG.get(i)[0].equals("B03")) {
                                                                            aGG += "M3.jsp";
                                                                        } else if (GG.get(i)[0].equals("B05")) {
                                                                            aGG += "1C.jsp";
                                                                        } else if (GG.get(i)[0].equals("C06")) {
                                                                            aGG = "regE_anomalieM5.jsp";
                                                                        }%>
                                                        <li class="mt-list-item" style="
                                                            <%if (color % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-warning" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=rimborsi_GG.get(k)%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=aGG%>" style="color:#1c6cc7;font-size: 15px;"><%=GG.get(i)[1]%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%color++;
                                                                    }
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7;font-size:2vh;">Anomalie</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mt-element-list">
                                    <div class="mt-list-container list-simple ext-1 group">
                                        <div class="list-toggle-container" >
                                            <div class="list-toggle small-box bg-blue" style="margin-bottom: 0px;padding: 0px;">
                                                <div class="inner uppercase">                                        
                                                    <h4  style="font-size:2.5vh;"><strong>DOTE LAVORO</strong></h4>
                                                    <%if (tot_DT > 0) {%>
                                                    <h3 class="circle bg-red-thunderbird" style="padding-top: 3px; width: 100px; font-size:5vh;">
                                                        <center><%=tot_DT%> <i class="fa fa-exclamation"></i></center>
                                                    </h3> 
                                                    <%} else {%>
                                                    <h3 style="padding-top: 3px;font-size:5vh;">&nbsp;</h3>
                                                    <%}%>
                                                </div>
                                                <div align="right" class="icon" >
                                                    <%if (tot_DT > 0) {%>
                                                    <i><img src="assets/seta/img/DTKO.png" width="30%" /></i>
                                                        <%} else {%>
                                                    <i><img src="assets/seta/img/DTOK.png" width="30%" /></i>
                                                        <%}%>
                                                </div>
                                                <a href="#pending-simple1" data-toggle="collapse" aria-expanded="false" class="small-box-footer" style="font: sans-serif;font-weight:normal;">
                                                    Visualizza <i class="fa fa-arrow-circle-down"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="panel-collapse collapse" id="pending-simple1">
                                            <ul>
                                                <%if (tot_protDT > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=tot_protDT%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#protDT" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">Da Protocollare</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="protDT">
                                                    <ul>
                                                        <%int color = 0;
                                                            for (int i = 0; i < DT.size(); i++) {
                                                                for (String k : rimborsi_DT.keySet()) {
                                                                    if (k.equals(DT.get(i)[0] + "-S-")) {
                                                                        String pDT = "reg_protocollo";
                                                                        if (DT.get(i)[0].equals("B1")) {
                                                                            pDT += "B1DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("B2")) {
                                                                            pDT += "B2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("B3")) {
                                                                            pDT += "B3DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C1")) {
                                                                            pDT += "C1DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C2")) {
                                                                            pDT += "C2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("D2")) {
                                                                            pDT += "D2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("D5")) {
                                                                            pDT += "D5DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C06")) {
                                                                            pDT = "regE_protocolloDT.jsp";
                                                                        }%>
                                                        <li class="mt-list-item" style="
                                                            <%if (color % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-legal" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=rimborsi_DT.get(k)%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=pDT%>" style="color:#1c6cc7;font-size: 15px;"><%=DT.get(i)[1]%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%color++;
                                                                    }
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7; font-size:2vh;">Da Protocollare</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                                <%if (tot_gestDT > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=tot_gestDT%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#elabDT" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">In Attesa Di Verifica</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="elabDT">
                                                    <ul>
                                                        <%int color = 0;
                                                            for (int i = 0; i < DT.size(); i++) {
                                                                for (String k : rimborsi_DT.keySet()) {
                                                                    if (k.equals(DT.get(i)[0] + "-N-")) {
                                                                        String eDT = "reg_gestione";
                                                                        if (DT.get(i)[0].equals("B1")) {
                                                                            eDT += "B1DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("B2")) {
                                                                            eDT += "B2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("B3")) {
                                                                            eDT += "B3DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C1")) {
                                                                            eDT += "C1DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C2")) {
                                                                            eDT += "C2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("D2")) {
                                                                            eDT += "D2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("D5")) {
                                                                            eDT += "D5DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C06")) {
                                                                            eDT = "regE_gestioneDT.jsp";
                                                                        }%>
                                                        <li class="mt-list-item" style="
                                                            <%if (color % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-list-ul" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=rimborsi_DT.get(k)%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=eDT%>" style="color:#1c6cc7;font-size: 15px;"><%=DT.get(i)[1]%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%color++;
                                                                    }
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7;font-size:2vh;">In Attesa Di Verifica</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                                <%if (tot_liqDT > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=tot_liqDT%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#liqDT" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">Da Liquidare</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="liqDT">
                                                    <ul>
                                                        <%int color = 0;
                                                            for (int i = 0; i < DT.size(); i++) {
                                                                for (String k : rimborsi_DT.keySet()) {
                                                                    if (k.equals(DT.get(i)[0] + "-R2-")) {
                                                                        String lDT = "reg_liquidazione";
                                                                        if (DT.get(i)[0].equals("B1")) {
                                                                            lDT += "B1DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("B2")) {
                                                                            lDT += "B2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("B3")) {
                                                                            lDT += "B3DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C1")) {
                                                                            lDT += "C1DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C2")) {
                                                                            lDT += "C2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("D2")) {
                                                                            lDT += "D2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("D5")) {
                                                                            lDT += "D5DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C06")) {
                                                                            lDT = "regE_liquidazioneDT.jsp";
                                                                        }%>
                                                        <li class="mt-list-item" style="
                                                            <%if (color % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-money" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=rimborsi_DT.get(k)%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=lDT%> " style="color:#1c6cc7;font-size: 15px;"><%=DT.get(i)[1]%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%color++;
                                                                    }
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7;font-size:2vh;">Da Liquidare</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                                <%if (tot_anomDT > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=tot_anomDT%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#anomDT" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">Anomalie</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="anomDT">
                                                    <ul>
                                                        <% int color = 0;
                                                            for (int i = 0; i < DT.size(); i++) {
                                                                for (String k : rimborsi_DT.keySet()) {
                                                                    if (k.equals(DT.get(i)[0] + "-E2-")) {
                                                                        String eDT = "reg_elabora";
                                                                        if (DT.get(i)[0].equals("B1")) {
                                                                            eDT += "B1DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("B2")) {
                                                                            eDT += "B2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("B3")) {
                                                                            eDT += "B3DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C1")) {
                                                                            eDT += "C1DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C2")) {
                                                                            eDT += "C2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("D2")) {
                                                                            eDT += "D2DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("D5")) {
                                                                            eDT += "D5DT.jsp";
                                                                        } else if (DT.get(i)[0].equals("C06")) {
                                                                            eDT = "regE_elaboraDT.jsp";
                                                                        }%>
                                                        <li class="mt-list-item" style="
                                                            <%if (color % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-warning" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=rimborsi_DT.get(k)%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=eDT%>" style="color:#1c6cc7;font-size: 15px;"><%=DT.get(i)[1]%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%color++;
                                                                    }
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7;font-size:2vh;">Anomalie</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mt-element-list">
                                    <div class="mt-list-container list-simple ext-1 group">
                                        <div class="list-toggle-container" >
                                            <div class="list-toggle small-box bg-blue" style="margin-bottom: 0px;padding: 0px;">
                                                <div class="inner uppercase">                                        
                                                    <h4 style="font-size:2.5vh;"><strong>REGISTRI PRG. FORMATIVI</strong></h4>
                                                    <%if (tot_R > 0) {%>
                                                    <h3 class="circle bg-red-thunderbird" style="padding-top: 3px; width: 100px; font-size:5vh;">
                                                        <center><%=tot_R%> <i class="fa fa-exclamation"></i></center>
                                                    </h3> 
                                                    <%} else {%>
                                                    <h3 style="padding-top: 3px; font-size:5vh;">&nbsp;</h3>
                                                    <%}%>
                                                </div>
                                                <div align="right" class="icon">
                                                    <%if (tot_R > 0) {%>
                                                    <i><img src="assets/seta/img/PFKO.png" width="30%"/></i>
                                                        <%} else {%>
                                                    <i><img src="assets/seta/img/PFOK.png" width="30%"/></i>
                                                        <%}%>
                                                </div>
                                                <a href="#pending-simple3" data-toggle="collapse" aria-expanded="false" class="small-box-footer" style="font: sans-serif;font-weight:normal;">
                                                    Visualizza <i class="fa fa-arrow-circle-down"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="panel-collapse collapse" id="pending-simple3">
                                            <ul>
                                                <%if ((totR_gestDT + totR_gestGG) > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=totR_gestDT + totR_gestGG%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#elabR" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">In Attesa Di Verifica</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="elabR">
                                                    <ul>
                                                        <%      int i = 0;
                                                            for (String k : registri.keySet()) {
                                                                String eR = "regT_elabora";
                                                                String descr = "";
                                                                int num = 0;
                                                                if (k.contains("-S-")) {
                                                                    if (k.contains("-S-DT")) {
                                                                        num = totR_gestDT;
                                                                        descr = "DT - Accomp.al tirocinio";
                                                                        eR += "DT.jsp";
                                                                    } else if (k.contains("-S-GG")) {
                                                                        eR += "M5.jsp";
                                                                        num = totR_gestGG;
                                                                        descr = "GG - Misura 5";
                                                                    } %>
                                                        <li class="mt-list-item" style="
                                                            <%if (i % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>

                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-list-ul" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=num%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=eR%>" style="color:#1c6cc7;font-size: 15px;"><%=descr%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%i++;
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7;font-size:2vh;">In Attesa Di Verifica</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                                <%if ((totR_liquDT + totR_liquGG) > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=totR_liquDT + totR_liquGG%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#liquR" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">Da Liquidare</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="liquR">
                                                    <ul>
                                                        <%      int i = 0;
                                                            for (String k : registri.keySet()) {
                                                                String lR = "regT_liquidazione";
                                                                String descr = "";
                                                                int num = 0;
                                                                if (k.contains("-R2-")) {
                                                                    if (k.contains("-R2-DT")) {
                                                                        num = totR_liquDT;
                                                                        descr = "DT - Accomp.al tirocinio";
                                                                        lR += "DT.jsp";
                                                                    } else if (k.contains("-R2-GG")) {
                                                                        lR += "M5.jsp";
                                                                        num = totR_liquGG;
                                                                        descr = "GG - Misura 5";
                                                                    } %>
                                                        <li class="mt-list-item" style="
                                                            <%if (i % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-money" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=num%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=lR%>" style="color:#1c6cc7;font-size: 15px;"><%=descr%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%i++;
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7;font-size:2vh;">Da Liquidare</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                                <%if ((totR_anomDT + totR_anomGG) > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=totR_anomDT + totR_anomGG%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#anomR" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">Anomalie</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="anomR">
                                                    <ul>
                                                        <%      int i = 0;
                                                            for (String k : registri.keySet()) {
                                                                String aR = "regT_anomalie";
                                                                String descr = "";
                                                                int num = 0;
                                                                if (k.contains("-E2-")) {
                                                                    if (k.contains("-E2-DT")) {
                                                                        num = totR_anomDT;
                                                                        descr = "DT - Accomp.al tirocinio";
                                                                        aR += "DT.jsp";
                                                                    } else if (k.contains("-E2-GG")) {
                                                                        aR += "M5.jsp";
                                                                        num = totR_anomGG;
                                                                        descr = "GG - Misura 5";
                                                                    } %>
                                                        <li class="mt-list-item" style="
                                                            <%if (i % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-warning" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=num%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=aR%>" style="color:#1c6cc7;font-size: 15px;"><%=descr%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%i++;
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7;font-size:2vh;">Anomalie</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                                <%if ((totR_inpsDT + totR_inpsGG) > 0) {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="fa fa-exclamation-circle" style=" font-size: 3vh; color: orange;"></i>
                                                    </div>
                                                    <div class="list-datetime bold" style="color:#0073b7; font-size: 2.5vh;"> <%=totR_inpsDT + totR_inpsGG%> </div>
                                                    <div class="list-item-content">
                                                        <h3 style="font-size:2vh;">
                                                            <a href="#elabIN" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">Rimborsi INPS</a>
                                                        </h3>
                                                    </div>
                                                </li>
                                                <div class="panel-collapse collapse" id="elabIN">
                                                    <ul>
                                                        <%      int i = 0;
                                                            for (String k : registri.keySet()) {
                                                                String iR = "regI_elabora";
                                                                String descr = "";
                                                                int num = 0;
                                                                if (k.contains("-W-")) {
                                                                    if (k.contains("-W-DT")) {
                                                                        num = totR_inpsDT;
                                                                        descr = "DT - Accomp.al tirocinio";
                                                                        iR += "DT.jsp";
                                                                    } else if (k.contains("-W-GG")) {
                                                                        iR += "M5.jsp";
                                                                        num = totR_inpsGG;
                                                                        descr = "GG - Misura 5";
                                                                    } %>
                                                        <li class="mt-list-item" style="
                                                            <%if (i % 2 == 0) {%>
                                                            background-color: #d3e9f8;
                                                            <%} else {%>
                                                            background-color: #e9f4fc;
                                                            <%}%> 
                                                            border-color: #538bbc #538bbc #e7ecf1;">
                                                            <div class="list-icon-container">
                                                                <i class="fa fa-list-ul" style="color:#1c6cc7;font-size: 20px;"></i>
                                                            </div>
                                                            <div class="list-datetime bold" style="color:#1c6cc7; font-size: 2.5vh;"> <%=num%> </div>
                                                            <div class="list-item-content">
                                                                <h3 style="font-size:2vh;">
                                                                    <a href="<%=iR%>" style="color:#1c6cc7;font-size: 15px;"><%=descr%></a>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%i++;
                                                                }
                                                            }%>
                                                    </ul>
                                                </div>
                                                <%} else {%>
                                                <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                    <div class="list-icon-container" >
                                                        <i class="icon-check" style=" font-size: 3vh;color: #09f107;"></i>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3 style="color:#0073b7; font-size:2vh;">Rimborsi INPS</h3>
                                                    </div>
                                                </li>
                                                <%}%>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>                
                        </div>
                    </section>
                </div>
            </div>
        </div>
        <div class="page-footer">
            <div class="page-footer-inner"><%=et.getFooter()%></div>
            <div class="scroll-to-top">
                <i class="icon-arrow-up"></i>
            </div>
        </div>
        <script src="assets/seta/js/test.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <script src="assets/global/plugins/fancybox/source/jquery.fancybox.pack.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/vendor/tmpl.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/vendor/load-image.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.iframe-transport.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-image.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-audio.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-video.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-ui.js" type="text/javascript"></script>
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
        <!--script src="assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js" type="text/javascript"></script-->
        <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/form-fileupload.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-validation/js/localization/messages_it.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="assets/pages/scripts/components-select2.min.js" type="text/javascript"></script>           
        <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>             
        <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>           
        <script src="assets/seta/js/form-wizard.js" type="text/javascript"></script>          
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>

    </body>
</html>
<%}
    }%>
