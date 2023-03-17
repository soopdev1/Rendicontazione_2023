<%-- 
    Document   : addtutor
    Created on : 15-ott-2018, 14.33.16
    Author     : agodino
--%>

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
        if (!Action.isVisibile(stat, "addTutor.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            //Etichette et = new Etichette("IT");

            ArrayList<String[]> ruoli = Action.getListTipoTutor();
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
                } else if (estensione == ".pdf" || estensione == ".PDF") {
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

            function ctrlForm() {
                var nome = $('#nome').val();
                var cognome = $('#cognome').val();
                var ruolo = $('#ruolo').val();
                var scadenza = $("#scadenza").val();

                var err = false;

                if (nome == "") {
                    $("#div_name").attr("class", "col-md-3 has-error");
                    err = true;
                } else {
                    $("#div_name").attr("class", "col-md-3 has-success");
                }
                if (cognome == "") {
                    $("#div_surname").attr("class", "col-md-3 has-error");
                    err = true;
                } else {
                    $("#div_surname").attr("class", "col-md-3 has-success");
                }
                if (ruolo == null || ruolo == "...") {
                    $("#div_ruolo").attr("class", "col-md-3 has-error");
                    err = true;
                } else {
                    $("#div_ruolo").attr("class", "col-md-3 has-success");
                }
                if (scadenza == "") {
                    $("#div_scadenza").attr("class", "col-md-3 has-error");
                    err = true;
                } else {
                    $("#div_scadenza").attr("class", "col-md-3 has-success");
                }
                if (!ctrlFile('file')) {
                    $("#div_file").attr("class", "col-md-4 has-error");
                    err = true;
                }
                if (!checkCF()) {
                    $("#div_cf").attr("class", "col-md-3 has-error");
                    err = true;
                }

                if (err) {
                    return false;
                }
                return true;
            }

            function isNumber(evt) {
                evt = (evt) ? evt : window.event;
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                return true;
            }

            function checkCF() {
                var cf = $('#cf').val();
                var esito = true;
                var validi, i, s, set1, set2, setpari, setdisp;
                if (cf === '')
                    esito = false;
                cf = cf.toUpperCase();
                if (cf.length === 16) {
                    if (cf.length !== 16) {
                        esito = false;
                    } else {
                        validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                        for (i = 0; i < 16; i++) {
                            if (validi.indexOf(cf.charAt(i)) === -1)
                                esito = false;
                        }
                        set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
                        s = 0;
                        for (i = 1; i <= 13; i += 2)
                            s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
                        for (i = 0; i <= 14; i += 2)
                            s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
                        if (s % 26 !== cf.charCodeAt(15) - 'A'.charCodeAt(0)) {
                            esito = false;
                        }
                        if (esito) {
                            $("#div_cf").attr("class", "col-md-3 has-success");
                            $("#a_cf").css("display", "none");
                        } else {
                            $("#div_cf").attr("class", "col-md-3 has-error");
                            $("#a_cf").css("display", "block");
                        }
                        return esito;
                    }
                } else {
                    return false;
                }
            }
        </script>

    </head>


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
                        <button type="button" class="btn dark btn-outline" data-dismiss="modal" onclick="return dismiss('large');">Chiudi</button>
                    </div>
                </div>
            </div>
        </div>

        <!--            BEGIN HEADER -->
        <%@ include file="menu/header.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu_ente/menu_Tutor.jsp"%>
            <!-- END MENU -->

            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <!-- Main content -->                    
                    <div class="logo">
                        <img src="assets/seta/img/logos1.png" height="75"  align="right" /> 
                    </div>  
                    <h3 class="page-title"><strong> TUTOR </strong><small> AGGIUNGI</small> </h3>
                    <% String esitoins = request.getParameter("esitoins");

                        if (esitoins == null) {
                            esitoins = "";
                        }

                        if (esitoins.equals("KO")) {%>           
                    <br>
                    <div class="alert bg-red-thunderbird  alert-dismissible " style="text-align: center;color: white">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Errore, Tutor non salvato! Riprovare</strong></span>            
                    </div>
                    <%} else if (esitoins.equals("OK")) {%> 
                    <br>
                    <div class="alert bg-green-jungle alert-dismissible " style="text-align: center;color: white">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Tutor aggiunto con successo!</strong></span>
                    </div>
                    <%}%>
                    <div class="portlet box blue-soft">
                        <div class="portlet-title">
                            <div class="caption">  
                                <i class="fa fa-user-plus font-white"></i>
                                <span class="caption-subject font-white bold ">Aggiungi Tutor</span>
                            </div>
                        </div>

                        <div class="portlet-body form">
                            <form class="form-horizontal" action="<%="OperazioniEnte?type=1"%>" method="post" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlForm();" enctype="multipart/form-data">
                                <div class="form-body">
                                    <div class="form-group">
                                        <div class="col-md-3" id="div_name">
                                            <label>Nome</label><label type="text" style="color: red" >*</label>
                                            <input type="text" class="form-control" id="nome" name="nome">
                                        </div>
                                        <div class="col-md-3" id="div_surname">
                                            <label>Cognome</label><label type="text" style="color: red" >*</label>
                                            <input type="text" class="form-control" id="cognome" name="cognome">
                                        </div>
                                        <div class="col-md-3" id="div_cf">
                                            <label>Codice Fiscale</label><label type="text" style="color: red" >*</label>
                                            <input type="text" class="form-control" id="cf" name="cf" maxlength="16" onchange="return checkCF();">
                                            <div class="" >
                                                <a id="a_cf" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                    <i class="fa fa-exclamation-triangle"></i> Codice Fiscale non conforme!
                                                </a>
                                            </div>
                                        </div>
                                        <div class="col-md-3" id="div_ruolo">
                                            <label>Ruolo</label><label type="text" style="color: red" >*</label>
                                            <select class="form-control select2-allow-clear" id="ruolo" name="ruolo">
                                                <option value="...">...</option>
                                                <%for (int i = 0; i < ruoli.size(); i++) {%>
                                                <option value="<%=ruoli.get(i)[0]%>"><%=ruoli.get(i)[1]%></option>
                                                <%}%>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-3" id="div_email">
                                            <label>Email</label>
                                            <input type="email" class="form-control" id="email" name="email">
                                        </div>
                                        <div class="col-md-3" id="div_tel">
                                            <label>Telefono</label>
                                            <input type="text" class="form-control" id="telefono" name="telefono" onkeypress="return isNumber(event);" maxlength="10">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-3" id="div_scadenza">
                                            <label>Scadenza Doc. Id.</label><label type="text" style="color: red" >*</label>
                                            <div class="input-group date date-picker" data-date-format="dd/mm/yyyy" data-date-start-date="+1d">
                                                <input type="text" class="form-control" id="scadenza" name="scadenza" readonly>
                                                <span class="input-group-btn">
                                                    <button class="btn default" type="button">
                                                        <i class="fa fa-calendar"></i>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="col-md-3" id="div_file">
                                            <div class="fileinput fileinput-new" data-provides="fileinput">
                                                <label>Carta d'Identità</label><label type="text" style="color: red" >*</label>
                                                <div class="input-group input-large">
                                                    <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                        <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                        <span class="fileinput-filename"> </span>
                                                    </div>
                                                    <span class="input-group-addon btn default btn-file">
                                                        <span class=" fileinput-new"> Scegli file </span>
                                                        <span class="fileinput-exists"> Modifica </span>
                                                        <input type="file" id="file" name="file" onchange="ctrlFile('file');"> </span>                                                    
                                                    <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                </div>
                                                <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                    <a id="a_file" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                        <i class="fa fa-exclamation-triangle"></i> Selezionare un file PDF
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <br><label type="text" style="color: red" >* Campo Obbligatorio</label><br><br>
                                    <div class="form-group">
                                        <div class="col-md-4"> 
                                            <button type="submit" class="btn blue-soft btn-outline"><i class="fa fa-user-plus"></i> Salva</button>                                            
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="page-footer">
                <div class="page-footer-inner"><%=et.getFooter()%></div>
                <div class="scroll-to-top">
                    <i class="icon-arrow-up"></i>
                </div>
            </div>
            <script src="assets/seta/js/test.js" type="text/javascript"></script>
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
            <script src="assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
            <!--script src="assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js" type="text/javascript"></script-->
            <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
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
            <script src="assets/seta/js/form-wizard.js" type="text/javascript"></script>          
            <!-- END PAGE LEVEL SCRIPTS -->
            <!-- BEGIN THEME LAYOUT SCRIPTS -->
            <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
            <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
            <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>


    </body>
</html>
<%}
    }%>

