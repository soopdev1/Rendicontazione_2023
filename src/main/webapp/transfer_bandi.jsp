<%@page import="com.seta.util.Utility"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.activity.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "transfer_bandi.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            //Etichette et = new Etichette("IT");
            ArrayList<String[]> tipoBando = Action.getList_TipoBando();
//            Map<String, String> tipobando = Action.getTipoBandoById();
%>
<!DOCTYPE html>
<html>
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

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
        <link href="assets/layouts/layout/css/custom.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/themes/grey.min.css" rel="stylesheet" type="text/css" id="style_color" />
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

            function setIDDisactive(id) {
                document.getElementById('confirmdisactive').href = 'OperazioniRegional?type=6&idbando=' + id;
            }
        </script>

        <style>
            .input-group{
                width: 100%;
            }
            .etichetta{
                width: 45%!important;
            }
        </style>

    </head>
    <!-- END HEAD -->

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
        <div class="modal fade bs-modal-lg" id="disactivemodal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header alert-danger">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Conferma chiusura</h4>
                    </div>
                    <div class="modal-body" id="largetext">                       
                        <label class="text"> Vuoi confermare la chiusura del bando? </label>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn large btn-danger" data-dismiss="modal" onclick="return dismiss('disactivemodal');">Chiudi</button>
                        <a class="btn btn-danger large " id="confirmdisactive" onclick="">Conferma</a> 
                    </div>
                </div>
            </div>
        </div>




        <!-- BEGIN HEADER -->
        <%@ include file="menu/header.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu_regional/menu_bandi.jsp"%>
            <!-- END MENU -->

            <%  user = session.getAttribute("username").toString();%>
            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <!-- BEGIN PAGE HEADER-->
                    <!-- BEGIN THEME PANEL -->
                    <!--    VUOTO RAF  -->
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <div class="logo">
                        <img src="assets/seta/img/logos1.png" height="75" align="right" /> 
                    </div>  
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->                    
                    <h3 class="page-title" style="color:#2D5F8B"><strong> BANDI </strong>  <small style="color:#2D5F8B"> TRANSFERIMENTO DENARO</small> </h3>    
                    <% String esitoact = request.getParameter("esitoact");
                        if (esitoact == null) {
                            esitoact = "";
                        }
                        if (esitoact.equals("KO")) {%>
                    <br>
                    <div class="alert bg-red-thunderbird alert-dismissible " style="text-align: center;color: white">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Errore riprovare!</strong></span>
                    </div>
                    <%} else if (esitoact.equals("OK")) {%>
                    <br>
                    <div class="alert bg-green-jungle alert-dismissible " style="text-align: center;color: white">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Operazione eseguita con successo!</strong></span>
                    </div>
                    <%} else if (esitoact.equals("Eccessivo")) {%>
                    <div class="alert bg-red-thunderbird alert-dismissible " style="text-align: center;color: white">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Budget Stanziato per le politiche maggiore rispetto all'importo da trasferire!</strong></span>
                    </div>
                    <%}%>
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box red-flamingo">
                                <div class="portlet-title">
                                    <div class="caption">  
                                        <i class="fa fa-search font-white"></i>
                                        <span class="caption-subject font-white bold ">Cerca </span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form class="form-horizontal" action="OperazioniRegional?type=25" method="post" id="submit_form" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlForm();">
                                        <div class="form-body">                                                                                         
                                            <div class="form-group">
                                                <div class="col-md-3">
                                                    <label>Tipo</label>                                                             
                                                    <select class="form-control select2" data-placeholder="" name="tipo" id="tipo" >
                                                        <option value=".." selected>..</option>
                                                        <% for (int j = 0; j < tipoBando.size(); j++) {%>
                                                        <option value="<%=tipoBando.get(j)[0]%>"><%=tipoBando.get(j)[1]%></option>                                                                                                                     
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="portlet box yellow-gold">
                                                        <div class="portlet-title">
                                                            <div class="caption">  
                                                                <a class="fa fa-exclamation-circle font-white" style="cursor: default;"></a>
                                                                <span class="caption-subject font-white bold ">IMPORTANTE: prima di procedere al trasferimento agire manualmente sui budget delle singole politiche</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">                                                
                                                <div class="col-md-4" id='div_donatore' style="display: none">
                                                    <label>Bando Donatore</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon etichetta" style="background-color: #eef1f5;padding: 0px;">
                                                            <a class='bg-blue-chambray btn btn-block btn-default fancyBoxRaf font-white' href='' id='modifica'>
                                                                <font color='white' face='verdana'> Modifica Budget Pol. </font> 
                                                            </a>
                                                        </span>
                                                        <select class="form-control select2" data-placeholder="" name="donatore" id="donatore">
                                                        </select>
                                                    </div>
                                                    <div class="input-group">
                                                        <span class="input-group-addon font-white bg-blue etichetta"><strong>Stanziato</strong></span>
                                                        <input class="form-control" style="direction: rtl;" type="text" id="d_b" readonly style="display: block">
                                                    </div>
                                                    <div class="input-group">
                                                        <span class="input-group-addon font-white bg-yellow-gold etichetta"><strong>Attuale</strong></span>
                                                        <input class="form-control" style="direction: rtl;" type="text" id="d_a" readonly>
                                                    </div>
                                                    <div class="input-group">    
                                                        <span class="input-group-addon font-white bg-red-flamingo etichetta"><strong>Previsonale</strong></span>
                                                        <input class="form-control" style="direction: rtl;" type="text" id="d_p" readonly>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="" id="div_budget" id='div_budget' style="display: none">
                                                        <label>Digitare Cifra</label>
                                                        <div class="input-group" id="cifra"> 
                                                            <span class="input-group-addon" style="background-color: #eef1f5;"><strong>€</strong></span>
                                                            <input class="form-control quantity" style="direction: rtl;" type="number" min="1" id="budget" name="budget" step="1" placeholder="0000">
                                                            <span class="input-group-addon" style="background-color: #eef1f5;"><strong>,</strong></span>
                                                            <input class="form-control quantity" type="number" id="centesimi" name="centesimi" placeholder="00" min="0" max="99" step="1" >
                                                        </div>
                                                        <div class="input-group">
                                                            <input class="form-control" style="direction: rtl;" type="text" id="max" readonly>
                                                            <span class="input-group-addon bg-red-flamingo font-white" style="width: 33.5%;"><strong>Cifra Massima</strong></span>

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4" id='div_ricevente' style="display: none">
                                                    <label>Bando Ricevente</label>
                                                    <select class="form-control select2" data-placeholder="" name="ricevente" id="ricevente">
                                                    </select>
                                                    <div class="input-group">
                                                        <span class="input-group-addon font-white bg-blue etichetta"><strong>Stanziato</strong></span>
                                                        <input class="form-control" style="direction: rtl;" type="text" id="r_b" readonly style="display: block">
                                                    </div>
                                                    <div class="input-group">
                                                        <span class="input-group-addon font-white bg-yellow-gold etichetta"><strong>Attuale</strong></span>
                                                        <input class="form-control" style="direction: rtl;" type="text" id="r_a" readonly>
                                                    </div>
                                                    <div class="input-group">    
                                                        <span class="input-group-addon font-white bg-red-flamingo etichetta"><strong>Previsonale</strong></span>
                                                        <input class="form-control" style="direction: rtl;" type="text" id="r_p" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                            <br>
                                            <div class="form-group" id="div_submit" style="display: none">
                                                <div class="col-md-4">
                                                    <button type="submit" class="btn blue btn-outline"><i class="fa fa-exchange"></i> Trasferisci</button>
                                                    <a href="transfer_bandi.jsp" class="btn red-haze btn-outline"><i class="fa fa-remove"></i> Reset</a>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- END CONTENT -->
                <!-- BEGIN QUICK SIDEBAR -->
                <!-- END QUICK SIDEBAR -->
            </div>
            <!-- END CONTAINER -->
            <!-- BEGIN FOOTER -->
            <div class="page-footer">
                <div class="page-footer-inner"> Rendicontazione v 1.0 </div>
                <div class="scroll-to-top">
                    <i class="icon-arrow-up"></i>
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

            <script>
                                        var obj;
                                        var max;
                                        $('#tipo').change(function () {
                                            $('#div_budget').css("display", "none");
                                            var cl = $('#cifra').attr('class').split('has-error').join('');
                                            $('#cifra').attr('class', cl)
                                            $('#div_ricevente').css("display", "none");
                                            $('#donatore').empty();
                                            $('#ricevente').empty();
                                            $('#budget').val('');
                                            $('#centesimi').val('');
                                            $('#d_b').val('');
                                            $('#d_a').val('');
                                            $('#d_p').val('');
                                            $('#modifica').attr("href", "");
                                            if ($('#tipo').val() != '..') {
                                                $.get("OperazioniRegional?type=24&tipo=" + $('#tipo').val(), function (response) {
                                                    obj = JSON.parse(response);
                                                    if (obj.length > 0) {
                                                        for (var i = 0; i < obj.length; i++) {
                                                            $('#donatore').append("<option value='" + obj[i].idbando + "' selected>" + obj[i].decreto + " " + obj[i].titolo + "</option>");
                                                        }
                                                        $('#donatore').append("<option value='..' selected>..</option>");
                                                        $('#div_donatore').css("display", "block");
                                                    } else {
                                                        $('#div_budget').css("display", "none");
                                                        $('#div_donatore').css("display", "none");
                                                        $('#div_ricevente').css("display", "none");
                                                    }
                                                });
                                            } else {
                                                $('#div_budget').css("display", "none");
                                                $('#div_donatore').css("display", "none");
                                                $('#div_ricevente').css("display", "none");
                                            }
                                        });

                                        $('#donatore').change(function () {
                                            if ($('#donatore').val() != '..') {
                                                $('#ricevente').empty();
                                                for (var i = 0; i < obj.length; i++) {
                                                    if (obj[i].idbando != $('#donatore').val()) {
                                                        $('#ricevente').append("<option value='" + obj[i].idbando + "' selected>" + obj[i].decreto + " " + obj[i].titolo + "</option>");
                                                        $('#r_b').val(currencyFormat(Number(obj[i].budget)));
                                                        $('#r_a').val(currencyFormat(Number(obj[i].budget_attuale)));
                                                        $('#r_p').val(currencyFormat(Number(obj[i].budget_previsione)));
                                                    } else {
                                                        max = obj[i].budget_previsione;
                                                        $('#max').val(currencyFormat(Number(obj[i].budget_previsione)));
                                                        $('#d_b').val(currencyFormat(Number(obj[i].budget)));
                                                        $('#d_a').val(currencyFormat(Number(obj[i].budget_attuale)));
                                                        $('#d_p').val(currencyFormat(Number(obj[i].budget_previsione)));
                                                        $('#modifica').attr("href", "modify_configuration_bando.jsp?&idbando=" + obj[i].idbando + "&tipo=" + obj[i].tipo_bando);
                                                    }
                                                }
                                                $('#div_ricevente').css("display", "block");
                                                $('#div_budget').css("display", "block");
                                                $('#div_submit').css("display", "block");
                                            } else {
                                                $('#div_ricevente').css("display", "none");
                                                $('#div_budget').css("display", "none");
                                                $('#div_submit').css("display", "none");
                                                $('#d_b').val('');
                                                $('#d_a').val('');
                                                $('#d_p').val('');
                                            }
                                        });

                                        $('#ricevente').change(function () {
                                            for (var i = 0; i < obj.length; i++) {
                                                if (obj[i].idbando == $('#ricevente').val()) {
                                                    $('#r_b').val(currencyFormat(Number(obj[i].budget)));
                                                    $('#r_a').val(currencyFormat(Number(obj[i].budget_attuale)));
                                                    $('#r_p').val(currencyFormat(Number(obj[i].budget_previsione)));
                                                }
                                            }
                                        });

                                        function currencyFormat(num) {
                                            return '€ ' + num.toFixed(2).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
                                        }

                                        $(".quantity").on("change", function () {
                                            var val = $('#centesimi').val();
                                            if (val.length >= 2) {
                                                $('#centesimi').val(val.substr(0, 2));
                                            } else if (val.length == 0) {
                                                $('#centesimi').val("00");
                                            } else if (val.length < 2) {
                                                $('#centesimi').val(val + "0");
                                            }
                                            if (Number($('#budget').val() + "." + $('#centesimi').val()) > max) {
//                                                $('#budget').val(max.split('.')[0]);
//                                                $('#centesimi').val(max.split('.')[1])
                                                if ($('#centesimi').val().length < 2) {
                                                    $('#centesimi').val($('#centesimi').val() + "0");
                                                }
                                                var cl = $('#cifra').attr('class');
                                                $('#cifra').attr('class', cl + ' has-error')
                                            }
                                            return true;
                                        });

                                        function ctrlForm() {
                                            var err = false;

                                            if ($('#donatore').val() == '..') {
                                                var cl = $('#div_donatore').attr('class');
                                                $('#div_donatore').attr('class', cl + ' has-error')
                                                err = true;
                                            }
                                            if (!ctrlValue()) {
                                                err = true;
                                            }
                                            if (err) {
                                                return false;
                                            }
                                            return true;
                                        }

                                        function ctrlValue() {
                                            var cl;
                                            var importo = $('#budget').val() + "." + $('#centesimi').val();
                                            if (importo == '.' || Number(importo) == 0) {
                                                cl = $('#cifra').attr('class');
                                                $('#cifra').attr('class', cl + ' has-error')
                                                return false;
                                            } else if (Number(importo) > max) {
//                                                $('#budget').val(max.split('.')[0]);
//                                                $('#centesimi').val(max.split('.')[1])
                                                if ($('#centesimi').val().length < 2) {
                                                    $('#centesimi').val($('#centesimi').val() + "0");
                                                }
                                                cl = $('#cifra').attr('class');
                                                $('#cifra').attr('class', cl + ' has-error')
                                                return false;
                                            }
                                            return true;
                                        }

//                            function updateLabel() {
//                                var importo = Number($('#budget').val() + "." + $('#centesimi').val());
//                                $('#d_b').val(currencyFormat(currencyDeFormat($('#d_b').val()) - importo));
//                                $('#d_a').val(currencyFormat(currencyDeFormat($('#d_a').val()) - importo));
//                                $('#d_p').val(currencyFormat(currencyDeFormat($('#d_p').val()) - importo));
//                                $('#r_b').val(currencyFormat(currencyDeFormat($('#r_b').val()) + importo));
//                                $('#r_a').val(currencyFormat(currencyDeFormat($('#r_a').val()) + importo));
//                                $('#r_p').val(currencyFormat(currencyDeFormat($('#r_p').val()) + importo));
//                            }
//                                        function currencyDeFormat(num) {
//                                            num = num.split('.').join('');
//                                            num = num.replace(',', '.');
//                                            num = num.replace('€', '');
//                                            num = num.trim();
//                                            return  Number(num)
//                                        }
            </script>
    </body>
</html>

<%}%>
<%}%>