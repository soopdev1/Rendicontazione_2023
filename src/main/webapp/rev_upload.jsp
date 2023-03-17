<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.activity.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "rev_upload.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            //Etichette et = new Etichette("IT");
            ArrayList<String[]> tipoBando = Action.getList_TipoBando();
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

        function ctrlForm() {
            var titolo = $("#titolo").val();
            var datainizio = $("#datainizio").val();
            var tipo = $("#tipo").val();
            var budget = $("#budget").val();
            var datafine = $("#datafine").val();
            var flag_sportello = document.getElementById("flag_sportello").checked;
            var err = false;
            var nomefile = $("#file").val().split('\\').pop();
            var estensione = nomefile.substring(nomefile.lastIndexOf(".")).toLowerCase();

            if (nomefile != "") {
                if (estensione != ".pdf" || estensione != ".PDF") {
                    $("#div_file").attr("class", "input-group input-large has-error");
                    $("#a_file").css("display", "block");
                    err = true;
                } else {
                    $("#div_file").attr("class", "input-group input-large has-success");
                    $("#a_file").css("display", "none");
                }
            }

            if (titolo == "") {
                $("#div_titolo").attr("class", "col-md-4 has-error");
                err = true;
            } else {
                $("#div_titolo").attr("class", "col-md-4 has-success");
            }

            if (datainizio == "") {
                $("#div_datainizio").attr("class", "col-md-3 has-error");
                err = true;
            } else {
                $("#div_datainizio").attr("class", "col-md-3 has-success");
            }

            if (tipo == "0" || tipo == null) {
                $("#div_tipo").attr("class", "col-md-3 has-error");
                err = true;
            } else {
                $("#div_tipo").attr("class", "col-md-3 has-success");
            }

            if (budget == "") {
                $("#div_budget").attr("class", "col-md-3 has-error");
                err = true;
            } else {
                $("#div_budget").attr("class", "col-md-3 has-success");
            }

            if (flag_sportello == false) {

                if (datafine == "") {
                    $("#div_datafine").attr("class", "col-md-3 has-error");
                    err = true;
                } else {
                    $("#div_datafine").attr("class", "col-md-3 has-success");
                }
            }

            if (flag_sportello == false && datafine != "") {
                var stryear1 = datainizio.substring(6, 10);
                var strmth1 = datainizio.substring(3, 5);
                var strday1 = datainizio.substring(0, 2);
                var date1 = new Date(stryear1, strmth1, strday1);

                var stryear2 = datafine.substring(6, 10);
                var strmth2 = datafine.substring(3, 5);
                var strday2 = datafine.substring(0, 2);
                var date2 = new Date(stryear2, strmth2, strday2);

                if (date2 < date1) {
                    //alert("Start date must be prior to end date");
                    $("#div_datainizio").attr("class", "col-md-3 has-warning");
                    $("#div_datafine").attr("class", "col-md-3 has-warning");
                    err = true;
                } else {
                    $("#div_datainizio").attr("class", "col-md-3 has-success");
                    $("#div_datafine").attr("class", "col-md-3 has-success");
                }
            }
            if (err) {
                return false;
            }
            return true;
        }

        function ctrlFile(file) {
            var nomefile = $("#" + file).val().split('\\').pop();
            var estensione = nomefile.substring(nomefile.lastIndexOf("."));
            if (estensione == null || estensione == "") {
                $("#div_" + file).attr("class", "col-md-4");
                $("#a_" + file).css("display", "none");
                return false;
            } else if (estensione != ".pdf" && estensione != ".PDF") {
                $("#div_" + file).attr("class", "col-md-4 has-error");
                $("#a_" + file).css("display", "block");
                return false;
            } else if (!ctrlDim(file)) {
                return false;
            }  else if (estensione == ".pdf" || estensione == ".PDF") {
                $("#div_" + file).attr("class", "col-md-4 has-success");
                $("#a_" + file).css("display", "none");
                return true;
            }
        }

        function ctrlDim(id) {
            var file = Number(document.getElementById(id).files[0].size);
            if (file > 10485760) {
                $("#div_" + id).attr("class", "col-md-4 has-error");
                $("#a_" + id).css("display", "block");
                $("#a_" + id).html("<i class='fa fa-exclamation-triangle'></i> File di dimensioni eccessive");
                return false;
            }
            $("#a_" + id).css("display", "none");
            return true;
        }

        function mostradatafine() {
            var flag_sportello = document.getElementById("flag_sportello").checked;
            if (flag_sportello === false) {
                document.getElementById('div_datafine').style.display = "block";
            } else {
                document.getElementById('div_datafine').style.display = "none";
                document.getElementById("datafine").value = "";
            }
        }


    </script>


    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">

        <div class="modal fade bs-modal-lg" id="large" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-purple-seance  alert-dismissible">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <font color="white"><h4 class="modal-title">Attenzione</h4></font>
                    </div>
                    <div class="modal-body" id="largetext">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn large btn-danger" data-dismiss="modal" onclick="return dismiss('large');">Chiudi</button>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="menu/header.jsp"%>
        <!-- BEGIN HEADER -->
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>

        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <%@ include file="menu_revisore/menu_configura.jsp"%>
            <div class="page-content-wrapper" >
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <div class="logo">
                        <img src="assets/seta/img/logos1.png" height="75"  align="right" /> 
                    </div>  
                    <h3 class="page-title"><strong> HOME </strong><small> Regional</small> </h3>  
                    <% String esitoins = request.getParameter("esitoins");

                        if (esitoins == null) {
                            esitoins = "";
                        }

                        if (esitoins.equals("KO")) {%>           
                    <br>
                    <div class="alert bg-red-thunderbird  alert-dismissible " style="text-align: center;color: white">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Errore, il modello non è stato caricato! Riprovare</strong></span>            
                    </div>
                    <%} else if (esitoins.equals("OK")) {%> 
                    <br>
                    <div class="alert bg-green-jungle alert-dismissible " style="text-align: center;color: white">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Modello caricato con successo!</strong></span>
                    </div>
                    <%}%>
                    <div class="portlet box blue-soft">
                        <div class="portlet-title">
                            <div class="caption">  
                                <i class="fa fa-upload font-white"></i>
                                <span class="caption-subject font-white bold ">Carica Modello</span>
                            </div>
                        </div>

                        <div class="portlet-body form">
                            <form class="form-horizontal" action="<%="/OperazioniReviser?type=1"%>" method="post" enctype="multipart/form-data" onsubmit="return ctrlForm();">
                                <div class="form-body">
                                    <div class="form-group">
                                        <div class="col-md-4" id="div_titolo">
                                            <label>Titolo</label><label type="text" style="color: red" >*</label>
                                            <input class="form-control" type="text" id="titolo" name="titolo">
                                        </div>

                                        <div class="col-md-3" id="div_tipo">
                                            <label>Tipo Bando</label><label type="text" style="color: red" >*</label>
                                            <select class="form-control select2-allow-clear" data-placeholder="" name="tipo" id="tipo" >
                                                <option value="0">...</option>
                                                <%for (int i = 0; i < tipoBando.size(); i++) {%>
                                                <option value="<%=tipoBando.get(i)[0]%>"><%=tipoBando.get(i)[1]%></option>
                                                <%}%>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-3" id="div_budget">
                                            <label>Budget</label><label type="text" style="color: red" >*</label>
                                            <input class="form-control" type="number" id="budget" name="budget" min="1" step="1">
                                        </div>
                                        <div class="col-md-4">
                                            <label>File</label><label type="text" style="color: red" >&nbsp;</label><br>
                                            <div class="fileinput fileinput-new" data-provides="fileinput">
                                                <div class="input-group input-large" id="div_file">
                                                    <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                        <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                        <span class="fileinput-filename"> </span>
                                                    </div>
                                                    <span class="input-group-addon btn default btn-file">
                                                        <span class="fileinput-new"> Scegli file </span>
                                                        <span class="fileinput-exists"> Modifica </span>
                                                        <input type="file" id="file" name="file" onchange="ctrlFile('file');"> </span>
                                                    <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Remove </a>
                                                </div>
                                                <a id="a_file" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                    <i class="fa fa-exclamation-triangle"></i> Selezionare un file PDF
                                                </a>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-3" id="div_datainizio">
                                            <label>Data Inizio</label><label type="text" style="color: red" >*</label>
                                            <div class="input-group input-medium date date-picker" data-date-format="dd/mm/yyyy" >
                                                <input type="text" class="form-control" id="datainizio" name="datainizio">
                                                <span class="input-group-btn">
                                                    <button class="btn default" type="button">
                                                        <i class="fa fa-calendar"></i>
                                                    </button>
                                                </span>
                                            </div>
                                            <!--span class="help-block"> Data Inizio </span-->
                                        </div>

                                        <div class="col-md-2" id="div_sportello" > 
                                            <label>Sportello</label><br/>
                                            <input type="checkbox" class="make-switch form-control" name="flag_sportello" id="flag_sportello" onchange="return mostradatafine();" data-on-text="<i class='fa fa-check'></i>" data-off-text="<i class='fa fa-times'></i>" >
                                        </div>

                                    </div>

                                    <div class="form-group ">       
                                        <div class="col-md-3" id="div_datafine" style="display: block;">
                                            <label>Data Fine</label><label type="text" style="color: red" >*</label>
                                            <div class="input-group input-medium date date-picker" data-date-format="dd/mm/yyyy" >
                                                <input type="text" class="form-control" id="datafine" name="datafine">
                                                <span class="input-group-btn">
                                                    <button class="btn default" type="button">
                                                        <i class="fa fa-calendar"></i>
                                                    </button>
                                                </span>
                                            </div>
                                            <!--span class="help-block"> Data Inizio </span-->
                                        </div>        
                                    </div>      
                                    <div class="form-group ">
                                        <div class="col-md-4">
                                            <label type="text" style="color: red" >* Campo Obbligatorio</label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-4"> 
                                            <button type="submit" class="btn blue btn-outline"><i class="fa fa-upload"></i> Carica</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
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


