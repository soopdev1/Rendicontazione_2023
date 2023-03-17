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
        if (!Action.isVisibile(stat, "show_politichelavoratore_rev.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
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
            function showmod(modal) {
                document.getElementById(modal).className = document.getElementById(modal).className + " in";
                document.getElementById(modal).style.display = "block";
            }

            function dismiss(modal) {
                document.getElementById(modal).className = "modal fade";
                document.getElementById(modal).style.display = "none";
            }

            function showStatoRimborso(domanda_rimborso, cod) {
                $.get('OperazioniRevisore?type=19&cod=' + cod + '&idrimborso=' + domanda_rimborso, function (response) {
                    if (response == "S") {
                        response = "<a data-toggle='modal' class='popovers btn btn-default purple-sharp fa fa-gavel' style='cursor: default; display: block;'> <font face='verdana'>Pronto ad essere protocollato</font></a>";
                    } else if (response == "N") {
                        response = "<a data-toggle='modal' class='popovers btn btn-default purple-sharp fa fa-gavel' style='cursor: default; display: block;'> <font face='verdana'>Protocollato</font></a>";
                    } else if (response == "R") {
                        response = "<a data-toggle='modal' class='popovers btn btn-default yellow-soft fa fa-spinner' style='cursor: default; display: block;'> <font color='white' face='verdana'>In attesa di verifica da parte del revisore</font></a>";
                    } else if (response == "E2") {
                        response = "<a data-toggle='modal' class='popovers btn btn-default yellow-gold fa fa-pencil-square-o' style='cursor: default; display: block;'> <font color='white' face='verdana'>Errore sanabile riscontrato dal revisore</font></a>";
                    } else if (response == "E1") {
                        response = "<a data-toggle='modal' class='popovers btn btn-default yellow-gold fa fa-pencil-square-o' style='cursor: default; display: block;'> <font color='white' face='verdana'>Errore sanabile riscontrato dalla regione</font></a>";
                    } else if (response == "R2") {
                        response = "<a data-toggle='modal' class='popovers btn btn-default yellow-soft fa fa-spinner' style='cursor: default; display: block;'> <font color='white' face='verdana'>In attesa di liquidazione tramite decreto</font></a>";
                    } else if (response == "P") {
                        response = "<a data-toggle='modal' class='popovers btn btn-default green-jungle fa fa-money block' style='cursor: default; display: block;'> <font color='white' face='verdana'>Rimborso liquidato tramite decreto</font></a>";
                    }
                    $('#statorimb').html(response);
                    $('#title_statorimb').html("Stato Rimborso");
                    showmod('showmodal');
                });
            }

            function showEnte(cf) {
                $.get('OperazioniRevisore?type=20&cf=' + cf, function (response) {

                    response = "<h3>" + response + "</h3>";

                    $('#statorimb').html(response);
                    $('#title_statorimb').html("Ragione Sociale Ente Promotore");
                    showmod('showmodal');
                });
            }
        </script>
    </head>
    <body  style="background-color: white;">
        <!--BEGIN HEADER -->
        <div class="page-container" style="background-color: white;">



            <div class="modal fade bs-modal-lg" id="showmodal" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content ">
                        <div class="modal-header bg-blue">
                            <button type="button" class="close" onclick="return dismiss('showmodal');" ></button>
                            <h4 class="modal-title" id="title_statorimb"></h4>
                        </div>

                        <div class="modal-body" id="statorimb">     
                        </div>
                        <div class="modal-footer">
                            <div class="form-group">                             
                                <div class="col-md-12"> 
                                    <button type="button" class="btn btn-danger large " data-dismiss="modal" onclick="return dismiss('showmodal');">Chiudi</button>                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>    

            <!-- BEGIN CONTENT BODY -->
            <!--div class="portlet-title">
                   <div class="caption">
                       <span class="caption-subject font-blue bold">
                           <h3 class="page-title"><strong>  </strong><small> RIMBORSI DESTINATARI</small> </h3>                                    
                       </span>
                   </div>
               </div-->
            <!-- BEGIN PORTLET-->
            <div class="portlet box purple-seance">
                <div class="portlet-title">
                    <div class="caption">
                        <a class="fa fa-list-ul white tooltips" style="cursor: default;color: white;"></a>
                        <span class="caption-subject font-white bold ">Politiche </span>
                        <span class="caption-subject font-white "></span>
                    </div>
                    <div class="tools">
                    </div>
                </div>
                <div class="portlet-body">
                    <table width="100%" class="table table-responsive table-bordered table-scrollable dataTable no-footerr" id="sample_0"> 
                        <thead>
                            <tr>
                                <th><center>Politica</center> </th>
                        <th><center>Data Avvio</center> </th>
                        <th><center>Data Fine</center> </th>
                        <th><center>CF Ente</center> </th>
                        <th><center>Stato Politica</center> </th>
                        <th><center>Stato Rimborso</center> </th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                        <tfoot hidden>
                            <tr>
                                <th><input type="text" class="form-control tf1" name="dest1" placeholder="..."></th>
                                <th><input type="text" class="form-control tf1" name="dest1" placeholder="..."></th>
                                <th><input type="text" class="form-control tf1" name="dest1" placeholder="..."></th>
                                <th><input type="text" class="form-control tf1" name="dest1" placeholder="..."></th>
                                <th><input type="text" class="form-control tf1" name="dest1" placeholder="..."></th>
                                <th><input type="text" class="form-control tf1" name="dest1" placeholder="..."></th>
                            </tr>
                        </tfoot>
                    </table>
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
                                                        height: 450,
                                                        width: 900,
                                                        paginate: {previous: "Precedente", next: "Successivo", last: "Ultimo", first: "Primo"}},
                                                    processing: true,
                                                    ajax: {
                                                        url: "QueryRevisore?type=22&idlav=<%=request.getParameter("idlav")%>",
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
                                                        {orderable: !1, targets: [5]},
                                                        {searchable: !1, targets: [5]},
                                                        {orderable: !1, targets: [2]},
                                                        {searchable: !1, targets: [2]},
                                                        {orderable: !1, targets: [1]},
                                                        {searchable: !1, targets: [1]},
                                                    ],
                                                    buttons: [
                                                        {extend: "excel", className: "btn white btn-outline", text: "<i class='fa fa-file-excel-o'></i> Excel"},
                                                        {extend: "colvis", className: "btn white btn-outline", text: "Columns"}]
                                                    ,
                                                    colReorder: {reorderCallback: function () {
                                                            console.log("callback");
                                                        }},
                                                    lengthMenu: [
                                                        [12],
                                                        [12]
                                                    ],
                                                    //scrollY: 'true',
                                                    scrollX: 'true',
                                                    pageLength: 12,
                                                    order: [],
                                                    dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                                                });
                                                $(".tf1").keyup(function () {
                                                    g.fnFilter(this.value, g.oApi._fnVisibleToColumnIndex(
                                                            g.fnSettings(), $(".tf1").index(this)));
                                                });
                                                $(".tf1").each(function (i) {
                                                    this.initVal = this.value;
                                                });
                                                $(".tf1").focus(function () {
                                                    if (this.className === "form-control")
                                                    {
                                                        this.className = "form-control";
                                                        this.value = "";
                                                    }
                                                });
                                                $(".tf1").blur(function (i) {
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
    </body>
</html>
<%}
    }%>

