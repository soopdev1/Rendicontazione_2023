
<%@page import="com.seta.util.Etichette"%>
<%
    String user = session.getAttribute("username").toString();
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);

    String uri = request.getRequestURI();
    String pageName = uri.substring(uri.lastIndexOf("/") + 1);

    String r_up = "", r_dl = "";

    if (pageName.equals("rev_upload.jsp")) {
        r_up = "active open";
    } else if (pageName.equals("rev_download.jsp")) {
        r_dl = "active open";
    }

%>


<div class="page-sidebar-wrapper">    
    <div class="page-sidebar navbar-collapse collapse">        
        <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
            <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
            <li class="sidebar-toggler-wrapper hide">
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="sidebar-toggler">
                    <span></span>
                </div>
                <!-- END SIDEBAR TOGGLER BUTTON -->
            </li>
            <li class="nav-item start ">
                <a href="index_Reviser.jsp" class="nav-link nav-toggle">
                    <i class="icon-home"></i>
                    <span class="title"> HOME</span>
                    <span class="selected"></span>

                </a>
            </li>   
            <li class="nav-item start active open">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-folder-open"></i>
                    <span class="title"> CONFIGURA</span>
                    <span class="selected"></span>
                    <span class="arrow open"></span>
                </a>                
                <ul class="sub-menu">
                    <li class="nav-item start <%=r_up%>">
                        <a href="rev_upload.jsp" class="nav-link ">
                            <i class="fa fa-cloud-upload"></i>
                            <span class="title"> Upload Modello</span>
                            <span class="badge bg-blue-chambray"></span>
                        </a>
                    </li>
                    <li class="nav-item start <%=r_dl%>">
                        <a href="rev_download.jsp" class="nav-link ">
                            <i class="fa fa-cloud-download"></i>
                            <span class="title"> Download Modello</span>
                            <span class="badge bg-purple-seance"></span>
                        </a>
                    </li>

                </ul>
            </li>
            <%@ include file="general/lavoratori_general.jsp"%>
            <%@ include file="general/garanziagiovani_general.jsp"%>
            <%@ include file="general/dote_general.jsp"%>
            <%@ include file="general/downloadGuida.jsp"%>
        </ul>
    </div>
    <!-- END SIDEBAR -->
</div>
