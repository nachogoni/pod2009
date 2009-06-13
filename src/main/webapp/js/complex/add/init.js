$(document).ready( function() {
setTooltip('#idNombre','Nombre del Complejo');
$("#divmultiplenames").click(function(){$("#divname").append("<label for='name'> *Nombre</label><div id='divname'><input id='idNombre' type='text' name='name' value='' ></div>")});
setTooltip('#idDescripcion','Descripción del Complejo');
setTooltip('#idDireccion','Dirección del Complejo');
setTooltip('#idBarrio','Barrio del Complejo');
});