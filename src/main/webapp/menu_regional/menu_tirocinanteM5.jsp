
<%@page import="com.seta.util.Etichette"%>
<%String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String elabora = "";
    String liquidazione = "";
    String anomalie = "";
    String es = "";
    if (pageName.equals("regT_elaboraM5.jsp")) {
        elabora = "active open";
    } else if (pageName.equals("regT_liquidazioneM5.jsp")) {
        liquidazione = "active open";
    } else if (pageName.equals("regT_anomalieM5.jsp")) {
        anomalie = "active open";
    } else if (pageName.equals("regT_esitiM5.jsp")) {
        es = "active open";
    }
%>

<link href="assets/layouts/layout/css/themes/blue.min.css" rel="stylesheet" type="text/css" id="style_color" />

<div class="page-sidebar-wrapper">
    <div class="page-sidebar navbar-collapse collapse">
        <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
            <li class="sidebar-toggler-wrapper hide">
                <div class="sidebar-toggler">
                    <span></span>
                </div>
            </li>
            <li class="nav-item start">
                <a href="index_Regional.jsp" class="nav-link nav-toggle">
                    <i class="icon-home"></i>
                    <span class="title"> HOME</span>
                    <span class="selected"></span>
                </a>
            </li>
            <%@ include file="general/regional_general.jsp"%>
            <%@ include file="general/lavoratori_general.jsp"%>
            <li class="nav-item start active open">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-tag"></i>
                    <span class="title"> GARANZIA GIOVANI</span>       
                    <span class="selected"></span>
                    <span class="arrow open"></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item active open">
                        <a href="javascript:;" class="nav-link nav-toggle">
                            <i class="fa fa-arrow-down"></i> Misura 5
                            <span class="arrow"></span>
                        </a>

                        <ul class="sub-menu">
                            <%@include file="politiche_gg/M5/general_ente.jsp" %>
                            <li class="nav-item active open">
                                <a href="javascript:;" class="nav-link nav-toggle">
                                    <i class="fa fa-user"></i>
                                    <span class="title"> Destinatario</span>       
                                    <span class="selected"></span>
                                    <span class="arrow open"></span>
                                </a>
                                <ul class="sub-menu">  
                                    <li class="nav-item start  <%=elabora%>" >
                                        <a href="regT_elaboraM5.jsp" class="nav-link ">
                                            <i class="fa fa-list-ul"></i>
                                            <span class="title"> Gestione</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start  <%=liquidazione%>" >
                                        <a href="regT_liquidazioneM5.jsp" class="nav-link ">
                                            <i class="fa fa-money"></i>
                                            <span class="title"> Liquidazione</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=anomalie%>" >
                                        <a href="regT_anomalieM5.jsp" class="nav-link ">
                                            <i class="fa fa-warning "></i>
                                            <span class="title"> Anomalie</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=es%>" >
                                        <a href="regT_esitiM5.jsp" class="nav-link ">
                                            <i class="fa fa-check "></i><i class="fa fa-close "></i>
                                            <span class="title"> Esiti</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <%@include file="politiche_gg/M5/general_inps.jsp" %>
                            <%@include file="politiche_gg/M5/general_tirocini.jsp" %>
                        </ul>


                    </li>
                    <%@ include file="politiche_gg/misura3.jsp"%>
                    <%@ include file="politiche_gg/politica1b.jsp"%>
                    <%@ include file="politiche_gg/politica1c.jsp"%>
                </ul>
            </li>
            <%@ include file="general/dote_general.jsp"%>
            <%@ include file="general/downloadGuida.jsp"%>
        </ul>
        <!-- END SIDEBAR MENU -->
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
</div>
