<%-- 
    Document   : addtutor
    Created on : 15-ott-2018, 14.33.16
    Author     : agodino
--%>

<%@page import="com.seta.entity.RegistroDt"%>
<%@page import="com.seta.entity.PrgFormativoDt"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.seta.entity.Registro"%>
<%@page import="com.seta.entity.Lavoratore"%>
<%@page import="com.seta.entity.PrgFormativo"%>
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
        if (!Action.isVisibile(stat, "upRegistroDt.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            //Etichette et = new Etichette("IT");
            String idprg = request.getParameter("idprg");
            PrgFormativoDt prg = Action.getPrgFormativoDtById(idprg);
            Tutor tutor = Action.findTutorById(String.valueOf(prg.getTutor()));

            int mesi = Action.getRegisterNumberDt(idprg);
            String[] mese = Action.getMese(mesi + 1);
            String from = "";
            String to = "";
            int ore_tot = 0;
            RegistroDt reg = Action.getLastRegisterDt(idprg);
            Calendar c = Calendar.getInstance();
            if (reg != null) {
                c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(reg.getDatafine()));
                c.add(Calendar.DATE, 1);
                from = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                c.add(Calendar.DATE, 30);
                to = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                ore_tot = Action.getTotlHourPrgDt(idprg);
            } else if (idprg != null) {
                c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(prg.getDataavvio()));
                from = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                c.add(Calendar.DATE, 30);
                to = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                ore_tot = Action.getTotlHourPrgDt(idprg);
            }
            String max_ore = Action.getPath("max_ore_mese_registro");
            //mesi = 4;
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

            function submitForm() {
                document.form.submit();
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
                        <button type="button" class="btn dark btn-outline" data-dismiss="modal" onclick="return dismiss('large');">Chiudi</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade bs-modal-lg" id="large2" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header alert-warning ">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <font color="white"><h4 class="modal-title">Attenzione</h4></font>
                    </div>
                    <div class="modal-body" id="largetext2">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" data-dismiss="modal" onclick="return dismiss('large2');">Chiudi</button>
                        <button type="button" class="btn green-jungle btn-outline" data-dismiss="modal" onclick="submitForm();">Carica Comunque</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade bs-modal-lg" id="large3" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header alert-success ">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <font color="white"><h4 class="modal-title">Attenzione</h4></font>
                    </div>
                    <div class="modal-body" id="largetext3">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" data-dismiss="modal" onclick="return dismiss('large3');">Chiudi</button>
                        <button type="button" class="btn green-jungle btn-outline" data-dismiss="modal" onclick="submitForm();">Carica</button>
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
                        <%  String esitoins = request.getParameter("esitoins");
                            String esitolast = request.getParameter("esitolast");

                            if (esitoins == null) {
                                esitoins = "";
                            }
                            if (esitolast == null) {
                                esitolast = "";
                            }

                            if (esitoins.equals("KO")) {%>           
                        <br>
                        <div class="alert bg-red-thunderbird  alert-dismissible " style="text-align: center;color: white">
                            <button type="button" class="close" data-close="alert"></button>
                            <span><strong>Errore! Riprovare</strong></span>            
                        </div>
                        <%} else if (esitoins.equals("OK")) {%> 
                        <br>
                        <div class="alert bg-green-jungle alert-dismissible " style="text-align: center;color: white">
                            <button type="button" class="close" data-close="alert"></button>
                            <span><strong>Registro caricato con successo!</strong></span>
                        </div>
                        <%} else if (esitolast.equals("OK")) {%> 
                        <br>
                        <div class="alert bg-yellow-active alert-dismissible " style="text-align: center;color: white">
                            <button type="button" class="close" data-close="alert"></button>
                            <span style="font-size: 18px"><strong>Ultimo registro caricato con successo. Ora il Progetto Formativo è contrassegnato come <font style="font-size: 25px">concluso</font>.
                                </strong></span>
                        </div>
                        <%} else if (esitoins.equals("") && esitolast.equals("")) {%>
                        <div class="portlet light bordered ">
                            <div class="portlet-title">
                                <div class="caption">
                                    <span class="caption-subject font-blue bold">
                                        <h3 class="page-title"><strong> CARICA REGISTRO</strong></h3>                                    
                                    </span>
                                </div>
                            </div>
                            <script type="text/javascript">
                                function ctrlForm() {

                                    var ore = $("#ore").val();
                                    var err = false;
                                    //var n_ore=Number(ore);
                                    var ore_tot = Number("<%=prg.getOre_tot()%>");
                                    var ore_n = Number(ore) + Number("<%=ore_tot%>");
                                    if (ore == null || ore == "") {
                                        $("#div_ore").attr("class", "col-md-2 has-error");
                                        err = true;
                                    } else {
                                        $("#div_ore").attr("class", "col-md-2 has-success");
                                    }
                                    if (!ctrlFile('file')) {
                                        $("#div_file").attr("class", "col-md-4 has-error");
                                        err = true;
                                    }
                                <%if ((mesi + 1) == prg.getDurata_mesi()) {%>
                                    if (!ctrlFile('cert')) {
                                        $("#div_cert").attr("class", "col-md-4 has-error");
                                        err = true;
                                    }
                                <%}%>
                                    if (err) {
                                        return false;
                                    } else {
                                        if (ore_n > ore_tot) {
                                            $("#div_ore").attr("class", "col-md-2 has-warning");
                                            showmod1("large2", "largetext2", "Il numero di ore inserite supera il totale delle ore del Progetto Formativo");
                                            return false;
                                        }
                                    }
                                    showmod1("large3", "largetext3", "Il registro che si sta per caricare non potrà piu essere modificato. Sicuro di voler proseguire?");
                                    return false;
//                                    return true;
                                }
                            </script>
                            <div class="portlet-body form">
                                <form class="form-horizontal" 
                                      action="OperazioniEnte?type=19" method="post" 
                                      name="form" 
                                      id="form" 
                                      accept-charset="ISO-8859-1" 
                                      onsubmit="return ctrlForm();" enctype="multipart/form-data">
                                    <input type="hidden" value="<%=idprg%>" name="idprg">                                    
                                    <input type="hidden" value="<%=tutor.getDocumento()%>" name="t_doc">
                                    <input type="hidden" value="<%=prg.getDoc_ragazzo()%>" name="r_doc">
                                    <input type="hidden" value="<%=mese[0]%>" name="mese">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <div class="col-md-3">
                                                <label>Tutor</label>
                                                <input type="text" class="form-control" value="<%=tutor.getNome()%> <%=tutor.getCognome()%>" readonly>
                                            </div>
                                            <div class="col-md-3">
                                                <label>Mese</label>
                                                <input type="text" class="form-control" value="<%=mese[1]%>" readonly>
                                            </div>
                                            <div class="col-md-3" id='div_date'>
                                                <label>Periodo</label><label type="text" style="color: red" >*</label>
                                                <div class="input-group date-picker input-daterange" data-date="01/01/2000" data-date-format="dd/mm/yyyy">
                                                    <input type="text" class="form-control" name="from" id="from" autocomplete="off" value="<%=from%>">
                                                    <span class="input-group-addon"> a </span>
                                                    <input type="text" class="form-control" name="to" id="to" autocomplete="off" value="<%=to%>"> 
                                                </div>
                                            </div>
                                            <div class="col-md-2" id="div_ore">
                                                <label>Ore</label><label type="text" style="color: red" >*</label>
                                                <input type="number" class="form-control" id="ore" name="ore" min="0" max="<%=max_ore%>" maxlength="3">
                                            </div>                                              
                                        </div>
                                        <br>
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_file">
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
                                                            <input type="file" id="file" name="file" onchange="ctrlFile('file');" accept="application/pdf"></span>                                                    
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

                                        <%if ((mesi + 1) == prg.getDurata_mesi()) {%>
                                        <div style="border:1px; border-style: solid; border-color: #eef1f5;"></div><br>
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_cert">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <label><strong>Certificato Delle Competenze</strong></label><label type="text" style="color: red" >*</label>
                                                    <div class="input-group input-large">
                                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                            <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                            <span class="fileinput-filename"> </span>
                                                        </div>
                                                        <span class="input-group-addon btn default btn-file">
                                                            <span class=" fileinput-new"> Scegli file </span>
                                                            <span class="fileinput-exists"> Modifica </span>
                                                            <input type="file" id="cert" name="cert" onchange="ctrlFile('cert');" accept="application/pdf"></span>                                                    
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Rimuovi </a>
                                                    </div>
                                                    <div class="col-md-8 pull-left" style="padding-left: 0px;padding-right: 23px;">
                                                        <a id="a_cert" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                            <i class="fa fa-exclamation-triangle"></i> Selezionare file pdf!
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-1">
                                            </div>
                                            <div class="col-md-4">
                                                <div class="row">
                                                    <div class="small-box" style="background-color: #d6af15">
                                                        <div class="inner" style="color: white">
                                                            <h4><strong>Attenzione!</strong></h4>
                                                            <p>Per caricare l'ultimo mese è necessario<br>caricare <strong>l'attestato delle competenze</strong></p>
                                                        </div>
                                                        <div class="icon" style="color: red;">
                                                            <i class="fa fa-exclamation-triangle"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <%}%>
                                        <br><label type="text" style="color: red" >* Campo Obbligatorio</label><br><br>
                                        <div class="form-group">
                                            <div class="col-md-4"> 
                                                <button type="submit" class="btn blue-soft btn-outline"><i class="fa fa-upload"></i> Carica</button>                                            
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
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
                                                                $(document).ready(function () {
                                                                    window.history.pushState(null, "", window.location.href);
                                                                    window.onpopstate = function () {
                                                                        window.history.pushState(null, "", window.location.href);
                                                                    };
                                                                });
        </script>
    </body>
</html>
<%}
    }%>

