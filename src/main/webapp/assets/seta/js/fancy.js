$('.fancybox').fancybox();

$("a.fancyBoxRaf").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    centerOnScroll: true,
    width: 1200,
    minHeight: 800,
    overlayOpacity: 0,
    overlayShow: true

});

$("a.fancyBoxDon").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    autoSize: false,
    fitToView: false,
    height: 450,
    width: 1100,
    centerOnScroll: true,
    overlayOpacity: 0,
    overlayShow: true

});

$("a.fancyBoxDonRef").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    autoSize: false,
    fitToView: false,
    height: 450,
    width: 900,
    centerOnScroll: true,
    overlayOpacity: 0,
    overlayShow: true,
    afterClose: function () {
        location.reload();
    }

});

$("a.fancyBoxRafRef").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    centerOnScroll: true,
    width: 1200,
    overlayOpacity: 0,
    overlayShow: true,
    afterClose: function () {
        location.reload();
    }
});

$("a.fancyBoxRafFull").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    openEffect : 'elastic',
    closeEffect : 'elastic',
    closeBtn: true,
    type: 'iframe',
    centerOnScroll: true,
    width: '100%',
    height: '100%',
    overlayOpacity: 0,
    overlayShow: true
});

$("a.fancyBoxRafFullRef").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    openEffect : 'elastic',
    closeEffect : 'elastic',
    closeBtn: true,
    type: 'iframe',
    centerOnScroll: true,
    width: '100%',
    height: '100%',
    overlayOpacity: 0,
    overlayShow: true,
    afterClose: function () {
        location.reload();
    }
});


$("a.fancyBoxRaf3").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    centerOnScroll: true,
    width: 1400,
    overlayOpacity: 0,
    overlayShow: true,
    afterShow: function () { // for v2.0.6+ use : 'beforeShow' 
        var win = null;
        var content = $('.fancybox-skin'); // for v2.x use : var content = $('.fancybox-inner');
        alert(content);
        $('.fancybox-wrap').append('<div id="fancy_print">Vincenzo</div>'); // for v2.x use : $('.fancybox-wrap').append(...
        $('#fancy_print').bind("click", function () {
            win = window.open("width=200,height=200");
            self.focus();
            win.document.open();
            win.document.write('<' + 'html' + '><' + 'head' + '><' + 'style' + '>');
            win.document.write('body, td { font-family: Verdana; font-size: 10pt;}');
            win.document.write('<' + '/' + 'style' + '><' + '/' + 'head' + '><' + 'body' + '>');
            win.document.write(content.html());
            win.document.write('<' + '/' + 'body' + '><' + '/' + 'html' + '>');
            win.document.close();
            win.print();
            win.close();
        }); // bind
    } //onComplete
});

