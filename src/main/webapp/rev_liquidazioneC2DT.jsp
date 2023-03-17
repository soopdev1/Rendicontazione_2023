<%@page import="com.seta.entity.Rimborso"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.activity.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "rev_liquidazioneC2DT.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            ArrayList<String[]> enti = Action.getList_Enti();
            String ente = request.getParameter("ente"),
                    from = request.getParameter("from"),
                    to = request.getParameter("to");

            if (ente == null) {
                ente = "";
            }

            if (from == null) {
                from = "";
            }
            if (to == null) {
                to = "";
            }
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

            function setScartaID(id) {
                $('#id_s').val(id);
            }
            function setScartaID2(id) {
                $('#id_s2').val(id);
                $('#idrimborsoscarto').val(id);
            }

            function setAccettaID(id) {
                $('#idrimborso').val(id);
            }


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
                        document.getElementById('conferma_scarta').href = "OperazioniRevisore?type=14&motivo=" + encodeURIComponent(motivo2) + "&idrimborso=" + $('#id_s').val() + "&pagina=rev_liquidazioneC2DT"
                                + "&ente=<%=ente%>&from=<%=from%>&to=<%=to%>";
                    }
                } else {
                    document.getElementById('conferma_scarta').href = "OperazioniRevisore?type=14&motivo=" + encodeURIComponent(motivo) + "&idrimborso=" + $('#id_s').val() + "&pagina=rev_liquidazioneC2DT"
                            + "&ente=<%=ente%>&from=<%=from%>&to=<%=to%>";
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
                        document.getElementById('conferma_scarta1').href = "OperazioniRevisore?type=15&motivo=" + encodeURIComponent(motivo12) + "&idrimborso=" + $('#id_s2').val() + "&pagina=rev_liquidazioneC2DT"
                                + "&ente=<%=ente%>&from=<%=from%>&to=<%=to%>";
                    }
                } else {
                    document.getElementById('conferma_scarta1').href = "OperazioniRevisore?type=15&motivo=" + encodeURIComponent(motivo1) + "&idrimborso=" + $('#id_s2').val() + "&pagina=rev_liquidazioneC2DT"
                            + "&ente=<%=ente%>&from=<%=from%>&to=<%=to%>";
                }

            }

            function ctrlForm() {
                var descrizione = document.getElementById("descrizione").value;
                var totale = document.getElementById("totale").value;
                var err = false;
                if (descrizione == "") {
                    $("#div_descrizione").attr("class", "has-error col-md-12");
                    err = true;
                }

                if (totale == "" || totale == "0") {
                    $("#div_totale").attr("class", "has-error col-md-3");
                    $("#div_centesimi").attr("class", "has-error col-md-1");
                    err = true;
                }

                if (!ctrlFile('file')) {
                    $("#div_file").attr("class", "input-group input-large has-error");
                    err = true;
                }
                if (err) {
                    return false;
                }
                return true;
            }

            function ctrlRicerca() {
                var ente = $("#ente").val();
                var from = $("#from").val();
                var to = $("#to").val();
                var err = false;
                if ((ente == null || ente == "...") && from == "" && to == "") {
                    showmod1("large", "largetext", "Compilare almeno un campo per la ricerca");
                    $("#div_form").attr("class", "form-body has-error");
                    err = true;
                } else {
                    $("#div_form").attr("class", "form-body");
                }
                if ((from != "" && to == "") || (from == "" && to != "")) {
                    $("#div_date").attr("class", "col-md-3 has-error");
                    err = true;
                }
                if (err) {
                    return false;
                }
                return true;
            }


            function ctrlFormScarto() {
                var motivo1 = document.getElementById("motivo1").value;
                var motivo12 = document.getElementById("motivo12").value;
                var err = false;

                if (motivo1 == null || motivo1 == "..." || motivo1 == "") {
                    $("#div_motivo1").attr("class", "col-md-12 has-error");
                    err = true;

                } else if (motivo1 == "Altro") {
                    if (motivo12 == "") {
                        $("#div_motivo12").attr("class", "col-md-12 has-error");
                        err = true;
                    } else {
                        document.getElementById('motivoscarto').value = motivo12;
                    }
                } else {
                    document.getElementById('motivoscarto').value = motivo1;
                }
                if (!ctrlFile('filescarto')) {
                    //                    $("#div_filescarto").attr("class", "input-group input-large has-error");
                    err = true;
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
                    $("#div_" + file).attr("class", "input-group input-large");
                    $("#a_" + file).css("display", "none");
                    return false;
                } else if (estensione != ".pdf" && estensione != ".PDF") {
                    $("#div_" + file).attr("class", "input-group input-large has-error");
                    $("#a_" + file).css("display", "block");
                    return false;
                } else if (!ctrlDim(file)) {
                    return false;
                } else if (estensione == ".pdf" || estensione == ".PDF") {
                    $("#div_" + file).attr("class", "input-group input-large has-success");
                    $("#a_" + file).css("display", "none");
                    return true;
                }
            }

            function ctrlDim(id) {
                var file = Number(document.getElementById(id).files[0].size);
                if (file > 10485760) {
                    $("#div_" + id).attr("class", "input-group input-large has-error");
                    $("#a_" + id).css("display", "block");
                    $("#a_" + id).html("<i class='fa fa-exclamation-triangle'></i> File di dimensioni eccessive");
                    return false;
                }
                $("#a_" + id).css("display", "none");
                return true;
            }

//            function ctrlFileScarto() {
//                var nomefile = $("#filescarto").val().split('\\').pop();
//                var estensione = nomefile.substring(nomefile.lastIndexOf(".")).toLowerCase();
//                if (estensione == null || estensione == "") {
//                    $("#div_filescarto").attr("class", "input-group input-large");
//                    $("#a_filescarto").css("display", "none");
//                    return false;
//                } else if (estensione != ".pdf" && estensione != ".PDF") {
//                    $("#div_filescarto").attr("class", "input-group input-large has-error");
//                    $("#a_filescarto").css("display", "block");
//                    return false;
//                } else if (!ctrlDim('filescarto')) {
//                    return false;
//                } else if (estensione == ".pdf" || estensione == ".PDF") {
//                    $("#div_filescarto").attr("class", "input-group input-large has-success");
//                    $("#a_filescarto").css("display", "none");
//            return true;
//                }
//            }
        </script>



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
        <div class="modal fade bs-modal-lg" id="scartamodal" tabindex="-1" role="dialog" aria-hidden="true">
            <%ArrayList<String[]> motivi = Action.getMotivi();%>
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header bg-yellow-gold" style="color:white;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Rigetta Rimborso</h4>
                    </div>

                    <div class="modal-body">                       
                        <label class="text">Specificare motivazione rigetto </label><label style="color: red"> &nbsp;*</label>
                        <input type="hidden" id="id_s">
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
                                <a class="btn btn-success large" id="conferma_scarta" style="color:white;" onclick="return submitScarto();">Conferma</a>                                                                      
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>  
        <div class="modal fade bs-modal-lg" id="scartamodal13" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header bg-red">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Scarta Definitivamente Rimborso</h4>
                    </div>

                    <div class="modal-body">                       
                        <label class="text">Specificare motivazione scarto </label><label style="color: red"> &nbsp;*</label>
                        <input type="hidden" id="id_s2">
                        <div id="div_motivo13">
                            <select class="form-control select2-container--classic" id="motivo13" name="motivo13">
                                <%for (int i = 0; i < motivi.size(); i++) {%>
                                <option value="<%=motivi.get(i)[1]%>"><%=motivi.get(i)[1]%></option>
                                <%}%>
                            </select>
                        </div>
                        <br>
                        <div id="div_motivo121">
                            <textarea class="form-control" rows="4" style="resize: none; display: none;" id="motivo123" name="motivo123" placeholder="Motivazione"></textarea>
                        </div>

                        <label type="text" style="color: red" >* Campo Obbligatorio</label>
                    </div>

                    <div class="modal-footer">
                        <div class="form-group">                             
                            <div class="col-md-12"> 
                                <button type="button" class="btn btn-danger large " data-dismiss="modal" onclick="return dismiss('scartamodal13');">Chiudi</button>                    
                                <a class="btn btn-success large " id="conferma_scarta113" onclick="return submitScarto2();">Conferma</a>                                                                      
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> 
        <div class="modal fade bs-modal-lg" id="scartamodal1" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header bg-red">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Scarta Definitivamente Rimborso</h4>
                    </div>
                    <form class="form-horizontal" action="<%="OperazioniRevisore?type=15"%>" method="post" enctype="multipart/form-data" onsubmit="return ctrlFormScarto();">
                        <br>
                        <input type="hidden" id="idrimborsoscarto" name="idrimborsoscarto">
                        <input type="hidden" id="pagina" name="pagina" value="rev_liquidazioneC2DT">
                        <input type="hidden" id="motivoscarto" name="motivoscarto">
                        <input type="hidden" name="ente" value="<%=ente%>">
                        <input type="hidden" name="from" value="<%=from%>">
                        <input type="hidden" name="to" value="<%=to%>">
                        <div id="div_motivo1" class="col-md-12">
                            <select class="form-control select2-container--classic" id="motivo1" name="motivo1">
                                <%for (int i = 0; i < motivi.size(); i++) {%>
                                <option value="<%=motivi.get(i)[1]%>"><%=motivi.get(i)[1]%></option>
                                <%}%>
                            </select>
                        </div>

                        <div id="div_motivo12" class="col-md-12"><br>
                            <textarea class="form-control" rows="4" style="resize: none; display: none;" id="motivo12" name="motivo12" placeholder="Motivazione"></textarea>
                        </div>


                        <div class="fileinput fileinput-new col-md-4" data-provides="fileinput">
                            <label class="text">Checklist </label><label style="color: red"> &nbsp;*</label>
                            <div class="input-group input-large" id="div_filescarto">
                                <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                    <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                    <span class="fileinput-filename"> </span>
                                </div>
                                <span class="input-group-addon btn default btn-file">
                                    <span class="fileinput-new"> Scegli file </span>
                                    <span class="fileinput-exists"> Modifica </span>
                                    <input type="file" id="filescarto" name="filescarto" accept="application/pdf" onchange="ctrlFile('filescarto');"> </span>
                                <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                            </div>
                            <div class="col-md-12 pull-left" style="padding-left: 0px;padding-right: 30px;">
                                <a id="a_filescarto" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                    <i class="fa fa-exclamation-triangle"></i> Selezionare un file PDF
                                </a>
                            </div>
                        </div>
                        <div class="col-md-12"><br>
                            <label type="text" style="color: red" >* Campo Obbligatorio</label>
                        </div>
                        <div class="modal-footer" style="border-top: #f4f4f4;">
                            <div class="form-group">                             
                                <div class="col-md-12"> 
                                    <button type="button" class="btn btn-danger large " data-dismiss="modal" onclick="return dismiss('scartamodal1');">Chiudi</button>                    
                                    <button type="submit" class="btn btn-success large ">Conferma</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>                              
        <div class="modal fade bs-modal-lg" id="accettamodal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header bg-blue">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Liquida Rimborso</h4>
                    </div>
                    <form class="form-horizontal" action="<%="OperazioniRevisore?type=13"%>" method="post" enctype="multipart/form-data" onsubmit="return ctrlForm();">
                        <br>
                        <input type="hidden" id="idrimborso" name="idrimborso">
                        <input type="hidden" id="pagina" name="pagina" value="rev_liquidazioneC2DT">
                        <input type="hidden" name="ente" value="<%=ente%>">
                        <input type="hidden" name="from" value="<%=from%>">
                        <input type="hidden" name="to" value="<%=to%>">

                        <div id="div_totale" class="col-md-3" style=" padding-right: 0px; width: 25%;">
                            <label>Totale da erogare</label><label type="text" style="color: red" >&nbsp;*</label>
                            <div class="input-group"> 
                                <span class="input-group-addon" style="background-color: #eef1f5;">€</span>
                                <input class="form-control" type="number" id="totale" name="totale" readonly style="direction: rtl;" min="1" placeholder="0000"> </div> 
                        </div>
                        <div class="col-md-1" style=" padding-left: 5px;padding-right: 10px; width: 1%; font-size: 20px;">  
                            <label>&nbsp;</label>,
                        </div>
                        <div class="col-md-1" id="div_centesimi" style=" padding-left: 0px; width: 10%; ">
                            <label>&nbsp;</label>
                            <input class="form-control" type="number" id="centesimi" name="centesimi" readonly value="00" placeholder="00" min="0" max="99" step="1" >
                        </div>

                        <div id="div_descrizione"  class="col-md-12">
                            <label class="text">Descrizione </label><label style="color: red"> &nbsp;*</label>
                            <textarea class="form-control" rows="4" style="resize: none;" id="descrizione" name="descrizione" placeholder="Descrizione"></textarea>
                        </div>

                        <div class="fileinput fileinput-new col-md-4" data-provides="fileinput">
                            <label class="text">Checklist </label><label style="color: red"> &nbsp;*</label>
                            <div class="input-group input-large" id="div_file">
                                <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                    <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                    <span class="fileinput-filename"> </span>
                                </div>
                                <span class="input-group-addon btn default btn-file">
                                    <span class="fileinput-new"> Scegli file </span>
                                    <span class="fileinput-exists"> Modifica </span>
                                    <input type="file" id="file" name="file" accept="application/pdf" onchange="ctrlFile('file');"> </span>
                                <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                            </div>
                            <div class="col-md-12 pull-left" style="padding-left: 0px;padding-right: 30px;">
                                <a id="a_file" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                    <i class="fa fa-exclamation-triangle"></i> Selezionare un file PDF
                                </a>
                            </div>
                        </div>

                        <div class="col-md-12"><br>
                            <label type="text" style="color: red" >* Campo Obbligatorio</label>
                        </div>


                        <div class="modal-footer" style="border-top: #f4f4f4;">
                            <div class="form-group">                             
                                <div class="col-md-12"> 
                                    <button type="button" class="btn btn-danger large " data-dismiss="modal" onclick="return dismiss('accettamodal');">Chiudi</button>                    
                                    <button type="submit" class="btn btn-success large ">Conferma</button>
                                </div>
                            </div>
                        </div>
                    </form>
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
            <%@ include file="menu_revisore/menu_politicaC2.jsp"%>
            <!-- END MENU -->

            <%  user = session.getAttribute("username").toString();
                String control = request.getParameter("varCtrl");
            %>
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
                    <h3 class="page-title font-blue-chambray"><strong> C2 </strong>  <small class="font-blue-chambray"> AMMISSIBILITÀ INDENNITÀ </small> </h3>    
                    <% String esitoact = request.getParameter("esitoact");

                        if (esitoact == null) {
                            esitoact = "";
                        }
                        if (esitoact.equals("KO")) {%>
                    <br>
                    <div class="alert bg-red-thunderbird  alert-dismissible " style="text-align: center;color: white">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Errore! Riprovare</strong></span>
                    </div>
                    <%} else if (esitoact.equals("OK")) {%>
                    <br>
                    <div class="alert bg-green-jungle alert-dismissible " style="text-align: center;color: white">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Operazione eseguita con successo!</strong></span>
                    </div>
                    <%}%>
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="portlet box purple-seance">
                        <div class="portlet-title">
                            <div class="caption">  
                                <i class="fa fa-search font-white"></i>
                                <span class="caption-subject font-white bold ">Cerca</span>
                            </div>
                        </div>
                        <div class="portlet-body form">
                            <form class="form-horizontal" action="rev_liquidazioneC2DT.jsp" method="post" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlRicerca();">
                                <input type="hidden" value="empty" name="search">
                                <div class="form-body" id="div_form">
                                    <div class="form-group">
                                        <div class="col-md-3" id="div_ente">
                                            <label>Ente</label>
                                            <select class="form-control select2-allow-clear" id="ente" name="ente">
                                                <option value="...">...</option>
                                                <%for (String[] t : enti) {
                                                        if (ente.equals(t[0])) {%>
                                                <option selected value="<%=t[0]%>"><%=t[1]%></option>
                                                <%} else {%>
                                                <option value="<%=t[0]%>"><%=t[1]%></option>
                                                <%}
                                                    }%>
                                            </select>
                                        </div>
                                        <div class="col-md-3" id='div_date'>
                                            <label>Periodo Erogazione</label>
                                            <div class="input-group date-picker input-daterange" data-date="01/01/2000" data-date-format="dd/mm/yyyy">
                                                <input type="text" class="form-control" name="from" id="from" autocomplete="off" value="<%=from%>">
                                                <span class="input-group-addon"> a </span>
                                                <input type="text" class="form-control" name="to" id="to" autocomplete="off" value="<%=to%>"> 
                                            </div>
                                        </div>
                                    </div>
                                    <br><br>
                                    <div class="form-group">
                                        <div class="col-md-4">
                                            <button type="submit" class="btn blue-chambray btn-outline"><i class="fa fa-search"></i> Cerca</button>  
                                            <a href="rev_liquidazioneC2DT.jsp" class="btn red-thunderbird btn-outline"><i class="fa fa-close"></i> Reset</a>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div><label style="color: #F7470C;">** Visualizzati solo i primi 25 risultati, per la visualizzazione completa effettuare una ricerca</label>
                    <div class="row">
                        <div class="col-md-12">
                            <!-- BEGIN PORTLET-->   

                            <div class="portlet box blue-chambray">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <span class="fa fa-folder-open font-white tooltips"></span>
                                        <span class="caption-subject font-white bold ">Rimborsi</span>
                                    </div>
                                    <div class="tools"> 
                                    </div>
                                </div>                               
                                <div class="portlet-body">                                  
                                    <table class="table table-bordered" id="sample_0" width="100%">
                                        <thead>
                                            <tr>
                                                <th width="15%">Azioni</th>
                                                <th>Singoli Rimborsi</th>
                                                <th>Convenzioni</th>
                                                <th>Ente</th>
                                                <th>AD/AU</th>
                                                <th>Politica</th>
                                                <th>Protocollo</th>
                                                <th>Documenti</th>
                                                <th>Data Domanda Rimborso</th>
                                            </tr>
                                        </thead>
                                        <tbody>                                       
                                        </tbody>
                                        <tfoot hidden>
                                            <tr>

                                                <th><input type="text" class="form-control" name="dest1" placeholder="..." disabled=""></th>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..." disabled=""></th>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..." disabled=""></th> 
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..." disabled=""></th>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                            </tr>
                                        </tfoot>
                                    </table>
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

            <script src="sumImportoRimborso.js" type="text/javascript"></script>

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

                                $(function () {
                                    $('#centesimi').on('change', function (e) {
                                        var $field = $(this),
                                                val = this.value;
                                        if (val && val.length >= 2) {
                                            $field.val(val.substr(0, 2));
                                        } else if (val && val.length < 2) {
                                            $field.val(val + "0");
                                        }
                                    });
                                });
            </script>

            <script type="text/javascript">
                jQuery(document).ready(function () {
                    var dt2 = function () {
                        var g = $("#sample_0");
                        g.dataTable({
                            language: {aria: {},
                                sProcessing: "Ricerca...",
                                emptyTable: "Nessun risultato",
                                info: "Mostra _START_ di _END_ su _TOTAL_ risultati",
                                infoEmpty: "Nessun risultato",
                                infoFiltered: "(filtrato su _MAX_)",
                                lengthMenu: "Mostra _MENU_", search: "Cerca",
                                zeroRecords: "Ricerca...",
                                paginate: {previous: "Precedente", next: "Successiva", last: "Ultima", first: "Prima"}},
                            processing: true,
                            ajax: {
                                url: "QueryRevisore?type=1&politica=C2&ente=<%=ente%>&from=<%=from%>&to=<%=to%>",
                                dataSrc: "aaData",
                                type: "GET"
                            },
                            drawCallback: function (settings, json) {
                                $('.popovers').popover({
                                    container: 'body',
                                });
                            },
                            columnDefs: [{class: "allineacentro", targets: [0]}, {class: "allineacentro", targets: [1]}, {class: "allineacentro", targets: [2]}, {class: "allineacentro", targets: [3]}, {class: "allineacentro", targets: [4]}, {class: "allineacentro", targets: [5]}, {class: "allineacentro", targets: [6]}, {class: "allineacentro", targets: [7]}, {class: "allineacentro", targets: [8]},
                                {orderable: !1, targets: [0]},
                                {orderable: !1, targets: [1]},
                                {orderable: !1, targets: [2]},
                                {orderable: !1, targets: [7]},
                            ],
                            buttons: [
                                {extend: "excel", className: "btn white btn-outline", text: "<i class='fa fa-file-excel-o'></i> Excel"},
                                {extend: "colvis", className: "btn white btn-outline", text: "Colonne"}]
                            ,
                            colReorder: {reorderCallback: function () {
                                    console.log("callback");
                                }},
                            lengthMenu: [
                                [25, 50, 100, -1],
                                [25, 50, 100, "All"]
                            ],
                            scrollX: true,
                            //scrollY: '45vh',
                            pageLength: 25,
                            order: [],
                            dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                        });
                    };
                    jQuery().dataTable && dt2();
                });
            </script>


    </body>
</html>

<%}%>
<%}%>