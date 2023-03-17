
<%@page import="com.seta.activity.Action"%>
<%@page import="com.seta.util.Etichette"%>
<%
    String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String add_t = "";
    String search_t = "";
    if (pageName.equals("addTutor.jsp")) {
        add_t = "active open";
    } else if (pageName.equals("searchTutor.jsp")) {
        search_t = "active open";
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
                <a href="index_Ente.jsp" class="nav-link nav-toggle">
                    <i class="icon-home"></i>
                    <span class="title"> HOME</span>
                    <span class="selected"></span>

                </a>
            </li>
            <%@ include file="general/Convenzione_general.jsp"%>
            <li class="nav-item start  active open">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-graduation-cap"></i>
                    <span class="title"> TUTOR</span>       
                    <span class="selected"></span>
                    <span class="arrow open"></span>
                </a>                
                <ul class="sub-menu">
                    <li class="nav-item start <%=add_t%>">
                        <a href="addTutor.jsp" class="nav-link ">
                            <i class="fa fa-plus"></i>
                            <span class="title"> Aggiungi</span>
                            <span class="badge bg-blue-chambray"></span>
                        </a>
                    </li>
                    <li class="nav-item start <%=search_t%>">
                        <a href="searchTutor.jsp" class="nav-link ">
                            <i class="fa fa-search"></i>
                            <span class="title"> Gestione</span>
                            <span class="badge bg-blue-chambray"></span>
                        </a>
                    </li>
                </ul>
            </li>
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
