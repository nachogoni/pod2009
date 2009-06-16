jQuery( function($) {
	$.datepicker.regional['es'] = {
		closeText : 'Cerrar',
		prevText : '&#x3c;Ant',
		nextText : 'Sig&#x3e;',
		currentText : 'Hoy',
		monthNames : [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
				'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre',
				'Diciembre' ],
		monthNamesShort : [ 'Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul',
				'Ago', 'Sep', 'Oct', 'Nov', 'Dic' ],
		dayNames : [ 'Domingo', 'Lunes', 'Martes', 'Mi&eacute;rcoles',
				'Jueves', 'Viernes', 'S&aacute;bado' ],
		dayNamesShort : [ 'Dom', 'Lun', 'Mar', 'Mi&eacute;', 'Juv', 'Vie',
				'S&aacute;b' ],
		dayNamesMin : [ 'Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'S&aacute;' ],
		dateFormat : 'dd/mm/yy',
		firstDay : 0,
		isRTL : false
	};
	$.datepicker.setDefaults($.datepicker.regional['es']);
	
});

function addCalendar(element, openCallback, closeCallback) {

	openCallback = (openCallback == undefined) ? function(input) {
	} : openCallback;
	closeCallback = (closeCallback == undefined) ? function(date) {
	} : closeCallback;

	$( function() {
		console.log($.extend( {
			beforeShow : openCallback,
			onClose : closeCallback,
			showMonthAfterYear : false,
			minDate : new Date()
		}, $.datepicker.regional['es']))
		
		$(element).datepicker($.extend( {
			beforeShow : openCallback,
			onClose : closeCallback,
			showMonthAfterYear : false,
			minDate : new Date()
		}, $.datepicker.regional['es']));
		//$(element).datepicker('option', 'minDate', new Date());
		//$(element).datepicker('option', 'showMonthAfterYear', false);
		//$(element).datepicker('option', 'onClose', closeCallback);
		//$(element).datepicker('option', 'beforeShow', closeCallback);
		
	});
}