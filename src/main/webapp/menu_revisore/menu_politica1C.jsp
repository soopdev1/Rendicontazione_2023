
<%@page import="com.seta.util.Etichette"%>
<%
    String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String liq = "";
    String esiti = "";
    if (pageName.equals("rev_liquidazione1C.jsp")) {
        liq = "active open";
    } else if (pageName.equals("rev_esiti1C.jsp")) {
        esiti = "active open";
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
            <li class="nav-item start ">
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
                    <%@ include file="politiche_gg/misura5.jsp"%>
                    <%@ include file="politiche_gg/misura3.jsp"%>
                    <%@ include file="politiche_gg/politica1b.jsp"%>
                    <li class="nav-item active open">
                        <a href="javascript:;" class="nav-link nav-toggle">
                            <i class="fa fa-arrow-down"></i> Politica 1C
                            <span class="arrow"></span>
                        </a>
                        <ul class="sub-menu">
                            <li class="nav-item start <%=liq%>">
                                <a href="rev_liquidazione1C.jsp" class="nav-link nav-toggle">
                                    <i class="fa fa-money"></i>
                                    <span class="title"> Ammiss. Indennità</span>       
                                    <span class="badge bg-blue-chambray"></span>
                                </a>   
                            </li>
                            <li class="nav-item start <%=esiti%>">
                                <a href="rev_esiti1C.jsp" class="nav-link nav-toggle">
                                    <i class="fa fa-check"></i><i class="fa fa-close"></i>
                                    <span class="title"> Esiti</span>       
                                    <span class="badge bg-blue-chambray"></span>
                                </a>   
                            </li>
                        </ul>
                    </li>
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
