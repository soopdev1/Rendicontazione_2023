
<%@page import="java.util.HashMap"%>
<%@page import="com.seta.entity.Bando"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.seta.activity.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "index_Reviser.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            //ArrayList<Bando> b_att = Action.bandiattivi();
            ArrayList<String[]> DT = Action.getListTipoPolitica("1");
            ArrayList<String[]> GG = Action.getListTipoPolitica("2");
            HashMap<String, Integer> rimborsi_GG = Action.getRimborsi_GG();
            HashMap<String, Integer> rimborsi_DT = Action.getRimborsi_DT();
            HashMap<String, Integer> registri = Action.getRegistri_REG();

            int tot_liqGG = 0;
            for (String k : rimborsi_GG.keySet()) {
                if (k.contains("-R-")) {
                    tot_liqGG += rimborsi_GG.get(k);
                }
            }

            int tot_liqDT = 0;
            for (String k : rimborsi_DT.keySet()) {
                if (k.contains("-R-")) {
                    tot_liqDT += rimborsi_DT.get(k);
                }
            }

            int totR_liquGG = 0;
            int totR_liquDT = 0;
            for (String k : registri.keySet()) {
                if (k.contains("-R-GG")) {
                    totR_liquGG += registri.get(k);
                } else if (k.contains("-R-DT")) {
                    totR_liquDT += registri.get(k);
                }
            }

            int tot_R = totR_liquDT + totR_liquGG;

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
        <link href="assets/layouts/layout/css/themes/grey.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />

        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/seta/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/seta/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/seta/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/seta/js/fancy.js"></script> 

        <link rel="stylesheet" href="AdminLTE-2.4.2/dist/css/AdminLTE.css">
        <link rel="shortcut icon" href="assets/seta/img/favicon.ico" />
        <style>
            .inner{
                padding-bottom: 5px;
            }
            .small-box-footer{
                font-size: 18px;
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
            <%@ include file="menu_revisore/menu_home.jsp"%>
            <!-- END MENU -->

            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <!-- Main content -->
                    <div class="logo">
                        <img src="assets/seta/img/logos1.png" height="75"  align="right" /> 
                    </div>  
                    <h3 class="page-title font-blue-chambray"><strong> HOME </strong><small class="font-blue-chambray"> REVISORE</small> </h3>  
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

                        <section class="content">
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="mt-element-list">
                                        <div class="mt-list-container list-simple ext-1 group">
                                            <div class="list-toggle-container" >
                                                <div class="list-toggle small-box bg-blue-chambray" style="margin-bottom: 0px;padding: 0px;">
                                                    <div class="inner uppercase">                                        
                                                        <h4 style="font-size:2.5vh;"><strong>GARANZIA GIOVANI</strong></h4>
                                                        <%if (tot_liqGG > 0) {%>
                                                        <h3 class="circle bg-red-thunderbird" style="padding-top: 3px; width: 100px; font-size:5vh;">
                                                            <center><%=tot_liqGG%> <i class="fa fa-exclamation"></i></center>
                                                        </h3> 
                                                        <%} else {%>
                                                        <h3 style="padding-top: 3px; font-size:5vh;">&nbsp;</h3>
                                                        <%}%>
                                                    </div>
                                                    <div align="right" class="icon">
                                                        <%if (tot_liqGG > 0) {%>
                                                        <i><img src="assets/seta/img/GGKO.png" width="25%"/></i>
                                                            <%} else {%>
                                                        <i><img src="assets/seta/img/GGOK.png" width="25%" /></i>
                                                            <%}%>
                                                    </div>
                                                    <a href="#pending-simple" data-toggle="collapse" aria-expanded="false" class="small-box-footer" style="font: sans-serif;font-weight:normal;font-size:2vh;">
                                                        Visualizza <i class="fa fa-arrow-circle-down"></i>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="panel-collapse collapse" id="pending-simple">
                                                <ul>
                                                    <%if (tot_liqGG > 0) {%>
                                                    <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                        <div class="list-icon-container" >
                                                            <!--i class="fa fa-exclamation-circle" style="color:#E87E04;font-size: 30px;"></i-->
                                                            <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                        </div>
                                                        <div class="list-datetime bold" style="color:#0073b7;font-size:2.5vh; vertical-align: middle;"> <%=tot_liqGG%> </div>
                                                        <div class="list-item-content">
                                                            <h3 style="font-size:2vh;">
                                                                <a href="#liqGG" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">In Attesa Di Verifica</a>
                                                            </h3>
                                                        </div>
                                                    </li>
                                                    <div class="panel-collapse collapse" id="liqGG">
                                                        <ul>
                                                            <%int cz = 0;
                                                                for (int i = 0; i < GG.size(); i++) {
                                                                    for (String k : rimborsi_GG.keySet()) {
                                                                        if (k.equals(GG.get(i)[0] + "-R-")) {
                                                                            String pGG = "rev_liquidazione";
                                                                            if (GG.get(i)[0].equals("A01")) {
                                                                                pGG += "1B.jsp";
                                                                            } else if (GG.get(i)[0].equals("B03")) {
                                                                                pGG += "M3.jsp";
                                                                            } else if (GG.get(i)[0].equals("B05")) {
                                                                                pGG += "1C.jsp";
                                                                            } else if (GG.get(i)[0].equals("C06")) {
                                                                                pGG = "rev_entiM5.jsp";
                                                                            }%>
                                                            <li class="mt-list-item" style="
                                                                <%if (cz % 2 == 0) {%>
                                                                background-color: #d3e9f8;
                                                                <%} else {%>
                                                                background-color: #e9f4fc;
                                                                <%}%> 
                                                                border-color: #538bbc #538bbc #e7ecf1;">
                                                                <div class="list-icon-container">
                                                                    <i class="fa fa-money" style="color:#1c6cc7;font-size: 20px;"></i>
                                                                </div>
                                                                <div class="list-datetime bold" style="color:#1c6cc7;font-size:2.5vh;"> <%=rimborsi_GG.get(k)%> </div>
                                                                <div class="list-item-content">
                                                                    <h3 style="font-size:2vh;">
                                                                        <a href="<%=pGG%>" style="color:#1c6cc7;font-size: 15px;"><%=GG.get(i)[1]%></a>
                                                                    </h3>
                                                                </div>
                                                            </li>
                                                            <%cz++;
                                                                        }
                                                                    }
                                                                }%>
                                                        </ul>
                                                    </div>
                                                    <%} else {%>
                                                    <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                        <div class="list-icon-container" >
                                                            <!--i class="fa fa-check-circle" style="color:#1BBC9B;font-size: 30px;"></i-->
                                                            <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                        </div>
                                                        <div class="list-item-content">
                                                            <h3 style="color:#0073b7;">In Attesa di Verifica</h3>
                                                        </div>
                                                    </li>
                                                    <%}%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="mt-element-list">
                                        <div class="mt-list-container list-simple ext-1 group">
                                            <div class="list-toggle-container" >
                                                <div class="list-toggle small-box bg-blue-chambray" style="margin-bottom: 0px;padding: 0px;">
                                                    <div class="inner uppercase">                                        
                                                        <h4 style="font-size:2.5vh;"><strong>DOTE LAVORO</strong></h4>
                                                        <%if (tot_liqDT > 0) {%>
                                                        <h3 class="circle bg-red-thunderbird" style="padding-top: 3px; width: 100px; font-size:5vh;">
                                                            <center><%=tot_liqDT%> <i class="fa fa-exclamation"></i></center>
                                                        </h3> 
                                                        <%} else {%>
                                                        <h3 style="padding-top: 3px; font-size:5vh;">&nbsp;</h3>
                                                        <%}%>
                                                    </div>
                                                    <div align="right" class="icon">
                                                        <%if (tot_liqDT > 0) {%>
                                                        <i><img src="assets/seta/img/DTKO.png" width="25%" /></i>
                                                            <%} else {%>
                                                        <i><img src="assets/seta/img/DTOK.png" width="25%" /></i>
                                                            <%}%>
                                                    </div>
                                                    <a href="#pending-simple1" data-toggle="collapse" aria-expanded="false" class="small-box-footer" style="font: sans-serif;font-weight:normal;font-size:2vh;">
                                                        Visualizza <i class="fa fa-arrow-circle-down"></i>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="panel-collapse collapse" id="pending-simple1">
                                                <ul>
                                                    <%if (tot_liqDT > 0) {%>
                                                    <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                        <div class="list-icon-container" >
                                                            <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                        </div>
                                                        <div class="list-datetime bold" style="color:#0073b7;font-size:2.5vh;"> <%=tot_liqDT%> </div>
                                                        <div class="list-item-content">
                                                            <h3 style="font-size:2vh;">
                                                                <a href="#liqDT" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">In Attesa Di Verifica</a>
                                                            </h3>
                                                        </div>
                                                    </li>
                                                    <div class="panel-collapse collapse" id="liqDT">
                                                        <ul>
                                                            <%int cz = 0;
                                                                for (int i = 0; i < DT.size(); i++) {
                                                                    for (String k : rimborsi_DT.keySet()) {
                                                                        if (k.equals(DT.get(i)[0] + "-R-")) {
                                                                            String lDT = "rev_liquidazione";
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
                                                                                lDT = "rev_entiDT.jsp";
                                                                            }%>
                                                            <li class="mt-list-item" style="
                                                                <%if (cz % 2 == 0) {%>
                                                                background-color: #d3e9f8;
                                                                <%} else {%>
                                                                background-color: #e9f4fc;
                                                                <%}%> 
                                                                border-color: #538bbc #538bbc #e7ecf1;">
                                                                <div class="list-icon-container">
                                                                    <i class="fa fa-money" style="color:#1c6cc7;font-size: 20px;"></i>
                                                                </div>
                                                                <div class="list-datetime bold" style="color:#1c6cc7;font-size:2.5vh;"> <%=rimborsi_DT.get(k)%> </div>
                                                                <div class="list-item-content">
                                                                    <h3 style="font-size:2vh;">
                                                                        <a href="<%=lDT%> " style="color:#1c6cc7;font-size: 15px;"><%=DT.get(i)[1]%></a>
                                                                    </h3>
                                                                </div>
                                                            </li>
                                                            <%cz++;
                                                                        }
                                                                    }
                                                                }%>
                                                        </ul>
                                                    </div>
                                                    <%} else {%>
                                                    <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                        <div class="list-icon-container" >
                                                            <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                        </div>
                                                        <div class="list-item-content">
                                                            <h3 style="color:#0073b7;">In Attesa Di Verifica</h3>
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
                                                <div class="list-toggle small-box bg-blue-chambray" style="margin-bottom: 0px;padding: 0px;">
                                                    <div class="inner uppercase">                                        
                                                        <h4 style="font-size:2.5vh;"><strong>REGISTRI PROG. FORMATIVI</strong></h4>
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
                                                        <i><img src="assets/seta/img/PFKO.png" width="25%"/></i>
                                                            <%} else {%>
                                                        <i><img src="assets/seta/img/PFOK.png" width="25%" /></i>
                                                            <%}%>
                                                    </div>
                                                    <a href="#pending-simple3" data-toggle="collapse" aria-expanded="false" class="small-box-footer" style="font: sans-serif;font-weight:normal;font-size:2vh;">
                                                        Visualizza <i class="fa fa-arrow-circle-down"></i>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="panel-collapse collapse" id="pending-simple3">
                                                <ul>
                                                    <%if ((totR_liquDT + totR_liquGG) > 0) {%>
                                                    <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                        <div class="list-icon-container" >
                                                            <i class="fa fa-exclamation-circle" style="font-size: 25px; color: orange;"></i>
                                                        </div>
                                                        <div class="list-datetime bold" style="color:#0073b7;font-size:2.5vh;"> <%=totR_liquDT + totR_liquGG%> </div>
                                                        <div class="list-item-content">
                                                            <h3 style="font-size:2vh;">
                                                                <a href="#liquR" style="color:#0073b7;" data-toggle="collapse" aria-expanded="false">In Attesa Di Verifica</a>
                                                            </h3>
                                                        </div>
                                                    </li>
                                                    <div class="panel-collapse collapse" id="liquR">
                                                        <ul>
                                                            <%  int i = 0;
                                                                for (String k : registri.keySet()) {
                                                                    String lR = "rev_tirocinanti";
                                                                    String descr = "";
                                                                    int num = 0;
                                                                    if (k.contains("-R-")) {
                                                                        if (k.contains("-R-DT")) {
                                                                            num = totR_liquDT;
                                                                            descr = "DT - Accomp.al tirocinio";
                                                                            lR += "DT.jsp";
                                                                        } else if (k.contains("-R-GG")) {
                                                                            lR += "M5.jsp";
                                                                            num = totR_liquGG;
                                                                            descr = "GG - Misura 5";
                                                                        }%>
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
                                                                <div class="list-datetime bold" style="color:#1c6cc7;font-size:2.5vh;"> <%=num%> </div>
                                                                <div class="list-item-content">
                                                                    <h3 style="font-size:2vh;">
                                                                        <a href="<%=lR%>" style="color:#1c6cc7;font-size: 15px;"><%=descr%></a>
                                                                    </h3>
                                                                </div>
                                                            </li>
                                                            <%}
                                                                    i++;
                                                                }%>
                                                        </ul>
                                                    </div>
                                                    <%} else {%>
                                                    <li class="mt-list-item " style="border-color: #0073b7 #0073b7 #e7ecf1;">
                                                        <div class="list-icon-container" >
                                                            <i class="icon-check" style="font-size: 25px;color: #09f107;"></i>
                                                        </div>
                                                        <div class="list-item-content">
                                                            <h3 style="color:#0073b7;">In Attesa Di Verifica</h3>
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
