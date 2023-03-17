
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
    String protocollo = "";
    String es = "";
    if (pageName.equals("regE_gestioneDT.jsp")) {
        elabora = "active open";
    } else if (pageName.equals("regE_liquidazioneDT.jsp")) {
        liquidazione = "active open";
    } else if (pageName.equals("regE_anomalieDT.jsp")) {
        anomalie = "active open";
    } else if (pageName.equals("regE_protocolloDT.jsp")) {
        protocollo = "active open";
    } else if (pageName.equals("regE_esitiDT.jsp")) {
        es = "active open";
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
                <a href="index_Regional.jsp" class="nav-link nav-toggle">
                    <i class="icon-home"></i>
                    <span class="title"> HOME</span>
                    <span class="selected"></span>
                </a>
            </li>
            <%@ include file="general/regional_general.jsp"%>
            <%@ include file="general/lavoratori_general.jsp"%>
            <%@include file="general/garanziagiovani_general.jsp"%>
            <li class="nav-item start active open">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-tag"></i>
                    <span class="title"> DOTE</span>       
                    <span class="selected"></span>
                    <span class="arrow open"></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item active open">
                        <a href="javascript:;" class="nav-link nav-toggle">
                            <i class="fa fa-arrow-down"></i> Accomp.al tirocinio
                            <span class="arrow"></span>
                        </a>
                        <ul class="sub-menu">  
                            <li class="nav-item start active open">
                                <a href="javascript:;" class="nav-link nav-toggle">
                                    <i class="fa fa-building"></i>
                                    <span class="title"> Ente</span>       
                                    <span class="selected"></span>
                                    <span class="arrow open"></span>
                                </a>
                                <ul class="sub-menu">
                                    <li class="nav-item start <%=protocollo%>">
                                        <a href="regE_protocolloDT.jsp" class="nav-link ">
                                            <i class="fa fa-legal"></i>
                                            <span class="title"> Protocolla</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=elabora%>" >
                                        <a href="regE_gestioneDT.jsp" class="nav-link ">
                                            <i class="fa fa-list-ul"></i>
                                            <span class="title"> Gestione</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=liquidazione%>" >
                                        <a href="regE_liquidazioneDT.jsp"  class="nav-link ">
                                            <i class="fa fa-money "></i>
                                            <span class="title"> Liquidazione</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=anomalie%>" >
                                        <a href="regE_anomalieDT.jsp" class="nav-link ">
                                            <i class="fa fa-warning "></i>
                                            <span class="title"> Anomalie</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=es%>" >
                                        <a href="regE_esitiDT.jsp" class="nav-link ">
                                            <i class="fa fa-check "></i><i class="fa fa-close "></i>
                                            <span class="title"> Esiti</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                </ul>
                                <%@include file="politiche_dote/C06/general_tirocinante.jsp" %>
                                <%@include file="politiche_dote/C06/general_inps.jsp" %>
                                <%@include file="politiche_dote/C06/general_tirocini.jsp" %>
                            </li>
                        </ul>
                    </li> 
                    <%@include file="politiche_dote/politicaB1.jsp" %>
                    <%@include file="politiche_dote/politicaC1.jsp" %>
                    <%@include file="politiche_dote/politicaD2.jsp" %>
                    <%@include file="politiche_dote/politicaD5.jsp" %>
                    <%@include file="politiche_dote/politicaB3.jsp" %>
                    <%@include file="politiche_dote/politicaB2.jsp" %>  
                    <%@include file="politiche_dote/politicaC2.jsp" %>
                </ul>
            </li>
            <%@ include file="general/downloadGuida.jsp"%>
        </ul>

        <!-- END SIDEBAR MENU -->
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
</div>
