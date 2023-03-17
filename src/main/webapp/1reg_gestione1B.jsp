<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.seta.entity.Politica"%>
<%@page import="java.util.Map"%>
<%@page import="com.seta.entity.Rimborso"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.activity.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "reg_gestione1B.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            //Etichette et = new Etichette("IT");

            ArrayList<Rimborso> listrimborsi = Action.getListRimborsi("A01", "N");
            Map<String, String[]> adinfo = Action.get_ADInfos();

            ArrayList<Politica> politica = new ArrayList<Politica>();
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat fm2 = new SimpleDateFormat("dd-MM-yyyy");

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
            }

            function setIDDisactive(id) {
                document.getElementById('confirmdisactive').href = 'OperazioniRegional?type=8&idpolitica=' + id;
            }
            function setIDAccettaRimborsi(id) {
                document.getElementById('confirmactive').href = 'OperazioniRegional?type=9&idrimborso=' + id;
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

            });
        </script>      

        <script type="text/javascript">
            function setDate() {
                var to = document.getElementById("to").value;
                var from = document.getElementById("from").value;
                if (to != "" || from != "") {
                    document.getElementById("to").required = true;
                    document.getElementById("from").required = true;
                } else {
                    document.getElementById("to").required = false;
                    document.getElementById("from").required = false;
                }
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
                        <button type="button" class="btn dark btn-outline" data-dismiss="modal" onclick="return dismiss('large');">Chiudi</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade bs-modal-lg" id="rifiutamodal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header alert-danger">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Scarta Rimborso</h4>
                    </div>
                    <div class="modal-body" id="largetext">                       
                        <label class="text"> Vuoi veramente scartare questo rimborso? </label>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" data-dismiss="modal" onclick="return dismiss('rifiutamodal');">Chiudi</button>
                        <a class="btn btn-danger large " id="confirmdisactive" onclick="">Procedi</a> 
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade bs-modal-lg" id="accettamodal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header alert-success">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Accetta Rimborsi</h4>
                    </div>
                    <div class="modal-body" id="largetext">                       
                        <label class="text"> Sicuro di voler accettare tutti i rimborsi? </label>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" data-dismiss="modal" onclick="return dismiss('accettamodal');">Chiudi</button>
                        <a class="btn btn-success large " id="confirmactive" onclick="">Procedi</a> 
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade bs-modal-lg" id="scartamodal" tabindex="-1" role="dialog" aria-hidden="true">
            <%ArrayList<String[]> motivi = Action.getMotivi();%>
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header bg-blue  alert-dismissable ">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Scarta Singolo Rimborso</h4>
                    </div>

                    <div class="modal-body">                       
                        <label class="text">Specificare motivazione scarto </label><label style="color: red"> &nbsp;*</label>
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
                        <label style="color: red">*  &nbsp;Campo obbligatorio</label>
                    </div>

                    <div class="modal-footer">
                        <div class="form-group">                             
                            <div class="col-md-12"> 
                                <button type="button" class="btn btn-danger large " data-dismiss="modal" onclick="return dismiss('scartamodal');">Indietro</button>                    
                                <a class="btn btn-success large " id="conferma_scarta" onclick="return submitScarto();">Avanti</a>                                                                      
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>  
        <div class="modal fade bs-modal-lg" id="scartamodal2" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content ">
                    <div class="modal-header  bg-red-thunderbird " style="color:white;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Rigetta Rimborsi</h4>
                    </div>

                    <div class="modal-body">                       
                        <label class="text">Specificare motivazione scarto </label><label style="color: red"> &nbsp;*</label>
                        <input type="hidden" id="id_s2">
                        <div id="div_motivo12">
                            <select class="form-control select2-container--classic" id="motivo12" name="motivo12">
                                <%for (int i = 0; i < motivi.size(); i++) {%>
                                <option value="<%=motivi.get(i)[1]%>"><%=motivi.get(i)[1]%></option>
                                <%}%>
                            </select>
                        </div>
                        <br>
                        <div id="div_motivo22">
                            <textarea class="form-control" rows="4" style="resize: none; display: none;" id="motivo22" name="motivo22" placeholder="Motivazione"></textarea>
                        </div>
                        <label style="color: red">* &nbsp;Campo obbligatorio</label>
                    </div>

                    <div class="modal-footer">
                        <div class="form-group">                             
                            <div class="col-md-12"> 
                                <button type="button" class="btn btn-danger large " data-dismiss="modal" onclick="return dismiss('scartamodal2');">Indietro</button>                    
                                <a class="btn btn-success large " id="conferma_scarta2" onclick="return submitScarto2();">Avanti</a>                                                                      
                            </div>
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
            <%@ include file="menu_regional/menu_politica1B.jsp"%>
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
                    <h3 class="page-title"><strong> 1B </strong>  <small> GESTIONE </small> </h3>    
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
                    <div class="row">
                        <div class="col-md-12">

                            <%
                                String tipo = request.getParameter("tipo");
                                String from = request.getParameter("from");
                                String to = request.getParameter("to");
                                String titolo = request.getParameter("titolo");

                                if (tipo == null) {
                                    tipo = "..";
                                }
                                if (titolo == null) {
                                    titolo = "";
                                }
                                if (from == null) {
                                    from = "";
                                }
                                if (to == null) {
                                    to = "";
                                }

                            %>


                            <script type="text/javascript">
                                function submitScarto() {
                                    var motivo = document.getElementById("motivo").value;
                                    var motivo2 = document.getElementById("motivo2").value;

                                    if (motivo == ".." || motivo == "") {
                                        //alert(motivo mancante);
                                        $("#div_motivo").attr("class", "has-error");
                                        return false;

                                    } else if (motivo == "Altro") {
                                        if (motivo2 == "") {
                                            $("#div_motivo2").attr("class", "has-error");
                                            return false;
                                        } else {
                                            document.getElementById('conferma_scarta').href = "OperazioniRegional?type=7&motivo=" + encodeURIComponent(motivo2) + "&idpolitica=" + $('#id_s').val();
                                        }
                                    } else {
                                        document.getElementById('conferma_scarta').href = "OperazioniRegional?type=7&motivo=" + encodeURIComponent(motivo) + "&idpolitica=" + $('#id_s').val();
                                    }

                                }

                                function submitScarto2() {
                                    var motivo12 = document.getElementById("motivo12").value;
                                    var motivo22 = document.getElementById("motivo22").value;

                                    if (motivo12 == ".." || motivo12 == "") {
                                        //alert(motivo mancante);
                                        $("#div_motivo12").attr("class", "has-error");
                                        return false;

                                    } else if (motivo12 == "Altro") {
                                        if (motivo22 == "") {
                                            $("#div_motivo22").attr("class", "has-error");
                                            return false;
                                        } else {
                                            document.getElementById('conferma_scarta2').href = "OperazioniRegional?type=10&motivo=" + encodeURIComponent(motivo22) + "&idrimborso=" + $('#id_s2').val();
                                        }
                                    } else {
                                        document.getElementById('conferma_scarta2').href = "OperazioniRegional?type=10&motivo=" + encodeURIComponent(motivo12) + "&idrimborso=" + $('#id_s2').val();
                                    }

                                }




                            </script>    

                            <%for (int j = 0; j < listrimborsi.size(); j++) {
                                    int aprirow = j + 1;
                                    int chiudirow = 0;
                                    if (aprirow % 3 == 0) {
                                        chiudirow = 1;
                            %>
                            <div class="row">
                                <%}%>
                                <section class="col-lg-4 col-xs-6">
                                    <div class="portlet-body">
                                        <div class="mt-element-list">
                                            <div class="mt-list-head blue list-todo">
                                                <div class="list-head-title-container">
                                                    <h4 class="list-title bold pull-left">Rimborso <%=adinfo.get(listrimborsi.get(j).getIdrimborso())[0]%></h4><h4 class="list-title pull-right"><%=fm2.format(fm.parse(listrimborsi.get(j).getData_up()))%></h4><br>
                                                    <h5>AD/AU: <%=adinfo.get(listrimborsi.get(j).getIdrimborso())[1]%></h5>
                                                    <div class="list-head-count">
                                                        <div class="list-head-count-item">
                                                            <a href="OperazioniGeneral?type=5&path=<%=StringUtils.replace(adinfo.get(listrimborsi.get(j).getIdrimborso())[2], "\\", "/")%>" class="btn bg-blue-active"><i class="fa fa-file" style="color: white;"></i> Carta I. AD/AU</a> 
                                                            <a data-toggle="modal" href="#accettamodal" onclick="return setIDAccettaRimborsi(<%=listrimborsi.get(j).getIdrimborso()%>)" class="btn bg-green-jungle" style="color:white;"><i class="fa fa-check" style="color: white;"></i> Accetta</a>     
                                                            <a data-toggle="modal" href="#scartamodal2" onclick="return setScartaID2(<%=listrimborsi.get(j).getIdrimborso()%>)" class="btn bg-red-thunderbird" style="color:white;"><i class="fa fa-ban" style="color: white;"></i> Rigetta</a> 
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="mt-list-container list-default ext-1 group" style="border: #3598DC; padding: 0px;">
                                                <% politica = Action.getListPolitiche(listrimborsi.get(j).getIdrimborso());%>
                                                <a class="list-toggle-container font-white" data-toggle="collapse" href="#task<%=j%>" aria-expanded="false">

                                                    <div class="list-toggle done" style="background-color: #51A7E0;">
                                                        <%if (politica.size() == 0) {%>
                                                        Nessun rimborso presente
                                                        <%} else {%>
                                                        Elenco rimborsi
                                                        <%}%>
                                                        <span class="badge badge-default pull-right bg-white font-blue bold"><%=politica.size()%></span>
                                                    </div>
                                                </a>
                                                <div class="task-list panel-collapse collapse " id="task<%=j%>">
                                                    <ul>  
                                                        <%for (int i = 0; i < politica.size(); i++) {%>
                                                        <li class="mt-list-item" style="border-left: 5px solid;border-right: 1px solid;border-bottom: 1px solid;border-color: #51A7E0;">
                                                            <div class="list-datetime pull-left">
                                                                <a href="reg_showdocs.jsp?idpol=<%=politica.get(i).getId()%>" class="btn bg-blue-active bold fancybox fancyBoxRaf popovers" data-trigger="hover" data-placement="top" data-content="Visualizza documenti" style="color:white;"><i class="fa fa-file" style="color: white;"></i></a>  
                                                            </div>
                                                            <div class="list-datetime"> 
                                                                <a data-toggle="modal" href="#scartamodal" onclick="return setScartaID(<%=politica.get(i).getId()%>)" class="btn bg-yellow-gold " style="color:white;"><i class="fa fa-exclamation" style="color: white;"></i> Rigetta</a>  
                                                                <a data-toggle="modal" href="#rifiutamodal" onclick="return setIDDisactive(<%=politica.get(i).getId()%>)" class="btn bg-red-thunderbird " style="color:white;"><i class="fa fa-ban" style="color: white;"></i> Scarta</a>  
                                                            </div>
                                                            <div class="list-item-content">
                                                                <h3 class="bold">
                                                                    &nbsp;&nbsp;<%=politica.get(i).getCognome()%> <%=politica.get(i).getNome()%>
                                                                </h3>
                                                                <p>&nbsp;&nbsp;&nbsp;<%=politica.get(i).getCf()%>
                                                            </div>
                                                        </li>
                                                        <%}%>
                                                    </ul>                                                            
                                                </div>
                                            </div>
                                        </div>
                                    </div>             
                                </section>
                                <%if (chiudirow == 1) {
                                        chiudirow = 0;
                                %>
                            </div><br>
                            <%}
                                    aprirow += 1;
                                }%>
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
                                                                    $("#motivo").change(function () {
                                                                        if ($("#motivo").val() == "Altro") {
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
                                url: "QueryRegional?type=2&titolo=<%=request.getParameter("titolo")%>&tipo=<%=request.getParameter("tipo")%>&from=<%=request.getParameter("from")%>&to=<%=request.getParameter("to")%>",
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
                                {class: "allineacentro", targets: [10]},
                                {orderable: !1, targets: [0]},
                                {orderable: !1, targets: [1]},
                                {orderable: !1, targets: [9]}
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
//                            scrollY: '45vh',
                            pageLength: 25,
                            order: [],
                            dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"

                        });
                    };
                    jQuery().dataTable && dt2();
                }
                );
            </script>


    </body>
</html>

<%}%>
<%}%>