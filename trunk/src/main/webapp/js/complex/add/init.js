$(document).ready( function() {
setTooltip('#idNombre','Nombre del Complejo');
setTooltip('#idDescripcion','Descripci&oacute;n del Complejo');
setTooltip('#idDireccion','Direcci&oacute;n del Complejo');
setTooltip('#idNeighbourhood','Barrio del Complejo');
setTooltip('#idTown','Ciudad del Complejo');
setTooltip('#idState','Provincia del Complejo');
setTooltip('#idZipCode','C&oacute;digo Postal del Complejo');
setTooltip('#idLatitude','Latitud del Complejo');
setTooltip('#idLongitude','Longitud del Complejo');
setTooltip('#idTelephone','Tel&eacute;fono del Complejo');
$("#multiplePhone").click(function(){$("#divtelephone").append("<label for='telephone'> *Tel&eacute;fono</label><div id='divtelephone'><input id='idTelephone' type='text' name='telephone' value='1234-1232' ></div>")});
setTooltip('#idCountry','Pa&iacute;s del Complejo');
});