/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function getSumRimborso(idrimborso, cod_pol, bando) {
    $.get("OperazioniRevisore?type=16&idrimborso=" + idrimborso + "&politica=" + cod_pol + "&bando=" + bando, function (response) {
        
        var html = $('#div_descrizione').html();
        
        $('#div_descrizione').html(html+'<input type="hidden" name="ctrl_rimborso" value='+response+'>');
        var s = response.split('.');
//        alert(new Intl.NumberFormat('de-DE', {style: 'currency'}).format(response));
        $('#totale').val(s[0]);
        if (s[1].length > 1) {
            $('#centesimi').val(s[1]);
        } else {
            $('#centesimi').val(s[1]+"0");
        }
        setAccettaID(idrimborso);
    })
}