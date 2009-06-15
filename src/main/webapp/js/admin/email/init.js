$(document).ready( function() {
setTooltip('#idMail','Correo electr&oacute;nico del usuario');
$("#multipleEmail").click(function(){$("#divemail").append("<label for='email'> *Correo</label><div id='divemail'><input id='idMail' type='text' name='email' value='25@admin1.com'  class='required'></div>")});
});