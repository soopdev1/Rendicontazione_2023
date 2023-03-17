<%@page import="com.seta.util.Etichette"%>

<%
    String username = (String) session.getAttribute("username");
    String lang = (String) session.getAttribute("language");
    String n = (String) session.getAttribute("nome");
    String c = (String) session.getAttribute("cognome");
    String e = (String) session.getAttribute("ente");
    lang = "IT";
    Etichette et = new Etichette(lang);
%>
<!--CSS E SCRIPT PER LOGO DI LOADING-->
<!--<style>
    .loader {
        position: fixed;
        left: 45%;
        top: 45%;
        width: 15%;
        height: 15%;
        z-index: 9999;
        background: url('assets/seta/img/load.gif') 50% 50% no-repeat;
        opacity: 1;
    }

</style>
<script type="text/javascript">
    $(window).load(function () {
        $(".loader").fadeOut("slow");
    });
</script>-->

<div class="page-header navbar navbar-fixed-top">
    <!-- BEGIN HEADER INNER -->
    <div class="page-header-inner ">
        <!-- BEGIN LOGO -->
        <div class="page-logo"> 

            <div class="menu-toggler sidebar-toggler" >
                <span></span>
            </div>
        </div>
        <!-- END LOGO -->
        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
            <span></span>
        </a>

        <!-- END RESPONSIVE MENU TOGGLER -->
        <!-- BEGIN TOP NAVIGATION MENU -->
        <div class="top-menu">
            <ul class="nav navbar-nav pull-right">
                <li class="dropdown dropdown-user">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true" style=" font-size:2vh;">
                        <%if ((int) session.getAttribute("tipo") == 1) {%>
                        <img alt="" class="img-circle" src="assets/seta/img/calabria.png" />
                        <%} else {%>
                        <img alt="" class="img-circle" src="assets/seta/img/av3.png" />
                        <%}%>
                        <span><font color="white"> <%=n%> <%=c%> | <%=e%></font></span>
                        <i class="fa fa-angle-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-default">
                        <li>
                            <a href="profile.jsp">
                                <i class="icon-user"></i> Profilo Personale </a> 
                        </li>
                    </ul>
                </li>
                <!-- END USER LOGIN DROPDOWN -->
                <!-- BEGIN QUICK SIDEBAR TOGGLER -->
                <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                <li class="dropdown dropdown-quick-sidebar-toggler">
                    <a href="<%="Login?type=2"%>" class="dropdown-toggle tooltips" data-placement="bottom" data-original-title="<%=et.getHeader1_logout()%>">
                        <i class="icon-logout"></i>
                    </a>
                </li>
                <!-- END QUICK SIDEBAR TOGGLER -->
            </ul>
        </div>
        <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END HEADER INNER -->
</div>