$(document).ready( function() {
setTooltip('#idNombre','Nombre del Complejo');
setTooltip('#idDescripcion','Descripcion del Complejo');
setTooltip('#idDireccion','Direccion del Complejo');
setTooltip('#idNeighbourhood','Barrio del Complejo');
setTooltip('#idTown','Ciudad del Complejo');
setTooltip('#idState','Provincia del Complejo');
setTooltip('#idZipCode','Codigo Postal del Complejo');
setTooltip('#idLatitude','Latidud del Complejo');
setTooltip('#idLongitude','Longitud del Complejo');
setTooltip('#idTelephone','Telefono del Complejo');
$("#multiplePhone").click(function(){$("#divtelephone").append("<label for='telephone'> *Teléfono</label><div id='divtelephone'><input id='idTelephone' type='text' name='telephone' value='4444-8888' ></div>")});
setTooltip('#idCountry','Pais del Complejo');
});