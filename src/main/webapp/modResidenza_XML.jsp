<%@page import="java.util.Map"%>
<%@page import="com.seta.entity.Lavoratore"%>
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
        if (!Action.isVisibile(stat, "modResidenza_XML.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            //Map<String, String> prov = Action.getProvinciaByCode();
            ArrayList<String[]> comuni = Action.get_ComuniResidenza();
            ArrayList<String[]> province = Action.get_ProvinciaResidenza();
            Lavoratore lav = Action.getLavoratoreById(Integer.parseInt(request.getParameter("id")));
            
            String provres = Action.getProvinciaResidenza(lav.getResidenza_codice_catastale());
            String provinciasel = String.valueOf(Action.getProvinciaResidenza(provres));
            String comunesel = lav.getResidenza_codice_catastale();
            //String provinciasel2 = String.valueOf(lav.getCodice_provincia());
            //String comunesel2 = lav.getDomicilio_codice_catastale();
            
            //if (comunesel2 == null) {
            //    comunesel2 = "";
            //}
            //if (provinciasel2 == null) {
            //    provinciasel2 = "";
            //}
            if (comunesel == null) {
                comunesel = "";
            }
            if (provinciasel == null) {
                provinciasel = "";
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

        <!--BEGIN HEADER -->
        <div class="page-container">
            <div class="page-content-wrapper" >
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">                    
                    <div class="clearfix"></div>
                    <div class="row">
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
                            <span><strong>Residenza tirocinance modificata con successo!</strong></span>
                        </div>
                        <%} else if (esitoact.equals("")) {%>
                        <div class="portlet light bordered ">
                            <div class="portlet-title">
                                <div class="caption">
                                    <span class="caption-subject font-blue bold">
                                        <h3 class="page-title"><strong> DESTINATARIO: <%=lav.getNome()%> <%=lav.getCognome()%> </strong><small> MODIFICA RESIDENZA</small> </h3>                                    
                                    </span>
                                </div>
                            </div>


                            <script type="text/javascript">
                                function ch_sel() {
                                    var category = document.getElementById('prov');
                                    var comselected = "<%=comunesel%>";
                                    var val_cat = category.value;
                                    var id_caus = '#comune';
                                    $(id_caus).empty().trigger('change');
                                <%
                                    for (int x = 0; x < comuni.size(); x++) {
                                        String[] cm = comuni.get(x);
                                %>
                                    var confront = "<%=cm[2]%>";
                                    if (confront === val_cat) {
                                        var value0 = "<%=cm[0]%>";
                                        var value1 = "<%=cm[1]%>";
                                        if (comselected === value0) {
                                            var o = $("<option/>", {value: value0, text: value1, selected: true});
                                        } else {
                                            var o = $("<option/>", {value: value0, text: value1});
                                        }
                                        $(id_caus).append(o);
                                    }
                                <%}%>
                                    if (comselected === '') {
                                        var o = $("<option/>", {value: '', text: '', selected: true});
                                        $(id_caus).append(o);
                                    }
                                    $(id_caus).val($(id_caus).val()).trigger('change');
                                }
                                
                               

                                function ctrlForm() {
                                    var indirizzo = $("#indirizzo").val();
                                    var comune = $("#comune").val();
                                    var prov = $("#prov").val();
                                    var err = false;

                                    if (indirizzo == "") {
                                        $("#div_indirizzo").attr("class", "col-md-4 has-error");
                                        err = true;
                                    } else {
                                        $("#div_indirizzo").attr("class", "col-md-4 has-success");
                                    }
                                    if (prov==null || prov == "" || prov == "...") {
                                        $("#div_comune").attr("class", "col-md-4 has-error");
                                        $("#div_prov").attr("class", "col-md-4 has-error");
                                        err = true;
                                    } else {
                                        $("#div_comune").attr("class", "col-md-4 has-success");
                                        $("#div_prov").attr("class", "col-md-4 has-success");
                                    }
                                    if (comune == null || comune == "") {
                                        $("#div_comune").attr("class", "col-md-4 has-error");
                                        err = true;
                                    } else {
                                        $("#div_comune").attr("class", "col-md-4 has-success");
                                    }

                                    if (err){
                                        return false;
                                    }
                                    return true;
                                }
                            </script>
                            <div class="portlet-body form">
                                <form class="form-horizontal" action="<%="OperazioniRegional?type=22"%>" method="post" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlForm();">
                                    <input type="hidden" name="idlavoratore" id="idlavoratore" value="<%=lav.getCdnlavoratore()%>">
                                    <div class="form-body">
                                        <label>Residenza</label>
                                        <div class="portlet light bordered">
                                        <div class="form-group">
                                            <div class="col-md-4" id="div_indirizzo">
                                                <label>Indirizzo</label><label type="text" style="color: red" >*</label>
                                                <input class="form-control" type="text" value="<%=lav.getResidenza_indirizzo()%>" id="indirizzo" name="indirizzo">
                                            </div>
                                        </div>
                                         <div class="form-group">
                                            <div class="col-md-4" id="div_prov">
                                                <label>Provincia</label><label type="text" style="color: red" >*</label>
                                                <select class="form-control select2-allow-clear" id="prov" name="prov" onchange="ch_sel();" style="width: 100%">
                                                    <option value="...">...</option>
                                                    <%for (String[] p : province) {
                                                            if (provres.equals(p[0])) {%>
                                                    <option selected value="<%=p[0]%>"><%=p[1]%></option>
                                                    <%} else {%>
                                                    <option value="<%=p[0]%>"><%=p[1]%></option>
                                                    <%}
                                                        }%>
                                                </select>
                                            </div>
                                            <div class="col-md-4" id="div_comune">
                                                <label>Comune</label><label type="text" style="color: red" >*</label>
                                                <select class="form-control select2-allow-clear" id="comune" name="comune" style="width: 100%">
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                        <br><label type="text" style="color: red" >* Campo Obbligatorio</label><br><br>
                                        <div class="form-group">
                                            <div class="col-md-4"> 
                                                <button type="submit" class="btn blue-soft btn-outline"><i class="fa fa-upload"></i> Modifica</button>                                            
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
            ch_sel();
        });
        </script>

    </body>
</html>
<%}
    }%>

