$(document).ready( function() {
setTooltip('#idMail','Correo electr&oacute;nico del usuario');
$("#multipleEmail").click(function(){$("#divemail").append("<label for='email'> *Correo</label><div id='divemail'><input id='idMail' type='text' name='email' value='2@user1.com'  class='required'></div>")});
setTooltip('#','Días antes de que el usuario sea notificado de una cauida de reserva.');
});