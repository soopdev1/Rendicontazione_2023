<%-- 
    Document   : addtutor
    Created on : 15-ott-2018, 14.33.16
    Author     : agodino
--%>

<%@page import="com.seta.entity.Politica"%>
<%@page import="com.seta.entity.Tutor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.activity.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "upDocContratto_dt.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            //Etichette et = new Etichette("IT");
            String id = request.getParameter("idpolitica");
            ArrayList<Tutor> tutor = Action.getListTutorEnte((int) session.getAttribute("idente"));
            Politica pol = Action.getPoliticaByIDDt(id);
            pol.setContratto(Action.getContrattoByIdDt(pol.getId()));
%>
<html>
    <head>
        <meta charset="utf-8">
        <meta charset="ISO-8859-1" />
        <title>Rendicontazione</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <link rel="shortcut icon" href="assets/seta/img/logos1.png">
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

        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <link href="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />

        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />

        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="assets/layouts/layout/css/custom.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />


        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/seta/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/seta/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/seta/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/seta/js/fancy.js"></script> 

        <link rel="stylesheet" href="AdminLTE-2.4.2/dist/css/AdminLTE.css">
        <link rel="shortcut icon" href="assets/seta/img/favicon.ico" />


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

            function ctrlAllDim() {
                if ($("#file").val().split('\\').pop() != "" && $("#m5").val().split('\\').pop() != "" && $("#contratto").val().split('\\').pop() != "") {
                    var file = Number(document.getElementById('file').files[0].size);
                    var m5 = Number(document.getElementById('m5').files[0].size);
                    var contratto = Number(document.getElementById('contratto').files[0].size);
                    if ((file + m5 + contratto) > 10485760) {
                        $("#a_file").css("display", "block");
                        $("#a_m5").css("display", "block");
                        $("#a_contratto").css("display", "block");
                        $("#a_file").html("<i class='fa fa-exclamation-triangle'></i> Dimensione Dei File Eccessiva");
                        $("#a_m5").html("<i class='fa fa-exclamation-triangle'></i> Dimensione Dei File Eccessiva");
                        $("#a_contratto").html("<i class='fa fa-exclamation-triangle'></i> Dimensione Dei File Eccessiva");
                        $("#div_contratto").attr("class", "col-md-4 has-error");
                        $("#div_file").attr("class", "col-md-4 has-error");
                        $("#div_m5").attr("class", "col-md-4 has-error");
                        return false;
                    } else {
                        resetDiv(file, 'file');
                        resetDiv(m5, 'm5');
                        resetDiv(contratto, 'contratto');
                        return true;
                    }
                } else {
                    resetDiv(file, 'file');
                    resetDiv(m5, 'm5');
                    resetDiv(contratto, 'contratto');
                    return true;
                }
            }

            function resetDiv(size, id) {
                if (size > 0) {
                    $("#a_" + id).css("display", "none");
                    $("#div_" + id).attr("class", "col-md-4 has-success");
                } else {
                    $("#a_" + id).css("display", "none");
                    $("#div_" + id).attr("class", "col-md-4");
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

//            function mostradatafine() {
//
//                var flag = document.getElementById("indeterminato").checked;
//                if (flag === false) {
//                    document.getElementById('div_fine').style.display = "block";
//                } else {
//                    document.getElementById('div_fine').style.display = "none";
//                    document.getElementById("fine").value = "";
//                }
//            }
//
//            function checkDate() {
//
//                if ($('#inizio').val() != "" && $('#fine').val() != "") {
//                    var datainizio = $('#inizio').val();
//                    var datafine = $('#fine').val();
//                    var stryear1 = datainizio.substring(6, 10);
//                    var strmth1 = datainizio.substring(3, 5);
//                    var strday1 = datainizio.substring(0, 2);
//                    var start = new Date(stryear1, strmth1, strday1);
//
//                    var stryear2 = datafine.substring(6, 10);
//                    var strmth2 = datafine.substring(3, 5);
//                    var strday2 = datafine.substring(0, 2);
//                    var end = new Date(stryear2, strmth2, strday2);
//
//                    if (start > end) {
//                        $("#div_inizio").attr("class", "col-md-3 has-error");
//                        $("#div_fine").attr("class", "col-md-3 has-error");
//                        showmod1('large', 'largetext', 'Data inizio successiva alla scadenza')
//                        return false;
//                    }
//                }
//                return true;
//            }
        </script>

    </head>


    <body class="page-full-width page-content-white">
        <div class="modal fade bs-modal-lg" id="large" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-red  alert-dismissible">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <font color="white"><h4 class="modal-title">Attenzione</h4></font>
                    </div>
                    <div class="modal-body" id="largetext">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" data-dismiss="modal" onclick="return dismiss('large');">Chiudi</button>
                    </div>
                </div>
            </div>
        </div>

        <!--BEGIN HEADER -->
        <div class="page-container">
            <div class="page-content-wrapper" >
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">                    
                    <div class="clearfix"></div>
                    <div class="row">
                        <% String esitoedit = request.getParameter("esitoedit");

                            if (esitoedit == null) {
                                esitoedit = "";
                            }

                            if (esitoedit.equals("KO")) {%>           
                        <br>
                        <div class="alert bg-red-thunderbird  alert-dismissible " style="text-align: center;color: white">
                            <button type="button" class="close" data-close="alert"></button>
                            <span><strong>Errore! Riprovare</strong></span>            
                        </div>
                        <%} else if (esitoedit.equals("OK")) {%> 
                        <br>
                        <div class="alert bg-green-jungle alert-dismissible " style="text-align: center;color: white">
                            <button type="button" class="close" data-close="alert"></button>
                            <span><strong>Documento aggiornato con successo!</strong></span>
                        </div>
                        <%} else if (esitoedit.equals("")) {%>
                        <div class="portlet light bordered ">
                            <div class="portlet-title">
                                <div class="caption">
                                    <span class="caption-subject font-blue bold">
                                        <h3 class="page-title"><strong> CARICA DOCUMENTI E CONTRATTO</strong> </h3>                                    
                                    </span>
                                </div>
                            </div>
                            <%if (pol.getContratto() == null) {%>
                            <script type="text/javascript">

                                function ctrlFile1(id) {
                                    var nomefile = $("#" + id).val().split('\\').pop();
                                    var estensione = nomefile.substring(nomefile.lastIndexOf("."));
                                    if (estensione == null || estensione == "") {
                                        $("#div_" + id).attr("class", "col-md-4");
                                        $("#a_" + id).css("display", "none");
                                        return false;
                                    } else if (estensione != ".pdf" && estensione != ".PDF") {
                                        $("#div_" + id).attr("class", "col-md-4 has-error");
                                        $("#a_" + id).css("display", "block");
                                        $("#a_" + id).html("<i class='fa fa-exclamation-triangle'></i> Selezionare file pdf!");
                                        return false;
                                    } else if (!ctrlDim(id)) {
                                        return false;
                                    } else if (!ctrlAllDim()) {
                                        return false;
                                    } else if (estensione == ".pdf" || estensione == ".PDF") {
                                        $("#div_" + id).attr("class", "col-md-4 has-success");
                                        $("#a_" + id).css("display", "none");
                                        return true;
                                    }
                                }

                                function ctrlForm() {
                                    var err = false;

                                    if (!ctrlFile1('file')) {
                                        $("#div_file").attr("class", "col-md-3 has-error");
                                        err = true;
                                    }
                                    if (!ctrlFile1('m5')) {
                                        $("#div_m5").attr("class", "col-md-4 has-error");
                                        err = true;
                                    }
                                    if (!ctrlFile1('contratto')) {
                                        $("#div_contratto").attr("class", "col-md-4 has-error");
                                        err = true;
                                    }
                                    if (err) {
                                        return false;
                                    }
                                    return true;
                                }
                            </script>
                            <div class="portlet-body form">
                                <form class="form-horizontal" action="<%="OperazioniEnte?type=24"%>" method="post" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlForm();" enctype="multipart/form-data">
                                    <input type="hidden" value="<%=id%>" name="id">
                                    <input type="hidden" value="new" name="tipo">
                                    <div class="form-body">
                                        <br/><label><strong>Dati Del Destinatario:</strong></label>
                                        <div class="form-group portlet light bordered">
                                            <div class="col-md-3" id="div_tutor" style="display: none;">
                                                <label>Tutor</label><label type="text" style="color: red" >*</label>
                                                <select class="form-control select2-allow-clear" id="tutor" name="tutor">
                                                    <option value="...">...</option>
                                                    <%for (Tutor t : tutor) {%>
                                                    <option value="<%=t.getId()%>"><%=t.getNome()%> <%=t.getCognome()%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                            <div class="form-group">  
                                                <div class="col-md-4" id="div_file">
                                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                                        <label>Carta d'Identità Destinatario</label><label type="text" style="color: red" >*</label>
                                                        <div class="input-group input-large">
                                                            <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                                <span class="fileinput-filename"> </span>
                                                            </div>
                                                            <span class="input-group-addon btn default btn-file">
                                                                <span class=" fileinput-new"> Scegli file </span>
                                                                <span class="fileinput-exists"> Modifica </span>
                                                                <input type="file" id="file" name="file" onchange="ctrlFile1('file');" accept="application/pdf"></span>                                                    
                                                            <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                        </div>
                                                        <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                            <a id="a_file" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                                <i id="a_file" class="fa fa-exclamation-triangle"></i> Selezionare un file PDF
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div></div>
                                            <div class="form-group">
                                                <div class="col-md-4" id="div_m5">
                                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                                        <label>Allegato 8</label><label type="text" style="color: red" >*</label>
                                                        <div class="input-group input-large">
                                                            <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                                <span class="fileinput-filename"> </span>
                                                            </div>
                                                            <span class="input-group-addon btn default btn-file">
                                                                <span class=" fileinput-new"> Scegli file </span>
                                                                <span class="fileinput-exists"> Modifica </span>
                                                                <input type="file" id="m5" name="m5" onchange="ctrlFile1('m5');" accept="application/pdf"> </span>
                                                            <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                        </div>
                                                        <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                            <a id="a_m5" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                                <i id="i_m5" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <br/><label><strong>Dati Del Contratto:</strong></label>
                                        <div class="portlet light bordered">
                                            <div class="form-group" style="display: none;">
                                                <div class="col-md-3" id="div_inizio" style="padding-right: 0px;">
                                                    <label>Data Inizio Contratto</label><label type="text" style="color: red" >*</label>
                                                    <div class="input-group date date-picker" data-date-format="dd/mm/yyyy">
                                                        <input type="text" class="form-control" id="inizio" name="inizio" autocomplete="off" readonly onchange="return checkDate();">
                                                        <span class="input-group-btn">
                                                            <button class="btn default" type="button">
                                                                <i class="fa fa-calendar"></i>
                                                            </button>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="col-md-1" id="div_indetrminato">
                                                    <label>Indeterminato</label><br/>
                                                    <input type="checkbox" class="make-switch form-control" name="indeterminato" id="indeterminato" onchange="return mostradatafine();" data-on-text="<i class='fa fa-check'></i>" data-off-text="<i class='fa fa-times'></i>" >
                                                </div>
                                                <div class="col-md-3" id="div_fine">
                                                    <label>Data Fine Contratto</label><label type="text" style="color: red" >*</label>
                                                    <div class="input-group date date-picker" data-date-format="dd/mm/yyyy">
                                                        <input type="text" class="form-control" id="fine" name="fine" autocomplete="off" readonly onchange="return checkDate();">
                                                        <span class="input-group-btn">
                                                            <button class="btn default" type="button">
                                                                <i class="fa fa-calendar"></i>
                                                            </button>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-md-3" id="div_contratto">
                                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                                        <label>Contratto Di Lavoro</label><label type="text" style="color: red" >*</label>
                                                        <div class="input-group input-large">
                                                            <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                                <span class="fileinput-filename"> </span>
                                                            </div>
                                                            <span class="input-group-addon btn default btn-file">
                                                                <span class=" fileinput-new"> Scegli file </span>
                                                                <span class="fileinput-exists"> Modifica </span>
                                                                <input type="file" id="contratto" name="contratto" onchange="ctrlFile1('contratto')" accept="application/pdf"></span>                                                    
                                                            <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                        </div>
                                                        <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                            <a id="a_contratto" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                                <i id="i_contratto" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <br><label type="text" style="color: red" >* Campo Obbligatorio</label><br><br>
                                        <div class="form-group">
                                            <div class="col-md-4"> 
                                                <button type="submit" class="btn blue-soft btn-outline"><i class="fa fa-upload"></i> Carica</button>                                            
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <%} else {%>
                            <script type="text/javascript">

                                function ctrlFile(id) {
                                    var nomefile = $("#" + id).val().split('\\').pop();
                                    var estensione = nomefile.substring(nomefile.lastIndexOf("."));
                                    if (estensione == null || estensione == "") {
                                        $("#div_" + id).attr("class", "col-md-4");
                                        $("#a_" + id).css("display", "none");
                                        return true;
                                    } else if (estensione != ".pdf" && estensione != ".PDF") {
                                        $("#div_" + id).attr("class", "col-md-4 has-error");
                                        $("#a_" + id).css("display", "block");
                                        $("#a_" + id).html("<i class='fa fa-exclamation-triangle'></i> Selezionare file pdf!");
                                        return false;
                                    } else if (!ctrlDim(id)) {
                                        return false;
                                    } else if (!ctrlAllDim()) {
                                        return false;
                                    } else if (estensione == ".pdf" || estensione == ".PDF") {
                                        $("#div_" + id).attr("class", "col-md-4 has-success");
                                        $("#a_" + id).css("display", "none");
                                        return true;
                                    }
                                }

                                function ctrlForm2() {
                                    var err = false;
//                                    var fine = $("#fine").val();
//                                    var inizio = $("#inizio").val();
//                                    var indetrminato = document.getElementById("indeterminato").checked;

                                    if (!ctrlFile('file')) {
                                        err = true;
                                    }
                                    if (!ctrlFile('m5')) {
                                        err = true;
                                    }
                                    if (!ctrlFile('contratto')) {
                                        err = true;
                                    }
//                                    if (inizio == "") {
//                                        $("#div_inizio").attr("class", "col-md-3 has-error");
//                                        err = true;
//                                    } else {
//                                        $("#div_inizio").attr("class", "col-md-3 has-success");
//                                    }
//                                    if (!indetrminato && fine == "") {
//                                        $("#div_fine").attr("class", "col-md-3 has-error");
//                                        err = true;
//                                    } else {
//                                        $("#div_fine").attr("class", "col-md-3 has-success");
//                                    }
//                                    if (!checkDate()) {
//                                        err = true;
//                                    }
                                    if (err) {
                                        return false;
                                    }
                                    return true;

                                }
                            </script>
                            <div class="portlet-body form">
                                <form class="form-horizontal" action="<%="OperazioniEnte?type=24"%>" method="post" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlForm2();" enctype="multipart/form-data">
                                    <input type="hidden" value="<%=id%>" name="id">
                                    <input type="hidden" value="<%=pol.getDoc_m5()%>" name="m5_old">
                                    <input type="hidden" value="<%=pol.getDoc_ragazzo()%>" name="file_old">
                                    <input type="hidden" value="<%=pol.getContratto().getFile()%>" name="contratto_old">
                                    <input type="hidden" value="mod" name="tipo">
                                    <div class="form-body">
                                        <br/><label><strong>Dati Del Destinatario:</strong></label>
                                        <div class="form-group portlet light bordered">
                                            <div class="col-md-3" id="div_tutor" style="display: none;">
                                                <label>Tutor</label>
                                                <select class="form-control select2-allow-clear" id="tutor" name="tutor">
                                                    <option value="...">...</option>
                                                    <%for (Tutor t : tutor) {
                                                            if (t.getId() == pol.getTutor()) {%>
                                                    <option selected value="<%=t.getId()%>"><%=t.getNome()%> <%=t.getCognome()%></option>
                                                    <%} else {%>
                                                    <option value="<%=t.getId()%>"><%=t.getNome()%> <%=t.getCognome()%></option>
                                                    <%}
                                                        }%>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-md-4" id="div_file">
                                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                                        <label>Carta d'Identità Destinatario</label>
                                                        <div class="input-group input-large">
                                                            <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                                <span class="fileinput-filename"> </span>
                                                            </div>
                                                            <span class="input-group-addon btn default btn-file">
                                                                <span class=" fileinput-new"> Scegli file </span>
                                                                <span class="fileinput-exists"> Modifica </span>
                                                                <input type="file" id="file" name="file" onchange="ctrlFile('file');" accept="application/pdf"></span>                                                    
                                                            <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                        </div>
                                                        <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                            <a id="a_file" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                                <i id="i_file" class="fa fa-exclamation-triangle"></i> Selezionare un file PDF
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div></div>
                                            <div class="form-group">
                                                <div class="col-md-4" id="div_m5">
                                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                                        <label>Allegato 8</label>
                                                        <div class="input-group input-large">
                                                            <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                                <span class="fileinput-filename"> </span>
                                                            </div>
                                                            <span class="input-group-addon btn default btn-file">
                                                                <span class=" fileinput-new"> Scegli file </span>
                                                                <span class="fileinput-exists"> Modifica </span>
                                                                <input type="file" id="m5" name="m5" onchange="ctrlFile('m5');" accept="application/pdf"> </span> 
                                                            <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                        </div>
                                                        <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                            <a id="a_m5" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                                <i class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div></div>
                                        </div>
                                        <br/><label><strong>Dati Del Contratto:</strong></label>
                                        <div class="portlet light bordered">                                            
                                            <div class="form-group">
                                                <div class="col-md-3" id="div_contratto">
                                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                                        <label>Contratto Di Lavoro</label>
                                                        <div class="input-group input-large">
                                                            <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                                <span class="fileinput-filename"> </span>
                                                            </div>
                                                            <span class="input-group-addon btn default btn-file">
                                                                <span class=" fileinput-new"> Scegli file </span>
                                                                <span class="fileinput-exists"> Modifica </span>
                                                                <input type="file" id="contratto" name="contratto" onchange="ctrlFile('contratto');" accept="application/pdf"></span>                                                    
                                                            <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                        </div>
                                                        <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                            <a id="a_contratto" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                                <i id="a_contratto" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <label style="color: red;">**i documenti resteranno invariati, se non verranno caricati nuovamente</label>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-4"> 
                                            <button type="submit" class="btn blue-soft btn-outline"><i class="fa fa-upload"></i> Carica</button>                                            
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <%}%>
                        </div>
                        <%}%>
                    </div>
                </div>
            </div>
        </div>
        <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <script src="assets/global/plugins/fancybox/source/jquery.fancybox.pack.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/vendor/tmpl.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/vendor/load-image.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.iframe-transport.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-image.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-audio.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-video.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-file-upload/js/jquery.fileupload-ui.js" type="text/javascript"></script>
        <!-- BEGIN PAGE LEVEL PLUGINS -->

        <script src="assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>

        <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
        <script src="assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/form-fileupload.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-validation/js/localization/messages_it.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="assets/pages/scripts/components-select2.min.js" type="text/javascript"></script>           
        <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>             
        <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>           

        <!-- END PAGE LEVEL SCRIPTS -->
        <script src="assets/pages/scripts/components-multi-select.min.js" type="text/javascript"></script>
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>

        <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>

        <!--        <script type="text/javascript">
                                                                            $(document).ready(mostradatafine());
                </script>-->

    </body>
</html>
<%}
    }%>

