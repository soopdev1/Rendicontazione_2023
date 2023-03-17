<%@page import="java.io.File"%>
<%@page import="java.text.SimpleDateFormat"%>
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
        if (!Action.isVisibile(stat, "modify_bando.jsp")) {
            response.sendRedirect("page_403.html");
        } else {
            ArrayList<String[]> tipoBando = Action.getList_TipoBando();

            Bando b = Action.getBandoById((String) request.getParameter("idbando"));
            SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
            String tot = Action.getBudgetStanziato((String) request.getParameter("idbando"));
            if (tot == null) {
                tot = "0";
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
        <link rel="shortcut icon" href="favicon.ico" />

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
            var titolo = $("#titolo").val();
            var totale = $("#totale").val();
            var decreto = $("#decreto").val();
            var datainizio = $("#datainizio").val();
            var tipo = $("#tipo").val();
            var budget = $("#budget").val();
            var datafine = $("#datafine").val();
            var flag_sportello = document.getElementById("flag_sportello").checked;
            var err = false;
            var nomefile = $("#file").val().split('\\').pop();
            var estensione = nomefile.substring(nomefile.lastIndexOf(".")).toLowerCase();

            if (nomefile != "") {
                if (estensione != ".pdf" || estensione != ".PDF") {
                    $("#div_file").attr("class", "input-group input-large has-error");
                    $("#a_file").css("display", "block");
                    err = true;
                } else {
                    $("#div_file").attr("class", "input-group input-large has-success");
                    $("#a_file").css("display", "none");
                }
            }


            if (titolo == "") {
                $("#div_titolo").attr("class", "col-md-4 has-error");
                err = true;
            } else {
                $("#div_titolo").attr("class", "col-md-4 has-success");
            }

            if (datainizio == "") {
                $("#div_datainizio").attr("class", "col-md-3 has-error");
                err = true;
            } else {
                $("#div_datainizio").attr("class", "col-md-3 has-success");
            }

            if (decreto == "") {
                $("#div_decreto").attr("class", "col-md-3 has-error");
                err = true;
            } else {
                $("#div_decreto").attr("class", "col-md-3 has-success");
            }

            if (tipo == "0" || tipo == null) {
                $("#div_tipo").attr("class", "col-md-3 has-error");
                err = true;
            } else {
                $("#div_tipo").attr("class", "col-md-3 has-success");
            }

            if (budget == "") {
                $("#div_budget").attr("class", "col-md-3 has-error");
                $("#div_centesimi").attr("class", "col-md-1 has-error");
                err = true;
            } else {
                $("#div_budget").attr("class", "col-md-3 has-success");
                $("#div_centesimi").attr("class", "col-md-1 has-success");
            }

            if (totale != "0" && (Number(budget) < Number(totale))) {
                document.getElementById("div_somma").style.display = "block";
                $("#div_budget").attr("class", "col-md-3 has-error");
                $("#div_centesimi").attr("class", "col-md-1 has-error");
                err = true;
            } else {
                $("#div_budget").attr("class", "col-md-3 has-success");
                $("#div_centesimi").attr("class", "col-md-1 has-success");
            }

            if (flag_sportello == false) {

                if (datafine == "") {
                    $("#div_datafine").attr("class", "col-md-3 has-error");
                    err = true;
                } else {
                    $("#div_datafine").attr("class", "col-md-3 has-success");
                }
            }

            if (flag_sportello == false && datafine != "") {
                var stryear1 = datainizio.substring(6, 10);
                var strmth1 = datainizio.substring(3, 5);
                var strday1 = datainizio.substring(0, 2);
                var date1 = new Date(stryear1, strmth1, strday1);

                var stryear2 = datafine.substring(6, 10);
                var strmth2 = datafine.substring(3, 5);
                var strday2 = datafine.substring(0, 2);
                var date2 = new Date(stryear2, strmth2, strday2);

                if (date2 < date1) {
                    //alert("Start date must be prior to end date");
                    $("#div_datainizio").attr("class", "col-md-3 has-warning");
                    $("#div_datafine").attr("class", "col-md-3 has-warning");
                    err = true;
                } else {
                    $("#div_datainizio").attr("class", "col-md-3 has-success");
                    $("#div_datafine").attr("class", "col-md-3 has-success");
                }
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

        function mostradatafine() {

            var flag_sportello = document.getElementById("flag_sportello").checked;
            if (flag_sportello === false) {
                document.getElementById('div_datafine').style.display = "block";
            } else {
                document.getElementById('div_datafine').style.display = "none";
                document.getElementById("datafine").value = "";
            }
        }


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
                            <div class="portlet-title">
                                <div class="caption">
                                    <span class="caption-subject font-blue bold">
                                        <h3 class="page-title"><strong> Modifica bando: </strong><small> <%=b.getTitolo()%></small> </h3>                                    
                                    </span>
                                </div>
                                <div class="caption pull-right">
                                    <span class="caption-subject font-blue bold pull-right">
                                        <% String budg_vis = String.format("%1$,.2f", Double.parseDouble(tot));
                                            if (budg_vis.startsWith("0")) {%>
                                        <h3 class="page-title pull-right"><strong> Budget non ancora stanziato </strong> </h3> 
                                        <%} else {%>
                                        <h3 class="page-title pull-right"><strong> Budget già stanziato: &#8364; <%=budg_vis%></strong> </h3> 

                                        <%}%>                          
                                    </span>
                                </div>
                            </div>                    
                            <div class="alert bg-red-thunderbird  alert-dismissible " id="div_somma" style="text-align: center;color: white; display: none;">
                                <button type="button" class="close" data-close="alert"></button>
                                <span><strong>Attenzione, il valore del budget inserito è minore del budget già stanziato.</strong></span>            
                            </div>        

                            <% String esitomod = request.getParameter("esitomod");
                                String check = "";
                                if (esitomod == null) {
                                    esitomod = "";
                                }

                                if (esitomod.equals("KO")) {%>           
                            <br>
                            <div class="alert bg-red-thunderbird  alert-dismissible " style="text-align: center;color: white">
                                <button type="button" class="close" data-close="alert"></button>
                                <span><strong>Errore, il bando non è stato modificato! Riprovare</strong></span>            
                            </div>
                            <%} else if (esitomod.equals("OK")) {%> 
                            <br>
                            <div class="alert bg-green-jungle alert-dismissible " style="text-align: center;color: white">
                                <button type="button" class="close" data-close="alert"></button>
                                <span><strong>Bando modificato con successo!</strong></span>
                            </div>
                            <%}%>
                            <div class="portlet-body form">
                                <form class="form-horizontal" action="<%="OperazioniRegional?type=2"%>" method="post" enctype="multipart/form-data" onsubmit="return ctrlForm();" >
                                    <div class="form-body">
                                        <div class="form-group">
                                            <input type="hidden" name="totale" id="totale" value="<%=tot%>"/>
                                            <input type="hidden" name="old_path" value="<%=b.getPath()%>"/>
                                            <input type="hidden" name="idbando" value="<%=b.getIdbando()%>"/>
                                            <%
                                                String budget = b.getBudget().substring(0, b.getBudget().lastIndexOf("."));
                                                String cents = b.getBudget().substring(b.getBudget().lastIndexOf(".") + 1);
                                                //if (cents.equals("0")) {
                                                //    cents = "00";
                                                //}
                                                if (cents.length() == 1) {
                                                    cents += "0";
                                                }
                                                String budgetp = b.getBudget_previsione().substring(0, b.getBudget_previsione().lastIndexOf("."));
                                                String centsp = b.getBudget_previsione().substring(b.getBudget_previsione().lastIndexOf(".") + 1);
                                                //if (centsp.equals("0")) {
                                                //    centsp = "00";
                                                //}
                                                if (centsp.length() == 1) {
                                                    centsp += "0";
                                                }
                                                String budgeta = b.getBudget_attuale().substring(0, b.getBudget_attuale().lastIndexOf("."));
                                                String centsa = b.getBudget_attuale().substring(b.getBudget_attuale().lastIndexOf(".") + 1);
                                                //if (centsa.equals("0")) {
                                                //    centsa = "00";
                                                //}
                                                if (centsa.length() == 1) {
                                                    centsa += "0";
                                                }
                                            %>
                                            <input type="hidden" name="budget_old" value="<%=budget + "," + cents%>"/>
                                            <div class="col-md-4" id="div_titolo">
                                                <label>Titolo</label><label type="text" style="color: red" >*</label>
                                                <input class="form-control" type="text" id="titolo" name="titolo" value="<%=b.getTitolo()%>">
                                            </div>

                                            <div class="col-md-3" id="div_tipo">
                                                <label>Tipo Bando</label><label type="text" style="color: red" >*</label>
                                                <select class="form-control select2-allow-clear" data-placeholder="" name="tipo" id="tipo" style="width:100%">
                                                    <% for (int j = 0; j < tipoBando.size(); j++) {
                                                            if (b.getTipo_bando().equals(tipoBando.get(j)[0])) {%>
                                                    <option value="<%=tipoBando.get(j)[0]%>" selected><%=tipoBando.get(j)[1]%></option>                                                                                                                     
                                                    <%} else {%>
                                                    <option value="<%=tipoBando.get(j)[0]%>"><%=tipoBando.get(j)[1]%></option>    
                                                    <%}
                                                        }%>
                                                </select>
                                            </div>

                                            <%String msg = "";
                                                if (!b.getPath().equals("-")) {
                                                    msg = "caricato: " + b.getPath().substring(b.getPath().lastIndexOf(File.separator) + 1);
                                                }%>     
                                            <div class="col-md-4" id="div_file">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <label>File  <%=msg%></label>
                                                    <div class="input-group input-large" >
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
                                                    <div class="col-md-8 pull-left" style="padding-left: 0px; padding-right: 23px;">
                                                        <a id="a_file" class="btn btn-default btn-block red active" style="cursor: default; display: none;">
                                                            <i class="fa fa-exclamation-triangle"></i> Selezionare un file PDF
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>    
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-2" id="div_budget" style=" padding-right: 0px; width: 24%;">
                                                <label>Budget</label><label type="text" style="color: red" >*</label>
                                                <div class="input-group"> 
                                                    <span class="input-group-addon" style="background-color: #eef1f5;">€</span>
                                                    <input class="form-control" style="direction: rtl;" type="number" id="budget" value="<%=budget%>" name="budget" step="1000" placeholder="0000"></div>
                                            </div>
                                            <div class="col-md-1" style=" padding-left: 5px;padding-right: 0px; width: 1%; font-size: 20px;">  <label>&nbsp;</label><br>
                                                ,
                                            </div>
                                            <div class="col-md-1" id="div_centesimi" style=" padding-left: 0px; width: 8%; ">
                                                <label>&nbsp;</label>
                                                <input class="form-control" type="number" id="centesimi" name="centesimi" value="<%=cents%>" min="0" max="99" step="1" >
                                            </div>
                                            <div class="col-md-2" id="div_budgetprev" style=" padding-right: 0px; width: 24%;">
                                                <label>Budget Previsionale</label>
                                                <div class="input-group"> 
                                                    <span class="input-group-addon" style="background-color: #eef1f5;">€</span>
                                                    <input class="form-control" readonly style="direction: rtl;" name="budgetp" id="budgetp" type="text" value="<%=budgetp + "," + centsp%>" ></div>
                                            </div>
                                            <div class="col-md-2" id="div_budgetatt" style=" padding-right: 0px; width: 24%; padding-left: 30px;">
                                                <label>Budget Attuale</label>
                                                <div class="input-group"> 
                                                    <span class="input-group-addon" style="background-color: #eef1f5;">€</span>
                                                    <input class="form-control" readonly style="direction: rtl;" name="budgeta" id="budgeta" type="text" value="<%=budgeta + "," + centsa%>" ></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-3" id="div_datainizio">
                                                <label>Data Inizio</label><label type="text" style="color: red" >*</label>
                                                <div class="input-group input-medium date date-picker" style="width: 100%!important;" data-date-format="dd/mm/yyyy" >
                                                    <input type="text" class="form-control" id="datainizio" name="datainizio" value="<%=fm.format(fm2.parse(b.getData_inizio()))%>">
                                                    <span class="input-group-btn">
                                                        <button class="btn default" type="button">
                                                            <i class="fa fa-calendar"></i>
                                                        </button>
                                                    </span>
                                                </div>
                                                <!--span class="help-block"> Data Inizio </span-->
                                            </div>
                                            <%String vis = "style='display: block;'";
                                                if (b.getFlag_sportello().equals("Y")) {
                                                    check = "checked";
                                                    vis = "style='display: none;'";
                                                }%>        
                                            <div class="col-md-1" id="div_sportello" style=" padding-left: 0px;"> 
                                                <label>Sportello</label><br/>
                                                <input type="checkbox" <%=check%> class="make-switch form-control" name="flag_sportello" id="flag_sportello" onchange="return mostradatafine();" data-on-text="<i class='fa fa-check'></i>" data-off-text="<i class='fa fa-times'></i>" >
                                            </div>

                                            <div class="col-md-3" id="div_decreto" style="padding-left: 7px" >
                                                <label>Numero Decreto</label><label type="text" style="color: red" >*</label>
                                                <input class="form-control" type="text" id="decreto" name="decreto" value="<%=b.getDecreto()%>" maxlength="45">
                                            </div>

                                        </div>
                                        <%  String datafine = b.getData_fine();
                                            if (datafine == null || datafine.equals("0000-00-00")) {
                                                datafine = "";
                                            } else {
                                                datafine = fm.format(fm2.parse(b.getData_fine()));
                                            }
                                        %>            
                                        <div class="form-group ">       
                                            <div class="col-md-3" <%=vis%> id="div_datafine">
                                                <label>Data Fine</label><label type="text" style="color: red" >*</label>
                                                <div class="input-group input-medium date date-picker" style="width: 100%!important;" data-date-format="dd/mm/yyyy" >
                                                    <input type="text" class="form-control" id="datafine" name="datafine" value="<%=datafine%>">
                                                    <span class="input-group-btn">
                                                        <button class="btn default" type="button">
                                                            <i class="fa fa-calendar"></i>
                                                        </button>
                                                    </span>
                                                </div>
                                                <!--span class="help-block"> Data Inizio </span-->
                                            </div>        
                                        </div>      
                                        <div class="form-group ">
                                            <div class="col-md-4">
                                                <label type="text" style="color: red" >* Campo Obbligatorio</label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-4"> 
                                                <button type="submit" class="btn blue btn-outline"><i class="fa fa-pencil-square-o"></i> Modifica</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
        </div>   

        <script type="text/javascript">
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


