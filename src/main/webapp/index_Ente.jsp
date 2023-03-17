
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.seta.activity.Action"%>
<%-- 
Document : index_Regional
Created on : 15-ott-2018, 12.07.08
Author : agodino
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "index_Ente.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
//Etichette et = new Etichette("IT");
            int tot_p = 0;
            HashMap<String, Integer> map = Action.getNumberAllPoliticheE((String) request.getSession().getAttribute("cf_ente"));
            int prg_e = Action.getNumberPrgE((String) request.getSession().getAttribute("cf_ente"), "C06");
            int prg_dt_e = Action.getNumberPrgDtE((String) request.getSession().getAttribute("cf_ente"), "C06");

            for (String key : map.keySet()) {
                tot_p += map.get(key);
            }
            tot_p = tot_p + prg_e + prg_dt_e;

//int id_tutor = Action.getNumberIdCardTutorScadute((int) request.getSession().getAttribute("idente"));
            int tot_id = Action.getNumberIdCardTutorScadute((int) request.getSession().getAttribute("idente"));

            int registri = Action.getNumberAnomaliRegister((String) request.getSession().getAttribute("cf_ente"));
            int registri_dt = Action.getNumberAnomaliRegisterDt((String) request.getSession().getAttribute("cf_ente"));
            int tot_r = registri + registri_dt;
            int tot_gg_i = 0;
            int tot_dt_i = 0;
            HashMap<String, Integer> map_i = Action.getNumberAllPoliticheI((String) request.getSession().getAttribute("cf_ente"));
            int prg_i = Action.getNumberPrgI((String) request.getSession().getAttribute("cf_ente"), "C06");
            int prg_dt_i = Action.getNumberPrgDtI((String) request.getSession().getAttribute("cf_ente"), "C06");

            for (String key : map_i.keySet()) {
//                if (key.equals("A01") || key.equals("B05") || key.equals("B03")) {
                if (key.equals("B05") || key.equals("B03")) {
                    tot_gg_i++;
                } else {
                    tot_dt_i++;
                }
            }
            tot_gg_i += prg_i;
            tot_dt_i += prg_dt_i;

            HashMap<String, Integer> map_k = Action.getSumRimborsiMese((int) request.getSession().getAttribute("idente"), "K");
            HashMap<String, Integer> map_p = Action.getSumRimborsiMese((int) request.getSession().getAttribute("idente"), "P");

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
        <link href="assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />

        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/seta/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/seta/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/seta/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/seta/js/fancy.js"></script> 

        <link rel="stylesheet" href="AdminLTE-2.4.2/dist/css/AdminLTE.css">
        <link rel="shortcut icon" href="assets/seta/img/favicon.ico" />
        <!--<link rel="shortcut icon" href="assets/seta/img/logos1.png">-->
        <style>
            .inner{
                padding-bottom: 5px;
            }
            .small-box-footer{
                font-size: 18px;
            }

            .list-item-content{
                padding-right: 0!important;
            }
        </style>

        <link href="assets/marquee.css" rel="stylesheet" type="text/css" />

    </head>
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
        <div class="modal fade bs-modal-lg" id="large" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-purple-seancealert-dismissible">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <font color="white"><h4 class="modal-title">Attenzione</h4></font>
                    </div>
                    <div class="modal-body" id="largetext">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" data-dismiss="modal" onclick="return dismiss('large');">Chiudi</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- BEGIN HEADER -->
        <%@ include file="menu/header.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"></div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu_ente/menu_home.jsp"%>
            <!-- END MENU -->
            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <!--<div class="loader"></div>        LOADER              -->
                    <!-- Main content -->
                    <div class="logo">
                        <img src="assets/seta/img/logos1.png" height="75"align="right" /> 
                    </div>
                    <h3 class="page-title"><strong> HOME </strong><small> ENTE</small></h3>
                    <div class="row">
                        <%if (avvisi.size() > 0) {
                                String mex = "";
                                for (String s : avvisi) {
                                    mex += "<i class='fa fa-circle'> </i> " + s + "&nbsp;&nbsp;";
                                }%>
                        <div class="col-md-12" style="height: 35px;">
                            <div class="bg-yellow-crusta marquee">
                                <div>
                                    <span><b><font size="4"><%=mex%></font></b></span>
                                    <span><b><font size="4"><%=mex%></font></b></span>
                                </div>
                            </div>
                        </div>
                        <%} else {%>
                        <br>
                        <%}%>
                        <div class="col-lg-6 font-white" style="padding:0px;">
                            <div class="col-lg-6 font-white">
                                <!-- small box -->
                                <div class="small-box" style="background-color: #1c6cc7;">
                                    <div class="inner uppercase">
                                        <h4 style="font-size:2vh;"><strong>Anomalie aggiornamenti<br>politiche</strong></h4>
                                                <%int anomalie = Action.getNumberAnomaliePolitiche((String) request.getSession().getAttribute("cf_ente"));%>
                                                <%if (anomalie > 0) {%>
                                        <h3 class="circle bg-red-thunderbird" style="padding-left: 5px; width: 100px; font-size:4vh;">
                                            <center><%=anomalie%> <i class="fa fa-exclamation"></i></center>
                                        </h3>
                                        <%} else {%>
                                        <h3 style="color: #1c6cc7; font-size:4vh;">-</h3>
                                        <%}%>
                                    </div>
                                    <div class="icon" >
                                        <%if (anomalie > 0) {%>
                                        <i class="fa fa-exclamation" style="color: #d91e18d6;"></i>
                                        <%} else {%>
                                        <i class="fa fa-check" style="color: #19d9189e;"></i>
                                        <%}%>
                                    </div>
                                    <a href="show_anomalie.jsp" class="small-box-footer fancyBoxDonRef" style="font-size:2vh;">visualizza <i class="fa fa-arrow-circle-right"></i></a>
                                </div>
                            </div>
                            <div class="col-lg-6 font-white">
                                <!-- small box -->
                                <div class="small-box bg-blue-soft">
                                    <div class="inner uppercase">
                                        <h4 style="font-size:2vh;"><strong>Carte d'identità scadute<br>Tutor</strong></h4>

                                        <%if (tot_id > 0) {%>
                                        <h3 class="circle bg-red-thunderbird" style="padding-left: 5px; width: 100px; font-size:4vh;">
                                            <center><%=tot_id%> <i class="fa fa-exclamation"></i></center>
                                        </h3>
                                        <%} else {%>
                                        <h3 class="font-blue-soft" style="font-size:4vh;">-</h3>
                                        <%}%>
                                    </div>
                                    <div class="icon">
                                        <%if (tot_id > 0) {%>
                                        <i class="fa fa-exclamation" style="color: #d91e18d6;"></i>
                                        <%} else {%>
                                        <i class="fa fa-check" style="color: #19d9189e;"></i>
                                        <%}%>
                                    </div>
                                    <a href="searchTutor.jsp" class="small-box-footer" style="font-size:2vh;">visualizza <i class="fa fa-arrow-circle-right"></i></a>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="portlet box blue-soft">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-bar-chart font-white" style="font-size: 25px;"></i>
                                            <span class="caption-subject font-white bold " style="font-size:2vh;">
                                                Rimborsi Accettati Respinti Dell'Anno <%=new SimpleDateFormat("yyyy").format(new Date())%></span>
                                        </div>
                                    </div>
                                    <div class="portlet-body form ">
                                        <div id="canvas-holder" style="width:102%;padding-top: 5px;">
                                            <!--<canvas id="chart-area"></canvas>-->
                                            <canvas id="canvas"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="mt-element-list">
                                <div class="mt-list-container list-simple ext-1 group">
                                    <div class="list-toggle-container" >
                                        <div class="list-toggle small-box bg-blue-sharp" style="margin-bottom: 0px;padding: 0px;">
                                            <div class="inner uppercase">
                                                <h4 style="font-size:2vh;"><strong>Anomalie politiche</strong></h4>
                                                <br>
                                                <%if (tot_p > 0) {%>
                                                <h3 class="circle bg-red-thunderbird" style="padding-left: 5px; width: 100px; font-size:4vh;">
                                                    <center> <%=tot_p%> <i class="fa fa-exclamation"></i></center>
                                                </h3>
                                                <%} else {%>
                                                <h3 class="font-blue-sharp" style="font-size:4vh;">-</h3>
                                                <%}%>
                                            </div>
                                            <div class="icon">
                                                <%if (tot_p > 0) {%>
                                                <i class="fa fa-exclamation" style="color: #d91e18d6;"></i>
                                                <%} else {%>
                                                <i class="fa fa-check" style="color: #19d9189e;"></i>
                                                <%}%>
                                            </div>
                                            <a href="#pending-simple" data-toggle="collapse" aria-expanded="false" class="small-box-footer" style="font: sans-serif;font-weight:normal; font-size:2vh;">
                                                visualizza <i class="fa fa-arrow-circle-down"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="panel-collapse collapse" id="pending-simple">
                                        <ul>
                                            <%if (map.get("A01") != null && map.get("A01") > 0) {%>
                                            <li hidden class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=map.get("A01")%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRimborso1B.jsp" style="hover: red;">Anomalie 1B<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                            <%if (map.get("B05") != null && map.get("B05") > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=map.get("B05")%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRimborso1C.jsp" style="hover: red;">Anomalie 1C<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                            <%if (map.get("B03") != null && map.get("B03") > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=map.get("B03")%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRimborsoM3.jsp" style="hover: red;">Anomalie MISURA3<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                            <%if (prg_e > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=prg_e%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyProgettoFormativo.jsp" style="hover: red;">Anomalie<br>progetti formativi<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                            <%if (prg_dt_e > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=prg_dt_e%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyProgettoFormativo.jsp" style="hover: red;">Anomalie<br>progetti formativi DOTE<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>

                                            <%if (map.get("B1") != null && map.get("B1") > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=map.get("B1")%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRimborsoB1.jsp" style="hover: red;">Anomalie B1<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                            <%if (map.get("C1") != null && map.get("C1") > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=map.get("C1")%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRimborsoC1.jsp" style="hover: red;">Anomalie C1<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                            <%if (map.get("D2") != null && map.get("D2") > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=map.get("D2")%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRimborsoD2.jsp" style="hover: red;">Anomalie D2<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                            <%if (map.get("D5") != null && map.get("D5") > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=map.get("D5")%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRimborsoD5.jsp" style="hover: red;">Anomalie D5<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                            <%if (map.get("B2") != null && map.get("B2") > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=map.get("B2")%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRimborsoB2.jsp" style="hover: red;">Anomalie B2<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                            <%if (map.get("C2") != null && map.get("C2") > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=map.get("C2")%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRimborsoC2.jsp" style="hover: red;">Anomalie C2<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="portlet-body" style="margin-top: 18px; padding-bottom: 30px;">
                                <div class="mt-element-list">
                                    <div class="mt-list-head list-news ext-1 font-white bg-blue-sharp">
                                        <div class="list-head-title-container">
                                            <h4 class="list-title" style="font-size:2vh;">Politiche Da Istruire G.G.</h4>
                                        </div>
                                        <div class="list-count pull-right ">
                                            <%if (tot_gg_i > 0) {%>
                                            <i class="fa fa-exclamation-circle" style="color: #f1d607;font-size: 30px"></i>
                                            <%} else {%>
                                            <i class="fa fa-ckeck-circle" style="color: #09f107;font-size: 30px"></i>
                                            <%}%>
                                        </div>
                                    </div>
                                    <div class="mt-list-container list-simple" style="border-color: #5c9bd1;">
                                        <ul>
                                            <li hidden class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (map_i.get("A01") != null && map_i.get("A01") > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"><%=(map_i.get("A01") != null) ? map_i.get("A01") : ""%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docRimborsi1B.jsp">PoliticA 1B</a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (map_i.get("B05") != null && map_i.get("B05") > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"> <%=(map_i.get("B05") != null) ? map_i.get("B05") : ""%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docRimborsi1C.jsp">POLITICA 1C</a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (map_i.get("B03") != null && map_i.get("B03") > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"> <%=(map_i.get("B03") != null) ? map_i.get("B03") : ""%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docRimborsiM3.jsp">MISURA 3</a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container done">
                                                    <%if (prg_i > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"> <%=((prg_i > 0) ? prg_i : "")%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docProgettoFormativo.jsp">PROG. FORMATIVO</a>
                                                    </h3>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="mt-element-list">
                                <div class="mt-list-container list-simple ext-1 group">
                                    <div class="list-toggle-container" >
                                        <div class="list-toggle small-box bg" style="margin-bottom: 0px;padding: 0px;background-color: #98b7d2;">
                                            <div class="inner uppercase">
                                                <h4 style="font-size:2vh;"><strong>Anomalie Registri</strong></h4>
                                                <br>
                                                <%if (registri + registri_dt > 0) {%>
                                                <h3 class="circle bg-red-thunderbird" style="padding-left: 5px; width: 100px; font-size:4vh;">
                                                    <center> <%=tot_r%> <i class="fa fa-exclamation"></i></center>
                                                </h3>
                                                <%} else {%>
                                                <h3 style="color:#98b7d2; font-size:4vh;">-</h3>
                                                <%}%>
                                            </div>
                                            <div class="icon">
                                                <%if (registri + registri_dt > 0) {%>
                                                <i class="fa fa-exclamation" style="color: #d91e18d6;"></i>
                                                <%} else {%>
                                                <i class="fa fa-check" style="color: #19d9189e;"></i>
                                                <%}%>
                                            </div>
                                            <a href="#registri-simple" data-toggle="collapse" aria-expanded="false" class="small-box-footer" style="font: sans-serif;font-weight:normal; font-size:2vh;">
                                                visualizza <i class="fa fa-arrow-circle-down"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="panel-collapse collapse" id="registri-simple">
                                        <ul>
                                            <%if (registri > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=registri%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRegister.jsp" style="hover: red;">Registri G.G.<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                            <%if (registri_dt > 0) {%>
                                            <li class="mt-list-item" style="border-color: #538bbc #538bbc #e7ecf1;">
                                                <div class="list-icon-container" style="font-size: 20px">
                                                    <i class="fa fa-exclamation" style="color: red;"></i>
                                                </div>
                                                <div class="list-datetime">
                                                    <label style="font-size: 20px"><%=registri_dt%></label>
                                                </div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="anomalyRegisterDT.jsp" style="hover: red;">Registri Dote<i class="fa fa-arrow-circle-right"></i></a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <%}%>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="portlet-body" style="margin-top: 18px">
                                <div class="mt-element-list">
                                    <div class="mt-list-head list-news ext-1 font-white" style="background-color: #98b7d2;">
                                        <div class="list-head-title-container">
                                            <h4 class="list-title" style="font-size:2vh;">Politiche Da Istruire DT </h4>
                                        </div>
                                        <div class="list-count pull-right ">
                                            <%if (tot_dt_i > 0) {%>
                                            <i class="fa fa-exclamation-circle" style="color: #f1d607;font-size: 30px"></i>
                                            <%} else {%>
                                            <i class="fa fa-ckeck-circle" style="color: #09f107;font-size: 30px"></i>
                                            <%}%>
                                        </div>
                                    </div>
                                    <div class="mt-list-container list-simple" style="border-color: #98b7d2;">
                                        <ul>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (prg_dt_i > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"> <%=((prg_dt_i > 0) ? prg_dt_i : "")%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docPrgDT.jsp">PROG. FORMATIVO</a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (map_i.get("B1") != null && map_i.get("B1") > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"><%=(map_i.get("B1") != null) ? map_i.get("B1") : ""%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docRimborsiB1.jsp">POLITICA B1</a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (map_i.get("C1") != null && map_i.get("C1") > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"> <%=(map_i.get("C1") != null) ? map_i.get("C1") : ""%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docRimborsiC1.jsp">POLITICA C1</a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (map_i.get("D2") != null && map_i.get("D2") > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"> <%=(map_i.get("D2") != null) ? map_i.get("D2") : ""%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docRimborsiD2.jsp">POLITICA D2</a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (map_i.get("D5") != null && map_i.get("D5") > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"> <%=(map_i.get("D5") != null) ? map_i.get("D5") : ""%></div>

                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docRimborsiD5.jsp">POLITICA D5</a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (map_i.get("B3") != null && map_i.get("B3") > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"> <%=(map_i.get("B3") != null) ? map_i.get("B3") : ""%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docRimborsiB3.jsp">POLITICA B3</a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (map_i.get("B2") != null && map_i.get("B2") > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"> <%=(map_i.get("B2") != null) ? map_i.get("B2") : ""%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docRimborsiB2.jsp">POLITICA B2</a>
                                                    </h3>
                                                </div>
                                            </li>
                                            <li class="mt-list-item">
                                                <div class="list-icon-container">
                                                    <%if (map_i.get("C2") != null && map_i.get("C2") > 0) {%>
                                                    <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                    <%} else {%>
                                                    <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                    <%}%>
                                                </div>
                                                <div class="list-datetime" style="font-size: 17px; font-size:2vh;"> <%=(map_i.get("C2") != null) ? map_i.get("C2") : ""%></div>
                                                <div class="list-item-content">
                                                    <h3 class="uppercase" style="font-size:2vh;">
                                                        <a href="docRimborsiC2.jsp">POLITICA C2</a>
                                                    </h3>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 
                    <div class="clearfix"> </div>
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

        <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>

        <!--chart-->
        <script src="Chart/Chart.bundle.js"></script>
        <script src="Chart/utils.js"></script>
        <script>
                            var barChartData = {
                            labels: ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'],
                                    datasets: [{
                                    label: 'Liquidati',
                                            backgroundColor: window.chartColors.l_green,
                                            data: [
            <%for (int i = 1; i < 13; i++) {%>
            <%=((map_p.get(String.valueOf(i)) != null) ? map_p.get(String.valueOf(i)) : 0)%>,
            <%}%>
                                            ]
                                    }, {
                                    label: 'Bocciati',
                                            backgroundColor: window.chartColors.red,
                                            data: [
            <%for (int i = 1; i < 13; i++) {%>
            <%=((map_k.get(String.valueOf(i)) != null) ? -map_k.get(String.valueOf(i)) : 0)%>,
            <%}%>
                                            ]
                                    }]

                            };
                            window.onload = function () {
                            var ctx = document.getElementById('canvas').getContext('2d');
                            window.myBar = new Chart(ctx, {
                            type: 'bar',
                                    data: barChartData,
                                    options: {
                                    animation: {
                                    easing:'easeOutElastic',
                                    },
                                            legend: {
                                            display: true,
                                                    position: 'bottom',
                                                    labels: {
                                                    fontSize: 15,
                                                            fontFamily: 'sans-serif',
                                                            fontStyle: 'bold',
                                                    }
                                            },
                                            tooltips: {
                                            mode: 'index',
                                                    intersect: false
                                            },
                                            responsive: true,
                                            scales: {
                                            xAxes: [{
                                            stacked: true                                                }],
                                                    yAxes: [{
                                                    stacked: true,
                                                    }]
                                            }
                                    }
                            });
                            };
                            Chart.plugins.register({
                            afterDatasetsDraw: function(chart) {
                            var ctx = chart.ctx;
                            chart.data.datasets.forEach(function(dataset, i) {
                            var meta = chart.getDatasetMeta(i);
                            if (!meta.hidden) {
                            meta.data.forEach(function(element, index) {
                            // Draw the text in black, with the specified font
                            ctx.fillStyle = 'rgb(35, 35, 35)';
                            var fontSize = 12;
                            var fontStyle = 'bold';
                            var fontFamily = 'sans-serif';
                            ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
                            // Just naively convert to string for now
                            var dataString = dataset.data[index].toString();
                            // Make sure alignment settings are correct
                            if (dataString != '0'){
                            ctx.textAlign = 'center';
                            ctx.textBaseline = 'top';
                            var padding = - 5;
                            var position = element.tooltipPosition();
                            ctx.fillText(dataString, position.x, position.y / 1.2 - (fontSize / 2) - padding);
                            }
                            });
                            }
                            });
                            }
                            });
        </script>
    </body>
</html>
<%}
    }%>
