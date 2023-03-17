
<%@page import="com.seta.util.Etichette"%>
<%
    String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String up = "";
    String sea = "";
    String tr = "";

    if (pageName.equals("upload_bando.jsp")) {
        up = "active open";
    } else if (pageName.equals("search_bandi.jsp")) {
        sea = "active open";
    } else if (pageName.equals("transfer_bandi.jsp")) {
        tr = "active open";
    }
%>


<div class="page-sidebar-wrapper">
    <!-- BEGIN SIDEBAR -->
    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
    <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
        <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
        <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
        <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
        <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
        <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
        <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
            <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
            <li class="sidebar-toggler-wrapper hide">
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="sidebar-toggler">
                    <span></span>
                </div>
                <!-- END SIDEBAR TOGGLER BUTTON -->
            </li>
            <!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->

            <li class="nav-item start">
                <a href="index_Regional.jsp" class="nav-link nav-toggle">
                    <i class="icon-home"></i>
                    <span class="title"> HOME</span>
                    <span class="selected"></span>

                </a>
            </li>
            <li class="nav-item start active open ">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-file-text"></i>
                    <span class="title"> GESTIONE BANDI</span>
                    <span class="selected"></span>
                    <span class="arrow open"></span>
                </a>                
                <ul class="sub-menu">
                    <li class="nav-item start <%=up%> ">
                        <a href="upload_bando.jsp" class="nav-link ">
                            <i class="fa fa-upload"></i>
                            <span class="title"> Carica</span>
                            <span class="badge bg-blue-chambray"></span>
                        </a>
                    </li>
                    <li class="nav-item start <%=sea%>">
                        <a href="search_bandi.jsp" class="nav-link ">
                            <i class="fa fa-search"></i>
                            <span class="title"> Consulta</span>
                            <span class="badge bg-blue-chambray"></span>
                        </a>
                    </li>
                    <li class="nav-item start <%=tr%>">
                        <a href="transfer_bandi.jsp" class="nav-link ">
                            <i class="fa fa-exchange"></i>
                            <span class="title"> Trasferimento Denaro</span>
                            <span class="badge bg-blue-chambray"></span>
                        </a>
                    </li>
                </ul>
            </li>
            <%@ include file="general/lavoratori_general.jsp"%>
            <%@ include file="general/garanziagiovani_general.jsp"%> 
            <%@ include file="general/dote_general.jsp"%>
            <%@ include file="general/downloadGuida.jsp"%>
        </ul>
        <!-- END SIDEBAR MENU -->
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
</div>
