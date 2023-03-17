<%@page import="com.seta.activity.Action"%>
<%if(Action.getPath("maintenance").equals("Y")){
    response.sendRedirect("NoService.html");
} else {
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta name="robots" content="noindex">
        <meta charset="ISO-8859-1" />
        <title>Rendicontazione</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link rel="shortcut icon" href="assets/seta/img/favicon.ico">
        <link href="assets/seta/fontg/fontsgoogle1.css" rel="stylesheet" type="text/css" /> 
        <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN PAGE LEVEL STYLES -->
        <link href="assets/pages/css/login-2.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <!-- END THEME LAYOUT STYLES -->
    </head>
    <!-- END HEAD -->
    <body class=" login"  >
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

        <input name=esito hidden="true">
        <!-- BEGIN LOGO -->
        <div class="logo" style="padding-bottom: 0px;margin-bottom: 15px;border-bottom-width: 15px;">
            <center><img src="assets/seta/img/logo.png" alt="" class="center logo" width="250px" style="margin-bottom: 0px;padding-bottom: 0px;padding-right: 0px;padding-left: 0px;padding-top: 0px;margin-top: 0px;"/></center>
        </div>
        <!-- END LOGO -->
        <!-- BEGIN LOGIN -->
        <div class="content">
            <!-- BEGIN LOGIN FORM -->
            <% if (((String) request.getParameter("esito")) == null) {
                    
            %>            
            <form class="login-form" action="Login?type=1" method="post">
                <h3 class="form-title font-white">Accedi</h3>
                <div class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span> Inserisci Username e Password </span>
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username" />
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="password" />
                </div>
                <div class="form-group">
                    <center>
                        <button type="submit" class="btn uppercase font-white">LOGIN<i class="icon-key"></i></button>
                    </center>
                    <br>
                    <center> <a href="javascript:;" id="forget-password" class="center"> Password Dimenticata?</a> </center>
                </div>
            </form>
            <form class="forget-form" action="Login?type=3" method="post" >

                <div class="form-group">
                    <h4 class="font-white">Password Dimenticata?</h4>
                    <h5 class="font-white">Inserisci Username ed Email per continuare</h5>
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username"  />
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Email</label>
                    <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email" id="email" />
                </div>
                <div class="form-group">
                    <a href="login.jsp" type="button" id="back-btn" class="btn btn-outline grey-cascade uppercase" style="width: 150px;"><i class="fa fa-arrow-left"></i> Indietro</a>
                    <button type="submit" class="btn uppercase pull-right font-white"  style="width: 150px;"> Avanti <i class="fa fa-arrow-right"></i></button>
                </div>
            </form>
            <% } else if (((String) request.getParameter("esito")).equals("banned")) {%>
            <h3 class="font-white" >Accesso Negato</h3>
            <p>  </p>
            <div class="alert alert-danger">
                <span>Non possiedi i permessi necessari per accedere a questo Sito</span>
            </div>
            <div class="form-group"><center>
                    <a href="login.jsp" type="button" id="back-btn" class="btn btn-outline grey-cascade uppercase"><i class="fa fa-arrow-left"></i> Indietro</a>
                </center>
            </div>

            <% } else if (((String) request.getParameter("esito")).equals("KO1")) {%>
            <form class="login-form" action="<%= "Login?type=1"%>" method="post">
                <h3 class="form-title font-white">Accedi</h3>
                <div class="alert alert-danger">
                    <button class="close" data-close="alert"></button>
                    <span> Password o Username Sbagliati: <br> Prova Nuovamente o clicca Password Dimenticata per resettarla </span>
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username" />
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="password" />
                </div>
                <div class="form-group">
                    <center>
                        <button type="submit" class="btn  uppercase font-white">Login <i class="icon-key"></i></button>
                    </center>
                    <br>
                    <center> <a href="javascript:;" id="forget-password" class="center">Password Dimenticata?</a> </center>
                </div>
            </form>
            <!-- END LOGIN FORM -->
            <!-- BEGIN FORGOT PASSWORD FORM -->
            <!-- <form class="forget-form" action="index.html" method="post"> -->
            <form class="forget-form" action="<%="Login?type=3"%>" method="post" >
                <div class="form-group">
                    <h4 class="font-white">Password Dimenticata?</h4>
                    <h5 class="font-white">Inserisci Username ed Email per continuare</h5>
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username"  />
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Email</label>
                    <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email" id="email"/>
                </div>
                <div class="form-group">
                    <a href="login.jsp" type="button" id="back-btn" class="btn btn-outline grey-cascade uppercase"><i class="fa fa-arrow-left"></i>Indietro</a>
                    <button type="submit" class="btn  uppercase pull-right font-white"  style="width: 150px;">Avanti<i class="fa fa-arrow-right"></i></button>
                </div>
            </form>
            <% } else if (((String) request.getParameter("esito")).equals("OK")) {
                String cred = request.getParameter("state");
                if (cred == null) {
                    cred = "";
                }
            %>
            <h3 class="font-white">LOGIN</h3>
            <br>
            <form class="login-form" action="<%= "Login?type=1"%>" method="post">
                <%if (cred.equals("NEWCRED")) {%>
                <div class="alert alert-success">
                    <button class="close" data-close="alert"></button>
                    <span> Password cambiata con successo. Accedi con le nuove credenziali.</span>
                </div>
                <%} else if (cred.equals("")) {%>
                <div class="alert alert-success">
                    <button class="close" data-close="alert"></button>
                    <span> Reset Password avvenuto con Successo. Controlla la tua Email per proseguire. </span>
                </div>
                <%}%>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username" />
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="password" />
                </div>
                <div class="form-group">
                    <center>
                        <button type="submit" class="btn  uppercase font-white">Login<i class="icon-key"></i></button>
                    </center>
                    <br>
                    <center> <a href="javascript:;" id="forget-password" class="center">Password Dimenticata?</a> </center>
                </div>
            </form>
            <!-- END LOGIN FORM -->
            <!-- BEGIN FORGOT PASSWORD FORM -->
            <!-- <form class="forget-form" action="index.html" method="post"> -->
            <form class="forget-form" action="<%= "Login?type=3"%>" method="post" >
                <div class="form-group">
                    <h4 class="font-white">Password Dimenticata?</h4>
                    <h5 class="font-white">Inserisci Username ed Email per continuare</h5>
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <input class="form-control form-control-solid  placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username" />
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Email</label>
                    <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email" id="email" />
                </div>
                <div class="form-group">
                    <a href="login.jsp" type="button" id="back-btn" class="btn btn-outline grey-cascade uppercase"><i class="icon-arrow-left"></i>Indietro</a>
                    <button type="submit" class="btn uppercase pull-right font-white" style="width: 150px;">Avanti <i class="icon-arrow-right"></i></button>
                </div>
            </form>
            <% } else if (((String) request.getParameter("esito")).equals("FIRSTACCESS")) {
                String state = request.getParameter("state");
            %>
            <form class="login-form" action="<%= "Login?type=4"%>" method="post">
                <%if (state.equals("0")) {%>
                <div class="alert alert-danger">
                    <button class="close" data-close="alert"></button>
                    <span><i class="fa fa-info-circle" ></i> Primo Accesso: <br> Cambia la Password</span>
                </div>
                <%} else if (state.equals("2")) {%>
                <div class="alert alert-danger">
                    <button class="close" data-close="alert"></button>
                    <span> <i class="fa fa-info-circle"  ></i> Reset Password</span><br>
                    <span> La nuova password deve essere composta da almeno 8 caratteri una lettera maiuscola, una lettera minuscola,un numero e un carattere speciale<br>!@#$%&*()_+=|<>?{}\[]~-</span>
                </div>
                <%} else if (state.equals("2PASS")) {%>
                <div class="alert alert-danger">
                    <button class="close" data-close="alert"></button>
                    <span> <i class="fa fa-info-circle"  ></i> Le Password non corrispondono. Prova Nuovamente</span>
                </div>
                <%} else if (state.equals("EPASS")) {%>
                <div class="alert alert-danger">
                    <button class="close" data-close="alert"></button>
                    <span><i class="fa fa-info-circle"  ></i> Errore durante il Cambio Password. Prova Nuovamente </span>
                </div>
                <%} else if (state.equals("PASS")) {%>
                <div class="alert alert-danger">
                    <button class="close" data-close="alert"></button>
                    <span> <i class="fa fa-info-circle" style="cursor:pointer;" title="Requisiti Nuova Password: \n - lunghezza (> 7 caratteri); \n - almeno una lettera maiuscola (A,B,...); \n - almeno una lettera minuscola (a,b,...); \n - almeno un numero (0-9); \n - almeno un carattere speciale (@,#,...)">
                        </i> Formato Password non Valido. Prova Nuovamente </span> 
                </div>
                <%}%>
                <div class="form-group">
                    <label class="info text-muted font-white">Password Attuale</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Password Attuale" name="actualpsw" /> 
                </div>
                <div class="form-group">
                    <label class="info text-muted font-white">Nuova Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Nuova Password" name="newpsw" /> 
                </div>
                <div class="form-group">
                    <label class="info text-muted font-white">Conferma Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Conferma Password" name="newpsw1" /> 
                </div>
                <div class="form-group">

                    <a href="login.jsp" type="button" id="back-btn" class="btn btn-outline grey-cascade uppercase"><i class="icon-arrow-left"></i> Indietro </a>
                    <button type="submit" class="btn uppercase pull-right font-white"> Cambia Password <i class="icon-arrow-right"></i></button>
                </div>
            </form>
            <% } else if (((String) request.getParameter("esito")).equals("KO3")) {%>
            <form class="login-form" action="<%= "Login?type=3"%>" method="post">
                <div class="alert alert-danger">
                    <button class="close" data-close="alert"></button>
                    <span>Password o Username Sbagliati. Prova Nuovamente</span>
                </div>
                <div class="form-group">
                    <h4 class="font-white">Password Dimenticata?</h4>
                    <h5 class="font-white">Inserisci Username ed Email per continuare</h5>
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <input class="form-control form-control-solid  placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username" />
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Email</label>
                    <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email" id="email" />
                </div>
                <div class="form-group">
                    <a href="login.jsp" type="button" id="back-btn" class="btn btn-outline grey-cascade uppercase"><i class="icon-arrow-left"></i> Indietro</a>
                    <button type="submit" class="btn uppercase pull-right font-white"  style="width: 150px;">Avanti <i class="icon-arrow-right"></i></button>
                </div>
            </form>
            <%}%>
            <!-- END FORGOT PASSWORD FORM -->
            <!-- BEGIN REGISTRATION FORM -->
            <!-- END REGISTRATION FORM -->
        </div>
        <div class="copyright"> Rendicontazione v 1.0</div>
        <!--[if lt IE 9]>
    <![endif]-->
        <!-- BEGIN CORE PLUGINS -->
        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="assets/pages/scripts/login.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->
    </body>
</html>
<%}%>