
<%@page import="com.seta.util.Etichette"%>
<%String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String anomaly_r = "";
    String result_r = "";
    String neg_r = "";

    if (pageName.equals("anomalyRegisterDT.jsp")) {
        anomaly_r = "active open";
    } else if (pageName.equals("negativeRegisterDT.jsp")) {
        neg_r = "active open";
    } else if (pageName.equals("resultRegisterDT.jsp")) {
        result_r = "active open";
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
                    <li class="nav-item active open">
                        <a href="javascript:;" class="nav-link nav-toggle">
                            <i class="fa fa-arrow-down"></i> Acc. Al Tirocinio
                            <span class="arrow"></span>
                        </a>
                        <ul class="sub-menu">
                            <%@ include file="politiche_dt/C06/general_ProgettoFormativo.jsp"%>
                            <li class="nav-item start active open">
                                <a href="javascript:;" class="nav-link nav-toggle">
                                    <i class="fa fa-calendar"></i>
                                    <span class="title"> Registri</span>       
                                    <span class="selected"></span>
                                    <span class="arrow open"></span>
                                </a>
                                <ul class="sub-menu">
                                    <li class="nav-item start <%=anomaly_r%>">
                                        <a href="anomalyRegisterDT.jsp" class="nav-link ">
                                            <i class="fa fa-exclamation-circle"></i>
                                            <span class="title">Anomalie</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=neg_r%>">
                                        <a href="negativeRegisterDT.jsp" class="nav-link ">
                                            <i class="fa fa-ban"></i>
                                            <span class="title">Respinti</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                    <li class="nav-item start <%=result_r%>">
                                        <a href="resultRegisterDT.jsp" class="nav-link ">
                                            <i class="fa fa-eur"></i>
                                            <span class="title">Liquidati</span>
                                            <span class="badge bg-blue-chambray"></span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <%@ include file="politiche_dt/Politica_B1.jsp"%>
                    <%@ include file="politiche_dt/Politica_C1.jsp"%>
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
