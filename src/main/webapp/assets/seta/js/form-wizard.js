var FormWizard = function(){return{init:function(){function e(e){}if (jQuery().bootstrapWizard){$("#country_list").select2({placeholder:"Select", allowClear:!0, formatResult:e, width:"auto",
        formatSelection:e, escapeMarkup:function(e){return e}}); var r = $("#submit_form"), t = $(".alert-danger", r),
        i = $(".alert-success", r); r.validate({doNotHideMessage:!0, errorElement:"span",
        errorClass:"help-block help-block-error", focusInvalid:!1,
        rules:{giardino:{minlength:0}, categoria:{minlength:0, required:!0}, tipomandato:{required:!0}, datascad:{minlength:10,required:!0}, descrizione:{required:!0}, titolo:{required:!0}, statorogito:{required:!0}, totpiani:{minlength:0, required:!0}, piano:{required:!0}, arredato:{required:!0}, ascensore:{required:!0}, indirizzo:{required:!0}, Comune:{required:!0}, provincia:{required:!0}, regione:{required:!0}, condizioni:{required:!0}, riscaldamento:{required:!0}, bagni:{required:!0}, locali:{required:!0}, prezzo:{minlength:3, required:!0}, mq:{required:!0}, tipologia:{minlength:0, required:!0}, contratto:{minlength:0, required:!0}, clenergetica:{required:!0}},
        invalidHandler:function(e, r){i.hide(), t.show(), App.scrollTo(t, - 200)},
        highlight:function(e){$(e).closest(".form-group").removeClass("has-success").addClass("has-error")},
        unhighlight:function(e){$(e).closest(".form-group").removeClass("has-error")},
        success:function(e){"gender" == e.attr("for") || "payment[]" == e.attr("for")?(e.closest(".form-group").removeClass("has-error").addClass("has-success"),
                e.remove()):e.addClass("valid").closest(".form-group").removeClass("has-error").addClass("has-success")},
        submitHandler:function(e){i.show(), t.hide()}});
        var a = function(){$("#tab5 .form-control-static", r).each(function(){
        var e = $('[name="' + $(this).attr("data-display") + '"]', r);
                if (e.is(":radio") && (e = $('[name="' + $(this).attr("data-display") + '"]:checked', r)), e.is(":text") || e.is("textarea"))$(this).html(e.val());
                else if (e.is("select"))$(this).html(e.find("option:selected").text());  else if (e.is(":radio") && e.is(":checked"))$(this).html(e.attr("data-title")); })},
        o = function(e, r, t){var i = r.find("li").length, o = t + 1; $(".step-title", $("#form_wizard_1")).text("Step " + (t + 1) + " of " + i), jQuery("li", $("#form_wizard_1")).removeClass("done");
                for (var n = r.find("li"), s = 0; t > s; s++)jQuery(n[s]).addClass("done"); 1 == o?$("#form_wizard_1").find(".button-previous").hide():$("#form_wizard_1").find(".button-previous").show(),
                o >= i?($("#form_wizard_1").find(".button-next").hide(), $("#form_wizard_1").find(".button-submit").show(), a()):($("#form_wizard_1").find(".button-next").show(),
                $("#form_wizard_1").find(".button-submit").hide()), App.scrollTo($(".page-title"))};
        $("#form_wizard_1").bootstrapWizard({nextSelector:".button-next", previousSelector:".button-previous",
        onTabClick:function(e, r, t, i){return!1},
        onNext:function(e, a, n){            
            
            return i.hide(), t.hide(), 0 == r.valid()?!1:void o(e, a, n)},
        onPrevious:function(e, r, a){i.hide(), t.hide(), o(e, r, a)},
        onTabShow:function(e, r, t){var i = r.find("li").length, a = t + 1, o = a / i * 100; $("#form_wizard_1").find(".progress-bar").css({width:o + "%"})}}),
        $("#form_wizard_1").find(".button-previous").hide(), $("#form_wizard_1 .button-submit").click(function(){}).hide(),
        $("#country_list", r).change(function(){r.validate().element($(this))})}}}}(); jQuery(document).ready(function(){FormWizard.init()});