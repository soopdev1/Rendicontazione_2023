<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.io.File"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.seta.entity.Bando"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.activity.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "reg_showdocs.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            String idp = request.getParameter("idpol");
            String[] listdocs = Action.getListDocsTirocinante(idp);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta charset="ISO-8859-1" />
        <title>Rendicontazione</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="assets/seta/fontg/fontsgoogle1.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->


        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />

        <link href="assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/clockface/css/clockface.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />

        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->

        <link href="assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />

        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/themes/grey.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />

        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/seta/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/seta/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/seta/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/seta/js/fancy.js"></script>

        <link rel="stylesheet" href="AdminLTE-2.4.2/dist/css/AdminLTE.min.css">
        <link rel="shortcut icon" href="assets/seta/img/favicon.ico" />

    </head>

    <script type="text/javascript">
        function submitfor(nameform) {
            document.forms[nameform].submit();
        }
    </script>

    <script type="text/javascript">

        function showmod1(modal, idtext, text) {
            document.getElementById(modal).className = document.getElementById(modal).className + " in";
            document.getElementById(idtext).innerHTML = text;
            document.getElementById(modal).style.display = "block";
        }

        function dismiss(modal) {
            document.getElementById(modal).className = "modal fade";
            document.getElementById(modal).style.display = "none";
        }


    </script>


    <body class="page-full-width page-content-white">


        <div class="page-container">
            <div class="page-content-wrapper" >
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">                    
                    <div class="clearfix"></div>

                    <div class="portlet light bordered ">
                        <div class="portlet-title">
                            <div class="caption">
                                <span class="caption-subject font-blue bold">
                                    <h3 class="page-title"> Consulta documentazione per <strong> <%=listdocs[2]%> <%=listdocs[1]%> </strong></h3>                                    
                                </span>
                            </div>
                        </div>                    
                    </div>
                    <section>      
                        <div class="col-lg-3 col-xs-6">
                            <div class="small-box">
                                <div class="icon">
                                     <label style="color: #000000"><h4>Carta d'identità Destinatario</h4></label><br>
                                    <a class="fancybox fancyBoxRafFull" href="OperazioniGeneral?type=5&path=<%=StringUtils.replace(listdocs[4], "\\", "/")%>" style="color: red;"><i class="fa fa-file-pdf-o"></i></a>                                                           
                                </div>
                            </div>
                        </div>
                    </section>
                    <section>   
                        <div class="col-lg-3 col-xs-6">
                            <div class="small-box"> 
                                <div class="icon">
                                    <label style="color: #000000"><h4>Carta d'identità Tutor</h4></label><br>
                                    <a class="fancybox fancyBoxRafFull" href="OperazioniGeneral?type=5&path=<%=StringUtils.replace(listdocs[5], "\\", "/")%>" style="color: red;"><i class="fa fa-file-pdf-o"></i></a>                                                           
                                </div>
                            </div>
                        </div>
                    </section>
                     <section>   
                        <div class="col-lg-3 col-xs-6">
                            <div class="small-box"> 
                                <div class="icon">
                                     <label style="color: #000000"><h4>Documento M5</h4></label><br>
                                    <a class="fancybox fancyBoxRafFull" href="OperazioniGeneral?type=5&path=<%=StringUtils.replace(listdocs[6], "\\", "/")%>" style="color: red;"><i class="fa fa-file-pdf-o"></i></a>                                                           
                                </div>
                            </div>
                        </div>
                    </section>

                </div>
            </div>
        </div> 
    </div>                                
    <!-- BEGIN CORE PLUGINS -->
    <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>

    <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>

    <!-- END CORE PLUGINS -->
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <script src="assets/seta/js/select2.full.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
    <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>

    <script src="assets/pages/scripts/components-select2.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
    <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
    <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
    <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
    <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
    <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
    <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
    <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>



    <!-- date-range-picker -->
    <script src="AdminLTE-2.4.2/bower_components/moment/min/moment.min.js"></script>
    <script src="AdminLTE-2.4.2/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
    <!-- bootstrap datepicker -->
    <script src="AdminLTE-2.4.2/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
    <!--bootstrap color picker -->
    <script src="AdminLTE-2.4.2/bower_components/bootstrap-colorpicker/dist/js/bootstrap-colorpicker.min.js"></script>

    <!--iCheck 1.0.1-->
    <script src="AdminLTE-2.4.2/plugins/iCheck/icheck.min.js"></script>





</body>

</html>
<%}
    }%>


