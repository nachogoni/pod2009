$(document).ready( function() {
$('#idDay').datepicker($.extend( {beforeShow : null,	onClose : null,showMonthAfterYear : false,minDate : new Date()}, $.datepicker.regional['es']));
$("#idFrom").timepickr();
$("#idTo").timepickr();
});