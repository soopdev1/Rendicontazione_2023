
<%@page import="com.seta.activity.Action"%>
<%String guida_ente = Action.getPath("guida_ente");%>
<li class="nav-item start">
    <a href="OperazioniGeneral?type=6&path=<%=guida_ente%>" class="nav-link nav-toggle">
        <i class="fa fa-download"></i>
        <span class="title"> SCARICA GUIDA</span>       
        <span class="selected"></span>
    </a>                
</li>