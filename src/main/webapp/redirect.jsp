<%-- 
    Document   : rediredt
    Created on : 30-ott-2018, 11.17.00
    Author     : agodino
--%>
<%@page import="com.seta.util.Utility"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.seta.activity.Action"%>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "redirect.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <form action="<%=request.getParameter("page")%>" method="post" name="form" id="form" accept-charset="ISO-8859-1" style="display: none">
            <%Enumeration<String> parameterNames = request.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String paramName = parameterNames.nextElement();
                    String[] paramValues = request.getParameterValues(paramName);
                    if (!paramName.equals("page")) {
                        for (int i = 0; i < paramValues.length; i++) {%>
            <input name="<%=paramName%>" value="<%=paramValues[i]%>">
            <%}
                    }
                }%>
        </form>
    </body>
    <script type="text/javascript" src="assets/seta/js/jquery-1.10.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            document.getElementById("form").submit();
        });
    </script>
</html>
<%}
    }%>
