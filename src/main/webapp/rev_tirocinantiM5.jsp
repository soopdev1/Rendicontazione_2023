<%@page import="com.seta.util.Utility"%>
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
        if (!Action.isVisibile(stat, "rev_tirocinantiM5.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            ArrayList<String[]> enti = Action.getList_Enti();
            String ente = request.getParameter("ente"),
                    from = request.getParameter("from"),
                    to = request.getParameter("to"),
                    nome = request.getParameter("nome"),
                    cognome = request.getParameter("cognome"),
                    cf = request.getParameter("cf");

            double[] prezziario = Action.getPrezziario("registro", "2");
            String value = String.valueOf(prezziario[0]);
            String unit = value.substring(0, value.lastIndexOf("."));
            String cent = value.substring(value.lastIndexOf(".") + 1, value.length()).length() == 1 ? value.substring(value.lastIndexOf(".") + 1, value.length()) + "0" : value.substring(value.lastIndexOf(".") + 1, value.length());

            if (ente == null) {
                ente = "";
            }
            if (from == null) {
                from = "";
            }
            if (to == null) {
                to = "";
            }
            if (cf == null) {
                cf = "";
            }
            if (nome == null) {
                nome = "";
            }
            if (cognome == null) {
                cognome = "";
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

            function showmod(modal) {
                document.getElementById(modal).className = document.getElementById(modal).className + " in";
                document.getElementById(modal).style.display = "block";
            }

            function dismiss(modal) {
                document.getElementById(modal).className = "modal fade";
                document.getElementById(modal).style.display = "none";
            }

            function setScartaID(id) {
                $('#id_s').val(id);
            }
            function setScartaID2(id, ore) {
                $('#id_s2').val(id);
                $('#ore2').val(ore);
            }

            //function setAccettaID(id) {
            //    document.getElementById('confirmactive').href = 'OperazioniRevisore?type=8&idregistri=' + id + '&tipo=C06';
            //}

            function setAccettaID(id, ore) {
                $('#idregistri').val(id);
                $('#ore').val(ore);
                $('#idore').val(ore);

            }

            function submitScarto() {
                var motivo1 = document.getElementById("motivo1").value;
                var motivo2 = document.getElementById("motivo2").value;

                if (motivo1 == null || motivo1 == "..." || motivo1 == "") {
                    //alert(motivo mancante);
                    $("#div_motivo1").attr("class", "has-error");
                    return false;

                } else if (motivo1 == "Altro") {
                    if (motivo2 == "") {
                        $("#div_motivo2").attr("class", "has-error");
                        return false;
                    } else {
                        document.getElementById('conferma_scarta').href = "OperazioniRevisore?type=9&motivo=" + encodeURIComponent(motivo2) + "&idregistri=" + $('#id_s').val() + '&tipo=M5'
                                + "&ente=<%=ente%>&from=<%=from%>&to=<%=to%>&nome=<%=nome%>&cognome=<%=cognome%>&cf=<%=cf%>";
                    }
                } else {
                    document.getElementById('conferma_scarta').href = "OperazioniRevisore?type=9&motivo=" + encodeURIComponent(motivo1) + "&idregistri=" + $('#id_s').val() + '&tipo=M5'
                            + "&ente=<%=ente%>&from=<%=from%>&to=<%=to%>&nome=<%=nome%>&cognome=<%=cognome%>&cf=<%=cf%>";
                }

            }

            function submitScarto2() {
                var motivo12 = document.getElementById("motivo12").value;
                var motivo22 = document.getElementById("motivo22").value;
                var ore2 = document.getElementById("ore2").value;
                var err = false;
                if (ore2 == null || ore2 == "") {
                    //alert(motivo mancante);
                    $("#div_ore2").attr("class", "col-md-4 has-error");
                    err = true;
                }
                if (motivo12 == null || motivo12 == "..." || motivo12 == "") {
                    //alert(motivo mancante);
                    $("#div_motivo12").attr("class", "col-md-8 has-error");
                    err = true;

                } else if (motivo12 == "Altro") {
                    if (motivo22 == "") {
                        $("#div_motivo22").attr("class", "has-error");
                        err = true;
                    } else {
                        document.getElementById('conferma_scarta2').href = "OperazioniRevisore?type=10&motivo=" + encodeURIComponent(motivo22) + "&idregistri=" + $('#id_s2').val() + '&tipo=M5&ore=' + ore2
                                + "&ente=<%=ente%>&from=<%=from%>&to=<%=to%>&nome=<%=nome%>&cognome=<%=cognome%>&cf=<%=cf%>";
                    }
                } else {
                    document.getElementById('conferma_scarta2').href = "OperazioniRevisore?type=10&motivo=" + encodeURIComponent(motivo12) + "&idregistri=" + $('#id_s2').val() + '&tipo=M5&ore=' + ore2
                            + "&ente=<%=ente%>&from=<%=from%>&to=<%=to%>&nome=<%=nome%>&cognome=<%=cognome%>&cf=<%=cf%>";
                }

                if (err) {
                    return false;
                }
                return true;
            }

            function ctrlForm2() {
                var descrizione = document.getElementById("descrizione").value;
                var totale = document.getElementById("totale").value;
                var ore = document.getElementById("ore").value;
                var err = false;
                if (descrizione == "") {
                    $("#div_descrizione").attr("class", "col-md-12 has-error");
                    err = true;
                }
                if (ore == "") {
                    $("#div_ore").attr("class", "col-md-3 pull-right has-error");
                    err = true;
                }
                if (totale == "" || totale == "0") {
                    $("#div_totale").attr("class", "col-md-3 has-error");
                    $("#div_centesimi").attr("class", "col-md-1 has-error");
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

            function ctrlForm() {
                var ente = $("#ente").val();
                var from = $("#from").val();
                var to = $("#to").val();
                var nome = $("#nome").val();
                var cognome = $("#cognome").val();
                var cf = $("#cf").val();
                var err = false;
                if ((ente == null || ente == "...") && from == "" && to == "" && cf == "" && nome == "" && cognome == "") {
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

        <div class="modal fade bs-modal-lg" id="accettamodal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header bg-blue ">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Ammissibilità Indennità Registro</h4>
                    </div>
                    <form class="form-horizontal" action="<%="OperazioniRevisore?type=8"%>" method="post" enctype="multipart/form-data" onsubmit="return ctrlForm2();">
                        <br>
                        <input type="hidden" id="idregistri" name="idregistri">
                        <input type="hidden" id="idore" name="idore">
                        <input type="hidden" id="pagina" name="pagina" value="rev_tirocinantiM5">
                        <input type="hidden" id="pagina" name="ente" value="<%=ente%>">
                        <input type="hidden" id="pagina" name="from" value="<%=from%>">
                        <input type="hidden" id="pagina" name="to" value="<%=to%>">
                        <input type="hidden" id="pagina" name="nome" value="<%=nome%>">
                        <input type="hidden" id="pagina" name="cognome" value="<%=cognome%>">
                        <input type="hidden" id="pagina" name="cf" value="<%=cf%>">

                        <div id="div_totale" class="col-md-3" style=" padding-right: 0px; width: 25%;">
                            <label>Totale da erogare</label><label type="text" style="color: red" >*</label>
                            <div class="input-group"> 
                                <span class="input-group-addon" style="background-color: #eef1f5;">€</span>
                                <input class="form-control" type="number" id="totale" name="totale" readonly style="direction: rtl;" min="1" placeholder="0000" value="<%=unit%>"> </div> 
                        </div>
                        <div class="col-md-1" style=" padding-left: 5px;padding-right: 10px; width: 1%; font-size: 20px;">  
                            <label>&nbsp;</label>,
                        </div>
                        <div class="col-md-1" id="div_centesimi" style=" padding-left: 0px; width: 10%; ">
                            <label>&nbsp;</label>
                            <input class="form-control" type="number" id="centesimi" name="centesimi" readonly placeholder="00" value="<%=cent%>" min="0" max="99" step="1">
                        </div>
                        <div id="div_ore"  class="col-md-3 pull-right">
                            <label class="text">Ore Effettuate dal Destinatario </label>
                            <input class="form-control" type="number" step="1" min="0" max="200" id="ore" name="ore" >
                        </div>
                        <div id="div_descrizione"  class="col-md-12">
                            <label class="text">Descrizione </label><label style="color: red"> &nbsp;*</label>
                            <textarea class="form-control" rows="4" style="resize: none;" id="descrizione" name="descrizione" placeholder="Descrizione"></textarea>
                        </div>

                        <div class="fileinput fileinput-new col-md-4" data-provides="fileinput">
                            <label class="text">Checklist </label><label style="color: red"> &nbsp;*</label>
                            <div class="input-group input-large" id="div_file">
                                <div class="form-control uneditable-input input-fixed input-medium" style="width:100%!important" data-trigger="fileinput">
                                    <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                    <span class="fileinput-filename"> </span>
                                </div>
                                <span class="input-group-addon btn default btn-file">
                                    <span class="fileinput-new"> Scegli file </span>
                                    <span class="fileinput-exists"> Modifica </span>
                                    <input type="file" id="file" name="file" accept="application/pdf" onchange="ctrlFile('file');"> </span>
                                <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                            </div>
                            <div class="col-md-10 pull-left" style="padding-left: 0px;padding-right: 19px;">
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

        <div class="modal fade bs-modal-lg" id="scartamodal" tabindex="-1" role="dialog" aria-hidden="true">
            <%ArrayList<String[]> motivi = Action.getMotivi();%>
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header   alert-warning ">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Rigetta Registro</h4>
                    </div>

                    <div class="modal-body">                       
                        <label class="text">Specificare motivazione rigetto </label><label style="color: red"> &nbsp;*</label>
                        <input type="hidden" id="id_s">
                        <div id="div_motivo1">
                            <select class="form-control select2-container--classic" id="motivo1" name="motivo1">
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
        <div class="modal fade bs-modal-lg" id="scartamodal2" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header  alert-danger " >
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Scarta Registro</h4>
                    </div>

                    <div class="modal-body">                       

                        <input type="hidden" id="id_s2">
                        <div id="div_ore2" class="col-md-4">
                            <label class="text">Ore Effettuate dal Destinatario </label><label style="color: red"> &nbsp;*</label>
                            <input class="form-control" type="number" step="1" min="0" max="200" id="ore2" name="ore2" >
                        </div>
                        <div id="div_motivo12" class="col-md-8">
                            <label class="text">Specificare motivazione scarto </label><label style="color: red"> &nbsp;*</label>
                            <select class="form-control select2-container--classic" id="motivo12" name="motivo12">
                                <%for (int i = 0; i < motivi.size(); i++) {%>
                                <option value="<%=motivi.get(i)[1]%>"><%=motivi.get(i)[1]%></option>
                                <%}%>
                            </select>
                            <div id="div_motivo22">
                                <textarea class="form-control" rows="4" style="resize: none; display: none;" id="motivo22" name="motivo22" placeholder="Motivazione"></textarea>
                            </div>
                        </div>
                        <br>

                        <div class="col-md-12">
                            <label type="text" style="color: red" >* Campo Obbligatorio</label>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <div class="form-group">                             
                            <div class="col-md-12"> 
                                <button type="button" class="btn btn-danger large " data-dismiss="modal" onclick="return dismiss('scartamodal2');">Chiudi</button>                    
                                <a class="btn btn-success large " id="conferma_scarta2" onclick="return submitScarto2();">Conferma</a>                                                                      
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade bs-modal-lg" id="showPrg" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-blue-hoki font-white">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="return dismiss('showPrg');"></button>
                        <h4 class="modal-title">Progetto Formativo</h4>
                    </div>
                    <div class="modal-body">
                        <div class="col-md-3">
                            <label class="text">Data Inizio</label>
                            <input type="text" readonly id="prg_start" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label class="text">Data Fine</label>
                            <input type="text" readonly id="prg_end" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label class="text">Ore Tot.</label>
                            <input type="text" readonly id="prg_ore" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label class="text">Ore Effettuate</label>
                            <input type="text" readonly id="prg_eff" class="form-control">
                        </div>
                        <div class="clearfix"></div><br>
                        <div class="col-md-3">
                            <label class="text">Profiling</label>
                            <input type="text" readonly id="prg_profiling" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label class="text">Documenti</label><br>
                            <a style='color: white; display: block' target="_blank" id="a_convenzione" class='bg-red-flamingo fa fa-file-pdf-o btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Visualizza Convenzione' ><font color='white' face='verdana'> Convenzione</font></a>
                            <a style='color: white; display: block' target="_blank" id="a_prg" class='bg-blue-soft fa fa-file-pdf-o btn btn-default popovers' data-trigger='hover' data-placement='top' data-content='Visualizza Prg Formativo' ><font color='white' face='verdana'> Prg Formativo</font></a>
                        </div>
                    </div>
                    <div class="modal-footer" style="border:0;">
                        <br><br>
                        <div class="col-md-12">
                            <button type="button" class="btn btn-danger large" data-dismiss="modal" onclick="return dismiss('showPrg');">Chiudi</button>
                        </div>
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
            <%@ include file="menu_revisore/menu_politicaM5.jsp"%>
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
                    <h3 class="page-title font-blue-chambray"><strong> M5 </strong>  <small class="font-blue-chambray"> AMMISSIBILITÀ INDENNITÀ DESTINATARI </small> </h3>    
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
                            <form class="form-horizontal" action="rev_tirocinantiM5.jsp" method="post" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlForm();">
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
                                    <div class="form-group">
                                        <div class="col-md-3" id="div_name">
                                            <label>Nome</label>
                                            <input type="text" class="form-control" id="nome" name="nome" value="<%=nome%>">
                                        </div>
                                        <div class="col-md-3" id="div_surname">
                                            <label>Cognome</label>
                                            <input type="text" class="form-control" id="cognome" name="cognome" value="<%=cognome%>">
                                        </div>
                                        <div class="col-md-3" id="div_cf">
                                            <label>Codice Fiscale</label>
                                            <input type="text" class="form-control" id="cf" name="cf" value="<%=cf%>">
                                        </div>
                                    </div>
                                    <br><br>
                                    <div class="form-group">
                                        <div class="col-md-4">
                                            <button type="submit" class="btn blue-chambray btn-outline"><i class="fa fa-search"></i> Cerca</button>       
                                            <a href="rev_tirocinantiM5.jsp" class="btn red-thunderbird btn-outline"><i class="fa fa-close"></i> Reset</a>

                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <!-- BEGIN PORTLET-->   

                            <div class="portlet box blue-chambray">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <span class="fa fa-users font-white tooltips"></span>
                                        <span class="caption-subject font-white bold ">Registri Destinatari</span>
                                    </div>
                                    <div class="tools"> 
                                    </div>
                                </div>                               
                                <div class="portlet-body">                                  
                                    <table class="table table-bordered" id="sample_0" width="100%">
                                        <thead>
                                            <tr>
                                                <th width="12%">Azione</th>
                                                <th width="10%">Destinatario</th>
                                                <th width="10%">Codice Fiscale</th>
                                                <th width="5%">Prg. Formativo</th>
                                                <th width="17%">Ente</th>
                                                <th width="8%">Mese</th>
                                                <th width="9%">Inizio/Fine</th>
                                                <th width="5%">Durata</th>
                                                <th width="15%">Documenti</th>
                                                <th width="9%">Data Domanda Rimborso</th>
                                            </tr>
                                        </thead>
                                        <tbody>                                       
                                        </tbody>
                                        <tfoot hidden>
                                            <tr>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..." disabled=""></th>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
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
                                                url: "QueryRevisore?type=8&politica=C06&ente=<%=ente%>&from=<%=from%>&to=<%=to%>&nome=<%=nome%>&cognome=<%=cognome%>&cf=<%=cf%>",
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
                                                {class: "allineacentro", targets: [7]},
                                                {class: "allineacentro", targets: [8]},
                                                {class: "allineacentro", targets: [9]},
                                                {orderable: !1, targets: [0]},
                                                {orderable: !1, targets: [8]}
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

            <script type="text/javascript">


                $('#centesimi').on('keyup change', function () {
                    var cent = $(this);
                    var maxlength = 2;
                    var value = cent.val();
                    if (value && value.length >= maxlength) {
                        cent.val(value.substr(0, maxlength));
                    } else if (value && value.length < maxlength) {
                        cent.val("0" + value.substr(0, maxlength));
                    }
                });

                $("#motivo1").change(function () {
                    if ($("#motivo1").val() == "Altro") {
                        $("#motivo2").css("display", "block");
                    } else {
                        $("#motivo2").css("display", "none");
                    }
                });
                $("#motivo12").change(function () {
                    if ($("#motivo12").val() == "Altro") {
                        $("#motivo22").css("display", "block");
                    } else {
                        $("#motivo22").css("display", "none");
                    }
                });
            </script>


            <script type="text/javascript">
                function showPrg(id) {
                    $.get('OperazioniRevisore?type=17&id=' + id, function (response) {
                        if (response != "") {
                            var json = JSON.parse(response);
                            $('#prg_start').val(json.dataavvio);
                            $('#prg_end').val(json.datafine);
                            $('#prg_eff').val(json.ore_effettuate);
                            $('#prg_ore').val(json.ore_tot);
                            $('#prg_profiling').val(json.profiling);
                            $("#a_convenzione").attr("href", json.path_convenzione)
                            $("#a_prg").attr("href", json.file)
                            showmod('showPrg');
                        }
                    });
                }
            </script>

    </body>
</html>

<%}%>
<%}%>
