$(document).ready( function() {
setTooltip('#idNombre','Nombre del Complejo');
$("#divmultiplenames").click(function(){$("#divname").append("<label for='name'> *Nombre</label><div id='divname'><input id='idNombre' type='text' name='name' value='' ></div>")});
setTooltip('#idDescripcion','Descripci&oacute;n del Complejo');
setTooltip('#idDireccion','Direcci&oacute;n del Complejo');
setTooltip('#idBarrio','Barrio del Complejo');
setTooltip('#depositLimit', 'Cantidad de d&iacute;as l&iacute;mite que ' +
		'dura una reserva se&ntilde;ada' );
setTooltip('#bookingLimit', 'Cantidad de d&iacute;as l&iacute;mite que ' +
		'dura una reserva sin ser pagada');
});