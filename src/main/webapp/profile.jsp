
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.seta.entity.Operator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.seta.activity.Action"%>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!new Action().isVisibile(stat, "profile.jsp")) {
            response.sendRedirect("index.jsp");
        } else {
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat fm2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
%>
<!DOCTYPE html>
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
        <%if (statu == 2) {%>
        <link href="assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <%} else if (statu == 1) {%>
        <link href="assets/layouts/layout/css/themes/blue.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <%} else if (statu == 3) {%>
        <link href="assets/layouts/layout/css/themes/grey.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <%}%>
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />

        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/seta/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/seta/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/seta/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/seta/js/fancy.js"></script> 

        <link rel="stylesheet" href="AdminLTE-2.4.2/dist/css/AdminLTE.css">
        <link rel="shortcut icon" href="assets/seta/img/favicon.ico" /><link rel="shortcut icon" href="favicon.ico" />

        <script type="text/javascript">
            function submitfor(nameform) {
                document.forms[nameform].submit();
            }
        </script>

        <%
            String lan_index = (String) session.getAttribute("language");
            lan_index = "IT";
            Etichette et_index = new Etichette(lan_index);
            int iduser = (int) session.getAttribute("id_operatore");
            Operator operator = Action.Operatore(iduser);
        %>

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

            function isNumber(evt) {
                evt = (evt) ? evt : window.event;
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                return true;
            }

            function ctrlFormPassword() {
                var oldPassword = document.getElementById("oldPassword").value;
                var newPassword = document.getElementById("newPassword").value;
                var newPassword2 = document.getElementById("newPassword2").value;
                if (oldPassword === "") {
                    showmod1("large", "largetext", "Il campo Password Attuale non può essere vuoto");
                    return false;
                }
                if (newPassword === "") {
                    showmod1("large", "largetext", "Il campo 'Nuova Passwor' non può essere vuoto");
                    return false;
                }
                if (newPassword2 === "") {
                    showmod1("large", "largetext", "Il campo 'Conferma Passwor' non può essere vuoto");
                    return false;
                }
                if (newPassword != newPassword2) {
                    showmod1("large", "largetext", "<%=et_index.getPswdontmatch()%>");
                    return false;
                }
                return true;
            }

            function ctrlForm() {
                var name = $("#name").val();
                var surname = $("#surname").val();
                var email = $("#email").val();
                var phone = $("#phone_number").val();

                if (name == '<%=operator.getNome()%>' && surname == '<%=operator.getCognome()%>' && email == '<%=operator.getEmail()%>' && (phone == '<%=operator.getTelefono()%>' || phone == '')) {
                    alert("prova");
                    return false;
                }

                var err = false;

                if (name = "") {
                    $("#div_name").attr("class", "form-group has-error");
                    err = true;
                } else {
                    $("#div_name").attr("class", "form-group has-success");
                }
                if (surname = "") {
                    $("#div_surname").attr("class", "form-group has-error");
                    err = true;
                } else {
                    $("#div_surname").attr("class", "form-group has-success");
                }
                if (email = "") {
                    $("#div_email").attr("class", "form-group has-error");
                    err = true;
                } else {
                    $("#div_email").attr("class", "form-group has-success");
                }
                if (phone = "" || phone.length < 6) {
                    $("#div_phone").attr("class", "form-group has-error");
                    err = true;
                } else {
                    $("#div_phone").attr("class", "form-group has-success");
                }

                if (err) {
                    return false;
                }
                return true;
            }
        </script>


    </head>

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">

        <div class="modal fade bs-modal-lg" id="large" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header alert-danger  alert-dismissible">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title"><%=et_index.getAttention()%></h4>
                    </div>
                    <div class="modal-body" id="largetext">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" data-dismiss="modal" onclick="return dismiss('large');"><%=et_index.getChiudi()%></button>
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

            <%if (statu == 1) {%>
            <%@ include file="menu_regional/menu_home.jsp"%>
            <%} else if (statu == 2) {%>
            <%@ include file="menu_ente/menu_home.jsp"%>
            <%} else if (statu == 3) {%>
            <%@ include file="menu_revisore/menu_home.jsp"%>
            <%}%>
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">


                    <%
                        String esitopass = request.getParameter("esitopass");
                        String esitoedit = request.getParameter("esitoedit");

                        if (esitopass == null) {
                            esitopass = "";
                        }
                        if (esitoedit == null) {
                            esitoedit = "";
                        }
                        if (esitopass.equals("OK")) {
                    %>
                    <div class="alert alert-success " style="text-align: center;">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong><%=et_index.getPassok()%></strong>  </span> 
                    </div>
                    <%
                    } else if (esitopass.equals("updateFail")) {
                    %>
                    <div class="alert alert-error alert-dismissible " style="text-align: center;">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong><%=et_index.getPassko()%></strong>  </span> 
                    </div>
                    <%
                    } else if (esitopass.equals("passErr")) {
                    %>
                    <div class="alert alert-error alert-dismissible " style="text-align: center;">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong><%=et_index.getPasserr()%></strong>  </span> 
                    </div>
                    <%
                        }
                        if (esitoedit.equals("OK")) {%>
                    <div class="alert alert-success " style="text-align: center;">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Dati aggiornati con successo.</strong>  </span> 
                    </div>
                    <%} else if (esitoedit.equals("KO")) {%>
                    <div class="alert alert-danger " style="text-align: center;">
                        <button type="button" class="close" data-close="alert"></button>
                        <span><strong>Errore. Dati non aggiornati.</strong>  </span> 
                    </div>
                    <%}%>

                    <div class="logo">
                        <img src="assets/seta/img/logos1.png" height="75"  align="right" /> 
                    </div>  
                    <h3 class="page-title"><strong><i class="fa fa-home"></i> HOME </strong><small>PROFILO </small> </h3>
                    <div class="tab-pane" id="tab_1_3">
                        <div class="row profile-account">
                            <div class="col-md-4">
                                <ul class="ver-inline-menu tabbable margin-bottom-10">
                                    <li class="active">
                                        <a data-toggle="tab" href="#tab_1-1" id="a_t_1">
                                            <i class="fa fa-cog"></i> Informazioni Personali </a>
                                        <span class="after"> </span>
                                    </li>
                                    <li>
                                        <a data-toggle="tab" href="#tab_2-2" id="a_t_2">
                                            <i class="fa fa-lock"></i> <%=et_index.getCambiapsw()%>
                                        </a>
                                    </li>
                                </ul>
                                <div class="alert bg-yellow-crusta" id="t_pwd" style="display: none;" >
                                    Formato nuova password: <br>
                                    <br>- lunghezza (>7 caratteri);
                                    <br>- almeno un carattere maiuscolo (A,B,...);
                                    <br>- almeno un carattere minuscoso (a,b,...);
                                    <br>- almeno un numero (0-9);
                                    <br>- almeno un carattere speciale (@,#,...)
                                </div> 
                            </div>
                            <div class="col-md-8">
                                <div class="tab-content">
                                    <div id="tab_1-1" class="tab-pane active">
                                        <form class="form-horizontal" action="<%="OperazioniGeneral?type=3"%>" method="post" name="form" accept-charset="ISO-8859-1" onsubmit="return ctrlForm();"  >


                                            <div class="form-group" id="div_name">
                                                <label class="control-label"><%=et_index.getOp_fname()%></label>
                                                <input  type="text" class="form-control"  placeholder="" id="name"  name="name" value="<%=operator.getNome()%>"/> 

                                            </div>
                                            <div class="form-group" id="div_surname">
                                                <label class="control-label"><%=et_index.getOp_lname()%></label>
                                                <input   type="text" class="form-control"  placeholder="" id="surname"  name="surname" value="<%=operator.getCognome()%>"/> 
                                            </div>

                                            <div class="form-group" id="div_email">
                                                <label class="control-label"><%=et_index.getOp_email()%></label>
                                                <input class="form-control"  type="email" id="email" name="email" value="<%=operator.getEmail()%>" /> 
                                            </div>
                                            <%String tel = "";
                                                if (!operator.getTelefono().equals("-")) {
                                                    tel = operator.getTelefono();
                                                }%>
                                            <div class="form-group" id="div_phone">
                                                <label class="control-label"><%=et_index.getOp_phone()%></label>
                                                <input type="tel" class="form-control" onkeypress="return isNumber(event)" placeholder="" id="phone_number"  name="phone_number" maxlength="10" value="<%=tel%>"/> 
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label"><%=et_index.getOp_username()%></label>
                                                <input readonly type="text" class="form-control" placeholder="" id="username"  name="username" value="<%=operator.getUsername()%>" disabled/> 
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Data Registrazione</label>
                                                <input readonly class="form-control"  type="text" id="datareg" name="datareg" value="<%=fm2.format(fm.parse(operator.getDataregistrazione()))%>" /> 
                                            </div>

                                            <div class="form-group">
                                                <button type="submit" class="btn blue btn-outline" id="salva"  style="display:  none;"><i class="fa fa-check"></i> Salva </button>
                                            </div>
                                        </form>
                                    </div>

                                    <div id="tab_2-2" class="tab-pane">
                                        <form role="form" method="post" action="<%="OperazioniGeneral?type=2"%>" onsubmit="return ctrlFormPassword();">
                                            <input type="hidden" name="email" value="<%=operator.getEmail()%>" />
                                            <div class="form-group">
                                                <label class="control-label"><%=et_index.getActualpsw()%></label>
                                                <input type="password" class="form-control" name="oldPassword" id="oldPassword"/> 
                                            </div>
                                            <div class="form-group" id="div_newPassword">
                                                <label class="control-label"><%=et_index.getNewpsw()%></label>
                                                <input type="password" class="form-control" name="newPassword" id="newPassword"/>
                                                <a id="a_newPassword" class="btn red active" style="cursor: default; display: none;">
                                                    <i class="fa fa-exclamation-triangle"></i> Password non conforme! 
                                                </a>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label"><%=et_index.getNewpsw1()%></label>
                                                <input type="password" class="form-control" name="newPassword2" id="newPassword2"/> </div>
                                            <div class="margin-top-10">
                                                <button type="submit" class="btn blue btn-outline"><i class="fa fa-check"></i> <%=et_index.getAvanti_log()%></button>
                                            </div>
                                        </form>
                                    </div>

                                </div>
                            </div>
                            <!--end col-md-9-->
                        </div>
                    </div>

                </div>       
            </div>
            <div class="page-footer">
                <div class="page-footer-inner">  Rendicontazione v 1.0</div>
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

            <script>
                                            $("#a_t_1").click(function () {
                                                $("#t_pwd").css("display", "none");
                                            });
                                            $("#a_t_2").click(function () {
                                                $("#t_pwd").css("display", "block");
                                            });


                                            $("#name").change(function () {
                                                if ($("#name").val() != '<%=operator.getNome()%>' || $("#surname").val() != '<%=operator.getCognome()%>' || $("#email").val() != '<%=operator.getEmail()%>' || ($("#phone_number").val() != '<%=operator.getTelefono()%>' && $("#phone_number").val() != '')) {
                                                    $("#salva").css("display", "block");
                                                } else {
                                                    $("#salva").css("display", "none");
                                                }
                                            });
                                            $("#surname").change(function () {
                                                if ($("#name").val() != '<%=operator.getNome()%>' || $("#surname").val() != '<%=operator.getCognome()%>' || $("#email").val() != '<%=operator.getEmail()%>' || ($("#phone_number").val() != '<%=operator.getTelefono()%>' && $("#phone_number").val() != '')) {
                                                    $("#salva").css("display", "block");
                                                } else {
                                                    $("#salva").css("display", "none");
                                                }
                                            });
                                            $("#email").change(function () {
                                                if ($("#name").val() != '<%=operator.getNome()%>' || $("#surname").val() != '<%=operator.getCognome()%>' || $("#email").val() != '<%=operator.getEmail()%>' || ($("#phone_number").val() != '<%=operator.getTelefono()%>' && $("#phone_number").val() != '')) {
                                                    $("#salva").css("display", "block");
                                                } else {
                                                    $("#salva").css("display", "none");
                                                }
                                            });
                                            $("#phone_number").change(function () {
                                                if ($("#name").val() != '<%=operator.getNome()%>' || $("#surname").val() != '<%=operator.getCognome()%>' || $("#email").val() != '<%=operator.getEmail()%>' || ($("#phone_number").val() != '<%=operator.getTelefono()%>' && $("#phone_number").val() != '')) {
                                                    $("#salva").css("display", "block");
                                                } else {
                                                    $("#salva").css("display", "none");
                                                }
                                            });

                                            $("#newPassword").change(function () {
                                                $.get("OperazioniGeneral?type=4&pwd=" + $("#newPassword").val(), function (response) {
                                                    if (response == "OK") {
                                                        $("#a_newPassword").css("display", "block");
                                                        $("#div_newPassword").attr("class", "form-group has-error");
                                                    } else {
                                                        $("#div_newPassword").attr("class", "form-group has-success");
                                                        $("#a_newPassword").css("display", "none");
                                                    }
                                                });
                                            });
            </script>

    </body>
</html>
<%}%>
<%}%>