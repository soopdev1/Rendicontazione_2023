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
        if (!Action.isVisibile(stat, "reg_protocolloD5DT.jsp")) {
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
        </script>

        <script type="text/javascript">

            $(".fancyBoxRaf").fancybox({
                prevEffect: 'none',
                nextEffect: 'none',
                closeBtn: true,
                type: 'iframe',
                centerOnScroll: true,
                width: '1200',
                overlayOpacity: 0,
                overlayShow: true,
                afterClose: function () {// USE THIS IT IS YOUR ANSWER THE KEY WORD IS "afterClose"            
                    //parent.location.reload(true);
                    //location.reload();            
                    location.href = 'reg_protocolloD5DT.jsp';
                }
            });


            function ctrlForm() {
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
        <!-- BEGIN HEADER -->
        <%@ include file="menu/header.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu_regional/menu_politicaD5.jsp"%>
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
                    <h3 class="page-title" style="color:#2D5F8B"><strong> D5 </strong>  <small style="color:#2D5F8B"> CARICA PROTOCOLLO </small> </h3>    
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

                    <div class="portlet box red-flamingo">
                        <div class="portlet-title">
                            <div class="caption">  
                                <i class="fa fa-search font-white"></i>
                                <span class="caption-subject font-white bold ">Cerca</span>
                            </div>
                        </div>
                        <div class="portlet-body form">
                            <form class="form-horizontal" action="reg_protocolloD5DT.jsp" method="post" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlForm();">
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
                                            <button type="submit" class="btn blue-soft btn-outline"><i class="fa fa-search"></i> Cerca</button>                                            
                                            <a href="reg_protocolloD5DT.jsp" class="btn red-thunderbird btn-outline"><i class="fa fa-close"></i> Reset</a>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <label style="color: #F7470C;">** Visualizzati solo i primi 25 risultati, per la visualizzazione completa effettuare una ricerca</label>
                    <div class="row">
                        <div class="col-md-12">
                            <!-- BEGIN PORTLET-->   

                            <div class="portlet box blue-soft">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <span class="fa fa-folder-open font-white tooltips"></span>
                                        <span class="caption-subject font-white bold ">Rimborsi da protocollare</span>
                                    </div>
                                    <div class="tools"> 
                                    </div>
                                </div>                               
                                <div class="portlet-body">                                  
                                    <table class="table table-bordered" id="sample_0" width="100%">
                                        <thead>
                                            <tr>
                                                <th>Azione</th>
                                                <th>Ente</th>
                                                <th>AD/AU</th>
                                                <th>Politica</th>
                                                <th>Data Domanda Rimborso</th>
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
                                                url: "QueryRegional?type=3&politica=D5&ente=<%=ente%>&from=<%=from%>&to=<%=to%>",
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
                                                {orderable: !1, targets: [0]},
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