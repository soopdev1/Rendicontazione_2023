
<%@page import="com.seta.activity.Action"%>
<%String guida = Action.getPath("guida_revisore");%>
<li class="nav-item start">
    <a href="OperazioniGeneral?type=6&path=<%=guida%>" class="nav-link nav-toggle">
        <i class="fa fa-download"></i>
        <span class="title"> SCARICA GUIDA</span>       
        <span class="selected"></span>
    </a>                
</li>