
<%@page import="com.seta.util.Etichette"%>
<%String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String elabora = "";
    String anomalie = "";
    if (pageName.equals("regI_elaboraM5.jsp")) {
        elabora = "active open";
    } else if (pageName.equals("regI_liquidatiM5.jsp")) {
        anomalie = "active open";
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
                            <%@include file="politiche_gg/M5/general_tirocinante.jsp" %>
                            <li class="nav-item active open">
                                <a href="javascript:;" class="nav-link nav-toggle">
                                    <i class="fa fa-refresh"></i>
                                    <span class="title"> INPS</span>       
                                    <span class="selected"></span>
                                    <span class="arrow open"></span>
                                </a>
                                <ul class="sub-menu">
                                    <li class="nav-item start <%=elabora%>" >
                                        <a href="regI_elaboraM5.jsp" class="nav-link ">
                                            <i class="fa fa-list-ul "></i>
                                            <span class="title"> Consulta</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=anomalie%>" >
                                        <a href="regI_liquidatiM5.jsp" class="nav-link ">
                                            <i class="fa fa-euro "></i>
                                            <span class="title"> Liquidati</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
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
