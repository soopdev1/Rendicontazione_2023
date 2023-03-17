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
        if (!Action.isVisibile(stat, "reg_showTirocinantiRimborsoDT.jsp")) {
            response.sendRedirect("page_403.html");
        } else {

            String esiti = request.getParameter("esi");
            if (esiti == null) {
                esiti = "KO";
            }
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

            function setScartaID(id, id2) {
            $('#id_s').val(id);
            $('#id_r').val(id2);
            }

            function setScartaID2(id, id2) {
            $('#id_s').val(id);
            $('#id_r').val(id2);
            }

            //function setIDDisactive(id, id2) {
            ////document.getElementById('confirmdisactive').href = "OperazioniRegional?type=8&idpolitica=" + id + "&idrimborso=" + id2 + "&tipo=tipo";
            //document.getElementById('confirmdisactive').href = "OperazioniRegional?type=8&idpolitica=" + id + "&idrimborso=" + id2;
            //}

            function submitScarto() {
            var motivo = document.getElementById("motivo").value;
            var motivo2 = document.getElementById("motivo2").value;
            if (motivo == null || motivo == "..." || motivo == "") {
            //alert(motivo mancante);
            $("#div_motivo").attr("class", "has-error");
            return false;
            } else if (motivo == "Altro") {
            if (motivo2 == "") {
            $("#div_motivo2").attr("class", "has-error");
            return false;
            } else {
            document.getElementById('conferma_scarta').href = "OperazioniRegional?type=14&motivo=" + encodeURIComponent(motivo2) + "&idpf_dt=" + $('#id_s').val() + "&idrimborso_dt=" + $('#id_r').val();
            }
            } else {
            document.getElementById('conferma_scarta').href = "OperazioniRegional?type=14&motivo=" + encodeURIComponent(motivo) + "&idpf_dt=" + $('#id_s').val() + "&idrimborso_dt=" + $('#id_r').val();
            }

            }

            function submitScarto2() {
            var motivo1 = document.getElementById("motivo1").value;
            var motivo12 = document.getElementById("motivo12").value;
            if (motivo1 == null || motivo1 == "..." || motivo1 == "") {
            //alert(motivo mancante);
            $("#div_motivo1").attr("class", "has-error");
            return false;
            } else if (motivo1 == "Altro") {
            if (motivo12 == "") {
            $("#div_motivo12").attr("class", "has-error");
            return false;
            } else {
            document.getElementById('conferma_scarta1').href = "OperazioniRegional?type=15&motivo=" + encodeURIComponent(motivo12) + "&idpf_dt=" + $('#id_s').val() + "&idrimborso_dt=" + $('#id_r').val();
            }
            } else {
            document.getElementById('conferma_scarta1').href = "OperazioniRegional?type=15&motivo=" + encodeURIComponent(motivo1) + "&idpf_dt=" + $('#id_s').val() + "&idrimborso_dt=" + $('#id_r').val();
            }

            }



        </script>
    </head>


    <body class="page-full-width page-content-white">
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
        <div class="modal fade bs-modal-lg" id="rifiutamodal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-red-thunderbird" style="color: white;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Scarta Progetto Informativo</h4>
                    </div>
                    <div class="modal-body" id="largetext">                       
                        <label class="text"> Vuoi veramente scartare questo progetto formativo? </label>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger large " data-dismiss="modal" onclick="return dismiss('rifiutamodal');">Chiudi</button>
                        <a class="btn btn-danger large " id="confirmdisactive" onclick="">Conferma</a> 
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade bs-modal-lg" id="scartamodal1" tabindex="-1" role="dialog" aria-hidden="true">
            <%ArrayList<String[]> motivi = Action.getMotivi();%>
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header bg-red-thunderbird " style="color: white;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Scarta Definitivamente Progetto Formativo</h4>
                    </div>

                    <div class="modal-body">                       
                        <label class="text">Specificare motivazione scarto </label><label style="color: red"> &nbsp;*</label>
                        <input type="hidden" id="id_s">
                        <input type="hidden" id="id_r">
                        <div id="div_motivo1">
                            <select class="form-control select2-container--classic" id="motivo1" name="motivo1">
                                <%for (int i = 0; i < motivi.size(); i++) {%>
                                <option value="<%=motivi.get(i)[1]%>"><%=motivi.get(i)[1]%></option>
                                <%}%>
                            </select>
                        </div>
                        <br>
                        <div id="div_motivo2">
                            <textarea class="form-control" rows="4" style="resize: none; display: none;" id="motivo12" name="motivo12" placeholder="Motivazione"></textarea>
                        </div>
                        <label type="text" style="color: red" >* Campo Obbligatorio</label>
                    </div>

                    <div class="modal-footer">
                        <div class="form-group">                             
                            <div class="col-md-12"> 
                                <button type="button" class="btn btn-danger large " data-dismiss="modal" onclick="return dismiss('scartamodal1');">Chiudi</button>                    
                                <a class="btn btn-success large " id="conferma_scarta1" onclick="return submitScarto2();">Conferma</a>                                                                      
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>  

        <div class="modal fade bs-modal-lg" id="scartamodal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header bg-yellow-gold  " style="color: white;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Rigetta Singolo Progetto Formativo</h4>
                    </div>

                    <div class="modal-body">                       
                        <label class="text">Specificare motivazione scarto </label><label style="color: red"> &nbsp;*</label>
                        <input type="hidden" id="id_s">
                        <input type="hidden" id="id_r">
                        <div id="div_motivo">
                            <select class="form-control select2-container--classic" id="motivo" name="motivo">
                                <%for (int i = 0; i < motivi.size(); i++) {%>
                                <option value="<%=motivi.get(i)[1]%>"><%=motivi.get(i)[1]%></option>
                                <%}%>
                            </select>
                        </div>
                        <br>
                        <div id="div_motivo2">
                            <textarea class="form-control" rows="4" style="resize: none; display: none;" id="motivo2" name="motivo2" placeholder="Motivazione"></textarea>
                        </div>
                        <label type="text" style="color: red" >* Campo Obbligatorio</label>
                    </div>

                    <div class="modal-footer">
                        <div class="form-group">                             
                            <div class="col-md-12"> 
                                <button type="button" class="btn btn-danger large " data-dismiss="modal" onclick="return dismiss('scartamodal');">Chiudi</button>                    
                                <a class="btn btn-success large " id="conferma_scarta" onclick="return submitScarto();">Conferma</a>                                                                      
                            </div>
                        </div>
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
                        <% String esitocl = request.getParameter("esitocl");

                            if (esitocl == null) {
                                esitocl = "";
                            }
                            if (esitocl.equals("KO")) {%>
                        <br>
                        <div class="alert bg-red-thunderbird  alert-dismissible " style="text-align: center;color: white">
                            <button type="button" class="close" data-close="alert"></button>
                            <span><strong>Errore! Riprovare</strong></span>
                        </div>
                        <%} else if (esitocl.equals("OK")) {%>
                        <br>
                        <div class="alert bg-green-jungle alert-dismissible " style="text-align: center;color: white">
                            <button type="button" class="close" data-close="alert"></button>
                            <span><strong>Operazione eseguita con successo!</strong></span>
                        </div>
                        <%}%>
                        <% String esito1 = request.getParameter("esito1");

                            if (esito1 == null) {
                                esito1 = "";
                            }
                            if (esito1.equals("KO")) {%>
                        <br>
                        <div class="alert bg-red-thunderbird  alert-dismissible " style="text-align: center;color: white">
                            <button type="button" class="close" data-close="alert"></button>
                            <span><strong>Errore! Riprovare</strong></span>
                        </div>
                        <%} else if (esito1.equals("OK")) {%>
                        <br>
                        <div class="alert bg-green-jungle alert-dismissible " style="text-align: center;color: white">
                            <button type="button" class="close" data-close="alert"></button>
                            <span><strong>Singolo progetto formativo rigettato con successo! Rigettato l'intero rimborso!</strong></span>
                        </div>
                        <%}%>
                        <div class="portlet-title">
                            <div class="caption">
                                <span class="caption-subject font-blue bold">
                                    <h3 class="page-title"><strong> PROGETTI FORMATIVI </strong><small> RIMBORSI DESTINATARI</small> </h3>                                    
                                </span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet box blue-soft">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <a class="fa fa-user white tooltips" style="cursor: default;color: white;"></a>
                                            <span class="caption-subject font-white bold ">Risultati</span>
                                        </div>
                                        <div class="tools">
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <table width="100%" class="table table-responsive table-bordered table-scrollable dataTable no-footerr" id="sample_0"> 
                                            <thead>
                                                <tr>
                                                    <%if (esiti.equals("KO")) {%>
                                                    <th width="10%"><center>Azioni </center> </th>
                                                    <%}%>
                                            <th><center>Destinatario </center> </th>
                                            <th><center>Codice Fiscale </center> </th>
                                            <th><center>Profiling </center> </th>
                                            <th><center>Durata </center> </th>
                                            <th><center>Ore Effettuate </center> </th>
                                            <th><center>Ore Totali </center> </th>
                                            <th><center>Documenti </center> </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                            <tfoot hidden>
                                                <tr>
                                                    <%if (esiti.equals("KO")) {%>
                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..." disabled=""></th>
                                                        <%}%> 
                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..." disabled=""></th>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div> 
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

        <script type="text/javascript">
                                    jQuery(document).ready(function () {
                                    var dt2 = function () {
                                    var g = $("#sample_0");
                                    g.dataTable({
                                    language: {aria: {},
                                            sProcessing: "Ricerca...",
                                            emptyTable: "nessun risultato",
                                            info: "Mostra _START_ di _END_ su _TOTAL_ risultati",
                                            infoEmpty: "Risultato Vuoto",
                                            infoFiltered: "(filtrato su _MAX_)",
                                            lengthMenu: "Mostra _MENU_", search: "Cerca",
                                            zeroRecords: "Nessun risultato trovato",
                                            paginate: {previous: "Precedente", next: "Successivo", last: "Ultimo", first: "Primo"}},
                                            processing: true,
                                            ajax: {
                                            url: "QueryRegional?type=21&idrimborso=<%=request.getParameter("idrimborso")%>&esi=<%=esiti%>",
                                                    dataSrc: "aaData",
                                                    type: "GET"
                                            },
                                            drawCallback: function (settings, json) {
                                            $('.popovers').popover({
                                            container: 'body',
                                            });
                                            },
                                            columnDefs: [
                                            {class: "allineacentro", targets: [0]},
                                            {class: "allineacentro", targets: [1]},
                                            {class: "allineacentro", targets: [2]},
                                            {class: "allineacentro", targets: [3]},
                                            {class: "allineacentro", targets: [4]},
                                            {class: "allineacentro", targets: [5]},
                                            {class: "allineacentro", targets: [6]},
            <%if (esiti.equals("KO")) {%>
                                            {class: "allineacentro", targets: [7]},
                                            {orderable: !1, targets: [0]},
                                            {searchable: !1, targets: [0]},
                                            {orderable: !1, targets: [7]},
                                            {searchable: !1, targets: [7]}
            <%} else {%>
                                            {orderable: !1, targets: [6]},
                                            {searchable: !1, targets: [6]}
            <%}%>
                                            ],
                                            buttons: [
                                            {extend: "excel", className: "btn white btn-outline", text: "<i class='fa fa-file-excel-o'></i> Excel"},
                                            {extend: "colvis", className: "btn white btn-outline", text: "Columns"}]
                                            ,
                                            colReorder: {reorderCallback: function () {
                                            console.log("callback");
                                            }},
                                            lengthMenu: [
                                            [25, 50, 100, - 1],
                                            [25, 50, 100, "All"]
                                            ],
                                            //scrollY: 'true',
                                            scrollX: 'true',
                                            pageLength: 25,
                                            order: [],
                                            dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"

                                    });
                                    $("tfoot input").keyup(function () {
                                    g.fnFilter(this.value, g.oApi._fnVisibleToColumnIndex(
                                            g.fnSettings(), $("tfoot input").index(this)));
                                    });
                                    $("tfoot input").each(function (i) {
                                    this.initVal = this.value;
                                    });
                                    $("tfoot input").focus(function () {
                                    if (this.className === "form-control")
                                    {
                                    this.className = "form-control";
                                    this.value = "";
                                    }
                                    });
                                    $("tfoot input").blur(function (i) {
                                    if (this.value === "")
                                    {
                                    this.className = "form-control";
                                    this.value = this.initVal;
                                    }
                                    });
                                    };
                                    jQuery().dataTable && dt2();
                                    });
        </script>


        <script type="text/javascript">
            $("#motivo").change(function () {
            if ($("#motivo").val() == "Altro") {
            $("#motivo2").css("display", "block");
            } else {
            $("#motivo2").css("display", "none");
            }
            });
            $("#motivo1").change(function () {
            if ($("#motivo1").val() == "Altro") {
            $("#motivo12").css("display", "block");
            } else {
            $("#motivo12").css("display", "none");
            }
            });

        </script>
    </body>
</html>
<%}
    }%>

