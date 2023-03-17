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
    if (pageName.equals("addRimborsi1C.jsp")) {
        add_r = "active open";
    } else if (pageName.equals("anomalyRimborso1C.jsp")) {
        anomaly_r = "active open";
    } else if (pageName.equals("resultRimborso1C.jsp")) {
        result_r = "active open";
    } else if (pageName.equals("docRimborsi1C.jsp")) {
        doc_r = "active open";
    } else if (pageName.equals("negativeResultRimborso1C.jsp")) {
        neg_r = "active open";
    } else if (pageName.equals("blockedRimborso1C.jsp")) {
        block_r = "active open";
    } else if (pageName.equals("generatedRimborso1C.jsp")) {
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
            <li class="nav-item start active open">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-tag"></i>
                    <span class="title"> GARANZIA GIOVANI</span>       
                    <span class="selected"></span>
                    <span class="arrow open"></span>
                </a>
                <ul class="sub-menu">
                    <%@ include file="politiche_gg/misura5.jsp"%>
                    <%@ include file="politiche_gg/Politica 1B.jsp"%>
                    <li class="nav-item active open">
                        <a href="javascript:;" class="nav-link nav-toggle">
                            <i class="fa fa-arrow-down"></i> Politica 1C
                            <span class="arrow"></span>
                        </a>
                        <ul class="sub-menu">
                            <li class="nav-item start <%=doc_r%>">
                                <a href="docRimborsi1C.jsp" class="nav-link ">
                                    <i class="fa fa-file"></i>
                                    <span class="title"> Carica Doc</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=add_r%>">
                                <a href="addRimborsi1C.jsp" class="nav-link ">
                                    <i class="fa fa-money"></i>
                                    <span class="title"> Richiedi Rimborso</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=anomaly_r%>">
                                <a href="anomalyRimborso1C.jsp" class="nav-link ">
                                    <i class="fa fa-exclamation-circle"></i>
                                    <span class="title"> Anomalie</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=block_r%>">
                                <a href="blockedRimborso1C.jsp" class="nav-link ">
                                    <i class="fa fa-close"></i>
                                    <span class="title"> Respinte</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=gen_r%>">
                                <a href="generatedRimborso1C.jsp" class="nav-link ">
                                    <i class="fa fa-spinner"></i>
                                    <span class="title">Domande In Attesa</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=result_r%>">
                                <a href="resultRimborso1C.jsp" class="nav-link ">
                                    <i class="fa fa-eur"></i>
                                    <span class="title"> Esiti Positivi</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                            <li class="nav-item start <%=neg_r%>">
                                <a href="negativeResultRimborso1C.jsp" class="nav-link ">
                                    <i class="fa fa-ban"></i>
                                    <span class="title"> Esiti Negativi</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <%@ include file="politiche_gg/misura3.jsp"%>
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
