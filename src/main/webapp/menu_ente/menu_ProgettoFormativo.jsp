
<%@page import="com.seta.util.Etichette"%>
<%String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String add_r = "";
    String anomaly_r = "";
    String result_r = "";
    String doc_r = "";
    String search_r = "";
    String neg_r = "";
    String block_r = "";
    String gen_r = "";
    String ter = "";
    if (pageName.equals("addRimborsiProgettoFormativo.jsp")) {
        add_r = "active open";
    } else if (pageName.equals("anomalyProgettoFormativo.jsp")) {
        anomaly_r = "active open";
    } else if (pageName.equals("resultRimborsoProgettoFormativo.jsp")) {
        result_r = "active open";
    } else if (pageName.equals("docProgettoFormativo.jsp")) {
        doc_r = "active open";
    } else if (pageName.equals("negativeResultRimborsoProgettoFormativo.jsp")) {
        neg_r = "active open";
    } else if (pageName.equals("blockedProgettoFormativo.jsp")) {
        block_r = "active open";
    } else if (pageName.equals("generatedRimborsoProgettoFormativo.jsp")) {
        gen_r = "active open";
    } else if (pageName.equals("searchProgettoFormativo.jsp")) {
        search_r = "active open";
    } else if (pageName.equals("finished_projects.jsp")) {
        ter = "active open";
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
                    <li class="nav-item active open">
                        <a href="javascript:;" class="nav-link nav-toggle">
                            <i class="fa fa-arrow-down"></i> Misura 5
                            <span class="arrow"></span>
                        </a>
                        <ul class="sub-menu">                            
                            <li class="nav-item start active open">
                                <a href="javascript:;" class="nav-link nav-toggle">
                                    <i class="fa fa-pencil"></i>
                                    <span class="title"> Progetto Formativo</span>       
                                    <span class="selected"></span>
                                    <span class="arrow open"></span>
                                </a>
                                <ul class="sub-menu">
                                    <li class="nav-item start <%=doc_r%>">
                                        <a href="docProgettoFormativo.jsp" class="nav-link ">
                                            <i class="fa fa-file"></i>
                                            <span class="title">Carica Doc.</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=search_r%>">
                                        <a href="searchProgettoFormativo.jsp" class="nav-link ">
                                            <i class="fa fa-search"></i>
                                            <span class="title">Gestione</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=ter%>">
                                        <a href="finished_projects.jsp" class="nav-link ">
                                            <i class="fa fa-hourglass-end"></i>
                                            <span class="title">Terminati</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=add_r%>">
                                        <a href="addRimborsiProgettoFormativo.jsp" class="nav-link ">
                                            <i class="fa fa-money"></i>
                                            <span class="title">Richiedi Rimborso</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=anomaly_r%>">
                                        <a href="anomalyProgettoFormativo.jsp" class="nav-link ">
                                            <i class="fa fa-exclamation-circle"></i>
                                            <span class="title">Anomalie</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=block_r%>">
                                        <a href="blockedProgettoFormativo.jsp" class="nav-link ">
                                            <i class="fa fa-close"></i>
                                            <span class="title">Respinti</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=gen_r%>">
                                        <a href="generatedRimborsoProgettoFormativo.jsp" class="nav-link ">
                                            <i class="fa fa-spinner"></i>
                                            <span class="title">In Attesa</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=result_r%>">
                                        <a href="resultRimborsoProgettoFormativo.jsp" class="nav-link ">
                                            <i class="fa fa-eur"></i>
                                            <span class="title">Esiti Positivi</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=neg_r%>">
                                        <a href="negativeResultRimborsoProgettoFormativo.jsp" class="nav-link ">
                                            <i class="fa fa-ban"></i>
                                            <span class="title">Esiti Negativi</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <%@ include file="politiche_gg/misura5/general_Registro.jsp"%>
                        </ul>
                    </li>
                    <%@ include file="politiche_gg/Politica 1B.jsp"%>
                    <%@ include file="politiche_gg/Politica 1C.jsp"%>
                    <%@ include file="politiche_gg/misura3.jsp"%>
                </ul>
                <ul class="sub-menu">

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
