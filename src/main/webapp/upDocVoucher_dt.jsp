<%-- 
    Document   : addtutor
    Created on : 15-ott-2018, 14.33.16
    Author     : agodino
--%>

<%@page import="com.seta.entity.Voucher"%>
<%@page import="com.seta.entity.B3"%>
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
        if (!Action.isVisibile(stat, "upDocVoucher_dt.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            //Etichette et = new Etichette("IT");
            String id = request.getParameter("idpolitica");
            ArrayList<Tutor> tutor = Action.getListTutorEnte((int) session.getAttribute("idente"));
            Voucher pol = Action.getVoucherByIdDt(id);
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
                if ($("#file").val().split('\\').pop() != "" && $("#attestato").val().split('\\').pop() != "" && $("#registro").val().split('\\').pop() != "" && $("#allegato").val().split('\\').pop() != "" && $("#delega").val().split('\\').pop() != "") {
                    var file = Number(document.getElementById('file').files[0].size);
                    var attestato = Number(document.getElementById('attestato').files[0].size);
                    var registro = Number(document.getElementById('registro').files[0].size);
                    var allegato = Number(document.getElementById('allegato').files[0].size);
                    var delega = Number(document.getElementById('delega').files[0].size);
                    if ((file + attestato + registro + allegato + delega) > 10485760) {
                        $("#a_file").css("display", "block");
                        $("#a_allegato").css("display", "block");
                        $("#a_attestato").css("display", "block");
                        $("#a_registro").css("display", "block");
                        $("#a_delega").css("display", "block");
                        $("#a_file").html("<i class='fa fa-exclamation-triangle'></i> Dimensione Dei File Eccessiva");
                        $("#a_allegato").html("<i class='fa fa-exclamation-triangle'></i> Dimensione Dei File Eccessiva");
                        $("#a_attestato").html("<i class='fa fa-exclamation-triangle'></i> Dimensione Dei File Eccessiva");
                        $("#a_registro").html("<i class='fa fa-exclamation-triangle'></i> Dimensione Dei File Eccessiva");
                        $("#a_delega").html("<i class='fa fa-exclamation-triangle'></i> Dimensione Dei File Eccessiva");
                        $("#div_file").attr("class", "col-md-4 has-error");
                        $("#div_attestato").attr("class", "col-md-4 has-error");
                        $("#div_allegato").attr("class", "col-md-4 has-error");
                        $("#div_registro").attr("class", "col-md-4 has-error");
                        $("#div_delega").attr("class", "col-md-4 has-error");
                        return false;
                    } else {
                        resetDiv(file, 'file');
                        resetDiv(attestato, 'attestato');
                        resetDiv(registro, 'registro');
                        resetDiv(allegato, 'allegato');
                        resetDiv(delega, 'delega');
                        return true;
                    }
                } else {
                    resetDiv(file, 'file');
                    resetDiv(attestato, 'm5');
                    resetDiv(registro, 'contratto');
                    resetDiv(allegato, 'file');
                    resetDiv(delega, 'm5');
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
                            <%if (pol.getDoc_ragazzo() == null) {%>
                            <script type="text/javascript">

                                function ctrlFile1(id) {
                                    var nomefile = $("#" + id).val().split('\\').pop();
                                    var estensione = nomefile.substring(nomefile.lastIndexOf("."));
                                    if (estensione == null || estensione == "") {
                                        $("#div_" + id).attr("class", "col-md-4");
                                        $("#a_" + id).css("display", "none");
                                    } else if (estensione == null || estensione == "") {
                                        $("#div_" + id).attr("class", "col-md-4");
                                        $("#a_" + id).css("display", "none");
                                        return false;
                                    } else if (estensione != ".pdf" && estensione != ".PDF") {
                                        $("#div_" + id).attr("class", "col-md-4 has-error");
                                        $("#a_" + id).css("display", "block");
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

                                    if ($('#ore').val() == 0) {
                                        $("#div_ore").attr("class", "col-md-1 has-error");
                                        err = true;
                                    } else {
                                        $("#div_ore").attr("class", "col-md-1 has-success");
                                    }
                                    if (Number($('#budget').val() + "." + $('#centesimi').val()) > <%=Action.getPath("max_voucher")%>) {
                                        $("#div_voucher").attr("class", "has-error");
                                        showmod1('large', 'largetext', 'Importo del voucher maggiore rispetto al massimo consentito')
                                        err = true;
                                    } else {
                                        $("#div_voucher").attr("class", "has-success");
                                    }
                                    if (!ctrlFile1("file")) {
                                        $("#div_file").attr("class", "col-md-3 has-error");
                                        err = true;
                                    }
                                    if (!ctrlFile1("delega")) {
                                        $("#div_delega").attr("class", "col-md-4 has-error");
                                        err = true;
                                    }
                                    if (!ctrlFile1("registro")) {
                                        $("#div_registro").attr("class", "col-md-4 has-error");
                                        err = true;
                                    }
                                    if (!ctrlFile1("attestato")) {
                                        $("#div_attestato").attr("class", "col-md-4 has-error");
                                        err = true;
                                    }
                                    if (!ctrlFile1("allegato")) {
                                        $("#div_allegato").attr("class", "col-md-4 has-error");
                                        err = true;
                                    }
                                    if (err) {
                                        return false;
                                    }
                                    return true;
                                }
                            </script>
                            <div class="portlet-title">
                                <div class="caption">
                                    <span class="caption-subject font-blue bold">
                                        <h3 class="page-title"><strong> CARICA DOCUMENTI</strong> </h3>                                    
                                    </span>
                                </div>
                            </div>
                            <div class="portlet-body form">
                                <form class="form-horizontal" action="<%="OperazioniEnte?type=28"%>" method="post" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlForm();" enctype="multipart/form-data">
                                    <input type="hidden" value="<%=id%>" name="id">
                                    <input type="hidden" value="new" name="tipo">
                                    <div class="form-body">
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
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_registro">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <label>Registro</label><label type="text" style="color: red" >*</label>
                                                    <div class="input-group input-large">
                                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                            <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                            <span class="fileinput-filename"> </span>
                                                        </div>
                                                        <span class="input-group-addon btn default btn-file">
                                                            <span class=" fileinput-new"> Scegli file </span>
                                                            <span class="fileinput-exists"> Modifica </span>
                                                            <input type="file" id="registro" name="registro" onchange="ctrlFile1('registro');" accept="application/pdf"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                    </div>
                                                    <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                        <a id="a_registro" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                            <i id="i_registro" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-1" id="div_ore">
                                                <label>Ore</label><label type="text" style="color: red" >*</label>
                                                <input type="text" class="form-control" id="ore" name="ore">
                                            </div>
                                            <div id="div_voucher">
                                                <div class="col-md-2 " style=" padding-right: 0px">
                                                    <label>Importo Voucher</label>
                                                    <div class="input-group"> 
                                                        <span class="input-group-addon" style="background-color: #eef1f5;">€</span>
                                                        <input class="form-control" type="number" id="budget" name="budget" placeholder="0000" style="padding-left: 103px; padding-right:0px;"></div>
                                                </div>
                                                <div class="col-md-1" style=" padding-left: 5px;padding-right: 5px; font-size: 20px; width: 1%;">  <label>&nbsp;</label><br>
                                                    ,
                                                </div>
                                                <div class="col-md-1" style=" padding-left: 0px;">
                                                    <label>&nbsp;</label>
                                                    <input class="form-control quantity" type="number" id="centesimi" name="centesimi" min="0" max="99" placeholder="00" step="1" style="padding-left: 5px;">

                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_attestato">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <label>Attestato Delle Competenze</label><label type="text" style="color: red" >*</label>
                                                    <div class="input-group input-large">
                                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                            <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                            <span class="fileinput-filename"> </span>
                                                        </div>
                                                        <span class="input-group-addon btn default btn-file">
                                                            <span class=" fileinput-new"> Scegli file </span>
                                                            <span class="fileinput-exists"> Modifica </span>
                                                            <input type="file" id="attestato" name="attestato" onchange="ctrlFile1('attestato');" accept="application/pdf"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                    </div>
                                                    <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                        <a id="a_attestato" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                            <i id="i_attestato" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_allegato">
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
                                                            <input type="file" id="allegato" name="allegato" onchange="ctrlFile1('allegato');" accept="application/pdf"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                    </div>
                                                    <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                        <a id="a_allegato" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                            <i id="i_allegato" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_delega">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <label>Delega Del Destinatario</label><label type="text" style="color: red" >*</label>
                                                    <div class="input-group input-large">
                                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                            <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                            <span class="fileinput-filename"> </span>
                                                        </div>
                                                        <span class="input-group-addon btn default btn-file">
                                                            <span class=" fileinput-new"> Scegli file </span>
                                                            <span class="fileinput-exists"> Modifica </span>
                                                            <input type="file" id="delega" name="delega" onchange="ctrlFile1('delega');" accept="application/pdf"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                    </div>
                                                    <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                        <a id="a_delega" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                            <i id="i_delega" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                        </a>
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
                            <%} else {
                                String[] voucher = String.format("%1$.2f", pol.getVoucher()).split(",");
                                String cent = voucher[1];
                                String budget = voucher[0];

                            %>
                            <div class="portlet-title">
                                <div class="caption">
                                    <span class="caption-subject font-blue bold">
                                        <h3 class="page-title"><strong> MODIFICA DOCUMENTI</strong> </h3>                                    
                                    </span>
                                </div>
                            </div>
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

                                    if ($('#ore').val() == 0) {
                                        $("#div_ore").attr("class", "col-md-1 has-error");
                                        err = true;
                                    }
                                    if (Number($('#budget').val() + "." + $('#centesimi').val()) > <%=Action.getPath("max_voucher")%>) {
                                        $("#div_voucher").attr("class", "has-error");
                                        showmod1('large', 'largetext', 'Importo del voucher maggiore rispetto al massimo consentito')
                                        err = true;
                                    }
                                    if (!ctrlFile("file")) {
                                        err = true;
                                    }
                                    if (!ctrlFile("delega")) {
                                        err = true;
                                    }
                                    if (!ctrlFile("registro")) {
                                        err = true;
                                    }
                                    if (!ctrlFile("attestato")) {
                                        err = true;
                                    }
                                    if (!ctrlFile("allegato")) {
                                        err = true;
                                    }
                                    if (err) {
                                        return false;
                                    }
                                    return true;

                                }
                            </script>
                            <div class="portlet-body form">
                                <form class="form-horizontal" action="<%="OperazioniEnte?type=28"%>" method="post" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlForm2();" enctype="multipart/form-data">
                                    <input type="hidden" value="<%=id%>" name="id">
                                    <input type="hidden" value="<%=pol.getDoc_registro()%>" name="registro_old">
                                    <input type="hidden" value="<%=pol.getDoc_ragazzo()%>" name="file_old">
                                    <input type="hidden" value="<%=pol.getDoc_attestato()%>" name="attestato_old">
                                    <input type="hidden" value="<%=pol.getDoc_allegato()%>" name="allegato_old">
                                    <input type="hidden" value="<%=pol.getDoc_delega()%>" name="delega_old">
                                    <input type="hidden" value="mod" name="tipo">
                                    <div class="form-body">
                                        <div class="col-md-3" id="div_tutor" style="display: none;">
                                            <label>Tutor</label>
                                            <select class="form-control select2-allow-clear" id="tutor" name="tutor">
                                                <option value="...">...</option>
                                                <%for (Tutor t : tutor) {
                                                        //if (t.getId() == pol.getTutor()) {
                                                        if (false) {%>
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
                                                    <label>Carta d'Identità Destinatario</label><label type="text" style="color: red" >*</label>
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
                                                            <i id="a_file" class="fa fa-exclamation-triangle"></i> Selezionare un file PDF
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_registro">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <label>Registro</label><label type="text" style="color: red" >*</label>
                                                    <div class="input-group input-large">
                                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                            <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                            <span class="fileinput-filename"> </span>
                                                        </div>
                                                        <span class="input-group-addon btn default btn-file">
                                                            <span class=" fileinput-new"> Scegli file </span>
                                                            <span class="fileinput-exists"> Modifica </span>
                                                            <input type="file" id="registro" name="registro" onchange="ctrlFile('registro');" accept="application/pdf"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                    </div>
                                                    <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                        <a id="a_registro" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                            <i id="i_registro" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-1" id="div_ore">
                                                <label>Ore</label><label type="text" style="color: red" >*</label>
                                                <input type="text" class="form-control" value="<%=pol.getOre()%>" id="ore" name="ore">
                                            </div>
                                            <div id="div_voucher">
                                                <div class="col-md-2 " style=" padding-right: 0px">
                                                    <label>Importo Voucher</label>
                                                    <div class="input-group"> 
                                                        <span class="input-group-addon" style="background-color: #eef1f5;">€</span>
                                                        <input class="form-control" type="number" id="budget" name="budget" value="<%=budget%>" style="padding-left: 103px; padding-right:0px;"></div>
                                                </div>
                                                <div class="col-md-1" style=" padding-left: 5px;padding-right: 5px; font-size: 20px; width: 1%;"><label>&nbsp;</label><br>
                                                    ,
                                                </div>
                                                <div class="col-md-1" style=" padding-left: 0px;">
                                                    <label>&nbsp;</label>
                                                    <input class="form-control quantity" type="number" id="centesimi" name="centesimi" value="<%=cent%>" min="0" max="99" placeholder="00" step="1" style="padding-left: 5px;">

                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_attestato">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <label>Attestato Delle Competenze</label><label type="text" style="color: red" >*</label>
                                                    <div class="input-group input-large">
                                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                            <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                            <span class="fileinput-filename"> </span>
                                                        </div>
                                                        <span class="input-group-addon btn default btn-file">
                                                            <span class=" fileinput-new"> Scegli file </span>
                                                            <span class="fileinput-exists"> Modifica </span>
                                                            <input type="file" id="attestato" name="attestato" onchange="ctrlFile('attestato');" accept="application/pdf"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                    </div>
                                                    <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                        <a id="a_attestato" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                            <i id="i_attestato" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_allegato">
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
                                                            <input type="file" id="allegato" name="allegato" onchange="ctrlFile('allegato');" accept="application/pdf"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                    </div>
                                                    <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                        <a id="a_allegato" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                            <i id="i_allegato" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_delega">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <label>Delega Del Destinatario</label><label type="text" style="color: red" >*</label>
                                                    <div class="input-group input-large">
                                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                            <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                            <span class="fileinput-filename"> </span>
                                                        </div>
                                                        <span class="input-group-addon btn default btn-file">
                                                            <span class=" fileinput-new"> Scegli file </span>
                                                            <span class="fileinput-exists"> Modifica </span>
                                                            <input type="file" id="delega" name="delega" onchange="ctrlFile('delega');" accept="application/pdf"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                    </div>
                                                    <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                        <a id="a_delega" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                            <i id="i_delega" class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <label style="color: red;">**i documenti resteranno invariati, se non verranno caricati nuovamente</label>
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

        <script type="text/javascript">
                                                                $(function () {
                                                                    $(".quantity").on("change", function (e) {
                                                                        var $field = $(this),
                                                                                val = this.value;
                                                                        if (val && val.length >= 2) {
                                                                            $field.val(val.substr(0, 2));
                                                                        } else if (val && val.length < 2) {
                                                                            $field.val(val + "0");
                                                                        }
                                                                        if (Number($('#budget').val() + "." + $('#centesimi').val()) ><%=Action.getPath("max_voucher")%>) {
                                                                            $field.val('<%=Action.getPath("max_voucher")%>');
                                                                            $('#centesimi').val('00')
                                                                        }
                                                                    });
                                                                });
                                                                $(function () {
                                                                    $("#budget").on("keyup change", function (e) {
                                                                        var $field = $(this),
                                                                                val = this.value;
                                                                        if (val && val.length >= 4) {
                                                                            $field.val(val.substr(0, 4));
                                                                        }
                                                                        if (Number(this.value + "." + $('#centesimi').val()) ><%=Action.getPath("max_voucher")%>) {
                                                                            $field.val('<%=Action.getPath("max_voucher")%>');
                                                                            $('#centesimi').val('00')
                                                                        }
                                                                    });
                                                                });
        </script>

    </body>
</html>
<%}
    }%>

