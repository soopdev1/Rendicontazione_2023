<%-- 
    Document   : menu_gg
    Created on : 17-ott-2018, 10.56.34
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

    String add_c = "";
    String search_c = "";
    if (pageName.equals("addConvenzione.jsp")) {
        add_c = "active open";
    } else if (pageName.equals("searchConvenzione.jsp")) {
        search_c = "active open";
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
            <li class="nav-item start active open">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-pencil"></i>
                    <span class="title"> CONVENZIONE</span>       
                    <span class="selected"></span>
                    <span class="arrow open"></span>
                </a>                
                <ul class="sub-menu">        
                    <li class="nav-item start <%=add_c%>">
                        <a href="addConvenzione.jsp" class="nav-link ">
                            <i class="fa fa-plus"></i>
                            <span class="title"> Aggiungi</span>
                            <span class="badge bg-blue-chambray"></span>
                        </a>
                    </li>
                    <li class="nav-item start <%=search_c%>">
                        <a href="searchConvenzione.jsp" class="nav-link ">
                            <i class="fa fa-search"></i>
                            <span class="title"> Cerca</span>
                            <span class="badge bg-blue-chambray"></span>
                        </a>
                    </li>
                </ul>
            </li>
            <%@ include file="general/Tutor_general.jsp"%>
            <%@ include file="general/lavoratori_general.jsp"%>
            <%@ include file="general/gg_general.jsp"%>
            <%@ include file="general/dote_general.jsp"%>
            <%@ include file="general/downloadGuida.jsp"%>
        </ul>

        <!-- END SIDEBAR MENU -->
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
</div>
