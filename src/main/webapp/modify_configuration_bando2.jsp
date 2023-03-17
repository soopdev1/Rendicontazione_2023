<%@page import="java.util.Map"%>
<%@page import="com.seta.entity.Bando"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.activity.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "modify_configuration_bando.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            Bando b = Action.getBandoById((String) request.getParameter("idbando"));
            ArrayList<String[]> valori = Action.getBudgetsPolitiche(b.getIdbando(), (String) request.getParameter("tipo"));
            double budget_stanziato = 0;
            for (int j = 0; j < valori.size(); j++) {
                budget_stanziato += Double.valueOf(valori.get(j)[2]);
            }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta charset="ISO-8859-1" />
        <title>Rendicontazione</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
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
        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />

        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->

        <link href="assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />

        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/themes/grey.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />

        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/seta/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/seta/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/seta/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/seta/js/fancy.js"></script>

        <link rel="stylesheet" href="AdminLTE-2.4.2/dist/css/AdminLTE.min.css">
        <link rel="shortcut icon" href="assets/seta/img/favicon.ico" />

    </head>

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

        function ctrlForm() {
            var somma = Number(0);
            var budgettot = $("#budgettot").val();
            var err = false;

        <%for (int j = 0; j < valori.size(); j++) {%>
            var provvisorio = $("#budget<%=valori.get(j)[0]%>").val() + "." + $("#centesimi<%=valori.get(j)[0]%>").val();
            //var bp = $("#budgetp<%=valori.get(j)[0]%>").val().replace(",", ".");
            var differenza = $("#differenza<%=valori.get(j)[0]%>").val().replace(",", ".");
            if (Number(provvisorio) <= Number(differenza)) {
                $("#div_budget<%=valori.get(j)[0]%>").attr("class", "col-md-4 has-error");
                $("#div_centesimi<%=valori.get(j)[0]%>").attr("class", "col-md-1 has-error");
                $("#div_budgetp<%=valori.get(j)[0]%>").attr("class", "col-md-2 has-warning");
                $("#msg_<%=valori.get(j)[0]%>").css("display", "block");
                window.scrollTo(0,0);
                err = true;
            } else {
                $("#msg_<%=valori.get(j)[0]%>").css("display", "none");
            }
            if (provvisorio != "." && provvisorio != "0.00") {
                somma += Number(provvisorio);
            } else {
                somma += Number($("#budget<%=valori.get(j)[0]%>").val());
            }
        <%}%>
            if (((somma * 10) / 10) > Number(budgettot)) {
        <%for (int j = 0; j < valori.size(); j++) {%>
                if ($("#budget<%=valori.get(j)[0]%>").val() != "") {
                    $("#div_budget<%=valori.get(j)[0]%>").attr("class", "col-md-4 has-error ");
                    $("#div_centesimi<%=valori.get(j)[0]%>").attr("class", "col-md-1 has-error");
                }
        <%}%>
                document.getElementById("div_somma").style.display = "block";
                err = true;
            }
            if (err) {
                return false;
            }
            return true;
        }
    </script>

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
            });
        });
    </script>

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
        <div class="page-container">
            <div class="page-content-wrapper" >
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">                    
                    <div class="clearfix"></div>
                    <div class="row">
                        <div class="portlet light bordered ">

                            <div class="alert bg-red-thunderbird  alert-dismissible " id="div_somma" style="text-align: center;color: white; display: none;">
                                <button type="button" class="close" data-close="alert"></button>
                                <span><strong>Attenzione, la somma dei budgets è maggiore del budget totale stanziato.</strong></span>            
                            </div>

                            <% String esitomod = request.getParameter("esitomod");
                                if (esitomod == null) {
                                    esitomod = "";
                                }

                                if (esitomod.equals("KO")) {%>           
                            <br>
                            <div class="alert bg-red-thunderbird  alert-dismissible " style="text-align: center;color: white">
                                <button type="button" class="close" data-close="alert"></button>
                                <span><strong>Errore, la configurazione del bando non è andata a buon fine! Riprovare</strong></span>            
                            </div>
                            <%} else if (esitomod.equals("OK")) {%> 
                            <br>
                            <div class="alert bg-green-jungle alert-dismissible " style="text-align: center;color: white">
                                <button type="button" class="close" data-close="alert"></button>
                                <span><strong>Bando configurato con successo!</strong></span>
                            </div>
                            <%}
                                if (esitomod.equals("")) {%>

                            <div class="portlet-title">
                                <div class="caption">
                                    <span class="caption-subject font-blue bold">
                                        <h3 class="page-title"><strong style="color:#3598DC;"><%=b.getTitolo()%></strong> </h3>                                    
                                    </span>
                                </div>
                                <div class="caption pull-right">
                                    <span class="caption-subject font-blue bold pull-right">
                                        <h3 class="page-title pull-right"><strong style="color:#3598DC;"> Budget totale stanziato: &#8364; <%=String.format("%1$,.2f", Double.parseDouble(b.getBudget()))%></strong> </h3> 
                                        <br> <h3 class="page-title pull-right"><strong style="color:#E87E04;"> Budget già stanziato: &#8364; <%=String.format("%1$,.2f", budget_stanziato)%></strong> </h3>

                                    </span>
                                </div>
                            </div>  
                            <div class="portlet-body form">
                                <form class="form-horizontal" action="<%="OperazioniRegional?type=4"%>" method="post" enctype="multipart/form-data" onsubmit="return ctrlForm();" >
                                    <div class="form-body">
                                        <%for (int i = 0; i < valori.size(); i++) {
                                                String budget = valori.get(i)[2].substring(0, valori.get(i)[2].lastIndexOf("."));
                                                String cents = valori.get(i)[2].substring(valori.get(i)[2].lastIndexOf(".") + 1);
                                                if (!cents.substring(0, 0).equals("0") && cents.length() == 1) {
                                                    cents += "0";
                                                } else if (cents.length() < 2) {
                                                    cents = "00";
                                                }   
                                                String budgetp = valori.get(i)[3].substring(0, valori.get(i)[3].lastIndexOf("."));
                                                String centsp = valori.get(i)[3].substring(valori.get(i)[3].lastIndexOf(".") + 1);
                                                if (centsp.length() == 1) {
                                                    centsp += "0";
                                                }
                                                double diff = Double.parseDouble(valori.get(i)[2]) - Double.parseDouble(valori.get(i)[3]);
                                        %>
                                        <div class="portlet light bordered" style="color:#3598DC;">
                                            <div class="form-group">
                                                <input type="hidden" name="budgettot" id="budgettot" value="<%=b.getBudget()%>"/>
                                                <input type="hidden" name="idbando" value="<%=b.getIdbando()%>"/>
                                                <input type="hidden" name="tipo" value="<%=(String) request.getParameter("tipo")%>"/>
                                                <input type="hidden" name="flag" value="<%=b.getFlag_sportello()%>"/>
                                                <input type="hidden" name="differenza<%=valori.get(i)[0]%>" id="differenza<%=valori.get(i)[0]%>" value="<%=diff%>" >
                                                <input type="hidden" name="budgetold<%=valori.get(i)[0]%>" id="budgetold<%=valori.get(i)[0]%>" value="<%=valori.get(i)[2]%>" >
                                                <div class="col-md-3" >
                                                    <label>Politica <%=valori.get(i)[0]%></label>
                                                    <input class="form-control bold" style="background-color: #FFFFFF; border-color:#FFFFFF;" readonly type="text" id="politica" name="politica" value="<%=valori.get(i)[1]%>">
                                                </div>
                                                <!--div class="col-md-4 pull-right" id="div_budget<%=valori.get(i)[0]%>">
                                                    <label>Budget da stanziare</label>
                                                    <input class="form-control" type="number" id="budget<%=valori.get(i)[0]%>" name="budget<%=valori.get(i)[0]%>" value="<%=valori.get(i)[2]%>" min="0" step="1">
                                                </div-->
                                                <div class="col-md-2 "  id="div_budgetp<%=valori.get(i)[0]%>">
                                                    <label>Budget previsionale</label>
                                                    <div class="input-group"> 
                                                        <span class="input-group-addon" style="background-color: #eef1f5;">€</span>
                                                        <input class="form-control" readonly style="direction: rtl;" name="budgetp<%=valori.get(i)[0]%>" id="budgetp<%=valori.get(i)[0]%>" type="text" value="<%=budgetp + "," + centsp%>" >
                                                    </div>
                                                </div>    
                                                <div class="col-md-7 pull-right">
                                                    <div class="col-md-6">&nbsp;
                                                    </div>
                                                    <div class="col-md-4" id="div_budget<%=valori.get(i)[0]%>" style=" padding-right: 0px; width: 25%;">
                                                        <label>Budget da stanziare</label><label type="text" style="color: red" >&nbsp;*</label>
                                                        <div class="input-group"> 
                                                            <span class="input-group-addon" style="background-color: #eef1f5;">€</span>
                                                            <input class="form-control" style="direction: rtl;" type="number" id="budget<%=valori.get(i)[0]%>" name="budget<%=valori.get(i)[0]%>" value="<%=budget%>" placeholder="0000"></div>
                                                    </div>
                                                    <div class="col-md-1" style=" padding-left: 5px;padding-right: 10px; width: 1%; font-size: 20px;">  <label>&nbsp;</label><br>
                                                        ,
                                                    </div>
                                                    <div class="col-md-1 " id="div_centesimi<%=valori.get(i)[0]%>" style=" padding-left: 0px; width: 11%; ">
                                                        <label>&nbsp;</label>
                                                        <input class="form-control quantity" type="number" id="centesimi<%=valori.get(i)[0]%>" name="centesimi<%=valori.get(i)[0]%>" value="<%=cents%>" min="0" max="99" placeholder="00" step="1" >
                                                    </div>
                                                </div>    
                                                <div class="col-md-3"></div>
                                                <div class="col-md-9 pull-right" id="msg_<%=valori.get(i)[0]%>" style="color: #D91E18; display: none;">
                                                    <i class="fa fa-exclamation-triangle" ></i> Il budget da stanziare deve essere maggiore o uguale di € <b><%=String.format("%1$,.2f",diff)%></b> (differenza tra budget attuale e budget previsionale)
                                                </div> 
                                            </div>
                                        
                                        </div>
                                        <%}%>
                                        <!--div class="form-group ">
                                            <div class="col-md-4">
                                                <label>Campo obbligatorio</label> <label type="text" style="color: red" >*</label>  
                                            </div>
                                        </div-->
                                        <div class="form-group">
                                            <div class="col-md-4"> 
                                                <button type="submit" class="btn blue btn-outline"><i class="fa fa-pencil-square-o"></i> Conferma</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <%}%>

                        </div>
                    </div>
                </div>
            </div> 
        </div>                                
        <!-- BEGIN CORE PLUGINS -->
        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>

        <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>

        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="assets/seta/js/select2.full.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
        <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>

        <script src="assets/pages/scripts/components-select2.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
        <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
        <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>



        <!-- date-range-picker -->
        <script src="AdminLTE-2.4.2/bower_components/moment/min/moment.min.js"></script>
        <script src="AdminLTE-2.4.2/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
        <!-- bootstrap datepicker -->
        <script src="AdminLTE-2.4.2/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
        <!--bootstrap color picker -->
        <script src="AdminLTE-2.4.2/bower_components/bootstrap-colorpicker/dist/js/bootstrap-colorpicker.min.js"></script>

        <!--iCheck 1.0.1-->
        <script src="AdminLTE-2.4.2/plugins/iCheck/icheck.min.js"></script>





    </body>

</html>
<%}
    }%>


