
<%@page import="com.seta.util.Etichette"%>
<%
    String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String ee = "";
    String tt = "";
    String esititir = "";
    String esitienti = "";
    String cerca = "";
    if (pageName.equals("rev_entiM5.jsp")) {
        ee = "active open";
    } else if (pageName.equals("rev_tirocinantiM5.jsp")) {
        tt = "active open";
    } else if (pageName.equals("revE_esitiM5.jsp")) {
        esitienti = "active open";
    } else if (pageName.equals("revT_esitiM5.jsp")) {
        esititir = "active open";
    } else if (pageName.equals("revT_cercaM5.jsp")) {
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
                <a href="index_Reviser.jsp" class="nav-link nav-toggle">
                    <i class="icon-home"></i>
                    <span class="title"> HOME</span>
                    <span class="selected"></span>

                </a>
            </li>
            <div style="display: none">
                <%@ include file="general/configura_general.jsp"%>
            </div>
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
                            <li class="nav-item start <%=tt%>">
                                <a href="rev_tirocinantiM5.jsp" class="nav-link nav-toggle">
                                    <i class="fa fa-users"></i>
                                    <span class="title"> Ammiss. Indennità</span>       
                                    <span class="badge bg-blue-chambray"></span>
                                </a>   
                            </li>
                            <li class="nav-item start <%=esititir%>">
                                <a href="revT_esitiM5.jsp" class="nav-link nav-toggle">
                                    <i class="fa fa-check"></i><i class="fa fa-close"></i>
                                    <span class="title"> Esiti Destinatari</span>       
                                    <span class="badge bg-blue-chambray"></span>
                                </a>   
                            </li>
                            <li class="nav-item start <%=ee%>">
                                <a href="rev_entiM5.jsp" class="nav-link nav-toggle">
                                    <i class="fa fa-building-o"></i>
                                    <span class="title"> Ammiss. Inden. Enti</span>       
                                    <span class="badge bg-blue-chambray"></span>
                                </a>   
                            </li>
                            <li class="nav-item start <%=esitienti%>">
                                <a href="revE_esitiM5.jsp" class="nav-link nav-toggle">
                                    <i class="fa fa-check"></i><i class="fa fa-close"></i>
                                    <span class="title"> Esiti Enti</span>       
                                    <span class="badge bg-blue-chambray"></span>
                                </a>   
                            </li>
                            <li class="nav-item start <%=cerca%>">
                                <a href="revT_cercaM5.jsp" class="nav-link ">
                                    <i class="fa fa-search "></i>
                                    <span class="title"> Cerca Tirocini</span>
                                    <span class="badge bg-blue-chambray"></span>
                                </a>
                            </li>
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
