<%@page import="java.util.HashMap"%>
<%@page import="com.seta.entity.Bando"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.activity.Action"%>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    } else {
        int statu = (int) session.getAttribute("tipo");
        String stat = String.valueOf(statu);
        if (!Action.isVisibile(stat, "bandoReport.jsp")) {
            response.sendRedirect("page_403.html");
        } else {%>
<%if (request.getParameter("reload") == null) {%>
<script>
    window.onload = function () {
    setTimeout(function(){
    location.href = "bandoReport.jsp?idbando=<%=request.getParameter("idbando")%>&reload=y";
    }, 300);
    };</script>
    <%} else {
        //Etichette et = new Etichette("IT");
        String id = request.getParameter("idbando");
        Bando b = Action.getBandoById(id);
        HashMap<String, String> d_pol = Action.getDecodificaPolitiche();
        ArrayList<String[]> valori = Action.getBudgetsPolitiche(b.getIdbando(), b.getTipo_bando());

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

        <style>
            .visual{
                padding-top: 35px!important;
                padding-left: 30px!important;
            }
            .dashboard-stat .visual > i {
                color: #b0b1b2!important;
                opacity: .5!important;
                font-size: 200px;
            }
        </style>
    </head>


    <body class="page-full-width page-content-white">
        <div class="page-container">
            <div class="page-content-wrapper" >
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">                    
                    <div class="clearfix"></div>
                    <h3 class="page-title"><strong> REPORT </strong><small> BUDGET</small> </h3><br>

                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                        <a class="dashboard-stat dashboard-stat-v2 blue" href="#" style="cursor: default;">
                            <div class="visual">
                                <i class="fa fa-euro"></i>
                            </div>
                            <div class="details">
                                <div class="number">
                                    <span data-counter="counterup" data-value="1349">&euro; <%=String.format("%1$,.2f", Double.parseDouble(b.getBudget()))%></span>
                                </div>
                                <div class="desc"><h4><strong>Budget Stanziato</strong></h4></div>
                            </div>
                        </a>
                        <div class="portlet box blue-soft">
                            <div class="portlet-title">
                                <div class="caption">
                                    <i class="fa fa-pie-chart font-white" style="font-size: 25px;"></i>
                                    <span class="caption-subject font-white bold ">Suddivisione Budget Politiche</span>
                                </div>
                            </div>
                            <div class="portlet-body form ">
                                <div id="canvas-holder" style="width:110%">
                                    <canvas id="chart-area"></canvas>
                                </div>
                            </div>                            
                        </div>
                    </div>
                    <div class="col-lg-8 col-md-8 col-sm-6 col-xs-12">                        
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <a class="dashboard-stat dashboard-stat-v2 yellow-gold" style="cursor: default;">
                                <div class="visual">
                                    <i class="fa fa-euro"></i>
                                </div>
                                <div class="details">
                                    <div class="number">
                                        <span data-counter="counterup" data-value="1349">&euro; <%=String.format("%1$,.2f", Double.parseDouble(b.getBudget_attuale()))%></span>
                                    </div>
                                    <div class="desc"><h4><strong>Budget Attuale</strong></h4></div>
                                </div>
                            </a>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <a class="dashboard-stat dashboard-stat-v2 red-flamingo" style="cursor: default;">
                                <div class="visual">
                                    <i class="fa fa-euro"></i>
                                </div>
                                <div class="details">
                                    <div class="number">
                                        <span data-counter="counterup" data-value="1349">&euro; <%=String.format("%1$,.2f", Double.parseDouble(b.getBudget_previsione()))%></span>
                                    </div>
                                    <div class="desc"><h4><strong>Budget Previsionale</strong></h4></div>
                                </div>
                            </a>                            
                        </div>
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="portlet box blue-soft">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart font-white" style="font-size: 25px;"></i>
                                        <span class="caption-subject font-white bold ">Budget Per Singola Politica</span>
                                    </div>
                                </div>
                                <div class="portlet-body form ">
                                    <div id="container" style="width: 93%;">
                                        <canvas id="canvas"></canvas>
                                    </div>
                                </div>                            
                            </div>

                        </div>
                    </div>
                    <div class="clearfix"></div>
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

        <script src="Chart/Chart.bundle.js"></script>
        <script src="Chart/utils.js"></script>


        <script>

    var config = {
    type: 'doughnut',
            data: {
            datasets: [{
            data: [
            <%for (String[] s : valori) {%>
            '<%=s[2]%>',
            <%}%>
            ],
                    backgroundColor: [
                            window.chartColors.red,
                            window.chartColors.orange,
                            window.chartColors.green,
                            window.chartColors.purple,
                            window.chartColors.blue,
                            window.chartColors.red,
                            window.chartColors.orange,
                            window.chartColors.green,
                            window.chartColors.purple,
                            window.chartColors.blue,
                    ],
                    label: 'Dataset 1'
            }],
                    labels: [
            <%for (String[] s : valori) {%>
                    '<%=d_pol.get(s[0])%>',
            <%}%>
                    ]
            },
            options: {
            tooltips: {
            mode: 'nearest',
            },
                    responsive: true,
                    legend: {
                    position: 'left',
                    },
                    title: {
                    display: false,
                            text: 'Chart.js Doughnut Chart'
                    },
                    animation: {
                    animateScale: true,
                            animateRotate: true,
                            easing:'easeOutElastic',
                            duration: 1800,
                    }
            }
    };
        </script>

        <script>

            var color = Chart.helpers.color;
            var horizontalBarChartData = {
            labels: [
            <%for (String[] s : valori) {%>
            '<%=d_pol.get(s[0])%>',
            <%}%>
            ],
                    datasets: [
//                        {
//                    label: 'Stanziato',
//                            backgroundColor: window.chartColors.blue,
//                            data: [
//            <%for (String[] s : valori) {%>
//                            '<%=s[2]%>',
//            <%}%>
//                            ]
//                    }, 
                    {
                    label: 'Attuale',
                            backgroundColor: window.chartColors.orange,
                            data: [
            <%for (String[] s : valori) {%>
                            '<%=s[4]%>',
            <%}%>
                            ]
                    }, {
                    label: 'Previsionale',
                            backgroundColor: window.chartColors.red,
                            data: [
            <%for (String[] s : valori) {%>
                            '<%=s[3]%>',
            <%}%>
                            ]
                    }]

            };
            window.onload = function () {
            var ctx = document.getElementById('canvas').getContext('2d'); //inizializzo grafico a barre hor
            window.myHorizontalBar = new Chart(ctx, {
            type: 'horizontalBar',
                    data: horizontalBarChartData,
                    options: {
                    // Elements options apply to all of the options unless overridden in a dataset
                    // In this case, we are setting the border of each horizontal bar to be 2px wide
                    elements: {
                    rectangle: {
                    borderWidth: 0,
                            barPercentage: 0.2,
                    },
                    },
                            tooltips: {
                            mode: 'nearest',
                            },
                            animation: {
                            easing:'easeOutElastic',
                                    duration: 1800,
                            },
                            responsive: true,
                            legend: {
                            position: 'top',
                            },
                            title: {
                            display: false,
                                    text: 'Chart.js Horizontal Bar Chart'
                            },
                            scales: {
                            xAxes: [{
                            ticks: {
                            beginAtZero:true
                            }
                            }]
                            }
                    }
            });
            ctx = document.getElementById('chart-area').getContext('2d'); //inizializzo secondo grafico
            window.myDoughnut = new Chart(ctx, config);
            };
            Chart.plugins.register({
            afterDatasetsDraw: function(chart) {
            var ctx = chart.ctx;
            chart.data.datasets.forEach(function(dataset, i) {
            var meta = chart.getDatasetMeta(i);
            if (!meta.hidden) {
            meta.data.forEach(function(element, index) {
            // Draw the text in black, with the specified font
            ctx.fillStyle = 'rgb(60, 60, 60)';
            var fontSize = 15;
            var fontStyle = 'bold';
            var fontFamily = 'sans-serif';
            ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
            // Just naively convert to string for now
            var dataString = currencyFormat(Number(dataset.data[index].toString()));
            // Make sure alignment settings are correct
            ctx.textAlign = 'lefth';
            ctx.textBaseline = 'middle';
            var padding = - 8;
            var position = element.tooltipPosition();
            ctx.fillText(dataString, position.x / 1.09, position.y - (fontSize / 2) - padding);
            });
            }
            });
            }
            });
            
            function currencyFormat(num) {
            return num.toFixed(2).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
            }
        </script>


    </body>
</html>
<%}
        }
    }%>