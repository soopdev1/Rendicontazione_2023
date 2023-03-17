
<%@page import="com.seta.util.Etichette"%>
<%String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String cerca = "";
    if (pageName.equals("regT_cercaC06DT.jsp")) {
        cerca = "active open";
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
                            <%@include file="politiche_dote/C06/general_ente.jsp" %>
                            <%@include file="politiche_dote/C06/general_tirocinante.jsp" %>
                            <%@include file="politiche_dote/C06/general_inps.jsp" %>
                            <li class="nav-item active open">
                                <a href="javascript:;" class="nav-link nav-toggle">
                                    <i class="fa fa-archive"></i>
                                    <span class="title"> TIROCINI</span>       
                                    <span class="selected"></span>
                                    <span class="arrow open"></span>
                                </a>
                                <ul class="sub-menu">
                                    <li class="nav-item start <%=cerca%>" >
                                        <a href="regT_cercaC06DT.jsp" class="nav-link ">
                                            <i class="fa fa-search "></i>
                                            <span class="title"> Cerca</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li> <%@include file="politiche_dote/politicaB1.jsp" %>
                    <%@include file="politiche_dote/politicaC1.jsp" %>
                    <%@include file="politiche_dote/politicaD2.jsp" %>
                    <%@include file="politiche_dote/politicaD5.jsp" %>
                    <%@include file="politiche_dote/politicaB3.jsp" %>
                    <%@ include file="politiche_dote/politicaB2.jsp"%>  
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
