/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var FormInputMask = function(){var a = function(){$("#mask_date").inputmask("d/m/y", {autoUnmask:!0}), $("#mask_date1").inputmask("d/m/y", {placeholder:"*"}), $("#mask_date2").inputmask("d/m/y", {placeholder:"dd/mm/yyyy"}), $("#mask_phone").inputmask("mask", {mask:"(999) 999-9999"}), $("#mask_tin").inputmask({mask:"99-9999999", placeholder:""}),$("#annocostruzione").inputmask({mask:"9", repeat:4, greedy:!1}),$("#spesecond").inputmask({mask:"9", repeat:9, greedy:!1}),$("#totpiani").inputmask({mask:"9", repeat:9, greedy:!1}),$("#piano").inputmask({mask:"9", repeat:9, greedy:!1}),$("#provvnetwork").inputmask({mask:"9", repeat:9, greedy:!1}),$("#mq").inputmask({mask:"9", repeat:9, greedy:!1}),$("#prezzo").inputmask({mask:"9", repeat:10, greedy:!1}), $("#tel1").inputmask({mask:"9", repeat:10, greedy:!1}),$("#tel2").inputmask({mask:"9", repeat:10, greedy:!1}),$("#fax").inputmask({mask:"9", repeat:10, greedy:!1}), $("#mask_decimal").inputmask("decimal", {rightAlignNumerics:!1}),  $("#mask_currency2").inputmask("$ 9,999.99", {numericInput:!0, rightAlignNumerics:!1, greedy:!1}), $("#mask_ssn").inputmask("999-99-9999", {placeholder:" ", clearMaskOnLostFocus:!0})}, n = function(){$("#input_ipv4").ipAddress(), $("#input_ipv6").ipAddress({v:6})}; return{init:function(){a(), n()}}}(); App.isAngularJsApp() === !1 && jQuery(document).ready(function(){FormInputMask.init()});


