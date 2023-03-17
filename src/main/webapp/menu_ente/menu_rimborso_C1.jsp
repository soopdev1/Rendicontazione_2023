<%-- 
    Document   : menu_rimborso_1B
    Created on : 19-ott-2018, 9.52.38
    Author     : agodino
--%>
<%@page import="com.seta.util.Etichette"%>
<%
    String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String add_r = "";
    String anomaly_r = "";
    String result_r = "";
    String doc_r = "";
    String neg_r = "";
    String block_r = "";
    String gen_r = "";
    if (pageName.equals("addRimborsiC1.jsp")) {
        add_r = "active open";
    } else if (pageName.equals("anomalyRimborsoC1.jsp")) {
        anomaly_r = "active open";
    } else if (pageName.equals("resultRimborsoC1.jsp")) {
        result_r = "active open";
    } else if (pageName.equals("docRimborsiC1.jsp")) {
        doc_r = "active open";
    } else if (pageName.equals("negativeResultRimborsoC1.jsp")) {
        neg_r = "active open";
    } else if (pageName.equals("blockedRimborsoC1.jsp")) {
        block_r = "active open";
    } else if (pageName.equals("generatedRimborsoC1.jsp")) {
        gen_r = "active open";
    }
%>
<div class="page-sidebar-wrapper">
    <div class="page-sidebar navbar-collapse collapse">
        <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
            <li class="sidebar-toggler-wrapper hide">
                <div class="sidebar-toggler">
                    <span></span>
                </div>
            </li>
            <li class="nav-item start">
                <a href="index_Ente.jsp" class="nav-link nav-toggle">
                    <i class="icon-home"></i>
                    <span class="title"> HOME</span>
                    <span class="selected"></span>

                </a>
            </li>
            <%@ include file="general/Convenzione_general.jsp"%>
            <%@ include file="general/Tutor_general.jsp"%>
            <%@ include file="general/lavoratori_general.jsp"%>
            <%@ include file="general/gg_general.jsp"%>
            <li class="nav-item start active open">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-tag"></i>
                    <span class="title"> DOTE</span>       
                    <span class="selected"></span>
                    <span class="arrow open"></span>
                </a>
                <ul class="sub-menu">
                    <%@ include file="politiche_dt/C06.jsp"%>
                    <%@ include file="politiche_dt/Politica_B1.jsp"%>
                    <li class="nav-item active open">
                        <a href="javascript:;" class="nav-link nav-toggle">
                            <i class="fa fa-arrow-down"></i> Politiche C1
                            <span class="arrow"></span>
                        </a>
                        <ul class="sub-menu">
                            <li class="nav-item start <%=doc_r%>">
                                <a href="docRimborsiC1.jsp" class="nav-link ">
                                    <i class="fa fa-file"></i>
                                    <span class="title"> Carica Doc</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=add_r%>">
                                <a href="addRimborsiC1.jsp" class="nav-link ">
                                    <i class="fa fa-money"></i>
                                    <span class="title"> Richiedi Rimborso</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=anomaly_r%>">
                                <a href="anomalyRimborsoC1.jsp" class="nav-link ">
                                    <i class="fa fa-exclamation-circle"></i>
                                    <span class="title"> Anomalie</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=block_r%>">
                                <a href="blockedRimborsoC1.jsp" class="nav-link ">
                                    <i class="fa fa-close"></i>
                                    <span class="title"> Respinte</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=gen_r%>">
                                <a href="generatedRimborsoC1.jsp" class="nav-link ">
                                    <i class="fa fa-spinner"></i>
                                    <span class="title">Domande In Attesa</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=result_r%>">
                                <a href="resultRimborsoC1.jsp" class="nav-link ">
                                    <i class="fa fa-eur"></i>
                                    <span class="title"> Esiti Positivi</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=neg_r%>">
                                <a href="negativeResultRimborsoC1.jsp" class="nav-link ">
                                    <i class="fa fa-ban"></i>
                                    <span class="title"> Esiti Negativi</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <%@ include file="politiche_dt/Politica_D2.jsp"%>
                    <%@ include file="politiche_dt/Politica_D5.jsp"%>
                    <%@ include file="politiche_dt/Politica_B3.jsp"%>
                    <%@ include file="politiche_dt/Politica_B2.jsp"%>
                    <%@ include file="politiche_dt/Politica_C2.jsp"%>
                </ul>
            </li>
            <%@ include file="general/downloadGuida.jsp"%>
        </ul>

        <!-- END SIDEBAR MENU -->
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
</div>
