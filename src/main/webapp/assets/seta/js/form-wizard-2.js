/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function check_date() {
    var data_iniziale = document.getElementById("data_attivazione").value;
    var data_finale = document.getElementById("data_scadenza").value;
    var arr1 = data_iniziale.split("/");
    var arr2 = data_finale.split("/");
    var d1 = new Date(arr1[2], arr1[1] - 1, arr1[0]);
    var d2 = new Date(arr2[2], arr2[1] - 1, arr2[0]);
    var r1 = d1.getTime();
    var r2 = d2.getTime();
    if (r1 < r2) {
        return true;
    } else {
        //document.getElementById("risultato2").style.display = "";
        return false;
    }
}

function checktel(){
 
     var cel = document.getElementById("cellulare").value;
     
     return /^\d+$/.test(cel);
    
}



function today() {
    var d = new Date();
    var curr_date = d.getDate();
    if(curr_date<10){
        curr_date="0"+curr_date;
    }
    var curr_month = d.getMonth() + 1;
     if(curr_month<10){
        curr_month="0"+curr_month;
    }
    var curr_year = d.getFullYear();
    document.getElementById("data_attivazione").value = curr_date + "/" + curr_month + "/" + curr_year;
    document.getElementById("data_scadenza").value = curr_date + "/" + curr_month + "/" + curr_year;
}

function validazione_email(email) {
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    if (!reg.test(email))
        return false;
    else
        return true;
}


var FormWizard = function () {
    return{init: function () {
            function e(e) {}
            if (jQuery().bootstrapWizard) {
                $("#country_list").select2({placeholder: "Select", allowClear: !0, formatResult: e, width: "auto",
                    formatSelection: e, escapeMarkup: function (e) {
                        return e
                    }});
                var r = $("#submit_form"), t = $(".alert-danger", r),
                        i = $(".alert-success", r);
                r.validate({doNotHideMessage: !0, errorElement: "span",
                    errorClass: "help-block help-block-error", focusInvalid: !1,
                    rules: {giardino: {minlength: 0}, categoria: {minlength: 0, required: !0}, tipomandato: {required: !0}, datascad: {required: !0}, descrizione: {required: !0}, regione: {required: !0}, provincia: {required: !0}, nome: {required: !0}, cognome: {required: !0}, email: {required: !0}, link: {required: !0},mesi_attivazione:{required: !0}, filebanner: {minlength: 0, required: !0}, data_attivazione: {required: !0}, data_scadenza: {required: !0}, cellulare: {required: !0}, titolo: {required: !0}, statorogito: {required: !0}, totpiani: {minlength: 0, required: !0}, piano: {required: !0}, arredato: {required: !0}, ascensore: {required: !0}, indirizzo: {required: !0}, Comune: {required: !0}, provincia:{required: !0}, regione:{required: !0}, condizioni: {required: !0}, riscaldamento: {required: !0}, bagni: {required: !0}, locali: {required: !0}, prezzo: {minlength: 3, required: !0}, mq: {required: !0}, tipologia: {minlength: 0, required: !0}, contratto: {minlength: 0, required: !0}, clenergetica: {required: !0}},
                    invalidHandler: function (e, r) {
                        i.hide(), t.show(), App.scrollTo(t, -200)
                    },
                    highlight: function (e) {
                        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
                    },
                    unhighlight: function (e) {
                        $(e).closest(".form-group").removeClass("has-error")
                    },
                    success: function (e) {
                        "gender" == e.attr("for") || "payment[]" == e.attr("for") ? (e.closest(".form-group").removeClass("has-error").addClass("has-success"),
                                e.remove()) : e.addClass("valid").closest(".form-group").removeClass("has-error").addClass("has-success")
                    },
                    submitHandler: function (e) {
                        i.show(), t.hide()
                    }});
                var a = function () {
                    $("#tab5 .form-control-static", r).each(function () {
                        var e = $('[name="' + $(this).attr("data-display") + '"]', r);
                        if (e.is(":radio") && (e = $('[name="' + $(this).attr("data-display") + '"]:checked', r)), e.is(":text") || e.is("textarea"))
                            $(this).html(e.val());
                        else if (e.is("select"))
                            $(this).html(e.find("option:selected").text());
                        else if (e.is(":radio") && e.is(":checked"))
                            $(this).html(e.attr("data-title"));
                    })
                },
                        o = function (e, r, t) {
                            var i = r.find("li").length, o = t + 1;
                            $(".step-title", $("#form_wizard_1")).text("Step " + (t + 1) + " of " + i), jQuery("li", $("#form_wizard_1")).removeClass("done");
                            for (var n = r.find("li"), s = 0; t > s; s++)
                                jQuery(n[s]).addClass("done");
                            1 == o ? $("#form_wizard_1").find(".button-previous").hide() : $("#form_wizard_1").find(".button-previous").show(),
                                    o >= i ? ($("#form_wizard_1").find(".button-next").hide(), $("#form_wizard_1").find(".button-submit").show(), a()) : ($("#form_wizard_1").find(".button-next").show(),
                                    $("#form_wizard_1").find(".button-submit").hide()), App.scrollTo($(".page-title"))
                        };
                $("#form_wizard_1").bootstrapWizard({nextSelector: ".button-next", previousSelector: ".button-previous",
                    onTabClick: function (e, r, t, i) {
                        return!1
                    },
                    onNext: function (e, a, n) {

                          
                        if (n == 2) {                            
                            document.getElementById('emailRiep').innerHTML = document.getElementById('email').value;
                            document.getElementById('cellulareRiep').innerHTML = document.getElementById('cellulare').value;
                            
                            
                            var chktel= checktel();
                            if(!chktel){
                                 showmod1("large", "largetext", "Inserire un numero di telefono valido");
                                 return false;
                            }
                        }

                        if (document.getElementById('filebanner').value == '' && n == 3) {
                            showmod1("large", "largetext", "Occorre scegliere l'immagine del banner");
                            return false;
                        }

                        var ctrlDate = check_date();
                        if (!ctrlDate && n==3) {
                            showmod1("large", "largetext", "La data di scadenza deve essere successiva a quella di attivazione");
                            today();
                            return false;
                        }

//                        var ctrlMail = validazione_email(document.getElementById("email").value);
//                        if (!ctrlMail) {
//                            showmod1("large", "largetext", "La data di scadenza deve essere successiva a quella di attivazione");
//                            alert(5);
//                            return false;
//                        }
                        return i.hide(), t.hide(), 0 == r.valid() ? !1 : void o(e, a, n)
                    },
                    onPrevious: function (e, r, a) {
                        i.hide(), t.hide(), o(e, r, a)
                    },
                    onTabShow: function (e, r, t) {
                        var i = r.find("li").length, a = t + 1, o = a / i * 100;
                        $("#form_wizard_1").find(".progress-bar").css({width: o + "%"})
                    }}),
                        $("#form_wizard_1").find(".button-previous").hide(), $("#form_wizard_1 .button-submit").click(function () {}).hide(),
                        $("#country_list", r).change(function () {
                    r.validate().element($(this))
                })
            }
        }}
}();
jQuery(document).ready(function () {
    FormWizard.init()
});



                